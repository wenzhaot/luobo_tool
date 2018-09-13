package com.feng.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.activity.VehicleClassDetailActivity;
import com.feng.car.databinding.SearchCarModelItemLayoutBinding;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.car.CarSeriesInfo;
import com.github.jdsjlzx.listener.OnSingleClickListener;

public class CarXSearchItemAdapter extends MvvmBaseAdapter<CarModelInfo, SearchCarModelItemLayoutBinding> {
    private CarSeriesInfo mCarSeriesInfo;
    private boolean mIsOwnerSearch = false;

    public CarXSearchItemAdapter(Context context, CarSeriesInfo info, boolean isOwnerSearch) {
        super(context, info.local_models);
        this.mCarSeriesInfo = info;
        this.mIsOwnerSearch = isOwnerSearch;
    }

    public void onBaseBindViewHolder(MvvmViewHolder<SearchCarModelItemLayoutBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        final CarModelInfo info = (CarModelInfo) this.mList.get(position);
        if (this.mIsOwnerSearch) {
            ((SearchCarModelItemLayoutBinding) holder.binding).tvPrice.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_e12c2c));
            ((SearchCarModelItemLayoutBinding) holder.binding).tvPrice.setText(info.getAvgPrice());
            if (info.avgprice <= 0.0d) {
                ((SearchCarModelItemLayoutBinding) holder.binding).tvPrice.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_54_000000));
            } else {
                ((SearchCarModelItemLayoutBinding) holder.binding).tvPrice.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_e12c2c));
            }
            ((SearchCarModelItemLayoutBinding) holder.binding).tvText.setText("成交价：");
        } else {
            ((SearchCarModelItemLayoutBinding) holder.binding).tvPrice.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_87_000000));
            ((SearchCarModelItemLayoutBinding) holder.binding).tvPrice.setText(info.getGuidePrice());
            ((SearchCarModelItemLayoutBinding) holder.binding).tvText.setText(info.getCarPriceText());
        }
        ((SearchCarModelItemLayoutBinding) holder.binding).getRoot().setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(CarXSearchItemAdapter.this.mContext, VehicleClassDetailActivity.class);
                intent.putExtra("carx_name", info.name);
                intent.putExtra("name", (String) CarXSearchItemAdapter.this.mCarSeriesInfo.name.get());
                intent.putExtra("id", CarXSearchItemAdapter.this.mCarSeriesInfo.id);
                intent.putExtra("carxid", info.id);
                CarXSearchItemAdapter.this.mContext.startActivity(intent);
            }
        });
    }

    public SearchCarModelItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return SearchCarModelItemLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(SearchCarModelItemLayoutBinding itemLayoutBinding, CarModelInfo carModelInfo) {
        itemLayoutBinding.setCarmodel(carModelInfo);
    }
}
