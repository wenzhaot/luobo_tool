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

public class WalletDetailTopPopBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View background;
    private long mDirtyFlags = -1;
    private Integer mUseless;
    private final LinearLayout mboundView0;
    public final RadioButton rbAllDetail;
    public final RadioButton rbAnswerSuccessGot;
    public final RadioButton rbPickUp;
    public final RadioButton rbSharePay;
    public final RadioButton rbSysRedPacket;
    public final RadioGroup rgChange;

    static {
        sViewsWithIds.put(2131625559, 1);
        sViewsWithIds.put(2131625560, 2);
        sViewsWithIds.put(2131625561, 3);
        sViewsWithIds.put(2131625562, 4);
        sViewsWithIds.put(2131625563, 5);
        sViewsWithIds.put(2131625564, 6);
        sViewsWithIds.put(2131625565, 7);
    }

    public WalletDetailTopPopBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.background = (View) bindings[7];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rbAllDetail = (RadioButton) bindings[2];
        this.rbAnswerSuccessGot = (RadioButton) bindings[3];
        this.rbPickUp = (RadioButton) bindings[6];
        this.rbSharePay = (RadioButton) bindings[4];
        this.rbSysRedPacket = (RadioButton) bindings[5];
        this.rgChange = (RadioGroup) bindings[1];
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

    public static WalletDetailTopPopBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static WalletDetailTopPopBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (WalletDetailTopPopBinding) DataBindingUtil.inflate(inflater, 2130903432, root, attachToRoot, bindingComponent);
    }

    public static WalletDetailTopPopBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static WalletDetailTopPopBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903432, null, false), bindingComponent);
    }

    public static WalletDetailTopPopBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static WalletDetailTopPopBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/wallet_detail_top_pop_0".equals(view.getTag())) {
            return new WalletDetailTopPopBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
