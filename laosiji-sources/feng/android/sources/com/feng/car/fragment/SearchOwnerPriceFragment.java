package com.feng.car.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.PriceRankingCarsNewActivity;
import com.feng.car.adapter.CarsOwnerPriceAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.car.CarSeriesInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.HttpConstant;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView.LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class SearchOwnerPriceFragment extends BaseFragment<CommonRecyclerviewBinding> {
    private CarsOwnerPriceAdapter mAdater;
    private int mCurrentCityId = 131;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private CarSeriesInfoList mList = new CarSeriesInfoList();
    public int mPageTotal = 0;
    private String mSearchKey = "";

    protected int setLayoutId() {
        return 2130903204;
    }

    public static SearchOwnerPriceFragment newInstance(String searchKey) {
        Bundle args = new Bundle();
        args.putString(HttpConstant.SEARCH_KEY, searchKey);
        SearchOwnerPriceFragment allSearchFragment = new SearchOwnerPriceFragment();
        allSearchFragment.setArguments(args);
        return allSearchFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSearchKey = getArguments().getString(FengConstant.FENGTYPE, "");
    }

    protected void initView() {
        this.mAdater = new CarsOwnerPriceAdapter(getActivity(), this.mList.getCarSeriesInfoList());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdater);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchOwnerPriceFragment.this.mBind).recyclerview, State.Normal);
                SearchOwnerPriceFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                SearchOwnerPriceFragment.this.mCurrentPage = 1;
                if (TextUtils.isEmpty(SearchOwnerPriceFragment.this.mSearchKey)) {
                    ((CommonRecyclerviewBinding) SearchOwnerPriceFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchOwnerPriceFragment.this.mBind).recyclerview, State.Normal);
                    return;
                }
                SearchOwnerPriceFragment.this.getData();
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) SearchOwnerPriceFragment.this.mBind).recyclerview) != State.Loading) {
                    if (SearchOwnerPriceFragment.this.mCurrentPage <= SearchOwnerPriceFragment.this.mPageTotal) {
                        RecyclerViewStateUtils.setFooterViewState(SearchOwnerPriceFragment.this.getActivity(), ((CommonRecyclerviewBinding) SearchOwnerPriceFragment.this.mBind).recyclerview, 20, State.Loading, null);
                        SearchOwnerPriceFragment.this.getData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(SearchOwnerPriceFragment.this.getActivity(), ((CommonRecyclerviewBinding) SearchOwnerPriceFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
                }
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLScrollListener(new LScrollListener() {
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
        this.mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SearchOwnerPriceFragment.this.getActivity(), PriceRankingCarsNewActivity.class);
                intent.putExtra(HttpConstant.CARS_ID, SearchOwnerPriceFragment.this.mList.get(position).id);
                SearchOwnerPriceFragment.this.startActivity(intent);
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshing(true);
    }

    private void getData() {
        if (!TextUtils.isEmpty(this.mSearchKey)) {
            Map<String, Object> map = new HashMap();
            String strKey = this.mSearchKey;
            Matcher emoticon = HttpConstant.PATTERN_EMOTICON_TEXT.matcher(strKey);
            while (emoticon.find()) {
                String key = emoticon.group();
                String value = (String) EmoticonsRule.sXhsEmoticonTextCodeHashMap.get(key);
                if (!TextUtils.isEmpty(value)) {
                    strKey = strKey.replace(key, value);
                }
            }
            map.put("search", strKey);
            map.put(HttpConstant.CITYID, String.valueOf(this.mCurrentCityId));
            map.put(HttpConstant.PAGE, String.valueOf(this.mCurrentPage));
            map.put("type", String.valueOf(4));
            FengApplication.getInstance().httpRequest(HttpConstant.SEARCH_YWF_INDEX_API, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (SearchOwnerPriceFragment.this.mList.size() > 0) {
                        ((BaseActivity) SearchOwnerPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                    } else {
                        SearchOwnerPriceFragment.this.showErrorView();
                    }
                    ((CommonRecyclerviewBinding) SearchOwnerPriceFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchOwnerPriceFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onStart() {
                }

                public void onFinish() {
                    ((CommonRecyclerviewBinding) SearchOwnerPriceFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchOwnerPriceFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    if (SearchOwnerPriceFragment.this.mList.size() > 0) {
                        ((BaseActivity) SearchOwnerPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                    } else {
                        SearchOwnerPriceFragment.this.showErrorView();
                    }
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject object = new JSONObject(content);
                        if (object.getInt("code") == 1) {
                            SearchOwnerPriceFragment.this.hideEmpty();
                            JSONObject jsonObject = object.getJSONObject("body").getJSONObject("search").getJSONObject("seriesprice");
                            BaseListModel<CarSeriesInfo> baseListModel = new BaseListModel();
                            baseListModel.parser(CarSeriesInfo.class, jsonObject);
                            SearchOwnerPriceFragment.this.mPageTotal = baseListModel.pagecount;
                            if (SearchOwnerPriceFragment.this.mCurrentPage == 1) {
                                SearchOwnerPriceFragment.this.mList.clear();
                            }
                            SearchOwnerPriceFragment.this.mList.addAll(baseListModel.list);
                            if (SearchOwnerPriceFragment.this.mCurrentPage == 1 && SearchOwnerPriceFragment.this.mList.size() == 0) {
                                SearchOwnerPriceFragment.this.showNoDataEmpty();
                            }
                            SearchOwnerPriceFragment.this.mCurrentPage = SearchOwnerPriceFragment.this.mCurrentPage + 1;
                            SearchOwnerPriceFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                        } else if (SearchOwnerPriceFragment.this.mList.size() > 0) {
                            ((BaseActivity) SearchOwnerPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                        } else {
                            SearchOwnerPriceFragment.this.showErrorView();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.printStackTrace();
                        if (SearchOwnerPriceFragment.this.mList.size() > 0) {
                            ((BaseActivity) SearchOwnerPriceFragment.this.mActivity).showSecondTypeToast(2131230831);
                        } else {
                            SearchOwnerPriceFragment.this.showErrorView();
                        }
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.SEARCH_YWF_INDEX_API, content, e);
                    }
                }
            });
        }
    }

    public void showErrorView() {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(2130838112);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231188);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ((CommonRecyclerviewBinding) SearchOwnerPriceFragment.this.mBind).recyclerview.forceToRefresh();
                }
            });
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    public void showNoDataEmpty() {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.hideEmptyImage();
            ((CommonRecyclerviewBinding) this.mBind).emptyView.hideEmptyButton();
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231300);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void hideEmpty() {
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(8);
    }

    public void loginSuccess() {
        super.loginSuccess();
        if (isAdded()) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    public void checkSearchKey(String searchKey, int cityID) {
        if (isAdded() && !TextUtils.isEmpty(searchKey)) {
            if (!searchKey.equals(this.mSearchKey) || this.mCurrentCityId != cityID) {
                hideEmpty();
                this.mSearchKey = searchKey;
                this.mCurrentCityId = cityID;
                ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setIsScrollDown(true);
        }
    }
}
