package com.feng.car.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.activity.AddCarModelActivity;
import com.feng.car.activity.NewSubjectActivity;
import com.feng.car.activity.StopSellingCarActivity;
import com.feng.car.databinding.ItemCarSeriesBinding;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.event.UploadPriceSelectEvent;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class CarSeriesListAdapter extends MvvmBaseAdapter<CarSeriesInfo, ItemCarSeriesBinding> {
    private boolean isNeedBrand = false;
    private int m300;
    private boolean mIsUploadPrice = false;
    private int mType;

    public void setIsUploadPrice(boolean flag) {
        this.mIsUploadPrice = flag;
    }

    public void setNeedBrand(boolean needBrand) {
        this.isNeedBrand = needBrand;
    }

    public CarSeriesListAdapter(Context mContext, int type, List<CarSeriesInfo> mDatas) {
        super(mContext, mDatas);
        this.mType = type;
        this.m300 = mContext.getResources().getDimensionPixelSize(R.dimen.default_300PX);
    }

    public ItemCarSeriesBinding getBinding(ViewGroup parent, int viewType) {
        return ItemCarSeriesBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(ItemCarSeriesBinding itemCarBrandBinding, CarSeriesInfo info) {
        itemCarBrandBinding.setCarSeriesInfo(info);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ItemCarSeriesBinding> holder, int position) {
        final CarSeriesInfo carSeriesInfo = (CarSeriesInfo) this.mList.get(position);
        if (this.isNeedBrand) {
            if (carSeriesInfo.posfirstflag == 1) {
                ((ItemCarSeriesBinding) holder.binding).tvCarBrand.setVisibility(0);
            } else {
                ((ItemCarSeriesBinding) holder.binding).tvCarBrand.setVisibility(8);
            }
            if (carSeriesInfo.poslastflag == 1) {
                ((ItemCarSeriesBinding) holder.binding).vLine.setVisibility(8);
            } else {
                ((ItemCarSeriesBinding) holder.binding).vLine.setVisibility(0);
            }
        } else {
            ((ItemCarSeriesBinding) holder.binding).tvCarBrand.setVisibility(8);
            ((ItemCarSeriesBinding) holder.binding).vLine.setVisibility(0);
        }
        ((ItemCarSeriesBinding) holder.binding).rlContent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent;
                if (CarSeriesListAdapter.this.mIsUploadPrice) {
                    EventBus.getDefault().post(new UploadPriceSelectEvent(carSeriesInfo.id, (String) carSeriesInfo.name.get(), 2));
                    ((Activity) CarSeriesListAdapter.this.mContext).finish();
                } else if (CarSeriesListAdapter.this.mType == 0) {
                    intent = new Intent(CarSeriesListAdapter.this.mContext, NewSubjectActivity.class);
                    intent.putExtra("carsid", carSeriesInfo.id);
                    intent.putExtra(NewSubjectActivity.CIRCLES_REQUEST_ID, carSeriesInfo.communityinfo.id);
                    CarSeriesListAdapter.this.mContext.startActivity(intent);
                } else {
                    intent = new Intent(CarSeriesListAdapter.this.mContext, AddCarModelActivity.class);
                    intent.putExtra("id", carSeriesInfo.id);
                    intent.putExtra("name", (String) carSeriesInfo.name.get());
                    if (CarSeriesListAdapter.this.mType == 2) {
                        intent.putExtra("feng_type", 1);
                    }
                    CarSeriesListAdapter.this.mContext.startActivity(intent);
                }
            }
        });
        ((ItemCarSeriesBinding) holder.binding).carImage.setAutoImageURI(Uri.parse(carSeriesInfo.image.url));
        if (this.mContext instanceof StopSellingCarActivity) {
            ((ItemCarSeriesBinding) holder.binding).level.setVisibility(8);
            ((ItemCarSeriesBinding) holder.binding).tvPrice.setVisibility(8);
            ((ItemCarSeriesBinding) holder.binding).levelNoSale.setVisibility(0);
            ((ItemCarSeriesBinding) holder.binding).tvName.setMaxWidth(this.m300);
            if (carSeriesInfo.state == 40) {
                ((ItemCarSeriesBinding) holder.binding).saleImage.setImageResource(R.drawable.icon_vehicle_stop_sell);
            } else {
                ((ItemCarSeriesBinding) holder.binding).saleImage.setImageResource(R.drawable.icon_not_sale);
            }
            ((ItemCarSeriesBinding) holder.binding).saleImage.setVisibility(0);
            return;
        }
        ((ItemCarSeriesBinding) holder.binding).saleImage.setVisibility(8);
        String price = "";
        if (carSeriesInfo.getCarPrice().equals("暂无")) {
            price = carSeriesInfo.getCarPriceText() + "<font color=#D63D3D>" + carSeriesInfo.getCarPrice() + "</font>";
        } else {
            price = carSeriesInfo.getCarPriceText() + "<font color=#070808>" + carSeriesInfo.getCarPrice() + "</font>";
        }
        ((ItemCarSeriesBinding) holder.binding).tvPrice.setText(fromHtml(price));
        if (StringUtil.isEmpty(carSeriesInfo.level)) {
            ((ItemCarSeriesBinding) holder.binding).level.setVisibility(8);
        } else {
            ((ItemCarSeriesBinding) holder.binding).level.setVisibility(0);
        }
    }

    public Spanned fromHtml(String source) {
        if (VERSION.SDK_INT >= 24) {
            return Html.fromHtml(source, 0);
        }
        return Html.fromHtml(source);
    }
}
