package com.shyouhan.aac.http;


import com.shyouhan.aac.bean.BannerBean;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.ExpressBean;
import com.shyouhan.aac.bean.PackStatusBean;
import com.shyouhan.aac.bean.PlaceBean;
import com.shyouhan.aac.bean.RequestParam;

import java.util.List;

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
     * @return
     */
    @GET("logistic/package/search")
    Observable<PackStatusBean> search(@Query("packid") String packid);
    /**
     * 出货
     * @param requestParam
     * @return
     */
    @POST("logistic/package/sending")
    Observable<BaseObject> sending(@Body RequestParam requestParam);
    /**
     * 签收
     * @return
     * 	"packid": "52803",
    "token": "y3tzphThTS"
     */
    @POST("logistic/package/sign")
    Observable<BaseObject> sign(@Body RequestParam requestParam);
    /**
     * 中转快递
     * @param requestParam
     * @return
     */
    @POST("logistic/package/transfer")
    Observable<BaseObject> transfer(@Body RequestParam requestParam);

    /**
     * 获取国内快递名
     * @return
     */
    @GET("logistic/package/getExpress")
    Observable<List<ExpressBean>> getExpress();


    /**
     * 反馈
     * @param requestParam
     * @return
     */
    @POST("app/api/feedback")
    Observable<BaseObject> feedback(@Body RequestParam requestParam);
    /**
     * Banner
     * @return
     */
    @GET("app/api/getbanner")
    Observable<List<BannerBean>> getbanner();
    /**
     * 反馈
     * @return
     */
    @GET("app/api/getplace")
    Observable<List<PlaceBean>> getplace();
}
