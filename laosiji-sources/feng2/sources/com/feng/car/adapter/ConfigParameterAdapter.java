package com.feng.car.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.ConfigParameterItemLayoutBinding;
import com.feng.car.entity.car.carconfig.ConfigItem;
import java.util.List;

public class ConfigParameterAdapter extends MvvmBaseAdapter<ConfigItem, ConfigParameterItemLayoutBinding> {
    private String mConfigName = "";

    public ConfigParameterAdapter(Context mContext, List<ConfigItem> list) {
        super(mContext, list);
    }

    public void setConfigName(String configName) {
        this.mConfigName = configName;
    }

    public ConfigParameterItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return ConfigParameterItemLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(ConfigParameterItemLayoutBinding itemLayoutBinding, ConfigItem info) {
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ConfigParameterItemLayoutBinding> holder, int position) {
        ConfigItem item = (ConfigItem) this.mList.get(position);
        ((ConfigParameterItemLayoutBinding) holder.binding).tvParameterName.setText(item.name);
        if (this.mConfigName.equals(item.name)) {
            ((ConfigParameterItemLayoutBinding) holder.binding).tvParameterName.setSelected(true);
            ((ConfigParameterItemLayoutBinding) holder.binding).tvParameterName.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this.mContext, R.drawable.icon_confirm), null);
            return;
        }
        ((ConfigParameterItemLayoutBinding) holder.binding).tvParameterName.setCompoundDrawables(null, null, null, null);
        ((ConfigParameterItemLayoutBinding) holder.binding).tvParameterName.setSelected(false);
    }
}
