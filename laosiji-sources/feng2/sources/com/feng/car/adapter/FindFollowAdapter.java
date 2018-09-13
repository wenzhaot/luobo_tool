package com.feng.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.feng.car.databinding.ItemFindFollowBinding;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.entity.user.UserInfoList;
import com.feng.car.utils.FengUtil;
import java.util.ArrayList;
import java.util.List;

public class FindFollowAdapter extends MvvmBaseAdapter<UserInfo, ItemFindFollowBinding> {
    public OnSelChangeListener mListener;
    private List<Integer> userIDList = new ArrayList();

    public interface OnSelChangeListener {
        void onSelChange(int i);
    }

    public FindFollowAdapter(Context context, UserInfoList list, OnSelChangeListener listener) {
        super(context, list.getUserInfoList());
        this.mListener = listener;
        setUserIDList();
    }

    public void setUserIDList() {
        this.userIDList.clear();
        for (UserInfo info : this.mList) {
            if (!this.userIDList.contains(Integer.valueOf(info.id))) {
                this.userIDList.add(Integer.valueOf(info.id));
            }
        }
    }

    public void onBaseBindViewHolder(final MvvmViewHolder<ItemFindFollowBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        final UserInfo user = (UserInfo) this.mList.get(position);
        ((ItemFindFollowBinding) holder.binding).ivFindFollowSelect.setSelected(this.userIDList.contains(Integer.valueOf(user.id)));
        ((ItemFindFollowBinding) holder.binding).rlParent.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                FindFollowAdapter.this.processSelect(user, ((ItemFindFollowBinding) holder.binding).ivFindFollowSelect);
            }
        });
        ((ItemFindFollowBinding) holder.binding).afdHead.setHeadUrl(FengUtil.getHeadImageUrl(user.getHeadImageInfo()));
    }

    public List<Integer> getUserIDList() {
        return this.userIDList;
    }

    public void clearUserList() {
        this.userIDList.clear();
    }

    private void processSelect(UserInfo user, View v) {
        if (v.isSelected()) {
            this.userIDList.remove(Integer.valueOf(user.id));
        } else if (!this.userIDList.contains(Integer.valueOf(user.id))) {
            this.userIDList.add(Integer.valueOf(user.id));
        }
        v.setSelected(this.userIDList.contains(Integer.valueOf(user.id)));
        this.mListener.onSelChange(this.userIDList.size());
    }

    public ItemFindFollowBinding getBinding(ViewGroup parent, int viewType) {
        return ItemFindFollowBinding.inflate(this.mInflater);
    }

    public void dataBindingTo(ItemFindFollowBinding itemFansBinding, UserInfo userInfo) {
        itemFansBinding.setUserInfo(userInfo);
    }
}
