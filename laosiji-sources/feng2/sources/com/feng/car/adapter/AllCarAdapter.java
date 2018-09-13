package com.feng.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.CarModleComparisonActivity;
import com.feng.car.activity.CarPhotoListActivity;
import com.feng.car.activity.NewSubjectActivity;
import com.feng.car.activity.SingleConfigureActivity;
import com.feng.car.activity.VehicleClassDetailActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ItemCarModelLayoutBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.car.RecommendCarxInfo;
import com.feng.car.event.CarComparisonAnmEvent;
import com.feng.car.event.RefreshEvent;
import com.feng.car.utils.JsonUtil;
import com.feng.car.view.CommonDialog;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class AllCarAdapter extends MvvmBaseAdapter<RecommendCarxInfo, ItemCarModelLayoutBinding> {
    private boolean isHideSomeFunctions = false;
    private int mCarSeriesID = 0;
    private String mCarSeriesName;
    private List<Integer> mCompareList = FengApplication.getInstance().getSparkDB().getCarComparisonIDList();

    public AllCarAdapter(Context context, int carSeriesID, String carSeriesName, List<RecommendCarxInfo> list) {
        super(context, list);
        this.mCarSeriesID = carSeriesID;
        this.mCarSeriesName = carSeriesName;
        if (this.mContext instanceof NewSubjectActivity) {
            this.isHideSomeFunctions = true;
        }
    }

    public void refreshLocal() {
        this.mCompareList = FengApplication.getInstance().getSparkDB().getCarComparisonIDList();
        notifyDataSetChanged();
    }

    public void onBaseBindViewHolder(final MvvmViewHolder<ItemCarModelLayoutBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        final RecommendCarxInfo carxInfo = (RecommendCarxInfo) this.mList.get(position);
        if (carxInfo.carx.isconfig == 0) {
            ((ItemCarModelLayoutBinding) holder.binding).llVehiclesConfig.setAlpha(0.4f);
        } else {
            ((ItemCarModelLayoutBinding) holder.binding).llVehiclesConfig.setAlpha(1.0f);
        }
        if (carxInfo.carx.imagecount <= 3) {
            ((ItemCarModelLayoutBinding) holder.binding).llVehiclesPicture.setAlpha(0.4f);
        } else {
            ((ItemCarModelLayoutBinding) holder.binding).llVehiclesPicture.setAlpha(1.0f);
        }
        ((ItemCarModelLayoutBinding) holder.binding).llVehiclesConfig.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (carxInfo.carx.isconfig == 0) {
                    ((BaseActivity) AllCarAdapter.this.mContext).showSecondTypeToast((int) R.string.car_no_config);
                    return;
                }
                MobclickAgent.onEvent(AllCarAdapter.this.mContext, "cartype_data");
                Intent intent = new Intent(AllCarAdapter.this.mContext, SingleConfigureActivity.class);
                intent.putExtra("carsid", AllCarAdapter.this.mCarSeriesID);
                intent.putExtra("carxid", carxInfo.carx.id);
                intent.putExtra("name", carxInfo.carx.name);
                intent.putExtra("cars_name", AllCarAdapter.this.mCarSeriesName);
                AllCarAdapter.this.mContext.startActivity(intent);
                MobclickAgent.onEvent(AllCarAdapter.this.mContext, "dealer_cartype_data");
            }
        });
        ((ItemCarModelLayoutBinding) holder.binding).llVehiclesPicture.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (carxInfo.carx.imagecount > 3) {
                    Intent intent = new Intent(AllCarAdapter.this.mContext, CarPhotoListActivity.class);
                    intent.putExtra("id", AllCarAdapter.this.mCarSeriesID);
                    intent.putExtra("name", AllCarAdapter.this.mCarSeriesName);
                    intent.putExtra("carx_name", carxInfo.carx.name);
                    intent.putExtra("carxid", carxInfo.carx.id);
                    intent.putExtra("year", carxInfo.carx.year);
                    AllCarAdapter.this.mContext.startActivity(intent);
                    MobclickAgent.onEvent(AllCarAdapter.this.mContext, "dealer_cartype_pic");
                }
            }
        });
        if (this.mCompareList.contains(Integer.valueOf(carxInfo.carx.id))) {
            ((ItemCarModelLayoutBinding) holder.binding).tvVehiclesCompare.setText("已对比");
            ((ItemCarModelLayoutBinding) holder.binding).tvVehiclesCompare.setSelected(true);
        } else {
            if (carxInfo.carx.isconfig == 0) {
                ((ItemCarModelLayoutBinding) holder.binding).tvVehiclesCompare.setAlpha(0.4f);
            } else {
                ((ItemCarModelLayoutBinding) holder.binding).tvVehiclesCompare.setAlpha(1.0f);
            }
            ((ItemCarModelLayoutBinding) holder.binding).tvVehiclesCompare.setText("对比");
            ((ItemCarModelLayoutBinding) holder.binding).tvVehiclesCompare.setSelected(false);
        }
        ((ItemCarModelLayoutBinding) holder.binding).llVehiclesCompare.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (((ItemCarModelLayoutBinding) holder.binding).tvVehiclesCompare.isSelected()) {
                    List<DialogItemEntity> list = new ArrayList();
                    list.add(new DialogItemEntity(AllCarAdapter.this.mContext.getString(R.string.affirm), false));
                    CommonDialog.showCommonDialog(AllCarAdapter.this.mContext, AllCarAdapter.this.mContext.getString(R.string.remove_model_from_pk_tips), list, new OnDialogItemClickListener() {
                        public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                            FengApplication.getInstance().getSparkDB().delCarComparisonRecond(carxInfo.carx.id + "");
                            ((ItemCarModelLayoutBinding) holder.binding).tvVehiclesCompare.setText("对比");
                            ((ItemCarModelLayoutBinding) holder.binding).tvVehiclesCompare.setSelected(false);
                            AllCarAdapter.this.mCompareList.remove(Integer.valueOf(carxInfo.carx.id));
                            List<CarModelInfo> list = JsonUtil.fromJson(SharedUtil.getString(AllCarAdapter.this.mContext, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY), new TypeToken<ArrayList<CarModelInfo>>() {
                            });
                            if (list == null) {
                                list = new ArrayList();
                            }
                            if (list.remove(carxInfo.carx)) {
                                SharedUtil.putString(AllCarAdapter.this.mContext, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY, JsonUtil.toJson(list));
                            }
                            EventBus.getDefault().post(new RefreshEvent());
                        }
                    });
                } else if (carxInfo.carx.isconfig == 0) {
                    ((BaseActivity) AllCarAdapter.this.mContext).showSecondTypeToast((int) R.string.car_no_config);
                    ((ItemCarModelLayoutBinding) holder.binding).tvVehiclesCompare.setAlpha(0.4f);
                    return;
                } else if (AllCarAdapter.this.mCompareList.size() >= 50) {
                    ((BaseActivity) AllCarAdapter.this.mContext).showThirdTypeToast((int) R.string.car_add_max_hint);
                } else {
                    FengApplication.getInstance().getSparkDB().addCarComparisonRecond(AllCarAdapter.this.mCarSeriesName, carxInfo.carx);
                    ((ItemCarModelLayoutBinding) holder.binding).tvVehiclesCompare.setText("已对比");
                    ((ItemCarModelLayoutBinding) holder.binding).tvVehiclesCompare.setSelected(true);
                    AllCarAdapter.this.mCompareList.add(Integer.valueOf(carxInfo.carx.id));
                    List<CarModelInfo> list2 = JsonUtil.fromJson(SharedUtil.getString(AllCarAdapter.this.mContext, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY), new TypeToken<ArrayList<CarModelInfo>>() {
                    });
                    if (list2 == null) {
                        list2 = new ArrayList();
                    }
                    if (list2.size() >= 9) {
                        list2.remove(0);
                    }
                    list2.add(carxInfo.carx);
                    SharedUtil.putString(AllCarAdapter.this.mContext, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY, JsonUtil.toJson(list2));
                    int[] loc = new int[2];
                    ((ItemCarModelLayoutBinding) holder.binding).tvVehiclesCompare.getLocationInWindow(loc);
                    EventBus.getDefault().post(new CarComparisonAnmEvent(loc));
                }
                AllCarAdapter.this.umengClick(3);
            }
        });
        ((ItemCarModelLayoutBinding) holder.binding).ivCarModelState.setVisibility(0);
        if (carxInfo.carx.state == 10) {
            ((ItemCarModelLayoutBinding) holder.binding).ivCarModelState.setImageResource(R.drawable.icon_vehicle_will_sell);
        } else if (carxInfo.carx.state == 30) {
            ((ItemCarModelLayoutBinding) holder.binding).ivCarModelState.setImageResource(R.drawable.icon_vehicle_stop_product_but_sell);
        } else if (carxInfo.carx.state == 40) {
            ((ItemCarModelLayoutBinding) holder.binding).ivCarModelState.setImageResource(R.drawable.icon_vehicle_stop_sell);
        } else {
            ((ItemCarModelLayoutBinding) holder.binding).ivCarModelState.setVisibility(8);
        }
        ((ItemCarModelLayoutBinding) holder.binding).llParent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intentTo = new Intent(AllCarAdapter.this.mContext, VehicleClassDetailActivity.class);
                intentTo.putExtra("id", AllCarAdapter.this.mCarSeriesID);
                intentTo.putExtra("name", AllCarAdapter.this.mCarSeriesName);
                intentTo.putExtra("carxid", carxInfo.carx.id);
                intentTo.putExtra("year", carxInfo.carx.year);
                intentTo.putExtra("carx_name", carxInfo.carx.name);
                AllCarAdapter.this.mContext.startActivity(intentTo);
                if (AllCarAdapter.this.isHideSomeFunctions) {
                    MobclickAgent.onEvent(AllCarAdapter.this.mContext, "car_cartype");
                } else {
                    MobclickAgent.onEvent(AllCarAdapter.this.mContext, "findcar_cartype_cartype");
                }
            }
        });
        ((ItemCarModelLayoutBinding) holder.binding).tvVehiclesEngine.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
            }
        });
        ((ItemCarModelLayoutBinding) holder.binding).ivVehiclesRecommendReason.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (carxInfo.carxaudio.discussinfo.id > 0) {
                    carxInfo.carxaudio.intentToViewPoint(AllCarAdapter.this.mContext, false);
                }
            }
        });
        if (this.isHideSomeFunctions && this.mList.size() - 1 == position) {
            ((ItemCarModelLayoutBinding) holder.binding).vLineBottom.setVisibility(0);
        } else {
            ((ItemCarModelLayoutBinding) holder.binding).vLineBottom.setVisibility(8);
        }
        if (carxInfo.carx.getAvgPrice().equals("暂无")) {
            ((ItemCarModelLayoutBinding) holder.binding).tvCarMiddlePrice.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_54_000000));
        } else {
            ((ItemCarModelLayoutBinding) holder.binding).tvCarMiddlePrice.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_c9272f));
        }
    }

    private void umengClick(int type) {
        if (type == 1) {
            if (this.isHideSomeFunctions) {
                MobclickAgent.onEvent(this.mContext, "car_cartype_data");
            } else {
                MobclickAgent.onEvent(this.mContext, "cartype_data");
            }
        } else if (type == 2) {
            if (this.isHideSomeFunctions) {
                MobclickAgent.onEvent(this.mContext, "car_cartype_pic");
            } else {
                MobclickAgent.onEvent(this.mContext, "cartype_pic");
            }
        } else if (type != 3) {
        } else {
            if (this.isHideSomeFunctions) {
                MobclickAgent.onEvent(this.mContext, "car_cartype_vs");
            } else {
                MobclickAgent.onEvent(this.mContext, "findcar_cartype_vs");
            }
        }
    }

    public ItemCarModelLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return ItemCarModelLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(ItemCarModelLayoutBinding itemVehiclesBinding, RecommendCarxInfo recommendCarxInfo) {
        itemVehiclesBinding.setCarSeriesName(this.mCarSeriesName);
        itemVehiclesBinding.setCarInfo(recommendCarxInfo.carx);
        itemVehiclesBinding.setSnsInfo(recommendCarxInfo.carxaudio);
        itemVehiclesBinding.setRecommendInfo(recommendCarxInfo);
    }
}
