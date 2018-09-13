package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class CarsOwnerPriceItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView discount;
    public final View divider;
    public final AutoFrescoDraweeView image;
    public final TextView level;
    private CarSeriesInfo mCarSeriesInfo;
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;
    public final TextView name;
    public final TextView ownerPriceCount;
    public final TextView price;
    public final TextView voiceText;

    static {
        sViewsWithIds.put(2131624155, 2);
        sViewsWithIds.put(2131624750, 3);
        sViewsWithIds.put(2131624904, 4);
        sViewsWithIds.put(2131624905, 5);
        sViewsWithIds.put(2131624906, 6);
        sViewsWithIds.put(2131624907, 7);
        sViewsWithIds.put(2131624240, 8);
    }

    public CarsOwnerPriceItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.discount = (TextView) bindings[6];
        this.divider = (View) bindings[8];
        this.image = (AutoFrescoDraweeView) bindings[2];
        this.level = (TextView) bindings[1];
        this.level.setTag(null);
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.name = (TextView) bindings[3];
        this.ownerPriceCount = (TextView) bindings[5];
        this.price = (TextView) bindings[4];
        this.voiceText = (TextView) bindings[7];
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
            case 7:
                setCarSeriesInfo((CarSeriesInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCarSeriesInfo(CarSeriesInfo CarSeriesInfo) {
        this.mCarSeriesInfo = CarSeriesInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(7);
        super.requestRebind();
    }

    public CarSeriesInfo getCarSeriesInfo() {
        return this.mCarSeriesInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        CarSeriesInfo carSeriesInfo = this.mCarSeriesInfo;
        String carSeriesInfoLevel = null;
        if (!((dirtyFlags & 3) == 0 || carSeriesInfo == null)) {
            carSeriesInfoLevel = carSeriesInfo.level;
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.level, carSeriesInfoLevel);
        }
    }

    public static CarsOwnerPriceItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CarsOwnerPriceItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CarsOwnerPriceItemBinding) DataBindingUtil.inflate(inflater, 2130903179, root, attachToRoot, bindingComponent);
    }

    public static CarsOwnerPriceItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CarsOwnerPriceItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903179, null, false), bindingComponent);
    }

    public static CarsOwnerPriceItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CarsOwnerPriceItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/cars_owner_price_item_0".equals(view.getTag())) {
            return new CarsOwnerPriceItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
