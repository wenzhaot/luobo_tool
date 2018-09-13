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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ActivityPushSecondaryBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View divider;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final RadioButton rbAll;
    public final RadioButton rbClose;
    public final RadioButton rbFriend;
    public final RadioGroup rgSetting;
    public final TextView tvImageQualityAutoTip;

    static {
        sViewsWithIds.put(2131624556, 2);
        sViewsWithIds.put(2131624240, 3);
        sViewsWithIds.put(2131624565, 4);
        sViewsWithIds.put(2131624557, 5);
        sViewsWithIds.put(2131624566, 6);
    }

    public ActivityPushSecondaryBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.divider = (View) bindings[3];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rbAll = (RadioButton) bindings[2];
        this.rbClose = (RadioButton) bindings[6];
        this.rbFriend = (RadioButton) bindings[5];
        this.rgSetting = (RadioGroup) bindings[1];
        this.rgSetting.setTag(null);
        this.tvImageQualityAutoTip = (TextView) bindings[4];
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

    public static ActivityPushSecondaryBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPushSecondaryBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityPushSecondaryBinding) DataBindingUtil.inflate(inflater, 2130903117, root, attachToRoot, bindingComponent);
    }

    public static ActivityPushSecondaryBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPushSecondaryBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903117, null, false), bindingComponent);
    }

    public static ActivityPushSecondaryBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPushSecondaryBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_push_secondary_0".equals(view.getTag())) {
            return new ActivityPushSecondaryBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
