package com.liulishuo.filedownloader.event;

import com.liulishuo.filedownloader.util.FileDownloadExecutors;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Executor;

public class DownloadEventPoolImpl implements IDownloadEventPool {
    private final HashMap<String, LinkedList<IDownloadListener>> listenersMap = new HashMap();
    private final Executor threadPool = FileDownloadExecutors.newDefaultThreadPool(10, "EventPool");

    public boolean addListener(String eventId, IDownloadListener listener) {
        Throwable th;
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.v(this, "setListener %s", eventId);
        }
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null!");
        }
        boolean add;
        LinkedList<IDownloadListener> container = (LinkedList) this.listenersMap.get(eventId);
        if (container == null) {
            synchronized (eventId.intern()) {
                container = (LinkedList) this.listenersMap.get(eventId);
                if (container == null) {
                    HashMap hashMap = this.listenersMap;
                    LinkedList<IDownloadListener> container2 = new LinkedList();
                    try {
                        hashMap.put(eventId, container2);
                        container = container2;
                    } catch (Throwable th2) {
                        th = th2;
                        container = container2;
                        throw th;
                    }
                }
                try {
                } catch (Throwable th3) {
                    th = th3;
                    throw th;
                }
            }
        }
        synchronized (eventId.intern()) {
            add = container.add(listener);
        }
        return add;
    }

    public boolean removeListener(String eventId, IDownloadListener listener) {
        boolean succeed = false;
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.v(this, "removeListener %s", eventId);
        }
        LinkedList<IDownloadListener> container = (LinkedList) this.listenersMap.get(eventId);
        if (container == null) {
            synchronized (eventId.intern()) {
                container = (LinkedList) this.listenersMap.get(eventId);
            }
        }
        if (!(container == null || listener == null)) {
            synchronized (eventId.intern()) {
                succeed = container.remove(listener);
                if (container.size() <= 0) {
                    this.listenersMap.remove(eventId);
                }
            }
        }
        return succeed;
    }

    /* JADX WARNING: Missing block: B:27:?, code:
            return false;
     */
    public boolean publish(com.liulishuo.filedownloader.event.IDownloadEvent r9) {
        /*
        r8 = this;
        r5 = 1;
        r4 = 0;
        r3 = com.liulishuo.filedownloader.util.FileDownloadLog.NEED_LOG;
        if (r3 == 0) goto L_0x0014;
    L_0x0006:
        r3 = "publish %s";
        r6 = new java.lang.Object[r5];
        r7 = r9.getId();
        r6[r4] = r7;
        com.liulishuo.filedownloader.util.FileDownloadLog.v(r8, r3, r6);
    L_0x0014:
        if (r9 != 0) goto L_0x001f;
    L_0x0016:
        r3 = new java.lang.IllegalArgumentException;
        r4 = "event must not be null!";
        r3.<init>(r4);
        throw r3;
    L_0x001f:
        r1 = r9.getId();
        r3 = r8.listenersMap;
        r2 = r3.get(r1);
        r2 = (java.util.LinkedList) r2;
        if (r2 != 0) goto L_0x0052;
    L_0x002d:
        r6 = r1.intern();
        monitor-enter(r6);
        r3 = r8.listenersMap;	 Catch:{ all -> 0x0057 }
        r3 = r3.get(r1);	 Catch:{ all -> 0x0057 }
        r0 = r3;
        r0 = (java.util.LinkedList) r0;	 Catch:{ all -> 0x0057 }
        r2 = r0;
        if (r2 != 0) goto L_0x0051;
    L_0x003e:
        r3 = com.liulishuo.filedownloader.util.FileDownloadLog.NEED_LOG;	 Catch:{ all -> 0x0057 }
        if (r3 == 0) goto L_0x004e;
    L_0x0042:
        r3 = "No listener for this event %s";
        r5 = 1;
        r5 = new java.lang.Object[r5];	 Catch:{ all -> 0x0057 }
        r7 = 0;
        r5[r7] = r1;	 Catch:{ all -> 0x0057 }
        com.liulishuo.filedownloader.util.FileDownloadLog.d(r8, r3, r5);	 Catch:{ all -> 0x0057 }
    L_0x004e:
        monitor-exit(r6);	 Catch:{ all -> 0x0057 }
        r3 = r4;
    L_0x0050:
        return r3;
    L_0x0051:
        monitor-exit(r6);	 Catch:{ all -> 0x0057 }
    L_0x0052:
        r8.trigger(r2, r9);
        r3 = r5;
        goto L_0x0050;
    L_0x0057:
        r3 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0057 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.filedownloader.event.DownloadEventPoolImpl.publish(com.liulishuo.filedownloader.event.IDownloadEvent):boolean");
    }

    public void asyncPublishInNewThread(final IDownloadEvent event) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.v(this, "asyncPublishInNewThread %s", event.getId());
        }
        if (event == null) {
            throw new IllegalArgumentException("event must not be null!");
        }
        this.threadPool.execute(new Runnable() {
            public void run() {
                DownloadEventPoolImpl.this.publish(event);
            }
        });
    }

    private void trigger(LinkedList<IDownloadListener> listeners, IDownloadEvent event) {
        for (Object o : listeners.toArray()) {
            if (o != null && ((IDownloadListener) o).callback(event)) {
                break;
            }
        }
        if (event.callback != null) {
            event.callback.run();
        }
    }
}
