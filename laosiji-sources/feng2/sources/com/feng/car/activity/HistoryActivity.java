package com.feng.car.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.adapter.CommonPostAdapter.OnCheckItemDeleteListener;
import com.feng.car.databinding.ActivityHistoryBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.SpacesItemDecoration;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class HistoryActivity extends BaseActivity<ActivityHistoryBinding> {
    private int m98;
    private int mCurrentPage = 1;
    private boolean mInSelState;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();
    private Map<Integer, String> mMap = new LinkedHashMap();
    private CommonPostAdapter mPostAdapter;
    private int mTotalPage;

    public int setBaseContentView() {
        return R.layout.activity_history;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.my_historical_record);
        initTitleBarRightText(R.string.edit, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                HistoryActivity.this.showEditState();
            }
        });
        this.m98 = this.mResources.getDimensionPixelSize(R.dimen.default_98PX);
        this.mRootBinding.titleLine.titlebarRightParent.setVisibility(8);
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView.scrollToPosition(0);
            }
        }));
        ((ActivityHistoryBinding) this.mBaseBinding).tvHistoryDeleteBtn.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (v.isSelected() && HistoryActivity.this.mMap.size() != 0) {
                    List<DialogItemEntity> list = new ArrayList();
                    list.add(new DialogItemEntity("确定", false));
                    CommonDialog.showCommonDialog(HistoryActivity.this, HistoryActivity.this.getString(R.string.make_sure_delete_history), list, new OnDialogItemClickListener() {
                        public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                            if (position == 0) {
                                HistoryActivity.this.deleteHistory();
                            }
                        }
                    });
                }
            }
        });
        this.mPostAdapter = new CommonPostAdapter(this, this.mList, 0, true, this.mLogGatherInfo);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mPostAdapter);
        ((ActivityHistoryBinding) this.mBaseBinding).recyclerView.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityHistoryBinding) this.mBaseBinding).recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((ActivityHistoryBinding) this.mBaseBinding).recyclerView.addItemDecoration(new SpacesItemDecoration(this));
        ((ActivityHistoryBinding) this.mBaseBinding).recyclerView.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView, State.Normal);
                HistoryActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                HistoryActivity.this.mCurrentPage = 1;
                ((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView.setNoMore(false);
                HistoryActivity.this.getData(true);
            }
        });
        ((ActivityHistoryBinding) this.mBaseBinding).recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (!((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView.getNoMore() && RecyclerViewStateUtils.getFooterViewState(((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView) != State.Loading) {
                    if (HistoryActivity.this.mCurrentPage <= HistoryActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(HistoryActivity.this, ((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView, 20, State.Loading, null);
                        HistoryActivity.this.getData(false);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(HistoryActivity.this, ((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView, 20, State.TheEnd, null);
                }
            }
        });
        ((ActivityHistoryBinding) this.mBaseBinding).recyclerView.setRefreshing(true);
        ((ActivityHistoryBinding) this.mBaseBinding).recyclerView.setLScrollListener(new LRecyclerView$LScrollListener() {
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
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void getData(final boolean isRefreshing) {
        Map<String, Object> map = new HashMap();
        map.put("page", String.valueOf(this.mCurrentPage));
        FengApplication.getInstance().httpRequest("user/ywf/myhistory/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (HistoryActivity.this.mList.size() > 0) {
                    HistoryActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    HistoryActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            HistoryActivity.this.getData(true);
                        }
                    });
                }
                ((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
                ((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView.refreshComplete();
                if (HistoryActivity.this.mCurrentPage > HistoryActivity.this.mTotalPage) {
                    ((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView.setNoMore(true);
                    RecyclerViewStateUtils.setFooterViewState(((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView, State.TheEndHint);
                    return;
                }
                RecyclerViewStateUtils.setFooterViewState(((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (HistoryActivity.this.mList.size() > 0) {
                    HistoryActivity.this.showThirdTypeToast((int) R.string.check_network);
                } else {
                    HistoryActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            HistoryActivity.this.getData(true);
                        }
                    });
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonThread = jsonResult.getJSONObject("body").getJSONObject("sns");
                        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SnsInfo.class, jsonThread);
                        HistoryActivity.this.mTotalPage = baseListModel.pagecount;
                        List<SnsInfo> list = baseListModel.list;
                        if (list != null && list.size() > 0) {
                            if (isRefreshing) {
                                HistoryActivity.this.mList.clear();
                            }
                            int oldsize = HistoryActivity.this.mList.size();
                            HistoryActivity.this.mList.addAll(list);
                            if (HistoryActivity.this.mCurrentPage == 1 && HistoryActivity.this.mList.size() == 0) {
                                HistoryActivity.this.showEmptyView((int) R.string.history_none, (int) R.drawable.blank_historical_record);
                                HistoryActivity.this.mRootBinding.titleLine.titlebarRightParent.setVisibility(8);
                                return;
                            }
                            HistoryActivity.this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
                            HistoryActivity.this.mCurrentPage = HistoryActivity.this.mCurrentPage + 1;
                            HistoryActivity.this.hideEmptyView();
                            if (isRefreshing) {
                                HistoryActivity.this.mPostAdapter.notifyDataSetChanged();
                                return;
                            } else {
                                HistoryActivity.this.mPostAdapter.notifyItemRangeInserted(oldsize, HistoryActivity.this.mList.size() - oldsize);
                                return;
                            }
                        } else if (HistoryActivity.this.mCurrentPage == 1) {
                            HistoryActivity.this.showEmptyView((int) R.string.history_none, (int) R.drawable.blank_historical_record);
                            HistoryActivity.this.mRootBinding.titleLine.titlebarRightParent.setVisibility(8);
                            return;
                        } else {
                            return;
                        }
                    }
                    FengApplication.getInstance().checkCode("user/ywf/myhistory/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("user/ywf/myhistory/", content, e);
                }
            }
        });
    }

    private void changeEditState() {
        this.mMap.clear();
        this.mInSelState = false;
        ((ActivityHistoryBinding) this.mBaseBinding).recyclerView.setPadding(0, 0, 0, 0);
        this.mPostAdapter.setFromType(0);
        this.mPostAdapter.notifyItemRangeChanged(0, this.mList.size());
        hideDeleteBtnAnimator();
        initTitleBarRightText(R.string.edit, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                HistoryActivity.this.showEditState();
            }
        });
    }

    private void showEditState() {
        this.mInSelState = true;
        ((ActivityHistoryBinding) this.mBaseBinding).tvHistoryDeleteBtn.setSelected(false);
        ((ActivityHistoryBinding) this.mBaseBinding).tvHistoryDeleteBtn.setText(R.string.delete);
        this.mPostAdapter.setFromType(3);
        this.mPostAdapter.notifyItemRangeChanged(0, this.mList.size());
        showDeleteBtnAnimator();
        this.mPostAdapter.setItemDeleteListener(this.mMap, new OnCheckItemDeleteListener() {
            public void onCheckItem(int count) {
                if (count > 0) {
                    ((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).tvHistoryDeleteBtn.setSelected(true);
                    ((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).tvHistoryDeleteBtn.setText("删除(" + count + ")");
                    return;
                }
                ((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).tvHistoryDeleteBtn.setSelected(false);
                ((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).tvHistoryDeleteBtn.setText(R.string.delete);
            }
        });
        initTitleBarRightText(R.string.finish, R.string.all_clear, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                HistoryActivity.this.changeEditState();
            }
        }, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                List<DialogItemEntity> list = new ArrayList();
                list.add(new DialogItemEntity("确定", false));
                CommonDialog.showCommonDialog(HistoryActivity.this, HistoryActivity.this.getString(R.string.make_sure_clear_history), list, new OnDialogItemClickListener() {
                    public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                        if (position == 0) {
                            HistoryActivity.this.deleteAllHistory();
                        }
                    }
                });
            }
        });
    }

    private void showNoHistory() {
        changeEditState();
        this.mRootBinding.titleLine.titlebarRightParent.setVisibility(8);
        showEmptyView((int) R.string.history_none, (int) R.drawable.blank_historical_record);
    }

    private void deleteHistory() {
        Map<String, Object> map = new HashMap();
        List<String> deleteData = new ArrayList();
        for (Integer intValue : this.mMap.keySet()) {
            deleteData.add(intValue.intValue() + "");
        }
        map.put("deldata", deleteData);
        FengApplication.getInstance().httpRequest("user/ywf/delhistory/list/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                HistoryActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
                HistoryActivity.this.showProgress("", HistoryActivity.this.getString(R.string.deleting));
            }

            public void onFinish() {
                HistoryActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                HistoryActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        for (Integer intValue : HistoryActivity.this.mMap.keySet()) {
                            int position = HistoryActivity.this.mList.getPosition((String) HistoryActivity.this.mMap.get(Integer.valueOf(intValue.intValue())));
                            if (position >= 0) {
                                HistoryActivity.this.mList.remove(position);
                            }
                        }
                        if (HistoryActivity.this.mList.size() > 0) {
                            HistoryActivity.this.changeEditState();
                            return;
                        } else {
                            HistoryActivity.this.showNoHistory();
                            return;
                        }
                    }
                    FengApplication.getInstance().checkCode("user/ywf/delhistory/list/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteAllHistory() {
        FengApplication.getInstance().httpRequest("user/ywf/delhistory/all/", new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
                HistoryActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
                HistoryActivity.this.showProgress("", HistoryActivity.this.getString(R.string.deleting));
            }

            public void onFinish() {
                HistoryActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                HistoryActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        HistoryActivity.this.mList.clear();
                        HistoryActivity.this.mPostAdapter.notifyDataSetChanged();
                        HistoryActivity.this.showNoHistory();
                        return;
                    }
                    FengApplication.getInstance().checkCode("user/ywf/delhistory/list/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showDeleteBtnAnimator() {
        if (!((ActivityHistoryBinding) this.mBaseBinding).tvHistoryDeleteBtn.isShown()) {
            ((ActivityHistoryBinding) this.mBaseBinding).tvHistoryDeleteBtn.setVisibility(0);
            ObjectAnimator animator = ObjectAnimator.ofFloat(((ActivityHistoryBinding) this.mBaseBinding).tvHistoryDeleteBtn, "translationY", new float[]{(float) getResources().getDimensionPixelSize(R.dimen.default_98PX), 0.0f});
            animator.setDuration(500);
            animator.start();
            animator.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animation) {
                }

                public void onAnimationEnd(Animator animation) {
                    ((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).recyclerView.setPadding(0, 0, 0, HistoryActivity.this.m98);
                }

                public void onAnimationCancel(Animator animation) {
                }

                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
    }

    private void hideDeleteBtnAnimator() {
        if (((ActivityHistoryBinding) this.mBaseBinding).tvHistoryDeleteBtn.isShown()) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(((ActivityHistoryBinding) this.mBaseBinding).tvHistoryDeleteBtn, "translationY", new float[]{0.0f, (float) getResources().getDimensionPixelSize(R.dimen.default_98PX)});
            animator.setDuration(500);
            animator.start();
            animator.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animation) {
                }

                public void onAnimationEnd(Animator animation) {
                    ((ActivityHistoryBinding) HistoryActivity.this.mBaseBinding).tvHistoryDeleteBtn.setVisibility(8);
                }

                public void onAnimationCancel(Animator animation) {
                }

                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
    }

    public void onBackPressed() {
        if (this.mInSelState) {
            changeEditState();
        } else {
            super.onBackPressed();
        }
    }

    public void loginSuccess() {
        super.loginSuccess();
        ((ActivityHistoryBinding) this.mBaseBinding).recyclerView.forceToRefresh();
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
