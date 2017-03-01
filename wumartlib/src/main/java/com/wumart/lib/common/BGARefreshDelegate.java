package com.wumart.lib.common;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wumart.lib.R;

import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * User: 吕勇
 * Date: 2016-05-04
 * Time: 09:18
 * Description:下拉刷新使用
 */
public class BGARefreshDelegate implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private boolean mIsMore;
    private BGARefreshDelegate.BGARefreshListener mRefreshListener;
    private int mCurrentPageNo;
    private int mPageSize;
    private int mLastDataSize;
    private BGARefreshDelegate.OnLoadedListener mLoadedListener;

    public BGARefreshDelegate(BGARefreshLayout refreshLayout, BGARefreshDelegate.BGARefreshListener refreshListener, boolean loadMore) {
        this.mCurrentPageNo = 1;
        this.mPageSize = 20;
        this.mLastDataSize = -1;
        this.initBga(refreshListener, refreshLayout);
        this.mRefreshLayout.setRefreshViewHolder(this.getRefreshViewHolder(refreshLayout.getContext(), loadMore));
    }

    public BGARefreshDelegate(BGARefreshLayout refreshLayout, BGARefreshDelegate.BGARefreshListener refreshListener, RecyclerView recyclerView, boolean loadMore) {
        this(refreshLayout, refreshListener, recyclerView, loadMore, 0);
    }

    public BGARefreshDelegate(BGARefreshLayout refreshLayout, BGARefreshDelegate.BGARefreshListener refreshListener, RecyclerView recyclerView, boolean loadMore, int column) {
        this.mCurrentPageNo = 1;
        this.mPageSize = 20;
        this.mLastDataSize = -1;
        this.initBga(refreshListener, refreshLayout);
        if(null != recyclerView) {
            this.mRefreshLayout.setRefreshViewHolder(this.getBGARefreshViewHolder(recyclerView, loadMore, column));
        } else {
            this.mRefreshLayout.setRefreshViewHolder(this.getRefreshViewHolder(refreshLayout.getContext(), loadMore));
        }

        refreshLayout.setDelegate(this);
    }

    private void initBga(BGARefreshDelegate.BGARefreshListener refreshListener, BGARefreshLayout refreshLayout) {
        try {
            this.mRefreshListener = refreshListener;
            this.mRefreshLayout = refreshLayout;
            if(this.mRefreshListener == null || this.mRefreshLayout == null) {
                throw new Exception("BGARefreshListener或者BGARefreshLayout为空");
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        this.mIsMore = false;
        this.mCurrentPageNo = 1;
        this.mLastDataSize = -1;
        this.mRefreshListener.onBGARefresh(true);
    }

    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        this.mIsMore = true;
        if(this.mLastDataSize != -1 && this.mLastDataSize < this.mPageSize) {
            if(null != this.mLoadedListener) {
                this.mLoadedListener.onLoaded();
            }

            return false;
        } else {
            this.mRefreshListener.onBGARefresh(false);
            return true;
        }
    }

    public void stopRefresh() {
        if(null != this.mRefreshLayout) {
            if(this.mIsMore) {
                this.mRefreshLayout.endLoadingMore();
            } else {
                this.mRefreshLayout.endRefreshing();
            }

        }
    }

    public void stopRefresh(boolean isSuccess, int dataSzie) {
        this.stopRefresh();
        this.mLastDataSize = isSuccess?dataSzie:0;
        this.mCurrentPageNo = isSuccess?this.mCurrentPageNo + 1:this.mCurrentPageNo;
    }

    public BGARefreshViewHolder getBGARefreshViewHolder(RecyclerView recyclerView, boolean loadMore, int column) {
        if(column > 0) {
            recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), column));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), 1, false));
        }

        return this.getRefreshViewHolder(recyclerView.getContext(), loadMore);
    }

    public BGARefreshViewHolder getRefreshViewHolder(Context context, boolean loadMore) {
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(context, loadMore);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.drawable.refresh_01);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_torefreshing);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);
        meiTuanRefreshViewHolder.setLoadingMoreText("");
        return meiTuanRefreshViewHolder;
    }

    public void cleanListener() {
        this.mRefreshListener = null;
    }

    public BGARefreshDelegate setLoadedListener(BGARefreshDelegate.OnLoadedListener loadedListener) {
        this.mLoadedListener = loadedListener;
        return this;
    }

    public int getCurrentPageNo() {
        return this.mCurrentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.mCurrentPageNo = currentPageNo;
    }

    public void setPageSize(int pageSize) {
        this.mPageSize = pageSize;
    }

    public int getPageSize() {
        return this.mPageSize;
    }

    public interface OnLoadedListener {
        void onLoaded();
    }

    public interface BGARefreshListener {
        void onBGARefresh(boolean var1);
    }
}