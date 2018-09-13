package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.selectcar.IndexBar.widget.IndexBarWithLetter;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ActivityCityListBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView close;
    public final IndexBarWithLetter indexBar;
    public final RelativeLayout llSearchBar;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final LRecyclerView recyclerview;
    public final TextView searchText;
    public final TextView tvSideBarHint;

    static {
        sViewsWithIds.put(2131624330, 1);
        sViewsWithIds.put(2131624331, 2);
        sViewsWithIds.put(2131624332, 3);
        sViewsWithIds.put(2131624280, 4);
        sViewsWithIds.put(2131624333, 5);
        sViewsWithIds.put(2131624334, 6);
    }

    public ActivityCityListBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.close = (ImageView) bindings[2];
        this.indexBar = (IndexBarWithLetter) bindings[5];
        this.llSearchBar = (RelativeLayout) bindings[1];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.recyclerview = (LRecyclerView) bindings[4];
        this.searchText = (TextView) bindings[3];
        this.tvSideBarHint = (TextView) bindings[6];
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

    public static ActivityCityListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCityListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityCityListBinding) DataBindingUtil.inflate(inflater, 2130903083, root, attachToRoot, bindingComponent);
    }

    public static ActivityCityListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCityListBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903083, null, false), bindingComponent);
    }

    public static ActivityCityListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCityListBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_city_list_0".equals(view.getTag())) {
            return new ActivityCityListBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
