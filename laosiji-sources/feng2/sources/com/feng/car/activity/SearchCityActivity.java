package com.feng.car.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CitySelectAdapter;
import com.feng.car.adapter.SearchRecordAdapter;
import com.feng.car.databinding.ActivitySearchCityBinding;
import com.feng.car.entity.lcoation.CityInfo;
import com.feng.car.entity.search.SearchItem;
import com.feng.car.event.SearchCityEvent;
import com.feng.car.event.SearchRecordEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.MapUtil;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SearchCityActivity extends BaseActivity<ActivitySearchCityBinding> {
    private List<CityInfo> cityList = new ArrayList();
    private CitySelectAdapter mCityAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SearchRecordAdapter mRecordAdapter;
    private List<SearchItem> mSearchRecord = new ArrayList();

    public int setBaseContentView() {
        return R.layout.activity_search_city;
    }

    public void initView() {
        hideDefaultTitleBar();
        ((ActivitySearchCityBinding) this.mBaseBinding).tvCancel.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCityActivity.this.finish();
            }
        });
        ((ActivitySearchCityBinding) this.mBaseBinding).etSearchKey.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() <= 0) {
                    ((ActivitySearchCityBinding) SearchCityActivity.this.mBaseBinding).emptyView.setVisibility(8);
                    ((ActivitySearchCityBinding) SearchCityActivity.this.mBaseBinding).recyclerView.setVisibility(8);
                    ((ActivitySearchCityBinding) SearchCityActivity.this.mBaseBinding).recordRecyclerView.setVisibility(0);
                    return;
                }
                List<CityInfo> list = MapUtil.newInstance().getCityListByName(SearchCityActivity.this, s.toString());
                if (list.size() > 0) {
                    if (SearchCityActivity.this.mCityAdapter == null) {
                        SearchCityActivity.this.cityList.addAll(list);
                        SearchCityActivity.this.mCityAdapter = new CitySelectAdapter(SearchCityActivity.this, SearchCityActivity.this.cityList);
                        SearchCityActivity.this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(SearchCityActivity.this.mCityAdapter);
                        ((ActivitySearchCityBinding) SearchCityActivity.this.mBaseBinding).recyclerView.setAdapter(SearchCityActivity.this.mLRecyclerViewAdapter);
                        ((ActivitySearchCityBinding) SearchCityActivity.this.mBaseBinding).recyclerView.setLayoutManager(new LinearLayoutManager(SearchCityActivity.this));
                        ((ActivitySearchCityBinding) SearchCityActivity.this.mBaseBinding).recyclerView.setNoMore(true);
                        ((ActivitySearchCityBinding) SearchCityActivity.this.mBaseBinding).recyclerView.setPullRefreshEnabled(false);
                        SearchCityActivity.this.mLRecyclerViewAdapter.removeFooterView(SearchCityActivity.this.mLRecyclerViewAdapter.getFooterView());
                        SearchCityActivity.this.mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
                            public void onItemClick(View view, int position) {
                                CityInfo info = (CityInfo) SearchCityActivity.this.cityList.get(position);
                                SearchItem item = new SearchItem();
                                item.content = info.name;
                                item.type = SearchItem.SEARCH_CITY_TYPE;
                                FengApplication.getInstance().getSparkDB().addSearchRecond(item);
                                MapUtil.newInstance().updateCurrentCityName(SearchCityActivity.this, info.name);
                                EventBus.getDefault().post(new SearchCityEvent(info));
                                SearchCityActivity.this.finish();
                            }
                        });
                    } else {
                        SearchCityActivity.this.cityList.clear();
                        SearchCityActivity.this.cityList.addAll(list);
                        SearchCityActivity.this.mCityAdapter.notifyDataSetChanged();
                    }
                    ((ActivitySearchCityBinding) SearchCityActivity.this.mBaseBinding).recyclerView.setVisibility(0);
                    ((ActivitySearchCityBinding) SearchCityActivity.this.mBaseBinding).emptyView.setVisibility(8);
                    ((ActivitySearchCityBinding) SearchCityActivity.this.mBaseBinding).recordRecyclerView.setVisibility(8);
                    return;
                }
                ((ActivitySearchCityBinding) SearchCityActivity.this.mBaseBinding).recyclerView.setVisibility(8);
                ((ActivitySearchCityBinding) SearchCityActivity.this.mBaseBinding).emptyView.setVisibility(0);
                ((ActivitySearchCityBinding) SearchCityActivity.this.mBaseBinding).recordRecyclerView.setVisibility(8);
            }

            public void afterTextChanged(Editable s) {
            }
        });
        showSearchRecord(false);
    }

    private void showSearchRecord(boolean isAll) {
        List<SearchItem> list = FengApplication.getInstance().getSparkDB().getSearchReconds(SearchItem.SEARCH_CITY_TYPE);
        this.mSearchRecord.clear();
        this.mSearchRecord.addAll(list);
        if (this.mSearchRecord.size() > 0) {
            ((ActivitySearchCityBinding) this.mBaseBinding).recordRecyclerView.setVisibility(0);
        } else {
            ((ActivitySearchCityBinding) this.mBaseBinding).recordRecyclerView.setVisibility(8);
        }
        if (this.mRecordAdapter == null) {
            this.mRecordAdapter = new SearchRecordAdapter(this, this.mSearchRecord);
            ((ActivitySearchCityBinding) this.mBaseBinding).recordRecyclerView.setAdapter(this.mRecordAdapter);
            ((ActivitySearchCityBinding) this.mBaseBinding).recordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            return;
        }
        this.mRecordAdapter.adapterNotifyDataSetChanged(isAll);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchRecordEvent event) {
        if (event.type == 1) {
            showSearchRecord(true);
        } else if (event.type == 3) {
            SearchItem item = event.searchItem;
            if (item.type != SearchItem.SEARCH_SHOW_ALL_RECORD_TYPE && item.type != SearchItem.SEARCH_SHOW_DEL_RECORD_TYPE) {
                CityInfo info = MapUtil.newInstance().getCityInfoByName(this, item.content);
                MapUtil.newInstance().updateCurrentCityName(this, item.content);
                EventBus.getDefault().post(new SearchCityEvent(info));
                finish();
            }
        } else {
            FengApplication.getInstance().getSparkDB().deleteAllSerchRecord(SearchItem.SEARCH_CAR_USER_CONTENT_TYPE);
            ((ActivitySearchCityBinding) this.mBaseBinding).recyclerView.setVisibility(8);
        }
    }

    public void finish() {
        super.finish();
        FengUtil.closeSoftKeyboard(((ActivitySearchCityBinding) this.mBaseBinding).etSearchKey);
    }
}
