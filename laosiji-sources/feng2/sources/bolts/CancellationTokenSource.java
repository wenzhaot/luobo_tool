package bolts;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CancellationTokenSource implements Closeable {
    private boolean cancellationRequested;
    private boolean closed;
    private final ScheduledExecutorService executor = BoltsExecutors.scheduled();
    private final Object lock = new Object();
    private final List<CancellationTokenRegistration> registrations = new ArrayList();
    private ScheduledFuture<?> scheduledCancellation;

    public boolean isCancellationRequested() {
        boolean z;
        synchronized (this.lock) {
            throwIfClosed();
            z = this.cancellationRequested;
        }
        return z;
    }

    public CancellationToken getToken() {
        CancellationToken cancellationToken;
        synchronized (this.lock) {
            throwIfClosed();
            cancellationToken = new CancellationToken(this);
        }
        return cancellationToken;
    }

    public void cancel() {
        synchronized (this.lock) {
            throwIfClosed();
            if (this.cancellationRequested) {
                return;
            }
            cancelScheduledCancellation();
            this.cancellationRequested = true;
            List<CancellationTokenRegistration> registrations = new ArrayList(this.registrations);
            notifyListeners(registrations);
        }
    }

    public void cancelAfter(long delay) {
        cancelAfter(delay, TimeUnit.MILLISECONDS);
    }

    /* JADX WARNING: Missing block: B:23:?, code:
            return;
     */
    private void cancelAfter(long r6, java.util.concurrent.TimeUnit r8) {
        /*
        r5 = this;
        r2 = -1;
        r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1));
        if (r0 >= 0) goto L_0x000f;
    L_0x0006:
        r0 = new java.lang.IllegalArgumentException;
        r1 = "Delay must be >= -1";
        r0.<init>(r1);
        throw r0;
    L_0x000f:
        r0 = 0;
        r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1));
        if (r0 != 0) goto L_0x0019;
    L_0x0015:
        r5.cancel();
    L_0x0018:
        return;
    L_0x0019:
        r1 = r5.lock;
        monitor-enter(r1);
        r0 = r5.cancellationRequested;	 Catch:{ all -> 0x0022 }
        if (r0 == 0) goto L_0x0025;
    L_0x0020:
        monitor-exit(r1);	 Catch:{ all -> 0x0022 }
        goto L_0x0018;
    L_0x0022:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0022 }
        throw r0;
    L_0x0025:
        r5.cancelScheduledCancellation();	 Catch:{ all -> 0x0022 }
        r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1));
        if (r0 == 0) goto L_0x0039;
    L_0x002c:
        r0 = r5.executor;	 Catch:{ all -> 0x0022 }
        r2 = new bolts.CancellationTokenSource$1;	 Catch:{ all -> 0x0022 }
        r2.<init>();	 Catch:{ all -> 0x0022 }
        r0 = r0.schedule(r2, r6, r8);	 Catch:{ all -> 0x0022 }
        r5.scheduledCancellation = r0;	 Catch:{ all -> 0x0022 }
    L_0x0039:
        monitor-exit(r1);	 Catch:{ all -> 0x0022 }
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.CancellationTokenSource.cancelAfter(long, java.util.concurrent.TimeUnit):void");
    }

    public void close() {
        synchronized (this.lock) {
            if (this.closed) {
                return;
            }
            cancelScheduledCancellation();
            for (CancellationTokenRegistration registration : this.registrations) {
                registration.close();
            }
            this.registrations.clear();
            this.closed = true;
        }
    }

    CancellationTokenRegistration register(Runnable action) {
        CancellationTokenRegistration ctr;
        synchronized (this.lock) {
            throwIfClosed();
            ctr = new CancellationTokenRegistration(this, action);
            if (this.cancellationRequested) {
                ctr.runAction();
            } else {
                this.registrations.add(ctr);
            }
        }
        return ctr;
    }

    void throwIfCancellationRequested() throws CancellationException {
        synchronized (this.lock) {
            throwIfClosed();
            if (this.cancellationRequested) {
                throw new CancellationException();
            }
        }
    }

    void unregister(CancellationTokenRegistration registration) {
        synchronized (this.lock) {
            throwIfClosed();
            this.registrations.remove(registration);
        }
    }

    private void notifyListeners(List<CancellationTokenRegistration> registrations) {
        for (CancellationTokenRegistration registration : registrations) {
            registration.runAction();
        }
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", new Object[]{getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(isCancellationRequested())});
    }

    private void throwIfClosed() {
        if (this.closed) {
            throw new IllegalStateException("Object already closed");
        }
    }

    private void cancelScheduledCancellation() {
        if (this.scheduledCancellation != null) {
            this.scheduledCancellation.cancel(true);
            this.scheduledCancellation = null;
        }
    }
}
