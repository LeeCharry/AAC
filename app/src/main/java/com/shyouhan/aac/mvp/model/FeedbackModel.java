package com.shyouhan.aac.mvp.model;

import com.shyouhan.aac.base.BaseModel;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.mvp.contract.FeedbackContract;

import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public class FeedbackModel extends BaseModel implements FeedbackContract.Model {
    @Override
    public Observable<BaseObject> feedback(RequestParam requestParam) {
        return apiService.feedback(requestParam);
    }
}
