package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.recyclerview.EmptyView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ChoiceFragmentLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final EmptyView emptyView;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final LRecyclerView recyclerview;
    public final RelativeLayout rlParent;
    public final TextView tvRefdataHint;

    static {
        sViewsWithIds.put(2131624280, 1);
        sViewsWithIds.put(2131624916, 2);
        sViewsWithIds.put(2131624231, 3);
    }

    public ChoiceFragmentLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.emptyView = (EmptyView) bindings[3];
        this.recyclerview = (LRecyclerView) bindings[1];
        this.rlParent = (RelativeLayout) bindings[0];
        this.rlParent.setTag(null);
        this.tvRefdataHint = (TextView) bindings[2];
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

    public static ChoiceFragmentLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ChoiceFragmentLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ChoiceFragmentLayoutBinding) DataBindingUtil.inflate(inflater, 2130903182, root, attachToRoot, bindingComponent);
    }

    public static ChoiceFragmentLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ChoiceFragmentLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903182, null, false), bindingComponent);
    }

    public static ChoiceFragmentLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ChoiceFragmentLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/choice_fragment_layout_0".equals(view.getTag())) {
            return new ChoiceFragmentLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
