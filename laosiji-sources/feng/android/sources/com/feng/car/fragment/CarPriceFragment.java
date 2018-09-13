package com.feng.car.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.PriceRankingCarXActivity;
import com.feng.car.activity.PricesModelChartActivity;
import com.feng.car.activity.PricesSeriesChartActivity;
import com.feng.car.adapter.PriceNearCityAdapter;
import com.feng.car.adapter.PriceRankingCarxAdapter;
import com.feng.car.adapter.VehiclePriceAdapter;
import com.feng.car.databinding.CarPriceCityHeaderBinding;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.car.RecommendCarxInfo;
import com.feng.car.entity.car.SeriesPriceVo;
import com.feng.car.entity.car.SeriesPriceVoList;
import com.feng.car.entity.lcoation.CityPriceInfo;
import com.feng.car.event.OwnerSwitchCityEven;
import com.feng.car.event.ReadyToScrollEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.MapUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.R;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView.LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CarPriceFragment extends BaseFragment<CommonRecyclerviewBinding> {
    private VehiclePriceAdapter mAdapter;
    private String mCarJson = "";
    private int mCarModelID = 0;
    private CarModelInfo mCarModelInfo;
    private List<RecommendCarxInfo> mCarModelList = new ArrayList();
    private int mCarSeriesID = 0;
    private PriceRankingCarxAdapter mCarxAdapter;
    private int mCityId = 0;
    private List<CityPriceInfo> mCityNearList = new ArrayList();
    private CityPriceInfo mCityPriceInfo;
    private int mCurrentPage = 1;
    private CarPriceCityHeaderBinding mHeaderBinding;
    private boolean mISVisible;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private SeriesPriceVoList mPriceList = new SeriesPriceVoList();
    private PriceNearCityAdapter mPriceNearCityAdapter;
    private CarSeriesInfo mSeriesInfo;
    private int mTotalPage = 0;
    private int mType;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        this.mCarSeriesID = args.getInt("id", 0);
        this.mCarModelID = args.getInt(HttpConstant.CARX_ID, 0);
        this.mType = args.getInt("type", 1);
        this.mCarJson = args.getString(FengConstant.DATA_JSON);
        this.mCityId = args.getInt(HttpConstant.CITYID);
        try {
            if (this.mCarModelID == 0) {
                this.mSeriesInfo = new CarSeriesInfo();
                this.mSeriesInfo.parser(new JSONObject(this.mCarJson));
            } else {
                this.mCarModelInfo = new CarModelInfo();
                this.mCarModelInfo.parser(new JSONObject(this.mCarJson));
            }
            if (this.mType == 1) {
                this.mCityPriceInfo = new CityPriceInfo();
                this.mCityPriceInfo.parser(new JSONObject(args.getString("titleJSON")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backTop() {
        if (isAdded() && this.mBind != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.scrollToPosition(0);
        }
    }

    public static CarPriceFragment newInstance(int carSeriesID, int carModelID, int type, int cityId, String titleJSON, String json) {
        Bundle args = new Bundle();
        args.putInt("id", carSeriesID);
        args.putInt(HttpConstant.CARX_ID, carModelID);
        args.putInt("type", type);
        args.putString("titleJSON", titleJSON);
        args.putString(FengConstant.DATA_JSON, json);
        args.putInt(HttpConstant.CITYID, cityId);
        CarPriceFragment fragment = new CarPriceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setCityPriceInfo(CityPriceInfo cityPriceInfo) {
        this.mCityPriceInfo = cityPriceInfo;
    }

    protected int setLayoutId() {
        return 2130903204;
    }

    protected void initView() {
        String areaName;
        this.mLinearLayoutManager = new LinearLayoutManager(this.mActivity);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(this.mLinearLayoutManager);
        if (this.mType == 1) {
            areaName = MapUtil.newInstance().getCityNameById(this.mActivity, this.mCityId);
        } else if (this.mType == 2) {
            areaName = MapUtil.newInstance().getProvinceNameById(this.mActivity, MapUtil.newInstance().getProvinceIdByCityId(this.mActivity, this.mCityId));
        } else {
            areaName = "全国";
        }
        this.mAdapter = new VehiclePriceAdapter(this.mActivity, areaName, this.mPriceList.getSeriesPriceVoList());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(false);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setIsOwner(true);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CarPriceFragment.this.mBind).recyclerview, State.Normal);
                CarPriceFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                CarPriceFragment.this.mCurrentPage = 1;
                CarPriceFragment.this.getRefreshData();
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (!RecyclerViewStateUtils.isRecylerViewLoading(((CommonRecyclerviewBinding) CarPriceFragment.this.mBind).recyclerview)) {
                    if (CarPriceFragment.this.mCurrentPage > CarPriceFragment.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(CarPriceFragment.this.mActivity, ((CommonRecyclerviewBinding) CarPriceFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(CarPriceFragment.this.mActivity, ((CommonRecyclerviewBinding) CarPriceFragment.this.mBind).recyclerview, 20, State.Loading, null);
                    CarPriceFragment.this.getPriceList();
                }
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLScrollListener(new LScrollListener() {
            public void onScrollUp() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(true));
                ((CommonRecyclerviewBinding) CarPriceFragment.this.mBind).recyclerview.setIsOwner(true);
            }

            public void onScrollDown() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(false));
                ((CommonRecyclerviewBinding) CarPriceFragment.this.mBind).recyclerview.setIsOwner(false);
            }

            public void onScrolled(int distanceX, int distanceY) {
            }

            public void onScrollStateChanged(int state) {
            }
        });
        this.mHeaderBinding = CarPriceCityHeaderBinding.inflate(this.mInflater, ((CommonRecyclerviewBinding) this.mBind).recyclerview, false);
        this.mPriceNearCityAdapter = new PriceNearCityAdapter(this.mActivity, this.mCarModelID != 0, this.mCityNearList);
        this.mHeaderBinding.rvNearbyCity.setLayoutManager(new GridLayoutManager(this.mActivity, 2));
        this.mHeaderBinding.rvNearbyCity.setNestedScrollingEnabled(false);
        this.mHeaderBinding.rvNearbyCity.setAdapter(this.mPriceNearCityAdapter);
        this.mPriceNearCityAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                EventBus.getDefault().post(new OwnerSwitchCityEven(((CityPriceInfo) CarPriceFragment.this.mCityNearList.get(position)).city, CarPriceFragment.this.mCarSeriesID, CarPriceFragment.this.mCarModelID));
            }
        });
        this.mHeaderBinding.btnAllTransactionPrice.setOnClickListener(this);
        this.mCarxAdapter = new PriceRankingCarxAdapter(this.mActivity, this.mCarModelList);
        this.mHeaderBinding.rvCarsInSale.setAdapter(this.mCarxAdapter);
        this.mHeaderBinding.rvCarsInSale.setNestedScrollingEnabled(false);
        this.mHeaderBinding.rvCarsInSale.setLayoutManager(new LinearLayoutManager(this.mActivity));
        this.mCarxAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(CarPriceFragment.this.mActivity, PriceRankingCarXActivity.class);
                intent.putExtra(HttpConstant.CARS_ID, CarPriceFragment.this.mCarSeriesID);
                intent.putExtra(HttpConstant.CITYID, CarPriceFragment.this.mCityId);
                intent.putExtra("name", (String) CarPriceFragment.this.mSeriesInfo.name.get());
                intent.putExtra(HttpConstant.CARX_ID, ((RecommendCarxInfo) CarPriceFragment.this.mCarModelList.get(position)).carx.id);
                intent.putExtra(FengConstant.FENGTYPE, CarPriceFragment.this.mType);
                CarPriceFragment.this.startActivity(intent);
            }
        });
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeaderBinding.getRoot());
        this.mHeaderBinding.llParent.setVisibility(8);
    }

    private void initHead() {
        this.mHeaderBinding.llParent.setVisibility(0);
        if (this.mType == 1) {
            String tuneTip;
            String tuneRange;
            String priceTip;
            String price;
            this.mHeaderBinding.tvTuneRank.setText(this.mCityPriceInfo.price.getRank());
            this.mHeaderBinding.tvTransactionMsgCount.setText(this.mCityPriceInfo.price.ownernum + "条");
            this.mHeaderBinding.llTransactionParent.setOnClickListener(this);
            if (this.mCarModelID == 0) {
                tuneTip = this.mCityPriceInfo.price.getPreferentialTip();
                tuneRange = this.mCityPriceInfo.price.getPreferentialForNew();
                priceTip = this.mSeriesInfo.getCarPriceText();
                price = this.mSeriesInfo.getCarPrice();
                if (this.mCityPriceInfo.price.preferential == 0.0d || this.mCityPriceInfo.price.preferential == 100.0d) {
                    this.mHeaderBinding.tvTuneRange.setTextColor(ContextCompat.getColor(this.mActivity, R.color.color_54_000000));
                    this.mHeaderBinding.tvPercent.setVisibility(8);
                    if (this.mCityPriceInfo.price.preferential == 0.0d) {
                        this.mHeaderBinding.tvTuneRank.setVisibility(8);
                    }
                } else if (this.mSeriesInfo.preferential - 100.0d > 0.0d) {
                    this.mHeaderBinding.tvTuneRange.setTextColor(ContextCompat.getColor(this.mActivity, 2131558513));
                } else {
                    this.mHeaderBinding.tvTuneRange.setTextColor(ContextCompat.getColor(this.mActivity, 2131558513));
                }
            } else {
                this.mHeaderBinding.tvPercent.setText(" 万");
                tuneTip = this.mCityPriceInfo.price.getOwnerPriceTip();
                tuneRange = this.mCityPriceInfo.price.getOwnerPriceNew();
                priceTip = this.mCarModelInfo.getCarPriceText();
                price = this.mCarModelInfo.getGuidePrice();
                if (this.mCityPriceInfo.price.ownerprice <= 0.0d) {
                    this.mHeaderBinding.tvTuneRange.setTextColor(ContextCompat.getColor(this.mActivity, R.color.color_54_000000));
                    this.mHeaderBinding.tvPercent.setVisibility(8);
                    this.mHeaderBinding.tvTuneRank.setVisibility(8);
                } else {
                    this.mHeaderBinding.tvTuneRange.setTextColor(ContextCompat.getColor(this.mActivity, 2131558513));
                }
            }
            this.mHeaderBinding.tvTuneTip.setText(tuneTip);
            this.mHeaderBinding.tvTuneRange.setText(tuneRange);
            this.mHeaderBinding.tvAdviserPriceTip.setText(priceTip);
            this.mHeaderBinding.tvAdviserPrice.setText(price);
        } else {
            this.mHeaderBinding.llCityParent.setVisibility(8);
            this.mHeaderBinding.llProvinceParent.setVisibility(0);
            this.mPriceNearCityAdapter.notifyDataSetChanged();
        }
        if (this.mType == 2) {
            this.mHeaderBinding.btnAllTransactionPrice.setText(MapUtil.newInstance().getProvinceNameById(this.mActivity, MapUtil.newInstance().getProvinceIdByCityId(this.mActivity, this.mCityId)) + "全部城市成交价");
        }
        if (this.mCarModelID > 0) {
            this.mHeaderBinding.tvOnSaleCarCount.setVisibility(8);
            this.mHeaderBinding.rvCarsInSale.setVisibility(8);
            this.mHeaderBinding.rvCarsInSale.setNestedScrollingEnabled(false);
        }
    }

    public void onSingleClick(View v) {
        switch (v.getId()) {
            case 2131624888:
                EventBus.getDefault().post(new ReadyToScrollEvent(this.mCarSeriesID, this.mCarModelID));
                return;
            case 2131624893:
                Intent intent;
                if (this.mCarModelID == 0) {
                    intent = new Intent(this.mActivity, PricesSeriesChartActivity.class);
                    intent.putExtra(HttpConstant.CITYID, this.mCityId);
                    if (this.mType == 2) {
                        intent.putExtra(HttpConstant.PROVINCEID, MapUtil.newInstance().getProvinceIdByCityId(this.mActivity, this.mCityId));
                    } else {
                        intent.putExtra(HttpConstant.PROVINCEID, 0);
                    }
                    intent.putExtra(FengConstant.DATA_JSON, this.mCarJson);
                    intent.putExtra(HttpConstant.CARS_ID, this.mCarSeriesID);
                } else {
                    intent = new Intent(this.mActivity, PricesModelChartActivity.class);
                    intent.putExtra(HttpConstant.CITYID, this.mCityId);
                    if (this.mType == 2) {
                        intent.putExtra(HttpConstant.PROVINCEID, MapUtil.newInstance().getProvinceIdByCityId(this.mActivity, this.mCityId));
                    } else {
                        intent.putExtra(HttpConstant.PROVINCEID, 0);
                    }
                    intent.putExtra(FengConstant.DATA_JSON, this.mCarJson);
                    intent.putExtra(HttpConstant.CARS_ID, this.mCarSeriesID);
                    intent.putExtra(HttpConstant.CARX_ID, this.mCarModelID);
                }
                startActivity(intent);
                return;
            default:
                return;
        }
    }

    public void scrollTo() {
        if (!isVisible() || !this.mISVisible) {
            return;
        }
        if (this.mPriceList.size() <= 0) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.scrollBy(0, this.mHeaderBinding.llParent.getBottom());
            return;
        }
        this.mLinearLayoutManager.scrollToPositionWithOffset(this.mLRecyclerViewAdapter.getHeaderViewsCount() + 1, 0);
    }

    private void getRefreshData() {
        this.mCurrentPage = 1;
        if (this.mType == 1) {
            initHead();
            if (this.mCarModelID == 0) {
                getAllCarInfoData();
            } else {
                getPriceList();
            }
        } else if (this.mCarModelID == 0) {
            getCarsNearCities();
        } else {
            getCarXNearCities();
        }
    }

    private void getCarsNearCities() {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.CARS_ID, String.valueOf(this.mCarSeriesID));
        if (this.mType == 2) {
            map.put(HttpConstant.PROVINCEID, String.valueOf(MapUtil.newInstance().getProvinceIdByCityId(this.mActivity, this.mCityId)));
        }
        FengApplication.getInstance().httpRequest(HttpConstant.CAR_FINALPRICE_SERIES_TOP_RANK, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (CarPriceFragment.this.mPriceList.size() > 0) {
                    ((BaseActivity) CarPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    CarPriceFragment.this.showErrorView();
                }
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (CarPriceFragment.this.mPriceList.size() > 0) {
                    ((BaseActivity) CarPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    CarPriceFragment.this.showErrorView();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONArray jsonArray = jsonResult.getJSONObject("body").getJSONArray(HttpConstant.PRICE);
                        CarPriceFragment.this.mCityNearList.clear();
                        int size = jsonArray.length();
                        for (int i = 0; i < size; i++) {
                            CityPriceInfo info = new CityPriceInfo();
                            info.parser(jsonArray.getJSONObject(i));
                            CarPriceFragment.this.mCityNearList.add(info);
                        }
                        CarPriceFragment.this.initHead();
                        if (CarPriceFragment.this.mCarModelID == 0) {
                            CarPriceFragment.this.getAllCarInfoData();
                            return;
                        } else {
                            CarPriceFragment.this.getPriceList();
                            return;
                        }
                    }
                    CarPriceFragment.this.showErrorView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCarXNearCities() {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.CARX_ID, String.valueOf(this.mCarModelID));
        if (this.mType == 2) {
            map.put(HttpConstant.PROVINCEID, String.valueOf(MapUtil.newInstance().getProvinceIdByCityId(this.mActivity, this.mCityId)));
        }
        FengApplication.getInstance().httpRequest(HttpConstant.CAR_FINALPRICE_SPEC_TOP_RANK, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (CarPriceFragment.this.mPriceList.size() > 0) {
                    ((BaseActivity) CarPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    CarPriceFragment.this.showErrorView();
                }
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (CarPriceFragment.this.mPriceList.size() > 0) {
                    ((BaseActivity) CarPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    CarPriceFragment.this.showErrorView();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONArray jsonArray = jsonResult.getJSONObject("body").getJSONArray(HttpConstant.PRICE);
                        CarPriceFragment.this.mCityNearList.clear();
                        int size = jsonArray.length();
                        for (int i = 0; i < size; i++) {
                            CityPriceInfo info = new CityPriceInfo();
                            info.parser(jsonArray.getJSONObject(i));
                            CarPriceFragment.this.mCityNearList.add(info);
                        }
                        CarPriceFragment.this.initHead();
                        if (CarPriceFragment.this.mCarModelID == 0) {
                            CarPriceFragment.this.getAllCarInfoData();
                            return;
                        } else {
                            CarPriceFragment.this.getPriceList();
                            return;
                        }
                    }
                    CarPriceFragment.this.showErrorView();
                } catch (JSONException e) {
                    e.printStackTrace();
                    CarPriceFragment.this.showErrorView();
                }
            }
        });
    }

    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        if (this.mBind != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(true);
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    public void onResume() {
        super.onResume();
        this.mISVisible = true;
    }

    public void onPause() {
        super.onPause();
        this.mISVisible = false;
    }

    private void getAllCarInfoData() {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.CARS_ID, String.valueOf(this.mCarSeriesID));
        map.put("territorytype", Integer.valueOf(this.mType));
        map.put(HttpConstant.CITYID, String.valueOf(this.mCityId));
        FengApplication.getInstance().httpRequest(HttpConstant.CAR_FINALPRICE_INSTOCK, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (CarPriceFragment.this.mPriceList.size() > 0) {
                    ((BaseActivity) CarPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    CarPriceFragment.this.showErrorView();
                }
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (CarPriceFragment.this.mPriceList.size() > 0) {
                    ((BaseActivity) CarPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    CarPriceFragment.this.showErrorView();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONArray jsonArray = jsonResult.getJSONObject("body").getJSONArray("list");
                        CarPriceFragment.this.mCarModelList.clear();
                        int size = jsonArray.length();
                        for (int i = 0; i < size; i++) {
                            RecommendCarxInfo rdInfo = new RecommendCarxInfo();
                            rdInfo.parser(jsonArray.getJSONObject(i));
                            CarPriceFragment.this.mCarModelList.add(rdInfo);
                        }
                        CarPriceFragment.this.hideEmptyImage();
                        if (CarPriceFragment.this.mCarModelList.size() <= 0) {
                            CarPriceFragment.this.mHeaderBinding.tvOnSaleCarCount.setVisibility(8);
                        } else {
                            CarPriceFragment.this.mHeaderBinding.tvOnSaleCarCount.setText(CarPriceFragment.this.getString(2131231339, new Object[]{Integer.valueOf(CarPriceFragment.this.mCarModelList.size())}));
                        }
                        CarPriceFragment.this.mCarxAdapter.notifyDataSetChanged();
                        CarPriceFragment.this.getPriceList();
                        return;
                    }
                    CarPriceFragment.this.showErrorView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getPriceList() {
        Map<String, Object> map = new HashMap();
        if (this.mType == 1) {
            map.put("territoryid", String.valueOf(this.mCityId));
        } else if (this.mType == 2) {
            map.put("territoryid", String.valueOf(MapUtil.newInstance().getProvinceIdByCityId(this.mActivity, this.mCityId)));
        }
        map.put(HttpConstant.PAGE, String.valueOf(this.mCurrentPage));
        map.put("territorytype", String.valueOf(this.mType));
        if (this.mCarModelID == 0) {
            map.put("cartype", String.valueOf(0));
            map.put("carid", String.valueOf(this.mCarSeriesID));
        } else {
            map.put("cartype", String.valueOf(1));
            map.put("carid", String.valueOf(this.mCarModelID));
        }
        FengApplication.getInstance().httpRequest(HttpConstant.CAR_FINALPRICE_OWNERPRICE, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (CarPriceFragment.this.mPriceList.size() > 0) {
                    ((BaseActivity) CarPriceFragment.this.mActivity).showSecondTypeToast(2131231188);
                } else {
                    CarPriceFragment.this.showErrorView();
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                ((CommonRecyclerviewBinding) CarPriceFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CarPriceFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (CarPriceFragment.this.mPriceList.size() > 0) {
                    ((BaseActivity) CarPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    CarPriceFragment.this.showErrorView();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        CarPriceFragment.this.hideEmptyImage();
                        ((CommonRecyclerviewBinding) CarPriceFragment.this.mBind).recyclerview.setPullRefreshEnabled(false);
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        BaseListModel<SeriesPriceVo> baseList = new BaseListModel();
                        baseList.parser(SeriesPriceVo.class, jsonBody);
                        if (CarPriceFragment.this.mCurrentPage == 1) {
                            CarPriceFragment.this.mAdapter.setTotalCount(baseList.count);
                            CarPriceFragment.this.mPriceList.clear();
                            CarPriceFragment.this.mTotalPage = baseList.pagecount;
                        }
                        CarPriceFragment.this.mPriceList.addAll(baseList.list);
                        if (CarPriceFragment.this.mCurrentPage == 1 && CarPriceFragment.this.mPriceList.size() == 0) {
                            CarPriceFragment.this.showEmptyLine();
                        } else {
                            CarPriceFragment.this.mAdapter.notifyDataSetChanged();
                        }
                        CarPriceFragment.this.mCurrentPage = CarPriceFragment.this.mCurrentPage + 1;
                    } else if (CarPriceFragment.this.mPriceList.size() > 0) {
                        ((BaseActivity) CarPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                    } else {
                        CarPriceFragment.this.showErrorView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (CarPriceFragment.this.mPriceList.size() > 0) {
                        ((BaseActivity) CarPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                    } else {
                        CarPriceFragment.this.showErrorView();
                    }
                }
            }
        });
    }

    private void hideEmptyImage() {
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(8);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setVisibility(0);
        this.mHeaderBinding.llEmpty.setVisibility(8);
    }

    private void showErrorView() {
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setVisibility(8);
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231188);
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(2130838112);
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((CommonRecyclerviewBinding) CarPriceFragment.this.mBind).recyclerview.forceToRefresh();
            }
        });
    }

    private void showEmptyLine() {
        if (this.mType == 1) {
            this.mHeaderBinding.setCity(MapUtil.newInstance().getCityNameById(this.mActivity, this.mCityId));
        } else if (this.mType == 2) {
            this.mHeaderBinding.setCity(MapUtil.newInstance().getProvinceNameById(this.mActivity, MapUtil.newInstance().getProvinceIdByCityId(this.mActivity, this.mCityId)));
        } else {
            this.mHeaderBinding.setCity("全国");
        }
        this.mHeaderBinding.llEmpty.setVisibility(0);
    }
}
