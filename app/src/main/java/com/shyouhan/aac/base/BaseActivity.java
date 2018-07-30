package com.shyouhan.aac.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tulib.util.base.XActivity;
import com.example.tulib.util.utils.DeviceUtil;
import com.shyouhan.aac.R;
import com.shyouhan.aac.activity.MainActivity;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.widget.SPUtils;


import java.util.Locale;



/**
 * Created by lcy on 2018/4/18.
 */

public abstract class BaseActivity extends XActivity {
    private ImageView ivBack;
    protected TextView tvTitle;
    protected Context mContext;
    private static final String action = "com.shyohan.aac.base.SwitchLanguageBroadcast";
    private SwitchLanguageBroadcast switchLanguageBroadcast;
    protected Dialog dialog;
    protected InputMethodManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setToolbar();
        setImmersiveStatus(BaseActivity.this);

        this.mContext = BaseActivity.this;
        //注册EventBus
//        EventBus.getDefault().register(this);
        registBroadCast();

    }

    private void registBroadCast() {
        IntentFilter intentFilter = new IntentFilter(action);
         switchLanguageBroadcast = new SwitchLanguageBroadcast();
        registerReceiver(switchLanguageBroadcast,intentFilter);
    }

    /**
     * 沉浸式标题栏
     */
    public void setImmersiveStatus(Activity context) {
//        if (context instanceof MainActivity) {
//            //MainActivity 不设置
//            return;
//        }
        Window window = context.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (context instanceof MainActivity) {
                //设置MainActivity的状态栏全透明效果
                //设置透明状态栏,这样才能让 ContentView 向上
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                ViewGroup mContentView = window.getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
                    ViewCompat.setFitsSystemWindows(mChildView, false);
                }

            }else{
                window.setStatusBarColor(context.getResources().getColor(R.color.ltgray));
            }

        }else{
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup systeiew = window.findViewById(android.R.id.content);
            View view = new View(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtil.getStatusHeight(context));
//
            if (context instanceof MainActivity) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                ViewGroup mContentView = window.getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
                View statusBarView = mContentView.getChildAt(0);
                //移除假的 View
                if (statusBarView != null && statusBarView.getLayoutParams() != null &&
                        statusBarView.getLayoutParams().height == getStatusBarHeight()) {
                    mContentView.removeView(statusBarView);
                }
                //不预留空间
                if (mContentView.getChildAt(0) != null) {
                    ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);
                }
            }
            else{
                view.setBackgroundColor(context.getResources().getColor(R.color.ltgray));
            }
            try {
                systeiew.getChildAt(0).setFitsSystemWindows(true);
                systeiew.addView(view,0,layoutParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 更改语言格式
     */
    protected void switchLanguage() {
        String language = SPUtils.getInstance().getString(AppConstant.LANGUAGE_TYPE, AppConstant.zh_TW);
        if (language.equals(AppConstant.zh_TW)) {
            //繁体字
            SPUtils.getInstance().put(AppConstant.LANGUAGE_TYPE, AppConstant.zh);
        } else {
            //简体
            SPUtils.getInstance().put(AppConstant.LANGUAGE_TYPE, AppConstant.zh_TW);
        }
        if (null != switchLanguageBroadcast) {
            //发送广播
            Intent intent = new Intent();
            intent.setAction(action);
            sendBroadcast(intent);
        }
    }
    /**
     * 语言切换 & 语言环境判断
     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBack() {
        Resources resources = getResources();
        String language = SPUtils.getInstance().getString(AppConstant.LANGUAGE_TYPE,"");

        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (TextUtils.isEmpty(language)) {
            //首次进来，根据系统语言设置
            Locale locaLanguage = getLocaLanguage();
            if (locaLanguage.equals(Locale.TRADITIONAL_CHINESE)){
                //繁体字
                config.locale = Locale.TRADITIONAL_CHINESE;
                SPUtils.getInstance().put(AppConstant.LANGUAGE_TYPE,AppConstant.zh_TW);
            }else   if (locaLanguage.equals(Locale.SIMPLIFIED_CHINESE)){
                //简体
                config.locale = Locale.SIMPLIFIED_CHINESE;
                SPUtils.getInstance().put(AppConstant.LANGUAGE_TYPE,AppConstant.zh);
            }
        }else if (language.equals(AppConstant.zh_TW)) {
            //繁体字
            config.locale = Locale.TRADITIONAL_CHINESE;
        } else  {
            //简体
            config.locale = Locale.SIMPLIFIED_CHINESE;
        }
        resources.updateConfiguration(config, dm);
        setTextString();
    }
    protected abstract void setTextString();

    /**
     * 获取系统语言
     * @return
     */
    protected Locale getLocaLanguage() {
        return  getResources().getConfiguration().locale;
    }

    /**
     * 是否是繁体
     * @return
     */
    protected Boolean isTranditional() {
        return getLocaLanguage().equals(Locale.TRADITIONAL_CHINESE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != switchLanguageBroadcast) {
            unregisterReceiver(switchLanguageBroadcast);
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
    }
    /**
     * 公用方法
     * @param message
     * @param callBack
     */
    protected void showdialog(Context context,String message, final CallBack callBack) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_logout_common, null);
        TextView tvMessage = contentView.findViewById(R.id.tv_message);
        tvMessage.setText(message+"");
       dialog = new Dialog(context, R.style.style_logout_dialog);
        // 确定
        contentView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (null != callBack) {
                    callBack.onConfirm();
                }
            }
        });
        //取消
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    /**
     * 多件扫描
     * @param context
     * @param message
     * @param callBack
     */
    protected void showDialogDuo(Context context,String message, final CallBack callBack) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_duojian, null);
        TextView tvMessage = contentView.findViewById(R.id.tv_message);
        tvMessage.setText(message+"");
        dialog = new Dialog(context, R.style.style_logout_dialog);
        // 确认
        contentView.findViewById(R.id.tv_queren).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (null != callBack) {
                    callBack.onSelect(v);
                }
            }
        });
        //确认并继续
        contentView.findViewById(R.id.tv_queren_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (null != callBack) {
                    callBack.onSelect(v);
                }
            }
        });
        //取消
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (null != callBack) {
                    callBack.onSelect(v);
                }
            }
        });
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    /**
     * 单件，多件选择框
     * @param context
     * @param callBack
     */
    protected void showCheckDialog(Context context, final CallBack callBack) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_select, null);
        dialog = new Dialog(context, R.style.style_logout_dialog);
        // 单件
        contentView.findViewById(R.id.rb_dan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (null != callBack) {
                    callBack.onSelect(v);
                }
            }
        });
        //多件
        contentView.findViewById(R.id.rb_duo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (null != callBack) {
                    callBack.onSelect(v);
                }
            }
        });
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }


    public interface CallBack{
        void onConfirm();
        void onSelect(View view);
    }

    /**
     * 字体切换广播
     */
    public class SwitchLanguageBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(action)){
                onBack();
            }
        }
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
