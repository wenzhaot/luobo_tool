package com.feng.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.CommonBottomDialogItemBinding;
import com.feng.car.entity.DialogItemEntity;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class CommonBottomDialogAdapter extends MvvmBaseAdapter<DialogItemEntity, CommonBottomDialogItemBinding> {
    private OnDialogItemClickListener mDialogItemClickListener;

    public interface OnDialogItemClickListener {
        void onItemClick(DialogItemEntity dialogItemEntity, int i);
    }

    public CommonBottomDialogAdapter(Context context, List<DialogItemEntity> list, OnDialogItemClickListener dialogItemClickListener) {
        super(context, list);
        this.mDialogItemClickListener = dialogItemClickListener;
    }

    public void onBaseBindViewHolder(MvvmViewHolder<CommonBottomDialogItemBinding> holder, final int position) {
        final DialogItemEntity item = (DialogItemEntity) this.mList.get(position);
        ((CommonBottomDialogItemBinding) holder.binding).tvDialogItemName.setText(item.itemContent);
        if (item.iconid != 0) {
            ((CommonBottomDialogItemBinding) holder.binding).icon.setVisibility(0);
            ((CommonBottomDialogItemBinding) holder.binding).icon.setImageResource(item.iconid);
        } else {
            ((CommonBottomDialogItemBinding) holder.binding).icon.setVisibility(8);
        }
        if (item.isHighLightShow) {
            ((CommonBottomDialogItemBinding) holder.binding).tvDialogItemName.setTextColor(this.mContext.getResources().getColor(R.color.color_d63d3d));
        } else {
            ((CommonBottomDialogItemBinding) holder.binding).tvDialogItemName.setTextColor(this.mContext.getResources().getColor(R.color.color_87_000000));
        }
        ((CommonBottomDialogItemBinding) holder.binding).llDialogItemContainer.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CommonBottomDialogAdapter.this.mDialogItemClickListener.onItemClick(item, position);
            }
        });
    }

    public CommonBottomDialogItemBinding getBinding(ViewGroup parent, int viewType) {
        return CommonBottomDialogItemBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(CommonBottomDialogItemBinding commonBottomDialogItemBinding, DialogItemEntity dialogItemEntity) {
    }
}
