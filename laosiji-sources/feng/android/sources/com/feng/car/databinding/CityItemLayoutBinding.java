package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feng.car.entity.lcoation.CityInfo;

public class CityItemLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView cityName;
    public final View divierLine;
    private CityInfo mCityInfo;
    private long mDirtyFlags = -1;
    private final LinearLayout mboundView0;

    static {
        sViewsWithIds.put(2131624937, 2);
    }

    public CityItemLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.cityName = (TextView) bindings[1];
        this.cityName.setTag(null);
        this.divierLine = (View) bindings[2];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
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
            case 13:
                setCityInfo((CityInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCityInfo(CityInfo CityInfo) {
        this.mCityInfo = CityInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(13);
        super.requestRebind();
    }

    public CityInfo getCityInfo() {
        return this.mCityInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        CityInfo cityInfo = this.mCityInfo;
        String cityInfoName = null;
        if (!((dirtyFlags & 3) == 0 || cityInfo == null)) {
            cityInfoName = cityInfo.name;
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.cityName, cityInfoName);
        }
    }

    public static CityItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CityItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CityItemLayoutBinding) DataBindingUtil.inflate(inflater, 2130903195, root, attachToRoot, bindingComponent);
    }

    public static CityItemLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CityItemLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903195, null, false), bindingComponent);
    }

    public static CityItemLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CityItemLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/city_item_layout_0".equals(view.getTag())) {
            return new CityItemLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
