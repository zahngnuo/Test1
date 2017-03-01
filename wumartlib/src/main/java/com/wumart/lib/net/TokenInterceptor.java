package com.wumart.lib.net;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
* User: 吕勇
* Date: 2016-07-11
* Time: 09:03
* Description:
*/
public abstract class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if(obtainAuthInfoStr()!=null)
            request = request.newBuilder().addHeader("authInfo", obtainAuthInfoStr()).build();
        Response response = chain.proceed(request);
        return response;
    }


         public abstract String obtainAuthInfoStr();

}
