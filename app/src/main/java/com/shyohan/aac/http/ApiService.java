package com.shyohan.aac.http;


import com.shyohan.aac.bean.BaseObject;
import com.shyohan.aac.bean.RequestParam;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lcy on 2018/4/8.
 */

public interface ApiService {
    /**
     * 登录
     * @return
     */
    @POST("user/user/login")
    Observable<BaseObject> login(@Body RequestParam requestParam);

    /**
     * 抵达站所
     * @param requestParam
     * @return
     */
    @POST("logistic/package/arriveplace")
    Observable<BaseObject> arriveplace(@Body RequestParam requestParam);

    /**
     * 派送
     * @param requestParam
     * @return
     */
    @POST("logistic/package/delivery")
    Observable<BaseObject> delivery(@Body RequestParam requestParam);
    /**
     * 揽件
     * @param requestParam
     * @return
     */
    @POST("logistic/package/lanjian")
    Observable<BaseObject> lanjian(@Body RequestParam requestParam);
    /**
     * 查询订单
     * @param requestParam
     * @return
     */
    @GET("logistic/package/search")
    Observable<BaseObject> search(@Body RequestParam requestParam);
    /**
     * 出货
     * @param requestParam
     * @return
     */
    @POST("logistic/package/sending")
    Observable<BaseObject> sending(@Body RequestParam requestParam);
    /**
     * 签收
     * @param requestParam
     * @return
     */
    @GET("logistic/package/sign")
    Observable<BaseObject> sign(@Body RequestParam requestParam);
    /**
     * 中转快递
     * @param requestParam
     * @return
     */
    @POST("logistic/package/transfer")
    Observable<BaseObject> transfer(@Body RequestParam requestParam);

}
