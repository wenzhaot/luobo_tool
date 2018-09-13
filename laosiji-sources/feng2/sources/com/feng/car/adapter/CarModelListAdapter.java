package com.feng.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.activity.CarPhotoListActivity;
import com.feng.car.activity.VehicleClassDetailActivity;
import com.feng.car.databinding.CarModelListItemBinding;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.event.CarModelChangeImageEvent;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class CarModelListAdapter extends MvvmBaseAdapter<CarModelInfo, CarModelListItemBinding> {
    private int mCarModelID = 0;

    public void setCarModelID(int carModelID) {
        this.mCarModelID = carModelID;
    }

    public CarModelListAdapter(Context context, int carModelID, List<CarModelInfo> list) {
        super(context, list);
        this.mCarModelID = carModelID;
    }

    public void setList(List<CarModelInfo> list) {
        this.mList = list;
    }

    public void onBaseBindViewHolder(MvvmViewHolder<CarModelListItemBinding> holder, int position) {
        final CarModelInfo item = (CarModelInfo) this.mList.get(position);
        ((CarModelListItemBinding) holder.binding).tvContent.setText(item.name);
        if (item.id == this.mCarModelID) {
            ((CarModelListItemBinding) holder.binding).tvContent.setSelected(true);
            ((CarModelListItemBinding) holder.binding).ivSelect.setVisibility(0);
        } else {
            ((CarModelListItemBinding) holder.binding).tvContent.setSelected(false);
            ((CarModelListItemBinding) holder.binding).ivSelect.setVisibility(4);
        }
        if (item.posfirstflag == 1) {
            ((CarModelListItemBinding) holder.binding).tvCarEngine.setVisibility(0);
        } else {
            ((CarModelListItemBinding) holder.binding).tvCarEngine.setVisibility(8);
        }
        if (item.poslastflag == 1) {
            ((CarModelListItemBinding) holder.binding).vLine.setVisibility(8);
        } else {
            ((CarModelListItemBinding) holder.binding).vLine.setVisibility(0);
        }
        ((CarModelListItemBinding) holder.binding).tvCarEngine.setOnClickListener(null);
        ((CarModelListItemBinding) holder.binding).defaultLine.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (CarModelListAdapter.this.mContext instanceof CarPhotoListActivity) {
                    EventBus.getDefault().post(new CarModelChangeImageEvent(item, CarModelChangeImageEvent.TYPE_CAR_IMAGE_LSIT));
                } else if (CarModelListAdapter.this.mContext instanceof VehicleClassDetailActivity) {
                    EventBus.getDefault().post(new CarModelChangeImageEvent(item, CarModelChangeImageEvent.TYPE_CAR_DEALER));
                }
            }
        });
        ((CarModelListItemBinding) holder.binding).ivCarState.setVisibility(0);
        if (item.state == 10) {
            ((CarModelListItemBinding) holder.binding).ivCarState.setImageResource(R.drawable.icon_vehicle_will_sell);
        } else if (item.state == 30) {
            ((CarModelListItemBinding) holder.binding).ivCarState.setImageResource(R.drawable.icon_vehicle_stop_product_but_sell);
        } else if (item.state == 40) {
            ((CarModelListItemBinding) holder.binding).ivCarState.setImageResource(R.drawable.icon_vehicle_stop_sell);
        } else {
            ((CarModelListItemBinding) holder.binding).ivCarState.setVisibility(8);
        }
    }

    public CarModelListItemBinding getBinding(ViewGroup parent, int viewType) {
        return CarModelListItemBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(CarModelListItemBinding searchDefaultItemBinding, CarModelInfo info) {
        searchDefaultItemBinding.setCarModelInfo(info);
    }
}
