package com.wumart.lib.common;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wumart.lib.R;

/**
 * 自定义Toast.
 */
public class ToastUtils {
    private static Context mContext;
    private volatile static ToastUtils mInstance;
    private static Toast mToast;
    private View layout;
    private TextView tv;
    private ImageView mImageView;

    public ToastUtils(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 单例模式
     *
     * @param context 传入的上下文
     * @return TabToast实例
     */
    private static ToastUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ToastUtils.class) {
                if (mInstance == null) {
                    mInstance = new ToastUtils(context);
                }
            }
        }
        return mInstance;
    }


    private static void getToast(int duration) {
        if (mToast == null) {
            mToast = new Toast(mContext);
            mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            mToast.setDuration(duration);
        }
    }

    public static void textToast(Context context, String text) {
        if (!StrUtils.isEmpty(text))
            textToast(context, text, 0, Toast.LENGTH_SHORT);
    }

    public static void textToastError(Context context, String text) {
        textToast(context, text, R.drawable.toast_error);
    }

    public static void textToast(Context context, String text, int imageId) {
        if (!StrUtils.isEmpty(text))
            textToast(context, text, imageId, Toast.LENGTH_SHORT);
    }

    public static void textToast(Context context, String text, int resId, int duration) {
        getInstance(context);
        getToast(duration);
        if (mInstance.layout == null || mInstance.tv == null || mInstance.mImageView == null) {
            mInstance.layout = LayoutInflater.from(mContext).inflate(R.layout.toast_layout, null);
            mInstance.tv = (TextView) mInstance.layout.findViewById(R.id.toast_text);
            mInstance.mImageView = (ImageView) mInstance.layout.findViewById(R.id.toast_image);
            mToast.setView(mInstance.layout);
        }
        mInstance.tv.setText(text);
        if (resId == 0)
            resId = R.drawable.toast_success;
        mInstance.mImageView.setImageResource(resId);
        mToast.show();
    }
}
