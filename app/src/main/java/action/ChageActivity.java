package action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import baiduanimation.R;

/**
 * desc:左右切换Activity
 * author:张肖换
 * date:${Date}
 */

public class ChageActivity extends Activity {
    private Button btn_chge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chage);
        initView();
    }

    private void initView() {
        btn_chge = (Button) findViewById(R.id.btn_chage);
        btn_chge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChageActivity.this, SlideSecondActivity.class);
                startActivity(intent);
                //设置切换动画，从右边进入，左边退出
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

            }
        });
    }
}
