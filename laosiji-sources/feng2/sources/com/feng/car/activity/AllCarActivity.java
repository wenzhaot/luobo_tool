package com.feng.car.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityAllCarBinding;
import com.feng.car.databinding.TabVehicleItemLayoutBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.RecommendCarxInfo;
import com.feng.car.event.CarComparisonAnmEvent;
import com.feng.car.event.RefreshEvent;
import com.feng.car.event.SearchCityEvent;
import com.feng.car.fragment.AllCarFragment;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.MapUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class AllCarActivity extends BaseActivity<ActivityAllCarBinding> {
    private int mCarSeriesID = 0;
    private String mCarSeriesName;
    private List<AllCarFragment> mFragments = new ArrayList();
    private List<Integer> mSpeclist = new ArrayList();
    private List<String> mTitles = new ArrayList();

    private class BizierEvaluator2 implements TypeEvaluator<Point> {
        private Point controllPoint;

        public BizierEvaluator2(Point controllPoint) {
            this.controllPoint = controllPoint;
        }

        public Point evaluate(float t, Point startValue, Point endValue) {
            return new Point((int) (((((1.0f - t) * (1.0f - t)) * ((float) startValue.x)) + (((2.0f * t) * (1.0f - t)) * ((float) this.controllPoint.x))) + ((t * t) * ((float) endValue.x))), (int) (((((1.0f - t) * (1.0f - t)) * ((float) startValue.y)) + (((2.0f * t) * (1.0f - t)) * ((float) this.controllPoint.y))) + ((t * t) * ((float) endValue.y))));
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_all_car;
    }

    public void initView() {
        hideDefaultTitleBar();
        this.mCarSeriesID = getIntent().getIntExtra("carsid", 0);
        this.mCarSeriesName = getIntent().getStringExtra("name");
        this.mSpeclist = getIntent().getIntegerArrayListExtra("cars");
        this.mCityId = MapUtil.newInstance().getCurrentCityId();
        ((ActivityAllCarBinding) this.mBaseBinding).ivBack.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                AllCarActivity.this.finish();
            }
        });
        showLaoSiJiDialog();
        getData();
    }

    private void initFragments(String json) {
        try {
            BaseListModel<RecommendCarxInfo> baseListModel = new BaseListModel();
            baseListModel.parser(RecommendCarxInfo.class, new JSONObject(json));
            if (baseListModel.list.size() > 0) {
                this.mTitles.add(this.mCarSeriesName);
                this.mFragments.add(AllCarFragment.newInstance(this.mCarSeriesID, this.mCarSeriesName, JsonUtil.toJson(baseListModel.list)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((ActivityAllCarBinding) this.mBaseBinding).vpAllCar.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            public int getCount() {
                return AllCarActivity.this.mFragments.size();
            }

            public Fragment getItem(int position) {
                return (Fragment) AllCarActivity.this.mFragments.get(position);
            }
        });
        ((ActivityAllCarBinding) this.mBaseBinding).vpAllCar.setCurrentItem(0);
        ((ActivityAllCarBinding) this.mBaseBinding).tabAllCar.setupWithViewPager(((ActivityAllCarBinding) this.mBaseBinding).vpAllCar);
        for (int i = 0; i < ((ActivityAllCarBinding) this.mBaseBinding).tabAllCar.getTabCount(); i++) {
            ((ActivityAllCarBinding) this.mBaseBinding).tabAllCar.getTabAt(i).setCustomView(getTabView(i));
        }
        if (this.mFragments.size() <= 0) {
            showEmptyView();
        }
    }

    private View getTabView(int position) {
        TabVehicleItemLayoutBinding binding = TabVehicleItemLayoutBinding.inflate(this.mInflater, ((ActivityAllCarBinding) this.mBaseBinding).tabAllCar, false);
        binding.tvTitle.setText((CharSequence) this.mTitles.get(position));
        binding.tvTitle.setBackgroundResource(R.color.transparent);
        return binding.getRoot();
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.mSpeclist.size(); i++) {
            sb.append(this.mSpeclist.get(i));
            if (i != this.mSpeclist.size() - 1) {
                sb.append(",");
            }
        }
        map.put("speclist", sb.toString());
        map.put("cityid", String.valueOf(MapUtil.newInstance().getCurrentCityId()));
        FengApplication.getInstance().httpRequest("carsearch/getspec/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                AllCarActivity.this.showNetErrorView();
            }

            public void onStart() {
                AllCarActivity.this.showLaoSiJiDialog();
            }

            public void onFinish() {
                AllCarActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                AllCarActivity.this.showNetErrorView();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        AllCarActivity.this.hintEmptyView();
                        AllCarActivity.this.initFragments(jsonResult.getJSONObject("body").getJSONObject("recommendcarx").toString());
                        return;
                    }
                    FengApplication.getInstance().checkCode("carsearch/getspec/", code);
                    AllCarActivity.this.showNetErrorView();
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("carsearch/getspec/", content, e);
                    AllCarActivity.this.showNetErrorView();
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
                    AllCarActivity.this.getData();
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
    public void onEventMainThread(CarComparisonAnmEvent event) {
        playAnimation(event.point);
    }

    private void playAnimation(int[] position) {
        final ImageView ivAnmView = new ImageView(this);
        ivAnmView.setImageResource(R.drawable.vehicles_compare);
        ivAnmView.setScaleType(ScaleType.MATRIX);
        ((ViewGroup) getWindow().getDecorView()).addView(ivAnmView);
        int[] des = new int[2];
        ((TextView) ((ActivityAllCarBinding) this.mBaseBinding).cvPk.findViewById(R.id.id_pk_tv_vs_num)).getLocationInWindow(des);
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
                ((ViewGroup) AllCarActivity.this.getWindow().getDecorView()).removeView(ivAnmView);
            }
        });
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(300);
                    AllCarActivity.this.runOnUiThread(new Runnable() {
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
    public void onEventMainThread(SearchCityEvent searchCityEvent) {
        if (searchCityEvent != null && !TextUtils.isEmpty(searchCityEvent.cityInfo.name) && this.mCityId != searchCityEvent.cityInfo.id) {
            this.mCityId = searchCityEvent.cityInfo.id;
            getData();
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
