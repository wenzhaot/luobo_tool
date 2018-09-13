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
import android.widget.TextView;

public class CarOwnerPriceItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView carName;
    public final TextView cityName;
    public final TextView cityText;
    public final LinearLayout countLine;
    public final TextView countText;
    public final View divider;
    public final TextView guideprice;
    public final LinearLayout infoLine;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final TextView nakedPrice;
    public final TextView payment;
    public final TextView remarks;
    public final TextView remarksTip;
    public final TextView time;
    public final TextView totalPrice;
    public final View vPlaceholder;

    static {
        sViewsWithIds.put(2131624871, 1);
        sViewsWithIds.put(2131624740, 2);
        sViewsWithIds.put(2131624872, 3);
        sViewsWithIds.put(2131624734, 4);
        sViewsWithIds.put(2131624873, 5);
        sViewsWithIds.put(2131624862, 6);
        sViewsWithIds.put(2131624874, 7);
        sViewsWithIds.put(2131624868, 8);
        sViewsWithIds.put(2131624865, 9);
        sViewsWithIds.put(2131624867, 10);
        sViewsWithIds.put(2131624875, 11);
        sViewsWithIds.put(2131624876, 12);
        sViewsWithIds.put(2131624877, 13);
        sViewsWithIds.put(2131624240, 14);
        sViewsWithIds.put(2131624878, 15);
    }

    public CarOwnerPriceItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds);
        this.carName = (TextView) bindings[5];
        this.cityName = (TextView) bindings[6];
        this.cityText = (TextView) bindings[2];
        this.countLine = (LinearLayout) bindings[1];
        this.countText = (TextView) bindings[3];
        this.divider = (View) bindings[14];
        this.guideprice = (TextView) bindings[8];
        this.infoLine = (LinearLayout) bindings[4];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.nakedPrice = (TextView) bindings[9];
        this.payment = (TextView) bindings[11];
        this.remarks = (TextView) bindings[13];
        this.remarksTip = (TextView) bindings[12];
        this.time = (TextView) bindings[7];
        this.totalPrice = (TextView) bindings[10];
        this.vPlaceholder = (View) bindings[15];
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

    public static CarOwnerPriceItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CarOwnerPriceItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CarOwnerPriceItemBinding) DataBindingUtil.inflate(inflater, 2130903174, root, attachToRoot, bindingComponent);
    }

    public static CarOwnerPriceItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CarOwnerPriceItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903174, null, false), bindingComponent);
    }

    public static CarOwnerPriceItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CarOwnerPriceItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/car_owner_price_item_0".equals(view.getTag())) {
            return new CarOwnerPriceItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
