package pdatest;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.wumart.lib.common.StrUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

import baiduanimation.R;
import shopcartest.bean.utils.BaseActivity;

/**
 * desc:商品上架
 * author:张肖换
 * date:${Date}
 */

public class Goodsshelves extends BaseActivity {
    private Button commitBtn;
    private EditText editText;
    private ImageView clear;

    @Override
    protected int loadLayoutId() {
        return R.layout.act_goods_shelves_act;
    }

    @Override
    protected void initViews() {
        commitBtn = $(R.id.commitBtn);
        editText = $(R.id.id_clare);
        clear = $(R.id.clear);
    }

    @Override
    protected void initData() {
        setTitleStr("商品上架");
    }

    @Override
    protected void bindListener() {
        super.bindListener();
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StrUtils.isNotEmpty(editText.getText().toString())) {
                    //此处对后台数据进行链接，
                    loadDate();
                } else {
                    showFailToast("请输入商品编号，商品编号不能为null");
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StrUtils.isNotEmpty(editText.getText().toString())) {
                    editText.setText("");
                }
            }
        });
    }

    //链接数据
    private void loadDate() {
        RequestParams rp = new RequestParams(Constants.APP_UPDATE);
        HashMap<String, String> params = new HashMap<>();
        params.put("", "");
        params.put("", "");
        params.put("", "");
        rp.setBodyContent(new Gson().toJson(params));
        x.http().post(rp, new Callback.CacheCallback<Object>() {

            @Override
            public void onSuccess(Object result) {
                if (result != null) {
                 startActivity(new Intent(getActivity(),GoodShelvesInfoAct.class));
                    Goodsshelves.this.finish();
                } else {
                    showFailToast("未找到上架任务");
                }
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
}
