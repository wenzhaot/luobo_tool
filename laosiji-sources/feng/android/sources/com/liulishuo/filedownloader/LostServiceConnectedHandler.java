package com.liulishuo.filedownloader;

import com.liulishuo.filedownloader.BaseDownloadTask.IRunningTask;
import com.liulishuo.filedownloader.event.DownloadServiceConnectChangedEvent.ConnectStatus;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LostServiceConnectedHandler extends FileDownloadConnectListener implements ILostServiceConnectedHandler {
    private final ArrayList<IRunningTask> mWaitingList = new ArrayList();

    public void connected() {
        IQueuesHandler queueHandler = FileDownloader.getImpl().getQueuesHandler();
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "The downloader service is connected.", new Object[0]);
        }
        synchronized (this.mWaitingList) {
            List<IRunningTask> copyWaitingList = (List) this.mWaitingList.clone();
            this.mWaitingList.clear();
            List<Integer> wakeupSerialQueueKeyList = new ArrayList(queueHandler.serialQueueSize());
            for (IRunningTask task : copyWaitingList) {
                int attachKey = task.getAttachKey();
                if (queueHandler.contain(attachKey)) {
                    task.getOrigin().asInQueueTask().enqueue();
                    if (!wakeupSerialQueueKeyList.contains(Integer.valueOf(attachKey))) {
                        wakeupSerialQueueKeyList.add(Integer.valueOf(attachKey));
                    }
                } else {
                    task.startTaskByRescue();
                }
            }
            queueHandler.unFreezeSerialQueues(wakeupSerialQueueKeyList);
        }
    }

    public void disconnected() {
        if (getConnectStatus() == ConnectStatus.lost) {
            IQueuesHandler queueHandler = FileDownloader.getImpl().getQueuesHandler();
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "lost the connection to the file download service, and current active task size is %d", Integer.valueOf(FileDownloadList.getImpl().size()));
            }
            if (FileDownloadList.getImpl().size() > 0) {
                synchronized (this.mWaitingList) {
                    FileDownloadList.getImpl().divertAndIgnoreDuplicate(this.mWaitingList);
                    Iterator it = this.mWaitingList.iterator();
                    while (it.hasNext()) {
                        ((IRunningTask) it.next()).free();
                    }
                    queueHandler.freezeAllSerialQueues();
                }
                try {
                    FileDownloader.getImpl().bindService();
                } catch (IllegalStateException e) {
                    FileDownloadLog.w(this, "restart service failed, you may need to restart downloading manually when the app comes back to foreground", new Object[0]);
                }
            }
        } else if (FileDownloadList.getImpl().size() > 0) {
            FileDownloadLog.w(this, "file download service has be unbound but the size of active tasks are not empty %d ", Integer.valueOf(FileDownloadList.getImpl().size()));
        }
    }

    public boolean isInWaitingList(IRunningTask task) {
        return !this.mWaitingList.isEmpty() && this.mWaitingList.contains(task);
    }

    public void taskWorkFine(IRunningTask task) {
        if (!this.mWaitingList.isEmpty()) {
            synchronized (this.mWaitingList) {
                this.mWaitingList.remove(task);
            }
        }
    }

    /* JADX WARNING: Missing block: B:23:?, code:
            return true;
     */
    public boolean dispatchTaskStart(com.liulishuo.filedownloader.BaseDownloadTask.IRunningTask r7) {
        /*
        r6 = this;
        r0 = 1;
        r1 = 0;
        r2 = com.liulishuo.filedownloader.FileDownloader.getImpl();
        r2 = r2.isServiceConnected();
        if (r2 != 0) goto L_0x0053;
    L_0x000c:
        r2 = r6.mWaitingList;
        monitor-enter(r2);
        r3 = com.liulishuo.filedownloader.FileDownloader.getImpl();	 Catch:{ all -> 0x0058 }
        r3 = r3.isServiceConnected();	 Catch:{ all -> 0x0058 }
        if (r3 != 0) goto L_0x0052;
    L_0x0019:
        r1 = com.liulishuo.filedownloader.util.FileDownloadLog.NEED_LOG;	 Catch:{ all -> 0x0058 }
        if (r1 == 0) goto L_0x0035;
    L_0x001d:
        r1 = "Waiting for connecting with the downloader service... %d";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x0058 }
        r4 = 0;
        r5 = r7.getOrigin();	 Catch:{ all -> 0x0058 }
        r5 = r5.getId();	 Catch:{ all -> 0x0058 }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ all -> 0x0058 }
        r3[r4] = r5;	 Catch:{ all -> 0x0058 }
        com.liulishuo.filedownloader.util.FileDownloadLog.d(r6, r1, r3);	 Catch:{ all -> 0x0058 }
    L_0x0035:
        r1 = com.liulishuo.filedownloader.FileDownloadServiceProxy.getImpl();	 Catch:{ all -> 0x0058 }
        r3 = com.liulishuo.filedownloader.util.FileDownloadHelper.getAppContext();	 Catch:{ all -> 0x0058 }
        r1.bindStartByContext(r3);	 Catch:{ all -> 0x0058 }
        r1 = r6.mWaitingList;	 Catch:{ all -> 0x0058 }
        r1 = r1.contains(r7);	 Catch:{ all -> 0x0058 }
        if (r1 != 0) goto L_0x0050;
    L_0x0048:
        r7.free();	 Catch:{ all -> 0x0058 }
        r1 = r6.mWaitingList;	 Catch:{ all -> 0x0058 }
        r1.add(r7);	 Catch:{ all -> 0x0058 }
    L_0x0050:
        monitor-exit(r2);	 Catch:{ all -> 0x0058 }
    L_0x0051:
        return r0;
    L_0x0052:
        monitor-exit(r2);	 Catch:{ all -> 0x0058 }
    L_0x0053:
        r6.taskWorkFine(r7);
        r0 = r1;
        goto L_0x0051;
    L_0x0058:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0058 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.filedownloader.LostServiceConnectedHandler.dispatchTaskStart(com.liulishuo.filedownloader.BaseDownloadTask$IRunningTask):boolean");
    }
}
