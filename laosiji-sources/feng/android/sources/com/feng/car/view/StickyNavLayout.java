package com.feng.car.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import com.feng.car.activity.NewSubjectActivity;
import com.feng.car.activity.PersonalHomePageNewActivity;
import com.feng.car.event.RecyclerviewDirectionEvent;
import org.greenrobot.eventbus.EventBus;

public class StickyNavLayout extends LinearLayout implements NestedScrollingParent {
    private OnActionMoveListener mActionMoveListener;
    private boolean mDragging;
    private boolean mIsCloseScrollUp = false;
    public boolean mIsMove = false;
    private boolean mIsPersonal = false;
    private boolean mIsScrollTop = false;
    private int mLastDy = 0;
    private float mLastY;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private View mNav;
    private int mPersonalViewPageHight = 0;
    private OverScroller mScroller;
    private int mTitleBarHeight = 0;
    private View mTop;
    private int mTopViewHeight;
    private int mTotalDy = 0;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private ViewPager mViewPager;

    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(1);
        if (context instanceof NewSubjectActivity) {
            this.mTitleBarHeight = 0;
        } else {
            this.mTitleBarHeight = getResources().getDimensionPixelSize(2131296859);
        }
        if (context instanceof PersonalHomePageNewActivity) {
            this.mIsPersonal = true;
        } else {
            this.mIsPersonal = false;
        }
        this.mScroller = new OverScroller(context);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        this.mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public void setActionMoveListener(OnActionMoveListener mActionMoveListener) {
        this.mActionMoveListener = mActionMoveListener;
    }

    public void setIsCloseScrollUp(boolean isCloseScrollUp) {
        this.mIsCloseScrollUp = isCloseScrollUp;
    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
    }

    public void onStopNestedScroll(View target) {
        this.mTotalDy = 0;
        this.mActionMoveListener.onActionCancel();
    }

    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (dy > 0) {
            if (this.mLastDy <= 0) {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(true));
            }
        } else if (this.mLastDy > 0) {
            EventBus.getDefault().post(new RecyclerviewDirectionEvent(false));
        }
        this.mLastDy = dy;
        if (((float) Math.abs(dx)) / (((float) Math.abs(dy)) * 1.0f) <= 3.0f) {
            boolean hiddenTop;
            if (dy <= 0 || getScrollY() >= this.mTopViewHeight) {
                hiddenTop = false;
            } else {
                hiddenTop = true;
            }
            boolean showTop;
            if (dy >= 0 || getScrollY() < 0 || ViewCompat.canScrollVertically(target, -1)) {
                showTop = false;
            } else {
                showTop = true;
            }
            this.mActionMoveListener.onLocationChange();
            if (!hiddenTop && !showTop) {
                return;
            }
            if (dy >= 0 || getScrollY() > 0) {
                this.mIsMove = true;
                scrollBy(0, dy);
                consumed[1] = dy;
                return;
            }
            if (dy >= -5) {
                dy = -5;
            } else if (dy >= -10) {
                dy = -10;
            } else if (dy >= -15) {
                dy = -15;
            } else {
                dy = -20;
            }
            this.mTotalDy += dy;
            this.mActionMoveListener.onActionMove(0.0f, (float) Math.abs(this.mTotalDy));
            this.mActionMoveListener.onActionHomeMove(0.0f, (float) Math.abs(this.mTotalDy));
        }
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        if (getScrollY() >= this.mTopViewHeight) {
            return false;
        }
        fling((int) velocityY);
        return true;
    }

    public int getNestedScrollAxes() {
        return 0;
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTrackerIfNotExists();
        this.mVelocityTracker.addMovement(event);
        int action = event.getAction();
        float y = event.getY();
        switch (action) {
            case 0:
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                this.mLastY = y;
                return true;
            case 1:
                this.mDragging = false;
                this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                int velocityY = (int) this.mVelocityTracker.getYVelocity();
                if (Math.abs(velocityY) > this.mMinimumVelocity && !this.mIsCloseScrollUp) {
                    fling(-velocityY);
                }
                recycleVelocityTracker();
                this.mTotalDy = 0;
                this.mActionMoveListener.onActionCancel();
                break;
            case 2:
                float dy = y - this.mLastY;
                this.mActionMoveListener.onLocationChange();
                if (!this.mDragging && Math.abs(dy) > ((float) this.mTouchSlop)) {
                    this.mDragging = true;
                }
                if (this.mDragging) {
                    if (getScrollY() <= 0) {
                        this.mTotalDy = (int) (((float) this.mTotalDy) + dy);
                        this.mActionMoveListener.onActionMove(0.0f, (float) this.mTotalDy);
                    } else {
                        scrollBy(0, (int) (-dy));
                    }
                }
                this.mLastY = y;
                break;
            case 3:
                this.mDragging = false;
                recycleVelocityTracker();
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                this.mTotalDy = 0;
                this.mActionMoveListener.onActionCancel();
                break;
        }
        return super.onTouchEvent(event);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mTop = findViewById(2131623969);
        this.mNav = findViewById(2131623967);
        View view = findViewById(2131623970);
        if (view instanceof ViewPager) {
            this.mViewPager = (ViewPager) view;
            return;
        }
        throw new RuntimeException("id_stickynavlayout_viewpager show used by ViewPager !");
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, 0));
        LayoutParams params = this.mViewPager.getLayoutParams();
        if (this.mIsPersonal && this.mPersonalViewPageHight < (getMeasuredHeight() - this.mNav.getMeasuredHeight()) - this.mTitleBarHeight) {
            this.mPersonalViewPageHight = (getMeasuredHeight() - this.mNav.getMeasuredHeight()) - this.mTitleBarHeight;
        }
        if (!this.mIsPersonal) {
            params.height = (getMeasuredHeight() - this.mNav.getMeasuredHeight()) - this.mTitleBarHeight;
        } else if (this.mPersonalViewPageHight > 0) {
            params.height = this.mPersonalViewPageHight;
        } else {
            params.height = (getMeasuredHeight() - this.mNav.getMeasuredHeight()) - this.mTitleBarHeight;
        }
        setMeasuredDimension(getMeasuredWidth(), (this.mTop.getMeasuredHeight() + this.mNav.getMeasuredHeight()) + this.mViewPager.getMeasuredHeight());
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mTopViewHeight = this.mTop.getMeasuredHeight() - this.mTitleBarHeight;
    }

    public void fling(int velocityY) {
        this.mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, this.mTopViewHeight);
        invalidate();
    }

    public boolean isScrollTop() {
        return this.mIsScrollTop;
    }

    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > this.mTopViewHeight) {
            y = this.mTopViewHeight;
            this.mIsScrollTop = true;
        } else {
            this.mIsScrollTop = false;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(0, this.mScroller.getCurrY());
            invalidate();
        }
    }

    public void resetLocation() {
        scrollBy(0, 0);
    }
}
