package com.shyohan.aac.mvp.model;

import com.shyohan.aac.base.BaseModel;
import com.shyohan.aac.bean.BaseObject;
import com.shyohan.aac.bean.RequestParam;
import com.shyohan.aac.mvp.contract.SignContract;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public class SignModel extends BaseModel implements SignContract.Model {


    @Override
    public Observable<BaseObject> sign(RequestParam requestParam) {
        return apiService.sign(requestParam);
    }
}
