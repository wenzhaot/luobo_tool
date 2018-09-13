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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginBy5xBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final EditText etLoginAccount;
    public final EditText etPwd;
    public final ImageView ivAsk5x;
    public final ImageView ivLogo;
    public final View line1;
    public final View line2;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final RelativeLayout mboundView0;
    public final TextView tv;
    public final TextView tvLogin;

    static {
        sViewsWithIds.put(2131624686, 1);
        sViewsWithIds.put(2131625252, 2);
        sViewsWithIds.put(2131625253, 3);
        sViewsWithIds.put(2131623983, 4);
        sViewsWithIds.put(2131625254, 5);
        sViewsWithIds.put(2131624460, 6);
        sViewsWithIds.put(2131624388, 7);
        sViewsWithIds.put(2131625255, 8);
    }

    public LoginBy5xBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.etLoginAccount = (EditText) bindings[3];
        this.etPwd = (EditText) bindings[5];
        this.ivAsk5x = (ImageView) bindings[8];
        this.ivLogo = (ImageView) bindings[1];
        this.line1 = (View) bindings[4];
        this.line2 = (View) bindings[6];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tv = (TextView) bindings[2];
        this.tvLogin = (TextView) bindings[7];
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

    public static LoginBy5xBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static LoginBy5xBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (LoginBy5xBinding) DataBindingUtil.inflate(inflater, 2130903306, root, attachToRoot, bindingComponent);
    }

    public static LoginBy5xBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static LoginBy5xBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903306, null, false), bindingComponent);
    }

    public static LoginBy5xBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static LoginBy5xBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/login_by_5x_0".equals(view.getTag())) {
            return new LoginBy5xBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
