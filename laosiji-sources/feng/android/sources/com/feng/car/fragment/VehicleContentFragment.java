package com.feng.car.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.databinding.FragmentVehicleContentBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.CircleFragmentRefreshFinishEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SendDelArticleSuccess;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.event.VideoInFragmentChangeEvent;
import com.feng.car.utils.HttpConstant;
import com.feng.car.view.SpacesItemDecoration;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
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
import org.json.JSONObject;

public class VehicleContentFragment extends BaseFragment<FragmentVehicleContentBinding> {
    public static final String CIRCLE_ID_FLAG = "CIRCLE_ID";
    public static final String CIRCLE_SORT_TYPE = "circle_sort_type";
    public static final int SORT_POPULAR_TYPE = 1;
    public static final int SORT_PUBLISH_TYPE = 0;
    public static final int SORT_REPLY_TYPE = 2;
    public static final String SUBCLASS_CIRCLE_ID_FLAG = "SUBCLASS_CIRCLE_ID";
    public static final String SUBCLASS_CIRCLE_NAME_FLAG = "SUBCLASS_CIRCLE_NAME";
    public static final String SUBCLASS_TAB_INDEX = "subclass_tab_index";
    private CommonPostAdapter mAdater;
    private int mCircleID = -1;
    private int mContentSortType;
    private int mCurrentPage = 1;
    private boolean mIsFirst = true;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();
    private String mSubclassCircleFragmentName = "";
    private int mSubclassCircleId = -1;
    private int mTotalPage;

    public void backToTop() {
        if (this.mBind != null && ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle != null) {
            ((LinearLayoutManager) ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.getLayoutManager()).scrollToPosition(0);
        }
    }

    protected int setLayoutId() {
        return 2130903250;
    }

    protected void initView() {
        this.mAdater = new CommonPostAdapter(getActivity(), this.mList, 2, true, getLogGatherInfo());
        this.mAdater.setTopicId(this.mCircleID);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdater);
        ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.setAdapter(this.mLRecyclerViewAdapter);
        ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.addItemDecoration(new SpacesItemDecoration(this.mActivity));
        ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.setRefreshProgressStyle(2);
        ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.setIsOwner(true);
        ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((FragmentVehicleContentBinding) VehicleContentFragment.this.mBind).rcviewVehicle, State.Normal);
                VehicleContentFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                if (VehicleContentFragment.this.mIsFirst) {
                    ((FragmentVehicleContentBinding) VehicleContentFragment.this.mBind).rcviewVehicle.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((FragmentVehicleContentBinding) VehicleContentFragment.this.mBind).rcviewVehicle, State.Normal);
                    return;
                }
                VehicleContentFragment.this.mCurrentPage = 1;
                VehicleContentFragment.this.getData(true);
            }
        });
        ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((FragmentVehicleContentBinding) VehicleContentFragment.this.mBind).rcviewVehicle) != State.Loading) {
                    if (VehicleContentFragment.this.mCurrentPage <= VehicleContentFragment.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(VehicleContentFragment.this.getActivity(), ((FragmentVehicleContentBinding) VehicleContentFragment.this.mBind).rcviewVehicle, 20, State.Loading, null);
                        VehicleContentFragment.this.getData(false);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(VehicleContentFragment.this.getActivity(), ((FragmentVehicleContentBinding) VehicleContentFragment.this.mBind).rcviewVehicle, 20, VehicleContentFragment.this.mTotalPage == 0 ? State.Normal : State.TheEnd, null);
                }
            }
        });
        ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.setLScrollListener(new LScrollListener() {
            public void onScrollUp() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(true));
                ((FragmentVehicleContentBinding) VehicleContentFragment.this.mBind).rcviewVehicle.setIsOwner(true);
            }

            public void onScrollDown() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(false));
                ((FragmentVehicleContentBinding) VehicleContentFragment.this.mBind).rcviewVehicle.setIsOwner(false);
            }

            public void onScrolled(int distanceX, int distanceY) {
            }

            public void onScrollStateChanged(int state) {
            }
        });
        ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.setRefreshing(true);
    }

    public static VehicleContentFragment newInstance(int circleID, int subclassCircleId, String subclassCircleFragmentName, int index) {
        Bundle args = new Bundle();
        args.putInt(CIRCLE_ID_FLAG, circleID);
        args.putInt(SUBCLASS_CIRCLE_ID_FLAG, subclassCircleId);
        args.putString(SUBCLASS_CIRCLE_NAME_FLAG, subclassCircleFragmentName);
        args.putString(SUBCLASS_CIRCLE_NAME_FLAG, subclassCircleFragmentName);
        args.putInt(SUBCLASS_TAB_INDEX, index);
        VehicleContentFragment fragment = new VehicleContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mCircleID = getArguments().getInt(CIRCLE_ID_FLAG);
        this.mSubclassCircleId = getArguments().getInt(SUBCLASS_CIRCLE_ID_FLAG);
        this.mSubclassCircleFragmentName = getArguments().getString(SUBCLASS_CIRCLE_NAME_FLAG, "");
        this.mContentSortType = SharedUtil.getInt(this.mActivity, CIRCLE_SORT_TYPE, 2);
        if (getArguments().getInt(SUBCLASS_TAB_INDEX) == 0) {
            this.mIsFirst = false;
        }
    }

    public void loadData(boolean isAuto) {
        try {
            if (this.mAdater == null || !isAuto) {
                this.mCurrentPage = 1;
                ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.setNoMore(false);
                getData(true);
                return;
            }
            refreshAuto();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getData(final boolean needClear) {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.COMMUNITY_ID, String.valueOf(this.mCircleID));
        map.put(HttpConstant.COMMUNITY_SUB_ID, String.valueOf(this.mSubclassCircleId));
        map.put(HttpConstant.COMMUNITY_SORT_TYPE, String.valueOf(this.mContentSortType));
        map.put(HttpConstant.PAGE, String.valueOf(this.mCurrentPage));
        FengApplication.getInstance().httpRequest(HttpConstant.COMMUNITY_FINAL_SORT_LIST, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                VehicleContentFragment.this.mIsFirst = false;
                if (VehicleContentFragment.this.mList.size() > 0) {
                    ((BaseActivity) VehicleContentFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    VehicleContentFragment.this.showNetErrorView();
                }
                EventBus.getDefault().post(new CircleFragmentRefreshFinishEvent());
            }

            public void onStart() {
            }

            public void onFinish() {
                VehicleContentFragment.this.mIsFirst = false;
                ((FragmentVehicleContentBinding) VehicleContentFragment.this.mBind).rcviewVehicle.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((FragmentVehicleContentBinding) VehicleContentFragment.this.mBind).rcviewVehicle, State.Normal);
                EventBus.getDefault().post(new CircleFragmentRefreshFinishEvent());
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (VehicleContentFragment.this.mList.size() > 0) {
                    ((BaseActivity) VehicleContentFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    VehicleContentFragment.this.showNetErrorView();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        ((FragmentVehicleContentBinding) VehicleContentFragment.this.mBind).rcviewVehicle.setPullRefreshEnabled(false);
                        JSONObject jsonSns = jsonResult.getJSONObject("body").getJSONObject("sns");
                        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SnsInfo.class, jsonSns);
                        VehicleContentFragment.this.mTotalPage = baseListModel.pagecount;
                        List<SnsInfo> list = baseListModel.list;
                        VehicleContentFragment.this.hideEmptyView();
                        if (list != null && list.size() > 0) {
                            if (needClear) {
                                VehicleContentFragment.this.mList.clear();
                                ((FragmentVehicleContentBinding) VehicleContentFragment.this.mBind).rcviewVehicle.setBackgroundResource(2131558527);
                                ((FragmentVehicleContentBinding) VehicleContentFragment.this.mBind).rcviewVehicle.refreshComplete();
                            }
                            int oldSize = VehicleContentFragment.this.mList.size();
                            VehicleContentFragment.this.mList.addAll(list);
                            VehicleContentFragment.this.mCurrentPage = VehicleContentFragment.this.mCurrentPage + 1;
                            if (needClear) {
                                VehicleContentFragment.this.mAdater.notifyDataSetChanged();
                                return;
                            } else {
                                VehicleContentFragment.this.mAdater.notifyItemRangeInserted(oldSize, VehicleContentFragment.this.mList.size() - oldSize);
                                return;
                            }
                        } else if (needClear) {
                            VehicleContentFragment.this.showEmptyView();
                            return;
                        } else {
                            return;
                        }
                    }
                    if (VehicleContentFragment.this.mList.size() > 0) {
                        ((BaseActivity) VehicleContentFragment.this.mActivity).showSecondTypeToast(2131231273);
                    } else {
                        VehicleContentFragment.this.showNetErrorView();
                    }
                    FengApplication.getInstance().checkCode(HttpConstant.COMMUNITY_FINAL_SORT_LIST, code);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (VehicleContentFragment.this.mList.size() > 0) {
                        ((BaseActivity) VehicleContentFragment.this.mActivity).showSecondTypeToast(2131230831);
                    } else {
                        VehicleContentFragment.this.showNetErrorView();
                    }
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.COMMUNITY_FINAL_SORT_LIST, content, e);
                }
            }
        });
    }

    private void showEmptyView() {
        if (isAdded() && this.mBind != null && ((FragmentVehicleContentBinding) this.mBind).emptyView != null && this.mList.size() == 0) {
            ((FragmentVehicleContentBinding) this.mBind).emptyView.setEmptyImage(2130837857);
            ((FragmentVehicleContentBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void showNetErrorView() {
        if (isAdded() && this.mBind != null && ((FragmentVehicleContentBinding) this.mBind).emptyView != null) {
            ((FragmentVehicleContentBinding) this.mBind).emptyView.setEmptyImage(2130838112);
            ((FragmentVehicleContentBinding) this.mBind).emptyView.setEmptyText(2131231188);
            ((FragmentVehicleContentBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    VehicleContentFragment.this.getData(true);
                }
            });
            ((FragmentVehicleContentBinding) this.mBind).emptyView.setVisibility(0);
            ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.setNoMore(true);
        }
    }

    private void hideEmptyView() {
        ((FragmentVehicleContentBinding) this.mBind).emptyView.setVisibility(8);
        ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.setNoMore(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(VideoInFragmentChangeEvent event) {
        if (isAdded() && event.type == VideoInFragmentChangeEvent.VEHIC_PAGE_SLIDE && event.index == this.mSubclassCircleId && this.mIsFirst) {
            this.mIsFirst = false;
            ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.forceToRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (event != null) {
            this.mAdater.refreshUserStatus(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.setIsScrollDown(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event != null) {
            this.mAdater.refreshSnsInfo(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SendDelArticleSuccess event) {
        if (event != null && TextUtils.equals(this.mSubclassCircleFragmentName, "全部") && event.snsInfo.communitylist.getPosition(this.mCircleID) >= 0) {
            if (event.type == 1) {
                this.mList.add(0, event.snsInfo);
                hideEmptyView();
                this.mAdater.notifyItemInserted(0);
            } else if (event.type == 2) {
                int pos = this.mList.getPosition(event.snsInfo.getLocalkey());
                if (pos != -1 && this.mList.get(pos).id == event.snsInfo.id) {
                    this.mList.remove(pos);
                    showEmptyView();
                    this.mAdater.notifyItemRemoved(pos);
                }
            }
        }
    }

    public void loginSuccess() {
        super.loginSuccess();
        refreshAuto();
    }

    public void checkPageAndSortType() {
        int sortType = SharedUtil.getInt(this.mActivity, CIRCLE_SORT_TYPE, 2);
        if (this.mAdater == null || this.mList.size() <= 0 || this.mContentSortType == sortType) {
            this.mContentSortType = sortType;
            if (this.mBind != null && this.mIsFirst) {
                this.mIsFirst = false;
                ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.forceToRefresh();
                return;
            }
            return;
        }
        this.mContentSortType = sortType;
        loadData(true);
    }

    private void refreshAuto() {
        ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.setPullRefreshEnabled(true);
        ((FragmentVehicleContentBinding) this.mBind).rcviewVehicle.forceToRefresh();
    }
}
