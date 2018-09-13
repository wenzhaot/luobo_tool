package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import com.feng.car.databinding.CommodityItemLayoutBinding;
import com.feng.car.entity.dealer.CommodityInfo;
import com.feng.car.utils.FengUtil;
import java.util.List;

public class CommodityAdapter extends MvvmBaseAdapter<CommodityInfo, CommodityItemLayoutBinding> {
    public CommodityAdapter(Context context, List<CommodityInfo> list) {
        super(context, list);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<CommodityItemLayoutBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        CommodityInfo info = (CommodityInfo) this.mList.get(position);
        if (position == 0) {
            ((CommodityItemLayoutBinding) holder.binding).vLeft.setVisibility(0);
        } else {
            ((CommodityItemLayoutBinding) holder.binding).vLeft.setVisibility(8);
        }
        ((CommodityItemLayoutBinding) holder.binding).ivImage.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(info.imageinfo, 350, 1.0f)));
        ((CommodityItemLayoutBinding) holder.binding).tvCommodityName.setText(info.title);
        ((CommodityItemLayoutBinding) holder.binding).tvCurrentPrice.setText(FengUtil.numberFormatCar((double) info.price));
        ((CommodityItemLayoutBinding) holder.binding).tvGuidePrice.setText(FengUtil.numberFormatCar((double) info.originalprice));
        ((CommodityItemLayoutBinding) holder.binding).tvGuidePrice.getPaint().setFlags(16);
        ((CommodityItemLayoutBinding) holder.binding).tvBrowseNum.setText(info.viewcount + "");
    }

    public CommodityItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return CommodityItemLayoutBinding.inflate(this.mInflater);
    }

    public void dataBindingTo(CommodityItemLayoutBinding itemLayoutBinding, CommodityInfo info) {
    }
}
