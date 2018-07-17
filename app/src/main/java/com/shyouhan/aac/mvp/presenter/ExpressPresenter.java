package com.shyouhan.aac.mvp.presenter;

import android.content.Context;

import com.example.tulib.util.base.BasePresenter;
import com.shyouhan.aac.R;
import com.shyouhan.aac.bean.ExpressBean;
import com.shyouhan.aac.http.XApi;
import com.shyouhan.aac.mvp.contract.ExpressContract;
import com.shyouhan.aac.mvp.model.ExpressModel;
import com.shyouhan.aac.widget.ToastUtils;

import java.util.List;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by lcy on 2018/4/8.
 */

public class ExpressPresenter extends BasePresenter<ExpressContract.Model,ExpressContract.View> {
    public ExpressPresenter(Context context, ExpressContract.View mRootview) {
        super(new ExpressModel(), mRootview,context);
    }
    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }
    /**
     * 抵达站所
     */
    public void getExpress() {
        mRootview.showLoading();
        if (!isNetworkConnected()) {
            ToastUtils.showShort(R.string.network_is_unavaliable);
            mRootview.hideLoading();
            return;
        }
        mMoudle.getExpress()
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<List<ExpressBean>>getApiTransformer())
                .compose(XApi.<List<ExpressBean>>getScheduler())
                .compose(bindToLifecycle(mRootview))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<List<ExpressBean>>(mErrorHandler) {
                    @Override
                    public void onNext(List<ExpressBean> baseObject) {
                        mRootview.hideLoading();

                        if (null !=  baseObject &&  baseObject.size() > 0) {
                            mRootview.onGetExpressSuccess( baseObject);
                        }else{
                            //Todo:
//                        mRootview.onGetExpressFailed(baseObject.getMsg());
                        }
                    }
                });
    }
}