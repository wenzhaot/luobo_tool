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
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ActivityFansBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LRecyclerView lrvFans;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;

    static {
        sViewsWithIds.put(2131624359, 1);
    }

    public ActivityFansBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds);
        this.lrvFans = (LRecyclerView) bindings[1];
        this.mboundView0 = (LinearLayout) bindings[0];
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

    public static ActivityFansBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityFansBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityFansBinding) DataBindingUtil.inflate(inflater, 2130903091, root, attachToRoot, bindingComponent);
    }

    public static ActivityFansBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityFansBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903091, null, false), bindingComponent);
    }

    public static ActivityFansBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityFansBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_fans_0".equals(view.getTag())) {
            return new ActivityFansBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
