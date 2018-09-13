package com.tencent.rtmp.downloader;

import com.tencent.rtmp.TXPlayerAuthBuilder;

public final class TXVodDownloadDataSource {
    public static final int QUALITY_2K = 5;
    public static final int QUALITY_4K = 6;
    public static final int QUALITY_FHD = 4;
    public static final int QUALITY_FLU = 1;
    public static final int QUALITY_HD = 3;
    public static final int QUALITY_OD = 0;
    public static final int QUALITY_SD = 2;
    protected TXPlayerAuthBuilder authBuilder;
    protected int quality;
    protected String token;

    public TXVodDownloadDataSource(TXPlayerAuthBuilder tXPlayerAuthBuilder, int i) {
        this.authBuilder = tXPlayerAuthBuilder;
        this.quality = i;
    }

    public void setToken(String str) {
        this.token = str;
    }
}
