package com.hpmt.cool100.Util.tools;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.hpmt.cool100.R;
import com.hpmt.cool100.widget.CustomDialog;

public class ShowAlertView {

	public static void Show(Context context, String str) {

		Builder builder = new Builder(context);
		builder.setTitle(context.getString(R.string.warm_hint));
		builder.setMessage(str);
		builder.setNegativeButton(context.getString(R.string.ok), null);
		Dialog dialog = builder.create();
		// dialog.getWindow()
		// .setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		Activity a = (Activity) context;
		if (!a.isFinishing()) {
			dialog.show();
		}

	}

	public static void showDialog(Context context, String msg,
			final ClickCallback callback) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setTitle(context.getString(R.string.warm_hint));
		builder.setMessage(msg);
		builder.setNegativeButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				callback.clickOk();
			}
		});
		Activity a = (Activity) context;
		if (!a.isFinishing()) {
			builder.show();
		}
	}

	public static void showDialog(Context context, String msg) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setTitle(context.getString(R.string.warm_hint));
		builder.setMessage(msg);
		builder.setNegativeButton(context.getString(R.string.ok), null);
		Activity a = (Activity) context;
		if (!a.isFinishing()) {
			builder.show();
		}
	}

	public static void showOkAndNegative(Context context, String msg,
			final ClickCallback callback) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setTitle(R.string.tip);
		builder.setMessage(msg);
		builder.setNegativeButton(R.string.negative, null);
		builder.setPositiveButton(R.string.positive,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						callback.clickOk();
					}
				});
		// builder.show();
		Activity a = (Activity) context;
		if (!a.isFinishing()) {
			builder.show();
		}
	}

	public static void showOkAndNegative(Context context, String msg,
			String title, final ClickCallback callback) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setNegativeButton(R.string.negative, null);
		builder.setPositiveButton(R.string.positive,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						callback.clickOk();
					}
				});
		Activity a = (Activity) context;
		if (!a.isFinishing()) {
			builder.show();
		}
		// builder.show();
	}

	public static void showOkAndNegative(Context context, String msg,
			String title, String negative, String positive,
			final ClickCallback callback, final ClickCallback callbackNegative) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setNegativeButton(negative,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (callback != null) {
							callback.clickOk();
						}
					}
				});
		builder.setPositiveButton(positive,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						callbackNegative.clickOk();
					}
				});
		Activity a = (Activity) context;
		if (!a.isFinishing()) {
			builder.show();
		}
		// builder.show();
	}

	public interface OvalClickCallback {
		public void ovalClickOk(String edtStr);

	}

	public interface ClickCallback {
		public void clickOk();

	}
}
