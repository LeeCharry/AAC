package com.shyouhan.aac.mvp.model;

import com.shyouhan.aac.base.BaseModel;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.mvp.contract.ArrivePlaceContract;

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
