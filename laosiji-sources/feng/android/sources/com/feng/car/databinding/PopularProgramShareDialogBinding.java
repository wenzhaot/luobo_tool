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

public class PopularProgramShareDialogBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView backHome;
    public final TextView cancel;
    public final TextView copyLink;
    public final View divider;
    public final TextView friendsShare;
    private long mDirtyFlags = -1;
    private final LinearLayout mboundView0;
    public final TextView qqShare;
    public final TextView qzoneShare;
    public final LinearLayout shareLine;
    public final TextView weiboShare;
    public final TextView weixinShare;

    static {
        sViewsWithIds.put(2131624821, 1);
        sViewsWithIds.put(2131624822, 2);
        sViewsWithIds.put(2131624823, 3);
        sViewsWithIds.put(2131624824, 4);
        sViewsWithIds.put(2131624825, 5);
        sViewsWithIds.put(2131624826, 6);
        sViewsWithIds.put(2131624240, 7);
        sViewsWithIds.put(2131624828, 8);
        sViewsWithIds.put(2131624834, 9);
        sViewsWithIds.put(2131624291, 10);
    }

    public PopularProgramShareDialogBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.backHome = (TextView) bindings[9];
        this.cancel = (TextView) bindings[10];
        this.copyLink = (TextView) bindings[8];
        this.divider = (View) bindings[7];
        this.friendsShare = (TextView) bindings[2];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.qqShare = (TextView) bindings[5];
        this.qzoneShare = (TextView) bindings[6];
        this.shareLine = (LinearLayout) bindings[1];
        this.weiboShare = (TextView) bindings[4];
        this.weixinShare = (TextView) bindings[3];
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

    public static PopularProgramShareDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static PopularProgramShareDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (PopularProgramShareDialogBinding) DataBindingUtil.inflate(inflater, 2130903344, root, attachToRoot, bindingComponent);
    }

    public static PopularProgramShareDialogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static PopularProgramShareDialogBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903344, null, false), bindingComponent);
    }

    public static PopularProgramShareDialogBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static PopularProgramShareDialogBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/popular_program_share_dialog_0".equals(view.getTag())) {
            return new PopularProgramShareDialogBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
