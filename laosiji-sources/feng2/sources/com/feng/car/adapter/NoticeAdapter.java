package com.feng.car.adapter;

import android.content.Context;
import android.view.ViewGroup;
import com.feng.car.databinding.SystemMsgItemBinding;
import com.feng.car.entity.sysmsg.SystemMessageInfo;
import java.util.List;

public class NoticeAdapter extends MvvmBaseAdapter<SystemMessageInfo, SystemMsgItemBinding> {
    public NoticeAdapter(Context con, List<SystemMessageInfo> list) {
        super(con, list);
    }

    public SystemMsgItemBinding getBinding(ViewGroup parent, int viewType) {
        return SystemMsgItemBinding.inflate(this.mInflater, parent, false);
    }

    public void onBindViewHolder(MvvmViewHolder<SystemMsgItemBinding> holder, int position) {
        super.onBindViewHolder((MvvmViewHolder) holder, position);
        ((SystemMsgItemBinding) holder.binding).tvSystemInfoDetail.setMatchLink(true);
        ((SystemMsgItemBinding) holder.binding).tvSystemInfoDetail.setContent(((SystemMessageInfo) this.mList.get(position)).data, false);
    }

    public void dataBindingTo(SystemMsgItemBinding systemMsgItemBinding, SystemMessageInfo systemMessageInfo) {
        systemMsgItemBinding.setSystemInfo(systemMessageInfo);
    }
}
