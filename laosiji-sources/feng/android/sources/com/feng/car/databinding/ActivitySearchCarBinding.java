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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ActivitySearchCarBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView back;
    public final LinearLayout bottomLine;
    public final TextView clear;
    public final RecyclerView groupRecyclerView;
    public final ImageView guidepriceImage;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final RelativeLayout mboundView0;
    public final ProgressBar progress;
    public final ImageView realpriceImage;
    public final LRecyclerView recyclerView;
    public final RelativeLayout resultLine;
    public final TextView resultText;
    public final TextView searchText;
    public final LinearLayout titlebar;

    static {
        sViewsWithIds.put(2131624521, 1);
        sViewsWithIds.put(2131624216, 2);
        sViewsWithIds.put(2131624332, 3);
        sViewsWithIds.put(2131624572, 4);
        sViewsWithIds.put(2131624573, 5);
        sViewsWithIds.put(2131624574, 6);
        sViewsWithIds.put(2131624236, 7);
        sViewsWithIds.put(2131624249, 8);
        sViewsWithIds.put(2131624288, 9);
        sViewsWithIds.put(2131624378, 10);
        sViewsWithIds.put(2131624379, 11);
        sViewsWithIds.put(2131623994, 12);
    }

    public ActivitySearchCarBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds);
        this.back = (ImageView) bindings[2];
        this.bottomLine = (LinearLayout) bindings[9];
        this.clear = (TextView) bindings[4];
        this.groupRecyclerView = (RecyclerView) bindings[7];
        this.guidepriceImage = (ImageView) bindings[5];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.progress = (ProgressBar) bindings[12];
        this.realpriceImage = (ImageView) bindings[6];
        this.recyclerView = (LRecyclerView) bindings[8];
        this.resultLine = (RelativeLayout) bindings[10];
        this.resultText = (TextView) bindings[11];
        this.searchText = (TextView) bindings[3];
        this.titlebar = (LinearLayout) bindings[1];
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

    public static ActivitySearchCarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySearchCarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivitySearchCarBinding) DataBindingUtil.inflate(inflater, 2130903121, root, attachToRoot, bindingComponent);
    }

    public static ActivitySearchCarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySearchCarBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903121, null, false), bindingComponent);
    }

    public static ActivitySearchCarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySearchCarBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_search_car_0".equals(view.getTag())) {
            return new ActivitySearchCarBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
