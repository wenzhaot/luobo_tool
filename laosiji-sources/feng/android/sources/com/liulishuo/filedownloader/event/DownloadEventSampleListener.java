package com.liulishuo.filedownloader.event;

public class DownloadEventSampleListener extends IDownloadListener {
    private final IEventListener i;

    public interface IEventListener {
        boolean callback(IDownloadEvent iDownloadEvent);
    }

    public DownloadEventSampleListener(IEventListener i) {
        this.i = i;
    }

    public boolean callback(IDownloadEvent event) {
        return this.i != null && this.i.callback(event);
    }
}
