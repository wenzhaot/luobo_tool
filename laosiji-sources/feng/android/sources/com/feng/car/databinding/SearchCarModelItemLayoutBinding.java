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

public class SearchCarModelItemLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private CarModelInfo mCarmodel;
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;
    public final TextView tvCarxName;
    public final TextView tvPrice;
    public final TextView tvText;

    static {
        sViewsWithIds.put(2131624312, 2);
        sViewsWithIds.put(2131624761, 3);
    }

    public SearchCarModelItemLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvCarxName = (TextView) bindings[1];
        this.tvCarxName.setTag(null);
        this.tvPrice = (TextView) bindings[3];
        this.tvText = (TextView) bindings[2];
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
            case 9:
                setCarmodel((CarModelInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCarmodel(CarModelInfo Carmodel) {
        this.mCarmodel = Carmodel;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(9);
        super.requestRebind();
    }

    public CarModelInfo getCarmodel() {
        return this.mCarmodel;
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
        CarModelInfo carmodel = this.mCarmodel;
        String carmodelName = null;
        if (!((dirtyFlags & 3) == 0 || carmodel == null)) {
            carmodelName = carmodel.name;
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.tvCarxName, carmodelName);
        }
    }

    public static SearchCarModelItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SearchCarModelItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SearchCarModelItemLayoutBinding) DataBindingUtil.inflate(inflater, 2130903371, root, attachToRoot, bindingComponent);
    }

    public static SearchCarModelItemLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SearchCarModelItemLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903371, null, false), bindingComponent);
    }

    public static SearchCarModelItemLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SearchCarModelItemLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/search_car_model_item_layout_0".equals(view.getTag())) {
            return new SearchCarModelItemLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
