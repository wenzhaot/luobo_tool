package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimepickerLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final DatePicker datePicker;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final TextView timeSubmit;

    static {
        sViewsWithIds.put(2131625503, 1);
        sViewsWithIds.put(2131625504, 2);
    }

    public TimepickerLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.datePicker = (DatePicker) bindings[1];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.timeSubmit = (TextView) bindings[2];
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

    public static TimepickerLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static TimepickerLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (TimepickerLayoutBinding) DataBindingUtil.inflate(inflater, 2130903411, root, attachToRoot, bindingComponent);
    }

    public static TimepickerLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static TimepickerLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903411, null, false), bindingComponent);
    }

    public static TimepickerLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static TimepickerLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/timepicker_layout_0".equals(view.getTag())) {
            return new TimepickerLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
