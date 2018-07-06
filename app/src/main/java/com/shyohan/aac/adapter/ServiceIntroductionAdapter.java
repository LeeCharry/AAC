package com.shyohan.aac.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import acc.tulip.com.accreputation.R;

/**
 * Created by lcy on 2018/7/1.
 */

public class ServiceIntroductionAdapter extends BaseExpandableListAdapter {

    private List<String> parentData = new ArrayList<>();
    private List<String[]> childData = new ArrayList<>();

    public ServiceIntroductionAdapter(List<String> parentData, List<String[]> childData) {
        this.parentData = parentData;
        this.childData = childData;
    }

    @Override
    public int getGroupCount() {
        return parentData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.size() - 1 >= groupPosition ? childData.get(groupPosition).length : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.size() - 1 >= groupPosition ? childData.get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_list_parent, null);
            holder = new ParentHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ParentHolder) convertView.getTag();
        }

        if (isExpanded) {
            holder.ivIsExpand.setImageResource(R.mipmap.ic_arrow_expand);
        }else{
            holder.ivIsExpand.setImageResource(R.mipmap.ic_select);

        }
        holder.tvParent.setText(parentData.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_list_child, null);
            holder = new ChildHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ChildHolder) convertView.getTag();
        }

        holder.tvChild.setText(childData.get(groupPosition)[childPosition]);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class ParentHolder {
        TextView tvParent;
        ImageView ivIsExpand;
        public ParentHolder(View view) {
            tvParent = view.findViewById(R.id.tv_parent);
            ivIsExpand = view.findViewById(R.id.iv_is_expand);

        }
    }
    class ChildHolder {
        TextView tvChild;


        public ChildHolder(View view) {
            tvChild = view.findViewById(R.id.tv_child);
        }
    }
}
