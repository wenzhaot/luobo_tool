package com.feng.car.fragment;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.HttpConstant;
import com.feng.car.view.SpacesItemDecoration;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView.LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchContentFragment extends BaseFragment<CommonRecyclerviewBinding> {
    private CommonPostAdapter mAdater;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();
    public int mPageTotal = 0;
    private String mSearchKey = "";

    protected int setLayoutId() {
        return 2130903204;
    }

    public static SearchContentFragment newInstance(String searchKey) {
        Bundle args = new Bundle();
        args.putString(HttpConstant.SEARCH_KEY, searchKey);
        SearchContentFragment allSearchFragment = new SearchContentFragment();
        allSearchFragment.setArguments(args);
        return allSearchFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSearchKey = getArguments().getString(FengConstant.FENGTYPE, "");
    }

    protected void initView() {
        this.mAdater = new CommonPostAdapter(getActivity(), this.mList, 0, true, getLogGatherInfo());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdater);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.addItemDecoration(new SpacesItemDecoration(this.mActivity));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchContentFragment.this.mBind).recyclerview, State.Normal);
                SearchContentFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                SearchContentFragment.this.mCurrentPage = 1;
                if (TextUtils.isEmpty(SearchContentFragment.this.mSearchKey)) {
                    ((CommonRecyclerviewBinding) SearchContentFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchContentFragment.this.mBind).recyclerview, State.Normal);
                    return;
                }
                SearchContentFragment.this.getSnsData();
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
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) SearchContentFragment.this.mBind).recyclerview) != State.Loading) {
                    if (SearchContentFragment.this.mCurrentPage <= SearchContentFragment.this.mPageTotal) {
                        RecyclerViewStateUtils.setFooterViewState(SearchContentFragment.this.getActivity(), ((CommonRecyclerviewBinding) SearchContentFragment.this.mBind).recyclerview, 20, State.Loading, null);
                        SearchContentFragment.this.getSnsData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(SearchContentFragment.this.getActivity(), ((CommonRecyclerviewBinding) SearchContentFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
                }
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshing(true);
    }

    private void getSnsData() {
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
            map.put("type", String.valueOf(7));
            FengApplication.getInstance().httpRequest(HttpConstant.SEARCH_YWF_INDEX_API, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    ((BaseActivity) SearchContentFragment.this.mActivity).showSecondTypeToast(2131231273);
                }

                public void onStart() {
                }

                public void onFinish() {
                    ((CommonRecyclerviewBinding) SearchContentFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchContentFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    ((BaseActivity) SearchContentFragment.this.mActivity).showSecondTypeToast(2131231273);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            SearchContentFragment.this.parserSns(jsonResult.getJSONObject("body").getJSONObject("search").getJSONObject("sns"));
                            return;
                        }
                        SearchContentFragment.this.showErrorView();
                        FengApplication.getInstance().checkCode(HttpConstant.SEARCH_YWF_INDEX_API, code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        SearchContentFragment.this.showErrorView();
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.SEARCH_YWF_INDEX_API, content, e);
                    }
                }
            });
        }
    }

    private void parserSns(JSONObject jsonObject) {
        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
        baseListModel.parser(SnsInfo.class, jsonObject);
        List<SnsInfo> list = baseListModel.list;
        if (this.mCurrentPage == 1) {
            this.mList.clear();
        }
        int oldSize = this.mList.size();
        this.mPageTotal = baseListModel.pagecount;
        if (list.size() > 0) {
            this.mList.addAll(list);
        }
        if (this.mCurrentPage == 1 && this.mList.size() == 0) {
            showNoDataEmpty();
        } else {
            hideEmpty();
        }
        this.mCurrentPage++;
        if (this.mCurrentPage == 2) {
            this.mAdater.notifyDataSetChanged();
        } else {
            this.mAdater.notifyItemRangeInserted(oldSize, this.mList.size() - oldSize);
        }
    }

    public void showErrorView() {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(2130838112);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231188);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ((CommonRecyclerviewBinding) SearchContentFragment.this.mBind).recyclerview.forceToRefresh();
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
}
