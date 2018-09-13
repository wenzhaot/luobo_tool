package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.FollowedCircleItemBinding;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.utils.FengUtil;
import java.util.List;

public class MyCircleAdapter extends MvvmBaseAdapter<CircleInfo, FollowedCircleItemBinding> {
    private int m140 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_140PX);

    public MyCircleAdapter(Context context, List<CircleInfo> list) {
        super(context, list);
    }

    public FollowedCircleItemBinding getBinding(ViewGroup parent, int viewType) {
        return FollowedCircleItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void dataBindingTo(FollowedCircleItemBinding followedCircleItemBinding, CircleInfo circleInfo) {
    }

    public void onBaseBindViewHolder(MvvmViewHolder<FollowedCircleItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        if (position == 0) {
            ((FollowedCircleItemBinding) holder.binding).vLine.setVisibility(0);
            ((FollowedCircleItemBinding) holder.binding).backImage.setImageResource(R.drawable.icon_add_circle);
            ((FollowedCircleItemBinding) holder.binding).circleImage.setVisibility(8);
            ((FollowedCircleItemBinding) holder.binding).markImage.setVisibility(8);
            ((FollowedCircleItemBinding) holder.binding).name.setText(this.mContext.getResources().getString(R.string.find_circle));
            return;
        }
        ((FollowedCircleItemBinding) holder.binding).vLine.setVisibility(8);
        ((FollowedCircleItemBinding) holder.binding).markImage.setVisibility(0);
        ((FollowedCircleItemBinding) holder.binding).circleImage.setVisibility(0);
        CircleInfo circleInfo = (CircleInfo) this.mList.get(position);
        ((FollowedCircleItemBinding) holder.binding).name.setText(circleInfo.name);
        ((FollowedCircleItemBinding) holder.binding).circleImage.setAutoImageURI(Uri.parse(FengUtil.getFixedCircleUrl(circleInfo.image, this.m140, this.m140)));
        if (circleInfo.redpoint.get() == 0) {
            ((FollowedCircleItemBinding) holder.binding).backImage.setImageResource(R.drawable.icon_circle_hasread);
        } else {
            ((FollowedCircleItemBinding) holder.binding).backImage.setImageResource(R.drawable.icon_circle_unread);
        }
    }
}
