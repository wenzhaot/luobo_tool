package com.feng.car.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.baidu.location.BDLocation;
import com.feng.car.R;
import com.feng.car.adapter.CitySelectAdapter;
import com.feng.car.databinding.ActivityCityListBinding;
import com.feng.car.databinding.GpsHeaderLayoutBinding;
import com.feng.car.entity.lcoation.CityInfo;
import com.feng.car.event.SearchCityEvent;
import com.feng.car.utils.MapUtil;
import com.feng.car.view.selectcar.decoration.TitleWithLetterItemDecoration;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.umeng.analytics.MobclickAgent;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CityListActivity extends LocationBaseActivity<ActivityCityListBinding> {
    private GpsHeaderLayoutBinding mHeaderBinding;

    public int setBaseContentView() {
        return R.layout.activity_city_list;
    }

    public void initView() {
        super.initView();
        hideDefaultTitleBar();
        this.mHeaderBinding = GpsHeaderLayoutBinding.inflate(LayoutInflater.from(this));
        ((ActivityCityListBinding) this.mBaseBinding).close.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CityListActivity.this.finish();
            }
        });
        ((ActivityCityListBinding) this.mBaseBinding).searchText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CityListActivity.this.startActivity(new Intent(CityListActivity.this, SearchCityActivity.class));
                MobclickAgent.onEvent(CityListActivity.this, "dealer_location_search");
            }
        });
        final List<CityInfo> cityList = MapUtil.newInstance().getAllCityList(this);
        if (cityList == null || cityList.size() <= 0) {
            showSecondTypeToast((int) R.string.get_cityinfo_error);
            return;
        }
        ((ActivityCityListBinding) this.mBaseBinding).recyclerview.addItemDecoration(new TitleWithLetterItemDecoration(this, 2, cityList));
        CitySelectAdapter mAdapter = new CitySelectAdapter(this, cityList);
        LRecyclerViewAdapter mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        ((ActivityCityListBinding) this.mBaseBinding).recyclerview.setAdapter(mLRecyclerViewAdapter);
        ((ActivityCityListBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityCityListBinding) this.mBaseBinding).recyclerview.setNoMore(true);
        ((ActivityCityListBinding) this.mBaseBinding).recyclerview.setPullRefreshEnabled(false);
        mLRecyclerViewAdapter.removeFooterView(mLRecyclerViewAdapter.getFooterView());
        mLRecyclerViewAdapter.addHeaderView(this.mHeaderBinding.getRoot());
        this.mHeaderBinding.getRoot().setLayoutParams(new LayoutParams(-1, -2));
        ((ActivityCityListBinding) this.mBaseBinding).indexBar.setmPressedShowTextView(((ActivityCityListBinding) this.mBaseBinding).tvSideBarHint).setNeedRealIndex(false).setHeadNum(2).setmLayoutManager((LinearLayoutManager) ((ActivityCityListBinding) this.mBaseBinding).recyclerview.getLayoutManager()).setmSourceDatas(cityList);
        mAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                CityInfo info = (CityInfo) cityList.get(position);
                MapUtil.newInstance().updateCurrentCityId(CityListActivity.this, info.id);
                EventBus.getDefault().post(new SearchCityEvent(info));
                CityListActivity.this.finish();
            }
        });
        for (int i = 0; i < cityList.size(); i++) {
            if (i != cityList.size() - 1) {
                if (((CityInfo) cityList.get(i)).abc.equals(((CityInfo) cityList.get(i + 1)).abc)) {
                    ((CityInfo) cityList.get(i)).posflag = 0;
                } else {
                    ((CityInfo) cityList.get(i)).posflag = 1;
                }
            }
        }
        ((CityInfo) cityList.get(cityList.size() - 1)).posflag = 0;
        mAdapter.notifyDataSetChanged();
    }

    protected void onResume() {
        super.onResume();
        if (MapUtil.newInstance().hasOpenGps(this)) {
            this.mHeaderBinding.cityNameLine.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    CityInfo info = MapUtil.newInstance().getCityInfoByName(CityListActivity.this, CityListActivity.this.mCity);
                    MapUtil.newInstance().updateCurrentCityId(CityListActivity.this, info.id);
                    EventBus.getDefault().post(new SearchCityEvent(info));
                    CityListActivity.this.finish();
                }
            });
            if (!StringUtil.isEmpty(this.mCity)) {
                this.mHeaderBinding.cityName.setText(this.mCity);
            }
            this.mHeaderBinding.reposition.setVisibility(0);
            this.mHeaderBinding.progressBar.setVisibility(8);
            this.mHeaderBinding.openGpsTips.setVisibility(8);
            this.mHeaderBinding.reposition.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    CityListActivity.this.mHeaderBinding.reposition.setVisibility(8);
                    CityListActivity.this.mHeaderBinding.progressBar.setVisibility(0);
                    CityListActivity.this.checkPermission(true);
                    MobclickAgent.onEvent(CityListActivity.this, "dealer_location_refresh");
                }
            });
            return;
        }
        this.mHeaderBinding.cityName.setText(R.string.sorry_gps_close);
        this.mHeaderBinding.reposition.setVisibility(8);
        this.mHeaderBinding.openGpsTips.setVisibility(0);
        this.mHeaderBinding.openGpsTips.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MapUtil.newInstance().openGpsSetting(CityListActivity.this);
                MobclickAgent.onEvent(CityListActivity.this, "dealer_location_opengps");
            }
        });
    }

    public void onReceiveLocationData(BDLocation bdLocation) {
        super.onReceiveLocationData(bdLocation);
        this.mCity = MapUtil.newInstance().cityNameFormat(bdLocation.getCity());
        this.mHeaderBinding.cityName.setText(this.mCity);
        this.mHeaderBinding.reposition.setVisibility(0);
        this.mHeaderBinding.openGpsTips.setVisibility(8);
        this.mHeaderBinding.progressBar.setVisibility(8);
        this.mHeaderBinding.cityNameLine.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CityInfo info = MapUtil.newInstance().getCityInfoByName(CityListActivity.this, CityListActivity.this.mCity);
                MapUtil.newInstance().updateCurrentCityId(CityListActivity.this, info.id);
                EventBus.getDefault().post(new SearchCityEvent(info));
                CityListActivity.this.finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchCityEvent event) {
        finish();
    }

    protected void onBaiDuLocationError() {
        super.onBaiDuLocationError();
        this.mHeaderBinding.cityName.setText(R.string.sorry_gps_close);
        this.mHeaderBinding.progressBar.setVisibility(8);
        this.mHeaderBinding.reposition.setVisibility(8);
        this.mHeaderBinding.openGpsTips.setVisibility(0);
        this.mHeaderBinding.openGpsTips.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MapUtil.newInstance().openGpsSetting(CityListActivity.this);
                MobclickAgent.onEvent(CityListActivity.this, "dealer_location_opengps");
            }
        });
        this.mHeaderBinding.cityNameLine.setOnClickListener(null);
        stopBaiduLocation();
        MobclickAgent.onEvent(this, "dealer_location_error");
    }
}
