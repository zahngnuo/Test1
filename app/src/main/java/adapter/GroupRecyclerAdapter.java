package adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wumart.lib.adapter.BaseHolder;
import com.wumart.lib.adapter.LBaseAdapter;
import com.wumart.lib.common.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */

public abstract class GroupRecyclerAdapter<T> extends LBaseAdapter<T> {


    protected int mGroupHeadResId;//组头ID
    protected static final int GROUP_HEAD_VIEW = 0x00000002;

    public GroupRecyclerAdapter(int layoutResId, int groupHeadResId, List<T> data) {
        super(layoutResId, data);
        this.mGroupHeadResId = groupHeadResId;
    }

    public GroupRecyclerAdapter(int layoutResId, int groupHeadResId) {
        this(layoutResId, groupHeadResId, new ArrayList<T>());
    }

    public GroupRecyclerAdapter() {
        this(0, 0);
    }

    @Override
    protected int getDefItemViewType(int position) {
    return  0;
    }

    @Override
    protected BaseHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == GROUP_HEAD_VIEW)
            return new BaseHolder(getItemView(mGroupHeadResId, parent));
        return super.onCreateDefViewHolder(parent, viewType);
    }

    @Override
    public void onBindItem(BaseHolder bHolder, int realPosition, T item) {
        switch (bHolder.getItemViewType()) {
            case GROUP_HEAD_VIEW:
                break;
            default:
                onBindGroupItem(bHolder, realPosition, item);
                break;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        //super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (getItemViewType(position) == GROUP_HEAD_VIEW || ArrayUtils.isEmpty(mDatas)) ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }


    protected abstract void onBindGroupItem(BaseHolder bHolder, int realPosition, T item);
}
