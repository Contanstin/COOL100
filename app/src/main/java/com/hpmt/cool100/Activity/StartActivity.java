package com.hpmt.cool100.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.hpmt.cool100.R;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.dbhelp.DBHelper;
import com.hpmt.cool100.model.PlistFileNameModel;

import java.util.Locale;

public class StartActivity extends BaseActivity {
	private ImageView homeImage;
	private SharedPreferences mSharedPreferences;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// 切换到登陆页面
				Intent intent = new Intent(StartActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start);
		initView();

		mSharedPreferences = getSharedPreferences(PlistFileNameModel.PLIS_NAME,
				Context.MODE_PRIVATE);
		String lg = mSharedPreferences.getString(PlistFileNameModel.Language,
				"");
		Locale locale = getResources().getConfiguration().locale;
		String language = locale.getLanguage(); // 获得语言码

		int versionCode = mSharedPreferences.getInt(
				PlistFileNameModel.VersionCode, 0);
		
		//清除跳转记录，让呼梯或者监控页面遇到跳转是会执行跳转
		mSharedPreferences.edit()
		.putInt(PlistFileNameModel.SWITCH_FLAUT,
				0).commit();
		
		// 如果是版本更新则清除数据
		if (getAppVersionCode(this) > versionCode) {
			DBHelper.getInstance(this).clearsuperParams();
		}

		if (lg.equals("")) {
			DBHelper.getInstance(this).clearsuperParams();
		} else {
			if (!lg.equals(language)) {
				DBHelper.getInstance(this).clearsuperParams();
			}

		}
		//保存语言
		mSharedPreferences.edit()
				.putString(PlistFileNameModel.Language, language).commit();
		
		//保存最新版本号
		mSharedPreferences
				.edit()
				.putInt(PlistFileNameModel.VersionCode, getAppVersionCode(this))
				.commit();
		alphaAnimation(2000);

	}

	private void initView() {
		homeImage = (ImageView) this.findViewById(R.id.homeimg);
	}

	// 获取软件的版本
	private int getAppVersionCode(Context context) {
		int versionName = 0;
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			versionName = packageInfo.versionCode;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 启动页面
	 */
	private void alphaAnimation(long delayMillis) {
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
		alphaAnimation.setDuration(2000);// 设定动画时间
		alphaAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
			}
		});
		homeImage.setAnimation(alphaAnimation);
		alphaAnimation.start();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				handler.sendEmptyMessage(1);
				// 给UI主线程发送消息
			}
		}, 2000); // 启动等待2秒钟
	}

}
