package com.feng.car.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.adapter.CircleAccededAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.entity.circle.CircleInfoList;
import com.feng.car.event.AddCircleEvent;
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

public class SearchCircleFragment extends BaseFragment<CommonRecyclerviewBinding> {
    private CircleAccededAdapter mAdater;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private CircleInfoList mList = new CircleInfoList();
    public int mPageTotal = 0;
    private String mSearchKey = "";

    protected int setLayoutId() {
        return 2130903204;
    }

    public static SearchCircleFragment newInstance(String searchKey) {
        Bundle args = new Bundle();
        args.putString(HttpConstant.SEARCH_KEY, searchKey);
        SearchCircleFragment allSearchFragment = new SearchCircleFragment();
        allSearchFragment.setArguments(args);
        return allSearchFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSearchKey = getArguments().getString(FengConstant.FENGTYPE, "");
    }

    protected void initView() {
        this.mAdater = new CircleAccededAdapter(getActivity(), this.mList.getCircleList(), CircleAccededAdapter.NORMAL_TYPE);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdater);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchCircleFragment.this.mBind).recyclerview, State.Normal);
                SearchCircleFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                SearchCircleFragment.this.mCurrentPage = 1;
                if (TextUtils.isEmpty(SearchCircleFragment.this.mSearchKey)) {
                    ((CommonRecyclerviewBinding) SearchCircleFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchCircleFragment.this.mBind).recyclerview, State.Normal);
                    return;
                }
                SearchCircleFragment.this.getData();
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) SearchCircleFragment.this.mBind).recyclerview) != State.Loading) {
                    if (SearchCircleFragment.this.mCurrentPage <= SearchCircleFragment.this.mPageTotal) {
                        RecyclerViewStateUtils.setFooterViewState(SearchCircleFragment.this.getActivity(), ((CommonRecyclerviewBinding) SearchCircleFragment.this.mBind).recyclerview, 20, State.Loading, null);
                        SearchCircleFragment.this.getData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(SearchCircleFragment.this.getActivity(), ((CommonRecyclerviewBinding) SearchCircleFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
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
                SearchCircleFragment.this.mList.get(position).intentToCircleFinalPage(SearchCircleFragment.this.getActivity());
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
            map.put(HttpConstant.PAGE, String.valueOf(this.mCurrentPage));
            map.put("type", String.valueOf(5));
            FengApplication.getInstance().httpRequest(HttpConstant.SEARCH_YWF_INDEX_API, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (SearchCircleFragment.this.mList.size() > 0) {
                        ((BaseActivity) SearchCircleFragment.this.mActivity).showSecondTypeToast(2131231273);
                    } else {
                        SearchCircleFragment.this.showErrorView();
                    }
                    ((CommonRecyclerviewBinding) SearchCircleFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchCircleFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onStart() {
                }

                public void onFinish() {
                    ((CommonRecyclerviewBinding) SearchCircleFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchCircleFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    if (SearchCircleFragment.this.mList.size() > 0) {
                        ((BaseActivity) SearchCircleFragment.this.mActivity).showSecondTypeToast(2131231273);
                    } else {
                        SearchCircleFragment.this.showErrorView();
                    }
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject object = new JSONObject(content);
                        if (object.getInt("code") == 1) {
                            SearchCircleFragment.this.hideEmpty();
                            JSONObject jsonSearch = object.getJSONObject("body").getJSONObject("search");
                            if (jsonSearch.has(HttpConstant.COMMUNITY) && !jsonSearch.isNull(HttpConstant.COMMUNITY)) {
                                BaseListModel<CircleInfo> baseListModel = new BaseListModel();
                                baseListModel.parser(CircleInfo.class, jsonSearch.getJSONObject(HttpConstant.COMMUNITY));
                                SearchCircleFragment.this.mPageTotal = baseListModel.pagecount;
                                if (SearchCircleFragment.this.mCurrentPage == 1) {
                                    SearchCircleFragment.this.mList.clear();
                                }
                                SearchCircleFragment.this.mList.addAll(baseListModel.list);
                                if (SearchCircleFragment.this.mCurrentPage == 1 && SearchCircleFragment.this.mList.size() == 0) {
                                    SearchCircleFragment.this.showNoDataEmpty();
                                }
                                SearchCircleFragment.this.mCurrentPage = SearchCircleFragment.this.mCurrentPage + 1;
                                SearchCircleFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                            } else if (SearchCircleFragment.this.mCurrentPage == 1) {
                                SearchCircleFragment.this.mList.clear();
                                SearchCircleFragment.this.showNoDataEmpty();
                            }
                        } else if (SearchCircleFragment.this.mList.size() > 0) {
                            ((BaseActivity) SearchCircleFragment.this.mActivity).showSecondTypeToast(2131231273);
                        } else {
                            SearchCircleFragment.this.showErrorView();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (SearchCircleFragment.this.mList.size() > 0) {
                            ((BaseActivity) SearchCircleFragment.this.mActivity).showSecondTypeToast(2131230831);
                        } else {
                            SearchCircleFragment.this.showErrorView();
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
                    ((CommonRecyclerviewBinding) SearchCircleFragment.this.mBind).recyclerview.forceToRefresh();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(AddCircleEvent event) {
        if (event != null && event.type == AddCircleEvent.ATTENTION_CANCEL_CIRCLE_TYPE) {
            int position = this.mList.getPosition(event.info.id);
            if (position > 0) {
                ((CircleInfo) this.mList.getCircleList().get(position)).isfans.set(event.info.isfans.get());
                return;
            }
            this.mList.add(0, event.info);
            this.mLRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    public void loginSuccess() {
        super.loginSuccess();
        if (isAdded()) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    public void checkSearchKey(String searchKey, int cityID) {
        if (isAdded() && !TextUtils.isEmpty(searchKey) && !searchKey.equals(this.mSearchKey)) {
            hideEmpty();
            this.mSearchKey = searchKey;
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setIsScrollDown(true);
        }
    }
}
