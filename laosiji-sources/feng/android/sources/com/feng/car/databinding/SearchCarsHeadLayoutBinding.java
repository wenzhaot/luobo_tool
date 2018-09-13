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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.recyclerview.EmptyView;

public class SearchCarsHeadLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final EmptyView headEmptyView;
    private long mDirtyFlags = -1;
    private final LinearLayout mboundView0;
    public final RelativeLayout rlOwnerCarPrice;
    public final RecyclerView rvCars;
    public final TextView showStopSellingCars;
    public final TextView tvSearchPrice;
    public final TextView tvText;
    public final View vLine;

    static {
        sViewsWithIds.put(2131625458, 1);
        sViewsWithIds.put(2131624312, 2);
        sViewsWithIds.put(2131625459, 3);
        sViewsWithIds.put(2131625460, 4);
        sViewsWithIds.put(2131624473, 5);
        sViewsWithIds.put(2131625461, 6);
        sViewsWithIds.put(2131625462, 7);
    }

    public SearchCarsHeadLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.headEmptyView = (EmptyView) bindings[6];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlOwnerCarPrice = (RelativeLayout) bindings[1];
        this.rvCars = (RecyclerView) bindings[4];
        this.showStopSellingCars = (TextView) bindings[7];
        this.tvSearchPrice = (TextView) bindings[3];
        this.tvText = (TextView) bindings[2];
        this.vLine = (View) bindings[5];
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

    public static SearchCarsHeadLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SearchCarsHeadLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SearchCarsHeadLayoutBinding) DataBindingUtil.inflate(inflater, 2130903373, root, attachToRoot, bindingComponent);
    }

    public static SearchCarsHeadLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SearchCarsHeadLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903373, null, false), bindingComponent);
    }

    public static SearchCarsHeadLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SearchCarsHeadLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/search_cars_head_layout_0".equals(view.getTag())) {
            return new SearchCarsHeadLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
