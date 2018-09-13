package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarFactoryInfo;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class ItemCarSeriesBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView carImage;
    public final TextView level;
    public final TextView levelNoSale;
    public final RelativeLayout llContent;
    private CarSeriesInfo mCarSeriesInfo;
    private long mDirtyFlags = -1;
    private final LinearLayout mboundView0;
    public final RelativeLayout rlContent;
    public final ImageView saleImage;
    public final TextView tvCarBrand;
    public final TextView tvName;
    public final TextView tvPrice;
    public final View vLine;
    public final TextView voice;

    static {
        sViewsWithIds.put(2131624858, 6);
        sViewsWithIds.put(2131625129, 7);
        sViewsWithIds.put(2131624410, 8);
        sViewsWithIds.put(2131624761, 9);
        sViewsWithIds.put(2131625131, 10);
        sViewsWithIds.put(2131624473, 11);
    }

    public ItemCarSeriesBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds);
        this.carImage = (AutoFrescoDraweeView) bindings[7];
        this.level = (TextView) bindings[5];
        this.level.setTag(null);
        this.levelNoSale = (TextView) bindings[4];
        this.levelNoSale.setTag(null);
        this.llContent = (RelativeLayout) bindings[8];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlContent = (RelativeLayout) bindings[6];
        this.saleImage = (ImageView) bindings[10];
        this.tvCarBrand = (TextView) bindings[1];
        this.tvCarBrand.setTag(null);
        this.tvName = (TextView) bindings[3];
        this.tvName.setTag(null);
        this.tvPrice = (TextView) bindings[9];
        this.vLine = (View) bindings[11];
        this.voice = (TextView) bindings[2];
        this.voice.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4;
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
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(7);
        super.requestRebind();
    }

    public CarSeriesInfo getCarSeriesInfo() {
        return this.mCarSeriesInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeCarSeriesInfoName((ObservableField) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeCarSeriesInfoName(ObservableField<String> observableField, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 1;
                }
                return true;
            default:
                return false;
        }
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        CarFactoryInfo carSeriesInfoFactory = null;
        ObservableField<String> carSeriesInfoName = null;
        CarSeriesInfo carSeriesInfo = this.mCarSeriesInfo;
        String carSeriesInfoLevel = null;
        int carSeriesInfoHasautovoice = 0;
        int carSeriesInfoHasautovoiceInt1ViewVISIBLEViewGONE = 0;
        String carSeriesInfoFactoryName = null;
        String carSeriesInfoNameGet = null;
        if ((7 & dirtyFlags) != 0) {
            if ((6 & dirtyFlags) != 0) {
                if (carSeriesInfo != null) {
                    carSeriesInfoFactory = carSeriesInfo.factory;
                    carSeriesInfoLevel = carSeriesInfo.level;
                    carSeriesInfoHasautovoice = carSeriesInfo.hasautovoice;
                }
                if (carSeriesInfoFactory != null) {
                    carSeriesInfoFactoryName = carSeriesInfoFactory.name;
                }
                boolean carSeriesInfoHasautovoiceInt1 = carSeriesInfoHasautovoice == 1;
                if ((6 & dirtyFlags) != 0) {
                    if (carSeriesInfoHasautovoiceInt1) {
                        dirtyFlags |= 16;
                    } else {
                        dirtyFlags |= 8;
                    }
                }
                carSeriesInfoHasautovoiceInt1ViewVISIBLEViewGONE = carSeriesInfoHasautovoiceInt1 ? 0 : 8;
            }
            if (carSeriesInfo != null) {
                carSeriesInfoName = carSeriesInfo.name;
            }
            updateRegistration(0, carSeriesInfoName);
            if (carSeriesInfoName != null) {
                carSeriesInfoNameGet = (String) carSeriesInfoName.get();
            }
        }
        if ((6 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.level, carSeriesInfoLevel);
            TextViewBindingAdapter.setText(this.levelNoSale, carSeriesInfoLevel);
            TextViewBindingAdapter.setText(this.tvCarBrand, carSeriesInfoFactoryName);
            this.voice.setVisibility(carSeriesInfoHasautovoiceInt1ViewVISIBLEViewGONE);
        }
        if ((7 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvName, carSeriesInfoNameGet);
        }
    }

    public static ItemCarSeriesBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCarSeriesBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemCarSeriesBinding) DataBindingUtil.inflate(inflater, 2130903267, root, attachToRoot, bindingComponent);
    }

    public static ItemCarSeriesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCarSeriesBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903267, null, false), bindingComponent);
    }

    public static ItemCarSeriesBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCarSeriesBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_car_series_0".equals(view.getTag())) {
            return new ItemCarSeriesBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
