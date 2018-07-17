package com.example.tulib.util.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.example.tulib.util.widget.LoadingDailog;
import com.google.gson.Gson;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


/**
 * Created by lcy on 2018/4/8.
 */

public abstract class XActivity extends RxAppCompatActivity {
    protected Gson gson;
    protected LoadingDailog loadingDailog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置不需要标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(getLayoutResId());
        //设置沉浸式标题栏
//        DeviceUtil.setImmersiveStatus(XActivity.this);

        gson = new Gson();
        initLoadingDailog();
//        ButterKnife.bind(this);
        initView();
    }




    private void initLoadingDailog() {
        loadingDailog = new LoadingDailog.Builder(this)
                .setCancelable(true)
                .setMessage("加載中...")
                .setShowMessage(true)
                .setCancelOutside(false)
                .create();
    }

    protected abstract void initView();

    protected abstract int getLayoutResId();

}
