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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.ClearEditText;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ActivitySearchCityBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView emptyView;
    public final ClearEditText etSearchKey;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final RecyclerView recordRecyclerView;
    public final LRecyclerView recyclerView;
    public final RelativeLayout rlSearchBar;
    public final TextView tvCancel;

    static {
        sViewsWithIds.put(2131624575, 1);
        sViewsWithIds.put(2131624219, 2);
        sViewsWithIds.put(2131624576, 3);
        sViewsWithIds.put(2131624231, 4);
        sViewsWithIds.put(2131624249, 5);
        sViewsWithIds.put(2131624577, 6);
    }

    public ActivitySearchCityBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.emptyView = (ImageView) bindings[4];
        this.etSearchKey = (ClearEditText) bindings[2];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.recordRecyclerView = (RecyclerView) bindings[6];
        this.recyclerView = (LRecyclerView) bindings[5];
        this.rlSearchBar = (RelativeLayout) bindings[1];
        this.tvCancel = (TextView) bindings[3];
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

    public static ActivitySearchCityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySearchCityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivitySearchCityBinding) DataBindingUtil.inflate(inflater, 2130903122, root, attachToRoot, bindingComponent);
    }

    public static ActivitySearchCityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySearchCityBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903122, null, false), bindingComponent);
    }

    public static ActivitySearchCityBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySearchCityBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_search_city_0".equals(view.getTag())) {
            return new ActivitySearchCityBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
