package com.liulishuo.filedownloader.util;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.BaseDownloadTask.FinishListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileDownloadSerialQueue {
    public static final int ID_INVALID = 0;
    private static final int WHAT_NEXT = 1;
    final SerialFinishCallback finishCallback;
    private final Handler mHandler;
    private final HandlerThread mHandlerThread = new HandlerThread(FileDownloadUtils.getThreadPoolName("SerialDownloadManager"));
    private final BlockingQueue<BaseDownloadTask> mTasks = new LinkedBlockingQueue();
    private final Object operationLock = new Object();
    volatile boolean paused = false;
    private final List<BaseDownloadTask> pausedList = new ArrayList();
    volatile BaseDownloadTask workingTask;

    private static class SerialFinishCallback implements FinishListener {
        private final WeakReference<FileDownloadSerialQueue> mQueueWeakReference;

        SerialFinishCallback(WeakReference<FileDownloadSerialQueue> queueWeakReference) {
            this.mQueueWeakReference = queueWeakReference;
        }

        public synchronized void over(BaseDownloadTask task) {
            task.removeFinishListener(this);
            if (this.mQueueWeakReference != null) {
                FileDownloadSerialQueue queue = (FileDownloadSerialQueue) this.mQueueWeakReference.get();
                if (queue != null) {
                    queue.workingTask = null;
                    if (!queue.paused) {
                        queue.sendNext();
                    }
                }
            }
        }
    }

    private class SerialLoop implements Callback {
        private SerialLoop() {
        }

        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        if (!FileDownloadSerialQueue.this.paused) {
                            FileDownloadSerialQueue.this.workingTask = (BaseDownloadTask) FileDownloadSerialQueue.this.mTasks.take();
                            FileDownloadSerialQueue.this.workingTask.addFinishListener(FileDownloadSerialQueue.this.finishCallback).start();
                            break;
                        }
                    } catch (InterruptedException e) {
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    public FileDownloadSerialQueue() {
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper(), new SerialLoop());
        this.finishCallback = new SerialFinishCallback(new WeakReference(this));
        sendNext();
    }

    public void enqueue(BaseDownloadTask task) {
        synchronized (this.finishCallback) {
            if (this.paused) {
                this.pausedList.add(task);
                return;
            }
            try {
                this.mTasks.put(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
    }

    /* JADX WARNING: Missing block: B:15:?, code:
            return;
     */
    public void pause() {
        /*
        r5 = this;
        r1 = r5.finishCallback;
        monitor-enter(r1);
        r0 = r5.paused;	 Catch:{ all -> 0x003b }
        if (r0 == 0) goto L_0x001f;
    L_0x0007:
        r0 = "require pause this queue(remain %d), but it has already been paused";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ all -> 0x003b }
        r3 = 0;
        r4 = r5.mTasks;	 Catch:{ all -> 0x003b }
        r4 = r4.size();	 Catch:{ all -> 0x003b }
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ all -> 0x003b }
        r2[r3] = r4;	 Catch:{ all -> 0x003b }
        com.liulishuo.filedownloader.util.FileDownloadLog.w(r5, r0, r2);	 Catch:{ all -> 0x003b }
        monitor-exit(r1);	 Catch:{ all -> 0x003b }
    L_0x001e:
        return;
    L_0x001f:
        r0 = 1;
        r5.paused = r0;	 Catch:{ all -> 0x003b }
        r0 = r5.mTasks;	 Catch:{ all -> 0x003b }
        r2 = r5.pausedList;	 Catch:{ all -> 0x003b }
        r0.drainTo(r2);	 Catch:{ all -> 0x003b }
        r0 = r5.workingTask;	 Catch:{ all -> 0x003b }
        if (r0 == 0) goto L_0x0039;
    L_0x002d:
        r0 = r5.workingTask;	 Catch:{ all -> 0x003b }
        r2 = r5.finishCallback;	 Catch:{ all -> 0x003b }
        r0.removeFinishListener(r2);	 Catch:{ all -> 0x003b }
        r0 = r5.workingTask;	 Catch:{ all -> 0x003b }
        r0.pause();	 Catch:{ all -> 0x003b }
    L_0x0039:
        monitor-exit(r1);	 Catch:{ all -> 0x003b }
        goto L_0x001e;
    L_0x003b:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x003b }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.filedownloader.util.FileDownloadSerialQueue.pause():void");
    }

    /* JADX WARNING: Missing block: B:17:?, code:
            return;
     */
    public void resume() {
        /*
        r5 = this;
        r1 = r5.finishCallback;
        monitor-enter(r1);
        r0 = r5.paused;	 Catch:{ all -> 0x0037 }
        if (r0 != 0) goto L_0x001f;
    L_0x0007:
        r0 = "require resume this queue(remain %d), but it is still running";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ all -> 0x0037 }
        r3 = 0;
        r4 = r5.mTasks;	 Catch:{ all -> 0x0037 }
        r4 = r4.size();	 Catch:{ all -> 0x0037 }
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ all -> 0x0037 }
        r2[r3] = r4;	 Catch:{ all -> 0x0037 }
        com.liulishuo.filedownloader.util.FileDownloadLog.w(r5, r0, r2);	 Catch:{ all -> 0x0037 }
        monitor-exit(r1);	 Catch:{ all -> 0x0037 }
    L_0x001e:
        return;
    L_0x001f:
        r0 = 0;
        r5.paused = r0;	 Catch:{ all -> 0x0037 }
        r0 = r5.mTasks;	 Catch:{ all -> 0x0037 }
        r2 = r5.pausedList;	 Catch:{ all -> 0x0037 }
        r0.addAll(r2);	 Catch:{ all -> 0x0037 }
        r0 = r5.pausedList;	 Catch:{ all -> 0x0037 }
        r0.clear();	 Catch:{ all -> 0x0037 }
        r0 = r5.workingTask;	 Catch:{ all -> 0x0037 }
        if (r0 != 0) goto L_0x003a;
    L_0x0032:
        r5.sendNext();	 Catch:{ all -> 0x0037 }
    L_0x0035:
        monitor-exit(r1);	 Catch:{ all -> 0x0037 }
        goto L_0x001e;
    L_0x0037:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0037 }
        throw r0;
    L_0x003a:
        r0 = r5.workingTask;	 Catch:{ all -> 0x0037 }
        r2 = r5.finishCallback;	 Catch:{ all -> 0x0037 }
        r0.addFinishListener(r2);	 Catch:{ all -> 0x0037 }
        r0 = r5.workingTask;	 Catch:{ all -> 0x0037 }
        r0.start();	 Catch:{ all -> 0x0037 }
        goto L_0x0035;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.filedownloader.util.FileDownloadSerialQueue.resume():void");
    }

    public int getWorkingTaskId() {
        return this.workingTask != null ? this.workingTask.getId() : 0;
    }

    public int getWaitingTaskCount() {
        return this.mTasks.size() + this.pausedList.size();
    }

    public List<BaseDownloadTask> shutdown() {
        List<BaseDownloadTask> unDealTaskList;
        synchronized (this.finishCallback) {
            if (this.workingTask != null) {
                pause();
            }
            unDealTaskList = new ArrayList(this.pausedList);
            this.pausedList.clear();
            this.mHandler.removeMessages(1);
            this.mHandlerThread.interrupt();
            this.mHandlerThread.quit();
        }
        return unDealTaskList;
    }

    private void sendNext() {
        this.mHandler.sendEmptyMessage(1);
    }
}
