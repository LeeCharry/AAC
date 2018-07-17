package com.example.tulib.util.http;



import com.example.tulib.util.utils.ZipHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by wanglei on 2016/12/24.
 */

public class XInterceptor implements Interceptor {

    RequestHandler handler;

    public XInterceptor(RequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Buffer requestbuffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(requestbuffer);
        } else {
//            Timber.tag("Request").d("request.body() == null");
        }
        if (handler != null) {
            request = handler.onBeforeRequest(request, chain);
        }

//        //打印url信息
//        Timber.tag("Request").d("Sending Request %s on %n Params --->  %s%n Connection ---> %s%n Headers ---> %s", request.url()
//                , request.body() != null ? requestbuffer.readUtf8() : "null"
//                , chain.connection()
//                , request.headers());
        long t1 = System.nanoTime();
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        //打赢响应时间
//        Timber.tag("Response").d("Received response  in %.1fms%n%s", (t2 - t1) / 1e6d, response.headers());

        //读取服务器返回的结果
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        //获取content的压缩类型
        String encoding = response
                .headers()
                .get("Content-Encoding");

        Buffer clone = buffer.clone();
        String bodyString;

        //解析response content
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content使用gzip压缩
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            clone.writeTo(outputStream);
            byte[] bytes = outputStream.toByteArray();
            bodyString = ZipHelper.decompressForGzip(bytes);//解压
            outputStream.close();
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content使用zlib压缩
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            clone.writeTo(outputStream);
            byte[] bytes = outputStream.toByteArray();
            bodyString = ZipHelper.decompressToStringForZlib(bytes);//解压
            outputStream.close();
        } else {//content没有被压缩
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            bodyString = clone.readString(charset);
        }

//        Timber.tag("Result").d(jsonFormat(bodyString));
        if (handler != null) {
            response = handler.onAfterRequest(response, bodyString, chain);
        }
        return response;
    }

    /**
     * json 格式化
     *
     * @param bodyString
     * @return
     */
    public static String jsonFormat(String bodyString) {
        String message;
        try {
            if (bodyString.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(bodyString);
                message = jsonObject.toString(4);
            } else if (bodyString.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(bodyString);
                message = jsonArray.toString(4);
            } else {
                message = bodyString;
            }
        } catch (JSONException e) {
            message = bodyString;
        }
        return message;
    }
}
