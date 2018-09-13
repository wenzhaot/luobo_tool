package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditarticleApplyDialogBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView applyCancel;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    private final LinearLayout mboundView1;
    public final EditText reaseon;
    public final TextView submit;
    public final TextView tvText;

    static {
        sViewsWithIds.put(2131624312, 2);
        sViewsWithIds.put(2131625012, 3);
        sViewsWithIds.put(2131625013, 4);
        sViewsWithIds.put(2131624747, 5);
    }

    public EditarticleApplyDialogBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.applyCancel = (TextView) bindings[4];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.reaseon = (EditText) bindings[3];
        this.submit = (TextView) bindings[5];
        this.tvText = (TextView) bindings[2];
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

    public static EditarticleApplyDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static EditarticleApplyDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (EditarticleApplyDialogBinding) DataBindingUtil.inflate(inflater, 2130903231, root, attachToRoot, bindingComponent);
    }

    public static EditarticleApplyDialogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static EditarticleApplyDialogBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903231, null, false), bindingComponent);
    }

    public static EditarticleApplyDialogBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static EditarticleApplyDialogBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/editarticle_apply_dialog_0".equals(view.getTag())) {
            return new EditarticleApplyDialogBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
