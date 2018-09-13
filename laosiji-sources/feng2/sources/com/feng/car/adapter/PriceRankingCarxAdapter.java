package com.feng.car.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.CarxpriceRankingItemBinding;
import com.feng.car.entity.car.RecommendCarxInfo;
import java.util.List;

public class PriceRankingCarxAdapter extends MvvmBaseAdapter<RecommendCarxInfo, CarxpriceRankingItemBinding> {
    public PriceRankingCarxAdapter(Context context, List<RecommendCarxInfo> list) {
        super(context, list);
    }

    public CarxpriceRankingItemBinding getBinding(ViewGroup parent, int viewType) {
        return CarxpriceRankingItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void dataBindingTo(CarxpriceRankingItemBinding carxpriceRankingItemBinding, RecommendCarxInfo carModelInfo) {
        carxpriceRankingItemBinding.setCarxInfo(carModelInfo);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<CarxpriceRankingItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        if (((RecommendCarxInfo) this.mList.get(position)).carx.avgprice <= 0.0d) {
            ((CarxpriceRankingItemBinding) holder.binding).avgprice.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_87_000000));
        } else {
            ((CarxpriceRankingItemBinding) holder.binding).avgprice.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_e12c2c));
        }
        if (position == this.mList.size() - 1) {
            ((CarxpriceRankingItemBinding) holder.binding).divier.setVisibility(4);
        } else {
            ((CarxpriceRankingItemBinding) holder.binding).divier.setVisibility(0);
        }
    }
}
