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
import android.widget.TextView;
import com.feng.car.entity.car.CarBrandInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class PhotoTextListItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView brandImage;
    private CarBrandInfo mCarBrandInfo;
    private long mDirtyFlags = -1;
    private final LinearLayout mboundView0;
    private final TextView mboundView1;

    static {
        sViewsWithIds.put(2131625356, 2);
    }

    public PhotoTextListItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.brandImage = (AutoFrescoDraweeView) bindings[2];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (TextView) bindings[1];
        this.mboundView1.setTag(null);
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
            case 3:
                setCarBrandInfo((CarBrandInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCarBrandInfo(CarBrandInfo CarBrandInfo) {
        this.mCarBrandInfo = CarBrandInfo;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(3);
        super.requestRebind();
    }

    public CarBrandInfo getCarBrandInfo() {
        return this.mCarBrandInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeCarBrandInfoName((ObservableField) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeCarBrandInfoName(ObservableField<String> observableField, int fieldId) {
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
        CarBrandInfo carBrandInfo = this.mCarBrandInfo;
        String carBrandInfoNameGet = null;
        ObservableField<String> carBrandInfoName = null;
        if ((dirtyFlags & 7) != 0) {
            if (carBrandInfo != null) {
                carBrandInfoName = carBrandInfo.name;
            }
            updateRegistration(0, carBrandInfoName);
            if (carBrandInfoName != null) {
                carBrandInfoNameGet = (String) carBrandInfoName.get();
            }
        }
        if ((dirtyFlags & 7) != 0) {
            TextViewBindingAdapter.setText(this.mboundView1, carBrandInfoNameGet);
        }
    }

    public static PhotoTextListItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static PhotoTextListItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (PhotoTextListItemBinding) DataBindingUtil.inflate(inflater, 2130903339, root, attachToRoot, bindingComponent);
    }

    public static PhotoTextListItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static PhotoTextListItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903339, null, false), bindingComponent);
    }

    public static PhotoTextListItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static PhotoTextListItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/photo_text_list_item_0".equals(view.getTag())) {
            return new PhotoTextListItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
