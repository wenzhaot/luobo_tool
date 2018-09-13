package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CircleAccededHeaderBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView ivTitleLeft;
    public final RelativeLayout llSearchBar;
    private long mDirtyFlags = -1;
    private String mUseless;
    public final TextView tvSearch;

    static {
        sViewsWithIds.put(2131624295, 1);
        sViewsWithIds.put(2131624545, 2);
    }

    public CircleAccededHeaderBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.ivTitleLeft = (ImageView) bindings[1];
        this.llSearchBar = (RelativeLayout) bindings[0];
        this.llSearchBar.setTag(null);
        this.tvSearch = (TextView) bindings[2];
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
            case 74:
                setUseless((String) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUseless(String Useless) {
        this.mUseless = Useless;
    }

    public String getUseless() {
        return this.mUseless;
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

    public static CircleAccededHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CircleAccededHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CircleAccededHeaderBinding) DataBindingUtil.inflate(inflater, 2130903187, root, attachToRoot, bindingComponent);
    }

    public static CircleAccededHeaderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CircleAccededHeaderBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903187, null, false), bindingComponent);
    }

    public static CircleAccededHeaderBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CircleAccededHeaderBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/circle_acceded_header_0".equals(view.getTag())) {
            return new CircleAccededHeaderBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
