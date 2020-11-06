package com.hpmt.cool100.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hpmt.cool100.R;
import com.hpmt.cool100.model.LeftFloorModel;

import java.util.ArrayList;

public class HomeLeftGiridAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<LeftFloorModel> data;

	// private String mTag;// 内外召的标识 1:内召 2：外召

	public HomeLeftGiridAdapter(Context context, ArrayList<LeftFloorModel> arr) {
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

		if (data.get(position).getFloorStaus().equals("0")) {
			viewHold.ll_content
					.setBackgroundResource(R.drawable.floor_out_not_one);
		} else if (data.get(position).getFloorStaus().equals("3")) {
			// 下行
			viewHold.ll_content
					.setBackgroundResource(R.drawable.floor_out_only_down);
		} else if (data.get(position).getFloorStaus().equals("2")) {
			// 上行
			viewHold.ll_content
					.setBackgroundResource(R.drawable.floor_out_only_up);

		} else if (data.get(position).getFloorStaus().equals("1")) {

			viewHold.ll_content
					.setBackgroundResource(R.drawable.floor_out_up_and_down);
		}

		return convertView;
	}

	public class ViewHold {
		public TextView txt_FloorNum;
		public LinearLayout ll_content;

	}

}
