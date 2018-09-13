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
import com.feng.car.entity.dealer.CommodityInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.AutoFrescoDraweeView;

public class RelativeGoodsItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView ivImage;
    private long mDirtyFlags = -1;
    private CommodityInfo mInfo;
    private final RelativeLayout mboundView0;
    public final TextView tvCurrentPrice;
    public final TextView tvGoodsName;
    public final TextView tvGuidePrice;

    static {
        sViewsWithIds.put(2131624438, 4);
    }

    public RelativeGoodsItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.ivImage = (AutoFrescoDraweeView) bindings[4];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvCurrentPrice = (TextView) bindings[2];
        this.tvCurrentPrice.setTag(null);
        this.tvGoodsName = (TextView) bindings[1];
        this.tvGoodsName.setTag(null);
        this.tvGuidePrice = (TextView) bindings[3];
        this.tvGuidePrice.setTag(null);
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
            case 28:
                setInfo((CommodityInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(CommodityInfo Info) {
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public CommodityInfo getInfo() {
        return this.mInfo;
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
        int infoPrice = 0;
        int infoOriginalprice = 0;
        String fengUtilNumberFormatCarInfoPrice = null;
        CommodityInfo info = this.mInfo;
        String infoTitle = null;
        String fengUtilNumberFormatCarInfoOriginalprice = null;
        if ((dirtyFlags & 3) != 0) {
            if (info != null) {
                infoPrice = info.price;
                infoOriginalprice = info.originalprice;
                infoTitle = info.title;
            }
            fengUtilNumberFormatCarInfoPrice = FengUtil.numberFormatCar((double) infoPrice);
            fengUtilNumberFormatCarInfoOriginalprice = FengUtil.numberFormatCar((double) infoOriginalprice);
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.tvCurrentPrice, fengUtilNumberFormatCarInfoPrice);
            TextViewBindingAdapter.setText(this.tvGoodsName, infoTitle);
            TextViewBindingAdapter.setText(this.tvGuidePrice, fengUtilNumberFormatCarInfoOriginalprice);
        }
    }

    public static RelativeGoodsItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static RelativeGoodsItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (RelativeGoodsItemBinding) DataBindingUtil.inflate(inflater, 2130903369, root, attachToRoot, bindingComponent);
    }

    public static RelativeGoodsItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static RelativeGoodsItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903369, null, false), bindingComponent);
    }

    public static RelativeGoodsItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static RelativeGoodsItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/relative_goods_item_0".equals(view.getTag())) {
            return new RelativeGoodsItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
