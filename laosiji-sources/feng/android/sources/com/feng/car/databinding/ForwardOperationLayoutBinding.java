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

public class ForwardOperationLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout cancelLine;
    public final LinearLayout commentLine;
    public final LinearLayout copyLine;
    public final LinearLayout deleteLine;
    public final TextView deleteText;
    public final LinearLayout forwadLine;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final LinearLayout reportLine;
    public final LinearLayout watchLine;

    static {
        sViewsWithIds.put(2131625039, 1);
        sViewsWithIds.put(2131625040, 2);
        sViewsWithIds.put(2131625041, 3);
        sViewsWithIds.put(2131625042, 4);
        sViewsWithIds.put(2131625043, 5);
        sViewsWithIds.put(2131624277, 6);
        sViewsWithIds.put(2131625044, 7);
        sViewsWithIds.put(2131625045, 8);
    }

    public ForwardOperationLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.cancelLine = (LinearLayout) bindings[8];
        this.commentLine = (LinearLayout) bindings[3];
        this.copyLine = (LinearLayout) bindings[4];
        this.deleteLine = (LinearLayout) bindings[6];
        this.deleteText = (TextView) bindings[7];
        this.forwadLine = (LinearLayout) bindings[2];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.reportLine = (LinearLayout) bindings[5];
        this.watchLine = (LinearLayout) bindings[1];
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

    public static ForwardOperationLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ForwardOperationLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ForwardOperationLayoutBinding) DataBindingUtil.inflate(inflater, 2130903245, root, attachToRoot, bindingComponent);
    }

    public static ForwardOperationLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ForwardOperationLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903245, null, false), bindingComponent);
    }

    public static ForwardOperationLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ForwardOperationLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/forward_operation_layout_0".equals(view.getTag())) {
            return new ForwardOperationLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
