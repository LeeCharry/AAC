package com.example.tulib.util.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.Locale;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErroListener;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lcy on 2018/4/8.
 */

public class BasePresenter<M, V extends BaseView> implements presenter, ResponseErroListener {
    protected M mMoudle;
    protected V mRootview;
    protected Gson gson;
    protected Context context;
    protected CompositeSubscription mCompositeSubscription;
    protected RxErrorHandler mErrorHandler;

    public BasePresenter(M mMoudle, V mRootview) {
        this.mMoudle = mMoudle;
        this.mRootview = mRootview;
        onStart();
    }

    public BasePresenter(M mMoudle, V mRootview, Context context) {
        this.mMoudle = mMoudle;
        this.mRootview = mRootview;
        this.context = context;
        this.mErrorHandler = RxErrorHandler.builder().with(context).responseErroListener(this).build();
        onStart();
    }

    protected Locale getLocaLanguage() {
        return context.getResources().getConfiguration().locale;
    }

    protected Boolean isTranditional() {
        return getLocaLanguage().equals(Locale.TRADITIONAL_CHINESE);
    }

    /**
     * 判断是否有网络连接
     * @return
     */
    public boolean isNetworkConnected() {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission")
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {
        gson = new Gson();
    }

    @Override
    public void onDestroy() {
        unSubscribe();//解除订阅
        this.mMoudle = null;
        this.mRootview = null;
    }

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();//保证activity结束时取消所有正在执行的订阅
        }
    }

    @Override
    public void unSubscribe(Subscription subscription) {
        if (null != subscription && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void handleResponseError(Context context, Exception e) {
        mRootview.hideLoading();
    }

    public static LifecycleTransformer bindToLifecycle(BaseView view) {
        if (view instanceof XActivity) {
            return ((XActivity) view).bindToLifecycle();
        } else if (view instanceof XFragment) {
            return ((XFragment) view).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }
    }
}
