package com.wumart.lib.net;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * User: 吕勇
 * Date: 2016-04-29
 * Time: 09:15
 * Description:网络拦截
 */
public class ProtocolInterceptor implements Interceptor {
    public static final String RESPONSE_SUCCESS = "100";//向服务器请求返回成功
    public static final String RESPONSE_REPETITION = "201";//其他设备登录
    public static final String RESPONSE_BUSINESS_ERROR = "202";//合同业务逻辑错误

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        MediaType mediaType = MediaType.parse("application/json; chartset='utf-8'");
        String data = parseDataFromBody(response.body().string());
        return response.newBuilder().body(ResponseBody.create(mediaType, data)).build();
    }

    private String parseDataFromBody(String body) throws IOException {
        JSONObject json;
        try {
            json = new JSONObject(body);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new WuIOException("服务器异常，请稍后重试");
        }
        if(TextUtils.equals(RESPONSE_SUCCESS,json.optString("code")))
            return json.optString("data");
        throw new WuIOException(json.optString("msg"),json.optString("code"));
    }

    public class WuIOException extends IOException {
        boolean mRepetition;
        String mCode;
        public WuIOException(String detailMessage,String code) {
            super(detailMessage);
            this.mCode=code;
            this.mRepetition=TextUtils.equals(RESPONSE_REPETITION,code);
        }
        public WuIOException(String detailMessage) {
            super(detailMessage);
        }
    }
}
