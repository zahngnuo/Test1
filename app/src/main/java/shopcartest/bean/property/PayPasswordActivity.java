package shopcartest.bean.property;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import baiduanimation.R;
import shopcartest.bean.utils.PasswordDialog;

/**
 * desc:支付密码界面
 * author:张肖换
 * date:${Date}
 */
public class PayPasswordActivity extends AppCompatActivity {
    private PasswordDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay_password_a);
        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.main_floationgActionButton);
        dialog = new PasswordDialog(PayPasswordActivity.this);
        dialog.setViewClickListener(new PasswordDialog.ViewClickListener() {
            @Override
            public void click() {
                Toast.makeText(PayPasswordActivity.this, "password is：" + dialog.getPassword(), Toast.LENGTH_SHORT).show();
            }
        });
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        });
    }
}

