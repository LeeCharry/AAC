package com.shyouhan.aac.mvp.contract;


import com.example.tulib.util.base.BaseView;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public interface DeliveryContract {
     interface View extends BaseView {
        void onDeliverySuccess(BaseObject baseObject);
        void onDeliveryFailed(String error);
    }
    interface Model{
        Observable<BaseObject> delivery(RequestParam requestParam);
    }
}
