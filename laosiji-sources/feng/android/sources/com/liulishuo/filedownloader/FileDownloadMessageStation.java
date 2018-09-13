package com.liulishuo.filedownloader;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.liulishuo.filedownloader.util.FileDownloadExecutors;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public class FileDownloadMessageStation {
    public static final int DEFAULT_INTERVAL = 10;
    public static final int DEFAULT_SUB_PACKAGE_SIZE = 5;
    static final int DISPOSE_MESSENGER_LIST = 2;
    static final int HANDOVER_A_MESSENGER = 1;
    static int INTERVAL = 10;
    static int SUB_PACKAGE_SIZE = 5;
    private final Executor blockCompletedPool;
    private final ArrayList<IFileDownloadMessenger> disposingList;
    private final Handler handler;
    private final Object queueLock;
    private final LinkedBlockingQueue<IFileDownloadMessenger> waitingQueue;

    private static final class HolderClass {
        private static final FileDownloadMessageStation INSTANCE = new FileDownloadMessageStation();

        private HolderClass() {
        }
    }

    private static class UIHandlerCallback implements Callback {
        private UIHandlerCallback() {
        }

        /* synthetic */ UIHandlerCallback(AnonymousClass1 x0) {
            this();
        }

        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                ((IFileDownloadMessenger) msg.obj).handoverMessage();
            } else if (msg.what == 2) {
                dispose((ArrayList) msg.obj);
                FileDownloadMessageStation.getImpl().push();
            }
            return true;
        }

        private void dispose(ArrayList<IFileDownloadMessenger> disposingList) {
            Iterator it = disposingList.iterator();
            while (it.hasNext()) {
                ((IFileDownloadMessenger) it.next()).handoverMessage();
            }
            disposingList.clear();
        }
    }

    /* synthetic */ FileDownloadMessageStation(AnonymousClass1 x0) {
        this();
    }

    public static FileDownloadMessageStation getImpl() {
        return HolderClass.INSTANCE;
    }

    private FileDownloadMessageStation() {
        this.blockCompletedPool = FileDownloadExecutors.newDefaultThreadPool(5, "BlockCompleted");
        this.queueLock = new Object();
        this.disposingList = new ArrayList();
        this.handler = new Handler(Looper.getMainLooper(), new UIHandlerCallback());
        this.waitingQueue = new LinkedBlockingQueue();
    }

    void requestEnqueue(IFileDownloadMessenger messenger) {
        requestEnqueue(messenger, false);
    }

    void requestEnqueue(final IFileDownloadMessenger messenger, boolean immediately) {
        if (messenger.handoverDirectly()) {
            messenger.handoverMessage();
        } else if (messenger.isBlockingCompleted()) {
            this.blockCompletedPool.execute(new Runnable() {
                public void run() {
                    messenger.handoverMessage();
                }
            });
        } else {
            if (!(isIntervalValid() || this.waitingQueue.isEmpty())) {
                synchronized (this.queueLock) {
                    if (!this.waitingQueue.isEmpty()) {
                        Iterator it = this.waitingQueue.iterator();
                        while (it.hasNext()) {
                            handoverInUIThread((IFileDownloadMessenger) it.next());
                        }
                    }
                    this.waitingQueue.clear();
                }
            }
            if (!isIntervalValid() || immediately) {
                handoverInUIThread(messenger);
            } else {
                enqueue(messenger);
            }
        }
    }

    private void handoverInUIThread(IFileDownloadMessenger messenger) {
        this.handler.sendMessage(this.handler.obtainMessage(1, messenger));
    }

    private void enqueue(IFileDownloadMessenger messenger) {
        synchronized (this.queueLock) {
            this.waitingQueue.offer(messenger);
        }
        push();
    }

    /* JADX WARNING: Missing block: B:17:0x0029, code:
            r8.handler.sendMessageDelayed(r8.handler.obtainMessage(2, r8.disposingList), (long) r0);
     */
    /* JADX WARNING: Missing block: B:25:?, code:
            return;
     */
    private void push() {
        /*
        r8 = this;
        r4 = r8.queueLock;
        monitor-enter(r4);
        r3 = r8.disposingList;	 Catch:{ all -> 0x0017 }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0017 }
        if (r3 != 0) goto L_0x000d;
    L_0x000b:
        monitor-exit(r4);	 Catch:{ all -> 0x0017 }
    L_0x000c:
        return;
    L_0x000d:
        r3 = r8.waitingQueue;	 Catch:{ all -> 0x0017 }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0017 }
        if (r3 == 0) goto L_0x001a;
    L_0x0015:
        monitor-exit(r4);	 Catch:{ all -> 0x0017 }
        goto L_0x000c;
    L_0x0017:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0017 }
        throw r3;
    L_0x001a:
        r3 = isIntervalValid();	 Catch:{ all -> 0x0017 }
        if (r3 != 0) goto L_0x0039;
    L_0x0020:
        r3 = r8.waitingQueue;	 Catch:{ all -> 0x0017 }
        r5 = r8.disposingList;	 Catch:{ all -> 0x0017 }
        r3.drainTo(r5);	 Catch:{ all -> 0x0017 }
        r0 = 0;
    L_0x0028:
        monitor-exit(r4);	 Catch:{ all -> 0x0017 }
        r3 = r8.handler;
        r4 = r8.handler;
        r5 = 2;
        r6 = r8.disposingList;
        r4 = r4.obtainMessage(r5, r6);
        r6 = (long) r0;
        r3.sendMessageDelayed(r4, r6);
        goto L_0x000c;
    L_0x0039:
        r0 = INTERVAL;	 Catch:{ all -> 0x0017 }
        r3 = r8.waitingQueue;	 Catch:{ all -> 0x0017 }
        r3 = r3.size();	 Catch:{ all -> 0x0017 }
        r5 = SUB_PACKAGE_SIZE;	 Catch:{ all -> 0x0017 }
        r2 = java.lang.Math.min(r3, r5);	 Catch:{ all -> 0x0017 }
        r1 = 0;
    L_0x0048:
        if (r1 >= r2) goto L_0x0028;
    L_0x004a:
        r3 = r8.disposingList;	 Catch:{ all -> 0x0017 }
        r5 = r8.waitingQueue;	 Catch:{ all -> 0x0017 }
        r5 = r5.remove();	 Catch:{ all -> 0x0017 }
        r3.add(r5);	 Catch:{ all -> 0x0017 }
        r1 = r1 + 1;
        goto L_0x0048;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.filedownloader.FileDownloadMessageStation.push():void");
    }

    public static boolean isIntervalValid() {
        return INTERVAL > 0;
    }
}
