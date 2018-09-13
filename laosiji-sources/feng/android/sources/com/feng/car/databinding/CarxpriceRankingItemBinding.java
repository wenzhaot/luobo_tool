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
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.car.RecommendCarxInfo;

public class CarxpriceRankingItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView averagePriceTip;
    public final TextView avgprice;
    public final TextView carxName;
    public final View divier;
    public final TextView guidePrice;
    public final TextView guidePriceTip;
    private RecommendCarxInfo mCarxInfo;
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;
    public final TextView tvPriceCount;

    static {
        sViewsWithIds.put(2131624912, 5);
        sViewsWithIds.put(2131624914, 6);
        sViewsWithIds.put(2131624751, 7);
    }

    public CarxpriceRankingItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.averagePriceTip = (TextView) bindings[5];
        this.avgprice = (TextView) bindings[2];
        this.avgprice.setTag(null);
        this.carxName = (TextView) bindings[1];
        this.carxName.setTag(null);
        this.divier = (View) bindings[7];
        this.guidePrice = (TextView) bindings[3];
        this.guidePrice.setTag(null);
        this.guidePriceTip = (TextView) bindings[6];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvPriceCount = (TextView) bindings[4];
        this.tvPriceCount.setTag(null);
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
            case 11:
                setCarxInfo((RecommendCarxInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCarxInfo(RecommendCarxInfo CarxInfo) {
        this.mCarxInfo = CarxInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(11);
        super.requestRebind();
    }

    public RecommendCarxInfo getCarxInfo() {
        return this.mCarxInfo;
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
        String carxInfoCarxGetGuidePrice = null;
        String carxInfoCarxGetAvgPrice = null;
        RecommendCarxInfo carxInfo = this.mCarxInfo;
        String carxInfoCarxName = null;
        String carxInfoCarxGetOwnerNum = null;
        CarModelInfo carxInfoCarx = null;
        if ((dirtyFlags & 3) != 0) {
            if (carxInfo != null) {
                carxInfoCarx = carxInfo.carx;
            }
            if (carxInfoCarx != null) {
                carxInfoCarxGetGuidePrice = carxInfoCarx.getGuidePrice();
                carxInfoCarxGetAvgPrice = carxInfoCarx.getAvgPrice();
                carxInfoCarxName = carxInfoCarx.name;
                carxInfoCarxGetOwnerNum = carxInfoCarx.getOwnerNum();
            }
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.avgprice, carxInfoCarxGetAvgPrice);
            TextViewBindingAdapter.setText(this.carxName, carxInfoCarxName);
            TextViewBindingAdapter.setText(this.guidePrice, carxInfoCarxGetGuidePrice);
            TextViewBindingAdapter.setText(this.tvPriceCount, carxInfoCarxGetOwnerNum);
        }
    }

    public static CarxpriceRankingItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CarxpriceRankingItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CarxpriceRankingItemBinding) DataBindingUtil.inflate(inflater, 2130903181, root, attachToRoot, bindingComponent);
    }

    public static CarxpriceRankingItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CarxpriceRankingItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903181, null, false), bindingComponent);
    }

    public static CarxpriceRankingItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CarxpriceRankingItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/carxprice_ranking_item_0".equals(view.getTag())) {
            return new CarxpriceRankingItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
