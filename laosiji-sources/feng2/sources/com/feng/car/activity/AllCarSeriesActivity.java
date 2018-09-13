package com.feng.car.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityAllCarBinding;
import com.feng.car.databinding.TabVehicleItemLayoutBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.event.ClosePageEvent;
import com.feng.car.fragment.AllCarSeriesFragment;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class AllCarSeriesActivity extends BaseActivity<ActivityAllCarBinding> {
    public static final int SEL_CAR_COMPARISON_FROM_NORMAL_TYPE = 1;
    public static final int SEL_CAR_COMPARISON_FROM_WEB_TYPE = 2;
    public static final int SEL_CAR_NORMAL_TYPE = 0;
    private FragmentPagerAdapter mAdapter;
    private int mBrandID = 0;
    private String mBrandImgageUrl = "";
    private String mBrandName = "";
    private List<AllCarSeriesFragment> mFragments = new ArrayList();
    private boolean mIsUploadPrice = false;
    private List<String> mTitles = new ArrayList();
    private int mType = 0;

    public int setBaseContentView() {
        return R.layout.activity_all_car;
    }

    public void getLocalIntentData() {
        this.mBrandID = getIntent().getIntExtra("brandid", 0);
        this.mIsUploadPrice = getIntent().getBooleanExtra("type", false);
        this.mType = getIntent().getIntExtra("feng_type", 0);
        this.mBrandName = getIntent().getStringExtra("name");
        this.mBrandImgageUrl = getIntent().getStringExtra("url");
    }

    public void initView() {
        hideDefaultTitleBar();
        ((ActivityAllCarBinding) this.mBaseBinding).cvPk.setVisibility(8);
        ((ActivityAllCarBinding) this.mBaseBinding).ivBack.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                AllCarSeriesActivity.this.finish();
            }
        });
        getCarsList();
    }

    private void initFragments(String json) {
        try {
            BaseListModel<CarSeriesInfo> baseListModel = new BaseListModel();
            baseListModel.parser(CarSeriesInfo.class, new JSONObject(json));
            if (baseListModel.list.size() > 0) {
                this.mTitles.add("在售");
                this.mFragments.add(AllCarSeriesFragment.newInstance(0, this.mType, this.mBrandName, this.mBrandImgageUrl, json));
                ((AllCarSeriesFragment) this.mFragments.get(0)).setIsUploadPrice(this.mIsUploadPrice);
            }
            if (!this.mIsUploadPrice && baseListModel.list1.size() > 0) {
                this.mTitles.add("停售");
                this.mFragments.add(AllCarSeriesFragment.newInstance(1, this.mType, this.mBrandName, this.mBrandImgageUrl, json));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            public int getCount() {
                return AllCarSeriesActivity.this.mFragments.size();
            }

            public Fragment getItem(int position) {
                return (Fragment) AllCarSeriesActivity.this.mFragments.get(position);
            }
        };
        ((ActivityAllCarBinding) this.mBaseBinding).vpAllCar.setAdapter(this.mAdapter);
        ((ActivityAllCarBinding) this.mBaseBinding).vpAllCar.setCurrentItem(0);
        ((ActivityAllCarBinding) this.mBaseBinding).tabAllCar.setupWithViewPager(((ActivityAllCarBinding) this.mBaseBinding).vpAllCar);
        for (int i = 0; i < ((ActivityAllCarBinding) this.mBaseBinding).tabAllCar.getTabCount(); i++) {
            ((ActivityAllCarBinding) this.mBaseBinding).tabAllCar.getTabAt(i).setCustomView(getTabView(i));
        }
        if (this.mFragments.size() <= 0) {
            showEmptyView();
        } else if (this.mFragments.size() == 1) {
            ((ActivityAllCarBinding) this.mBaseBinding).tabAllCar.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.transparent));
        }
    }

    private View getTabView(int position) {
        TabVehicleItemLayoutBinding binding = TabVehicleItemLayoutBinding.inflate(this.mInflater, ((ActivityAllCarBinding) this.mBaseBinding).tabAllCar, false);
        binding.tvTitle.setText((CharSequence) this.mTitles.get(position));
        return binding.getRoot();
    }

    private void getCarsList() {
        Map<String, Object> map = new HashMap();
        map.put("brandid", String.valueOf(this.mBrandID));
        FengApplication.getInstance().httpRequest("car/carspklist/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                AllCarSeriesActivity.this.showNetErrorView();
            }

            public void onStart() {
                AllCarSeriesActivity.this.showLaoSiJiDialog();
            }

            public void onFinish() {
                AllCarSeriesActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                AllCarSeriesActivity.this.showNetErrorView();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        AllCarSeriesActivity.this.hintEmptyView();
                        AllCarSeriesActivity.this.initFragments(jsonResult.getJSONObject("body").getJSONObject("cars").toString());
                        return;
                    }
                    FengApplication.getInstance().checkCode("car/carspklist/", code);
                    AllCarSeriesActivity.this.showNetErrorView();
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("car/carspklist/", content, e);
                }
            }
        });
    }

    private void showNetErrorView() {
        if (((ActivityAllCarBinding) this.mBaseBinding).emptyView != null) {
            ((ActivityAllCarBinding) this.mBaseBinding).emptyView.setEmptyImage(R.drawable.icon_load_faile);
            ((ActivityAllCarBinding) this.mBaseBinding).emptyView.setEmptyText(R.string.load_faile);
            ((ActivityAllCarBinding) this.mBaseBinding).emptyView.setButtonListener(R.string.reload, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AllCarSeriesActivity.this.getCarsList();
                }
            });
            ((ActivityAllCarBinding) this.mBaseBinding).emptyView.setVisibility(0);
        }
    }

    private void hintEmptyView() {
        ((ActivityAllCarBinding) this.mBaseBinding).emptyView.setVisibility(8);
    }

    private void showEmptyView() {
        if (((ActivityAllCarBinding) this.mBaseBinding).emptyView != null) {
            ((ActivityAllCarBinding) this.mBaseBinding).emptyView.setEmptyImage(R.drawable.no_carmodel_blank);
            ((ActivityAllCarBinding) this.mBaseBinding).emptyView.setEmptyText(R.string.no_carmodel_hint);
            ((ActivityAllCarBinding) this.mBaseBinding).emptyView.setVisibility(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ClosePageEvent event) {
        if (this.mType != 0) {
            finish();
        }
    }

    protected void onResume() {
        super.onResume();
    }

    public String getLogCurrentPage() {
        return "app_car_pk_sel_carseries?brandid=" + this.mBrandID;
    }
}
