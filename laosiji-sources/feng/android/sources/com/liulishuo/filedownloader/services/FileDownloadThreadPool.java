package com.liulishuo.filedownloader.services;

import android.util.SparseArray;
import com.liulishuo.filedownloader.download.DownloadLaunchRunnable;
import com.liulishuo.filedownloader.util.FileDownloadExecutors;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

class FileDownloadThreadPool {
    private int mIgnoreCheckTimes = 0;
    private int mMaxThreadCount;
    private ThreadPoolExecutor mThreadPool;
    private SparseArray<DownloadLaunchRunnable> runnablePool = new SparseArray();
    private final String threadPrefix = "Network";

    FileDownloadThreadPool(int maxNetworkThreadCount) {
        this.mThreadPool = FileDownloadExecutors.newDefaultThreadPool(maxNetworkThreadCount, "Network");
        this.mMaxThreadCount = maxNetworkThreadCount;
    }

    public synchronized boolean setMaxNetworkThreadCount(int count) {
        boolean z = false;
        synchronized (this) {
            if (exactSize() > 0) {
                FileDownloadLog.w(this, "Can't change the max network thread count, because the  network thread pool isn't in IDLE, please try again after all running tasks are completed or invoking FileDownloader#pauseAll directly.", new Object[0]);
            } else {
                int validCount = FileDownloadProperties.getValidNetworkThreadCount(count);
                if (FileDownloadLog.NEED_LOG) {
                    FileDownloadLog.d(this, "change the max network thread count, from %d to %d", Integer.valueOf(this.mMaxThreadCount), Integer.valueOf(validCount));
                }
                List<Runnable> taskQueue = this.mThreadPool.shutdownNow();
                this.mThreadPool = FileDownloadExecutors.newDefaultThreadPool(validCount, "Network");
                if (taskQueue.size() > 0) {
                    FileDownloadLog.w(this, "recreate the network thread pool and discard %d tasks", Integer.valueOf(taskQueue.size()));
                }
                this.mMaxThreadCount = validCount;
                z = true;
            }
        }
        return z;
    }

    public void execute(DownloadLaunchRunnable launchRunnable) {
        launchRunnable.pending();
        synchronized (this) {
            this.runnablePool.put(launchRunnable.getId(), launchRunnable);
        }
        this.mThreadPool.execute(launchRunnable);
        if (this.mIgnoreCheckTimes >= 600) {
            filterOutNoExist();
            this.mIgnoreCheckTimes = 0;
            return;
        }
        this.mIgnoreCheckTimes++;
    }

    public void cancel(int id) {
        filterOutNoExist();
        synchronized (this) {
            DownloadLaunchRunnable r = (DownloadLaunchRunnable) this.runnablePool.get(id);
            if (r != null) {
                r.pause();
                boolean result = this.mThreadPool.remove(r);
                if (FileDownloadLog.NEED_LOG) {
                    FileDownloadLog.d(this, "successful cancel %d %B", Integer.valueOf(id), Boolean.valueOf(result));
                }
            }
            this.runnablePool.remove(id);
        }
    }

    private synchronized void filterOutNoExist() {
        SparseArray<DownloadLaunchRunnable> correctedRunnablePool = new SparseArray();
        int size = this.runnablePool.size();
        for (int i = 0; i < size; i++) {
            int key = this.runnablePool.keyAt(i);
            DownloadLaunchRunnable runnable = (DownloadLaunchRunnable) this.runnablePool.get(key);
            if (runnable.isAlive()) {
                correctedRunnablePool.put(key, runnable);
            }
        }
        this.runnablePool = correctedRunnablePool;
    }

    public boolean isInThreadPool(int downloadId) {
        DownloadLaunchRunnable runnable = (DownloadLaunchRunnable) this.runnablePool.get(downloadId);
        return runnable != null && runnable.isAlive();
    }

    public int findRunningTaskIdBySameTempPath(String tempFilePath, int excludeId) {
        if (tempFilePath == null) {
            return 0;
        }
        int size = this.runnablePool.size();
        for (int i = 0; i < size; i++) {
            DownloadLaunchRunnable runnable = (DownloadLaunchRunnable) this.runnablePool.valueAt(i);
            if (runnable != null && runnable.isAlive() && runnable.getId() != excludeId && tempFilePath.equals(runnable.getTempFilePath())) {
                return runnable.getId();
            }
        }
        return 0;
    }

    public synchronized int exactSize() {
        filterOutNoExist();
        return this.runnablePool.size();
    }

    public synchronized List<Integer> getAllExactRunningDownloadIds() {
        List<Integer> list;
        filterOutNoExist();
        list = new ArrayList();
        for (int i = 0; i < this.runnablePool.size(); i++) {
            list.add(Integer.valueOf(((DownloadLaunchRunnable) this.runnablePool.get(this.runnablePool.keyAt(i))).getId()));
        }
        return list;
    }
}
