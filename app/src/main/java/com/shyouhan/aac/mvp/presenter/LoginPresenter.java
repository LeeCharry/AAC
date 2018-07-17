package com.shyouhan.aac.mvp.presenter;

import android.content.Context;
import com.example.tulib.util.base.BasePresenter;

import com.shyouhan.aac.R;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.RequestParam;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.http.XXApi;
import com.shyouhan.aac.mvp.contract.LoginContract;
import com.shyouhan.aac.mvp.model.LoginModel;
import com.shyouhan.aac.widget.SPUtils;
import com.shyouhan.aac.widget.ToastUtils;

import java.net.HttpURLConnection;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by lcy on 2018/4/8.
 */

public class LoginPresenter extends BasePresenter<LoginContract.Model,LoginContract.View> {
    public LoginPresenter(Context context, LoginContract.View mRootview) {
        super(new LoginModel(), mRootview,context);
    }
    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }
    /**
     * 登录
     */
    public void login(String username, String password,String deviceId) {
        mRootview.showLoading();
        //判断网络是否连接
        if (!isNetworkConnected()) {
            ToastUtils.showShort(R.string.network_is_unavaliable);
            mRootview.hideLoading();
            return;
        }
        RequestParam requestParam = new RequestParam(username, password, deviceId);
        mMoudle.login(requestParam)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XXApi.<BaseObject>getApiTransformer())
                .compose(XXApi.<BaseObject>getScheduler())
                .compose(bindToLifecycle(mRootview))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject baseObject) {
                        mRootview.hideLoading();
                        if (baseObject.getStatus() == HttpURLConnection.HTTP_OK) {
                            //保存token
                            SPUtils.getInstance().put(AppConstant.TOKEN,baseObject.getToken().toString());
                            SPUtils.getInstance().put(AppConstant.USER_NAME,baseObject.getUsername().toString());
                            SPUtils.getInstance().put(AppConstant.AVATAR,baseObject.getAvatar().toString());
                            //登录状态存入本地
                            SPUtils.getInstance().put(AppConstant.IS_LOGIN,true);
                            ToastUtils.showShort(baseObject.getMsg().toString());
                            mRootview.onLoginSucess();
                        }else{
                            if (isTranditional()) {
                                mRootview.onLoginFailed(baseObject.getTramsg());
                            }else{
                                mRootview.onLoginFailed(baseObject.getMsg());
                            }
                        }
                    }
                });
    }
}