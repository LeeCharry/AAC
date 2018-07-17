package com.shyouhan.aac.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shyouhan.aac.R;
import com.shyouhan.aac.bean.PlaceBean;

import java.util.List;
import java.util.Locale;

/**
 * Created by lcy on 2018/6/30.
 */

public class SiteRvAdapter extends BaseQuickAdapter<PlaceBean,BaseViewHolder> {
    public SiteRvAdapter(@Nullable List<PlaceBean> data) {
        super(R.layout.item_site_inquiry, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlaceBean item) {
        if (isTranditional()) {
            helper.setText(R.id.tv_site_name,item.getTraplacename());
            helper.setText(R.id.tv_site_addr,item.getTraaddress());
        }else{
            helper.setText(R.id.tv_site_name,item.getPlacename());
            helper.setText(R.id.tv_site_addr,item.getAddress());
        }
        helper.setText(R.id.tv_site_phone,item.getPhone());
    }
    /**
     * 获取系统语言环境
     */
    protected Locale getLocaLanguage() {
        return this.mContext.getResources().getConfiguration().locale;
    }

    /**
     * 是否是繁体
     * @return
     */
    protected Boolean isTranditional() {
        return getLocaLanguage().equals(Locale.TRADITIONAL_CHINESE);
    }
}
