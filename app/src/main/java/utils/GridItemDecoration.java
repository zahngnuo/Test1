package utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */

public class GridItemDecoration  extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{16843284};
    private Drawable mDivider;
    private boolean hasHeader;

    public GridItemDecoration(Context context) {
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        this.mDivider = a.getDrawable(0);
        a.recycle();
    }

    public GridItemDecoration(Context context, boolean header) {
        this(context);
        this.hasHeader = header;
    }

    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        this.drawHorizontal(c, parent);
        this.drawVertical(c, parent);
    }

    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager)layoutManager).getSpanCount();
        } else if(layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager)layoutManager).getSpanCount();
        }

        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();

        for(int i = 0; i < childCount; ++i) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin + this.mDivider.getIntrinsicWidth();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + this.mDivider.getIntrinsicHeight();
            this.mDivider.setBounds(left, top, right, bottom);
            this.mDivider.draw(c);
        }

    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();

        for(int i = 0; i < childCount; ++i) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            int top = child.getTop() - params.topMargin;
            int bottom = child.getBottom() + params.bottomMargin;
            int left = child.getRight() + params.rightMargin;
            int right = left + this.mDivider.getIntrinsicWidth();
            this.mDivider.setBounds(left, top, right, bottom);
            this.mDivider.draw(c);
        }

    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager) {
            childCount -= childCount % spanCount;
            if(pos >= childCount) {
                return true;
            }
        } else if(layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager)layoutManager).getOrientation();
            if(orientation == 1) {
                childCount -= childCount % spanCount;
                if(pos >= childCount) {
                    return true;
                }
            } else if((pos + 1) % spanCount == 0) {
                return true;
            }
        }

        return false;
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager) {
            if((pos + 1) % spanCount == 0) {
                return true;
            }
        } else if(layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager)layoutManager).getOrientation();
            if(orientation == 1) {
                if((pos + 1) % spanCount == 0) {
                    return true;
                }
            } else {
                childCount -= childCount % spanCount;
                if(pos >= childCount) {
                    return true;
                }
            }
        }

        return false;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, this.mDivider.getIntrinsicWidth(), this.mDivider.getIntrinsicHeight());
    }
}
