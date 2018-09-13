package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarPriceCityHeaderBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final Button btnAllTransactionPrice;
    public final View divider;
    public final ImageView ivEmpty;
    public final LinearLayout llAdviserParent;
    public final LinearLayout llCityParent;
    public final LinearLayout llEmpty;
    public final LinearLayout llParent;
    public final LinearLayout llProvinceParent;
    public final LinearLayout llTransactionParent;
    private String mCity;
    private long mDirtyFlags = -1;
    public final RecyclerView rvCarsInSale;
    public final RecyclerView rvNearbyCity;
    public final TextView tvAdviserPrice;
    public final TextView tvAdviserPriceTip;
    public final TextView tvEmptyCity;
    public final TextView tvOnSaleCarCount;
    public final TextView tvPercent;
    public final TextView tvTransactionMsgCount;
    public final TextView tvTransactionMsgTip;
    public final TextView tvTuneRange;
    public final TextView tvTuneRank;
    public final TextView tvTuneTip;

    static {
        sViewsWithIds.put(2131624880, 2);
        sViewsWithIds.put(2131624881, 3);
        sViewsWithIds.put(2131624882, 4);
        sViewsWithIds.put(2131624883, 5);
        sViewsWithIds.put(2131624884, 6);
        sViewsWithIds.put(2131624885, 7);
        sViewsWithIds.put(2131624886, 8);
        sViewsWithIds.put(2131624887, 9);
        sViewsWithIds.put(2131624240, 10);
        sViewsWithIds.put(2131624888, 11);
        sViewsWithIds.put(2131624889, 12);
        sViewsWithIds.put(2131624890, 13);
        sViewsWithIds.put(2131624891, 14);
        sViewsWithIds.put(2131624892, 15);
        sViewsWithIds.put(2131624893, 16);
        sViewsWithIds.put(2131624894, 17);
        sViewsWithIds.put(2131624895, 18);
        sViewsWithIds.put(2131624808, 19);
        sViewsWithIds.put(2131624483, 20);
    }

    public CarPriceCityHeaderBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 21, sIncludes, sViewsWithIds);
        this.btnAllTransactionPrice = (Button) bindings[16];
        this.divider = (View) bindings[10];
        this.ivEmpty = (ImageView) bindings[20];
        this.llAdviserParent = (LinearLayout) bindings[7];
        this.llCityParent = (LinearLayout) bindings[2];
        this.llEmpty = (LinearLayout) bindings[19];
        this.llParent = (LinearLayout) bindings[0];
        this.llParent.setTag(null);
        this.llProvinceParent = (LinearLayout) bindings[14];
        this.llTransactionParent = (LinearLayout) bindings[11];
        this.rvCarsInSale = (RecyclerView) bindings[18];
        this.rvNearbyCity = (RecyclerView) bindings[15];
        this.tvAdviserPrice = (TextView) bindings[9];
        this.tvAdviserPriceTip = (TextView) bindings[8];
        this.tvEmptyCity = (TextView) bindings[1];
        this.tvEmptyCity.setTag(null);
        this.tvOnSaleCarCount = (TextView) bindings[17];
        this.tvPercent = (TextView) bindings[5];
        this.tvTransactionMsgCount = (TextView) bindings[13];
        this.tvTransactionMsgTip = (TextView) bindings[12];
        this.tvTuneRange = (TextView) bindings[4];
        this.tvTuneRank = (TextView) bindings[6];
        this.tvTuneTip = (TextView) bindings[3];
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
            case 12:
                setCity((String) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCity(String City) {
        this.mCity = City;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(12);
        super.requestRebind();
    }

    public String getCity() {
        return this.mCity;
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
        String city = this.mCity;
        if ((dirtyFlags & 3) != 0) {
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.tvEmptyCity, city);
        }
    }

    public static CarPriceCityHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CarPriceCityHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CarPriceCityHeaderBinding) DataBindingUtil.inflate(inflater, 2130903176, root, attachToRoot, bindingComponent);
    }

    public static CarPriceCityHeaderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CarPriceCityHeaderBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903176, null, false), bindingComponent);
    }

    public static CarPriceCityHeaderBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CarPriceCityHeaderBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/car_price_city_header_0".equals(view.getTag())) {
            return new CarPriceCityHeaderBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
