package com.hpmt.cool100.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class addBuild extends BaseActivity implements View.OnClickListener {

    private  static  String vkey;
    private  static  String id;
    private  static  String city;
    private  static  String lnglat;
    private EditText  edt_1; //楼盘名称
    private EditText  edt_3;//使用场合
    private EditText  edt_4;//详细地址
    private TextView  edt_5;//省/市/区
    private TextView  edt_6;//经纬度
    private Button  commit;//
    private ImageButton  addbutton;
    private ImageButton imgBtn_left;
    private TextView txt_title;
    private JSONObject value;
    private int MIN_CLICK_DELAY_TIME = 3000;
    private long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build);
        Intent Mesage=getIntent();
        vkey=Mesage.getStringExtra("vkey");
        id=Mesage.getStringExtra("id");
        init();
    }


    private void init() {
        edt_1=(EditText)findViewById(R.id.edt_1);
        edt_3=(EditText)findViewById(R.id.edt_3);
        edt_4=(EditText)findViewById(R.id.edt_4);
        edt_5=(TextView)findViewById(R.id.edt_5);
        edt_6=(TextView)findViewById(R.id.edt_6);
        commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(this);
        addbutton = (ImageButton) findViewById(R.id.addButton);
        addbutton.setOnClickListener(this);
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
            Toast.makeText(addBuild.this,R.string.fill_property,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (edt_6.getText().toString().length() == 0) {
            Toast.makeText(addBuild.this,R.string.fill_latitude,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams();
        params.put("vkey",vkey);
        params.put("buildingName",edt_1.getText().toString());
        params.put("useOccasion",edt_3.getText().toString());
        params.put("detailAdd",edt_4.getText().toString());
        params.put("provinceCityDistrict",edt_5.getText().toString());
        params.put("latitudeLongitude",edt_6.getText().toString());
        // companyName  address   provinceCityDistrict  contactName   phone

        HttpGetData.post(addBuild.this,
                //MacrosConfig.BaseUrl + "/MontServer/LoginServlet", params,
                MacrosConfig.BaseUrl +"/hpmt/editBuilding.mvc", params,
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
                            //if(value.getString("count").equals("1"))
                            if(status==1)
                            {

                                //Intent intent = new Intent();
                              //  intent.putExtra("buildName", value.getString("buildingName"));
                               // intent.putExtra("buildId",value.getString("id"));
                             //   setResult(11, intent);
                             //   finish();

                                ShowAlertView.showDialog(
                                        addBuild.this, getResources().getString(R.string.success),
                                        new ShowAlertView.ClickCallback() {

                                            @Override
                                            public void clickOk() {
                                                //跳转界面，获取返回值
                                                try {
                                                    Intent intent = new Intent();
                                                    intent.putExtra("buildName", value.getString("buildingName"));
                                                    intent.putExtra("buildId",value.getString("id"));
                                                    setResult(11, intent);
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
                                Toast.makeText(addBuild.this, R.string.add_fail,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if (status==2)
                            {
                                Toast.makeText(addBuild.this, R.string.add_repeatedly,
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
                        Toast.makeText(addBuild.this, error,
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
                addBuildDate();
                break;
            case R.id.addButton:
                Intent Build1intent = new Intent(addBuild.this, webMap.class);
                Build1intent.putExtra("id", id);
                Build1intent.putExtra("vkey", vkey);
                startActivityForResult(Build1intent,4);
                break;
            case R.id.left_imgBtn:
                if (SoftKeyboardUtils.isSoftShowing(addBuild.this)){
                    SoftKeyboardUtils.hideSoftKeyboard(addBuild.this);
                }else {
                    onBackPressed();
                }

//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm.isActive()) {
//                   // imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
//                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                }

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
        if (requestCode == 4 && resultCode == 10) {
            city = data.getStringExtra("city");
            lnglat= data.getStringExtra("lnglat");
            edt_5.setText(city);
            edt_6.setText(lnglat);
        }

    }

    // 隐藏虚拟键盘
    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

        }
    }

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
