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

public class ActivityPrivateSettingBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private Integer mUseless;
    private final LinearLayout mboundView0;
    public final RadioButton rbAll;
    public final RadioButton rbFriend;
    public final RadioGroup rgPrivateSetting;

    static {
        sViewsWithIds.put(2131624556, 2);
        sViewsWithIds.put(2131624557, 3);
    }

    public ActivityPrivateSettingBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rbAll = (RadioButton) bindings[2];
        this.rbFriend = (RadioButton) bindings[3];
        this.rgPrivateSetting = (RadioGroup) bindings[1];
        this.rgPrivateSetting.setTag(null);
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
            case 74:
                setUseless((Integer) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUseless(Integer Useless) {
        this.mUseless = Useless;
    }

    public Integer getUseless() {
        return this.mUseless;
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

    public static ActivityPrivateSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPrivateSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityPrivateSettingBinding) DataBindingUtil.inflate(inflater, 2130903115, root, attachToRoot, bindingComponent);
    }

    public static ActivityPrivateSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPrivateSettingBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903115, null, false), bindingComponent);
    }

    public static ActivityPrivateSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPrivateSettingBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_private_setting_0".equals(view.getTag())) {
            return new ActivityPrivateSettingBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
