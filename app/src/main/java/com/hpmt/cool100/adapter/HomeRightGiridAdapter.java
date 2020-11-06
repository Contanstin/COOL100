package com.hpmt.cool100.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hpmt.cool100.R;
import com.hpmt.cool100.model.FloorModel;

import java.util.ArrayList;

public class HomeRightGiridAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<FloorModel> data;

	// private String mTag;// 内外召的标识 1:内召 2：外召

	public HomeRightGiridAdapter(Context context, ArrayList<FloorModel> arr) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.data = arr;
		// this.mTag = tag;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHold viewHold = null;
		if (convertView == null) {
			viewHold = new ViewHold();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_gridview_home, null);
			viewHold.txt_FloorNum = (TextView) convertView
					.findViewById(R.id.item_gridview_home_floornum);
			viewHold.ll_content = (LinearLayout) convertView
					.findViewById(R.id.item_floor_content);
			convertView.setTag(viewHold);

		} else {
			viewHold = (ViewHold) convertView.getTag();

		}
		viewHold.txt_FloorNum.setText(data.get(position).getFloorNum());
		viewHold.ll_content.setBackgroundResource(R.drawable.floor_in_defu1);
		// if (mTag.equals("1")) {
		if (data.get(position).getFloorStaus().equals("0")) {
			//内容为黑
			viewHold.txt_FloorNum.setTextColor(Color.parseColor("#ff333333"));
		} else {
			//内容为绿
			viewHold.txt_FloorNum.setTextColor(Color.parseColor("#3cb371"));
		}

		return convertView;
	}

	public class ViewHold {
		public TextView txt_FloorNum;
		public LinearLayout ll_content;

	}

}
