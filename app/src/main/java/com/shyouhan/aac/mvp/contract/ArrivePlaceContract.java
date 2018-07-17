package com.shyouhan.aac.mvp.contract;


import com.example.tulib.util.base.BaseView;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public interface ArrivePlaceContract {
     interface View extends BaseView {
        void onArrivePlaceSuccess(BaseObject baseObject);
        void onArrivePlaceFailed(String error);
    }
    interface Model{
        Observable<BaseObject> arrivePlace(RequestParam requestParam);
    }
}
