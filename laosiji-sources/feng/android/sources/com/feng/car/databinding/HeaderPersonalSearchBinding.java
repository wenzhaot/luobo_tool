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
import android.widget.TextView;

public class HeaderPersonalSearchBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private Integer mUserless;
    private final LinearLayout mboundView0;
    public final TextView tvCount;

    static {
        sViewsWithIds.put(2131624948, 1);
    }

    public HeaderPersonalSearchBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds);
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvCount = (TextView) bindings[1];
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
            case 76:
                setUserless((Integer) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUserless(Integer Userless) {
        this.mUserless = Userless;
    }

    public Integer getUserless() {
        return this.mUserless;
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

    public static HeaderPersonalSearchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static HeaderPersonalSearchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (HeaderPersonalSearchBinding) DataBindingUtil.inflate(inflater, 2130903255, root, attachToRoot, bindingComponent);
    }

    public static HeaderPersonalSearchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static HeaderPersonalSearchBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903255, null, false), bindingComponent);
    }

    public static HeaderPersonalSearchBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static HeaderPersonalSearchBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/header_personal_search_0".equals(view.getTag())) {
            return new HeaderPersonalSearchBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
