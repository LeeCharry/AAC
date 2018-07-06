package com.shyohan.aac.mvp.model;

import com.shyohan.aac.base.BaseModel;
import com.shyohan.aac.bean.BaseObject;
import com.shyohan.aac.bean.RequestParam;
import com.shyohan.aac.mvp.contract.ArrivePlaceContract;
import com.shyohan.aac.mvp.contract.DeliveryContract;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public class ArrivePlaceModel extends BaseModel implements ArrivePlaceContract.Model {
    @Override
    public Observable<BaseObject> arrivePlace(RequestParam requestParam) {
        return apiService.arriveplace(requestParam);
    }
}
