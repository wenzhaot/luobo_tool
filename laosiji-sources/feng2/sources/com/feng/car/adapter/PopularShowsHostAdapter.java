package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import com.feng.car.databinding.PopularShowsHostItemBinding;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.FengUtil;
import java.util.List;

public class PopularShowsHostAdapter extends MvvmBaseAdapter<UserInfo, PopularShowsHostItemBinding> {
    public PopularShowsHostAdapter(Context context, List<UserInfo> list) {
        super(context, list);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<PopularShowsHostItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        ((PopularShowsHostItemBinding) holder.binding).afdHeadImage.setAutoImageURI(Uri.parse(FengUtil.getHeadImageUrl(((UserInfo) this.mList.get(position)).image)));
    }

    public PopularShowsHostItemBinding getBinding(ViewGroup parent, int viewType) {
        return PopularShowsHostItemBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(PopularShowsHostItemBinding popularShowsHostItemBinding, UserInfo userInfo) {
        popularShowsHostItemBinding.setUserInfo(userInfo);
    }
}
