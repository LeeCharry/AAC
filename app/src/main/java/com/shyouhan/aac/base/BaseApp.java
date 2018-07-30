package com.shyouhan.aac.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import com.example.tulib.util.http.NetError;
import com.example.tulib.util.http.NetProvider;
import com.example.tulib.util.http.RequestHandler;
import com.example.tulib.util.utils.DataHelper;

import java.io.File;

import com.shyouhan.aac.http.Api;
import com.shyouhan.aac.http.XXApi;
import com.shyouhan.aac.widget.CrashHandler;
import com.shyouhan.aac.widget.LogUtils;
import com.shyouhan.aac.widget.Utils;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by lcy on 2018/6/8.
 */

public class BaseApp extends Application {
    private static BaseApp instance;
    public static final String savePath = Environment.getExternalStorageDirectory().getPath() + "/AAc/aac";

    //    private PersistentCookieStore persistentCookieStore;
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
//        initCcrash();
        //路由初始化
//        if (BuildConfig.DEBUG) {
//            ARouter.openDebug();
//            ARouter.openLog();
//        }
//        ARouter.init(this);
//            initCcrash();
//        CrashHandler.getInstance().init(this);
//
//        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
//        persistentCookieStore = new PersistentCookieStore(this);
        //错误日志
        CrashHandler.getInstance().init(BaseApp.this);
        XXApi.registerProvider(new NetProvider() {
            @Override
            public String configBaseUrl() {
                return Api.getURL();
            }

            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[0];
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }
            @Override
            public CookieJar configCookie() {
                return  null;
            }

            @Override
            public RequestHandler configHandler() {
                return requestHandler;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

            @Override
            public File getFile() {
                return DataHelper.getCacheFile(BaseApp.this);
            }
        });
    }

    private RequestHandler requestHandler = new RequestHandler() {
        @Override
        public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
            RequestBody body = request.body();
            return request;
        }
        @Override
        public Response onAfterRequest(Response response, String result, Interceptor.Chain chain) {
            int code = response.code();
            ResponseBody body = response.body();
            return response;
        }
    };

}
