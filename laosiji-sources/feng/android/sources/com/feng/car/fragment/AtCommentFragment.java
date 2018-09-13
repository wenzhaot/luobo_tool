package com.feng.car.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.adapter.CommentAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.comment.CommentInfoList;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.event.CommentPageRefreshEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SendCommentStartSlideEvent;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.LogConstans;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
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
import org.json.JSONException;
import org.json.JSONObject;

public class AtCommentFragment extends BaseFragment<CommonRecyclerviewBinding> {
    private CommentAdapter mCommentAdapter;
    private CommentInfoList mCommentInfoList;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int mPage = 1;
    private int mTotalPage = 0;

    public void backToTop() {
        if (this.mBind != null && ((CommonRecyclerviewBinding) this.mBind).recyclerview != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.scrollToPosition(0);
        }
    }

    protected int setLayoutId() {
        return 2130903204;
    }

    protected void initView() {
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(true);
        this.mCommentInfoList = new CommentInfoList();
        this.mCommentAdapter = new CommentAdapter(this.mActivity, this.mCommentInfoList.getCommentList());
        this.mCommentAdapter.setCommentType(2);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mCommentAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new LinearLayoutManager(this.mActivity));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) AtCommentFragment.this.mBind).recyclerview, State.Normal);
                AtCommentFragment.this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
                AtCommentFragment.this.mPage = 1;
                AtCommentFragment.this.getData(true, false);
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) AtCommentFragment.this.mBind).recyclerview) != State.Loading) {
                    if (AtCommentFragment.this.mPage <= AtCommentFragment.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(AtCommentFragment.this.mActivity, ((CommonRecyclerviewBinding) AtCommentFragment.this.mBind).recyclerview, 20, State.Loading, null);
                        AtCommentFragment.this.getData(false, true);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(AtCommentFragment.this.mActivity, ((CommonRecyclerviewBinding) AtCommentFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
                }
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshing(true);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLScrollListener(new LScrollListener() {
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

    private void getData(final boolean needClear, boolean isLoadMoore) {
        if (!isLoadMoore || this.mPage <= this.mTotalPage) {
            Map<String, Object> map = new HashMap();
            map.put(HttpConstant.PAGE, String.valueOf(this.mPage));
            FengApplication.getInstance().httpRequest(HttpConstant.MESSAGE_AT_COMMENT_LIST, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (AtCommentFragment.this.mCommentInfoList.size() > 0) {
                        ((BaseActivity) AtCommentFragment.this.mActivity).showSecondTypeToast(2131231273);
                    } else {
                        AtCommentFragment.this.showNetErrorView();
                    }
                    ((CommonRecyclerviewBinding) AtCommentFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) AtCommentFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onStart() {
                }

                public void onFinish() {
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) AtCommentFragment.this.mBind).recyclerview, State.Normal);
                    ((CommonRecyclerviewBinding) AtCommentFragment.this.mBind).recyclerview.refreshComplete();
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    ((BaseActivity) AtCommentFragment.this.mActivity).showSecondTypeToast(2131231273);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            JSONObject jsonComment = jsonResult.getJSONObject("body").getJSONObject(HttpConstant.COMMENT);
                            BaseListModel<CommentInfo> baseListModel = new BaseListModel();
                            baseListModel.parser(CommentInfo.class, jsonComment);
                            AtCommentFragment.this.mTotalPage = baseListModel.pagecount;
                            List<CommentInfo> list = baseListModel.list;
                            AtCommentFragment.this.hideEmpty();
                            if (list != null && list.size() > 0) {
                                if (needClear) {
                                    AtCommentFragment.this.mCommentInfoList.clear();
                                }
                                AtCommentFragment.this.mCommentInfoList.addAll(list);
                                if (AtCommentFragment.this.mPage == 1 && AtCommentFragment.this.mCommentInfoList.size() <= 0) {
                                    AtCommentFragment.this.showNoDataEmpty();
                                }
                                AtCommentFragment.this.mPage = AtCommentFragment.this.mPage + 1;
                            } else if (AtCommentFragment.this.mPage == 1) {
                                AtCommentFragment.this.showNoDataEmpty();
                            }
                            RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) AtCommentFragment.this.mBind).recyclerview, State.Normal);
                            AtCommentFragment.this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
                            if (needClear) {
                                ((CommonRecyclerviewBinding) AtCommentFragment.this.mBind).recyclerview.smoothScrollToPosition(0);
                            }
                        } else if (AtCommentFragment.this.mCommentInfoList.size() > 0) {
                            FengApplication.getInstance().checkCode(HttpConstant.MESSAGE_MY_COMMENT_LIST, code);
                        } else {
                            AtCommentFragment.this.showNetErrorView();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (AtCommentFragment.this.mCommentInfoList.size() > 0) {
                            ((BaseActivity) AtCommentFragment.this.mActivity).showSecondTypeToast(2131230831);
                        } else {
                            AtCommentFragment.this.showNetErrorView();
                        }
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.MESSAGE_MY_COMMENT_LIST, content, e);
                    }
                }
            });
            return;
        }
        ((BaseActivity) this.mActivity).showThirdTypeToast(2131231189);
    }

    private void showNetErrorView() {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(2130838112);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231188);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AtCommentFragment.this.getData(true, false);
                }
            });
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void showNoDataEmpty() {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.hideEmptyImage();
            ((CommonRecyclerviewBinding) this.mBind).emptyView.hideEmptyButton();
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231226);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void hideEmpty() {
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(8);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CommentPageRefreshEvent event) {
        if (event == null) {
            return;
        }
        if (event.isTop) {
            int position = this.mCommentInfoList.getPosition(event.commentId);
            if (position != -1) {
                CommentInfo commentInfo = this.mCommentInfoList.get(position);
                commentInfo.istop.set(1);
                commentInfo.top.set(event.topNum);
                return;
            }
            return;
        }
        this.mCommentInfoList.get(this.mCommentInfoList.getPosition(event.commentId)).isreply = 1;
        this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setIsScrollDown(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final SendCommentStartSlideEvent event) {
        if (event != null && isAdded() && !TextUtils.isEmpty(event.key) && this.mCommentAdapter != null && this.mCommentAdapter.getSoleKey().equals(event.key)) {
            LinearLayoutManager manager = (LinearLayoutManager) ((CommonRecyclerviewBinding) this.mBind).recyclerview.getLayoutManager();
            int lastItemPosition = manager.findLastVisibleItemPosition();
            if (lastItemPosition >= manager.getItemCount() - 3) {
                int firstItemPosition = manager.findFirstVisibleItemPosition();
                int childSize = (lastItemPosition - firstItemPosition) + 1;
                if (lastItemPosition == firstItemPosition && lastItemPosition == 1 && manager.getChildCount() >= 2) {
                    childSize = 2;
                }
                View view = manager.getChildAt(childSize - 1);
                if (view != null) {
                    if (!LogGatherReadUtil.getInstance().getCurrentPage().equals(LogConstans.APP_SEND_COMMENT)) {
                        view.setPadding(0, 0, 0, 1920);
                    } else if (event.isFinish) {
                        view.setPadding(0, 0, 0, 0);
                    } else {
                        view.setPadding(0, 0, 0, 1920);
                    }
                }
            }
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.postDelayed(new Runnable() {
                public void run() {
                    ((CommonRecyclerviewBinding) AtCommentFragment.this.mBind).recyclerview.smoothScrollBy(0, event.dy);
                }
            }, 100);
        }
    }
}
