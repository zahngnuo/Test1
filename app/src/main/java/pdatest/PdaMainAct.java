package pdatest;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.widget.ImageView;

import com.wumart.lib.adapter.BaseHolder;
import com.wumart.lib.adapter.LBaseAdapter;
import com.wumart.lib.common.StrUtils;

import baiduanimation.R;
import base.BaseRecyclerActivity;
import utils.GridItemDecoration;
import utils.ImageLoader;

/**
 * desc:PDA项目的主页面
 * author:张肖换
 * date:${Date}
 */

public class PdaMainAct extends BaseRecyclerActivity implements AppBarLayout.OnOffsetChangedListener, Handler.Callback {
    private Handler mHandler;

    @Override
    protected int loadLayoutId() {
        return R.layout.act_pda_act;
    }

    @Override
    protected void initData() {
        super.initData();
        setTitleStr("物美店铺PDA系统");
        mHandler = new Handler(this);
        mColumn = 4;//由三列改为四列
        mRecyclerView.addItemDecoration(new GridItemDecoration(this, true));
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    protected LBaseAdapter<MenuInfoBean> getLBaseAdapter() {
        return new LBaseAdapter<MenuInfoBean>(R.layout.item_pda_main) {
            @Override
            public void onBindItem(final BaseHolder baseHolder, int realPosition, final MenuInfoBean infoBean) {
                if (!TextUtils.isEmpty(infoBean.getMenuName()) && !TextUtils.isEmpty(infoBean.getMenuIcon())) {//菜单item
                    baseHolder.setText(R.id.tv_item, infoBean.getMenuName());
                    ImageLoader.loadImage(baseHolder.getView(R.id.iv_item, ImageView.class), infoBean.getMenuIcon());
                } else if (TextUtils.isEmpty(infoBean.getMenuName()) && TextUtils.isEmpty(infoBean.getMenuIcon())) {
                    baseHolder.setText(R.id.tv_item, "").setImageBitmap(R.id.iv_item, null);//填充空白grid
                }
            }

            @Override
            protected void onItemClick(MenuInfoBean infoBean, int position) {
                super.onItemClick(infoBean, position);
                if (!StrUtils.isEmpty(infoBean.getId())) {
                    toPage(infoBean);
                }
            }
        };

    }

    private void toPage(MenuInfoBean infoBean) {
        switch (infoBean.getId()) {
            case Constants.GOODS_SHELVES:
                startActivity(new Intent(PdaMainAct.this, Goodsshelves.class));
                break;
            case  Constants.STORE_REPLENISHMENT:
                startActivity(new Intent(PdaMainAct.this,StoreReplenishmentAct.class));
                break;
            case  Constants.INVENTORY:
                startActivity(new Intent(PdaMainAct.this,InventoryAct.class));
                break;
            case  Constants.QUERY:
                startActivity(new Intent(PdaMainAct.this,QueryAct.class));
                break;
            case  Constants.LOCATION_ADJUSTMENT:
                startActivity(new Intent(PdaMainAct.this,LocationAdjustmentAct.class));
                break;
            case  Constants.THE_NEW_LOCATION:
                startActivity(new Intent(PdaMainAct.this,TheNewLocationAct.class));
                break;

        }
    }

    @Override
    public void onBGARefresh(boolean var1) {

    }

}