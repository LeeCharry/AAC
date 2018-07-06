package com.shyohan.aac.mvp.model;

import com.shyohan.aac.base.BaseModel;
import com.shyohan.aac.bean.BaseObject;
import com.shyohan.aac.bean.RequestParam;
import com.shyohan.aac.mvp.contract.SendingContract;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public class SendingModel extends BaseModel implements SendingContract.Model {

    @Override
    public Observable<BaseObject> sending(RequestParam requestParam) {
        return apiService.sending(requestParam);
    }
}
