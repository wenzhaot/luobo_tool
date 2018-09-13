package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class PricesSeriesChartLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;
    public final LRecyclerView recyclerview;
    public final LinearLayout rlBottom;
    public final RelativeLayout rlName;
    public final TextView tvCarName;
    public final TextView tvCityName;
    public final TextView tvContent;
    public final TextView tvOwnerPrice;
    public final TextView tvOwnerText;
    public final TextView tvPrice;
    public final TextView tvPriceText;

    static {
        sViewsWithIds.put(2131625408, 1);
        sViewsWithIds.put(2131625409, 2);
        sViewsWithIds.put(2131624859, 3);
        sViewsWithIds.put(2131624280, 4);
        sViewsWithIds.put(2131624728, 5);
        sViewsWithIds.put(2131624547, 6);
        sViewsWithIds.put(2131624583, 7);
        sViewsWithIds.put(2131624761, 8);
        sViewsWithIds.put(2131625410, 9);
        sViewsWithIds.put(2131625411, 10);
    }

    public PricesSeriesChartLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.recyclerview = (LRecyclerView) bindings[4];
        this.rlBottom = (LinearLayout) bindings[5];
        this.rlName = (RelativeLayout) bindings[1];
        this.tvCarName = (TextView) bindings[2];
        this.tvCityName = (TextView) bindings[6];
        this.tvContent = (TextView) bindings[3];
        this.tvOwnerPrice = (TextView) bindings[10];
        this.tvOwnerText = (TextView) bindings[9];
        this.tvPrice = (TextView) bindings[8];
        this.tvPriceText = (TextView) bindings[7];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1;
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
        return false;
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

    public static PricesSeriesChartLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static PricesSeriesChartLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (PricesSeriesChartLayoutBinding) DataBindingUtil.inflate(inflater, 2130903356, root, attachToRoot, bindingComponent);
    }

    public static PricesSeriesChartLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static PricesSeriesChartLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903356, null, false), bindingComponent);
    }

    public static PricesSeriesChartLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static PricesSeriesChartLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/prices_series_chart_layout_0".equals(view.getTag())) {
            return new PricesSeriesChartLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
