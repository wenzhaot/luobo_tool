package com.feng.car.adapter;

import android.content.Context;
import android.view.ViewGroup;
import com.feng.car.databinding.GoodsItemListLayoutBinding;
import java.util.List;

public class GoodsServeItemAdater extends MvvmBaseAdapter<String, GoodsItemListLayoutBinding> {
    public GoodsServeItemAdater(Context context, List<String> list) {
        super(context, list);
    }

    public GoodsItemListLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return GoodsItemListLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<GoodsItemListLayoutBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
    }

    public void dataBindingTo(GoodsItemListLayoutBinding layoutBinding, String str) {
        layoutBinding.setStr(str);
    }
}
