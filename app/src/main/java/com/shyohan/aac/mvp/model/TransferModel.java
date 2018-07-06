package com.shyohan.aac.mvp.model;

import com.shyohan.aac.base.BaseModel;
import com.shyohan.aac.bean.BaseObject;
import com.shyohan.aac.bean.RequestParam;
import com.shyohan.aac.mvp.contract.TransferContract;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public class TransferModel extends BaseModel implements TransferContract.Model {

    @Override
    public Observable<BaseObject> transfer(RequestParam requestParam) {
        return apiService.transfer(requestParam);
    }
}
