package com.shyohan.aac;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;

import com.shyohan.aac.bean.UserBean;
import com.shyohan.aac.constant.AppConstant;

/**
 * Created by lcy on 2018/4/19.
 */

/**
 * 用户对象管理类
 */
public class UserManager {
    /**
     * 保存userbean
     * @param data
     */
    public static void saveUserBean(UserBean data) {
        if (null == data) {
            new NullPointerException("userbean cann't be null");
            return;
        }
        Gson gson = new Gson();
        getSpUtils().put(AppConstant.USERBEAN,gson.toJson(data).toString());
    }
    /**
     * 获取userbean
     * @return
     */
    public static UserBean getUserBean() {
        String data = getSpUtils().getString(AppConstant.USERBEAN).toString();
        if (TextUtils.isEmpty(data)){
            ToastUtils.showShort("本地用户数据为空");
            return null;
        }
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(data, UserBean.class);
        return userBean;
    }

    private static SPUtils getSpUtils() {
        return SPUtils.getInstance(AppConstant.SP_FILE_NAME);
    }

    /**
     * 获取当前登录状态
     * @return
     */
    public static Boolean isLogin() {
        return   getSpUtils().getBoolean(AppConstant.LOGIN_FLAG);
    }

    /**
     * 设置登陆状态
     * @param b
     */
    public static void setLogin(boolean b) {
        getSpUtils().put(AppConstant.LOGIN_FLAG,b);
    }
}
