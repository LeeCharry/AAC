package com.shyohan.aac.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shyohan.aac.adapter.SiteRvAdapter;
import com.shyohan.aac.base.BaseActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import acc.tulip.com.accreputation.R;

/**
 * 站点查询
 */
public class SiteInquiryActivity extends BaseActivity {
    private RecyclerView rvSites;
    private List<String> datas = new ArrayList<>();
    private SiteRvAdapter adapter;

    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.site_inquiry);
        findId();
        onBack("");
        initRv();
    }

    private void initRv() {
        for (int i = 0; i < 7; i++) {
            datas.add(" "+i);
        }
        rvSites.setLayoutManager(new LinearLayoutManager(SiteInquiryActivity.this));
        adapter = new SiteRvAdapter(datas);
        rvSites.setAdapter(adapter);
    }

    private void findId() {
        rvSites = findViewById(R.id.rv_sites);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_site_inquiry;
    }

    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.site_inquiry);
    }
    @Override
    public void onBack(Object msg) {
        super.onBack(msg);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
