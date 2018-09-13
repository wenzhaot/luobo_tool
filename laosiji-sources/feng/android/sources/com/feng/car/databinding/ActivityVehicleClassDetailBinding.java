package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarSeriesInfo;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ActivityVehicleClassDetailBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View bubbleCenterDivider;
    public final LinearLayout llBubbleCenterDividerContainer;
    public final LinearLayout llBubbleLeftMapContainer;
    public final LinearLayout llBubbleRightCityContainer;
    public final LinearLayout llNoDealersWithCity;
    public final LinearLayout llSelectCarModel;
    public final LRecyclerView lrvCarShopAgency;
    private long mDirtyFlags = -1;
    private CarSeriesInfo mInfo;
    private final RelativeLayout mboundView0;
    public final RelativeLayout rlBubbleMapContainer;
    public final TabLayout tabCarModelYear;
    public final TextView tvAgencyBubbleLocationCity;
    public final TextView tvCarSeries;
    public final View viewShade;
    public final ViewPager vpCarModel;

    static {
        sViewsWithIds.put(2131624719, 1);
        sViewsWithIds.put(2131624720, 2);
        sViewsWithIds.put(2131624721, 3);
        sViewsWithIds.put(2131624722, 4);
        sViewsWithIds.put(2131624723, 5);
        sViewsWithIds.put(2131624724, 6);
        sViewsWithIds.put(2131624725, 7);
        sViewsWithIds.put(2131624433, 8);
        sViewsWithIds.put(2131624304, 9);
        sViewsWithIds.put(2131624305, 10);
        sViewsWithIds.put(2131624306, 11);
        sViewsWithIds.put(2131624307, 12);
        sViewsWithIds.put(2131624308, 13);
    }

    public ActivityVehicleClassDetailBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds);
        this.bubbleCenterDivider = (View) bindings[6];
        this.llBubbleCenterDividerContainer = (LinearLayout) bindings[5];
        this.llBubbleLeftMapContainer = (LinearLayout) bindings[4];
        this.llBubbleRightCityContainer = (LinearLayout) bindings[7];
        this.llNoDealersWithCity = (LinearLayout) bindings[1];
        this.llSelectCarModel = (LinearLayout) bindings[10];
        this.lrvCarShopAgency = (LRecyclerView) bindings[2];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlBubbleMapContainer = (RelativeLayout) bindings[3];
        this.tabCarModelYear = (TabLayout) bindings[12];
        this.tvAgencyBubbleLocationCity = (TextView) bindings[8];
        this.tvCarSeries = (TextView) bindings[11];
        this.viewShade = (View) bindings[9];
        this.vpCarModel = (ViewPager) bindings[13];
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

    public static ActivityVehicleClassDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityVehicleClassDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityVehicleClassDetailBinding) DataBindingUtil.inflate(inflater, 2130903147, root, attachToRoot, bindingComponent);
    }

    public static ActivityVehicleClassDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityVehicleClassDetailBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903147, null, false), bindingComponent);
    }

    public static ActivityVehicleClassDetailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityVehicleClassDetailBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_vehicle_class_detail_0".equals(view.getTag())) {
            return new ActivityVehicleClassDetailBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
