package com.shyouhan.aac.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.tulib.util.base.BasePresenter;
import com.shyouhan.aac.R;
import com.shyouhan.aac.activity.LoginActivity;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.http.XXApi;
import com.shyouhan.aac.mvp.contract.SignContract;
import com.shyouhan.aac.mvp.model.SignModel;
import com.shyouhan.aac.widget.SPUtils;
import com.shyouhan.aac.widget.ToastUtils;

import java.net.HttpURLConnection;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by lcy on 2018/4/8.
 */

public class SignPresenter extends BasePresenter<SignContract.Model,SignContract.View> {
    public SignPresenter(Context context, SignContract.View mRootview) {
        super(new SignModel(), mRootview,context);
    }
    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }
    /**
     * 签收
     */
    public void sign(String packid) {
        mRootview.showLoading();
        if (!isNetworkConnected()) {
            ToastUtils.showShort(R.string.network_is_unavaliable);
            mRootview.hideLoading();
            return;
        }
      RequestParam requestParam = new RequestParam(SPUtils.getInstance().getString(AppConstant.TOKEN),packid);
        mMoudle.sign(requestParam)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XXApi.<BaseObject>getApiTransformer())
                .compose(XXApi.<BaseObject>getScheduler())
                .compose(bindToLifecycle(mRootview))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject baseObject) {
                        mRootview.hideLoading();
                        if (baseObject.getStatus() == HttpURLConnection.HTTP_OK) {
                            mRootview.onSignSuccess(baseObject);
                        }else{
                            if (isTranditional()) {
                                mRootview.onSignFailed(baseObject.getTramsg());
                            }else{
                                mRootview.onSignFailed(baseObject.getMsg());
                            }
                            if (baseObject.getStatus() == 401){
                                mRootview.launchActivity(new Intent(context, LoginActivity.class));
                            }
                        }
                    }
                });
    }
}