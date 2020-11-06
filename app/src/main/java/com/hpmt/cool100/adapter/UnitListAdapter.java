package com.hpmt.cool100.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hpmt.cool100.R;
import com.hpmt.cool100.model.UnitList;

import java.util.List;


public class UnitListAdapter extends ArrayAdapter<UnitList> implements Filterable {

    private int resourceId;
    private List<UnitList> list = null;
    private Context mContext;

    public UnitListAdapter(Context context, int textViewResourceId,
                           List<UnitList> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.mContext = context;
        this.list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UnitList UnitList = getItem(position); // 获取当前项的Fruit实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.listname = (TextView) view.findViewById (R.id.list_name);
            viewHolder.listnum = (TextView) view.findViewById (R.id.list_num);
            viewHolder.listeleid = (TextView) view.findViewById (R.id.list_eleid);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.listnum.setText(UnitList.getUnitacountName());
        viewHolder.listname.setText(UnitList.getUnitcompanyName());
        viewHolder.listeleid.setText(UnitList.getUnitId());

        return view;
    }

    class ViewHolder {
        TextView listname;
        TextView listnum;
        TextView listeleid;
    }
    public void updateListView(List<UnitList> list){
        this.list = list;
        notifyDataSetChanged();
    }
}

