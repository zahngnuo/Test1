package com.wumart.lib.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.wumart.lib.R;
import com.wumart.lib.bean.DownLoadBean;
import com.wumart.lib.common.CommonUtils;

import java.io.File;

/**
 * User: 吕勇
 * Date: 2016-05-11
 * Time: 10:03
 * Description:通用的文件下载(目前没有回调，有需要可以做)
 * 用法如下：
 * Intent intent = new Intent(this, WuDownLoadService.class);
 * DownLoadBean loadBean=new DownLoadBean();
 * loadBean.setIcon(R.drawable.ic_launcher);
 * loadBean.setFileName("wpde.jpg");
 * loadBean.setFilePath(WmHelperAplication.getInstance().getFileBasePath());
 * loadBean.setUrl("http://img05.tooopen.com/images/20140604/sy_62331342149.jpg");
 * intent.putExtra(WuDownLoadService.DOWN_PARAM, loadBean);
 * startService(intent);
 */
public class WuDownLoadService extends Service {
    MultiResumeDownTask multiResumeDownTask;
    public static final int NOTIFY_ID = 0X15;
    public static final String DOWN_PARAM = "DOWN_PARAM";
    private int progress;
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private boolean statusNow = true, isApk;
    private String saveFileName;
    private DownLoadBean mLoadBean;
    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && TextUtils.equals("WuDownLoadService", intent.getAction())) {
                stopSelf();
                return;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            statusNow = (activeNetInfo != null);
            handler.sendEmptyMessage(10000);
        }
    };


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (0 == msg.what) {
                resumService();
            } else if (10000 == msg.what) {
                if (statusNow) {
                    if (!multiResumeDownTask.isDownloading()) {
                        multiResumeDownTask.startDownload();
                    }
                } else {
                    multiResumeDownTask.resumeDownload();
                }
            } else {
                if (progress < 100) {
                    RemoteViews contentview = mNotification.contentView;
                    contentview.setTextViewText(R.id.notif_progress, progress + "%");
                    contentview.setProgressBar(R.id.notif_progressbar, 100, progress, false);
                    mNotificationManager.notify(NOTIFY_ID, mNotification);
                } else {
                    loadOver();
                }

            }
        }
    };

    private void resumService() {
        if (!multiResumeDownTask.isDownloading()) {
            multiResumeDownTask.startDownload();
        } else {
            multiResumeDownTask.resumeDownload();
        }
    }

    private void loadOver() {
        mNotificationManager.cancel(NOTIFY_ID);
        installApk();
        mLoadBean = null;
        stopSelf();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(networkReceiver);
        super.onDestroy();
    }

    private void initTask() {
        multiResumeDownTask = new MultiResumeDownTask(
                mLoadBean, new MultiResumeDownTask.OnDownLoadStateListener() {
            @Override
            public void OnDownLoadProcessChange(final int process) {
                progress = process;
                handler.sendEmptyMessage(100);
            }

            @Override
            public void OnDownLoadFinished() {
                loadOver();
            }
        });
        if (CommonUtils.isNetworkAvailable(getApplicationContext()))
            handler.sendEmptyMessage(0);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        progress = 0;
        super.onCreate();
    }

    /**
     * 创建通知
     */
    private void setUpNotification() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplication(), 100, new Intent("WuDownLoadService"), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication());
        RemoteViews contentView = new RemoteViews(getApplication().getPackageName(), R.layout.download_notification_layout);
        contentView.setTextViewText(R.id.notif_name, " 正在下载,请稍候...");
        contentView.setOnClickPendingIntent(R.id.notif_layout, pendingIntent);
        contentView.setImageViewResource(R.id.notif_image, mLoadBean.getIcon());
        builder.setDefaults(Notification.DEFAULT_LIGHTS)
                .setContent(contentView)
                .setWhen(System.currentTimeMillis())
                .setTicker("开始下载")
                .setAutoCancel(true)
                .setSmallIcon(mLoadBean.getIcon());
        mNotification = builder.build();
        mNotification.contentView = contentView;
        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filter = new IntentFilter();//创建IntentFilter对象
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("WuDownLoadService");
        registerReceiver(networkReceiver, filter);//注册BroadcastReceiver
        mLoadBean = intent.getParcelableExtra(DOWN_PARAM);
        if (mLoadBean == null) {
            if (null != mNotificationManager)
                mNotificationManager.cancel(NOTIFY_ID);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
        setUpNotification();
        saveFileName = mLoadBean.getFilePath() + mLoadBean.getFileName();
        isApk = mLoadBean.getFileName().endsWith(".apk");
        initTask();
        startForeground(NOTIFY_ID, mNotification);
        return START_STICKY;
    }

    private void installApk() {
        if (!isApk)
            return;
        File apkfile = new File(saveFileName);
        if (!apkfile.exists())
            return;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        getApplication().startActivity(i);
    }

}
