package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.feng.car.R;
import com.feng.car.databinding.RelativeGoodsItemBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.dealer.CommodityInfo;
import com.feng.car.utils.FengUtil;
import java.util.List;

public class RelativeGoodsAdapter extends MvvmBaseAdapter<CommodityInfo, RelativeGoodsItemBinding> {
    private LayoutParams mParams;
    private int mWidth;

    public RelativeGoodsAdapter(Context context, List<CommodityInfo> list) {
        super(context, list);
        this.mWidth = 0;
        this.mWidth = FengUtil.getScreenWidth(this.mContext) - (this.mContext.getResources().getDimensionPixelSize(R.dimen.default_32PX) * 3);
    }

    public RelativeGoodsItemBinding getBinding(ViewGroup parent, int viewType) {
        return RelativeGoodsItemBinding.inflate(this.mInflater, parent, false);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<RelativeGoodsItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        CommodityInfo info = (CommodityInfo) this.mList.get(position);
        this.mParams = (LayoutParams) ((RelativeGoodsItemBinding) holder.binding).ivImage.getLayoutParams();
        this.mParams.width = this.mWidth / 2;
        this.mParams.height = getImageHeight(info.imageinfo);
        ((RelativeGoodsItemBinding) holder.binding).ivImage.setLayoutParams(this.mParams);
        ((RelativeGoodsItemBinding) holder.binding).ivImage.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(info.imageinfo, 350)));
        ((RelativeGoodsItemBinding) holder.binding).tvGuidePrice.getPaint().setFlags(16);
    }

    public void dataBindingTo(RelativeGoodsItemBinding relativeGoodsItemBinding, CommodityInfo commodityInfo) {
        relativeGoodsItemBinding.setInfo(commodityInfo);
    }

    private int getImageHeight(ImageInfo info) {
        if (info.width == 0 || info.height == 0) {
            return this.mWidth / 2;
        }
        return FengUtil.getRelativeWH(info, this.mWidth / 2, 350)[1];
    }
}
