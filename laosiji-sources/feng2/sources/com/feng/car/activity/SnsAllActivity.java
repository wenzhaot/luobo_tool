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

public class SnsAllActivity extends BaseActivity<CommonRecyclerviewBinding> {
    private CommonPostAdapter mAdapter;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();
    private int mTotalPage;

    public int setBaseContentView() {
        return R.layout.common_recyclerview;
    }

    public void initView() {
        initNormalTitleBar("最新文章");
        this.mAdapter = new CommonPostAdapter(this, this.mList, 0, true, this.mLogGatherInfo);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.addItemDecoration(new SpacesItemDecoration(this));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SnsAllActivity.this.mBaseBinding).recyclerview, State.Normal);
                SnsAllActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                SnsAllActivity.this.mCurrentPage = 1;
                SnsAllActivity.this.getData(true);
            }
        });
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) SnsAllActivity.this.mBaseBinding).recyclerview) != State.Loading) {
                    if (SnsAllActivity.this.mCurrentPage <= SnsAllActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(SnsAllActivity.this, ((CommonRecyclerviewBinding) SnsAllActivity.this.mBaseBinding).recyclerview, 20, State.Loading, null);
                        SnsAllActivity.this.getData(false);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(SnsAllActivity.this, ((CommonRecyclerviewBinding) SnsAllActivity.this.mBaseBinding).recyclerview, 20, State.TheEnd, null);
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
        FengApplication.getInstance().httpRequest("sns/snsall/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (SnsAllActivity.this.mList.size() <= 0) {
                    SnsAllActivity.this.showNetErrorView();
                } else {
                    SnsAllActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                ((CommonRecyclerviewBinding) SnsAllActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SnsAllActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (SnsAllActivity.this.mList.size() <= 0) {
                    SnsAllActivity.this.showNetErrorView();
                } else {
                    SnsAllActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonSns = jsonResult.getJSONObject("body").getJSONObject("sns");
                        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SnsInfo.class, jsonSns);
                        SnsAllActivity.this.mTotalPage = baseListModel.pagecount;
                        List<SnsInfo> list = baseListModel.list;
                        SnsAllActivity.this.hideEmptyView();
                        if (list != null && list.size() > 0) {
                            int oldSize = SnsAllActivity.this.mList.size();
                            if (needClear) {
                                SnsAllActivity.this.mList.clear();
                            }
                            SnsAllActivity.this.mList.addAll(list);
                            SnsAllActivity.this.mCurrentPage = SnsAllActivity.this.mCurrentPage + 1;
                            if (needClear) {
                                SnsAllActivity.this.mAdapter.notifyDataSetChanged();
                            } else {
                                SnsAllActivity.this.mAdapter.notifyItemRangeInserted(oldSize, SnsAllActivity.this.mList.size() - oldSize);
                            }
                        }
                    } else if (SnsAllActivity.this.mList.size() <= 0) {
                        SnsAllActivity.this.showNetErrorView();
                    } else {
                        FengApplication.getInstance().checkCode("sns/snsall/", code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (SnsAllActivity.this.mList.size() <= 0) {
                        SnsAllActivity.this.showNetErrorView();
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("sns/snsall/", content, e);
                }
            }
        });
    }

    public void showNetErrorView() {
        showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SnsAllActivity.this.getData(true);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (event != null) {
            this.mAdapter.refreshUserStatus(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event != null) {
            this.mAdapter.refreshSnsInfo(event);
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
}
