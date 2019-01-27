package com.shyouhan.aac.mvp.model;

import com.shyouhan.aac.base.BaseModel;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.mvp.contract.ArrivePlaceContract;
import com.shyouhan.aac.mvp.contract.FakeContract;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public class FakeModel extends BaseModel implements FakeContract.Model {
    @Override
    public Observable<BaseObject> fake(RequestParam requestParam) {
        return apiService.fake(requestParam);
    }
}
