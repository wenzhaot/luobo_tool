package com.feng.car.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.BlackListInnerAdapter;
import com.feng.car.databinding.ActivityBlacklistBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.utils.BlackUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
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

public class BlackListActivity extends BaseActivity<ActivityBlacklistBinding> {
    private BlackListInnerAdapter mBlackListInnerAdapter;
    private boolean mIsRefresh;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<UserInfo> mList = new ArrayList();
    private int mPage = 1;

    static {
        StubApp.interface11(2208);
    }

    protected native void onCreate(Bundle bundle);

    public int setBaseContentView() {
        return R.layout.activity_blacklist;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (event != null && event.type == 2 && this.mList.size() > 0 && event.userInfo != null) {
            for (int i = 0; i < this.mList.size(); i++) {
                UserInfo user = (UserInfo) this.mList.get(i);
                if (user.id == event.userInfo.id) {
                    user.isblack.set(event.userInfo.isblack.get());
                    user.isfollow.set(event.userInfo.isfollow.get());
                    return;
                }
            }
        }
    }

    public void initView() {
        initNormalTitleBar((int) R.string.blacklist);
        ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setPullRefreshEnabled(true);
        ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setRefreshing(true);
        this.mBlackListInnerAdapter = new BlackListInnerAdapter(this, this.mList);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mBlackListInnerAdapter);
        ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityBlacklistBinding) BlackListActivity.this.mBaseBinding).recyclerview, State.Normal);
                BlackListActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                BlackListActivity.this.mIsRefresh = true;
                BlackListActivity.this.getData();
            }
        });
        ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setNoMore(true);
        ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setLScrollListener(new LRecyclerView$LScrollListener() {
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

    private void getData() {
        Map<String, Object> map = new HashMap();
        map.put("page", String.valueOf(this.mPage));
        if (FengApplication.getInstance().isLoginUser()) {
            map.put("userid", String.valueOf(FengApplication.getInstance().getUserInfo().id));
        }
        FengApplication.getInstance().httpRequest("user/blacklist/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (BlackListActivity.this.mList.size() > 0) {
                    BlackListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    BlackListActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            BlackListActivity.this.getData();
                        }
                    });
                }
                ((ActivityBlacklistBinding) BlackListActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivityBlacklistBinding) BlackListActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityBlacklistBinding) BlackListActivity.this.mBaseBinding).recyclerview, State.Normal);
                ((ActivityBlacklistBinding) BlackListActivity.this.mBaseBinding).recyclerview.refreshComplete();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (BlackListActivity.this.mList.size() > 0) {
                    BlackListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    BlackListActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            BlackListActivity.this.getData();
                        }
                    });
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonUser = jsonResult.getJSONObject("body").getJSONObject("user");
                        BaseListModel<UserInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(UserInfo.class, jsonUser);
                        BlackListActivity.this.hideEmptyView();
                        if (BlackListActivity.this.mIsRefresh) {
                            BlackListActivity.this.mList.clear();
                            BlackListActivity.this.mIsRefresh = false;
                            ((ActivityBlacklistBinding) BlackListActivity.this.mBaseBinding).recyclerview.refreshComplete();
                        }
                        BlackListActivity.this.mList.addAll(baseListModel.list);
                        RecyclerViewStateUtils.setFooterViewState(((ActivityBlacklistBinding) BlackListActivity.this.mBaseBinding).recyclerview, State.Normal);
                        BlackListActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                        if (BlackListActivity.this.mList.size() > 0) {
                            for (int i = 0; i < BlackListActivity.this.mList.size(); i++) {
                                UserInfo info = (UserInfo) BlackListActivity.this.mList.get(i);
                                if (info != null) {
                                    info.isblack.set(1);
                                }
                            }
                        } else if (BlackListActivity.this.mPage == 1) {
                            BlackListActivity.this.showEmptyView((int) R.string.black_empty_tips, (int) R.drawable.empty_black_list);
                        }
                        BlackUtil.getInstance().addAllBlackList(BlackListActivity.this.mList, BlackListActivity.this);
                        return;
                    }
                    BlackListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    FengApplication.getInstance().upLoadLog(true, "user/blacklist/    " + code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    BlackListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    FengApplication.getInstance().upLoadTryCatchLog("user/blacklist/", content, e);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setIsScrollDown(true);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
