package baiduanimation;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;

import com.orhanobut.hawk.BuildConfig;
import com.orhanobut.hawk.Hawk;
import com.wumart.lib.common.CommonUtils;
import com.wumart.lib.common.ToastUtils;
import com.wumart.lib.net.HttpUtil;
import com.wumart.lib.net.NetworkStatusListener;
import com.wumart.lib.net.TokenInterceptor;

import org.xutils.x;

import java.io.File;
import java.util.ArrayList;


/**
 * desc.移动POSApplication
 * name: sunshine
 * data: 2016/9/19 0019 11:40
 */
public class TestPosAplication extends Application {
    private static final String BASE_FILE_PATH = "WMApp";
    private static TestPosAplication WmHelperAppInstance;
    private BroadcastReceiver networkReceiver;
    private String fileBasePath;
    private ArrayList<NetworkStatusListener> networkStatusListenerList;
    private String authJsonStr;

    @Override
    public void onCreate() {
        super.onCreate();
        if (isRemoteProcess()) {
            return;
        }
        WmHelperAppInstance = this;
        Hawk.init(this).build();//初始化Hawk

        fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + BASE_FILE_PATH + File.separator;
        //initJPush();
        initHttp();
        initNetworkStatusReceiver();
        //xutils
        x.Ext.init(this); //初始化Xutils
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
    }

    public String obtainAuthJson() {
        return authJsonStr;
    }

    private void initHttp() {
        HttpUtil.initHttpClient(new TokenInterceptor() {
            @Override
            public String obtainAuthInfoStr() {
                return obtainAuthJson();
            }
        });
    }

    public static synchronized TestPosAplication getInstance() {
        if (WmHelperAppInstance == null)
            WmHelperAppInstance = new TestPosAplication();
        return WmHelperAppInstance;
    }

    /**
     * 监听网络状态
     */
    private void initNetworkStatusReceiver() {
        registerReceiver(networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetworkAvailable;
                if (!(isNetworkAvailable = CommonUtils.isNetworkAvailable(WmHelperAppInstance)))
                    ToastUtils.textToastError(getInstance(), "网络断开，请检查！");
                if (networkStatusListenerList != null && networkStatusListenerList.size() > 0) {
                    for (NetworkStatusListener networkStatusListener : networkStatusListenerList) {
                        networkStatusListener.onStateChange(isNetworkAvailable);
                    }
                }

            }
        }, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    /**
     * 低内存时执行
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    /**
     * 系统终止时执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(networkReceiver);
        networkReceiver = null;
    }

    private boolean isRemoteProcess() {
        return getApplicationInfo().processName.endsWith(":remote");
    }



    public String getFileBasePath() {
        return fileBasePath;
    }


}
