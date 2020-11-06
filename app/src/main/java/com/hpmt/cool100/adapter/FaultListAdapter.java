package com.hpmt.cool100.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hpmt.cool100.R;
import com.hpmt.cool100.model.FaultList;

import java.util.List;

public class FaultListAdapter extends ArrayAdapter<FaultList> implements Filterable {
    private int resourceId;
    private List<FaultList> list = null;
    private Context mContext;

    public FaultListAdapter(Context context, int textViewResourceId,
                               List<FaultList> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.mContext = context;
        this.list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FaultList faultList = getItem(position); // 获取当前项的Fruit实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            //写模型数据
            viewHolder.faultcode = (TextView) view.findViewById (R.id.faultcode);
            viewHolder.faultfloor = (TextView) view.findViewById (R.id.faultfloor);
            viewHolder.faultname = (TextView) view.findViewById (R.id.faultname);
            viewHolder.faulttime = (TextView) view.findViewById (R.id.faulttime);
            viewHolder.sub_code = (TextView) view.findViewById (R.id.sub_code);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.faultcode.setText(faultList.getfaultcode());
        viewHolder.faultfloor.setText(faultList.getfaultfloor());
        viewHolder.faultname.setText(faultList.getfaultname());
        viewHolder.faulttime.setText(faultList.getfaulttime());
        viewHolder.sub_code.setText(faultList.getsub_code());
        return view;
    }

    class ViewHolder {
        TextView faultcode;
        TextView sub_code;
        TextView faultfloor;
        TextView faulttime;
        TextView faultname;
    }
    public void updateListView(List<FaultList> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
