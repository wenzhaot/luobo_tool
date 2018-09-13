package com.feng.car.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CarModelTabAdapter;
import com.feng.car.databinding.ActivityCarPhotoListBinding;
import com.feng.car.databinding.SelectMiddleTitleBinding;
import com.feng.car.databinding.TabMenuItemLayoutBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.model.CarPhotoTabItem;
import com.feng.car.event.CarModelChangeImageEvent;
import com.feng.car.fragment.CarPhotoListFragment;
import com.feng.car.utils.CarImageListDataUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.stub.StubApp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class CarPhotoListActivity extends BaseActivity<ActivityCarPhotoListBinding> {
    public static final String JUMP_PAGE_TYPE = "jump_page_type";
    private int mCarModelID;
    private List<CarModelInfo> mCarModelInfoList = new ArrayList();
    private Map<String, List<CarModelInfo>> mCarModelMap = new TreeMap(new Comparator<String>() {
        public int compare(String o1, String o2) {
            return o2.compareTo(o1);
        }
    });
    private String mCarModelName;
    private int mCarSeriesID;
    private String mCarSeriesName;
    private int mCurrentTab = 0;
    private List<CarPhotoListFragment> mFragments = new ArrayList();
    private boolean mIsFirst = true;
    private int mJumpPageType;
    private SelectMiddleTitleBinding mMiddleTitleBinding;
    private boolean mPhotoListShowFlag;
    private List<CarPhotoTabItem> mTabList = new ArrayList();
    private String mYear = "";

    static {
        StubApp.interface11(2286);
    }

    protected native void onCreate(Bundle bundle);

    public int setBaseContentView() {
        return R.layout.activity_car_photo_list;
    }

    private void initCarModelTabs() {
        ((ActivityCarPhotoListBinding) this.mBaseBinding).tvCarSeries.setText(this.mCarSeriesName);
        if (this.mCarModelID == 0) {
            ((ActivityCarPhotoListBinding) this.mBaseBinding).tvCarSeries.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.icon_confirm), null);
            ((ActivityCarPhotoListBinding) this.mBaseBinding).tvCarSeries.setSelected(true);
        }
        parserCarsModelData();
        List<String> list = new ArrayList();
        for (String key : this.mCarModelMap.keySet()) {
            list.add(key);
        }
        ((ActivityCarPhotoListBinding) this.mBaseBinding).vpCarModel.setOffscreenPageLimit(1);
        final CarModelTabAdapter adapter = new CarModelTabAdapter(getSupportFragmentManager(), this.mCarModelID, list, this.mCarModelMap);
        ((ActivityCarPhotoListBinding) this.mBaseBinding).vpCarModel.setAdapter(adapter);
        ((ActivityCarPhotoListBinding) this.mBaseBinding).tabCarModelYear.setupWithViewPager(((ActivityCarPhotoListBinding) this.mBaseBinding).vpCarModel);
        ((ActivityCarPhotoListBinding) this.mBaseBinding).tabCarModelYear.setTabMode(0);
        for (int i = 0; i < ((ActivityCarPhotoListBinding) this.mBaseBinding).tabCarModelYear.getTabCount(); i++) {
            ((ActivityCarPhotoListBinding) this.mBaseBinding).tabCarModelYear.getTabAt(i).setCustomView(getCarModelTabView(((String) list.get(i)) + "款"));
        }
        int selIndex = list.indexOf(this.mYear);
        if (selIndex > 0) {
            ((ActivityCarPhotoListBinding) this.mBaseBinding).vpCarModel.setCurrentItem(selIndex);
        }
        ((ActivityCarPhotoListBinding) this.mBaseBinding).vpCarModel.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (adapter.getCurrentItem(position) != null) {
                    adapter.getCurrentItem(position).checkCarModelId(CarPhotoListActivity.this.mCarModelID);
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        ((ActivityCarPhotoListBinding) this.mBaseBinding).tvCarSeries.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (((ActivityCarPhotoListBinding) CarPhotoListActivity.this.mBaseBinding).tvCarSeries.isSelected()) {
                    CarPhotoListActivity.this.mSwipeBackLayout.setCanScroll(true);
                    ((ActivityCarPhotoListBinding) CarPhotoListActivity.this.mBaseBinding).llSelectCarModel.setVisibility(8);
                    ((ActivityCarPhotoListBinding) CarPhotoListActivity.this.mBaseBinding).viewShade.setVisibility(8);
                    CarPhotoListActivity.this.mPhotoListShowFlag = false;
                    CarPhotoListActivity.this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
                    return;
                }
                CarModelInfo info = new CarModelInfo();
                info.id = 0;
                info.year = "";
                info.name = CarPhotoListActivity.this.mCarSeriesName;
                EventBus.getDefault().post(new CarModelChangeImageEvent(info, CarModelChangeImageEvent.TYPE_CAR_IMAGE_LSIT));
            }
        });
    }

    private void initCarPhotoTabs() {
        boolean z;
        this.mTabList.add(new CarPhotoTabItem("外观", this.mCarSeriesID, this.mCarModelID, this.mYear));
        this.mTabList.add(new CarPhotoTabItem("中控", this.mCarSeriesID, this.mCarModelID, this.mYear));
        this.mTabList.add(new CarPhotoTabItem("座椅", this.mCarSeriesID, this.mCarModelID, this.mYear));
        this.mTabList.add(new CarPhotoTabItem("细节", this.mCarSeriesID, this.mCarModelID, this.mYear));
        List list = this.mFragments;
        int i = this.mCarSeriesID;
        int i2 = this.mCarModelID;
        String str = this.mYear;
        if (this.mJumpPageType == 1) {
            z = true;
        } else {
            z = false;
        }
        list.add(CarPhotoListFragment.newInstance(1, i, i2, str, z));
        list = this.mFragments;
        i = this.mCarSeriesID;
        i2 = this.mCarModelID;
        str = this.mYear;
        if (this.mJumpPageType == 2) {
            z = true;
        } else {
            z = false;
        }
        list.add(CarPhotoListFragment.newInstance(2, i, i2, str, z));
        list = this.mFragments;
        i = this.mCarSeriesID;
        i2 = this.mCarModelID;
        str = this.mYear;
        if (this.mJumpPageType == 3) {
            z = true;
        } else {
            z = false;
        }
        list.add(CarPhotoListFragment.newInstance(3, i, i2, str, z));
        list = this.mFragments;
        i = this.mCarSeriesID;
        i2 = this.mCarModelID;
        str = this.mYear;
        if (this.mJumpPageType == 4) {
            z = true;
        } else {
            z = false;
        }
        list.add(CarPhotoListFragment.newInstance(4, i, i2, str, z));
        ((ActivityCarPhotoListBinding) this.mBaseBinding).vpCarPhoto.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            public int getCount() {
                return CarPhotoListActivity.this.mFragments.size();
            }

            public Fragment getItem(int position) {
                return (Fragment) CarPhotoListActivity.this.mFragments.get(position);
            }
        });
        ((ActivityCarPhotoListBinding) this.mBaseBinding).tabCarPhotoType.setupWithViewPager(((ActivityCarPhotoListBinding) this.mBaseBinding).vpCarPhoto);
        ((ActivityCarPhotoListBinding) this.mBaseBinding).tabCarPhotoType.setTabMode(1);
        for (int i3 = 0; i3 < ((ActivityCarPhotoListBinding) this.mBaseBinding).tabCarPhotoType.getTabCount(); i3++) {
            ((ActivityCarPhotoListBinding) this.mBaseBinding).tabCarPhotoType.getTabAt(i3).setCustomView(getTabView(i3));
        }
        switch (this.mJumpPageType) {
            case 1:
                ((ActivityCarPhotoListBinding) this.mBaseBinding).vpCarPhoto.setCurrentItem(0);
                uploadSlidePv();
                break;
            case 2:
                ((ActivityCarPhotoListBinding) this.mBaseBinding).vpCarPhoto.setCurrentItem(1);
                break;
            case 3:
                ((ActivityCarPhotoListBinding) this.mBaseBinding).vpCarPhoto.setCurrentItem(2);
                break;
            case 4:
                ((ActivityCarPhotoListBinding) this.mBaseBinding).vpCarPhoto.setCurrentItem(3);
                break;
        }
        ((ActivityCarPhotoListBinding) this.mBaseBinding).vpCarPhoto.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (CarPhotoListActivity.this.mCurrentTab != position) {
                    ((CarPhotoListFragment) CarPhotoListActivity.this.mFragments.get(CarPhotoListActivity.this.mCurrentTab)).getLogGatherInfo().addPageOutTime();
                }
                CarPhotoListActivity.this.mCurrentTab = position;
                CarPhotoListActivity.this.changerType();
                CarPhotoListActivity.this.uploadSlidePv();
                if (((CarPhotoListFragment) CarPhotoListActivity.this.mFragments.get(position)).isAdded()) {
                    ((CarPhotoListFragment) CarPhotoListActivity.this.mFragments.get(position)).reload(CarPhotoListActivity.this.mCarSeriesID, CarPhotoListActivity.this.mCarModelID, CarPhotoListActivity.this.mYear);
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void changerType() {
        switch (this.mCurrentTab) {
            case 0:
                this.mJumpPageType = 1;
                return;
            case 1:
                this.mJumpPageType = 2;
                return;
            case 2:
                this.mJumpPageType = 3;
                return;
            case 3:
                this.mJumpPageType = 4;
                return;
            default:
                return;
        }
    }

    public void initView() {
        this.mCarSeriesID = getIntent().getIntExtra("id", 0);
        this.mCarSeriesName = getIntent().getStringExtra("name");
        this.mCarModelName = getIntent().getStringExtra("carx_name");
        this.mCarModelID = getIntent().getIntExtra("carxid", 0);
        this.mYear = getIntent().getStringExtra("year");
        if (TextUtils.isEmpty(this.mYear)) {
            this.mYear = "";
        }
        this.mJumpPageType = getIntent().getIntExtra(JUMP_PAGE_TYPE, 1);
        this.mPhotoListShowFlag = false;
        this.mMiddleTitleBinding = SelectMiddleTitleBinding.inflate(this.mInflater);
        ((ActivityCarPhotoListBinding) this.mBaseBinding).viewShade.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (CarPhotoListActivity.this.mPhotoListShowFlag) {
                    ((ActivityCarPhotoListBinding) CarPhotoListActivity.this.mBaseBinding).llSelectCarModel.setVisibility(8);
                    ((ActivityCarPhotoListBinding) CarPhotoListActivity.this.mBaseBinding).viewShade.setVisibility(8);
                    CarPhotoListActivity.this.mPhotoListShowFlag = false;
                    CarPhotoListActivity.this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
                }
            }
        });
        this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
        this.mMiddleTitleBinding.llParent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (CarPhotoListActivity.this.mCarModelMap.size() > 0) {
                    if (CarPhotoListActivity.this.mPhotoListShowFlag) {
                        CarPhotoListActivity.this.mSwipeBackLayout.setCanScroll(true);
                        ((ActivityCarPhotoListBinding) CarPhotoListActivity.this.mBaseBinding).llSelectCarModel.setVisibility(8);
                        ((ActivityCarPhotoListBinding) CarPhotoListActivity.this.mBaseBinding).viewShade.setVisibility(8);
                        CarPhotoListActivity.this.mPhotoListShowFlag = false;
                        CarPhotoListActivity.this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
                        return;
                    }
                    ((ActivityCarPhotoListBinding) CarPhotoListActivity.this.mBaseBinding).llSelectCarModel.setVisibility(0);
                    ((ActivityCarPhotoListBinding) CarPhotoListActivity.this.mBaseBinding).llSelectCarModel.setBackgroundResource(R.drawable.bg_white);
                    ((ActivityCarPhotoListBinding) CarPhotoListActivity.this.mBaseBinding).llSelectCarModel.getBackground().mutate().setAlpha(255);
                    ((ActivityCarPhotoListBinding) CarPhotoListActivity.this.mBaseBinding).viewShade.setVisibility(0);
                    CarPhotoListActivity.this.mPhotoListShowFlag = true;
                    CarPhotoListActivity.this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_up_yellow);
                    CarPhotoListActivity.this.mSwipeBackLayout.setCanScroll(false);
                }
            }
        });
        this.mRootBinding.titleLine.titlebarMiddleParent.removeAllViews();
        this.mRootBinding.titleLine.titlebarMiddleParent.addView(this.mMiddleTitleBinding.getRoot());
        initTitleBarRightPk();
        if (this.mCarModelInfoList.size() <= 0) {
            loadCarsModelList();
        }
        initCarPhotoTabs();
    }

    private void uploadSlidePv() {
        if (this.mFragments.size() > this.mCurrentTab) {
            ((CarPhotoListFragment) this.mFragments.get(this.mCurrentTab)).getLogGatherInfo().setBasePage(getLogCurrentPage());
            ((CarPhotoListFragment) this.mFragments.get(this.mCurrentTab)).getLogGatherInfo().addPageInTime();
        }
    }

    protected void onResume() {
        if (this.mIsFirst) {
            this.mIsFirst = false;
        } else {
            uploadSlidePv();
        }
        super.onResume();
    }

    protected void onPause() {
        if (this.mFragments.size() > this.mCurrentTab) {
            ((CarPhotoListFragment) this.mFragments.get(this.mCurrentTab)).getLogGatherInfo().addPageOutTime();
        }
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        CarImageListDataUtil.getInstance().release();
    }

    private View getCarModelTabView(String titleName) {
        TabMenuItemLayoutBinding binding = TabMenuItemLayoutBinding.inflate(this.mInflater, ((ActivityCarPhotoListBinding) this.mBaseBinding).tabCarModelYear, false);
        binding.tvTitle.setText(titleName);
        return binding.getRoot();
    }

    private View getTabView(int position) {
        TabMenuItemLayoutBinding binding = TabMenuItemLayoutBinding.inflate(this.mInflater, ((ActivityCarPhotoListBinding) this.mBaseBinding).tabCarPhotoType, false);
        binding.tvTitle.setText(((CarPhotoTabItem) this.mTabList.get(position)).title);
        return binding.getRoot();
    }

    private void parserCarsModelData() {
        for (CarModelInfo info : this.mCarModelInfoList) {
            if (this.mCarModelMap.containsKey(info.year)) {
                ((List) this.mCarModelMap.get(info.year)).add(info);
            } else {
                List<CarModelInfo> carModelInfos = new ArrayList();
                carModelInfos.add(info);
                this.mCarModelMap.put(info.year, carModelInfos);
            }
        }
    }

    private void loadCarsModelList() {
        Map<String, Object> map = new HashMap();
        map.put("carsid", String.valueOf(this.mCarSeriesID));
        FengApplication.getInstance().httpRequest("car/carxlist/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (CarPhotoListActivity.this.mCarModelInfoList.size() > 0) {
                    CarPhotoListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    CarPhotoListActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            CarPhotoListActivity.this.loadCarsModelList();
                        }
                    });
                }
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (CarPhotoListActivity.this.mCarModelInfoList.size() > 0) {
                    CarPhotoListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    CarPhotoListActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            CarPhotoListActivity.this.loadCarsModelList();
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
                        List<CarModelInfo> list = baseListModel.list;
                        CarPhotoListActivity.this.hideEmptyView();
                        if (list != null) {
                            CarPhotoListActivity.this.mCarModelInfoList.clear();
                            CarPhotoListActivity.this.mCarModelInfoList.addAll(list);
                        }
                        CarPhotoListActivity.this.initCarModelTabs();
                    } else if (CarPhotoListActivity.this.mCarModelInfoList.size() > 0) {
                        FengApplication.getInstance().checkCode("car/carxlist/", code);
                    } else {
                        CarPhotoListActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                CarPhotoListActivity.this.loadCarsModelList();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    CarPhotoListActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            CarPhotoListActivity.this.loadCarsModelList();
                        }
                    });
                    FengApplication.getInstance().upLoadTryCatchLog("car/carxlist/", content, e);
                }
            }
        });
    }

    public String getLogCurrentPage() {
        if (this.mCarModelID == 0) {
            return "app_car_series_picture_" + this.mJumpPageType + "?index=" + (this.mJumpPageType - 1) + "&carsid=" + this.mCarSeriesID;
        }
        return "app_car_model_picture_" + this.mJumpPageType + "?index=" + (this.mJumpPageType - 1) + "&carsid=" + this.mCarSeriesID + "&carxid" + this.mCarModelID;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CarModelChangeImageEvent event) {
        if (event.type == CarModelChangeImageEvent.TYPE_CAR_IMAGE_LSIT) {
            if (this.mCarModelID != event.info.id) {
                this.mCarModelID = event.info.id;
                this.mYear = event.info.year;
                this.mMiddleTitleBinding.tvTitle.setText(event.info.name);
                if (event.info.id == 0) {
                    ((ActivityCarPhotoListBinding) this.mBaseBinding).tvCarSeries.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.icon_confirm), null);
                    ((ActivityCarPhotoListBinding) this.mBaseBinding).tvCarSeries.setSelected(true);
                } else {
                    ((ActivityCarPhotoListBinding) this.mBaseBinding).tvCarSeries.setCompoundDrawables(null, null, null, null);
                    ((ActivityCarPhotoListBinding) this.mBaseBinding).tvCarSeries.setSelected(false);
                }
                if (this.mFragments.size() > 0) {
                    ((CarPhotoListFragment) this.mFragments.get(this.mCurrentTab)).reload(this.mCarSeriesID, this.mCarModelID, this.mYear);
                }
            }
            this.mSwipeBackLayout.setCanScroll(true);
            this.mPhotoListShowFlag = false;
            this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
            ((ActivityCarPhotoListBinding) this.mBaseBinding).llSelectCarModel.setVisibility(8);
            ((ActivityCarPhotoListBinding) this.mBaseBinding).viewShade.setVisibility(8);
        }
    }
}
