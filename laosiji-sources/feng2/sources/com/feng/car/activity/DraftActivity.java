package com.feng.car.activity;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.DraftAdapter;
import com.feng.car.adapter.DraftAdapter.OnDraftItemDeleteListener;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.db.SparkDB;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.drafts.DraftsList;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.event.DraftsRefreshEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.SpacesItemDecoration;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class DraftActivity extends BaseActivity<CommonRecyclerviewBinding> {
    private DraftAdapter mAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private DraftsList mList;
    private Map<String, Integer> mLocalDraftsThreadID = new HashMap();
    private int mPage = 1;
    private SparkDB mSparkDB;
    private int mTotalPage = 0;

    public int setBaseContentView() {
        return R.layout.common_recyclerview;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.draft);
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((CommonRecyclerviewBinding) DraftActivity.this.mBaseBinding).recyclerview.scrollToPosition(0);
            }
        }));
        this.mSparkDB = FengApplication.getInstance().getSparkDB();
        this.mList = new DraftsList();
        this.mLocalDraftsThreadID.clear();
        this.mLocalDraftsThreadID.putAll(this.mSparkDB.getDrafts(FengApplication.getInstance().getUserInfo().id, 1));
        this.mAdapter = new DraftAdapter(this, this.mList.getSnsList());
        this.mAdapter.setOnDraftItemDeleteListener(new OnDraftItemDeleteListener() {
            public void onDraftDel(SnsInfo info, final int position) {
                List<DialogItemEntity> list = new ArrayList();
                list.add(new DialogItemEntity("删除草稿", true));
                CommonDialog.showCommonDialog(DraftActivity.this, "", list, new OnDialogItemClickListener() {
                    public void onItemClick(DialogItemEntity dialogItemEntity, int pos) {
                        DraftActivity.this.deleteThread(DraftActivity.this.mList.get(position), position);
                    }
                });
            }
        });
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.addItemDecoration(new SpacesItemDecoration(this));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        setListener();
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

    private void setListener() {
        this.mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                if (FengApplication.getInstance().getSeverceState()) {
                    Intent intent = new Intent(DraftActivity.this, PostInitActivity.class);
                    intent.putExtra("snsid", DraftActivity.this.mList.get(position).id);
                    intent.putExtra("resourceid", DraftActivity.this.mList.get(position).resourceid);
                    intent.putExtra("from_type_key", 1);
                    DraftActivity.this.startActivity(intent);
                    return;
                }
                DraftActivity.this.showSecondTypeToast((int) R.string.server_maintain);
            }
        });
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) DraftActivity.this.mBaseBinding).recyclerview, State.Normal);
                DraftActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                DraftActivity.this.mPage = 1;
                DraftActivity.this.mLocalDraftsThreadID.clear();
                DraftActivity.this.mLocalDraftsThreadID.putAll(DraftActivity.this.mSparkDB.getDrafts(FengApplication.getInstance().getUserInfo().id, 1));
                DraftActivity.this.getData(true);
            }
        });
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (!RecyclerViewStateUtils.isRecylerViewLoading(((CommonRecyclerviewBinding) DraftActivity.this.mBaseBinding).recyclerview)) {
                    if (DraftActivity.this.mPage > DraftActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(DraftActivity.this, ((CommonRecyclerviewBinding) DraftActivity.this.mBaseBinding).recyclerview, 20, State.TheEnd, null);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(DraftActivity.this, ((CommonRecyclerviewBinding) DraftActivity.this.mBaseBinding).recyclerview, 20, State.Loading, null);
                    DraftActivity.this.getData(false);
                }
            }
        });
    }

    private void getData(final boolean refresh) {
        Map<String, Object> map = new HashMap();
        map.put("page", String.valueOf(this.mPage));
        map.put("resourcetype", String.valueOf(0));
        FengApplication.getInstance().httpRequest("thread/draftlist/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (DraftActivity.this.mList.size() > 0) {
                    DraftActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    DraftActivity.this.showNetErrorView();
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) DraftActivity.this.mBaseBinding).recyclerview, State.Normal);
                ((CommonRecyclerviewBinding) DraftActivity.this.mBaseBinding).recyclerview.refreshComplete();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (DraftActivity.this.mList.size() > 0) {
                    DraftActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    DraftActivity.this.showNetErrorView();
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
                        DraftActivity.this.mTotalPage = baseListModel.pagecount;
                        List<SnsInfo> list = baseListModel.list;
                        DraftActivity.this.hideEmptyView();
                        if (refresh) {
                            DraftActivity.this.mList.clear();
                        }
                        int oldSize = DraftActivity.this.mList.size();
                        DraftActivity.this.mList.addAll(list);
                        if (DraftActivity.this.mPage == 1 && DraftActivity.this.mList.size() <= 0) {
                            DraftActivity.this.showNoDataEmpty();
                        }
                        DraftActivity.this.mPage = DraftActivity.this.mPage + 1;
                        if (refresh) {
                            DraftActivity.this.mAdapter.notifyDataSetChanged();
                            return;
                        } else {
                            DraftActivity.this.mAdapter.notifyItemRangeInserted(oldSize, DraftActivity.this.mList.size() - oldSize);
                            return;
                        }
                    }
                    if (DraftActivity.this.mList.size() > 0) {
                        DraftActivity.this.showSecondTypeToast((int) R.string.loaddata_failed);
                    } else {
                        DraftActivity.this.showNetErrorView();
                    }
                    FengApplication.getInstance().upLoadLog(true, "thread/draftlist/  " + code);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (DraftActivity.this.mList.size() > 0) {
                        DraftActivity.this.showSecondTypeToast((int) R.string.loaddata_failed);
                    } else {
                        DraftActivity.this.showNetErrorView();
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("thread/draftlist/", content, e);
                }
            }
        });
    }

    private void deleteThread(final SnsInfo snsInfo, final int position) {
        if (FengApplication.getInstance().getSeverceState()) {
            Map<String, Object> map = new HashMap();
            map.put("resourceid", String.valueOf(snsInfo.resourceid));
            map.put("resourcetype", String.valueOf(snsInfo.snstype));
            map.put(NotificationCompat.CATEGORY_STATUS, String.valueOf(-1));
            FengApplication.getInstance().httpRequest("sns/delete/", map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    DraftActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }

                public void onStart() {
                }

                public void onFinish() {
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    DraftActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        int code = new JSONObject(content).getInt("code");
                        if (code == 1) {
                            DraftActivity.this.showFirstTypeToast((int) R.string.delete_myarticle_success);
                            DraftActivity.this.mList.remove(position);
                            DraftActivity.this.mSparkDB.delDrafts(snsInfo.resourceid + "_" + snsInfo.snstype, FengApplication.getInstance().getUserInfo().id, 1);
                            DraftActivity.this.mAdapter.removeNotifyItem(position);
                            if (DraftActivity.this.mList.size() == 0) {
                                DraftActivity.this.showNoDataEmpty();
                                return;
                            }
                            return;
                        }
                        FengApplication.getInstance().checkCode("sns/delete/", code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        DraftActivity.this.showSecondTypeToast((int) R.string.delete_drafts_failed);
                        FengApplication.getInstance().upLoadTryCatchLog("sns/delete/", content, e);
                    }
                }
            });
            return;
        }
        showSecondTypeToast((int) R.string.server_maintain);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshDrafts(DraftsRefreshEvent event) {
        if (event != null) {
            SnsInfo info;
            int size;
            int i;
            switch (event.type) {
                case 1:
                    int pos = 0;
                    if (event.snsInfo != null) {
                        pos = this.mList.getPosition(event.snsInfo.resourceid);
                        if (pos >= 0) {
                            info = this.mList.get(pos);
                            if (info.resourceid == event.snsInfo.resourceid) {
                                info.title.set(event.snsInfo.title.get());
                                info.description.set(event.snsInfo.description.get());
                                info.modifytime.set(event.snsInfo.modifytime.get());
                                info.image.url = event.snsInfo.image.url;
                                info.image.width = event.snsInfo.image.width;
                                info.image.height = event.snsInfo.image.height;
                                info.image.description = event.snsInfo.image.description;
                            }
                        }
                    }
                    if (this.mLocalDraftsThreadID.containsKey(event.threadID)) {
                        this.mLocalDraftsThreadID.remove(event.threadID);
                        this.mSparkDB.delDrafts(event.threadID, FengApplication.getInstance().getUserInfo().id, 1);
                    }
                    this.mAdapter.notifyItemChanged(pos);
                    return;
                case 2:
                    size = this.mList.size();
                    for (i = 0; i < size; i++) {
                        info = this.mList.get(i);
                        if ((info.resourceid + "_" + info.snstype).equals(event.threadID)) {
                            this.mList.remove(info);
                            if (this.mLocalDraftsThreadID.containsKey(event.threadID)) {
                                this.mLocalDraftsThreadID.remove(event.threadID);
                                this.mSparkDB.delDrafts(event.threadID, FengApplication.getInstance().getUserInfo().id, 1);
                            }
                            this.mAdapter.removeNotifyItem(i);
                            if (this.mList.size() == 0) {
                                showNoDataEmpty();
                                return;
                            }
                            return;
                        }
                    }
                    return;
                case 3:
                    this.mLocalDraftsThreadID.clear();
                    this.mLocalDraftsThreadID.putAll(this.mSparkDB.getDrafts(FengApplication.getInstance().getUserInfo().id, 1));
                    this.mLRecyclerViewAdapter.notifyDataSetChanged();
                    return;
                case 4:
                    size = this.mList.size();
                    for (i = 0; i < size; i++) {
                        info = this.mList.get(i);
                        if ((info.resourceid + "_" + info.snstype).equals(event.threadID)) {
                            this.mList.remove(info);
                            if (this.mLocalDraftsThreadID.containsKey(event.threadID)) {
                                this.mLocalDraftsThreadID.remove(event.threadID);
                                this.mSparkDB.delDrafts(event.threadID, FengApplication.getInstance().getUserInfo().id, 1);
                            }
                            this.mAdapter.removeNotifyItem(i);
                            if (this.mList.size() == 0) {
                                showNoDataEmpty();
                                return;
                            }
                            return;
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void showNetErrorView() {
        if (((CommonRecyclerviewBinding) this.mBaseBinding).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBaseBinding).emptyView.setEmptyImage(R.drawable.icon_load_faile);
            ((CommonRecyclerviewBinding) this.mBaseBinding).emptyView.setEmptyText(R.string.load_faile);
            ((CommonRecyclerviewBinding) this.mBaseBinding).emptyView.setButtonListener(R.string.reload, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    DraftActivity.this.getData(true);
                }
            });
            ((CommonRecyclerviewBinding) this.mBaseBinding).emptyView.setVisibility(0);
        }
    }

    public void showNoDataEmpty() {
        if (((CommonRecyclerviewBinding) this.mBaseBinding).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBaseBinding).emptyView.setEmptyText(R.string.draft_empty_tips);
            ((CommonRecyclerviewBinding) this.mBaseBinding).emptyView.setEmptyImage(R.drawable.blank_draft);
            ((CommonRecyclerviewBinding) this.mBaseBinding).emptyView.hideEmptyButton();
            ((CommonRecyclerviewBinding) this.mBaseBinding).emptyView.setVisibility(0);
        }
    }

    public void hideEmptyView() {
        ((CommonRecyclerviewBinding) this.mBaseBinding).emptyView.setVisibility(8);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setIsScrollDown(true);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
