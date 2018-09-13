package com.feng.car.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.PopupWindow;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.facebook.common.util.UriUtil;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.ArticleDetailAdapter;
import com.feng.car.adapter.ArticleDetailAdapter.ArticleContentHolder;
import com.feng.car.adapter.ArticleDetailAdapter.InteractionChangeListener;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivityVideoFinalpageBinding;
import com.feng.car.databinding.ArticleCommentArraydialogBinding;
import com.feng.car.databinding.ArticleShareDialogBinding;
import com.feng.car.databinding.VideofinalpageHeaderBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.ad.AdvertInfo;
import com.feng.car.entity.comment.CommentInfoList;
import com.feng.car.entity.praise.PraiseInfoList;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.thread.ArticleInfo;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.entity.thread.GratuityRecordInfo;
import com.feng.car.entity.thread.MediaInfo;
import com.feng.car.event.ProgramFollowEvent;
import com.feng.car.event.SendCommentStartSlideEvent;
import com.feng.car.event.SnsInfoModifyEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.listener.FengUMShareListener;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.service.AudioPlayService;
import com.feng.car.service.AudioPlayService.MyBinder;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.utils.MapUtil;
import com.feng.car.video.FengVideoPlayer;
import com.feng.car.video.FengVideoPlayer$OptionListener;
import com.feng.car.video.VideoUtil;
import com.feng.car.video.download.VideoDownloadManager;
import com.feng.car.video.player.JCAutoFullscreenListener;
import com.feng.car.video.player.JCMediaManager;
import com.feng.car.video.player.JCVideoPlayer;
import com.feng.car.video.player.JCVideoPlayer$VideoCloseClickListener;
import com.feng.car.video.player.JCVideoPlayerManager;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.StringUtil;
import com.feng.library.utils.WifiUtil;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.stub.StubApp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoFinalPageActivity extends VideoBaseActivity<ActivityVideoFinalpageBinding> implements InteractionChangeListener, JCVideoPlayer$VideoCloseClickListener, FengVideoPlayer$OptionListener {
    private final int HOT_COMMENT_ARRAY = 1;
    private final int NEED_LOCATION = 1;
    private final int NO_LOCATION = 0;
    private final int READ_AND_WRITE_EXTERNAL_STORAGE = 111;
    private final int REQUEST_COMMENT = 1;
    private final int REQUEST_PRAISE = 3;
    private final int TIME_COMMENT_ARRAY = 2;
    private AudioManager audioManager;
    private boolean blueLogin = false;
    private ServiceConnection connection;
    private Drawable downloadCompleteDrawable;
    private Drawable downloadDrawable;
    private boolean hasShowFlowTips = false;
    private boolean lastStateIsPlaying = false;
    private boolean loadCommentSuccess = false;
    private boolean loadPraiseSuccess = false;
    private int m184;
    private int m220;
    private int m8;
    private AdvertInfo mAdvertInfo = new AdvertInfo();
    private boolean mAlreadyExposure = false;
    private int mAnimationEndTime = 150;
    private int mAnimationTime = 200;
    private ArticleDetailAdapter mArticleDetailAdapter;
    private ArticleShareDialogBinding mArticleShareBinding;
    private MyBinder mBinder;
    private ArticleCommentArraydialogBinding mCommentArrayBinding;
    private PopupWindow mCommentArrayWindow;
    private int mCommentEmptyHight = 0;
    private int mCommentId = -1;
    private int mCommentOrderid = 1;
    private int mCurrentOffset = 0;
    private int mCurrentPosition = 0;
    public OnSingleClickListener mDownloadImageListener = new OnSingleClickListener() {
        public void onSingleClick(View v) {
            VideoFinalPageActivity.this.hideShareDialog();
            if (VideoDownloadManager.newInstance().hasDownloadVideo(VideoFinalPageActivity.this.mMediaInfo)) {
                VideoFinalPageActivity.this.showFifthTypeToast(R.string.video_has_download_finish);
                VideoFinalPageActivity.this.mIsVideoCache = true;
                VideoFinalPageActivity.this.mArticleShareBinding.download.setClickable(false);
                VideoFinalPageActivity.this.mArticleShareBinding.download.setCompoundDrawables(null, VideoFinalPageActivity.this.downloadCompleteDrawable, null, null);
                VideoFinalPageActivity.this.mArticleShareBinding.download.setText(R.string.cache_complete);
            } else if (VideoDownloadManager.newInstance().hasAddedTask(VideoFinalPageActivity.this.mMediaInfo)) {
                VideoFinalPageActivity.this.showFifthTypeToast(R.string.has_exists_download_queue);
            } else {
                if (VERSION.SDK_INT >= 23) {
                    boolean flag1;
                    boolean flag2;
                    if (VideoFinalPageActivity.this.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != 0) {
                        flag1 = true;
                    } else {
                        flag1 = false;
                    }
                    if (VideoFinalPageActivity.this.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        flag2 = true;
                    } else {
                        flag2 = false;
                    }
                    List<String> strPermissions = new ArrayList();
                    if (flag1) {
                        strPermissions.add("android.permission.READ_EXTERNAL_STORAGE");
                    }
                    if (flag2) {
                        strPermissions.add("android.permission.WRITE_EXTERNAL_STORAGE");
                    }
                    if (strPermissions.size() > 0) {
                        VideoFinalPageActivity.this.requestPermissions((String[]) strPermissions.toArray(new String[strPermissions.size()]), 111);
                        return;
                    }
                }
                VideoFinalPageActivity.this.showDownloadDialog();
            }
        }
    };
    private boolean mFirstInit = true;
    private int mFirstLocation = -1;
    public boolean mForcePlayHigh = false;
    private int mFromArticlePage = 0;
    private VideofinalpageHeaderBinding mHeader;
    private CommentInfoList mHotCommentList = new CommentInfoList();
    private int mHotCommentPage = 1;
    private int mHotCommentTotalPage = 0;
    private boolean mIsCommentChangeEmptyHight = false;
    private boolean mIsCommentEmpty = false;
    private boolean mIsCommentNeedEmpty = false;
    private boolean mIsHintComment = false;
    private boolean mIsNeedPlayVoice = false;
    private boolean mIsPraiseChangeEmptyHight = false;
    private boolean mIsPraiseEmpty = false;
    public boolean mIsPraiseNeedEmpty = false;
    private boolean mIsRefresh = false;
    private boolean mIsShowTopBar = false;
    private boolean mIsVideoCache = false;
    private IWXAPI mIwxapi;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int mLastOffset;
    private int mLastPosition;
    private List<ArticleInfo> mList = new ArrayList();
    private int mLocationY = 0;
    private MediaInfo mMediaInfo;
    private int mOffset = -1;
    private int mPosition = -1;
    private int mPraiseEmptyHight = 0;
    private PraiseInfoList mPraiseList = new PraiseInfoList();
    private int mPraisePage = 1;
    private int mPraiseTotalPage = 0;
    private int mResourceId = 0;
    private int mResourceType = 2;
    private int mScreenWidth;
    private Dialog mShareDialog;
    private int mSnsId;
    private SnsInfo mSnsInfo = new SnsInfo();
    private ArticleInfo mTabArticleInfo;
    private int mTabBarPos;
    private CommentInfoList mTimeCommentList = new CommentInfoList();
    private int mTimeCommentPage = 1;
    private int mTimeCommentTotalPage = 0;
    private TimeReceiver mTimeReceiver;
    private int mType = 1;
    private int mValidRecyclerViewHight;
    private int mVideoHeight = 0;
    private int mVideoLocalScreenY;
    private MyVolumeReceiver mVolumeReceiver;
    private FengUMShareListener umShareListener;

    private class MyVolumeReceiver extends BroadcastReceiver {
        private MyVolumeReceiver() {
        }

        /* synthetic */ MyVolumeReceiver(VideoFinalPageActivity x0, AnonymousClass1 x1) {
            this();
        }

        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                    JCMediaManager.instance().getPlayer().setMute(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class TimeReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).videoPlayer.updateTime();
        }
    }

    static {
        StubApp.interface11(3226);
    }

    protected native void onCreate(Bundle bundle);

    private void resetCurrentPage() {
        if (this.mType == 1) {
            if (this.mCommentOrderid == 1) {
                this.mHotCommentPage = 1;
            } else {
                this.mTimeCommentPage = 1;
            }
        } else if (this.mType == 3) {
            this.mPraisePage = 1;
        }
    }

    private void updateCurrentPage() {
        if (this.mType == 1) {
            if (this.mCommentOrderid == 1) {
                this.mHotCommentPage++;
            } else {
                this.mTimeCommentPage++;
            }
        } else if (this.mType == 3) {
            this.mPraisePage++;
        }
    }

    private int getCurrentPage() {
        if (this.mType == 1) {
            if (this.mCommentOrderid == 1) {
                return this.mHotCommentPage;
            }
            return this.mTimeCommentPage;
        } else if (this.mType == 3) {
            return this.mPraisePage;
        } else {
            return 1;
        }
    }

    private int getTotalPage() {
        if (this.mType == 1) {
            if (this.mCommentOrderid == 1) {
                return this.mHotCommentTotalPage;
            }
            return this.mTimeCommentTotalPage;
        } else if (this.mType == 3) {
            return this.mPraiseTotalPage;
        } else {
            return 0;
        }
    }

    private void setTotalPage(int totalPage) {
        if (this.mType == 1) {
            if (this.mCommentOrderid == 1) {
                this.mHotCommentTotalPage = totalPage;
            } else {
                this.mTimeCommentTotalPage = totalPage;
            }
        } else if (this.mType == 3) {
            this.mPraiseTotalPage = totalPage;
        }
    }

    private void bindService() {
        this.connection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
            }

            public void onServiceConnected(ComponentName name, IBinder service) {
                VideoFinalPageActivity.this.mBinder = (MyBinder) service;
                VideoFinalPageActivity.this.mBinder.pauseAudioPlayer();
            }
        };
        bindService(new Intent(this, AudioPlayService.class), this.connection, 1);
    }

    private void unbindService() {
        try {
            if (this.connection != null) {
                unbindService(this.connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLocalIntentData() {
        this.mSnsId = getIntent().getIntExtra("snsid", 0);
        this.mResourceId = getIntent().getIntExtra("resourceid", 0);
        this.mResourceType = getIntent().getIntExtra("resourcetype", 0);
    }

    private void showDownloadDialog() {
        List<DialogItemEntity> list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if (!StringUtil.isEmpty(this.mMediaInfo.getBDUrl())) {
            sb.append(this.mMediaInfo.getBDName());
            sb.append("画质（");
            sb.append(FengUtil.formatVideoSize(this.mMediaInfo.getBDSize()));
            sb.append("）");
            DialogItemEntity item = new DialogItemEntity(sb.toString(), false);
            if (!FengApplication.getInstance().isLoginUser()) {
                item.setIconId(R.drawable.icon_blue_login);
            }
            list.add(item);
        }
        if (!StringUtil.isEmpty(this.mMediaInfo.getFHDUrl())) {
            sb = new StringBuilder();
            sb.append(this.mMediaInfo.getFHDName());
            sb.append("画质（");
            sb.append(FengUtil.formatVideoSize(this.mMediaInfo.getFHDSize()));
            sb.append("）");
            list.add(new DialogItemEntity(sb.toString(), false));
        }
        if (!StringUtil.isEmpty(this.mMediaInfo.getHDUrl())) {
            sb = new StringBuilder();
            sb.append(this.mMediaInfo.getHDName());
            sb.append("画质（");
            sb.append(FengUtil.formatVideoSize(this.mMediaInfo.getHDSize()));
            sb.append("）");
            list.add(new DialogItemEntity(sb.toString(), false));
        }
        sb = new StringBuilder();
        sb.append(this.mMediaInfo.getSDName());
        sb.append("画质（");
        sb.append(FengUtil.formatVideoSize(this.mMediaInfo.getSDSize()));
        sb.append("）");
        list.add(new DialogItemEntity(sb.toString(), false));
        CommonDialog.showCommonDialog(this, "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (VideoDownloadManager.newInstance().hasAddedTask(VideoFinalPageActivity.this.mMediaInfo)) {
                    VideoFinalPageActivity.this.showFifthTypeToast(R.string.has_exists_download_queue);
                } else if (!FengUtil.isNetworkAvailable(VideoFinalPageActivity.this)) {
                    VideoFinalPageActivity.this.showThirdTypeToast((int) R.string.check_network);
                } else if (FengUtil.externalMemoryAvailable()) {
                    long availableSize = FengUtil.getAvailableExternalMemorySize();
                    if (!dialogItemEntity.itemContent.contains(VideoFinalPageActivity.this.mMediaInfo.getBDName()) || StringUtil.isEmpty(VideoFinalPageActivity.this.mMediaInfo.getBDUrl())) {
                        if (!dialogItemEntity.itemContent.contains(VideoFinalPageActivity.this.mMediaInfo.getFHDName()) || StringUtil.isEmpty(VideoFinalPageActivity.this.mMediaInfo.getFHDUrl())) {
                            if (!dialogItemEntity.itemContent.contains(VideoFinalPageActivity.this.mMediaInfo.getHDName()) || StringUtil.isEmpty(VideoFinalPageActivity.this.mMediaInfo.getHDUrl())) {
                                if (availableSize - VideoFinalPageActivity.this.mMediaInfo.getSDSize() < 20971520) {
                                    VideoFinalPageActivity.this.showSecondTypeToast((int) R.string.sdcard_not_enough_to_download);
                                    return;
                                } else {
                                    VideoFinalPageActivity.this.mMediaInfo.downloadDefination = 1;
                                    VideoDownloadManager.newInstance().downloadVideo(VideoFinalPageActivity.this.mMediaInfo, VideoFinalPageActivity.this.mSnsInfo, VideoFinalPageActivity.this.mMediaInfo.videourl.sdinfo);
                                }
                            } else if (availableSize - VideoFinalPageActivity.this.mMediaInfo.getHDSize() < 20971520) {
                                VideoFinalPageActivity.this.showSecondTypeToast((int) R.string.sdcard_not_enough_to_download);
                                return;
                            } else {
                                VideoFinalPageActivity.this.mMediaInfo.downloadDefination = 2;
                                VideoDownloadManager.newInstance().downloadVideo(VideoFinalPageActivity.this.mMediaInfo, VideoFinalPageActivity.this.mSnsInfo, VideoFinalPageActivity.this.mMediaInfo.videourl.hdinfo);
                            }
                        } else if (availableSize - VideoFinalPageActivity.this.mMediaInfo.getFHDSize() < 20971520) {
                            VideoFinalPageActivity.this.showSecondTypeToast((int) R.string.sdcard_not_enough_to_download);
                            return;
                        } else {
                            VideoFinalPageActivity.this.mMediaInfo.downloadDefination = 3;
                            VideoDownloadManager.newInstance().downloadVideo(VideoFinalPageActivity.this.mMediaInfo, VideoFinalPageActivity.this.mSnsInfo, VideoFinalPageActivity.this.mMediaInfo.videourl.fhdinfo);
                        }
                    } else if (!FengApplication.getInstance().isLoginUser()) {
                        Intent intent = new Intent(VideoFinalPageActivity.this, LoginActivity.class);
                        intent.putExtra("video_bd_login_flag", 2);
                        VideoFinalPageActivity.this.startActivity(intent);
                        return;
                    } else if (availableSize - VideoFinalPageActivity.this.mMediaInfo.getBDSize() < 20971520) {
                        VideoFinalPageActivity.this.showSecondTypeToast((int) R.string.sdcard_not_enough_to_download);
                        return;
                    } else {
                        VideoFinalPageActivity.this.mMediaInfo.downloadDefination = 4;
                        VideoDownloadManager.newInstance().downloadVideo(VideoFinalPageActivity.this.mMediaInfo, VideoFinalPageActivity.this.mSnsInfo, VideoFinalPageActivity.this.mMediaInfo.videourl.bdinfo);
                    }
                    VideoFinalPageActivity.this.showDownloadToast();
                } else {
                    VideoFinalPageActivity.this.showSecondTypeToast((int) R.string.sdcard_not_available);
                }
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111) {
            boolean canDownload = true;
            for (int i : grantResults) {
                if (i != 0) {
                    canDownload = false;
                    break;
                }
            }
            if (canDownload) {
                showDownloadDialog();
            } else {
                showSecondTypeToast((int) R.string.download_permission);
            }
        }
    }

    private void showDownloadToast() {
        if (!FengUtil.isNetworkAvailable(this)) {
            return;
        }
        if (FengUtil.isWifiConnectivity(this)) {
            showFifthTypeToast(R.string.add_download_queue);
        } else if (!SharedUtil.getBoolean(this, "no_wifi_download", false)) {
            showThirdTypeToast((int) R.string.user_4g_download_video);
            SharedUtil.putBoolean(this, "no_wifi_download", true);
        }
    }

    private void registerVolumeReceiver() {
        this.mVolumeReceiver = new MyVolumeReceiver(this, null);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(this.mVolumeReceiver, filter);
        this.audioManager = (AudioManager) getSystemService("audio");
    }

    private void unregisterVolumeReceiver() {
        if (this.mVolumeReceiver != null) {
            unregisterReceiver(this.mVolumeReceiver);
        }
    }

    public void finish() {
        super.finish();
        getWindow().clearFlags(128);
        JCVideoPlayer.releaseAllVideos();
        System.gc();
        if (this.mIsNeedPlayVoice) {
            unbindService();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        JCVideoPlayerManager.setFirstFloor(null);
    }

    public void registerTimeReceiver() {
        IntentFilter filter = new IntentFilter("android.intent.action.TIME_TICK");
        this.mTimeReceiver = new TimeReceiver();
        registerReceiver(this.mTimeReceiver, filter);
    }

    public void unregisterTimeReceiver() {
        if (this.mTimeReceiver != null) {
            unregisterReceiver(this.mTimeReceiver);
        }
    }

    public void initListener() {
        super.initListener();
        registerVolumeReceiver();
        registerTimeReceiver();
    }

    protected void onResume() {
        boolean flag = true;
        super.onResume();
        if (FengApplication.getInstance().getAudioState().audioState == 1) {
            this.mIsNeedPlayVoice = true;
            bindService();
        }
        if (this.mSnsInfo.isflag == 0 && !this.blueLogin && this.mMediaInfo != null && !this.mMediaInfo.isComplete && this.mRootBinding.emptyView.getVisibility() == 8) {
            try {
                if (this.mFirstInit) {
                    if (this.mIsVideoCache) {
                        ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.playVideo();
                        getWindow().addFlags(128);
                    } else {
                        if (this.mFromArticlePage != 1) {
                            flag = false;
                        }
                        if (flag) {
                            ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.playVideo();
                            getWindow().addFlags(128);
                            ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.show4GToast();
                        } else if (WifiUtil.isWifiConnectivity(this)) {
                            ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.playVideo();
                            getWindow().addFlags(128);
                        } else {
                            ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.showFlowTipsLine();
                        }
                    }
                    this.mFirstInit = false;
                } else if (this.mIsVideoCache) {
                    if (this.lastStateIsPlaying && ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.mCurrentState != 2) {
                        ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.playVideo();
                        getWindow().addFlags(128);
                    }
                } else if (!this.hasShowFlowTips) {
                    if (this.lastStateIsPlaying && ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.mCurrentState != 2) {
                        ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.playVideo();
                        getWindow().addFlags(128);
                    }
                    this.lastStateIsPlaying = false;
                }
                if (JCVideoPlayerManager.needStartFullScreen) {
                    if (JCVideoPlayerManager.lastDirection == JCVideoPlayerManager.CURRENT_VIDEO_STATE_VERTICAL) {
                        ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.startWindowFullscreen(false);
                    } else {
                        ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.startWindowFullscreen(true);
                    }
                    JCVideoPlayerManager.needStartFullScreen = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void onPause() {
        super.onPause();
        if (((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.mCurrentState == 2) {
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.playVideo();
            this.lastStateIsPlaying = true;
        }
        unregisterVolumeReceiver();
        unregisterTimeReceiver();
    }

    public int setBaseContentView() {
        return R.layout.activity_video_finalpage;
    }

    public void initView() {
        FengApplication.getInstance().setVideoBdLoginFlag(0);
        JCVideoPlayerManager.mCurrentDirection = JCVideoPlayerManager.CURRENT_VIDEO_STATE_VERTICAL;
        hideDefaultTitleBar();
        this.m8 = this.mResources.getDimensionPixelSize(R.dimen.default_8PX);
        this.m184 = this.mResources.getDimensionPixelSize(R.dimen.default_184PX);
        this.m220 = this.mResources.getDimensionPixelSize(R.dimen.default_220PX);
        this.mTabArticleInfo = new ArticleInfo();
        this.mTabArticleInfo.type = 5;
        this.mArticleDetailAdapter = new ArticleDetailAdapter(this, this.mList);
        this.mArticleDetailAdapter.setInteractionChangeListener(this);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mArticleDetailAdapter);
        this.mHeader = VideofinalpageHeaderBinding.inflate(LayoutInflater.from(this));
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeader.getRoot());
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.setRefreshProgressStyle(2);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.setPullRefreshEnabled(true);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.setOverScrollMode(2);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView, State.Normal);
                VideoFinalPageActivity.this.resetCurrentPage();
                VideoFinalPageActivity.this.mLastOffset = 0;
                VideoFinalPageActivity.this.updateViewPosition(-1, 0);
                VideoFinalPageActivity.this.getAdData();
            }
        });
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (!RecyclerViewStateUtils.isRecylerViewLoading(((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView) && !VideoFinalPageActivity.this.mIsRefresh) {
                    if (VideoFinalPageActivity.this.getCurrentPage() > VideoFinalPageActivity.this.getTotalPage()) {
                        RecyclerViewStateUtils.setFooterViewState(VideoFinalPageActivity.this, ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView, 20, State.TheEnd, null);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(VideoFinalPageActivity.this, ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView, 20, State.Loading, null);
                    VideoFinalPageActivity.this.loadBottomData(0, false);
                }
            }
        });
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.setLScrollListener(new LRecyclerView$LScrollListener() {
            public void onScrollUp() {
            }

            public void onScrollDown() {
            }

            public void onScrolled(int distanceX, int distanceY) {
                if (VideoFinalPageActivity.this.mValidRecyclerViewHight == 0) {
                    VideoFinalPageActivity.this.mValidRecyclerViewHight = ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).bottomLine.getTop();
                }
                LinearLayoutManager lm = (LinearLayoutManager) ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.getLayoutManager();
                int firstVisiblePosition = lm.findFirstVisibleItemPosition();
                ArticleContentHolder holder = VideoFinalPageActivity.this.mArticleDetailAdapter.getTabHolder();
                if (holder != null) {
                    int[] location = new int[2];
                    holder.mBinding.choiceLine.getLocationOnScreen(location);
                    int position = VideoFinalPageActivity.this.mArticleDetailAdapter.getTabPosition();
                    if (location[1] >= ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).videoPlayer.getHeight() + VideoFinalPageActivity.this.getTitleBarBottomY() && position >= firstVisiblePosition - 1) {
                        VideoFinalPageActivity.this.mIsShowTopBar = false;
                        ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).topLine.setVisibility(8);
                        VideoFinalPageActivity.this.adShowOrGone(true);
                    } else if (position >= firstVisiblePosition - 1) {
                        VideoFinalPageActivity.this.mIsShowTopBar = false;
                        ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).topLine.setVisibility(8);
                        VideoFinalPageActivity.this.adShowOrGone(true);
                    } else {
                        ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).topLine.setVisibility(0);
                        VideoFinalPageActivity.this.mIsShowTopBar = true;
                        VideoFinalPageActivity.this.adShowOrGone(false);
                    }
                }
                VideoFinalPageActivity.this.setEmptyHight(lm);
            }

            public void onScrollStateChanged(int state) {
                if (state == 0 && VideoFinalPageActivity.this.mArticleDetailAdapter.getTabPosition() != 0) {
                    ArticleContentHolder holder = VideoFinalPageActivity.this.mArticleDetailAdapter.getTabHolder();
                    if (holder != null) {
                        VideoFinalPageActivity.this.updateViewPosition(VideoFinalPageActivity.this.mArticleDetailAdapter.getTabPosition() + 2, holder.mBinding.getRoot().getTop());
                    }
                }
            }
        });
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).ivForward.setOnClickListener(this);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).praiseLine.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).praiseLine.setEnabled(false);
                VideoFinalPageActivity.this.mSnsInfo.praiseOperation(VideoFinalPageActivity.this, false, new SuccessFailCallback() {
                    public void onSuccess() {
                        ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).praiseLine.setEnabled(true);
                    }

                    public void onFail() {
                        ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).praiseLine.setEnabled(true);
                    }
                });
            }
        });
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).tvSendMessage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                VideoFinalPageActivity.this.mSnsInfo.intentToSendComment(VideoFinalPageActivity.this);
            }
        });
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).commentText.setOnClickListener(this);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).commentText.setSelected(true);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).praiseText.setOnClickListener(this);
        this.mTabBarPos = 0;
        this.mList.add(this.mTabArticleInfo);
        this.umShareListener = new FengUMShareListener(this, this.mSnsInfo);
        regToWX();
    }

    private void regToWX() {
        this.mIwxapi = WXAPIFactory.createWXAPI(this, HttpConstant.BASE_WX_APP_ID, false);
        this.mIwxapi.registerApp(HttpConstant.BASE_WX_APP_ID);
    }

    private void adShowOrGone(boolean isShow) {
        if (!TextUtils.isEmpty(this.mAdvertInfo.showid)) {
            if (isShow) {
                if (!this.mAlreadyExposure) {
                    this.mAlreadyExposure = true;
                    this.mAdvertInfo.adPvHandle(this, true);
                }
            } else if (this.mAlreadyExposure) {
                this.mAlreadyExposure = false;
                this.mAdvertInfo.adPvHandle(this, false);
            }
        }
    }

    private void enterAnimator() {
        if (this.mLocationY != 0) {
            int[] location = new int[2];
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.getLocationOnScreen(location);
            this.mVideoLocalScreenY = location[1];
            ValueAnimator translateYAnimator = ValueAnimator.ofFloat(new float[]{(float) this.mLocationY, 0.0f});
            translateYAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).videoPlayer.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            translateYAnimator.setDuration((long) this.mAnimationTime);
            ValueAnimator alphaAnimator = ValueAnimator.ofFloat(new float[]{1.0f, 255.0f});
            alphaAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).llContent.getBackground().mutate().setAlpha((int) ((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            alphaAnimator.setDuration((long) this.mAnimationTime);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(translateYAnimator).with(alphaAnimator);
            animatorSet.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animation) {
                    ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).rlBottom.setVisibility(8);
                }

                public void onAnimationEnd(Animator animation) {
                    ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).llContent.setBackgroundResource(R.color.color_f3f3f3);
                    if (!VideoFinalPageActivity.this.mIsHintComment) {
                        ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).rlBottom.setVisibility(0);
                    }
                }

                public void onAnimationCancel(Animator animation) {
                }

                public void onAnimationRepeat(Animator animation) {
                }
            });
            animatorSet.start();
        }
    }

    private void exitAnimator() {
        if (this.mLocationY == 0) {
            finish();
            return;
        }
        this.mMediaInfo.isComplete = false;
        if (this.mVideoLocalScreenY == 0) {
            int[] location = new int[2];
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.getLocationOnScreen(location);
            this.mVideoLocalScreenY = location[1];
        }
        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(new float[]{(float) this.mVideoLocalScreenY, (float) this.mLocationY});
        translateYAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).videoPlayer.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        translateYAnimator.setDuration((long) this.mAnimationTime);
        ValueAnimator alphaAnimator = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
        alphaAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).videoPlayer.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        alphaAnimator.setDuration((long) this.mAnimationTime);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translateYAnimator).with(alphaAnimator);
        animatorSet.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animation) {
                ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).getRoot().getBackground().mutate().setAlpha(0);
                ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).llContent.setBackgroundResource(R.color.transparent);
                ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).rlBottom.setVisibility(8);
            }

            public void onAnimationEnd(Animator animation) {
                ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).videoPlayer.setAlpha(1.0f);
                ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).videoPlayer.setVisibility(4);
                VideoFinalPageActivity.this.finish();
                VideoFinalPageActivity.this.overridePendingTransition(0, 0);
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.start();
    }

    private void getAdData() {
        Map<String, Object> map = new HashMap();
        map.put("pageid", String.valueOf(999));
        map.put("datatype", "0");
        map.put("pagecode", "0");
        FengApplication.getInstance().httpRequest("advert/adserver/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                VideoFinalPageActivity.this.loadData();
            }

            public void onStart() {
            }

            public void onFinish() {
                VideoFinalPageActivity.this.loadData();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONArray adData = jsonResult.getJSONObject("body").getJSONArray(UriUtil.DATA_SCHEME);
                        if (adData.length() > 0) {
                            VideoFinalPageActivity.this.mAdvertInfo.resetData();
                            VideoFinalPageActivity.this.mAdvertInfo.parser(adData.getJSONObject(0));
                            return;
                        }
                        VideoFinalPageActivity.this.mAdvertInfo.resetData();
                        return;
                    }
                    VideoFinalPageActivity.this.mAdvertInfo.resetData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showHeaderEmptyView() {
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).headerEmptyView.setEmptyImage(R.drawable.icon_load_faile);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).headerEmptyView.setEmptyText(R.string.network_not_connect);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).headerEmptyView.setButtonListener(R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                VideoFinalPageActivity.this.getAdData();
            }
        });
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).headerEmptyView.setVisibility(0);
    }

    public void showHeaderTextEmptyView(int textID, int drawableID) {
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).headerEmptyView.setEmptyImage(drawableID);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).headerEmptyView.setEmptyText(textID);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).headerEmptyView.setVisibility(0);
    }

    public void hideHeaderEmptyView() {
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).headerEmptyView.setVisibility(8);
    }

    private void loadData() {
        Map<String, Object> map = new HashMap();
        map.put("snsid", String.valueOf(this.mSnsId));
        map.put("resourceid", String.valueOf(this.mResourceId));
        map.put("resourcetype", String.valueOf(this.mResourceType));
        map.put("showtype", String.valueOf(2));
        map.put("cityid", String.valueOf(MapUtil.newInstance().getCurrentCityId()));
        FengApplication.getInstance().httpRequest("sns/info/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                VideoFinalPageActivity.this.hideProgress();
                if (VideoFinalPageActivity.this.mIsVideoCache) {
                    VideoFinalPageActivity.this.showHeaderEmptyView();
                } else {
                    if (StringUtil.isEmpty((String) VideoFinalPageActivity.this.mSnsInfo.title.get())) {
                        VideoFinalPageActivity.this.initNormalTitleBar((int) R.string.none);
                        VideoFinalPageActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                VideoFinalPageActivity.this.getAdData();
                            }
                        });
                    } else {
                        VideoFinalPageActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    }
                    VideoFinalPageActivity.this.hideHeaderEmptyView();
                }
                ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.refreshComplete();
            }

            public void onStart() {
                VideoFinalPageActivity.this.mIsRefresh = true;
            }

            public void onFinish() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView, State.Normal);
                if (VideoFinalPageActivity.this.loadCommentSuccess || VideoFinalPageActivity.this.loadPraiseSuccess) {
                    VideoFinalPageActivity.this.hideProgress();
                }
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (VideoFinalPageActivity.this.mIsVideoCache) {
                    VideoFinalPageActivity.this.showHeaderEmptyView();
                } else {
                    if (StringUtil.isEmpty((String) VideoFinalPageActivity.this.mSnsInfo.title.get())) {
                        VideoFinalPageActivity.this.initNormalTitleBar((int) R.string.none);
                        VideoFinalPageActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                VideoFinalPageActivity.this.getAdData();
                            }
                        });
                    } else {
                        VideoFinalPageActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    }
                    VideoFinalPageActivity.this.hideHeaderEmptyView();
                }
                ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.refreshComplete();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        VideoFinalPageActivity.this.hideDefaultTitleBar();
                        VideoFinalPageActivity.this.hideEmptyView();
                        VideoFinalPageActivity.this.mSnsInfo.parser(jsonObject.getJSONObject("body").getJSONObject("sns"));
                        if (VideoFinalPageActivity.this.mSnsId == 0) {
                            VideoFinalPageActivity.this.mSnsId = VideoFinalPageActivity.this.mSnsInfo.id;
                        }
                        if (VideoFinalPageActivity.this.mResourceId == 0) {
                            VideoFinalPageActivity.this.mResourceId = VideoFinalPageActivity.this.mSnsInfo.resourceid;
                        }
                        VideoFinalPageActivity.this.mResourceType = VideoFinalPageActivity.this.mSnsInfo.snstype;
                        if (VideoFinalPageActivity.this.mSnsInfo.isflag != 0) {
                            VideoFinalPageActivity.this.showEmptyView((int) R.string.fengwen_delete, (int) R.drawable.icon_article_delete);
                            VideoFinalPageActivity.this.mRootBinding.titleLine.titlebarRightParent.setVisibility(8);
                            JCVideoPlayer.releaseAllVideos();
                            return;
                        }
                        VideoFinalPageActivity.this.hideEmptyView();
                        VideoFinalPageActivity.this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
                        ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).videoPlayer.setTitle(VideoFinalPageActivity.this.mSnsInfo.getFeedTitleOrDes(), true);
                        VideoFinalPageActivity.this.hideHeaderEmptyView();
                        VideoFinalPageActivity.this.parseThread();
                        if (VideoFinalPageActivity.this.mCommentId == -1 && !((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.isShown()) {
                            ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.setVisibility(0);
                        }
                        VideoFinalPageActivity.this.getPlayCount();
                        VideoFinalPageActivity.this.resetEmptyParameter();
                        if (VideoFinalPageActivity.this.mFirstLocation != -1) {
                            if (VideoFinalPageActivity.this.mFirstLocation == 1) {
                                VideoFinalPageActivity.this.mType = 1;
                            } else if (VideoFinalPageActivity.this.mFirstLocation == 3) {
                                VideoFinalPageActivity.this.mType = 3;
                            }
                            VideoFinalPageActivity.this.mArticleDetailAdapter.setType(VideoFinalPageActivity.this.mType);
                            VideoFinalPageActivity.this.changeTabColor(VideoFinalPageActivity.this.mType);
                            VideoFinalPageActivity.this.loadBottomData(1, false);
                            return;
                        }
                        VideoFinalPageActivity.this.loadBottomData(0, false);
                    } else if (code == -48 || code == -50) {
                        if (VideoFinalPageActivity.this.mIsVideoCache) {
                            VideoFinalPageActivity.this.showHeaderTextEmptyView(R.string.fengwen_delete, R.drawable.icon_article_delete);
                            ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.refreshComplete();
                            return;
                        }
                        VideoFinalPageActivity.this.initNormalTitleBar((int) R.string.none);
                        if (VideoFinalPageActivity.this.mResourceType == 9 || VideoFinalPageActivity.this.mResourceType == 10) {
                            VideoFinalPageActivity.this.showEmptyView((int) R.string.viewpoint_delete, (int) R.drawable.icon_article_delete);
                        } else {
                            VideoFinalPageActivity.this.showEmptyView((int) R.string.fengwen_delete, (int) R.drawable.icon_article_delete);
                        }
                        VideoFinalPageActivity.this.mRootBinding.titleLine.titlebarRightParent.setVisibility(8);
                        ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.refreshComplete();
                        JCVideoPlayer.releaseAllVideos();
                    } else if (code != -49 && code != -51 && code != -52) {
                        FengApplication.getInstance().checkCode("sns/info/", code);
                        if (VideoFinalPageActivity.this.mIsVideoCache) {
                            VideoFinalPageActivity.this.showHeaderEmptyView();
                            ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.refreshComplete();
                            return;
                        }
                        if (StringUtil.isEmpty((String) VideoFinalPageActivity.this.mSnsInfo.title.get())) {
                            VideoFinalPageActivity.this.initNormalTitleBar((int) R.string.none);
                            VideoFinalPageActivity.this.mRootBinding.titleLine.titlebarRightParent.setVisibility(8);
                            VideoFinalPageActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                                public void onSingleClick(View v) {
                                    VideoFinalPageActivity.this.getAdData();
                                }
                            });
                        } else {
                            VideoFinalPageActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                        }
                        ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.refreshComplete();
                        JCVideoPlayer.releaseAllVideos();
                    } else if (VideoFinalPageActivity.this.mIsVideoCache) {
                        VideoFinalPageActivity.this.showHeaderTextEmptyView(R.string.fengwen_edit, R.drawable.icon_article_delete);
                        ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.refreshComplete();
                    } else {
                        VideoFinalPageActivity.this.showEmptyView((int) R.string.fengwen_edit, (int) R.drawable.icon_article_delete);
                        VideoFinalPageActivity.this.initNormalTitleBar((int) R.string.none);
                        VideoFinalPageActivity.this.mRootBinding.titleLine.titlebarRightParent.setVisibility(8);
                        ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.refreshComplete();
                        JCVideoPlayer.releaseAllVideos();
                    }
                } catch (Exception e) {
                    ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.refreshComplete();
                    if (StringUtil.isEmpty((String) VideoFinalPageActivity.this.mSnsInfo.title.get())) {
                        VideoFinalPageActivity.this.initNormalTitleBar((int) R.string.none);
                        VideoFinalPageActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                VideoFinalPageActivity.this.getAdData();
                            }
                        });
                    } else {
                        VideoFinalPageActivity.this.showThirdTypeToast((int) R.string.check_network);
                    }
                    JCVideoPlayer.releaseAllVideos();
                    FengApplication.getInstance().upLoadTryCatchLog("sns/info/", content, e);
                }
            }
        });
    }

    private void setEmptyHight(LinearLayoutManager lm) {
        int dataSize = 0;
        try {
            if (this.mType == 1) {
                if (this.mCommentOrderid == 1) {
                    dataSize = this.mHotCommentList.size();
                } else {
                    dataSize = this.mTimeCommentList.size();
                }
            } else if (this.mType == 3) {
                dataSize = this.mPraiseList.size();
            }
            if (dataSize > 0) {
                if (this.mType == 3 && !this.mIsPraiseChangeEmptyHight && this.mIsPraiseNeedEmpty) {
                    calculateEmptyHight(lm, dataSize);
                } else if (this.mType == 1 && !this.mIsCommentChangeEmptyHight && this.mIsCommentNeedEmpty) {
                    calculateEmptyHight(lm, dataSize);
                }
            } else if (this.mType == 3 && this.mIsPraiseEmpty && !this.mIsPraiseChangeEmptyHight) {
                showHintEmpty();
            } else if (this.mType == 1 && this.mIsCommentEmpty && !this.mIsCommentChangeEmptyHight) {
                showHintEmpty();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateEmptyHight(LinearLayoutManager lm, int dataSize) {
        int firstVisiblePosition = lm.findFirstVisibleItemPosition();
        int lastVisiblePosition = lm.findLastVisibleItemPosition();
        View view;
        int hight;
        int tabBarTop;
        int i;
        View childView;
        ArticleInfo info;
        if (this.mIsShowTopBar) {
            if (firstVisiblePosition >= this.mTabBarPos + 2 && lastVisiblePosition <= (dataSize + 1) + 2) {
                view = lm.getChildAt(lastVisiblePosition - firstVisiblePosition);
                hight = 1;
                if (view.getTag() != null && view.getTag().toString().equals("empty_view")) {
                    tabBarTop = 0;
                    for (i = 0; i < lastVisiblePosition - firstVisiblePosition; i++) {
                        childView = lm.getChildAt(i);
                        if (childView.getTag() != null && childView.getTag().toString().equals("tab_bar")) {
                            tabBarTop = childView.getTop();
                            break;
                        }
                    }
                    hight = this.mValidRecyclerViewHight - (view.getTop() - tabBarTop);
                }
                int size;
                if (this.mType == 1 && this.mIsCommentNeedEmpty && !this.mIsCommentChangeEmptyHight) {
                    size = this.mList.size();
                    ((ArticleInfo) this.mList.get(size - 1)).viewhight = hight >= 0 ? hight : 1;
                    if (hight < 0) {
                        hight = 1;
                    }
                    this.mCommentEmptyHight = hight;
                    this.mArticleDetailAdapter.notifyItemChanged(size - 1);
                    this.mIsCommentChangeEmptyHight = true;
                } else if (this.mType == 3 && this.mIsPraiseNeedEmpty && !this.mIsPraiseChangeEmptyHight) {
                    size = this.mList.size();
                    ((ArticleInfo) this.mList.get(size - 1)).viewhight = hight >= 0 ? hight : 1;
                    if (hight < 0) {
                        hight = 1;
                    }
                    this.mPraiseEmptyHight = hight;
                    this.mArticleDetailAdapter.notifyItemChanged(size - 1);
                    this.mIsPraiseChangeEmptyHight = true;
                }
            }
        } else if (lastVisiblePosition == (dataSize + 1) + 1) {
            tabBarTop = 0;
            for (i = 0; i < lastVisiblePosition - firstVisiblePosition; i++) {
                view = lm.getChildAt(i);
                if (view.getTag() != null && view.getTag().toString().equals("tab_bar")) {
                    tabBarTop = view.getTop();
                    break;
                }
            }
            view = lm.getChildAt(lastVisiblePosition - firstVisiblePosition);
            if (lastVisiblePosition - 1 < this.mList.size()) {
                info = (ArticleInfo) this.mList.get(lastVisiblePosition - 1);
                hight = this.mValidRecyclerViewHight - (view.getBottom() - tabBarTop);
                if (this.mType == 1) {
                    this.mCommentEmptyHight = hight >= 0 ? hight : 0;
                    this.mIsCommentChangeEmptyHight = true;
                } else if (this.mType == 3) {
                    this.mIsPraiseChangeEmptyHight = true;
                    this.mPraiseEmptyHight = hight >= 0 ? hight : 0;
                }
                if (hight < 0) {
                    hight = 0;
                }
                info.viewhight = hight;
                this.mArticleDetailAdapter.notifyItemChanged(lastVisiblePosition - 1);
            }
        } else if (lastVisiblePosition != (dataSize + 1) + 2) {
        } else {
            if ((this.mType == 3 && !this.mIsPraiseChangeEmptyHight) || (this.mType == 1 && !this.mIsCommentChangeEmptyHight)) {
                view = lm.getChildAt(lastVisiblePosition - firstVisiblePosition);
                if (view.getTag() != null && view.getTag().toString().equals("empty_view")) {
                    tabBarTop = 0;
                    for (i = 0; i < lastVisiblePosition - firstVisiblePosition; i++) {
                        childView = lm.getChildAt(i);
                        if (childView.getTag() != null && childView.getTag().toString().equals("tab_bar")) {
                            tabBarTop = childView.getTop();
                            break;
                        }
                    }
                    info = (ArticleInfo) this.mList.get(lastVisiblePosition - 2);
                    hight = this.mValidRecyclerViewHight - (view.getTop() - tabBarTop);
                    if (this.mType == 1) {
                        this.mCommentEmptyHight = hight >= 0 ? hight : 0;
                        this.mIsCommentChangeEmptyHight = true;
                    } else if (this.mType == 3) {
                        this.mPraiseEmptyHight = hight >= 0 ? hight : 0;
                        this.mIsPraiseChangeEmptyHight = true;
                    }
                    if (hight < 0) {
                        hight = 0;
                    }
                    info.viewhight = hight;
                    this.mArticleDetailAdapter.notifyItemChanged(lastVisiblePosition - 2);
                }
            }
        }
    }

    private void showHintEmpty() {
        int size = this.mList.size();
        if (size > 0) {
            ArticleInfo info = (ArticleInfo) this.mList.get(size - 1);
            if (this.mType == 3) {
                if (this.mPraiseEmptyHight == 0) {
                    this.mPraiseEmptyHight = this.mValidRecyclerViewHight - this.mResources.getDimensionPixelSize(R.dimen.default_98PX);
                }
                if (info.type == 6) {
                    info.viewhight = this.mPraiseEmptyHight;
                }
                this.mIsPraiseChangeEmptyHight = true;
                this.mArticleDetailAdapter.notifyItemChanged(size - 1);
            } else if (this.mType == 1) {
                if (this.mCommentEmptyHight == 0) {
                    this.mCommentEmptyHight = this.mValidRecyclerViewHight - this.mResources.getDimensionPixelSize(R.dimen.default_98PX);
                }
                if (info.type == 6) {
                    info.viewhight = this.mCommentEmptyHight;
                }
                this.mIsCommentChangeEmptyHight = true;
                this.mArticleDetailAdapter.notifyItemChanged(size - 1);
            }
        }
    }

    private void resetEmptyParameter() {
        this.mIsPraiseChangeEmptyHight = false;
        this.mPraiseEmptyHight = 0;
        this.mIsPraiseEmpty = false;
        this.mIsPraiseNeedEmpty = false;
        this.mIsCommentChangeEmptyHight = false;
        this.mCommentEmptyHight = 0;
        this.mIsCommentEmpty = false;
        this.mIsCommentNeedEmpty = false;
    }

    private void changeTabInitEmpty() {
        int size;
        if (this.mType == 3 && this.mPraiseEmptyHight != 0 && ((this.mIsPraiseEmpty || this.mIsPraiseNeedEmpty) && this.mIsPraiseChangeEmptyHight)) {
            size = this.mList.size();
            ((ArticleInfo) this.mList.get(size - 1)).viewhight = this.mPraiseEmptyHight;
            this.mArticleDetailAdapter.notifyItemChanged(size - 1);
        } else if (this.mType == 1 && this.mCommentEmptyHight != 0) {
            if ((this.mIsCommentEmpty || this.mIsCommentNeedEmpty) && this.mIsCommentChangeEmptyHight) {
                size = this.mList.size();
                ((ArticleInfo) this.mList.get(size - 1)).viewhight = this.mCommentEmptyHight;
                this.mArticleDetailAdapter.notifyItemChanged(size - 1);
            }
        }
    }

    private void updateViewPosition(int position, int offset) {
        this.mPosition = position;
        this.mOffset = offset;
    }

    private void parseThread() {
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).rlComment.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (VideoFinalPageActivity.this.mSnsInfo.commentcount.get() > 0) {
                    LinearLayoutManager lm = (LinearLayoutManager) ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.getLayoutManager();
                    int lastPosition = lm.findFirstVisibleItemPosition();
                    int[] locations = new int[2];
                    View view = lm.getChildAt(0);
                    if (view != null) {
                        view.getLocationInWindow(locations);
                        if (VideoFinalPageActivity.this.mLastOffset != 0) {
                            if (lastPosition < 2 && VideoFinalPageActivity.this.mLastPosition < 2) {
                                VideoFinalPageActivity.this.mLastPosition = VideoFinalPageActivity.this.mCurrentPosition;
                                VideoFinalPageActivity.this.mLastOffset = VideoFinalPageActivity.this.mCurrentOffset;
                            }
                            lm.scrollToPositionWithOffset(VideoFinalPageActivity.this.mLastPosition, VideoFinalPageActivity.this.mLastOffset - (VideoFinalPageActivity.this.getResources().getDimensionPixelSize(R.dimen.default_88PX) + FengApplication.getInstance().getStatusBarHeight()));
                            VideoFinalPageActivity.this.mCurrentPosition = VideoFinalPageActivity.this.mLastPosition;
                            VideoFinalPageActivity.this.mCurrentOffset = VideoFinalPageActivity.this.mLastOffset;
                        } else if (VideoFinalPageActivity.this.mIsShowTopBar) {
                            lm.scrollToPositionWithOffset(0, 0);
                        } else {
                            lm.scrollToPositionWithOffset(2, 0);
                        }
                        VideoFinalPageActivity.this.mLastOffset = locations[1];
                        VideoFinalPageActivity.this.mLastPosition = lastPosition;
                        return;
                    }
                    return;
                }
                VideoFinalPageActivity.this.mSnsInfo.intentToSendComment(VideoFinalPageActivity.this);
            }
        });
        if (this.mSnsInfo.isoldthread == 1) {
            this.mSnsInfo.description.set("");
        }
        this.mHeader.setMSnsInfo(this.mSnsInfo);
        if (this.mSnsInfo.user.getIsMy() == 1) {
            this.mHeader.urvFianlpageTop.setVisibility(8);
            this.mHeader.llExposureRead.setVisibility(0);
            this.mHeader.tvExposureNum.setText(FengUtil.numberFormat(this.mSnsInfo.exposurecount.get()));
            this.mHeader.tvReadNum.setText(FengUtil.numberFormat(this.mSnsInfo.cilckcount.get()));
        } else {
            this.mHeader.urvFianlpageTop.setUserInfo(this.mSnsInfo.user);
            this.mHeader.llExposureRead.setVisibility(8);
        }
        this.mHeader.title.setMatchLink(false);
        this.mHeader.title.setContent((String) this.mSnsInfo.title.get(), true);
        this.mHeader.describe.setContent((String) this.mSnsInfo.description.get(), true);
        this.mHeader.userImage.setHeadUrl(FengUtil.getHeadImageUrl(this.mSnsInfo.user.getHeadImageInfo()));
        this.mHeader.userImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                VideoFinalPageActivity.this.mSnsInfo.user.intentToPersonalHome(VideoFinalPageActivity.this);
            }
        });
        this.mHeader.recommendView.setSnsInfo(this.mSnsInfo, this.mSwipeBackLayout);
        this.mHeader.recommendView.setAdInfo(this.mAdvertInfo);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).setMSnsInfo(this.mSnsInfo);
        this.mArticleDetailAdapter.setmSnsInfo(this.mSnsInfo);
        this.mArticleDetailAdapter.setmCommentId(this.mCommentId);
    }

    private void parseThreadComment(List<CommentInfo> list, int locationType) {
        if (locationType == 1) {
            this.mList.clear();
            this.mList.add(this.mTabArticleInfo);
        }
        if (list != null && list.size() > 0) {
            for (CommentInfo info : list) {
                if (info != null) {
                    ArticleInfo articleInfo = new ArticleInfo();
                    articleInfo.commentInfo = info;
                    articleInfo.type = 2;
                    this.mList.add(articleInfo);
                }
            }
        }
        int size = this.mCommentOrderid == 1 ? this.mHotCommentList.size() : this.mTimeCommentList.size();
        boolean isEmpty = false;
        if (size < 15) {
            this.mIsCommentNeedEmpty = true;
            ArticleInfo info2 = new ArticleInfo();
            if (size == 0) {
                info2.type = 6;
                isEmpty = true;
            } else {
                info2.type = 7;
                isEmpty = false;
            }
            info2.viewhight = this.mCommentEmptyHight > 0 ? this.mCommentEmptyHight : 1920;
            this.mList.add(info2);
        } else {
            this.mIsCommentNeedEmpty = false;
        }
        this.mLRecyclerViewAdapter.notifyDataSetChanged();
        if (this.mFirstLocation != -1) {
            ((LinearLayoutManager) ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.getLayoutManager()).scrollToPositionWithOffset(2, 0);
            this.mFirstLocation = -1;
        } else if (locationType == 1) {
            ((LinearLayoutManager) ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.getLayoutManager()).scrollToPositionWithOffset(this.mPosition, isEmpty ? 0 : this.mOffset);
        }
        if (!((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.isShown()) {
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.setVisibility(0);
        }
    }

    private void loadCommentData(final int locationType) {
        Map<String, Object> map = new HashMap();
        map.put("snsid", String.valueOf(this.mSnsId));
        map.put("resourceid", String.valueOf(this.mResourceId));
        map.put("resourcetype", String.valueOf(this.mResourceType));
        map.put("page", String.valueOf(getCurrentPage()));
        map.put("orderid", String.valueOf(this.mCommentOrderid));
        FengApplication.getInstance().httpRequest("comment/getlist/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                VideoFinalPageActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                VideoFinalPageActivity.this.hideProgress();
            }

            public void onStart() {
            }

            public void onFinish() {
                VideoFinalPageActivity.this.mIsRefresh = false;
                ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView, State.Normal);
                VideoFinalPageActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                VideoFinalPageActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (VideoFinalPageActivity.this.getCurrentPage() == 1) {
                    VideoFinalPageActivity.this.mIsCommentEmpty = true;
                    VideoFinalPageActivity.this.parseThreadComment(null, locationType);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        if (!VideoFinalPageActivity.this.loadCommentSuccess) {
                            VideoFinalPageActivity.this.loadCommentSuccess = true;
                        }
                        JSONObject commentJson = resultJson.getJSONObject("body").getJSONObject("comment");
                        BaseListModel<CommentInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CommentInfo.class, commentJson);
                        VideoFinalPageActivity.this.setTotalPage(baseListModel.pagecount);
                        List<CommentInfo> list = baseListModel.list;
                        if (VideoFinalPageActivity.this.getCurrentPage() == 1) {
                            VideoFinalPageActivity.this.mList.clear();
                            VideoFinalPageActivity.this.mList.add(VideoFinalPageActivity.this.mTabArticleInfo);
                        }
                        if (list.size() > 0) {
                            VideoFinalPageActivity.this.mIsCommentEmpty = false;
                            if (VideoFinalPageActivity.this.mCommentOrderid == 1) {
                                if (VideoFinalPageActivity.this.getCurrentPage() == 1) {
                                    VideoFinalPageActivity.this.mHotCommentList.clear();
                                }
                                VideoFinalPageActivity.this.mHotCommentList.addAll(list);
                            } else if (VideoFinalPageActivity.this.mCommentOrderid == 2) {
                                if (VideoFinalPageActivity.this.getCurrentPage() == 1) {
                                    VideoFinalPageActivity.this.mTimeCommentList.clear();
                                }
                                VideoFinalPageActivity.this.mTimeCommentList.addAll(list);
                            }
                            VideoFinalPageActivity.this.updateCurrentPage();
                            VideoFinalPageActivity.this.parseThreadComment(list, locationType);
                            return;
                        } else if (VideoFinalPageActivity.this.getCurrentPage() == 1) {
                            VideoFinalPageActivity.this.mIsCommentEmpty = true;
                            VideoFinalPageActivity.this.parseThreadComment(list, locationType);
                            return;
                        } else {
                            return;
                        }
                    }
                    FengApplication.getInstance().checkCode("comment/getlist/", code);
                } catch (JSONException e) {
                    VideoFinalPageActivity.this.showSecondTypeToast((int) R.string.analysis_data_failed);
                    FengApplication.getInstance().upLoadTryCatchLog("comment/getlist/", content, e);
                }
            }
        });
    }

    private void loadPraiseData(final int locationType) {
        Map<String, Object> map = new HashMap();
        map.put("snsid", String.valueOf(this.mSnsId));
        map.put("resourceid", String.valueOf(this.mResourceId));
        map.put("resourcetype", String.valueOf(this.mResourceType));
        map.put("page", String.valueOf(getCurrentPage()));
        FengApplication.getInstance().httpRequest("sns/praise/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                VideoFinalPageActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                VideoFinalPageActivity.this.hideProgress();
            }

            public void onStart() {
            }

            public void onFinish() {
                VideoFinalPageActivity.this.mIsRefresh = false;
                ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView, State.Normal);
                VideoFinalPageActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                VideoFinalPageActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (VideoFinalPageActivity.this.getCurrentPage() == 1) {
                    VideoFinalPageActivity.this.mIsPraiseEmpty = true;
                    VideoFinalPageActivity.this.parseThreadPraise(null, locationType);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        if (!VideoFinalPageActivity.this.loadPraiseSuccess) {
                            VideoFinalPageActivity.this.loadPraiseSuccess = true;
                        }
                        JSONObject userJson = resultJson.getJSONObject("body").getJSONObject("praise");
                        BaseListModel<GratuityRecordInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(GratuityRecordInfo.class, userJson);
                        VideoFinalPageActivity.this.setTotalPage(baseListModel.pagecount);
                        if (VideoFinalPageActivity.this.getCurrentPage() == 1) {
                            ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.setNoMore(false);
                            VideoFinalPageActivity.this.mList.clear();
                            VideoFinalPageActivity.this.mList.add(VideoFinalPageActivity.this.mTabArticleInfo);
                        }
                        List<GratuityRecordInfo> list = baseListModel.list;
                        GratuityRecordInfo gratuityInfo;
                        if (list.size() > 0) {
                            VideoFinalPageActivity.this.mIsPraiseEmpty = false;
                            if (VideoFinalPageActivity.this.getCurrentPage() == 1) {
                                VideoFinalPageActivity.this.mPraiseList.clear();
                            }
                            VideoFinalPageActivity.this.mPraiseList.addAll(list);
                            if (VideoFinalPageActivity.this.getCurrentPage() == VideoFinalPageActivity.this.getTotalPage() && VideoFinalPageActivity.this.mSnsInfo.anonymouspraisenum > 0) {
                                gratuityInfo = new GratuityRecordInfo();
                                gratuityInfo.local_praise_num = VideoFinalPageActivity.this.mSnsInfo.anonymouspraisenum;
                                VideoFinalPageActivity.this.mPraiseList.getPraiseList().add(gratuityInfo);
                                list.add(gratuityInfo);
                            }
                            VideoFinalPageActivity.this.updateCurrentPage();
                            VideoFinalPageActivity.this.parseThreadPraise(list, locationType);
                            return;
                        }
                        if (VideoFinalPageActivity.this.getCurrentPage() == VideoFinalPageActivity.this.getTotalPage() && VideoFinalPageActivity.this.mSnsInfo.anonymouspraisenum > 0) {
                            gratuityInfo = new GratuityRecordInfo();
                            gratuityInfo.local_praise_num = VideoFinalPageActivity.this.mSnsInfo.anonymouspraisenum;
                            VideoFinalPageActivity.this.mPraiseList.getPraiseList().add(gratuityInfo);
                            list.add(gratuityInfo);
                        }
                        if (list.size() > 0) {
                            VideoFinalPageActivity.this.parseThreadPraise(list, locationType);
                            return;
                        } else if (VideoFinalPageActivity.this.getCurrentPage() == 1) {
                            VideoFinalPageActivity.this.mIsPraiseEmpty = true;
                            VideoFinalPageActivity.this.parseThreadPraise(list, locationType);
                            return;
                        } else {
                            return;
                        }
                    }
                    FengApplication.getInstance().checkCode("sns/praise/", code);
                } catch (Exception e) {
                    FengApplication.getInstance().upLoadTryCatchLog("sns/praise/", content, e);
                }
            }
        });
    }

    private void parseThreadPraise(List<GratuityRecordInfo> list, int locationType) {
        ArticleInfo info;
        if (locationType == 1) {
            this.mList.clear();
            this.mList.add(this.mTabArticleInfo);
        }
        if (list != null && list.size() > 0) {
            for (GratuityRecordInfo gratuityRecordInfo : list) {
                info = new ArticleInfo();
                info.gratuityInfo = gratuityRecordInfo;
                info.type = 3;
                this.mList.add(info);
            }
        }
        boolean isEmpty = false;
        if (this.mPraiseList.size() < 15) {
            this.mIsPraiseNeedEmpty = true;
            info = new ArticleInfo();
            if (this.mPraiseList.size() == 0) {
                info.type = 6;
                isEmpty = true;
            } else {
                info.type = 7;
                isEmpty = false;
            }
            info.viewhight = 1920;
            this.mList.add(info);
        } else {
            this.mIsPraiseNeedEmpty = false;
        }
        this.mLRecyclerViewAdapter.notifyDataSetChanged();
        if (this.mFirstLocation != -1) {
            ((LinearLayoutManager) ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.getLayoutManager()).scrollToPositionWithOffset(2, 0);
            this.mFirstLocation = -1;
        } else if (locationType == 1) {
            ((LinearLayoutManager) ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.getLayoutManager()).scrollToPositionWithOffset(this.mPosition, isEmpty ? 0 : this.mOffset);
        }
        if (!((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.isShown()) {
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.setVisibility(0);
        }
    }

    private void changeTabColor(int type) {
        if (type == 1) {
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).commentText.setSelected(true);
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).praiseText.setSelected(false);
        } else if (type == 3) {
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).commentText.setSelected(false);
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).praiseText.setSelected(true);
        }
    }

    private void changeTab(int type) {
        this.mType = type;
        this.mArticleDetailAdapter.setType(this.mType);
        if (getCurrentPage() <= getTotalPage()) {
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.setNoMore(false);
        }
        if (this.mType == 1) {
            changeTabColor(1);
            if (this.mCommentOrderid == 1) {
                if (this.mHotCommentList.size() > 0) {
                    parseThreadComment(this.mHotCommentList.getCommentList(), 1);
                } else {
                    loadBottomData(1);
                }
            } else if (this.mTimeCommentList.size() > 0) {
                parseThreadComment(this.mTimeCommentList.getCommentList(), 1);
            } else {
                loadBottomData(1);
            }
        } else if (this.mType == 3) {
            changeTabColor(3);
            if (this.mPraiseList.size() > 0) {
                parseThreadPraise(this.mPraiseList.getPraiseList(), 1);
            } else {
                loadBottomData(1);
            }
        }
        changeTabInitEmpty();
    }

    private void loadBottomData(int locationType, boolean isShowLoad) {
        if (isShowLoad) {
        }
        if (this.mType == 1) {
            loadCommentData(locationType);
        } else if (this.mType == 3) {
            loadPraiseData(locationType);
        }
    }

    private void loadBottomData(int locationType) {
        loadBottomData(locationType, true);
    }

    public void onSingleClick(View view) {
        switch (view.getId()) {
            case R.id.iv_forward /*2131624247*/:
                showShareDialog(true);
                return;
            case R.id.commentText /*2131624251*/:
                if (this.mType != 1) {
                    changeTab(1);
                    return;
                }
                return;
            case R.id.praiseText /*2131624252*/:
                if (this.mType != 3) {
                    changeTab(3);
                    return;
                }
                return;
            case R.id.cancel /*2131624291*/:
                hideShareDialog();
                return;
            case R.id.arrayHot /*2131624784*/:
                break;
            case R.id.arrayTime /*2131624785*/:
                if (this.mCommentOrderid != 2) {
                    this.mCommentOrderid = 2;
                    this.mCommentArrayBinding.arrayHot.setBackgroundResource(17170445);
                    this.mCommentArrayBinding.arrayHot.setTextColor(this.mResources.getColor(R.color.color_000000));
                    this.mCommentArrayBinding.arrayTime.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                    this.mCommentArrayBinding.arrayTime.setTextColor(this.mResources.getColor(R.color.color_ffffff));
                    this.mCommentArrayBinding.arrayHot.setPadding(this.m8, this.m8, this.m8, this.m8);
                    this.mCommentArrayBinding.arrayTime.setPadding(this.m8, this.m8, this.m8, this.m8);
                    if (this.mArticleDetailAdapter.getArrayHolder() != null) {
                        this.mArticleDetailAdapter.getArrayHolder().mBinding.arrayText.setText(R.string.comment_timearray);
                        this.mArticleDetailAdapter.setmCommentOrderid(this.mCommentOrderid);
                    }
                    changeTab(1);
                }
                hideCommentArrayWindow();
                return;
            case R.id.array_text /*2131624787*/:
                showArraryDialog(view);
                return;
            case R.id.userImage /*2131624802*/:
                this.mSnsInfo.user.intentToPersonalHome(this);
                break;
            case R.id.friendsShare /*2131624822*/:
                share(SHARE_MEDIA.WEIXIN_CIRCLE);
                hideShareDialog();
                return;
            case R.id.weixinShare /*2131624823*/:
                this.mSnsInfo.shareToWeiXin(this, this.mIwxapi);
                hideShareDialog();
                return;
            case R.id.weiboShare /*2131624824*/:
                share(SHARE_MEDIA.SINA);
                hideShareDialog();
                return;
            case R.id.qqShare /*2131624825*/:
                share(SHARE_MEDIA.QQ);
                hideShareDialog();
                return;
            case R.id.qzoneShare /*2131624826*/:
                share(SHARE_MEDIA.QZONE);
                hideShareDialog();
                return;
            case R.id.collectText /*2131624827*/:
                this.mSnsInfo.collectOperation(this, null);
                hideShareDialog();
                return;
            case R.id.copyLink /*2131624828*/:
                if (this.mSnsInfo.snstype == 0 || this.mSnsInfo.snstype == 1 || this.mSnsInfo.snstype == 9) {
                    this.mSnsInfo.copySnsUrl(this, 1);
                }
                hideShareDialog();
                return;
            case R.id.edit /*2131624829*/:
                this.mSnsInfo.editOperation(this);
                hideShareDialog();
                return;
            case R.id.delete /*2131624830*/:
                this.mSnsInfo.deleteOperation(this, null);
                hideShareDialog();
                return;
            case R.id.follow /*2131624832*/:
                this.mSnsInfo.user.followOperation(this, null);
                hideShareDialog();
                return;
            case R.id.report /*2131624833*/:
                this.mSnsInfo.reportOperation(this);
                return;
            case R.id.backHome /*2131624834*/:
                startActivity(new Intent(this, HomeActivity.class));
                return;
            default:
                return;
        }
        if (this.mCommentOrderid != 1) {
            this.mCommentOrderid = 1;
            this.mCommentArrayBinding.arrayHot.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mCommentArrayBinding.arrayHot.setTextColor(this.mResources.getColor(R.color.color_ffffff));
            this.mCommentArrayBinding.arrayTime.setBackgroundResource(17170445);
            this.mCommentArrayBinding.arrayTime.setTextColor(this.mResources.getColor(R.color.color_000000));
            this.mCommentArrayBinding.arrayHot.setPadding(this.m8, this.m8, this.m8, this.m8);
            this.mCommentArrayBinding.arrayTime.setPadding(this.m8, this.m8, this.m8, this.m8);
            if (this.mArticleDetailAdapter.getArrayHolder() != null) {
                this.mArticleDetailAdapter.getArrayHolder().mBinding.arrayText.setText(R.string.comment_hotarray);
                this.mArticleDetailAdapter.setmCommentOrderid(this.mCommentOrderid);
            }
            changeTab(1);
        }
        hideCommentArrayWindow();
    }

    private void share(SHARE_MEDIA media) {
        if (this.mSnsInfo != null) {
            if (this.mSnsInfo.snstype == 0) {
                this.mSnsInfo.socialShare(this, media, this.umShareListener, 1);
            }
            if (this.mSnsInfo.snstype == 9) {
                this.mSnsInfo.socialShare(this, media, this.umShareListener, 4);
            }
        }
    }

    private void showShareDialog(boolean isForward) {
        if (this.mArticleShareBinding == null) {
            this.mArticleShareBinding = ArticleShareDialogBinding.inflate(LayoutInflater.from(this));
            this.mArticleShareBinding.setUserInfo(this.mSnsInfo.user);
            this.mArticleShareBinding.setMSnsInfo(this.mSnsInfo);
            this.mArticleShareBinding.friendsShare.setOnClickListener(this);
            this.mArticleShareBinding.weixinShare.setOnClickListener(this);
            this.mArticleShareBinding.weiboShare.setOnClickListener(this);
            this.mArticleShareBinding.qqShare.setOnClickListener(this);
            this.mArticleShareBinding.qzoneShare.setOnClickListener(this);
            this.mArticleShareBinding.collectText.setOnClickListener(this);
            this.mArticleShareBinding.copyLink.setOnClickListener(this);
            this.mArticleShareBinding.edit.setOnClickListener(this);
            this.mArticleShareBinding.delete.setOnClickListener(this);
            this.mArticleShareBinding.report.setOnClickListener(this);
            this.mArticleShareBinding.backHome.setOnClickListener(this);
            this.mArticleShareBinding.cancel.setOnClickListener(this);
            this.mArticleShareBinding.download.setOnClickListener(this.mDownloadImageListener);
        }
        this.mArticleShareBinding.follow.setVisibility(8);
        if (this.mSnsInfo.user.getIsMy() != 1) {
            this.downloadDrawable = this.mResources.getDrawable(R.drawable.download_video_icon_selector);
            this.downloadDrawable.setBounds(0, 0, this.downloadDrawable.getMinimumWidth(), this.downloadDrawable.getMinimumHeight());
            this.downloadCompleteDrawable = this.mResources.getDrawable(R.drawable.icon_download_complete);
            this.downloadCompleteDrawable.setBounds(0, 0, this.downloadCompleteDrawable.getMinimumWidth(), this.downloadCompleteDrawable.getMinimumHeight());
            if (this.mIsVideoCache) {
                this.mArticleShareBinding.download.setCompoundDrawables(null, this.downloadCompleteDrawable, null, null);
                this.mArticleShareBinding.download.setText(R.string.cache_complete);
                this.mArticleShareBinding.download.setClickable(false);
            } else if (VideoDownloadManager.newInstance().hasDownloadVideo(this.mMediaInfo)) {
                this.mArticleShareBinding.download.setCompoundDrawables(null, this.downloadCompleteDrawable, null, null);
                this.mArticleShareBinding.download.setText(R.string.cache_complete);
                this.mArticleShareBinding.download.setClickable(false);
            } else {
                this.mArticleShareBinding.download.setCompoundDrawables(null, this.downloadDrawable, null, null);
                this.mArticleShareBinding.download.setText(R.string.cache);
            }
            this.mArticleShareBinding.download.setVisibility(0);
        }
        if (isForward) {
            this.mArticleShareBinding.placeView.setVisibility(0);
            this.mArticleShareBinding.placeView1.setVisibility(0);
            this.mArticleShareBinding.placeView2.setVisibility(0);
            this.mArticleShareBinding.collectText.setVisibility(8);
            this.mArticleShareBinding.edit.setVisibility(8);
            this.mArticleShareBinding.delete.setVisibility(8);
            this.mArticleShareBinding.report.setVisibility(8);
            this.mArticleShareBinding.backHome.setVisibility(8);
        } else {
            this.mArticleShareBinding.placeView.setVisibility(8);
            this.mArticleShareBinding.placeView1.setVisibility(8);
            this.mArticleShareBinding.placeView2.setVisibility(8);
            this.mArticleShareBinding.collectText.setVisibility(0);
            this.mArticleShareBinding.backHome.setVisibility(0);
            if (this.mResourceType == 9) {
                this.mArticleShareBinding.collectText.setVisibility(8);
                if (this.mSnsInfo.user.getIsMy() == 1) {
                    this.mArticleShareBinding.edit.setVisibility(8);
                    this.mArticleShareBinding.delete.setVisibility(8);
                    this.mArticleShareBinding.report.setVisibility(8);
                    this.mArticleShareBinding.placeView.setVisibility(0);
                    this.mArticleShareBinding.placeView1.setVisibility(0);
                    this.mArticleShareBinding.placeView2.setVisibility(0);
                } else {
                    this.mArticleShareBinding.edit.setVisibility(8);
                    this.mArticleShareBinding.delete.setVisibility(8);
                    this.mArticleShareBinding.report.setVisibility(8);
                    this.mArticleShareBinding.placeView.setVisibility(0);
                    this.mArticleShareBinding.placeView1.setVisibility(0);
                }
            } else if (this.mResourceType == 10) {
                this.mArticleShareBinding.collectText.setVisibility(8);
                if (this.mSnsInfo.user.getIsMy() == 1) {
                    this.mArticleShareBinding.edit.setVisibility(8);
                    this.mArticleShareBinding.delete.setVisibility(0);
                    this.mArticleShareBinding.report.setVisibility(8);
                    this.mArticleShareBinding.placeView.setVisibility(0);
                    this.mArticleShareBinding.placeView1.setVisibility(0);
                } else {
                    this.mArticleShareBinding.edit.setVisibility(8);
                    this.mArticleShareBinding.delete.setVisibility(8);
                    this.mArticleShareBinding.report.setVisibility(0);
                    this.mArticleShareBinding.placeView.setVisibility(0);
                }
            } else if (this.mResourceType == 0) {
                if (this.mSnsInfo.user.getIsMy() == 1) {
                    this.mArticleShareBinding.edit.setVisibility(0);
                    this.mArticleShareBinding.delete.setVisibility(0);
                    this.mArticleShareBinding.report.setVisibility(8);
                } else {
                    this.mArticleShareBinding.edit.setVisibility(8);
                    this.mArticleShareBinding.delete.setVisibility(8);
                    this.mArticleShareBinding.report.setVisibility(0);
                }
            } else if (this.mResourceType == 2) {
                this.mArticleShareBinding.edit.setVisibility(8);
                this.mArticleShareBinding.placeView.setVisibility(0);
                if (this.mSnsInfo.user.getIsMy() == 1) {
                    this.mArticleShareBinding.delete.setVisibility(0);
                    this.mArticleShareBinding.report.setVisibility(8);
                } else {
                    this.mArticleShareBinding.delete.setVisibility(8);
                    this.mArticleShareBinding.report.setVisibility(0);
                }
            }
        }
        if (this.mShareDialog == null) {
            this.mShareDialog = new Dialog(this, R.style.ArticleShareDialog);
            this.mShareDialog.setCanceledOnTouchOutside(true);
            this.mShareDialog.setCancelable(true);
            Window window = this.mShareDialog.getWindow();
            window.setGravity(80);
            window.setWindowAnimations(R.style.shareAnimation);
            window.setContentView(this.mArticleShareBinding.getRoot());
            window.setLayout(-1, -2);
            this.mShareDialog.setOnCancelListener(new OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    VideoFinalPageActivity.this.videoPlaying();
                }
            });
        }
        this.mShareDialog.show();
        videoPause();
    }

    private void hideShareDialog() {
        if (this.mShareDialog != null) {
            this.mShareDialog.dismiss();
        }
        videoPlaying();
    }

    private void hideCommentArrayWindow() {
        if (this.mCommentArrayWindow != null && this.mCommentArrayWindow.isShowing()) {
            this.mCommentArrayWindow.dismiss();
        }
    }

    private void showArraryDialog(View view) {
        if (this.mCommentArrayBinding == null) {
            this.mCommentArrayBinding = ArticleCommentArraydialogBinding.inflate(LayoutInflater.from(this));
            this.mCommentArrayBinding.arrayHot.setOnClickListener(this);
            this.mCommentArrayBinding.arrayTime.setOnClickListener(this);
        }
        if (this.mCommentOrderid == 1) {
            this.mCommentArrayBinding.arrayHot.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mCommentArrayBinding.arrayTime.setBackgroundResource(17170445);
        } else {
            this.mCommentArrayBinding.arrayHot.setBackgroundResource(17170445);
            this.mCommentArrayBinding.arrayTime.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
        }
        this.mCommentArrayBinding.arrayHot.setPadding(this.m8, this.m8, this.m8, this.m8);
        this.mCommentArrayBinding.arrayTime.setPadding(this.m8, this.m8, this.m8, this.m8);
        if (this.mCommentArrayWindow == null) {
            this.mCommentArrayWindow = new PopupWindow(this.mCommentArrayBinding.getRoot(), this.m220, this.m184, true);
        }
        this.mCommentArrayWindow.setFocusable(true);
        this.mCommentArrayWindow.setOutsideTouchable(true);
        this.mCommentArrayWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, 17170445)));
        int[] loc_int = new int[2];
        view.getLocationOnScreen(loc_int);
        Rect location = new Rect();
        location.right = loc_int[0] + view.getWidth();
        location.bottom = loc_int[1] + view.getHeight();
        this.mCommentArrayWindow.showAtLocation(view, 53, location.left, location.bottom - 10);
    }

    public void onArrayChanged(View view) {
        showArraryDialog(view);
    }

    public void onCommentSelected() {
        changeTab(1);
    }

    public void onPraiseSelected() {
        changeTab(3);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (event.userInfo != null && event.userInfo.id == this.mSnsInfo.user.id) {
            if (event.type == 2) {
                this.mSnsInfo.user.isfollow.set(event.userInfo.isfollow.get());
                this.mHeader.urvFianlpageTop.changeStatus(1);
            } else if (event.type == 1) {
                if (!(StringUtil.isEmpty((String) event.userInfo.name.get()) || ((String) this.mSnsInfo.user.name.get()).equals(event.userInfo.name.get()))) {
                    this.mSnsInfo.user.name.set(event.userInfo.name.get());
                }
                if (!StringUtil.isEmpty(event.userInfo.getHeadImageInfo().url) && !this.mSnsInfo.user.getHeadImageInfo().url.equals(event.userInfo.getHeadImageInfo().url)) {
                    this.mSnsInfo.user.getHeadImageInfo().width = event.userInfo.getHeadImageInfo().width;
                    this.mSnsInfo.user.getHeadImageInfo().height = event.userInfo.getHeadImageInfo().height;
                    this.mSnsInfo.user.getHeadImageInfo().url = event.userInfo.getHeadImageInfo().url;
                    this.mSnsInfo.user.getHeadImageInfo().hash = event.userInfo.getHeadImageInfo().hash;
                    this.mSnsInfo.user.getHeadImageInfo().mimetype = event.userInfo.getHeadImageInfo().mimetype;
                    this.mHeader.userImage.setHeadUrl(FengUtil.getHeadImageUrl(this.mSnsInfo.user.getHeadImageInfo()));
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoModifyEvent event) {
        if (event.snsInfo != null && event.snsInfo.id == this.mSnsInfo.id) {
            getAdData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event.snsInfo != null && event.snsInfo.id == this.mSnsInfo.id) {
            ArticleInfo articleInfo;
            switch (event.refreshType) {
                case 2001:
                    this.mSnsInfo.isfavorite.set(event.snsInfo.isfavorite.get());
                    return;
                case ShowBigImageActivity.ENTRANCE_TYPE_ARTICLE_IMAGE /*2003*/:
                    this.mSnsInfo.commentcount.set(event.snsInfo.commentcount.get());
                    int commentPos;
                    BaseListModel baseListModel;
                    if (this.mType == 1) {
                        if (event.commentInfo.originparentid == 0) {
                            articleInfo = new ArticleInfo();
                            articleInfo.commentInfo = event.commentInfo;
                            articleInfo.type = 2;
                            this.mList.add(1, articleInfo);
                            if (this.mCommentOrderid == 1) {
                                this.mHotCommentList.add(0, event.commentInfo);
                            } else {
                                this.mTimeCommentList.add(0, event.commentInfo);
                            }
                        } else if (this.mCommentOrderid == 1) {
                            commentPos = this.mHotCommentList.getPosition(event.commentInfo.originparentid);
                            if (commentPos != -1) {
                                baseListModel = this.mHotCommentList.get(commentPos).reply;
                                baseListModel.count++;
                                this.mHotCommentList.get(commentPos).reply.list.add(0, event.commentInfo);
                            }
                        } else {
                            commentPos = this.mTimeCommentList.getPosition(event.commentInfo.originparentid);
                            if (commentPos != -1) {
                                baseListModel = this.mTimeCommentList.get(commentPos).reply;
                                baseListModel.count++;
                                this.mTimeCommentList.get(commentPos).reply.list.add(0, event.commentInfo);
                            }
                        }
                        if (this.mIsCommentNeedEmpty || this.mIsCommentEmpty) {
                            ((ArticleInfo) this.mList.get(this.mList.size() - 1)).type = 7;
                            this.mIsCommentChangeEmptyHight = false;
                            this.mCommentEmptyHight = 0;
                            this.mIsCommentEmpty = false;
                            this.mIsCommentNeedEmpty = true;
                        }
                        this.mLRecyclerViewAdapter.notifyDataSetChanged();
                        return;
                    } else if (!this.loadCommentSuccess) {
                        return;
                    } else {
                        if (event.commentInfo.originparentid == 0) {
                            if (this.mCommentOrderid == 1) {
                                this.mHotCommentList.add(0, event.commentInfo);
                                return;
                            } else {
                                this.mTimeCommentList.add(0, event.commentInfo);
                                return;
                            }
                        } else if (this.mCommentOrderid == 1) {
                            commentPos = this.mHotCommentList.getPosition(event.commentInfo.originparentid);
                            if (commentPos != -1) {
                                baseListModel = this.mHotCommentList.get(commentPos).reply;
                                baseListModel.count++;
                                this.mHotCommentList.get(commentPos).reply.list.add(0, event.commentInfo);
                                return;
                            }
                            return;
                        } else {
                            commentPos = this.mTimeCommentList.getPosition(event.commentInfo.originparentid);
                            if (commentPos != -1) {
                                baseListModel = this.mTimeCommentList.get(commentPos).reply;
                                baseListModel.count++;
                                this.mTimeCommentList.get(commentPos).reply.list.add(0, event.commentInfo);
                                return;
                            }
                            return;
                        }
                    }
                case 2004:
                    this.mSnsInfo.praisecount.set(event.snsInfo.praisecount.get());
                    this.mSnsInfo.ispraise.set(event.snsInfo.ispraise.get());
                    if (event.snsInfo.ispraise.get() == 1) {
                        articleInfo = new ArticleInfo();
                        articleInfo.gratuityInfo = event.gratuityRecordInfo;
                        articleInfo.type = 3;
                        if (this.mType == 3) {
                            this.mList.add(1, articleInfo);
                            this.mPraiseList.add(0, articleInfo.gratuityInfo);
                            if (this.mIsPraiseNeedEmpty || this.mIsPraiseEmpty) {
                                ((ArticleInfo) this.mList.get(this.mList.size() - 1)).type = 7;
                                this.mIsPraiseChangeEmptyHight = false;
                                this.mPraiseEmptyHight = 0;
                                this.mIsPraiseEmpty = false;
                                this.mIsPraiseNeedEmpty = true;
                            }
                            this.mArticleDetailAdapter.notifyDataSetChanged();
                            return;
                        } else if (this.loadPraiseSuccess) {
                            this.mPraiseList.add(0, articleInfo.gratuityInfo);
                            return;
                        } else {
                            return;
                        }
                    }
                    int praisePos = this.mPraiseList.getPosition(event.gratuityRecordInfo.id);
                    if (praisePos != -1) {
                        this.mPraiseList.remove(praisePos);
                        if (this.mType == 3) {
                            resetEmptyParameter();
                            parseThreadPraise(this.mPraiseList.getPraiseList(), 1);
                            return;
                        }
                        return;
                    }
                    return;
                case m_AppUI.MSG_APP_VERSION_FORCE /*2005*/:
                    getAdData();
                    return;
                case m_AppUI.MSG_APP_VERSION_COMMEND /*2006*/:
                    this.mSnsInfo.commentcount.set(event.snsInfo.commentcount.get());
                    int hotPos;
                    int timePos;
                    int index;
                    if (this.mType == 1) {
                        if (event.commentInfo.originparentid == 0) {
                            if (this.mCommentOrderid == 1) {
                                hotPos = this.mHotCommentList.getPosition(event.commentInfo.id);
                                if (hotPos != -1) {
                                    this.mHotCommentList.remove(hotPos);
                                    resetEmptyParameter();
                                    parseThreadComment(this.mHotCommentList.getCommentList(), 1);
                                    return;
                                }
                                return;
                            } else if (this.mCommentOrderid == 2) {
                                timePos = this.mTimeCommentList.getPosition(event.commentInfo.id);
                                if (timePos != -1) {
                                    this.mTimeCommentList.remove(timePos);
                                    resetEmptyParameter();
                                    parseThreadComment(this.mTimeCommentList.getCommentList(), 1);
                                    return;
                                }
                                return;
                            } else {
                                return;
                            }
                        } else if (this.mCommentOrderid == 1) {
                            hotPos = this.mHotCommentList.getPosition(event.commentInfo.originparentid);
                            if (hotPos != -1) {
                                index = this.mHotCommentList.get(hotPos).reply.list.indexOf(event.commentInfo);
                                if (index > -1) {
                                    ((CommentInfo) this.mHotCommentList.get(hotPos).reply.list.get(index)).isdel = 1;
                                    this.mLRecyclerViewAdapter.notifyDataSetChanged();
                                    return;
                                }
                                return;
                            }
                            return;
                        } else if (this.mCommentOrderid == 2) {
                            timePos = this.mTimeCommentList.getPosition(event.commentInfo.originparentid);
                            if (timePos != -1) {
                                index = this.mTimeCommentList.get(timePos).reply.list.indexOf(event.commentInfo);
                                if (index > -1) {
                                    ((CommentInfo) this.mTimeCommentList.get(timePos).reply.list.get(index)).isdel = 1;
                                    this.mLRecyclerViewAdapter.notifyDataSetChanged();
                                    return;
                                }
                                return;
                            }
                            return;
                        } else {
                            return;
                        }
                    } else if (event.commentInfo.originparentid == 0) {
                        hotPos = this.mHotCommentList.getPosition(event.commentid);
                        if (hotPos != -1) {
                            this.mHotCommentList.remove(hotPos);
                        }
                        timePos = this.mTimeCommentList.getPosition(event.commentid);
                        if (timePos != -1) {
                            this.mTimeCommentList.remove(timePos);
                            return;
                        }
                        return;
                    } else {
                        hotPos = this.mHotCommentList.getPosition(event.commentInfo.originparentid);
                        if (hotPos != -1) {
                            index = this.mHotCommentList.get(hotPos).reply.list.indexOf(event.commentInfo);
                            if (index > -1) {
                                ((CommentInfo) this.mHotCommentList.get(hotPos).reply.list.get(index)).isdel = 1;
                            }
                        }
                        timePos = this.mTimeCommentList.getPosition(event.commentInfo.originparentid);
                        if (timePos != -1) {
                            index = this.mTimeCommentList.get(timePos).reply.list.indexOf(event.commentInfo);
                            if (index > -1) {
                                ((CommentInfo) this.mTimeCommentList.get(timePos).reply.list.get(index)).isdel = 1;
                                return;
                            }
                            return;
                        }
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public void loginSuccess() {
        super.loginSuccess();
        if (FengApplication.getInstance().getVideoBdLoginFlag() == 1) {
            this.blueLogin = true;
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.changeDefination(4);
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.startWindowFullscreen(false);
            JCVideoPlayerManager.needStartFullScreen = false;
        } else if (FengApplication.getInstance().getVideoBdLoginFlag() == 2) {
            if (FengUtil.getAvailableExternalMemorySize() - this.mMediaInfo.videourl.bdinfo.size < 20971520) {
                showSecondTypeToast((int) R.string.sdcard_not_enough_to_download);
                return;
            }
            this.mMediaInfo.downloadDefination = 4;
            VideoDownloadManager.newInstance().downloadVideo(this.mMediaInfo, this.mSnsInfo, this.mMediaInfo.videourl.bdinfo);
            showDownloadToast();
        }
        FengApplication.getInstance().setVideoBdLoginFlag(0);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.forceToRefresh();
    }

    public void onPortraitCloseClick() {
        exitAnimator();
    }

    public void onBackPressed() {
        if (!((ActivityVideoFinalpageBinding) this.mBaseBinding).videoPlayer.backPress()) {
            exitAnimator();
        }
    }

    public void keepPortraitScreen() {
        setRequestedOrientation(1);
        if (this.sensorManager != null && this.sensorEventListener != null) {
            this.sensorManager.unregisterListener(this.sensorEventListener);
        }
    }

    public void keepLandscapeScreen() {
        if (JCVideoPlayerManager.getCurrentJcvd() != null) {
            setRequestedOrientation(6);
            if (this.sensorManager != null && this.sensorEventListener != null) {
                this.sensorManager.unregisterListener(this.sensorEventListener);
            }
        }
    }

    public void recoveryGravityScreen() {
        if (this.sensorManager == null) {
            this.sensorManager = (SensorManager) getSystemService("sensor");
        }
        if (this.sensorEventListener == null) {
            this.sensorEventListener = new JCAutoFullscreenListener(this);
        }
        this.sensorManager.registerListener(this.sensorEventListener, this.sensorManager.getDefaultSensor(1), 3);
    }

    public void onOptionClick() {
        if (this.mSnsInfo.id != 0) {
            showShareDialog(false);
        }
    }

    private void getPlayCount() {
        Map<String, Object> map = new HashMap();
        map.put("videoresourceid", String.valueOf(this.mMediaInfo.id));
        map.put("resourcetype", String.valueOf(this.mResourceType));
        FengApplication.getInstance().httpRequest("sns/getplaycount/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        JSONObject jsonObject = resultJson.getJSONObject("body");
                        if (jsonObject.has("playcount")) {
                            int nPlayCount = jsonObject.getInt("playcount");
                            VideoFinalPageActivity.this.mHeader.recommendView.setVideoPlayCount(nPlayCount);
                            VideoFinalPageActivity.this.mHeader.viewCount.setText("播放" + FengUtil.numberFormat(nPlayCount));
                            return;
                        }
                        FengApplication.getInstance().checkCode("sns/info/", code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ProgramFollowEvent event) {
        if (event != null && this.mSnsInfo.hotshow != null && event.id == this.mSnsInfo.hotshow.id) {
            this.mSnsInfo.hotshow.isfollow.set(event.isFollow);
            this.mSnsInfo.hotshow.isremind.set(event.isRemind);
            this.mHeader.recommendView.setSnsInfo(this.mSnsInfo, this.mSwipeBackLayout);
        }
    }

    public void onVideoEnterFullscreen() {
        super.onVideoEnterFullscreen();
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).getRoot().setFitsSystemWindows(false);
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).bottomLine.setVisibility(8);
    }

    public void onVideoQuitFullscreen() {
        super.onVideoQuitFullscreen();
        ((ActivityVideoFinalpageBinding) this.mBaseBinding).getRoot().setFitsSystemWindows(true);
        if (!this.mIsHintComment) {
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).bottomLine.setVisibility(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final SendCommentStartSlideEvent event) {
        if (event != null && !TextUtils.isEmpty(event.key) && this.mArticleDetailAdapter != null && this.mArticleDetailAdapter.getSoleKey().equals(event.key)) {
            if (LogGatherReadUtil.getInstance().getCurrentPage().equals("app_send_comment") && event.isFinish && ((ArticleInfo) this.mList.get(this.mList.size() - 1)).isextend) {
                this.mList.remove(this.mList.size() - 1);
                this.mArticleDetailAdapter.notifyDataSetChanged();
            }
            ((ActivityVideoFinalpageBinding) this.mBaseBinding).recyclerView.postDelayed(new Runnable() {
                public void run() {
                    ((ActivityVideoFinalpageBinding) VideoFinalPageActivity.this.mBaseBinding).recyclerView.smoothScrollBy(0, event.dy);
                }
            }, 100);
        }
    }

    public void on4GConnect() {
        if (!this.mIsVideoCache && !VideoUtil.hasClick4GPlay) {
            this.hasShowFlowTips = true;
            if (JCVideoPlayerManager.getCurrentJcvd() != null) {
                ((FengVideoPlayer) JCVideoPlayerManager.getCurrentJcvd()).showFlowTipsLine();
            }
        }
    }

    public void onWifiConnect() {
        if (!this.mIsVideoCache) {
            this.hasShowFlowTips = false;
            if (JCVideoPlayerManager.getCurrentJcvd() != null) {
                ((FengVideoPlayer) JCVideoPlayerManager.getCurrentJcvd()).backToWifiPlay();
            }
        }
    }

    public String getLogCurrentPage() {
        return "app_video_play_final?snsid=" + this.mSnsId + "&resourceid=" + this.mResourceId + "&resourcetype=" + this.mResourceType + "&cityid=" + MapUtil.newInstance().getCurrentCityId();
    }
}
