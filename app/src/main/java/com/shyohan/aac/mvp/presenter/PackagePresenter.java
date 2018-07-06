package com.shyohan.aac.mvp.presenter;

import android.content.Context;

import com.blankj.utilcode.util.SPUtils;
import com.example.tulib.util.base.BasePresenter;
import com.shyohan.aac.bean.BaseObject;
import com.shyohan.aac.bean.RequestParam;
import com.shyohan.aac.constant.AppConstant;
import com.shyohan.aac.http.XXApi;
import com.shyohan.aac.mvp.contract.PackageContract;
import com.shyohan.aac.mvp.model.PackageModel;
import com.shyohan.aac.mvp.model.SendingModel;

import java.net.HttpURLConnection;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by lcy on 2018/4/8.
 */

public class PackagePresenter extends BasePresenter<PackageContract.Model,PackageContract.View> {
    public PackagePresenter(Context context, PackageContract.View mRootview) {
        super(new PackageModel(), mRootview,context);
    }
    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }
    /**
     * 揽件
     */
    public void lanjian(long packid) {
        mRootview.showLoading();
        RequestParam requestParam = new RequestParam(SPUtils.getInstance().getString(AppConstant.TOKEN),packid);
        mMoudle.lanjian(requestParam)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XXApi.<BaseObject>getApiTransformer())
                .compose(XXApi.<BaseObject>getScheduler())
                .compose(bindToLifecycle(mRootview))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject baseObject) {
                        mRootview.hideLoading();
                        if (baseObject.getStatus() == HttpURLConnection.HTTP_OK) {
                            mRootview.onPackageSuccess();
                        }else{
                            //Todo:
                            mRootview.onPackageFailed("");
                        }
                    }
                });
    }
}