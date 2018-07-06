package com.shyohan.aac.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.example.tulib.util.cookie.PersistentCookieStore;
import com.example.tulib.util.http.NetError;
import com.example.tulib.util.http.NetProvider;
import com.example.tulib.util.http.RequestHandler;
import com.example.tulib.util.utils.DataHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.shyohan.aac.http.Api;
import com.shyohan.aac.http.XXApi;
import com.shyohan.aac.widget.CrashHandler;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
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
    final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private PersistentCookieStore persistentCookieStore;
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
        CrashHandler.getInstance().init(this);
//
//        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        persistentCookieStore = new PersistentCookieStore(this);

//};

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
//                return  null;
                return new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                        for (Cookie cookie : cookies) {
//                            Log.d("BaseApplication", "cookie " + cookie.toString());
//                            LogUtils.a("lcy", cookie.toString());
//                        }
//                        cookieStore.put(url.host(), cookies);
                        for (Cookie cookie:cookies) {
                            persistentCookieStore.add(url,cookie);
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
//                        LogUtils.a("lcy", url.toString());
//                        List<Cookie> cookies = cookieStore.get(url);
//                        return cookies != null ? cookies : new ArrayList<Cookie>();
                        List<Cookie> cookies = persistentCookieStore.get(url);
                        for (int i = 0; i < cookies.size(); i++) {
//                            Log.d(this.get, "loadForRequest: "+cookies.get(i).toString());
                        }
                       return persistentCookieStore.get(url);
                    }
                };
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initCcrash() {
        LogUtils.a("lcy", Environment.getExternalStorageDirectory().toString());
        LogUtils.a("lcy",getExternalCacheDir().toString());

        String crashPath = Environment.getExternalStorageDirectory() + File.separator + "crash.txt";
        File file = new File(crashPath);
        if (file.exists()) {
            file.delete();
//        }
        try {
            file.createNewFile();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }
//            CrashUtils.init(file);
            CrashUtils.init();
//            LogUtils.a("lcy",init+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
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
//            LogUtils.a("lcy",code);
            ResponseBody body = response.body();
            return response;
        }
    };

    public static BaseApp getAppInstance() {
        if (instance == null){
           instance = new BaseApp();
        }
        return instance;
    }
}
