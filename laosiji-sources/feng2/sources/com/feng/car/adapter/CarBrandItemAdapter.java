package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.ItemCarBrandBinding;
import com.feng.car.entity.car.CarBrandInfo;
import java.util.List;

public class CarBrandItemAdapter extends MvvmBaseAdapter<CarBrandInfo, ItemCarBrandBinding> {
    private int mBrandId = 0;

    public void setmBrandId(int id) {
        this.mBrandId = id;
    }

    public CarBrandItemAdapter(Context mContext, List<CarBrandInfo> mDatas) {
        super(mContext, mDatas);
    }

    public ItemCarBrandBinding getBinding(ViewGroup parent, int viewType) {
        return ItemCarBrandBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(ItemCarBrandBinding itemCarBrandBinding, CarBrandInfo info) {
        itemCarBrandBinding.setCarBrandInfo(info);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ItemCarBrandBinding> holder, int position) {
        CarBrandInfo carBrandInfo = (CarBrandInfo) this.mList.get(position);
        ((ItemCarBrandBinding) holder.binding).afdCarBrandImage.setAutoImageURI(Uri.parse(carBrandInfo.image.url));
        ((ItemCarBrandBinding) holder.binding).afdCarBrandImage.setVisibility(0);
        if (carBrandInfo.posflag == 1) {
            ((ItemCarBrandBinding) holder.binding).vLine.setVisibility(8);
        } else {
            ((ItemCarBrandBinding) holder.binding).vLine.setVisibility(0);
        }
        if (carBrandInfo.id == this.mBrandId) {
            ((ItemCarBrandBinding) holder.binding).tvCarBrand.setTextColor(this.mContext.getResources().getColor(R.color.color_ffb90a));
        } else {
            ((ItemCarBrandBinding) holder.binding).tvCarBrand.setTextColor(this.mContext.getResources().getColor(R.color.color_87_000000));
        }
    }
}
