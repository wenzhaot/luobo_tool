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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class ChooseCarRelevantCarItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView fdvRelevantCar;
    public final LinearLayout llRelevantCarContainer;
    private CarSeriesInfo mCarSeriesInfo;
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;
    public final TextView tvRelevantCarName;

    static {
        sViewsWithIds.put(2131624923, 2);
        sViewsWithIds.put(2131624924, 3);
    }

    public ChooseCarRelevantCarItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.fdvRelevantCar = (AutoFrescoDraweeView) bindings[3];
        this.llRelevantCarContainer = (LinearLayout) bindings[2];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvRelevantCarName = (TextView) bindings[1];
        this.tvRelevantCarName.setTag(null);
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
        ObservableField<String> carSeriesInfoName = null;
        CarSeriesInfo carSeriesInfo = this.mCarSeriesInfo;
        String carSeriesInfoNameGet = null;
        if ((dirtyFlags & 7) != 0) {
            if (carSeriesInfo != null) {
                carSeriesInfoName = carSeriesInfo.name;
            }
            updateRegistration(0, carSeriesInfoName);
            if (carSeriesInfoName != null) {
                carSeriesInfoNameGet = (String) carSeriesInfoName.get();
            }
        }
        if ((dirtyFlags & 7) != 0) {
            TextViewBindingAdapter.setText(this.tvRelevantCarName, carSeriesInfoNameGet);
        }
    }

    public static ChooseCarRelevantCarItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ChooseCarRelevantCarItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ChooseCarRelevantCarItemBinding) DataBindingUtil.inflate(inflater, 2130903186, root, attachToRoot, bindingComponent);
    }

    public static ChooseCarRelevantCarItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ChooseCarRelevantCarItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903186, null, false), bindingComponent);
    }

    public static ChooseCarRelevantCarItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ChooseCarRelevantCarItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/choose_car_relevant_car_item_0".equals(view.getTag())) {
            return new ChooseCarRelevantCarItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
