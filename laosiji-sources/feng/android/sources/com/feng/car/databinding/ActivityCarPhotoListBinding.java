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

public class ActivityCarPhotoListBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout llSelectCarModel;
    private long mDirtyFlags = -1;
    private String mStr;
    private final RelativeLayout mboundView0;
    public final TextView nodataTips;
    public final TabLayout tabCarModelYear;
    public final TabLayout tabCarPhotoType;
    public final TextView tvCarSeries;
    public final View viewShade;
    public final ViewPager vpCarModel;
    public final ViewPager vpCarPhoto;

    static {
        sViewsWithIds.put(2131624301, 1);
        sViewsWithIds.put(2131624302, 2);
        sViewsWithIds.put(2131624303, 3);
        sViewsWithIds.put(2131624304, 4);
        sViewsWithIds.put(2131624305, 5);
        sViewsWithIds.put(2131624306, 6);
        sViewsWithIds.put(2131624307, 7);
        sViewsWithIds.put(2131624308, 8);
    }

    public ActivityCarPhotoListBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.llSelectCarModel = (LinearLayout) bindings[5];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.nodataTips = (TextView) bindings[3];
        this.tabCarModelYear = (TabLayout) bindings[7];
        this.tabCarPhotoType = (TabLayout) bindings[1];
        this.tvCarSeries = (TextView) bindings[6];
        this.viewShade = (View) bindings[4];
        this.vpCarModel = (ViewPager) bindings[8];
        this.vpCarPhoto = (ViewPager) bindings[2];
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
            case 65:
                setStr((String) variable);
                return true;
            default:
                return false;
        }
    }

    public void setStr(String Str) {
        this.mStr = Str;
    }

    public String getStr() {
        return this.mStr;
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

    public static ActivityCarPhotoListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCarPhotoListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityCarPhotoListBinding) DataBindingUtil.inflate(inflater, 2130903080, root, attachToRoot, bindingComponent);
    }

    public static ActivityCarPhotoListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCarPhotoListBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903080, null, false), bindingComponent);
    }

    public static ActivityCarPhotoListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCarPhotoListBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_car_photo_list_0".equals(view.getTag())) {
            return new ActivityCarPhotoListBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
