package com.feng.car.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class ScrollerBottomView extends LinearLayout {
    private int downY;
    private boolean isTop;
    private View mChild;
    private int mScrollHeight;
    private OnScrollerStateListener mScrollListener;
    private Scroller mScroller;
    private boolean mScrolling;
    private float mTouchDownX;
    private float mTouchDownY;
    private int moveY;
    private int movedY;
    private float visibilityHeight;

    public int getScrollHeight() {
        return this.mScrollHeight;
    }

    public ScrollerBottomView(Context context) {
        this(context, null);
    }

    public ScrollerBottomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.isTop = false;
        this.visibilityHeight = (float) context.getResources().getDimensionPixelSize(2131296288);
        init(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mScrollHeight = (int) (((float) this.mChild.getMeasuredHeight()) - this.visibilityHeight);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.mChild.layout(0, this.mScrollHeight, this.mChild.getMeasuredWidth(), this.mChild.getMeasuredHeight() + this.mScrollHeight);
    }

    private void init(Context context) {
        this.mScroller = new Scroller(context);
        setBackgroundColor(0);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0 || getChildAt(0) == null) {
            throw new RuntimeException("没有子控件！");
        } else if (getChildCount() > 1) {
            throw new RuntimeException("只能有一个子控件！");
        } else {
            this.mChild = getChildAt(0);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.mTouchDownX = event.getX();
                this.mTouchDownY = event.getY();
                this.mScrolling = false;
                break;
            case 1:
                this.mScrolling = false;
                break;
            case 2:
                if (Math.abs(this.mTouchDownX - event.getX()) < ((float) ViewConfiguration.get(getContext()).getScaledTouchSlop()) && Math.abs(this.mTouchDownY - event.getY()) < ((float) ViewConfiguration.get(getContext()).getScaledTouchSlop())) {
                    this.mScrolling = false;
                    break;
                }
                this.mScrolling = true;
                break;
                break;
        }
        return this.mScrolling;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.downY = (int) event.getY();
                if (this.isTop || this.downY >= this.mScrollHeight) {
                    return true;
                }
                return super.onTouchEvent(event);
            case 1:
                if (this.movedY > this.mScrollHeight / 4 && !this.isTop) {
                    this.mScroller.startScroll(0, getScrollY(), 0, this.mScrollHeight - getScrollY());
                    invalidate();
                    this.movedY = this.mScrollHeight;
                    this.isTop = true;
                    if (this.mScrollListener != null) {
                        this.mScrollListener.onScrollState(this.isTop, this.movedY);
                        break;
                    }
                }
                this.mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
                postInvalidate();
                this.movedY = 0;
                this.isTop = false;
                if (this.mScrollListener != null) {
                    this.mScrollListener.onScrollState(this.isTop, this.movedY);
                    break;
                }
                break;
            case 2:
                this.moveY = (int) event.getY();
                int deY = this.downY - this.moveY;
                if (deY > 0) {
                    this.movedY += deY;
                    if (this.movedY > this.mScrollHeight) {
                        this.movedY = this.mScrollHeight;
                    }
                    if (this.mScrollListener != null) {
                        this.mScrollListener.onScrollState(true, this.movedY);
                    }
                    if (this.movedY < this.mScrollHeight) {
                        scrollBy(0, deY);
                        this.downY = this.moveY;
                        return true;
                    }
                }
                if (deY < 0 && this.isTop) {
                    this.movedY += deY;
                    if (this.movedY < 0) {
                        this.movedY = 0;
                    }
                    if (this.mScrollListener != null) {
                        this.mScrollListener.onScrollState(false, this.movedY);
                    }
                    if (this.movedY > 0) {
                        scrollBy(0, deY);
                    }
                    this.downY = this.moveY;
                    return true;
                }
        }
        return super.onTouchEvent(event);
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(0, this.mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void resetScroll() {
        if (this.isTop) {
            this.mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
            postInvalidate();
            this.movedY = 0;
            this.isTop = false;
            if (this.mScrollListener != null) {
                this.mScrollListener.onScrollState(this.isTop, this.movedY);
            }
        }
    }

    public void scrollerToTop() {
        if (!this.isTop) {
            this.mScroller.startScroll(0, getScrollY(), 0, this.mScrollHeight);
            invalidate();
            this.movedY = this.mScrollHeight;
            this.isTop = true;
            if (this.mScrollListener != null) {
                this.mScrollListener.onScrollState(this.isTop, this.movedY);
            }
        }
    }

    public void setScrollListener(OnScrollerStateListener scrollListener) {
        this.mScrollListener = scrollListener;
    }
}
