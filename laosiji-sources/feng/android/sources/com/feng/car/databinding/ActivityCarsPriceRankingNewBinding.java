package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.view.SlideViewPager;

public class ActivityCarsPriceRankingNewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AppBarLayout appBar;
    public final ImageView ibMsg;
    public final ImageView ivTitleLeft;
    public final LinearLayout llCityBtn;
    public final LinearLayout llCountryBtn;
    public final LinearLayout llProvinceBtn;
    private long mDirtyFlags = -1;
    private CarSeriesInfo mSeriesInfo;
    public final CoordinatorLayout parent;
    public final RelativeLayout rlFirstChild;
    public final RelativeLayout rlTitleBar;
    public final TextView tvCarSBtn;
    public final TextView tvCarsName;
    public final TextView tvCity1;
    public final TextView tvCity2;
    public final TextView tvCity3;
    public final TextView tvLocationCity;
    public final TextView tvPriceCount1;
    public final TextView tvPriceCount2;
    public final TextView tvPriceCount3;
    public final TextView tvText;
    public final TextView tvTitle;
    public final TextView tvTuneRange1;
    public final TextView tvTuneRange2;
    public final TextView tvTuneRange3;
    public final SlideViewPager vpFragment;

    static {
        sViewsWithIds.put(2131624309, 1);
        sViewsWithIds.put(2131624310, 2);
        sViewsWithIds.put(2131624311, 3);
        sViewsWithIds.put(2131624312, 4);
        sViewsWithIds.put(2131624313, 5);
        sViewsWithIds.put(2131624314, 6);
        sViewsWithIds.put(2131624315, 7);
        sViewsWithIds.put(2131624316, 8);
        sViewsWithIds.put(2131624317, 9);
        sViewsWithIds.put(2131624318, 10);
        sViewsWithIds.put(2131624319, 11);
        sViewsWithIds.put(2131624320, 12);
        sViewsWithIds.put(2131624321, 13);
        sViewsWithIds.put(2131624322, 14);
        sViewsWithIds.put(2131624323, 15);
        sViewsWithIds.put(2131624324, 16);
        sViewsWithIds.put(2131624325, 17);
        sViewsWithIds.put(2131624215, 18);
        sViewsWithIds.put(2131624295, 19);
        sViewsWithIds.put(2131624296, 20);
        sViewsWithIds.put(2131624326, 21);
        sViewsWithIds.put(2131624298, 22);
        sViewsWithIds.put(2131624327, 23);
    }

    public ActivityCarsPriceRankingNewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds);
        this.appBar = (AppBarLayout) bindings[1];
        this.ibMsg = (ImageView) bindings[21];
        this.ivTitleLeft = (ImageView) bindings[19];
        this.llCityBtn = (LinearLayout) bindings[6];
        this.llCountryBtn = (LinearLayout) bindings[14];
        this.llProvinceBtn = (LinearLayout) bindings[10];
        this.parent = (CoordinatorLayout) bindings[0];
        this.parent.setTag(null);
        this.rlFirstChild = (RelativeLayout) bindings[2];
        this.rlTitleBar = (RelativeLayout) bindings[18];
        this.tvCarSBtn = (TextView) bindings[5];
        this.tvCarsName = (TextView) bindings[3];
        this.tvCity1 = (TextView) bindings[7];
        this.tvCity2 = (TextView) bindings[11];
        this.tvCity3 = (TextView) bindings[15];
        this.tvLocationCity = (TextView) bindings[22];
        this.tvPriceCount1 = (TextView) bindings[9];
        this.tvPriceCount2 = (TextView) bindings[13];
        this.tvPriceCount3 = (TextView) bindings[17];
        this.tvText = (TextView) bindings[4];
        this.tvTitle = (TextView) bindings[20];
        this.tvTuneRange1 = (TextView) bindings[8];
        this.tvTuneRange2 = (TextView) bindings[12];
        this.tvTuneRange3 = (TextView) bindings[16];
        this.vpFragment = (SlideViewPager) bindings[23];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2;
        }
        requestRebind();
    }

    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return false;
        }
    }

    public boolean setVariable(int variableId, Object variable) {
        switch (variableId) {
            case 54:
                setSeriesInfo((CarSeriesInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setSeriesInfo(CarSeriesInfo SeriesInfo) {
        this.mSeriesInfo = SeriesInfo;
    }

    public CarSeriesInfo getSeriesInfo() {
        return this.mSeriesInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    protected void executeBindings() {
        synchronized (this) {
            long dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
    }

    public static ActivityCarsPriceRankingNewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCarsPriceRankingNewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityCarsPriceRankingNewBinding) DataBindingUtil.inflate(inflater, 2130903081, root, attachToRoot, bindingComponent);
    }

    public static ActivityCarsPriceRankingNewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCarsPriceRankingNewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903081, null, false), bindingComponent);
    }

    public static ActivityCarsPriceRankingNewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCarsPriceRankingNewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_cars_price_ranking_new_0".equals(view.getTag())) {
            return new ActivityCarsPriceRankingNewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
