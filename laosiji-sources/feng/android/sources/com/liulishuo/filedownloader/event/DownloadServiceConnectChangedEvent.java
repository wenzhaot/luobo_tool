package com.liulishuo.filedownloader.event;

public class DownloadServiceConnectChangedEvent extends IDownloadEvent {
    public static final String ID = "event.service.connect.changed";
    private final Class<?> serviceClass;
    private final ConnectStatus status;

    public enum ConnectStatus {
        connected,
        disconnected,
        lost
    }

    public DownloadServiceConnectChangedEvent(ConnectStatus status, Class<?> serviceClass) {
        super(ID);
        this.status = status;
        this.serviceClass = serviceClass;
    }

    public ConnectStatus getStatus() {
        return this.status;
    }

    public boolean isSuchService(Class<?> serviceClass) {
        return this.serviceClass != null && this.serviceClass.getName().equals(serviceClass.getName());
    }
}
