package com.feng.car.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.adapter.AtContentAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SendCommentStartSlideEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.LogConstans;
import com.feng.car.utils.LogGatherReadUtil;
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
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class AtMicroFragment extends BaseFragment<CommonRecyclerviewBinding> {
    private AtContentAdapter mAtContentAdapter;
    private boolean mIsVisibleToUser;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int mPage = 1;
    private SnsInfoList mSnsInfoList;
    private int mTotalPage = 0;
    private Map<String, Object> map = new HashMap();

    public void backToTop() {
        if (this.mBind != null && ((CommonRecyclerviewBinding) this.mBind).recyclerview != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.scrollToPosition(0);
        }
    }

    protected int setLayoutId() {
        return 2130903204;
    }

    protected void initView() {
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(true);
        this.mSnsInfoList = new SnsInfoList();
        this.mAtContentAdapter = new AtContentAdapter(this.mActivity, this.mSnsInfoList, getLogGatherInfo());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAtContentAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new LinearLayoutManager(this.mActivity));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) AtMicroFragment.this.mBind).recyclerview, State.Normal);
                AtMicroFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                AtMicroFragment.this.mPage = 1;
                AtMicroFragment.this.getData(true, false);
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) AtMicroFragment.this.mBind).recyclerview) != State.Loading) {
                    if (AtMicroFragment.this.mPage <= AtMicroFragment.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(AtMicroFragment.this.mActivity, ((CommonRecyclerviewBinding) AtMicroFragment.this.mBind).recyclerview, 20, State.Loading, null);
                        AtMicroFragment.this.getData(false, true);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(AtMicroFragment.this.mActivity, ((CommonRecyclerviewBinding) AtMicroFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
                }
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshing(true);
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
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mIsVisibleToUser = isVisibleToUser;
    }

    public void onResume() {
        super.onResume();
    }

    private void getData(final boolean needClear, boolean isLoadMoore) {
        if (this.mPage <= this.mTotalPage || !isLoadMoore) {
            this.map.put(HttpConstant.PAGE, String.valueOf(this.mPage));
            FengApplication.getInstance().httpRequest(HttpConstant.AT_ME_LIST, this.map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (AtMicroFragment.this.mSnsInfoList.size() > 0) {
                        ((BaseActivity) AtMicroFragment.this.mActivity).showSecondTypeToast(2131231273);
                    } else {
                        AtMicroFragment.this.showNetErrorView();
                    }
                    ((CommonRecyclerviewBinding) AtMicroFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) AtMicroFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onStart() {
                }

                public void onFinish() {
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) AtMicroFragment.this.mBind).recyclerview, State.Normal);
                    ((CommonRecyclerviewBinding) AtMicroFragment.this.mBind).recyclerview.refreshComplete();
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    ((BaseActivity) AtMicroFragment.this.mActivity).showSecondTypeToast(2131231273);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            JSONObject jsonSns = jsonResult.getJSONObject("body").getJSONObject("sns");
                            BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                            baseListModel.parser(SnsInfo.class, jsonSns);
                            AtMicroFragment.this.mTotalPage = baseListModel.pagecount;
                            List<SnsInfo> list = baseListModel.list;
                            AtMicroFragment.this.hideEmpty();
                            if (list.size() > 0) {
                                if (needClear) {
                                    AtMicroFragment.this.mSnsInfoList.clear();
                                }
                                AtMicroFragment.this.mSnsInfoList.addAll(list);
                                if (AtMicroFragment.this.mPage == 1 && AtMicroFragment.this.mSnsInfoList.size() <= 0) {
                                    AtMicroFragment.this.showNoDataEmpty();
                                }
                                AtMicroFragment.this.mPage = AtMicroFragment.this.mPage + 1;
                            } else if (AtMicroFragment.this.mPage == 1) {
                                AtMicroFragment.this.showNoDataEmpty();
                            }
                            RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) AtMicroFragment.this.mBind).recyclerview, State.Normal);
                            AtMicroFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                        } else if (AtMicroFragment.this.mSnsInfoList.size() > 0) {
                            FengApplication.getInstance().checkCode(HttpConstant.AT_ME_LIST, code);
                        } else {
                            AtMicroFragment.this.showNetErrorView();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (AtMicroFragment.this.mSnsInfoList.size() > 0) {
                            ((BaseActivity) AtMicroFragment.this.mActivity).showSecondTypeToast(2131230831);
                        } else {
                            AtMicroFragment.this.showNetErrorView();
                        }
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.AT_ME_LIST, content, e);
                    }
                }
            });
            return;
        }
        ((BaseActivity) this.mActivity).showThirdTypeToast(2131231189);
    }

    private void showNetErrorView() {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(2130838112);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231188);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AtMicroFragment.this.getData(true, false);
                }
            });
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void showNoDataEmpty() {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.hideEmptyImage();
            ((CommonRecyclerviewBinding) this.mBind).emptyView.hideEmptyButton();
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231226);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void hideEmpty() {
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(8);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (event != null) {
            this.mAtContentAdapter.refreshUserStatus(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event != null) {
            this.mAtContentAdapter.refreshSnsInfo(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setIsScrollDown(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final SendCommentStartSlideEvent event) {
        if (event != null && isAdded() && !TextUtils.isEmpty(event.key) && this.mAtContentAdapter != null && this.mAtContentAdapter.getSoleKey().equals(event.key)) {
            LinearLayoutManager manager = (LinearLayoutManager) ((CommonRecyclerviewBinding) this.mBind).recyclerview.getLayoutManager();
            int lastItemPosition = manager.findLastVisibleItemPosition();
            if (lastItemPosition >= manager.getItemCount() - 3) {
                int firstItemPosition = manager.findFirstVisibleItemPosition();
                int childSize = (lastItemPosition - firstItemPosition) + 1;
                if (lastItemPosition == firstItemPosition && lastItemPosition == 1 && manager.getChildCount() >= 2) {
                    childSize = 2;
                }
                View view = manager.getChildAt(childSize - 1);
                if (view != null) {
                    if (!LogGatherReadUtil.getInstance().getCurrentPage().equals(LogConstans.APP_SEND_COMMENT)) {
                        view.setPadding(0, 0, 0, 1920);
                    } else if (event.isFinish) {
                        view.setPadding(0, 0, 0, 0);
                    } else {
                        view.setPadding(0, 0, 0, 1920);
                    }
                }
            }
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.postDelayed(new Runnable() {
                public void run() {
                    ((CommonRecyclerviewBinding) AtMicroFragment.this.mBind).recyclerview.smoothScrollBy(0, event.dy);
                }
            }, 100);
        }
    }
}
