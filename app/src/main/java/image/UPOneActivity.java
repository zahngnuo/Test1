package image;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.widget.Button;

import baiduanimation.R;
import shopcartest.bean.utils.BaseActivity;

/**
 * desc:向上的导航Activity
 * author:张肖换
 * date:${Date}
 */

public class UPOneActivity extends BaseActivity {
    private Button  up_btn;
    @Override
    protected int loadLayoutId() {

        return R.layout.act_up_load;
    }

    @Override
    protected void initViews() {
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        up_btn=$(R.id.up_home);
    }
    @Override
    protected void initData() {
    setTitleStr("向上导航菜单");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.up_home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // 这个activity不是这个app任务的一部分, 所以当向上导航时创建
                    // 用合成后退栈(synthesized back stack)创建一个新任务。
                    TaskStackBuilder.create(this)
                            // 添加这个activity的所有父activity到后退栈中
                            .addNextIntentWithParentStack(upIntent)
                            // 向上导航到最近的一个父activity
                            .startActivities();
                } else {
                    // 这个activity是这个app任务的一部分, 所以
                    // 向上导航至逻辑父activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
