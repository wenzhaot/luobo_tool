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

public class ActivityLevelConditionBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout bottomLine;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final RelativeLayout mboundView0;
    public final ProgressBar progress;
    public final RecyclerView recyclerView;
    public final RelativeLayout resultLine;
    public final TextView resultText;
    public final TextView text;
    public final TextView text2;

    static {
        sViewsWithIds.put(2131624009, 1);
        sViewsWithIds.put(2131624010, 2);
        sViewsWithIds.put(2131624249, 3);
        sViewsWithIds.put(2131624288, 4);
        sViewsWithIds.put(2131624378, 5);
        sViewsWithIds.put(2131624379, 6);
        sViewsWithIds.put(2131623994, 7);
    }

    public ActivityLevelConditionBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.bottomLine = (LinearLayout) bindings[4];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.progress = (ProgressBar) bindings[7];
        this.recyclerView = (RecyclerView) bindings[3];
        this.resultLine = (RelativeLayout) bindings[5];
        this.resultText = (TextView) bindings[6];
        this.text = (TextView) bindings[1];
        this.text2 = (TextView) bindings[2];
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

    public static ActivityLevelConditionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityLevelConditionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityLevelConditionBinding) DataBindingUtil.inflate(inflater, 2130903097, root, attachToRoot, bindingComponent);
    }

    public static ActivityLevelConditionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityLevelConditionBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903097, null, false), bindingComponent);
    }

    public static ActivityLevelConditionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityLevelConditionBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_level_condition_0".equals(view.getTag())) {
            return new ActivityLevelConditionBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
