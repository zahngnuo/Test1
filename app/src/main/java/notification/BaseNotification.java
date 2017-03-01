package notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import baiduanimation.R;
import shopcartest.bean.utils.BaseActivity;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */

public class BaseNotification extends BaseActivity {
    private Button button0, button1, button2, button3, button4, button5, button6;

    //通知管理器
    private NotificationManager nm;
    //通知显示内容
    private PendingIntent pd;


    @Override
    protected int loadLayoutId() {
        return R.layout.act_base_noti_fication;
    }

    @Override
    protected void initViews() {
        button0 = $(R.id.BaseNotification);
        button1 = $(R.id.UpdateBaseNotification);
        button2 = $(R.id.ClearBaseNotification);
        button3 = $(R.id.MediaNotification);
        button4 = $(R.id.ClearALL);
        button5 = $(R.id.CustomNotification);
        button6 = $(R.id.ClearMediaNotification);
    }


    @Override
    protected void initData() {
        setTitleStr("通知栏信息");
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, BaseNotification.class);
        pd = PendingIntent.getActivity(BaseNotification.this, 0, intent, 0);
        button0.setOnClickListener(onclick);
        button1.setOnClickListener(onclick);
        button2.setOnClickListener(onclick);
        button3.setOnClickListener(onclick);
        button4.setOnClickListener(onclick);
        button5.setOnClickListener(onclick);
        button6.setOnClickListener(onclick);

    }

    View.OnClickListener onclick = new View.OnClickListener() {
        //BASE Notification ID
        private int Notification_ID_BASE = 110;
        private Notification baseNF;
        //Notification ID
        private int Notification_ID_MEDIA = 119;
        private Notification mediaNF;

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.BaseNotification:
                    //新建状态栏通知
                    baseNF = new Notification();

                    //设置通知在状态栏显示的图标
                    baseNF.icon = R.drawable.image;
                    //默认的系统时间
                    baseNF.when = System.currentTimeMillis();
                    //通知时在状态栏显示的内容
                    baseNF.tickerText = "You clicked BaseNF!";

                    //通知的默认参数 DEFAULT_SOUND, DEFAULT_VIBRATE, DEFAULT_LIGHTS.
                    //如果要全部采用默认值, 用 DEFAULT_ALL.
                    //此处采用默认声音
                    baseNF.defaults |= Notification.DEFAULT_SOUND;
                    baseNF.defaults |= Notification.DEFAULT_VIBRATE;
                    baseNF.defaults |= Notification.DEFAULT_LIGHTS;
//                    baseNF.defaults = Notification.DEFAULT_ALL;//所有的都是默认，时间，声音，闪屏
                    //让声音、振动无限循环，直到用户响应
                    baseNF.flags |= Notification.FLAG_INSISTENT;

                    //通知被点击后，自动消失
                    baseNF.flags |= Notification.FLAG_AUTO_CANCEL;

                    //点击'Clear'时，不清楚该通知(QQ的通知无法清除，就是用的这个)
                    baseNF.flags |= Notification.FLAG_NO_CLEAR;


                    //第二个参数 ：下拉状态栏时显示的消息标题 expanded message title
                    //第三个参数：下拉状态栏时显示的消息内容 expanded message text
                    //第四个参数：点击该通知时执行页面跳转
//                    baseNF.setLatestEventInfo(BaseNotification.this, "Title01", "Content01", pd);

                    //发出状态栏通知
                    //The first parameter is the unique ID for the Notification
                    // and the second is the Notification object.
                    nm.notify(Notification_ID_BASE, baseNF);

                    break;

                case R.id.UpdateBaseNotification:
                    //更新通知
                    //比如状态栏提示有一条新短信，还没来得及查看，又来一条新短信的提示。
                    //此时采用更新原来通知的方式比较。
                    //(再重新发一个通知也可以，但是这样会造成通知的混乱，而且显示多个通知给用户，对用户也不友好)
//                    baseNF.setLatestEventInfo(BaseNotification.this, "title", "lesson", pd);
                    nm.notify(Notification_ID_BASE, baseNF);
                    break;

                case R.id.ClearBaseNotification:

                    //清除 baseNF
                    nm.cancel(Notification_ID_BASE);
                    break;

                case R.id.MediaNotification:
                    mediaNF = new Notification();
                    mediaNF.icon = R.drawable.image;
                    mediaNF.tickerText = "You clicked MediaNF!";

                    //自定义声音import android.provider.MediaStore.Audio;
                    mediaNF.sound = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "6");

                    //通知时发出的振动
                    //第一个参数: 振动前等待的时间
                    //第二个参数： 第一次振动的时长、以此类推
                    long[] vir = {0,100,200,300};
                    mediaNF.vibrate = vir;

//                    mediaNF.setLatestEventInfo(BaseNotification.this, "Title03", "Content03", pd);

                    nm.notify(Notification_ID_MEDIA, mediaNF);
                    break;

                case R.id.ClearALL:
                    //清除 mediaNF
                    nm.cancel(Notification_ID_MEDIA);
                    break;

                case R.id.CustomNotification:
                    nm.cancelAll();
                    break;

                case R.id.ClearMediaNotification:
                    //自定义下拉视图，比如下载软件时，显示的进度条。
                    Notification notification = new Notification();

                    notification.icon = R.drawable.image;
                    notification.tickerText = "Custom!";

                    RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.act_custom);
                    contentView.setImageViewResource(R.id.image, R.drawable.image);
                    contentView.setTextViewText(R.id.text, "Hello, this message is in a custom expanded view");
                    notification.contentView = contentView;

                    //使用自定义下拉视图时，不需要再调用setLatestEventInfo()方法
                    //但是必须定义 contentIntent
                    notification.contentIntent = pd;

                    nm.notify(3, notification);
                    break;
            }
        }

    };
}