package com.shyouhan.aac.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;


import com.example.tulib.util.base.BasePresenter;
import com.shyouhan.aac.R;
import com.shyouhan.aac.activity.LoginActivity;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.http.XXApi;
import com.shyouhan.aac.mvp.contract.ArrivePlaceContract;
import com.shyouhan.aac.mvp.model.ArrivePlaceModel;
import com.shyouhan.aac.widget.SPUtils;
import com.shyouhan.aac.widget.ToastUtils;

import java.net.HttpURLConnection;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by lcy on 2018/4/8.
 */

public class ArrivePlacePresenter extends BasePresenter<ArrivePlaceContract.Model,ArrivePlaceContract.View> {
    public ArrivePlacePresenter(Context context, ArrivePlaceContract.View mRootview) {
        super(new ArrivePlaceModel(), mRootview,context);
    }
    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }
    /**
     * 抵达站所
     */
    public void arrivePlace(String packid,String fakeIds) {
        mRootview.showLoading();
        if (!isNetworkConnected()) {
            ToastUtils.showShort(R.string.network_is_unavaliable);
            mRootview.hideLoading();
            return;
        }
        RequestParam requestParam;
        if (!TextUtils.isEmpty(fakeIds)){
            requestParam = new RequestParam(SPUtils.getInstance().getString(AppConstant.TOKEN),packid,fakeIds,0);
        }else{
            requestParam = new RequestParam(SPUtils.getInstance().getString(AppConstant.TOKEN),packid);
        }
        mMoudle.arrivePlace(requestParam)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XXApi.<BaseObject>getApiTransformer())
                .compose(XXApi.<BaseObject>getScheduler())
//                .compose(bindToLifecycle(mRootview))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject baseObject) {
                        mRootview.hideLoading();
                        if (baseObject.getStatus() == HttpURLConnection.HTTP_OK) {
                            mRootview.onArrivePlaceSuccess(baseObject);
                        }else{
                            if (isTranditional()) {
                                mRootview.onArrivePlaceFailed(baseObject.getTramsg());
                            }else{
                                mRootview.onArrivePlaceFailed(baseObject.getMsg());
                            }
                            if (baseObject.getStatus() == 401){
                                mRootview.launchActivity(new Intent(context, LoginActivity.class));
                            }
                        }
                    }
                });
    }
}