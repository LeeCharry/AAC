package com.shyouhan.aac.mvp.model;

import com.shyouhan.aac.base.BaseModel;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.mvp.contract.TransferContract;

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
