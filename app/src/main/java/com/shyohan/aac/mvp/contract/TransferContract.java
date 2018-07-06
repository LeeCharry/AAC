package com.shyohan.aac.mvp.contract;


import com.example.tulib.util.base.BaseView;
import com.shyohan.aac.bean.BaseObject;
import com.shyohan.aac.bean.RequestParam;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public interface TransferContract {
     interface View extends BaseView {
        void onTransferSuccess();
        void onTransferFailed(String error);
    }
    interface Model{
        Observable<BaseObject> transfer(RequestParam requestParam);
    }
}
