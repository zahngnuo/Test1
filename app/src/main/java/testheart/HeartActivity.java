package testheart;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import baiduanimation.R;


/**
 * desc:android表白心
 *
 * author:张肖换
 * date:${Date}
 */
public class HeartActivity  extends Activity{
    private  HeartView  heartView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_heartactivity_act);
        heartView = (HeartView) findViewById(R.id.surfaceView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        heartView.reDraw();
        return super.onTouchEvent(event);
    }

    public void reDraw(View v) {

        heartView.reDraw();

    }
}
