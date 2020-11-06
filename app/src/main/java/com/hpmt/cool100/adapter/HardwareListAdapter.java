package com.hpmt.cool100.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hpmt.cool100.R;
import com.hpmt.cool100.model.ElevatorList;
import com.hpmt.cool100.model.HardwareList;

import java.util.List;


public class HardwareListAdapter extends ArrayAdapter<HardwareList> implements Filterable {

    private int resourceId;
    private List<HardwareList> list = null;
    private Context mContext;

    public HardwareListAdapter(Context context, int textViewResourceId,
                               List<HardwareList> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.mContext = context;
        this.list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HardwareList hardwareList = getItem(position); // 获取当前项的Fruit实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.listname = (TextView) view.findViewById (R.id.list_name);
            viewHolder.listnum = (TextView) view.findViewById (R.id.list_num);
            viewHolder.listeleid = (TextView) view.findViewById (R.id.list_eleid);
            viewHolder.listelehard = (TextView) view.findViewById (R.id.list_hardid);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.listnum.setText(hardwareList.getregisterNum());
        viewHolder.listname.setText(hardwareList.getelevatorNum());
        viewHolder.listeleid.setText(hardwareList.getelevatorId());
        viewHolder.listelehard.setText(hardwareList.getHardwareId());
        return view;
    }

    class ViewHolder {
        TextView listname;
        TextView listnum;
        TextView listeleid;
        TextView listelehard;
    }
    public void updateListView(List<HardwareList> list){
        this.list = list;
        notifyDataSetChanged();
    }
}

