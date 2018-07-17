package com.shyouhan.aac.mvp.contract;


import com.example.tulib.util.base.BaseView;
import com.shyouhan.aac.bean.ExpressBean;

import java.util.List;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public interface ExpressContract {
     interface View extends BaseView {
        void onGetExpressSuccess(List<ExpressBean> baseObject);
        void onGetExpressFailed(String error);
    }
    interface Model{
        Observable<List<ExpressBean>> getExpress();
    }
}
