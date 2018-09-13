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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarImageInfo;
import com.feng.car.view.FixedViewPager;

public class ActivityShowCarimageBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageButton ibClose;
    public final ImageView ivBigimageHd;
    public final ImageView ivBigimageSave;
    public final ImageView ivFirstBottomLine;
    public final ImageView ivFourBottomLine;
    public final ImageView ivSecondBottomLine;
    public final ImageView ivThirdBottomLine;
    public final RelativeLayout llBottom;
    public final LinearLayout llContent;
    public final RelativeLayout llParent;
    public final LinearLayout llTab;
    public final LinearLayout llTypeFirst;
    public final LinearLayout llTypeFour;
    public final LinearLayout llTypeSecond;
    public final LinearLayout llTypeThird;
    public final LinearLayout llVehicleImage4sInfoContainer;
    private CarImageInfo mCarImageInfo;
    private long mDirtyFlags = -1;
    public final RelativeLayout rlControllerContainer;
    public final RelativeLayout rlTopBar;
    public final TextView tvBigimagePageText;
    public final TextView tvCarModelName;
    public final TextView tvFirstTab;
    public final TextView tvFourTab;
    public final TextView tvSecondTab;
    public final TextView tvThirdTab;
    public final TextView tvVehicleImage4sName;
    public final TextView tvVehicleImage4sTel;
    public final TextView tvVehicleImageSourceText;
    public final FixedViewPager viewpager;

    static {
        sViewsWithIds.put(2131624650, 2);
        sViewsWithIds.put(2131624663, 3);
        sViewsWithIds.put(2131624664, 4);
        sViewsWithIds.put(2131624665, 5);
        sViewsWithIds.put(2131624666, 6);
        sViewsWithIds.put(2131624667, 7);
        sViewsWithIds.put(2131624668, 8);
        sViewsWithIds.put(2131624669, 9);
        sViewsWithIds.put(2131624670, 10);
        sViewsWithIds.put(2131624671, 11);
        sViewsWithIds.put(2131624672, 12);
        sViewsWithIds.put(2131624673, 13);
        sViewsWithIds.put(2131624674, 14);
        sViewsWithIds.put(2131624675, 15);
        sViewsWithIds.put(2131624676, 16);
        sViewsWithIds.put(2131624677, 17);
        sViewsWithIds.put(2131624678, 18);
        sViewsWithIds.put(2131624410, 19);
        sViewsWithIds.put(2131624680, 20);
        sViewsWithIds.put(2131624681, 21);
        sViewsWithIds.put(2131624682, 22);
        sViewsWithIds.put(2131624683, 23);
        sViewsWithIds.put(2131624684, 24);
        sViewsWithIds.put(2131624658, 25);
        sViewsWithIds.put(2131624661, 26);
        sViewsWithIds.put(2131624662, 27);
    }

    public ActivityShowCarimageBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 28, sIncludes, sViewsWithIds);
        this.ibClose = (ImageButton) bindings[4];
        this.ivBigimageHd = (ImageView) bindings[26];
        this.ivBigimageSave = (ImageView) bindings[27];
        this.ivFirstBottomLine = (ImageView) bindings[8];
        this.ivFourBottomLine = (ImageView) bindings[17];
        this.ivSecondBottomLine = (ImageView) bindings[11];
        this.ivThirdBottomLine = (ImageView) bindings[14];
        this.llBottom = (RelativeLayout) bindings[18];
        this.llContent = (LinearLayout) bindings[19];
        this.llParent = (RelativeLayout) bindings[0];
        this.llParent.setTag(null);
        this.llTab = (LinearLayout) bindings[5];
        this.llTypeFirst = (LinearLayout) bindings[6];
        this.llTypeFour = (LinearLayout) bindings[15];
        this.llTypeSecond = (LinearLayout) bindings[9];
        this.llTypeThird = (LinearLayout) bindings[12];
        this.llVehicleImage4sInfoContainer = (LinearLayout) bindings[21];
        this.rlControllerContainer = (RelativeLayout) bindings[24];
        this.rlTopBar = (RelativeLayout) bindings[3];
        this.tvBigimagePageText = (TextView) bindings[25];
        this.tvCarModelName = (TextView) bindings[1];
        this.tvCarModelName.setTag(null);
        this.tvFirstTab = (TextView) bindings[7];
        this.tvFourTab = (TextView) bindings[16];
        this.tvSecondTab = (TextView) bindings[10];
        this.tvThirdTab = (TextView) bindings[13];
        this.tvVehicleImage4sName = (TextView) bindings[22];
        this.tvVehicleImage4sTel = (TextView) bindings[23];
        this.tvVehicleImageSourceText = (TextView) bindings[20];
        this.viewpager = (FixedViewPager) bindings[2];
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
            case 4:
                setCarImageInfo((CarImageInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCarImageInfo(CarImageInfo CarImageInfo) {
        this.mCarImageInfo = CarImageInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    public CarImageInfo getCarImageInfo() {
        return this.mCarImageInfo;
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
        String carImageInfoCarxname = null;
        CarImageInfo carImageInfo = this.mCarImageInfo;
        if (!((dirtyFlags & 3) == 0 || carImageInfo == null)) {
            carImageInfoCarxname = carImageInfo.carxname;
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.tvCarModelName, carImageInfoCarxname);
        }
    }

    public static ActivityShowCarimageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityShowCarimageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityShowCarimageBinding) DataBindingUtil.inflate(inflater, 2130903140, root, attachToRoot, bindingComponent);
    }

    public static ActivityShowCarimageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityShowCarimageBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903140, null, false), bindingComponent);
    }

    public static ActivityShowCarimageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityShowCarimageBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_show_carimage_0".equals(view.getTag())) {
            return new ActivityShowCarimageBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
