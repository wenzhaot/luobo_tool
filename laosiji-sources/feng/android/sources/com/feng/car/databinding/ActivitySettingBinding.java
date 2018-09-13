package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ActivitySettingBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView ivSettingAboutHint;
    public final ImageView ivSettingImageQuality;
    public final ImageView ivSettingPushMessage;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final ScrollView mboundView0;
    public final RelativeLayout rlSettingAboutUs;
    public final RelativeLayout rlSettingAccount;
    public final RelativeLayout rlSettingClearCache;
    public final RelativeLayout rlSettingCooperation;
    public final RelativeLayout rlSettingFeedback;
    public final RelativeLayout rlSettingImageQuality;
    public final RelativeLayout rlSettingPrivate;
    public final RelativeLayout rlSettingPushMessage;
    public final RelativeLayout rlSettingUserAgreement;
    public final TextView tvSettingCacheSize;
    public final TextView tvSettingExit;
    public final TextView tvSettingImageQuality;

    static {
        sViewsWithIds.put(2131624611, 1);
        sViewsWithIds.put(2131624612, 2);
        sViewsWithIds.put(2131624613, 3);
        sViewsWithIds.put(2131624614, 4);
        sViewsWithIds.put(2131624615, 5);
        sViewsWithIds.put(2131624616, 6);
        sViewsWithIds.put(2131624617, 7);
        sViewsWithIds.put(2131624618, 8);
        sViewsWithIds.put(2131624619, 9);
        sViewsWithIds.put(2131624620, 10);
        sViewsWithIds.put(2131624621, 11);
        sViewsWithIds.put(2131624622, 12);
        sViewsWithIds.put(2131624623, 13);
        sViewsWithIds.put(2131624624, 14);
        sViewsWithIds.put(2131624625, 15);
    }

    public ActivitySettingBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds);
        this.ivSettingAboutHint = (ImageView) bindings[9];
        this.ivSettingImageQuality = (ImageView) bindings[7];
        this.ivSettingPushMessage = (ImageView) bindings[3];
        this.mboundView0 = (ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.rlSettingAboutUs = (RelativeLayout) bindings[8];
        this.rlSettingAccount = (RelativeLayout) bindings[1];
        this.rlSettingClearCache = (RelativeLayout) bindings[10];
        this.rlSettingCooperation = (RelativeLayout) bindings[14];
        this.rlSettingFeedback = (RelativeLayout) bindings[12];
        this.rlSettingImageQuality = (RelativeLayout) bindings[5];
        this.rlSettingPrivate = (RelativeLayout) bindings[4];
        this.rlSettingPushMessage = (RelativeLayout) bindings[2];
        this.rlSettingUserAgreement = (RelativeLayout) bindings[13];
        this.tvSettingCacheSize = (TextView) bindings[11];
        this.tvSettingExit = (TextView) bindings[15];
        this.tvSettingImageQuality = (TextView) bindings[6];
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

    public static ActivitySettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivitySettingBinding) DataBindingUtil.inflate(inflater, 2130903134, root, attachToRoot, bindingComponent);
    }

    public static ActivitySettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySettingBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903134, null, false), bindingComponent);
    }

    public static ActivitySettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySettingBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_setting_0".equals(view.getTag())) {
            return new ActivitySettingBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
