package com.feng.car.fragment;

import android.content.Intent;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.AllProgramActivity;
import com.feng.car.activity.BaseActivity;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.car.event.ProgramFollowEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
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
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class MyProgrameFragment extends BaseFragment<CommonRecyclerviewBinding> {
    private CommonPostAdapter mAdater;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();
    private int mPage = 1;
    private int mTotalPage = 0;

    public void backToTop() {
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.scrollToPosition(0);
    }

    protected int setLayoutId() {
        return 2130903204;
    }

    protected void initView() {
        this.mAdater = new CommonPostAdapter(this.mActivity, this.mList, 5, true, getLogGatherInfo());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdater);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.addItemDecoration(new SpacesItemDecoration(this.mActivity));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(false);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) MyProgrameFragment.this.mBind).recyclerview, State.Normal);
                MyProgrameFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                MyProgrameFragment.this.mPage = 1;
                MyProgrameFragment.this.getData();
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) MyProgrameFragment.this.mBind).recyclerview) != State.Loading) {
                    if (MyProgrameFragment.this.mPage <= MyProgrameFragment.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(MyProgrameFragment.this.mActivity, ((CommonRecyclerviewBinding) MyProgrameFragment.this.mBind).recyclerview, 20, State.Loading, null);
                        MyProgrameFragment.this.getData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(MyProgrameFragment.this.mActivity, ((CommonRecyclerviewBinding) MyProgrameFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
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

    protected void onFragmentFirstVisible() {
        if (this.mBind != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(true);
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.PAGE, String.valueOf(this.mPage));
        FengApplication.getInstance().httpRequest(HttpConstant.PROGRAM_HOTSHOWLIST, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (MyProgrameFragment.this.mList.size() <= 0) {
                    MyProgrameFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) MyProgrameFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
                ((CommonRecyclerviewBinding) MyProgrameFragment.this.mBind).recyclerview.refreshComplete();
            }

            public void onStart() {
            }

            public void onFinish() {
                ((CommonRecyclerviewBinding) MyProgrameFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) MyProgrameFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (MyProgrameFragment.this.mList.size() <= 0) {
                    MyProgrameFragment.this.showNetErrorView();
                } else {
                    ((BaseActivity) MyProgrameFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        JSONObject jsonBody = jsonObject.getJSONObject("body").getJSONObject(HttpConstant.HOTSHOW);
                        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SnsInfo.class, jsonBody);
                        MessageCountManager.getInstance().clearNewHotShow();
                        EventBus.getDefault().post(new MessageCountRefreshEvent());
                        MyProgrameFragment.this.mTotalPage = baseListModel.pagecount;
                        MyProgrameFragment.this.hideEmptyView();
                        if (MyProgrameFragment.this.mPage == 1) {
                            MyProgrameFragment.this.mList.clear();
                        }
                        int oldSize = MyProgrameFragment.this.mList.size();
                        MyProgrameFragment.this.mList.addAll(baseListModel.list);
                        MyProgrameFragment.this.mPage = MyProgrameFragment.this.mPage + 1;
                        if (MyProgrameFragment.this.mList.size() <= 0) {
                            MyProgrameFragment.this.showEmptyView(2130837856, new OnSingleClickListener() {
                                public void onSingleClick(View v) {
                                    MyProgrameFragment.this.startActivity(new Intent(MyProgrameFragment.this.mActivity, AllProgramActivity.class));
                                }
                            });
                        }
                        if (MyProgrameFragment.this.mPage == 2) {
                            MyProgrameFragment.this.mAdater.notifyDataSetChanged();
                        } else {
                            MyProgrameFragment.this.mAdater.notifyItemRangeInserted(oldSize, MyProgrameFragment.this.mList.size() - oldSize);
                        }
                    } else if (MyProgrameFragment.this.mList.size() > 0) {
                        FengApplication.getInstance().checkCode(HttpConstant.PROGRAM_HOTSHOWLIST, code);
                    } else {
                        MyProgrameFragment.this.showNetErrorView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (MyProgrameFragment.this.mList.size() <= 0) {
                        MyProgrameFragment.this.showNetErrorView();
                    }
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.PROGRAM_HOTSHOWLIST, content, e);
                }
            }
        });
    }

    public void showNetErrorView() {
        showEmptyView(2131231188, 2130838112, 2131231470, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                MyProgrameFragment.this.getData();
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

    public void showEmptyView(int drawableID, OnSingleClickListener listener) {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.hideEmptyText();
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(drawableID);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setButtonListener(listener);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ProgramFollowEvent event) {
        if (event != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    public void loginSuccess() {
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
    }

    public void loginOut() {
        this.mList.clear();
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
    }

    public void refreshData() {
        if (isAdded() && this.mBind != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }
}
