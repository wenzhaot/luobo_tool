package com.feng.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ItemCarModleAddBinding;
import com.feng.car.entity.car.CarModelInfo;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class CarModelAddAdapter extends MvvmBaseAdapter<CarModelInfo, ItemCarModleAddBinding> {
    private String mCarSeriesName;
    private boolean mIsUploadPrice = false;
    private List<Integer> mLocalList = FengApplication.getInstance().getSparkDB().getCarComparisonIDList();

    public void setIsUploadPrice(boolean mIsUploadPrice) {
        this.mIsUploadPrice = mIsUploadPrice;
    }

    public CarModelAddAdapter(Context context, List<CarModelInfo> list, String carSeriesName) {
        super(context, list);
        this.mCarSeriesName = carSeriesName;
    }

    public List<Integer> getLocalList() {
        return this.mLocalList;
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ItemCarModleAddBinding> holder, int position) {
        CarModelInfo info = (CarModelInfo) this.mList.get(position);
        if (this.mIsUploadPrice) {
            ((ItemCarModleAddBinding) holder.binding).tvComparison.setVisibility(4);
        } else if (this.mLocalList.contains(Integer.valueOf(info.id))) {
            ((ItemCarModleAddBinding) holder.binding).tvComparison.setVisibility(0);
        } else {
            ((ItemCarModleAddBinding) holder.binding).tvComparison.setVisibility(4);
        }
        ((ItemCarModleAddBinding) holder.binding).ivCarState.setVisibility(0);
        if (info.state == 10) {
            ((ItemCarModleAddBinding) holder.binding).ivCarState.setImageResource(R.drawable.icon_vehicle_will_sell);
        } else if (info.state == 30) {
            ((ItemCarModleAddBinding) holder.binding).ivCarState.setImageResource(R.drawable.icon_vehicle_stop_product_but_sell);
        } else if (info.state == 40) {
            ((ItemCarModleAddBinding) holder.binding).ivCarState.setImageResource(R.drawable.icon_vehicle_stop_sell);
        } else {
            ((ItemCarModleAddBinding) holder.binding).ivCarState.setVisibility(8);
        }
        ((ItemCarModleAddBinding) holder.binding).tvCarEngine.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
            }
        });
        ((ItemCarModleAddBinding) holder.binding).tvYear.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
            }
        });
        ((ItemCarModleAddBinding) holder.binding).setPosition(Integer.valueOf(position));
    }

    public ItemCarModleAddBinding getBinding(ViewGroup parent, int viewType) {
        return ItemCarModleAddBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(ItemCarModleAddBinding searchDefaultItemBinding, CarModelInfo info) {
        searchDefaultItemBinding.setInfo(info);
        searchDefaultItemBinding.setCarseriesname(this.mCarSeriesName);
    }
}
