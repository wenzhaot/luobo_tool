package com.feng.car.activity;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.View;
import com.feng.car.R;
import com.feng.car.databinding.ActivityShortvideoSelectimageBinding;
import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import com.feng.car.event.ClosePageEvent;
import com.feng.car.event.ImageOrVideoPathEvent;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.PostDataManager;
import com.feng.car.video.shortvideo.VideoSelectImageTimeLine$ProgressChangedListner;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.tencent.ugc.TXVideoEditConstants.TXPreviewParam;
import com.tencent.ugc.TXVideoEditConstants.TXVideoInfo;
import com.tencent.ugc.TXVideoEditer;
import com.tencent.ugc.TXVideoEditer.TXThumbnailListener;
import com.tencent.ugc.TXVideoEditer.TXVideoPreviewListener;
import com.tencent.ugc.TXVideoInfoReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class ShortVideoSelectImageActivity extends BaseActivity<ActivityShortvideoSelectimageBinding> implements TXVideoPreviewListener, TXThumbnailListener {
    public static final String EDIT_COVER_FLAG = "edit_cover_flag";
    private boolean isFirst = true;
    private int mCount;
    private int mCurrentState = 0;
    private long mCurrentTime;
    private boolean mIsEditCover = false;
    private TXVideoEditer mTXVideoEditer;
    private TXVideoInfo mTxVideoInfo;
    private String mVideoPath;

    public int setBaseContentView() {
        return R.layout.activity_shortvideo_selectimage;
    }

    public void initView() {
        closeSwip();
        hideDefaultTitleBar();
        getWindow().addFlags(128);
        this.mVideoPath = getIntent().getStringExtra("key_video_editer_path");
        this.mIsEditCover = getIntent().getBooleanExtra(EDIT_COVER_FLAG, false);
        if (StringUtil.isEmpty(this.mVideoPath)) {
            for (UploadQiNiuLocalPathInfo info : PostDataManager.getInstance().getLocalSelMedia()) {
                if (info.type == 3) {
                    this.mVideoPath = info.path;
                    break;
                }
            }
        }
        if (StringUtil.isEmpty(this.mVideoPath)) {
            finish();
        } else {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        ShortVideoSelectImageActivity.this.startInitPlayer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void startInitPlayer() {
        this.mTXVideoEditer = new TXVideoEditer(this);
        if (this.mTXVideoEditer.setVideoPath(this.mVideoPath) != 0) {
            finish();
            return;
        }
        this.mTXVideoEditer.setTXVideoPreviewListener(this);
        startInitVideoTimeLine();
        initListener();
    }

    public void startPlay(long startTime, long endTime) {
        if (this.mCurrentState == 0 || this.mCurrentState == 4 || this.mCurrentState == 6) {
            this.mTXVideoEditer.startPlayFromTime(startTime, endTime);
            this.mCurrentState = 1;
        }
    }

    public void pausePlay() {
        if (this.mCurrentState == 2 || this.mCurrentState == 1) {
            this.mTXVideoEditer.pausePlay();
            this.mCurrentState = 3;
        }
    }

    public void stopPlay() {
        if (this.mCurrentState == 2 || this.mCurrentState == 1 || this.mCurrentState == 6 || this.mCurrentState == 3) {
            this.mTXVideoEditer.stopPlay();
            this.mCurrentState = 4;
        }
    }

    private void initPlayerLayout() {
        TXPreviewParam param = new TXPreviewParam();
        param.videoView = ((ActivityShortvideoSelectimageBinding) this.mBaseBinding).editerFlVideo;
        param.renderMode = 2;
        this.mTXVideoEditer.initWithPreview(param);
    }

    private void initListener() {
        ((ActivityShortvideoSelectimageBinding) this.mBaseBinding).videoSelectImageTimeLine.setProgressChangedListner(new VideoSelectImageTimeLine$ProgressChangedListner() {
            public void onProgressChangeListener(float distanceX, int totalDistance) {
                ShortVideoSelectImageActivity.this.mCurrentTime = (long) ((distanceX / ((float) totalDistance)) * ((float) ShortVideoSelectImageActivity.this.mTxVideoInfo.duration));
                ShortVideoSelectImageActivity.this.mTXVideoEditer.previewAtTime(ShortVideoSelectImageActivity.this.mCurrentTime);
            }
        });
        ((ActivityShortvideoSelectimageBinding) this.mBaseBinding).cancelText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ShortVideoSelectImageActivity.this.finish();
            }
        });
        ((ActivityShortvideoSelectimageBinding) this.mBaseBinding).completText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Bitmap bitmap = null;
                try {
                    ((ActivityShortvideoSelectimageBinding) ShortVideoSelectImageActivity.this.mBaseBinding).completText.setClickable(false);
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    mmr.setDataSource(ShortVideoSelectImageActivity.this.mVideoPath);
                    bitmap = mmr.getFrameAtTime(ShortVideoSelectImageActivity.this.mCurrentTime * 1000);
                    if (bitmap == null) {
                        ShortVideoSelectImageActivity.this.showProgress("", "截图中...");
                        bitmap = TXVideoInfoReader.getInstance().getSampleImage(ShortVideoSelectImageActivity.this.mCurrentTime, ShortVideoSelectImageActivity.this.mVideoPath);
                    }
                    File file = FengUtil.saveBitmapToFile(bitmap, FengConstant.VIDEO_EDIT_PATH, System.currentTimeMillis() + ".jpg");
                    UploadQiNiuLocalPathInfo info;
                    if (ShortVideoSelectImageActivity.this.mIsEditCover) {
                        info = new UploadQiNiuLocalPathInfo(10, ShortVideoSelectImageActivity.this.mVideoPath, file.getAbsolutePath());
                        List<UploadQiNiuLocalPathInfo> list = new ArrayList();
                        list.add(info);
                        EventBus.getDefault().post(new ImageOrVideoPathEvent(false, list));
                    } else {
                        if (PostDataManager.getInstance().hasSelectVideo()) {
                            for (UploadQiNiuLocalPathInfo info2 : PostDataManager.getInstance().getLocalSelMedia()) {
                                if (info2.type == 3) {
                                    info2.path = ShortVideoSelectImageActivity.this.mVideoPath;
                                    info2.videocoverpath = file.getAbsolutePath();
                                    break;
                                }
                            }
                        }
                        PostDataManager.getInstance().addLocalSelMedia(new UploadQiNiuLocalPathInfo(3, ShortVideoSelectImageActivity.this.mVideoPath, file.getAbsolutePath()));
                        EventBus.getDefault().post(new ImageOrVideoPathEvent(true, PostDataManager.getInstance().getLocalSelMedia()));
                    }
                    EventBus.getDefault().post(new ClosePageEvent());
                    ShortVideoSelectImageActivity.this.finish();
                    ShortVideoSelectImageActivity.this.hideProgress();
                    ShortVideoSelectImageActivity.this.hideProgress();
                    ((ActivityShortvideoSelectimageBinding) ShortVideoSelectImageActivity.this.mBaseBinding).completText.setClickable(true);
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ShortVideoSelectImageActivity.this.hideProgress();
                    ((ActivityShortvideoSelectimageBinding) ShortVideoSelectImageActivity.this.mBaseBinding).completText.setClickable(true);
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                } catch (Throwable th) {
                    Throwable th2 = th;
                    ShortVideoSelectImageActivity.this.hideProgress();
                    ((ActivityShortvideoSelectimageBinding) ShortVideoSelectImageActivity.this.mBaseBinding).completText.setClickable(true);
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                }
            }
        });
        ((ActivityShortvideoSelectimageBinding) this.mBaseBinding).cancelButton.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ShortVideoSelectImageActivity.this.finish();
            }
        });
    }

    private void startInitVideoTimeLine() {
        runOnUiThread(new Runnable() {
            public void run() {
                ShortVideoSelectImageActivity.this.mCurrentState = 0;
                ShortVideoSelectImageActivity.this.initPlayerLayout();
                ShortVideoSelectImageActivity.this.startPlay(0, 500);
            }
        });
        this.mTxVideoInfo = TXVideoInfoReader.getInstance().getVideoFileInfo(this.mVideoPath);
        if (this.mTxVideoInfo != null) {
            this.mCount = ((ActivityShortvideoSelectimageBinding) this.mBaseBinding).videoSelectImageTimeLine.getBitmapCount(this);
            this.mTXVideoEditer.getThumbnail(this.mCount, 100, 100, true, this);
        }
    }

    public void onPreviewProgress(int i) {
        if (this.isFirst) {
            this.isFirst = false;
            if (this.mTXVideoEditer != null) {
                pausePlay();
                this.mTXVideoEditer.setTXVideoPreviewListener(null);
                this.mTXVideoEditer.previewAtTime(0);
            }
        }
    }

    public void onPreviewFinished() {
    }

    protected void onDestroy() {
        super.onDestroy();
        if (!ActivityManager.getInstance().hasContainsActivity(ShortVideoCropActivity.class)) {
            release();
        }
    }

    private void release() {
        if (this.mTXVideoEditer != null) {
            stopPlay();
            this.mTXVideoEditer.release();
            this.mTXVideoEditer = null;
        }
    }

    public void onThumbnail(int i, long l, final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            public void run() {
                ((ActivityShortvideoSelectimageBinding) ShortVideoSelectImageActivity.this.mBaseBinding).videoSelectImageTimeLine.addBitmap(bitmap);
            }
        });
    }
}
