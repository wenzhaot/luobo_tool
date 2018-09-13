package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.feng.car.databinding.PhotoTextListItemBinding;
import com.feng.car.entity.car.CarBrandInfo;
import java.util.List;

public class PhotoTextListAdapter extends MvvmBaseAdapter<CarBrandInfo, PhotoTextListItemBinding> {
    public PhotoTextListAdapter(Context context, List<CarBrandInfo> list) {
        super(context, list);
    }

    public PhotoTextListItemBinding getBinding(ViewGroup parent, int viewType) {
        return PhotoTextListItemBinding.inflate(LayoutInflater.from(this.mContext));
    }

    public void dataBindingTo(PhotoTextListItemBinding photoTextListItemBinding, CarBrandInfo carBrandInfo) {
        photoTextListItemBinding.setCarBrandInfo(carBrandInfo);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<PhotoTextListItemBinding> holder, int position) {
        ((PhotoTextListItemBinding) holder.binding).brandImage.setAutoImageURI(Uri.parse(((CarBrandInfo) this.mList.get(position)).image.url));
    }
}
