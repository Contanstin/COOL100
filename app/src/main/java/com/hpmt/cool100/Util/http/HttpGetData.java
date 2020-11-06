package com.hpmt.cool100.Util.http;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hpmt.cool100.Activity.RegisterActivtiy;
import com.hpmt.cool100.R;
import com.hpmt.cool100.Util.tools.JsonUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpGetData {

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(final Context mContext, String url,
                           RequestParams params, String msg, final getDataCallBack callBack) {

		if (!IsNetWorkConnected.isNetWorkConnected(mContext)) {
			// showToast(mContext, mContext.getString(R.string.networkalter));
			return;
		}

		final ProgressDialog dialog = new ProgressDialog(mContext);// ProgressDialog.show(mContext,
																	// "", msg);
		dialog.setCancelable(true);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
		dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
		dialog.setMessage(msg);

		if (!msg.equals("")) {
			dialog.show();
		}

		client.setTimeout(20000);
		client.get(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {

				callBack.succcess(new String(arg2));

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {

				callBack.fail(ConnetFailturMessage.connetFailturMessage(arg3
						.toString()));

			}

			@Override
			public void onFinish() {
				super.onFinish();
				dialog.dismiss();
			}
		});

	}

	/**
	 * 网络请求封装方法
	 * 
	 * @param mContext
	 * @param url
	 *            接口地址
	 * @param params
	 *            参数
	 * @param callBack
	 *            回调
	 */
	// post方法进行网络请求
	public static void post(final Context mContext, String url,
                            RequestParams params, String msg, final getDataCallBack callBack) {

		if (!IsNetWorkConnected.isNetWorkConnected(mContext)) {
			showToast(mContext, mContext.getString(R.string.networkalter));
			return;
		}

		final AlertDialog builder = new Builder(mContext).create();
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_progress_wheel, null);
		TextView text_msg = (TextView) view.findViewById(R.id.text_msg);
		builder.setCancelable(true);// 返回键是否取消
		builder.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
		text_msg.setText(msg);

		if (!msg.equals("")) {
			Activity a = (Activity) mContext;
			if (!a.isFinishing()) {
				builder.show();
				builder.getWindow().setContentView(view);
			}
		}

		client.setTimeout(20000);
		params.setUseJsonStreamer(true);
//        RequestParams params1 = new RequestParams();
//        params1.put("vkey","ed04d1");
//        params1.put("pageNumber",1);
//        params1.put("pageSize",10);
        /* client.setRequestProperty("Content-Type", "application/json;charset=UTF-8"); */
         client.post(mContext,getAbsoluteUrl(url),null,params,RequestParams.APPLICATION_JSON,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {

                        JSONObject jObject;
                        try {
                            jObject = new JSONObject(new String(arg2));
                            int code = JsonUtils.getJSONInt(jObject, "code");

                            if (code != 3) {
                                callBack.succcess(new String(arg2));
                            } else {
                                Activity activity = (Activity) mContext;
                                if (!activity.isFinishing()) {
                                    showToast(mContext, "登录超时");
                                    Intent login = new Intent(mContext,
                                            RegisterActivtiy.class);
                                    login.putExtra("loginType", true);
                                    mContext.startActivity(login);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        Log.e("error4", String.valueOf(arg0));
                        // //请求错误弹出对话框提示
                        callBack.fail(ConnetFailturMessage
                                .connetFailturMessage(arg3.toString()));

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        builder.dismiss();
                    }
                });
    }

	public static String getAbsoluteUrl(String relativeUrl) {

		return relativeUrl;

	}

	public interface getDataCallBack {
		public void succcess(String res);

		public void fail(String error);
	}

	public interface upLoadfileCallBack {
		public void success(String res);

		public void fail(String error);

	}

	/**
	 * Toast
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

}
