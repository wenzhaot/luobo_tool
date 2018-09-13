package com.feng.car.activity;

import android.content.Intent;
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
import com.feng.car.event.UserFirstFollowEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.utils.FengUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
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

public class FansFollowActivity extends BaseActivity<ActivityFansBinding> {
    private FansAdapter mAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private UserInfoList mList = new UserInfoList();
    private Map<String, Object> mMap = new HashMap();
    private int mPage = 1;
    private int mTotalPage = 0;
    private int mUserID = -1;

    public void loginSuccess() {
        super.loginSuccess();
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.forceToRefresh();
    }

    private void setListener() {
        this.mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                FansFollowActivity.this.mList.get(position).intentToPersonalHome(FansFollowActivity.this);
            }
        });
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityFansBinding) FansFollowActivity.this.mBaseBinding).lrvFans, State.Normal);
                FansFollowActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                FansFollowActivity.this.mPage = 1;
                FansFollowActivity.this.getData(true, false);
            }
        });
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (!RecyclerViewStateUtils.isRecylerViewLoading(((ActivityFansBinding) FansFollowActivity.this.mBaseBinding).lrvFans)) {
                    if (FansFollowActivity.this.mPage > FansFollowActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(FansFollowActivity.this, ((ActivityFansBinding) FansFollowActivity.this.mBaseBinding).lrvFans, 20, State.TheEnd, null);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(FansFollowActivity.this, ((ActivityFansBinding) FansFollowActivity.this.mBaseBinding).lrvFans, 20, State.Loading, null);
                    FansFollowActivity.this.getData(false, true);
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
            FengApplication.getInstance().httpRequest("user/follow/", this.mMap, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (FansFollowActivity.this.mList.size() > 0) {
                        FansFollowActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        FansFollowActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                FansFollowActivity.this.getData(false, false);
                            }
                        });
                    }
                    RecyclerViewStateUtils.setFooterViewState(((ActivityFansBinding) FansFollowActivity.this.mBaseBinding).lrvFans, State.Normal);
                    ((ActivityFansBinding) FansFollowActivity.this.mBaseBinding).lrvFans.refreshComplete();
                }

                public void onStart() {
                }

                public void onFinish() {
                    RecyclerViewStateUtils.setFooterViewState(((ActivityFansBinding) FansFollowActivity.this.mBaseBinding).lrvFans, State.Normal);
                    ((ActivityFansBinding) FansFollowActivity.this.mBaseBinding).lrvFans.refreshComplete();
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    if (FansFollowActivity.this.mList.size() > 0) {
                        FansFollowActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        FansFollowActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                FansFollowActivity.this.getData(false, false);
                            }
                        });
                    }
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            JSONObject jsonUsers = jsonResult.getJSONObject("body").getJSONObject("user");
                            BaseListModel<UserInfo> mPageInfo = new BaseListModel();
                            mPageInfo.parser(UserInfo.class, jsonUsers);
                            FansFollowActivity.this.mTotalPage = mPageInfo.pagecount;
                            List<UserInfo> list = mPageInfo.list;
                            FansFollowActivity.this.hideEmptyView();
                            if (list.size() > 0) {
                                if (needClear) {
                                    FansFollowActivity.this.mList.clear();
                                }
                                FansFollowActivity.this.mList.addAll(list);
                                FansFollowActivity.this.mPage = FansFollowActivity.this.mPage + 1;
                            } else if (FansFollowActivity.this.mPage == 1) {
                                if (FengUtil.isMine(FansFollowActivity.this.mUserID)) {
                                    FansFollowActivity.this.showEmptyView(R.string.iHaveNoFansFollow, R.drawable.no_fans, R.string.findFansFollow, new OnSingleClickListener() {
                                        public void onSingleClick(View v) {
                                            FansFollowActivity.this.startActivity(new Intent(FansFollowActivity.this, FindFollowActivity.class));
                                        }
                                    });
                                } else {
                                    FansFollowActivity.this.showEmptyView((int) R.string.other_follow_empty_tips, (int) R.drawable.no_fans);
                                }
                            }
                            FansFollowActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                            return;
                        }
                        FengApplication.getInstance().checkCode("user/fins/", code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        FansFollowActivity.this.showSecondTypeToast((int) R.string.analysis_data_failed);
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
        initNormalTitleBar((int) R.string.attention);
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((ActivityFansBinding) FansFollowActivity.this.mBaseBinding).lrvFans.scrollToPosition(0);
            }
        }));
        this.mUserID = getIntent().getIntExtra("userid", -1);
        this.mAdapter = new FansAdapter(this, this.mList);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        setListener();
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.setRefreshing(true);
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.setRefreshProgressStyle(2);
        ((ActivityFansBinding) this.mBaseBinding).lrvFans.setLScrollListener(new LRecyclerView$LScrollListener() {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((ActivityFansBinding) this.mBaseBinding).lrvFans.setIsScrollDown(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserFirstFollowEvent event) {
        if (event != null && FengUtil.isMine(this.mUserID)) {
            ((ActivityFansBinding) this.mBaseBinding).lrvFans.forceToRefresh();
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
