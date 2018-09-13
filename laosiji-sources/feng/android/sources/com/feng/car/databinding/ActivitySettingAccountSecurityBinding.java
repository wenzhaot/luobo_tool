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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivitySettingAccountSecurityBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private int mUseless;
    private final LinearLayout mboundView0;
    public final RelativeLayout rlAccountPhone;
    public final RelativeLayout rlAccountQq;
    public final RelativeLayout rlAccountSina;
    public final RelativeLayout rlAccountWeChat;
    public final TextView tvAccountBindPhone;
    public final TextView tvAccountPhone;
    public final TextView tvAccountQQ;
    public final TextView tvAccountSina;
    public final TextView tvAccountWeChat;
    public final TextView tvPhoneNumber;

    static {
        sViewsWithIds.put(2131624631, 1);
        sViewsWithIds.put(2131624632, 2);
        sViewsWithIds.put(2131624627, 3);
        sViewsWithIds.put(2131624633, 4);
        sViewsWithIds.put(2131624634, 5);
        sViewsWithIds.put(2131624635, 6);
        sViewsWithIds.put(2131624636, 7);
        sViewsWithIds.put(2131624637, 8);
        sViewsWithIds.put(2131624638, 9);
        sViewsWithIds.put(2131624639, 10);
    }

    public ActivitySettingAccountSecurityBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlAccountPhone = (RelativeLayout) bindings[1];
        this.rlAccountQq = (RelativeLayout) bindings[9];
        this.rlAccountSina = (RelativeLayout) bindings[7];
        this.rlAccountWeChat = (RelativeLayout) bindings[5];
        this.tvAccountBindPhone = (TextView) bindings[4];
        this.tvAccountPhone = (TextView) bindings[2];
        this.tvAccountQQ = (TextView) bindings[10];
        this.tvAccountSina = (TextView) bindings[8];
        this.tvAccountWeChat = (TextView) bindings[6];
        this.tvPhoneNumber = (TextView) bindings[3];
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
                setUseless(((Integer) variable).intValue());
                return true;
            default:
                return false;
        }
    }

    public void setUseless(int Useless) {
        this.mUseless = Useless;
    }

    public int getUseless() {
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

    public static ActivitySettingAccountSecurityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySettingAccountSecurityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivitySettingAccountSecurityBinding) DataBindingUtil.inflate(inflater, 2130903136, root, attachToRoot, bindingComponent);
    }

    public static ActivitySettingAccountSecurityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySettingAccountSecurityBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903136, null, false), bindingComponent);
    }

    public static ActivitySettingAccountSecurityBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySettingAccountSecurityBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_setting_account_security_0".equals(view.getTag())) {
            return new ActivitySettingAccountSecurityBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
