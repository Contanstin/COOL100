package com.hpmt.cool100.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hpmt.cool100.R;
import com.hpmt.cool100.Util.http.HttpGetData;
import com.hpmt.cool100.Util.tools.JsonUtils;
import com.hpmt.cool100.Util.tools.ShowAlertView;
import com.hpmt.cool100.Util.tools.SoftKeyboardUtils;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.config.MacrosConfig;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class addUnit extends BaseActivity implements View.OnClickListener {

    private  static  String vkey;
    private  static  String id;
    private  static  String unitType;
    private  static  String unitnum;
    private EditText  edt_1; //单位名称
    private EditText  edt_3;//地址
    private EditText  edt_4;//城市
    private EditText  edt_5;//联系人
    private EditText  edt_6;//电话
    private Button  commit;//
    private ImageButton imgBtn_left;
    private TextView txt_title;
    private JSONObject value;
    private List<String> unitList = new ArrayList<>();
    private int MIN_CLICK_DELAY_TIME = 3000;
    private long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        Intent Mesage=getIntent();
        vkey=Mesage.getStringExtra("vkey");
        id=Mesage.getStringExtra("id");
        unitType=Mesage.getStringExtra("unitType");
        unitnum=Mesage.getStringExtra("unitnum");
        init();
    }


    private void init() {
        edt_1=(EditText)findViewById(R.id.edt_1);

        edt_3=(EditText)findViewById(R.id.edt_3);
        edt_4=(EditText)findViewById(R.id.edt_4);
        edt_5=(EditText)findViewById(R.id.edt_5);
        edt_6=(EditText)findViewById(R.id.edt_6);
        commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(this);
        imgBtn_left = (ImageButton) findViewById(R.id.left_imgBtn);
        imgBtn_left.setOnClickListener(this);
        txt_title = (TextView) findViewById(R.id.center_txt);
        //imgBtn_left.setImageResource(R.drawable.fanhui);
        imgBtn_left.setVisibility(View.VISIBLE);
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(R.string.add);
    }
    private void addBuildDate() {
        if (edt_1.getText().toString().length() == 0) {
            Toast.makeText(addUnit.this,R.string.fill_unit,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams();
        params.put("vkey",vkey);
        params.put("unitType",unitType);
        params.put("companyName",edt_1.getText().toString());
        params.put("address",edt_3.getText().toString());
        params.put("provinceCityDistrict",edt_4.getText().toString());
        params.put("contactName",edt_5.getText().toString());
        params.put("phone",edt_6.getText().toString());
        // companyName  address   provinceCityDistrict  contactName   phone

        HttpGetData.post(addUnit.this,
                //MacrosConfig.BaseUrl + "/MontServer/LoginServlet", params,
                MacrosConfig.BaseUrl +"/hpmt/editUnit.mvc", params,
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
                           // if(value.getString("status").equals("1"))
                            if(status==1)
                            {
//                                Intent intent = new Intent();
//                                intent.putExtra("unitName", value.getString("companyName"));
//                                intent.putExtra("unitId",value.getString("id"));
//                                intent.putExtra("unitnum1", unitnum);
//                                setResult(8, intent);
//                                finish();

                                ShowAlertView.showDialog(
                                        addUnit.this, getResources().getString(R.string.success),
                                        new ShowAlertView.ClickCallback() {

                                            @Override
                                            public void clickOk() {
                                                //跳转界面，获取返回值
                                                try {
                                                    Intent intent = new Intent();
                                                    intent.putExtra("unitName", value.getString("companyName"));
                                                    intent.putExtra("unitId",value.getString("id"));
                                                    intent.putExtra("unitnum1", unitnum);
                                                    setResult(8, intent);
                                                    finish();
                                                   // value.getString("id");
                                                   // value.getString("companyName");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                //  finish();
                                            }
                                        });
                            }
                            else if (status==0)
                            {
                                Toast.makeText(addUnit.this, R.string.add_fail,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if (status==2)
                            {
                                Toast.makeText(addUnit.this, R.string.add_repeatedly,
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
                        Toast.makeText(addUnit.this, error,
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
                addBuildDate();
                break;
            case R.id.left_imgBtn:
                if (SoftKeyboardUtils.isSoftShowing(addUnit.this)){
                    SoftKeyboardUtils.hideSoftKeyboard(addUnit.this);
                }else {
                    onBackPressed();
                }
               // finish();
                break;
            default:
                break;
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
