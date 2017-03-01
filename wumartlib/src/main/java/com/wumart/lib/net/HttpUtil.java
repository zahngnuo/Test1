package com.wumart.lib.net;


import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * User: 吕勇
 * Date: 2016-04-29
 * Time: 10:49
 * Description:网络请求类(后台每个请求有固定的两个参数，故设计如此，后期变化可更改)
 */
public class HttpUtil {

    public static final int OUT_TIME = 30;

    /**
     * 不带参数的网络请求
     *
     * @param url      URL地址
     * @param callBack 回调
     */
    public static void http(String url, HttpCallBack callBack) {
        http(url, new ArrayMap<String, String>(), callBack);
    }

    /**
     * 带参数的网络请求
     *
     * @param url        URL地址
     * @param jsonParams 参数
     * @param callBack   回调
     */
    public static void http(String url, String jsonParams, HttpCallBack callBack) {
        Object tag = "Okhttp";
        if (null != callBack.getHttpInterface())
            tag = callBack.getHttpInterface();
        OkHttpUtils
                .postString()
                .url(url)
                //.addHeader("authInfo", authJson())
                .content(jsonParams)
                .tag(tag)
                .build()
                .execute(callBack);
    }


    /**
     * 带参数的网络请求
     *
     * @param url      URL地址
     * @param params   参数
     * @param callBack 回调
     */
    public static void http(String url, Map<String, ?> params, HttpCallBack callBack) {
        Object tag = "Okhttp";
        if (null == params)
            params = new ArrayMap<>();
        if (null != callBack.getHttpInterface())
            tag = callBack.getHttpInterface();
        OkHttpUtils
                .postString()
                .url(url)
                //.addHeader("authInfo", authJson())
                .content(new Gson().toJson(params))
                .tag(tag)
                .build()
                .execute(callBack);
    }

    /**
     * 初始化网络请求
     *
     * @param tokenInterceptor tokenInterceptor
     */
    public static void initHttpClient(TokenInterceptor tokenInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        builder.connectTimeout(OUT_TIME, TimeUnit.SECONDS)
                .writeTimeout(OUT_TIME, TimeUnit.SECONDS)
                .readTimeout(OUT_TIME, TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .addInterceptor(new ProtocolInterceptor())
                .addNetworkInterceptor(tokenInterceptor);
        OkHttpUtils.initClient(builder.build());
    }

}
