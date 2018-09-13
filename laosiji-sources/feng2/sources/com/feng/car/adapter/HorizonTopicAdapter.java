package com.feng.car.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.HorizonTopicItemBinding;
import com.feng.car.entity.circle.CircleInfo;
import java.util.List;

public class HorizonTopicAdapter extends MvvmBaseAdapter<CircleInfo, HorizonTopicItemBinding> {
    public static int TYPE_HISTORY = 1002;
    public static int TYPE_SELECTED = 1001;
    private int mType;

    public HorizonTopicAdapter(Context context, List<CircleInfo> list, int type) {
        super(context, list);
        this.mType = type;
    }

    public HorizonTopicItemBinding getBinding(ViewGroup parent, int viewType) {
        return HorizonTopicItemBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(HorizonTopicItemBinding itemBinding, CircleInfo info) {
        itemBinding.setInfo(info);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<HorizonTopicItemBinding> holder, int position) {
        CircleInfo info = (CircleInfo) this.mList.get(position);
        if (position == 0) {
            ((HorizonTopicItemBinding) holder.binding).vLeft.setVisibility(0);
        } else {
            ((HorizonTopicItemBinding) holder.binding).vLeft.setVisibility(8);
        }
        if (this.mType == TYPE_SELECTED) {
            ((HorizonTopicItemBinding) holder.binding).tvTopicName.setBackgroundResource(0);
            ((HorizonTopicItemBinding) holder.binding).tvTopicName.setPadding(0, 0, 0, 0);
            ((HorizonTopicItemBinding) holder.binding).tvTopicName.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_fb6c06));
            ((HorizonTopicItemBinding) holder.binding).tvTopicName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.topic_orange, 0, 0, 0);
        } else if (info.islocalselect.get()) {
            ((HorizonTopicItemBinding) holder.binding).tvTopicName.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_fb6c06));
            ((HorizonTopicItemBinding) holder.binding).tvTopicName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.topic_orange, 0, 0, 0);
        } else {
            ((HorizonTopicItemBinding) holder.binding).tvTopicName.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_87_000000));
            ((HorizonTopicItemBinding) holder.binding).tvTopicName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.topic_gray, 0, 0, 0);
        }
    }
}
