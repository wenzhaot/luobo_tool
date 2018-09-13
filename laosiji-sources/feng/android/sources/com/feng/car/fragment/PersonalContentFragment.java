package com.feng.car.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.LoginActivity;
import com.feng.car.activity.PostInitActivity;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.CircleFragmentRefreshFinishEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SendDelArticleSuccess;
import com.feng.car.event.SnsInfoModifyEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.utils.HttpConstant;
import com.feng.car.view.SpacesItemDecoration;
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

public class PersonalContentFragment extends BaseFragment<CommonRecyclerviewBinding> {
    public static int LIKE_TYPE = 2002;
    public static int POST_TYPE = 2001;
    private CommonPostAdapter mCommonPostAdapter;
    private int mCurrentPage = 1;
    private boolean mIsMine = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();
    private int mTotalPage = 0;
    private int mType;
    private int mUserId;

    protected int setLayoutId() {
        return 2130903204;
    }

    public static PersonalContentFragment newInstance(int userID, boolean isMine, int pageType) {
        Bundle arg = new Bundle();
        arg.putInt("userid", userID);
        arg.putInt("type", pageType);
        arg.putBoolean("ISMINE", isMine);
        PersonalContentFragment fragment = new PersonalContentFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mUserId = getArguments().getInt("userid");
        this.mType = getArguments().getInt("type");
        this.mIsMine = getArguments().getBoolean("ISMINE");
    }

    protected void initView() {
        this.mCommonPostAdapter = new CommonPostAdapter(this.mActivity, this.mList, 0, true, getLogGatherInfo());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mCommonPostAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.addItemDecoration(new SpacesItemDecoration(this.mActivity));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        if (!this.mIsMine) {
            ((CommonRecyclerviewBinding) this.mBind).llParent.setPadding(0, 0, 0, getResources().getDimensionPixelSize(2131296871));
        }
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(false);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) PersonalContentFragment.this.mBind).recyclerview, State.Normal);
                PersonalContentFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                PersonalContentFragment.this.mCurrentPage = 1;
                PersonalContentFragment.this.loadUserArticleData(true);
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshing(true);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) PersonalContentFragment.this.mBind).recyclerview) != State.Loading) {
                    if (PersonalContentFragment.this.mCurrentPage > PersonalContentFragment.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(PersonalContentFragment.this.mActivity, ((CommonRecyclerviewBinding) PersonalContentFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(PersonalContentFragment.this.mActivity, ((CommonRecyclerviewBinding) PersonalContentFragment.this.mBind).recyclerview, 20, State.Loading, null);
                    PersonalContentFragment.this.loadUserArticleData(false);
                }
            }
        });
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

    protected void onFragmentFirstVisible() {
        if (this.mBind != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(true);
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    public void loadUserArticleData(final boolean isRefresh) {
        String api;
        if (isRefresh) {
            this.mCurrentPage = 1;
        }
        Map<String, Object> map = new HashMap();
        map.put("userid", String.valueOf(this.mUserId));
        map.put(HttpConstant.PAGE, String.valueOf(this.mCurrentPage));
        if (this.mType == POST_TYPE) {
            api = HttpConstant.SNS_THREADLIST;
        } else {
            api = HttpConstant.USER_LIKE;
        }
        FengApplication.getInstance().httpRequest(api, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((CommonRecyclerviewBinding) PersonalContentFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) PersonalContentFragment.this.mBind).recyclerview, State.Normal);
                EventBus.getDefault().post(new CircleFragmentRefreshFinishEvent());
                if (PersonalContentFragment.this.mList.size() <= 0) {
                    PersonalContentFragment.this.showEmptyViewFailed();
                } else {
                    ((BaseActivity) PersonalContentFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                if (PersonalContentFragment.this.mBind != null) {
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) PersonalContentFragment.this.mBind).recyclerview, State.Normal);
                    ((CommonRecyclerviewBinding) PersonalContentFragment.this.mBind).recyclerview.refreshComplete();
                }
                EventBus.getDefault().post(new CircleFragmentRefreshFinishEvent());
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (PersonalContentFragment.this.mList.size() <= 0) {
                    PersonalContentFragment.this.showEmptyViewFailed();
                } else {
                    ((BaseActivity) PersonalContentFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        JSONObject threadJson;
                        PersonalContentFragment.this.hideEmptyView();
                        ((CommonRecyclerviewBinding) PersonalContentFragment.this.mBind).recyclerview.setPullRefreshEnabled(false);
                        JSONObject bodyJson = jsonObject.getJSONObject("body");
                        if (PersonalContentFragment.this.mType == PersonalContentFragment.LIKE_TYPE) {
                            threadJson = bodyJson.getJSONObject(HttpConstant.LIKE);
                        } else {
                            threadJson = bodyJson.getJSONObject("sns");
                        }
                        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SnsInfo.class, threadJson);
                        PersonalContentFragment.this.mTotalPage = baseListModel.pagecount;
                        if (isRefresh) {
                            ((CommonRecyclerviewBinding) PersonalContentFragment.this.mBind).recyclerview.setNoMore(false);
                            PersonalContentFragment.this.mList.clear();
                        }
                        List<SnsInfo> list = baseListModel.list;
                        int oldSize = PersonalContentFragment.this.mList.size();
                        if (list.size() > 0) {
                            PersonalContentFragment.this.mList.addAll(list);
                            if (PersonalContentFragment.this.mList.size() == 0) {
                                PersonalContentFragment.this.showBlankView();
                            } else {
                                PersonalContentFragment.this.hideBlankView();
                            }
                            PersonalContentFragment.this.mCurrentPage = PersonalContentFragment.this.mCurrentPage + 1;
                            if (isRefresh) {
                                PersonalContentFragment.this.mCommonPostAdapter.notifyDataSetChanged();
                            } else {
                                PersonalContentFragment.this.mCommonPostAdapter.notifyItemRangeInserted(oldSize, PersonalContentFragment.this.mList.size() - oldSize);
                            }
                        } else if (PersonalContentFragment.this.mCurrentPage == 1) {
                            PersonalContentFragment.this.showBlankView();
                        }
                    } else if (PersonalContentFragment.this.mList.size() <= 0) {
                        PersonalContentFragment.this.showEmptyViewFailed();
                    } else {
                        FengApplication.getInstance().checkCode(api, code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showBlankView() {
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setVisibility(8);
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        if (this.mType == LIKE_TYPE) {
            if (this.mIsMine) {
                showEmptyImage(2130838310);
            } else {
                showEmptyImage(2130838311);
            }
        } else if (this.mIsMine) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231329);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(2130837956);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setButtonListener(2131231074, new OnSingleClickListener() {
                public void onSingleClick(View view) {
                    if (!FengApplication.getInstance().getSeverceState()) {
                        ((BaseActivity) PersonalContentFragment.this.mActivity).showSecondTypeToast(2131231540);
                    } else if (FengApplication.getInstance().isLoginUser()) {
                        PersonalContentFragment.this.startActivity(new Intent(PersonalContentFragment.this.mActivity, PostInitActivity.class));
                    } else {
                        PersonalContentFragment.this.startActivity(new Intent(PersonalContentFragment.this.mActivity, LoginActivity.class));
                    }
                }
            });
        } else {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(2130837956);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231330);
        }
    }

    private void showEmptyImage(int drawableID) {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.hideEmptyText();
            ((CommonRecyclerviewBinding) this.mBind).emptyView.hideEmptyButton();
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(drawableID);
        }
    }

    private void hideBlankView() {
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(8);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setVisibility(0);
    }

    private void hideEmptyView() {
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(8);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setVisibility(0);
    }

    private void showEmptyViewFailed() {
        if (this.mBind != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setVisibility(8);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231188);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(2130838112);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    PersonalContentFragment.this.loadUserArticleData(true);
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoModifyEvent event) {
        if (event != null) {
            this.mCommonPostAdapter.modifySnsInfo(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event == null) {
            return;
        }
        if (event.refreshType != 2005) {
            this.mCommonPostAdapter.refreshSnsInfo(event);
        } else if (!this.mIsMine) {
        } else {
            if (event.deleterefresh) {
                this.mCommonPostAdapter.modifySnsInfo(new SnsInfoModifyEvent(event.snsInfo));
                return;
            }
            SnsInfo snsInfo = event.snsInfo;
            if (snsInfo != null) {
                int pos = this.mList.getPosition(snsInfo.getLocalkey());
                if (pos != -1) {
                    this.mList.remove(pos);
                    this.mCommonPostAdapter.removeNotifyItem(pos);
                    if (this.mList.size() == 0) {
                        showBlankView();
                    } else {
                        hideBlankView();
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SendDelArticleSuccess event) {
        if (event == null) {
            return;
        }
        if (event.type == 1) {
            if (this.mIsMine) {
                this.mList.add(0, event.snsInfo);
                hideBlankView();
                this.mCommonPostAdapter.insertedNotifyItem(0);
            }
        } else if (event.type == 2 && this.mIsMine) {
            int pos = this.mList.getPosition(event.snsInfo.getLocalkey());
            if (pos != -1 && this.mList.get(pos).id == event.snsInfo.id) {
                this.mList.remove(pos);
                this.mCommonPostAdapter.removeNotifyItem(pos);
                if (this.mList.size() == 0) {
                    showBlankView();
                } else {
                    hideBlankView();
                }
            }
        }
    }
}
