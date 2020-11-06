package com.hpmt.cool100.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hpmt.cool100.R;
import com.hpmt.cool100.Util.http.HttpGetData;
import com.hpmt.cool100.Util.http.HttpGetData.getDataCallBack;
import com.hpmt.cool100.Util.tools.JsonUtils;
import com.hpmt.cool100.Util.tools.MD5Utils;
import com.hpmt.cool100.Util.tools.ShowAlertView;
import com.hpmt.cool100.Util.tools.ShowAlertView.ClickCallback;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.config.MacrosConfig;
import com.hpmt.cool100.model.PlistFileNameModel;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

public class ForgotPassdwordAcitity extends BaseActivity implements
        OnClickListener {

	private ImageButton imgBtn_left;
	private TextView txt_title;
	private Button btnCommit;
	private Button btnGetNum;
	private EditText edt_phone;
	private EditText edt_psw;
	private EditText edt_name;
	private EditText edt_num;
	private TimeCount tmCt;
	private String FvalifyCode="";
	
	private SharedPreferences share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgotpassword);
		imgBtn_left = (ImageButton) findViewById(R.id.left_imgBtn);
		txt_title = (TextView) findViewById(R.id.center_txt);
		//imgBtn_left.setImageResource(R.drawable.fanhui);
		imgBtn_left.setVisibility(View.VISIBLE);
		txt_title.setVisibility(View.VISIBLE);

		edt_phone = (EditText) findViewById(R.id.edt_1);
		edt_psw = (EditText) findViewById(R.id.edt_2);
		edt_num = (EditText) findViewById(R.id.edt_f_3);
		edt_name = (EditText) findViewById(R.id.edt_name);

		btnCommit = (Button) findViewById(R.id.btn_commit);
		btnGetNum = (Button) findViewById(R.id.btn_getNum);
		btnGetNum.setOnClickListener(this);

		txt_title.setText(R.string.forget_password);
		imgBtn_left.setOnClickListener(this);

		btnCommit.setOnClickListener(this);
		
		share = getSharedPreferences(PlistFileNameModel.PLIS_NAME,
				Context.MODE_PRIVATE);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.left_imgBtn:
			finish();
			break;
		case R.id.btn_commit:
			modifiPsw();
			break;
		case R.id.btn_getNum:
			if(edt_phone.getText().toString().length() > 0 && edt_name.getText().toString().length() > 0)
			{
				edt_phone.setEnabled(false);
				edt_name.setEnabled(false);
				sendVerificationCode(edt_phone.getText().toString());
			}
			else
			{  if(edt_name.getText().toString().length() == 0) {
				Toast.makeText(ForgotPassdwordAcitity.this,
						getResources().getString(R.string.login_name_hint1),
						Toast.LENGTH_SHORT).show();
				return;
			}
				if(edt_phone.getText().toString().length() == 0) {
				Toast.makeText(ForgotPassdwordAcitity.this, R.string.fill_tel,
						Toast.LENGTH_SHORT).show();
				return;
			}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 发送验证码
	 * 
	 * @param phone
	 */
	private void sendVerificationCode(String phone) {
		btnGetNum.setBackgroundResource(R.drawable.getok);
		if (edt_phone.getText().toString().length() == 0) {
			//消息模式没有输入的时候显示内容
			Toast.makeText(ForgotPassdwordAcitity.this,
					R.string.fill_tel,
					Toast.LENGTH_SHORT).show();
			return;

		}
		tmCt = new TimeCount(120000, 1000);
		tmCt.start();
		RequestParams params = new RequestParams();
		params.put("telNo", phone);
//		params.put("language", share.getString(PlistFileNameModel.Language,
//				""));

		HttpGetData.post(ForgotPassdwordAcitity.this,
				//"https://www.hpmontserver.com/SpringIot/hpmt/getValifyCode.mvc",
				MacrosConfig.BaseUrl +"/hpmt/getValifyCode.mvc",
				params, getResources().getString(R.string.send_vertifi_code),
				new getDataCallBack() {

					@Override
					public void succcess(String res) {
						// TODO Auto-generated method stub
						try {
							JSONObject jsonObject = new JSONObject(res);
							String code = JsonUtils.getJSONString(jsonObject,
									"status");
							FvalifyCode = JsonUtils.getJSONString(jsonObject,
									"valifyCode");
							if (code.equals("1")) {
								Toast.makeText(ForgotPassdwordAcitity.this,
										R.string.getnum_click_name, Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(ForgotPassdwordAcitity.this,
										R.string.fail, Toast.LENGTH_SHORT).show();
								tmCt.onFinish();
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void fail(String error) {
						// TODO Auto-generated method stub
						Toast.makeText(ForgotPassdwordAcitity.this, error,
								Toast.LENGTH_SHORT).show();
						tmCt.onFinish();
					}
				});

	}

	/**
	 * 忘记密码
	 */
	private void modifiPsw() {

		if (edt_name.getText().toString().length() == 0) {
			Toast.makeText(ForgotPassdwordAcitity.this,
					getResources().getString(R.string.login_name_hint1),
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (edt_phone.getText().toString().length() == 0) {
			Toast.makeText(ForgotPassdwordAcitity.this,
					getResources().getString(R.string.iphone_t_hint),
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (edt_psw.getText().toString().length() == 0) {
			Toast.makeText(ForgotPassdwordAcitity.this,
					getResources().getString(R.string.psw_new_name_hint),
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (edt_num.getText().toString().length() == 0) {
			Toast.makeText(ForgotPassdwordAcitity.this,
					getResources().getString(R.string.verificty_name_hint),
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (!FvalifyCode.equals("") ) {
			RequestParams params = new RequestParams();
			String password = null;
			try {
				password = MD5Utils.md5Password(edt_psw.getText().toString());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			params.put("accountName", edt_name.getText().toString());
			params.put("phoneNum", edt_phone.getText().toString());
			params.put("valifyCode", edt_num.getText().toString());
			params.put("updatePassword", password);
//		params.put("language", share.getString(PlistFileNameModel.Language,
//				""));

			HttpGetData.post(ForgotPassdwordAcitity.this,
					//"http://www.hpmontserver.com/SpringIot/hpmt/changePassword.mvc",
					MacrosConfig.BaseUrl + "/hpmt/changePassword2.mvc",
					params, getResources().getString(R.string.modify_password),
					new getDataCallBack() {

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
											ForgotPassdwordAcitity.this, getResources().getString(R.string.success),
											new ClickCallback() {

												@Override
												public void clickOk() {
													// TODO Auto-generated method
													// stub
													ForgotPassdwordAcitity.this
															.finish();

												}
											});
								} else if (code.equals("2"))
								{
									Toast.makeText(ForgotPassdwordAcitity.this,
											R.string.verification_code_err, Toast.LENGTH_SHORT).show();
								}
								else if (code.equals("3"))
								{
									Toast.makeText(ForgotPassdwordAcitity.this,
											R.string.tijiao_code_err, Toast.LENGTH_SHORT).show();
								}
								else {
									Toast.makeText(ForgotPassdwordAcitity.this,
											R.string.fail, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

						@Override
						public void fail(String error) {
							// TODO Auto-generated method stub
							Toast.makeText(ForgotPassdwordAcitity.this, error,
									Toast.LENGTH_SHORT).show();
						}
					});
		}
		else
		{
			Toast.makeText(ForgotPassdwordAcitity.this, R.string.code_err,
					Toast.LENGTH_LONG).show();
		}
	}

	// 时间倒时器
	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInFuture) {
			super(millisInFuture, countDownInFuture);

		}

		@Override
		public void onFinish() {
			btnGetNum.setText(getResources().getString(
					R.string.re_send_vertifi_code));
			btnGetNum.setClickable(true);
			edt_phone.setEnabled(true);
			edt_name.setEnabled(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			btnGetNum.setClickable(false);
			btnGetNum.setText(millisUntilFinished / 1000
					+ getResources().getString(R.string.second));

		}

	}

}
