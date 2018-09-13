package com.feng.car.view.largeimage;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.WindowManager.LayoutParams;

public abstract class UpdateView extends View {
    private boolean lock;
    private final OnPreDrawListener mDrawListener;
    private boolean mGlobalListenersAdded;
    final LayoutParams mLayout;
    int mLeft;
    int[] mLocation;
    boolean mRequestedVisible;
    final OnScrollChangedListener mScrollChangedListener;
    int mTop;
    boolean mViewVisibility;
    private Rect mVisibilityRect;
    boolean mVisible;
    boolean mWindowVisibility;

    protected abstract void onUpdateWindow(Rect rect);

    @TargetApi(21)
    public UpdateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mRequestedVisible = false;
        this.mWindowVisibility = false;
        this.mViewVisibility = false;
        this.mLayout = new LayoutParams();
        this.mScrollChangedListener = new OnScrollChangedListener() {
            public void onScrollChanged() {
                UpdateView.this.updateWindow(false, false);
            }
        };
        this.mDrawListener = new OnPreDrawListener() {
            public boolean onPreDraw() {
                return true;
            }
        };
        this.mLocation = new int[2];
        this.mVisible = false;
        this.mLeft = -1;
        this.mTop = -1;
    }

    @TargetApi(11)
    public UpdateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mRequestedVisible = false;
        this.mWindowVisibility = false;
        this.mViewVisibility = false;
        this.mLayout = new LayoutParams();
        this.mScrollChangedListener = /* anonymous class already generated */;
        this.mDrawListener = /* anonymous class already generated */;
        this.mLocation = new int[2];
        this.mVisible = false;
        this.mLeft = -1;
        this.mTop = -1;
    }

    public UpdateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mRequestedVisible = false;
        this.mWindowVisibility = false;
        this.mViewVisibility = false;
        this.mLayout = new LayoutParams();
        this.mScrollChangedListener = /* anonymous class already generated */;
        this.mDrawListener = /* anonymous class already generated */;
        this.mLocation = new int[2];
        this.mVisible = false;
        this.mLeft = -1;
        this.mTop = -1;
    }

    public UpdateView(Context context) {
        super(context);
        this.mRequestedVisible = false;
        this.mWindowVisibility = false;
        this.mViewVisibility = false;
        this.mLayout = new LayoutParams();
        this.mScrollChangedListener = /* anonymous class already generated */;
        this.mDrawListener = /* anonymous class already generated */;
        this.mLocation = new int[2];
        this.mVisible = false;
        this.mLeft = -1;
        this.mTop = -1;
    }

    protected void onWindowVisibilityChanged(int visibility) {
        boolean z;
        boolean z2 = true;
        super.onWindowVisibilityChanged(visibility);
        if (visibility == 0) {
            z = true;
        } else {
            z = false;
        }
        this.mWindowVisibility = z;
        if (!(this.mWindowVisibility && this.mViewVisibility)) {
            z2 = false;
        }
        this.mRequestedVisible = z2;
    }

    public void setVisibility(int visibility) {
        boolean z;
        boolean newRequestedVisible;
        super.setVisibility(visibility);
        if (visibility == 0) {
            z = true;
        } else {
            z = false;
        }
        this.mViewVisibility = z;
        if (this.mWindowVisibility && this.mViewVisibility) {
            newRequestedVisible = true;
        } else {
            newRequestedVisible = false;
        }
        if (newRequestedVisible != this.mRequestedVisible) {
            requestLayout();
        }
        this.mRequestedVisible = newRequestedVisible;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mLayout.token = getWindowToken();
        this.mLayout.setTitle("SurfaceView");
        this.mViewVisibility = getVisibility() == 0;
        if (!this.mGlobalListenersAdded) {
            ViewTreeObserver observer = getViewTreeObserver();
            observer.addOnScrollChangedListener(this.mScrollChangedListener);
            observer.addOnPreDrawListener(this.mDrawListener);
            this.mGlobalListenersAdded = true;
        }
    }

    protected void onDetachedFromWindow() {
        if (this.mGlobalListenersAdded) {
            ViewTreeObserver observer = getViewTreeObserver();
            observer.removeOnScrollChangedListener(this.mScrollChangedListener);
            observer.removeOnPreDrawListener(this.mDrawListener);
            this.mGlobalListenersAdded = false;
        }
        this.mRequestedVisible = false;
        updateWindow(false, false);
        this.mLayout.token = null;
        super.onDetachedFromWindow();
    }

    protected void lock() {
        this.lock = true;
    }

    protected void unLock() {
        this.lock = false;
    }

    private void updateWindow(boolean force, boolean redrawNeeded) {
        if (!this.lock) {
            int[] tempLocationInWindow = new int[2];
            getLocationInWindow(tempLocationInWindow);
            boolean visibleChanged;
            if (this.mVisible != this.mRequestedVisible) {
                visibleChanged = true;
            } else {
                visibleChanged = false;
            }
            if (force || visibleChanged || tempLocationInWindow[0] != this.mLocation[0] || tempLocationInWindow[1] != this.mLocation[1] || redrawNeeded) {
                this.mLocation = tempLocationInWindow;
                Rect visibilityRect = new Rect();
                getVisibilityRect(visibilityRect);
                if (this.mVisibilityRect == null || !this.mVisibilityRect.equals(visibilityRect)) {
                    this.mVisibilityRect = visibilityRect;
                    onUpdateWindow(visibilityRect);
                }
            }
        }
    }

    protected void getVisibilityRect(Rect visibilityRect) {
        getGlobalVisibleRect(visibilityRect);
        int[] location = new int[2];
        getLocationOnScreen(location);
        visibilityRect.left -= location[0];
        visibilityRect.right -= location[0];
        visibilityRect.top -= location[1];
        visibilityRect.bottom -= location[1];
    }
}
