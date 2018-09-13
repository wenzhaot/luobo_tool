package com.feng.car.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.ConfigClassifyAdapter;
import com.feng.car.adapter.ConfigClassifyAdapter.ItemSelectListener;
import com.feng.car.adapter.SingleConfigAdapter;
import com.feng.car.databinding.SingleConfigureActivityBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.car.carconfig.CarConfigureInfo;
import com.feng.car.entity.car.carconfig.CarConfigureItemInfo;
import com.feng.car.entity.car.carconfig.CarConfigureParent;
import com.feng.car.entity.car.carconfig.ConfigItem;
import com.feng.car.event.RefreshEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.MapUtil;
import com.feng.car.view.vhtableview.SectionDecoration;
import com.feng.car.view.vhtableview.SectionDecoration$DecorationCallback;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

public class SingleConfigureActivity extends BaseActivity<SingleConfigureActivityBinding> implements ItemSelectListener {
    private SingleConfigAdapter mAdapter;
    private String mCarModelName;
    private int mCarModleID = 0;
    private String mCarSeriesName;
    private int mCarsid = 0;
    private ConfigClassifyAdapter mClassifyAdapter;
    private List<ConfigItem> mConfigureItemList = new ArrayList();
    private List<String> mIntData = new ArrayList();
    private LinearLayoutManager mLayoutManager;
    private List<CarConfigureInfo> mList = new ArrayList();

    private void initClassifyView() {
        if (this.mClassifyAdapter == null) {
            ((SingleConfigureActivityBinding) this.mBaseBinding).rvConfigClass.setLayoutManager(new GridLayoutManager(this, 2));
            this.mClassifyAdapter = new ConfigClassifyAdapter(this, this.mConfigureItemList);
            this.mClassifyAdapter.setItemSelectListener(this);
            ((SingleConfigureActivityBinding) this.mBaseBinding).rvConfigClass.setAdapter(this.mClassifyAdapter);
            ((SingleConfigureActivityBinding) this.mBaseBinding).rvConfigClass.setOverScrollMode(2);
        } else {
            this.mClassifyAdapter.notifyDataSetChanged();
        }
        ((SingleConfigureActivityBinding) this.mBaseBinding).ivClassify.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (((SingleConfigureActivityBinding) SingleConfigureActivity.this.mBaseBinding).rvConfigClass.isShown()) {
                    ((SingleConfigureActivityBinding) SingleConfigureActivity.this.mBaseBinding).rvConfigClass.setVisibility(8);
                } else {
                    ((SingleConfigureActivityBinding) SingleConfigureActivity.this.mBaseBinding).rvConfigClass.setVisibility(0);
                }
            }
        });
    }

    public int setBaseContentView() {
        return R.layout.single_configure_activity;
    }

    public void getLocalIntentData() {
        this.mCarsid = getIntent().getIntExtra("carsid", 0);
        this.mCarModleID = getIntent().getIntExtra("carxid", 0);
        this.mCarModelName = getIntent().getStringExtra("name");
        this.mCarSeriesName = getIntent().getStringExtra("cars_name");
    }

    public void initView() {
        hideDefaultTitleBar();
        ((SingleConfigureActivityBinding) this.mBaseBinding).title.setText(R.string.parameter_config);
        ((SingleConfigureActivityBinding) this.mBaseBinding).ivBack.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SingleConfigureActivity.this.finish();
            }
        });
        ((SingleConfigureActivityBinding) this.mBaseBinding).carxName.setText(this.mCarModelName);
        List<Integer> list = FengApplication.getInstance().getSparkDB().getCarComparisonIDList();
        if (list.contains(Integer.valueOf(this.mCarModleID))) {
            ((SingleConfigureActivityBinding) this.mBaseBinding).ivRightImageTwo.setSelected(true);
        }
        if (list.size() >= 50) {
            ((SingleConfigureActivityBinding) this.mBaseBinding).ivRightImageTwo.setSelected(true);
        }
        FengConstant.CAR_MAX_CURRENT_COMPARISON_NUM = list.size();
        ((SingleConfigureActivityBinding) this.mBaseBinding).ivRightImageTwo.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (!((SingleConfigureActivityBinding) SingleConfigureActivity.this.mBaseBinding).ivRightImageTwo.isSelected()) {
                    ((SingleConfigureActivityBinding) SingleConfigureActivity.this.mBaseBinding).ivRightImageTwo.setSelected(true);
                    if (FengConstant.CAR_MAX_CURRENT_COMPARISON_NUM >= 50) {
                        SingleConfigureActivity.this.showThirdTypeToastLong(R.string.car_add_max_hint);
                        return;
                    }
                    CarModelInfo info = new CarModelInfo();
                    info.id = SingleConfigureActivity.this.mCarModleID;
                    info.name = SingleConfigureActivity.this.mCarModelName;
                    FengApplication.getInstance().getSparkDB().addCarComparisonRecond(SingleConfigureActivity.this.mCarSeriesName, info);
                    List<CarModelInfo> list = JsonUtil.fromJson(SharedUtil.getString(SingleConfigureActivity.this, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY), new TypeToken<ArrayList<CarModelInfo>>() {
                    });
                    if (list == null) {
                        list = new ArrayList();
                    }
                    if (list.size() >= 9) {
                        list.remove(0);
                    }
                    SharedUtil.putString(SingleConfigureActivity.this, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY, JsonUtil.toJson(list));
                    EventBus.getDefault().post(new RefreshEvent());
                }
            }
        });
        initClassifyView();
        loadCarsConfigures();
    }

    private void loadCarsConfigures() {
        Map<String, Object> map = new HashMap();
        map.put("carxids", String.valueOf(this.mCarModleID));
        map.put("cityid", String.valueOf(MapUtil.newInstance().getCurrentCityId()));
        FengApplication.getInstance().httpRequest("car/carxpk/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                SingleConfigureActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
                SingleConfigureActivity.this.showLaoSiJiDialog();
            }

            public void onFinish() {
                SingleConfigureActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                SingleConfigureActivity.this.hideProgress();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        JSONObject jsonConfigs = jsonBody.getJSONObject("configs");
                        JSONArray jsonRedcols = jsonBody.getJSONArray("redcols");
                        BaseListModel<CarConfigureParent> baseListModel = new BaseListModel();
                        baseListModel.parser(CarConfigureParent.class, jsonConfigs);
                        List<String> redcolsList = new ArrayList();
                        int size = jsonRedcols.length();
                        for (int i = 0; i < size; i++) {
                            redcolsList.add(jsonRedcols.getString(i));
                        }
                        SingleConfigureActivity.this.assembleData(baseListModel.list, redcolsList);
                        return;
                    }
                    SingleConfigureActivity.this.showSecondTypeToast((int) R.string.retry_tips);
                } catch (Exception e) {
                    e.printStackTrace();
                    SingleConfigureActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }
        });
    }

    private void assembleData(List<CarConfigureParent> list, List<String> redcolsList) {
        for (CarConfigureParent parent : list) {
            for (CarConfigureItemInfo itemInfo : parent.items) {
                ConfigItem item = new ConfigItem();
                item.name = itemInfo.name;
                item.postion = this.mList.size() - 1;
                this.mConfigureItemList.add(item);
                this.mList.addAll(itemInfo.confs);
                for (int i = 0; i < itemInfo.confs.size(); i++) {
                    this.mIntData.add(itemInfo.name);
                }
            }
        }
        this.mAdapter = new SingleConfigAdapter(this, this.mList, redcolsList);
        ((SingleConfigureActivityBinding) this.mBaseBinding).recyclerview.setOverScrollMode(2);
        ((SingleConfigureActivityBinding) this.mBaseBinding).recyclerview.setAdapter(this.mAdapter);
        ((SingleConfigureActivityBinding) this.mBaseBinding).recyclerview.addItemDecoration(new SectionDecoration(this, new ArrayList(), new SectionDecoration$DecorationCallback() {
            public String getGroupId(int position) {
                if (position >= SingleConfigureActivity.this.mIntData.size() || SingleConfigureActivity.this.mIntData.get(position) == null) {
                    return "-1";
                }
                return (String) SingleConfigureActivity.this.mIntData.get(position);
            }

            public String getGroupFirstLine(int position) {
                if (position < SingleConfigureActivity.this.mIntData.size()) {
                    return (String) SingleConfigureActivity.this.mIntData.get(position);
                }
                return "-1";
            }
        }));
        this.mLayoutManager = new LinearLayoutManager(this);
        ((SingleConfigureActivityBinding) this.mBaseBinding).recyclerview.setLayoutManager(this.mLayoutManager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        if (FengApplication.getInstance().getSparkDB().getCarComparisonIDList().contains(Integer.valueOf(this.mCarModleID))) {
            ((SingleConfigureActivityBinding) this.mBaseBinding).ivRightImageTwo.setSelected(true);
        } else {
            ((SingleConfigureActivityBinding) this.mBaseBinding).ivRightImageTwo.setSelected(false);
        }
    }

    public void onItemClick(int position) {
        this.mLayoutManager.scrollToPositionWithOffset(position + 1, 0);
        ((SingleConfigureActivityBinding) this.mBaseBinding).rvConfigClass.setVisibility(8);
    }

    public String getLogCurrentPage() {
        return "app_car_single_configuration?carsid=" + this.mCarsid + "&carxid=" + this.mCarModleID + "&cityid=" + MapUtil.newInstance().getCurrentCityId();
    }
}
