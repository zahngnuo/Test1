package com.wumart.lib.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.orhanobut.hawk.Hawk;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * User: 吕勇
 * Date: 2016-04-28
 * Time: 16:38
 * Description:网络请求返回接口
 */
public class HttpCallBack<T> extends Callback<T> {
    public static final String FORM_HTTPCALLBACK = "FORM_HTTPCALLBACK";//用来处理重复登陆
    protected WeakReference<HttpInterface> mWeakReference;
    protected boolean mShowLoadingView;
    protected boolean mShowToast;
    protected boolean success;
    private Type mType;

    public Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) return null;
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public HttpCallBack(HttpInterface httpInterface) {
        this(httpInterface, true, true);
    }

    public HttpCallBack(HttpInterface httpInterface, boolean showLoadingView) {
        this(httpInterface, showLoadingView, true);
    }

    public HttpCallBack(HttpInterface httpInterface, boolean showLoadingView, boolean showToast) {
        mWeakReference = new WeakReference<>(httpInterface);
        mShowLoadingView = showLoadingView;
        mShowToast = showToast;
    }

    @Override
    public void onBefore(Request request, int id) {
        HttpInterface httpInterface = mWeakReference.get();
        if (httpInterface != null && mShowLoadingView)
            httpInterface.showLoadingView();
    }

    @Override
    public void onAfter(int id) {
        HttpInterface httpInterface = mWeakReference.get();
        if (httpInterface != null && mShowLoadingView)
            httpInterface.hideLoadingView();
        onFinish();
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        mType = getSuperclassTypeParameter(getClass());
        if (mType == null || mType.toString().equals(Void.class))
            return null;
        return new Gson().fromJson(response.body().string(), mType);
    }


    @Override
    public void onError(Call call, Exception e, int id) {
        String resultCode = "0";
        if (null != call && !call.isCanceled())
            call.cancel();
        success = false;
        String error = "网络异常，请稍候再试！";
        if (e instanceof SocketTimeoutException) {
            error = "连接服务器超时，请稍候再试！";
        } else if (e instanceof ProtocolInterceptor.WuIOException) {
            ProtocolInterceptor.WuIOException ioException = (ProtocolInterceptor.WuIOException) e;
            error = e.getMessage();
            resultCode = ioException.mCode;
            if (ioException.mRepetition) {
                error = "";
                loginOut(e.getMessage());
            }
        }
        if (TextUtils.equals("Canceled", e.getMessage()))
            error = "";
        showError(resultCode, e.getMessage());
    }

    private void loginOut(String message) {
        HttpInterface httpInterface = mWeakReference.get();
        if (httpInterface != null) {
            Hawk.put(FORM_HTTPCALLBACK, true);
            httpInterface.notifyDialog(message);
        }
    }

    @Override
    public void onResponse(T response, int id) {
        success = true;
        if (response != null)
            onSuccess(response);
        onSuccessWithNull(response);
    }


    public HttpInterface getHttpInterface() {
        return mWeakReference.get();
    }

    protected void showError(String msg, String resultCode) {
        if (!TextUtils.isEmpty(msg) && "200".equals(resultCode)) {
            onError(resultCode, msg);
        }
        onError(resultCode);
    }

    /**
     * 网络发生错误
     */
    protected void onError(String resultCode, String msg) {
    }

    /**
     * 网络发生错误
     */
    protected void onError(String resultCode) {
    }

    /**
     * 网络请求成功并且数据不为空
     */
    protected void onSuccess(T t) {
    }

    /**
     * 网络请求成功并且数据为空
     */
    protected void onSuccessWithNull(T t) {
    }

    /**
     * 网络请求结束
     */
    public void onFinish() {
    }
}
