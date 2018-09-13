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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivitySearchConditionLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout bottomLine;
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;
    public final ProgressBar progress;
    public final RecyclerView recyclerview;
    public final RelativeLayout resultLine;
    public final TextView resultText;

    static {
        sViewsWithIds.put(2131624280, 1);
        sViewsWithIds.put(2131624288, 2);
        sViewsWithIds.put(2131624378, 3);
        sViewsWithIds.put(2131624379, 4);
        sViewsWithIds.put(2131623994, 5);
    }

    public ActivitySearchConditionLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.bottomLine = (LinearLayout) bindings[2];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.progress = (ProgressBar) bindings[5];
        this.recyclerview = (RecyclerView) bindings[1];
        this.resultLine = (RelativeLayout) bindings[3];
        this.resultText = (TextView) bindings[4];
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

    public static ActivitySearchConditionLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySearchConditionLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivitySearchConditionLayoutBinding) DataBindingUtil.inflate(inflater, 2130903123, root, attachToRoot, bindingComponent);
    }

    public static ActivitySearchConditionLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySearchConditionLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903123, null, false), bindingComponent);
    }

    public static ActivitySearchConditionLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySearchConditionLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_search_condition_layout_0".equals(view.getTag())) {
            return new ActivitySearchConditionLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
