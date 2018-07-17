package com.shyouhan.aac.base;

import com.shyouhan.aac.http.Api;
import com.shyouhan.aac.http.ApiCache;
import com.shyouhan.aac.http.ApiService;

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
