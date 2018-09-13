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

public class WebMoreDialogBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView backHome;
    public final TextView cancel;
    public final TextView copyLink;
    private long mDirtyFlags = -1;
    private final LinearLayout mboundView0;
    public final TextView placeView;
    public final TextView tvRefresh;
    public final TextView tvWebOpen;

    static {
        sViewsWithIds.put(2131624828, 1);
        sViewsWithIds.put(2131625577, 2);
        sViewsWithIds.put(2131625578, 3);
        sViewsWithIds.put(2131624834, 4);
        sViewsWithIds.put(2131624835, 5);
        sViewsWithIds.put(2131624291, 6);
    }

    public WebMoreDialogBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.backHome = (TextView) bindings[4];
        this.cancel = (TextView) bindings[6];
        this.copyLink = (TextView) bindings[1];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.placeView = (TextView) bindings[5];
        this.tvRefresh = (TextView) bindings[2];
        this.tvWebOpen = (TextView) bindings[3];
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

    public static WebMoreDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static WebMoreDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (WebMoreDialogBinding) DataBindingUtil.inflate(inflater, 2130903436, root, attachToRoot, bindingComponent);
    }

    public static WebMoreDialogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static WebMoreDialogBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903436, null, false), bindingComponent);
    }

    public static WebMoreDialogBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static WebMoreDialogBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/web_more_dialog_0".equals(view.getTag())) {
            return new WebMoreDialogBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
