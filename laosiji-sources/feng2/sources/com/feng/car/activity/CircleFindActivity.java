package com.feng.car.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CircleAccededAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.entity.circle.CircleInfoList;
import com.feng.car.event.AddCircleEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.umeng.analytics.MobclickAgent;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class CircleFindActivity extends BaseActivity<CommonRecyclerviewBinding> {
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private CircleInfoList mList = new CircleInfoList();
    private int mTotalPage;

    public static CircleFindActivity newInstance() {
        return new CircleFindActivity();
    }

    public void initView() {
        initCircleSearchTitleBar(R.string.search_circle, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(CircleFindActivity.this, SearchNewActivity.class);
                intent.putExtra("type", 5);
                CircleFindActivity.this.startActivity(intent);
                CircleFindActivity.this.overridePendingTransition(R.anim.fade_in, 0);
                MobclickAgent.onEvent(CircleFindActivity.this, "forum_my_seach");
            }
        });
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(new CircleAccededAdapter((Context) this, this.mList.getCircleList(), CircleAccededAdapter.ALL_TYPE));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CircleFindActivity.this.mBaseBinding).recyclerview, State.Normal);
                CircleFindActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                CircleFindActivity.this.mCurrentPage = 1;
                CircleFindActivity.this.getData();
            }
        });
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) CircleFindActivity.this.mBaseBinding).recyclerview) != State.Loading) {
                    if (CircleFindActivity.this.mCurrentPage <= CircleFindActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(CircleFindActivity.this, ((CommonRecyclerviewBinding) CircleFindActivity.this.mBaseBinding).recyclerview, 20, State.Loading, null);
                        CircleFindActivity.this.getData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(CircleFindActivity.this, ((CommonRecyclerviewBinding) CircleFindActivity.this.mBaseBinding).recyclerview, 20, State.TheEnd, null);
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
        this.mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                CircleFindActivity.this.mList.get(position).intentToCircleFinalPage(CircleFindActivity.this);
            }
        });
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        map.put("type", String.valueOf(0));
        map.put("page", String.valueOf(this.mCurrentPage));
        FengApplication.getInstance().httpRequest("community/getlist/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (CircleFindActivity.this.mList.size() > 0) {
                    CircleFindActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    CircleFindActivity.this.showNetErrorView();
                }
                ((CommonRecyclerviewBinding) CircleFindActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CircleFindActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
                ((CommonRecyclerviewBinding) CircleFindActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) CircleFindActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (CircleFindActivity.this.mList.size() > 0) {
                    CircleFindActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    CircleFindActivity.this.showNetErrorView();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject object = new JSONObject(content);
                    if (object.getInt("code") == 1) {
                        JSONObject jsonTag = object.getJSONObject("body").getJSONObject("community");
                        BaseListModel<CircleInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CircleInfo.class, jsonTag);
                        CircleFindActivity.this.mTotalPage = baseListModel.pagecount;
                        CircleFindActivity.this.hideEmptyView();
                        if (CircleFindActivity.this.mCurrentPage == 1) {
                            CircleFindActivity.this.mList.clear();
                        }
                        CircleFindActivity.this.mList.addAll(baseListModel.list);
                        CircleFindActivity.this.mCurrentPage = CircleFindActivity.this.mCurrentPage + 1;
                        CircleFindActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                    } else if (CircleFindActivity.this.mList.size() > 0) {
                        CircleFindActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        CircleFindActivity.this.showNetErrorView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showNetErrorView() {
        showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((CommonRecyclerviewBinding) CircleFindActivity.this.mBaseBinding).recyclerview.forceToRefresh();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(AddCircleEvent event) {
        if (event != null && event.type == AddCircleEvent.ATTENTION_CANCEL_CIRCLE_TYPE) {
            int position = this.mList.getPosition(event.info.id);
            if (position > 0) {
                this.mList.get(position).isfans.set(event.info.isfans.get());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setIsScrollDown(true);
        }
    }

    public void loginSuccess() {
        super.loginSuccess();
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.forceToRefresh();
    }

    public int setBaseContentView() {
        return R.layout.common_recyclerview;
    }
}
