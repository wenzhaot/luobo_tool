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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UpdateVersionViewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView btCancel;
    public final TextView btUpdate;
    public final LinearLayout llBtn;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final RelativeLayout mboundView0;
    public final ProgressBar progressbar;
    public final TextView tvDescription;
    public final TextView tvProgressNum;
    public final TextView tvTitle;
    public final View vLine;

    static {
        sViewsWithIds.put(2131624296, 1);
        sViewsWithIds.put(2131625523, 2);
        sViewsWithIds.put(2131624473, 3);
        sViewsWithIds.put(2131625524, 4);
        sViewsWithIds.put(2131625525, 5);
        sViewsWithIds.put(2131625526, 6);
        sViewsWithIds.put(2131625348, 7);
        sViewsWithIds.put(2131625527, 8);
    }

    public UpdateVersionViewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.btCancel = (TextView) bindings[7];
        this.btUpdate = (TextView) bindings[8];
        this.llBtn = (LinearLayout) bindings[6];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.progressbar = (ProgressBar) bindings[4];
        this.tvDescription = (TextView) bindings[2];
        this.tvProgressNum = (TextView) bindings[5];
        this.tvTitle = (TextView) bindings[1];
        this.vLine = (View) bindings[3];
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

    public static UpdateVersionViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static UpdateVersionViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (UpdateVersionViewBinding) DataBindingUtil.inflate(inflater, 2130903415, root, attachToRoot, bindingComponent);
    }

    public static UpdateVersionViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static UpdateVersionViewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903415, null, false), bindingComponent);
    }

    public static UpdateVersionViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static UpdateVersionViewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/update_version_view_0".equals(view.getTag())) {
            return new UpdateVersionViewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
