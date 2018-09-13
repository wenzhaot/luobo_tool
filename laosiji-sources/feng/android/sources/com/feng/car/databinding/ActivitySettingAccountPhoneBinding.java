package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivitySettingAccountPhoneBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final EditText etPhoneCode;
    public final EditText etPhoneNumber;
    private long mDirtyFlags = -1;
    private int mUseless;
    private final LinearLayout mboundView0;
    public final TextView tvComplete;
    public final TextView tvPhoneNumber;
    public final TextView tvPhoneReceiveCodeTip;
    public final TextView tvPhoneSendCode;

    static {
        sViewsWithIds.put(2131624494, 1);
        sViewsWithIds.put(2131624626, 2);
        sViewsWithIds.put(2131624627, 3);
        sViewsWithIds.put(2131624628, 4);
        sViewsWithIds.put(2131624629, 5);
        sViewsWithIds.put(2131624630, 6);
    }

    public ActivitySettingAccountPhoneBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.etPhoneCode = (EditText) bindings[4];
        this.etPhoneNumber = (EditText) bindings[1];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvComplete = (TextView) bindings[6];
        this.tvPhoneNumber = (TextView) bindings[3];
        this.tvPhoneReceiveCodeTip = (TextView) bindings[2];
        this.tvPhoneSendCode = (TextView) bindings[5];
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

    public static ActivitySettingAccountPhoneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySettingAccountPhoneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivitySettingAccountPhoneBinding) DataBindingUtil.inflate(inflater, 2130903135, root, attachToRoot, bindingComponent);
    }

    public static ActivitySettingAccountPhoneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySettingAccountPhoneBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903135, null, false), bindingComponent);
    }

    public static ActivitySettingAccountPhoneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySettingAccountPhoneBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_setting_account_phone_0".equals(view.getTag())) {
            return new ActivitySettingAccountPhoneBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
