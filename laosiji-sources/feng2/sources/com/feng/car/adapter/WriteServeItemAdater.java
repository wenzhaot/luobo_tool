package com.feng.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.WriteServeItemLayoutBinding;
import com.feng.car.entity.dealer.WriteServeItem;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class WriteServeItemAdater extends MvvmBaseAdapter<WriteServeItem, WriteServeItemLayoutBinding> {
    private OnAddItemListener mListener;

    public interface OnAddItemListener {
        void onAdd(int i);

        void onDelete(WriteServeItem writeServeItem);
    }

    public WriteServeItemAdater(Context context, List<WriteServeItem> list, OnAddItemListener listener) {
        super(context, list);
        this.mListener = listener;
    }

    public WriteServeItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return WriteServeItemLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<WriteServeItemLayoutBinding> holder, final int position) {
        super.onBaseBindViewHolder(holder, position);
        final WriteServeItem item = (WriteServeItem) this.mList.get(position);
        ((WriteServeItemLayoutBinding) holder.binding).tvTextTip.setVisibility(8);
        ((WriteServeItemLayoutBinding) holder.binding).etItem.setVisibility(8);
        ((WriteServeItemLayoutBinding) holder.binding).llAdd.setVisibility(8);
        ((WriteServeItemLayoutBinding) holder.binding).rlPrice.setVisibility(8);
        ((WriteServeItemLayoutBinding) holder.binding).delete.setVisibility(8);
        if (item.type == 0) {
            ((WriteServeItemLayoutBinding) holder.binding).etItem.setVisibility(0);
            if (position == 0) {
                ((WriteServeItemLayoutBinding) holder.binding).etItem.setHint("项目1...");
                ((WriteServeItemLayoutBinding) holder.binding).tvTextTip.setVisibility(0);
                ((WriteServeItemLayoutBinding) holder.binding).tvTextTip.setText(R.string.please_write_serve_item);
                return;
            }
            ((WriteServeItemLayoutBinding) holder.binding).etItem.setHint("项目" + (position + 1) + "(选填)");
            ((WriteServeItemLayoutBinding) holder.binding).delete.setVisibility(0);
            ((WriteServeItemLayoutBinding) holder.binding).delete.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    WriteServeItemAdater.this.mListener.onDelete(item);
                }
            });
        } else if (item.type == 1) {
            ((WriteServeItemLayoutBinding) holder.binding).llAdd.setVisibility(0);
            ((WriteServeItemLayoutBinding) holder.binding).llAdd.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (WriteServeItemAdater.this.mListener != null) {
                        WriteServeItemAdater.this.mListener.onAdd(position);
                    }
                }
            });
        } else if (item.type == 2) {
            ((WriteServeItemLayoutBinding) holder.binding).rlPrice.setVisibility(0);
            ((WriteServeItemLayoutBinding) holder.binding).tvTextTip.setVisibility(0);
            ((WriteServeItemLayoutBinding) holder.binding).tvTextTip.setText(R.string.write_serve_item_price);
        }
    }

    public void dataBindingTo(WriteServeItemLayoutBinding layoutBinding, WriteServeItem item) {
        layoutBinding.setItem(item);
    }
}
