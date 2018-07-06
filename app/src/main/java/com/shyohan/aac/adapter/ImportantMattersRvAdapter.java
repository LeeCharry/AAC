package com.shyohan.aac.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import acc.tulip.com.accreputation.R;

/**
 * Created by lcy on 2018/6/30.
 * 重要事项列表
 */

public class ImportantMattersRvAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public ImportantMattersRvAdapter(@Nullable List<String> data) {
        super(R.layout.item_service_list_parent, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_parent,item);
//        helper.setText(R.id.tv_site_addr,"");
//        helper.setText(R.id.tv_site_phone,"");

    }
}
