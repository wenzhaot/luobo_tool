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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogOpenNotifyBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView cancelText;
    public final ImageView ivClose;
    public final LinearLayout llParent;
    private long mDirtyFlags = -1;
    private Integer mUseless;
    private final RelativeLayout mboundView0;
    public final TextView openText;

    static {
        sViewsWithIds.put(2131624641, 2);
        sViewsWithIds.put(2131624992, 3);
        sViewsWithIds.put(2131624993, 4);
    }

    public DialogOpenNotifyBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.cancelText = (TextView) bindings[2];
        this.ivClose = (ImageView) bindings[4];
        this.llParent = (LinearLayout) bindings[1];
        this.llParent.setTag(null);
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.openText = (TextView) bindings[3];
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
                setUseless((Integer) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUseless(Integer Useless) {
        this.mUseless = Useless;
    }

    public Integer getUseless() {
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

    public static DialogOpenNotifyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static DialogOpenNotifyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (DialogOpenNotifyBinding) DataBindingUtil.inflate(inflater, 2130903225, root, attachToRoot, bindingComponent);
    }

    public static DialogOpenNotifyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static DialogOpenNotifyBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903225, null, false), bindingComponent);
    }

    public static DialogOpenNotifyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static DialogOpenNotifyBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/dialog_open_notify_0".equals(view.getTag())) {
            return new DialogOpenNotifyBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
