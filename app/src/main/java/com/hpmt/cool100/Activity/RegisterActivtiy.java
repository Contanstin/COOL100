package com.hpmt.cool100.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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

public class RegisterActivtiy extends BaseActivity implements OnClickListener {
	private TextView txt_title;
	private ImageButton imgBtn_back;// 返回按钮
	private Button btn_commit;// 提交
	private Button btn_yanzh;
	private EditText edt2;// 姓名
	private EditText edt3;// 注册码
	private EditText edt4;// 邮箱
	private EditText edt5;// 手机
	private EditText edt6;// 地址
	private EditText edt7;// 密码
	private String NewvalifyCode="";
	private SharedPreferences share;
	private TimeCount tmCt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		txt_title = (TextView) findViewById(R.id.center_txt);
		txt_title.setVisibility(View.VISIBLE);
		txt_title.setText(getResources().getString(R.string.register_click_name));

		imgBtn_back = (ImageButton) findViewById(R.id.left_imgBtn);
		imgBtn_back.setVisibility(View.VISIBLE);
		imgBtn_back.setOnClickListener(this);
		btn_yanzh=(Button) findViewById(R.id.btn_getRNum);
		btn_commit = (Button) findViewById(R.id.btn_commit);
		btn_commit.setOnClickListener(this);
		btn_yanzh.setOnClickListener(this);

		edt2 = (EditText) findViewById(R.id.edt_2);
		edt3 = (EditText) findViewById(R.id.edt_R_3);
		edt4 = (EditText) findViewById(R.id.edt_4);
		edt5 = (EditText) findViewById(R.id.edt_5);
		edt6 = (EditText) findViewById(R.id.edt_6);
		edt7 = (EditText) findViewById(R.id.edt_7);
		share = getSharedPreferences(PlistFileNameModel.PLIS_NAME,
				Context.MODE_PRIVATE);


	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_commit:
			// 提交注册
			if (edt2.getText().toString().length() == 0) {
				Toast.makeText(RegisterActivtiy.this,
						getResources().getString(R.string.login_name_hint1),
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (edt5.getText().toString().length() == 0) {
				Toast.makeText(this,
						getResources().getString(R.string.iphone_t_hint),
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (edt7.getText().toString().length() == 0) {
				Toast.makeText(this,
						getResources().getString(R.string.input_password_hint),
						Toast.LENGTH_SHORT).show();
				return;
			}

			if (edt3.getText().toString().length() == 0) {
				Toast.makeText(RegisterActivtiy.this,
						getResources().getString(R.string.verificty_name_hint),
						Toast.LENGTH_SHORT).show();
				return;
			}


			RegisterEnvent();

			break;
		case R.id.left_imgBtn:
			finish();
			break;
		case R.id.btn_getRNum:
			if(edt5.getText().toString().length() > 0) {
				edt5.setEnabled(false);
				sendVerificationCode(edt5.getText().toString());
			}
			else
			{
				Toast.makeText(RegisterActivtiy.this,R.string.fill_tel,
						Toast.LENGTH_SHORT).show();
				return;
			}
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
	/**
	 * 发送验证码
	 *
	 * @param phone
	 */
	private void sendVerificationCode(String phone) {
		btn_yanzh.setBackgroundResource(R.drawable.getok);
		if (edt5.getText().toString().length() == 0) {
			//消息模式没有输入的时候显示内容
			Toast.makeText(RegisterActivtiy.this,R.string.fill_tel,
					Toast.LENGTH_SHORT).show();
			return;

		}

		tmCt = new TimeCount(120000, 1000);
		tmCt.start();
		RequestParams params = new RequestParams();
		params.put("telNo", phone);
		Log.e("phone", "phone:" +phone);
//		params.put("language", share.getString(PlistFileNameModel.Language,
//				""));

		HttpGetData.post(RegisterActivtiy.this,
				MacrosConfig.BaseUrl +"/hpmt/getValifyCode.mvc",
				params, getResources().getString(R.string.send_vertifi_code),
				new getDataCallBack() {

					@Override
					public void succcess(String res) {
						// TODO Auto-generated method stub
						Log.e("res", res);
						try {
							JSONObject jsonObject = new JSONObject(res);

							String code = JsonUtils.getJSONString(jsonObject,
									"status");
							 NewvalifyCode = JsonUtils.getJSONString(jsonObject,
									"valifyCode");

							if (code.equals("1")) {
								Toast.makeText(RegisterActivtiy.this,
										R.string.sent_success, Toast.LENGTH_SHORT).show();
							}
							else {
								Toast.makeText(RegisterActivtiy.this,
										R.string.get_failed, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(RegisterActivtiy.this, error,
								Toast.LENGTH_SHORT).show();
						tmCt.onFinish();
					}
				});

	}
	// 时间倒时器
	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInFuture) {
			super(millisInFuture, countDownInFuture);

		}

		@Override
		public void onFinish() {
			btn_yanzh.setText(getResources().getString(
					R.string.re_send_vertifi_code));
			btn_yanzh.setClickable(true);
			edt5.setEnabled(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			btn_yanzh.setClickable(false);
			btn_yanzh.setText(millisUntilFinished / 1000
					+ getResources().getString(R.string.second));

		}
	}
	private void RegisterEnvent() {
		// TODO Auto-generated method stub
		if (!NewvalifyCode.equals("") || NewvalifyCode.equals("null"))
		{
			RequestParams params = new RequestParams();
			String password = null;
			try {
				password = MD5Utils.md5Password(edt7.getText().toString());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			params.put("userName", edt7.getText().toString());
			params.put("password", password);
			params.put("valifyCode", edt3.getText().toString());
			params.put("email", edt4.getText().toString());
			params.put("accountName", edt2.getText().toString());
			params.put("phoneNum",edt5.getText().toString());
			params.put("addr", edt6.getText().toString());
			//原验证码
			HttpGetData.post(RegisterActivtiy.this,
					//MacrosConfig.BaseUrl +"/MontServer/RegisterServlet",
					MacrosConfig.BaseUrl + "/hpmt/registered.mvc",
					params, getResources().getString(R.string.connect_register),
					new getDataCallBack() {
						@Override
						public void succcess(String res) {
							// TODO Auto-generated method stub
							Log.e("res", res);
							try {
								JSONObject jsonObject = new JSONObject(res);
								// JSONObject dataObject = JsonUtils.getJSONObject(
								// jsonObject, "data");
								String code = JsonUtils.getJSONString(jsonObject,
										"status");
								if (code.equals("1")) {
									ShowAlertView.showDialog(RegisterActivtiy.this,
											getResources().getString(R.string.success), new ClickCallback() {

												@Override
												public void clickOk() {
													finish();
												}
											});

								} else if (code.equals("3")) {
									Toast.makeText(RegisterActivtiy.this, R.string.reg_duplicate,
											Toast.LENGTH_LONG).show();
								} else if (code.equals("2")) {
									Toast.makeText(RegisterActivtiy.this,R.string.verification_code_err ,
											Toast.LENGTH_LONG).show();
								} else {
									Toast.makeText(RegisterActivtiy.this, R.string.reg_fail ,
											Toast.LENGTH_LONG).show();
								}


							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

						@Override
						public void fail(String error) {
							// TODO Auto-generated method stub
							Toast.makeText(RegisterActivtiy.this, error,
									Toast.LENGTH_LONG).show();
						}
					});
		}

	}


}


