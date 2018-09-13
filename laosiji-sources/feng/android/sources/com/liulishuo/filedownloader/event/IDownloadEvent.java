package com.liulishuo.filedownloader.event;

import com.liulishuo.filedownloader.util.FileDownloadLog;

public abstract class IDownloadEvent {
    public Runnable callback = null;
    protected final String id;

    public IDownloadEvent(String id) {
        this.id = id;
    }

    public IDownloadEvent(String id, boolean order) {
        this.id = id;
        if (order) {
            FileDownloadLog.w(this, "do not handle ORDER any more, %s", id);
        }
    }

    public final String getId() {
        return this.id;
    }
}
