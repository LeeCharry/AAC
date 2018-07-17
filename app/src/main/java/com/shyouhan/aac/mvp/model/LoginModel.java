package com.shyouhan.aac.mvp.model;

import com.shyouhan.aac.base.BaseModel;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.mvp.contract.LoginContract;

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
