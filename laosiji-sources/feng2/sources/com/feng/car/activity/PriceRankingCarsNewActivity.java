package com.feng.car.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityCarsPriceRankingNewBinding;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.lcoation.CityPriceInfo;
import com.feng.car.entity.search.SearchItem;
import com.feng.car.event.OwnerSwitchCityEven;
import com.feng.car.event.ReadyToScrollEvent;
import com.feng.car.event.SearchCityEvent;
import com.feng.car.fragment.CarPriceFragment;
import com.feng.car.listener.AppBarStateChangeListener;
import com.feng.car.listener.AppBarStateChangeListener.State;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.utils.MapUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PriceRankingCarsNewActivity extends BaseActivity<ActivityCarsPriceRankingNewBinding> {
    public static final int CITY_TAB = 1;
    public static final int NATIONWIDE_TAB = 0;
    public static final int PROVINCE_TAB = 2;
    private FragmentStatePagerAdapter mAdapter;
    private int mCarSeriesID;
    private int mCityId = 0;
    private int mCurrentItem = 0;
    List<CarPriceFragment> mFragments = new ArrayList();
    private boolean mIsFirst = true;
    private int mProvinceId = 0;

    public int setBaseContentView() {
        return R.layout.activity_cars_price_ranking_new;
    }

    public void initView() {
        hideDefaultTitleBar();
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).rlTitleBar.setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                if (PriceRankingCarsNewActivity.this.mFragments.size() > 0) {
                    ((CarPriceFragment) PriceRankingCarsNewActivity.this.mFragments.get(PriceRankingCarsNewActivity.this.mCurrentItem)).backTop();
                }
            }
        }));
        this.mCarSeriesID = getIntent().getIntExtra("carsid", 0);
        this.mCityId = MapUtil.newInstance().getCurrentCityId();
        this.mProvinceId = MapUtil.newInstance().getProvinceIdByCityId(this, this.mCityId);
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvLocationCity.setText(MapUtil.newInstance().getCurrentCityName());
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state != State.EXPANDED && state == State.COLLAPSED) {
                }
            }

            public void onVerticalOffset(int verticalOffset, int barHeight) {
                if (barHeight != 0) {
                    ((ActivityCarsPriceRankingNewBinding) PriceRankingCarsNewActivity.this.mBaseBinding).rlTitleBar.getBackground().mutate().setAlpha((int) (((float) (Math.abs(verticalOffset) * 255)) / (((float) barHeight) * 1.0f)));
                    ((ActivityCarsPriceRankingNewBinding) PriceRankingCarsNewActivity.this.mBaseBinding).tvTitle.setAlpha(((float) Math.abs(verticalOffset)) / (((float) barHeight) * 1.0f));
                }
            }
        });
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llCityBtn.setOnClickListener(this);
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llProvinceBtn.setOnClickListener(this);
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llCountryBtn.setOnClickListener(this);
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvCarSBtn.setOnClickListener(this);
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvLocationCity.setOnClickListener(this);
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).ivTitleLeft.setOnClickListener(this);
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).ibMsg.setOnClickListener(this);
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        map.put("cityid", Integer.valueOf(this.mCityId));
        map.put("carsid", Integer.valueOf(this.mCarSeriesID));
        FengApplication.getInstance().httpRequest("car/finalprice/series/info/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                PriceRankingCarsNewActivity.this.showErrorView();
            }

            public void onStart() {
            }

            public void onFinish() {
                PriceRankingCarsNewActivity.this.mIsFirst = false;
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                PriceRankingCarsNewActivity.this.showErrorView();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        PriceRankingCarsNewActivity.this.hideEmptyView();
                        PriceRankingCarsNewActivity.this.hideDefaultTitleBar();
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        JSONObject jsonCars = jsonBody.getJSONObject("cars");
                        CarSeriesInfo seriesInfo = new CarSeriesInfo();
                        seriesInfo.parser(jsonCars);
                        PriceRankingCarsNewActivity.this.parseData(seriesInfo, jsonCars.toString(), jsonBody.getJSONArray("price"));
                        SearchItem item = new SearchItem();
                        item.content = (String) seriesInfo.name.get();
                        item.contentid = seriesInfo.id;
                        item.type = SearchItem.SEARCH_OWNER_PRICE_TYPE;
                        FengApplication.getInstance().getSparkDB().addSearchRecond(item);
                        return;
                    }
                    PriceRankingCarsNewActivity.this.showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                    PriceRankingCarsNewActivity.this.showErrorView();
                }
            }
        });
    }

    private void parseData(CarSeriesInfo seriesInfo, String json, JSONArray jsonArray) throws JSONException {
        int i;
        CityPriceInfo cityPriceInfo;
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvCarsName.setText((CharSequence) seriesInfo.name.get());
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvTitle.setText((CharSequence) seriesInfo.name.get());
        int size = jsonArray.length();
        List<CityPriceInfo> list = new ArrayList();
        for (i = 0; i < size; i++) {
            cityPriceInfo = new CityPriceInfo();
            cityPriceInfo.parser(jsonArray.getJSONObject(i));
            list.add(cityPriceInfo);
        }
        this.mCurrentItem = 0;
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).vpFragment.removeAllViewsInLayout();
        this.mFragments.clear();
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llCityBtn.setVisibility(4);
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llProvinceBtn.setVisibility(4);
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llCountryBtn.setVisibility(4);
        if (list.size() == 2) {
            cityPriceInfo = (CityPriceInfo) list.get(0);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvCity1.setText(cityPriceInfo.city.name);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvTuneRange1.setText(cityPriceInfo.price.getPreferentialTextNew2() + cityPriceInfo.price.getPreferentialDetail2());
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvPriceCount1.setText(cityPriceInfo.price.ownernum + "条");
            this.mFragments.add(CarPriceFragment.newInstance(this.mCarSeriesID, 0, 1, this.mCityId, jsonArray.getJSONObject(0).toString(), json));
            cityPriceInfo = (CityPriceInfo) list.get(1);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvCity2.setText(cityPriceInfo.city.name);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvTuneRange2.setText(cityPriceInfo.price.getPreferentialTextNew2() + cityPriceInfo.price.getPreferentialDetail2());
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvPriceCount2.setText(cityPriceInfo.price.ownernum + "条");
            this.mFragments.add(CarPriceFragment.newInstance(this.mCarSeriesID, 0, 0, this.mCityId, "", json));
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llCityBtn.setVisibility(0);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llProvinceBtn.setVisibility(0);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llCountryBtn.setVisibility(8);
        } else if (list.size() == 3) {
            cityPriceInfo = (CityPriceInfo) list.get(0);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvCity1.setText(cityPriceInfo.city.name);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvTuneRange1.setText(cityPriceInfo.price.getPreferentialTextNew2() + cityPriceInfo.price.getPreferentialDetail2());
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvPriceCount1.setText(cityPriceInfo.price.ownernum + "条");
            this.mFragments.add(CarPriceFragment.newInstance(this.mCarSeriesID, 0, 1, this.mCityId, jsonArray.getJSONObject(0).toString(), json));
            cityPriceInfo = (CityPriceInfo) list.get(1);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvCity2.setText(cityPriceInfo.city.name);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvTuneRange2.setText(cityPriceInfo.price.getPreferentialTextNew2() + cityPriceInfo.price.getPreferentialDetail2());
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvPriceCount2.setText(cityPriceInfo.price.ownernum + "条");
            this.mFragments.add(CarPriceFragment.newInstance(this.mCarSeriesID, 0, 2, this.mCityId, "", json));
            cityPriceInfo = (CityPriceInfo) list.get(2);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvCity3.setText(cityPriceInfo.city.name);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvTuneRange3.setText(cityPriceInfo.price.getPreferentialTextNew2() + cityPriceInfo.price.getPreferentialDetail2());
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvPriceCount3.setText(cityPriceInfo.price.ownernum + "条");
            this.mFragments.add(CarPriceFragment.newInstance(this.mCarSeriesID, 0, 0, this.mCityId, "", json));
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llCityBtn.setVisibility(0);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llProvinceBtn.setVisibility(0);
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llCountryBtn.setVisibility(0);
        }
        changeDirectButtonUI();
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).vpFragment.setOffscreenPageLimit(2);
        this.mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            public int getItemPosition(Object object) {
                return -2;
            }

            public int getCount() {
                return PriceRankingCarsNewActivity.this.mFragments.size();
            }

            public Fragment getItem(int position) {
                return (Fragment) PriceRankingCarsNewActivity.this.mFragments.get(position);
            }
        };
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).vpFragment.setAdapter(this.mAdapter);
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).vpFragment.clearOnPageChangeListeners();
        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).vpFragment.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                ((CarPriceFragment) PriceRankingCarsNewActivity.this.mFragments.get(PriceRankingCarsNewActivity.this.mCurrentItem)).getLogGatherInfo().addPageOutTime();
                PriceRankingCarsNewActivity.this.mCurrentItem = position;
                PriceRankingCarsNewActivity.this.uploadPv();
                PriceRankingCarsNewActivity.this.changeDirectButtonUI();
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        if (this.mIsFirst) {
            if (size > 0) {
                ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).vpFragment.setCurrentItem(size - 1);
                this.mCurrentItem = size - 1;
            } else {
                for (i = 0; i < size; i++) {
                    if (((CityPriceInfo) list.get(i)).price.hasPreferential()) {
                        ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).vpFragment.setCurrentItem(i);
                        this.mCurrentItem = i;
                        break;
                    }
                }
            }
            if (this.mCurrentItem == 0) {
                uploadPv();
            }
        }
    }

    private void uploadPv() {
        if (this.mFragments.size() > this.mCurrentItem) {
            ((CarPriceFragment) this.mFragments.get(this.mCurrentItem)).getLogGatherInfo().setBasePage(getLogCurrentPage());
            ((CarPriceFragment) this.mFragments.get(this.mCurrentItem)).getLogGatherInfo().addPageInTime();
        }
    }

    public void onResume() {
        if (!this.mIsFirst) {
            uploadPv();
        }
        super.onResume();
    }

    protected void onPause() {
        if (this.mFragments.size() > this.mCurrentItem) {
            ((CarPriceFragment) this.mFragments.get(this.mCurrentItem)).getLogGatherInfo().addPageOutTime();
        }
        super.onPause();
    }

    private void showErrorView() {
        initNormalTitleBar("");
        showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                PriceRankingCarsNewActivity.this.getData();
            }
        });
    }

    public void onSingleClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_title_left /*2131624295*/:
                finish();
                return;
            case R.id.tv_location_city /*2131624298*/:
                startActivity(new Intent(this, CityListActivity.class));
                return;
            case R.id.tv_carS_btn /*2131624313*/:
                intent = new Intent(this, NewSubjectActivity.class);
                intent.putExtra("carsid", this.mCarSeriesID);
                startActivity(intent);
                return;
            case R.id.ll_city_btn /*2131624314*/:
                this.mCurrentItem = 0;
                ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).vpFragment.setCurrentItem(this.mCurrentItem);
                return;
            case R.id.ll_province_btn /*2131624318*/:
                this.mCurrentItem = 1;
                ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).vpFragment.setCurrentItem(this.mCurrentItem);
                return;
            case R.id.ll_country_btn /*2131624322*/:
                this.mCurrentItem = 2;
                ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).vpFragment.setCurrentItem(this.mCurrentItem);
                return;
            case R.id.ib_msg /*2131624326*/:
                if (!FengApplication.getInstance().isLoginUser()) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                } else if (StringUtil.isEmpty(FengApplication.getInstance().getUserInfo().phonenumber)) {
                    intent = new Intent(this, SettingAccountPhoneActivity.class);
                    intent.putExtra("feng_type", 0);
                    startActivity(intent);
                    return;
                } else {
                    intent = new Intent(this, InvoiceUploadActivity.class);
                    intent.putExtra("carsid", this.mCarSeriesID);
                    startActivity(intent);
                    return;
                }
            default:
                return;
        }
    }

    private void changeDirectButtonUI() {
        boolean z;
        boolean z2 = true;
        LinearLayout linearLayout = ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llCityBtn;
        if (this.mCurrentItem == 0) {
            z = true;
        } else {
            z = false;
        }
        linearLayout.setSelected(z);
        linearLayout = ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llProvinceBtn;
        if (this.mCurrentItem == 1) {
            z = true;
        } else {
            z = false;
        }
        linearLayout.setSelected(z);
        LinearLayout linearLayout2 = ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).llCountryBtn;
        if (this.mCurrentItem != 2) {
            z2 = false;
        }
        linearLayout2.setSelected(z2);
    }

    public String getLogCurrentPage() {
        if (this.mCurrentItem == 0) {
            return "app_car_onwer_price_series_c?carsid=" + this.mCarSeriesID + "&cityid=" + this.mCityId + "&territoryid=" + this.mCityId;
        }
        if (this.mCurrentItem == 1) {
            if (this.mFragments.size() == 2) {
                return "app_car_onwer_price_series_a?carsid=" + this.mCarSeriesID + "&cityid=" + this.mCityId;
            }
            return "app_car_onwer_price_series_p?carsid=" + this.mCarSeriesID + "&cityid=" + this.mCityId + "&territoryid=" + this.mProvinceId;
        } else if (this.mCurrentItem == 2) {
            return "app_car_onwer_price_series_a?carsid=" + this.mCarSeriesID + "&cityid=" + this.mCityId;
        } else {
            return "-";
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchCityEvent searchCityEvent) {
        if (searchCityEvent != null && !TextUtils.isEmpty(searchCityEvent.cityInfo.name)) {
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).tvLocationCity.setText(MapUtil.newInstance().getCurrentCityName());
            if (this.mCityId != searchCityEvent.cityInfo.id) {
                this.mCityId = searchCityEvent.cityInfo.id;
                this.mProvinceId = MapUtil.newInstance().getProvinceIdByCityId(this, this.mCityId);
                if (LogGatherReadUtil.getInstance().getCurrentPage().indexOf("app_car_onwer_price_series_") >= 0) {
                    if (this.mFragments.size() > this.mCurrentItem) {
                        ((CarPriceFragment) this.mFragments.get(this.mCurrentItem)).getLogGatherInfo().addPageOutTime();
                    }
                    this.mCurrentItem = 0;
                    uploadPv();
                }
                ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).appBar.setExpanded(true, false);
                getData();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(OwnerSwitchCityEven switchCityEven) {
        if (switchCityEven != null && this.mCarSeriesID == switchCityEven.carSeriesID && switchCityEven.carModelID == 0 && !TextUtils.isEmpty(switchCityEven.cityInfo.name) && this.mCityId != switchCityEven.cityInfo.id) {
            this.mCityId = switchCityEven.cityInfo.id;
            this.mProvinceId = MapUtil.newInstance().getProvinceIdByCityId(this, this.mCityId);
            if (LogGatherReadUtil.getInstance().getCurrentPage().indexOf("app_car_onwer_price_series_") >= 0) {
                if (this.mFragments.size() > this.mCurrentItem) {
                    ((CarPriceFragment) this.mFragments.get(this.mCurrentItem)).getLogGatherInfo().addPageOutTime();
                }
                this.mCurrentItem = 0;
                uploadPv();
            }
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).appBar.setExpanded(true, false);
            getData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ReadyToScrollEvent event) {
        if (event.carSeriesID == this.mCarSeriesID && event.carModelID == 0 && this.mCurrentItem == 0) {
            ((ActivityCarsPriceRankingNewBinding) this.mBaseBinding).appBar.setExpanded(false, false);
            ((CarPriceFragment) this.mFragments.get(0)).scrollTo();
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
