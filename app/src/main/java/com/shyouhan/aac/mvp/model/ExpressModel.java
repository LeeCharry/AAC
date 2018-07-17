package com.shyouhan.aac.mvp.model;

import com.shyouhan.aac.base.BaseModel;
import com.shyouhan.aac.bean.ExpressBean;
import com.shyouhan.aac.mvp.contract.ExpressContract;

import java.util.List;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public class ExpressModel extends BaseModel implements ExpressContract.Model {

    @Override
    public Observable<List<ExpressBean>> getExpress() {
        return apiService.getExpress();
    }
}
