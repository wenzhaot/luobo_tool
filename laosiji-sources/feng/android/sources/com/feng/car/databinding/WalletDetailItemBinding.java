package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.ImageViewBindingAdapter;
import android.databinding.adapters.TextViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.wallet.WalletDetailInfo;

public class WalletDetailItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private WalletDetailInfo mBonusDetailInfo;
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;
    private final ImageView mboundView3;
    public final TextView tvAward;
    public final TextView tvInfo;
    public final TextView tvTime;

    public WalletDetailItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView3 = (ImageView) bindings[3];
        this.mboundView3.setTag(null);
        this.tvAward = (TextView) bindings[4];
        this.tvAward.setTag(null);
        this.tvInfo = (TextView) bindings[1];
        this.tvInfo.setTag(null);
        this.tvTime = (TextView) bindings[2];
        this.tvTime.setTag(null);
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
            case 1:
                setBonusDetailInfo((WalletDetailInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setBonusDetailInfo(WalletDetailInfo BonusDetailInfo) {
        this.mBonusDetailInfo = BonusDetailInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(1);
        super.requestRebind();
    }

    public WalletDetailInfo getBonusDetailInfo() {
        return this.mBonusDetailInfo;
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
        String bonusDetailInfoInfo = null;
        double bonusDetailInfoAward = 0.0d;
        WalletDetailInfo bonusDetailInfo = this.mBonusDetailInfo;
        String bonusDetailInfoCreatetime = null;
        Drawable bonusDetailInfoAwardInt0MboundView3AndroidDrawableLiveMoneyDetailAddMboundView3AndroidDrawableLiveMoneyDetailSub = null;
        int bonusDetailInfoAwardInt0TvAwardAndroidColorColorFffc832dTvAwardAndroidColorColorFf9b9b9b = 0;
        String bonusDetailInfoGetAwardString = null;
        if ((3 & dirtyFlags) != 0) {
            if (bonusDetailInfo != null) {
                bonusDetailInfoInfo = bonusDetailInfo.info;
                bonusDetailInfoAward = bonusDetailInfo.award;
                bonusDetailInfoCreatetime = bonusDetailInfo.createtime;
                bonusDetailInfoGetAwardString = bonusDetailInfo.getAwardString();
            }
            boolean bonusDetailInfoAwardInt0 = bonusDetailInfoAward >= 0.0d;
            if ((3 & dirtyFlags) != 0) {
                if (bonusDetailInfoAwardInt0) {
                    dirtyFlags = (dirtyFlags | 8) | 32;
                } else {
                    dirtyFlags = (dirtyFlags | 4) | 16;
                }
            }
            bonusDetailInfoAwardInt0MboundView3AndroidDrawableLiveMoneyDetailAddMboundView3AndroidDrawableLiveMoneyDetailSub = bonusDetailInfoAwardInt0 ? getDrawableFromResource(this.mboundView3, 2130838319) : getDrawableFromResource(this.mboundView3, 2130838322);
            bonusDetailInfoAwardInt0TvAwardAndroidColorColorFffc832dTvAwardAndroidColorColorFf9b9b9b = bonusDetailInfoAwardInt0 ? getColorFromResource(this.tvAward, 2131558557) : getColorFromResource(this.tvAward, 2131558547);
        }
        if ((3 & dirtyFlags) != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.mboundView3, bonusDetailInfoAwardInt0MboundView3AndroidDrawableLiveMoneyDetailAddMboundView3AndroidDrawableLiveMoneyDetailSub);
            TextViewBindingAdapter.setText(this.tvAward, bonusDetailInfoGetAwardString);
            this.tvAward.setTextColor(bonusDetailInfoAwardInt0TvAwardAndroidColorColorFffc832dTvAwardAndroidColorColorFf9b9b9b);
            TextViewBindingAdapter.setText(this.tvInfo, bonusDetailInfoInfo);
            TextViewBindingAdapter.setText(this.tvTime, bonusDetailInfoCreatetime);
        }
    }

    public static WalletDetailItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static WalletDetailItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (WalletDetailItemBinding) DataBindingUtil.inflate(inflater, 2130903431, root, attachToRoot, bindingComponent);
    }

    public static WalletDetailItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static WalletDetailItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903431, null, false), bindingComponent);
    }

    public static WalletDetailItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static WalletDetailItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/wallet_detail_item_0".equals(view.getTag())) {
            return new WalletDetailItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
