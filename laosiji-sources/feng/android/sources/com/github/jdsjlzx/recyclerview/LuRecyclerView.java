package com.github.jdsjlzx.recyclerview;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.AppBarStateChangeListener.State;
import com.github.jdsjlzx.view.LoadingFooter;

public class LuRecyclerView extends RecyclerView {
    private static final int HIDE_THRESHOLD = 20;
    private State appbarState;
    private int currentScrollState;
    private int[] lastPositions;
    private int lastVisibleItemPosition;
    protected LayoutManagerType layoutManagerType;
    private final AdapterDataObserver mDataObserver;
    private int mDistance;
    private View mEmptyView;
    private View mFootView;
    private boolean mIsScrollDown;
    private LScrollListener mLScrollListener;
    private OnLoadMoreListener mLoadMoreListener;
    private int mScrolledXDistance;
    private int mScrolledYDistance;
    private LuRecyclerViewAdapter mWrapAdapter;

    private class DataObserver extends AdapterDataObserver {
        private DataObserver() {
        }

        /* synthetic */ DataObserver(LuRecyclerView x0, AnonymousClass1 x1) {
            this();
        }

        public void onChanged() {
            Adapter<?> adapter = LuRecyclerView.this.getAdapter();
            if (adapter instanceof LRecyclerViewAdapter) {
                LRecyclerViewAdapter headerAndFooterAdapter = (LRecyclerViewAdapter) adapter;
                if (!(headerAndFooterAdapter.getInnerAdapter() == null || LuRecyclerView.this.mEmptyView == null)) {
                    if (headerAndFooterAdapter.getInnerAdapter().getItemCount() == 0) {
                        LuRecyclerView.this.mEmptyView.setVisibility(0);
                        LuRecyclerView.this.setVisibility(8);
                    } else {
                        LuRecyclerView.this.mEmptyView.setVisibility(8);
                        LuRecyclerView.this.setVisibility(0);
                    }
                }
            } else if (!(adapter == null || LuRecyclerView.this.mEmptyView == null)) {
                if (adapter.getItemCount() == 0) {
                    LuRecyclerView.this.mEmptyView.setVisibility(0);
                    LuRecyclerView.this.setVisibility(8);
                } else {
                    LuRecyclerView.this.mEmptyView.setVisibility(8);
                    LuRecyclerView.this.setVisibility(0);
                }
            }
            if (LuRecyclerView.this.mWrapAdapter != null) {
                LuRecyclerView.this.mWrapAdapter.notifyDataSetChanged();
            }
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            LuRecyclerView.this.mWrapAdapter.notifyItemRangeChanged(LuRecyclerView.this.mWrapAdapter.getHeaderViewsCount() + positionStart, itemCount);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            LuRecyclerView.this.mWrapAdapter.notifyItemRangeInserted(LuRecyclerView.this.mWrapAdapter.getHeaderViewsCount() + positionStart, itemCount);
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            LuRecyclerView.this.mWrapAdapter.notifyItemRangeRemoved(LuRecyclerView.this.mWrapAdapter.getHeaderViewsCount() + positionStart, itemCount);
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            int headerViewsCountCount = LuRecyclerView.this.mWrapAdapter.getHeaderViewsCount();
            LuRecyclerView.this.mWrapAdapter.notifyItemRangeChanged(fromPosition + headerViewsCountCount, (toPosition + headerViewsCountCount) + itemCount);
        }
    }

    public interface LScrollListener {
        void onScrollDown();

        void onScrollStateChanged(int i);

        void onScrollUp();

        void onScrolled(int i, int i2);
    }

    public enum LayoutManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }

    public LuRecyclerView(Context context) {
        this(context, null);
    }

    public LuRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mDataObserver = new DataObserver(this, null);
        this.currentScrollState = 0;
        this.mDistance = 0;
        this.mIsScrollDown = true;
        this.mScrolledYDistance = 0;
        this.mScrolledXDistance = 0;
        this.appbarState = State.EXPANDED;
        init();
    }

    private void init() {
        this.mFootView = new LoadingFooter(getContext());
        this.mFootView.setVisibility(8);
    }

    public void setAdapter(Adapter adapter) {
        this.mWrapAdapter = (LuRecyclerViewAdapter) adapter;
        super.setAdapter(this.mWrapAdapter);
        this.mWrapAdapter.getInnerAdapter().registerAdapterDataObserver(this.mDataObserver);
        this.mDataObserver.onChanged();
        this.mWrapAdapter.addFooterView(this.mFootView);
    }

    private int findMax(int[] lastPositions) {
        int i = 0;
        int max = lastPositions[0];
        int length = lastPositions.length;
        while (i < length) {
            int value = lastPositions[i];
            if (value > max) {
                max = value;
            }
            i++;
        }
        return max;
    }

    private int findMin(int[] firstPositions) {
        int i = 0;
        int min = firstPositions[0];
        int length = firstPositions.length;
        while (i < length) {
            int value = firstPositions[i];
            if (value < min) {
                min = value;
            }
            i++;
        }
        return min;
    }

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mLoadMoreListener = listener;
    }

    public void setLScrollListener(LScrollListener listener) {
        this.mLScrollListener = listener;
    }

    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        int firstVisibleItemPosition = 0;
        LayoutManager layoutManager = getLayoutManager();
        if (this.layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                this.layoutManagerType = LayoutManagerType.LinearLayout;
            } else if (layoutManager instanceof GridLayoutManager) {
                this.layoutManagerType = LayoutManagerType.GridLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                this.layoutManagerType = LayoutManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }
        switch (this.layoutManagerType) {
            case LinearLayout:
                firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                this.lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GridLayout:
                firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                this.lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (this.lastPositions == null) {
                    this.lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(this.lastPositions);
                this.lastVisibleItemPosition = findMax(this.lastPositions);
                staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(this.lastPositions);
                firstVisibleItemPosition = findMax(this.lastPositions);
                break;
        }
        calculateScrollUpOrDown(firstVisibleItemPosition, dy);
        this.mScrolledXDistance += dx;
        this.mScrolledYDistance += dy;
        this.mScrolledXDistance = this.mScrolledXDistance < 0 ? 0 : this.mScrolledXDistance;
        this.mScrolledYDistance = this.mScrolledYDistance < 0 ? 0 : this.mScrolledYDistance;
        if (this.mIsScrollDown && dy == 0) {
            this.mScrolledYDistance = 0;
        }
        if (this.mLScrollListener != null) {
            this.mLScrollListener.onScrolled(this.mScrolledXDistance, this.mScrolledYDistance);
        }
    }

    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        this.currentScrollState = state;
        if (this.mLScrollListener != null) {
            this.mLScrollListener.onScrollStateChanged(state);
        }
        if (this.mLoadMoreListener == null) {
            return;
        }
        if (this.currentScrollState == 0 || this.currentScrollState == 2) {
            LayoutManager layoutManager = getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (visibleItemCount > 0 && this.lastVisibleItemPosition >= totalItemCount - 1 && totalItemCount > visibleItemCount) {
                this.mLoadMoreListener.onLoadMore();
            }
        }
    }

    private void calculateScrollUpOrDown(int firstVisibleItemPosition, int dy) {
        if (this.mLScrollListener != null) {
            if (firstVisibleItemPosition == 0) {
                if (!this.mIsScrollDown) {
                    this.mIsScrollDown = true;
                    this.mLScrollListener.onScrollDown();
                }
            } else if (this.mDistance > 20 && this.mIsScrollDown) {
                this.mIsScrollDown = false;
                this.mLScrollListener.onScrollUp();
                this.mDistance = 0;
            } else if (this.mDistance < -20 && !this.mIsScrollDown) {
                this.mIsScrollDown = true;
                this.mLScrollListener.onScrollDown();
                this.mDistance = 0;
            }
        }
        if ((this.mIsScrollDown && dy > 0) || (!this.mIsScrollDown && dy < 0)) {
            this.mDistance += dy;
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        AppBarLayout appBarLayout = null;
        ViewParent p = getParent();
        while (p != null && !(p instanceof CoordinatorLayout)) {
            p = p.getParent();
        }
        if (p instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) p;
            for (int i = coordinatorLayout.getChildCount() - 1; i >= 0; i--) {
                View child = coordinatorLayout.getChildAt(i);
                if (child instanceof AppBarLayout) {
                    appBarLayout = (AppBarLayout) child;
                    break;
                }
            }
            if (appBarLayout != null) {
                appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
                    public void onStateChanged(AppBarLayout appBarLayout, State state) {
                        LuRecyclerView.this.appbarState = state;
                    }
                });
            }
        }
    }
}
