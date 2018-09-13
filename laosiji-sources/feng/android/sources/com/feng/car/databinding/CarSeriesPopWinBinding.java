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
import android.widget.TextView;
import com.feng.car.view.AutoFrescoDraweeView;

public class CarSeriesPopWinBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdCarBrandImage;
    private long mDirtyFlags = -1;
    private String mStr;
    private final LinearLayout mboundView0;
    public final TextView tvCarBrand;

    static {
        sViewsWithIds.put(2131624901, 1);
        sViewsWithIds.put(2131624902, 2);
    }

    public CarSeriesPopWinBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.afdCarBrandImage = (AutoFrescoDraweeView) bindings[1];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvCarBrand = (TextView) bindings[2];
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
            case 65:
                setStr((String) variable);
                return true;
            default:
                return false;
        }
    }

    public void setStr(String Str) {
        this.mStr = Str;
    }

    public String getStr() {
        return this.mStr;
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

    public static CarSeriesPopWinBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CarSeriesPopWinBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CarSeriesPopWinBinding) DataBindingUtil.inflate(inflater, 2130903178, root, attachToRoot, bindingComponent);
    }

    public static CarSeriesPopWinBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CarSeriesPopWinBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903178, null, false), bindingComponent);
    }

    public static CarSeriesPopWinBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CarSeriesPopWinBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/car_series_pop_win_0".equals(view.getTag())) {
            return new CarSeriesPopWinBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
