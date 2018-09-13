package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.ComparisonView;
import com.feng.car.view.guideview.ShowTipsView;
import com.feng.car.view.recyclerview.EmptyView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ActivityVehicleBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdVehiclePicture1;
    public final AutoFrescoDraweeView afdVehiclePicture2;
    public final AutoFrescoDraweeView afdVehiclePicture3;
    public final AutoFrescoDraweeView afdVehiclePicture4;
    public final AutoFrescoDraweeView afdVehiclePicture5;
    public final AppBarLayout appBar;
    public final EmptyView emptyView;
    public final TabLayout idStickynavlayoutIndicator;
    public final ViewPager idStickynavlayoutViewpager;
    public final ImageView ivBack;
    public final ImageView ivShare;
    public final ImageView ivVehicleConfiguration;
    public final LinearLayout llBubbleCityContainer;
    public final LinearLayout llVehiclePictureContainer;
    public final LinearLayout llVehiclePriceContainer;
    private long mDirtyFlags = -1;
    private CarSeriesInfo mInfo;
    private final RelativeLayout mboundView0;
    public final LinearLayout navLayoutContainer;
    public final CoordinatorLayout parent;
    public final LRecyclerView rcvVehicleCenterDetailInfo;
    public final ComparisonView rightCvPk;
    public final RelativeLayout rlTitleBar;
    public final RelativeLayout rlVehiclePictureContainer;
    public final ShowTipsView showTipsView;
    public final TextView step0;
    public final RelativeLayout tipsline0;
    public final TextView tvAgencyBubbleLocationCity;
    public final TextView tvCarPriceText;
    public final TextView tvTitle;
    public final TextView tvVehicleLevel;
    public final TextView tvVehicleName;
    public final TextView tvVehiclePictureMoreNum;
    public final TextView tvVehiclePrice;

    static {
        sViewsWithIds.put(2131624215, 2);
        sViewsWithIds.put(2131624228, 3);
        sViewsWithIds.put(2131624296, 4);
        sViewsWithIds.put(2131624297, 5);
        sViewsWithIds.put(2131624701, 6);
        sViewsWithIds.put(2131623989, 7);
        sViewsWithIds.put(2131624309, 8);
        sViewsWithIds.put(2131624702, 9);
        sViewsWithIds.put(2131624703, 10);
        sViewsWithIds.put(2131624705, 11);
        sViewsWithIds.put(2131624706, 12);
        sViewsWithIds.put(2131624707, 13);
        sViewsWithIds.put(2131624708, 14);
        sViewsWithIds.put(2131624709, 15);
        sViewsWithIds.put(2131624710, 16);
        sViewsWithIds.put(2131624711, 17);
        sViewsWithIds.put(2131624712, 18);
        sViewsWithIds.put(2131624713, 19);
        sViewsWithIds.put(2131624714, 20);
        sViewsWithIds.put(2131624715, 21);
        sViewsWithIds.put(2131624716, 22);
        sViewsWithIds.put(2131624717, 23);
        sViewsWithIds.put(2131623967, 24);
        sViewsWithIds.put(2131623970, 25);
        sViewsWithIds.put(2131624432, 26);
        sViewsWithIds.put(2131624433, 27);
        sViewsWithIds.put(2131624527, 28);
        sViewsWithIds.put(2131624718, 29);
        sViewsWithIds.put(2131624528, 30);
        sViewsWithIds.put(2131624231, 31);
    }

    public ActivityVehicleBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 32, sIncludes, sViewsWithIds);
        this.afdVehiclePicture1 = (AutoFrescoDraweeView) bindings[17];
        this.afdVehiclePicture2 = (AutoFrescoDraweeView) bindings[18];
        this.afdVehiclePicture3 = (AutoFrescoDraweeView) bindings[20];
        this.afdVehiclePicture4 = (AutoFrescoDraweeView) bindings[19];
        this.afdVehiclePicture5 = (AutoFrescoDraweeView) bindings[21];
        this.appBar = (AppBarLayout) bindings[8];
        this.emptyView = (EmptyView) bindings[31];
        this.idStickynavlayoutIndicator = (TabLayout) bindings[24];
        this.idStickynavlayoutViewpager = (ViewPager) bindings[25];
        this.ivBack = (ImageView) bindings[3];
        this.ivShare = (ImageView) bindings[6];
        this.ivVehicleConfiguration = (ImageView) bindings[15];
        this.llBubbleCityContainer = (LinearLayout) bindings[26];
        this.llVehiclePictureContainer = (LinearLayout) bindings[16];
        this.llVehiclePriceContainer = (LinearLayout) bindings[11];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.navLayoutContainer = (LinearLayout) bindings[9];
        this.parent = (CoordinatorLayout) bindings[7];
        this.rcvVehicleCenterDetailInfo = (LRecyclerView) bindings[23];
        this.rightCvPk = (ComparisonView) bindings[5];
        this.rlTitleBar = (RelativeLayout) bindings[2];
        this.rlVehiclePictureContainer = (RelativeLayout) bindings[10];
        this.showTipsView = (ShowTipsView) bindings[28];
        this.step0 = (TextView) bindings[30];
        this.tipsline0 = (RelativeLayout) bindings[29];
        this.tvAgencyBubbleLocationCity = (TextView) bindings[27];
        this.tvCarPriceText = (TextView) bindings[13];
        this.tvTitle = (TextView) bindings[4];
        this.tvVehicleLevel = (TextView) bindings[12];
        this.tvVehicleName = (TextView) bindings[1];
        this.tvVehicleName.setTag(null);
        this.tvVehiclePictureMoreNum = (TextView) bindings[22];
        this.tvVehiclePrice = (TextView) bindings[14];
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
            case 28:
                setInfo((CarSeriesInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(CarSeriesInfo Info) {
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public CarSeriesInfo getInfo() {
        return this.mInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeInfoName((ObservableField) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeInfoName(ObservableField<String> observableField, int fieldId) {
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
        ObservableField<String> infoName = null;
        String infoNameGet = null;
        CarSeriesInfo info = this.mInfo;
        if ((dirtyFlags & 7) != 0) {
            if (info != null) {
                infoName = info.name;
            }
            updateRegistration(0, infoName);
            if (infoName != null) {
                infoNameGet = (String) infoName.get();
            }
        }
        if ((dirtyFlags & 7) != 0) {
            TextViewBindingAdapter.setText(this.tvVehicleName, infoNameGet);
        }
    }

    public static ActivityVehicleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityVehicleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityVehicleBinding) DataBindingUtil.inflate(inflater, 2130903146, root, attachToRoot, bindingComponent);
    }

    public static ActivityVehicleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityVehicleBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903146, null, false), bindingComponent);
    }

    public static ActivityVehicleBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityVehicleBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_vehicle_0".equals(view.getTag())) {
            return new ActivityVehicleBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
