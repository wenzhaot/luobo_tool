package com.feng.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.feng.car.databinding.CityItemLayoutBinding;
import com.feng.car.entity.lcoation.CityInfo;
import java.util.List;

public class CitySelectAdapter extends MvvmBaseAdapter<CityInfo, CityItemLayoutBinding> {
    public CitySelectAdapter(Context context, List<CityInfo> list) {
        super(context, list);
    }

    public CityItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return CityItemLayoutBinding.inflate(LayoutInflater.from(this.mContext));
    }

    public void dataBindingTo(CityItemLayoutBinding cityItemLayoutBinding, CityInfo cityInfo) {
        cityItemLayoutBinding.setCityInfo(cityInfo);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<CityItemLayoutBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        CityInfo cityInfo = (CityInfo) this.mList.get(position);
        ((CityItemLayoutBinding) holder.binding).cityName.setText(cityInfo.name);
        if (cityInfo.posflag == 1) {
            ((CityItemLayoutBinding) holder.binding).divierLine.setVisibility(4);
        } else {
            ((CityItemLayoutBinding) holder.binding).divierLine.setVisibility(0);
        }
    }
}
