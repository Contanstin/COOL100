package com.hpmt.cool100.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hpmt.cool100.R;
import com.hpmt.cool100.Util.http.HttpGetData;
import com.hpmt.cool100.Util.tools.JsonUtils;
import com.hpmt.cool100.Util.tools.MD5Utils;
import com.hpmt.cool100.Util.tools.ShowAlertView;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.config.MacrosConfig;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

public class overlookPsw extends BaseActivity  implements View.OnClickListener {
    private ImageButton imgBtn_left;
    private TextView txt_title;
    private Button btnCommit;
    private Button forget;
    private EditText old_psw;
    private EditText name;
    private EditText new_psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.overlookpsw);
        //初始化
        imgBtn_left = (ImageButton) findViewById(R.id.left_imgBtn);
        imgBtn_left.setVisibility(View.VISIBLE);
        imgBtn_left.setOnClickListener(this);
        txt_title = (TextView) findViewById(R.id.center_txt);
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(getResources().getString(R.string.forget_psw_name));
        old_psw=(EditText) findViewById(R.id.edt_11);
        name=(EditText) findViewById(R.id.edt_12);
        new_psw=(EditText) findViewById(R.id.edt_22);
        forget=(Button) findViewById(R.id.activity_login_forget_password_btn1);
        forget.setOnClickListener(this);
        btnCommit=(Button) findViewById(R.id.btn_commit11);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_imgBtn:
                finish();
                break;
            case R.id.btn_commit11:
                modifiPsw();
                break;
            case R.id.activity_login_forget_password_btn1:
                Intent f = new Intent(overlookPsw.this, ForgotPassdwordAcitity.class);
                startActivity(f);
                break;
            default:
                break;
        }

    }

    private void modifiPsw() {
        if (name.getText().toString().length() == 0) {
            Toast.makeText(overlookPsw.this,
                    R.string.login_name_hint1,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (old_psw.getText().toString().length() == 0) {
            Toast.makeText(overlookPsw.this,
                   R.string.original_password1,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (new_psw.getText().toString().length() == 0) {
            Toast.makeText(overlookPsw.this, R.string.psw_new_name_hint,
                    Toast.LENGTH_SHORT).show();
            return;
        }
            RequestParams params = new RequestParams();
            String oldpassword = null;
            String password = null;
            try {
                oldpassword = MD5Utils.md5Password(old_psw.getText().toString());
                password = MD5Utils.md5Password(new_psw.getText().toString());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            params.put("accountName", name.getText().toString());
            params.put("password",oldpassword);
            params.put("updatePassword", password);
//		params.put("language", share.getString(PlistFileNameModel.Language,
//				""));

            HttpGetData.post(overlookPsw.this,
                    //"http://www.hpmontserver.com/SpringIot/hpmt/changePassword.mvc",
                    MacrosConfig.BaseUrl + "/hpmt/changePassword1.mvc",
                    params, getResources().getString(R.string.modify_password),
                    new HttpGetData.getDataCallBack() {

                        @Override
                        public void succcess(String res) {
                            // TODO Auto-generated method stub
                            JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(res);
                                String code = JsonUtils.getJSONString(jsonObject,
                                        "status");
                                if (code.equals("1")) {
                                    ShowAlertView.showDialog(
                                            overlookPsw.this, getResources().getString(R.string.success),
                                            new ShowAlertView.ClickCallback() {

                                                @Override
                                                public void clickOk() {
                                                    // TODO Auto-generated method
                                                    // stub
                                                    overlookPsw.this
                                                            .finish();

                                                }
                                            });
                                } else if (code.equals("2"))
                                {
                                    Toast.makeText(overlookPsw.this,
                                            R.string.verification_code_err, Toast.LENGTH_SHORT).show();
                                }
                                else if (code.equals("3"))
                                {
                                    Toast.makeText(overlookPsw.this,
                                            R.string.original_password_err, Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(overlookPsw.this,
                                            "", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void fail(String error) {
                            // TODO Auto-generated method stub
                            Toast.makeText(overlookPsw.this, error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }


}
