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
import com.feng.car.view.CircleRecommendView;
import com.feng.car.view.recyclerview.EmptyView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class CommonRecyclerviewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final CircleRecommendView circleRecommendView;
    public final EmptyView emptyView;
    public final LinearLayout llParent;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final LRecyclerView recyclerview;

    static {
        sViewsWithIds.put(2131624231, 1);
        sViewsWithIds.put(2131624972, 2);
        sViewsWithIds.put(2131624280, 3);
    }

    public CommonRecyclerviewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.circleRecommendView = (CircleRecommendView) bindings[2];
        this.emptyView = (EmptyView) bindings[1];
        this.llParent = (LinearLayout) bindings[0];
        this.llParent.setTag(null);
        this.recyclerview = (LRecyclerView) bindings[3];
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

    public static CommonRecyclerviewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CommonRecyclerviewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CommonRecyclerviewBinding) DataBindingUtil.inflate(inflater, 2130903204, root, attachToRoot, bindingComponent);
    }

    public static CommonRecyclerviewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CommonRecyclerviewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903204, null, false), bindingComponent);
    }

    public static CommonRecyclerviewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CommonRecyclerviewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/common_recyclerview_0".equals(view.getTag())) {
            return new CommonRecyclerviewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
