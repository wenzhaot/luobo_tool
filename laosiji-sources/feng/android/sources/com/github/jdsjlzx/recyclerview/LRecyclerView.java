package com.github.jdsjlzx.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import com.github.jdsjlzx.interfaces.BaseRefreshHeader;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.AppBarStateChangeListener.State;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.ArrowRefreshHeader;
import com.github.jdsjlzx.view.LoadingFooter;

public class LRecyclerView extends RecyclerView {
    private static final float DRAG_RATE = 2.2f;
    private static final int HIDE_THRESHOLD = 20;
    private State appbarState;
    private int currentScrollState;
    private boolean isNoMore;
    private int[] lastPositions;
    private int lastVisibleItemPosition;
    protected LayoutManagerType layoutManagerType;
    private VideoChangeListener mChangeListener;
    private final AdapterDataObserver mDataObserver;
    private int mDistance;
    private View mEmptyView;
    private View mFootView;
    private boolean mIsChoiceness;
    private boolean mIsOwner;
    private boolean mIsScrollDown;
    private LRecyclerViewOnToucheListener mLRecyclerViewOnToucheListener;
    private LScrollListener mLScrollListener;
    private float mLastY;
    private OnLoadMoreListener mLoadMoreListener;
    private OnToucheUpListener mOnToucheUpListener;
    private ArrowRefreshHeader mRefreshHeader;
    private int mRefreshHeaderHeight;
    private OnRefreshListener mRefreshListener;
    private int mRefreshProgressStyle;
    private int mScrolledXDistance;
    private int mScrolledYDistance;
    private LRecyclerViewAdapter mWrapAdapter;
    private boolean pullRefreshEnabled;

    private class DataObserver extends AdapterDataObserver {
        private DataObserver() {
        }

        public void onChanged() {
            Adapter<?> adapter = LRecyclerView.this.getAdapter();
            if (adapter instanceof LRecyclerViewAdapter) {
                LRecyclerViewAdapter lRecyclerViewAdapter = (LRecyclerViewAdapter) adapter;
                if (!(lRecyclerViewAdapter.getInnerAdapter() == null || LRecyclerView.this.mEmptyView == null)) {
                    if (lRecyclerViewAdapter.getInnerAdapter().getItemCount() == 0) {
                        LRecyclerView.this.mEmptyView.setVisibility(0);
                        LRecyclerView.this.setVisibility(8);
                    } else {
                        LRecyclerView.this.mEmptyView.setVisibility(8);
                        LRecyclerView.this.setVisibility(0);
                    }
                }
            } else if (!(adapter == null || LRecyclerView.this.mEmptyView == null)) {
                if (adapter.getItemCount() == 0) {
                    LRecyclerView.this.mEmptyView.setVisibility(0);
                    LRecyclerView.this.setVisibility(8);
                } else {
                    LRecyclerView.this.mEmptyView.setVisibility(8);
                    LRecyclerView.this.setVisibility(0);
                }
            }
            if (LRecyclerView.this.mWrapAdapter != null) {
                LRecyclerView.this.mWrapAdapter.notifyDataSetChanged();
            }
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            LRecyclerView.this.mWrapAdapter.notifyItemRangeChanged((LRecyclerView.this.mWrapAdapter.getHeaderViewsCount() + positionStart) + 1, itemCount);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            LRecyclerView.this.mWrapAdapter.notifyItemRangeInserted((LRecyclerView.this.mWrapAdapter.getHeaderViewsCount() + positionStart) + 1, itemCount);
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            LRecyclerView.this.mWrapAdapter.notifyItemRangeRemoved((LRecyclerView.this.mWrapAdapter.getHeaderViewsCount() + positionStart) + 1, itemCount);
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            int headerViewsCountCount = LRecyclerView.this.mWrapAdapter.getHeaderViewsCount();
            LRecyclerView.this.mWrapAdapter.notifyItemRangeChanged((fromPosition + headerViewsCountCount) + 1, ((toPosition + headerViewsCountCount) + 1) + itemCount);
        }
    }

    public enum LayoutManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }

    public interface OnToucheUpListener {
        void onActionCancelWhenHeaderExpend();
    }

    public interface VideoChangeListener {
        void onVideoAutoChange();
    }

    public void setIsScrollDown(boolean isScrollDown) {
        this.mIsScrollDown = isScrollDown;
    }

    public boolean getIsScrollDown() {
        return this.mIsScrollDown;
    }

    public LRecyclerView(Context context) {
        this(context, null);
    }

    public LRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.pullRefreshEnabled = true;
        this.mRefreshProgressStyle = -1;
        this.mDataObserver = new DataObserver();
        this.mLastY = -1.0f;
        this.isNoMore = false;
        this.currentScrollState = 0;
        this.mDistance = 0;
        this.mIsScrollDown = true;
        this.mScrolledYDistance = 0;
        this.mScrolledXDistance = 0;
        this.appbarState = State.EXPANDED;
        this.mIsChoiceness = false;
        this.mIsOwner = false;
        init();
    }

    public void setIsChoiceness(boolean isChoiceness) {
        this.mIsChoiceness = isChoiceness;
    }

    private void init() {
        if (this.pullRefreshEnabled) {
            this.mRefreshHeader = new ArrowRefreshHeader(getContext());
            this.mRefreshHeader.setProgressStyle(this.mRefreshProgressStyle);
        }
        this.mFootView = new LoadingFooter(getContext());
        this.mFootView.setVisibility(8);
    }

    public void setLayoutManager(LayoutManager layout) {
        layout.setItemPrefetchEnabled(false);
        super.setLayoutManager(layout);
    }

    public void setAdapter(Adapter adapter) {
        this.mWrapAdapter = (LRecyclerViewAdapter) adapter;
        super.setAdapter(this.mWrapAdapter);
        this.mWrapAdapter.getInnerAdapter().registerAdapterDataObserver(this.mDataObserver);
        this.mDataObserver.onChanged();
        this.mWrapAdapter.setRefreshHeader(this.mRefreshHeader);
        this.mWrapAdapter.addFooterView(this.mFootView);
    }

    public void setLRecyclerViewOnToucheListener(LRecyclerViewOnToucheListener mLRecyclerViewOnToucheListener) {
        this.mLRecyclerViewOnToucheListener = mLRecyclerViewOnToucheListener;
    }

    public void setOnToucheUpListener(OnToucheUpListener onToucheUpListener) {
        this.mOnToucheUpListener = onToucheUpListener;
    }

    public boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
        r5 = this;
        r3 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r2 = r5.mLastY;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 != 0) goto L_0x000e;
    L_0x0008:
        r2 = r6.getRawY();
        r5.mLastY = r2;
    L_0x000e:
        r2 = r6.getAction();	 Catch:{ Exception -> 0x0058 }
        switch(r2) {
            case 0: goto L_0x0051;
            case 1: goto L_0x0015;
            case 2: goto L_0x005d;
            default: goto L_0x0015;
        };	 Catch:{ Exception -> 0x0058 }
    L_0x0015:
        r2 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r5.mLastY = r2;	 Catch:{ Exception -> 0x0058 }
        r2 = r5.mLRecyclerViewOnToucheListener;	 Catch:{ Exception -> 0x0058 }
        if (r2 == 0) goto L_0x0022;
    L_0x001d:
        r2 = r5.mLRecyclerViewOnToucheListener;	 Catch:{ Exception -> 0x0058 }
        r2.onActionCancel();	 Catch:{ Exception -> 0x0058 }
    L_0x0022:
        r2 = r5.isOnTop();	 Catch:{ Exception -> 0x0058 }
        if (r2 == 0) goto L_0x004c;
    L_0x0028:
        r2 = r5.pullRefreshEnabled;	 Catch:{ Exception -> 0x0058 }
        if (r2 == 0) goto L_0x004c;
    L_0x002c:
        r2 = r5.appbarState;	 Catch:{ Exception -> 0x0058 }
        r3 = com.github.jdsjlzx.recyclerview.AppBarStateChangeListener.State.EXPANDED;	 Catch:{ Exception -> 0x0058 }
        if (r2 != r3) goto L_0x004c;
    L_0x0032:
        r2 = r5.mRefreshHeader;	 Catch:{ Exception -> 0x0058 }
        r2 = r2.releaseAction();	 Catch:{ Exception -> 0x0058 }
        if (r2 == 0) goto L_0x004c;
    L_0x003a:
        r2 = r5.mOnToucheUpListener;	 Catch:{ Exception -> 0x0058 }
        if (r2 == 0) goto L_0x0043;
    L_0x003e:
        r2 = r5.mOnToucheUpListener;	 Catch:{ Exception -> 0x0058 }
        r2.onActionCancelWhenHeaderExpend();	 Catch:{ Exception -> 0x0058 }
    L_0x0043:
        r2 = r5.mRefreshListener;	 Catch:{ Exception -> 0x0058 }
        if (r2 == 0) goto L_0x004c;
    L_0x0047:
        r2 = r5.mRefreshListener;	 Catch:{ Exception -> 0x0058 }
        r2.onRefresh();	 Catch:{ Exception -> 0x0058 }
    L_0x004c:
        r2 = super.onTouchEvent(r6);
    L_0x0050:
        return r2;
    L_0x0051:
        r2 = r6.getRawY();	 Catch:{ Exception -> 0x0058 }
        r5.mLastY = r2;	 Catch:{ Exception -> 0x0058 }
        goto L_0x004c;
    L_0x0058:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x004c;
    L_0x005d:
        r2 = r6.getRawY();	 Catch:{ Exception -> 0x0058 }
        r3 = r5.mLastY;	 Catch:{ Exception -> 0x0058 }
        r0 = r2 - r3;
        r2 = r5.isOnTop();	 Catch:{ Exception -> 0x0058 }
        if (r2 == 0) goto L_0x004c;
    L_0x006b:
        r2 = r5.pullRefreshEnabled;	 Catch:{ Exception -> 0x0058 }
        if (r2 == 0) goto L_0x004c;
    L_0x006f:
        r2 = r5.appbarState;	 Catch:{ Exception -> 0x0058 }
        r3 = com.github.jdsjlzx.recyclerview.AppBarStateChangeListener.State.EXPANDED;	 Catch:{ Exception -> 0x0058 }
        if (r2 != r3) goto L_0x004c;
    L_0x0075:
        r2 = r5.mLRecyclerViewOnToucheListener;	 Catch:{ Exception -> 0x0058 }
        if (r2 == 0) goto L_0x0085;
    L_0x0079:
        r2 = r5.mLRecyclerViewOnToucheListener;	 Catch:{ Exception -> 0x0058 }
        r3 = r5.mLastY;	 Catch:{ Exception -> 0x0058 }
        r4 = r6.getRawY();	 Catch:{ Exception -> 0x0058 }
        r2.onActionMove(r3, r4);	 Catch:{ Exception -> 0x0058 }
        goto L_0x004c;
    L_0x0085:
        r2 = r6.getRawY();	 Catch:{ Exception -> 0x0058 }
        r5.mLastY = r2;	 Catch:{ Exception -> 0x0058 }
        r2 = r5.mRefreshHeader;	 Catch:{ Exception -> 0x0058 }
        r3 = 1074580685; // 0x400ccccd float:2.2 double:5.309134E-315;
        r3 = r0 / r3;
        r2.onMove(r3);	 Catch:{ Exception -> 0x0058 }
        r2 = r5.mRefreshHeader;	 Catch:{ Exception -> 0x0058 }
        r2 = r2.getVisibleHeight();	 Catch:{ Exception -> 0x0058 }
        if (r2 <= 0) goto L_0x004c;
    L_0x009d:
        r2 = r5.mRefreshHeader;	 Catch:{ Exception -> 0x0058 }
        r2 = r2.getState();	 Catch:{ Exception -> 0x0058 }
        r3 = 2;
        if (r2 >= r3) goto L_0x004c;
    L_0x00a6:
        r2 = 0;
        goto L_0x0050;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.jdsjlzx.recyclerview.LRecyclerView.onTouchEvent(android.view.MotionEvent):boolean");
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

    private boolean isOnTop() {
        if (!this.pullRefreshEnabled || this.mRefreshHeader.getParent() == null) {
            return false;
        }
        return true;
    }

    public void setEmptyView(View emptyView) {
        if (this.mEmptyView == null) {
            this.mEmptyView = emptyView;
            this.mDataObserver.onChanged();
        }
    }

    public void refreshComplete() {
        this.mRefreshHeader.refreshComplete();
        setNoMore(false);
    }

    public void refreshCompleteLetter() {
        this.mRefreshHeader.refreshComplete();
    }

    public void setNoMore(boolean noMore) {
        this.isNoMore = noMore;
    }

    public boolean getNoMore() {
        return this.isNoMore;
    }

    public void setRefreshHeader(BaseRefreshHeader refreshHeader) {
        this.mRefreshHeader = (ArrowRefreshHeader) refreshHeader;
    }

    public void setPullRefreshEnabled(boolean enabled) {
        this.pullRefreshEnabled = enabled;
        if (!this.pullRefreshEnabled) {
            this.mRefreshHeader.setMinimumHeight(1);
        }
    }

    public void setRefreshProgressStyle(int style) {
        if (this.mRefreshHeader != null) {
            this.mRefreshHeader.setProgressStyle(style);
        }
    }

    public void setArrowImageView(int resId) {
        if (this.mRefreshHeader != null) {
            this.mRefreshHeader.setArrowImageView(resId);
        }
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mRefreshListener = listener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mLoadMoreListener = listener;
    }

    public void setLScrollListener(LScrollListener listener) {
        this.mLScrollListener = listener;
    }

    public void setVideoChangeListener(VideoChangeListener listener) {
        this.mChangeListener = listener;
    }

    public void setRefreshing(boolean refreshing) {
        if (refreshing && this.pullRefreshEnabled && this.mRefreshListener != null) {
            this.mRefreshHeader.setState(2);
            this.mRefreshHeaderHeight = this.mRefreshHeader.getMeasuredHeight();
            this.mRefreshHeader.onMove((float) this.mRefreshHeaderHeight);
            this.mRefreshListener.onRefresh();
        }
    }

    public void forceToRefresh() {
        if (this.mEmptyView != null) {
            this.mEmptyView.setVisibility(8);
            setVisibility(0);
        }
        if (RecyclerViewStateUtils.getFooterViewState(this) != LoadingFooter.State.Loading && this.pullRefreshEnabled && this.mRefreshListener != null) {
            scrollToPosition(0);
            this.mRefreshHeader.setState(2);
            if (this.mRefreshHeaderHeight == 0) {
                this.mRefreshHeaderHeight = 240;
            }
            this.mRefreshHeader.onMove((float) this.mRefreshHeaderHeight);
            this.mRefreshListener.onRefresh();
        }
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

    public void setScrolledXDistance(int x) {
        this.mScrolledXDistance = x;
    }

    public void setScrolledYDistance(int y) {
        this.mScrolledYDistance = y;
    }

    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        this.currentScrollState = state;
        if (this.mLScrollListener != null) {
            this.mLScrollListener.onScrollStateChanged(state);
        }
        if (this.mChangeListener != null && state == 0) {
            this.mChangeListener.onVideoAutoChange();
        }
        if (this.mLoadMoreListener != null && this.currentScrollState == 0) {
            LayoutManager layoutManager = getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (visibleItemCount > 0 && this.lastVisibleItemPosition >= totalItemCount - 3) {
                if (this.mIsChoiceness) {
                    if (totalItemCount < visibleItemCount) {
                        return;
                    }
                } else if (totalItemCount < visibleItemCount) {
                    return;
                }
                if (!this.isNoMore && this.mRefreshHeader.getState() != 2) {
                    this.mLoadMoreListener.onLoadMore();
                }
            }
        }
    }

    public void setIsOwner(boolean isOwner) {
        this.mIsOwner = isOwner;
    }

    public boolean hasNestedScrollingParent(int type) {
        if (this.mIsOwner) {
            return false;
        }
        return super.hasNestedScrollingParent(type);
    }

    private void calculateScrollUpOrDown(int firstVisibleItemPosition, int dy) {
        if (firstVisibleItemPosition == 0) {
            if (!this.mIsScrollDown) {
                this.mIsScrollDown = true;
                if (this.mLScrollListener != null) {
                    this.mLScrollListener.onScrollDown();
                }
            }
        } else if (this.mDistance > 20 && this.mIsScrollDown) {
            this.mIsScrollDown = false;
            if (this.mLScrollListener != null) {
                this.mLScrollListener.onScrollUp();
            }
            this.mDistance = 0;
        } else if (this.mDistance < -20 && !this.mIsScrollDown) {
            this.mIsScrollDown = true;
            if (this.mLScrollListener != null) {
                this.mLScrollListener.onScrollDown();
            }
            this.mDistance = 0;
        }
        if ((this.mIsScrollDown && dy > 0) || (!this.mIsScrollDown && dy < 0)) {
            this.mDistance += dy;
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}
