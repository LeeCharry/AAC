package com.shyouhan.aac.mvp.presenter;

import android.content.Context;

import com.example.tulib.util.base.BasePresenter;
import com.shyouhan.aac.R;
import com.shyouhan.aac.bean.BannerBean;
import com.shyouhan.aac.http.XApi;
import com.shyouhan.aac.mvp.contract.BannerContract;
import com.shyouhan.aac.mvp.model.BannerModel;
import com.shyouhan.aac.widget.ToastUtils;

import java.util.List;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by lcy on 2018/4/8.
 */

public class BannerPresenter extends BasePresenter<BannerContract.Model,BannerContract.View> {
    public BannerPresenter(Context context, BannerContract.View mRootview) {
        super(new BannerModel(), mRootview,context);
    }
    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }
    /**
     * 获取banner
     */
    public void getBanner() {
        mRootview.showLoading();
        if (!isNetworkConnected()) {
            ToastUtils.showShort(R.string.network_is_unavaliable);
            mRootview.hideLoading();
            return;
        }
        mMoudle.getBanner()
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<List<BannerBean>>getApiTransformer())
                .compose(XApi.<List<BannerBean>>getScheduler())
                .compose(bindToLifecycle(mRootview))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<List<BannerBean>>(mErrorHandler) {
                    @Override
                    public void onNext(List<BannerBean> baseObject) {
                        mRootview.hideLoading();
                        if (null != baseObject && baseObject.size() > 0) {
                            mRootview.onGetBannerSuccess(baseObject);
                        }else{
                            //Todo:
//                            mRootview.onGetBannerFailed(baseObject);
                        }
                    }
                });
    }
}