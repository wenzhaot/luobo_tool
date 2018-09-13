package com.feng.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.activity.AllProgramActivity;
import com.feng.car.activity.PopularProgramListActivity;
import com.feng.car.databinding.ItemLavelLayoutBinding;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.utils.FengUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class HomeLavelHeadAdater extends MvvmBaseAdapter<HotShowInfo, ItemLavelLayoutBinding> {
    private int mCategoryID = 0;
    private int mCount = 0;
    private int mHeight = 0;

    public void setCount(int count, int categoryID) {
        this.mCount = count;
        this.mCategoryID = categoryID;
        this.mHeight = (FengUtil.getScreenWidth(this.mContext) - this.mContext.getResources().getDimensionPixelSize(R.dimen.default_100PX)) / 5;
    }

    public HomeLavelHeadAdater(Context context, List<HotShowInfo> list) {
        super(context, list);
    }

    public ItemLavelLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return ItemLavelLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ItemLavelLayoutBinding> holder, final int position) {
        super.onBaseBindViewHolder(holder, position);
        ((ItemLavelLayoutBinding) holder.binding).adProgram.getLayoutParams().height = this.mHeight;
        if (this.mCount <= 5 || position != 4) {
            ((ItemLavelLayoutBinding) holder.binding).adProgram.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(((HotShowInfo) this.mList.get(position)).image, 350, 1.0f)));
            ((ItemLavelLayoutBinding) holder.binding).adProgram.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    Intent intent = new Intent(HomeLavelHeadAdater.this.mContext, PopularProgramListActivity.class);
                    intent.putExtra("hotshowid", ((HotShowInfo) HomeLavelHeadAdater.this.mList.get(position)).id);
                    HomeLavelHeadAdater.this.mContext.startActivity(intent);
                }
            });
            return;
        }
        ((ItemLavelLayoutBinding) holder.binding).adProgram.setAutoImageURI(Uri.parse("res://com.feng.car/2130838187"));
        ((ItemLavelLayoutBinding) holder.binding).adProgram.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(HomeLavelHeadAdater.this.mContext, AllProgramActivity.class);
                intent.putExtra("id", HomeLavelHeadAdater.this.mCategoryID);
                HomeLavelHeadAdater.this.mContext.startActivity(intent);
            }
        });
    }

    public void dataBindingTo(ItemLavelLayoutBinding layoutBinding, HotShowInfo info) {
    }
}
