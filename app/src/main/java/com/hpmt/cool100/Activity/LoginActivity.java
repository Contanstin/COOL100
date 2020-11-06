package com.hpmt.cool100.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.hpmt.cool100.BuildConfig;
import com.hpmt.cool100.R;
import com.hpmt.cool100.Util.http.HttpGetData;
import com.hpmt.cool100.Util.http.HttpGetData.getDataCallBack;
import com.hpmt.cool100.Util.tools.JsonUtils;
import com.hpmt.cool100.Util.tools.MD5Utils;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.config.MacrosConfig;
import com.hpmt.cool100.model.PlistFileNameModel;
import com.loopj.android.http.RequestParams;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.zhy.m.permission.MPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

public class LoginActivity extends BaseActivity implements OnClickListener {
    private static final int PERMISION_CATMER = 1022;
    public static final int FILE_SELECT_CODE = 220;
    private TextView txt_title; // 导航栏标题
    private Button btn_login;// 登录按钮
    private Button btn_register;// 注册按钮
    private Button btn_forgotPsw;//修改密码
    private Button btn_forgotPsw1;//忘记密码
    private EditText edt_userName;
    private EditText edt_passWord;
    private Button deleteBtn;
    private Button deleteBtn1;
    private Button callButton;
    private Button company;
    private TextView txt_version;
    private TextView txt_save;
    private ImageView img_save;
    private boolean isSavePsw;
    private SharedPreferences share;
    private String oldvision;
    private String vision;
    private LinearLayout center;
    private RelativeLayout user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width2 = outMetrics.widthPixels;
        center=(LinearLayout) findViewById(R.id.center);
        ViewGroup.LayoutParams para;
        para = center.getLayoutParams();
        para.height=width2;
        center.setLayoutParams(para);
//second高度

        double a= width2*0.25;

        int b=(int)a;

        user=(RelativeLayout)findViewById(R.id.second);

        LinearLayout.LayoutParams layoutParam;

        layoutParam = (LinearLayout.LayoutParams) user.getLayoutParams();

        layoutParam.setMargins(0,b, 0, 0);

        if (ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //需要申请权限
            MPermissions.requestPermissions(LoginActivity.this, 0, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {

            oldvision=getAppVersion(this);
            PgyUpdateManager.register(LoginActivity.this, BuildConfig.APPLICATION_ID+".provider",
                    new UpdateManagerListener() {

                        @Override
                        public void onUpdateAvailable(final String result) {
                            //获取当前版本信息
                            try {
                                JSONObject object = new JSONObject(result);
                                //JSONArray Ele = object.getJSONArray(object);
                                JSONObject value =object.getJSONObject("data");
                                vision= value.getString("versionName");
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                            // 将新版本信息封装到AppBean中
                            final AppBean appBean = getAppBeanFromString(result);
                            Log.e("result",result);
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            View customView = getLayoutInflater().inflate(R.layout.updata, null);
                            builder.setView(customView);
                            final AlertDialog mDialog = builder.create();
                            mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            mDialog.setInverseBackgroundForced(false);
                            Button titleTv = (Button) customView.findViewById(R.id.a);
                            Button e= (Button) customView.findViewById(R.id.e);
                            TextView confirmBtn = (TextView)customView.findViewById(R.id.b);
                            TextView cancelBtn =(TextView)customView.findViewById(R.id.c);
                            confirmBtn.setText("当前版本："+oldvision);
                            cancelBtn.setText("最新版本："+vision);
                            titleTv.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                 mDialog.dismiss();
                                }
                            });
                            e.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startDownloadTask(
                                            LoginActivity.this,
                                            appBean.getDownloadURL());
                                }
                            });
                            mDialog.show();

                             WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
                             params.width = 600;
                             mDialog.getWindow().setAttributes(params);
                        }

                        @Override
                        public void onNoUpdateAvailable() {
                        }
                    });

        }


        btn_forgotPsw = (Button) findViewById(R.id.activity_login_forget_password_btn);
        btn_forgotPsw1 = (Button) findViewById(R.id.forget);
        btn_login = (Button) findViewById(R.id.activity_login_login_bt);//将xml中的登录按钮赋值给java
        btn_register = (Button) findViewById(R.id.activity_login_activate_btn);
        txt_title = (TextView) findViewById(R.id.center_txt);
        edt_userName = (EditText) findViewById(R.id.etd_login_usename_et);
        edt_passWord = (EditText) findViewById(R.id.edt_login_pas_et);
        edt_userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    deleteBtn.setVisibility(View.VISIBLE);
                }
                else
                {
                    deleteBtn.setVisibility(View.GONE);
                }
            }
        });
        edt_passWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    deleteBtn1.setVisibility(View.VISIBLE);
                }
                else
                {
                    deleteBtn1.setVisibility(View.GONE);
                }
            }
        });
        deleteBtn = (Button) findViewById(R.id.login_deleteBtn);
        deleteBtn1 = (Button) findViewById(R.id.login_deleteBtn1);
        deleteBtn.setOnClickListener(this);//设置点击事件
        deleteBtn1.setOnClickListener(this);
        txt_save = (TextView) findViewById(R.id.save_psw);
        img_save = (ImageView) findViewById(R.id.login_save);
        txt_save.setOnClickListener(this);
        img_save.setOnClickListener(this);

        txt_version = (TextView) findViewById(R.id.version);

        txt_version.setText("v " + getAppVersion(this));

        callButton = (Button) findViewById(R.id.call_num);
        callButton.setOnClickListener(this);
        company = (Button) findViewById(R.id.company);
        company.setOnClickListener(this);

        btn_forgotPsw.setOnClickListener(this);
        btn_forgotPsw1.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);//文本赋值按钮设置点击事件

        txt_title.setText(getResources().getString(R.string.login_click_name));
        txt_title.setTextColor(Color.parseColor("white"));
        txt_title.setVisibility(View.VISIBLE);//可见view.setVisibility(View.INVISIBLE);不可见view.setVisibility(View.GONE);隐藏

        share = getSharedPreferences(PlistFileNameModel.PLIS_NAME,
                Context.MODE_PRIVATE);

        String psw = share.getString(PlistFileNameModel.PASSWORD, "");
        String username = share.getString(PlistFileNameModel.USER_NAME, "");
        edt_userName.setText(username);
        isSavePsw = share.getBoolean(PlistFileNameModel.IS_SAVE_PSW, false);
        if (isSavePsw) {
            edt_passWord.setText(psw);
            img_save.setImageResource(R.drawable.save_psw);
        } else {
            edt_passWord.setText("");
            img_save.setImageResource(R.drawable.no_save_psw);
        }

        edt_userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    deleteBtn.setVisibility(View.VISIBLE);
                }
                else {
                    deleteBtn.setVisibility(View.GONE);
                }
            }
        });
        edt_passWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    deleteBtn1.setVisibility(View.VISIBLE);
                }
                else {
                    deleteBtn1.setVisibility(View.GONE);
                }
            }
        });

        // checkBluetoothPermission();

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.activity_login_forget_password_btn:
                // 忘记密码
                Intent forgot = new Intent(LoginActivity.this,
                        overlookPsw.class);
                startActivity(forgot);

                break;
            case R.id.activity_login_login_bt:

                LoginEnvent();//另一个事件

                break;
            case R.id.activity_login_activate_btn:
                // 注册
                Intent intent2 = new Intent(LoginActivity.this,
                        RegisterActivtiy.class);
                startActivity(intent2);
                break;
            case R.id.login_deleteBtn:
                edt_userName.setText("");
                break;
            case R.id.login_deleteBtn1:
                edt_passWord.setText("");
                break;
            case R.id.save_psw:
            case R.id.login_save:
                if (isSavePsw) {
                    isSavePsw = false;
                    img_save.setImageResource(R.drawable.no_save_psw);
                } else {
                    isSavePsw = true;
                    img_save.setImageResource(R.drawable.save_psw);
                }

                break;
            case R.id.forget:
                // 忘记密码
                Intent forgot1 = new Intent(LoginActivity.this,
                        ForgotPassdwordAcitity.class);
                startActivity(forgot1);

                break;
            case R.id.call_num:
                String[] str = {};
                str = (callButton.getText().toString()).split(":");
                final String numbers = str[1].replaceAll("-", "");

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                mBuilder.setTitle(getResources().getString(R.string.company_name));
                mBuilder.setMessage(getResources()
                        .getString(R.string.service_phone) + numbers);
                mBuilder.setPositiveButton(getResources()
                        .getString(R.string.cancel), null);
                mBuilder.setNegativeButton(getResources().getString(R.string.dial),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    //需要申请权限
                                    MPermissions.requestPermissions(LoginActivity.this, 0, android.Manifest.permission.CALL_PHONE);
                                }
                                else
                                {
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numbers));
                                    startActivity(intent);
                                }
                            }
                        });
                mBuilder.create();
                mBuilder.show();
                break;
            case R.id.company:
                Uri uri = Uri.parse("http://www.hpmont.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    // 获取软件的版本
    private String getAppVersion(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            versionName = packageInfo.versionName;

            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }


    private void LoginEnvent()  {
        // TODO Auto-generated method stub

        if (edt_userName.getText().toString().length() == 0) {
            //消息模式没有输入的时候显示内容
            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.login_name_hint1),
                    Toast.LENGTH_SHORT).show();
            return;

        }
        if (edt_passWord.getText().toString().length() == 0) {
            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.psw_hint),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        //创建请求对象
        RequestParams params = new RequestParams();
        String password= null;
        try {
            password = MD5Utils.md5Password(edt_passWord.getText().toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        params.put("accountName", edt_userName.getText().toString());
        params.put("password",password);
        //输出错误信息
        Log.e("data", "userName:" + edt_userName.getText().toString()
                + "passWord:" +password);
        //获取后台验证
        HttpGetData.post(LoginActivity.this,
                 //"http://120.79.59.8:8080/SpringIot/hpmt/login.mvc", params,
                MacrosConfig.BaseUrl +"/hpmt/login.mvc", params,
                getResources().getString(R.string.logining),
                new getDataCallBack() {
                    @Override
                    public void succcess(String res) {
                        // TODO Auto-generated method stub
                        Log.e("res", res);//打印错误信息
                        try {
                            JSONObject object = new JSONObject(res);
                            String code = JsonUtils.getJSONString(object,
                                    "status");

                            String vkey = JsonUtils.getJSONString(object, "vkey");
                            String id = JsonUtils.getJSONString(object, "id");
                            //&& vkey.length()>0
                            if (code.equals("1")&& vkey.length()>0 ) {
                                // 保存用户名
                                share.edit()
                                        .putString(
                                                PlistFileNameModel.USER_NAME,
                                                edt_userName.getText()
                                                        .toString()).commit();
                                share.edit()
                                        .putString(
                                                PlistFileNameModel.PASSWORD,
                                                edt_passWord.getText()
                                                        .toString()).commit();
                                share.edit()
                                        .putBoolean(
                                                PlistFileNameModel.IS_SAVE_PSW,
                                                isSavePsw).commit();
                                Intent mainAct = new Intent(LoginActivity.this, MainActivity.class);
                                //Intent mainAct = new Intent(LoginActivity.this, test.class);
                                mainAct.putExtra("vkey", vkey);// 1表示在登录进去的
                                mainAct.putExtra("id", id);
                                mainAct.putExtra("Tel",  edt_userName.getText().toString());//用户账号
                                startActivity(mainAct);
                                LoginActivity.this.finish();

                            }
                            else if(code.equals("2"))
                            {
                                Toast.makeText(LoginActivity.this,R.string.name_password_err,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if(code.equals("3"))
                            {
                                Toast.makeText(LoginActivity.this,R.string.interface_err,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if(code.equals("4"))
                            {
                                Toast.makeText(LoginActivity.this,R.string.para_none,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if( vkey.length()<=0)
                            {
                                Toast.makeText(LoginActivity.this,R.string.vkey_none,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this,R.string.error,
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
                        Toast.makeText(LoginActivity.this, error,
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }
    /**
     * 摁两次返回键强制退出程序*/

    private long extiTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - extiTime) > 2000) {
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.exit_toadt),
                        Toast.LENGTH_SHORT).show();
                extiTime = System.currentTimeMillis();

            } else {

                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                application.exit();


            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

}
