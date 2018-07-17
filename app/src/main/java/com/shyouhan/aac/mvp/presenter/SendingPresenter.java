package com.shyouhan.aac.mvp.presenter;

import android.content.Context;

import com.example.tulib.util.base.BasePresenter;
import com.shyouhan.aac.R;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.http.XXApi;
import com.shyouhan.aac.mvp.contract.SendingContract;
import com.shyouhan.aac.mvp.model.SendingModel;
import com.shyouhan.aac.widget.SPUtils;
import com.shyouhan.aac.widget.ToastUtils;

import java.net.HttpURLConnection;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by lcy on 2018/4/8.
 */

public class SendingPresenter extends BasePresenter<SendingContract.Model,SendingContract.View> {
    public SendingPresenter(Context context, SendingContract.View mRootview) {
        super(new SendingModel(), mRootview,context);
    }
    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }
    /**
     * 出货
     */
    public void sending(String packid) {
        mRootview.showLoading();
        if (!isNetworkConnected()) {
            ToastUtils.showShort(R.string.network_is_unavaliable);
            mRootview.hideLoading();
            return;
        }
        RequestParam requestParam = new RequestParam(SPUtils.getInstance().getString(AppConstant.TOKEN),packid);
        mMoudle.sending(requestParam)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XXApi.<BaseObject>getApiTransformer())
                .compose(XXApi.<BaseObject>getScheduler())
                .compose(bindToLifecycle(mRootview))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject baseObject) {
                        mRootview.hideLoading();
                        if (baseObject.getStatus() == HttpURLConnection.HTTP_OK) {
                            mRootview.onSendingSuccess(baseObject);
                        }else{
                            if (isTranditional()) {
                                mRootview.onSendingFailed(baseObject.getTramsg());
                            }else{
                                mRootview.onSendingFailed(baseObject.getMsg());
                            }
                        }
                    }
                });
    }

}