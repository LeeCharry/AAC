package com.shyouhan.aac.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shyouhan.aac.R;
import com.shyouhan.aac.bean.ResultBean;

import java.util.List;

/**
 * Created by lcy on 2018/6/30.
 */

public class ScanGunRvAdapter extends BaseQuickAdapter<ResultBean,BaseViewHolder> {
    public ScanGunRvAdapter(@Nullable List<ResultBean> data) {
        super(R.layout.item_rv_scan_gun, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResultBean item) {

        helper.setText(R.id.tv_result,"单号 "+item.getDanhao()+"  状态 "+item.getMsg());

    }
}
