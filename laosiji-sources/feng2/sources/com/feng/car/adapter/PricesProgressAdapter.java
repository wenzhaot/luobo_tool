package com.feng.car.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.feng.car.R;
import com.feng.car.databinding.ProgressItemLayoutBinding;
import com.feng.car.entity.lcoation.CityPriceInfo;
import com.feng.car.utils.FengUtil;
import java.util.List;

public class PricesProgressAdapter extends MvvmBaseAdapter<CityPriceInfo, ProgressItemLayoutBinding> {
    private double mDownMax = 1.0d;
    private LayoutParams mParams;
    private int mScreenHalfWidth = 0;
    private int mType = 0;
    private double mUpMax = 1.0d;

    public PricesProgressAdapter(Context context, List<CityPriceInfo> list, int type, double downMax, double upMax) {
        super(context, list);
        this.mType = type;
        this.mDownMax = downMax;
        this.mUpMax = upMax;
        this.mScreenHalfWidth = (FengUtil.getScreenWidth(this.mContext) - (this.mContext.getResources().getDimensionPixelSize(R.dimen.default_32PX) * 2)) / 2;
    }

    public ProgressItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return ProgressItemLayoutBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ProgressItemLayoutBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        if (position == 0) {
            ((ProgressItemLayoutBinding) holder.binding).vLine.setVisibility(0);
        } else {
            ((ProgressItemLayoutBinding) holder.binding).vLine.setVisibility(8);
        }
        CityPriceInfo info = (CityPriceInfo) this.mList.get(position);
        this.mParams = (LayoutParams) ((ProgressItemLayoutBinding) holder.binding).prBar.getLayoutParams();
        this.mParams.width = calculateProgress(info, holder);
        ((ProgressItemLayoutBinding) holder.binding).prBar.setLayoutParams(this.mParams);
        ((ProgressItemLayoutBinding) holder.binding).tvCityName.setText(String.valueOf((position + 1) + ".") + info.city.name);
    }

    public void dataBindingTo(ProgressItemLayoutBinding progressItemLayoutBinding, CityPriceInfo cityInfo) {
    }

    private int calculateProgress(CityPriceInfo cityInfo, MvvmViewHolder<ProgressItemLayoutBinding> holder) {
        ((ProgressItemLayoutBinding) holder.binding).prBar.setTag("");
        if (this.mType == 0) {
            double d;
            if (cityInfo.price.preferential == 0.0d) {
                ((ProgressItemLayoutBinding) holder.binding).prBar.setProgressDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.gradient_f3f3f3));
                ((ProgressItemLayoutBinding) holder.binding).tvPrice.setText("暂无成交价");
                ((ProgressItemLayoutBinding) holder.binding).tvCityName.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_38_000000));
                ((ProgressItemLayoutBinding) holder.binding).tvPrice.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_38_000000));
                return this.mScreenHalfWidth * 2;
            } else if (cityInfo.price.preferential <= 100.0d) {
                ((ProgressItemLayoutBinding) holder.binding).prBar.setTag("优惠");
                ((ProgressItemLayoutBinding) holder.binding).tvPrice.setText("优惠" + cityInfo.price.getPreferentialDetail());
                ((ProgressItemLayoutBinding) holder.binding).prBar.setProgressDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.progress_bar_selector));
                ((ProgressItemLayoutBinding) holder.binding).tvCityName.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_ffffff));
                ((ProgressItemLayoutBinding) holder.binding).tvPrice.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_ffffff));
                d = Math.abs(this.mDownMax - this.mUpMax);
                if (d == 0.0d) {
                    return this.mScreenHalfWidth * 2;
                }
                return (int) ((((Math.abs(cityInfo.price.preferential - 100.0d) - this.mDownMax) * ((double) this.mScreenHalfWidth)) / d) + ((double) this.mScreenHalfWidth));
            } else if (cityInfo.price.preferential <= 100.0d) {
                return this.mScreenHalfWidth;
            } else {
                ((ProgressItemLayoutBinding) holder.binding).prBar.setTag("加价");
                ((ProgressItemLayoutBinding) holder.binding).prBar.setProgressDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.gradient_909090));
                ((ProgressItemLayoutBinding) holder.binding).tvPrice.setText("加价" + cityInfo.price.getPreferentialDetail());
                ((ProgressItemLayoutBinding) holder.binding).tvCityName.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_ffffff));
                ((ProgressItemLayoutBinding) holder.binding).tvPrice.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_ffffff));
                d = Math.abs(this.mDownMax - this.mUpMax);
                if (d == 0.0d) {
                    return this.mScreenHalfWidth * 2;
                }
                return (int) (((double) this.mScreenHalfWidth) + (((Math.abs(cityInfo.price.preferential - 100.0d) - this.mDownMax) * ((double) this.mScreenHalfWidth)) / d));
            }
        } else if (cityInfo.price.ownerprice > 0.0d) {
            ((ProgressItemLayoutBinding) holder.binding).prBar.setTag("成交价");
            ((ProgressItemLayoutBinding) holder.binding).prBar.setProgressDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.progress_bar_selector));
            ((ProgressItemLayoutBinding) holder.binding).tvCityName.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_ffffff));
            ((ProgressItemLayoutBinding) holder.binding).tvPrice.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_ffffff));
            ((ProgressItemLayoutBinding) holder.binding).tvPrice.setText(cityInfo.price.getCarxOwnerPrice());
            if (this.mUpMax - this.mDownMax == 0.0d) {
                return this.mScreenHalfWidth * 2;
            }
            return (int) ((((cityInfo.price.ownerprice - this.mDownMax) * ((double) this.mScreenHalfWidth)) / (this.mUpMax - this.mDownMax)) + ((double) this.mScreenHalfWidth));
        } else {
            ((ProgressItemLayoutBinding) holder.binding).prBar.setProgressDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.gradient_f3f3f3));
            ((ProgressItemLayoutBinding) holder.binding).tvPrice.setText("暂无成交价");
            ((ProgressItemLayoutBinding) holder.binding).tvCityName.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_38_000000));
            ((ProgressItemLayoutBinding) holder.binding).tvPrice.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_38_000000));
            return this.mScreenHalfWidth * 2;
        }
    }
}
