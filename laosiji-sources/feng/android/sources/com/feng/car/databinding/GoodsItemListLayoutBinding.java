package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GoodsItemListLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private String mStr;
    private final RelativeLayout mboundView0;
    public final TextView tvDot;
    public final TextView tvText;
    public final View vLine;

    static {
        sViewsWithIds.put(2131625051, 2);
        sViewsWithIds.put(2131624473, 3);
    }

    public GoodsItemListLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvDot = (TextView) bindings[2];
        this.tvText = (TextView) bindings[1];
        this.tvText.setTag(null);
        this.vLine = (View) bindings[3];
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
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(65);
        super.requestRebind();
    }

    public String getStr() {
        return this.mStr;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        String str = this.mStr;
        if ((dirtyFlags & 3) != 0) {
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.tvText, str);
        }
    }

    public static GoodsItemListLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static GoodsItemListLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (GoodsItemListLayoutBinding) DataBindingUtil.inflate(inflater, 2130903251, root, attachToRoot, bindingComponent);
    }

    public static GoodsItemListLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static GoodsItemListLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903251, null, false), bindingComponent);
    }

    public static GoodsItemListLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static GoodsItemListLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/goods_item_list_layout_0".equals(view.getTag())) {
            return new GoodsItemListLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
