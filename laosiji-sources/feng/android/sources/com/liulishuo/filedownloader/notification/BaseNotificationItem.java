package com.liulishuo.filedownloader.notification;

import android.app.NotificationManager;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.umeng.message.entity.UMessage;

public abstract class BaseNotificationItem {
    private String desc;
    private int id;
    private int lastStatus = 0;
    private NotificationManager manager;
    private int sofar;
    private int status = 0;
    private String title;
    private int total;

    public abstract void show(boolean z, int i, boolean z2);

    public BaseNotificationItem(int id, String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

    public void show(boolean isShowProgress) {
        show(isChanged(), getStatus(), isShowProgress);
    }

    public void update(int sofar, int total) {
        this.sofar = sofar;
        this.total = total;
        show(true);
    }

    public void updateStatus(int status) {
        this.status = status;
    }

    public void cancel() {
        getManager().cancel(this.id);
    }

    protected NotificationManager getManager() {
        if (this.manager == null) {
            this.manager = (NotificationManager) FileDownloadHelper.getAppContext().getSystemService(UMessage.DISPLAY_TYPE_NOTIFICATION);
        }
        return this.manager;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSofar() {
        return this.sofar;
    }

    public void setSofar(int sofar) {
        this.sofar = sofar;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        this.lastStatus = this.status;
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLastStatus() {
        return this.lastStatus;
    }

    public boolean isChanged() {
        return this.lastStatus != this.status;
    }
}
