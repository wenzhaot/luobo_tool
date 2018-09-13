package com.feng.car.adapter;

import android.content.Context;
import android.view.ViewGroup;
import com.feng.car.activity.SearchNewActivity;
import com.feng.car.databinding.ItemFansBinding;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.entity.user.UserInfoList;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.utils.FengUtil;

public class FansAdapter extends MvvmBaseAdapter<UserInfo, ItemFansBinding> {
    private UserInfoList mDataList;
    private int mType = 0;

    public FansAdapter(Context context, UserInfoList list) {
        super(context, list.getUserInfoList());
        this.mDataList = list;
        if (this.mContext instanceof SearchNewActivity) {
            this.mType = 1;
        }
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ItemFansBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        UserInfo user = (UserInfo) this.mList.get(position);
        ((ItemFansBinding) holder.binding).urvFans.setUserInfo(user, 1);
        ((ItemFansBinding) holder.binding).afdHead.setHeadUrl(FengUtil.getHeadImageUrl(user.getHeadImageInfo()));
        ((ItemFansBinding) holder.binding).tvUserName.setText((CharSequence) user.name.get());
    }

    public ItemFansBinding getBinding(ViewGroup parent, int viewType) {
        return ItemFansBinding.inflate(this.mInflater);
    }

    public void dataBindingTo(ItemFansBinding itemFansBinding, UserInfo userInfo) {
        itemFansBinding.setUserInfo(userInfo);
        itemFansBinding.setNType(Integer.valueOf(this.mType));
    }

    public void refreshUserFollowState(UserInfoRefreshEvent event) {
        if (event != null && event.type == 2 && this.mDataList.size() > 0 && event.userInfo != null) {
            int pos = this.mDataList.getPosition(event.userInfo.id);
            if (pos >= 0) {
                UserInfo user = this.mDataList.get(pos);
                if (user.id == event.userInfo.id) {
                    user.isfollow.set(event.userInfo.isfollow.get());
                    user.isblack.set(event.userInfo.isblack.get());
                    notifyItemChanged(pos);
                }
            }
        }
    }
}
