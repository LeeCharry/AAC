package com.shyouhan.aac.mvp.contract;


import com.example.tulib.util.base.BaseView;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public interface TransferContract {
     interface View extends BaseView {
        void onTransferSuccess(BaseObject baseObject);
        void onTransferFailed(String error);
    }
    interface Model{
        Observable<BaseObject> transfer(RequestParam requestParam);
    }
}
