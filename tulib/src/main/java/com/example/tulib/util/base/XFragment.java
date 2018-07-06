package com.example.tulib.util.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tulib.util.widget.LoadingDailog;
import com.google.gson.Gson;
import com.trello.rxlifecycle.components.RxFragment;

/**
 * Created by lcy on 2018/4/8.
 */

public abstract class XFragment extends RxFragment {
    protected View mRootview;
    protected Gson gson;
    protected Context context;
    protected LoadingDailog loadingDailog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       mRootview = inflater.inflate(getLayoutResId(), container, false);
        context = container.getContext();
       initView(mRootview);
       initLoadingDailog();
        return mRootview;
    }

    private void initLoadingDailog() {
        loadingDailog = new LoadingDailog.Builder(context)
                .setCancelable(true)
                .setMessage("加載中...")
                .setShowMessage(true)
                .setCancelOutside(false)
                .create();
    }

    protected abstract void initView(View mRootview);

    protected abstract int getLayoutResId();
}
