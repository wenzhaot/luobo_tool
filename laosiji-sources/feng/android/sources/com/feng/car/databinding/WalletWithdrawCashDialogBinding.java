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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WalletWithdrawCashDialogBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final EditText aliAccount;
    public final ImageView bottomButton;
    public final ImageButton ivClose;
    private long mDirtyFlags = -1;
    private Integer mUseless;
    public final RelativeLayout rlPickUpParent;
    public final LinearLayout successLine;
    public final TextView successTips;
    public final TextView title;
    public final TextView tvIsAliPayTip;
    public final TextView tvNotAliPayTip;
    public final TextView tvText;
    public final EditText userName;
    public final ImageView withDrawComit;

    static {
        sViewsWithIds.put(2131624993, 1);
        sViewsWithIds.put(2131624312, 2);
        sViewsWithIds.put(2131624419, 3);
        sViewsWithIds.put(2131625567, 4);
        sViewsWithIds.put(2131625568, 5);
        sViewsWithIds.put(2131625569, 6);
        sViewsWithIds.put(2131625570, 7);
        sViewsWithIds.put(2131624748, 8);
        sViewsWithIds.put(2131624015, 9);
        sViewsWithIds.put(2131625571, 10);
        sViewsWithIds.put(2131625572, 11);
    }

    public WalletWithdrawCashDialogBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds);
        this.aliAccount = (EditText) bindings[4];
        this.bottomButton = (ImageView) bindings[11];
        this.ivClose = (ImageButton) bindings[1];
        this.rlPickUpParent = (RelativeLayout) bindings[0];
        this.rlPickUpParent.setTag(null);
        this.successLine = (LinearLayout) bindings[8];
        this.successTips = (TextView) bindings[10];
        this.title = (TextView) bindings[9];
        this.tvIsAliPayTip = (TextView) bindings[5];
        this.tvNotAliPayTip = (TextView) bindings[6];
        this.tvText = (TextView) bindings[2];
        this.userName = (EditText) bindings[3];
        this.withDrawComit = (ImageView) bindings[7];
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

    public static WalletWithdrawCashDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static WalletWithdrawCashDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (WalletWithdrawCashDialogBinding) DataBindingUtil.inflate(inflater, 2130903433, root, attachToRoot, bindingComponent);
    }

    public static WalletWithdrawCashDialogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static WalletWithdrawCashDialogBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903433, null, false), bindingComponent);
    }

    public static WalletWithdrawCashDialogBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static WalletWithdrawCashDialogBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/wallet_withdraw_cash_dialog_0".equals(view.getTag())) {
            return new WalletWithdrawCashDialogBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
