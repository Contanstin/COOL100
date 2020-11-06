package com.hpmt.cool100.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hpmt.cool100.R;

/**
 * 
 * Create custom Dialog windows for your application Custom dialogs rely on
 * custom layouts wich allow you to create and use your own look & feel.
 * 
 * Under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 * 
 * @author antoine vianey
 *
 */
public class SlowScrollView extends ScrollView {

	public SlowScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SlowScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlowScrollView(Context context) {
		super(context);
	}

	/**
	 * 滑动事件
	 */
	@Override
	public void fling(int velocityY) {
		super.fling(velocityY / 2);
	}
}