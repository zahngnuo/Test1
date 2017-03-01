package pdatest;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

import baiduanimation.R;
import shopcartest.bean.utils.BaseActivity;
import weight.StockTextView;

/**
 * desc:上架任务详情
 * author:张肖换
 * date:${Date}
 */

public class GoodShelvesInfoAct extends BaseActivity {
    private ImageView clear, actual_clear;
    private EditText edit_number, number;
    private Button commitBtn;
    private StockTextView text_name, text_the_shelves;

    @Override
    protected int loadLayoutId() {
        return R.layout.act_good_info;
    }

    @Override
    protected void initViews() {
        clear = $(R.id.clear);
        actual_clear = $(R.id.image_clear);
        edit_number = $(R.id.edit_number);
        number = $(R.id.number);
        commitBtn = $(R.id.commitBtn);
        text_name = $(R.id.text_name);
        text_the_shelves = $(R.id.text_the_shelves);
    }

    @Override
    protected void initData() {
        setTitleStr("上架任务详情");
        loadDate();
    }

    //获取数据
    private void loadDate() {
        RequestParams rp = new RequestParams("");
        HashMap<String, String> param = new HashMap<>();
        param.put("", "");
        param.put("", "");
        x.http().post(rp, new Callback.CacheCallback<Object>() {

            @Override
            public void onSuccess(Object result) {
                text_name.setCenterTxt("A01-001-1");
                text_the_shelves.setCenterTxt("120");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(Object result) {
                return false;
            }
        });
    }

    @Override
    protected void bindListener() {
        super.bindListener();
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_number.setText("");
            }
        });
        actual_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText("");
            }
        });
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断输入的仓位是否和目标仓位一致
                if (!"A01-001-1".equals(edit_number.getText().toString())) {
                    //弹出对话框
                }
            }
        });
    }

    private void popWindow() {

    }
}
