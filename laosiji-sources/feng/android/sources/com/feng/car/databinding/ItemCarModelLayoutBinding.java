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
import com.feng.car.entity.car.RecommendCarxInfo;
import com.feng.car.entity.choosecar.ChooseCarInfo;
import com.feng.car.entity.sns.SnsInfo;

public class ItemCarModelLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View bottomMenuDivider1;
    public final View bottomMenuDivider2;
    public final TextView itemVehicleName;
    public final ImageView ivCarModelState;
    public final ImageView ivVehiclesRecommendReason;
    public final View line;
    public final LinearLayout llMenu;
    public final LinearLayout llParent;
    public final LinearLayout llVehiclesCompare;
    public final LinearLayout llVehiclesConfig;
    public final LinearLayout llVehiclesPicture;
    private CarModelInfo mCarInfo;
    private String mCarSeriesName;
    private long mDirtyFlags = -1;
    private RecommendCarxInfo mRecommendInfo;
    private SnsInfo mSnsInfo;
    private final View mboundView7;
    public final TextView priceText;
    public final RelativeLayout rlVehicleNameTitle;
    public final TextView tvCarMiddlePrice;
    public final TextView tvCarPrice;
    public final TextView tvVehiclesCompare;
    public final TextView tvVehiclesConfig;
    public final TextView tvVehiclesEngine;
    public final TextView tvVehiclesPicture;
    public final View vLineBottom;

    static {
        sViewsWithIds.put(2131625112, 8);
        sViewsWithIds.put(2131625115, 9);
        sViewsWithIds.put(2131624374, 10);
        sViewsWithIds.put(2131624409, 11);
        sViewsWithIds.put(2131625118, 12);
        sViewsWithIds.put(2131625119, 13);
        sViewsWithIds.put(2131625120, 14);
        sViewsWithIds.put(2131625121, 15);
        sViewsWithIds.put(2131625122, 16);
        sViewsWithIds.put(2131625123, 17);
        sViewsWithIds.put(2131625124, 18);
        sViewsWithIds.put(2131625125, 19);
        sViewsWithIds.put(2131624445, 20);
    }

    public ItemCarModelLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 21, sIncludes, sViewsWithIds);
        this.bottomMenuDivider1 = (View) bindings[14];
        this.bottomMenuDivider2 = (View) bindings[17];
        this.itemVehicleName = (TextView) bindings[2];
        this.itemVehicleName.setTag(null);
        this.ivCarModelState = (ImageView) bindings[9];
        this.ivVehiclesRecommendReason = (ImageView) bindings[3];
        this.ivVehiclesRecommendReason.setTag(null);
        this.line = (View) bindings[10];
        this.llMenu = (LinearLayout) bindings[11];
        this.llParent = (LinearLayout) bindings[0];
        this.llParent.setTag(null);
        this.llVehiclesCompare = (LinearLayout) bindings[18];
        this.llVehiclesConfig = (LinearLayout) bindings[15];
        this.llVehiclesPicture = (LinearLayout) bindings[12];
        this.mboundView7 = (View) bindings[7];
        this.mboundView7.setTag(null);
        this.priceText = (TextView) bindings[5];
        this.priceText.setTag(null);
        this.rlVehicleNameTitle = (RelativeLayout) bindings[8];
        this.tvCarMiddlePrice = (TextView) bindings[4];
        this.tvCarMiddlePrice.setTag(null);
        this.tvCarPrice = (TextView) bindings[6];
        this.tvCarPrice.setTag(null);
        this.tvVehiclesCompare = (TextView) bindings[19];
        this.tvVehiclesConfig = (TextView) bindings[16];
        this.tvVehiclesEngine = (TextView) bindings[1];
        this.tvVehiclesEngine.setTag(null);
        this.tvVehiclesPicture = (TextView) bindings[13];
        this.vLineBottom = (View) bindings[20];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16;
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
            case 5:
                setCarInfo((CarModelInfo) variable);
                return true;
            case 8:
                setCarSeriesName((String) variable);
                return true;
            case 49:
                setRecommendInfo((RecommendCarxInfo) variable);
                return true;
            case 62:
                setSnsInfo((SnsInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCarInfo(CarModelInfo CarInfo) {
        this.mCarInfo = CarInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(5);
        super.requestRebind();
    }

    public CarModelInfo getCarInfo() {
        return this.mCarInfo;
    }

    public void setRecommendInfo(RecommendCarxInfo RecommendInfo) {
        this.mRecommendInfo = RecommendInfo;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(49);
        super.requestRebind();
    }

    public RecommendCarxInfo getRecommendInfo() {
        return this.mRecommendInfo;
    }

    public void setCarSeriesName(String CarSeriesName) {
        this.mCarSeriesName = CarSeriesName;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(8);
        super.requestRebind();
    }

    public String getCarSeriesName() {
        return this.mCarSeriesName;
    }

    public void setSnsInfo(SnsInfo SnsInfo) {
        this.mSnsInfo = SnsInfo;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(62);
        super.requestRebind();
    }

    public SnsInfo getSnsInfo() {
        return this.mSnsInfo;
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
        String carInfoName = null;
        CarModelInfo carInfo = this.mCarInfo;
        RecommendCarxInfo recommendInfo = this.mRecommendInfo;
        String carInfoGetGuidePrice = null;
        int recommendInfoIsrecommend = 0;
        String carSeriesNameJavaLangStringCarInfoName = null;
        int snsInfoDiscussinfoId = 0;
        String carSeriesName = this.mCarSeriesName;
        int carInfoPosfirstflag = 0;
        ChooseCarInfo snsInfoDiscussinfo = null;
        int carInfoPosfirstflagInt1ViewVISIBLEViewGONE = 0;
        int carInfoPoslastflag = 0;
        String carInfoGetCarPriceText = null;
        SnsInfo snsInfo = this.mSnsInfo;
        String carInfoGetAvgPrice = null;
        int snsInfoDiscussinfoIdInt0ViewGONEViewVISIBLE = 0;
        String carInfoEngine = null;
        boolean recommendInfoIsrecommendInt1 = false;
        int recommendInfoIsrecommendInt1SnsInfoDiscussinfoIdInt0ViewGONEViewVISIBLEViewGONE = 0;
        int carInfoPoslastflagInt1ViewGONEViewVISIBLE = 0;
        if ((21 & dirtyFlags) != 0) {
            if (carInfo != null) {
                carInfoName = carInfo.name;
            }
            carSeriesNameJavaLangStringCarInfoName = (carSeriesName + " ") + carInfoName;
            if ((17 & dirtyFlags) != 0) {
                if (carInfo != null) {
                    carInfoGetGuidePrice = carInfo.getGuidePrice();
                    carInfoPosfirstflag = carInfo.posfirstflag;
                    carInfoPoslastflag = carInfo.poslastflag;
                    carInfoGetCarPriceText = carInfo.getCarPriceText();
                    carInfoGetAvgPrice = carInfo.getAvgPrice();
                    carInfoEngine = carInfo.engine;
                }
                boolean carInfoPosfirstflagInt1 = carInfoPosfirstflag == 1;
                boolean carInfoPoslastflagInt1 = carInfoPoslastflag == 1;
                if ((17 & dirtyFlags) != 0) {
                    if (carInfoPosfirstflagInt1) {
                        dirtyFlags |= 64;
                    } else {
                        dirtyFlags |= 32;
                    }
                }
                if ((17 & dirtyFlags) != 0) {
                    if (carInfoPoslastflagInt1) {
                        dirtyFlags |= 4096;
                    } else {
                        dirtyFlags |= 2048;
                    }
                }
                carInfoPosfirstflagInt1ViewVISIBLEViewGONE = carInfoPosfirstflagInt1 ? 0 : 8;
                carInfoPoslastflagInt1ViewGONEViewVISIBLE = carInfoPoslastflagInt1 ? 8 : 0;
            }
        }
        if ((26 & dirtyFlags) != 0) {
            if (recommendInfo != null) {
                recommendInfoIsrecommend = recommendInfo.isrecommend;
            }
            recommendInfoIsrecommendInt1 = recommendInfoIsrecommend == 1;
            if ((26 & dirtyFlags) != 0) {
                if (recommendInfoIsrecommendInt1) {
                    dirtyFlags |= 1024;
                } else {
                    dirtyFlags |= 512;
                }
            }
        }
        if ((1024 & dirtyFlags) != 0) {
            if (snsInfo != null) {
                snsInfoDiscussinfo = snsInfo.discussinfo;
            }
            if (snsInfoDiscussinfo != null) {
                snsInfoDiscussinfoId = snsInfoDiscussinfo.id;
            }
            boolean snsInfoDiscussinfoIdInt0 = snsInfoDiscussinfoId == 0;
            if ((1024 & dirtyFlags) != 0) {
                if (snsInfoDiscussinfoIdInt0) {
                    dirtyFlags |= 256;
                } else {
                    dirtyFlags |= 128;
                }
            }
            snsInfoDiscussinfoIdInt0ViewGONEViewVISIBLE = snsInfoDiscussinfoIdInt0 ? 8 : 0;
        }
        if ((26 & dirtyFlags) != 0) {
            recommendInfoIsrecommendInt1SnsInfoDiscussinfoIdInt0ViewGONEViewVISIBLEViewGONE = recommendInfoIsrecommendInt1 ? snsInfoDiscussinfoIdInt0ViewGONEViewVISIBLE : 8;
        }
        if ((21 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.itemVehicleName, carSeriesNameJavaLangStringCarInfoName);
        }
        if ((26 & dirtyFlags) != 0) {
            this.ivVehiclesRecommendReason.setVisibility(recommendInfoIsrecommendInt1SnsInfoDiscussinfoIdInt0ViewGONEViewVISIBLEViewGONE);
        }
        if ((17 & dirtyFlags) != 0) {
            this.mboundView7.setVisibility(carInfoPoslastflagInt1ViewGONEViewVISIBLE);
            TextViewBindingAdapter.setText(this.priceText, carInfoGetCarPriceText);
            TextViewBindingAdapter.setText(this.tvCarMiddlePrice, carInfoGetAvgPrice);
            TextViewBindingAdapter.setText(this.tvCarPrice, carInfoGetGuidePrice);
            TextViewBindingAdapter.setText(this.tvVehiclesEngine, carInfoEngine);
            this.tvVehiclesEngine.setVisibility(carInfoPosfirstflagInt1ViewVISIBLEViewGONE);
        }
    }

    public static ItemCarModelLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCarModelLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemCarModelLayoutBinding) DataBindingUtil.inflate(inflater, 2130903265, root, attachToRoot, bindingComponent);
    }

    public static ItemCarModelLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCarModelLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903265, null, false), bindingComponent);
    }

    public static ItemCarModelLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCarModelLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_car_model_layout_0".equals(view.getTag())) {
            return new ItemCarModelLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
