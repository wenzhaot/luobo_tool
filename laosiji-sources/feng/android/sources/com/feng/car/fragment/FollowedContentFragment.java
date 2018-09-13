package com.feng.car.fragment;

import android.content.Intent;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.FindFollowActivity;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.RefreshEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.MessageCountManager;
import com.feng.car.view.SpacesItemDecoration;
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

public class FollowedContentFragment extends BaseFragment<CommonRecyclerviewBinding> {
    private CommonPostAdapter mAdapter;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();
    private int mTotalPage;

    public void backToTop() {
        if (((CommonRecyclerviewBinding) this.mBind).recyclerview != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.scrollToPosition(0);
        }
    }

    protected void initView() {
        this.mAdapter = new CommonPostAdapter(this.mActivity, this.mList, 0, true, getLogGatherInfo());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.addItemDecoration(new SpacesItemDecoration(this.mActivity));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) FollowedContentFragment.this.mBind).recyclerview, State.Normal);
                FollowedContentFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                FollowedContentFragment.this.mCurrentPage = 1;
                FollowedContentFragment.this.getData(true);
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) FollowedContentFragment.this.mBind).recyclerview) != State.Loading) {
                    if (FollowedContentFragment.this.mCurrentPage <= FollowedContentFragment.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(FollowedContentFragment.this.mActivity, ((CommonRecyclerviewBinding) FollowedContentFragment.this.mBind).recyclerview, 20, State.Loading, null);
                        FollowedContentFragment.this.getData(false);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(FollowedContentFragment.this.mActivity, ((CommonRecyclerviewBinding) FollowedContentFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
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

    private void getData(final boolean needClear) {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.PAGE, String.valueOf(this.mCurrentPage));
        FengApplication.getInstance().httpRequest(HttpConstant.LISTBYFOLLOW, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (FollowedContentFragment.this.mList.size() <= 0) {
                    FollowedContentFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) FollowedContentFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                ((CommonRecyclerviewBinding) FollowedContentFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) FollowedContentFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (FollowedContentFragment.this.mList.size() <= 0) {
                    FollowedContentFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) FollowedContentFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        FengApplication.getInstance().getFollowState().clear();
                        JSONObject jsonThread = jsonResult.getJSONObject("body").getJSONObject("sns");
                        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SnsInfo.class, jsonThread);
                        FollowedContentFragment.this.mTotalPage = baseListModel.pagecount;
                        List<SnsInfo> list = baseListModel.list;
                        FollowedContentFragment.this.hideEmptyView();
                        if (list != null && list.size() > 0) {
                            if (needClear) {
                                FollowedContentFragment.this.mList.clear();
                                MessageCountManager.getInstance().clearSnsfollow();
                                EventBus.getDefault().post(new MessageCountRefreshEvent());
                            }
                            int oldSize = FollowedContentFragment.this.mList.size();
                            FollowedContentFragment.this.mList.addAll(list);
                            if (FollowedContentFragment.this.mCurrentPage == 1 && FollowedContentFragment.this.mList.size() <= 0) {
                                FollowedContentFragment.this.showNoDataEmpty();
                            }
                            FollowedContentFragment.this.mCurrentPage = FollowedContentFragment.this.mCurrentPage + 1;
                            if (needClear) {
                                FollowedContentFragment.this.mAdapter.notifyDataSetChanged();
                            } else {
                                FollowedContentFragment.this.mAdapter.notifyItemRangeInserted(oldSize, FollowedContentFragment.this.mList.size() - oldSize);
                            }
                        } else if (FollowedContentFragment.this.mCurrentPage == 1) {
                            FollowedContentFragment.this.showNoDataEmpty();
                        }
                    } else if (FollowedContentFragment.this.mList.size() <= 0) {
                        FollowedContentFragment.this.showNetErrorView();
                    } else {
                        FengApplication.getInstance().checkCode(HttpConstant.LISTBYFOLLOW, code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (FollowedContentFragment.this.mList.size() <= 0) {
                        FollowedContentFragment.this.showNetErrorView();
                    } else {
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.LISTBYFOLLOW, content, e);
                    }
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (event != null && event.type != 2) {
            this.mAdapter.refreshUserStatus(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        if (event != null && event.type == 2) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event != null) {
            this.mAdapter.refreshSnsInfo(event);
        }
    }

    public void loginSuccess() {
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
    }

    public void loginOut() {
        this.mList.clear();
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
    }

    protected int setLayoutId() {
        return 2130903204;
    }

    public void showNetErrorView() {
        showEmptyView(2131231188, 2130838112, 2131231470, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                FollowedContentFragment.this.getData(true);
            }
        });
    }

    public void showNoDataEmpty() {
        showEmptyView(2131231108, 2130838369, 2131231046, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                FollowedContentFragment.this.startActivity(new Intent(FollowedContentFragment.this.mActivity, FindFollowActivity.class));
            }
        });
    }

    private void showEmptyView(int textID, int drawableID, int btnTextID, OnSingleClickListener listener) {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(drawableID);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(textID);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setButtonListener(btnTextID, listener);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void hideEmptyView() {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(8);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setIsScrollDown(true);
        }
    }

    public void refreshData() {
        if (isAdded() && this.mBind != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }
}
