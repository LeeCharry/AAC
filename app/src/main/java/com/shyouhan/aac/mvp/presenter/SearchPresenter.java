package com.shyouhan.aac.mvp.presenter;

import android.content.Context;

import com.example.tulib.util.base.BasePresenter;
import com.shyouhan.aac.R;
import com.shyouhan.aac.bean.PackStatusBean;
import com.shyouhan.aac.http.XApi;
import com.shyouhan.aac.mvp.contract.SearchContract;
import com.shyouhan.aac.mvp.model.SearchModel;
import com.shyouhan.aac.widget.ToastUtils;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by lcy on 2018/4/8.
 */

public class SearchPresenter extends BasePresenter<SearchContract.Model,SearchContract.View> {
    public SearchPresenter(Context context, SearchContract.View mRootview) {
        super(new SearchModel(), mRootview,context);
    }
    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }
    /**
     * 抵达站所
     */
    public void search(String packid) {
        mRootview.showLoading();
        if (!isNetworkConnected()) {
            ToastUtils.showShort(R.string.network_is_unavaliable);
            mRootview.hideLoading();
            return;
        }
        mMoudle.search(packid)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<PackStatusBean>getApiTransformer())
                .compose(XApi.<PackStatusBean>getScheduler())
                .compose(bindToLifecycle(mRootview))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<PackStatusBean>(mErrorHandler) {
                    @Override
                    public void onNext(PackStatusBean baseObject) {
                        mRootview.hideLoading();
                        if (null != baseObject.getData() && baseObject.getData().size() > 0 ) {
                            //有列表数据返回
                            mRootview.onSearchSuccess(baseObject);

                        }else{
                            if (isTranditional()) {
                                mRootview.onSearchFailed(baseObject.getTramsg());
                            }else{
                                mRootview.onSearchFailed(baseObject.getMsg());
                            }
                        }
                    }
                });
    }
}