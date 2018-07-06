package com.shyohan.aac.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shyohan.aac.adapter.ImportantMattersRvAdapter;

import java.util.List;

import acc.tulip.com.accreputation.R;

/**
 * Created by lcy on 2018/7/1.
 * 搜索结果
 */

public class SearchResultAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public SearchResultAdapter( @Nullable List<String> data) {
        super(R.layout.item_search_result, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        private TextView tvDate;
//        private TextView tvTime;
//        private ImageView ivState;
//        private TextView tvState;
//        private TextView tvDetails;
//
//        tvDate = findViewById(R.id.tv_date);
//        tvTime = findViewById(R.id.tv_time);
//        ivState = findViewById(R.id.iv_state);
//        tvState = findViewById(R.id.tv_state);
//        tvDetails = findViewById(R.id.tv_details);

    }
}
