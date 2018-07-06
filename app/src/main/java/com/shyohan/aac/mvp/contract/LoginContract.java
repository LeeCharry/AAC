package com.shyohan.aac.mvp.contract;


import com.example.tulib.util.base.BaseView;

import com.shyohan.aac.bean.BaseObject;
import com.shyohan.aac.bean.RequestParam;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public interface LoginContract {
     interface View extends BaseView {
        void onLogin();
    }
    interface Model{
        Observable<BaseObject> login(RequestParam requestParam);
    }
}
