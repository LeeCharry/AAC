package com.example.tulib.util.base;

import android.content.Intent;

/**
 * Created by lcy on 2018/4/8.
 */

public interface BaseView {
   void showLoading();
   void hideLoading();
   void showMessage(String msg);
   void launchActivity(Intent intent);
   void killMySelf();
}
