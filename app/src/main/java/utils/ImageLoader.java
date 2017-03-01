package utils;

import android.widget.ImageView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import baiduanimation.R;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */

public class ImageLoader {
    private static ImageOptions imageOptions = new ImageOptions.Builder()
            .setLoadingDrawableId(R.drawable.icon_def)
            .setFailureDrawableId(R.drawable.icon_def)
            .build();

    public static void loadImage(ImageView imageView, String url) {
        x.image().bind(imageView, url, imageOptions);
    }
}
