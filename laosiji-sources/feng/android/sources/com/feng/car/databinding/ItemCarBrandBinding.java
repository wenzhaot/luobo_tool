package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarBrandInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class ItemCarBrandBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdCarBrandImage;
    private CarBrandInfo mCarBrandInfo;
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;
    public final TextView tvCarBrand;
    public final View vLine;

    static {
        sViewsWithIds.put(2131624901, 2);
        sViewsWithIds.put(2131624473, 3);
    }

    public ItemCarBrandBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.afdCarBrandImage = (AutoFrescoDraweeView) bindings[2];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvCarBrand = (TextView) bindings[1];
        this.tvCarBrand.setTag(null);
        this.vLine = (View) bindings[3];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4;
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
            case 3:
                setCarBrandInfo((CarBrandInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCarBrandInfo(CarBrandInfo CarBrandInfo) {
        this.mCarBrandInfo = CarBrandInfo;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(3);
        super.requestRebind();
    }

    public CarBrandInfo getCarBrandInfo() {
        return this.mCarBrandInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeCarBrandInfoName((ObservableField) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeCarBrandInfoName(ObservableField<String> observableField, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 1;
                }
                return true;
            default:
                return false;
        }
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        CarBrandInfo carBrandInfo = this.mCarBrandInfo;
        String carBrandInfoNameGet = null;
        ObservableField<String> carBrandInfoName = null;
        if ((dirtyFlags & 7) != 0) {
            if (carBrandInfo != null) {
                carBrandInfoName = carBrandInfo.name;
            }
            updateRegistration(0, carBrandInfoName);
            if (carBrandInfoName != null) {
                carBrandInfoNameGet = (String) carBrandInfoName.get();
            }
        }
        if ((dirtyFlags & 7) != 0) {
            TextViewBindingAdapter.setText(this.tvCarBrand, carBrandInfoNameGet);
        }
    }

    public static ItemCarBrandBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCarBrandBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemCarBrandBinding) DataBindingUtil.inflate(inflater, 2130903264, root, attachToRoot, bindingComponent);
    }

    public static ItemCarBrandBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCarBrandBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903264, null, false), bindingComponent);
    }

    public static ItemCarBrandBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCarBrandBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_car_brand_0".equals(view.getTag())) {
            return new ItemCarBrandBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
