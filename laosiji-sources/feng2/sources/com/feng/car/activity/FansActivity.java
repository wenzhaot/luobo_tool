package com.feng.car.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.FansAdapter;
import com.feng.car.databinding.ActivityFansBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.entity.user.UserInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.utils.FengUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.stub.StubApp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class FansActivity extends BaseActivity<ActivityFansBinding> {
    private FansAdapter mAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private UserInfoList mList = new UserInfoList();
    private Map<String, Object> mMap = new HashMap();
    private int mPage = 1;
    private int mTotalPage = 0;
    private int mUserID = -1;

    static {
        StubApp.interface11(2405);
    }

    protected native void onCreate(Bundle bundle);

    public void loginSuccess() {
        super.loginSuccess();
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.forceToRefresh();
    }

    private void setListener() {
        this.mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                FansActivity.this.mList.get(position).intentToPersonalHome(FansActivity.this);
            }
        });
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityFansBinding) FansActivity.this.mBaseBinding).lrvFans, State.Normal);
                FansActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                FansActivity.this.mPage = 1;
                FansActivity.this.getData(true, false);
            }
        });
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (!RecyclerViewStateUtils.isRecylerViewLoading(((ActivityFansBinding) FansActivity.this.mBaseBinding).lrvFans)) {
                    if (FansActivity.this.mPage > FansActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(FansActivity.this, ((ActivityFansBinding) FansActivity.this.mBaseBinding).lrvFans, 20, State.TheEnd, null);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(FansActivity.this, ((ActivityFansBinding) FansActivity.this.mBaseBinding).lrvFans, 20, State.Loading, null);
                    FansActivity.this.getData(false, true);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (this.mAdapter != null) {
            this.mAdapter.refreshUserFollowState(event);
        }
    }

    private void getData(final boolean needClear, boolean isLoadMoore) {
        if (this.mPage <= this.mTotalPage || !isLoadMoore) {
            this.mMap.put("page", String.valueOf(this.mPage));
            if (this.mUserID != -1) {
                this.mMap.put("userid", String.valueOf(this.mUserID));
            }
            FengApplication.getInstance().httpRequest("user/fins/", this.mMap, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (FansActivity.this.mList.size() > 0) {
                        FansActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        FansActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                FansActivity.this.getData(false, false);
                            }
                        });
                    }
                    RecyclerViewStateUtils.setFooterViewState(((ActivityFansBinding) FansActivity.this.mBaseBinding).lrvFans, State.Normal);
                    ((ActivityFansBinding) FansActivity.this.mBaseBinding).lrvFans.refreshComplete();
                }

                public void onStart() {
                }

                public void onFinish() {
                    RecyclerViewStateUtils.setFooterViewState(((ActivityFansBinding) FansActivity.this.mBaseBinding).lrvFans, State.Normal);
                    ((ActivityFansBinding) FansActivity.this.mBaseBinding).lrvFans.refreshComplete();
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    if (FansActivity.this.mList.size() > 0) {
                        FansActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        FansActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                FansActivity.this.getData(false, false);
                            }
                        });
                    }
                    RecyclerViewStateUtils.setFooterViewState(((ActivityFansBinding) FansActivity.this.mBaseBinding).lrvFans, State.Normal);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            JSONObject jsonUsers = jsonResult.getJSONObject("body").getJSONObject("user");
                            BaseListModel<UserInfo> mPageInfo = new BaseListModel();
                            mPageInfo.parser(UserInfo.class, jsonUsers);
                            FansActivity.this.mTotalPage = mPageInfo.pagecount;
                            List<UserInfo> list = mPageInfo.list;
                            FansActivity.this.hideEmptyView();
                            if (list.size() > 0) {
                                if (needClear) {
                                    FansActivity.this.mList.clear();
                                }
                                FansActivity.this.mList.addAll(list);
                                FansActivity.this.mPage = FansActivity.this.mPage + 1;
                            } else if (FansActivity.this.mPage == 1) {
                                if (FengUtil.isMine(FansActivity.this.mUserID)) {
                                    FansActivity.this.showEmptyView((int) R.string.iHaveNoFans, (int) R.drawable.no_fans);
                                } else {
                                    FansActivity.this.showEmptyView((int) R.string.other_fans_empty_tips, (int) R.drawable.no_fans);
                                }
                            }
                            FansActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                            return;
                        }
                        FengApplication.getInstance().checkCode("user/fins/", code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        FansActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                        FengApplication.getInstance().upLoadTryCatchLog("user/fins/", content, e);
                    }
                }
            });
            return;
        }
        showThirdTypeToast((int) R.string.load_more_tips);
    }

    public int setBaseContentView() {
        return R.layout.activity_fans;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.fans);
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((ActivityFansBinding) FansActivity.this.mBaseBinding).lrvFans.scrollToPosition(0);
            }
        }));
        this.mAdapter = new FansAdapter(this, this.mList);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.setRefreshProgressStyle(2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((ActivityFansBinding) this.mBaseBinding).lrvFans.setIsScrollDown(true);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
