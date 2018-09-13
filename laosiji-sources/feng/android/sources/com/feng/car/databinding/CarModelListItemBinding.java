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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarModelInfo;

public class CarModelListItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout defaultLine;
    public final ImageView ivCarState;
    public final ImageView ivSelect;
    private CarModelInfo mCarModelInfo;
    private long mDirtyFlags = -1;
    public final RelativeLayout rlContent;
    public final TextView tvCarEngine;
    public final TextView tvContent;
    public final View vLine;

    static {
        sViewsWithIds.put(2131624858, 2);
        sViewsWithIds.put(2131624859, 3);
        sViewsWithIds.put(2131624861, 4);
        sViewsWithIds.put(2131624860, 5);
        sViewsWithIds.put(2131624473, 6);
    }

    public CarModelListItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.defaultLine = (LinearLayout) bindings[0];
        this.defaultLine.setTag(null);
        this.ivCarState = (ImageView) bindings[4];
        this.ivSelect = (ImageView) bindings[5];
        this.rlContent = (RelativeLayout) bindings[2];
        this.tvCarEngine = (TextView) bindings[1];
        this.tvCarEngine.setTag(null);
        this.tvContent = (TextView) bindings[3];
        this.vLine = (View) bindings[6];
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
            case 6:
                setCarModelInfo((CarModelInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCarModelInfo(CarModelInfo CarModelInfo) {
        this.mCarModelInfo = CarModelInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(6);
        super.requestRebind();
    }

    public CarModelInfo getCarModelInfo() {
        return this.mCarModelInfo;
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
        int carModelInfoPosfirstflag = 0;
        CarModelInfo carModelInfo = this.mCarModelInfo;
        String carModelInfoEngine = null;
        int carModelInfoPosfirstflagInt1ViewVISIBLEViewGONE = 0;
        if ((3 & dirtyFlags) != 0) {
            if (carModelInfo != null) {
                carModelInfoPosfirstflag = carModelInfo.posfirstflag;
                carModelInfoEngine = carModelInfo.engine;
            }
            boolean carModelInfoPosfirstflagInt1 = carModelInfoPosfirstflag == 1;
            if ((3 & dirtyFlags) != 0) {
                if (carModelInfoPosfirstflagInt1) {
                    dirtyFlags |= 8;
                } else {
                    dirtyFlags |= 4;
                }
            }
            carModelInfoPosfirstflagInt1ViewVISIBLEViewGONE = carModelInfoPosfirstflagInt1 ? 0 : 8;
        }
        if ((3 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvCarEngine, carModelInfoEngine);
            this.tvCarEngine.setVisibility(carModelInfoPosfirstflagInt1ViewVISIBLEViewGONE);
        }
    }

    public static CarModelListItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CarModelListItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CarModelListItemBinding) DataBindingUtil.inflate(inflater, 2130903172, root, attachToRoot, bindingComponent);
    }

    public static CarModelListItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CarModelListItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903172, null, false), bindingComponent);
    }

    public static CarModelListItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CarModelListItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/car_model_list_item_0".equals(view.getTag())) {
            return new CarModelListItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
