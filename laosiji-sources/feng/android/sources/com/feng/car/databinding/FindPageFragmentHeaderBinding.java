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
import android.widget.LinearLayout;
import android.widget.TextView;

public class FindPageFragmentHeaderBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout llSearchCar;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final RecyclerView rvSelectCarPhoto;
    public final LinearLayout tvCarPrice;
    public final LinearLayout tvCarRank;
    public final LinearLayout tvMoreCondition;
    public final TextView tvSearchCar;

    static {
        sViewsWithIds.put(2131625028, 1);
        sViewsWithIds.put(2131625029, 2);
        sViewsWithIds.put(2131625030, 3);
        sViewsWithIds.put(2131625031, 4);
        sViewsWithIds.put(2131625032, 5);
        sViewsWithIds.put(2131625033, 6);
    }

    public FindPageFragmentHeaderBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.llSearchCar = (LinearLayout) bindings[1];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rvSelectCarPhoto = (RecyclerView) bindings[6];
        this.tvCarPrice = (LinearLayout) bindings[2];
        this.tvCarRank = (LinearLayout) bindings[3];
        this.tvMoreCondition = (LinearLayout) bindings[4];
        this.tvSearchCar = (TextView) bindings[5];
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

    public static FindPageFragmentHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static FindPageFragmentHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (FindPageFragmentHeaderBinding) DataBindingUtil.inflate(inflater, 2130903242, root, attachToRoot, bindingComponent);
    }

    public static FindPageFragmentHeaderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static FindPageFragmentHeaderBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903242, null, false), bindingComponent);
    }

    public static FindPageFragmentHeaderBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static FindPageFragmentHeaderBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/find_page_fragment_header_0".equals(view.getTag())) {
            return new FindPageFragmentHeaderBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
