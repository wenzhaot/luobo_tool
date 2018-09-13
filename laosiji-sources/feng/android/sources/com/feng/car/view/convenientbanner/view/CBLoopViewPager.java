package com.feng.car.view.convenientbanner.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.feng.car.view.convenientbanner.adapter.CBPageAdapter;
import com.feng.car.view.convenientbanner.listener.OnItemClickListener;

public class CBLoopViewPager extends ViewPager {
    private boolean canLoop = true;
    private boolean isCanScroll = true;
    private CBPageAdapter mAdapter;
    OnPageChangeListener mOuterPageChangeListener;
    private ViewPageTouchedListener mViewPageTouchedListener;
    private float newX = 0.0f;
    private float oldX = 0.0f;
    private OnItemClickListener onItemClickListener;
    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousPosition = -1.0f;

        public void onPageSelected(int position) {
            int realPosition = CBLoopViewPager.this.mAdapter.toRealPosition(position);
            if (this.mPreviousPosition != ((float) realPosition)) {
                this.mPreviousPosition = (float) realPosition;
                if (CBLoopViewPager.this.mOuterPageChangeListener != null) {
                    CBLoopViewPager.this.mOuterPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int realPosition = position;
            if (CBLoopViewPager.this.mOuterPageChangeListener == null) {
                return;
            }
            if (realPosition != CBLoopViewPager.this.mAdapter.getRealCount() - 1) {
                CBLoopViewPager.this.mOuterPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            } else if (((double) positionOffset) > 0.5d) {
                CBLoopViewPager.this.mOuterPageChangeListener.onPageScrolled(0, 0.0f, 0);
            } else {
                CBLoopViewPager.this.mOuterPageChangeListener.onPageScrolled(realPosition, 0.0f, 0);
            }
        }

        public void onPageScrollStateChanged(int state) {
            if (CBLoopViewPager.this.mOuterPageChangeListener != null) {
                CBLoopViewPager.this.mOuterPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };
    private final float sens = 5.0f;

    public interface ViewPageTouchedListener {
        void onTouched(MotionEvent motionEvent);
    }

    public void setAdapter(PagerAdapter adapter, boolean canLoop) {
        this.mAdapter = (CBPageAdapter) adapter;
        this.mAdapter.setCanLoop(canLoop);
        this.mAdapter.setViewPager(this);
        super.setAdapter(this.mAdapter);
        setCurrentItem(getFristItem(), false);
    }

    public int getFristItem() {
        return this.canLoop ? this.mAdapter.getRealCount() : 0;
    }

    public int getLastItem() {
        return this.mAdapter.getRealCount() - 1;
    }

    public boolean isCanScroll() {
        return this.isCanScroll;
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    public void setViewPageTouchedListener(ViewPageTouchedListener l) {
        this.mViewPageTouchedListener = l;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.isCanScroll) {
            return false;
        }
        if (this.onItemClickListener != null) {
            if (this.mViewPageTouchedListener != null) {
                this.mViewPageTouchedListener.onTouched(ev);
            }
            switch (ev.getAction()) {
                case 0:
                    this.oldX = ev.getX();
                    break;
                case 1:
                    this.newX = ev.getX();
                    if (Math.abs(this.oldX - this.newX) < 5.0f) {
                        this.onItemClickListener.onItemClick(getRealItem());
                    }
                    this.oldX = 0.0f;
                    this.newX = 0.0f;
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.isCanScroll) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    public CBPageAdapter getAdapter() {
        return this.mAdapter;
    }

    public int getRealItem() {
        return this.mAdapter != null ? this.mAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mOuterPageChangeListener = listener;
    }

    public CBLoopViewPager(Context context) {
        super(context);
        init();
    }

    public CBLoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.setOnPageChangeListener(this.onPageChangeListener);
    }

    public boolean isCanLoop() {
        return this.canLoop;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        if (!canLoop) {
            setCurrentItem(getRealItem(), false);
        }
        if (this.mAdapter != null) {
            this.mAdapter.setCanLoop(canLoop);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
