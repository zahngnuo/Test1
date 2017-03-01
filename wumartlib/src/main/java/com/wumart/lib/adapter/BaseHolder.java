package com.wumart.lib.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * User: 吕勇
 * Date: 2016-03-01
 * Time: 8:39
 * Description:HeaderView的ViewHolder
 */
public class BaseHolder extends ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;

    /**
     * Instantiates a new Base holder.
     *
     * @param mConvertView the m convert view
     */
    public BaseHolder(View mConvertView) {
        super(mConvertView);
        this.mConvertView = mConvertView;
        this.mViews = new SparseArray<>();
    }


    /**
     * Gets view.
     *
     * @param <T>    the type parameter
     * @param viewId the view id
     * @return the view
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置TextView的内
     *
     * @param viewId 控件id
     * @param text   文本内容
     * @return ViewHolder text
     */
    public BaseHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public BaseHolder setTextFormart(int viewId, String formart, Object... objs) {
        TextView view = getView(viewId);
        String formatStr = String.format(formart, objs);
        view.setText(formatStr.replaceAll("null", ""));
        return this;
    }

    /**
     * 设置TextView的内
     *
     * @param viewId  控件id
     * @param spanned 文本内容
     * @return BaseHolder spanned
     */
    public BaseHolder setSpanned(int viewId, Spanned spanned) {
        TextView view = getView(viewId);
        view.setText(spanned);
        return this;
    }

    /**
     * 设置TextView的内
     *
     * @param viewId 控件id
     * @param resId  资源文件中的id
     * @return BaseHolder res id text
     */
    public BaseHolder setResIdText(int viewId, int resId) {
        TextView view = getView(viewId);
        view.setText(resId);
        return this;
    }

    /**
     * Sets image resource.
     *
     * @param viewId 控件id
     * @param resId  资源文件中的id
     * @return BaseHolder image resource
     */
    public BaseHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * Sets image bitmap.
     *
     * @param viewId 控件id
     * @param bitmap 图片的的Bitmap
     * @return BaseHolder image bitmap
     */
    public BaseHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    /**
     * Sets image drawable.
     *
     * @param viewId   控件id
     * @param drawable 图片的的Drawable
     * @return BaseHolder image drawable
     */
    public BaseHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * 给控件设置tag
     *
     * @param viewId 控件id
     * @param obj    tag
     * @return ViewHolder tag
     */
    public BaseHolder setTag(int viewId, Object obj) {
        View view = getView(viewId);
        view.setTag(obj);
        return this;
    }

    /**
     * 给控件设置OnClickListener
     *
     * @param viewId   控件id
     * @param listener 点击事件
     * @param tag      the tag
     * @return ViewHolder click listener
     */
    public BaseHolder setClickListener(int viewId, View.OnClickListener listener, Object tag) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        view.setTag(tag);
        return this;
    }

    /**
     * 给控件设置OnClickListener
     *
     * @param listener 点击事件
     * @param tag      the tag
     * @param viewIds  控件ids
     * @return ViewHolder click listener
     */
    public BaseHolder setClickListener(View.OnClickListener listener, Object tag, int... viewIds) {
        for (int viewId : viewIds) {
            View view = getView(viewId);
            view.setTag(tag);
            view.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 设置选中
     *
     * @param checked 是否选择
     * @param viewIds view的Id
     * @return BaseHolder checked
     */
    public BaseHolder setChecked(boolean checked, int... viewIds) {
        for (int viewId : viewIds) {
            Checkable view = getView(viewId);
            view.setChecked(checked);
        }
        return this;
    }

    /**
     * 设置view可见
     *
     * @param visible 是否看见
     * @param viewIds view的Id
     * @return BaseHolder visibility
     */
    public BaseHolder setVisibility(boolean visible, int... viewIds) {
        for (int viewId : viewIds) {
            setVisible(viewId, visible);
        }
        return this;
    }

    /**
     * Sets visible.
     *
     * @param viewId  the view id
     * @param visible the visible
     * @return the visible
     */
    public BaseHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * Gets view.
     *
     * @param <T>    the type parameter
     * @param viewId the view id
     * @param tClass the t class
     * @return the view
     */
    public <T extends View>T getView(int viewId, Class<T> tClass) {
        return  getView(viewId);
    }

    /**
     * Sets visible.
     *
     * @param viewId  the view id
     * @param visible the visible
     * @return the visible
     */
    public BaseHolder setVisible(int viewId, int visible) {
        View view = getView(viewId);
        view.setVisibility(visible);
        return this;
    }


    /**
     * Sets alpha.
     *
     * @param value   the value
     * @param viewIds the view ids
     * @return the alpha
     */
    public BaseHolder setAlpha(float value, int... viewIds) {
        for (int viewId : viewIds) {
            setAlpha(viewId, value);
        }
        return this;
    }

    /**
     * Sets alpha.
     *
     * @param viewId the view id
     * @param value  the value
     * @return the alpha
     */
    public BaseHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    /**
     * Sets image level.
     *
     * @param viewId the view id
     * @param level  the level
     * @return the image level
     */
    public BaseHolder setImageLevel(int viewId, int level) {
        ImageView view = getView(viewId);
        view.setImageLevel(level);
        return this;
    }

    /**
     * Sets selected.
     *
     * @param viewId   the view id
     * @param selected the selected
     * @return the selected
     */
    public BaseHolder setSelected(int viewId, boolean selected) {
        CheckedTextView view = getView(viewId);
        view.setSelected(selected);
        return this;
    }

    /**
     * Sets selected.
     *
     * @param selected the selected
     * @param viewIds  the view ids
     * @return the selected
     */
    public BaseHolder setSelected(boolean selected,int...viewIds) {
        for (int viewId : viewIds) {
            CheckedTextView view = getView(viewId);
            view.setSelected(selected);
        }
        return this;
    }

    /**
     * 給item的子控件设置点击事件
     * @param listener the OnItemChildClickListener
     * @param viewIds the viewIds
     */
    public BaseHolder setOnItemChildClickListener(LBaseAdapter.OnItemChildClickListener listener,int...viewIds) {
        listener.position = getAdapterPosition();
        for (int viewId : viewIds) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
        }
        return this;
    }
}
