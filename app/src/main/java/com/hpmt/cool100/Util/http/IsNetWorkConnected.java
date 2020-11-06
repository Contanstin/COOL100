package com.hpmt.cool100.Util.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.hpmt.cool100.config.MacrosConfig;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class IsNetWorkConnected{
	/**
	 * 
	 * @功能:判断网络是否连接
	 * @param context
	 * @return boolean
	 * @author wujh
	 * @date 2014.04.15
	 */
	static public boolean isNetWorkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}

		}
		return false;
	}
	public static boolean isConnByHttp() {

		boolean isConn = false;
		URL url;
		HttpURLConnection conn = null;

		try {
            //url = new URL( "https://www.hpmontserver.com/SpringIot/");
			url = new URL(MacrosConfig.BaseUrl);
			//url = new URL("http://113.87.163.172:3392/SpringIot");
			conn = (HttpURLConnection) url.openConnection();
			// conn.setHeader("Range","bytes="+"");
			conn.setConnectTimeout(1000 * 8);
			if (conn.getResponseCode() == 200) {
				isConn = true;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block

		}
		catch (Exception e) {

           Log.e("daying",e.toString());
			//conn.disconnect();
		}
		finally {
			conn.disconnect();
		}
		return isConn;
	}

}
