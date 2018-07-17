package com.shyouhan.aac.mvp.contract;


import com.example.tulib.util.base.BaseView;
import com.shyouhan.aac.bean.BannerBean;

import java.util.List;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public interface BannerContract {
     interface View extends BaseView {
        void onGetBannerSuccess(List<BannerBean> bannerBeanList);
    }
    interface Model{
        Observable<List<BannerBean>> getBanner();
    }
}
