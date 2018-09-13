package com.tencent.rtmp.downloader;

import com.tencent.liteav.h.a;

public final class TXVodDownloadMediaInfo {
    protected TXVodDownloadDataSource dataSource;
    protected int downloadSize;
    protected int duration;
    protected boolean isStop;
    protected a netApi;
    protected String playPath;
    protected float progress;
    protected int size;
    protected int tid = -1;
    protected String url;

    public TXVodDownloadDataSource getDataSource() {
        return this.dataSource;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getSize() {
        return this.size;
    }

    public int getDownloadSize() {
        return this.downloadSize;
    }

    public float getProgress() {
        if (this.size > 0) {
            return ((float) this.downloadSize) / ((float) this.size);
        }
        return 0.0f;
    }

    public String getPlayPath() {
        return this.playPath;
    }

    public int getTaskId() {
        return this.tid;
    }

    public String getUrl() {
        return this.url;
    }
}
