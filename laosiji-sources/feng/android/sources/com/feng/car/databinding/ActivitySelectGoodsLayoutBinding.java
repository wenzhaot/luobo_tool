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
import com.feng.car.view.recyclerview.EmptyView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ActivitySelectGoodsLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView back;
    public final EmptyView goodsEmpty;
    public final LinearLayout llGoods;
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;
    public final LRecyclerView recyclerview;
    public final RelativeLayout rlTitleBar;
    public final TextView tvCreatGoods;
    public final TextView tvFinish;
    public final TextView tvTitleName;

    static {
        sViewsWithIds.put(2131624280, 1);
        sViewsWithIds.put(2131624596, 2);
        sViewsWithIds.put(2131624595, 3);
        sViewsWithIds.put(2131624597, 4);
        sViewsWithIds.put(2131624215, 5);
        sViewsWithIds.put(2131624216, 6);
        sViewsWithIds.put(2131624217, 7);
        sViewsWithIds.put(2131624218, 8);
    }

    public ActivitySelectGoodsLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.back = (TextView) bindings[6];
        this.goodsEmpty = (EmptyView) bindings[2];
        this.llGoods = (LinearLayout) bindings[3];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.recyclerview = (LRecyclerView) bindings[1];
        this.rlTitleBar = (RelativeLayout) bindings[5];
        this.tvCreatGoods = (TextView) bindings[4];
        this.tvFinish = (TextView) bindings[8];
        this.tvTitleName = (TextView) bindings[7];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1;
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
        return false;
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

    public static ActivitySelectGoodsLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySelectGoodsLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivitySelectGoodsLayoutBinding) DataBindingUtil.inflate(inflater, 2130903130, root, attachToRoot, bindingComponent);
    }

    public static ActivitySelectGoodsLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySelectGoodsLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903130, null, false), bindingComponent);
    }

    public static ActivitySelectGoodsLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySelectGoodsLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_select_goods_layout_0".equals(view.getTag())) {
            return new ActivitySelectGoodsLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
