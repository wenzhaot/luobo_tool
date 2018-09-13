package com.feng.car.view.convenientbanner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.feng.car.R;
import com.feng.car.view.convenientbanner.adapter.CBPageAdapter;
import com.feng.car.view.convenientbanner.holder.CBViewHolderCreator;
import com.feng.car.view.convenientbanner.listener.CBPageChangeListener;
import com.feng.car.view.convenientbanner.listener.OnItemClickListener;
import com.feng.car.view.convenientbanner.view.CBLoopViewPager;
import com.feng.car.view.convenientbanner.view.CBLoopViewPager.ViewPageTouchedListener;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConvenientBanner<T> extends LinearLayout {
    private AdSwitchTask adSwitchTask;
    private long autoTurningTime;
    private boolean canLoop = true;
    private boolean canTurn = false;
    private ViewGroup loPageTurningPoint;
    private List<T> mDatas;
    private ArrayList<ImageView> mPointViews = new ArrayList();
    private boolean manualPageable = true;
    private OnPageChangeListener onPageChangeListener;
    private CBPageAdapter pageAdapter;
    private CBPageChangeListener pageChangeListener;
    private int[] page_indicatorId;
    private ViewPagerScroller scroller;
    private boolean turning;
    private CBLoopViewPager viewPager;

    static class AdSwitchTask implements Runnable {
        private final WeakReference<ConvenientBanner> reference;

        AdSwitchTask(ConvenientBanner convenientBanner) {
            this.reference = new WeakReference(convenientBanner);
        }

        public void run() {
            ConvenientBanner convenientBanner = (ConvenientBanner) this.reference.get();
            if (convenientBanner != null && convenientBanner.viewPager != null && convenientBanner.turning) {
                convenientBanner.viewPager.setCurrentItem(convenientBanner.viewPager.getCurrentItem() + 1);
                convenientBanner.postDelayed(convenientBanner.adSwitchTask, convenientBanner.autoTurningTime);
            }
        }
    }

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT,
        ALIGN_PARENT_RIGHT,
        CENTER_HORIZONTAL
    }

    public ConvenientBanner(Context context) {
        super(context);
        init(context);
    }

    public ConvenientBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        this.canLoop = a.getBoolean(0, true);
        a.recycle();
        init(context);
    }

    @TargetApi(11)
    public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        this.canLoop = a.getBoolean(0, true);
        a.recycle();
        init(context);
    }

    @TargetApi(21)
    public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        this.canLoop = a.getBoolean(0, true);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        View hView = LayoutInflater.from(context).inflate(2130903262, this, true);
        this.viewPager = (CBLoopViewPager) hView.findViewById(2131625105);
        this.loPageTurningPoint = (ViewGroup) hView.findViewById(2131625106);
        initViewPagerScroll();
        this.adSwitchTask = new AdSwitchTask(this);
    }

    public void setViewPageTouchListener(ViewPageTouchedListener l) {
        if (this.viewPager != null) {
            this.viewPager.setViewPageTouchedListener(l);
        }
    }

    public ConvenientBanner setPages(CBViewHolderCreator holderCreator, List<T> datas) {
        this.mDatas = datas;
        this.pageAdapter = new CBPageAdapter(holderCreator, this.mDatas);
        this.viewPager.setAdapter(this.pageAdapter, this.canLoop);
        if (this.page_indicatorId != null) {
            setPageIndicator(this.page_indicatorId);
        }
        return this;
    }

    public void notifyDataSetChanged() {
        this.viewPager.getAdapter().notifyDataSetChanged();
        if (this.page_indicatorId != null) {
            setPageIndicator(this.page_indicatorId);
        }
    }

    public ConvenientBanner setPointViewVisible(boolean visible) {
        this.loPageTurningPoint.setVisibility(visible ? 0 : 8);
        return this;
    }

    public ConvenientBanner setPageIndicator(int[] page_indicatorId) {
        this.loPageTurningPoint.removeAllViews();
        this.mPointViews.clear();
        this.page_indicatorId = page_indicatorId;
        if (this.mDatas != null) {
            for (int count = 0; count < this.mDatas.size(); count++) {
                ImageView pointView = new ImageView(getContext());
                pointView.setPadding(5, 0, 5, 0);
                if (this.mPointViews.isEmpty()) {
                    pointView.setImageResource(page_indicatorId[1]);
                } else {
                    pointView.setImageResource(page_indicatorId[0]);
                }
                this.mPointViews.add(pointView);
                this.loPageTurningPoint.addView(pointView);
            }
            this.pageChangeListener = new CBPageChangeListener(this.mPointViews, page_indicatorId);
            this.viewPager.setOnPageChangeListener(this.pageChangeListener);
            this.pageChangeListener.onPageSelected(this.viewPager.getRealItem());
            if (this.onPageChangeListener != null) {
                this.pageChangeListener.setOnPageChangeListener(this.onPageChangeListener);
            }
        }
        return this;
    }

    public ConvenientBanner setPageIndicatorAlign(PageIndicatorAlign align) {
        LayoutParams layoutParams = (LayoutParams) this.loPageTurningPoint.getLayoutParams();
        layoutParams.addRule(12, -1);
        this.loPageTurningPoint.setLayoutParams(layoutParams);
        return this;
    }

    public boolean isTurning() {
        return this.turning;
    }

    public ConvenientBanner startTurning(long autoTurningTime) {
        if (this.turning) {
            stopTurning();
        }
        this.canTurn = true;
        this.autoTurningTime = autoTurningTime;
        this.turning = true;
        postDelayed(this.adSwitchTask, autoTurningTime);
        return this;
    }

    public void stopTurning() {
        this.turning = false;
        removeCallbacks(this.adSwitchTask);
    }

    public ConvenientBanner setPageTransformer(PageTransformer transformer) {
        this.viewPager.setPageTransformer(true, transformer);
        return this;
    }

    private void initViewPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            this.scroller = new ViewPagerScroller(this.viewPager.getContext());
            mScroller.set(this.viewPager, this.scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
    }

    public boolean isManualPageable() {
        return this.viewPager.isCanScroll();
    }

    public void setManualPageable(boolean manualPageable) {
        this.viewPager.setCanScroll(manualPageable);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == 1 || action == 3 || action == 4) {
            if (this.canTurn) {
                startTurning(this.autoTurningTime);
            }
        } else if (action == 0 && this.canTurn) {
            stopTurning();
        }
        return super.dispatchTouchEvent(ev);
    }

    public int getCurrentItem() {
        if (this.viewPager != null) {
            return this.viewPager.getRealItem();
        }
        return -1;
    }

    public void setcurrentitem(int index) {
        if (this.viewPager != null) {
            this.viewPager.setCurrentItem(index);
        }
    }

    public OnPageChangeListener getOnPageChangeListener() {
        return this.onPageChangeListener;
    }

    public ConvenientBanner setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
        if (this.pageChangeListener != null) {
            this.pageChangeListener.setOnPageChangeListener(onPageChangeListener);
        } else {
            this.viewPager.setOnPageChangeListener(onPageChangeListener);
        }
        return this;
    }

    public boolean isCanLoop() {
        return this.viewPager.isCanLoop();
    }

    public ConvenientBanner setOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (onItemClickListener == null) {
            this.viewPager.setOnItemClickListener(null);
        } else {
            this.viewPager.setOnItemClickListener(onItemClickListener);
        }
        return this;
    }

    public void setScrollDuration(int scrollDuration) {
        this.scroller.setScrollDuration(scrollDuration);
    }

    public int getScrollDuration() {
        return this.scroller.getScrollDuration();
    }

    public CBLoopViewPager getViewPager() {
        return this.viewPager;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        this.viewPager.setCanLoop(canLoop);
    }
}
