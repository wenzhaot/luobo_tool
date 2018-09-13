package com.feng.car.entity.sns;

import android.content.Context;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import com.feng.car.databinding.CarsRelevantItemBinding;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.convenientbanner.holder.Holder;
import com.github.jdsjlzx.R;

public class RecommandCarsHolder implements Holder<CarSeriesInfo> {
    private int m130;
    private int m173;
    private CarsRelevantItemBinding mBinding;

    public View createView(Context context) {
        CarsRelevantItemBinding binding = CarsRelevantItemBinding.inflate(LayoutInflater.from(context));
        this.m173 = context.getResources().getDimensionPixelSize(2131296338);
        this.m130 = context.getResources().getDimensionPixelSize(2131296291);
        this.mBinding = binding;
        return this.mBinding.getRoot();
    }

    public void UpdateUI(Context context, int position, CarSeriesInfo carSeriesInfo) {
        if (carSeriesInfo != null) {
            this.mBinding.carsImage.setAutoImageURI(Uri.parse(FengUtil.getFixedSizeUrl(carSeriesInfo.image, this.m173, this.m130)));
            this.mBinding.carsName.setText((CharSequence) carSeriesInfo.name.get());
            this.mBinding.guidePrice.setText(changePriceColor(context, carSeriesInfo.getCarPriceText(), carSeriesInfo.getCarPrice()));
            if (carSeriesInfo.preferential == 0.0d) {
                this.mBinding.price.setTextColor(context.getResources().getColor(2131558464));
                this.mBinding.price.setText("暂无成交价");
            } else if (carSeriesInfo.preferential == 100.0d) {
                this.mBinding.price.setTextColor(context.getResources().getColor(2131558464));
                this.mBinding.price.setText("成交价无优惠");
            } else {
                this.mBinding.price.setTextColor(context.getResources().getColor(2131558513));
                if (carSeriesInfo.preferential > 100.0d) {
                    this.mBinding.price.setText("成交价加价 " + carSeriesInfo.getPreferential());
                } else {
                    this.mBinding.price.setText("成交价优惠 " + carSeriesInfo.getPreferential());
                }
            }
        }
    }

    private SpannableStringBuilder changePriceColor(Context context, String tip, String price) {
        StringBuilder sb = new StringBuilder();
        sb.append(tip);
        sb.append(price);
        SpannableStringBuilder builder = new SpannableStringBuilder(sb.toString());
        ForegroundColorSpan color54Span = new ForegroundColorSpan(context.getResources().getColor(R.color.color_54_000000));
        ForegroundColorSpan color87Span = new ForegroundColorSpan(context.getResources().getColor(2131558478));
        builder.setSpan(color54Span, 0, tip.length(), 33);
        builder.setSpan(color87Span, tip.length(), sb.length(), 18);
        return builder;
    }
}
