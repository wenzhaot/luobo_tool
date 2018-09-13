package com.feng.car.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityCarModelListBinding;
import com.feng.car.databinding.TabVehicleItemLayoutBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.RecommendCarxInfo;
import com.feng.car.event.CarComparisonAnmEvent;
import com.feng.car.event.RefreshEvent;
import com.feng.car.event.SearchCityEvent;
import com.feng.car.fragment.AllCarFragment;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.utils.BizierEvaluator2;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.MapUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class CarModelListActivity extends BaseActivity<ActivityCarModelListBinding> {
    private List<AllCarFragment> mAllCarFragments = new ArrayList();
    private List<String> mAllCarTitles = new ArrayList();
    private int mCarSeriesID = 0;
    private String mCarSeriesName;
    private int mCurrentIndex;
    private boolean mHasModelList2 = false;
    private boolean mIsFirst = true;

    public int setBaseContentView() {
        return R.layout.activity_car_model_list;
    }

    public void getLocalIntentData() {
        this.mCarSeriesID = getIntent().getIntExtra("carsid", 0);
        this.mCarSeriesName = getIntent().getStringExtra("name");
    }

    public void initView() {
        hideDefaultTitleBar();
        this.mCity = MapUtil.newInstance().getCurrentCityName();
        this.mCityId = MapUtil.newInstance().getCurrentCityId();
        ((ActivityCarModelListBinding) this.mBaseBinding).tvLocationCity.setText(this.mCity);
        ((ActivityCarModelListBinding) this.mBaseBinding).rlTitleBar.setVisibility(8);
        ((ActivityCarModelListBinding) this.mBaseBinding).rlTitleBar.setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                if (CarModelListActivity.this.mAllCarFragments.size() > 0) {
                    ((AllCarFragment) CarModelListActivity.this.mAllCarFragments.get(CarModelListActivity.this.mCurrentIndex)).backTop();
                }
            }
        }));
        ((ActivityCarModelListBinding) this.mBaseBinding).tvLocationCity.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CarModelListActivity.this.startActivity(new Intent(CarModelListActivity.this, CityListActivity.class));
            }
        });
        ((ActivityCarModelListBinding) this.mBaseBinding).ivTitleLeft.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CarModelListActivity.this.finish();
            }
        });
        if (this.mCarSeriesID > 0) {
            getAllCarInfoData();
        } else {
            showNoAllCarTips();
        }
    }

    private void getAllCarInfoData() {
        Map<String, Object> map = new HashMap();
        map.put("carsid", String.valueOf(this.mCarSeriesID));
        map.put("cityid", String.valueOf(this.mCityId));
        FengApplication.getInstance().httpRequest("car/getallspec/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                CarModelListActivity.this.hideLaoSiJiLoadingDialog();
                CarModelListActivity.this.showNetErrorView();
            }

            public void onStart() {
                CarModelListActivity.this.showLaoSiJiDialog();
            }

            public void onFinish() {
                CarModelListActivity.this.hideLaoSiJiLoadingDialog();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                CarModelListActivity.this.showNetErrorView();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        CarModelListActivity.this.hideEmptyView();
                        CarModelListActivity.this.initAllCarFragments(jsonResult.getJSONObject("body").getJSONObject("carx").toString());
                        ((ActivityCarModelListBinding) CarModelListActivity.this.mBaseBinding).tvTitle.setText(CarModelListActivity.this.mCarSeriesName);
                        return;
                    }
                    FengApplication.getInstance().checkCode("car/getallspec/", code);
                    CarModelListActivity.this.showNoAllCarTips();
                } catch (Exception e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("car/getallspec/", content, e);
                    CarModelListActivity.this.showNoAllCarTips();
                }
            }
        });
    }

    private void initAllCarFragments(String json) {
        try {
            BaseListModel<RecommendCarxInfo> baseListModel = new BaseListModel();
            baseListModel.parser(RecommendCarxInfo.class, new JSONObject(json));
            ((ActivityCarModelListBinding) this.mBaseBinding).vpCarModelList.removeAllViewsInLayout();
            this.mAllCarTitles.clear();
            this.mAllCarFragments.clear();
            if (baseListModel.list2.size() > 0) {
                this.mAllCarTitles.add("即将销售");
                this.mAllCarFragments.add(AllCarFragment.newInstance(this.mCarSeriesID, this.mCarSeriesName, JsonUtil.toJson(baseListModel.list2)));
                this.mHasModelList2 = true;
            }
            if (baseListModel.list.size() > 0) {
                this.mAllCarTitles.add("全部在售");
                this.mAllCarFragments.add(AllCarFragment.newInstance(this.mCarSeriesID, this.mCarSeriesName, JsonUtil.toJson(baseListModel.list)));
            }
            if (baseListModel.list3.size() > 0) {
                Map<String, List<RecommendCarxInfo>> classifyDataGroups = new LinkedHashMap();
                for (RecommendCarxInfo info : baseListModel.list3) {
                    if (info.carx.state != 40 || !info.carx.getGuidePrice().equals("暂无")) {
                        if (classifyDataGroups.containsKey(info.carx.getYear())) {
                            ((List) classifyDataGroups.get(info.carx.getYear())).add(info);
                        } else {
                            List<RecommendCarxInfo> dataList = new ArrayList();
                            dataList.add(info);
                            classifyDataGroups.put(info.carx.getYear(), dataList);
                        }
                    }
                }
                for (String key : classifyDataGroups.keySet()) {
                    this.mAllCarTitles.add(key);
                    this.mAllCarFragments.add(AllCarFragment.newInstance(this.mCarSeriesID, this.mCarSeriesName, JsonUtil.toJson((List) classifyDataGroups.get(key))));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((ActivityCarModelListBinding) this.mBaseBinding).vpCarModelList.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            public int getItemPosition(Object object) {
                return -2;
            }

            public int getCount() {
                return CarModelListActivity.this.mAllCarFragments.size();
            }

            public Fragment getItem(int position) {
                return (Fragment) CarModelListActivity.this.mAllCarFragments.get(position);
            }
        });
        ((ActivityCarModelListBinding) this.mBaseBinding).vpCarModelList.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (CarModelListActivity.this.mCurrentIndex != position) {
                    ((AllCarFragment) CarModelListActivity.this.mAllCarFragments.get(CarModelListActivity.this.mCurrentIndex)).getLogGatherInfo().addPageOutTime();
                }
                CarModelListActivity.this.mCurrentIndex = position;
                CarModelListActivity.this.uploadPv();
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        if (this.mHasModelList2) {
            ((ActivityCarModelListBinding) this.mBaseBinding).vpCarModelList.setCurrentItem(1);
            this.mCurrentIndex = 1;
        } else {
            ((ActivityCarModelListBinding) this.mBaseBinding).vpCarModelList.setCurrentItem(0);
            this.mCurrentIndex = 0;
            uploadPv();
        }
        ((ActivityCarModelListBinding) this.mBaseBinding).tabCarModelList.setupWithViewPager(((ActivityCarModelListBinding) this.mBaseBinding).vpCarModelList);
        for (int i = 0; i < ((ActivityCarModelListBinding) this.mBaseBinding).tabCarModelList.getTabCount(); i++) {
            ((ActivityCarModelListBinding) this.mBaseBinding).tabCarModelList.getTabAt(i).setCustomView(getAllCarTabView(i));
        }
        if (this.mAllCarFragments.size() <= 0) {
            showNoAllCarTips();
        } else {
            hideEmptyView();
        }
    }

    private View getAllCarTabView(int position) {
        TabVehicleItemLayoutBinding binding = TabVehicleItemLayoutBinding.inflate(this.mInflater, ((ActivityCarModelListBinding) this.mBaseBinding).tabCarModelList, false);
        binding.tvTitle.setText((CharSequence) this.mAllCarTitles.get(position));
        return binding.getRoot();
    }

    private void showNoAllCarTips() {
        showEmptyView((int) R.string.no_carmodel_hint, (int) R.drawable.no_carmodel_blank);
    }

    private void hideLaoSiJiLoadingDialog() {
        ((ActivityCarModelListBinding) this.mBaseBinding).rlTitleBar.setVisibility(0);
        hideProgress();
    }

    public void showNetErrorView() {
        showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CarModelListActivity.this.getAllCarInfoData();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CarComparisonAnmEvent event) {
        playAnimation(event.point);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchCityEvent searchCityEvent) {
        if (searchCityEvent != null && !TextUtils.isEmpty(searchCityEvent.cityInfo.name) && this.mCityId != searchCityEvent.cityInfo.id) {
            ((ActivityCarModelListBinding) this.mBaseBinding).tvLocationCity.setText(searchCityEvent.cityInfo.name);
            this.mCityId = searchCityEvent.cityInfo.id;
            getAllCarInfoData();
            showLaoSiJiDialog();
        }
    }

    private void playAnimation(int[] position) {
        final ImageView ivAnmView = new ImageView(this);
        ivAnmView.setImageResource(R.drawable.vehicles_compare);
        ivAnmView.setScaleType(ScaleType.MATRIX);
        ((ViewGroup) getWindow().getDecorView()).addView(ivAnmView);
        int[] des = new int[2];
        ((TextView) ((ActivityCarModelListBinding) this.mBaseBinding).rightCvPk.findViewById(R.id.id_pk_tv_vs_num)).getLocationInWindow(des);
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
                ((ViewGroup) CarModelListActivity.this.getWindow().getDecorView()).removeView(ivAnmView);
            }
        });
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(300);
                    CarModelListActivity.this.runOnUiThread(new Runnable() {
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

    private void uploadPv() {
        if (this.mAllCarFragments.size() > this.mCurrentIndex) {
            ((AllCarFragment) this.mAllCarFragments.get(this.mCurrentIndex)).getLogGatherInfo().setBasePage(getLogCurrentPage());
            ((AllCarFragment) this.mAllCarFragments.get(this.mCurrentIndex)).getLogGatherInfo().addPageInTime();
        }
    }

    protected void onResume() {
        if (this.mIsFirst) {
            this.mIsFirst = false;
        } else {
            uploadPv();
        }
        super.onResume();
    }

    protected void onPause() {
        if (this.mAllCarFragments.size() > this.mCurrentIndex) {
            ((AllCarFragment) this.mAllCarFragments.get(this.mCurrentIndex)).getLogGatherInfo().addPageOutTime();
        }
        super.onPause();
    }

    public String getLogCurrentPage() {
        if (this.mAllCarTitles.size() > this.mCurrentIndex) {
            return "app_car_model_" + ((String) this.mAllCarTitles.get(this.mCurrentIndex)) + "?carsid=" + this.mCarSeriesID;
        }
        return "-";
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
