package com.shyouhan.aac.http;

/**
 * Created by lcy on 2018/4/8.
 */

public class Api {
    private static ApiService apiService;
    private static ApiCache apiCache;

    public static String getURL(){
        return "http://aac.shyouhan.com/";
    }
    public static ApiService getApiService() {
        synchronized (Api.class){
            apiService = XXApi.getInstance().getRetrofit(true).create(ApiService.class);
        }
        return apiService;
    }
    public static ApiCache getApiCache() {
        if (null == apiCache) {
            synchronized (Api.class){
                if (null == apiCache) {
                    apiCache = XXApi.getInstance().getRxCache().using(ApiCache.class);
                }
            }
        }
        return apiCache;
    }
}
