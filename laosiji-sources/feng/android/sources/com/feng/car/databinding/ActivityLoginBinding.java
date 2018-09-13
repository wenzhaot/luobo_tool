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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityLoginBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final EditText etLoginNumber;
    public final EditText etLoginVerify;
    public final ImageView ivLogin5x;
    public final ImageView ivLoginLogo;
    public final ImageView ivLoginQq;
    public final ImageView ivLoginWechat;
    public final ImageView ivLoginWeibo;
    public final LinearLayout llLoginAuth;
    public final RelativeLayout llLoginVerify;
    public final LinearLayout llThirdLogin;
    private long mDirtyFlags = -1;
    private int mUseless;
    private final LinearLayout mboundView0;
    public final RelativeLayout rl5x;
    public final RelativeLayout rlWeibo;
    public final TextView tvLogin;
    public final TextView tvLoginLatestUse5x;
    public final TextView tvLoginLatestUseQq;
    public final TextView tvLoginLatestUseWechat;
    public final TextView tvLoginLatestUseWeibo;
    public final TextView tvLoginSendVerify;
    public final TextView tvLoginWelcome;
    public final TextView tvPhoneCheck;
    public final TextView tvSkipVerifyPhone;
    public final TextView tvVerifyPhoneTip;
    public final RelativeLayout wechatLine;

    static {
        sViewsWithIds.put(2131624380, 1);
        sViewsWithIds.put(2131624381, 2);
        sViewsWithIds.put(2131624382, 3);
        sViewsWithIds.put(2131624383, 4);
        sViewsWithIds.put(2131624384, 5);
        sViewsWithIds.put(2131624385, 6);
        sViewsWithIds.put(2131624386, 7);
        sViewsWithIds.put(2131624387, 8);
        sViewsWithIds.put(2131624388, 9);
        sViewsWithIds.put(2131624389, 10);
        sViewsWithIds.put(2131624390, 11);
        sViewsWithIds.put(2131624391, 12);
        sViewsWithIds.put(2131624392, 13);
        sViewsWithIds.put(2131624393, 14);
        sViewsWithIds.put(2131624394, 15);
        sViewsWithIds.put(2131624395, 16);
        sViewsWithIds.put(2131624396, 17);
        sViewsWithIds.put(2131624397, 18);
        sViewsWithIds.put(2131624398, 19);
        sViewsWithIds.put(2131624399, 20);
        sViewsWithIds.put(2131624400, 21);
        sViewsWithIds.put(2131624401, 22);
        sViewsWithIds.put(2131624402, 23);
    }

    public ActivityLoginBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds);
        this.etLoginNumber = (EditText) bindings[5];
        this.etLoginVerify = (EditText) bindings[7];
        this.ivLogin5x = (ImageView) bindings[14];
        this.ivLoginLogo = (ImageView) bindings[1];
        this.ivLoginQq = (ImageView) bindings[22];
        this.ivLoginWechat = (ImageView) bindings[17];
        this.ivLoginWeibo = (ImageView) bindings[20];
        this.llLoginAuth = (LinearLayout) bindings[12];
        this.llLoginVerify = (RelativeLayout) bindings[6];
        this.llThirdLogin = (LinearLayout) bindings[11];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rl5x = (RelativeLayout) bindings[13];
        this.rlWeibo = (RelativeLayout) bindings[19];
        this.tvLogin = (TextView) bindings[9];
        this.tvLoginLatestUse5x = (TextView) bindings[15];
        this.tvLoginLatestUseQq = (TextView) bindings[23];
        this.tvLoginLatestUseWechat = (TextView) bindings[18];
        this.tvLoginLatestUseWeibo = (TextView) bindings[21];
        this.tvLoginSendVerify = (TextView) bindings[8];
        this.tvLoginWelcome = (TextView) bindings[2];
        this.tvPhoneCheck = (TextView) bindings[3];
        this.tvSkipVerifyPhone = (TextView) bindings[10];
        this.tvVerifyPhoneTip = (TextView) bindings[4];
        this.wechatLine = (RelativeLayout) bindings[16];
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

    public static ActivityLoginBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityLoginBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityLoginBinding) DataBindingUtil.inflate(inflater, 2130903098, root, attachToRoot, bindingComponent);
    }

    public static ActivityLoginBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityLoginBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903098, null, false), bindingComponent);
    }

    public static ActivityLoginBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityLoginBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_login_0".equals(view.getTag())) {
            return new ActivityLoginBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
