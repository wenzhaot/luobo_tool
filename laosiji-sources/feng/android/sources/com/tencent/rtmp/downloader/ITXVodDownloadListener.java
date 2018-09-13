package com.tencent.rtmp.downloader;

public interface ITXVodDownloadListener {
    void onDownloadError(TXVodDownloadMediaInfo tXVodDownloadMediaInfo, int i, String str);

    void onDownloadFinish(TXVodDownloadMediaInfo tXVodDownloadMediaInfo);

    void onDownloadProgress(TXVodDownloadMediaInfo tXVodDownloadMediaInfo);

    void onDownloadStart(TXVodDownloadMediaInfo tXVodDownloadMediaInfo);

    void onDownloadStop(TXVodDownloadMediaInfo tXVodDownloadMediaInfo);
}
