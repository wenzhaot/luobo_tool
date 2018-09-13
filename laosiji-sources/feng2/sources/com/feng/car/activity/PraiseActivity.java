package com.feng.car.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.PraiseAdapter;
import com.feng.car.databinding.PraiseMsgActivtyBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.praise.PraiseInfoList;
import com.feng.car.entity.thread.GratuityRecordInfo;
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
import com.stub.StubApp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class PraiseActivity extends BaseActivity<PraiseMsgActivtyBinding> {
    private final int GET_PRAISE = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private PraiseInfoList mList = new PraiseInfoList();
    private int mPage = 1;
    private PraiseAdapter mPraiseAdapter;
    private int mTotalPage = 0;
    private Map<String, Object> map = new HashMap();
    private int type = 1;

    static {
        StubApp.interface11(2688);
    }

    protected native void onCreate(Bundle bundle);

    public void initView() {
        initNormalTitleBar("赞");
        ((PraiseMsgActivtyBinding) this.mBaseBinding).rcviewPraise.setPullRefreshEnabled(true);
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((PraiseMsgActivtyBinding) PraiseActivity.this.mBaseBinding).rcviewPraise.scrollToPosition(0);
            }
        }));
        this.mPraiseAdapter = new PraiseAdapter(this, this.mList.getPraiseList());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mPraiseAdapter);
        ((PraiseMsgActivtyBinding) this.mBaseBinding).rcviewPraise.setAdapter(this.mLRecyclerViewAdapter);
        ((PraiseMsgActivtyBinding) this.mBaseBinding).rcviewPraise.setLayoutManager(new LinearLayoutManager(this));
        ((PraiseMsgActivtyBinding) this.mBaseBinding).rcviewPraise.setRefreshProgressStyle(2);
        ((PraiseMsgActivtyBinding) this.mBaseBinding).rcviewPraise.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((PraiseMsgActivtyBinding) PraiseActivity.this.mBaseBinding).rcviewPraise, State.Normal);
                PraiseActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                PraiseActivity.this.mPage = 1;
                PraiseActivity.this.getData(true, false);
            }
        });
        ((PraiseMsgActivtyBinding) this.mBaseBinding).rcviewPraise.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((PraiseMsgActivtyBinding) PraiseActivity.this.mBaseBinding).rcviewPraise) != State.Loading) {
                    if (PraiseActivity.this.mPage <= PraiseActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(PraiseActivity.this, ((PraiseMsgActivtyBinding) PraiseActivity.this.mBaseBinding).rcviewPraise, 20, State.Loading, null);
                        PraiseActivity.this.getData(false, true);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(PraiseActivity.this, ((PraiseMsgActivtyBinding) PraiseActivity.this.mBaseBinding).rcviewPraise, 20, State.TheEnd, null);
                }
            }
        });
        ((PraiseMsgActivtyBinding) this.mBaseBinding).rcviewPraise.setRefreshing(true);
        ((PraiseMsgActivtyBinding) this.mBaseBinding).rcviewPraise.setLScrollListener(new LRecyclerView$LScrollListener() {
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

    private void getData(final boolean needClear, final boolean isLoadMoore) {
        if (this.mPage <= this.mTotalPage || !isLoadMoore) {
            this.map.put("type", String.valueOf(this.type));
            this.map.put("page", String.valueOf(this.mPage));
            FengApplication.getInstance().httpRequest("user/ywf/praise/", this.map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (PraiseActivity.this.mList.size() > 0) {
                        PraiseActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        PraiseActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                PraiseActivity.this.getData(needClear, isLoadMoore);
                            }
                        });
                    }
                    ((PraiseMsgActivtyBinding) PraiseActivity.this.mBaseBinding).rcviewPraise.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((PraiseMsgActivtyBinding) PraiseActivity.this.mBaseBinding).rcviewPraise, State.Normal);
                }

                public void onStart() {
                }

                public void onFinish() {
                    PraiseActivity.this.hideProgress();
                    RecyclerViewStateUtils.setFooterViewState(((PraiseMsgActivtyBinding) PraiseActivity.this.mBaseBinding).rcviewPraise, State.Normal);
                    ((PraiseMsgActivtyBinding) PraiseActivity.this.mBaseBinding).rcviewPraise.refreshComplete();
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    PraiseActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            JSONObject jsonPraise = jsonResult.getJSONObject("body").getJSONObject("praise");
                            BaseListModel<GratuityRecordInfo> baseListModel = new BaseListModel();
                            baseListModel.parser(GratuityRecordInfo.class, jsonPraise);
                            PraiseActivity.this.mTotalPage = baseListModel.pagecount;
                            List<GratuityRecordInfo> list = baseListModel.list;
                            PraiseActivity.this.hideEmptyView();
                            if (list.size() > 0) {
                                if (needClear) {
                                    PraiseActivity.this.mList.clear();
                                }
                                PraiseActivity.this.mList.addAll(list);
                                if (PraiseActivity.this.mPage == 1 && PraiseActivity.this.mList.size() <= 0) {
                                    PraiseActivity.this.showEmptyView(R.string.message_page_praise_empty_tips);
                                }
                                PraiseActivity.this.mPage = PraiseActivity.this.mPage + 1;
                            } else if (PraiseActivity.this.mPage == 1) {
                                PraiseActivity.this.showEmptyView(R.string.message_page_praise_empty_tips);
                            }
                            PraiseActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                        } else if (PraiseActivity.this.mList.size() > 0) {
                            FengApplication.getInstance().checkCode("user/ywf/praise/", code);
                        } else {
                            PraiseActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                                public void onSingleClick(View v) {
                                    PraiseActivity.this.getData(needClear, isLoadMoore);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (PraiseActivity.this.mList.size() > 0) {
                            PraiseActivity.this.showSecondTypeToast((int) R.string.analysis_data_failed);
                        } else {
                            PraiseActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                                public void onSingleClick(View v) {
                                    PraiseActivity.this.getData(needClear, isLoadMoore);
                                }
                            });
                        }
                        FengApplication.getInstance().upLoadTryCatchLog("user/ywf/praise/", content, e);
                    }
                }
            });
            return;
        }
        showThirdTypeToast((int) R.string.load_more_tips);
    }

    public int setBaseContentView() {
        return R.layout.praise_msg_activty;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((PraiseMsgActivtyBinding) this.mBaseBinding).rcviewPraise.setIsScrollDown(true);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
