package shopcartest.bean.property;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import baiduanimation.R;
import shopcartest.bean.property.basic.AlipayFailureView;
import shopcartest.bean.property.basic.AlipaySuccessView;
import shopcartest.bean.utils.BaseActivity;

/**
 * desc:支付界面
 * author:张肖换
 * date:${Date}
 */
public class AliPayAnimActivity extends BaseActivity{
    private AlipaySuccessView alipaySuccessView;
    private AlipayFailureView alipayFailureView;
    private Button success, failure;
    private int radius1, radius2;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.e("measure ", ".......>  " + alipaySuccessView.getWidth() + "  " + alipaySuccessView.getHeight());
             System.out.print(alipayFailureView.getWidth()+alipayFailureView.getHeight());
           //在布局中取长宽中较小的值作为圆的半径
            radius1 = Math.min(alipaySuccessView.getWidth(), alipaySuccessView.getHeight());
            radius2 = Math.min(alipayFailureView.getWidth(), alipayFailureView.getHeight());

        }
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_demo_view;
    }

    @Override
    protected void initViews() {
        alipaySuccessView = $(R.id.successview);
        alipayFailureView = (AlipayFailureView) findViewById(R.id.faliureview);
        success= (Button) findViewById(R.id.success);
        failure= (Button) findViewById(R.id.faliur);
        alipaySuccessView.addCircleAnimatorEndListner(new AlipaySuccessView.OnCircleFinishListener() {
            @Override
            public void onCircleDone() {
                success.setText("支付成功");
                alipaySuccessView.setPaintColor(Color.GREEN);
            }
        });
        alipayFailureView.addCircleAnimatorEndListner(new AlipayFailureView.OnCircleFinishListener() {
            @Override
            public void onCircleDone() {
                failure.setText("支付失败");
                alipayFailureView.setPaintColor(Color.RED);
            }
        });
        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alipaySuccessView.loadCircle(radius1 / 2);
            }
        });
        failure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alipayFailureView.startAnim(radius2 / 2);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
