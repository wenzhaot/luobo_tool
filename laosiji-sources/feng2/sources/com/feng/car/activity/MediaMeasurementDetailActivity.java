package com.feng.car.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.databinding.ActivityMediaMeasurementDetailBinding;
import com.feng.car.databinding.MediaMeasureHeaderLayoutBinding;
import com.feng.car.databinding.PopularProgramListArraydialogBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.SpacesItemDecoration;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LRecyclerViewOnToucheListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
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

public class MediaMeasurementDetailActivity extends VideoBaseActivity<ActivityMediaMeasurementDetailBinding> implements LRecyclerView$LRecyclerViewOnToucheListener {
    public static final String MEDIA_MEASURE_CAR_ID = "media_measure_id";
    public static final String MEDIA_MEASURE_CAR_NAME = "media_measure_name";
    public static final int SORT_COMMENT_COUNT = 2;
    public static final int SORT_PUBLISH_TIME = 0;
    public static final int SORT_READ_COUNT = 1;
    private int m220;
    private int m250;
    private int m8;
    private CommonPostAdapter mAdater;
    private int mCurrentHotShowSort = 0;
    private int mCurrentPage = 1;
    private int mIsRemind = 0;
    private boolean mIsSetNotification = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();
    private String mMeasureCarName = "";
    private MediaMeasureHeaderLayoutBinding mMeasureHeaderLayoutBinding;
    private int mMediaMeasureId = -1;
    private PopularProgramListArraydialogBinding mSortArrayDialogBinding;
    private PopupWindow mSortArrayWindow;
    private int mTotalPage;

    public void onActionMove(float mLastY, float mCurrentY) {
    }

    public void onActionCancel() {
    }

    public int setBaseContentView() {
        return R.layout.activity_media_measurement_detail;
    }

    public void getLocalIntentData() {
        this.mMediaMeasureId = getIntent().getIntExtra(MEDIA_MEASURE_CAR_ID, -1);
        this.mMeasureCarName = getIntent().getStringExtra(MEDIA_MEASURE_CAR_NAME);
    }

    public void initView() {
        initNormalTitleBar(this.mMeasureCarName + " " + this.mResources.getString(R.string.media_measurement_tips));
        initTitleBarRightPk();
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((ActivityMediaMeasurementDetailBinding) MediaMeasurementDetailActivity.this.mBaseBinding).rcvMediaMeasurementList.scrollToPosition(0);
            }
        }));
        this.m8 = this.mResources.getDimensionPixelSize(R.dimen.default_8PX);
        this.m220 = this.mResources.getDimensionPixelSize(R.dimen.default_220PX);
        this.m250 = this.mResources.getDimensionPixelSize(R.dimen.default_250PX);
        this.mMeasureHeaderLayoutBinding = MediaMeasureHeaderLayoutBinding.inflate(LayoutInflater.from(this));
        this.mAdater = new CommonPostAdapter(this, this.mList, 0, true, this.mLogGatherInfo);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdater);
        ((ActivityMediaMeasurementDetailBinding) this.mBaseBinding).rcvMediaMeasurementList.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityMediaMeasurementDetailBinding) this.mBaseBinding).rcvMediaMeasurementList.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((ActivityMediaMeasurementDetailBinding) this.mBaseBinding).rcvMediaMeasurementList.addItemDecoration(new SpacesItemDecoration(this));
        ((ActivityMediaMeasurementDetailBinding) this.mBaseBinding).rcvMediaMeasurementList.setRefreshProgressStyle(2);
        ((ActivityMediaMeasurementDetailBinding) this.mBaseBinding).rcvMediaMeasurementList.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityMediaMeasurementDetailBinding) MediaMeasurementDetailActivity.this.mBaseBinding).rcvMediaMeasurementList, State.Normal);
                MediaMeasurementDetailActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                MediaMeasurementDetailActivity.this.mCurrentPage = 1;
                MediaMeasurementDetailActivity.this.getData(true);
            }
        });
        ((ActivityMediaMeasurementDetailBinding) this.mBaseBinding).rcvMediaMeasurementList.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((ActivityMediaMeasurementDetailBinding) MediaMeasurementDetailActivity.this.mBaseBinding).rcvMediaMeasurementList) != State.Loading) {
                    if (MediaMeasurementDetailActivity.this.mCurrentPage <= MediaMeasurementDetailActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(MediaMeasurementDetailActivity.this, ((ActivityMediaMeasurementDetailBinding) MediaMeasurementDetailActivity.this.mBaseBinding).rcvMediaMeasurementList, 20, State.Loading, null);
                        MediaMeasurementDetailActivity.this.getData(false);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(MediaMeasurementDetailActivity.this, ((ActivityMediaMeasurementDetailBinding) MediaMeasurementDetailActivity.this.mBaseBinding).rcvMediaMeasurementList, 20, State.TheEnd, null);
                }
            }
        });
        ((ActivityMediaMeasurementDetailBinding) this.mBaseBinding).rcvMediaMeasurementList.setLScrollListener(new LRecyclerView$LScrollListener() {
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
        this.mMeasureHeaderLayoutBinding.tvSortType.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MediaMeasurementDetailActivity.this.showArraryDialog(v);
            }
        });
        this.mMeasureHeaderLayoutBinding.ivUpdateRemind.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (MediaMeasurementDetailActivity.this.mIsRemind == 1) {
                    MediaMeasurementDetailActivity.this.showCloseRemindDialog();
                    MobclickAgent.onEvent(MediaMeasurementDetailActivity.this, "car_guide_remind_cancel");
                    return;
                }
                MediaMeasurementDetailActivity.this.showOpenRemindDialog();
                MobclickAgent.onEvent(MediaMeasurementDetailActivity.this, "car_guide_remind");
            }
        });
        this.mLRecyclerViewAdapter.addHeaderView(this.mMeasureHeaderLayoutBinding.getRoot());
        ((ActivityMediaMeasurementDetailBinding) this.mBaseBinding).rcvMediaMeasurementList.setRefreshing(true);
    }

    protected void onResume() {
        super.onResume();
        if (this.mIsSetNotification) {
            if (FengUtil.isNotificationAuthorityEnabled(this)) {
                setRemind(1);
            }
            this.mIsSetNotification = false;
        }
    }

    private void getData(final boolean needClear) {
        Map<String, Object> map = new HashMap();
        map.put("carsid", String.valueOf(this.mMediaMeasureId));
        map.put("page", String.valueOf(this.mCurrentPage));
        map.put("type", String.valueOf(this.mCurrentHotShowSort));
        FengApplication.getInstance().httpRequest("car/autoguide/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (MediaMeasurementDetailActivity.this.mList.size() <= 0) {
                    MediaMeasurementDetailActivity.this.showErrorView();
                } else {
                    MediaMeasurementDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                ((ActivityMediaMeasurementDetailBinding) MediaMeasurementDetailActivity.this.mBaseBinding).rcvMediaMeasurementList.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivityMediaMeasurementDetailBinding) MediaMeasurementDetailActivity.this.mBaseBinding).rcvMediaMeasurementList, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (MediaMeasurementDetailActivity.this.mList.size() <= 0) {
                    MediaMeasurementDetailActivity.this.showErrorView();
                } else {
                    MediaMeasurementDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        MediaMeasurementDetailActivity.this.mIsRemind = jsonBody.getInt("isremind");
                        JSONObject jsonThread = jsonBody.getJSONObject("guides");
                        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SnsInfo.class, jsonThread);
                        MediaMeasurementDetailActivity.this.mTotalPage = baseListModel.pagecount;
                        List<SnsInfo> list = baseListModel.list;
                        MediaMeasurementDetailActivity.this.hideEmptyView();
                        if (list != null && list.size() > 0) {
                            if (needClear) {
                                MediaMeasurementDetailActivity.this.mList.clear();
                            }
                            int oloSize = MediaMeasurementDetailActivity.this.mList.size();
                            MediaMeasurementDetailActivity.this.mList.addAll(list);
                            if (MediaMeasurementDetailActivity.this.mList.size() > 0) {
                                MediaMeasurementDetailActivity.this.mCurrentPage = MediaMeasurementDetailActivity.this.mCurrentPage + 1;
                                MediaMeasurementDetailActivity.this.mMeasureHeaderLayoutBinding.mediaMeasureHeaderContainer.setVisibility(0);
                            }
                            if (needClear) {
                                MediaMeasurementDetailActivity.this.mAdater.notifyDataSetChanged();
                            } else {
                                MediaMeasurementDetailActivity.this.mAdater.notifyItemRangeInserted(oloSize, MediaMeasurementDetailActivity.this.mList.size() - oloSize);
                            }
                        } else if (needClear) {
                            MediaMeasurementDetailActivity.this.showEmptyView((int) R.string.media_measurement_empty_tips, (int) R.drawable.icon_article_delete);
                        }
                        MediaMeasurementDetailActivity.this.changeRemind();
                    } else if (MediaMeasurementDetailActivity.this.mList.size() <= 0) {
                        MediaMeasurementDetailActivity.this.showErrorView();
                    } else {
                        FengApplication.getInstance().checkCode("car/autoguide/", code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (MediaMeasurementDetailActivity.this.mList.size() <= 0) {
                        MediaMeasurementDetailActivity.this.showErrorView();
                    } else {
                        FengApplication.getInstance().upLoadTryCatchLog("car/autoguide/", content, e);
                    }
                }
            }
        });
    }

    public void showErrorView() {
        showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                MediaMeasurementDetailActivity.this.getData(true);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((ActivityMediaMeasurementDetailBinding) this.mBaseBinding).rcvMediaMeasurementList.setIsScrollDown(true);
        }
    }

    private void showArraryDialog(View view) {
        if (this.mSortArrayDialogBinding == null) {
            this.mSortArrayDialogBinding = PopularProgramListArraydialogBinding.inflate(LayoutInflater.from(this));
            this.mSortArrayDialogBinding.arrayTime.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (MediaMeasurementDetailActivity.this.mCurrentHotShowSort != 0) {
                        MediaMeasurementDetailActivity.this.mCurrentHotShowSort = 0;
                        MediaMeasurementDetailActivity.this.mMeasureHeaderLayoutBinding.tvSortType.setText(R.string.comment_timearray);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayTime.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayTime.setTextColor(MediaMeasurementDetailActivity.this.mResources.getColor(R.color.color_ffffff));
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayCommentCount.setBackgroundResource(17170445);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayCommentCount.setTextColor(MediaMeasurementDetailActivity.this.mResources.getColor(R.color.color_000000));
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayReadCount.setBackgroundResource(17170445);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayReadCount.setTextColor(MediaMeasurementDetailActivity.this.mResources.getColor(R.color.color_000000));
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayTime.setPadding(MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayCommentCount.setPadding(MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayReadCount.setPadding(MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8);
                        MediaMeasurementDetailActivity.this.mCurrentPage = 1;
                        MediaMeasurementDetailActivity.this.getData(true);
                        Map<String, String> map = new HashMap();
                        map.put("position", "index_" + MediaMeasurementDetailActivity.this.mCurrentHotShowSort);
                        MobclickAgent.onEvent(MediaMeasurementDetailActivity.this, "program_sort", map);
                    }
                    MediaMeasurementDetailActivity.this.hideCommentArrayWindow();
                }
            });
            this.mSortArrayDialogBinding.arrayCommentCount.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (MediaMeasurementDetailActivity.this.mCurrentHotShowSort != 2) {
                        MediaMeasurementDetailActivity.this.mCurrentHotShowSort = 2;
                        MediaMeasurementDetailActivity.this.mMeasureHeaderLayoutBinding.tvSortType.setText(R.string.sort_comment_count_array);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayTime.setBackgroundResource(17170445);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayTime.setTextColor(MediaMeasurementDetailActivity.this.mResources.getColor(R.color.color_000000));
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayCommentCount.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayCommentCount.setTextColor(MediaMeasurementDetailActivity.this.mResources.getColor(R.color.color_ffffff));
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayReadCount.setBackgroundResource(17170445);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayReadCount.setTextColor(MediaMeasurementDetailActivity.this.mResources.getColor(R.color.color_000000));
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayTime.setPadding(MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayCommentCount.setPadding(MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayReadCount.setPadding(MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8);
                        MediaMeasurementDetailActivity.this.mCurrentPage = 1;
                        MediaMeasurementDetailActivity.this.getData(true);
                        Map<String, String> map = new HashMap();
                        map.put("position", "index_" + MediaMeasurementDetailActivity.this.mCurrentHotShowSort);
                        MobclickAgent.onEvent(MediaMeasurementDetailActivity.this, "program_sort", map);
                    }
                    MediaMeasurementDetailActivity.this.hideCommentArrayWindow();
                }
            });
            this.mSortArrayDialogBinding.arrayReadCount.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (MediaMeasurementDetailActivity.this.mCurrentHotShowSort != 1) {
                        MediaMeasurementDetailActivity.this.mCurrentHotShowSort = 1;
                        MediaMeasurementDetailActivity.this.mMeasureHeaderLayoutBinding.tvSortType.setText(R.string.sort_read_count_array);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayTime.setBackgroundResource(17170445);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayTime.setTextColor(MediaMeasurementDetailActivity.this.mResources.getColor(R.color.color_000000));
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayCommentCount.setBackgroundResource(17170445);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayCommentCount.setTextColor(MediaMeasurementDetailActivity.this.mResources.getColor(R.color.color_000000));
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayReadCount.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayReadCount.setTextColor(MediaMeasurementDetailActivity.this.mResources.getColor(R.color.color_ffffff));
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayTime.setPadding(MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayCommentCount.setPadding(MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8);
                        MediaMeasurementDetailActivity.this.mSortArrayDialogBinding.arrayReadCount.setPadding(MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8, MediaMeasurementDetailActivity.this.m8);
                        MediaMeasurementDetailActivity.this.mCurrentPage = 1;
                        MediaMeasurementDetailActivity.this.getData(true);
                        Map<String, String> map = new HashMap();
                        map.put("position", "index_" + MediaMeasurementDetailActivity.this.mCurrentHotShowSort);
                        MobclickAgent.onEvent(MediaMeasurementDetailActivity.this, "program_sort", map);
                    }
                    MediaMeasurementDetailActivity.this.hideCommentArrayWindow();
                }
            });
        }
        if (this.mCurrentHotShowSort == 0) {
            this.mSortArrayDialogBinding.arrayTime.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mSortArrayDialogBinding.arrayTime.setTextColor(this.mResources.getColor(R.color.color_ffffff));
            this.mSortArrayDialogBinding.arrayCommentCount.setBackgroundResource(17170445);
            this.mSortArrayDialogBinding.arrayCommentCount.setTextColor(this.mResources.getColor(R.color.color_000000));
            this.mSortArrayDialogBinding.arrayReadCount.setBackgroundResource(17170445);
            this.mSortArrayDialogBinding.arrayReadCount.setTextColor(this.mResources.getColor(R.color.color_000000));
        } else if (this.mCurrentHotShowSort == 2) {
            this.mSortArrayDialogBinding.arrayTime.setBackgroundResource(17170445);
            this.mSortArrayDialogBinding.arrayTime.setTextColor(this.mResources.getColor(R.color.color_000000));
            this.mSortArrayDialogBinding.arrayCommentCount.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mSortArrayDialogBinding.arrayCommentCount.setTextColor(this.mResources.getColor(R.color.color_ffffff));
            this.mSortArrayDialogBinding.arrayReadCount.setBackgroundResource(17170445);
            this.mSortArrayDialogBinding.arrayReadCount.setTextColor(this.mResources.getColor(R.color.color_000000));
        } else if (this.mCurrentHotShowSort == 1) {
            this.mSortArrayDialogBinding.arrayTime.setBackgroundResource(17170445);
            this.mSortArrayDialogBinding.arrayTime.setTextColor(this.mResources.getColor(R.color.color_000000));
            this.mSortArrayDialogBinding.arrayCommentCount.setBackgroundResource(17170445);
            this.mSortArrayDialogBinding.arrayCommentCount.setTextColor(this.mResources.getColor(R.color.color_000000));
            this.mSortArrayDialogBinding.arrayReadCount.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mSortArrayDialogBinding.arrayReadCount.setTextColor(this.mResources.getColor(R.color.color_ffffff));
        }
        this.mSortArrayDialogBinding.arrayTime.setPadding(this.m8, this.m8, this.m8, this.m8);
        this.mSortArrayDialogBinding.arrayCommentCount.setPadding(this.m8, this.m8, this.m8, this.m8);
        this.mSortArrayDialogBinding.arrayReadCount.setPadding(this.m8, this.m8, this.m8, this.m8);
        if (this.mSortArrayWindow == null) {
            this.mSortArrayWindow = new PopupWindow(this.mSortArrayDialogBinding.getRoot(), this.m220, this.m250, true);
        }
        this.mSortArrayWindow.setFocusable(true);
        this.mSortArrayWindow.setOutsideTouchable(true);
        this.mSortArrayWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, 17170445)));
        int[] loc_int = new int[2];
        view.getLocationOnScreen(loc_int);
        Rect location = new Rect();
        location.right = loc_int[0] + view.getWidth();
        location.bottom = loc_int[1] + view.getHeight();
        this.mSortArrayWindow.showAtLocation(view, 51, location.left, location.bottom - 100);
    }

    private void hideCommentArrayWindow() {
        if (this.mSortArrayWindow != null && this.mSortArrayWindow.isShowing()) {
            this.mSortArrayWindow.dismiss();
        }
    }

    private void showOpenRemindDialog() {
        if (FengUtil.isNotificationAuthorityEnabled(this)) {
            setRemind(1);
            return;
        }
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.pop_program_open_remind_confirm), false));
        CommonDialog.showCommonDialog(this, getString(R.string.pop_program_open_remind_tips), "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.addFlags(268435456);
                    intent.setData(Uri.fromParts("package", MediaMeasurementDetailActivity.this.getPackageName(), null));
                    MediaMeasurementDetailActivity.this.startActivity(intent);
                    MediaMeasurementDetailActivity.this.mIsSetNotification = true;
                }
            }
        }, true);
    }

    private void showCloseRemindDialog() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.pop_program_close_remind_cancel), false));
        list.add(new DialogItemEntity(getString(R.string.pop_program_close_remind_confirm), false));
        CommonDialog.showCommonDialog(this, getString(R.string.pop_program_cancel_remind_tips), "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position != 0 && position == 1) {
                    MediaMeasurementDetailActivity.this.setRemind(0);
                }
            }
        }, false);
    }

    private void setRemind(final int type) {
        Map<String, Object> map = new HashMap();
        map.put("carsid", String.valueOf(this.mMediaMeasureId));
        map.put("type", String.valueOf(type));
        FengApplication.getInstance().httpRequest("car/inputautoremind/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                MediaMeasurementDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                MediaMeasurementDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code != 1) {
                        FengApplication.getInstance().checkCode("snshotshow/getlistbyid/", code);
                    } else if (type == 1) {
                        MediaMeasurementDetailActivity.this.mIsRemind = 1;
                        MediaMeasurementDetailActivity.this.changeRemind();
                        MediaMeasurementDetailActivity.this.showFirstTypeToast((int) R.string.pop_program_open_remind_succeed_tips);
                    } else {
                        MediaMeasurementDetailActivity.this.mIsRemind = 0;
                        MediaMeasurementDetailActivity.this.changeRemind();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void changeRemind() {
        if (this.mIsRemind == 1) {
            this.mMeasureHeaderLayoutBinding.ivUpdateRemind.setBackgroundResource(R.drawable.pop_program_close_remind_btn_selector);
        } else {
            this.mMeasureHeaderLayoutBinding.ivUpdateRemind.setBackgroundResource(R.drawable.pop_program_open_remind_btn_selector);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }

    public void loginSuccess() {
        super.loginSuccess();
        ((ActivityMediaMeasurementDetailBinding) this.mBaseBinding).rcvMediaMeasurementList.forceToRefresh();
    }

    public String getLogCurrentPage() {
        return "app_car_series_evaluating_guide?carsid=" + this.mMediaMeasureId + "&sortid=" + this.mCurrentHotShowSort;
    }
}
