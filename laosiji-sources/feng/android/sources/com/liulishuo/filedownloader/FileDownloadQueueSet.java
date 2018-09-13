package com.liulishuo.filedownloader;

import com.liulishuo.filedownloader.BaseDownloadTask.FinishListener;
import java.util.ArrayList;
import java.util.List;

public class FileDownloadQueueSet {
    private Integer autoRetryTimes;
    private Integer callbackProgressMinIntervalMillis;
    private Integer callbackProgressTimes;
    private String directory;
    private Boolean isForceReDownload;
    private boolean isSerial;
    private Boolean isWifiRequired;
    private Boolean syncCallback;
    private Object tag;
    private FileDownloadListener target;
    private List<FinishListener> taskFinishListenerList;
    private BaseDownloadTask[] tasks;

    public FileDownloadQueueSet(FileDownloadListener target) {
        if (target == null) {
            throw new IllegalArgumentException("create FileDownloadQueueSet must with valid target!");
        }
        this.target = target;
    }

    public FileDownloadQueueSet downloadTogether(BaseDownloadTask... tasks) {
        this.isSerial = false;
        this.tasks = tasks;
        return this;
    }

    public FileDownloadQueueSet downloadTogether(List<BaseDownloadTask> tasks) {
        this.isSerial = false;
        this.tasks = new BaseDownloadTask[tasks.size()];
        tasks.toArray(this.tasks);
        return this;
    }

    public FileDownloadQueueSet downloadSequentially(BaseDownloadTask... tasks) {
        this.isSerial = true;
        this.tasks = tasks;
        return this;
    }

    public FileDownloadQueueSet downloadSequentially(List<BaseDownloadTask> tasks) {
        this.isSerial = true;
        this.tasks = new BaseDownloadTask[tasks.size()];
        tasks.toArray(this.tasks);
        return this;
    }

    public void reuseAndStart() {
        for (BaseDownloadTask task : this.tasks) {
            task.reuse();
        }
        start();
    }

    public void start() {
        for (BaseDownloadTask task : this.tasks) {
            task.setListener(this.target);
            if (this.autoRetryTimes != null) {
                task.setAutoRetryTimes(this.autoRetryTimes.intValue());
            }
            if (this.syncCallback != null) {
                task.setSyncCallback(this.syncCallback.booleanValue());
            }
            if (this.isForceReDownload != null) {
                task.setForceReDownload(this.isForceReDownload.booleanValue());
            }
            if (this.callbackProgressTimes != null) {
                task.setCallbackProgressTimes(this.callbackProgressTimes.intValue());
            }
            if (this.callbackProgressMinIntervalMillis != null) {
                task.setCallbackProgressMinInterval(this.callbackProgressMinIntervalMillis.intValue());
            }
            if (this.tag != null) {
                task.setTag(this.tag);
            }
            if (this.taskFinishListenerList != null) {
                for (FinishListener finishListener : this.taskFinishListenerList) {
                    task.addFinishListener(finishListener);
                }
            }
            if (this.directory != null) {
                task.setPath(this.directory, true);
            }
            if (this.isWifiRequired != null) {
                task.setWifiRequired(this.isWifiRequired.booleanValue());
            }
            task.asInQueueTask().enqueue();
        }
        FileDownloader.getImpl().start(this.target, this.isSerial);
    }

    public FileDownloadQueueSet setDirectory(String directory) {
        this.directory = directory;
        return this;
    }

    public FileDownloadQueueSet setAutoRetryTimes(int autoRetryTimes) {
        this.autoRetryTimes = Integer.valueOf(autoRetryTimes);
        return this;
    }

    public FileDownloadQueueSet setSyncCallback(boolean syncCallback) {
        this.syncCallback = Boolean.valueOf(syncCallback);
        return this;
    }

    public FileDownloadQueueSet setForceReDownload(boolean isForceReDownload) {
        this.isForceReDownload = Boolean.valueOf(isForceReDownload);
        return this;
    }

    public FileDownloadQueueSet setCallbackProgressTimes(int callbackProgressTimes) {
        this.callbackProgressTimes = Integer.valueOf(callbackProgressTimes);
        return this;
    }

    public FileDownloadQueueSet setCallbackProgressMinInterval(int minIntervalMillis) {
        this.callbackProgressMinIntervalMillis = Integer.valueOf(minIntervalMillis);
        return this;
    }

    public FileDownloadQueueSet ignoreEachTaskInternalProgress() {
        setCallbackProgressTimes(-1);
        return this;
    }

    public FileDownloadQueueSet disableCallbackProgressTimes() {
        return setCallbackProgressTimes(0);
    }

    public FileDownloadQueueSet setTag(Object tag) {
        this.tag = tag;
        return this;
    }

    public FileDownloadQueueSet addTaskFinishListener(FinishListener finishListener) {
        if (this.taskFinishListenerList == null) {
            this.taskFinishListenerList = new ArrayList();
        }
        this.taskFinishListenerList.add(finishListener);
        return this;
    }

    public FileDownloadQueueSet setWifiRequired(boolean isWifiRequired) {
        this.isWifiRequired = Boolean.valueOf(isWifiRequired);
        return this;
    }
}
