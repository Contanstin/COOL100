package com.hpmt.cool100.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.hpmt.cool100.Util.tools.SoftKeyboardUtils;
import com.yzq.zxinglibrary.common.Constant;
import com.zhy.m.permission.MPermissions;
import com.hpmt.cool100.R;
import com.hpmt.cool100.Util.http.HttpGetData;
import com.hpmt.cool100.Util.tools.JsonUtils;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.config.MacrosConfig;
import com.loopj.android.http.RequestParams;
import com.yzq.zxinglibrary.android.CaptureActivity;


import org.json.JSONException;
import org.json.JSONObject;

public class AddDevice extends BaseActivity implements View.OnClickListener {
    private String Num;
    private int Type=1;

    private EditText text1;
    private EditText text3;
    private ImageButton imgBtn_back;// 返回按钮
    private ImageButton sao;
    private EditText text4;
    private EditText text5;
    private RadioButton Yes;
    private RadioButton No;
    private RadioButton Yes1;
    private RadioButton No1;

    private RadioButton cic_b;
    private RadioButton cic_b1;
    private RadioButton cic_c;
    private RadioButton cic_e;
    private RadioButton cic_e1;
    private RadioButton cic_g;
    private Button DeviceRe;
    private RelativeLayout mac;
    private RelativeLayout imsi;
    private String vkey;
    private String buttontype;
    private int push=1;
    private int push1=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        Intent v=getIntent();
        vkey=v.getStringExtra("vkey");
        buttontype=v.getStringExtra("buttontype");
        imgBtn_back = (ImageButton) findViewById(R.id.left_imgBtn);
        imgBtn_back.setVisibility(View.VISIBLE);
        //imgBtn_back.setImageResource(R.drawable.icon_fanhui);
        imgBtn_back.setOnClickListener(this);
        sao = (ImageButton) findViewById(R.id.right_imgBtn);
        sao.setVisibility(View.VISIBLE);
        //imgBtn_back.setImageResource(R.drawable.icon_fanhui);
        sao.setOnClickListener(this);
        //初始化
        text1 = (EditText) findViewById(R.id.edt1);//注册码
        JsonUtils.EditTextEnable(true,text1);
        //text2 = (TextView) findViewById(R.id.edt2);//设备类型
        text3 = (EditText) findViewById(R.id.edt3);//控制类型
        text4 = (EditText) findViewById(R.id.edt4);//mac
        text4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==12) {
                    text1.setText(MacToImsi(text4.getText().toString()));
                }

            }
        });

        text5 = (EditText) findViewById(R.id.edt5);//控制类型
        imsi = (RelativeLayout) findViewById(R.id.imsi);
        mac = (RelativeLayout) findViewById(R.id.mac);

        Yes = (RadioButton) findViewById(R.id.yes);//是
        Yes.setOnClickListener(this);
        No = (RadioButton) findViewById(R.id.no);//是
        No.setOnClickListener(this);
        Yes1 = (RadioButton) findViewById(R.id.yes1);//是
        Yes1.setOnClickListener(this);
        No1 = (RadioButton) findViewById(R.id.no1);//是
        No1.setOnClickListener(this);
        DeviceRe = (Button) findViewById(R.id.btn_ok);
        DeviceRe.setOnClickListener(this);
        cic_b = (RadioButton) findViewById(R.id.cic_b);//是
        cic_b.setOnClickListener(this);
        cic_b1 = (RadioButton) findViewById(R.id.cic_b1);//是
        cic_b1.setOnClickListener(this);
        cic_c = (RadioButton) findViewById(R.id.cic_c);//是
        cic_c.setOnClickListener(this);
        cic_e = (RadioButton) findViewById(R.id.cic_e);//是
        cic_e.setOnClickListener(this);
        cic_e1 = (RadioButton) findViewById(R.id.cic_e1);//是
        cic_e1.setOnClickListener(this);
        cic_g = (RadioButton) findViewById(R.id.cic_g);//是
        cic_g.setOnClickListener(this);
    }


   //Mac转imsi
    public static String MacToImsi(String a)
    {
        String str =a;
        String reg ="^[A-Fa-f0-9]+$";
        String regis="";
        boolean r = str.matches(reg);
        if (r==false&&str!=""){
            return "错误(error)";
        }
        if (str!=null&&str.length()==12){
            String sub1 = str.substring(0,6);
            int st1 =  Integer.parseInt(sub1,16);
            String sub2 = str.substring(6,12);
            int st2 = Integer.parseInt(sub2,16);
            regis= frontCompWithZore(st1%10000000,7)+""+frontCompWithZore(st2, 8);
           // regis= String.valueOf(st1)+String.valueOf(st2);
            if (regis.length()==15){
                return regis;

            }
        }
        return regis;
    }
    //类型
    public static int GetType(String a)
    {
        String str =a;
        int st1=0;
        if (str!=null&&str.length()==2){
            String sub1 = str.substring(0,2);
            st1 =  Integer.parseInt(sub1,2);
        }
        return st1;
    }
    public static String frontCompWithZore(int sourceDate,int formatLength)
    {

        String newString = String.format("%0"+formatLength+"d", sourceDate);
        return  newString;
    }
    //注册
    private void DeviceAdd() {

        RequestParams params = new RequestParams();
        if(Type==3) {
            if (text4.getText().toString().length() != 12) {
                Toast.makeText(AddDevice.this,
                        R.string.mac_err,
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (text1.getText().toString().length() != 15) {
            Toast.makeText(AddDevice.this,
                    R.string.registration_code_error,
                    Toast.LENGTH_SHORT).show();
            return;
        }
//        if(Type==2){
//            if (text5.getText().toString().length() != 15) {
//                Toast.makeText(AddDevice.this,
//                        R.string.imsi_err,
//                        Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
        else
        {
            text5.setText(text1.getText().toString());
        }


        params.put("registerNum",text1.getText().toString());
        params.put("imsi",text5.getText().toString());
        params.put("mac",text4.getText().toString());
        params.put("deviceType",Type);
        params.put("controlType",text3.getText().toString());
        params.put("notifySts", push);
        params.put("lookSts", push1);
        params.put("vkey",vkey);
        HttpGetData.post(AddDevice.this,
                //"http://120.79.59.8:8080/SpringIot/hpmt/addHardware.mvc", params,
                MacrosConfig.BaseUrl+"/hpmt/addHardware.mvc", params,
                getResources().getString(R.string.register_wait),
                new HttpGetData.getDataCallBack() {
                    @Override
                    public void succcess(String res) {//这里面可以看到lists的值
                        // TODO Auto-generated method stub
                        JSONObject object = null;
                        try {
                            object = new JSONObject(res);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String code = JsonUtils.getJSONString(object,
                                "status");
                        if(code.equals("1")) {
                            if(buttontype.equals("add"))
                            {
                            Intent AddToEle = new Intent(AddDevice.this, MainActivity.class);
                            AddToEle.putExtra("registerNum", text1.getText().toString());
                            setResult(13,AddToEle);
                            }
                            else if(buttontype.equals("sao"))
                            {
                            Intent AddToMain = new Intent(AddDevice.this, MainActivity.class);
                            AddToMain.putExtra("registerNum", text1.getText().toString());
                            setResult(15,AddToMain);
                            }
                            finish();
                            //startActivity(AddToMain)
                            Toast.makeText(AddDevice.this,R.string.add_success,
                                    Toast.LENGTH_SHORT).show();
                            Log.e("res", res);//打印错误信息
                        }
                        else if(code.equals("2"))
                        {
                            Toast.makeText(AddDevice.this,R.string.vkey_err,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if(code.equals("3"))
                        {
                            Toast.makeText(AddDevice.this,R.string.add_hardware_err,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if(code.equals("4"))
                        {
                            Toast.makeText(AddDevice.this,R.string.para_err,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(AddDevice.this,R.string.add_fail,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void fail(String error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(AddDevice.this, error,
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.yes:
                push=1;
                break;
            case R.id.no:
                push=2;
                break;
            case R.id.yes1:
                push1=1;
                break;
            case R.id.no1:
                push1=2;
                break;
            case R.id.left_imgBtn:
                if(buttontype.equals("sao"))
                {
                    Intent AddToMain = new Intent(AddDevice.this, MainActivity.class);
                    AddToMain.putExtra("registerNum", text1.getText().toString());
                    setResult(15,AddToMain);
                }
                if (SoftKeyboardUtils.isSoftShowing(AddDevice.this)){
                    SoftKeyboardUtils.hideSoftKeyboard(AddDevice.this);
                }else {
                    onBackPressed();
                }
                finish();
                break;
            case R.id.right_imgBtn:
                scanQRCode();
                break;
            case R.id.btn_ok:
                DeviceAdd();
                break;
            case R.id.cic_b:
                Type=1;
                text4.setText("");
                text5.setText("");
               // text1.setFocusable(true);
                JsonUtils.EditTextEnable(true,text1);
                mac.setVisibility(View.GONE);
               // imsi.setVisibility(View.GONE);
                break;
            case R.id.cic_b1:
                Type=2;
                text4.setText("");
                text5.setText("");
                //text1.setFocusable(true);
                JsonUtils.EditTextEnable(true,text1);
                mac.setVisibility(View.GONE);
                //imsi.setVisibility(View.VISIBLE);
                break;
            case R.id.cic_c:

                Type=4;
                text4.setText("");
                text5.setText("");
                //text1.setFocusable(true);
                JsonUtils.EditTextEnable(true,text1);
                mac.setVisibility(View.GONE);
               // imsi.setVisibility(View.GONE);
                break;
            case R.id.cic_e:

                Type=3;
                text4.setText("");
                text5.setText("");
                JsonUtils.EditTextEnable(false,text1);
               // imsi.setVisibility(View.GONE);
                mac.setVisibility(View.VISIBLE);
                break;
            case R.id.cic_g:

                Type=5;
                text4.setText("");
                text5.setText("");
                JsonUtils.EditTextEnable(true,text1);
                mac.setVisibility(View.GONE);
                // imsi.setVisibility(View.GONE);

                break;
            case R.id.cic_e1:

                Type=6;
                text4.setText("");
                text5.setText("");
                //text1.setFocusable(true);
                JsonUtils.EditTextEnable(false,text1);
                mac.setVisibility(View.VISIBLE);
                // imsi.setVisibility(View.GONE);
                break;
            default:
                break;

        }
    }
    //扫一扫
    private void scanQRCode() {
        if (ContextCompat.checkSelfPermission(AddDevice.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            MPermissions.requestPermissions(AddDevice.this, 0, android.Manifest.permission.CAMERA);
        }
        else {
            Intent intent = new Intent(AddDevice.this, CaptureActivity.class);
            startActivityForResult(intent, 3);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    scanQRCode();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == 3 && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Log.e("erweima", String.valueOf(content.length()));
                if(content.length()==16 || content.length()==12||content.length()==18 )
                {
                    if(content.length()==16)
                    {
                        String ma = content.substring(4, 16);
                        Num = MacToImsi(ma);
                       // TypeName = "MT70-CIC-E";
                        text1.setText(Num);
                       // text2.setText(TypeName);
                        Type=3;
                        mac.setVisibility(View.VISIBLE);
                        cic_e.setChecked(true);
                        text4.setText(ma);
                        text5.setText(Num);
                        text1.setFocusable(false);
                    }
                    else if(content.length()==12)
                    {
                        Num = MacToImsi(content);
                       // TypeName = "MT70-CIC-E";
                        text1.setText(Num);
                       // text2.setText(TypeName);
                        Type=3;
                        mac.setVisibility(View.VISIBLE);
                        cic_e.setChecked(true);
                        text4.setText(content);
                        text5.setText(Num);
                        text1.setFocusable(false);
                    }
                    else if(content.length()==18)
                    {
                        String ma = content.substring(6, 18);
                        Num = MacToImsi(ma);
                        // TypeName = "MT70-CIC-E";
                        text1.setText(Num);
                        // text2.setText(TypeName);
                        Type=6;
                        mac.setVisibility(View.VISIBLE);
                        cic_e1.setChecked(true);
                        text4.setText(ma);
                        text5.setText(Num);
                        text1.setFocusable(false);
                    }
                    else
                    {
                        Toast.makeText(this, R.string.barcode_err, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(AddDevice.this, R.string.qr_err, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(MainActivity.this, a, Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            setResult(15, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
