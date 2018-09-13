package com.feng.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.feng.car.databinding.PhotoTextUserListItemBinding;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.FengUtil;
import java.util.List;

public class PhotoTextUserListAdapter extends MvvmBaseAdapter<UserInfo, PhotoTextUserListItemBinding> {
    public PhotoTextUserListAdapter(Context context, List<UserInfo> list) {
        super(context, list);
    }

    public PhotoTextUserListItemBinding getBinding(ViewGroup parent, int viewType) {
        return PhotoTextUserListItemBinding.inflate(LayoutInflater.from(this.mContext));
    }

    public void dataBindingTo(PhotoTextUserListItemBinding photoTextUserListItemBinding, UserInfo userInfo) {
        photoTextUserListItemBinding.setUserInfo(userInfo);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<PhotoTextUserListItemBinding> holder, int position) {
        UserInfo item = (UserInfo) this.mList.get(position);
        ((PhotoTextUserListItemBinding) holder.binding).tvUserName.setText((CharSequence) item.name.get());
        ((PhotoTextUserListItemBinding) holder.binding).brandImage.setHeadUrl(FengUtil.getHeadImageUrl(item.getHeadImageInfo()));
    }
}
