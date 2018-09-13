package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.feng.car.R;
import com.feng.car.databinding.SearchPriceCarsLayoutBinding;
import com.feng.car.entity.car.CarSeriesInfo;
import java.util.List;

public class CarsPriceItemAdapter extends MvvmBaseAdapter<CarSeriesInfo, SearchPriceCarsLayoutBinding> {
    private int m32 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_32PX);
    private LayoutParams mParams;

    public CarsPriceItemAdapter(Context context, List<CarSeriesInfo> list) {
        super(context, list);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<SearchPriceCarsLayoutBinding> holder, int position) {
        this.mParams = (LayoutParams) ((SearchPriceCarsLayoutBinding) holder.binding).adCarImage.getLayoutParams();
        if (position == 0) {
            this.mParams.leftMargin = this.m32;
        } else {
            this.mParams.leftMargin = 0;
        }
        ((SearchPriceCarsLayoutBinding) holder.binding).adCarImage.setAutoImageURI(Uri.parse(((CarSeriesInfo) this.mList.get(position)).image.url));
        ((SearchPriceCarsLayoutBinding) holder.binding).adCarImage.setLayoutParams(this.mParams);
    }

    public SearchPriceCarsLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return SearchPriceCarsLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(SearchPriceCarsLayoutBinding layoutBinding, CarSeriesInfo info) {
        layoutBinding.setCarSeriesInfo(info);
    }
}
