package shopcartest.bean.property.basic;

import android.graphics.Bitmap;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */
public class GoodItem {

    // 商品图
    private Bitmap mGoodsBitmap;

    public GoodItem(Bitmap mGoodsBitmap) {
        this.mGoodsBitmap = mGoodsBitmap;
    }

    public Bitmap getmGoodsBitmap() {
        return mGoodsBitmap;
    }

    public void setmGoodsBitmap(Bitmap mGoodsBitmap) {
        this.mGoodsBitmap = mGoodsBitmap;
    }
}
