package weight;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import baiduanimation.R;
/**
 * desc:自定义在库item
 * author:张肖换
 * date:${Date}
 */

public class StockTextView extends LinearLayout {
    public TextView mTextLeft;
    public TextView mTextCenter;
    public TextView mTextRight;

    public StockTextView(Context context) {
        this(context, null);
    }

    public StockTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StockTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View rootView = LayoutInflater.from(context).inflate(R.layout.stock_text_view, this, true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StockTextView);
        int defColor = ContextCompat.getColor(context, R.color.gray);
        int leftColor = array.getColor(R.styleable.StockTextView_textLeftColor, defColor);
        int centerColor = array.getColor(R.styleable.StockTextView_textCenterColor, defColor);
        int rightColor = array.getColor(R.styleable.StockTextView_textRightColor, defColor);
        ColorStateList colorStateList = array.getColorStateList(R.styleable.StockTextView_textCenterColorList);

        float leftSize = array.getFloat(R.styleable.StockTextView_textLeftSize, 12);
        float centerSize = array.getFloat(R.styleable.StockTextView_textCenterSize, 12);
        float rightSize = array.getFloat(R.styleable.StockTextView_textRightSize, 12);
        int leftCentPadding = (int) array.getDimension(R.styleable.StockTextView_leftCentPadding, 0);
        int textCenterMin = (int) array.getDimension(R.styleable.StockTextView_textCenterMin, 0);
        int textCenterWidth = (int) array.getDimension(R.styleable.StockTextView_textCenterWidth, 0);

        boolean rightBold = array.getBoolean(R.styleable.StockTextView_textRightBold, false);
        boolean leftGravityRight = array.getBoolean(R.styleable.StockTextView_textLeftGravityRight, false);

        boolean textCenterGravity = array.getBoolean(R.styleable.StockTextView_textCenterGravity, false);

        String leftTxt = array.getString(R.styleable.StockTextView_textLeftTxt);
        String centerTxt = array.getString(R.styleable.StockTextView_textCenterTxt);
        String rightTxt = array.getString(R.styleable.StockTextView_textRightTxt);
        array.recycle();
        this.mTextLeft = (TextView) rootView.findViewById(R.id.stock_text_left);
        this.mTextCenter = (TextView) rootView.findViewById(R.id.stock_text_center);
        this.mTextRight = (TextView) rootView.findViewById(R.id.stock_text_right);
        if (0 != textCenterWidth)
            mTextCenter.setWidth(textCenterWidth);
        if (textCenterMin != 0) {
            mTextCenter.setMinWidth(textCenterMin);
            mTextCenter.setGravity(textCenterGravity ? Gravity.LEFT : Gravity.END);
        }
        if (null != colorStateList)
            setCenterColor(ContextCompat.getColorStateList(context, R.color.progressbar_select));
        else
            setCenterColor(centerColor);
        setLeftColor(leftColor)
                .setRightColor(rightColor)
                .setLeftSize(leftSize)
                .setCenterSize(centerSize)
                .setRightSize(rightSize)
                .setLeftTxt(leftTxt)
                .setCenterTxt(centerTxt)
                .setRightTxt(rightTxt);
        if (0 != leftCentPadding)
            mTextCenter.setPadding(leftCentPadding, 0, 0, 0);
        if (rightBold)
            mTextCenter.getPaint().setFakeBoldText(true);
        if (leftGravityRight)
            mTextLeft.setGravity(Gravity.END);
    }

    public StockTextView setLeftColor(int leftColor) {
        if (null != mTextLeft)
            mTextLeft.setTextColor(leftColor);
        return this;
    }

    public StockTextView setCenterColor(int centerColor) {
        if (null != mTextCenter)
            mTextCenter.setTextColor(centerColor);
        return this;
    }

    public StockTextView setCenterColor(ColorStateList centerColor) {
        if (null != mTextCenter)
            mTextCenter.setTextColor(centerColor);
        return this;
    }

    public StockTextView setRightColor(int rightColor) {
        if (null != mTextRight)
            mTextRight.setTextColor(rightColor);
        return this;
    }

    public StockTextView setLeftSize(float leftSize) {
        if (null != mTextLeft)
            mTextLeft.setTextSize(leftSize);
        return this;
    }

    public StockTextView setCenterSize(float centerSize) {
        if (null != mTextCenter)
            mTextCenter.setTextSize(centerSize);
        return this;
    }

    public StockTextView setRightSize(float rightSize) {
        if (null != mTextRight)
            mTextRight.setTextSize(rightSize);
        return this;
    }

    public StockTextView setLeftTxt(String leftTxt) {
        if (null != mTextLeft)
            mTextLeft.setText(leftTxt);
        return this;
    }

    public StockTextView setCenterTxt(String centerTxt) {
        if (null != mTextCenter)
            mTextCenter.setText(centerTxt);
        return this;
    }

    public StockTextView setRightTxt(String rightTxt) {
        if (null != mTextRight)
            mTextRight.setText(rightTxt);
        return this;
    }

    public TextView getTextCenter() {
        return mTextCenter;
    }

}
