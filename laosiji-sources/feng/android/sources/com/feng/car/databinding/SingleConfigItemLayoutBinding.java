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

public class SingleConfigItemLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout llParent;
    private long mDirtyFlags = -1;
    private String mStr;
    public final RelativeLayout rlItemSub;
    public final TextView tvCarxName;
    public final TextView tvSub;
    public final TextView tvTitle;
    public final TextView tvValue;

    static {
        sViewsWithIds.put(2131625456, 1);
        sViewsWithIds.put(2131624296, 2);
        sViewsWithIds.put(2131625490, 3);
        sViewsWithIds.put(2131625491, 4);
        sViewsWithIds.put(2131625492, 5);
    }

    public SingleConfigItemLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.llParent = (LinearLayout) bindings[0];
        this.llParent.setTag(null);
        this.rlItemSub = (RelativeLayout) bindings[3];
        this.tvCarxName = (TextView) bindings[1];
        this.tvSub = (TextView) bindings[4];
        this.tvTitle = (TextView) bindings[2];
        this.tvValue = (TextView) bindings[5];
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
            case 65:
                setStr((String) variable);
                return true;
            default:
                return false;
        }
    }

    public void setStr(String Str) {
        this.mStr = Str;
    }

    public String getStr() {
        return this.mStr;
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

    public static SingleConfigItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SingleConfigItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SingleConfigItemLayoutBinding) DataBindingUtil.inflate(inflater, 2130903391, root, attachToRoot, bindingComponent);
    }

    public static SingleConfigItemLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SingleConfigItemLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903391, null, false), bindingComponent);
    }

    public static SingleConfigItemLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SingleConfigItemLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/single_config_item_layout_0".equals(view.getTag())) {
            return new SingleConfigItemLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
