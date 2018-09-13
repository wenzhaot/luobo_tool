package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.RecommendedTopicItemBinding;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.utils.FengUtil;
import java.util.List;

public class RecommendedTopicAdapter extends MvvmBaseAdapter<CircleInfo, RecommendedTopicItemBinding> {
    private boolean IS_SEARCH = false;
    private int m140;

    public RecommendedTopicAdapter(Context context, List<CircleInfo> list, boolean isSearch) {
        super(context, list);
        this.IS_SEARCH = isSearch;
        this.m140 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_140PX);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<RecommendedTopicItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        CircleInfo info = (CircleInfo) this.mList.get(position);
        ((RecommendedTopicItemBinding) holder.binding).tvFansNumContentNum.setText(info.fanscount + "人关注 " + info.snscount + "篇帖子");
        if (position == 0) {
            ((RecommendedTopicItemBinding) holder.binding).divider.setVisibility(8);
        } else {
            ((RecommendedTopicItemBinding) holder.binding).divider.setVisibility(0);
        }
        ((RecommendedTopicItemBinding) holder.binding).ivImage.setAutoImageURI(Uri.parse(FengUtil.getFixedCircleUrl(info.image, this.m140, this.m140)));
        if (this.IS_SEARCH) {
            if (position == 0) {
                ((RecommendedTopicItemBinding) holder.binding).divider1.setVisibility(8);
            } else {
                ((RecommendedTopicItemBinding) holder.binding).divider1.setVisibility(0);
            }
            if (info.islocalcreate.get()) {
                ((RecommendedTopicItemBinding) holder.binding).ivImage.setAutoImageURI(Uri.parse("res://com.feng.car/2130838537"));
                ((RecommendedTopicItemBinding) holder.binding).tvFansNumContentNum.setText("新话题 还没有人参与");
            }
        }
    }

    public RecommendedTopicItemBinding getBinding(ViewGroup parent, int viewType) {
        return RecommendedTopicItemBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(RecommendedTopicItemBinding itemBinding, CircleInfo circleInfo) {
        itemBinding.setInfo(circleInfo);
    }
}
