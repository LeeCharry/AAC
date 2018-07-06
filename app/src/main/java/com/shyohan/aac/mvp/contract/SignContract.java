package com.shyohan.aac.mvp.contract;


import com.example.tulib.util.base.BaseView;
import com.shyohan.aac.bean.BaseObject;
import com.shyohan.aac.bean.RequestParam;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public interface SignContract {
     interface View extends BaseView {
        void onSignSuccess();
        void onSignFailed(String error);
    }
    interface Model{
        Observable<BaseObject> sign(RequestParam requestParam);
    }
}
