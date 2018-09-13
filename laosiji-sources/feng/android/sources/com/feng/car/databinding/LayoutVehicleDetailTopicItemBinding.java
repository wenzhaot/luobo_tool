package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.view.VoiceBoxView;

public class LayoutVehicleDetailTopicItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private CarSeriesInfo mInfo;
    private final LinearLayout mboundView0;
    public final VoiceBoxView vbvVehicleBanner;

    static {
        sViewsWithIds.put(2131625244, 1);
    }

    public LayoutVehicleDetailTopicItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds);
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.vbvVehicleBanner = (VoiceBoxView) bindings[1];
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
            case 28:
                setInfo((CarSeriesInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(CarSeriesInfo Info) {
        this.mInfo = Info;
    }

    public CarSeriesInfo getInfo() {
        return this.mInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    protected void executeBindings() {
        synchronized (this) {
            long dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
    }

    public static LayoutVehicleDetailTopicItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static LayoutVehicleDetailTopicItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (LayoutVehicleDetailTopicItemBinding) DataBindingUtil.inflate(inflater, 2130903299, root, attachToRoot, bindingComponent);
    }

    public static LayoutVehicleDetailTopicItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static LayoutVehicleDetailTopicItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903299, null, false), bindingComponent);
    }

    public static LayoutVehicleDetailTopicItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static LayoutVehicleDetailTopicItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/layout_vehicle_detail_topic_item_0".equals(view.getTag())) {
            return new LayoutVehicleDetailTopicItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
