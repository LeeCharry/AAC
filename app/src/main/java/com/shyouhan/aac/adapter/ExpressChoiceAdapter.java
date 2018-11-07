package com.shyouhan.aac.adapter;

import android.widget.CompoundButton;
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
    private  ExpressChoiceAdapter adapter;
    private  List<ExpressBean> data;
    public ExpressChoiceAdapter( List<ExpressBean> data) {
        super(R.layout.item_express_choice, data);
        this.adapter = this;
        this.data = data;
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
//            rb.setChecked(true);
            rb.setBackgroundResource(R.drawable.ic_selected);
        } else {
//            rb.setChecked(false);
            rb.setBackgroundResource(R.drawable.ic_unselect);
        }
        final int position = data.indexOf(item);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!item.getSelected()) {
                    item.setSelected(true);
                    for (int i = 0; i < data.size() ; i++) {
                        if (i != position) {
                            data.get(i).setSelected(false);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
}
