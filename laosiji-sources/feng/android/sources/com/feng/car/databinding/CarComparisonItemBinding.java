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
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import com.feng.car.entity.car.CarModelInfo;

public class CarComparisonItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final CheckBox cbCarSel;
    public final View line;
    private long mDirtyFlags = -1;
    private CarModelInfo mInfo;
    private final RelativeLayout mboundView0;

    static {
        sViewsWithIds.put(2131624374, 2);
    }

    public CarComparisonItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.cbCarSel = (CheckBox) bindings[1];
        this.cbCarSel.setTag(null);
        this.line = (View) bindings[2];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
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
            case 28:
                setInfo((CarModelInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(CarModelInfo Info) {
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public CarModelInfo getInfo() {
        return this.mInfo;
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
        String infoName = null;
        CarModelInfo info = this.mInfo;
        if (!((dirtyFlags & 3) == 0 || info == null)) {
            infoName = info.name;
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.cbCarSel, infoName);
        }
    }

    public static CarComparisonItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CarComparisonItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CarComparisonItemBinding) DataBindingUtil.inflate(inflater, 2130903170, root, attachToRoot, bindingComponent);
    }

    public static CarComparisonItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CarComparisonItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903170, null, false), bindingComponent);
    }

    public static CarComparisonItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CarComparisonItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/car_comparison_item_0".equals(view.getTag())) {
            return new CarComparisonItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
