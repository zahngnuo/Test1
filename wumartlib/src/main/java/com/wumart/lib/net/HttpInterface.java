package com.wumart.lib.net;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * User: 吕勇
 * Date: 2016-03-17
 * Time: 09:38
 * Description:显示加载框的接口
 */
public interface HttpInterface {

    void showLoadingView();

    void hideLoadingView();

    void showSuccessToast(@NonNull String message);

    void showFailToast(@NonNull String message);

    void notifyDialog(@NonNull String message);

    Activity getActivity();

}