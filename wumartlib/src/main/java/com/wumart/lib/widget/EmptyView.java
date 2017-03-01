package com.wumart.lib.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wumart.lib.R;


/**
 * User: 吕勇
 * Date: 2016-03-25
 * Time: 09:05
 * Description:空数据显示
 */
public class EmptyView extends RelativeLayout {
    private TextView mTextView;
    private Drawable mDrawable;
    private int mImageResId;
    private String messageStr = "无交易数据";
    private boolean isOne = true;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);
        if (null == messageStr)
            messageStr = "无交易数据";
        if (null == mDrawable)
            mDrawable = ContextCompat.getDrawable(context, R.drawable.no_data);
        mTextView = new TextView(getContext());
        mTextView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
        mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        setMessageStr(messageStr);
        setDrawable(mDrawable);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addView(mTextView, params);
        setVisibility(INVISIBLE);
    }


    public EmptyView setImageResId(int imageResId) {
        if (null != mTextView && imageResId != 0)
            setDrawable(ContextCompat.getDrawable(getContext(), imageResId));
        mImageResId = imageResId;
        return this;
    }

    public EmptyView setDrawable(Drawable drawable) {
        if (mDrawable != null && mTextView != null)
            mTextView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        mDrawable = drawable;
        return this;
    }

    public EmptyView setTextSize(int textSize) {
        if (mTextView != null)
            mTextView.setTextSize(textSize);
        return this;
    }

    public EmptyView setColorResId(int colorResId) {
        if (mTextView != null)
            mTextView.setTextColor(colorResId);
        return this;
    }

    public EmptyView setMessageStr(String messageStr) {
        if (null != mTextView && messageStr != null)
            mTextView.setText(messageStr);
        this.messageStr = messageStr;
        return this;
    }

    public EmptyView setBottomPadding(int padding) {
        mTextView.setPadding(0, 0, 0, padding);
        return this;
    }

    public EmptyView showNetWorkError() {
        if (isOne) {
            isOne = false;
            return this;
        }
        setImageResId(R.drawable.network_disconnection);
        setMessageStr("网络故障,请检查后重新获取数据!");
        setVisibility(VISIBLE);
        return this;
    }

    public EmptyView showEmptyView() {
        if (isOne) {
            isOne = false;
            return this;
        }
        if (mImageResId != 0) {
            setImageResId(mImageResId);
        } else if (mDrawable != null && mTextView != null) {
            setDrawable(mDrawable);
        }
        setMessageStr(messageStr);
        setVisibility(VISIBLE);
        return this;
    }

    public boolean isOne() {
        return isOne;
    }

    public void setOne(boolean one) {
        isOne = one;
    }
}
