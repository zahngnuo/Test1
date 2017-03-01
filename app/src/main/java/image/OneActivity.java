package image;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import baiduanimation.R;
import shopcartest.bean.utils.BaseActivity;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */

public class OneActivity extends BaseActivity {
    private Button next_btn;

    @Override
    protected int loadLayoutId() {
        return R.layout.act_ont_act;
    }

    @Override
    protected void initViews() {
        next_btn = $(R.id.next_button);

    }

    @Override
    protected void initData() {
        setTitleStr("第一个界面");
    }

    @Override
    protected void bindListener() {
        super.bindListener();
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),UPOneActivity.class));
            }
        });
    }
}
