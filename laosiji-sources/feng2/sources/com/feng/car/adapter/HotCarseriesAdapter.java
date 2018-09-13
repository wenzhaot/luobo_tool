package com.feng.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.feng.car.databinding.HotCarseriesItemBinding;
import com.feng.car.entity.car.CarSeriesInfo;
import java.util.List;

public class HotCarseriesAdapter extends MvvmBaseAdapter<CarSeriesInfo, HotCarseriesItemBinding> {
    public HotCarseriesAdapter(Context context, List<CarSeriesInfo> list) {
        super(context, list);
    }

    public HotCarseriesItemBinding getBinding(ViewGroup parent, int viewType) {
        return HotCarseriesItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void dataBindingTo(HotCarseriesItemBinding hotCarseriesItemBinding, CarSeriesInfo carSeriesInfo) {
        hotCarseriesItemBinding.setInfo(carSeriesInfo);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<HotCarseriesItemBinding> holder, int position) {
        if (position == 0) {
            ((HotCarseriesItemBinding) holder.binding).vLeft.setVisibility(0);
        } else {
            ((HotCarseriesItemBinding) holder.binding).vLeft.setVisibility(8);
        }
    }
}
