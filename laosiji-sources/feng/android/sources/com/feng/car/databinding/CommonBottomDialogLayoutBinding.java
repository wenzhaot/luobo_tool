package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommonBottomDialogLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout llCommonDialogContainer;
    public final LinearLayout llDialogCancelLine;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final RecyclerView rvCommonDialogList;
    public final TextView tvCancel;
    public final TextView tvDialogSecondTitle;
    public final TextView tvDialogTitle;
    public final View vLine;
    public final View viewDialogCancelTopDivider;

    static {
        sViewsWithIds.put(2131624958, 1);
        sViewsWithIds.put(2131624959, 2);
        sViewsWithIds.put(2131624473, 3);
        sViewsWithIds.put(2131624960, 4);
        sViewsWithIds.put(2131624961, 5);
        sViewsWithIds.put(2131624962, 6);
        sViewsWithIds.put(2131624576, 7);
    }

    public CommonBottomDialogLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.llCommonDialogContainer = (LinearLayout) bindings[0];
        this.llCommonDialogContainer.setTag(null);
        this.llDialogCancelLine = (LinearLayout) bindings[6];
        this.rvCommonDialogList = (RecyclerView) bindings[4];
        this.tvCancel = (TextView) bindings[7];
        this.tvDialogSecondTitle = (TextView) bindings[2];
        this.tvDialogTitle = (TextView) bindings[1];
        this.vLine = (View) bindings[3];
        this.viewDialogCancelTopDivider = (View) bindings[5];
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

    public static CommonBottomDialogLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CommonBottomDialogLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CommonBottomDialogLayoutBinding) DataBindingUtil.inflate(inflater, 2130903202, root, attachToRoot, bindingComponent);
    }

    public static CommonBottomDialogLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CommonBottomDialogLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903202, null, false), bindingComponent);
    }

    public static CommonBottomDialogLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CommonBottomDialogLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/common_bottom_dialog_layout_0".equals(view.getTag())) {
            return new CommonBottomDialogLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
