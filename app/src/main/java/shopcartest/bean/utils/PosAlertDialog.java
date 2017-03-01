package shopcartest.bean.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wumart.lib.common.CommonUtils;

import baiduanimation.R;


/**
 * desc:
 * author:张肖换
 * date:${Date}
 */

public class PosAlertDialog {
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_msg1;
    private TextView txt_msg;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private DisplayMetrics dm;

    public PosAlertDialog(Context context) {
        dm = CommonUtils.getDisplayMetrics(context);
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.view_notitile_dialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        txt_msg1 = (TextView) view.findViewById(R.id.txt_msg1);
        txt_msg1.setVisibility(View.GONE);
        btn_neg = (Button) view.findViewById(com.wumart.lib.R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(com.wumart.lib.R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(com.wumart.lib.R.id.img_line);
        img_line.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, com.wumart.lib.R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (dm.widthPixels * 0.7), LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    public PosAlertDialog setMsg(String msg) {
        showMsg = true;
        txt_msg.setText(msg);
        txt_msg.setVisibility(View.VISIBLE);
        return this;
    }

    public PosAlertDialog setMsg1(String msg) {
        showMsg = true;
        txt_msg1.setText(msg);
        txt_msg1.setVisibility(View.VISIBLE);
        return this;
    }

    public PosAlertDialog setNegativeButton(@ColorRes int color) {
        setNegativeButton("", null);
        btn_neg.setTextColor(ContextCompat.getColor(dialog.getContext(), color));
        return this;
    }

    public PosAlertDialog setTxtGravity(int gravity) {
        if (txt_msg != null)
            txt_msg.setGravity(gravity);
        return this;
    }

    public PosAlertDialog setShowMsg(boolean showMsg) {
        this.showMsg = showMsg;
        return this;
    }

    public PosAlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public PosAlertDialog setPositiveButton(String text, final View.OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public PosAlertDialog setNegativeButton(String text,
                                            final View.OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
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
        if (showMsg)
            txt_msg.setVisibility(View.VISIBLE);
        if (showMsg)
            txt_msg1.setVisibility(View.VISIBLE);
        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(com.wumart.lib.R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(com.wumart.lib.R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(com.wumart.lib.R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(com.wumart.lib.R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(com.wumart.lib.R.drawable.alertdialog_single_selector);
        }
    }

    public PosAlertDialog setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
        return this;
    }

    public PosAlertDialog builder() {
        setLayout();
        return this;
    }

    public void show() {
        if (dialog.isShowing())
            return;
        dialog.show();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }
}
