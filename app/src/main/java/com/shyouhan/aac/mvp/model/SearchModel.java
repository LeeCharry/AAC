package com.shyouhan.aac.mvp.model;

import com.shyouhan.aac.base.BaseModel;
import com.shyouhan.aac.bean.PackStatusBean;
import com.shyouhan.aac.mvp.contract.SearchContract;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public class SearchModel extends BaseModel implements SearchContract.Model {

    @Override
    public Observable<PackStatusBean> search(String packid) {
        return apiService.search(packid);
    }
}
