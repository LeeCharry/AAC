package com.shyouhan.aac.activity;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import com.shyouhan.aac.R;
import com.shyouhan.aac.base.BaseActivity;

/**
 * Created by lcy on 2018/7/13.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void initView() {

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                SplashActivity.this.finish();
            }
        },1500);
    }

    @Override
    protected int getLayoutResId() {
        //设置全屏

        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    protected void setTextString() {

    }
}
