package com.shyouhan.aac.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shyouhan.aac.R;

import java.util.List;


/**
 * Created by lcy on 2018/7/9.
 */
/*
*
* */
public class SearchHistoryAdapter extends BaseQuickAdapter<String,BaseViewHolder> implements OnItemHelperCallBack{
    private  List<String> data;

    public SearchHistoryAdapter(@Nullable List<String> data) {
        super(R.layout.item_search_history, data);
        this.data = data;
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_waybill,mContext.getString(R.string.waybill_no3)+item);
    }
    @Override
    public void onSwipe(int poition) {
        data.remove(poition);
        notifyItemRemoved(poition+1);
    }
}


