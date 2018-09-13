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
import android.widget.TextView;
import com.feng.car.entity.searchcar.SearchCarBean;

public class SearchCarsConditionLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private SearchCarBean mCarBean;
    private long mDirtyFlags = -1;
    public final TextView tvConditionName;

    public SearchCarsConditionLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        this.tvConditionName = (TextView) mapBindings(bindingComponent, root, 1, sIncludes, sViewsWithIds)[0];
        this.tvConditionName.setTag(null);
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
            case 2:
                setCarBean((SearchCarBean) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCarBean(SearchCarBean CarBean) {
        this.mCarBean = CarBean;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(2);
        super.requestRebind();
    }

    public SearchCarBean getCarBean() {
        return this.mCarBean;
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
        SearchCarBean carBean = this.mCarBean;
        String carBeanGetConditionName = null;
        if (!((dirtyFlags & 3) == 0 || carBean == null)) {
            carBeanGetConditionName = carBean.getConditionName();
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.tvConditionName, carBeanGetConditionName);
        }
    }

    public static SearchCarsConditionLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SearchCarsConditionLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SearchCarsConditionLayoutBinding) DataBindingUtil.inflate(inflater, 2130903372, root, attachToRoot, bindingComponent);
    }

    public static SearchCarsConditionLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SearchCarsConditionLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903372, null, false), bindingComponent);
    }

    public static SearchCarsConditionLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SearchCarsConditionLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/search_cars_condition_layout_0".equals(view.getTag())) {
            return new SearchCarsConditionLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
