package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.CarsOwnerPriceItemBinding;
import com.feng.car.entity.car.CarSeriesInfo;
import java.util.List;

public class CarsOwnerPriceAdapter extends MvvmBaseAdapter<CarSeriesInfo, CarsOwnerPriceItemBinding> {
    public CarsOwnerPriceAdapter(Context context, List<CarSeriesInfo> list) {
        super(context, list);
    }

    public CarsOwnerPriceItemBinding getBinding(ViewGroup parent, int viewType) {
        return CarsOwnerPriceItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void dataBindingTo(CarsOwnerPriceItemBinding carsOwnerPriceItemBinding, CarSeriesInfo carSeriesInfo) {
        carsOwnerPriceItemBinding.setCarSeriesInfo(carSeriesInfo);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<CarsOwnerPriceItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        if (position == 0) {
            ((CarsOwnerPriceItemBinding) holder.binding).divider.setVisibility(8);
        } else {
            ((CarsOwnerPriceItemBinding) holder.binding).divider.setVisibility(0);
        }
        CarSeriesInfo info = (CarSeriesInfo) this.mList.get(position);
        ((CarsOwnerPriceItemBinding) holder.binding).price.setVisibility(0);
        ((CarsOwnerPriceItemBinding) holder.binding).image.setAutoImageURI(Uri.parse(info.image.url));
        ((CarsOwnerPriceItemBinding) holder.binding).name.setText((CharSequence) info.name.get());
        ((CarsOwnerPriceItemBinding) holder.binding).price.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_87_000000));
        ((CarsOwnerPriceItemBinding) holder.binding).price.setText(info.getCarPriceText() + info.getCarPrice());
        ((CarsOwnerPriceItemBinding) holder.binding).voiceText.setVisibility(8);
        ((CarsOwnerPriceItemBinding) holder.binding).ownerPriceCount.setVisibility(0);
        ((CarsOwnerPriceItemBinding) holder.binding).discount.setVisibility(0);
        if (info.preferential == 0.0d || info.preferential == 100.0d) {
            ((CarsOwnerPriceItemBinding) holder.binding).discount.setTextColor(this.mContext.getResources().getColor(R.color.color_38_000000));
        } else if (info.preferential - 100.0d > 0.0d) {
            ((CarsOwnerPriceItemBinding) holder.binding).discount.setTextColor(this.mContext.getResources().getColor(R.color.color_ff2c04));
        } else if (info.preferential - 100.0d < 0.0d) {
            ((CarsOwnerPriceItemBinding) holder.binding).discount.setTextColor(this.mContext.getResources().getColor(R.color.color_ff2c04));
        }
        ((CarsOwnerPriceItemBinding) holder.binding).discount.setText(info.getPreferentialNew());
        ((CarsOwnerPriceItemBinding) holder.binding).ownerPriceCount.setText(info.getPricecount());
    }
}
