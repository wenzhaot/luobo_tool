package com.feng.car.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CarsConditionAdapter;
import com.feng.car.adapter.CarsPriceItemAdapter;
import com.feng.car.adapter.SearchCarResultAdapter;
import com.feng.car.databinding.ActivitySearchcarResultBinding;
import com.feng.car.databinding.SearchCarsHeadLayoutBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.car.CarSeriesInfoList;
import com.feng.car.entity.searchcar.SearchCarBean;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SearchCarEvent;
import com.feng.car.event.SearchCityEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.MapUtil;
import com.feng.car.utils.SearchCarManager;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchCarResultActivity extends BaseActivity<ActivitySearchcarResultBinding> {
    private int m12;
    private int m16;
    private SearchCarResultAdapter mAdapter;
    private CarsConditionAdapter mConditionAdapter;
    private List<SearchCarBean> mConditionList = new ArrayList();
    private int mCurrentPage = 1;
    private SearchCarsHeadLayoutBinding mHeadLayoutBinding;
    private boolean mIsSlidingToFirst = false;
    private boolean mIsSlidingToFirstCars = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private CarSeriesInfoList mList = new CarSeriesInfoList();
    private List<CarSeriesInfo> mPriceCarsList = new ArrayList();
    private CarsPriceItemAdapter mPriceItemAdapter;
    private int mTotalPage = 0;

    public int setBaseContentView() {
        return R.layout.activity_searchcar_result;
    }

    public void initView() {
        boolean z;
        hideDefaultTitleBar();
        Resources resources = getResources();
        this.m12 = resources.getDimensionPixelOffset(R.dimen.default_12PX);
        this.m16 = resources.getDimensionPixelOffset(R.dimen.default_16PX);
        this.mCityId = MapUtil.newInstance().getCurrentCityId();
        ((ActivitySearchcarResultBinding) this.mBaseBinding).tvCityName.setText(MapUtil.newInstance().getCurrentCityName());
        ((ActivitySearchcarResultBinding) this.mBaseBinding).ivBack.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarResultActivity.this.finish();
            }
        });
        ((ActivitySearchcarResultBinding) this.mBaseBinding).tvCityName.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarResultActivity.this.startActivity(new Intent(SearchCarResultActivity.this, CityListActivity.class));
            }
        });
        ((ActivitySearchcarResultBinding) this.mBaseBinding).llBrand.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarResultActivity.this.startActivity(new Intent(SearchCarResultActivity.this, BrandConditionActivity.class));
                SearchCarResultActivity.this.overridePendingTransition(R.anim.bottom_in_anim, 0);
            }
        });
        ((ActivitySearchcarResultBinding) this.mBaseBinding).llPrice.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarResultActivity.this.startActivity(new Intent(SearchCarResultActivity.this, PriceConditionActivity.class));
                SearchCarResultActivity.this.overridePendingTransition(R.anim.bottom_in_anim, 0);
            }
        });
        ((ActivitySearchcarResultBinding) this.mBaseBinding).llLevel.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarResultActivity.this.startActivity(new Intent(SearchCarResultActivity.this, LevelConditionActivity.class));
                SearchCarResultActivity.this.overridePendingTransition(R.anim.bottom_in_anim, 0);
            }
        });
        ((ActivitySearchcarResultBinding) this.mBaseBinding).llMore.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarResultActivity.this.startActivity(new Intent(SearchCarResultActivity.this, MoreConditionActivity.class));
                SearchCarResultActivity.this.overridePendingTransition(R.anim.bottom_in_anim, 0);
            }
        });
        this.mAdapter = new SearchCarResultAdapter(this, this.mList.getCarSeriesInfoList());
        SearchCarResultAdapter searchCarResultAdapter = this.mAdapter;
        if (SearchCarManager.newInstance().getPriceType() == SearchCarManager.REAL_PRICE_TYPE) {
            z = true;
        } else {
            z = false;
        }
        searchCarResultAdapter.setIsOwnerSearch(z);
        this.mAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                if (SearchCarResultActivity.this.mList.size() > position) {
                    Intent intent = new Intent(SearchCarResultActivity.this, NewSubjectActivity.class);
                    intent.putExtra("carsid", SearchCarResultActivity.this.mList.get(position).id);
                    intent.putExtra(NewSubjectActivity.CIRCLES_REQUEST_ID, SearchCarResultActivity.this.mList.get(position).communityinfo.id);
                    SearchCarResultActivity.this.startActivity(intent);
                }
            }
        });
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((ActivitySearchcarResultBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivitySearchcarResultBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((ActivitySearchcarResultBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((ActivitySearchcarResultBinding) this.mBaseBinding).recyclerview.setPullRefreshEnabled(false);
        ((ActivitySearchcarResultBinding) this.mBaseBinding).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((ActivitySearchcarResultBinding) SearchCarResultActivity.this.mBaseBinding).recyclerview) != State.Loading) {
                    if (SearchCarResultActivity.this.mCurrentPage <= SearchCarResultActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(SearchCarResultActivity.this, ((ActivitySearchcarResultBinding) SearchCarResultActivity.this.mBaseBinding).recyclerview, 20, State.Loading, null);
                        SearchCarResultActivity.this.getData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(SearchCarResultActivity.this, ((ActivitySearchcarResultBinding) SearchCarResultActivity.this.mBaseBinding).recyclerview, 20, State.TheEnd, null);
                }
            }
        });
        ((ActivitySearchcarResultBinding) this.mBaseBinding).recyclerview.setLScrollListener(new LRecyclerView$LScrollListener() {
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
        this.mConditionAdapter = new CarsConditionAdapter(this, this.mConditionList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(0);
        ((ActivitySearchcarResultBinding) this.mBaseBinding).rvCondition.setLayoutManager(layoutManager);
        ((ActivitySearchcarResultBinding) this.mBaseBinding).rvCondition.setAdapter(this.mConditionAdapter);
        ((ActivitySearchcarResultBinding) this.mBaseBinding).rvCondition.addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == 0) {
                    if (manager.findFirstCompletelyVisibleItemPosition() == 0 && SearchCarResultActivity.this.mIsSlidingToFirst) {
                        SearchCarResultActivity.this.mSwipeBackLayout.setCanScroll(true);
                    }
                } else if (newState == 1) {
                    SearchCarResultActivity.this.mSwipeBackLayout.setCanScroll(false);
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx > 0) {
                    SearchCarResultActivity.this.mIsSlidingToFirst = false;
                } else {
                    SearchCarResultActivity.this.mIsSlidingToFirst = true;
                }
            }
        });
        ((ActivitySearchcarResultBinding) this.mBaseBinding).rvCondition.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        SearchCarResultActivity.this.mSwipeBackLayout.setCanScroll(false);
                        break;
                    case 1:
                        SearchCarResultActivity.this.mSwipeBackLayout.setCanScroll(true);
                        break;
                }
                return false;
            }
        });
        this.mConditionAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                SearchCarManager.newInstance().clearSelectCondition((SearchCarBean) SearchCarResultActivity.this.mConditionList.get(position));
                SearchCarResultActivity.this.mConditionList.remove(position);
                SearchCarResultActivity.this.mConditionAdapter.notifyDataSetChanged();
                if (SearchCarManager.newInstance().hasOtherConditionSelect()) {
                    ((ActivitySearchcarResultBinding) SearchCarResultActivity.this.mBaseBinding).cbOptional.setVisibility(0);
                    ((ActivitySearchcarResultBinding) SearchCarResultActivity.this.mBaseBinding).ivShade.setVisibility(0);
                } else {
                    ((ActivitySearchcarResultBinding) SearchCarResultActivity.this.mBaseBinding).cbOptional.setVisibility(8);
                    ((ActivitySearchcarResultBinding) SearchCarResultActivity.this.mBaseBinding).ivShade.setVisibility(8);
                }
                SearchCarResultActivity.this.refreshData();
            }
        });
        changeCondition();
        initHeadView();
        refreshData();
    }

    private void changeCondition() {
        if (SearchCarManager.newInstance().getPriceType() == SearchCarManager.GUIDE_PRICE_TYPE) {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).rlTitleBar.setBackgroundResource(R.drawable.gradient_ff9718_ff890a);
        } else {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).rlTitleBar.setBackgroundResource(R.drawable.gradient_62d1fc_33a4f7);
        }
        if (SearchCarManager.newInstance().getBrandId() > 0) {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).tvBrandText.setSelected(true);
            if (SearchCarManager.newInstance().getPriceType() == SearchCarManager.GUIDE_PRICE_TYPE) {
                ((ActivitySearchcarResultBinding) this.mBaseBinding).tvBrandText.setTextColor(ContextCompat.getColor(this, R.color.color_ffb90a));
            } else {
                ((ActivitySearchcarResultBinding) this.mBaseBinding).tvBrandText.setTextColor(ContextCompat.getColor(this, R.color.color_33a4f7));
            }
        } else {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).tvBrandText.setSelected(false);
            ((ActivitySearchcarResultBinding) this.mBaseBinding).tvBrandText.setTextColor(ContextCompat.getColor(this, R.color.color_ffffff));
        }
        ((ActivitySearchcarResultBinding) this.mBaseBinding).tvBrandText.setPadding(this.m16, 0, this.m16, 0);
        if (SearchCarManager.newInstance().hasPriceCondition()) {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).tvPriceText.setSelected(true);
            if (SearchCarManager.newInstance().getPriceType() == SearchCarManager.GUIDE_PRICE_TYPE) {
                ((ActivitySearchcarResultBinding) this.mBaseBinding).tvPriceText.setText(R.string.car_recommend_price_text);
                ((ActivitySearchcarResultBinding) this.mBaseBinding).tvPriceText.setTextColor(ContextCompat.getColor(this, R.color.color_ffb90a));
            } else {
                ((ActivitySearchcarResultBinding) this.mBaseBinding).tvPriceText.setText(R.string.owner_price);
                ((ActivitySearchcarResultBinding) this.mBaseBinding).tvPriceText.setTextColor(ContextCompat.getColor(this, R.color.color_33a4f7));
            }
        } else {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).tvPriceText.setSelected(false);
            if (SearchCarManager.newInstance().getPriceType() == SearchCarManager.GUIDE_PRICE_TYPE) {
                ((ActivitySearchcarResultBinding) this.mBaseBinding).tvPriceText.setText(R.string.car_recommend_price_text);
            } else {
                ((ActivitySearchcarResultBinding) this.mBaseBinding).tvPriceText.setText(R.string.owner_price);
            }
            ((ActivitySearchcarResultBinding) this.mBaseBinding).tvPriceText.setTextColor(ContextCompat.getColor(this, R.color.color_ffffff));
        }
        ((ActivitySearchcarResultBinding) this.mBaseBinding).tvPriceText.setPadding(this.m12, 0, this.m12, 0);
        if (SearchCarManager.newInstance().hasLevelCondition()) {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).tvLevelText.setSelected(true);
            if (SearchCarManager.newInstance().getPriceType() == SearchCarManager.GUIDE_PRICE_TYPE) {
                ((ActivitySearchcarResultBinding) this.mBaseBinding).tvLevelText.setTextColor(ContextCompat.getColor(this, R.color.color_ffb90a));
            } else {
                ((ActivitySearchcarResultBinding) this.mBaseBinding).tvLevelText.setTextColor(ContextCompat.getColor(this, R.color.color_33a4f7));
            }
        } else {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).tvLevelText.setSelected(false);
            ((ActivitySearchcarResultBinding) this.mBaseBinding).tvLevelText.setTextColor(ContextCompat.getColor(this, R.color.color_ffffff));
        }
        ((ActivitySearchcarResultBinding) this.mBaseBinding).tvLevelText.setPadding(this.m12, 0, this.m12, 0);
        if (SearchCarManager.newInstance().hasOtherCondition()) {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).tvMoreText.setSelected(true);
            if (SearchCarManager.newInstance().getPriceType() == SearchCarManager.GUIDE_PRICE_TYPE) {
                ((ActivitySearchcarResultBinding) this.mBaseBinding).tvMoreText.setTextColor(ContextCompat.getColor(this, R.color.color_ffb90a));
            } else {
                ((ActivitySearchcarResultBinding) this.mBaseBinding).tvMoreText.setTextColor(ContextCompat.getColor(this, R.color.color_33a4f7));
            }
        } else {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).tvMoreText.setSelected(false);
            ((ActivitySearchcarResultBinding) this.mBaseBinding).tvMoreText.setTextColor(ContextCompat.getColor(this, R.color.color_ffffff));
        }
        ((ActivitySearchcarResultBinding) this.mBaseBinding).tvMoreText.setPadding(this.m16, 0, this.m16, 0);
    }

    private void initHeadView() {
        if (SearchCarManager.newInstance().hasOtherConditionSelect()) {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).cbOptional.setVisibility(0);
            ((ActivitySearchcarResultBinding) this.mBaseBinding).ivShade.setVisibility(0);
        } else {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).cbOptional.setVisibility(8);
            ((ActivitySearchcarResultBinding) this.mBaseBinding).ivShade.setVisibility(8);
        }
        ((ActivitySearchcarResultBinding) this.mBaseBinding).cbOptional.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SearchCarManager.newInstance().setHasSelection(isChecked);
                SearchCarResultActivity.this.refreshData();
            }
        });
        this.mConditionList.addAll(SearchCarManager.newInstance().getAllSelectConditionList());
        this.mHeadLayoutBinding = SearchCarsHeadLayoutBinding.inflate(this.mInflater);
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeadLayoutBinding.getRoot());
        this.mPriceItemAdapter = new CarsPriceItemAdapter(this, this.mPriceCarsList);
        this.mHeadLayoutBinding.rvCars.setAdapter(this.mPriceItemAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(0);
        this.mHeadLayoutBinding.rvCars.setLayoutManager(layoutManager);
        this.mHeadLayoutBinding.rvCars.addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == 0) {
                    if (manager.findFirstCompletelyVisibleItemPosition() == 0 && SearchCarResultActivity.this.mIsSlidingToFirstCars) {
                        SearchCarResultActivity.this.mSwipeBackLayout.setCanScroll(true);
                    }
                } else if (newState == 1) {
                    SearchCarResultActivity.this.mSwipeBackLayout.setCanScroll(false);
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx > 0) {
                    SearchCarResultActivity.this.mIsSlidingToFirstCars = false;
                } else {
                    SearchCarResultActivity.this.mIsSlidingToFirstCars = true;
                }
            }
        });
        this.mPriceItemAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SearchCarResultActivity.this, NewSubjectActivity.class);
                intent.putExtra("carsid", ((CarSeriesInfo) SearchCarResultActivity.this.mPriceCarsList.get(position)).id);
                intent.putExtra(NewSubjectActivity.CIRCLES_REQUEST_ID, ((CarSeriesInfo) SearchCarResultActivity.this.mPriceCarsList.get(position)).communityinfo.id);
                SearchCarResultActivity.this.startActivity(intent);
            }
        });
        this.mHeadLayoutBinding.tvSearchPrice.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarManager.newInstance().setPriceType(SearchCarManager.REAL_PRICE_TYPE);
                SearchCarResultActivity.this.refreshData();
            }
        });
    }

    private void refreshData() {
        changeCondition();
        this.mCurrentPage = 1;
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        int brandid = SearchCarManager.newInstance().getBrandId();
        if (brandid != 0) {
            map.put("brandid", String.valueOf(brandid));
        }
        map.put("searchtype", String.valueOf(SearchCarManager.newInstance().getPriceType()));
        map.put("cityid", String.valueOf(MapUtil.newInstance().getCurrentCityId()));
        map.put("searchterms", SearchCarManager.newInstance().getAllConditionData());
        if (!"0".equals(SearchCarManager.newInstance().getCurrentPriceValueString())) {
            map.put("pricerange", SearchCarManager.newInstance().getCurrentPriceValueString());
        }
        map.put("containoption", SearchCarManager.newInstance().hasSelection() ? "1" : "0");
        map.put("page", String.valueOf(this.mCurrentPage));
        FengApplication.getInstance().cancelRequest("carsearch/series/");
        FengApplication.getInstance().httpRequest("carsearch/series/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (SearchCarResultActivity.this.mCurrentPage == 1) {
                    SearchCarResultActivity.this.showErrorView();
                } else {
                    SearchCarResultActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
                SearchCarResultActivity.this.hideProgress();
            }

            public void onStart() {
                SearchCarResultActivity.this.showLaoSiJiDialog();
                if (SearchCarResultActivity.this.mCurrentPage == 1) {
                    ((ActivitySearchcarResultBinding) SearchCarResultActivity.this.mBaseBinding).recyclerview.setVisibility(8);
                }
            }

            public void onFinish() {
                SearchCarResultActivity.this.hideProgress();
                ((ActivitySearchcarResultBinding) SearchCarResultActivity.this.mBaseBinding).recyclerview.setVisibility(0);
                ((ActivitySearchcarResultBinding) SearchCarResultActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivitySearchcarResultBinding) SearchCarResultActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (SearchCarResultActivity.this.mCurrentPage == 1) {
                    SearchCarResultActivity.this.showErrorView();
                } else {
                    SearchCarResultActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
                SearchCarResultActivity.this.hideProgress();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        SearchCarResultActivity.this.hideEmptyView();
                        SearchCarResultActivity.this.parserData(jsonResult.getJSONObject("body").getJSONObject("cars").toString());
                        return;
                    }
                    FengApplication.getInstance().checkCode("carsearch/series/", code);
                    if (SearchCarResultActivity.this.mCurrentPage == 1) {
                        SearchCarResultActivity.this.showErrorView();
                    } else {
                        SearchCarResultActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (SearchCarResultActivity.this.mCurrentPage == 1) {
                        SearchCarResultActivity.this.showErrorView();
                    } else {
                        SearchCarResultActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("carsearch/series/", content, e);
                }
            }
        });
    }

    private void parserData(String jsonData) throws JSONException {
        boolean z = true;
        BaseListModel<CarSeriesInfo> baseListModel = new BaseListModel();
        baseListModel.parser(CarSeriesInfo.class, new JSONObject(jsonData));
        List<CarSeriesInfo> list = baseListModel.list;
        List<CarSeriesInfo> list1 = baseListModel.list1;
        if (this.mCurrentPage == 1) {
            boolean isShowBrand;
            if (this.mConditionList.size() == 0) {
                this.mTotalPage = 1000;
            } else {
                this.mTotalPage = 1;
            }
            this.mList.clear();
            if (list == null || list.size() <= 0) {
                showNoDataEmpty();
                if (list1 == null || list1.size() <= 0) {
                    this.mHeadLayoutBinding.showStopSellingCars.setVisibility(8);
                } else {
                    this.mHeadLayoutBinding.showStopSellingCars.setVisibility(0);
                    this.mHeadLayoutBinding.showStopSellingCars.setOnClickListener(new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            SearchCarResultActivity.this.startActivity(new Intent(SearchCarResultActivity.this, StopSellingCarActivity.class));
                        }
                    });
                }
            } else {
                this.mList.addAll(list);
                hideNoDataEmpty();
                this.mHeadLayoutBinding.showStopSellingCars.setVisibility(8);
            }
            if (baseListModel.list2 == null || baseListModel.list2.size() <= 0) {
                this.mHeadLayoutBinding.rlOwnerCarPrice.setVisibility(8);
                this.mHeadLayoutBinding.vLine.setVisibility(8);
            } else {
                this.mPriceCarsList.clear();
                this.mPriceCarsList.addAll(baseListModel.list2);
                this.mHeadLayoutBinding.rlOwnerCarPrice.setVisibility(0);
                this.mHeadLayoutBinding.vLine.setVisibility(0);
                this.mHeadLayoutBinding.tvText.setText("这些车" + SearchCarManager.newInstance().getCurrentPriceValue().getPriceSection() + "也能买");
                this.mPriceItemAdapter.notifyDataSetChanged();
            }
            if (SearchCarManager.newInstance().getBrandId() != 0) {
                isShowBrand = true;
            } else {
                isShowBrand = false;
            }
            if (isShowBrand) {
                carsModelGroup();
            }
            this.mAdapter.setIsShowBrand(isShowBrand);
            this.mAdapter.setStopSaleList(list1);
        } else {
            if (list.size() == 0) {
                this.mTotalPage = this.mCurrentPage;
            }
            this.mList.addAll(list);
        }
        this.mCurrentPage++;
        SearchCarResultAdapter searchCarResultAdapter = this.mAdapter;
        if (SearchCarManager.newInstance().getPriceType() != SearchCarManager.REAL_PRICE_TYPE) {
            z = false;
        }
        searchCarResultAdapter.setIsOwnerSearch(z);
        this.mAdapter.notifyDataSetChanged();
        if (this.mCurrentPage <= 2) {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).recyclerview.scrollToPosition(0);
        }
    }

    private void carsModelGroup() {
        Collections.sort(this.mList.getCarSeriesInfoList(), new Comparator<CarSeriesInfo>() {
            public int compare(CarSeriesInfo lhs, CarSeriesInfo rhs) {
                return Integer.valueOf(lhs.factory.factoryPosition).compareTo(Integer.valueOf(rhs.factory.factoryPosition));
            }
        });
        setFlagsInCarModel();
    }

    private void setFlagsInCarModel() {
        int i = 0;
        while (i < this.mList.size()) {
            if (!(i == this.mList.size() - 1 || this.mList.get(i).factory.name.equals(this.mList.get(i + 1).factory.name))) {
                this.mList.get(i).poslastflag = 1;
            }
            if (!(i == 0 || this.mList.get(i).factory.name.equals(this.mList.get(i - 1).factory.name))) {
                this.mList.get(i).posfirstflag = 1;
            }
            i++;
        }
        if (this.mList.size() > 0) {
            this.mList.get(0).posfirstflag = 1;
            this.mList.get(this.mList.size() - 1).poslastflag = 1;
        }
    }

    private void showNoDataEmpty() {
        LayoutParams params = (LayoutParams) this.mHeadLayoutBinding.headEmptyView.getLayoutParams();
        params.width = FengUtil.getScreenWidth(this);
        this.mHeadLayoutBinding.headEmptyView.setLayoutParams(params);
        this.mHeadLayoutBinding.headEmptyView.setEmptyImage(R.drawable.icon_cars_empty);
        this.mHeadLayoutBinding.headEmptyView.setEmptyText(R.string.unfind_cars);
        if (SearchCarManager.newInstance().getPriceType() == SearchCarManager.REAL_PRICE_TYPE) {
            this.mHeadLayoutBinding.headEmptyView.setButtonListener(R.string.use_car_search_guide_price, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    SearchCarManager.newInstance().setPriceType(SearchCarManager.GUIDE_PRICE_TYPE);
                    SearchCarResultActivity.this.refreshData();
                }
            });
        } else {
            this.mHeadLayoutBinding.headEmptyView.hideEmptyButton();
        }
        this.mHeadLayoutBinding.headEmptyView.setVisibility(0);
    }

    private void hideNoDataEmpty() {
        this.mHeadLayoutBinding.headEmptyView.setVisibility(8);
    }

    private void showErrorView() {
        initNormalTitleBar("");
        showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarResultActivity.this.refreshData();
            }
        });
    }

    public void hideEmptyView() {
        super.hideEmptyView();
        hideDefaultTitleBar();
    }

    public String getLogCurrentPage() {
        return "app_search_car_result?brandid=" + SearchCarManager.newInstance().getBrandId() + "&case=" + SearchCarManager.newInstance().getAllConditionData() + "&price=" + SearchCarManager.newInstance().getCurrentPriceValueString() + "&cityid=" + MapUtil.newInstance().getCurrentCityId() + "&pricetype=" + SearchCarManager.newInstance().getPriceType() + "&containoption=" + (SearchCarManager.newInstance().hasSelection() ? "1" : "0");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchCityEvent event) {
        if (event != null && !TextUtils.isEmpty(event.cityInfo.name)) {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).tvCityName.setText(MapUtil.newInstance().getCurrentCityName());
            if (this.mCityId != event.cityInfo.id) {
                this.mCityId = event.cityInfo.id;
                refreshData();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchCarEvent event) {
        this.mConditionList.clear();
        if (SearchCarManager.newInstance().hasOtherConditionSelect()) {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).cbOptional.setVisibility(0);
            ((ActivitySearchcarResultBinding) this.mBaseBinding).ivShade.setVisibility(0);
        } else {
            ((ActivitySearchcarResultBinding) this.mBaseBinding).cbOptional.setVisibility(8);
            ((ActivitySearchcarResultBinding) this.mBaseBinding).ivShade.setVisibility(8);
        }
        this.mConditionList.addAll(SearchCarManager.newInstance().getAllSelectConditionList());
        this.mConditionAdapter.notifyDataSetChanged();
        refreshData();
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }

    public void finish() {
        super.finish();
        FengApplication.getInstance().cancelRequest("carsearch/series/");
        SearchCarManager.newInstance().clearAllCondition();
    }
}
