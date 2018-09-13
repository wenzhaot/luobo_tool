package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.AutoFrescoDraweeView;

public class CommodityItemLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView ivImage;
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;
    public final TextView tvBrowseNum;
    public final TextView tvCommodityName;
    public final TextView tvCurrentPrice;
    public final TextView tvGuidePrice;
    public final View vLeft;

    static {
        sViewsWithIds.put(2131624949, 1);
        sViewsWithIds.put(2131624438, 2);
        sViewsWithIds.put(2131624950, 3);
        sViewsWithIds.put(2131624951, 4);
        sViewsWithIds.put(2131624952, 5);
        sViewsWithIds.put(2131624953, 6);
    }

    public CommodityItemLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.ivImage = (AutoFrescoDraweeView) bindings[2];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvBrowseNum = (TextView) bindings[6];
        this.tvCommodityName = (TextView) bindings[3];
        this.tvCurrentPrice = (TextView) bindings[4];
        this.tvGuidePrice = (TextView) bindings[5];
        this.vLeft = (View) bindings[1];
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

    public static CommodityItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CommodityItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CommodityItemLayoutBinding) DataBindingUtil.inflate(inflater, 2130903199, root, attachToRoot, bindingComponent);
    }

    public static CommodityItemLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CommodityItemLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903199, null, false), bindingComponent);
    }

    public static CommodityItemLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CommodityItemLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/commodity_item_layout_0".equals(view.getTag())) {
            return new CommodityItemLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
