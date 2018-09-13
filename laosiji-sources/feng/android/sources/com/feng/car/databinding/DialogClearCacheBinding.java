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

public class DialogClearCacheBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags = -1;
    private final LinearLayout mboundView0;

    public DialogClearCacheBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        this.mboundView0 = (LinearLayout) mapBindings(bindingComponent, root, 1, sIncludes, sViewsWithIds)[0];
        this.mboundView0.setTag(null);
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

    public static DialogClearCacheBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static DialogClearCacheBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (DialogClearCacheBinding) DataBindingUtil.inflate(inflater, 2130903223, root, attachToRoot, bindingComponent);
    }

    public static DialogClearCacheBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static DialogClearCacheBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903223, null, false), bindingComponent);
    }

    public static DialogClearCacheBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static DialogClearCacheBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/dialog_clear_cache_0".equals(view.getTag())) {
            return new DialogClearCacheBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
