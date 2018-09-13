package com.feng.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.PhotoVideoItemBinding;
import com.feng.car.entity.model.ImageVideoBucket;
import java.util.List;

public class PhotoAlbumAdapter extends MvvmBaseAdapter<ImageVideoBucket, PhotoVideoItemBinding> {
    private int m100 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_100PX);
    private int mSelectPost = 0;

    public PhotoAlbumAdapter(Context context, List<ImageVideoBucket> list) {
        super(context, list);
    }

    public void setSelectPost(int sel) {
        this.mSelectPost = sel;
        notifyDataSetChanged();
    }

    public void onBaseBindViewHolder(MvvmViewHolder<PhotoVideoItemBinding> holder, int position) {
        ImageVideoBucket bucket = (ImageVideoBucket) this.mList.get(position);
        ((PhotoVideoItemBinding) holder.binding).tvName.setText(bucket.bucketName);
        ((PhotoVideoItemBinding) holder.binding).tvNum.setText("(" + bucket.count + ")");
        ((PhotoVideoItemBinding) holder.binding).ivCover.setDraweeImage("file://" + bucket.list.get(0).url, this.m100, this.m100);
        if (this.mSelectPost == position) {
            ((PhotoVideoItemBinding) holder.binding).rlParent.setSelected(true);
        } else {
            ((PhotoVideoItemBinding) holder.binding).rlParent.setSelected(false);
        }
        if (bucket.selCount > 0) {
            ((PhotoVideoItemBinding) holder.binding).ivSelect.setVisibility(0);
        } else {
            ((PhotoVideoItemBinding) holder.binding).ivSelect.setVisibility(8);
        }
    }

    public PhotoVideoItemBinding getBinding(ViewGroup parent, int viewType) {
        return PhotoVideoItemBinding.inflate(LayoutInflater.from(this.mContext));
    }

    public void dataBindingTo(PhotoVideoItemBinding photoVideoItemBinding, ImageVideoBucket imageBucket) {
    }
}
