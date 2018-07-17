package com.shyouhan.aac.mvp.model;

import com.shyouhan.aac.base.BaseModel;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.mvp.contract.PackageContract;

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
