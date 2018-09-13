package com.feng.car.adapter;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.baidu.mapapi.model.LatLng;
import com.feng.car.R;
import com.feng.car.activity.DistributorMapActivity;
import com.feng.car.databinding.ItemVehicleAgencyBinding;
import com.feng.car.entity.car.DealerInfo;
import com.feng.car.entity.model.LogGatherInfo;
import com.feng.car.event.DealerCallEvent;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.MapUtil;
import com.feng.library.emoticons.keyboard.utils.EmoticonsKeyboardUtils;
import com.feng.library.emoticons.keyboard.widget.EmoticonSpan;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.umeng.analytics.MobclickAgent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;

public class VehicleAgencyDetailAdapter extends MvvmBaseAdapter<DealerInfo, ItemVehicleAgencyBinding> {
    private String mAddress = "";
    private double mCurrentLat = 0.0d;
    private double mCurrentLon = 0.0d;
    private LogGatherInfo mLogGatherInfo;

    public VehicleAgencyDetailAdapter(Context context, List<DealerInfo> list, LogGatherInfo logGatherInfo) {
        super(context, list);
        this.mLogGatherInfo = logGatherInfo;
    }

    public ItemVehicleAgencyBinding getBinding(ViewGroup parent, int viewType) {
        return ItemVehicleAgencyBinding.inflate(this.mInflater, parent, false);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ItemVehicleAgencyBinding> holder, int position) {
        int itemWidth;
        final DealerInfo dealerInfo = (DealerInfo) this.mList.get(position);
        Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.icon_vehicle_shop_addr_tips);
        int fontSize = EmoticonsKeyboardUtils.getFontHeight(((ItemVehicleAgencyBinding) holder.binding).tvVehicleShopAddress);
        if (fontSize == -1) {
            itemWidth = drawable.getIntrinsicWidth();
        } else {
            fontSize -= 8;
            itemWidth = (int) ((((float) (drawable.getIntrinsicWidth() * fontSize)) * 1.0f) / ((float) drawable.getIntrinsicHeight()));
        }
        drawable.setBounds(0, 0, itemWidth, fontSize);
        EmoticonSpan imageSpan = new EmoticonSpan(drawable);
        SpannableStringBuilder spannable = new SpannableStringBuilder("@ " + dealerInfo.dealeraddress);
        spannable.setSpan(imageSpan, 0, 1, 17);
        ((ItemVehicleAgencyBinding) holder.binding).tvVehicleShopAddress.setText(spannable);
        ((ItemVehicleAgencyBinding) holder.binding).rlVehicleShopDetail.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intentTo = new Intent(VehicleAgencyDetailAdapter.this.mContext, DistributorMapActivity.class);
                intentTo.putExtra("DATA_JSON", JsonUtil.toJson(VehicleAgencyDetailAdapter.this.mList));
                intentTo.putExtra("id", dealerInfo.id);
                VehicleAgencyDetailAdapter.this.mContext.startActivity(intentTo);
            }
        });
        ((ItemVehicleAgencyBinding) holder.binding).llShopItemCallContainer.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (!TextUtils.isEmpty(dealerInfo.dealermobile)) {
                    VehicleAgencyDetailAdapter.this.showMakeCallDialog(dealerInfo);
                }
                Map<String, String> map = new HashMap();
                map.put("jxsid", dealerInfo.id + "");
                map.put("iscall", "0");
                VehicleAgencyDetailAdapter.this.mLogGatherInfo.addLogBtnEvent("app_btn_jxs_mobile_click", map);
            }
        });
        ((ItemVehicleAgencyBinding) holder.binding).llShopItemNavContainer.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                MapUtil.newInstance().showNavigationDialog(VehicleAgencyDetailAdapter.this.mContext, dealerInfo, new LatLng(VehicleAgencyDetailAdapter.this.mCurrentLat, VehicleAgencyDetailAdapter.this.mCurrentLon), VehicleAgencyDetailAdapter.this.mAddress);
            }
        });
        if (position <= 0 || position != this.mList.size() - 1) {
            ((ItemVehicleAgencyBinding) holder.binding).dividerLineLast.setVisibility(8);
            ((ItemVehicleAgencyBinding) holder.binding).dividerLine.setVisibility(0);
            return;
        }
        ((ItemVehicleAgencyBinding) holder.binding).dividerLineLast.setVisibility(0);
        ((ItemVehicleAgencyBinding) holder.binding).dividerLine.setVisibility(8);
    }

    public void dataBindingTo(ItemVehicleAgencyBinding itemVehicleAgencyBinding, DealerInfo dealerInfo) {
        itemVehicleAgencyBinding.setDealerInfo(dealerInfo);
    }

    public void setLocationInfo(double mCurrentLat, double mCurrentLon, String mAddress) {
        this.mCurrentLat = mCurrentLat;
        this.mCurrentLon = mCurrentLon;
        this.mAddress = mAddress;
    }

    private void showMakeCallDialog(final DealerInfo dealerInfo) {
        MobclickAgent.onEvent(this.mContext, "dealer_call");
        Builder builder = new Builder(this.mContext, 3);
        builder.setMessage(dealerInfo.dealermobile);
        builder.setPositiveButton(this.mContext.getString(R.string.cancel), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(this.mContext.getString(R.string.shop_item_call_tips), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                EventBus.getDefault().post(new DealerCallEvent(dealerInfo.dealermobile));
                Map<String, String> map = new HashMap();
                map.put("jxsid", dealerInfo.id + "");
                map.put("iscall", "1");
                VehicleAgencyDetailAdapter.this.mLogGatherInfo.addLogBtnEvent("app_btn_jxs_mobile_click", map);
            }
        });
        builder.create().show();
    }
}
