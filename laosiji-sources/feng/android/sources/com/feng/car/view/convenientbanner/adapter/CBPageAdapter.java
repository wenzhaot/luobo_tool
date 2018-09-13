package com.feng.car.view.convenientbanner.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.view.convenientbanner.holder.CBViewHolderCreator;
import com.feng.car.view.convenientbanner.holder.Holder;
import com.feng.car.view.convenientbanner.view.CBLoopViewPager;
import java.util.List;

public class CBPageAdapter<T> extends PagerAdapter {
    private final int MULTIPLE_COUNT = 300;
    private boolean canLoop = true;
    protected CBViewHolderCreator holderCreator;
    protected List<T> mDatas;
    private CBLoopViewPager viewPager;

    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0) {
            return 0;
        }
        return position % realCount;
    }

    public int getCount() {
        return this.canLoop ? getRealCount() * 300 : getRealCount();
    }

    public int getRealCount() {
        return this.mDatas == null ? 0 : this.mDatas.size();
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(toRealPosition(position), null, container);
        container.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void finishUpdate(ViewGroup container) {
        int position = this.viewPager.getCurrentItem();
        if (position == 0) {
            position = this.viewPager.getFristItem();
        } else if (position == getCount() - 1) {
            position = this.viewPager.getLastItem();
        }
        try {
            this.viewPager.setCurrentItem(position, false);
        } catch (IllegalStateException e) {
        }
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public void setViewPager(CBLoopViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public CBPageAdapter(CBViewHolderCreator holderCreator, List<T> datas) {
        this.holderCreator = holderCreator;
        this.mDatas = datas;
    }

    public View getView(int position, View view, ViewGroup container) {
        Holder holder;
        if (view == null) {
            holder = (Holder) this.holderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(2131623950, holder);
        } else {
            holder = (Holder) view.getTag(2131623950);
        }
        if (!(this.mDatas == null || this.mDatas.isEmpty())) {
            holder.UpdateUI(container.getContext(), position, this.mDatas.get(position));
        }
        return view;
    }
}
