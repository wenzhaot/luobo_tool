package com.feng.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.AllprogramGroupItemBinding;
import com.feng.car.entity.hotshow.HotShowInfo;
import java.util.List;

public class ProgramGroupAdapter extends MvvmBaseAdapter<HotShowInfo, AllprogramGroupItemBinding> {
    private int mCurrentId = 0;
    private int mSelectedColor = this.mContext.getResources().getColor(R.color.color_ffffff);
    private int mUnSelectedColor = this.mContext.getResources().getColor(R.color.color_f3f3f3);

    public void setCurrentId(int id) {
        this.mCurrentId = id;
    }

    public ProgramGroupAdapter(Context context, List<HotShowInfo> list) {
        super(context, list);
    }

    public AllprogramGroupItemBinding getBinding(ViewGroup parent, int viewType) {
        return AllprogramGroupItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void dataBindingTo(AllprogramGroupItemBinding allprogramGroupItemBinding, HotShowInfo hotShowInfo) {
    }

    public void onBaseBindViewHolder(MvvmViewHolder<AllprogramGroupItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        HotShowInfo info = (HotShowInfo) this.mList.get(position);
        ((AllprogramGroupItemBinding) holder.binding).name.setText(info.name);
        if (position == this.mList.size() - 1) {
            ((AllprogramGroupItemBinding) holder.binding).divier.setVisibility(8);
        } else {
            ((AllprogramGroupItemBinding) holder.binding).divier.setVisibility(0);
        }
        if (info.id == this.mCurrentId) {
            ((AllprogramGroupItemBinding) holder.binding).parent.setBackgroundColor(this.mSelectedColor);
        } else {
            ((AllprogramGroupItemBinding) holder.binding).parent.setBackgroundColor(this.mUnSelectedColor);
        }
    }
}
