package com.liulishuo.filedownloader.notification;

import android.util.SparseArray;

public class FileDownloadNotificationHelper<T extends BaseNotificationItem> {
    private final SparseArray<T> notificationArray = new SparseArray();

    public T get(int id) {
        return (BaseNotificationItem) this.notificationArray.get(id);
    }

    public boolean contains(int id) {
        return get(id) != null;
    }

    public T remove(int id) {
        T n = get(id);
        if (n == null) {
            return null;
        }
        this.notificationArray.remove(id);
        return n;
    }

    public void add(T notification) {
        this.notificationArray.remove(notification.getId());
        this.notificationArray.put(notification.getId(), notification);
    }

    public void showProgress(int id, int sofar, int total) {
        T notification = get(id);
        if (notification != null) {
            notification.updateStatus(3);
            notification.update(sofar, total);
        }
    }

    public void showIndeterminate(int id, int status) {
        BaseNotificationItem notification = get(id);
        if (notification != null) {
            notification.updateStatus(status);
            notification.show(false);
        }
    }

    public void cancel(int id) {
        BaseNotificationItem notification = remove(id);
        if (notification != null) {
            notification.cancel();
        }
    }

    public void clear() {
        SparseArray<BaseNotificationItem> cloneArray = this.notificationArray.clone();
        this.notificationArray.clear();
        for (int i = 0; i < cloneArray.size(); i++) {
            ((BaseNotificationItem) cloneArray.get(cloneArray.keyAt(i))).cancel();
        }
    }
}
