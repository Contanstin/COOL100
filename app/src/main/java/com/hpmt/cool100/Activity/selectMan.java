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
import com.hpmt.cool100.adapter.ManListAdapter;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.config.MacrosConfig;
import com.hpmt.cool100.model.BuildingList;
import com.hpmt.cool100.model.ManList;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class selectMan extends BaseActivity implements View.OnClickListener {
    private List<ManList> lists = new ArrayList<>();
    private List<ManList> Orlist = new ArrayList<>();//
    private ManListAdapter adapter;
    private ListView listView;
    private  static  String vkey;
    private  static  String id;
    private  static  String manType;
    private  static  String mannum;
    private ManList manList;
    private EditText et_filter;
    private Button delete;
    private ImageButton imgBtn_left;
    private TextView txt_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_man);
        Intent Mesage=getIntent();
        vkey=Mesage.getStringExtra("vkey");
        id=Mesage.getStringExtra("id");
        manType=Mesage.getStringExtra("manType");
        mannum=Mesage.getStringExtra("mannum");
        GetDate();
        imgBtn_left = (ImageButton) findViewById(R.id.left_imgBtn);
        imgBtn_left.setOnClickListener(this);
        txt_title = (TextView) findViewById(R.id.center_txt);
        //imgBtn_left.setImageResource(R.drawable.fanhui);
        imgBtn_left.setVisibility(View.VISIBLE);
        txt_title.setVisibility(View.VISIBLE);
        listView = (ListView) findViewById(R.id.lv_left_menu);
        adapter = new ManListAdapter(selectMan.this, R.layout.manlist, lists);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                manList = lists.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(selectMan.this);

                builder.setTitle(R.string.kindly_remind);
                builder.setMessage(R.string.choose);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //System.out.println("点了确定");
                        Intent intent = new Intent();
                        intent.putExtra("manName", manList.getManName());
                        intent.putExtra("manId", manList.getManId());
                        intent.putExtra("manTel", manList.getManphone());
                        intent.putExtra("mannum1", mannum);
                        setResult(5, intent);
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

        delete=(Button) findViewById(R.id.searchdelete);
        delete.setOnClickListener(this);
    }



    private void GetDate() {
        RequestParams params = new RequestParams();
        params.put("vkey",vkey);
        params.put("id",id);
        params.put("manType",manType);
        params.put("pageNumber", 1);
        params.put("pageSize",5000);
        HttpGetData.post(selectMan.this,
                //MacrosConfig.BaseUrl + "/MontServer/LoginServlet", params,
                MacrosConfig.BaseUrl +"/hpmt/pageMan.mvc", params,
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
                                String phone = value.getString("phone");
                                String manName = value.getString("manName");
                                String manId = value.getString("id");
                                //所有监控电梯的长连接
                                Orlist.add(new ManList(manId,manName,phone));
                                lists.add(new ManList(manId,manName,phone));

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
                        Toast.makeText(selectMan.this, error,
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
            for (ManList mlist : Orlist) {
                String name = mlist.getManName();
                if (name.indexOf(filterStr.toString()) != -1 ) {
                    lists.add(mlist);
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
                if (SoftKeyboardUtils.isSoftShowing(selectMan.this)){
                    SoftKeyboardUtils.hideSoftKeyboard(selectMan.this);
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
