package com.feng.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.MessageLetterItemBinding;
import com.feng.car.entity.privatemsg.MessageInfo;
import com.feng.car.utils.FengUtil;
import com.feng.library.emoticons.keyboard.SimpleCommonUtils;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class PrivateLetterAdapter extends MvvmBaseAdapter<MessageInfo, MessageLetterItemBinding> {
    private int m84 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_84PX);

    public PrivateLetterAdapter(Context context, List<MessageInfo> list) {
        super(context, list);
    }

    public MessageLetterItemBinding getBinding(ViewGroup parent, int viewType) {
        return MessageLetterItemBinding.inflate(LayoutInflater.from(this.mContext));
    }

    public void dataBindingTo(MessageLetterItemBinding messageLetterItemBinding, MessageInfo messageInfo) {
        messageLetterItemBinding.setUserInfo(messageInfo.user);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<MessageLetterItemBinding> holder, int position) {
        final MessageInfo item = (MessageInfo) this.mList.get(position);
        if (item.user != null) {
            ((MessageLetterItemBinding) holder.binding).fdvLetterUserPortrait.setHeadUrl(FengUtil.getHeadImageUrl(item.user.getHeadImageInfo()));
            ((MessageLetterItemBinding) holder.binding).fdvLetterUserPortrait.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    item.user.intentToPersonalHome(PrivateLetterAdapter.this.mContext);
                }
            });
            ((MessageLetterItemBinding) holder.binding).tvLetterUsername.setText((CharSequence) item.user.name.get());
        }
        ((MessageLetterItemBinding) holder.binding).tvLetterTimestamp.setText((CharSequence) item.time.get());
        ((MessageLetterItemBinding) holder.binding).tvLetterDetail.setText((CharSequence) item.content.get());
        SimpleCommonUtils.spannableEmoticonFilter(((MessageLetterItemBinding) holder.binding).tvLetterDetail, (String) item.content.get());
        if (!StringUtil.isEmpty(item.image.url) && ((String) item.content.get()).length() <= 0) {
            ((MessageLetterItemBinding) holder.binding).tvLetterDetail.setText("[图片消息]");
        }
        if (item.unreadcount.get() > 0) {
            ((MessageLetterItemBinding) holder.binding).tvLetterNum.setVisibility(0);
            if (item.unreadcount.get() < 99) {
                ((MessageLetterItemBinding) holder.binding).tvLetterNum.setText(item.unreadcount.get() + "");
            } else if (item.unreadcount.get() > 99) {
                ((MessageLetterItemBinding) holder.binding).tvLetterNum.setText("99+");
            }
        } else {
            ((MessageLetterItemBinding) holder.binding).tvLetterNum.setVisibility(8);
        }
        if (position == getItemCount() - 1) {
            ((MessageLetterItemBinding) holder.binding).ivMessageLetterItemDivider.setVisibility(8);
        } else {
            ((MessageLetterItemBinding) holder.binding).ivMessageLetterItemDivider.setVisibility(0);
        }
    }
}
