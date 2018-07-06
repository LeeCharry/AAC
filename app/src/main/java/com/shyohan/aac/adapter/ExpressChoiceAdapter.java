package com.shyohan.aac.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import acc.tulip.com.accreputation.R;

/**
 * Created by lcy on 2018/7/1.
 * 快递选择
 */

public class ExpressChoiceAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    private List<String > datas;
    public ExpressChoiceAdapter(@Nullable List<String> data) {
        super(R.layout.item_express_choice, data);
        this.datas = data;
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
        RadioButton rb = helper.getView(R.id.rb);
        helper.setText(R.id.tv_express_name,item);
        if (datas.indexOf(item) == 0) {
                rb.setChecked(true);
        }else{
            rb.setChecked(false);
        }
    }
}
