package com.shyouhan.aac.mvp.contract;


import com.example.tulib.util.base.BaseView;
import com.shyouhan.aac.bean.PlaceBean;

import java.util.List;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public interface GetPlaceContract {
     interface View extends BaseView {
        void onGetPlaceSuccess(List<PlaceBean> placeBeanList);
        void onGetPlaceFailed(String error);
    }
    interface Model{
        Observable<List<PlaceBean>> getPlace();
    }
}
