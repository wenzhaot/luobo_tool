package com.feng.car.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CarModelAddAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.car.CarModelInfoList;
import com.feng.car.entity.car.RecommendCarxInfo;
import com.feng.car.event.ClosePageEvent;
import com.feng.car.event.RefreshEvent;
import com.feng.car.event.UploadPriceSelectEvent;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.MapUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class AddCarModelActivity extends BaseActivity<CommonRecyclerviewBinding> {
    public static final int FROM_NORMAL_ADD = 0;
    public static final int FROM_WEB_ADD = 1;
    private CarModelAddAdapter mAdapter;
    private CarModelInfoList mCarModelInfoList;
    private int mCarSeriesID;
    private String mCarSeriesName = "";
    private boolean mIsUploadPrice = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int mType;

    public int setBaseContentView() {
        return R.layout.common_recyclerview;
    }

    public void getLocalIntentData() {
        Intent intent = getIntent();
        this.mCarSeriesID = intent.getIntExtra("id", 0);
        this.mType = intent.getIntExtra("feng_type", 0);
        this.mCarSeriesName = intent.getStringExtra("name");
        this.mIsUploadPrice = intent.getBooleanExtra("type", false);
        this.mCityId = MapUtil.newInstance().getCurrentCityId();
    }

    public void initView() {
        initNormalTitleBar("添加车型");
        if (this.mIsUploadPrice) {
            this.mCarModelInfoList = new CarModelInfoList(0);
        } else {
            this.mCarModelInfoList = new CarModelInfoList(1);
        }
        this.mAdapter = new CarModelAddAdapter(this, this.mCarModelInfoList.getCarModelInfoList(), this.mCarSeriesName);
        this.mAdapter.setIsUploadPrice(this.mIsUploadPrice);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setNoMore(true);
        this.mLRecyclerViewAdapter.removeFooterView(this.mLRecyclerViewAdapter.getFooterView());
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) AddCarModelActivity.this.mBaseBinding).recyclerview, State.Normal);
                AddCarModelActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                if (AddCarModelActivity.this.mIsUploadPrice) {
                    AddCarModelActivity.this.getAllCarInfoData();
                } else {
                    AddCarModelActivity.this.loadCarsModelList();
                }
            }
        });
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshing(true);
        this.mAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                CarModelInfo info = AddCarModelActivity.this.mCarModelInfoList.get(position);
                if (AddCarModelActivity.this.mIsUploadPrice) {
                    EventBus.getDefault().post(new UploadPriceSelectEvent(info.id, info.name, 3));
                    AddCarModelActivity.this.finish();
                } else if (info.isconfig == 0) {
                    AddCarModelActivity.this.showSecondTypeToast((int) R.string.car_no_config);
                } else if (AddCarModelActivity.this.mAdapter.getLocalList().contains(Integer.valueOf(info.id))) {
                    AddCarModelActivity.this.showThirdTypeToast((int) R.string.has_added_carx);
                } else {
                    info.name = AddCarModelActivity.this.mCarSeriesName + " " + info.name;
                    FengApplication.getInstance().getSparkDB().addCarComparisonRecond(info);
                    EventBus.getDefault().post(new ClosePageEvent());
                    if (AddCarModelActivity.this.mType == 0) {
                        List<CarModelInfo> list = JsonUtil.fromJson(SharedUtil.getString(AddCarModelActivity.this, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY), new TypeToken<ArrayList<CarModelInfo>>() {
                        });
                        if (list == null) {
                            list = new ArrayList();
                        }
                        if (list.size() >= 9) {
                            list.remove(0);
                        }
                        list.add(info);
                        SharedUtil.putString(AddCarModelActivity.this, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY, JsonUtil.toJson(list));
                        EventBus.getDefault().post(new RefreshEvent());
                    } else {
                        EventBus.getDefault().post(new RefreshEvent(1, info));
                    }
                    AddCarModelActivity.this.finish();
                }
            }
        });
    }

    private void loadCarsModelList() {
        Map<String, Object> map = new HashMap();
        map.put("carsid", String.valueOf(this.mCarSeriesID));
        FengApplication.getInstance().httpRequest("car/getpkallspec/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (AddCarModelActivity.this.mCarModelInfoList.size() > 0) {
                    AddCarModelActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    AddCarModelActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((CommonRecyclerviewBinding) AddCarModelActivity.this.mBaseBinding).recyclerview.forceToRefresh();
                        }
                    });
                }
                ((CommonRecyclerviewBinding) AddCarModelActivity.this.mBaseBinding).recyclerview.refreshComplete();
            }

            public void onStart() {
            }

            public void onFinish() {
                ((CommonRecyclerviewBinding) AddCarModelActivity.this.mBaseBinding).recyclerview.refreshComplete();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (AddCarModelActivity.this.mCarModelInfoList.size() > 0) {
                    AddCarModelActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    AddCarModelActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((CommonRecyclerviewBinding) AddCarModelActivity.this.mBaseBinding).recyclerview.forceToRefresh();
                        }
                    });
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonBrand = jsonResult.getJSONObject("body").getJSONObject("carx");
                        BaseListModel<CarModelInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CarModelInfo.class, jsonBrand);
                        AddCarModelActivity.this.carsModelGroup(baseListModel.list);
                    } else if (AddCarModelActivity.this.mCarModelInfoList.size() > 0) {
                        FengApplication.getInstance().checkCode("car/getpkallspec/", code);
                    } else {
                        AddCarModelActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                AddCarModelActivity.this.loadCarsModelList();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    AddCarModelActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((CommonRecyclerviewBinding) AddCarModelActivity.this.mBaseBinding).recyclerview.forceToRefresh();
                        }
                    });
                    FengApplication.getInstance().upLoadTryCatchLog("car/getpkallspec/", content, e);
                }
            }
        });
    }

    private void carsModelGroup(List<CarModelInfo> list) {
        List<CarModelInfo> carModelInfoList;
        this.mCarModelInfoList.clear();
        Map<String, List<CarModelInfo>> mapYear = new LinkedHashMap();
        for (CarModelInfo info : list) {
            if (this.mIsUploadPrice || !(info.isconfig == 0 || (info.state == 40 && info.getGuidePrice().equals("暂无")))) {
                if (mapYear.containsKey(info.getYear())) {
                    ((List) mapYear.get(info.getYear())).add(info);
                } else {
                    carModelInfoList = new ArrayList();
                    carModelInfoList.add(info);
                    mapYear.put(info.getYear(), carModelInfoList);
                }
            }
        }
        Map<String, List<CarModelInfo>> listEngine = new LinkedHashMap();
        Map<String, String> mapStop = new LinkedHashMap();
        for (String key : mapYear.keySet()) {
            List<CarModelInfo> infoList = (List) mapYear.get(key);
            mapStop.clear();
            listEngine.clear();
            for (CarModelInfo info2 : infoList) {
                if (!(info2.state == 30 || info2.state == 40)) {
                    mapStop.put(info2.engine, "");
                }
                if (listEngine.containsKey(info2.engine)) {
                    ((List) listEngine.get(info2.engine)).add(info2);
                } else {
                    carModelInfoList = new ArrayList();
                    carModelInfoList.add(info2);
                    listEngine.put(info2.engine, carModelInfoList);
                }
            }
            List<CarModelInfo> listStop = new ArrayList();
            for (String engineKey : listEngine.keySet()) {
                if (mapStop.containsKey(engineKey)) {
                    this.mCarModelInfoList.addAll((List) listEngine.get(engineKey));
                } else {
                    listStop.addAll((Collection) listEngine.get(engineKey));
                }
            }
            this.mCarModelInfoList.addAll(listStop);
        }
        if (this.mCarModelInfoList.size() > 0) {
            setFlagsInCarModel();
        } else if (this.mIsUploadPrice) {
            showEmptyView((int) R.string.carx_empty, (int) R.drawable.icon_blank_vs);
        } else {
            showEmptyView((int) R.string.carseries_no_config, (int) R.drawable.icon_blank_vs);
        }
    }

    private void setFlagsInCarModel() {
        int i = 0;
        while (i < this.mCarModelInfoList.size()) {
            if (!(i == this.mCarModelInfoList.size() - 1 || (this.mCarModelInfoList.get(i).getYear() + this.mCarModelInfoList.get(i).engine).equals(this.mCarModelInfoList.get(i + 1).getYear() + this.mCarModelInfoList.get(i + 1).engine))) {
                this.mCarModelInfoList.get(i).poslastflag = 1;
                this.mCarModelInfoList.get(i).localshowyear = false;
            }
            if (!(i == 0 || (this.mCarModelInfoList.get(i).getYear() + this.mCarModelInfoList.get(i).engine).equals(this.mCarModelInfoList.get(i - 1).getYear() + this.mCarModelInfoList.get(i - 1).engine))) {
                this.mCarModelInfoList.get(i).posfirstflag = 1;
                if (this.mCarModelInfoList.get(i).getYear().equals(this.mCarModelInfoList.get(i - 1).getYear())) {
                    this.mCarModelInfoList.get(i).localshowyear = false;
                } else {
                    this.mCarModelInfoList.get(i).localshowyear = true;
                }
            }
            i++;
        }
        if (this.mCarModelInfoList.size() > 0) {
            this.mCarModelInfoList.get(0).posfirstflag = 1;
            this.mCarModelInfoList.get(0).localshowyear = true;
            this.mCarModelInfoList.get(this.mCarModelInfoList.size() - 1).poslastflag = 1;
        }
    }

    private void getAllCarInfoData() {
        Map<String, Object> map = new HashMap();
        map.put("carsid", String.valueOf(this.mCarSeriesID));
        map.put("cityid", String.valueOf(this.mCityId));
        FengApplication.getInstance().httpRequest("car/getallspec/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (AddCarModelActivity.this.mCarModelInfoList.size() > 0) {
                    AddCarModelActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    AddCarModelActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((CommonRecyclerviewBinding) AddCarModelActivity.this.mBaseBinding).recyclerview.forceToRefresh();
                        }
                    });
                }
                ((CommonRecyclerviewBinding) AddCarModelActivity.this.mBaseBinding).recyclerview.refreshComplete();
            }

            public void onStart() {
            }

            public void onFinish() {
                ((CommonRecyclerviewBinding) AddCarModelActivity.this.mBaseBinding).recyclerview.refreshComplete();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (AddCarModelActivity.this.mCarModelInfoList.size() > 0) {
                    AddCarModelActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    AddCarModelActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((CommonRecyclerviewBinding) AddCarModelActivity.this.mBaseBinding).recyclerview.forceToRefresh();
                        }
                    });
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonObject = jsonResult.getJSONObject("body").getJSONObject("carx");
                        BaseListModel<RecommendCarxInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(RecommendCarxInfo.class, jsonObject);
                        if (baseListModel.list.size() > 0) {
                            List<CarModelInfo> carModelInfoList = new ArrayList();
                            for (RecommendCarxInfo info : baseListModel.list) {
                                carModelInfoList.add(info.carx);
                            }
                            AddCarModelActivity.this.carsModelGroup(carModelInfoList);
                            return;
                        }
                        return;
                    }
                    FengApplication.getInstance().checkCode("car/getallspec/", code);
                } catch (Exception e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("car/getallspec/", content, e);
                }
            }
        });
    }

    public String getLogCurrentPage() {
        return "app_car_pk_sel_carmodel?carsid=" + this.mCarSeriesID;
    }
}
