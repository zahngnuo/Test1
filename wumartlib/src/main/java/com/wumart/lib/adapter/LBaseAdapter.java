//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wumart.lib.adapter;

import android.animation.Animator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout.LayoutParams;

import com.wumart.lib.anim.BaseAnimation;
import com.wumart.lib.anim.SlideInBottomAnimation;
import com.wumart.lib.common.ArrayUtils;
import com.wumart.lib.common.CommonUtils;
import com.wumart.lib.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;

public abstract class LBaseAdapter<T> extends Adapter<BaseHolder> {
    private boolean mOpenAnimationEnable;
    private Interpolator mInterpolator;
    private int mDuration;
    private int mLastPosition;
    private int mLastDataSize;
    private BaseAnimation mBaseAnimation;
    protected View mHeaderView;
    protected View mFooterView;
    protected EmptyView mEmptyView;
    protected boolean mShowFooterView;
    protected int mLayoutResId;
    protected List<T> mDatas;
    protected static final int HEADER_VIEW = 273;
    protected static final int FOOTER_VIEW = 819;
    protected static final int EMPTY_VIEW = 1365;
    protected boolean mFirstOnlyEnable;
    protected LayoutInflater mLayoutInflater;
    protected LayoutParams layoutParams;
    private LBaseAdapter.OnRecyclerItemChildClickListener mChildClickListener;

    public void setOnRecyclerItemChildClickListener(LBaseAdapter.OnRecyclerItemChildClickListener childClickListener) {
        this.mChildClickListener = childClickListener;
    }

    public LBaseAdapter() {
        this(0);
    }

    public LBaseAdapter(int layoutResId) {
        this(layoutResId, new ArrayList());
    }

    public LBaseAdapter(int layoutResId, List<T> datas) {
        this.mOpenAnimationEnable = true;
        this.mInterpolator = new LinearInterpolator();
        this.mDuration = 300;
        this.mLastPosition = -1;
        this.mFirstOnlyEnable = true;
        this.mDatas = datas;
        this.mLayoutResId = layoutResId;
        this.mBaseAnimation = new SlideInBottomAnimation();
    }

    public void addItems(List<T> items) {
        if(ArrayUtils.isEmpty(items)) {
            this.mLastDataSize = 0;
        } else {
            this.mLastDataSize = items.size();
            int oldCont = this.mDatas.size();
            this.mDatas.addAll(items);
            if(oldCont == 0) {
                this.notifyDataSetChanged();
            } else {
                this.notifyItemRangeChanged(oldCont, this.mDatas.size());
            }

        }
    }

    public void addItem(T item) {
        this.mDatas.add(item);
        this.notifyItemInserted(this.mDatas.size());
    }

    public void addFirstItem(T item) {
        if(ArrayUtils.isNotEmpty(this.mDatas)) {
            this.mDatas.add(0, item);
            this.notifyItemInserted(0);
        } else {
            this.addItem(item);
        }
    }

    public void addItems(List<T> items, boolean isRefresh) {
        if(isRefresh) {
            this.mDatas.clear();
            this.notifyDataSetChanged();
        }

        this.addItems(items);
    }

    public T getItem(int position) {
        return this.mDatas.get(position);
    }

    public int getHeaderViewsCount() {
        return this.mHeaderView == null?0:1;
    }

    public int getFooterViewsCount() {
        return this.mFooterView == null?0:1;
    }

    public int getmEmptyViewCount() {
        return this.mEmptyView == null?0:1;
    }

    public int getItemCount() {
        int count = this.mDatas.size() + this.getHeaderViewsCount() + this.getFooterViewsCount();
        if(this.getHeaderViewsCount() == 1 && count == 1 || count == 0) {
            count += this.getmEmptyViewCount();
        }

        return count;
    }

    public int getItemViewType(int position) {
        return this.mHeaderView != null && position == 0?273:(this.mEmptyView != null && this.mDatas.size() == 0 && !this.mShowFooterView?1365:(position == this.mDatas.size() + this.getHeaderViewsCount() && this.mFooterView != null?819:this.getDefItemViewType(position)));
    }

    public void setShowFooterView(boolean showFooterView) {
        this.mShowFooterView = showFooterView;
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder baseViewHolder;
        switch(viewType) {
            case 273:
                baseViewHolder = new BaseHolder(this.mHeaderView);
                break;
            case 819:
                baseViewHolder = new BaseHolder(this.mFooterView);
                break;
            case 1365:
                baseViewHolder = new BaseHolder(this.mEmptyView);
                break;
            default:
                baseViewHolder = this.onCreateDefViewHolder(parent, viewType);
        }

        return baseViewHolder;
    }

    public void onBindViewHolder(BaseHolder baseHolder, int positions) {
        switch(baseHolder.getItemViewType()) {
            case 273:
            case 819:
                break;
            case 1365:
                this.showEmptyView();
                break;
            default:
                int pos = this.getRealPosition(baseHolder);
                baseHolder.itemView.setTag(Integer.valueOf(pos));
                this.onBindItem(baseHolder, pos, this.mDatas.get(pos));
                this.addAnimation(baseHolder);
        }

    }

    protected void showEmptyView() {
        if(this.layoutParams == null && this.mHeaderView != null && this.mHeaderView.getParent() instanceof RecyclerView) {
            RecyclerView networkAvailable = (RecyclerView)this.mHeaderView.getParent();
            this.layoutParams = new LayoutParams(-1, networkAvailable.getHeight() - this.mHeaderView.getHeight());
            this.mEmptyView.setLayoutParams(this.layoutParams);
        }

        boolean networkAvailable1 = CommonUtils.isNetworkAvailable(this.mEmptyView.getContext());
        if(networkAvailable1) {
            this.mEmptyView.showEmptyView();
        } else {
            this.mEmptyView.showNetWorkError();
        }

    }

    public int getRealPosition(ViewHolder holder) {
        return holder.getLayoutPosition() - this.getHeaderViewsCount();
    }

    protected BaseHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return this.createBaseViewHolder(parent, this.mLayoutResId);
    }

    protected BaseHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        BaseHolder baseViewHolder = new BaseHolder(this.getItemView(layoutResId, parent));
        baseViewHolder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Object vTag = view.getTag();
                if(vTag != null && vTag instanceof Integer) {
                    int position = Integer.valueOf(vTag.toString()).intValue();
                    LBaseAdapter.this.onItemClick(LBaseAdapter.this.mDatas.get(position), position);
                }

            }
        });
        return baseViewHolder;
    }

    protected void onItemClick(T t, int position) {
    }

    public void addHeaderView(View header) {
        if(header == null) {
            throw new RuntimeException("header is null");
        } else {
            this.mHeaderView = header;
            this.notifyDataSetChanged();
        }
    }

    public void addFooterView(View footer) {
        this.addFooterView(footer, false);
    }

    public void addFooterView(View footer, boolean showFooterView) {
        if(footer == null) {
            throw new RuntimeException("footer is null");
        } else {
            this.mFooterView = footer;
            this.mShowFooterView = showFooterView;
            this.notifyDataSetChanged();
        }
    }

    public void setEmptyView(EmptyView emptyView) {
        this.mEmptyView = emptyView;
    }

    public void setFirstOnlyEnable(boolean firstOnlyEnable) {
        this.mFirstOnlyEnable = firstOnlyEnable;
    }

    public EmptyView getEmptyView() {
        return this.mEmptyView;
    }

    protected void addAnimation(ViewHolder holder) {
        if(this.mOpenAnimationEnable && (!this.mFirstOnlyEnable || holder.getLayoutPosition() > this.mLastPosition)) {
            Animator[] var2 = this.mBaseAnimation.getAnimators(holder.itemView);
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Animator anim = var2[var4];
                anim.setDuration((long)this.mDuration).start();
                anim.setInterpolator(this.mInterpolator);
            }

            this.mLastPosition = holder.getLayoutPosition();
        }

    }

    protected View getItemView(int layoutResId, ViewGroup parent) {
        if(this.mLayoutInflater == null) {
            this.mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        return this.mLayoutInflater.inflate(layoutResId, parent, false);
    }

    public void setBaseAnimation(BaseAnimation baseAnimation) {
        this.mOpenAnimationEnable = true;
        this.mBaseAnimation = baseAnimation;
    }

    public void setOpenAnimationEnable(boolean openAnimationEnable) {
        this.mOpenAnimationEnable = openAnimationEnable;
    }

    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }

    public abstract void onBindItem(BaseHolder var1, int var2, T var3);

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager)manager;
            gridManager.setSpanSizeLookup(new SpanSizeLookup() {
                public int getSpanSize(int position) {
                    return LBaseAdapter.this.getSpanCount(position, gridManager.getSpanCount());
                }
            });
        }

    }

    protected int getSpanCount(int position, int defCount) {
        return !this.isHeaderView(position) && !this.isBottomView(position) && !ArrayUtils.isEmpty(this.mDatas)?1:defCount;
    }

    public boolean isHeaderView(int position) {
        return this.getHeaderViewsCount() != 0 && position < this.getHeaderViewsCount();
    }

    public boolean isBottomView(int position) {
        return this.getFooterViewsCount() != 0 && position >= this.getHeaderViewsCount() + this.mDatas.size();
    }

    public long getItemId(int position) {
        return (long)position;
    }

    public boolean isEmpty() {
        return ArrayUtils.isEmpty(this.mDatas);
    }

    public void clearDatas() {
        if(!this.isEmpty()) {
            this.mDatas.clear();
            this.notifyDataSetChanged();
        }

    }

    public int getDataSize() {
        return ArrayUtils.isEmpty(this.mDatas)?0:this.mDatas.size();
    }

    public boolean isLast(int position) {
        return this.mDatas.size() - 1 == position;
    }

    public int getLastDataSize() {
        return this.mLastDataSize;
    }

    public class OnItemChildClickListener implements OnClickListener {
        public int position;

        public OnItemChildClickListener() {
        }

        public void onClick(View v) {
            if(LBaseAdapter.this.mChildClickListener != null) {
                LBaseAdapter.this.mChildClickListener.onItemChildClick(v, this.position - LBaseAdapter.this.getHeaderViewsCount());
            }

        }
    }

    public interface OnRecyclerItemChildClickListener {
        void onItemChildClick(View var1, int var2);
    }
}
