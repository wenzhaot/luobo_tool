package com.feng.car.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.HotShowAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.car.event.ProgramFollowEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.MessageCountManager;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;
import com.github.jdsjlzx.view.LoadingFooter$FooterCallBack;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class ShowsFollowedActivity extends BaseActivity<CommonRecyclerviewBinding> {
    private HotShowAdapter mHotShowAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<HotShowInfo> mList = new ArrayList();
    private int mPage = 1;
    private int mTotalPage = 0;

    public void initView() {
        initNormalTitleBar((int) R.string.shows_followed);
        initTitleBarMsgCount();
        changeRedDot();
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        this.mHotShowAdapter = new HotShowAdapter(this, this.mList);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mHotShowAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) ShowsFollowedActivity.this.mBaseBinding).recyclerview, State.Normal);
                ShowsFollowedActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                ShowsFollowedActivity.this.mPage = 1;
                ShowsFollowedActivity.this.getData();
            }
        });
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (!((CommonRecyclerviewBinding) ShowsFollowedActivity.this.mBaseBinding).recyclerview.getNoMore() && RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) ShowsFollowedActivity.this.mBaseBinding).recyclerview) != State.Loading) {
                    RecyclerViewStateUtils.setFooterViewState(ShowsFollowedActivity.this, ((CommonRecyclerviewBinding) ShowsFollowedActivity.this.mBaseBinding).recyclerview, 20, State.Loading, null);
                    ShowsFollowedActivity.this.getData();
                }
            }
        });
        this.mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                HotShowInfo hotShowInfo = (HotShowInfo) ShowsFollowedActivity.this.mList.get(position);
                hotShowInfo.redpoint.set(0);
                Intent intent = new Intent(ShowsFollowedActivity.this, PopularProgramListActivity.class);
                intent.putExtra("hotshowid", hotShowInfo.id);
                intent.putExtra("hot_show_name", hotShowInfo.name);
                ShowsFollowedActivity.this.startActivity(intent);
                Map<String, String> map = new HashMap();
                map.put("position", hotShowInfo.name);
                MobclickAgent.onEvent(ShowsFollowedActivity.this, "program_article_click", map);
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

    private void changeRedDot() {
        if (MessageCountManager.getInstance().getMessagePageTotalCount() <= 0 || !FengApplication.getInstance().isLoginUser()) {
            this.mRootBinding.titleLine.tvMessageCommentNum.setVisibility(8);
            return;
        }
        this.mRootBinding.titleLine.tvMessageCommentNum.setText(FengUtil.numberFormat99(MessageCountManager.getInstance().getMessagePageTotalCount()));
        this.mRootBinding.titleLine.tvMessageCommentNum.setVisibility(0);
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        map.put("page", String.valueOf(this.mPage));
        FengApplication.getInstance().httpRequest("snshotshow/mylist/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (ShowsFollowedActivity.this.mList.size() <= 0) {
                    ShowsFollowedActivity.this.showNetErrorView();
                } else {
                    ShowsFollowedActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
                ((CommonRecyclerviewBinding) ShowsFollowedActivity.this.mBaseBinding).recyclerview.refreshComplete();
            }

            public void onStart() {
            }

            public void onFinish() {
                ((CommonRecyclerviewBinding) ShowsFollowedActivity.this.mBaseBinding).recyclerview.refreshCompleteLetter();
                if (!((CommonRecyclerviewBinding) ShowsFollowedActivity.this.mBaseBinding).recyclerview.getNoMore()) {
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) ShowsFollowedActivity.this.mBaseBinding).recyclerview, State.Normal);
                }
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (ShowsFollowedActivity.this.mList.size() <= 0) {
                    ShowsFollowedActivity.this.showNetErrorView();
                } else {
                    ShowsFollowedActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        JSONObject jsonBody = jsonObject.getJSONObject("body").getJSONObject("hotshow");
                        BaseListModel<HotShowInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(HotShowInfo.class, jsonBody);
                        ShowsFollowedActivity.this.mTotalPage = baseListModel.pagecount;
                        List<HotShowInfo> list = baseListModel.list;
                        ShowsFollowedActivity.this.hideEmptyView();
                        if (ShowsFollowedActivity.this.mPage == 1) {
                            ShowsFollowedActivity.this.mList.clear();
                            ShowsFollowedActivity.this.mList.addAll(list);
                        }
                        ShowsFollowedActivity.this.mPage = ShowsFollowedActivity.this.mPage + 1;
                        if (ShowsFollowedActivity.this.mList.size() <= 0) {
                            ShowsFollowedActivity.this.showEmptyView((int) R.drawable.empty_shows_followed, new OnSingleClickListener() {
                                public void onSingleClick(View v) {
                                    ShowsFollowedActivity.this.startActivity(new Intent(ShowsFollowedActivity.this, AllProgramActivity.class));
                                }
                            });
                        }
                        if (list.size() <= 0 || ShowsFollowedActivity.this.mPage > ShowsFollowedActivity.this.mTotalPage) {
                            RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) ShowsFollowedActivity.this.mBaseBinding).recyclerview, State.TheEndShowBtn);
                            ((CommonRecyclerviewBinding) ShowsFollowedActivity.this.mBaseBinding).recyclerview.setNoMore(true);
                            ((LoadingFooter) ShowsFollowedActivity.this.mLRecyclerViewAdapter.getFooterView()).setCallBack(new LoadingFooter$FooterCallBack() {
                                public void onClick() {
                                    ShowsFollowedActivity.this.startActivity(new Intent(ShowsFollowedActivity.this, AllProgramActivity.class));
                                }
                            });
                        }
                        ShowsFollowedActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                    } else if (ShowsFollowedActivity.this.mList.size() > 0) {
                        FengApplication.getInstance().checkCode("snshotshow/getlist/", code);
                    } else {
                        ShowsFollowedActivity.this.showNetErrorView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (ShowsFollowedActivity.this.mList.size() <= 0) {
                        ShowsFollowedActivity.this.showNetErrorView();
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("snshotshow/getlist/", content, e);
                }
            }
        });
    }

    public int setBaseContentView() {
        return R.layout.common_recyclerview;
    }

    public void showNetErrorView() {
        showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ShowsFollowedActivity.this.getData();
            }
        });
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setIsScrollDown(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageCountRefreshEvent event) {
        if (event != null) {
            changeRedDot();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ProgramFollowEvent event) {
        if (event != null) {
            ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.forceToRefresh();
        }
    }
}
