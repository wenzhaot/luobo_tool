package com.feng.car.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.VoiceChooseCarAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
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

public class ListenedHistoryActivity extends BaseActivity<CommonRecyclerviewBinding> {
    private VoiceChooseCarAdapter mAdapter;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();
    private int mTotalPage;

    public int setBaseContentView() {
        return R.layout.common_recyclerview;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.listened_history);
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((CommonRecyclerviewBinding) ListenedHistoryActivity.this.mBaseBinding).recyclerview.scrollToPosition(0);
            }
        }));
        this.mAdapter = new VoiceChooseCarAdapter(this, this.mList.getSnsList());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) ListenedHistoryActivity.this.mBaseBinding).recyclerview, State.Normal);
                ListenedHistoryActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                ListenedHistoryActivity.this.mCurrentPage = 1;
                ListenedHistoryActivity.this.getData(true);
            }
        });
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) ListenedHistoryActivity.this.mBaseBinding).recyclerview) != State.Loading) {
                    if (ListenedHistoryActivity.this.mCurrentPage <= ListenedHistoryActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(ListenedHistoryActivity.this, ((CommonRecyclerviewBinding) ListenedHistoryActivity.this.mBaseBinding).recyclerview, 20, State.Loading, null);
                        ListenedHistoryActivity.this.getData(false);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(ListenedHistoryActivity.this, ((CommonRecyclerviewBinding) ListenedHistoryActivity.this.mBaseBinding).recyclerview, 20, State.TheEnd, null);
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
        FengApplication.getInstance().httpRequest("user/ywf/audio/history/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (ListenedHistoryActivity.this.mList.size() > 0) {
                    ListenedHistoryActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    ListenedHistoryActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ListenedHistoryActivity.this.getData(true);
                        }
                    });
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) ListenedHistoryActivity.this.mBaseBinding).recyclerview, State.Normal);
                ((CommonRecyclerviewBinding) ListenedHistoryActivity.this.mBaseBinding).recyclerview.refreshComplete();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (ListenedHistoryActivity.this.mList.size() > 0) {
                    ListenedHistoryActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    ListenedHistoryActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ListenedHistoryActivity.this.getData(true);
                        }
                    });
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject sns = jsonResult.getJSONObject("body").getJSONObject("sns");
                        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SnsInfo.class, sns);
                        ListenedHistoryActivity.this.mTotalPage = baseListModel.pagecount;
                        List<SnsInfo> list = baseListModel.list;
                        ListenedHistoryActivity.this.hideEmptyView();
                        if (needClear) {
                            ListenedHistoryActivity.this.mList.clear();
                        }
                        ListenedHistoryActivity.this.mList.addAll(baseListModel.list);
                        if (ListenedHistoryActivity.this.mList.size() <= 0) {
                            ListenedHistoryActivity.this.showEmptyView((int) R.string.listened_history_none, (int) R.drawable.listened_history_none);
                        }
                        ListenedHistoryActivity.this.mCurrentPage = ListenedHistoryActivity.this.mCurrentPage + 1;
                        ListenedHistoryActivity.this.mAdapter.notifyDataSetChanged();
                    } else if (ListenedHistoryActivity.this.mList.size() > 0) {
                        FengApplication.getInstance().checkCode("user/ywf/audio/history/", code);
                    } else {
                        ListenedHistoryActivity.this.showEmptyView((int) R.string.listened_history_none, (int) R.drawable.listened_history_none);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ListenedHistoryActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    if (ListenedHistoryActivity.this.mList.size() <= 0) {
                        ListenedHistoryActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                ListenedHistoryActivity.this.getData(true);
                            }
                        });
                    }
                }
            }
        });
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
