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
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectMiddleTitleBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView ivRankDownUp;
    public final LinearLayout llParent;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final TextView tvTitle;

    static {
        sViewsWithIds.put(2131624296, 1);
        sViewsWithIds.put(2131625484, 2);
    }

    public SelectMiddleTitleBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.ivRankDownUp = (ImageView) bindings[2];
        this.llParent = (LinearLayout) bindings[0];
        this.llParent.setTag(null);
        this.tvTitle = (TextView) bindings[1];
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

    public static SelectMiddleTitleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SelectMiddleTitleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SelectMiddleTitleBinding) DataBindingUtil.inflate(inflater, 2130903386, root, attachToRoot, bindingComponent);
    }

    public static SelectMiddleTitleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SelectMiddleTitleBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903386, null, false), bindingComponent);
    }

    public static SelectMiddleTitleBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SelectMiddleTitleBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/select_middle_title_0".equals(view.getTag())) {
            return new SelectMiddleTitleBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
