package com.shyouhan.aac.mvp.model;

import com.shyouhan.aac.base.BaseModel;
import com.shyouhan.aac.bean.PlaceBean;
import com.shyouhan.aac.mvp.contract.GetPlaceContract;

import java.util.List;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public class GetPlaceModel extends BaseModel implements GetPlaceContract.Model {

    @Override
    public Observable<List<PlaceBean>> getPlace() {
        return apiService.getplace();
    }
}
