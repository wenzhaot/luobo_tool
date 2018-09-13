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

public class ItemVehiclesBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View bottomMenuDivider1;
    public final View bottomMenuDivider2;
    public final TextView itemVehicleName;
    public final ImageView ivCarModelState;
    public final ImageView ivVehiclesRecommendReason;
    public final View line;
    public final LinearLayout llMenu;
    public final LinearLayout llNoDealersEmptyContainer;
    public final LinearLayout llVehiclesCompare;
    public final LinearLayout llVehiclesConfig;
    public final LinearLayout llVehiclesPicture;
    private CarModelInfo mCarInfo;
    private String mCarSeriesName;
    private long mDirtyFlags = -1;
    private RecommendCarxInfo mRecommendInfo;
    private SnsInfo mSnsInfo;
    private final RelativeLayout mboundView0;
    private final TextView mboundView3;
    private final TextView mboundView4;
    public final RelativeLayout rlCarMiddlePrice;
    public final RelativeLayout rlVehicleNameTitle;
    public final TextView tvCarMiddlePrice;
    public final TextView tvCount;
    public final TextView tvMiddleText;
    public final TextView tvVehiclesCompare;
    public final TextView tvVehiclesConfig;
    public final TextView tvVehiclesPicture;
    public final View vLine;
    public final View vLineBottom;

    static {
        sViewsWithIds.put(2131625112, 5);
        sViewsWithIds.put(2131625115, 6);
        sViewsWithIds.put(2131625170, 7);
        sViewsWithIds.put(2131625171, 8);
        sViewsWithIds.put(2131625116, 9);
        sViewsWithIds.put(2131624473, 10);
        sViewsWithIds.put(2131624948, 11);
        sViewsWithIds.put(2131624374, 12);
        sViewsWithIds.put(2131624409, 13);
        sViewsWithIds.put(2131625118, 14);
        sViewsWithIds.put(2131625119, 15);
        sViewsWithIds.put(2131625120, 16);
        sViewsWithIds.put(2131625121, 17);
        sViewsWithIds.put(2131625122, 18);
        sViewsWithIds.put(2131625123, 19);
        sViewsWithIds.put(2131625124, 20);
        sViewsWithIds.put(2131625125, 21);
        sViewsWithIds.put(2131624445, 22);
        sViewsWithIds.put(2131625172, 23);
    }

    public ItemVehiclesBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds);
        this.bottomMenuDivider1 = (View) bindings[16];
        this.bottomMenuDivider2 = (View) bindings[19];
        this.itemVehicleName = (TextView) bindings[1];
        this.itemVehicleName.setTag(null);
        this.ivCarModelState = (ImageView) bindings[6];
        this.ivVehiclesRecommendReason = (ImageView) bindings[2];
        this.ivVehiclesRecommendReason.setTag(null);
        this.line = (View) bindings[12];
        this.llMenu = (LinearLayout) bindings[13];
        this.llNoDealersEmptyContainer = (LinearLayout) bindings[23];
        this.llVehiclesCompare = (LinearLayout) bindings[20];
        this.llVehiclesConfig = (LinearLayout) bindings[17];
        this.llVehiclesPicture = (LinearLayout) bindings[14];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView3 = (TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView4 = (TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.rlCarMiddlePrice = (RelativeLayout) bindings[7];
        this.rlVehicleNameTitle = (RelativeLayout) bindings[5];
        this.tvCarMiddlePrice = (TextView) bindings[9];
        this.tvCount = (TextView) bindings[11];
        this.tvMiddleText = (TextView) bindings[8];
        this.tvVehiclesCompare = (TextView) bindings[21];
        this.tvVehiclesConfig = (TextView) bindings[18];
        this.tvVehiclesPicture = (TextView) bindings[15];
        this.vLine = (View) bindings[10];
        this.vLineBottom = (View) bindings[22];
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
        ChooseCarInfo snsInfoDiscussinfo = null;
        SnsInfo snsInfo = this.mSnsInfo;
        String carInfoGetCarPriceText2 = null;
        int snsInfoDiscussinfoIdInt0ViewGONEViewVISIBLE = 0;
        boolean recommendInfoIsrecommendInt1 = false;
        int recommendInfoIsrecommendInt1SnsInfoDiscussinfoIdInt0ViewGONEViewVISIBLEViewGONE = 0;
        if ((21 & dirtyFlags) != 0) {
            if (carInfo != null) {
                carInfoName = carInfo.name;
            }
            carSeriesNameJavaLangStringCarInfoName = (carSeriesName + " ") + carInfoName;
            if (!((17 & dirtyFlags) == 0 || carInfo == null)) {
                carInfoGetGuidePrice = carInfo.getGuidePrice();
                carInfoGetCarPriceText2 = carInfo.getCarPriceText2();
            }
        }
        if ((26 & dirtyFlags) != 0) {
            if (recommendInfo != null) {
                recommendInfoIsrecommend = recommendInfo.isrecommend;
            }
            recommendInfoIsrecommendInt1 = recommendInfoIsrecommend == 1;
            if ((26 & dirtyFlags) != 0) {
                dirtyFlags = recommendInfoIsrecommendInt1 ? dirtyFlags | 256 : dirtyFlags | 128;
            }
        }
        if ((256 & dirtyFlags) != 0) {
            if (snsInfo != null) {
                snsInfoDiscussinfo = snsInfo.discussinfo;
            }
            if (snsInfoDiscussinfo != null) {
                snsInfoDiscussinfoId = snsInfoDiscussinfo.id;
            }
            boolean snsInfoDiscussinfoIdInt0 = snsInfoDiscussinfoId == 0;
            if ((256 & dirtyFlags) != 0) {
                if (snsInfoDiscussinfoIdInt0) {
                    dirtyFlags |= 64;
                } else {
                    dirtyFlags |= 32;
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
            TextViewBindingAdapter.setText(this.mboundView3, carInfoGetCarPriceText2);
            TextViewBindingAdapter.setText(this.mboundView4, carInfoGetGuidePrice);
        }
    }

    public static ItemVehiclesBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemVehiclesBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemVehiclesBinding) DataBindingUtil.inflate(inflater, 2130903284, root, attachToRoot, bindingComponent);
    }

    public static ItemVehiclesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemVehiclesBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903284, null, false), bindingComponent);
    }

    public static ItemVehiclesBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemVehiclesBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_vehicles_0".equals(view.getTag())) {
            return new ItemVehiclesBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
