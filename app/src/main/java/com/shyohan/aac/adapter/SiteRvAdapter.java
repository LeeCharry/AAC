package com.shyohan.aac.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import acc.tulip.com.accreputation.R;

/**
 * Created by lcy on 2018/6/30.
 */

public class SiteRvAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public SiteRvAdapter(@Nullable List<String> data) {
        super(R.layout.item_site_inquiry, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        helper.setText(R.id.tv_site_name,"");
//        helper.setText(R.id.tv_site_addr,"");
//        helper.setText(R.id.tv_site_phone,"");


    }
}
