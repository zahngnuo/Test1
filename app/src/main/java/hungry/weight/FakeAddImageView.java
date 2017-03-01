package hungry.weight;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */
public class FakeAddImageView extends ImageView {
    private PointF pointF;

    public FakeAddImageView(Context context) {
        super(context);
    }

    public FakeAddImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FakeAddImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FakeAddImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setMPointF(PointF pointF) {
        setX(pointF.x);
        setY(pointF.y);
    }

    public PointF getmPointF() {
        return pointF;
    }

    public void setmPointF(PointF mPointF) {
        setX(mPointF.x);
        setY(mPointF.y);
    }
}
