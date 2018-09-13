package com.github.jdsjlzx.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.view.ArrowRefreshHeader;
import java.util.ArrayList;
import java.util.List;

public class LRecyclerViewAdapter extends Adapter<android.support.v7.widget.RecyclerView.ViewHolder> {
    private static final int HEADER_INIT_INDEX = 10002;
    private static final int TYPE_FOOTER_VIEW = 10001;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_REFRESH_HEADER = 10000;
    private static List<Integer> mHeaderTypes = new ArrayList();
    private ArrayList<View> mFooterViews = new ArrayList();
    private ArrayList<View> mHeaderViews = new ArrayList();
    private Adapter mInnerAdapter;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private ArrowRefreshHeader mRefreshHeader;
    private ViewRecycledListener mViewRecycledListener;

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface ViewRecycledListener {
        void onViewRecycled(int i);
    }

    public LRecyclerViewAdapter(Adapter innerAdapter) {
        this.mInnerAdapter = innerAdapter;
    }

    public void setRefreshHeader(ArrowRefreshHeader refreshHeader) {
        this.mRefreshHeader = refreshHeader;
    }

    public Adapter getInnerAdapter() {
        return this.mInnerAdapter;
    }

    public void addHeaderView(View view) {
        if (view == null) {
            throw new RuntimeException("header is null");
        }
        mHeaderTypes.add(Integer.valueOf(this.mHeaderViews.size() + 10002));
        this.mHeaderViews.add(view);
    }

    public void addFooterView(View view) {
        if (view == null) {
            throw new RuntimeException("footer is null");
        }
        if (getFooterViewsCount() > 0) {
            removeFooterView(getFooterView());
        }
        this.mFooterViews.add(view);
    }

    private View getHeaderViewByType(int itemType) {
        if (isHeaderType(itemType)) {
            return (View) this.mHeaderViews.get(itemType - 10002);
        }
        return null;
    }

    private boolean isHeaderType(int itemViewType) {
        return this.mHeaderViews.size() > 0 && mHeaderTypes.contains(Integer.valueOf(itemViewType));
    }

    public View getFooterView() {
        return getFooterViewsCount() > 0 ? (View) this.mFooterViews.get(0) : null;
    }

    public View getHeaderView() {
        return getHeaderViewsCount() > 0 ? (View) this.mHeaderViews.get(0) : null;
    }

    public ArrayList<View> getHeaderViews() {
        return this.mHeaderViews;
    }

    public void removeHeaderView(View view) {
        this.mHeaderViews.remove(view);
        notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        this.mFooterViews.remove(view);
        notifyDataSetChanged();
    }

    public int getHeaderViewsCount() {
        return this.mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return this.mFooterViews.size();
    }

    public boolean isHeader(int position) {
        return position >= 1 && position < this.mHeaderViews.size() + 1;
    }

    public boolean isRefreshHeader(int position) {
        return position == 0;
    }

    public boolean isFooter(int position) {
        return getFooterViewsCount() > 0 && position >= getItemCount() - getFooterViewsCount();
    }

    public android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_REFRESH_HEADER) {
            return new ViewHolder(this.mRefreshHeader);
        }
        if (isHeaderType(viewType)) {
            return new ViewHolder(getHeaderViewByType(viewType));
        }
        if (viewType == 10001) {
            return new ViewHolder((View) this.mFooterViews.get(0));
        }
        return this.mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    public void onBindViewHolder(final android.support.v7.widget.RecyclerView.ViewHolder holder, int position) {
        if (!isHeader(position) && !isRefreshHeader(position)) {
            final int adjPosition = position - (getHeaderViewsCount() + 1);
            if (this.mInnerAdapter != null && adjPosition < this.mInnerAdapter.getItemCount()) {
                this.mInnerAdapter.onBindViewHolder(holder, adjPosition);
                if (this.mOnItemClickListener != null) {
                    holder.itemView.setOnClickListener(new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            LRecyclerViewAdapter.this.mOnItemClickListener.onItemClick(holder.itemView, adjPosition);
                        }
                    });
                }
                if (this.mOnItemLongClickListener != null) {
                    holder.itemView.setOnLongClickListener(new OnLongClickListener() {
                        public boolean onLongClick(View v) {
                            LRecyclerViewAdapter.this.mOnItemLongClickListener.onItemLongClick(holder.itemView, adjPosition);
                            return true;
                        }
                    });
                }
            }
        }
    }

    public void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else if (!isHeader(position) && !isRefreshHeader(position)) {
            int adjPosition = position - (getHeaderViewsCount() + 1);
            if (this.mInnerAdapter != null && adjPosition < this.mInnerAdapter.getItemCount()) {
                this.mInnerAdapter.onBindViewHolder(holder, adjPosition, payloads);
            }
        }
    }

    public int getItemCount() {
        if (this.mInnerAdapter != null) {
            return ((getHeaderViewsCount() + getFooterViewsCount()) + this.mInnerAdapter.getItemCount()) + 1;
        }
        return (getHeaderViewsCount() + getFooterViewsCount()) + 1;
    }

    public int getItemViewType(int position) {
        int adjPosition = position - (getHeaderViewsCount() + 1);
        if (isRefreshHeader(position)) {
            return TYPE_REFRESH_HEADER;
        }
        if (isHeader(position)) {
            return ((Integer) mHeaderTypes.get(position - 1)).intValue();
        } else if (isFooter(position)) {
            return 10001;
        } else {
            if (this.mInnerAdapter == null || adjPosition >= this.mInnerAdapter.getItemCount()) {
                return 0;
            }
            return this.mInnerAdapter.getItemViewType(adjPosition);
        }
    }

    public long getItemId(int position) {
        if (this.mInnerAdapter != null && position >= getHeaderViewsCount()) {
            int adjPosition = position - getHeaderViewsCount();
            if (adjPosition < this.mInnerAdapter.getItemCount()) {
                return this.mInnerAdapter.getItemId(adjPosition);
            }
        }
        return -1;
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new SpanSizeLookup() {
                public int getSpanSize(int position) {
                    return (LRecyclerViewAdapter.this.isHeader(position) || LRecyclerViewAdapter.this.isFooter(position) || LRecyclerViewAdapter.this.isRefreshHeader(position)) ? gridManager.getSpanCount() : 1;
                }
            });
        }
        this.mInnerAdapter.onAttachedToRecyclerView(recyclerView);
    }

    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.mInnerAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    public void onViewAttachedToWindow(android.support.v7.widget.RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && (lp instanceof StaggeredGridLayoutManager.LayoutParams) && (isHeader(holder.getLayoutPosition()) || isRefreshHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
            ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
        }
        this.mInnerAdapter.onViewAttachedToWindow(holder);
    }

    public void onViewDetachedFromWindow(android.support.v7.widget.RecyclerView.ViewHolder holder) {
        this.mInnerAdapter.onViewDetachedFromWindow(holder);
    }

    public void onViewRecycled(android.support.v7.widget.RecyclerView.ViewHolder holder) {
        this.mInnerAdapter.onViewRecycled(holder);
        if (this.mViewRecycledListener != null) {
            this.mViewRecycledListener.onViewRecycled(holder.getAdapterPosition());
        }
    }

    public void setViewRecycledListener(ViewRecycledListener mViewRecycledListener) {
        this.mViewRecycledListener = mViewRecycledListener;
    }

    public int getAdapterPosition(boolean isCallback, int position) {
        if (!isCallback) {
            return (getHeaderViewsCount() + position) + 1;
        }
        int adjPosition = position - (getHeaderViewsCount() + 1);
        return adjPosition < this.mInnerAdapter.getItemCount() ? adjPosition : -1;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.mOnItemLongClickListener = itemLongClickListener;
    }
}
