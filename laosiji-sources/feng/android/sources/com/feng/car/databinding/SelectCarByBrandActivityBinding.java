package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.feng.car.view.selectcar.IndexBar.widget.IndexBarWithLetter;

public class SelectCarByBrandActivityBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final IndexBarWithLetter indexBar;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final FrameLayout mboundView0;
    public final RecyclerView rvCarBrand;
    public final TextView tvSideBarHint;

    static {
        sViewsWithIds.put(2131625477, 1);
        sViewsWithIds.put(2131624333, 2);
        sViewsWithIds.put(2131624334, 3);
    }

    public SelectCarByBrandActivityBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.indexBar = (IndexBarWithLetter) bindings[2];
        this.mboundView0 = (FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rvCarBrand = (RecyclerView) bindings[1];
        this.tvSideBarHint = (TextView) bindings[3];
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

    public static SelectCarByBrandActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SelectCarByBrandActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SelectCarByBrandActivityBinding) DataBindingUtil.inflate(inflater, 2130903380, root, attachToRoot, bindingComponent);
    }

    public static SelectCarByBrandActivityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SelectCarByBrandActivityBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903380, null, false), bindingComponent);
    }

    public static SelectCarByBrandActivityBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SelectCarByBrandActivityBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/select_car_by_brand_activity_0".equals(view.getTag())) {
            return new SelectCarByBrandActivityBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
