package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.feng.car.R;
import com.feng.car.databinding.CarPhotoListItemBinding;
import com.feng.car.entity.car.CarImageInfo;
import com.feng.car.utils.FengUtil;
import java.util.List;

public class CarPhotoListAdapter extends MvvmBaseAdapter<CarImageInfo, CarPhotoListItemBinding> {
    private int mViewHeight = ((this.mViewWidth * 3) / 4);
    private int mViewWidth = ((this.mContext.getResources().getDisplayMetrics().widthPixels - this.mContext.getResources().getDimensionPixelSize(R.dimen.default_16PX)) / 3);

    public CarPhotoListAdapter(Context context, List<CarImageInfo> list, int carPhotoType) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    public CarPhotoListItemBinding getBinding(ViewGroup parent, int viewType) {
        return CarPhotoListItemBinding.inflate(LayoutInflater.from(this.mContext));
    }

    public void dataBindingTo(CarPhotoListItemBinding carPhotoListItemBinding, CarImageInfo imageInfo) {
    }

    public void onBaseBindViewHolder(MvvmViewHolder<CarPhotoListItemBinding> holder, int position) {
        String imageUrl;
        CarImageInfo carImageInfo = (CarImageInfo) this.mList.get(position);
        LayoutParams params = (LayoutParams) ((CarPhotoListItemBinding) holder.binding).afdCarPhoto.getLayoutParams();
        params.width = this.mViewWidth;
        params.height = this.mViewHeight;
        ((CarPhotoListItemBinding) holder.binding).afdCarPhoto.setLayoutParams(params);
        if (carImageInfo.imagelist.p240.width > 0) {
            imageUrl = carImageInfo.imagelist.p240.url;
        } else {
            imageUrl = FengUtil.getCarImageSizeUrl(carImageInfo.image, 2001);
        }
        ((CarPhotoListItemBinding) holder.binding).afdCarPhoto.setAutoImageURI(Uri.parse(imageUrl));
    }
}
