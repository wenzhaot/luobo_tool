package com.feng.car.utils;

import android.text.TextUtils;
import com.feng.car.entity.download.VideoDownloadInfo;
import com.feng.car.entity.sns.SnsPostResources;
import com.feng.car.entity.thread.MediaInfo;
import com.feng.car.video.download.VideoDownloadManager;
import com.feng.library.utils.StringUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class VideoManager {
    private static VideoManager instance;
    private Map<String, MediaInfo> mVideoMap = new HashMap();
    private Map<String, Integer> positionMap = new HashMap();

    public static VideoManager newInstance() {
        if (instance == null) {
            instance = new VideoManager();
        }
        return instance;
    }

    public void updatePosition(MediaInfo mediaInfo) {
        if (mediaInfo != null) {
            this.positionMap.put(mediaInfo.key, Integer.valueOf(mediaInfo.position));
        }
    }

    public int getPosition(MediaInfo mediaInfo) {
        if (mediaInfo == null || !this.positionMap.containsKey(mediaInfo.key)) {
            return 0;
        }
        return ((Integer) this.positionMap.get(mediaInfo.key)).intValue();
    }

    public void clear() {
        this.mVideoMap.clear();
    }

    public MediaInfo getMediaInfoByKey(String key) {
        if (StringUtil.isEmpty(key) || !this.mVideoMap.containsKey(key)) {
            return null;
        }
        return (MediaInfo) this.mVideoMap.get(key);
    }

    public void updateVideoInfo(MediaInfo mediaInfo) {
        if (mediaInfo != null) {
            MediaInfo info;
            if (this.mVideoMap.containsKey(mediaInfo.key)) {
                info = (MediaInfo) this.mVideoMap.get(mediaInfo.key);
            } else {
                info = new MediaInfo();
            }
            info.id = mediaInfo.id;
            info.key = mediaInfo.key;
            info.play_state = mediaInfo.play_state;
            info.defination = mediaInfo.defination;
            info.position = mediaInfo.position;
            info.videourl.hash = mediaInfo.videourl.hash;
            info.videourl.sdinfo.update(mediaInfo.videourl.sdinfo);
            info.videourl.hdinfo.update(mediaInfo.videourl.hdinfo);
            info.videourl.bdinfo.update(mediaInfo.videourl.bdinfo);
            info.image.url = mediaInfo.image.url;
            info.image.width = mediaInfo.image.width;
            info.image.height = mediaInfo.image.height;
            info.path = mediaInfo.path;
            info.downloadDefination = mediaInfo.downloadDefination;
            info.playcount = mediaInfo.playcount;
            info.playtime = mediaInfo.playtime;
            info.hasChangeDefinition = mediaInfo.hasChangeDefinition;
            this.mVideoMap.put(info.key, info);
            updatePosition(info);
        }
    }

    public MediaInfo createMediaInfo(SnsPostResources resources) {
        if (resources == null) {
            return null;
        }
        MediaInfo mediaInfo = new MediaInfo();
        VideoDownloadInfo info = VideoDownloadManager.newInstance().getVideoDownloadInfoByMediaId(resources.id);
        if (info == null || info.status != -3) {
            if (this.mVideoMap.containsKey(resources.videourl.hash)) {
                mediaInfo = (MediaInfo) this.mVideoMap.get(resources.videourl.hash);
            } else if (this.mVideoMap.containsKey(resources.hashCode() + "")) {
                mediaInfo = (MediaInfo) this.mVideoMap.get(resources.hashCode() + "");
            }
        } else if (new File(info.path).exists()) {
            mediaInfo = info.mediaInfo;
            this.mVideoMap.put(mediaInfo.key, mediaInfo);
            updatePosition(mediaInfo);
            return mediaInfo;
        }
        mediaInfo.id = resources.id;
        if (TextUtils.isEmpty(resources.videourl.hash)) {
            mediaInfo.key = resources.hashCode() + "";
        } else {
            mediaInfo.key = resources.videourl.hash;
        }
        mediaInfo.videourl.sdinfo.update(resources.videourl.sdinfo);
        mediaInfo.videourl.hdinfo.update(resources.videourl.hdinfo);
        mediaInfo.videourl.fhdinfo.update(resources.videourl.fhdinfo);
        mediaInfo.videourl.bdinfo.update(resources.videourl.bdinfo);
        mediaInfo.image.url = resources.image.url;
        mediaInfo.image.width = resources.image.width;
        mediaInfo.image.height = resources.image.height;
        mediaInfo.playcount = resources.playcount;
        mediaInfo.playtime = resources.playtime;
        if (this.mVideoMap.containsKey(mediaInfo.key)) {
            ((MediaInfo) this.mVideoMap.get(mediaInfo.key)).playcount = resources.playcount;
            return mediaInfo;
        }
        this.mVideoMap.put(mediaInfo.key, mediaInfo);
        return mediaInfo;
    }
}
