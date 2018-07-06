package com.shyohan.aac.mvp.presenter;

import android.content.Context;

import com.blankj.utilcode.util.SPUtils;
import com.example.tulib.util.base.BasePresenter;
import com.shyohan.aac.bean.BaseObject;
import com.shyohan.aac.bean.RequestParam;
import com.shyohan.aac.constant.AppConstant;
import com.shyohan.aac.http.XXApi;
import com.shyohan.aac.mvp.contract.TransferContract;
import com.shyohan.aac.mvp.model.TransferModel;

import java.net.HttpURLConnection;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by lcy on 2018/4/8.
 */

public class TransferPresenter extends BasePresenter<TransferContract.Model,TransferContract.View> {
    public TransferPresenter(Context context, TransferContract.View mRootview) {
        super(new TransferModel(), mRootview,context);
    }
    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }
    /**
     * 中转快递
     */
    public void transfer(long packid) {
        mRootview.showLoading();
        RequestParam requestParam = new RequestParam(SPUtils.getInstance().getString(AppConstant.TOKEN),packid);
        mMoudle.transfer(requestParam)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XXApi.<BaseObject>getApiTransformer())
                .compose(XXApi.<BaseObject>getScheduler())
                .compose(bindToLifecycle(mRootview))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject baseObject) {
                        mRootview.hideLoading();
                        if (baseObject.getStatus() == HttpURLConnection.HTTP_OK) {
                            mRootview.onTransferSuccess();
                        }else{
                            //Todo:
                            mRootview.onTransferFailed("");
                        }
                    }
                });
    }
}