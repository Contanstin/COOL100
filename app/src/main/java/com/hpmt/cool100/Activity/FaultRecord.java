package com.hpmt.cool100.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hpmt.cool100.R;
import com.hpmt.cool100.Util.http.HttpGetData;
import com.hpmt.cool100.adapter.FaultListAdapter;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.config.MacrosConfig;
import com.hpmt.cool100.model.FaultList;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FaultRecord extends BaseActivity implements View.OnClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private View footer;
    private TextView txt_title;
    private TextView text;
    private String key;
    private String elenum;
    private ImageButton imgBtn_back;// 返回按钮
    private List<FaultList> lists = new ArrayList<>();
    private List<FaultList> lists2 = new ArrayList<>();
    private List<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    FaultListAdapter faultadapter;
    private boolean loadFinishFlag;
    private int startIndex;
    private final int pageSize = 10;

    String locale ="";
    String lang ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faultrecord);
        txt_title = (TextView) findViewById(R.id.center_txt);
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(getResources().getString(
                R.string.faulttitle));
        imgBtn_back = (ImageButton) findViewById(R.id.left_imgBtn);
        imgBtn_back.setVisibility(View.VISIBLE);
        locale = Locale.getDefault().getLanguage();
        if(locale.equals("en"))
        {
            //英文
            lang="3";
        }
        else
        {
            lang="1";
        }
        imgBtn_back.setOnClickListener(this);
        Intent Mesage=getIntent();
        key=Mesage.getStringExtra("vkey");
        elenum=Mesage.getStringExtra("eleNum");
        text = (TextView) findViewById(R.id.text);
        text.setText(getResources().getString(
                R.string.ele_id)+elenum);
        if(elenum.length()>0) {
            GetDate();
        }
        else
        {
            Toast.makeText(FaultRecord.this,
                    R.string.choose_elevator,
                    Toast.LENGTH_SHORT).show();
        }
        listView = (ListView) this.findViewById(R.id.listview);
        footer = getLayoutInflater().inflate(R.layout.footer, null);
        faultadapter = new FaultListAdapter(FaultRecord.this, R.layout.recordlist, lists);
        listView.setAdapter(faultadapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        FaultList faultList = lists.get(position);
                        String faultcode=faultList.getfaultcode();
                        String name=faultList.getfaultname();
                        String reason=faultList.getfaultreason();
                        String method=faultList.getfaultmethod();
                        Intent fdetail = new Intent(FaultRecord.this,FaultDetail.class);
                        fdetail.putExtra("faultcode", faultcode);
                        fdetail.putExtra("reason", reason);
                        fdetail.putExtra("name", name);
                        fdetail.putExtra("method", method);
                        startActivity(fdetail);
                    }

                });

    }

    //获取故障记录列表
    private List<FaultList> GetDate() {
        RequestParams params = new RequestParams();
        params.put("vkey", key);
        params.put("elevatorNum", elenum);
        params.put("pageNumber",1);
        params.put("pageSize", 1000);
        params.put("lang", lang);
        HttpGetData.post(FaultRecord.this,
                //MacrosConfig.BaseUrl + "/MontServer/LoginServlet", params,
                MacrosConfig.BaseUrl + "/hpmt/findHistoryFaultListByVkey.mvc", params,
                getResources().getString(R.string.logining),
                new HttpGetData.getDataCallBack() {
                    @Override
                    public void succcess(String res) {//这里面可以看到lists的值
                        // TODO Auto-generated method stub
                        Log.e("res", res);//打印错误信息
                        try {
                            JSONObject object = new JSONObject(res);
                            JSONObject content = object.getJSONObject("content");
                            JSONArray record = content.getJSONArray("rows");
                            for (int i = 0; i < record.length(); i++) {
                                JSONObject value = record.getJSONObject(i);
                                String fualtcode = value.getString("errorCode");
                                String fualtfloor = value.getString("floor");
                                String fualtname = value.getString("error_name");
                                String stu_code = value.getString("latestSubErrCode");
                                String fualttime = value.getString("reportTime");
                                String reason = value.getString("reason");
                                String method = value.getString("method");
                                //所有监控电梯的长连接
                                lists.add(new FaultList(fualtcode, stu_code, fualtfloor, fualttime, fualtname,reason,method));
                            }
                            faultadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void fail(String error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(FaultRecord.this, error,
                                Toast.LENGTH_SHORT).show();

                    }
                });
        return lists;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.left_imgBtn:
            Intent intent = new Intent();
            setResult(17, intent);
        finish();
        break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            setResult(17, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
