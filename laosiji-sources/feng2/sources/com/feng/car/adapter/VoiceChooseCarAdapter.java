package com.feng.car.adapter;

import android.content.Context;
import android.view.ViewGroup;
import com.feng.car.databinding.VoiceChooseCarItemBinding;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.view.VoiceBoxView;
import java.util.List;

public class VoiceChooseCarAdapter extends MvvmBaseAdapter<SnsInfo, VoiceChooseCarItemBinding> {
    public VoiceChooseCarAdapter(Context context, List<SnsInfo> list) {
        super(context, list);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<VoiceChooseCarItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        ((VoiceChooseCarItemBinding) holder.binding).vbvChooseCarItem.initVoiceBox((SnsInfo) this.mList.get(position), VoiceBoxView.VBV_SHOW_AUTHOR);
        if (position == getItemCount() - 1) {
            ((VoiceChooseCarItemBinding) holder.binding).footDivider.setVisibility(0);
        } else {
            ((VoiceChooseCarItemBinding) holder.binding).footDivider.setVisibility(8);
        }
    }

    public VoiceChooseCarItemBinding getBinding(ViewGroup parent, int viewType) {
        return VoiceChooseCarItemBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(VoiceChooseCarItemBinding voiceChooseCarItemBinding, SnsInfo snsInfo) {
    }
}
