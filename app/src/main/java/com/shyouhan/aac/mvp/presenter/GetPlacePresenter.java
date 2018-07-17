package com.shyouhan.aac.mvp.presenter;

import android.content.Context;

import com.example.tulib.util.base.BasePresenter;
import com.shyouhan.aac.R;
import com.shyouhan.aac.bean.PlaceBean;
import com.shyouhan.aac.http.XApi;
import com.shyouhan.aac.mvp.contract.GetPlaceContract;
import com.shyouhan.aac.mvp.model.GetPlaceModel;
import com.shyouhan.aac.widget.ToastUtils;

import java.util.List;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by lcy on 2018/4/8.
 */

public class GetPlacePresenter extends BasePresenter<GetPlaceContract.Model,GetPlaceContract.View> {
    public GetPlacePresenter(Context context, GetPlaceContract.View mRootview) {
        super(new GetPlaceModel(), mRootview,context);
    }
    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }

    public void getPlace() {
        mRootview.showLoading();
        if (!isNetworkConnected()) {
            ToastUtils.showShort(R.string.network_is_unavaliable);
            mRootview.hideLoading();
            return;
        }
        mMoudle.getPlace()
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<List<PlaceBean>>getApiTransformer())
                .compose(XApi.<List<PlaceBean>>getScheduler())
                .compose(bindToLifecycle(mRootview))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<List<PlaceBean>>(mErrorHandler) {
                    @Override
                    public void onNext(List<PlaceBean> baseObject) {
                        mRootview.hideLoading();
                        if (null != baseObject && baseObject.size() > 0) {
                            mRootview.onGetPlaceSuccess(baseObject);
                        }else{

                        }
                    }
                });
    }
}