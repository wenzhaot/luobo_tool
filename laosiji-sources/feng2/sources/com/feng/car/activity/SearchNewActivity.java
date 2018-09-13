package com.feng.car.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.AssociatedSearchAdapter;
import com.feng.car.databinding.ActivityNewSearchBinding;
import com.feng.car.databinding.TabCarFriendsCircleItemLayoutBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.entity.search.AssociatedSearchInfo;
import com.feng.car.entity.search.SearchItem;
import com.feng.car.entity.search.SearchKey;
import com.feng.car.event.NewSearchSwitchEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SearchCityEvent;
import com.feng.car.fragment.BaseFragment;
import com.feng.car.fragment.SearchAllFragment;
import com.feng.car.fragment.SearchCircleFragment;
import com.feng.car.fragment.SearchContentFragment;
import com.feng.car.fragment.SearchOwnerPriceFragment;
import com.feng.car.fragment.SearchUserFragment;
import com.feng.car.fragment.SearchVoiceFragment;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.MapUtil;
import com.feng.car.video.player.JCVideoPlayer;
import com.feng.car.view.tagview.TagCloudView$OnTagClickListener;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.WifiUtil;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.stub.StubApp;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchNewActivity extends BaseActivity<ActivityNewSearchBinding> {
    public static final int SEARCH_RESULT_ALL_TYPE = 1;
    public static final int SEARCH_RESULT_CAR_STRATEGY_TYPE = 3;
    public static final int SEARCH_RESULT_CIRCLE_TYPE = 5;
    public static final int SEARCH_RESULT_CONTENT_TYPE = 7;
    public static final int SEARCH_RESULT_ONWER_TYPE = 4;
    public static final int SEARCH_RESULT_USER_TYPE = 6;
    private FragmentPagerAdapter mAdapter;
    private List<String> mAllTab = new ArrayList();
    private List<AssociatedSearchInfo> mAssociatedList = new ArrayList();
    private AssociatedSearchAdapter mAssociatedSearchAdapter;
    private int mAutoIndex = 0;
    private int mCurrentIndex = 0;
    private List<BaseFragment> mFragments = new ArrayList();
    private int mFromType = 1;
    private boolean mIsFirst = true;
    private boolean mIsZtFlag = false;
    private boolean mNotAssociate = false;
    private SearchKey mSearchKey = new SearchKey();
    private List<SearchItem> mSearchRecord = new ArrayList();

    private class EditChangedListener implements TextWatcher {
        private EditChangedListener() {
        }

        /* synthetic */ EditChangedListener(SearchNewActivity x0, AnonymousClass1 x1) {
            this();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() <= 0) {
                SearchNewActivity.this.hideEmptyView();
                SearchNewActivity.this.showSearchRecord(false);
            } else if (SearchNewActivity.this.mNotAssociate) {
                SearchNewActivity.this.mNotAssociate = false;
            } else {
                SearchNewActivity.this.loadAutoSearchData(s.toString());
            }
        }
    }

    private class EditorActionListener implements OnEditorActionListener {
        private EditorActionListener() {
        }

        /* synthetic */ EditorActionListener(SearchNewActivity x0, AnonymousClass1 x1) {
            this();
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 3) {
                return false;
            }
            if (((String) SearchNewActivity.this.mSearchKey.searchKey.get()).trim().length() > 0) {
                FengUtil.closeSoftKeyboard(v);
                SearchNewActivity.this.saveSearchReconds();
                SearchNewActivity.this.mRootBinding.titleLine.etSearchKey.clearFocus();
                ((ActivityNewSearchBinding) SearchNewActivity.this.mBaseBinding).rlRecordDel.setVisibility(8);
                ((ActivityNewSearchBinding) SearchNewActivity.this.mBaseBinding).tgRecordView.setVisibility(8);
                SearchNewActivity.this.startSearch();
                MobclickAgent.onEvent(SearchNewActivity.this, "search");
            }
            return true;
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_new_search;
    }

    public void initView() {
        closeSwip();
        this.mCityId = MapUtil.newInstance().getCurrentCityId();
        this.mFromType = getIntent().getIntExtra("type", 1);
        initSearchTitleBar(this.mSearchKey, (int) R.string.search_text, new EditorActionListener(this, null), new EditChangedListener(this, null));
        this.mRootBinding.titleLine.etSearchKey.setFilters(new InputFilter[]{new LengthFilter(100)});
        changeSearchHint();
        showSearchRecord(false);
        initAssociatedView();
        initTab();
        this.mRootBinding.titleLine.etSearchKey.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    return;
                }
                if (TextUtils.isEmpty((CharSequence) SearchNewActivity.this.mSearchKey.searchKey.get())) {
                    SearchNewActivity.this.showSearchRecord(false);
                } else if (SearchNewActivity.this.mAutoIndex != SearchNewActivity.this.mCurrentIndex) {
                    SearchNewActivity.this.loadAutoSearchData((String) SearchNewActivity.this.mSearchKey.searchKey.get());
                } else {
                    SearchNewActivity.this.showAssociateData();
                }
            }
        });
        ((ActivityNewSearchBinding) this.mBaseBinding).tvRecordDel.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                FengApplication.getInstance().getSparkDB().deleteAllSerchRecord(SearchItem.SEARCH_CAR_USER_CONTENT_TYPE);
                ((ActivityNewSearchBinding) SearchNewActivity.this.mBaseBinding).rlRecordDel.setVisibility(8);
                ((ActivityNewSearchBinding) SearchNewActivity.this.mBaseBinding).tgRecordView.setVisibility(8);
            }
        });
    }

    private void changeSearchHint() {
        if (this.mFromType == 3) {
            this.mRootBinding.titleLine.etSearchKey.setHint(R.string.search_car_strategy);
        } else if (this.mFromType == 4) {
            this.mRootBinding.titleLine.etSearchKey.setHint(R.string.search_owner_price);
        } else if (this.mFromType == 5) {
            this.mRootBinding.titleLine.etSearchKey.setHint(R.string.search_circle);
        } else if (this.mFromType == 6) {
            this.mRootBinding.titleLine.etSearchKey.setHint(R.string.search_users);
        } else if (this.mFromType == 7) {
            this.mRootBinding.titleLine.etSearchKey.setHint(R.string.search_contont);
        } else {
            this.mRootBinding.titleLine.etSearchKey.setHint(R.string.search_text);
        }
        if (this.mFragments.size() > this.mCurrentIndex) {
            ((BaseFragment) this.mFragments.get(this.mCurrentIndex)).getLogGatherInfo().setBasePage(getLogCurrentPage());
            ((BaseFragment) this.mFragments.get(this.mCurrentIndex)).getLogGatherInfo().addPageInTime();
        }
    }

    private void initAssociatedView() {
        ((ActivityNewSearchBinding) this.mBaseBinding).rvAssociateRecord.setVisibility(8);
        this.mAssociatedSearchAdapter = new AssociatedSearchAdapter(this, this.mAssociatedList);
        ((ActivityNewSearchBinding) this.mBaseBinding).rvAssociateRecord.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityNewSearchBinding) this.mBaseBinding).rvAssociateRecord.setAdapter(this.mAssociatedSearchAdapter);
        this.mAssociatedSearchAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                AssociatedSearchInfo searchInfo = (AssociatedSearchInfo) SearchNewActivity.this.mAssociatedList.get(position);
                Intent intent;
                if (searchInfo.type == 2 || searchInfo.type == 3) {
                    SearchNewActivity.this.changSearchKey((String) searchInfo.sns.title.get());
                } else if (searchInfo.type == 1) {
                    SearchNewActivity.this.mIsZtFlag = true;
                    if (SearchNewActivity.this.mFragments.size() > SearchNewActivity.this.mCurrentIndex) {
                        ((BaseFragment) SearchNewActivity.this.mFragments.get(SearchNewActivity.this.mCurrentIndex)).getLogGatherInfo().setCurrentPage(SearchNewActivity.this.getLogCurrentPage());
                    }
                    searchInfo.user.intentToPersonalHome(SearchNewActivity.this);
                } else if (searchInfo.type == 0) {
                    SearchNewActivity.this.mIsZtFlag = true;
                    if (SearchNewActivity.this.mFragments.size() > SearchNewActivity.this.mCurrentIndex) {
                        ((BaseFragment) SearchNewActivity.this.mFragments.get(SearchNewActivity.this.mCurrentIndex)).getLogGatherInfo().setCurrentPage(SearchNewActivity.this.getLogCurrentPage());
                    }
                    if (SearchNewActivity.this.mCurrentIndex == 4) {
                        intent = new Intent(SearchNewActivity.this, PriceRankingCarsNewActivity.class);
                        intent.putExtra("carsid", searchInfo.series.id);
                        SearchNewActivity.this.startActivity(intent);
                        return;
                    }
                    intent = new Intent(SearchNewActivity.this, NewSubjectActivity.class);
                    intent.putExtra("carsid", searchInfo.series.id);
                    intent.putExtra(NewSubjectActivity.CIRCLES_REQUEST_ID, searchInfo.series.communityinfo.id);
                    SearchNewActivity.this.startActivity(intent);
                } else if (searchInfo.type == 4) {
                    SearchNewActivity.this.mIsZtFlag = true;
                    if (SearchNewActivity.this.mFragments.size() > SearchNewActivity.this.mCurrentIndex) {
                        ((BaseFragment) SearchNewActivity.this.mFragments.get(SearchNewActivity.this.mCurrentIndex)).getLogGatherInfo().setCurrentPage(SearchNewActivity.this.getLogCurrentPage());
                    }
                    intent = new Intent(SearchNewActivity.this, PopularProgramListActivity.class);
                    intent.putExtra("hotshowid", searchInfo.hotshow.id);
                    intent.putExtra("hot_show_name", searchInfo.hotshow.name);
                    SearchNewActivity.this.startActivity(intent);
                }
            }
        });
    }

    private void initTab() {
        this.mAllTab.add("全部");
        this.mAllTab.add("内容");
        this.mAllTab.add("话题");
        this.mAllTab.add("选车攻略");
        this.mAllTab.add("查成交价");
        this.mAllTab.add("用户");
        this.mFragments.add(SearchAllFragment.newInstance((String) this.mSearchKey.searchKey.get()));
        this.mFragments.add(SearchContentFragment.newInstance((String) this.mSearchKey.searchKey.get()));
        this.mFragments.add(SearchCircleFragment.newInstance((String) this.mSearchKey.searchKey.get()));
        this.mFragments.add(SearchVoiceFragment.newInstance((String) this.mSearchKey.searchKey.get()));
        this.mFragments.add(SearchOwnerPriceFragment.newInstance((String) this.mSearchKey.searchKey.get()));
        this.mFragments.add(SearchUserFragment.newInstance((String) this.mSearchKey.searchKey.get()));
        this.mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            public int getCount() {
                return SearchNewActivity.this.mFragments.size();
            }

            public Fragment getItem(int position) {
                return (Fragment) SearchNewActivity.this.mFragments.get(position);
            }
        };
        ((ActivityNewSearchBinding) this.mBaseBinding).vpSearch.setAdapter(this.mAdapter);
        this.mCurrentIndex = getIndexByFromType();
        ((ActivityNewSearchBinding) this.mBaseBinding).vpSearch.setCurrentItem(this.mCurrentIndex);
        ((ActivityNewSearchBinding) this.mBaseBinding).tbMenu.setupWithViewPager(((ActivityNewSearchBinding) this.mBaseBinding).vpSearch);
        for (int i = 0; i < ((ActivityNewSearchBinding) this.mBaseBinding).tbMenu.getTabCount(); i++) {
            ((ActivityNewSearchBinding) this.mBaseBinding).tbMenu.getTabAt(i).setCustomView(getAllCarTabView(i));
        }
        ((ActivityNewSearchBinding) this.mBaseBinding).vpSearch.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (SearchNewActivity.this.mCurrentIndex != position) {
                    ((BaseFragment) SearchNewActivity.this.mFragments.get(SearchNewActivity.this.mCurrentIndex)).getLogGatherInfo().addPageOutTime();
                }
                SearchNewActivity.this.mCurrentIndex = position;
                SearchNewActivity.this.mFromType = SearchNewActivity.this.getFromTypeByIndex();
                SearchNewActivity.this.changeSearchHint();
                SearchNewActivity.this.startSearch();
                JCVideoPlayer.releaseAllVideos();
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(false));
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        if (this.mCurrentIndex == 0) {
            changeSearchHint();
        }
    }

    private int getFromTypeByIndex() {
        switch (this.mCurrentIndex) {
            case 1:
                return 7;
            case 2:
                return 5;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 6;
            default:
                return 1;
        }
    }

    private int getIndexByFromType() {
        switch (this.mFromType) {
            case 1:
                return 0;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 2;
            case 6:
                return 5;
            default:
                return 1;
        }
    }

    private View getAllCarTabView(int position) {
        TabCarFriendsCircleItemLayoutBinding binding = TabCarFriendsCircleItemLayoutBinding.inflate(this.mInflater, ((ActivityNewSearchBinding) this.mBaseBinding).tbMenu, false);
        binding.tvTitle.setText((CharSequence) this.mAllTab.get(position));
        return binding.getRoot();
    }

    private void saveSearchReconds() {
        SearchItem item = new SearchItem();
        item.content = ((String) this.mSearchKey.searchKey.get()).trim();
        item.type = SearchItem.SEARCH_CAR_USER_CONTENT_TYPE;
        FengApplication.getInstance().getSparkDB().addSearchRecond(item);
    }

    private void checkKeyWord(String strKey) {
        String searchKeyAd = FengApplication.getInstance().getSearchKeyAd();
        if (!TextUtils.isEmpty(searchKeyAd) && !searchKeyAd.equals("0") && searchKeyAd.indexOf("|||") > 0) {
            String[] strSp = searchKeyAd.split("\\|\\|\\|");
            if (strSp.length == 2 && strKey.equals(strSp[0])) {
                FengApplication.getInstance().handlerUrlSkip(this, strSp[1], "", false, null);
            }
        }
    }

    private void loadAutoSearchData(String strKey) {
        checkKeyWord(strKey);
        this.mAutoIndex = this.mCurrentIndex;
        Map<String, Object> map = new HashMap();
        Matcher emoticon = HttpConstant.PATTERN_EMOTICON_TEXT.matcher(strKey);
        while (emoticon.find()) {
            String key = emoticon.group();
            String value = (String) EmoticonsRule.sXhsEmoticonTextCodeHashMap.get(key);
            if (!TextUtils.isEmpty(value)) {
                strKey = strKey.replace(key, value);
            }
        }
        final String s = strKey;
        map.put("prefix", strKey);
        map.put("type", String.valueOf(this.mFromType));
        FengApplication.getInstance().httpRequest("search/ywf/suggestion/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                SearchNewActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        SearchNewActivity.this.loadAutoSearchData(s);
                    }
                });
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                SearchNewActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        SearchNewActivity.this.loadAutoSearchData(s);
                    }
                });
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    if (jsonObject.getInt("code") == 1) {
                        JSONObject searchJson = jsonObject.getJSONObject("body").getJSONObject("suggestion");
                        BaseListModel<AssociatedSearchInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(AssociatedSearchInfo.class, searchJson);
                        SearchNewActivity.this.mAssociatedList.clear();
                        SearchNewActivity.this.mAssociatedList.addAll(baseListModel.list);
                        SearchNewActivity.this.mAssociatedSearchAdapter.setSearchIndex(SearchNewActivity.this.mCurrentIndex);
                        SearchNewActivity.this.showAssociateData();
                        SearchNewActivity.this.hideEmptyView();
                        return;
                    }
                    SearchNewActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("search/ywf/suggestion/", content, e);
                    SearchNewActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }
        });
    }

    private void showAssociateData() {
        ((ActivityNewSearchBinding) this.mBaseBinding).rlTab.setVisibility(4);
        ((ActivityNewSearchBinding) this.mBaseBinding).vpSearch.setVisibility(4);
        ((ActivityNewSearchBinding) this.mBaseBinding).llBubbleCityContainer.setVisibility(8);
        ((ActivityNewSearchBinding) this.mBaseBinding).rlRecordDel.setVisibility(8);
        ((ActivityNewSearchBinding) this.mBaseBinding).tgRecordView.setVisibility(8);
        if (this.mAssociatedSearchAdapter != null) {
            ((ActivityNewSearchBinding) this.mBaseBinding).rvAssociateRecord.setVisibility(0);
            this.mAssociatedSearchAdapter.notifyDataSetChanged();
        }
    }

    private void showSearchRecord(boolean isAll) {
        ((ActivityNewSearchBinding) this.mBaseBinding).rvAssociateRecord.setVisibility(8);
        ((ActivityNewSearchBinding) this.mBaseBinding).rlTab.setVisibility(4);
        ((ActivityNewSearchBinding) this.mBaseBinding).vpSearch.setVisibility(4);
        ((ActivityNewSearchBinding) this.mBaseBinding).llBubbleCityContainer.setVisibility(8);
        final List<SearchItem> list = FengApplication.getInstance().getSparkDB().getSearchReconds(SearchItem.SEARCH_CAR_USER_CONTENT_TYPE);
        this.mSearchRecord.clear();
        this.mSearchRecord.addAll(list);
        if (this.mSearchRecord.size() > 0) {
            ((ActivityNewSearchBinding) this.mBaseBinding).rlRecordDel.setVisibility(0);
            ((ActivityNewSearchBinding) this.mBaseBinding).tgRecordView.setVisibility(0);
        } else {
            ((ActivityNewSearchBinding) this.mBaseBinding).rlRecordDel.setVisibility(8);
            ((ActivityNewSearchBinding) this.mBaseBinding).tgRecordView.setVisibility(8);
        }
        ((ActivityNewSearchBinding) this.mBaseBinding).tgRecordView.setRecodeTags(list);
        ((ActivityNewSearchBinding) this.mBaseBinding).tgRecordView.setVisibility(0);
        ((ActivityNewSearchBinding) this.mBaseBinding).tgRecordView.setOnTagClickListener(new TagCloudView$OnTagClickListener() {
            public void onTagClick(TextView tagView, CircleInfo circleInfo, int position) {
                SearchNewActivity.this.changSearchKey(((SearchItem) list.get(position)).content);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NewSearchSwitchEvent event) {
        switch (event.type) {
            case 2:
                ((ActivityNewSearchBinding) this.mBaseBinding).vpSearch.setCurrentItem(2);
                return;
            case 5:
                ((ActivityNewSearchBinding) this.mBaseBinding).vpSearch.setCurrentItem(5);
                return;
            default:
                return;
        }
    }

    private void changSearchKey(String strKey) {
        this.mNotAssociate = true;
        this.mRootBinding.titleLine.etSearchKey.setText(strKey);
        this.mSearchKey.searchKey.set(strKey);
        FengUtil.closeSoftKeyboard(this.mRootBinding.titleLine.etSearchKey);
        ((ActivityNewSearchBinding) this.mBaseBinding).rlRecordDel.setVisibility(8);
        ((ActivityNewSearchBinding) this.mBaseBinding).tgRecordView.setVisibility(8);
        MobclickAgent.onEvent(this, "search");
        saveSearchReconds();
        startSearch();
    }

    private void startSearch() {
        this.mIsZtFlag = false;
        if (this.mFragments.size() > this.mCurrentIndex) {
            ((BaseFragment) this.mFragments.get(this.mCurrentIndex)).getLogGatherInfo().setCurrentPage(getLogCurrentPage());
        }
        this.mRootBinding.titleLine.etSearchKey.setFocusable(false);
        this.mRootBinding.titleLine.etSearchKey.setFocusableInTouchMode(false);
        this.mRootBinding.titleLine.etSearchKey.clearFocus();
        ((ActivityNewSearchBinding) this.mBaseBinding).rlTab.setVisibility(0);
        ((ActivityNewSearchBinding) this.mBaseBinding).vpSearch.setVisibility(0);
        ((ActivityNewSearchBinding) this.mBaseBinding).llBubbleCityContainer.setVisibility(8);
        ((ActivityNewSearchBinding) this.mBaseBinding).rlRecordDel.setVisibility(8);
        ((ActivityNewSearchBinding) this.mBaseBinding).tgRecordView.setVisibility(8);
        ((ActivityNewSearchBinding) this.mBaseBinding).rvAssociateRecord.setVisibility(8);
        if (WifiUtil.isNetworkAvailable(StubApp.getOrigApplicationContext(getApplicationContext()))) {
            ((BaseFragment) this.mFragments.get(this.mCurrentIndex)).checkSearchKey((String) this.mSearchKey.searchKey.get(), this.mCityId);
            this.mRootBinding.titleLine.etSearchKey.setFocusable(true);
            this.mRootBinding.titleLine.etSearchKey.setFocusableInTouchMode(true);
            return;
        }
        showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((BaseFragment) SearchNewActivity.this.mFragments.get(SearchNewActivity.this.mCurrentIndex)).checkSearchKey((String) SearchNewActivity.this.mSearchKey.searchKey.get(), SearchNewActivity.this.mCityId);
            }
        });
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }

    protected void onResume() {
        if (this.mIsFirst) {
            this.mIsFirst = false;
        } else if (this.mFragments.size() > this.mCurrentIndex) {
            ((BaseFragment) this.mFragments.get(this.mCurrentIndex)).getLogGatherInfo().setBasePage(getLogCurrentPage());
            ((BaseFragment) this.mFragments.get(this.mCurrentIndex)).getLogGatherInfo().addPageInTime();
        }
        super.onResume();
    }

    protected void onPause() {
        if (this.mFragments.size() > this.mCurrentIndex) {
            ((BaseFragment) this.mFragments.get(this.mCurrentIndex)).getLogGatherInfo().addPageOutTime();
        }
        super.onPause();
    }

    public String getLogCurrentPage() {
        if (TextUtils.isEmpty((CharSequence) this.mSearchKey.searchKey.get())) {
            if (this.mFromType == 3) {
                return "app_search_host_voice_topic";
            }
            if (this.mFromType == 4) {
                return "app_search_host_onwer_price";
            }
            if (this.mFromType == 5) {
                return "app_search_host_circle";
            }
            if (this.mFromType == 6) {
                return "app_search_host_user";
            }
            if (this.mFromType == 7) {
                return "app_search_host_content";
            }
            return "app_search";
        } else if (this.mFromType == 3) {
            return "app_search_host_voice_topic?keyword=" + ((String) this.mSearchKey.searchKey.get()) + (this.mIsZtFlag ? "&type=1" : "");
        } else if (this.mFromType == 4) {
            return "app_search_host_onwer_price?keyword=" + ((String) this.mSearchKey.searchKey.get()) + "&cityid=" + this.mCityId + (this.mIsZtFlag ? "&type=1" : "");
        } else if (this.mFromType == 5) {
            return "app_search_host_circle?keyword=" + ((String) this.mSearchKey.searchKey.get()) + (this.mIsZtFlag ? "&type=1" : "");
        } else if (this.mFromType == 6) {
            return "app_search_host_user?keyword=" + ((String) this.mSearchKey.searchKey.get()) + (this.mIsZtFlag ? "&type=1" : "");
        } else if (this.mFromType == 7) {
            return "app_search_host_content?keyword=" + ((String) this.mSearchKey.searchKey.get()) + (this.mIsZtFlag ? "&type=1" : "");
        } else {
            return "app_search?keyword=" + ((String) this.mSearchKey.searchKey.get()) + "&cityid=" + this.mCityId + (this.mIsZtFlag ? "&type=1" : "");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchCityEvent searchCityEvent) {
        if (searchCityEvent != null && !TextUtils.isEmpty(searchCityEvent.cityInfo.name) && this.mCityId != searchCityEvent.cityInfo.id) {
            this.mCityId = searchCityEvent.cityInfo.id;
            if (this.mCurrentIndex == 0 || this.mCurrentIndex == 3) {
                ((BaseFragment) this.mFragments.get(this.mCurrentIndex)).checkSearchKey((String) this.mSearchKey.searchKey.get(), this.mCityId);
            }
        }
    }
}
