package com.feng.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.databinding.ItemHomeMenuLayoutBinding;
import com.feng.car.entity.home.HomeMenuModel;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class HomeMenuAdater extends MvvmBaseAdapter<HomeMenuModel, ItemHomeMenuLayoutBinding> {
    public HomeMenuAdater(Context context, List<HomeMenuModel> list) {
        super(context, list);
    }

    public ItemHomeMenuLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return ItemHomeMenuLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ItemHomeMenuLayoutBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        final HomeMenuModel menuModel = (HomeMenuModel) this.mList.get(position);
        ((ItemHomeMenuLayoutBinding) holder.binding).ivIcon.setImageResource(menuModel.resourcesID);
        ((ItemHomeMenuLayoutBinding) holder.binding).ivIcon.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                menuModel.handleItemClickListener(HomeMenuAdater.this.mContext);
            }
        });
    }

    public void dataBindingTo(ItemHomeMenuLayoutBinding layoutBinding, HomeMenuModel info) {
    }
}
