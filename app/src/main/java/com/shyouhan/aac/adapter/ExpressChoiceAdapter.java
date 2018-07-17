package com.shyouhan.aac.adapter;

import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shyouhan.aac.R;
import com.shyouhan.aac.bean.ExpressBean;
import com.shyouhan.aac.constant.AppConstant;

import java.util.List;
import java.util.Locale;



/**
 * Created by lcy on 2018/7/1.
 * 快递选择
 */

public class ExpressChoiceAdapter extends BaseQuickAdapter<ExpressBean, BaseViewHolder> {
    public ExpressChoiceAdapter( List<ExpressBean> data) {
        super(R.layout.item_express_choice, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ExpressBean item) {
        final RadioButton rb = helper.getView(R.id.rb);
        if (mContext.getResources().getConfiguration().locale.equals(Locale.TRADITIONAL_CHINESE)) {
            helper.setText(R.id.tv_express_name, item.getTraname());
        } else {
            helper.setText(R.id.tv_express_name, item.getName());
        }
        if (item.getSelected()){
            rb.setChecked(true);
        } else {
            rb.setChecked(false);
        }
    }
}
