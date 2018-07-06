package com.shyohan.aac.base;

import com.shyohan.aac.http.Api;
import com.shyohan.aac.http.ApiCache;
import com.shyohan.aac.http.ApiService;

/**
 * Created by lcy on 2018/6/8.
 */

public class BaseModel {
    protected ApiService apiService;
    protected ApiCache apiCache;

    public BaseModel() {
        this.apiService = Api.getApiService();
        this.apiCache = Api.getApiCache();
    }
    public void onDestroy() {
        if (apiService != null) {
            apiService = null;
        }
        if (apiCache != null) {
            apiCache = null;
        }
    }
}
