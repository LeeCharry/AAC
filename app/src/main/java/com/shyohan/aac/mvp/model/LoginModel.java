package com.shyohan.aac.mvp.model;

import com.shyohan.aac.base.BaseModel;
import com.shyohan.aac.bean.BaseObject;
import com.shyohan.aac.bean.RequestParam;
import com.shyohan.aac.mvp.contract.LoginContract;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public class LoginModel extends BaseModel implements LoginContract.Model {


    @Override
    public Observable<BaseObject> login(RequestParam requestParam) {
        return apiService.login(requestParam);
    }
}
