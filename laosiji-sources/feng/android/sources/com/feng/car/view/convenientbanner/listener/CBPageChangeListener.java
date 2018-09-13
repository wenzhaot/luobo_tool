package com.feng.car.view.convenientbanner.listener;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import java.util.ArrayList;

public class CBPageChangeListener implements OnPageChangeListener {
    private OnPageChangeListener onPageChangeListener;
    private int[] page_indicatorId;
    private ArrayList<ImageView> pointViews;

    public CBPageChangeListener(ArrayList<ImageView> pointViews, int[] page_indicatorId) {
        this.pointViews = pointViews;
        this.page_indicatorId = page_indicatorId;
    }

    public void onPageScrollStateChanged(int state) {
        if (this.onPageChangeListener != null) {
            this.onPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (this.onPageChangeListener != null) {
            this.onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int index) {
        for (int i = 0; i < this.pointViews.size(); i++) {
            ((ImageView) this.pointViews.get(index)).setImageResource(this.page_indicatorId[1]);
            if (index != i) {
                ((ImageView) this.pointViews.get(i)).setImageResource(this.page_indicatorId[0]);
            }
        }
        if (this.onPageChangeListener != null) {
            this.onPageChangeListener.onPageSelected(index);
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }
}
