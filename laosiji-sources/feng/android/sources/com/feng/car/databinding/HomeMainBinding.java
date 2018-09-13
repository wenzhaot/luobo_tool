package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeMainBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView activityBtn;
    public final FrameLayout fragmentContainer;
    public final ImageView ivFiveDot;
    public final ImageView ivFourDot;
    public final ImageView ivThirdTab;
    public final View line;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final LinearLayout mainBottom;
    private final RelativeLayout mboundView0;
    public final ImageView placeHolderThird;
    public final RelativeLayout rlFirstTab;
    public final RelativeLayout rlFiveTab;
    public final RelativeLayout rlFourTab;
    public final TextView tvFirstDot;
    public final TextView tvFirstTab;
    public final TextView tvFiveTab;
    public final TextView tvFourTab;
    public final TextView tvSecondTab;
    public final View viewGuideTag;

    static {
        sViewsWithIds.put(2131624374, 1);
        sViewsWithIds.put(2131625089, 2);
        sViewsWithIds.put(2131625090, 3);
        sViewsWithIds.put(2131624667, 4);
        sViewsWithIds.put(2131625091, 5);
        sViewsWithIds.put(2131624670, 6);
        sViewsWithIds.put(2131625092, 7);
        sViewsWithIds.put(2131625093, 8);
        sViewsWithIds.put(2131624676, 9);
        sViewsWithIds.put(2131625094, 10);
        sViewsWithIds.put(2131625095, 11);
        sViewsWithIds.put(2131625096, 12);
        sViewsWithIds.put(2131625097, 13);
        sViewsWithIds.put(2131625098, 14);
        sViewsWithIds.put(2131625099, 15);
        sViewsWithIds.put(2131625100, 16);
        sViewsWithIds.put(2131625101, 17);
    }

    public HomeMainBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds);
        this.activityBtn = (TextView) bindings[17];
        this.fragmentContainer = (FrameLayout) bindings[16];
        this.ivFiveDot = (ImageView) bindings[13];
        this.ivFourDot = (ImageView) bindings[10];
        this.ivThirdTab = (ImageView) bindings[14];
        this.line = (View) bindings[1];
        this.mainBottom = (LinearLayout) bindings[2];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.placeHolderThird = (ImageView) bindings[7];
        this.rlFirstTab = (RelativeLayout) bindings[3];
        this.rlFiveTab = (RelativeLayout) bindings[11];
        this.rlFourTab = (RelativeLayout) bindings[8];
        this.tvFirstDot = (TextView) bindings[5];
        this.tvFirstTab = (TextView) bindings[4];
        this.tvFiveTab = (TextView) bindings[12];
        this.tvFourTab = (TextView) bindings[9];
        this.tvSecondTab = (TextView) bindings[6];
        this.viewGuideTag = (View) bindings[15];
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
            case 72:
                setUnwanted((Integer) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUnwanted(Integer Unwanted) {
        this.mUnwanted = Unwanted;
    }

    public Integer getUnwanted() {
        return this.mUnwanted;
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

    public static HomeMainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static HomeMainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (HomeMainBinding) DataBindingUtil.inflate(inflater, 2130903256, root, attachToRoot, bindingComponent);
    }

    public static HomeMainBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static HomeMainBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903256, null, false), bindingComponent);
    }

    public static HomeMainBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static HomeMainBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/home_main_0".equals(view.getTag())) {
            return new HomeMainBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
