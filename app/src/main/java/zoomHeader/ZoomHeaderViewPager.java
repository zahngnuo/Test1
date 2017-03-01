package zoomHeader;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * desc:自定义viewPager
 * author:张肖换
 * date:${Date}
 */
public class ZoomHeaderViewPager extends ViewPager {
    public boolean canScroll = true;

    public ZoomHeaderViewPager(Context context) {
        super(context);
    }

    public ZoomHeaderViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return canScroll && super.onTouchEvent(event);
    }

    /**
     * //改变系统绘制顺序
     */
    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int postion = getCurrentItem();
        if (postion < 0) {
            return i;
        } else {
            if (i == childCount - 1) {////这是最后一个需要刷新的item
                if (postion > i) {
                    postion = i;
                }
                return postion;
            }
            if (i == postion) {//这是原本要在最后一个刷新的item
                return childCount - 1;
            }
        }
        return i;
    }
}
