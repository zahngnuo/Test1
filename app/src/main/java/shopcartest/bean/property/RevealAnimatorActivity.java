package shopcartest.bean.property;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;

import baiduanimation.R;
import shopcartest.bean.utils.BaseActivity;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */
public class RevealAnimatorActivity extends BaseActivity {
    private Context mContext;


    @Override
    protected int loadLayoutId() {
        return R.layout.activity_reveal_animation;
    }

    @Override
    protected void initViews() {
        final LinearLayout blueBox = (LinearLayout) findViewById(R.id.blueBox);
        final LinearLayout redBox = (LinearLayout) findViewById(R.id.redBox);
        final View content = findViewById(R.id.content);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floating);
        blueBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Animator animator = ViewAnimationUtils.createCircularReveal(blueBox, blueBox.getWidth() / 2
                            , blueBox.getHeight() / 2, 0, Math.min(blueBox.getWidth() / 2, blueBox.getHeight() / 2));
                    animator.start();
                }
            }
        });

//        redBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//
//                    Animator animator = ViewAnimationUtils.createCircularReveal(redBox, 0, 0, 0, redBox.getWidth());
//                    animator.start();
//                }
//            }
//        });

        redBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                        Animator animator = ViewAnimationUtils.createCircularReveal(redBox, x, y, 0, redBox.getWidth());
                        animator.start();
                    }
                }
                return true;
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });


        content.post(new Runnable() {
            @Override
            public void run() {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    float endValue = (float) Math.hypot(content.getWidth(), content.getHeight());
                    Animator animator = ViewAnimationUtils.createCircularReveal(content, 0, 0, 0, endValue);
                    animator.start();
                }
            }
        });


    }

    @Override
    protected void initData() {

    }


}
