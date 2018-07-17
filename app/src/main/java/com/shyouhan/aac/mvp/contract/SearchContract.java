package com.shyouhan.aac.mvp.contract;


import com.example.tulib.util.base.BaseView;
import com.shyouhan.aac.bean.PackStatusBean;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public interface SearchContract {
     interface View extends BaseView {
        void onSearchSuccess(PackStatusBean packStatusBean);
        void onSearchFailed(String error);
    }
    interface Model{
        Observable<PackStatusBean> search(String packid );
    }
}
