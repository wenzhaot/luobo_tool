package com.feng.car.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.NoticeAdapter;
import com.feng.car.databinding.SystemNoticeActivityBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.sysmsg.SystemMessageInfo;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class SystemNoticeActivity extends BaseActivity<SystemNoticeActivityBinding> {
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<SystemMessageInfo> mList = new ArrayList();
    private NoticeAdapter mNoticeAdapter;
    private int mPage = 1;
    private int mTotalPage = 0;

    static {
        StubApp.interface11(3146);
    }

    protected native void onCreate(Bundle bundle);

    public int setBaseContentView() {
        return R.layout.system_notice_activity;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.system_notice);
        ((SystemNoticeActivityBinding) this.mBaseBinding).rcviewNotice.setPullRefreshEnabled(true);
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((SystemNoticeActivityBinding) SystemNoticeActivity.this.mBaseBinding).rcviewNotice.scrollToPosition(0);
            }
        }));
        this.mList = new ArrayList();
        this.mNoticeAdapter = new NoticeAdapter(this, this.mList);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mNoticeAdapter);
        ((SystemNoticeActivityBinding) this.mBaseBinding).rcviewNotice.setAdapter(this.mLRecyclerViewAdapter);
        ((SystemNoticeActivityBinding) this.mBaseBinding).rcviewNotice.setLayoutManager(new LinearLayoutManager(this));
        ((SystemNoticeActivityBinding) this.mBaseBinding).rcviewNotice.setRefreshProgressStyle(2);
        ((SystemNoticeActivityBinding) this.mBaseBinding).rcviewNotice.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((SystemNoticeActivityBinding) SystemNoticeActivity.this.mBaseBinding).rcviewNotice, State.Normal);
                SystemNoticeActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                SystemNoticeActivity.this.mPage = 1;
                SystemNoticeActivity.this.getData(true);
            }
        });
        ((SystemNoticeActivityBinding) this.mBaseBinding).rcviewNotice.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((SystemNoticeActivityBinding) SystemNoticeActivity.this.mBaseBinding).rcviewNotice) != State.Loading) {
                    if (SystemNoticeActivity.this.mPage <= SystemNoticeActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(SystemNoticeActivity.this, ((SystemNoticeActivityBinding) SystemNoticeActivity.this.mBaseBinding).rcviewNotice, 20, State.Loading, null);
                        SystemNoticeActivity.this.getData(false);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(SystemNoticeActivity.this, ((SystemNoticeActivityBinding) SystemNoticeActivity.this.mBaseBinding).rcviewNotice, 20, State.TheEnd, null);
                }
            }
        });
        ((SystemNoticeActivityBinding) this.mBaseBinding).rcviewNotice.setRefreshing(true);
        ((SystemNoticeActivityBinding) this.mBaseBinding).rcviewNotice.setLScrollListener(new LRecyclerView$LScrollListener() {
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
        map.put("page", String.valueOf(this.mPage));
        FengApplication.getInstance().httpRequest("user/system/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (SystemNoticeActivity.this.mList.size() > 0) {
                    SystemNoticeActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    SystemNoticeActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            SystemNoticeActivity.this.getData(needClear);
                        }
                    });
                }
                ((SystemNoticeActivityBinding) SystemNoticeActivity.this.mBaseBinding).rcviewNotice.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((SystemNoticeActivityBinding) SystemNoticeActivity.this.mBaseBinding).rcviewNotice, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
                RecyclerViewStateUtils.setFooterViewState(((SystemNoticeActivityBinding) SystemNoticeActivity.this.mBaseBinding).rcviewNotice, State.Normal);
                ((SystemNoticeActivityBinding) SystemNoticeActivity.this.mBaseBinding).rcviewNotice.refreshComplete();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                SystemNoticeActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        JSONObject systemJson = resultJson.getJSONObject("body").getJSONObject("system");
                        BaseListModel<SystemMessageInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SystemMessageInfo.class, systemJson);
                        SystemNoticeActivity.this.mTotalPage = baseListModel.pagecount;
                        SystemNoticeActivity.this.hideEmptyView();
                        List<SystemMessageInfo> list = baseListModel.list;
                        if (list != null && list.size() > 0) {
                            if (needClear) {
                                SystemNoticeActivity.this.mList.clear();
                            }
                            SystemNoticeActivity.this.mList.addAll(list);
                            SystemNoticeActivity.this.mPage = SystemNoticeActivity.this.mPage + 1;
                        } else if (SystemNoticeActivity.this.mPage == 1) {
                            SystemNoticeActivity.this.showEmptyView(R.string.message_page_infos_empty_tips);
                        }
                        RecyclerViewStateUtils.setFooterViewState(((SystemNoticeActivityBinding) SystemNoticeActivity.this.mBaseBinding).rcviewNotice, State.Normal);
                        SystemNoticeActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                    } else if (SystemNoticeActivity.this.mList.size() > 0) {
                        FengApplication.getInstance().checkCode("user/system/", code);
                    } else {
                        SystemNoticeActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                SystemNoticeActivity.this.getData(needClear);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (SystemNoticeActivity.this.mList.size() > 0) {
                        SystemNoticeActivity.this.showSecondTypeToast((int) R.string.analysis_data_failed);
                    } else {
                        SystemNoticeActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                SystemNoticeActivity.this.getData(needClear);
                            }
                        });
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("user/system/", content, e);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((SystemNoticeActivityBinding) this.mBaseBinding).rcviewNotice.setIsScrollDown(true);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
