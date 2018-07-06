package com.shyohan.aac.activity;
import android.widget.ExpandableListView;

import com.shyohan.aac.adapter.ServiceIntroductionAdapter;
import com.shyohan.aac.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import acc.tulip.com.accreputation.R;

/**
 * 服务介绍
 */
public class ServiceIntroductionActivity extends BaseActivity {
    private ExpandableListView listview;
    private ServiceIntroductionAdapter adapter;


    private List<String> parentData = new ArrayList<>();
    private List<String[]> childData = new ArrayList<>();
    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.service_introduction);
        initData();
        findId();
        onBack("");

    }

    private void initData() {
        parentData.add("全方位配送服务");
        parentData.add("仓储服务");
        parentData.add("陆运");
        parentData.add("国际货运");
        parentData.add("快递服务");
        parentData.add("冷冻设置代理");

        childData.add(new String[]{"常温配送", "低温配送", "医药配送"});
        childData.add(new String[]{"仓储服务1", "仓储服务2", "仓储服务3"});
//      childData.add(new String[]{"陆运1", "陆运2", "陆运3"});

//
//        listview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
////                adapter.notifyDataSetChanged();
//            }
//        });
    }

    private void findId() {
        listview = findViewById(R.id.listview);
        adapter = new ServiceIntroductionAdapter(parentData,childData);

        listview.setAdapter(adapter);

        for (int i = 0; i < parentData.size(); i++) {
            listview.expandGroup(i);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service_introduction;
    }

    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.service_introduction);
    }

    @Override
    public void onBack(Object msg) {
        super.onBack(msg);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
