package com.feng.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.activity.OldDriverChooseCarNextActivity;
import com.feng.car.databinding.OldDriverChooseCarItemBinding;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.view.VoiceBoxView;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class OldDriverChooseCarAdapter extends MvvmBaseAdapter<SnsInfo, OldDriverChooseCarItemBinding> {
    public OldDriverChooseCarAdapter(Context context, List<SnsInfo> list) {
        super(context, list);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<OldDriverChooseCarItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        if (position == getItemCount() - 1) {
            ((OldDriverChooseCarItemBinding) holder.binding).footDivider.setVisibility(0);
        } else {
            ((OldDriverChooseCarItemBinding) holder.binding).footDivider.setVisibility(8);
        }
        SnsInfo info = (SnsInfo) this.mList.get(position);
        if (info.discussinfo.localtype == 1) {
            ((OldDriverChooseCarItemBinding) holder.binding).divider.setVisibility(8);
            ((OldDriverChooseCarItemBinding) holder.binding).rlMoreParent.setVisibility(0);
            ((OldDriverChooseCarItemBinding) holder.binding).tvDividerTitle.setText(R.string.oldDriver_helpYou_chooseCar);
            ((OldDriverChooseCarItemBinding) holder.binding).tvMore.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    Intent intent = new Intent(OldDriverChooseCarAdapter.this.mContext, OldDriverChooseCarNextActivity.class);
                    intent.putExtra(OldDriverChooseCarNextActivity.RECOMMEND_TYPE, OldDriverChooseCarNextActivity.RECOMMEND_TYPE_CHOOSE_CAR);
                    OldDriverChooseCarAdapter.this.mContext.startActivity(intent);
                }
            });
        } else if (info.discussinfo.localtype == 2) {
            ((OldDriverChooseCarItemBinding) holder.binding).divider.setVisibility(0);
            ((OldDriverChooseCarItemBinding) holder.binding).rlMoreParent.setVisibility(0);
            ((OldDriverChooseCarItemBinding) holder.binding).tvDividerTitle.setText(R.string.oldDriver_recommended_carModel);
            ((OldDriverChooseCarItemBinding) holder.binding).tvMore.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    Intent intent = new Intent(OldDriverChooseCarAdapter.this.mContext, OldDriverChooseCarNextActivity.class);
                    intent.putExtra(OldDriverChooseCarNextActivity.RECOMMEND_TYPE, OldDriverChooseCarNextActivity.RECOMMEND_TYPE_RECOMMEND_CAR);
                    OldDriverChooseCarAdapter.this.mContext.startActivity(intent);
                }
            });
        } else {
            ((OldDriverChooseCarItemBinding) holder.binding).rlMoreParent.setVisibility(8);
            ((OldDriverChooseCarItemBinding) holder.binding).divider.setVisibility(8);
        }
        ((OldDriverChooseCarItemBinding) holder.binding).vbvChooseCarItem.initVoiceBox(info, VoiceBoxView.VBV_SHOW_AUTHOR);
    }

    public OldDriverChooseCarItemBinding getBinding(ViewGroup parent, int viewType) {
        return OldDriverChooseCarItemBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(OldDriverChooseCarItemBinding oldDriverChooseCarItemBinding, SnsInfo snsInfo) {
    }
}
