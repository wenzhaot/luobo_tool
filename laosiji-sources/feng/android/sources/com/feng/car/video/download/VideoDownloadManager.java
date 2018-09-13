package com.feng.car.video.download;

import com.feng.car.FengApplication;
import com.feng.car.entity.download.VideoDownloadInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.thread.MediaInfo;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.video.VideoUrlInfo;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.StringUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;

public class VideoDownloadManager {
    private static VideoDownloadManager instance;
    private final String CACHE_RED_POINT = "cache_red_point";
    private final String MINE_RED_POINT = "mine_red_point";
    private BaseDownloadTask mCurrentBaseDownloadTask;
    private String mCurrentUrl = "";
    private FileDownloadLargeFileListener mGlobalFileDownloadListener = new FileDownloadLargeFileListener() {
        protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            VideoDownloadInfo info = VideoDownloadManager.this.getVideoDownloadInfoByUrl(task.getUrl());
            if (info != null && info.status != 1) {
                if (info.downloadId == 0) {
                    info.downloadId = task.getId();
                }
                info.status = 1;
                VideoDownloadManager.this.updateDownloadInfo(info);
            }
        }

        protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            VideoDownloadManager.this.setCurrentBaseDownloadTask(task);
            VideoDownloadInfo videoDownloadInfo = (VideoDownloadInfo) VideoDownloadManager.this.mVideoInfoMap.get(task.getUrl());
            if (videoDownloadInfo != null && videoDownloadInfo.status != 3) {
                if (videoDownloadInfo.downloadId == 0) {
                    videoDownloadInfo.downloadId = task.getId();
                }
                videoDownloadInfo.tips = "";
                videoDownloadInfo.status = 3;
                VideoDownloadManager.this.updateDownloadInfo(videoDownloadInfo);
            }
        }

        protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            VideoDownloadInfo info = VideoDownloadManager.this.getVideoDownloadInfoByUrl(task.getUrl());
            if (info != null && info.status != -2) {
                if (info.downloadId == 0) {
                    info.downloadId = task.getId();
                }
                info.status = -2;
                VideoDownloadManager.this.updateDownloadInfo(info);
            }
        }

        protected void completed(BaseDownloadTask task) {
            VideoDownloadInfo info = VideoDownloadManager.this.getVideoDownloadInfoByUrl(task.getUrl());
            if (info != null && info.status != -3) {
                if (info.downloadId == 0) {
                    info.downloadId = task.getId();
                }
                info.status = -3;
                VideoDownloadManager.this.updateDownloadInfo(info);
                VideoDownloadManager.this.downloadFinishUpdateMineFragment();
                EventBus.getDefault().post(new MessageCountRefreshEvent());
                if (FengUtil.isWifiConnectivity(FengApplication.getInstance())) {
                    VideoDownloadManager.this.startNextPendingTask(true);
                }
            }
        }

        protected void error(BaseDownloadTask task, Throwable e) {
            VideoDownloadInfo info = VideoDownloadManager.this.getVideoDownloadInfoByUrl(task.getUrl());
            if (info != null && info.status != -1) {
                if (info.downloadId == 0) {
                    info.downloadId = task.getId();
                }
                info.status = -1;
                VideoDownloadManager.this.updateDownloadInfo(info);
            }
        }

        protected void warn(BaseDownloadTask task) {
            VideoDownloadInfo info = VideoDownloadManager.this.getVideoDownloadInfoByUrl(task.getUrl());
            if (info != null && info.status != -4) {
                if (info.downloadId == 0) {
                    info.downloadId = task.getId();
                }
                info.status = -4;
                VideoDownloadManager.this.updateDownloadInfo(info);
            }
        }
    };
    private VideoDBManager mVideoDBManager = new VideoDBManager(FengApplication.getInstance());
    private Map<String, VideoDownloadInfo> mVideoInfoMap = new HashMap();

    public String getCurrentUrl() {
        return this.mCurrentUrl;
    }

    public void setCurrentBaseDownloadTask(BaseDownloadTask task) {
        if (this.mCurrentBaseDownloadTask != task) {
            this.mCurrentBaseDownloadTask = task;
        }
        if (!this.mCurrentUrl.equals(task.getUrl())) {
            this.mCurrentUrl = task.getUrl();
        }
    }

    public BaseDownloadTask getCurrentBaseDownloadTask() {
        return this.mCurrentBaseDownloadTask;
    }

    public void pauseCurrentBaseDownloadTask() {
        if (this.mCurrentBaseDownloadTask != null) {
            this.mCurrentBaseDownloadTask.setListener(null);
            FileDownloader.getImpl().pause(this.mCurrentBaseDownloadTask.getId());
        }
    }

    public VideoDownloadInfo getVideoDownloadInfoByUrl(String url) {
        if (this.mVideoDBManager == null) {
            return new VideoDownloadInfo();
        }
        return this.mVideoDBManager.getVideoDownloadInfoByUrl(url);
    }

    public VideoDownloadInfo getVideoDownloadInfoByMediaId(int mediaid) {
        if (this.mVideoDBManager == null) {
            return new VideoDownloadInfo();
        }
        return this.mVideoDBManager.getVideoDownloadInfoByMediaId(mediaid);
    }

    public FileDownloadListener getGlobalListener() {
        return this.mGlobalFileDownloadListener;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0048  */
    public void startNextPendingTask(boolean r9) {
        /*
        r8 = this;
        r7 = 3;
        r0 = 0;
        r2 = r8.getAllDownloadInfoList();
        r4 = r2.iterator();
    L_0x000a:
        r5 = r4.hasNext();
        if (r5 == 0) goto L_0x0046;
    L_0x0010:
        r1 = r4.next();
        r1 = (com.feng.car.entity.download.VideoDownloadInfo) r1;
        r5 = r1.status;
        r6 = 1;
        if (r5 != r6) goto L_0x000a;
    L_0x001b:
        r4 = com.liulishuo.filedownloader.FileDownloader.getImpl();
        r5 = r1.url;
        r3 = r4.create(r5);
        r4 = r1.url;
        r3.setTag(r4);
        r4 = r1.path;
        r4 = r3.setPath(r4);
        r4.setAutoRetryTimes(r7);
        if (r9 == 0) goto L_0x003a;
    L_0x0035:
        r4 = r8.mGlobalFileDownloadListener;
        r3.setListener(r4);
    L_0x003a:
        r3.start();
        r1.status = r7;
        r8.updateDownloadInfo(r1);
        r8.setCurrentBaseDownloadTask(r3);
        r0 = 1;
    L_0x0046:
        if (r0 != 0) goto L_0x004d;
    L_0x0048:
        r4 = "";
        r8.mCurrentUrl = r4;
    L_0x004d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.download.VideoDownloadManager.startNextPendingTask(boolean):void");
    }

    public static VideoDownloadManager newInstance() {
        if (instance == null) {
            instance = new VideoDownloadManager();
        }
        return instance;
    }

    private VideoDownloadManager() {
    }

    public void initFileDownloader() {
        FileDownloader.setup(FengApplication.getInstance());
        FileDownloadLog.NEED_LOG = true;
        FileDownloader.setGlobalPost2UIInterval(10);
        checkFileExists();
    }

    public void allTaskSetGlobalListener() {
        if (this.mCurrentBaseDownloadTask != null) {
            this.mCurrentBaseDownloadTask.setListener(this.mGlobalFileDownloadListener);
        }
    }

    public void pendingCurrentAndStartNext(VideoDownloadInfo videoDownloadInfo) {
        for (VideoDownloadInfo info : getAllDownloadInfoList()) {
            if (info.status == 1 && info.downloadId != videoDownloadInfo.downloadId) {
                BaseDownloadTask task = FileDownloader.getImpl().create(info.url);
                task.setTag(info.url);
                task.setPath(info.path).setAutoRetryTimes(3);
                task.start();
                info.status = 3;
                updateDownloadInfo(info);
                setCurrentBaseDownloadTask(task);
                return;
            }
        }
    }

    public void startCurrentTask(VideoDownloadInfo info) {
        info.status = 3;
        info.tips = "";
        updateDownloadInfo(info);
        BaseDownloadTask task = FileDownloader.getImpl().create(info.url);
        task.setTag(info.url);
        task.setPath(info.path).setAutoRetryTimes(3).start();
        setCurrentBaseDownloadTask(task);
    }

    public void downloadVideo(MediaInfo mediaInfo, SnsInfo snsInfo, VideoUrlInfo videoUrlInfo) {
        File file = new File(FengConstant.VIDEO_CACHE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        String path = FengConstant.VIDEO_CACHE_PATH + snsInfo.id + System.currentTimeMillis();
        VideoDownloadInfo downloadInfo = new VideoDownloadInfo();
        downloadInfo.snsInfo = snsInfo;
        downloadInfo.url = videoUrlInfo.url;
        downloadInfo.size = videoUrlInfo.size;
        downloadInfo.downloadTime = System.currentTimeMillis();
        downloadInfo.path = path;
        downloadInfo.status = 1;
        downloadInfo.mediaInfo = mediaInfo;
        downloadInfo.mediaInfo.path = path;
        downloadInfo.mediaid = mediaInfo.id;
        if (!hasDownloadTask()) {
            BaseDownloadTask task = FileDownloader.getImpl().create(videoUrlInfo.url);
            task.setTag(videoUrlInfo.url);
            task.setPath(path).setAutoRetryTimes(3).setListener(this.mGlobalFileDownloadListener);
            task.start();
            setCurrentBaseDownloadTask(task);
        }
        addDownloadInfo(downloadInfo);
    }

    private void addDownloadInfo(VideoDownloadInfo info) {
        this.mVideoInfoMap.put(info.url, info);
        this.mVideoDBManager.addDownloadInfo(info);
    }

    public boolean hasDownloadTask() {
        for (VideoDownloadInfo info : getAllDownloadInfoList()) {
            if (info != null && info.status == 3) {
                return true;
            }
        }
        return false;
    }

    public List<VideoDownloadInfo> getAllDownloadInfoList() {
        return this.mVideoDBManager.getDownloadInfoList();
    }

    public void updateDownloadInfo(VideoDownloadInfo info) {
        this.mVideoDBManager.updateDownloadInfo(info);
        this.mVideoInfoMap.put(info.url, info);
    }

    public void deleteDownloadInfo(VideoDownloadInfo info) {
        if (this.mVideoInfoMap.containsKey(info.url)) {
            this.mVideoInfoMap.remove(info.url);
        }
        if (info.downloadId != 0) {
            FileDownloader.getImpl().clear(info.downloadId, info.path);
        }
        File file = new File(info.path);
        if (file.exists()) {
            file.delete();
        }
        this.mVideoDBManager.deleteDownloadInfo(info);
    }

    public boolean hasDownloadVideo(MediaInfo mediaInfo) {
        VideoDownloadInfo info = getVideoDownloadInfoByMediaId(mediaInfo.id);
        if (info.mediaid == -1 || StringUtil.isEmpty(info.path) || info.status != -3) {
            return false;
        }
        return true;
    }

    public boolean hasAddedTask(MediaInfo mediaInfo) {
        VideoDownloadInfo info = getVideoDownloadInfoByMediaId(mediaInfo.id);
        if (StringUtil.isEmpty(info.url) || info.status == -3) {
            return false;
        }
        return true;
    }

    public void startAllTask(boolean isVideoCacheActivity) {
        BaseDownloadTask task;
        List<VideoDownloadInfo> list = getAllDownloadInfoList();
        boolean flag = false;
        if (!StringUtil.isEmpty(this.mCurrentUrl)) {
            VideoDownloadInfo currentInfo = getVideoDownloadInfoByUrl(this.mCurrentUrl);
            if (!(currentInfo == null || currentInfo.status == -3)) {
                task = FileDownloader.getImpl().create(currentInfo.url);
                task.setTag(currentInfo.url);
                task.setPath(currentInfo.path).setAutoRetryTimes(3);
                if (!isVideoCacheActivity) {
                    task.setListener(this.mGlobalFileDownloadListener);
                }
                currentInfo.tips = "";
                currentInfo.status = 3;
                updateDownloadInfo(currentInfo);
                task.start();
                setCurrentBaseDownloadTask(task);
            }
            this.mVideoInfoMap.put(currentInfo.url, currentInfo);
            flag = true;
        }
        for (VideoDownloadInfo info : list) {
            if (!(info.status == -3 || flag || info.status == -2)) {
                task = FileDownloader.getImpl().create(info.url);
                task.setTag(info.url);
                task.setPath(info.path).setAutoRetryTimes(3);
                if (!isVideoCacheActivity) {
                    task.setListener(this.mGlobalFileDownloadListener);
                }
                task.start();
                setCurrentBaseDownloadTask(task);
                flag = true;
                info.tips = "";
                info.status = 3;
                updateDownloadInfo(info);
            }
            info.tips = "";
            this.mVideoInfoMap.put(info.url, info);
        }
    }

    public void pauseAllTask(boolean is4G) {
        if (getCurrentBaseDownloadTask() != null && !StringUtil.isEmpty(this.mCurrentUrl)) {
            pauseCurrentBaseDownloadTask();
            VideoDownloadInfo info = getVideoDownloadInfoByUrl(getCurrentBaseDownloadTask().getUrl());
            if (info != null) {
                info.status = 1;
                if (is4G) {
                    info.tips = "切换到运营商网络自动暂停下载";
                } else {
                    info.tips = "";
                }
                newInstance().updateDownloadInfo(info);
            }
        }
    }

    public void checkFileExists() {
        for (VideoDownloadInfo info : getAllDownloadInfoList()) {
            if (info.status == -3 && !new File(info.path).exists()) {
                deleteDownloadInfo(info);
            }
        }
    }

    public void updateVideoPosition(MediaInfo mediaInfo, int duration) {
        VideoDownloadInfo info = getVideoDownloadInfoByMediaId(mediaInfo.id);
        if (info != null && duration > 0) {
            info.hasWatchProgress = (mediaInfo.position * 100) / duration;
            info.mediaInfo.position = mediaInfo.position;
            updateDownloadInfo(info);
        }
    }

    public void downloadFinishUpdateMineFragment() {
        SharedUtil.putBoolean(FengApplication.getInstance(), "mine_red_point", true);
        SharedUtil.putBoolean(FengApplication.getInstance(), "cache_red_point", true);
    }

    public void hideMineFragmentRedPoint() {
        SharedUtil.putBoolean(FengApplication.getInstance(), "mine_red_point", false);
    }

    public void hideCacheRedPoint() {
        SharedUtil.putBoolean(FengApplication.getInstance(), "cache_red_point", false);
    }

    public boolean isMineFragmentHasRedPoint() {
        return SharedUtil.getBoolean(FengApplication.getInstance(), "mine_red_point", false);
    }

    public boolean isCacheHasRedPoint() {
        return SharedUtil.getBoolean(FengApplication.getInstance(), "cache_red_point", false);
    }
}
