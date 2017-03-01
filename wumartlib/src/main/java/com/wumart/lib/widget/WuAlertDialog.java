package com.wumart.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.wumart.lib.R;
import com.wumart.lib.common.CommonUtils;

/**
 * User: 吕勇
 * Date: 2016-03-25
 * Time: 09:05
 * Description:对话框
 */
public class WuAlertDialog {
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private TextView txt_msg;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private DisplayMetrics dm;

    public WuAlertDialog(Context context) {
        dm = CommonUtils.getDisplayMetrics(context);
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_alertdialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (dm.widthPixels * 0.7), LayoutParams.WRAP_CONTENT));
    }

    public WuAlertDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setVisibility(View.GONE);
        } else {
            txt_title.setVisibility(View.VISIBLE);
            txt_title.setText(title);
        }
        txt_title.setVisibility(View.VISIBLE);
        return this;
    }

    public WuAlertDialog setTitleSize(float size) {
        this.txt_title.setTextSize(2, size);
        return this;
    }

    public WuAlertDialog setMsgSize(float size) {
        this.txt_msg.setTextSize(2, size);
        return this;
    }

    public WuAlertDialog setMsg(String msg) {
        showMsg = true;
        txt_msg.setText(msg);
        txt_msg.setVisibility(View.VISIBLE);
        return this;
    }

    public WuAlertDialog setNegativeButton(@ColorRes int color) {
        setNegativeButton("", null);
        btn_neg.setTextColor(ContextCompat.getColor(dialog.getContext(), color));
        return this;
    }

    public WuAlertDialog setTxtGravity(int gravity) {
        if (txt_msg != null)
            txt_msg.setGravity(gravity);
        return this;
    }

    public WuAlertDialog setTxtMargin(int left, int top, int right, int bottom) {
        android.widget.LinearLayout.LayoutParams layoutParam = new android.widget.LinearLayout.LayoutParams(-1, -2);
        layoutParam.setMargins(left, top, right, bottom);
        this.txt_msg.setLayoutParams(layoutParam);
        return this;
    }

    public WuAlertDialog setShowMsg(boolean showMsg) {
        this.showMsg = showMsg;
        return this;
    }

    public WuAlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public WuAlertDialog setPositiveButton(String text, final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public WuAlertDialog setNegativeButton(String text,
                                           final OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (showTitle)
            txt_title.setVisibility(View.VISIBLE);

        if (showMsg)
            txt_msg.setVisibility(View.VISIBLE);

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public WuAlertDialog setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
        return this;
    }

    public WuAlertDialog builder() {
        this.setLayout();
        return this;
    }

    public void show() {
        if (!this.dialog.isShowing()) {
            this.dialog.show();
        }
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }


}