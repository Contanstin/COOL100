package com.hpmt.cool100.Util.http;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ConnectNetwork {
	/**
	 * 
	 * @功能 链接网络
	 * @param context
	 * @param ll_fail
	 * @param ll_nosignal
	 * @author wujh
	 * @dete 2014.08.11
	 */

	public static void ConnectivityManager(Context context,
			LinearLayout ll_fail, LinearLayout ll_nosignal) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取代表联网状态的NetWorkInfo对象
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		// 获取当前的网络连接是否可用
		if (null == networkInfo) {
			Toast.makeText(context, "当前的网络连接不可用", Toast.LENGTH_SHORT).show();
			// 当网络不可用时，跳转到网络设置页面
			Activity activity = (Activity) context;
			activity.startActivityForResult(new Intent(
					android.provider.Settings.ACTION_WIFI_SETTINGS), 1);

		} else {
			boolean available = networkInfo.isAvailable();
			ll_fail.setVisibility(View.VISIBLE);
			ll_nosignal.setVisibility(View.GONE);
			if (available) {
				Log.i("通知", "当前的网络连接可用");
				Toast.makeText(context, "当前的网络连接可用", Toast.LENGTH_SHORT).show();
			} else {
				Log.i("通知", "当前的网络连接不可用");
				Toast.makeText(context, "当前的网络连接不可用", Toast.LENGTH_SHORT)
						.show();
			}
		}

		State state = connManager.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).getState();
		if (State.CONNECTED == state) {
			Log.i("通知", "GPRS网络已连接");
			Toast.makeText(context, "GPRS网络已连接", Toast.LENGTH_SHORT).show();
		}

		state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (State.CONNECTED == state) {
			Log.i("通知", "WIFI网络已连接");
			Toast.makeText(context, "WIFI网络已连接", Toast.LENGTH_SHORT).show();
		}

	}

}
