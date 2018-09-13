package com.feng.car.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.CarOwnerPriceItemBinding;
import com.feng.car.entity.car.SeriesPriceVo;
import com.feng.car.utils.MapUtil;
import java.util.List;

public class VehiclePriceAdapter extends MvvmBaseAdapter<SeriesPriceVo, CarOwnerPriceItemBinding> {
    private String mAreaName = "";
    private int mCurrentCount = 0;

    public void setTotalCount(int count) {
        this.mCurrentCount = count;
    }

    public VehiclePriceAdapter(Context context, String areaName, List list) {
        super(context, list);
        this.mAreaName = areaName;
    }

    public CarOwnerPriceItemBinding getBinding(ViewGroup parent, int viewType) {
        return CarOwnerPriceItemBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(CarOwnerPriceItemBinding carOwnerPriceItemBinding, SeriesPriceVo info) {
    }

    public void onBaseBindViewHolder(MvvmViewHolder<CarOwnerPriceItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        SeriesPriceVo info = (SeriesPriceVo) this.mList.get(position);
        if (position == 0) {
            ((CarOwnerPriceItemBinding) holder.binding).countLine.setVisibility(0);
            ((CarOwnerPriceItemBinding) holder.binding).cityText.setText(this.mAreaName);
            ((CarOwnerPriceItemBinding) holder.binding).countText.setText(this.mCurrentCount + "条成交价");
        } else {
            ((CarOwnerPriceItemBinding) holder.binding).countLine.setVisibility(8);
        }
        ((CarOwnerPriceItemBinding) holder.binding).infoLine.setVisibility(0);
        if (info != null) {
            ((CarOwnerPriceItemBinding) holder.binding).carName.setText(info.carx.name);
            ((CarOwnerPriceItemBinding) holder.binding).cityName.setText(changeTexTColor("购车城市：", MapUtil.newInstance().getCityNameById(this.mContext, info.cityid), R.color.color_87_000000));
            ((CarOwnerPriceItemBinding) holder.binding).time.setText(changeTexTColor("购车时间：", info.dealtime, R.color.color_87_000000));
            if (info.carx.state == 10) {
                ((CarOwnerPriceItemBinding) holder.binding).guideprice.setText(changeTexTColor("预售价格：", info.carx.getGuidePrice(), R.color.color_87_000000));
            } else {
                ((CarOwnerPriceItemBinding) holder.binding).guideprice.setText(changeTexTColor("指导价格：", info.carx.getGuidePrice(), R.color.color_87_000000));
            }
            ((CarOwnerPriceItemBinding) holder.binding).nakedPrice.setText(changeTexTColor("裸车价格：", info.getPriceString(), R.color.color_e12c2c));
            ((CarOwnerPriceItemBinding) holder.binding).totalPrice.setText(changeTexTColor("购车总价：", info.getTotalPriceString(), R.color.color_e12c2c));
            ((CarOwnerPriceItemBinding) holder.binding).payment.setText(changeTexTColor("付款方式：", info.getDealtypeString(), R.color.color_87_000000));
            ((CarOwnerPriceItemBinding) holder.binding).remarks.setText(info.remark);
            ((CarOwnerPriceItemBinding) holder.binding).remarksTip.setText("备注信息：");
        }
        if (position == this.mList.size() - 1) {
            ((CarOwnerPriceItemBinding) holder.binding).divider.setVisibility(8);
            ((CarOwnerPriceItemBinding) holder.binding).vPlaceholder.setVisibility(0);
            return;
        }
        ((CarOwnerPriceItemBinding) holder.binding).divider.setVisibility(0);
        ((CarOwnerPriceItemBinding) holder.binding).vPlaceholder.setVisibility(8);
    }

    private SpannableStringBuilder changeTexTColor(String tag, String text, int color) {
        StringBuilder sb = new StringBuilder(tag);
        sb.append(text);
        SpannableStringBuilder style = new SpannableStringBuilder(sb.toString());
        style.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.color_54_000000)), 0, tag.length(), 34);
        style.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(color)), tag.length(), sb.length(), 34);
        return style;
    }
}
