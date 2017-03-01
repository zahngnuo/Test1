package base;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;

import com.wumart.lib.adapter.LBaseAdapter;
import com.wumart.lib.common.BGARefreshDelegate;
import com.wumart.lib.widget.EmptyView;

import java.util.List;
import java.util.Map;

import baiduanimation.R;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import shopcartest.bean.utils.BaseActivity;
import utils.GridItemDecoration;


/**
 * desc:
 * author:张肖换
 * date:${Date}
 */

public abstract class BaseRecyclerActivity<T> extends BaseActivity implements BGARefreshDelegate.BGARefreshListener {
    protected BGARefreshLayout mBGARefreshLayout;
    protected RecyclerView mRecyclerView;
    protected LBaseAdapter<T> mBaseAdapter;
    protected EmptyView mEmptyView;
    protected BGARefreshDelegate mDelegate;
    protected boolean mLoadMore = false;
    protected int mColumn = 0;

    public BaseRecyclerActivity() {
    }

    protected int loadLayoutId() {
        return R.layout.common_title_recyclerview;
    }

    protected void initViews() {
        this.mBGARefreshLayout = (BGARefreshLayout)this.$(R.id.common_recyclerview_refresh);
        this.mRecyclerView = (RecyclerView)this.$(R.id.common_recyclerview);
    }

    protected void bindListener() {
        this.mBGARefreshLayout.setDelegate(this.mDelegate);
    }

    protected void processLogic() {
        this.mBGARefreshLayout.beginRefreshing();
    }

    protected void initData() {
        this.mDelegate = new BGARefreshDelegate(this.mBGARefreshLayout, this, this.mRecyclerView, this.mLoadMore, this.mColumn);
        if(this.mRecyclerView != null) {
            this.mBaseAdapter = this.getLBaseAdapter();
            if(null != this.mBaseAdapter) {
                this.mRecyclerView.setAdapter(this.mBaseAdapter);
            }

            if(this.mColumn > 0) {
                this.mRecyclerView.addItemDecoration(new GridItemDecoration(this));
            }
        }

        if(this.mBaseAdapter != null) {
            this.mEmptyView = new EmptyView(this);
            this.mBaseAdapter.setEmptyView(this.mEmptyView);
        }

    }

    protected abstract LBaseAdapter<T> getLBaseAdapter();

    protected void addItems(List<T> items) {
        this.addItems(items, true);
    }

    protected void addItems(List<T> items, boolean isRefresh) {
        if(this.mBaseAdapter != null) {
            this.mBaseAdapter.addItems(items, isRefresh);
        }

    }

    protected void stopRefreshing() {
        if(null != this.mDelegate) {
            this.mDelegate.stopRefresh();
        }

        if(this.mBaseAdapter != null && this.mBaseAdapter.isEmpty()) {
            this.mBaseAdapter.notifyDataSetChanged();
        }

    }

    protected void stopRefreshOrMore(boolean isSuccess) {
        if(null != this.mDelegate && this.mBaseAdapter != null) {
            this.mDelegate.stopRefresh(isSuccess, this.mBaseAdapter.getLastDataSize());
        }

        if(this.mBaseAdapter != null && this.mBaseAdapter.isEmpty()) {
            this.mBaseAdapter.notifyDataSetChanged();
        }

    }

    protected void onDestroy() {
        if(null != this.mDelegate) {
            this.mDelegate.cleanListener();
        }

        this.mBGARefreshLayout = null;
        this.mRecyclerView = null;
        this.mBaseAdapter = null;
        this.mEmptyView = null;
        this.mDelegate = null;
        super.onDestroy();
    }

    protected Map<String, Object> getPageMap() {
        return this.getPageMap(new ArrayMap());
    }

    protected Map<String, Object> getPageMap(Map<String, Object> map) {
        if(map == null) {
            map = new ArrayMap();
        }

        if(this.mDelegate == null) {
            return (Map)map;
        } else {
            ((Map)map).put("pageNo", Integer.valueOf(this.mDelegate.getCurrentPageNo()));
            ((Map)map).put("pageSize", Integer.valueOf(this.mDelegate.getPageSize()));
            return (Map)map;
        }
    }
}
