package com.shyohan.aac.mvp.model;

import com.shyohan.aac.base.BaseModel;
import com.shyohan.aac.bean.BaseObject;
import com.shyohan.aac.bean.RequestParam;
import com.shyohan.aac.mvp.contract.DeliveryContract;
import com.shyohan.aac.mvp.contract.PackageContract;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public class DeliveryModel extends BaseModel implements DeliveryContract.Model {
    @Override
    public Observable<BaseObject> delivery(RequestParam requestParam) {
        return apiService.delivery(requestParam);
    }
}
