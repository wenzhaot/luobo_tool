package com.feng.car.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.adapter.VoiceChooseCarAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.HttpConstant;
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
import org.json.JSONObject;

public class SearchVoiceFragment extends BaseFragment<CommonRecyclerviewBinding> {
    private VoiceChooseCarAdapter mAdapter;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();
    public int mPageTotal = 0;
    private String mSearchKey = "";

    protected int setLayoutId() {
        return 2130903204;
    }

    public static SearchVoiceFragment newInstance(String searchKey) {
        Bundle args = new Bundle();
        args.putString(HttpConstant.SEARCH_KEY, searchKey);
        SearchVoiceFragment allSearchFragment = new SearchVoiceFragment();
        allSearchFragment.setArguments(args);
        return allSearchFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSearchKey = getArguments().getString(FengConstant.FENGTYPE, "");
    }

    protected void initView() {
        this.mAdapter = new VoiceChooseCarAdapter(getActivity(), this.mList.getSnsList());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchVoiceFragment.this.mBind).recyclerview, State.Normal);
                SearchVoiceFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                SearchVoiceFragment.this.mCurrentPage = 1;
                if (TextUtils.isEmpty(SearchVoiceFragment.this.mSearchKey)) {
                    ((CommonRecyclerviewBinding) SearchVoiceFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchVoiceFragment.this.mBind).recyclerview, State.Normal);
                    return;
                }
                SearchVoiceFragment.this.getData();
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) SearchVoiceFragment.this.mBind).recyclerview) != State.Loading) {
                    if (SearchVoiceFragment.this.mCurrentPage <= SearchVoiceFragment.this.mPageTotal) {
                        RecyclerViewStateUtils.setFooterViewState(SearchVoiceFragment.this.getActivity(), ((CommonRecyclerviewBinding) SearchVoiceFragment.this.mBind).recyclerview, 20, State.Loading, null);
                        SearchVoiceFragment.this.getData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(SearchVoiceFragment.this.getActivity(), ((CommonRecyclerviewBinding) SearchVoiceFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
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
            map.put("type", String.valueOf(3));
            FengApplication.getInstance().httpRequest(HttpConstant.SEARCH_YWF_INDEX_API, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (SearchVoiceFragment.this.mList.size() <= 0) {
                        SearchVoiceFragment.this.showErrorView();
                    } else {
                        ((BaseActivity) SearchVoiceFragment.this.mActivity).showSecondTypeToast(2131231273);
                    }
                    ((CommonRecyclerviewBinding) SearchVoiceFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchVoiceFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onStart() {
                }

                public void onFinish() {
                    ((CommonRecyclerviewBinding) SearchVoiceFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchVoiceFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    if (SearchVoiceFragment.this.mList.size() <= 0) {
                        SearchVoiceFragment.this.showErrorView();
                    } else {
                        ((BaseActivity) SearchVoiceFragment.this.mActivity).showSecondTypeToast(2131231273);
                    }
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonObject = new JSONObject(content);
                        int code = jsonObject.getInt("code");
                        if (code == 1) {
                            JSONObject jsonBody = jsonObject.getJSONObject("body").getJSONObject("search").getJSONObject(HttpConstant.AUTODISCUSS);
                            BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                            baseListModel.parser(SnsInfo.class, jsonBody);
                            SearchVoiceFragment.this.mPageTotal = baseListModel.pagecount;
                            List<SnsInfo> list = baseListModel.list;
                            SearchVoiceFragment.this.hideEmpty();
                            if (list != null && list.size() > 0) {
                                if (SearchVoiceFragment.this.mCurrentPage == 1) {
                                    SearchVoiceFragment.this.mList.clear();
                                }
                                SearchVoiceFragment.this.mList.addAll(list);
                                SearchVoiceFragment.this.mCurrentPage = SearchVoiceFragment.this.mCurrentPage + 1;
                                SearchVoiceFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                            } else if (SearchVoiceFragment.this.mCurrentPage == 1) {
                                SearchVoiceFragment.this.showNoDataEmpty();
                            }
                        } else if (SearchVoiceFragment.this.mList.size() < 0) {
                            SearchVoiceFragment.this.showErrorView();
                        } else {
                            FengApplication.getInstance().checkCode(HttpConstant.SEARCH_YWF_INDEX_API, code);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (SearchVoiceFragment.this.mList.size() <= 0) {
                            SearchVoiceFragment.this.showErrorView();
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
                    ((CommonRecyclerviewBinding) SearchVoiceFragment.this.mBind).recyclerview.forceToRefresh();
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
            this.mSearchKey = searchKey;
            hideEmpty();
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
