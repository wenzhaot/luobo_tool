package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityPriceNewRankingBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AppBarLayout appBar;
    public final ImageView backImage;
    public final TextView collectPriceCount;
    public final CoordinatorLayout coordLayout;
    public final TextView coverCarxCount;
    public final ImageView ivAddCarPrice;
    public final ImageView ivPriceFrom;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final RelativeLayout rlFirstChild;
    public final RelativeLayout rlTitleBar;
    public final TabLayout tabLayout;
    public final RecyclerView tagRecyclerView;
    public final TextView tvCityName;
    public final TextView tvSearch;
    public final TextView tvText1;
    public final TextView tvText2;
    public final TextView tvTitle;
    public final TextView updateCount;
    public final View vLine;
    public final View vTabLine;
    public final ViewPager viewPage;

    static {
        sViewsWithIds.put(2131624309, 1);
        sViewsWithIds.put(2131624310, 2);
        sViewsWithIds.put(2131624539, 3);
        sViewsWithIds.put(2131624540, 4);
        sViewsWithIds.put(2131624473, 5);
        sViewsWithIds.put(2131624542, 6);
        sViewsWithIds.put(2131624541, 7);
        sViewsWithIds.put(2131624543, 8);
        sViewsWithIds.put(2131624544, 9);
        sViewsWithIds.put(2131624545, 10);
        sViewsWithIds.put(2131624546, 11);
        sViewsWithIds.put(2131624215, 12);
        sViewsWithIds.put(2131624418, 13);
        sViewsWithIds.put(2131624296, 14);
        sViewsWithIds.put(2131624547, 15);
        sViewsWithIds.put(2131624548, 16);
        sViewsWithIds.put(2131624549, 17);
        sViewsWithIds.put(2131624430, 18);
        sViewsWithIds.put(2131624550, 19);
    }

    public ActivityPriceNewRankingBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds);
        this.appBar = (AppBarLayout) bindings[1];
        this.backImage = (ImageView) bindings[13];
        this.collectPriceCount = (TextView) bindings[4];
        this.coordLayout = (CoordinatorLayout) bindings[0];
        this.coordLayout.setTag(null);
        this.coverCarxCount = (TextView) bindings[8];
        this.ivAddCarPrice = (ImageView) bindings[16];
        this.ivPriceFrom = (ImageView) bindings[9];
        this.rlFirstChild = (RelativeLayout) bindings[2];
        this.rlTitleBar = (RelativeLayout) bindings[12];
        this.tabLayout = (TabLayout) bindings[17];
        this.tagRecyclerView = (RecyclerView) bindings[11];
        this.tvCityName = (TextView) bindings[15];
        this.tvSearch = (TextView) bindings[10];
        this.tvText1 = (TextView) bindings[3];
        this.tvText2 = (TextView) bindings[6];
        this.tvTitle = (TextView) bindings[14];
        this.updateCount = (TextView) bindings[7];
        this.vLine = (View) bindings[5];
        this.vTabLine = (View) bindings[18];
        this.viewPage = (ViewPager) bindings[19];
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
            case 72:
                setUnwanted((Integer) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUnwanted(Integer Unwanted) {
        this.mUnwanted = Unwanted;
    }

    public Integer getUnwanted() {
        return this.mUnwanted;
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

    public static ActivityPriceNewRankingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPriceNewRankingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityPriceNewRankingBinding) DataBindingUtil.inflate(inflater, 2130903112, root, attachToRoot, bindingComponent);
    }

    public static ActivityPriceNewRankingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPriceNewRankingBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903112, null, false), bindingComponent);
    }

    public static ActivityPriceNewRankingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPriceNewRankingBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_price_new_ranking_0".equals(view.getTag())) {
            return new ActivityPriceNewRankingBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
