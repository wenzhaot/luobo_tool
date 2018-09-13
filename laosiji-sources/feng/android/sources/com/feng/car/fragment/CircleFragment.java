package com.feng.car.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.CircleFindActivity;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.adapter.MyCircleAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.databinding.MyFollowedCircleHeaderLayoutBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.entity.circle.CircleInfoList;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.AddCircleEvent;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.MessageCountManager;
import com.feng.car.view.CircleRecommendView.FollowCircleListener;
import com.feng.car.view.SpacesItemDecoration;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView.LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class CircleFragment extends BaseFragment<CommonRecyclerviewBinding> implements FollowCircleListener {
    private MyCircleAdapter mCircleAdapter;
    private CircleInfoList mCircleInfoList = new CircleInfoList();
    private Map<String, Object> mCircleMap = new HashMap();
    private CommonPostAdapter mCommonPostAdapter;
    private int mCurrentPage = 1;
    private MyFollowedCircleHeaderLayoutBinding mHeaderBinding;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mSnsInfoList = new SnsInfoList();
    private Map<String, Object> mSnsMap = new HashMap();
    private int mTotalPage = 1;

    public void backToTop() {
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.scrollToPosition(0);
    }

    protected int setLayoutId() {
        return 2130903204;
    }

    protected void initView() {
        this.mHeaderBinding = MyFollowedCircleHeaderLayoutBinding.inflate(LayoutInflater.from(this.mActivity));
        this.mCircleAdapter = new MyCircleAdapter(this.mActivity, this.mCircleInfoList.getCircleList());
        this.mCircleAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    CircleFragment.this.startActivity(new Intent(CircleFragment.this.mActivity, CircleFindActivity.class));
                    return;
                }
                final CircleInfo info = CircleFragment.this.mCircleInfoList.get(position);
                info.intentToCircleFinalPage(CircleFragment.this.mActivity);
                if (info.redpoint.get() == 1) {
                    info.redpoint.set(0);
                }
                CircleFragment.this.mHeaderBinding.recyclerView.postDelayed(new Runnable() {
                    public void run() {
                        CircleFragment.this.mCircleInfoList.remove(info);
                        CircleFragment.this.mCircleInfoList.add(info);
                        CircleFragment.this.mCircleAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mActivity);
        linearLayoutManager.setOrientation(0);
        this.mHeaderBinding.recyclerView.setLayoutManager(linearLayoutManager);
        this.mHeaderBinding.recyclerView.setAdapter(this.mCircleAdapter);
        this.mCommonPostAdapter = new CommonPostAdapter(this.mActivity, this.mSnsInfoList, 4, true, getLogGatherInfo());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mCommonPostAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.addItemDecoration(new SpacesItemDecoration(this.mActivity));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(false);
        this.mHeaderBinding.getRoot().setLayoutParams(new LayoutParams(-1, -2));
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeaderBinding.getRoot());
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview, State.Normal);
                CircleFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                CircleFragment.this.mCurrentPage = 1;
                CircleFragment.this.getData();
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (CircleFragment.this.mCurrentPage > CircleFragment.this.mTotalPage) {
                    RecyclerViewStateUtils.setFooterViewState(CircleFragment.this.mActivity, ((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
                } else if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview) != State.Loading) {
                    RecyclerViewStateUtils.setFooterViewState(CircleFragment.this.mActivity, ((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview, 20, State.Loading, null);
                    CircleFragment.this.getSnsData();
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
        this.mCircleMap.put("type", String.valueOf(1));
        FengApplication.getInstance().httpRequest(HttpConstant.COMMUNITY_LIST, this.mCircleMap, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (CircleFragment.this.mSnsInfoList.size() > 0 || CircleFragment.this.mCircleInfoList.size() > 0) {
                    ((BaseActivity) CircleFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    CircleFragment.this.showNetErrorView();
                }
                ((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (CircleFragment.this.mSnsInfoList.size() > 0 || CircleFragment.this.mCircleInfoList.size() > 0) {
                    ((BaseActivity) CircleFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    CircleFragment.this.showNetErrorView();
                }
                ((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject object = new JSONObject(content);
                    int code = object.getInt("code");
                    if (code == 1) {
                        JSONObject jsonTag = object.getJSONObject("body").getJSONObject(HttpConstant.COMMUNITY);
                        BaseListModel<CircleInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CircleInfo.class, jsonTag);
                        CircleFragment.this.hideEmpty();
                        if (baseListModel.list.size() == 0) {
                            CircleFragment.this.getRecommendCircleData();
                            return;
                        }
                        ((CommonRecyclerviewBinding) CircleFragment.this.mBind).circleRecommendView.setVisibility(8);
                        CircleFragment.this.circleDataSort(baseListModel.list);
                        CircleFragment.this.mCircleAdapter.notifyDataSetChanged();
                        MessageCountManager.getInstance().clearCommunity();
                        EventBus.getDefault().post(new MessageCountRefreshEvent());
                        CircleFragment.this.getSnsData();
                    } else if (CircleFragment.this.mCircleInfoList.size() > 0) {
                        FengApplication.getInstance().checkCode(HttpConstant.COMMUNITY_LIST, code);
                    } else {
                        CircleFragment.this.showNetErrorView();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void circleDataSort(List<CircleInfo> circleInfos) {
        this.mCircleInfoList.clear();
        CircleInfo circleInfo = new CircleInfo();
        circleInfo.id = Integer.MAX_VALUE;
        this.mCircleInfoList.add(circleInfo);
        for (CircleInfo info : circleInfos) {
            if (info.redpoint.get() == 1) {
                this.mCircleInfoList.add(info);
            }
        }
        this.mCircleInfoList.addAll(circleInfos);
    }

    private void getSnsData() {
        this.mSnsMap.put(HttpConstant.PAGE, String.valueOf(this.mCurrentPage));
        FengApplication.getInstance().httpRequest(HttpConstant.COMMUNITY_GETSNSLIST, this.mSnsMap, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (CircleFragment.this.mSnsInfoList.size() <= 0) {
                    CircleFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) CircleFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
                ((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
                ((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (CircleFragment.this.mSnsInfoList.size() <= 0) {
                    CircleFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) CircleFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
                ((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject object = new JSONObject(content);
                    int code = object.getInt("code");
                    if (code == 1) {
                        JSONObject jsonCommunity = object.getJSONObject("body").getJSONObject("sns");
                        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SnsInfo.class, jsonCommunity);
                        int oldSize = CircleFragment.this.mSnsInfoList.size();
                        if (CircleFragment.this.mCurrentPage == 1) {
                            CircleFragment.this.mSnsInfoList.clear();
                            CircleFragment.this.mSnsInfoList.addAll(baseListModel.list);
                            if (CircleFragment.this.mSnsInfoList.size() == 0) {
                                CircleFragment.this.mHeaderBinding.emptyView.setVisibility(0);
                                ((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview.setNoMore(true);
                            } else {
                                CircleFragment.this.mHeaderBinding.emptyView.setVisibility(8);
                            }
                        } else {
                            CircleFragment.this.mSnsInfoList.addAll(baseListModel.list);
                        }
                        CircleFragment.this.mTotalPage = baseListModel.pagecount;
                        CircleFragment.this.mCurrentPage = CircleFragment.this.mCurrentPage + 1;
                        if (CircleFragment.this.mCurrentPage == 2) {
                            CircleFragment.this.mCommonPostAdapter.notifyDataSetChanged();
                        } else {
                            CircleFragment.this.mCommonPostAdapter.notifyItemRangeInserted(oldSize, CircleFragment.this.mSnsInfoList.size() - oldSize);
                        }
                    } else if (CircleFragment.this.mSnsInfoList.size() <= 0) {
                        CircleFragment.this.showNetErrorView();
                    } else {
                        FengApplication.getInstance().checkCode(HttpConstant.COMMUNITY_GETSNSLIST, code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (CircleFragment.this.mSnsInfoList.size() <= 0) {
                        CircleFragment.this.showNetErrorView();
                    } else {
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.COMMUNITY_GETSNSLIST, content, e);
                    }
                }
            }
        });
    }

    protected void onFragmentFirstVisible() {
        if (this.mBind != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(true);
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(AddCircleEvent event) {
        if (event != null && event.type == AddCircleEvent.ATTENTION_CANCEL_CIRCLE_TYPE) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (event != null) {
            this.mCommonPostAdapter.refreshUserStatus(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageCountRefreshEvent event) {
        if (MessageCountManager.getInstance().getCommunitylist().size() > 0 && this.mCircleInfoList.size() > 0 && this.mCircleAdapter != null) {
            int size = this.mCircleInfoList.getCircleList().size();
            List<CircleInfo> list = new ArrayList();
            int i = 0;
            while (i < size) {
                CircleInfo info = (CircleInfo) this.mCircleInfoList.getCircleList().get(i);
                if (MessageCountManager.getInstance().getCommunitylist().contains(Integer.valueOf(info.id))) {
                    info.redpoint.set(1);
                    list.add(info);
                    this.mCircleInfoList.getCircleList().remove(info);
                    i--;
                    size--;
                } else {
                    info.redpoint.set(0);
                }
                i++;
            }
            if (list.size() > 0) {
                this.mCircleInfoList.getCircleList().addAll(1, list);
                this.mCircleInfoList.resetDataIndex();
                this.mCircleAdapter.notifyItemRangeChanged(0, this.mSnsInfoList.size());
                ((LinearLayoutManager) this.mHeaderBinding.recyclerView.getLayoutManager()).scrollToPosition(0);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event != null) {
            this.mCommonPostAdapter.refreshSnsInfo(event);
        }
    }

    public void loginSuccess() {
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
    }

    public void loginOut() {
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
    }

    private void getRecommendCircleData() {
        Map<String, Object> map = new HashMap();
        map.put("type", String.valueOf(2));
        FengApplication.getInstance().httpRequest(HttpConstant.COMMUNITY_LIST, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                CircleFragment.this.showNetErrorView();
                ((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                CircleFragment.this.showNetErrorView();
                ((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject object = new JSONObject(content);
                    int code = object.getInt("code");
                    if (code == 1) {
                        MessageCountManager.getInstance().clearCommunity();
                        EventBus.getDefault().post(new MessageCountRefreshEvent());
                        JSONObject jsonTag = object.getJSONObject("body").getJSONObject(HttpConstant.COMMUNITY);
                        BaseListModel<CircleInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CircleInfo.class, jsonTag);
                        if (baseListModel.list.size() > 0) {
                            ((CommonRecyclerviewBinding) CircleFragment.this.mBind).circleRecommendView.setVisibility(0);
                            ((CommonRecyclerviewBinding) CircleFragment.this.mBind).circleRecommendView.setFollowCircleListener(CircleFragment.this);
                            ((CommonRecyclerviewBinding) CircleFragment.this.mBind).circleRecommendView.setCircleList(baseListModel.list);
                            return;
                        }
                        return;
                    }
                    FengApplication.getInstance().checkCode(HttpConstant.COMMUNITY_LIST, code);
                    CircleFragment.this.showNetErrorView();
                    ((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview, State.Normal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showNetErrorView() {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(2130838112);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231188);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ((CommonRecyclerviewBinding) CircleFragment.this.mBind).recyclerview.forceToRefresh();
                }
            });
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void hideEmpty() {
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(8);
    }

    public void onFollowedCircle() {
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
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
