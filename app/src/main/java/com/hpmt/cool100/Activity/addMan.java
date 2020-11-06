package com.hpmt.cool100.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hpmt.cool100.R;
import com.hpmt.cool100.Util.http.HttpGetData;
import com.hpmt.cool100.Util.http.IsNetWorkConnected;
import com.hpmt.cool100.Util.tools.JsonUtils;
import com.hpmt.cool100.Util.tools.MD5Utils;
import com.hpmt.cool100.Util.tools.ShowAlertView;
import com.hpmt.cool100.Util.tools.SoftKeyboardUtils;
import com.hpmt.cool100.adapter.ManListAdapter;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.config.MacrosConfig;
import com.hpmt.cool100.model.ManList;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class addMan extends BaseActivity implements View.OnClickListener {

    private  static  String vkey;
    private  static  String id;
    private  static  String manType;
    private  static  String mannum;
    private EditText  edt_1;     //人员
    private EditText  edt_2;//电话
    private EditText  edt_3;//密码
    private TextView  edt_4;//单位
    private EditText  edt_5;
    private TextView tx_hint1;
    private Button  commit;
    private ImageButton add;
    private ImageButton imgBtn_left;
    private TextView txt_title;
    private JSONObject value;
    private RelativeLayout num1;
    private RelativeLayout num2;
    private int MIN_CLICK_DELAY_TIME = 3000;
    private long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man);
        Intent Mesage=getIntent();
        vkey=Mesage.getStringExtra("vkey");
        id=Mesage.getStringExtra("id");
        manType=Mesage.getStringExtra("manType");
        mannum=Mesage.getStringExtra("mannum");

        init();
        if(manType.equals("1"))
        {

            tx_hint1.setText(R.string.maint_person);
            edt_1.setHint(R.string.maint_person);
            num1.setVisibility(View.VISIBLE);
            num2.setVisibility(View.VISIBLE);
        }
        else
        {
            tx_hint1.setText(R.string.manager);
            edt_1.setHint(R.string.manager);

        }
    }


    private void init() {
        edt_1=(EditText)findViewById(R.id.edt_1);
        edt_2=(EditText)findViewById(R.id.edt_2);
        edt_3=(EditText)findViewById(R.id.edt_3);
        edt_4=(TextView)findViewById(R.id.edt_4);
        edt_5=(EditText)findViewById(R.id.edt_5);
        commit = (Button) findViewById(R.id.commit);
        tx_hint1=(TextView)findViewById(R.id.tx_hint1);
        commit.setOnClickListener(this);
        add = (ImageButton) findViewById(R.id.addButton);
        add.setOnClickListener(this);
        imgBtn_left = (ImageButton) findViewById(R.id.left_imgBtn);
        imgBtn_left.setOnClickListener(this);
        txt_title = (TextView) findViewById(R.id.center_txt);
        //imgBtn_left.setImageResource(R.drawable.fanhui);
        imgBtn_left.setVisibility(View.VISIBLE);
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(R.string.add);
        num1=(RelativeLayout)findViewById(R.id.num1);
        num2=(RelativeLayout)findViewById(R.id.num2);
    }
    private void addManDate() {
        if (edt_1.getText().toString().length() == 0) {
            Toast.makeText(addMan.this,R.string.fill_person,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams();
        String password = null;
        if(edt_3.getText().toString().length()!=0) {
            try {
                password = MD5Utils.md5Password(edt_3.getText().toString());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        params.put("vkey",vkey);
        params.put("manType",manType);
        params.put("manName",edt_1.getText().toString());
        params.put("phone",edt_2.getText().toString());
        params.put("password",password);
        params.put("unitId",edt_5.getText().toString());
        HttpGetData.post(addMan.this,
                //MacrosConfig.BaseUrl + "/MontServer/LoginServlet", params,
                MacrosConfig.BaseUrl +"/hpmt/editMan.mvc", params,
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
                           // JSONObject status = object.getJSONObject("status");
                            value = object.getJSONObject("content");
                            int status = JsonUtils.getJSONInt(object,"status");
                            if(status==1)
                            {


                                ShowAlertView.showDialog(
                                        addMan.this, getResources().getString(R.string.success),
                                        new ShowAlertView.ClickCallback() {

                                            @Override
                                            public void clickOk() {
                                                try {

                                                    Intent intent = new Intent();
                                                    intent.putExtra("manName", value.getString("manName"));
                                                    intent.putExtra("manId",value.getString("id"));
                                                    intent.putExtra("mannum1", mannum);
                                                    setResult(7, intent);
                                                    finish();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                            }
                            else if (status==0)
                            {
                                Toast.makeText(addMan.this, R.string.add_fail,
                                        Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void fail(String error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(addMan.this, error,
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit:
                if (!isFastClick()) {
                    Toast.makeText(getApplicationContext(),
                            R.string.click_3s,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //提交
                addManDate();
                break;
            case R.id.addButton:
                //提交
                Intent maintainintent = new Intent(addMan.this, selectUnit.class);
                maintainintent.putExtra("id", id);
                maintainintent.putExtra("vkey", vkey);
                maintainintent.putExtra("unitType", "1");
                maintainintent.putExtra("unitnum","1");
                startActivityForResult(maintainintent,3);
                break;
            case R.id.left_imgBtn:
                if (SoftKeyboardUtils.isSoftShowing(addMan.this)){
                    SoftKeyboardUtils.hideSoftKeyboard(addMan.this);
                }else {
                    onBackPressed();
                }
                finish();
                break;
            default:
                break;
        }

    }

    //接受返回值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        //智能硬件
        if (requestCode == 3 && resultCode == 6) {
            edt_5.setText(data.getStringExtra("unitId"));
            edt_4.setText(data.getStringExtra("unitName"));
        }

    }

    //防止快速点击
    public  boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

}
