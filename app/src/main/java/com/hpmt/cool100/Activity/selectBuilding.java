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
import com.hpmt.cool100.Util.http.IsNetWorkConnected;
import com.hpmt.cool100.Util.tools.JsonUtils;
import com.hpmt.cool100.Util.tools.SoftKeyboardUtils;
import com.hpmt.cool100.adapter.ElevatorListAdapter;
import com.hpmt.cool100.adapter.BuildingListAdapter;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.config.MacrosConfig;
import com.hpmt.cool100.model.ElevatorList;
import com.hpmt.cool100.model.BuildingList;
import com.hpmt.cool100.model.HardwareList;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class selectBuilding extends BaseActivity  implements View.OnClickListener{
    private List<BuildingList> lists = new ArrayList<>();
    private List<BuildingList> Orlist = new ArrayList<>();//
    private BuildingListAdapter adapter;
    private ListView listView;
    private  static  String vkey;
    private  static  String id;
    private BuildingList buildingList;
    private EditText et_filter;
    private Button delete;
    private ImageButton imgBtn_left;
    private TextView txt_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_build);
        Intent Mesage=getIntent();
        vkey=Mesage.getStringExtra("vkey");
        id=Mesage.getStringExtra("id");
        GetDate();
        imgBtn_left = (ImageButton) findViewById(R.id.left_imgBtn);
        imgBtn_left.setOnClickListener(this);
        txt_title = (TextView) findViewById(R.id.center_txt);
        //imgBtn_left.setImageResource(R.drawable.fanhui);
        imgBtn_left.setVisibility(View.VISIBLE);
        txt_title.setVisibility(View.VISIBLE);
        listView = (ListView) findViewById(R.id.lv_left_menu);
        adapter = new BuildingListAdapter(selectBuilding.this, R.layout.buildlist, lists);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                buildingList = lists.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(selectBuilding.this);

                builder.setTitle(R.string.kindly_remind);
                builder.setMessage(R.string.choose);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //System.out.println("点了确定");
                        Intent intent = new Intent();
                        intent.putExtra("buildName", buildingList.getBuildcompanyName());
                        intent.putExtra("buildId", buildingList.getBuildId());
                        intent.putExtra("buildCity", buildingList.getBuildCityDistrict());
                        setResult(4, intent);
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
        params.put("pageNumber", 1);
        params.put("pageSize",5000);
        HttpGetData.post(selectBuilding.this,
                //MacrosConfig.BaseUrl + "/MontServer/LoginServlet", params,
                MacrosConfig.BaseUrl +"/hpmt/pageBuilding.mvc", params,
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

                                String buildCity = value.getString("provinceCityDistrict");
                                String buildName = value.getString("buildingName");
                                String buildId = value.getString("id");
                                //所有监控电梯的长连接
                                Orlist.add(new BuildingList(buildId,buildCity,buildName));
                                lists.add(new BuildingList(buildId,buildCity,buildName));

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
                        Toast.makeText(selectBuilding.this, error,
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
        List<ElevatorList> filterDateList = new ArrayList<ElevatorList>();

        if (TextUtils.isEmpty(filterStr)) {
            lists.clear();
            lists.addAll(Orlist);
        } else {
            lists.clear();
            for (BuildingList blist : Orlist) {
                String name = blist.getBuildcompanyName();
                if (name.indexOf(filterStr.toString()) != -1 ) {
                    lists.add(blist);
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
                if (SoftKeyboardUtils.isSoftShowing(selectBuilding.this)){
                    SoftKeyboardUtils.hideSoftKeyboard(selectBuilding.this);
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
