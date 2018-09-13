package com.feng.car.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.NewSubjectActivity;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.adapter.HomeMenuAdater;
import com.feng.car.adapter.HotCarseriesAdapter;
import com.feng.car.databinding.ChoiceFragmentLayoutBinding;
import com.feng.car.databinding.ChoicenessHeaderLayoutBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.ad.AdvertInfo;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.home.HomeMenuModel;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.HomeLabelRefreshEvent;
import com.feng.car.event.HomeSwitchEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.RefreshEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserDisLikeEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.LogBtnConstans;
import com.feng.car.view.SpacesItemDecoration;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView.LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.google.gson.reflect.TypeToken;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChoicenessFragment extends BaseFragment<ChoiceFragmentLayoutBinding> {
    private final int ALREADY_USE_HISTORY = 1;
    private final int NO_USE_HISTORY = 0;
    private List<SnsInfo> mAdSnsList = new ArrayList();
    private CommonPostAdapter mAdater;
    private List<CarSeriesInfo> mCarSeriesInfoList = new ArrayList();
    private Map<Integer, Integer> mCurrentIds = new LinkedHashMap();
    private ChoicenessHeaderLayoutBinding mHeaderBinding;
    private int mHistoryPullNum = 12;
    private int mHistoryPushNum = 6;
    private HotCarseriesAdapter mHotCarseriesAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String mLastCarsID = "";
    private SnsInfoList mList = new SnsInfoList();
    private int mMaxShowNew = 100;
    private HomeMenuAdater mMenuAdater;
    private List<HomeMenuModel> mMenuModels = new ArrayList();
    private Map<Integer, Integer> mNextIds = new LinkedHashMap();
    private SnsInfo mTopSnsInfo = new SnsInfo();
    private List<Integer> mUpHistoryIds = new ArrayList();
    private int mUseHistoryFlag = 0;

    public void backToTop() {
        if (((ChoiceFragmentLayoutBinding) this.mBind).recyclerview != null) {
            ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.scrollToPosition(0);
        }
    }

    protected int setLayoutId() {
        return 2130903182;
    }

    protected void initView() {
        initHistoryData();
        this.mList.setRecommend(true);
        initHeadView();
        this.mAdater = new CommonPostAdapter(this.mActivity, this.mList, 1, false, getLogGatherInfo());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdater);
        ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.addItemDecoration(new SpacesItemDecoration(this.mActivity));
        ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.setIsChoiceness(true);
        ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.setLScrollListener(new LScrollListener() {
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
        ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview, State.Normal);
                ChoicenessFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                if (ChoicenessFragment.this.mNextIds.size() > 0) {
                    LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap();
                    linkedHashMap.putAll(ChoicenessFragment.this.mCurrentIds);
                    ChoicenessFragment.this.mCurrentIds.clear();
                    ChoicenessFragment.this.mCurrentIds.putAll(ChoicenessFragment.this.mNextIds);
                    ChoicenessFragment.this.mNextIds.clear();
                    ChoicenessFragment.this.mCurrentIds.putAll(linkedHashMap);
                }
                ChoicenessFragment.this.mUseHistoryFlag = 0;
                ChoicenessFragment.this.getAdData();
            }
        });
        ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (!((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview.getNoMore() && RecyclerViewStateUtils.getFooterViewState(((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview) != State.Loading) {
                    if (ChoicenessFragment.this.mCurrentIds.size() == 0) {
                        RecyclerViewStateUtils.setFooterViewState(ChoicenessFragment.this.mActivity, ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(ChoicenessFragment.this.mActivity, ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview, 20, State.Loading, null);
                    ChoicenessFragment.this.getNewData(false);
                }
            }
        });
        ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.setRefreshing(true);
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeaderBinding.getRoot());
        this.mHeaderBinding.getRoot().setVisibility(8);
    }

    private void initHistoryData() {
        this.mCurrentIds.putAll(FengApplication.getInstance().getSparkDB().getRecommendHistory());
    }

    protected void onFragmentFirstVisible() {
    }

    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible && this.mList.size() > 0) {
            refreshCars();
        }
    }

    private void initHeadView() {
        this.mHeaderBinding = ChoicenessHeaderLayoutBinding.inflate(LayoutInflater.from(this.mActivity), ((ChoiceFragmentLayoutBinding) this.mBind).rlParent, false);
        this.mMenuModels.add(new HomeMenuModel(0, 2130837915));
        this.mMenuModels.add(new HomeMenuModel(1, 2130837918));
        this.mMenuModels.add(new HomeMenuModel(2, 2130837919));
        this.mMenuModels.add(new HomeMenuModel(3, 2130837917));
        this.mMenuModels.add(new HomeMenuModel(4, 2130837916));
        this.mMenuAdater = new HomeMenuAdater(this.mActivity, this.mMenuModels);
        this.mHeaderBinding.rvHomeMenu.setLayoutManager(new GridLayoutManager(this.mActivity, 5));
        this.mHeaderBinding.rvHomeMenu.setNestedScrollingEnabled(false);
        this.mHeaderBinding.rvHomeMenu.setAdapter(this.mMenuAdater);
        this.mHotCarseriesAdapter = new HotCarseriesAdapter(getActivity(), this.mCarSeriesInfoList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(0);
        this.mHeaderBinding.rvSeeCars.setNestedScrollingEnabled(false);
        this.mHeaderBinding.rvSeeCars.setLayoutManager(manager);
        this.mHeaderBinding.rvSeeCars.setAdapter(this.mHotCarseriesAdapter);
        this.mHotCarseriesAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ChoicenessFragment.this.getActivity(), NewSubjectActivity.class);
                intent.putExtra(HttpConstant.CARS_ID, ((CarSeriesInfo) ChoicenessFragment.this.mCarSeriesInfoList.get(position)).id);
                intent.putExtra(NewSubjectActivity.CIRCLES_REQUEST_ID, ((CarSeriesInfo) ChoicenessFragment.this.mCarSeriesInfoList.get(position)).communityinfo.id);
                ChoicenessFragment.this.startActivity(intent);
            }
        });
    }

    private void getAdData() {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.PAGEID, "992,0");
        map.put("datatype", PushConstants.PUSH_TYPE_NOTIFY);
        map.put("pagecode", PushConstants.PUSH_TYPE_NOTIFY);
        FengApplication.getInstance().httpRequest(HttpConstant.ADVERT_ADSERVER, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ChoicenessFragment.this.getCarsTopic(false);
            }

            public void onStart() {
            }

            public void onFinish() {
                ChoicenessFragment.this.getCarsTopic(false);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        int i;
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        if (ChoicenessFragment.this.mList.size() > 0) {
                            ChoicenessFragment.this.mList.removeAllAd();
                        }
                        ChoicenessFragment.this.mAdSnsList.clear();
                        JSONArray adData = jsonBody.getJSONArray("data");
                        JSONArray adSns = jsonBody.getJSONArray("snsinfolist");
                        int size = adData.length();
                        int snsSize = adSns.length();
                        for (i = 0; i < size; i++) {
                            SnsInfo snsInfo = new SnsInfo();
                            snsInfo.advertInfo = new AdvertInfo();
                            snsInfo.advertInfo.parser(adData.getJSONObject(i));
                            if (snsInfo.advertInfo.pageid == 992) {
                                ChoicenessFragment.this.initBannerAd(snsInfo.advertInfo);
                            } else {
                                snsInfo.snstype = 1000;
                                ChoicenessFragment.this.mAdSnsList.add(snsInfo);
                            }
                        }
                        if (snsSize > 0) {
                            for (SnsInfo snsInfo1 : ChoicenessFragment.this.mAdSnsList) {
                                if (snsInfo1.advertInfo.isinner != 1) {
                                    snsInfo1.id = snsInfo1.advertInfo.adid;
                                    snsInfo1.resourceid = snsInfo1.advertInfo.adid;
                                    snsInfo1.isflag = 0;
                                } else {
                                    i = 0;
                                    while (i < snsSize) {
                                        if (adSns.getJSONObject(i) != null && snsInfo1.advertInfo.contentid == adSns.getJSONObject(i).getInt("resourceid") && snsInfo1.advertInfo.resthype == adSns.getJSONObject(i).getInt("snstype")) {
                                            snsInfo1.parser(adSns.getJSONObject(i));
                                            snsInfo1.snstype = 1000;
                                        }
                                        i++;
                                    }
                                }
                            }
                            return;
                        }
                        for (SnsInfo snsInfo12 : ChoicenessFragment.this.mAdSnsList) {
                            if (snsInfo12.advertInfo.isinner != 1) {
                                snsInfo12.id = snsInfo12.advertInfo.adid;
                                snsInfo12.resourceid = snsInfo12.advertInfo.adid;
                                snsInfo12.isflag = 0;
                            }
                        }
                        return;
                    }
                    ChoicenessFragment.this.mAdSnsList.clear();
                    if (ChoicenessFragment.this.mList.size() > 0) {
                        ChoicenessFragment.this.mList.removeAllAd();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initBannerAd(final AdvertInfo advertInfo) {
        this.mHeaderBinding.adNotice.setVisibility(0);
        this.mHeaderBinding.vAdLine.setVisibility(0);
        this.mHeaderBinding.tvAdLabel.setVisibility(0);
        this.mHeaderBinding.adNotice.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(advertInfo.tmpmap.image.size() > 0 ? (ImageInfo) advertInfo.tmpmap.image.get(0) : new ImageInfo(), FengConstant.IMAGEMIDDLEWIDTH, 0.17f)));
        this.mHeaderBinding.tvAdLabel.setText(advertInfo.tmpmap.label);
        this.mHeaderBinding.adNotice.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                advertInfo.adClickHandle(ChoicenessFragment.this.getActivity());
            }
        });
        advertInfo.adPvHandle(getActivity(), true);
    }

    public void onPause() {
        super.onPause();
    }

    private void getTopFeedData() {
        FengApplication.getInstance().httpRequest(HttpConstant.HOME_TOPFEED, new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (ChoicenessFragment.this.mList.size() <= 0) {
                    ChoicenessFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) ChoicenessFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
                ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (ChoicenessFragment.this.mList.size() <= 0) {
                    ChoicenessFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) ChoicenessFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
                ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        if (jsonBody.has("ontopfeed") && !jsonBody.isNull("ontopfeed")) {
                            ChoicenessFragment.this.mTopSnsInfo.parser(jsonBody.getJSONObject("ontopfeed"));
                        }
                        ChoicenessFragment.this.getHistoryIds();
                    } else if (ChoicenessFragment.this.mList.size() <= 0) {
                        ChoicenessFragment.this.showNetErrorView();
                    } else {
                        FengApplication.getInstance().checkCode(HttpConstant.HOME_TOPFEED, code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (ChoicenessFragment.this.mList.size() <= 0) {
                        ChoicenessFragment.this.showNetErrorView();
                    }
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.HOME_TOPFEED, content, e);
                }
            }
        });
    }

    private void initHistoryIds(boolean isRefresh) {
        this.mUpHistoryIds.clear();
        Iterator<Entry<Integer, Integer>> iter = this.mCurrentIds.entrySet().iterator();
        int size = this.mHistoryPushNum;
        if (isRefresh) {
            size = this.mHistoryPullNum;
        }
        while (iter.hasNext() && size != 0) {
            Entry<Integer, Integer> e = (Entry) iter.next();
            if (isRefresh) {
                size--;
                this.mUpHistoryIds.add(e.getKey());
            } else if (this.mList.getPosition(getHistoryKey(((Integer) e.getKey()).intValue())) == -1) {
                size--;
                this.mUpHistoryIds.add(e.getKey());
            } else {
                iter.remove();
            }
        }
    }

    private void addIdsToHistory() {
        Object linkedHashMap = new LinkedHashMap();
        int maxSize = 200;
        for (Entry<Integer, Integer> e : this.mNextIds.entrySet()) {
            linkedHashMap.put(e.getKey(), e.getValue());
            maxSize--;
            if (maxSize == 0) {
                break;
            }
        }
        if (maxSize > 0) {
            for (Entry<Integer, Integer> e2 : this.mCurrentIds.entrySet()) {
                linkedHashMap.put(e2.getKey(), e2.getValue());
                maxSize--;
                if (maxSize == 0) {
                    break;
                }
            }
        }
        FengApplication.getInstance().getSparkDB().addRecommendHistory(JsonUtil.toJson(linkedHashMap));
    }

    private void getNewData(final boolean isRefresh) {
        if (isRefresh || (this.mUseHistoryFlag != 1 && this.mList.size() < this.mMaxShowNew)) {
            initHistoryIds(isRefresh);
            Map<String, Object> map = new HashMap();
            map.put("timediffer", String.valueOf(0));
            map.put("oldsnsids", this.mUpHistoryIds);
            map.put("gettype", isRefresh ? PushConstants.PUSH_TYPE_NOTIFY : PushConstants.PUSH_TYPE_THROUGH_MESSAGE);
            FengApplication.getInstance().httpRequest(HttpConstant.RECOMMEND_GETNEW, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (ChoicenessFragment.this.mList.size() <= 0) {
                        ChoicenessFragment.this.showNetErrorView();
                    } else {
                        ((BaseActivity) ChoicenessFragment.this.mActivity).showSecondTypeToast(2131231273);
                    }
                    ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onStart() {
                }

                public void onFinish() {
                    ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    if (ChoicenessFragment.this.mList.size() <= 0) {
                        ChoicenessFragment.this.showNetErrorView();
                    } else {
                        ((BaseActivity) ChoicenessFragment.this.mActivity).showSecondTypeToast(2131231273);
                    }
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            ChoicenessFragment.this.hideEmpty();
                            ChoicenessFragment.this.mHeaderBinding.getRoot().setVisibility(0);
                            JSONObject jsonBody = jsonResult.getJSONObject("body");
                            JSONObject jsonSns = jsonBody.getJSONObject("sns");
                            BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                            baseListModel.parser(SnsInfo.class, jsonSns);
                            if (jsonBody.has("usehistory")) {
                                ChoicenessFragment.this.mUseHistoryFlag = jsonBody.getInt("usehistory");
                            }
                            if (ChoicenessFragment.this.mUseHistoryFlag == 1) {
                                for (Integer intValue : ChoicenessFragment.this.mUpHistoryIds) {
                                    ChoicenessFragment.this.mCurrentIds.remove(Integer.valueOf(intValue.intValue()));
                                }
                            }
                            if (isRefresh) {
                                ChoicenessFragment.this.mList.clear();
                                if (jsonBody.has("newfeedcount")) {
                                    int count = jsonBody.getInt("newfeedcount");
                                    if (count > 0) {
                                        ChoicenessFragment.this.showRedDataHint("老司机为您推荐" + count + "条更新内容");
                                    }
                                }
                                if (ChoicenessFragment.this.mTopSnsInfo.id > 0) {
                                    if (ChoicenessFragment.this.mAdSnsList.size() > 0) {
                                        for (SnsInfo info : ChoicenessFragment.this.mAdSnsList) {
                                            if (info.advertInfo.isinner == 1 && ChoicenessFragment.this.mTopSnsInfo.id == info.id) {
                                                ChoicenessFragment.this.mTopSnsInfo.id = 0;
                                                break;
                                            }
                                        }
                                        if (ChoicenessFragment.this.mTopSnsInfo.id > 0) {
                                            ChoicenessFragment.this.mTopSnsInfo.ontop = 1;
                                            baseListModel.list.add(0, ChoicenessFragment.this.mTopSnsInfo);
                                        }
                                    } else {
                                        ChoicenessFragment.this.mTopSnsInfo.ontop = 1;
                                        baseListModel.list.add(0, ChoicenessFragment.this.mTopSnsInfo);
                                    }
                                }
                            }
                            ChoicenessFragment.this.addData(isRefresh, baseListModel.list);
                        } else if (ChoicenessFragment.this.mList.size() <= 0) {
                            ChoicenessFragment.this.showNetErrorView();
                        } else {
                            FengApplication.getInstance().checkCode(HttpConstant.RECOMMEND_GETOLD, code);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (ChoicenessFragment.this.mList.size() <= 0) {
                            ChoicenessFragment.this.showNetErrorView();
                        }
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.RECOMMEND_GETOLD, content, e);
                    }
                }
            });
            return;
        }
        getHinstoryInfo();
    }

    private void getHistoryIds() {
        if (this.mCurrentIds.size() > 50) {
            getNewData(true);
            return;
        }
        FengApplication.getInstance().httpRequest(HttpConstant.RECOMMEND_LISTSNSIDS, new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (ChoicenessFragment.this.mList.size() <= 0) {
                    ChoicenessFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) ChoicenessFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
                ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (ChoicenessFragment.this.mList.size() <= 0) {
                    ChoicenessFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) ChoicenessFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        if (jsonBody.has("snsids")) {
                            JSONArray jsonArray = jsonBody.getJSONArray("snsids");
                            int size = jsonArray.length();
                            for (int i = 0; i < size; i++) {
                                int id = jsonArray.getInt(i);
                                ChoicenessFragment.this.mCurrentIds.put(Integer.valueOf(id), Integer.valueOf(id));
                            }
                            ChoicenessFragment.this.getNewData(true);
                        } else if (ChoicenessFragment.this.mList.size() <= 0) {
                            ChoicenessFragment.this.showNetErrorView();
                        } else {
                            FengApplication.getInstance().checkCode(HttpConstant.RECOMMEND_GETOLD, code);
                        }
                    } else if (ChoicenessFragment.this.mList.size() <= 0) {
                        ChoicenessFragment.this.showNetErrorView();
                    } else {
                        FengApplication.getInstance().checkCode(HttpConstant.RECOMMEND_GETOLD, code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (ChoicenessFragment.this.mList.size() <= 0) {
                        ChoicenessFragment.this.showNetErrorView();
                    }
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.RECOMMEND_GETOLD, content, e);
                }
            }
        });
    }

    private void getHinstoryInfo() {
        initHistoryIds(false);
        Map<String, Object> map = new HashMap();
        map.put("snsids", this.mUpHistoryIds);
        FengApplication.getInstance().httpRequest(HttpConstant.RECOMMEND_LISTSNSINFOBYID, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (ChoicenessFragment.this.mList.size() <= 0) {
                    ChoicenessFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) ChoicenessFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
                ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
                ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (ChoicenessFragment.this.mList.size() <= 0) {
                    ChoicenessFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) ChoicenessFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        for (Integer intValue : ChoicenessFragment.this.mUpHistoryIds) {
                            ChoicenessFragment.this.mCurrentIds.remove(Integer.valueOf(intValue.intValue()));
                        }
                        JSONObject jsonSns = jsonResult.getJSONObject("body").getJSONObject("sns");
                        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SnsInfo.class, jsonSns);
                        int oldSize = ChoicenessFragment.this.mList.size();
                        for (SnsInfo snsInfo : baseListModel.list) {
                            ChoicenessFragment.this.mList.add(snsInfo);
                            ChoicenessFragment.this.mNextIds.put(Integer.valueOf(snsInfo.id), Integer.valueOf(snsInfo.id));
                        }
                        ChoicenessFragment.this.addIdsToHistory();
                        ChoicenessFragment.this.mAdater.notifyItemRangeInserted(oldSize, ChoicenessFragment.this.mList.size() - oldSize);
                    } else if (ChoicenessFragment.this.mList.size() <= 0) {
                        ChoicenessFragment.this.showNetErrorView();
                    } else {
                        FengApplication.getInstance().checkCode(HttpConstant.RECOMMEND_LISTSNSINFOBYID, code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (ChoicenessFragment.this.mList.size() <= 0) {
                        ChoicenessFragment.this.showNetErrorView();
                    }
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.RECOMMEND_LISTSNSINFOBYID, content, e);
                }
            }
        });
    }

    private void showRedDataHint(String strText) {
        ((ChoiceFragmentLayoutBinding) this.mBind).tvRefdataHint.setText(strText);
        ((ChoiceFragmentLayoutBinding) this.mBind).tvRefdataHint.setVisibility(0);
        ((ChoiceFragmentLayoutBinding) this.mBind).tvRefdataHint.postDelayed(new Runnable() {
            public void run() {
                ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).tvRefdataHint.setVisibility(8);
            }
        }, 2000);
    }

    private void addData(boolean isRefresh, List<SnsInfo> list) {
        for (SnsInfo snsInfo : list) {
            if (this.mCurrentIds.containsKey(Integer.valueOf(snsInfo.id))) {
                this.mCurrentIds.remove(Integer.valueOf(snsInfo.id));
            }
            this.mNextIds.put(Integer.valueOf(snsInfo.id), Integer.valueOf(snsInfo.id));
        }
        addIdsToHistory();
        int oldSize = this.mList.size();
        this.mList.addAll(list);
        int size = this.mList.size();
        if (isRefresh) {
            size = this.mList.size();
            String localKey = "";
            for (SnsInfo info : this.mAdSnsList) {
                if (info.advertInfo.isinner == 1 && !info.advertInfo.local_add_feed) {
                    localKey = info.id + "_1";
                    if (this.mList.getPosition(localKey) > -1) {
                        this.mList.remove(this.mList.getPosition(localKey));
                        size--;
                    }
                }
            }
            for (SnsInfo info2 : this.mAdSnsList) {
                if (!info2.advertInfo.local_add_feed && info2.advertInfo.pageid == 0 && info2.advertInfo.pageorder <= size) {
                    this.mList.add(info2.advertInfo.pageorder, info2);
                    info2.advertInfo.local_add_feed = true;
                    size++;
                }
            }
        }
        if (isRefresh) {
            this.mAdater.notifyDataSetChanged();
        } else {
            this.mAdater.notifyItemRangeInserted(oldSize, size - oldSize);
        }
    }

    private void getCarsTopic(final boolean isRefreshCar) {
        Map<String, Object> map = new HashMap();
        String strIDs = SharedUtil.getString(this.mActivity, "see_history_cars");
        this.mLastCarsID = strIDs;
        List<Integer> list = null;
        if (!TextUtils.isEmpty(strIDs)) {
            list = JsonUtil.fromJson(strIDs, new TypeToken<ArrayList<Integer>>() {
            });
        }
        if (list == null) {
            list = new ArrayList();
        }
        map.put("seriesids", list);
        FengApplication.getInstance().httpRequest(HttpConstant.HOT_SERIES_LIST, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (ChoicenessFragment.this.mList.size() <= 0) {
                    ChoicenessFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) ChoicenessFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
                ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (ChoicenessFragment.this.mList.size() <= 0) {
                    ChoicenessFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) ChoicenessFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
                ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        JSONObject jsonSns = jsonBody.getJSONObject("series");
                        BaseListModel<CarSeriesInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CarSeriesInfo.class, jsonSns);
                        ChoicenessFragment.this.mCarSeriesInfoList.clear();
                        ChoicenessFragment.this.mCarSeriesInfoList.addAll(baseListModel.list);
                        if (ChoicenessFragment.this.mCarSeriesInfoList.size() == 0) {
                            ChoicenessFragment.this.mHeaderBinding.rvSeeCars.setVisibility(8);
                        } else {
                            ChoicenessFragment.this.mHeaderBinding.rvSeeCars.setVisibility(0);
                            ChoicenessFragment.this.mHeaderBinding.rvSeeCars.scrollToPosition(0);
                        }
                        ChoicenessFragment.this.mHotCarseriesAdapter.notifyDataSetChanged();
                        if (!isRefreshCar) {
                            if (jsonBody.has("historynum")) {
                                ChoicenessFragment.this.mMaxShowNew = jsonBody.getInt("historynum");
                            }
                            if (jsonBody.has("pullnum")) {
                                ChoicenessFragment.this.mHistoryPullNum = jsonBody.getInt("pullnum");
                            }
                            if (jsonBody.has("pushnum")) {
                                ChoicenessFragment.this.mHistoryPushNum = jsonBody.getInt("pushnum");
                            }
                            ChoicenessFragment.this.getTopFeedData();
                            return;
                        }
                        return;
                    }
                    if (ChoicenessFragment.this.mList.size() <= 0) {
                        ChoicenessFragment.this.showNetErrorView();
                    } else {
                        ((BaseActivity) ChoicenessFragment.this.mActivity).showSecondTypeToast(2131231273);
                    }
                    ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview, State.Normal);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void refreshCars() {
        String strIDs = SharedUtil.getString(this.mActivity, "see_history_cars");
        if (!TextUtils.isEmpty(strIDs) && !strIDs.equals(this.mLastCarsID)) {
            getCarsTopic(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (event != null) {
            this.mAdater.refreshUserStatus(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event != null) {
            this.mAdater.refreshSnsInfo(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        if (event != null && event.type == 2) {
            getAdData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HomeLabelRefreshEvent event) {
        if (event.labelId == 0 && isAdded()) {
            ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    public void loginSuccess() {
        this.mList.clear();
        ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.forceToRefresh();
    }

    public void loginOut() {
        this.mList.clear();
        ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.forceToRefresh();
    }

    public void showNetErrorView() {
        if (((ChoiceFragmentLayoutBinding) this.mBind).emptyView != null) {
            ((ChoiceFragmentLayoutBinding) this.mBind).emptyView.setEmptyImage(2130838112);
            ((ChoiceFragmentLayoutBinding) this.mBind).emptyView.setEmptyText(2131231188);
            ((ChoiceFragmentLayoutBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ((ChoiceFragmentLayoutBinding) ChoicenessFragment.this.mBind).recyclerview.forceToRefresh();
                }
            });
            ((ChoiceFragmentLayoutBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void hideEmpty() {
        ((ChoiceFragmentLayoutBinding) this.mBind).emptyView.setVisibility(8);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((ChoiceFragmentLayoutBinding) this.mBind).recyclerview.setIsScrollDown(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserDisLikeEvent event) {
        int pos = this.mList.getPosition(event.snsInfo.getLocalkey());
        if (pos >= 0) {
            this.mList.remove(pos);
            this.mAdater.removeNotifyItem(pos);
            this.mCurrentIds.remove(Integer.valueOf(event.snsInfo.id));
            this.mNextIds.remove(Integer.valueOf(event.snsInfo.id));
            addIdsToHistory();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HomeSwitchEvent event) {
        if (isAdded()) {
            getLogGatherInfo().addLogBtnEvent(LogBtnConstans.APP_BTN_CAR_MODEL_LIBRARY);
        }
    }

    private String getHistoryKey(int id) {
        return id + "_1";
    }

    private void changeSnsTime(SnsInfo info) {
    }
}
