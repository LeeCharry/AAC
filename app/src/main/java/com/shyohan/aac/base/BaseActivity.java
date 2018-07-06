package com.shyohan.aac.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.tulib.util.base.XActivity;
import com.example.tulib.util.utils.DeviceUtil;
import com.example.tulib.util.utils.Util;
import com.shyohan.aac.activity.MainActivity;
import com.shyohan.aac.constant.AppConstant;
import com.shyohan.aac.google.zxing.activity.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import acc.tulip.com.accreputation.R;

/**
 * Created by lcy on 2018/4/18.
 */

public abstract class BaseActivity extends XActivity {
    private ImageView ivBack;
    protected TextView tvTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setToolbar();
        setImmersiveStatus(BaseActivity.this);

        //注册EventBus
        EventBus.getDefault().register(this);

    }
    /**
     * 沉浸式标题栏
     */
    public static void setImmersiveStatus(Activity context) {
        if (context instanceof MainActivity) {
            //MainActivity 不设置
            return;
        }
        Window window = context.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(context.getResources().getColor(R.color.colorPrimary));
//            if (context instanceof MainActivity) {
//                window.setStatusBarColor(context.getResources().getColor(R.color.colorPrimary));
//            }else{
//            }
            window.setStatusBarColor(context.getResources().getColor(R.color.ltgray));

        }else{
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup systeiew = window.findViewById(android.R.id.content);
            View view = new View(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtil.getStatusHeight(context));
//
//            if (context instanceof MainActivity) {
//                view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
//            }
//            else{
//            }
            view.setBackgroundColor(context.getResources().getColor(R.color.ltgray));
            systeiew.getChildAt(0).setFitsSystemWindows(true);
            systeiew.addView(view,0,layoutParams);
        }
    }

    /**
     * 更改语言格式
     */
    protected void switchLanguage() {
        String language = SPUtils.getInstance().getString(AppConstant.LANGUAGE_TYPE,getlocaLanguage(getResources()));
        if (language.equals(AppConstant.zh_TW)) {
            //繁体字
            SPUtils.getInstance().put(AppConstant.LANGUAGE_TYPE, AppConstant.zh);
        } else {
            //简体
            SPUtils.getInstance().put(AppConstant.LANGUAGE_TYPE, AppConstant.zh_TW);
        }
        EventBus.getDefault().post("");
    }

    /**
     * 语言切换 & 语言环境判断
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBack(Object msg) {
        Resources resources = getResources();
        String language = SPUtils.getInstance().getString(AppConstant.LANGUAGE_TYPE, getlocaLanguage(resources));

        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (language == AppConstant.zh_TW) {
            //繁体字
            config.locale = Locale.TRADITIONAL_CHINESE;
        } else {
            //简体
            config.locale = Locale.SIMPLIFIED_CHINESE;
        }
        resources.updateConfiguration(config, dm);
        setTextString();
    }
    protected abstract void setTextString();

    /**
     * 获取系统语言环境
     * @param resources
     */
    protected String getlocaLanguage(Resources resources) {
        Locale locale = resources.getConfiguration().locale;
        return locale.getLanguage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected void iniTitlelayout() {
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        //返回
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finish();
            }
        });
        //title
    }

    private void setToolbar() {
//      toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if (null == toolbar) {
//            new NullPointerException("toolbar can't be null!");
//        }
//      setSupportActionBar(toolbar);
//      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        /**toolbar除掉阴影*/
//        getSupportActionBar().setElevation(0);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            toolbar.setElevation(0);
//        }
    }
}
