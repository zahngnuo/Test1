package heartlike;

import android.graphics.Path;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */
public class CommentPathAdapter  implements  LikeView.GraphAdapter {
    private static CommentPathAdapter instance;
    private static final float xOffsetScale = 0.06f;
    private static final float yOffsetScale = 0.2f;
    //可用单例模式
    public static CommentPathAdapter getInstance() {
        synchronized (CommentPathAdapter.class) {
            if (null == instance) {
                instance = new CommentPathAdapter();
            }
        }
        return instance;
    }
    //这里绘制你想要的图形
    @Override
    public Path getGraphPath(LikeView view, int length) {
        Path path = new Path();
        int dx = (int) (length * xOffsetScale);
        int dy = (int) (length * yOffsetScale);
        int w = (int) (length * (1 - xOffsetScale * 2));
        int h = (int) (length * (1 - yOffsetScale * 2));
        path.moveTo(dx, dy);
        path.lineTo(dx + w, dy);
        path.lineTo(dx + w, dy + h);
        path.lineTo(dx + (w * 0.35f), dy + h);
        path.lineTo(dx + (w * 0.1f), dy + (h * 1.4f));
        path.lineTo(dx + (w * 0.1f), dy + h);
        path.lineTo(dx, dy + h);
        path.lineTo(dx, dy);
        return path;
    }
}
