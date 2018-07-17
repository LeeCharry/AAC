package com.shyouhan.aac.mvp.presenter;

import android.content.Context;


import com.example.tulib.util.base.BasePresenter;
import com.shyouhan.aac.R;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.http.XXApi;
import com.shyouhan.aac.mvp.contract.FeedbackContract;
import com.shyouhan.aac.mvp.model.FeedbackModel;
import com.shyouhan.aac.widget.ToastUtils;

import java.net.HttpURLConnection;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by lcy on 2018/4/8.
 */

public class FeedbackPresenter extends BasePresenter<FeedbackContract.Model,FeedbackContract.View> {
    public FeedbackPresenter(Context context, FeedbackContract.View mRootview) {
        super(new FeedbackModel(), mRootview,context);
    }
    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }

    public void feedback(String username,String phone,String content) {
        mRootview.showLoading();
        if (!isNetworkConnected()) {
            ToastUtils.showShort(R.string.network_is_unavaliable);
            mRootview.hideLoading();
            return;
        }
        RequestParam requestParam = new RequestParam(username, "",phone, content);
        mMoudle.feedback(requestParam)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XXApi.<BaseObject>getApiTransformer())
                .compose(XXApi.<BaseObject>getScheduler())
                .compose(bindToLifecycle(mRootview))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject baseObject) {
                        mRootview.hideLoading();
                        if (baseObject.getStatus() == HttpURLConnection.HTTP_OK) {
                            mRootview.onFeedbackSuccess(baseObject.getMsg());
                        }else{
                            if (isTranditional()) {
                                mRootview.onFeedbackFailed(baseObject.getTramsg());
                            }else{
                                mRootview.onFeedbackFailed(baseObject.getMsg());
                            }
                        }
                    }
                });
    }
}