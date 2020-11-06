package com.hpmt.cool100.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.hpmt.cool100.R;
import com.hpmt.cool100.Util.http.HttpGetData;
import com.hpmt.cool100.Util.tools.JsonUtils;
import com.hpmt.cool100.Util.tools.SoftKeyboardUtils;
import com.hpmt.cool100.adapter.UnitListAdapter;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.config.MacrosConfig;
import com.hpmt.cool100.model.ManList;
import com.hpmt.cool100.model.UnitList;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class selectUnit extends BaseActivity implements View.OnClickListener{
    private List<UnitList> lists = new ArrayList<>();
    private List<UnitList> Orlist = new ArrayList<>();//
    private UnitListAdapter adapter;
    private ListView listView;
    private   String vkey;
    private    String id;
    private   String unitType;
    private   String unitnum;
    private UnitList unitList;
    private EditText et_filter;
    private Button delete;
    private ImageButton imgBtn_left;
    private TextView txt_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_unit);
        Intent Mesage=getIntent();
        vkey=Mesage.getStringExtra("vkey");
        id=Mesage.getStringExtra("id");
        unitType=Mesage.getStringExtra("unitType");
        unitnum=Mesage.getStringExtra("unitnum");
        GetDate();

        imgBtn_left = (ImageButton) findViewById(R.id.left_imgBtn);
        imgBtn_left.setOnClickListener(this);
        txt_title = (TextView) findViewById(R.id.center_txt);
        //imgBtn_left.setImageResource(R.drawable.fanhui);
        imgBtn_left.setVisibility(View.VISIBLE);
        txt_title.setVisibility(View.VISIBLE);
        listView = (ListView) findViewById(R.id.lv_left_menu);
        adapter = new UnitListAdapter(selectUnit.this, R.layout.unitlist, lists);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                unitList = lists.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(selectUnit.this);

                builder.setTitle(R.string.kindly_remind);
                builder.setMessage(R.string.choose);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //System.out.println("点了确定");
                        Intent intent = new Intent();
                        intent.putExtra("unitName", unitList.getUnitcompanyName());
                        intent.putExtra("unitId", unitList.getUnitId());
                        intent.putExtra("unitnum1",unitnum );
                        setResult(6, intent);
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                //一样要show
                builder.show();

            }
        });
        et_filter=(EditText)findViewById(R.id.search);

        delete=(Button) findViewById(R.id.searchdelete);
        delete.setOnClickListener(this);
        et_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterData(s.toString());
            }
        });

    }



    private void GetDate() {
        RequestParams params = new RequestParams();
        params.put("vkey",vkey);
        params.put("id",id);
        params.put("unitType", unitType);
        params.put("pageNumber", 1);
        params.put("pageSize",5000);
        HttpGetData.post(selectUnit.this,
                //MacrosConfig.BaseUrl + "/MontServer/LoginServlet", params,
                MacrosConfig.BaseUrl +"/hpmt/pageUnit.mvc", params,
                getResources().getString(R.string.loading),
                new HttpGetData.getDataCallBack() {
                    @Override
                    public void succcess(String res) {//这里面可以看到lists的值
                        // TODO Auto-generated method stub
                        Log.e("res", res);//打印错误信息
                        try {
                            JSONObject object = new JSONObject(res);
//                            String code = JsonUtils.getJSONString(object,
//                                    "content");
                            JSONObject content = object.getJSONObject("content");
                            JSONArray Ele = content.getJSONArray("rows");
                            for (int i = 0; i < Ele.length(); i++) {

                                JSONObject value = Ele.getJSONObject(i);
                                String contactName = value.getString("contactName");
                                String unitName = value.getString("companyName");
                                String unitId = value.getString("id");
                                //所有监控电梯的长连接
                                Orlist.add(new UnitList(unitId,unitName,contactName));
                                lists.add(new UnitList(unitId,unitName,contactName));

                            }
                            adapter.notifyDataSetChanged();
                            String status = JsonUtils.getJSONString(object, "status");


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void fail(String error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(selectUnit.this, error,
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }


    //查询
    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<ManList> filterDateList = new ArrayList<ManList>();

        if (TextUtils.isEmpty(filterStr)) {
            lists.clear();
            lists.addAll(Orlist);
        } else {
            lists.clear();
            for (UnitList ulist : Orlist) {
                String name = ulist.getUnitcompanyName();
                if (name.indexOf(filterStr.toString()) != -1 ) {
                    lists.add(ulist);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchdelete:
                et_filter.setText("");
                lists.clear();
                lists.addAll(Orlist);
                adapter.notifyDataSetChanged();
                break;
            case R.id.left_imgBtn:
                if (SoftKeyboardUtils.isSoftShowing(selectUnit.this)){
                    SoftKeyboardUtils.hideSoftKeyboard(selectUnit.this);
                }else {
                    onBackPressed();
                }
                finish();
                break;
            default:
                break;
        }
    }
}
