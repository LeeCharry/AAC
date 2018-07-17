package com.shyouhan.aac.mvp.model;

import com.shyouhan.aac.base.BaseModel;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.mvp.contract.SendingContract;

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
