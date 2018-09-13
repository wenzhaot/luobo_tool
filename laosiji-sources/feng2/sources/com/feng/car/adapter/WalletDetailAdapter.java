package com.feng.car.adapter;

import android.content.Context;
import android.view.ViewGroup;
import com.feng.car.databinding.WalletDetailItemBinding;
import com.feng.car.entity.wallet.WalletDetailInfo;
import java.util.List;

public class WalletDetailAdapter extends MvvmBaseAdapter<WalletDetailInfo, WalletDetailItemBinding> {
    public WalletDetailAdapter(Context context, List<WalletDetailInfo> list) {
        super(context, list);
    }

    public WalletDetailItemBinding getBinding(ViewGroup parent, int viewType) {
        return WalletDetailItemBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(WalletDetailItemBinding walletDetailItemBinding, WalletDetailInfo bonusDetailInfo) {
        walletDetailItemBinding.setBonusDetailInfo(bonusDetailInfo);
    }
}
