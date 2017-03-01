package zoomHeader;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import baiduanimation.R;


/**
 * desc:
 * author:张肖换
 * date:${Date}
 */
public class ZoomHeaderView extends LinearLayout {
    private float mTouchSlop;
    private float iDownY;
    private ZoomHeaderViewPager mViewPager;
    private float mFirstY;
    private boolean isExpand = false;
    private RecyclerView mRecyclerView;
    private TextView mCloseTxt;
    private RelativeLayout mBottomView;
    //图片放到最大时候的y
    private float mMaxY;

    //底部栏的起始Y
    private int mBottomY;

    public ZoomHeaderView(Context context) {
        super(context);
    }

    public ZoomHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public ZoomHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mViewPager = (ZoomHeaderViewPager) getChildAt(1);
        mFirstY = getY();
        mCloseTxt = (TextView) findViewById(R.id.tv_close);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY() - iDownY;
                float curry = event.getY();
                //向上滑动viewpager整体移动
                if (curry + moveY < 0 && curry + moveY > getHeight() / 2) {
                    mMaxY = curry + moveY;
                    setTranslationY(curry + moveY);
                    mCloseTxt.setAlpha(0f);
                }
                //向下移动
                if (curry + moveY > 0 && curry + moveY < 800) {
                    View view = mViewPager.getChildAt(mViewPager.getCurrentItem());
                    view.setTranslationY((curry + moveY) / 4);
                    mCloseTxt.setAlpha(view.getY() / 76);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                float upY = event.getY() - iDownY;
                float currentUpY = getY();
                //超过阀值 结束Activity
                if (upY+currentUpY>190){
                     finish();
                }
                //不在任何阀值  恢复
                if (currentUpY + upY > -getHeight() / 4 && currentUpY + upY < 190) {
                    restore(upY + currentUpY);
                }

                //超过展开阀值
                if (upY + currentUpY < -getHeight() / 4) {

                    if (upY + currentUpY < mMaxY) {
                        expand(mMaxY, 0);
                    } else {
                        expand(upY, currentUpY);
                    }
                }

                return true;
        }
        return super.onTouchEvent(event);
    }
    public void restore(float y) {
        mCloseTxt.setAlpha(0f);
        if (y > mFirstY) {
            ValueAnimator alphaAnimate = ValueAnimator.ofFloat(1, 0);
            alphaAnimate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override public void onAnimationUpdate(ValueAnimator animation) {
                    mCloseTxt.setAlpha((Float) animation.getAnimatedValue());
                }
            });
            alphaAnimate.setDuration(500);
            alphaAnimate.start();
        }

        mRecyclerView.smoothScrollToPosition(0);
        ValueAnimator va = ValueAnimator.ofFloat(y, mFirstY);
        va.setInterpolator(new DecelerateInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                float y = (float) animation.getAnimatedValue();
                setTranslationY(y);
                isExpand = false;
                mViewPager.canScroll = true;
            }
        });
        va.setDuration(500);
        va.start();



        //底部隐藏


        ValueAnimator bottomAnimate = ValueAnimator.ofFloat(mBottomView.getY(),mBottomY+mBottomView.getHeight());
        bottomAnimate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBottomView.setY((Float) animation.getAnimatedValue());

            }
        });

        bottomAnimate.start();
    }

    private void expand(float upY, float currentUpY) {
        mRecyclerView.scrollToPosition(0);
        ValueAnimator va = ValueAnimator.ofFloat(currentUpY + upY, -getHeight() / 3);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                float y = (float) animation.getAnimatedValue();
                mViewPager.canScroll = false;
                setTranslationY(y);
                isExpand = true;
            }
        });

        va.setInterpolator(new DecelerateInterpolator());
        va.setDuration(500);
        va.start();


        //底部上移


        ValueAnimator bottomAnimate = ValueAnimator.ofFloat(mBottomView.getY(),mBottomY);
        bottomAnimate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBottomView.setY((Float) animation.getAnimatedValue());

            }
        });

        bottomAnimate.start();

    }

    public void expand() {
        setTranslationY(-getHeight() / 3);
    }

    private void finish() {
        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, 1000);
        ta.setDuration(500);
        ta.setFillAfter(true);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {

            }

            @Override public void onAnimationEnd(Animation animation) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((Activity) getContext()).finish();
                }
            }

            @Override public void onAnimationRepeat(Animation animation) {

            }
        });

        startAnimation(ta);
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                iDownY = (int) ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();
                if (Math.abs(moveY - iDownY) > mTouchSlop) {

                    return true;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public ZoomHeaderViewPager getViewPager() {
        return mViewPager;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    public RelativeLayout getmBottomView() {
        return mBottomView;
    }

    public void setmBottomView(RelativeLayout mBottomView,int bottomY) {
        this.mBottomView = mBottomView;
        mBottomY = bottomY;
    }

    public boolean isExpand() {
        return isExpand;
    }
}
