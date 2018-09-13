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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.PriceVo;
import com.feng.car.entity.lcoation.CityInfo;
import com.feng.car.entity.lcoation.CityPriceInfo;

public class CarPriceHeaderCityItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View divider;
    private CityPriceInfo mCityPriceInfo;
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;
    public final TextView tvCity;
    public final TextView tvPriceChangeProportion;
    public final TextView tvPriceChangeTip;
    public final TextView tvTransactionMsg;

    static {
        sViewsWithIds.put(2131624898, 3);
        sViewsWithIds.put(2131624900, 4);
        sViewsWithIds.put(2131624240, 5);
    }

    public CarPriceHeaderCityItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.divider = (View) bindings[5];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvCity = (TextView) bindings[1];
        this.tvCity.setTag(null);
        this.tvPriceChangeProportion = (TextView) bindings[4];
        this.tvPriceChangeTip = (TextView) bindings[3];
        this.tvTransactionMsg = (TextView) bindings[2];
        this.tvTransactionMsg.setTag(null);
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
            case 14:
                setCityPriceInfo((CityPriceInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCityPriceInfo(CityPriceInfo CityPriceInfo) {
        this.mCityPriceInfo = CityPriceInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(14);
        super.requestRebind();
    }

    public CityPriceInfo getCityPriceInfo() {
        return this.mCityPriceInfo;
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
        CityPriceInfo cityPriceInfo = this.mCityPriceInfo;
        String cityPriceInfoPriceGetOwernum = null;
        CityInfo cityPriceInfoCity = null;
        PriceVo cityPriceInfoPrice = null;
        String cityPriceInfoCityName = null;
        if ((dirtyFlags & 3) != 0) {
            if (cityPriceInfo != null) {
                cityPriceInfoCity = cityPriceInfo.city;
                cityPriceInfoPrice = cityPriceInfo.price;
            }
            if (cityPriceInfoCity != null) {
                cityPriceInfoCityName = cityPriceInfoCity.name;
            }
            if (cityPriceInfoPrice != null) {
                cityPriceInfoPriceGetOwernum = cityPriceInfoPrice.getOwernum();
            }
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.tvCity, cityPriceInfoCityName);
            TextViewBindingAdapter.setText(this.tvTransactionMsg, cityPriceInfoPriceGetOwernum);
        }
    }

    public static CarPriceHeaderCityItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CarPriceHeaderCityItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CarPriceHeaderCityItemBinding) DataBindingUtil.inflate(inflater, 2130903177, root, attachToRoot, bindingComponent);
    }

    public static CarPriceHeaderCityItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CarPriceHeaderCityItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903177, null, false), bindingComponent);
    }

    public static CarPriceHeaderCityItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CarPriceHeaderCityItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/car_price_header_city_item_0".equals(view.getTag())) {
            return new CarPriceHeaderCityItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
