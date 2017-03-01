package com.wumart.lib.net;

/**
 * User: 吕勇
 * Date: 2016-03-30
 * Time: 14:38
 * Description:网络监听回调
 */
public interface NetworkStatusListener {
    /**
     * 网络回调
     * @param status true有网
     */
    void onStateChange(boolean status);
}
