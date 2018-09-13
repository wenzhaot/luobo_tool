package com.tencent.rtmp;

import java.util.Map;

public class TXVodPlayConfig {
    boolean autoRotate = true;
    String cacheMp4ExtName = "mp4";
    boolean enableAccurateSeek = true;
    String mCacheFolderPath;
    int mConnectRetryCount = 3;
    int mConnectRetryInterval = 3;
    Map<String, String> mHeaders;
    int mMaxCacheItems;
    int mPlayerType;
    int mTimeout = 10;
    int progressInterval;
    boolean smoothSwitchBitrate = false;

    public void setConnectRetryCount(int i) {
        this.mConnectRetryCount = i;
    }

    public void setConnectRetryInterval(int i) {
        this.mConnectRetryInterval = i;
    }

    public void setTimeout(int i) {
        this.mTimeout = i;
    }

    public void setCacheFolderPath(String str) {
        this.mCacheFolderPath = str;
    }

    public void setMaxCacheItems(int i) {
        this.mMaxCacheItems = i;
    }

    public void setPlayerType(int i) {
        this.mPlayerType = i;
    }

    public void setHeaders(Map<String, String> map) {
        this.mHeaders = map;
    }

    public void setEnableAccurateSeek(boolean z) {
        this.enableAccurateSeek = z;
    }

    public void setAutoRotate(boolean z) {
        this.autoRotate = z;
    }

    public void setSmoothSwitchBitrate(boolean z) {
        this.smoothSwitchBitrate = z;
    }

    public void setCacheMp4ExtName(String str) {
        this.cacheMp4ExtName = str;
    }

    public void setProgressInterval(int i) {
        this.progressInterval = i;
    }
}
