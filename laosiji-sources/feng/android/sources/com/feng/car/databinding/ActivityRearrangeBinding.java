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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityRearrangeBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView back;
    public final Button closeTipsButton;
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;
    public final RecyclerView recyclerView;
    public final RelativeLayout rlTitleBar;
    public final TextView text;
    public final RelativeLayout tipsLine;
    public final TextView tvFinish;
    public final TextView tvTitleName;

    static {
        sViewsWithIds.put(2131624215, 1);
        sViewsWithIds.put(2131624216, 2);
        sViewsWithIds.put(2131624217, 3);
        sViewsWithIds.put(2131624218, 4);
        sViewsWithIds.put(2131624249, 5);
        sViewsWithIds.put(2131624568, 6);
        sViewsWithIds.put(2131624009, 7);
        sViewsWithIds.put(2131624569, 8);
    }

    public ActivityRearrangeBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.back = (TextView) bindings[2];
        this.closeTipsButton = (Button) bindings[8];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.recyclerView = (RecyclerView) bindings[5];
        this.rlTitleBar = (RelativeLayout) bindings[1];
        this.text = (TextView) bindings[7];
        this.tipsLine = (RelativeLayout) bindings[6];
        this.tvFinish = (TextView) bindings[4];
        this.tvTitleName = (TextView) bindings[3];
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

    public static ActivityRearrangeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityRearrangeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityRearrangeBinding) DataBindingUtil.inflate(inflater, 2130903119, root, attachToRoot, bindingComponent);
    }

    public static ActivityRearrangeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityRearrangeBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903119, null, false), bindingComponent);
    }

    public static ActivityRearrangeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityRearrangeBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_rearrange_0".equals(view.getTag())) {
            return new ActivityRearrangeBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
