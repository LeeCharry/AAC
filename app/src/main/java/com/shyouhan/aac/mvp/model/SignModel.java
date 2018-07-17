package com.shyouhan.aac.mvp.model;

import com.shyouhan.aac.base.BaseModel;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.mvp.contract.SignContract;

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
