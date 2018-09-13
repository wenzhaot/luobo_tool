package com.liulishuo.filedownloader;

import android.os.SystemClock;
import com.liulishuo.filedownloader.IDownloadSpeed.Lookup;
import com.liulishuo.filedownloader.IDownloadSpeed.Monitor;

public class DownloadSpeedMonitor implements Monitor, Lookup {
    private long mLastRefreshSofarBytes;
    private long mLastRefreshTime;
    private int mMinIntervalUpdateSpeed = 1000;
    private int mSpeed;
    private long mStartSofarBytes;
    private long mStartTime;
    private long mTotalBytes;

    public void start(long startBytes) {
        this.mStartTime = SystemClock.uptimeMillis();
        this.mStartSofarBytes = startBytes;
    }

    public void end(long sofarBytes) {
        if (this.mStartTime > 0) {
            long downloadSize = sofarBytes - this.mStartSofarBytes;
            this.mLastRefreshTime = 0;
            long interval = SystemClock.uptimeMillis() - this.mStartTime;
            if (interval <= 0) {
                this.mSpeed = (int) downloadSize;
            } else {
                this.mSpeed = (int) (downloadSize / interval);
            }
        }
    }

    public void update(long sofarBytes) {
        if (this.mMinIntervalUpdateSpeed > 0) {
            boolean isUpdateData = false;
            if (this.mLastRefreshTime == 0) {
                isUpdateData = true;
            } else {
                long interval = SystemClock.uptimeMillis() - this.mLastRefreshTime;
                if (interval >= ((long) this.mMinIntervalUpdateSpeed) || (this.mSpeed == 0 && interval > 0)) {
                    this.mSpeed = (int) ((sofarBytes - this.mLastRefreshSofarBytes) / interval);
                    this.mSpeed = Math.max(0, this.mSpeed);
                    isUpdateData = true;
                }
            }
            if (isUpdateData) {
                this.mLastRefreshSofarBytes = sofarBytes;
                this.mLastRefreshTime = SystemClock.uptimeMillis();
            }
        }
    }

    public void reset() {
        this.mSpeed = 0;
        this.mLastRefreshTime = 0;
    }

    public int getSpeed() {
        return this.mSpeed;
    }

    public void setMinIntervalUpdateSpeed(int minIntervalUpdateSpeed) {
        this.mMinIntervalUpdateSpeed = minIntervalUpdateSpeed;
    }
}
