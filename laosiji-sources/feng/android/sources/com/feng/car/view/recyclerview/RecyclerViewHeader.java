package com.feng.car.view.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnChildAttachStateChangeListener;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.RelativeLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RecyclerViewHeader extends RelativeLayout {
    private int downTranslation;
    private boolean hidden = false;
    private int intendedVisibility = 0;
    private boolean isAttachedToRecycler;
    private boolean isVertical;
    private LayoutManagerDelegate layoutManager;
    private RecyclerViewDelegate recyclerView;
    private boolean recyclerWantsTouch;

    private class HeaderItemDecoration extends ItemDecoration {
        private int firstRowSpan;
        private int headerHeight;
        private int headerWidth;

        public HeaderItemDecoration() {
            this.firstRowSpan = RecyclerViewHeader.this.layoutManager.getFirstRowSpan();
        }

        public void setWidth(int width) {
            this.headerWidth = width;
        }

        public void setHeight(int height) {
            this.headerHeight = height;
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            boolean headerRelatedPosition;
            int heightOffset;
            int widthOffset = 0;
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildLayoutPosition(view) < this.firstRowSpan) {
                headerRelatedPosition = true;
            } else {
                headerRelatedPosition = false;
            }
            if (headerRelatedPosition && RecyclerViewHeader.this.isVertical) {
                heightOffset = this.headerHeight;
            } else {
                heightOffset = 0;
            }
            if (headerRelatedPosition && !RecyclerViewHeader.this.isVertical) {
                widthOffset = this.headerWidth;
            }
            if (RecyclerViewHeader.this.layoutManager.isReversed()) {
                outRect.bottom = heightOffset;
                outRect.right = widthOffset;
                return;
            }
            outRect.top = heightOffset;
            outRect.left = widthOffset;
        }
    }

    private static class LayoutManagerDelegate {
        @Nullable
        private final GridLayoutManager grid;
        @Nullable
        private final LinearLayoutManager linear;
        @Nullable
        private final StaggeredGridLayoutManager staggeredGrid;

        private LayoutManagerDelegate(@NonNull LayoutManager manager) {
            Class<? extends LayoutManager> managerClass = manager.getClass();
            if (managerClass == LinearLayoutManager.class) {
                this.linear = (LinearLayoutManager) manager;
                this.grid = null;
                this.staggeredGrid = null;
            } else if (managerClass == GridLayoutManager.class) {
                this.linear = null;
                this.grid = (GridLayoutManager) manager;
                this.staggeredGrid = null;
            } else {
                throw new IllegalArgumentException("Currently RecyclerViewHeader supports only LinearLayoutManager and GridLayoutManager.");
            }
        }

        public static LayoutManagerDelegate with(@NonNull LayoutManager layoutManager) {
            return new LayoutManagerDelegate(layoutManager);
        }

        public final int getFirstRowSpan() {
            if (this.linear != null) {
                return 1;
            }
            if (this.grid != null) {
                return this.grid.getSpanCount();
            }
            return 0;
        }

        public final boolean isFirstRowVisible() {
            if (this.linear != null) {
                if (this.linear.findFirstVisibleItemPosition() == 0) {
                    return true;
                }
                return false;
            } else if (this.grid == null) {
                return false;
            } else {
                if (this.grid.findFirstVisibleItemPosition() != 0) {
                    return false;
                }
                return true;
            }
        }

        public final boolean isReversed() {
            if (this.linear != null) {
                return this.linear.getReverseLayout();
            }
            if (this.grid != null) {
                return this.grid.getReverseLayout();
            }
            return false;
        }

        public final boolean isVertical() {
            if (this.linear != null) {
                if (this.linear.getOrientation() == 1) {
                    return true;
                }
                return false;
            } else if (this.grid == null) {
                return false;
            } else {
                if (this.grid.getOrientation() != 1) {
                    return false;
                }
                return true;
            }
        }
    }

    private static class RecyclerViewDelegate {
        private HeaderItemDecoration decoration;
        private OnChildAttachStateChangeListener onChildAttachListener;
        private OnScrollListener onScrollListener;
        @NonNull
        private final RecyclerView recyclerView;

        private RecyclerViewDelegate(@NonNull RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        public static RecyclerViewDelegate with(@NonNull RecyclerView recyclerView) {
            return new RecyclerViewDelegate(recyclerView);
        }

        public final void onHeaderSizeChanged(int height, int width) {
            if (this.decoration != null) {
                this.decoration.setHeight(height);
                this.decoration.setWidth(width);
                this.recyclerView.post(new Runnable() {
                    public void run() {
                        RecyclerViewDelegate.this.invalidateItemDecorations();
                    }
                });
            }
        }

        private void invalidateItemDecorations() {
            if (!this.recyclerView.isComputingLayout()) {
                this.recyclerView.invalidateItemDecorations();
            }
        }

        public final int getScrollOffset(boolean isVertical) {
            return isVertical ? this.recyclerView.computeVerticalScrollOffset() : this.recyclerView.computeHorizontalScrollOffset();
        }

        public final int getTranslationBase(boolean isVertical) {
            if (isVertical) {
                return this.recyclerView.computeVerticalScrollRange() - this.recyclerView.getHeight();
            }
            return this.recyclerView.computeHorizontalScrollRange() - this.recyclerView.getWidth();
        }

        public final boolean hasItems() {
            return (this.recyclerView.getAdapter() == null || this.recyclerView.getAdapter().getItemCount() == 0) ? false : true;
        }

        public final void setHeaderDecoration(HeaderItemDecoration decoration) {
            clearHeaderDecoration();
            this.decoration = decoration;
            this.recyclerView.addItemDecoration(this.decoration, 0);
        }

        public final void clearHeaderDecoration() {
            if (this.decoration != null) {
                this.recyclerView.removeItemDecoration(this.decoration);
                this.decoration = null;
            }
        }

        public final void setOnScrollListener(OnScrollListener onScrollListener) {
            clearOnScrollListener();
            this.onScrollListener = onScrollListener;
            this.recyclerView.addOnScrollListener(this.onScrollListener);
        }

        public final void clearOnScrollListener() {
            if (this.onScrollListener != null) {
                this.recyclerView.removeOnScrollListener(this.onScrollListener);
                this.onScrollListener = null;
            }
        }

        public final void setOnChildAttachListener(OnChildAttachStateChangeListener onChildAttachListener) {
            clearOnChildAttachListener();
            this.onChildAttachListener = onChildAttachListener;
            this.recyclerView.addOnChildAttachStateChangeListener(this.onChildAttachListener);
        }

        public final void clearOnChildAttachListener() {
            if (this.onChildAttachListener != null) {
                this.recyclerView.removeOnChildAttachStateChangeListener(this.onChildAttachListener);
                this.onChildAttachListener = null;
            }
        }

        public final void reset() {
            clearHeaderDecoration();
            clearOnScrollListener();
            clearOnChildAttachListener();
        }

        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return this.recyclerView.onInterceptTouchEvent(ev);
        }

        public boolean onTouchEvent(MotionEvent ev) {
            return this.recyclerView.onTouchEvent(ev);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface Visibility {
    }

    public RecyclerViewHeader(Context context) {
        super(context);
    }

    public RecyclerViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public final void attachTo(@NonNull final RecyclerView recycler) {
        validate(recycler);
        this.recyclerView = RecyclerViewDelegate.with(recycler);
        this.layoutManager = LayoutManagerDelegate.with(recycler.getLayoutManager());
        this.isVertical = this.layoutManager.isVertical();
        this.isAttachedToRecycler = true;
        this.recyclerView.setHeaderDecoration(new HeaderItemDecoration());
        this.recyclerView.setOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RecyclerViewHeader.this.onScrollChanged();
            }
        });
        this.recyclerView.setOnChildAttachListener(new OnChildAttachStateChangeListener() {
            public void onChildViewAttachedToWindow(View view) {
            }

            public void onChildViewDetachedFromWindow(View view) {
                recycler.post(new Runnable() {
                    public void run() {
                        RecyclerViewHeader.this.recyclerView.invalidateItemDecorations();
                        RecyclerViewHeader.this.onScrollChanged();
                    }
                });
            }
        });
    }

    public final void detach() {
        if (this.isAttachedToRecycler) {
            this.isAttachedToRecycler = false;
            this.recyclerWantsTouch = false;
            this.recyclerView.reset();
            this.recyclerView = null;
            this.layoutManager = null;
        }
    }

    private void onScrollChanged() {
        boolean z = this.recyclerView.hasItems() && !this.layoutManager.isFirstRowVisible();
        this.hidden = z;
        super.setVisibility(this.hidden ? 4 : this.intendedVisibility);
        if (!this.hidden) {
            int translation = calculateTranslation();
            if (this.isVertical) {
                setTranslationY((float) translation);
            } else {
                setTranslationX((float) translation);
            }
        }
    }

    private int calculateTranslation() {
        return (this.layoutManager.isReversed() ? this.recyclerView.getTranslationBase(this.isVertical) : 0) - this.recyclerView.getScrollOffset(this.isVertical);
    }

    public final void setVisibility(int visibility) {
        this.intendedVisibility = visibility;
        if (!this.hidden) {
            super.setVisibility(this.intendedVisibility);
        }
    }

    public final int getVisibility() {
        return this.intendedVisibility;
    }

    protected final void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && this.isAttachedToRecycler) {
            int verticalMargins = 0;
            int horizontalMargins = 0;
            if (getLayoutParams() instanceof MarginLayoutParams) {
                MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
                verticalMargins = layoutParams.topMargin + layoutParams.bottomMargin;
                horizontalMargins = layoutParams.leftMargin + layoutParams.rightMargin;
            }
            this.recyclerView.onHeaderSizeChanged(getHeight() + verticalMargins, getWidth() + horizontalMargins);
            onScrollChanged();
        }
    }

    @CallSuper
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean z;
        if (this.isAttachedToRecycler && this.recyclerView.onInterceptTouchEvent(ev)) {
            z = true;
        } else {
            z = false;
        }
        this.recyclerWantsTouch = z;
        if (this.recyclerWantsTouch && ev.getAction() == 2) {
            this.downTranslation = calculateTranslation();
        }
        if (this.recyclerWantsTouch || super.onInterceptTouchEvent(ev)) {
            return true;
        }
        return false;
    }

    @CallSuper
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (!this.recyclerWantsTouch) {
            return super.onTouchEvent(event);
        }
        int verticalDiff;
        int horizontalDiff;
        int scrollDiff = this.downTranslation - calculateTranslation();
        if (this.isVertical) {
            verticalDiff = scrollDiff;
        } else {
            verticalDiff = 0;
        }
        if (this.isVertical) {
            horizontalDiff = 0;
        } else {
            horizontalDiff = scrollDiff;
        }
        this.recyclerView.onTouchEvent(MotionEvent.obtain(event.getDownTime(), event.getEventTime(), event.getAction(), event.getX() - ((float) horizontalDiff), event.getY() - ((float) verticalDiff), event.getMetaState()));
        return false;
    }

    private void validate(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() == null) {
            throw new IllegalStateException("Be sure to attach RecyclerViewHeader after setting your RecyclerView's LayoutManager.");
        }
    }
}
