package com.feng.car.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.Behavior;
import android.support.design.widget.AppBarLayout.Behavior.DragCallback;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public final class FlingBehavior extends Behavior {
    private int m25 = 40;
    private boolean mAlreadExpanded = false;
    private OnAppBarCloseStateListener mBarOpenStateListener;
    private boolean mOpen = true;
    private int mVerticalOffset = 0;

    public interface OnAppBarCloseStateListener {
        void onBarClose(boolean z);
    }

    public void setBarCloseStateListener(OnAppBarCloseStateListener barCloseStateListener) {
        this.mBarOpenStateListener = barCloseStateListener;
    }

    public void setVerticalOffset(int verticalOffset) {
        this.mVerticalOffset = verticalOffset;
    }

    public FlingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m25 = context.getResources().getDimensionPixelSize(2131296434);
    }

    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
    }

    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout child, @NonNull View target, float velocityX, float velocityY) {
        return true;
    }

    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        if (!this.mOpen || dy <= this.m25 || this.mAlreadExpanded) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
            return;
        }
        this.mAlreadExpanded = true;
        if (this.mBarOpenStateListener != null) {
            this.mBarOpenStateListener.onBarClose(true);
        }
        child.setExpanded(false, false);
        consumed[1] = dy;
    }

    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, abl, target, type);
        if (!(this.mAlreadExpanded || Math.abs(getTopAndBottomOffset()) == abl.getTotalScrollRange())) {
            if (Math.abs(getTopAndBottomOffset()) <= Math.abs(abl.getTotalScrollRange() / 5)) {
                abl.setExpanded(true, false);
            } else if (Math.abs(getTopAndBottomOffset()) != abl.getTotalScrollRange()) {
                if (this.mBarOpenStateListener != null) {
                    this.mBarOpenStateListener.onBarClose(false);
                }
                abl.setExpanded(false, true);
            }
        }
        this.mAlreadExpanded = false;
    }

    public void setmOpen(boolean mOpen) {
        this.mOpen = mOpen;
        if (mOpen) {
            setDragCallback(null);
        } else {
            setDragCallback(new DragCallback() {
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return false;
                }
            });
        }
    }

    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        if (ev.getAction() != 1) {
            return super.onTouchEvent(parent, child, ev);
        }
        if (Math.abs(getTopAndBottomOffset()) > Math.abs(child.getTotalScrollRange() / 5)) {
            if (this.mBarOpenStateListener != null) {
                this.mBarOpenStateListener.onBarClose(false);
            }
            child.setExpanded(false, true);
            return true;
        }
        child.setExpanded(true, true);
        return true;
    }
}
