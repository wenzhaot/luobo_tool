package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarOwnerPriceHeaderBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView cityName;
    public final FrameLayout emptyView;
    public final TextView guideprice;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final TextView nakedName;
    public final TextView nakedPrice;
    public final TextView taxText;
    public final TextView taxTips;
    public final TextView title;
    public final TextView titleTips;
    public final TextView totalName;
    public final TextView totalPrice;

    static {
        sViewsWithIds.put(2131624862, 1);
        sViewsWithIds.put(2131624015, 2);
        sViewsWithIds.put(2131624863, 3);
        sViewsWithIds.put(2131624864, 4);
        sViewsWithIds.put(2131624865, 5);
        sViewsWithIds.put(2131624866, 6);
        sViewsWithIds.put(2131624867, 7);
        sViewsWithIds.put(2131624868, 8);
        sViewsWithIds.put(2131624869, 9);
        sViewsWithIds.put(2131624870, 10);
        sViewsWithIds.put(2131624231, 11);
    }

    public CarOwnerPriceHeaderBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds);
        this.cityName = (TextView) bindings[1];
        this.emptyView = (FrameLayout) bindings[11];
        this.guideprice = (TextView) bindings[8];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.nakedName = (TextView) bindings[4];
        this.nakedPrice = (TextView) bindings[5];
        this.taxText = (TextView) bindings[10];
        this.taxTips = (TextView) bindings[9];
        this.title = (TextView) bindings[2];
        this.titleTips = (TextView) bindings[3];
        this.totalName = (TextView) bindings[6];
        this.totalPrice = (TextView) bindings[7];
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

    public static CarOwnerPriceHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CarOwnerPriceHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CarOwnerPriceHeaderBinding) DataBindingUtil.inflate(inflater, 2130903173, root, attachToRoot, bindingComponent);
    }

    public static CarOwnerPriceHeaderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CarOwnerPriceHeaderBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903173, null, false), bindingComponent);
    }

    public static CarOwnerPriceHeaderBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CarOwnerPriceHeaderBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/car_owner_price_header_0".equals(view.getTag())) {
            return new CarOwnerPriceHeaderBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
