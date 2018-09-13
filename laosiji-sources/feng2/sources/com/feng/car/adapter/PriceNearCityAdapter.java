package com.feng.car.adapter;

import android.content.Context;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.CarPriceHeaderCityItemBinding;
import com.feng.car.entity.lcoation.CityPriceInfo;
import java.util.List;

public class PriceNearCityAdapter extends MvvmBaseAdapter<CityPriceInfo, CarPriceHeaderCityItemBinding> {
    private final boolean mISModel;
    private int mSize = 0;

    public PriceNearCityAdapter(Context context, boolean isModel, List<CityPriceInfo> list) {
        super(context, list);
        this.mISModel = isModel;
    }

    public CarPriceHeaderCityItemBinding getBinding(ViewGroup parent, int viewType) {
        return CarPriceHeaderCityItemBinding.inflate(this.mInflater, null, false);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<CarPriceHeaderCityItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        this.mSize = this.mList.size();
        ((CarPriceHeaderCityItemBinding) holder.binding).divider.setVisibility(0);
        if (this.mSize % 2 == 0) {
            if (position == this.mSize - 1 || position == this.mSize - 2) {
                ((CarPriceHeaderCityItemBinding) holder.binding).divider.setVisibility(8);
            }
        } else if (position == this.mSize - 1) {
            ((CarPriceHeaderCityItemBinding) holder.binding).divider.setVisibility(8);
        }
        CityPriceInfo info = (CityPriceInfo) this.mList.get(position);
        if (this.mISModel) {
            ((CarPriceHeaderCityItemBinding) holder.binding).tvPriceChangeTip.setVisibility(8);
            ((CarPriceHeaderCityItemBinding) holder.binding).tvPriceChangeProportion.setText(info.price.getOwnerPrice());
            if (info.price.ownerprice <= 0.0d) {
                ((CarPriceHeaderCityItemBinding) holder.binding).tvPriceChangeProportion.setTextColor(this.mContext.getResources().getColor(R.color.color_38_000000));
            } else {
                ((CarPriceHeaderCityItemBinding) holder.binding).tvPriceChangeProportion.setTextColor(this.mContext.getResources().getColor(R.color.color_e12c2c));
            }
        } else if (info.price.preferential == 0.0d) {
            ((CarPriceHeaderCityItemBinding) holder.binding).tvPriceChangeTip.setText("");
            ((CarPriceHeaderCityItemBinding) holder.binding).tvPriceChangeProportion.setText(info.price.getPreferential());
            ((CarPriceHeaderCityItemBinding) holder.binding).tvPriceChangeProportion.setTextColor(this.mContext.getResources().getColor(R.color.color_38_000000));
        } else {
            ((CarPriceHeaderCityItemBinding) holder.binding).tvPriceChangeTip.setText(info.price.getPreferentialTextNew());
            ((CarPriceHeaderCityItemBinding) holder.binding).tvPriceChangeProportion.setText(info.price.getPreferentialDetail());
            ((CarPriceHeaderCityItemBinding) holder.binding).tvPriceChangeProportion.setTextColor(this.mContext.getResources().getColor(R.color.color_e12c2c));
        }
    }

    public void dataBindingTo(CarPriceHeaderCityItemBinding carPriceHeaderCityItemBinding, CityPriceInfo cityPriceInfo) {
        carPriceHeaderCityItemBinding.setCityPriceInfo(cityPriceInfo);
    }
}
