package com.wumart.lib.common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.orhanobut.hawk.Hawk;

import java.security.MessageDigest;
import java.util.Locale;

/**
 * @author andy he
 * @ClassName: CommonUtils
 * @Description: 通用、不好归类的工具
 * @date 2016年1月15日 上午10:18:53
 */
public class CommonUtils {
    //网络类型
    public static final int NETWORK_NONE = 0;//无连接
    public static final int NETWORK_OTHER = 1;//其它：非4g,非wifi
    public static final int NETWORK_4G = 4;//4g
    public static final int NETWORK_WIFI = 9;//wifi

    /**
     * desc: 网络是否可用
     *
     * @author andy he
     * @time 2016/5/4 12:49
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null)
                return mNetworkInfo.isAvailable();
        }
        return false;

    }

    /**
     * desc: 网络类型（4G/WIFI/其它）
     *
     * @author andy he
     * @time 2016/5/4 12:49
     */
    public static int GetNetworkType(Context context) {
        int netType = NETWORK_NONE;//无网络
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    netType = NETWORK_WIFI;
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    int networkType = networkInfo.getSubtype();
                    switch (networkType) {
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            netType = NETWORK_4G;
                            break;
                        default:
                            netType = NETWORK_OTHER;
                            break;
                    }
                }
            }
        }
        return netType;
    }


    /**
     * 获取版本名称
     */
    public static String versionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取版本号
     */
    public static int versionCode(Context context) {
        int currentVersionCode = 1;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersionCode;
    }

    /**
     * 显示键盘
     *
     * @param context 内容上下文
     */
    public static void showKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏键盘
     *
     * @param view 控件
     */
    public static void hiddenKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    /**
     * 检查SDK是否存在
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取屏幕
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * 像素转化
     */
    public static float dp2px(Context context, float dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * 像素转化
     */
    public static float px2dp(Context context, float pxVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxVal, context.getResources().getDisplayMetrics());
    }

    public static String getUuid(Context context) {
        String m_szUniqueID = Hawk.get("uuid_key", "");
        if (StrUtils.isNotEmpty(m_szUniqueID)) {
            return m_szUniqueID;
        } else {
            try {
                TelephonyManager e = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String m_szImei = e.getDeviceId();
                String serialNumber = Build.SERIAL;
                String m_szLongID = m_szImei + (StrUtils.isEmpty(serialNumber) ? "" : serialNumber);
                MessageDigest m = MessageDigest.getInstance("MD5");
                m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
                byte[] p_md5Data = m.digest();

                for (int i = 0; i < p_md5Data.length; ++i) {
                    int b = 255 & p_md5Data[i];
                    if (b <= 15) {
                        m_szUniqueID = m_szUniqueID + "0";
                    }

                    m_szUniqueID = m_szUniqueID + Integer.toHexString(b);
                }

                m_szUniqueID = m_szUniqueID.toUpperCase(Locale.CHINESE);
            } catch (Exception var10) {
                m_szUniqueID = "";
            }

            Hawk.put("uuid_key", m_szUniqueID);
            return m_szUniqueID;
        }
    }

    /**
     * desc: 摄像头是否可用(针对6.0 以前的系统版本)
     *
     * @author andy he
     * @time 2016/6/29 15:58
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            //当拒绝相机使用权限时，魅族OS Flyme 5.x 通过Camera.open()不会抛出异常，且拿到的Camera对象并不为null，
            //利用setParameters来捕获异常，判断相机是否可用。
            mCamera.setParameters(mCamera.getParameters());
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }
}
