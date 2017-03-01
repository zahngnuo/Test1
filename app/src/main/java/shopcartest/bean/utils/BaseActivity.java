package shopcartest.bean.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.wumart.lib.common.CommonUtils;
import com.wumart.lib.common.ToastUtils;
import com.wumart.lib.net.HttpCallBack;
import com.wumart.lib.net.HttpInterface;
import com.wumart.lib.widget.LoadingDialog;
import com.wumart.lib.widget.WuAlertDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import baiduanimation.R;
import wmqq.LoginAct;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */

public abstract class BaseActivity  extends Activity implements HttpInterface, View.OnClickListener{
    protected TextView title/*, more*/;
    protected View more;
    public Toolbar mToolbar;
    protected LoadingDialog mLoadingDialog;
    protected WuAlertDialog mNotifyDialog;
    protected int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    protected Gson gson;

    public static final String ACTION_NOTIFICATION_RECEIVED = "com.retail.wumartmms.ACTION_NOTIFICATION_RECEIVED";//推送消息
    public static final String MESSAGE_USER_EXIT_ACTION = "com.retail.wumartmms.MESSAGE_USER_EXIT_ACTION";//用户退出
    protected PosAlertDialog mPosAlertDialog;
    protected BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            disposeReceiver(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(orientation);
        setContentView(loadLayoutId());
        initToolbar();
        initViews();
        gson = new Gson();
        initData();
        registerMessageReceiver();
        bindListener();
        processLogic();
    }

    protected void initToolbar() {
        mToolbar = $(R.id.toolbar);
        if (mToolbar == null)
            return;
        title = $(R.id.toolbar_title);
        more = $(R.id.toolbar_more);
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        mLoadingDialog = null;
        mNotifyDialog = null;
        mMessageReceiver = null;
        gson = null;
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }

    protected void registerMessageReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_USER_EXIT_ACTION);
        filter.addAction(ACTION_NOTIFICATION_RECEIVED);
        registerReceiver(mMessageReceiver, filter);
    }

    protected <T extends View> T $(@IdRes int viewId) {
        return (T) findViewById(viewId);
    }

    protected <T extends View> T $(View view, @IdRes int viewId) {
        return (T) view.findViewById(viewId);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 为Activity加载布局文件
     */
    protected abstract int loadLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initViews();

    /**
     * 初始化数剧
     */
    protected abstract void initData();

    /**
     * 为控件设置监听
     */
    protected void bindListener() {

    }

    /**
     * 逻辑操作，网络请求
     */
    protected void processLogic() {

    }

    /**
     * 控件点击回调
     */
    protected void onClick(View view, int id) {

    }


    @Override
    public void onClick(View v) {
        CommonUtils.hiddenKeyBoard(v);
        onClick(v, v.getId());
    }


    /**
     * 处理广播
     */
    protected void disposeReceiver(Intent intent) {
        if (intent.getAction().equals(MESSAGE_USER_EXIT_ACTION)) {
            finish();
        }
    }

    @Override
    public void showLoadingView() {
        if (mLoadingDialog == null)
            mLoadingDialog = new LoadingDialog(this);
        if (!mLoadingDialog.isShowing())
            mLoadingDialog.show();
    }

    @Override
    public void hideLoadingView() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    @Override
    public void showSuccessToast(@NonNull String message) {
        ToastUtils.textToast(this, message);
    }

    @Override
    public void showFailToast(@NonNull String message) {
        ToastUtils.textToastError(this, message);
    }

    public void showScanError(String text, int imageId) {
        ToastUtils.textToast(this, text, imageId);
    }

    public void showNullToast(@NonNull String message, String message1) {
        PosToastUtils.textNullToast(this, message, message1);
    }


    @Override
    public void notifyDialog(@NonNull String message) {
        this.notifyDialog(message, "");
    }

    public void notifyDialog(@NonNull String info, String title) {
        this.notifyDialog(info, title, false);
    }

    public void notifyDialog(String info, String title, final boolean isFinish) {
        if (mNotifyDialog == null) {
            mNotifyDialog = new WuAlertDialog(this)
                    .setMsg(info)
                    .setTitle(title)
                    .setNegativeButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Hawk.get(HttpCallBack.FORM_HTTPCALLBACK, false)) {
                                Hawk.put(HttpCallBack.FORM_HTTPCALLBACK, false);
                                loginOut();
                                return;
                            }
                            notifyDialogBack();
                            if (isFinish)
                                finish();
                        }
                    }).builder();
        }
        mNotifyDialog.setMsg(info);
        mNotifyDialog.setTitle(title);
        if (!mNotifyDialog.isShowing())
            mNotifyDialog.show();
    }

    /**
     * 没有Title的Dialog
     *
     * @param info     Message
     * @param into1    Message
     * @param isFinish 是否关闭Activity
     */
    public void notifyNoTitleDialog(String info, String into1, final boolean isFinish) {
        if (mPosAlertDialog == null) {
            mPosAlertDialog = new PosAlertDialog(this)
                    .setMsg(info)
                    .setMsg1(into1)
                    .setNegativeButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Hawk.get(HttpCallBack.FORM_HTTPCALLBACK, false)) {
                                Hawk.put(HttpCallBack.FORM_HTTPCALLBACK, false);
                                loginOut();
                                return;
                            }
                            notifyDialogBack();
                            if (isFinish)
                                finish();
                        }
                    }).builder();
        }
        mPosAlertDialog.setMsg(info);
        mPosAlertDialog.setMsg1(into1);
        if (!mPosAlertDialog.isShowing())
            mPosAlertDialog.show();
    }

    /**
     * 退出登录
     */
    protected void loginOut() {
        sendBroadcast(new Intent(MESSAGE_USER_EXIT_ACTION));
        Intent intentTag = new Intent();
        intentTag.setClass(this, LoginAct.class);
        intentTag.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentTag.putExtra("CANCELSITE", "CANCELSITE");
        startActivity(intentTag);
        finish();
    }

    protected void notifyDialogBack() {
    }


    protected void setTitleStr(String titleStr) {
        if (null != title)
            title.setText(titleStr);
    }

    protected void setMoreStr(String moreStr) {
        if (null != more) {
            more.setVisibility(View.VISIBLE);
            ((TextView) more).setText(moreStr);
        }
    }

    public int getActionBarHeight() {
        if (null != mToolbar) {
            return mToolbar.getHeight();
        }
        return 0;
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    /**
     * 获取状态栏高度
     *
     * @return int
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
