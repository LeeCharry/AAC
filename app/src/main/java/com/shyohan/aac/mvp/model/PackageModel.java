package com.shyohan.aac.mvp.model;

import com.shyohan.aac.base.BaseModel;
import com.shyohan.aac.bean.BaseObject;
import com.shyohan.aac.bean.RequestParam;
import com.shyohan.aac.mvp.contract.PackageContract;
import com.shyohan.aac.mvp.contract.SendingContract;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public class PackageModel extends BaseModel implements PackageContract.Model {

    @Override
    public Observable<BaseObject> lanjian(RequestParam requestParam) {
        return apiService.lanjian(requestParam);
    }
}
