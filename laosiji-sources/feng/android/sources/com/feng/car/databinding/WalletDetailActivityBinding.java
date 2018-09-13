package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class WalletDetailActivityBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View divider;
    public final ImageButton ibPopIndicate;
    public final ImageView ivTitleLeft;
    public final LRecyclerView lRc;
    private long mDirtyFlags = -1;
    private Integer mUseless;
    public final RelativeLayout rlParent;
    public final RelativeLayout rlTopMenu;
    public final RelativeLayout title;
    public final TextView tvDetailFilter;

    static {
        sViewsWithIds.put(2131624015, 1);
        sViewsWithIds.put(2131624295, 2);
        sViewsWithIds.put(2131625553, 3);
        sViewsWithIds.put(2131625554, 4);
        sViewsWithIds.put(2131625555, 5);
        sViewsWithIds.put(2131624240, 6);
        sViewsWithIds.put(2131625556, 7);
    }

    public WalletDetailActivityBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.divider = (View) bindings[6];
        this.ibPopIndicate = (ImageButton) bindings[5];
        this.ivTitleLeft = (ImageView) bindings[2];
        this.lRc = (LRecyclerView) bindings[7];
        this.rlParent = (RelativeLayout) bindings[0];
        this.rlParent.setTag(null);
        this.rlTopMenu = (RelativeLayout) bindings[3];
        this.title = (RelativeLayout) bindings[1];
        this.tvDetailFilter = (TextView) bindings[4];
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

    public static WalletDetailActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static WalletDetailActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (WalletDetailActivityBinding) DataBindingUtil.inflate(inflater, 2130903430, root, attachToRoot, bindingComponent);
    }

    public static WalletDetailActivityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static WalletDetailActivityBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903430, null, false), bindingComponent);
    }

    public static WalletDetailActivityBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static WalletDetailActivityBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/wallet_detail_activity_0".equals(view.getTag())) {
            return new WalletDetailActivityBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
