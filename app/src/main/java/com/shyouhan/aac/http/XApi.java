package com.shyouhan.aac.http;



import com.example.tulib.util.http.NetProvider;
import com.example.tulib.util.http.RequestHandler;
import com.example.tulib.util.http.XInterceptor;
import com.example.tulib.util.kit.Kits;

import java.io.File;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wanglei on 2016/12/24.
 */

public class XApi {
    private static NetProvider provider = null;
    private Retrofit retrofit = null;
    private OkHttpClient client = null;
//    private RxCache rxCache = null;

    public static final long connectTimeoutMills = 10 * 1000L;
    public static final long readTimeoutMills = 10 * 1000L;
    public static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;//缓存文件最大值为10Mb

    private static XApi instance;

    private XApi() {

    }

    public static XApi getInstance() {
        if (instance == null) {
            synchronized (XApi.class) {
                if (instance == null) {
                    instance = new XApi();
                }
            }
        }
        return instance;
    }


    public static <S> S get(Class<S> service) {
        return getInstance().getRetrofit(true).create(service);
    }

    public static void registerProvider(NetProvider provider) {
        XApi.provider = provider;
    }


    public Retrofit getRetrofit(boolean useRx) {
        checkProvider();

        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(provider.configBaseUrl())
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create());
            if (useRx) {
                builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            }
            retrofit = builder.build();
        }
        return retrofit;
    }

    public OkHttpClient getClient() {
        checkProvider();

        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.connectTimeout(provider.configConnectTimeoutMills() != 0
                    ? provider.configConnectTimeoutMills()
                    : connectTimeoutMills, TimeUnit.MILLISECONDS);
            builder.readTimeout(provider.configReadTimeoutMills() != 0
                    ? provider.configReadTimeoutMills() : readTimeoutMills, TimeUnit.MILLISECONDS);

            File cacheFile = provider.getFile();
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 10); //100Mb
            builder.cache(cache);

            CookieJar cookieJar = provider.configCookie();
            if (cookieJar != null) {
                builder.cookieJar(cookieJar);
            }
            provider.configHttps(builder);

            RequestHandler handler = provider.configHandler();
            if (handler != null) {
                builder.addNetworkInterceptor(new XInterceptor(handler));
            }

            Interceptor[] interceptors = provider.configInterceptors();
            if (!Kits.Empty.check(interceptors)) {
                for (Interceptor interceptor : interceptors) {
                    builder.addInterceptor(interceptor);
                }
            }

            if (provider.configLogEnable()) {
//                HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
//                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                builder.addInterceptor(logInterceptor);
            }

            client = builder.build();
        }
        return client;
    }

    private static void checkProvider() {
        if (provider == null
                || Kits.Empty.check(provider.configBaseUrl())) {
            throw new IllegalStateException("must register provider first");
        }
    }

    public static NetProvider getProvider() {
        return provider;
    }

    /**
     * 线程切换
     *
     * @return
     */
    public static <T > Observable.Transformer<T, ? extends T> getScheduler() {
        Observable.Transformer<T, ? extends T> transformer = new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
        return transformer;
    }

    /**
     * 异常处理变换
     *
     * @return
     */
    public static <T > Observable.Transformer<T, ? extends T> getApiTransformer() {
        Observable.Transformer<T, ? extends T> transformer = new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {

                return observable.flatMap(new Func1<T, Observable<T>>() {
                    @Override
                    public Observable<T> call(T model) {

                            return Observable.just(model);

                    }

                });
            }
        };
        return transformer;
    }


}
