package com.feng.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.activity.BaseActivity;
import com.feng.car.databinding.ItemBlackListBinding;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.BlackUtil$BlackExcuteCallBack;
import com.feng.car.utils.FengUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class BlackListInnerAdapter extends MvvmBaseAdapter<UserInfo, ItemBlackListBinding> {
    public BlackListInnerAdapter(Context context, List<UserInfo> list) {
        super(context, list);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ItemBlackListBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        final UserInfo info = (UserInfo) this.mList.get(position);
        ((ItemBlackListBinding) holder.binding).afdBlackHead.setHeadUrl(FengUtil.getHeadImageUrl(info.getHeadImageInfo()));
        ((ItemBlackListBinding) holder.binding).rrBlackListItem.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (info != null) {
                    info.intentToPersonalHome(BlackListInnerAdapter.this.mContext);
                }
            }
        });
        ((ItemBlackListBinding) holder.binding).blackText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                info.blackOperation(BlackListInnerAdapter.this.mContext, new BlackUtil$BlackExcuteCallBack() {
                    public void onAddBlackSuccess() {
                        ((BaseActivity) BlackListInnerAdapter.this.mContext).showFirstTypeToast((int) R.string.add_black);
                    }

                    public void onRemoveBlackSuccess() {
                        ((BaseActivity) BlackListInnerAdapter.this.mContext).showFirstTypeToast((int) R.string.remove_black);
                    }
                });
            }
        });
    }

    public ItemBlackListBinding getBinding(ViewGroup parent, int viewType) {
        return ItemBlackListBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(ItemBlackListBinding itemBlackListBinding, UserInfo userInfo) {
        itemBlackListBinding.setUserInfo(userInfo);
    }
}
