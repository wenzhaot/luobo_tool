package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feng.car.view.selectcar.IndexBar.widget.IndexBarWithLetter;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class SelectCarByBrandActivity1Binding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final DrawerLayout drawerlayout;
    public final IndexBarWithLetter indexBar;
    public final LinearLayout llRightDrawer;
    public final LinearLayout llSelectCarByBrand;
    public final LRecyclerView lrecyclerView;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final RecyclerView rvCarBrand;
    public final TextView tvSideBarHint;

    static {
        sViewsWithIds.put(2131625479, 1);
        sViewsWithIds.put(2131625477, 2);
        sViewsWithIds.put(2131624333, 3);
        sViewsWithIds.put(2131624334, 4);
        sViewsWithIds.put(2131625480, 5);
        sViewsWithIds.put(2131625481, 6);
    }

    public SelectCarByBrandActivity1Binding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.drawerlayout = (DrawerLayout) bindings[1];
        this.indexBar = (IndexBarWithLetter) bindings[3];
        this.llRightDrawer = (LinearLayout) bindings[5];
        this.llSelectCarByBrand = (LinearLayout) bindings[0];
        this.llSelectCarByBrand.setTag(null);
        this.lrecyclerView = (LRecyclerView) bindings[6];
        this.rvCarBrand = (RecyclerView) bindings[2];
        this.tvSideBarHint = (TextView) bindings[4];
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

    public static SelectCarByBrandActivity1Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SelectCarByBrandActivity1Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SelectCarByBrandActivity1Binding) DataBindingUtil.inflate(inflater, 2130903381, root, attachToRoot, bindingComponent);
    }

    public static SelectCarByBrandActivity1Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SelectCarByBrandActivity1Binding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903381, null, false), bindingComponent);
    }

    public static SelectCarByBrandActivity1Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SelectCarByBrandActivity1Binding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/select_car_by_brand_activity1_0".equals(view.getTag())) {
            return new SelectCarByBrandActivity1Binding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
