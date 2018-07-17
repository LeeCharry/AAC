package com.shyouhan.aac.mvp.model;

import com.shyouhan.aac.base.BaseModel;
import com.shyouhan.aac.bean.BannerBean;
import com.shyouhan.aac.mvp.contract.BannerContract;

import java.util.List;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public class BannerModel extends BaseModel implements BannerContract.Model {


    @Override
    public Observable<List<BannerBean>> getBanner() {
        return apiService.getbanner();
    }
}
