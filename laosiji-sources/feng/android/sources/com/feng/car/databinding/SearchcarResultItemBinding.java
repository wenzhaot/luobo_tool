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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarFactoryInfo;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class SearchcarResultItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView count;
    public final TextView discount;
    public final View divider;
    public final View divierLine1;
    public final View divierLine2;
    public final AutoFrescoDraweeView image;
    public final ImageView ivOpenClose;
    public final ImageView ivStopSell;
    public final TextView level;
    private CarSeriesInfo mCarSeriesInfo;
    private long mDirtyFlags = -1;
    public final TextView name;
    public final RelativeLayout parentLine;
    public final TextView price;
    public final ProgressBar progressBar;
    public final RelativeLayout rlBottom;
    public final RelativeLayout rlCarsInfo;
    public final RecyclerView rvCarModel;
    public final LinearLayout stopSaleLine;
    public final TextView stopSaleText;
    public final TextView tvCarBrand;
    public final TextView voiceText;

    static {
        sViewsWithIds.put(2131624155, 3);
        sViewsWithIds.put(2131625468, 4);
        sViewsWithIds.put(2131624750, 5);
        sViewsWithIds.put(2131624904, 6);
        sViewsWithIds.put(2131624906, 7);
        sViewsWithIds.put(2131625469, 8);
        sViewsWithIds.put(2131624728, 9);
        sViewsWithIds.put(2131625351, 10);
        sViewsWithIds.put(2131625470, 11);
        sViewsWithIds.put(2131625471, 12);
        sViewsWithIds.put(2131624907, 13);
        sViewsWithIds.put(2131625472, 14);
        sViewsWithIds.put(2131625473, 15);
        sViewsWithIds.put(2131625474, 16);
        sViewsWithIds.put(2131624240, 17);
        sViewsWithIds.put(2131625475, 18);
        sViewsWithIds.put(2131625476, 19);
    }

    public SearchcarResultItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds);
        this.count = (TextView) bindings[10];
        this.discount = (TextView) bindings[7];
        this.divider = (View) bindings[17];
        this.divierLine1 = (View) bindings[15];
        this.divierLine2 = (View) bindings[16];
        this.image = (AutoFrescoDraweeView) bindings[3];
        this.ivOpenClose = (ImageView) bindings[11];
        this.ivStopSell = (ImageView) bindings[8];
        this.level = (TextView) bindings[2];
        this.level.setTag(null);
        this.name = (TextView) bindings[5];
        this.parentLine = (RelativeLayout) bindings[0];
        this.parentLine.setTag(null);
        this.price = (TextView) bindings[6];
        this.progressBar = (ProgressBar) bindings[12];
        this.rlBottom = (RelativeLayout) bindings[9];
        this.rlCarsInfo = (RelativeLayout) bindings[4];
        this.rvCarModel = (RecyclerView) bindings[14];
        this.stopSaleLine = (LinearLayout) bindings[18];
        this.stopSaleText = (TextView) bindings[19];
        this.tvCarBrand = (TextView) bindings[1];
        this.tvCarBrand.setTag(null);
        this.voiceText = (TextView) bindings[13];
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
            case 7:
                setCarSeriesInfo((CarSeriesInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCarSeriesInfo(CarSeriesInfo CarSeriesInfo) {
        this.mCarSeriesInfo = CarSeriesInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(7);
        super.requestRebind();
    }

    public CarSeriesInfo getCarSeriesInfo() {
        return this.mCarSeriesInfo;
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
        CarFactoryInfo carSeriesInfoFactory = null;
        CarSeriesInfo carSeriesInfo = this.mCarSeriesInfo;
        String carSeriesInfoLevel = null;
        String carSeriesInfoFactoryName = null;
        if ((dirtyFlags & 3) != 0) {
            if (carSeriesInfo != null) {
                carSeriesInfoFactory = carSeriesInfo.factory;
                carSeriesInfoLevel = carSeriesInfo.level;
            }
            if (carSeriesInfoFactory != null) {
                carSeriesInfoFactoryName = carSeriesInfoFactory.name;
            }
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.level, carSeriesInfoLevel);
            TextViewBindingAdapter.setText(this.tvCarBrand, carSeriesInfoFactoryName);
        }
    }

    public static SearchcarResultItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SearchcarResultItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SearchcarResultItemBinding) DataBindingUtil.inflate(inflater, 2130903378, root, attachToRoot, bindingComponent);
    }

    public static SearchcarResultItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SearchcarResultItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903378, null, false), bindingComponent);
    }

    public static SearchcarResultItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SearchcarResultItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/searchcar_result_item_0".equals(view.getTag())) {
            return new SearchcarResultItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
