package com.tencent.rtmp.downloader;

import android.net.Uri;
import android.text.TextUtils;
import com.feng.car.video.shortvideo.FileUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.ijk.media.player.IjkDownloadCenter;
import com.tencent.ijk.media.player.IjkDownloadCenter.OnDownloadListener;
import com.tencent.ijk.media.player.IjkDownloadMedia;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.h.a;
import com.tencent.liteav.h.b;
import com.tencent.liteav.h.c;
import com.tencent.liteav.h.d;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXPlayerAuthBuilder;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

public class TXVodDownloadManager {
    public static final int DOWNLOAD_AUTH_FAILED = -5001;
    public static final int DOWNLOAD_DISCONNECT = -5005;
    public static final int DOWNLOAD_FORMAT_ERROR = -5004;
    public static final int DOWNLOAD_HLS_KEY_ERROR = -5006;
    public static final int DOWNLOAD_NO_FILE = -5003;
    public static final int DOWNLOAD_PATH_ERROR = -5007;
    public static final int DOWNLOAD_SUCCESS = 0;
    private static final int IJKDM_EVT_FILE_OPEN_ERROR = 1008;
    private static final int IJKDM_EVT_HLS_KEY_ERROR = 1008;
    private static final int IJKDM_EVT_NET_DISCONNECT = 1001;
    private static final String TAG = "TXVodDownloadManager";
    private static TXVodDownloadManager instance = null;
    protected IjkDownloadCenter mDownloadCenter = IjkDownloadCenter.getInstance();
    OnDownloadListener mDownloadCenterListener = new OnDownloadListener() {
        public void downloadBegin(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia) {
            TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
            if (convertMedia != null) {
                TXCLog.i(TXVodDownloadManager.TAG, "downloadBegin " + convertMedia.playPath);
                TXVodDownloadManager.this.mListener.onDownloadStart(convertMedia);
                if (new File(convertMedia.playPath).isFile()) {
                    TXCLog.d(TXVodDownloadManager.TAG, "file state ok");
                } else {
                    TXCLog.e(TXVodDownloadManager.TAG, "file not create!");
                }
            }
        }

        public void downloadEnd(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia) {
            TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
            if (convertMedia != null) {
                TXCLog.i(TXVodDownloadManager.TAG, "downloadEnd " + convertMedia.playPath);
                TXVodDownloadManager.this.mMediaInfoArray.remove(convertMedia);
                TXVodDownloadManager.this.mListener.onDownloadStop(convertMedia);
            }
        }

        public void downloadFinish(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia) {
            TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
            if (convertMedia != null) {
                TXCLog.i(TXVodDownloadManager.TAG, "downloadFinish " + convertMedia.playPath);
                TXVodDownloadManager.this.mMediaInfoArray.remove(convertMedia);
                if (new File(convertMedia.playPath).isFile()) {
                    TXVodDownloadManager.this.mListener.onDownloadFinish(convertMedia);
                } else {
                    TXVodDownloadManager.this.mListener.onDownloadError(convertMedia, TXVodDownloadManager.DOWNLOAD_NO_FILE, "文件已被删除");
                }
            }
        }

        public void downloadError(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia, int i, String str) {
            TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
            if (convertMedia != null) {
                TXCLog.e(TXVodDownloadManager.TAG, "downloadError " + convertMedia.playPath + " " + str);
                TXVodDownloadManager.this.mMediaInfoArray.remove(convertMedia);
                if (convertMedia.isStop) {
                    TXVodDownloadManager.this.mListener.onDownloadStop(convertMedia);
                } else if (i == TXLiveConstants.PUSH_EVT_START_VIDEO_ENCODER) {
                    TXVodDownloadManager.this.mListener.onDownloadError(convertMedia, TXVodDownloadManager.DOWNLOAD_HLS_KEY_ERROR, str);
                } else {
                    TXVodDownloadManager.this.mListener.onDownloadError(convertMedia, TXVodDownloadManager.DOWNLOAD_DISCONNECT, str);
                }
            }
        }

        public void downloadProgress(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia) {
            TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
            if (convertMedia != null) {
                TXVodDownloadManager.this.mListener.onDownloadProgress(convertMedia);
            }
        }
    };
    protected String mDownloadPath;
    protected ITXVodDownloadListener mListener;
    protected ArrayList<TXVodDownloadMediaInfo> mMediaInfoArray;

    private TXVodDownloadManager() {
        this.mDownloadCenter.setListener(this.mDownloadCenterListener);
        this.mMediaInfoArray = new ArrayList();
    }

    public static TXVodDownloadManager getInstance() {
        if (instance == null) {
            instance = new TXVodDownloadManager();
        }
        return instance;
    }

    public void setDownloadPath(String str) {
        if (str != null) {
            new File(str).mkdirs();
            this.mDownloadPath = str;
        }
    }

    public void setListener(ITXVodDownloadListener iTXVodDownloadListener) {
        this.mListener = iTXVodDownloadListener;
    }

    public TXVodDownloadMediaInfo startDownloadUrl(String str) {
        TXVodDownloadMediaInfo tXVodDownloadMediaInfo = new TXVodDownloadMediaInfo();
        tXVodDownloadMediaInfo.url = str;
        this.mMediaInfoArray.add(tXVodDownloadMediaInfo);
        downloadMedia(tXVodDownloadMediaInfo);
        return tXVodDownloadMediaInfo;
    }

    public TXVodDownloadMediaInfo startDownload(final TXVodDownloadDataSource tXVodDownloadDataSource) {
        final TXVodDownloadMediaInfo tXVodDownloadMediaInfo = new TXVodDownloadMediaInfo();
        tXVodDownloadMediaInfo.dataSource = tXVodDownloadDataSource;
        if (tXVodDownloadDataSource.authBuilder != null) {
            TXPlayerAuthBuilder tXPlayerAuthBuilder = tXVodDownloadDataSource.authBuilder;
            a aVar = new a();
            aVar.a(tXPlayerAuthBuilder.isHttps());
            aVar.a(new b() {
                public void onNetSuccess(a aVar) {
                    if (tXVodDownloadMediaInfo.isStop) {
                        TXVodDownloadManager.this.mMediaInfoArray.remove(tXVodDownloadMediaInfo);
                        if (TXVodDownloadManager.this.mListener != null) {
                            TXVodDownloadManager.this.mListener.onDownloadStop(tXVodDownloadMediaInfo);
                        }
                        TXCLog.w(TXVodDownloadManager.TAG, "已取消下载任务");
                        return;
                    }
                    d dVar = null;
                    c a = aVar.a();
                    if (tXVodDownloadDataSource.quality == 0) {
                        dVar = a.d();
                        if (dVar == null && a.c() != null) {
                            dVar = (d) a.c().get(0);
                        }
                    } else {
                        int i = tXVodDownloadDataSource.quality - 1;
                        if (a.c() != null && a.c().size() > i) {
                            dVar = (d) a.c().get(i);
                        }
                    }
                    if (dVar == null) {
                        TXVodDownloadManager.this.mMediaInfoArray.remove(tXVodDownloadMediaInfo);
                        if (TXVodDownloadManager.this.mListener != null) {
                            TXVodDownloadManager.this.mListener.onDownloadError(tXVodDownloadMediaInfo, TXVodDownloadManager.DOWNLOAD_NO_FILE, "无此清晰度");
                            return;
                        }
                        return;
                    }
                    tXVodDownloadMediaInfo.url = dVar.b();
                    tXVodDownloadMediaInfo.size = dVar.d();
                    TXVodDownloadManager.this.downloadMedia(tXVodDownloadMediaInfo);
                }

                public void onNetFailed(a aVar, String str, int i) {
                    TXVodDownloadManager.this.mMediaInfoArray.remove(tXVodDownloadMediaInfo);
                    if (TXVodDownloadManager.this.mListener != null) {
                        TXVodDownloadManager.this.mListener.onDownloadError(tXVodDownloadMediaInfo, TXVodDownloadManager.DOWNLOAD_AUTH_FAILED, str);
                    }
                }
            });
            if (aVar.a(tXPlayerAuthBuilder.getAppId(), tXPlayerAuthBuilder.getFileId(), tXPlayerAuthBuilder.getTimeout(), tXPlayerAuthBuilder.getUs(), tXPlayerAuthBuilder.getExper(), tXPlayerAuthBuilder.getSign()) == 0) {
                tXVodDownloadMediaInfo.netApi = aVar;
                this.mMediaInfoArray.add(tXVodDownloadMediaInfo);
                return tXVodDownloadMediaInfo;
            }
            TXCLog.e(TAG, "unable to getPlayInfo");
        }
        return null;
    }

    public void stopDownload(TXVodDownloadMediaInfo tXVodDownloadMediaInfo) {
        if (tXVodDownloadMediaInfo != null) {
            tXVodDownloadMediaInfo.isStop = true;
            if (tXVodDownloadMediaInfo.tid < 0) {
                TXCLog.w(TAG, "stop download not start task");
                return;
            }
            this.mDownloadCenter.stop(tXVodDownloadMediaInfo.tid);
            TXCLog.d(TAG, "stop download " + tXVodDownloadMediaInfo.url);
        }
    }

    public boolean deleteDownloadFile(String str) {
        TXCLog.d(TAG, "delete file " + str);
        Iterator it = this.mMediaInfoArray.iterator();
        while (it.hasNext()) {
            if (((TXVodDownloadMediaInfo) it.next()).playPath.equals(str)) {
                TXCLog.e(TAG, "file is downloading, can not be delete");
                return false;
            }
        }
        File file = new File(str);
        file.delete();
        new File(file.getParent()).delete();
        TXCLog.e(TAG, "delete success");
        return true;
    }

    protected void downloadMedia(TXVodDownloadMediaInfo tXVodDownloadMediaInfo) {
        String str = tXVodDownloadMediaInfo.url;
        if (str != null) {
            if (Uri.parse(str).getPath().endsWith(".m3u8")) {
                tXVodDownloadMediaInfo.playPath = makePlayPath(str);
                if (tXVodDownloadMediaInfo.playPath != null) {
                    if (!(tXVodDownloadMediaInfo.dataSource == null || tXVodDownloadMediaInfo.dataSource.token == null)) {
                        String[] split = str.split("/");
                        if (split.length > 0) {
                            int lastIndexOf = str.lastIndexOf(split[split.length - 1]);
                            str = str.substring(0, lastIndexOf) + "voddrm.token." + tXVodDownloadMediaInfo.dataSource.token + FileUtils.FILE_EXTENSION_SEPARATOR + str.substring(lastIndexOf);
                        }
                    }
                    TXCLog.d(TAG, "download hls " + str + " to " + tXVodDownloadMediaInfo.playPath);
                    tXVodDownloadMediaInfo.tid = this.mDownloadCenter.downloadHls(str, tXVodDownloadMediaInfo.playPath);
                    if (tXVodDownloadMediaInfo.tid < 0) {
                        TXCLog.e(TAG, "start download failed");
                        if (this.mListener != null) {
                            this.mListener.onDownloadError(tXVodDownloadMediaInfo, DOWNLOAD_FORMAT_ERROR, "Internal error");
                            return;
                        }
                        return;
                    }
                    return;
                } else if (this.mListener != null) {
                    this.mListener.onDownloadError(tXVodDownloadMediaInfo, DOWNLOAD_PATH_ERROR, "本地路径创建失败");
                    return;
                } else {
                    return;
                }
            }
            TXCLog.e(TAG, "format error: " + str);
            if (this.mListener != null) {
                this.mListener.onDownloadError(tXVodDownloadMediaInfo, DOWNLOAD_FORMAT_ERROR, "No support format");
            }
        }
    }

    protected String makePlayPath(String str) {
        String str2 = this.mDownloadPath + "/txdownload";
        File file = new File(str2);
        if ((!file.exists() || !file.isDirectory()) && !file.mkdir()) {
            TXCLog.e(TAG, "创建下载路径失败 " + str2);
            return null;
        } else if (Uri.parse(str).getPath().endsWith(".m3u8")) {
            return str2 + "/" + md5(str) + ".m3u8.sqlite";
        } else {
            TXCLog.e(TAG, "不支持格式");
            return null;
        }
    }

    protected static String md5(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes());
            int length = digest.length;
            int i = 0;
            String str2 = "";
            while (i < length) {
                String toHexString = Integer.toHexString(digest[i] & 255);
                if (toHexString.length() == 1) {
                    toHexString = PushConstants.PUSH_TYPE_NOTIFY + toHexString;
                }
                i++;
                str2 = str2 + toHexString;
            }
            return str2;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    TXVodDownloadMediaInfo convertMedia(IjkDownloadMedia ijkDownloadMedia) {
        Iterator it = this.mMediaInfoArray.iterator();
        while (it.hasNext()) {
            TXVodDownloadMediaInfo tXVodDownloadMediaInfo = (TXVodDownloadMediaInfo) it.next();
            if (tXVodDownloadMediaInfo.tid == ijkDownloadMedia.tid) {
                tXVodDownloadMediaInfo.downloadSize = ijkDownloadMedia.downloadSize;
                if (tXVodDownloadMediaInfo.size != 0) {
                    return tXVodDownloadMediaInfo;
                }
                tXVodDownloadMediaInfo.size = ijkDownloadMedia.size;
                return tXVodDownloadMediaInfo;
            }
        }
        return null;
    }
}
