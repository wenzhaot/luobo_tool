package com.feng.car.activity;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.view.SpacesItemDecoration;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
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

public class CollectionActivity extends BaseActivity<CommonRecyclerviewBinding> {
    private CommonPostAdapter mAdapter;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();
    private int mTotalPage;

    public int setBaseContentView() {
        return R.layout.common_recyclerview;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.mine_collect);
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((CommonRecyclerviewBinding) CollectionActivity.this.mBaseBinding).recyclerview.scrollToPosition(0);
            }
        }));
        this.mAdapter = new CommonPostAdapter(this, this.mList, 0, true, this.mLogGatherInfo);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.addItemDecoration(new SpacesItemDecoration(this));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CollectionActivity.this.mBaseBinding).recyclerview, State.Normal);
                CollectionActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                CollectionActivity.this.mCurrentPage = 1;
                CollectionActivity.this.getData(true);
            }
        });
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) CollectionActivity.this.mBaseBinding).recyclerview) != State.Loading) {
                    if (CollectionActivity.this.mCurrentPage <= CollectionActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(CollectionActivity.this, ((CommonRecyclerviewBinding) CollectionActivity.this.mBaseBinding).recyclerview, 20, State.Loading, null);
                        CollectionActivity.this.getData(false);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(CollectionActivity.this, ((CommonRecyclerviewBinding) CollectionActivity.this.mBaseBinding).recyclerview, 20, State.TheEnd, null);
                }
            }
        });
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshing(true);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLScrollListener(new LRecyclerView$LScrollListener() {
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
        map.put("page", String.valueOf(this.mCurrentPage));
        FengApplication.getInstance().httpRequest("thread/listbyfavorite/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (CollectionActivity.this.mList.size() > 0) {
                    CollectionActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    CollectionActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            CollectionActivity.this.getData(true);
                        }
                    });
                }
                ((CommonRecyclerviewBinding) CollectionActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CollectionActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
                ((CommonRecyclerviewBinding) CollectionActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CollectionActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (CollectionActivity.this.mList.size() > 0) {
                    CollectionActivity.this.showThirdTypeToast((int) R.string.check_network);
                } else {
                    CollectionActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            CollectionActivity.this.getData(true);
                        }
                    });
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonThread = jsonResult.getJSONObject("body").getJSONObject("sns");
                        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SnsInfo.class, jsonThread);
                        CollectionActivity.this.mTotalPage = baseListModel.pagecount;
                        List<SnsInfo> list = baseListModel.list;
                        if (list != null && list.size() > 0) {
                            if (needClear) {
                                CollectionActivity.this.mList.clear();
                            }
                            int oldSize = CollectionActivity.this.mList.size();
                            CollectionActivity.this.mList.addAll(list);
                            if (CollectionActivity.this.mCurrentPage == 1 && CollectionActivity.this.mList.size() == 0) {
                                CollectionActivity.this.showEmptyView((int) R.string.collect_none, (int) R.drawable.blank_collect);
                                return;
                            }
                            CollectionActivity.this.mCurrentPage = CollectionActivity.this.mCurrentPage + 1;
                            CollectionActivity.this.hideEmptyView();
                            if (needClear) {
                                CollectionActivity.this.mAdapter.notifyDataSetChanged();
                                return;
                            } else {
                                CollectionActivity.this.mAdapter.notifyItemRangeInserted(oldSize, CollectionActivity.this.mList.size() - oldSize);
                                return;
                            }
                        } else if (CollectionActivity.this.mCurrentPage == 1) {
                            CollectionActivity.this.showEmptyView((int) R.string.collect_none, (int) R.drawable.blank_collect);
                            return;
                        } else {
                            return;
                        }
                    }
                    FengApplication.getInstance().checkCode("thread/listbyfavorite/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("thread/listbyfavorite/", content, e);
                }
            }
        });
    }

    protected void onPause() {
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event != null) {
            this.mAdapter.refreshSnsInfo(event);
            if (this.mList.size() == 0) {
                showEmptyView((int) R.string.collect_none, (int) R.drawable.blank_collect);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (event != null) {
            this.mAdapter.refreshUserStatus(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setIsScrollDown(true);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }

    public void loginSuccess() {
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.forceToRefresh();
    }
}
