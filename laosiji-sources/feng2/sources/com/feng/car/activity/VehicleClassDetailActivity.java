package com.feng.car.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CarModelTabAdapter;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.VehicleAgencyDetailAdapter;
import com.feng.car.databinding.ActivityVehicleClassDetailBinding;
import com.feng.car.databinding.ItemVehiclesBinding;
import com.feng.car.databinding.SelectMiddleTitleBinding;
import com.feng.car.databinding.TabMenuItemLayoutBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.car.DealerInfo;
import com.feng.car.entity.car.DealerInfoList;
import com.feng.car.entity.car.PriceVo;
import com.feng.car.entity.car.RecommendCarxInfo;
import com.feng.car.entity.car.RecommendCarxInfoList;
import com.feng.car.event.CarComparisonAnmEvent;
import com.feng.car.event.CarModelChangeImageEvent;
import com.feng.car.event.DealerCallEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.RefreshEvent;
import com.feng.car.event.SearchCityEvent;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.BizierEvaluator2;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.MapUtil;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.google.gson.reflect.TypeToken;
import com.stub.StubApp;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VehicleClassDetailActivity extends LocationBaseActivity<ActivityVehicleClassDetailBinding> {
    private boolean isRemoveHeaderView = true;
    private String mCallingTaskNum = "";
    private int mCarModelID;
    private Map<String, List<CarModelInfo>> mCarModelMap = new TreeMap(new Comparator<String>() {
        public int compare(String o1, String o2) {
            return o2.compareTo(o1);
        }
    });
    private String mCarModelName;
    private int mCarSeriesID;
    private String mCarSeriesName = "";
    private int mCurrentCityId = 131;
    private DealerInfoList mDealerInfoCloneList = new DealerInfoList();
    private DealerInfoList mDealerInfoList = new DealerInfoList();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SelectMiddleTitleBinding mMiddleTitleBinding;
    private ItemVehiclesBinding mModelInfoHeaderBinding;
    private boolean mPhotoListShowFlag;
    private RecommendCarxInfo mRecommendCarxInfo;
    private RecommendCarxInfoList mRecommendCarxInfoList = new RecommendCarxInfoList();
    private VehicleAgencyDetailAdapter mVehicleAgencyDetailAdapter;
    private String mYear = "";

    static {
        StubApp.interface11(3185);
    }

    protected native void onCreate(Bundle bundle);

    public int setBaseContentView() {
        return R.layout.activity_vehicle_class_detail;
    }

    public void getLocalIntentData() {
        this.mCity = MapUtil.newInstance().getCurrentCityName();
        this.mCurrentCityId = MapUtil.newInstance().getCurrentCityId();
        this.mCarSeriesID = getIntent().getIntExtra("id", 0);
        this.mCarSeriesName = getIntent().getStringExtra("name");
        this.mCarModelName = getIntent().getStringExtra("carx_name");
        this.mCarModelID = getIntent().getIntExtra("carxid", 0);
        this.mYear = getIntent().getStringExtra("year");
    }

    public void initView() {
        super.initView();
        if (TextUtils.isEmpty(this.mYear)) {
            this.mYear = "";
        }
        this.mPhotoListShowFlag = false;
        this.mMiddleTitleBinding = SelectMiddleTitleBinding.inflate(this.mInflater);
        this.mModelInfoHeaderBinding = ItemVehiclesBinding.inflate(LayoutInflater.from(this));
        this.mVehicleAgencyDetailAdapter = new VehicleAgencyDetailAdapter(this, this.mDealerInfoList.getDealerInfoList(), this.mLogGatherInfo);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mVehicleAgencyDetailAdapter);
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).lrvCarShopAgency.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).lrvCarShopAgency.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).lrvCarShopAgency.setRefreshProgressStyle(2);
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).lrvCarShopAgency.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).lrvCarShopAgency, State.Normal);
                VehicleClassDetailActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                VehicleClassDetailActivity.this.checkPermission(true);
            }
        });
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).lrvCarShopAgency.setNoMore(true);
        this.mLRecyclerViewAdapter.removeFooterView(this.mLRecyclerViewAdapter.getFooterView());
        this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
        this.mMiddleTitleBinding.llParent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (VehicleClassDetailActivity.this.mCarModelMap.size() > 0) {
                    if (VehicleClassDetailActivity.this.mPhotoListShowFlag) {
                        VehicleClassDetailActivity.this.mSwipeBackLayout.setCanScroll(true);
                        ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).llSelectCarModel.setVisibility(8);
                        ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).viewShade.setVisibility(8);
                        VehicleClassDetailActivity.this.mPhotoListShowFlag = false;
                        VehicleClassDetailActivity.this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
                        return;
                    }
                    ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).llSelectCarModel.setVisibility(0);
                    ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).llSelectCarModel.setBackgroundResource(R.drawable.bg_white);
                    ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).llSelectCarModel.getBackground().mutate().setAlpha(255);
                    ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).viewShade.setVisibility(0);
                    VehicleClassDetailActivity.this.mPhotoListShowFlag = true;
                    VehicleClassDetailActivity.this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_up_yellow);
                    VehicleClassDetailActivity.this.mSwipeBackLayout.setCanScroll(false);
                    MobclickAgent.onEvent(VehicleClassDetailActivity.this, "dealer_droplist");
                }
            }
        });
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).llBubbleLeftMapContainer.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intentTo = new Intent(VehicleClassDetailActivity.this, DistributorMapActivity.class);
                intentTo.putExtra("DATA_JSON", JsonUtil.toJson(VehicleClassDetailActivity.this.mDealerInfoList.getDealerInfoList()));
                intentTo.putExtra("id", -1);
                VehicleClassDetailActivity.this.startActivity(intentTo);
                MobclickAgent.onEvent(VehicleClassDetailActivity.this, "dealer_map");
            }
        });
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).llBubbleRightCityContainer.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                VehicleClassDetailActivity.this.startActivity(new Intent(VehicleClassDetailActivity.this, CityListActivity.class));
                MobclickAgent.onEvent(VehicleClassDetailActivity.this, "dealer_location");
            }
        });
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).viewShade.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (VehicleClassDetailActivity.this.mPhotoListShowFlag) {
                    ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).llSelectCarModel.setVisibility(8);
                    ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).viewShade.setVisibility(8);
                    VehicleClassDetailActivity.this.mPhotoListShowFlag = false;
                    VehicleClassDetailActivity.this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
                }
            }
        });
        this.mRootBinding.titleLine.titlebarMiddleParent.removeAllViews();
        this.mRootBinding.titleLine.titlebarMiddleParent.addView(this.mMiddleTitleBinding.getRoot());
        initTitleBarRightPk();
        showLaoSiJiDialog();
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tvAgencyBubbleLocationCity.setText(this.mCity);
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).lrvCarShopAgency.setLScrollListener(new LRecyclerView$LScrollListener() {
            public void onScrollUp() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(true));
            }

            public void onScrollDown() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(false));
            }

            public void onScrolled(int distanceX, int distanceY) {
            }

            public void onScrollStateChanged(int state) {
            }
        });
    }

    private void initHeaderInfo() {
        if (this.mRecommendCarxInfo != null) {
            final List<Integer> mCompareList = FengApplication.getInstance().getSparkDB().getCarComparisonIDList();
            this.mModelInfoHeaderBinding.setCarSeriesName(this.mCarSeriesName);
            this.mModelInfoHeaderBinding.setCarInfo(this.mRecommendCarxInfo.carx);
            this.mModelInfoHeaderBinding.setSnsInfo(this.mRecommendCarxInfo.carxaudio);
            this.mModelInfoHeaderBinding.setRecommendInfo(this.mRecommendCarxInfo);
            if (mCompareList.contains(Integer.valueOf(this.mRecommendCarxInfo.carx.id))) {
                this.mModelInfoHeaderBinding.tvVehiclesCompare.setText("已对比");
                this.mModelInfoHeaderBinding.tvVehiclesCompare.setSelected(true);
            } else {
                if (this.mRecommendCarxInfo.carx.isconfig == 0) {
                    this.mModelInfoHeaderBinding.tvVehiclesCompare.setAlpha(0.4f);
                } else {
                    this.mModelInfoHeaderBinding.tvVehiclesCompare.setAlpha(1.0f);
                }
                this.mModelInfoHeaderBinding.tvVehiclesCompare.setText("对比");
                this.mModelInfoHeaderBinding.tvVehiclesCompare.setSelected(false);
            }
            if (this.mRecommendCarxInfo.carx.isconfig == 0) {
                this.mModelInfoHeaderBinding.llVehiclesConfig.setAlpha(0.4f);
            } else {
                this.mModelInfoHeaderBinding.llVehiclesConfig.setAlpha(1.0f);
            }
            if (this.mRecommendCarxInfo.carx.imagecount <= 3) {
                this.mModelInfoHeaderBinding.llVehiclesPicture.setAlpha(0.4f);
            } else {
                this.mModelInfoHeaderBinding.llVehiclesPicture.setAlpha(1.0f);
            }
            this.mModelInfoHeaderBinding.ivVehiclesRecommendReason.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (VehicleClassDetailActivity.this.mRecommendCarxInfo.carxaudio.discussinfo.id > 0) {
                        VehicleClassDetailActivity.this.mRecommendCarxInfo.carxaudio.intentToViewPoint(VehicleClassDetailActivity.this, false);
                    }
                }
            });
            this.mModelInfoHeaderBinding.llVehiclesCompare.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (VehicleClassDetailActivity.this.mModelInfoHeaderBinding.tvVehiclesCompare.isSelected()) {
                        List<DialogItemEntity> list = new ArrayList();
                        list.add(new DialogItemEntity(VehicleClassDetailActivity.this.getString(R.string.affirm), false));
                        CommonDialog.showCommonDialog(VehicleClassDetailActivity.this, VehicleClassDetailActivity.this.getString(R.string.remove_model_from_pk_tips), list, new OnDialogItemClickListener() {
                            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                                FengApplication.getInstance().getSparkDB().delCarComparisonRecond(VehicleClassDetailActivity.this.mRecommendCarxInfo.carx.id + "");
                                VehicleClassDetailActivity.this.mModelInfoHeaderBinding.tvVehiclesCompare.setText("对比");
                                VehicleClassDetailActivity.this.mModelInfoHeaderBinding.tvVehiclesCompare.setSelected(false);
                                mCompareList.remove(Integer.valueOf(VehicleClassDetailActivity.this.mRecommendCarxInfo.carx.id));
                                List<CarModelInfo> list = JsonUtil.fromJson(SharedUtil.getString(VehicleClassDetailActivity.this, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY), new TypeToken<ArrayList<CarModelInfo>>() {
                                });
                                if (list == null) {
                                    list = new ArrayList();
                                }
                                if (list.remove(VehicleClassDetailActivity.this.mRecommendCarxInfo.carx)) {
                                    SharedUtil.putString(VehicleClassDetailActivity.this, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY, JsonUtil.toJson(list));
                                }
                                EventBus.getDefault().post(new RefreshEvent());
                            }
                        });
                    } else if (VehicleClassDetailActivity.this.mRecommendCarxInfo.carx.isconfig == 0) {
                        VehicleClassDetailActivity.this.showSecondTypeToast((int) R.string.car_no_config);
                        VehicleClassDetailActivity.this.mModelInfoHeaderBinding.tvVehiclesCompare.setAlpha(0.4f);
                        return;
                    } else if (mCompareList.size() >= 50) {
                        VehicleClassDetailActivity.this.showThirdTypeToastLong(R.string.car_add_max_hint);
                    } else {
                        FengApplication.getInstance().getSparkDB().addCarComparisonRecond(VehicleClassDetailActivity.this.mCarSeriesName, VehicleClassDetailActivity.this.mRecommendCarxInfo.carx);
                        VehicleClassDetailActivity.this.mModelInfoHeaderBinding.tvVehiclesCompare.setText("已对比");
                        VehicleClassDetailActivity.this.mModelInfoHeaderBinding.tvVehiclesCompare.setSelected(true);
                        mCompareList.add(Integer.valueOf(VehicleClassDetailActivity.this.mRecommendCarxInfo.carx.id));
                        List<CarModelInfo> list2 = JsonUtil.fromJson(SharedUtil.getString(VehicleClassDetailActivity.this, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY), new TypeToken<ArrayList<CarModelInfo>>() {
                        });
                        if (list2 == null) {
                            list2 = new ArrayList();
                        }
                        if (list2.size() >= 9) {
                            list2.remove(0);
                        }
                        list2.add(VehicleClassDetailActivity.this.mRecommendCarxInfo.carx);
                        SharedUtil.putString(VehicleClassDetailActivity.this, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY, JsonUtil.toJson(list2));
                        int[] loc = new int[2];
                        VehicleClassDetailActivity.this.mModelInfoHeaderBinding.tvVehiclesCompare.getLocationInWindow(loc);
                        EventBus.getDefault().post(new CarComparisonAnmEvent(loc));
                    }
                    MobclickAgent.onEvent(VehicleClassDetailActivity.this, "dealer_cartype_vs");
                }
            });
            if (this.mRecommendCarxInfo.carx.state == 10) {
                this.mModelInfoHeaderBinding.ivCarModelState.setImageResource(R.drawable.icon_vehicle_will_sell);
                this.mModelInfoHeaderBinding.ivCarModelState.setVisibility(0);
                hideNoDealersEmptyView();
            } else if (this.mRecommendCarxInfo.carx.state == 30) {
                this.mModelInfoHeaderBinding.ivCarModelState.setImageResource(R.drawable.icon_vehicle_stop_product_but_sell);
                this.mModelInfoHeaderBinding.ivCarModelState.setVisibility(0);
                hideNoDealersEmptyView();
            } else if (this.mRecommendCarxInfo.carx.state == 40) {
                this.mModelInfoHeaderBinding.ivCarModelState.setImageResource(R.drawable.icon_vehicle_stop_sell);
                this.mModelInfoHeaderBinding.ivCarModelState.setVisibility(0);
                showNoDealersEmptyView();
            } else {
                this.mModelInfoHeaderBinding.ivCarModelState.setVisibility(8);
                hideNoDealersEmptyView();
            }
            if (this.mRecommendCarxInfo.carxaudio.id != 0) {
                this.mModelInfoHeaderBinding.ivVehiclesRecommendReason.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        VehicleClassDetailActivity.this.mRecommendCarxInfo.carxaudio.intentToViewPoint(VehicleClassDetailActivity.this, false);
                        MobclickAgent.onEvent(VehicleClassDetailActivity.this, "dealer_recommend");
                    }
                });
            } else {
                this.mModelInfoHeaderBinding.rlVehicleNameTitle.setOnClickListener(null);
            }
            this.mModelInfoHeaderBinding.llVehiclesConfig.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (VehicleClassDetailActivity.this.mRecommendCarxInfo.carx.isconfig == 0) {
                        VehicleClassDetailActivity.this.showSecondTypeToast((int) R.string.car_no_config);
                        return;
                    }
                    MobclickAgent.onEvent(VehicleClassDetailActivity.this, "cartype_data");
                    Intent intent = new Intent(VehicleClassDetailActivity.this, SingleConfigureActivity.class);
                    intent.putExtra("carsid", VehicleClassDetailActivity.this.mCarSeriesID);
                    intent.putExtra("carxid", VehicleClassDetailActivity.this.mRecommendCarxInfo.carx.id);
                    intent.putExtra("name", VehicleClassDetailActivity.this.mRecommendCarxInfo.carx.name);
                    intent.putExtra("cars_name", VehicleClassDetailActivity.this.mCarSeriesName);
                    VehicleClassDetailActivity.this.startActivity(intent);
                    MobclickAgent.onEvent(VehicleClassDetailActivity.this, "dealer_cartype_data");
                }
            });
            this.mModelInfoHeaderBinding.llVehiclesPicture.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (VehicleClassDetailActivity.this.mRecommendCarxInfo.carx.imagecount > 3) {
                        Intent intent = new Intent(VehicleClassDetailActivity.this, CarPhotoListActivity.class);
                        intent.putExtra("id", VehicleClassDetailActivity.this.mCarSeriesID);
                        intent.putExtra("name", VehicleClassDetailActivity.this.mCarSeriesName);
                        intent.putExtra("carx_name", VehicleClassDetailActivity.this.mRecommendCarxInfo.carx.name);
                        intent.putExtra("carxid", VehicleClassDetailActivity.this.mRecommendCarxInfo.carx.id);
                        intent.putExtra("year", VehicleClassDetailActivity.this.mRecommendCarxInfo.carx.year);
                        VehicleClassDetailActivity.this.startActivity(intent);
                        MobclickAgent.onEvent(VehicleClassDetailActivity.this, "dealer_cartype_pic");
                    }
                }
            });
            this.mModelInfoHeaderBinding.rlCarMiddlePrice.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    Intent intentTo = new Intent(VehicleClassDetailActivity.this, PriceRankingCarXActivity.class);
                    intentTo.putExtra("carsid", VehicleClassDetailActivity.this.mCarSeriesID);
                    intentTo.putExtra("name", VehicleClassDetailActivity.this.mCarSeriesName);
                    intentTo.putExtra("carxid", VehicleClassDetailActivity.this.mRecommendCarxInfo.carx.id);
                    intentTo.putExtra("feng_type", 0);
                    VehicleClassDetailActivity.this.startActivity(intentTo);
                }
            });
            if (this.mRecommendCarxInfo.carx.getAvgPrice().equals("暂无")) {
                this.mModelInfoHeaderBinding.tvCarMiddlePrice.setTextColor(ContextCompat.getColor(this, R.color.color_54_000000));
            } else {
                this.mModelInfoHeaderBinding.tvCarMiddlePrice.setTextColor(ContextCompat.getColor(this, R.color.color_c9272f));
            }
            if (this.isRemoveHeaderView) {
                this.isRemoveHeaderView = false;
                this.mLRecyclerViewAdapter.addHeaderView(this.mModelInfoHeaderBinding.getRoot());
                this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
            }
        }
    }

    private void initCarModelTabs(String json) throws JSONException {
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tvCarSeries.setText(this.mCarSeriesName);
        if (this.mCarModelID == 0) {
            ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tvCarSeries.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.icon_confirm), null);
            ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tvCarSeries.setSelected(true);
        }
        parserCarsModelData(json);
        List<String> list = new ArrayList();
        for (String key : this.mCarModelMap.keySet()) {
            list.add(key);
        }
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).vpCarModel.setOffscreenPageLimit(1);
        final CarModelTabAdapter adapter = new CarModelTabAdapter(getSupportFragmentManager(), this.mCarModelID, list, this.mCarModelMap);
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).vpCarModel.setAdapter(adapter);
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tabCarModelYear.setupWithViewPager(((ActivityVehicleClassDetailBinding) this.mBaseBinding).vpCarModel);
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tabCarModelYear.setTabMode(0);
        for (int i = 0; i < ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tabCarModelYear.getTabCount(); i++) {
            ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tabCarModelYear.getTabAt(i).setCustomView(getCarModelTabView(((String) list.get(i)) + "款"));
        }
        int selIndex = list.indexOf(this.mYear);
        if (selIndex > 0) {
            ((ActivityVehicleClassDetailBinding) this.mBaseBinding).vpCarModel.setCurrentItem(selIndex);
        }
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).vpCarModel.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (adapter.getCurrentItem(position) != null) {
                    adapter.getCurrentItem(position).checkCarModelId(VehicleClassDetailActivity.this.mCarModelID);
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tvCarSeries.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).tvCarSeries.isSelected()) {
                    VehicleClassDetailActivity.this.mSwipeBackLayout.setCanScroll(true);
                    ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).llSelectCarModel.setVisibility(8);
                    ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).viewShade.setVisibility(8);
                    VehicleClassDetailActivity.this.mPhotoListShowFlag = false;
                    VehicleClassDetailActivity.this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
                    return;
                }
                CarModelInfo info = new CarModelInfo();
                info.id = 0;
                info.year = "";
                info.name = VehicleClassDetailActivity.this.mCarSeriesName;
                EventBus.getDefault().post(new CarModelChangeImageEvent(info, CarModelChangeImageEvent.TYPE_CAR_DEALER));
            }
        });
    }

    private View getCarModelTabView(String titleName) {
        TabMenuItemLayoutBinding binding = TabMenuItemLayoutBinding.inflate(this.mInflater, ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tabCarModelYear, false);
        binding.tvTitle.setText(titleName);
        return binding.getRoot();
    }

    private void parserCarsModelData(String json) throws JSONException {
        BaseListModel<RecommendCarxInfo> baseListModel = new BaseListModel();
        baseListModel.parser(RecommendCarxInfo.class, new JSONObject(json));
        if (baseListModel.list3.size() > 0) {
            this.mRecommendCarxInfoList.addAll(baseListModel.list3);
            for (RecommendCarxInfo recommendCarxInfo : this.mRecommendCarxInfoList.getRecommendCarxInfoList()) {
                if (this.mCarModelID == recommendCarxInfo.carx.id) {
                    this.mRecommendCarxInfo = recommendCarxInfo;
                    initHeaderInfo();
                }
                if (this.mCarModelMap.containsKey(recommendCarxInfo.carx.year)) {
                    ((List) this.mCarModelMap.get(recommendCarxInfo.carx.year)).add(recommendCarxInfo.carx);
                } else {
                    List<CarModelInfo> carModelInfos = new ArrayList();
                    carModelInfos.add(recommendCarxInfo.carx);
                    this.mCarModelMap.put(recommendCarxInfo.carx.year, carModelInfos);
                }
            }
        }
    }

    private void getAllCarInfoData() {
        Map<String, Object> map = new HashMap();
        map.put("carsid", String.valueOf(this.mCarSeriesID));
        FengApplication.getInstance().httpRequest("car/getallspec/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                VehicleClassDetailActivity.this.hideProgress();
                VehicleClassDetailActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        VehicleClassDetailActivity.this.getAllCarInfoData();
                    }
                });
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                VehicleClassDetailActivity.this.hideProgress();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        VehicleClassDetailActivity.this.initCarModelTabs(jsonResult.getJSONObject("body").getJSONObject("carx").toString());
                        if (VehicleClassDetailActivity.this.mCurrentCityId != 0) {
                            VehicleClassDetailActivity.this.loadShopAgenciesData();
                            return;
                        }
                        return;
                    }
                    FengApplication.getInstance().checkCode("car/getallspec/", code);
                    VehicleClassDetailActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            VehicleClassDetailActivity.this.getAllCarInfoData();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("car/getallspec/", content, e);
                    VehicleClassDetailActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            VehicleClassDetailActivity.this.getAllCarInfoData();
                        }
                    });
                }
            }
        });
    }

    private void getAllCarxPriceData(final CarModelChangeImageEvent event) {
        Map<String, Object> map = new HashMap();
        if (event != null) {
            map.put("specid", String.valueOf(event.info.id));
        } else {
            map.put("specid", String.valueOf(this.mCarModelID));
        }
        map.put("cityid", String.valueOf(this.mCurrentCityId));
        FengApplication.getInstance().httpRequest("dealer/spec/preferential/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                VehicleClassDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                if (event != null) {
                    VehicleClassDetailActivity.this.changeUiShowGone();
                }
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                VehicleClassDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                if (event != null) {
                    VehicleClassDetailActivity.this.changeUiShowGone();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        VehicleClassDetailActivity.this.changeCarModel(event);
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        if (!jsonBody.isNull("price")) {
                            JSONObject jsonPrice = jsonBody.getJSONObject("price");
                            PriceVo priceVo = new PriceVo();
                            priceVo.parser(jsonPrice);
                            VehicleClassDetailActivity.this.mModelInfoHeaderBinding.tvCarMiddlePrice.setText(priceVo.getCarxOwnerPrice());
                            if (priceVo.ownernum <= 0) {
                                VehicleClassDetailActivity.this.mModelInfoHeaderBinding.tvCount.setText(R.string.empty_cars_price);
                                return;
                            } else {
                                VehicleClassDetailActivity.this.mModelInfoHeaderBinding.tvCount.setText("全国 " + priceVo.ownernum + "条成交价");
                                return;
                            }
                        }
                        return;
                    }
                    VehicleClassDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    if (event != null) {
                        VehicleClassDetailActivity.this.changeUiShowGone();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (event != null) {
                        VehicleClassDetailActivity.this.changeUiShowGone();
                    }
                    VehicleClassDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }
        });
    }

    private void loadShopAgenciesData() {
        Map<String, Object> map = new HashMap();
        map.put("seriesid", String.valueOf(this.mCarSeriesID));
        map.put("cityid", String.valueOf(this.mCurrentCityId));
        FengApplication.getInstance().httpRequest("dealer/list/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                VehicleClassDetailActivity.this.hideProgress();
                ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).rlBubbleMapContainer.setVisibility(0);
                VehicleClassDetailActivity.this.showAgenciesRequestErrorView();
            }

            public void onStart() {
            }

            public void onFinish() {
                VehicleClassDetailActivity.this.hideProgress();
                ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).rlBubbleMapContainer.setVisibility(0);
                ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).lrvCarShopAgency.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).lrvCarShopAgency, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                VehicleClassDetailActivity.this.hideProgress();
                ((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).rlBubbleMapContainer.setVisibility(0);
                VehicleClassDetailActivity.this.showAgenciesRequestErrorView();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        int i;
                        DealerInfo dealerInfo;
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        JSONArray hotDealersArray = jsonBody.getJSONArray("hotdealers");
                        JSONArray normalDealersArray = jsonBody.getJSONArray("normaldealers");
                        VehicleClassDetailActivity.this.mDealerInfoList.clear();
                        VehicleClassDetailActivity.this.mDealerInfoCloneList.clear();
                        int hotDealersSize = hotDealersArray.length();
                        for (i = 0; i < hotDealersSize; i++) {
                            dealerInfo = new DealerInfo();
                            dealerInfo.parser(hotDealersArray.getJSONObject(i));
                            if (!(dealerInfo.baidulon == 0.0d || dealerInfo.baidulat == 0.0d)) {
                                VehicleClassDetailActivity.this.mDealerInfoList.add(dealerInfo);
                            }
                        }
                        VehicleClassDetailActivity.this.calculateAgencyDistance(VehicleClassDetailActivity.this.mDealerInfoList.getDealerInfoList());
                        int normalDealersSize = normalDealersArray.length();
                        List<DealerInfo> normalDealers = new ArrayList();
                        for (i = 0; i < normalDealersSize; i++) {
                            dealerInfo = new DealerInfo();
                            dealerInfo.parser(normalDealersArray.getJSONObject(i));
                            if (!(dealerInfo.baidulon == 0.0d || dealerInfo.baidulat == 0.0d)) {
                                normalDealers.add(dealerInfo);
                            }
                        }
                        VehicleClassDetailActivity.this.calculateAgencyDistance(normalDealers);
                        Collections.sort(normalDealers, new Comparator<DealerInfo>() {
                            public int compare(DealerInfo lhs, DealerInfo rhs) {
                                return new Double(lhs.localDistance).compareTo(new Double(rhs.localDistance));
                            }
                        });
                        VehicleClassDetailActivity.this.mDealerInfoList.addAll(normalDealers);
                        VehicleClassDetailActivity.this.hideEmptyView();
                        if (VehicleClassDetailActivity.this.mDealerInfoList != null && VehicleClassDetailActivity.this.mDealerInfoList.size() > 0) {
                            VehicleClassDetailActivity.this.mDealerInfoCloneList.addAll(VehicleClassDetailActivity.this.mDealerInfoList.getDealerInfoList());
                        }
                        if (VehicleClassDetailActivity.this.mRecommendCarxInfo != null && VehicleClassDetailActivity.this.mRecommendCarxInfo.carx.state == 40) {
                            VehicleClassDetailActivity.this.mDealerInfoList.clear();
                        }
                        if (VehicleClassDetailActivity.this.mRecommendCarxInfoList.getPosition(VehicleClassDetailActivity.this.mCarModelID) >= 0) {
                            if (VehicleClassDetailActivity.this.mDealerInfoList.size() == 0) {
                                VehicleClassDetailActivity.this.showNoDealersEmptyView();
                            } else {
                                VehicleClassDetailActivity.this.hideNoDealersEmptyView();
                            }
                        } else if (VehicleClassDetailActivity.this.mDealerInfoList.size() == 0) {
                            VehicleClassDetailActivity.this.showNoDealersWithCityEmptyView();
                        } else {
                            VehicleClassDetailActivity.this.hideNoDealersWithCityEmptyView();
                        }
                        RecyclerViewStateUtils.setFooterViewState(((ActivityVehicleClassDetailBinding) VehicleClassDetailActivity.this.mBaseBinding).lrvCarShopAgency, State.Normal);
                        VehicleClassDetailActivity.this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
                    } else if (VehicleClassDetailActivity.this.mDealerInfoList.size() > 0) {
                        FengApplication.getInstance().checkCode("user/ywf/comment/", code);
                    } else {
                        VehicleClassDetailActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                VehicleClassDetailActivity.this.loadShopAgenciesData();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (VehicleClassDetailActivity.this.mDealerInfoList.size() > 0) {
                        VehicleClassDetailActivity.this.showSecondTypeToast((int) R.string.analysis_data_failed);
                    } else {
                        VehicleClassDetailActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                VehicleClassDetailActivity.this.loadShopAgenciesData();
                            }
                        });
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("user/ywf/comment/", content, e);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CarModelChangeImageEvent event) {
        if (event.type == CarModelChangeImageEvent.TYPE_CAR_DEALER) {
            getAllCarxPriceData(event);
        }
    }

    private void changeCarModel(CarModelChangeImageEvent event) {
        if (event != null) {
            if (this.mCarModelID != event.info.id) {
                this.mCarModelID = event.info.id;
                this.mYear = event.info.year;
                this.mMiddleTitleBinding.tvTitle.setText(event.info.name);
                int position = this.mRecommendCarxInfoList.getPosition(this.mCarModelID);
                if (position >= 0) {
                    this.mRecommendCarxInfo = this.mRecommendCarxInfoList.get(position);
                    initHeaderInfo();
                    hideNoDealersWithCityEmptyView();
                    if (event.info.state == 40) {
                        this.mDealerInfoList.clear();
                        this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
                        showNoDealersEmptyView();
                    } else {
                        if (this.mDealerInfoList.size() == 0 && this.mDealerInfoCloneList.size() > 0) {
                            this.mDealerInfoList.addAll(this.mDealerInfoCloneList.getDealerInfoList());
                        }
                        if (this.mDealerInfoList.size() == 0) {
                            showNoDealersEmptyView();
                        } else {
                            hideNoDealersEmptyView();
                        }
                        this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
                    }
                } else {
                    this.isRemoveHeaderView = true;
                    this.mLRecyclerViewAdapter.removeHeaderView(this.mModelInfoHeaderBinding.getRoot());
                    this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
                    hideNoDealersEmptyView();
                    if (this.mDealerInfoList.size() == 0) {
                        showNoDealersWithCityEmptyView();
                    } else {
                        hideNoDealersWithCityEmptyView();
                    }
                }
                if (event.info.id == 0) {
                    ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tvCarSeries.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.icon_confirm), null);
                    ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tvCarSeries.setSelected(true);
                } else {
                    ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tvCarSeries.setCompoundDrawables(null, null, null, null);
                    ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tvCarSeries.setSelected(false);
                }
                ((ActivityVehicleClassDetailBinding) this.mBaseBinding).lrvCarShopAgency.scrollToPosition(0);
            }
            changeUiShowGone();
        }
    }

    private void changeUiShowGone() {
        this.mSwipeBackLayout.setCanScroll(true);
        this.mPhotoListShowFlag = false;
        this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).llSelectCarModel.setVisibility(8);
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).viewShade.setVisibility(8);
    }

    private void showNoDealersEmptyView() {
        this.mModelInfoHeaderBinding.llNoDealersEmptyContainer.setVisibility(0);
    }

    private void showNoDealersWithCityEmptyView() {
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).llNoDealersWithCity.setVisibility(0);
    }

    private void hideNoDealersEmptyView() {
        this.mModelInfoHeaderBinding.llNoDealersEmptyContainer.setVisibility(8);
    }

    private void hideNoDealersWithCityEmptyView() {
        ((ActivityVehicleClassDetailBinding) this.mBaseBinding).llNoDealersWithCity.setVisibility(8);
    }

    private void showAgenciesRequestErrorView() {
        if (this.mDealerInfoList.size() > 0) {
            showSecondTypeToast((int) R.string.net_abnormal);
        } else {
            showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    VehicleClassDetailActivity.this.loadShopAgenciesData();
                }
            });
        }
    }

    private void calculateAgencyDistance(List<DealerInfo> list) {
        if (this.mCurrentLat > 0.0d && this.mCurrentLon > 0.0d && list != null && list.size() > 0) {
            for (DealerInfo dealerInfo : list) {
                double distance = MapUtil.newInstance().getDistanceBetween2Points(new LatLng(this.mCurrentLat, this.mCurrentLon), new LatLng(dealerInfo.baidulat, dealerInfo.baidulon));
                dealerInfo.localDistance = distance;
                dealerInfo.localDistanceTips = MapUtil.newInstance().getFormateDistance(distance);
            }
        }
    }

    public void onReceiveLocationData(BDLocation bdLocation) {
        this.mCurrentLat = bdLocation.getLatitude();
        this.mCurrentLon = bdLocation.getLongitude();
        this.mAddress = bdLocation.getAddrStr();
        this.mCity = MapUtil.newInstance().cityNameFormat(bdLocation.getCity());
        String strLocal = SharedUtil.getString(this, "local_sel_city");
        if (!TextUtils.isEmpty(strLocal)) {
            this.mCity = strLocal;
        }
        if (this.mCurrentLat > 0.0d && this.mCurrentLon > 0.0d && !TextUtils.isEmpty(this.mCity)) {
            ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tvAgencyBubbleLocationCity.setText(this.mCity);
            this.mVehicleAgencyDetailAdapter.setLocationInfo(this.mCurrentLat, this.mCurrentLon, this.mAddress);
            int cityId = MapUtil.newInstance().getCityIdByName(this, this.mCity);
            if (this.mCurrentCityId != cityId) {
                this.mCurrentCityId = cityId;
            }
        }
        if (this.mRecommendCarxInfoList.size() > 0) {
            loadShopAgenciesData();
        } else {
            getAllCarInfoData();
        }
        if (this.mCarModelID > 0) {
            getAllCarxPriceData(null);
        }
        stopBaiduLocation();
    }

    protected void onBaiDuLocationError() {
        if (this.mRecommendCarxInfoList.size() > 0) {
            loadShopAgenciesData();
        } else {
            getAllCarInfoData();
        }
        if (this.mCarModelID > 0) {
            getAllCarxPriceData(null);
        }
        stopBaiduLocation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchCityEvent searchCityEvent) {
        if (searchCityEvent != null && !TextUtils.isEmpty(searchCityEvent.cityInfo.name)) {
            ((ActivityVehicleClassDetailBinding) this.mBaseBinding).tvAgencyBubbleLocationCity.setText(searchCityEvent.cityInfo.name);
            if (this.mCurrentCityId != searchCityEvent.cityInfo.id) {
                this.mCurrentCityId = searchCityEvent.cityInfo.id;
                loadShopAgenciesData();
                if (this.mCarModelID > 0) {
                    getAllCarxPriceData(null);
                    return;
                }
                return;
            }
            this.mCurrentCityId = searchCityEvent.cityInfo.id;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DealerCallEvent dealerCallEvent) {
        if (dealerCallEvent != null && !TextUtils.isEmpty(dealerCallEvent.dealermobile)) {
            checkCallTelPermission(dealerCallEvent.dealermobile);
        }
    }

    private void checkCallTelPermission(String dealer4stel) {
        if (!TextUtils.isEmpty(dealer4stel)) {
            this.mCallingTaskNum = dealer4stel;
            if (VERSION.SDK_INT < 23) {
                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + this.mCallingTaskNum)));
                this.mCallingTaskNum = "";
            } else if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
                requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 50002);
            } else {
                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + this.mCallingTaskNum)));
                this.mCallingTaskNum = "";
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50005) {
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50002) {
            try {
                if (permissions[0].equals("android.permission.CALL_PHONE") && grantResults[0] == 0) {
                    Intent intentTo = new Intent("android.intent.action.CALL", Uri.parse("tel:" + this.mCallingTaskNum));
                    if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
                        showSecondTypeToast((int) R.string.authorization_failed_cannot_make_phonecall);
                        this.mCallingTaskNum = "";
                        return;
                    }
                    startActivity(intentTo);
                } else if (grantResults[0] == 0) {
                    this.mCallingTaskNum = "";
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    showPermissionsDialog(false);
                } else {
                    showPermissionsDialog(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.mCallingTaskNum = "";
            }
        }
    }

    private void showPermissionsDialog(final boolean notInquiry) {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.go_open_permissions), false));
        CommonDialog.showCommonDialog(ActivityManager.getInstance().getCurrentActivity(), getString(R.string.no_open_call_phone_permissions), "", getString(R.string.repulse_permissions), list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (notInquiry) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", VehicleClassDetailActivity.this.getPackageName(), null));
                    VehicleClassDetailActivity.this.startActivityForResult(intent, 50005);
                    return;
                }
                VehicleClassDetailActivity.this.checkCallTelPermission(VehicleClassDetailActivity.this.mCallingTaskNum);
            }
        }, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                VehicleClassDetailActivity.this.mCallingTaskNum = "";
            }
        }, true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CarComparisonAnmEvent event) {
        playAnimation(event.point);
    }

    private void playAnimation(int[] position) {
        final ImageView ivAnmView = new ImageView(this);
        ivAnmView.setImageResource(R.drawable.vehicles_compare);
        ivAnmView.setScaleType(ScaleType.MATRIX);
        ((ViewGroup) getWindow().getDecorView()).addView(ivAnmView);
        int[] des = getTitleBarPKLocation();
        Point startPosition = new Point(position[0], position[1]);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BizierEvaluator2(new Point(new Point(des[0], des[1]).x, startPosition.y)), new Object[]{startPosition, endPosition});
        valueAnimator.setDuration(375);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Point point = (Point) valueAnimator.getAnimatedValue();
                ivAnmView.setX((float) point.x);
                ivAnmView.setY((float) point.y);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ((ViewGroup) VehicleClassDetailActivity.this.getWindow().getDecorView()).removeView(ivAnmView);
            }
        });
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(300);
                    VehicleClassDetailActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            EventBus.getDefault().post(new RefreshEvent());
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((ActivityVehicleClassDetailBinding) this.mBaseBinding).lrvCarShopAgency.setIsScrollDown(true);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }

    public String getLogCurrentPage() {
        if (this.mCarModelID == 0) {
            return "app_car_series_dealer?carsid=" + this.mCarSeriesID + "&cityid=" + this.mCurrentCityId;
        }
        return "app_car_model_dealer?carsid=" + this.mCarSeriesID + "&carxid=" + this.mCarModelID + "&cityid=" + this.mCurrentCityId;
    }
}
