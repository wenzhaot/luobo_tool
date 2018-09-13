package com.feng.car.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.adapter.FansAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.entity.user.UserInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.UserInfoRefreshEvent;
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
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchUserFragment extends BaseFragment<CommonRecyclerviewBinding> {
    private FansAdapter mAdater;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private UserInfoList mList = new UserInfoList();
    public int mPageTotal = 0;
    private String mSearchKey = "";

    protected int setLayoutId() {
        return 2130903204;
    }

    public static SearchUserFragment newInstance(String searchKey) {
        Bundle args = new Bundle();
        args.putString(HttpConstant.SEARCH_KEY, searchKey);
        SearchUserFragment allSearchFragment = new SearchUserFragment();
        allSearchFragment.setArguments(args);
        return allSearchFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSearchKey = getArguments().getString(FengConstant.FENGTYPE, "");
    }

    protected void initView() {
        this.mAdater = new FansAdapter(getActivity(), this.mList);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdater);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchUserFragment.this.mBind).recyclerview, State.Normal);
                SearchUserFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                SearchUserFragment.this.mCurrentPage = 1;
                if (TextUtils.isEmpty(SearchUserFragment.this.mSearchKey)) {
                    ((CommonRecyclerviewBinding) SearchUserFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchUserFragment.this.mBind).recyclerview, State.Normal);
                    return;
                }
                SearchUserFragment.this.getData();
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) SearchUserFragment.this.mBind).recyclerview) != State.Loading) {
                    if (SearchUserFragment.this.mCurrentPage <= SearchUserFragment.this.mPageTotal) {
                        RecyclerViewStateUtils.setFooterViewState(SearchUserFragment.this.getActivity(), ((CommonRecyclerviewBinding) SearchUserFragment.this.mBind).recyclerview, 20, State.Loading, null);
                        SearchUserFragment.this.getData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(SearchUserFragment.this.getActivity(), ((CommonRecyclerviewBinding) SearchUserFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
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
                SearchUserFragment.this.mList.get(position).intentToPersonalHome(SearchUserFragment.this.getActivity());
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
            map.put("type", String.valueOf(6));
            FengApplication.getInstance().httpRequest(HttpConstant.SEARCH_YWF_INDEX_API, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (SearchUserFragment.this.mList.size() > 0) {
                        ((BaseActivity) SearchUserFragment.this.mActivity).showSecondTypeToast(2131231273);
                    } else {
                        SearchUserFragment.this.showErrorView();
                    }
                }

                public void onStart() {
                }

                public void onFinish() {
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchUserFragment.this.mBind).recyclerview, State.Normal);
                    ((CommonRecyclerviewBinding) SearchUserFragment.this.mBind).recyclerview.refreshComplete();
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    if (SearchUserFragment.this.mList.size() > 0) {
                        ((BaseActivity) SearchUserFragment.this.mActivity).showSecondTypeToast(2131231273);
                    } else {
                        SearchUserFragment.this.showErrorView();
                    }
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            JSONObject jsonUsers = jsonResult.getJSONObject("body").getJSONObject("search").getJSONObject(HttpConstant.USER);
                            BaseListModel<UserInfo> mPageInfo = new BaseListModel();
                            mPageInfo.parser(UserInfo.class, jsonUsers);
                            SearchUserFragment.this.mPageTotal = mPageInfo.pagecount;
                            List<UserInfo> list = mPageInfo.list;
                            SearchUserFragment.this.hideEmpty();
                            if (list.size() > 0) {
                                if (SearchUserFragment.this.mCurrentPage == 1) {
                                    SearchUserFragment.this.mList.clear();
                                }
                                SearchUserFragment.this.mList.addAll(list);
                                SearchUserFragment.this.mCurrentPage = SearchUserFragment.this.mCurrentPage + 1;
                            } else if (SearchUserFragment.this.mCurrentPage == 1) {
                                SearchUserFragment.this.showNoDataEmpty();
                            }
                            SearchUserFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                        } else if (SearchUserFragment.this.mList.size() > 0) {
                            FengApplication.getInstance().checkCode(HttpConstant.SEARCH_YWF_INDEX_API, code);
                        } else {
                            SearchUserFragment.this.showErrorView();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (SearchUserFragment.this.mList.size() > 0) {
                            ((BaseActivity) SearchUserFragment.this.mActivity).showSecondTypeToast(2131231190);
                        } else {
                            SearchUserFragment.this.showErrorView();
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
                    ((CommonRecyclerviewBinding) SearchUserFragment.this.mBind).recyclerview.forceToRefresh();
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
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (this.mAdater != null) {
            this.mAdater.refreshUserFollowState(event);
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
