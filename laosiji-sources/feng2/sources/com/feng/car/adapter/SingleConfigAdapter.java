package com.feng.car.adapter;

import android.content.Context;
import android.view.ViewGroup;
import com.feng.car.databinding.SingleConfigItemLayoutBinding;
import com.feng.car.entity.car.carconfig.CarConfigureInfo;
import java.util.List;

public class SingleConfigAdapter extends MvvmBaseAdapter<CarConfigureInfo, SingleConfigItemLayoutBinding> {
    private List<String> mRedcolsList;

    public SingleConfigAdapter(Context mContext, List<CarConfigureInfo> list, List<String> redcolsList) {
        super(mContext, list);
        this.mRedcolsList = redcolsList;
    }

    public SingleConfigItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return SingleConfigItemLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(SingleConfigItemLayoutBinding itemLayoutBinding, CarConfigureInfo info) {
    }

    public void onBaseBindViewHolder(MvvmViewHolder<SingleConfigItemLayoutBinding> holder, int position) {
        CarConfigureInfo info = (CarConfigureInfo) this.mList.get(position);
        if (info.local_type == 0) {
            ((SingleConfigItemLayoutBinding) holder.binding).rlItemSub.setVisibility(0);
            ((SingleConfigItemLayoutBinding) holder.binding).tvCarxName.setVisibility(8);
            ((SingleConfigItemLayoutBinding) holder.binding).tvTitle.setVisibility(8);
            if (this.mRedcolsList.contains(info.sub)) {
                ((SingleConfigItemLayoutBinding) holder.binding).tvValue.setSelected(true);
            } else {
                ((SingleConfigItemLayoutBinding) holder.binding).tvValue.setSelected(false);
            }
            if (info.sub.equals("车主价格(元)")) {
                ((SingleConfigItemLayoutBinding) holder.binding).tvSub.setText("成交价");
            } else {
                ((SingleConfigItemLayoutBinding) holder.binding).tvSub.setText(info.sub);
            }
            ((SingleConfigItemLayoutBinding) holder.binding).tvValue.setText(info.value);
            ((SingleConfigItemLayoutBinding) holder.binding).llParent.setTag(null);
        } else if (info.local_type == 1) {
            ((SingleConfigItemLayoutBinding) holder.binding).rlItemSub.setVisibility(8);
            ((SingleConfigItemLayoutBinding) holder.binding).tvCarxName.setVisibility(8);
            ((SingleConfigItemLayoutBinding) holder.binding).tvTitle.setVisibility(0);
            ((SingleConfigItemLayoutBinding) holder.binding).tvTitle.setText(info.item);
            ((SingleConfigItemLayoutBinding) holder.binding).llParent.setTag(info.item);
        } else if (info.local_type == 2) {
            ((SingleConfigItemLayoutBinding) holder.binding).rlItemSub.setVisibility(8);
            ((SingleConfigItemLayoutBinding) holder.binding).tvCarxName.setVisibility(0);
            ((SingleConfigItemLayoutBinding) holder.binding).tvTitle.setVisibility(8);
            ((SingleConfigItemLayoutBinding) holder.binding).tvCarxName.setText(info.item);
            ((SingleConfigItemLayoutBinding) holder.binding).llParent.setTag(null);
        }
    }
}
