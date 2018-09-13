package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.databinding.adapters.ViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feng.car.entity.car.DealerInfo;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.R;

public class ItemVehicleAgencyBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View dividerLine;
    public final View dividerLineLast;
    public final ImageView ivHotShopSiginal;
    public final LinearLayout llShopItemCallContainer;
    public final LinearLayout llShopItemNavContainer;
    private DealerInfo mDealerInfo;
    private long mDirtyFlags = -1;
    private final LinearLayout mboundView0;
    public final LinearLayout rlVehicleShopDetail;
    public final TextView tvShopItemCall;
    public final TextView tvShopItemNav;
    public final TextView tvVehicleShopAddress;
    public final TextView tvVehicleShopDistance;
    public final TextView tvVehicleShopName;

    static {
        sViewsWithIds.put(2131625156, 6);
        sViewsWithIds.put(2131625160, 7);
        sViewsWithIds.put(2131625163, 8);
        sViewsWithIds.put(2131625164, 9);
        sViewsWithIds.put(2131625165, 10);
        sViewsWithIds.put(2131625166, 11);
    }

    public ItemVehicleAgencyBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds);
        this.dividerLine = (View) bindings[10];
        this.dividerLineLast = (View) bindings[11];
        this.ivHotShopSiginal = (ImageView) bindings[2];
        this.ivHotShopSiginal.setTag(null);
        this.llShopItemCallContainer = (LinearLayout) bindings[4];
        this.llShopItemCallContainer.setTag(null);
        this.llShopItemNavContainer = (LinearLayout) bindings[8];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlVehicleShopDetail = (LinearLayout) bindings[6];
        this.tvShopItemCall = (TextView) bindings[5];
        this.tvShopItemCall.setTag(null);
        this.tvShopItemNav = (TextView) bindings[9];
        this.tvVehicleShopAddress = (TextView) bindings[7];
        this.tvVehicleShopDistance = (TextView) bindings[3];
        this.tvVehicleShopDistance.setTag(null);
        this.tvVehicleShopName = (TextView) bindings[1];
        this.tvVehicleShopName.setTag(null);
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
            case 19:
                setDealerInfo((DealerInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setDealerInfo(DealerInfo DealerInfo) {
        this.mDealerInfo = DealerInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(19);
        super.requestRebind();
    }

    public DealerInfo getDealerInfo() {
        return this.mDealerInfo;
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
        int dealerInfoOntop = 0;
        DealerInfo dealerInfo = this.mDealerInfo;
        int dealerInfoOntopInt1ViewVISIBLEViewGONE = 0;
        String dealerInfoLocalDistanceTips = null;
        Drawable stringUtilIsEmptyDealerInfoDealermobileLlShopItemCallContainerAndroidDrawableColorSelectorFfffffPressedFfffffLlShopItemCallContainerAndroidDrawableColorSelectorFfffffPressedF2f2f2 = null;
        String dealerInfoDealermobile = null;
        Drawable stringUtilIsEmptyDealerInfoDealermobileTvShopItemCallAndroidDrawableIconShopItemTelephoneDisabledTvShopItemCallAndroidDrawableIconShopItemTelephone = null;
        int stringUtilIsEmptyDealerInfoDealermobileTvShopItemCallAndroidColorColor38000000TvShopItemCallAndroidColorColor54000000 = 0;
        String dealerInfoGetShortname = null;
        int stringUtilIsEmptyDealerInfoLocalDistanceTipsViewGONEViewVISIBLE = 0;
        if ((3 & dirtyFlags) != 0) {
            if (dealerInfo != null) {
                dealerInfoOntop = dealerInfo.ontop;
                dealerInfoLocalDistanceTips = dealerInfo.localDistanceTips;
                dealerInfoDealermobile = dealerInfo.dealermobile;
                dealerInfoGetShortname = dealerInfo.getShortname();
            }
            boolean dealerInfoOntopInt1 = dealerInfoOntop == 1;
            boolean stringUtilIsEmptyDealerInfoLocalDistanceTips = StringUtil.isEmpty(dealerInfoLocalDistanceTips);
            boolean stringUtilIsEmptyDealerInfoDealermobile = StringUtil.isEmpty(dealerInfoDealermobile);
            if ((3 & dirtyFlags) != 0) {
                if (dealerInfoOntopInt1) {
                    dirtyFlags |= 8;
                } else {
                    dirtyFlags |= 4;
                }
            }
            if ((3 & dirtyFlags) != 0) {
                if (stringUtilIsEmptyDealerInfoLocalDistanceTips) {
                    dirtyFlags |= 2048;
                } else {
                    dirtyFlags |= 1024;
                }
            }
            if ((3 & dirtyFlags) != 0) {
                if (stringUtilIsEmptyDealerInfoDealermobile) {
                    dirtyFlags = ((dirtyFlags | 32) | 128) | 512;
                } else {
                    dirtyFlags = ((dirtyFlags | 16) | 64) | 256;
                }
            }
            dealerInfoOntopInt1ViewVISIBLEViewGONE = dealerInfoOntopInt1 ? 0 : 8;
            stringUtilIsEmptyDealerInfoLocalDistanceTipsViewGONEViewVISIBLE = stringUtilIsEmptyDealerInfoLocalDistanceTips ? 8 : 0;
            stringUtilIsEmptyDealerInfoDealermobileLlShopItemCallContainerAndroidDrawableColorSelectorFfffffPressedFfffffLlShopItemCallContainerAndroidDrawableColorSelectorFfffffPressedF2f2f2 = stringUtilIsEmptyDealerInfoDealermobile ? getDrawableFromResource(this.llShopItemCallContainer, 2130837809) : getDrawableFromResource(this.llShopItemCallContainer, 2130837808);
            stringUtilIsEmptyDealerInfoDealermobileTvShopItemCallAndroidDrawableIconShopItemTelephoneDisabledTvShopItemCallAndroidDrawableIconShopItemTelephone = stringUtilIsEmptyDealerInfoDealermobile ? getDrawableFromResource(this.tvShopItemCall, 2130838235) : getDrawableFromResource(this.tvShopItemCall, 2130838234);
            stringUtilIsEmptyDealerInfoDealermobileTvShopItemCallAndroidColorColor38000000TvShopItemCallAndroidColorColor54000000 = stringUtilIsEmptyDealerInfoDealermobile ? getColorFromResource(this.tvShopItemCall, 2131558448) : getColorFromResource(this.tvShopItemCall, R.color.color_54_000000);
        }
        if ((3 & dirtyFlags) != 0) {
            this.ivHotShopSiginal.setVisibility(dealerInfoOntopInt1ViewVISIBLEViewGONE);
            ViewBindingAdapter.setBackground(this.llShopItemCallContainer, stringUtilIsEmptyDealerInfoDealermobileLlShopItemCallContainerAndroidDrawableColorSelectorFfffffPressedFfffffLlShopItemCallContainerAndroidDrawableColorSelectorFfffffPressedF2f2f2);
            TextViewBindingAdapter.setDrawableLeft(this.tvShopItemCall, stringUtilIsEmptyDealerInfoDealermobileTvShopItemCallAndroidDrawableIconShopItemTelephoneDisabledTvShopItemCallAndroidDrawableIconShopItemTelephone);
            TextViewBindingAdapter.setDrawableStart(this.tvShopItemCall, stringUtilIsEmptyDealerInfoDealermobileTvShopItemCallAndroidDrawableIconShopItemTelephoneDisabledTvShopItemCallAndroidDrawableIconShopItemTelephone);
            this.tvShopItemCall.setTextColor(stringUtilIsEmptyDealerInfoDealermobileTvShopItemCallAndroidColorColor38000000TvShopItemCallAndroidColorColor54000000);
            TextViewBindingAdapter.setText(this.tvVehicleShopDistance, dealerInfoLocalDistanceTips);
            this.tvVehicleShopDistance.setVisibility(stringUtilIsEmptyDealerInfoLocalDistanceTipsViewGONEViewVISIBLE);
            TextViewBindingAdapter.setText(this.tvVehicleShopName, dealerInfoGetShortname);
        }
    }

    public static ItemVehicleAgencyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemVehicleAgencyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemVehicleAgencyBinding) DataBindingUtil.inflate(inflater, 2130903282, root, attachToRoot, bindingComponent);
    }

    public static ItemVehicleAgencyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemVehicleAgencyBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903282, null, false), bindingComponent);
    }

    public static ItemVehicleAgencyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemVehicleAgencyBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_vehicle_agency_0".equals(view.getTag())) {
            return new ItemVehicleAgencyBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
