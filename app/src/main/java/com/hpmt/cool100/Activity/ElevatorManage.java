package com.hpmt.cool100.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hpmt.cool100.R;
import com.hpmt.cool100.Util.http.HttpGetData;
import com.hpmt.cool100.Util.http.IsNetWorkConnected;
import com.hpmt.cool100.Util.tools.JsonUtils;
import com.hpmt.cool100.adapter.ElevatorListAdapter;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.config.MacrosConfig;
import com.hpmt.cool100.model.ElevatorList;
import com.loopj.android.http.RequestParams;
import com.yzq.zxinglibrary.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ElevatorManage extends BaseActivity implements View.OnClickListener {
    private List<ElevatorList> lists = new ArrayList<>();
    private List<ElevatorList> Orlist = new ArrayList<>();
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;
    private ElevatorListAdapter adapter;
    private ListView listView;
    private Button addele;
    private EditText et_filter;
    private Button delete;
    private  static  String eleid;
    private  static  String vkey;
    private  static  String userid;
    private ImageButton imgBtn_left;
    private TextView txt_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_manage);
        Intent Mesage = getIntent();
        vkey = Mesage.getStringExtra("vkey");
        userid = Mesage.getStringExtra("id");
        GetDate();
        listView = (ListView) findViewById(R.id.lv_left_menu);
        adapter = new ElevatorListAdapter(ElevatorManage.this, R.layout.elelist, lists);
        listView.setAdapter(adapter);
        //获取数据实时显示
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (!isFastClick()) {
                    Toast.makeText(getApplicationContext(),
                            R.string.click_1s,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                ElevatorList elevatorList = lists.get(position);

                //实时获取电梯信息
                if(IsNetWorkConnected.isNetWorkConnected(ElevatorManage.this) && IsNetWorkConnected.isConnByHttp())
                { //获取id跳转界面
                    eleid = elevatorList.getEleId();
                    Intent intent = new Intent(ElevatorManage.this, AddEleActivity.class);
                    intent.putExtra("eleid", eleid);
                    intent.putExtra("vkey", vkey);
                    intent.putExtra("userid", userid);
                    intent.putExtra("function", "edit");
                    startActivityForResult(intent,5);
                    //startActivity(intent);
                }
            }
        });
        addele=(Button)findViewById(R.id.addele);
        addele.setOnClickListener(this);
        imgBtn_left = (ImageButton) findViewById(R.id.left_imgBtn);
        txt_title = (TextView) findViewById(R.id.center_txt);
        //imgBtn_left.setImageResource(R.drawable.fanhui);
        imgBtn_left.setVisibility(View.VISIBLE);
        imgBtn_left.setOnClickListener(this);
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(R.string.elevator_info);
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
        params.put("id",userid);
        params.put("pageNumber", 1);
        params.put("pageSize",4000);
        HttpGetData.post(ElevatorManage.this,
                //MacrosConfig.BaseUrl + "/MontServer/LoginServlet", params,
                MacrosConfig.BaseUrl +"/hpmt/findElevatorById.mvc", params,
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
                                String E = value.getString("elevatorNum");
                                String I = value.getString("insideNum");
                                String EleId = value.getString("id");
                                //所有监控电梯的长连接
                                Orlist.add(new ElevatorList(E, I,EleId));
                                lists.add(new ElevatorList(E, I,EleId));

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
                        Toast.makeText(ElevatorManage.this, error,
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addele:
                //跳转界面
                Intent adddintent = new Intent(ElevatorManage.this, AddEleActivity.class);
                adddintent.putExtra("eleid","none");
                adddintent.putExtra("vkey", vkey);
                adddintent.putExtra("userid", userid);
                adddintent.putExtra("function", "add");
                startActivityForResult(adddintent,5);
                //startActivity(adddintent);

                break;
            case R.id.searchdelete:
                et_filter.setText("");
                lists.clear();
                lists.addAll(Orlist);
                adapter.notifyDataSetChanged();
                break;
            case R.id.left_imgBtn:
                Intent intent = new Intent();
                setResult(16, intent);
                finish();
                break;
            default:
                break;
        }
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
            for (ElevatorList elist : Orlist) {
                String name = elist.getElevatorNum();
                if (name.indexOf(filterStr.toString()) != -1 ) {
                    lists.add(elist);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK){
        Intent intent = new Intent();
        setResult(16, intent);
        finish();
        return true;
    }
    return super.onKeyDown(keyCode, event);
}



    //接受返回值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        //电梯管理
        if (resultCode == 18) {
            //刷新数据
            et_filter.setText("");
            lists.clear();
            Orlist.clear();
            GetDate();
        }
        else if(resultCode == 19)
        {
            //刷新数据
            et_filter.setText("");
            lists.clear();
            Orlist.clear();
            GetDate();
        }
        else if(resultCode == 20)
        {
            //刷新数据
            et_filter.setText("");
            lists.clear();
            Orlist.clear();
            GetDate();
        }

    }

    //防止快速点击
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }



}
