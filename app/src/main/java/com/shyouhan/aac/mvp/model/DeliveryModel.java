package com.shyouhan.aac.mvp.model;

import com.shyouhan.aac.base.BaseModel;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.mvp.contract.DeliveryContract;

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
