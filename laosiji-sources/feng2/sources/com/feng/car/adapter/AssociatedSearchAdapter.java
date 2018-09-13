package com.feng.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.NewAssociatedSearchItemBinding;
import com.feng.car.entity.search.AssociatedSearchInfo;
import com.feng.car.utils.FengUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class AssociatedSearchAdapter extends MvvmBaseAdapter<AssociatedSearchInfo, NewAssociatedSearchItemBinding> {
    private int mSearchIndex = 0;

    public void setSearchIndex(int searchIndex) {
        this.mSearchIndex = searchIndex;
    }

    public AssociatedSearchAdapter(Context context, List<AssociatedSearchInfo> list) {
        super(context, list);
    }

    public NewAssociatedSearchItemBinding getBinding(ViewGroup parent, int viewType) {
        return NewAssociatedSearchItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void dataBindingTo(NewAssociatedSearchItemBinding searchItemBinding, AssociatedSearchInfo searchInfo) {
        searchItemBinding.setUserInfo(searchInfo.user);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<NewAssociatedSearchItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        final AssociatedSearchInfo searchInfo = (AssociatedSearchInfo) this.mList.get(position);
        if (position == 0) {
            ((NewAssociatedSearchItemBinding) holder.binding).vLine.setVisibility(8);
        } else {
            ((NewAssociatedSearchItemBinding) holder.binding).vLine.setVisibility(0);
        }
        if (searchInfo.type == 2 || searchInfo.type == 3) {
            ((NewAssociatedSearchItemBinding) holder.binding).llUserInfo.setVisibility(8);
            ((NewAssociatedSearchItemBinding) holder.binding).rlCarNormal.setVisibility(0);
            ((NewAssociatedSearchItemBinding) holder.binding).tvGoTo.setVisibility(8);
            ((NewAssociatedSearchItemBinding) holder.binding).ivIcon.setImageResource(R.drawable.search1);
            ((NewAssociatedSearchItemBinding) holder.binding).tvContent.setText((CharSequence) searchInfo.sns.title.get());
        } else if (searchInfo.type == 1) {
            ((NewAssociatedSearchItemBinding) holder.binding).llUserInfo.setVisibility(0);
            ((NewAssociatedSearchItemBinding) holder.binding).rlCarNormal.setVisibility(8);
            ((NewAssociatedSearchItemBinding) holder.binding).afdHead.setHeadUrl(FengUtil.getHeadImageUrl(searchInfo.user.getHeadImageInfo()));
            ((NewAssociatedSearchItemBinding) holder.binding).tvFansNum.setText("粉丝：" + searchInfo.user.fanscount.get());
            ((NewAssociatedSearchItemBinding) holder.binding).afdHead.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    searchInfo.user.intentToPersonalHome(AssociatedSearchAdapter.this.mContext);
                }
            });
        } else if (searchInfo.type == 0) {
            ((NewAssociatedSearchItemBinding) holder.binding).llUserInfo.setVisibility(8);
            ((NewAssociatedSearchItemBinding) holder.binding).rlCarNormal.setVisibility(0);
            ((NewAssociatedSearchItemBinding) holder.binding).tvGoTo.setVisibility(0);
            if (this.mSearchIndex == 4) {
                ((NewAssociatedSearchItemBinding) holder.binding).ivIcon.setImageResource(R.drawable.icon_search_price);
                ((NewAssociatedSearchItemBinding) holder.binding).tvGoTo.setText(R.string.go_to_price);
            } else {
                ((NewAssociatedSearchItemBinding) holder.binding).tvGoTo.setText(R.string.go_to_channel);
                ((NewAssociatedSearchItemBinding) holder.binding).ivIcon.setImageResource(R.drawable.icon_car_search);
            }
            ((NewAssociatedSearchItemBinding) holder.binding).tvContent.setText((CharSequence) searchInfo.series.name.get());
        } else if (searchInfo.type == 4) {
            ((NewAssociatedSearchItemBinding) holder.binding).llUserInfo.setVisibility(8);
            ((NewAssociatedSearchItemBinding) holder.binding).rlCarNormal.setVisibility(0);
            ((NewAssociatedSearchItemBinding) holder.binding).tvGoTo.setVisibility(0);
            ((NewAssociatedSearchItemBinding) holder.binding).ivIcon.setImageResource(R.drawable.icon_program_search);
            ((NewAssociatedSearchItemBinding) holder.binding).tvGoTo.setText(R.string.go_to_program);
            ((NewAssociatedSearchItemBinding) holder.binding).tvContent.setText(searchInfo.hotshow.name);
        }
    }
}
