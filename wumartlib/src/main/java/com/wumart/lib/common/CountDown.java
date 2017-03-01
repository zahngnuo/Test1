package com.wumart.lib.common;

import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

/**
 * User: 吕勇
 * Date: 2016-05-06
 * Time: 14:06
 * Description:定时器
 */
public class CountDown extends CountDownTimer {
    private View view;
    private String finishStr, tickStr;
    private boolean finshAct;
    private Activity activity;
    private CountDownBack downBack;
    private long mCountDownInterval;

    public CountDown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        mCountDownInterval=countDownInterval;
    }
    public CountDown(long millisInFuture) {
        this(millisInFuture, 1000);
    }

    public CountDown setView(View view) {
        this.view = view;
        return this;
    }

    public CountDown setFinishStr(String finishStr) {
        this.finishStr = finishStr;
        return this;
    }

    public CountDown setTickStr(String tickStr) {
        this.tickStr = tickStr;
        return this;
    }

    public CountDown setFinshAct(boolean finshAct) {
        this.finshAct = finshAct;
        return this;
    }

    public CountDown setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }
    public void resetData() {
        cancel();
        setText();
    }

    public CountDown setDownBack(CountDownBack downBack) {
        this.downBack = downBack;
        return this;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (null != view && view instanceof TextView && tickStr != null) {
            ((TextView) view).setText(millisUntilFinished / mCountDownInterval + tickStr);
            view.setEnabled(false);
        }
    }

    @Override
    public void onFinish() {
        setText();
        if(null!=downBack)
            downBack.countDownFinish();
        if (finshAct && activity != null)
            activity.finish();
    }

    private void setText() {
        if (null != view && view instanceof TextView && finishStr != null) {
            ((TextView) view).setText(finishStr);
            ((TextView) view).setTextColor(Color.WHITE);
            view.setEnabled(true);
        }
    }

    public interface CountDownBack {
        void countDownFinish();
    }

}
