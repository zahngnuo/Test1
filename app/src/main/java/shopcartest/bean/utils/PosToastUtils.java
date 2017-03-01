package shopcartest.bean.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wumart.lib.common.StrUtils;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */

public class PosToastUtils {
    private static Context mContext;
    private volatile static PosToastUtils mInstance;
    private static Toast mToast;
    private View layout;
    private TextView tv;
    private TextView tv1;

    public PosToastUtils(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 单例模式
     *
     * @param context 传入的上下文
     * @return TabToast实例
     */
    private static PosToastUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (PosToastUtils.class) {
                if (mInstance == null) {
                    mInstance = new PosToastUtils(context);
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

    public static void textNullToast(Context context, String text, String text1) {
        if (!StrUtils.isEmpty(text))
            textNullToast(context, text, text1, Toast.LENGTH_SHORT);
    }

    /**
     * 没有图案的Toast
     *
     * @param context
     * @param text
     * @param duration
     */
    public static void textNullToast(Context context, String text, String text1, int duration) {
        getInstance(context);
        getToast(duration);
        if (mInstance.layout == null || mInstance.tv == null) {
            mInstance.layout = LayoutInflater.from(mContext).inflate(com.wumart.lib.R.layout.toast_null_layout, null);
            mInstance.tv = (TextView) mInstance.layout.findViewById(com.wumart.lib.R.id.toast_text);
            mInstance.tv1 = (TextView) mInstance.layout.findViewById(com.wumart.lib.R.id.toast_text1);
            mToast.setView(mInstance.layout);
        }
        mInstance.tv.setText(text);
        mInstance.tv1.setText(text1);
        mToast.show();
    }
}
