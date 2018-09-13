package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DialogLogoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags = -1;
    public final TextView tvDialogMsg;

    public DialogLogoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        this.tvDialogMsg = (TextView) mapBindings(bindingComponent, root, 1, sIncludes, sViewsWithIds)[0];
        this.tvDialogMsg.setTag(null);
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

    public static DialogLogoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static DialogLogoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (DialogLogoutBinding) DataBindingUtil.inflate(inflater, 2130903224, root, attachToRoot, bindingComponent);
    }

    public static DialogLogoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static DialogLogoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903224, null, false), bindingComponent);
    }

    public static DialogLogoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static DialogLogoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/dialog_logout_0".equals(view.getTag())) {
            return new DialogLogoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
