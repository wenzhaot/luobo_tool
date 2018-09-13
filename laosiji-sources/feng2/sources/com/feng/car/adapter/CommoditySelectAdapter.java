package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.CommoditySelectItemLayoutBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.dealer.CommodityInfo;
import com.feng.car.utils.FengUtil;
import java.util.List;

public class CommoditySelectAdapter extends MvvmBaseAdapter<CommodityInfo, CommoditySelectItemLayoutBinding> {
    private LayoutParams mParams;
    private int mWidth;

    public CommoditySelectAdapter(Context context, List<CommodityInfo> list) {
        super(context, list);
        this.mWidth = 0;
        this.mWidth = FengUtil.getScreenWidth(this.mContext) - (this.mContext.getResources().getDimensionPixelSize(R.dimen.default_32PX) * 3);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<CommoditySelectItemLayoutBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        CommodityInfo info = (CommodityInfo) this.mList.get(position);
        this.mParams = (LayoutParams) ((CommoditySelectItemLayoutBinding) holder.binding).ivImage.getLayoutParams();
        this.mParams.width = this.mWidth / 2;
        this.mParams.height = getImageHeight(info.imageinfo);
        ((CommoditySelectItemLayoutBinding) holder.binding).ivImage.setLayoutParams(this.mParams);
        ((CommoditySelectItemLayoutBinding) holder.binding).ivImage.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(info.imageinfo, 350)));
        ((CommoditySelectItemLayoutBinding) holder.binding).tvCommodityName.setText(info.title);
        ((CommoditySelectItemLayoutBinding) holder.binding).tvCurrentPrice.setText(FengUtil.numberFormatCar((double) info.price));
        ((CommoditySelectItemLayoutBinding) holder.binding).tvGuidePrice.setText(FengUtil.numberFormatCar((double) info.originalprice));
        ((CommoditySelectItemLayoutBinding) holder.binding).tvGuidePrice.getPaint().setFlags(16);
        ((CommoditySelectItemLayoutBinding) holder.binding).tvBrowseNum.setText(info.viewcount + "");
        if (info.status == 15) {
            ((CommoditySelectItemLayoutBinding) holder.binding).tvCover.setVisibility(8);
            return;
        }
        ((CommoditySelectItemLayoutBinding) holder.binding).tvCover.setVisibility(0);
        if (info.status == 5) {
            ((CommoditySelectItemLayoutBinding) holder.binding).tvCover.setText("审核失败");
            ((CommoditySelectItemLayoutBinding) holder.binding).tvCover.setBackgroundResource(R.drawable.bg_round_60_e12c2c_8px_top);
        } else {
            if (info.status == 3) {
                ((CommoditySelectItemLayoutBinding) holder.binding).tvCover.setText("审核中");
            } else if (info.status == 10) {
                ((CommoditySelectItemLayoutBinding) holder.binding).tvCover.setText("审核中");
            } else if (info.status == 20) {
                ((CommoditySelectItemLayoutBinding) holder.binding).tvCover.setText("已下架");
            } else if (info.status == 25) {
                ((CommoditySelectItemLayoutBinding) holder.binding).tvCover.setText("已售出");
            }
            ((CommoditySelectItemLayoutBinding) holder.binding).tvCover.setBackgroundResource(R.drawable.bg_round_70_000000_8px_top);
        }
        ((CommoditySelectItemLayoutBinding) holder.binding).tvCover.getLayoutParams().height = this.mParams.height;
    }

    public CommoditySelectItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return CommoditySelectItemLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(CommoditySelectItemLayoutBinding itemLayoutBinding, CommodityInfo info) {
        itemLayoutBinding.setInfo(info);
        itemLayoutBinding.setSaletype(Integer.valueOf(FengApplication.getInstance().getUserInfo().getLocalSaleType()));
    }

    private int getImageHeight(ImageInfo info) {
        if (info.width == 0 || info.height == 0) {
            return this.mWidth / 2;
        }
        return FengUtil.getRelativeWH(info, this.mWidth / 2, 350)[1];
    }
}
