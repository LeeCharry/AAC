package com.shyouhan.aac.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shyouhan.aac.R;
import com.shyouhan.aac.bean.PackStatusBean;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.widget.DateUtil;

import java.util.List;
import java.util.Locale;


/**
 * Created by lcy on 2018/7/1.
 * 搜索结果
 */

public class SearchResultAdapter extends BaseQuickAdapter<PackStatusBean.StatusBean,BaseViewHolder> {

    private final List<PackStatusBean.StatusBean> data;

    public SearchResultAdapter(@Nullable List<PackStatusBean.StatusBean> data) {
        super(R.layout.item_search_result, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, PackStatusBean.StatusBean item) {
        ImageView ivState = helper.getView(R.id.iv_state);
        ivState.setScaleType(ImageView.ScaleType.FIT_XY);
        Long time = item.getTime();

        String date = DateUtil.getDate3(time);
        String[] split = date.split(" ");
//        if (DateUtil.isToday(time)){
//            helper.setText(R.id.tv_date,R.string.today);
//        }else{
//            helper.setText(R.id.tv_date,split[0]);
//        }
        helper.setText(R.id.tv_date,split[0]);
        helper.setText(R.id.tv_time,split[1]);
        String status = item.getStatus();
        TextView tvStatus = helper.getView(R.id.tv_state);
        TextView TvDetail = helper.getView(R.id.tv_details);
        switch (status) {
            case AppConstant.LANJIAN:
                tvStatus.setText(R.string.has_reward);
//                if (data.size() > 1) {
//                    ivState.setScaleType(ImageView.ScaleType.FIT_START);
//                    ivState.setImageResource(R.mipmap.ic_big_dot);
//                }
                break;
            case AppConstant.SENDING:
                tvStatus.setText(R.string.has_sending);
                break;
            case AppConstant.TRANSFER:
                tvStatus.setText(R.string.has_transfer);
                break;
            case AppConstant.JIADAN:
                tvStatus.setText(R.string.in_transit);
                break;
            case AppConstant.ARRIVEPLACE:
                tvStatus.setText(R.string.has_arriveplace);
                break;
            case AppConstant.DELIVERY:
                tvStatus.setText(R.string.has_delivery);
                break;
            case AppConstant.SIGN:
                tvStatus.setText(R.string.has_sign);
                break;
        }
        if (isTranditional()) {
            TvDetail.setText(item.getTradesc());
        }else {
            TvDetail.setText(item.getDesc());
        }
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
//
//    /**
//     * @param time
//     * @return
//     */
//    private String getDate(Long time) {
//        String str = new SimpleDateFormat("MM月dd日").format(new Date(time));
//        return str;
//    }
//
//    /**
//     * 是否是今天
//     * @param time
//     * @return
//     */
//    private boolean isToday(Long time) {
//        return getDate(time).equals(getDate(System.currentTimeMillis()));
//    }
//
//    /**
//     *
//     * @param time
//     * @return
//     */
//    private String getTime(Long time) {
//        return new SimpleDateFormat("mm:ss").format(new Date(time));
//    }
}
