package com.feng.car.activity;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ProgressBar;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.PricesProgressAdapter;
import com.feng.car.databinding.PricesSeriesChartLayoutBinding;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.lcoation.CityPriceInfo;
import com.feng.car.event.OwnerSwitchCityEven;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.utils.MapUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PricesSeriesChartActivity extends BaseActivity<PricesSeriesChartLayoutBinding> {
    private PricesProgressAdapter mAdapter;
    private CarSeriesInfo mCarSeriesInfo = new CarSeriesInfo();
    private int mCityID = 0;
    private LinearLayoutManager mLayoutManager;
    private List<CityPriceInfo> mList = new ArrayList();
    private int mProvinceID = 0;
    private int mSeriesID = 0;

    public int setBaseContentView() {
        return R.layout.prices_series_chart_layout;
    }

    public void initView() {
        initNormalTitleBar("");
        this.mRootBinding.titleLine.divier.setVisibility(8);
        closeSwip();
        changeLeftIcon(R.drawable.icon_close);
        this.mCityID = getIntent().getIntExtra("cityid", 131);
        this.mProvinceID = getIntent().getIntExtra("provinceid", 0);
        this.mSeriesID = getIntent().getIntExtra("carsid", 0);
        try {
            this.mCarSeriesInfo.parser(new JSONObject(getIntent().getStringExtra("DATA_JSON")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((PricesSeriesChartLayoutBinding) this.mBaseBinding).tvCarName.setText((CharSequence) this.mCarSeriesInfo.name.get());
        if (this.mProvinceID > 0) {
            ((PricesSeriesChartLayoutBinding) this.mBaseBinding).tvContent.setText(MapUtil.newInstance().getProvinceNameById(this, this.mProvinceID) + "全部城市成交价");
        } else {
            ((PricesSeriesChartLayoutBinding) this.mBaseBinding).tvContent.setText("全国全部城市成交价");
        }
        ((PricesSeriesChartLayoutBinding) this.mBaseBinding).recyclerview.setNoMore(true);
        ((PricesSeriesChartLayoutBinding) this.mBaseBinding).recyclerview.setPullRefreshEnabled(false);
        ((PricesSeriesChartLayoutBinding) this.mBaseBinding).recyclerview.setLScrollListener(new LRecyclerView$LScrollListener() {
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
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        map.put("carsid", Integer.valueOf(this.mSeriesID));
        if (this.mProvinceID > 0) {
            map.put("provinceid", Integer.valueOf(this.mProvinceID));
        }
        FengApplication.getInstance().httpRequest("car/finalprice/series/rank/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                PricesSeriesChartActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        PricesSeriesChartActivity.this.getData();
                    }
                });
            }

            public void onStart() {
                PricesSeriesChartActivity.this.showLaoSiJiDialog();
            }

            public void onFinish() {
                PricesSeriesChartActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                PricesSeriesChartActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        PricesSeriesChartActivity.this.getData();
                    }
                });
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        int i;
                        CityPriceInfo cityPriceInfo;
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        JSONArray jsonArray = jsonBody.getJSONArray("price");
                        JSONArray jsonArray2 = jsonBody.getJSONArray("noprice");
                        int size = jsonArray.length();
                        List<CityPriceInfo> list1 = new ArrayList();
                        for (i = 0; i < size; i++) {
                            cityPriceInfo = new CityPriceInfo();
                            cityPriceInfo.parser(jsonArray.getJSONObject(i));
                            list1.add(cityPriceInfo);
                        }
                        List<CityPriceInfo> list2 = new ArrayList();
                        size = jsonArray2.length();
                        for (i = 0; i < size; i++) {
                            cityPriceInfo = new CityPriceInfo();
                            cityPriceInfo.parser(jsonArray2.getJSONObject(i));
                            list2.add(cityPriceInfo);
                        }
                        PricesSeriesChartActivity.this.initAdapter(list1, list2);
                        return;
                    }
                    PricesSeriesChartActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            PricesSeriesChartActivity.this.getData();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("user/ywf/myhistory/", content, e);
                }
            }
        });
    }

    private void initAdapter(List<CityPriceInfo> list1, List<CityPriceInfo> list2) {
        this.mList.clear();
        this.mList.addAll(list1);
        this.mList.addAll(list2);
        int size = list1.size();
        double min = 100.0d;
        double max = 0.0d;
        for (int i = 0; i < size; i++) {
            if (Math.abs(((CityPriceInfo) list1.get(i)).price.preferential - 100.0d) > max) {
                max = Math.abs(((CityPriceInfo) list1.get(i)).price.preferential - 100.0d);
            }
            if (Math.abs(((CityPriceInfo) list1.get(i)).price.preferential - 100.0d) < min) {
                min = Math.abs(((CityPriceInfo) list1.get(i)).price.preferential - 100.0d);
            }
        }
        if (list1.size() > 0) {
            this.mAdapter = new PricesProgressAdapter(this, this.mList, 0, min, max);
        } else {
            this.mAdapter = new PricesProgressAdapter(this, this.mList, 0, 0.0d, 0.0d);
        }
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((PricesSeriesChartLayoutBinding) this.mBaseBinding).recyclerview.setAdapter(lRecyclerViewAdapter);
        this.mLayoutManager = new LinearLayoutManager(this);
        ((PricesSeriesChartLayoutBinding) this.mBaseBinding).recyclerview.setLayoutManager(this.mLayoutManager);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                EventBus.getDefault().post(new OwnerSwitchCityEven(((CityPriceInfo) PricesSeriesChartActivity.this.mList.get(position)).city, PricesSeriesChartActivity.this.mSeriesID, 0));
                PricesSeriesChartActivity.this.finish();
            }
        });
        initBottom();
        ((PricesSeriesChartLayoutBinding) this.mBaseBinding).recyclerview.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                PricesSeriesChartActivity.this.stateAnimator();
                ((PricesSeriesChartLayoutBinding) PricesSeriesChartActivity.this.mBaseBinding).recyclerview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    private void initBottom() {
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            CityPriceInfo info = (CityPriceInfo) this.mList.get(i);
            if (info.city.id == this.mCityID) {
                ((PricesSeriesChartLayoutBinding) this.mBaseBinding).tvCityName.setText((i + 1) + "." + info.city.name);
                ((PricesSeriesChartLayoutBinding) this.mBaseBinding).tvPriceText.setText(this.mCarSeriesInfo.getCarPriceText());
                ((PricesSeriesChartLayoutBinding) this.mBaseBinding).tvPrice.setText(this.mCarSeriesInfo.getCarPrice());
                if (info.price.preferential == 0.0d) {
                    ((PricesSeriesChartLayoutBinding) this.mBaseBinding).tvOwnerText.setText("成交价：");
                    ((PricesSeriesChartLayoutBinding) this.mBaseBinding).tvOwnerPrice.setTextColor(ContextCompat.getColor(this, R.color.color_54_000000));
                    ((PricesSeriesChartLayoutBinding) this.mBaseBinding).tvOwnerPrice.setText("暂无");
                    return;
                }
                ((PricesSeriesChartLayoutBinding) this.mBaseBinding).tvOwnerPrice.setTextColor(ContextCompat.getColor(this, R.color.color_e12c2c));
                ((PricesSeriesChartLayoutBinding) this.mBaseBinding).tvOwnerText.setText(info.price.getPreferentialTextNew() + "：");
                ((PricesSeriesChartLayoutBinding) this.mBaseBinding).tvOwnerPrice.setText(info.price.getPreferentialDetail());
                return;
            }
        }
    }

    private void stateAnimator() {
        try {
            int childSize = (this.mLayoutManager.findLastVisibleItemPosition() - this.mLayoutManager.findFirstVisibleItemPosition()) + 1;
            int i = 0;
            while (i < childSize) {
                View view = this.mLayoutManager.getChildAt(i);
                if (view != null) {
                    ProgressBar bar = (ProgressBar) view.findViewById(R.id.pr_bar);
                    if (!(bar == null || bar.getTag() == null || TextUtils.isEmpty(bar.getTag().toString()))) {
                        stateAnimator(bar);
                    }
                    i++;
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stateAnimator(final ProgressBar bar) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[]{50.0f, 100.0f});
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                bar.setProgress((int) Float.parseFloat(animation.getAnimatedValue().toString()));
            }
        });
        valueAnimator.start();
    }

    public String getLogCurrentPage() {
        if (this.mProvinceID > 0) {
            return "app_car_onwer_price_rank_series?carsid=" + this.mSeriesID + "&cityid=" + this.mCityId + "&territoryid=" + this.mProvinceID + "&territorytype=p";
        }
        return "app_car_onwer_price_rank_series?carsid=" + this.mSeriesID + "&cityid=" + this.mCityId + "&territorytype=a";
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((PricesSeriesChartLayoutBinding) this.mBaseBinding).recyclerview.setIsScrollDown(true);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
