package com.feng.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.databinding.ConfigClassifyItemLayoutBinding;
import com.feng.car.entity.car.carconfig.ConfigItem;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class ConfigClassifyAdapter extends MvvmBaseAdapter<ConfigItem, ConfigClassifyItemLayoutBinding> {
    private ItemSelectListener mItemSelectListener;

    public interface ItemSelectListener {
        void onItemClick(int i);
    }

    public ConfigClassifyAdapter(Context context, List<ConfigItem> list) {
        super(context, list);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ConfigClassifyItemLayoutBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        final ConfigItem item = (ConfigItem) this.mList.get(position);
        ((ConfigClassifyItemLayoutBinding) holder.binding).tvClassifyName.setText(item.name);
        ((ConfigClassifyItemLayoutBinding) holder.binding).tvClassifyName.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (ConfigClassifyAdapter.this.mItemSelectListener != null) {
                    ConfigClassifyAdapter.this.mItemSelectListener.onItemClick(item.postion);
                }
            }
        });
    }

    public ConfigClassifyItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return ConfigClassifyItemLayoutBinding.inflate(LayoutInflater.from(this.mContext));
    }

    public void dataBindingTo(ConfigClassifyItemLayoutBinding itemLayoutBinding, ConfigItem bean) {
    }

    public void setItemSelectListener(ItemSelectListener l) {
        this.mItemSelectListener = l;
    }
}
