package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class AsyncTimeout extends Timeout {
    private static final long IDLE_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(60);
    private static final long IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(IDLE_TIMEOUT_MILLIS);
    private static final int TIMEOUT_WRITE_SIZE = 65536;
    @Nullable
    static AsyncTimeout head;
    private boolean inQueue;
    @Nullable
    private AsyncTimeout next;
    private long timeoutAt;

    public final void enter() {
        if (this.inQueue) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }
        long timeoutNanos = timeoutNanos();
        boolean hasDeadline = hasDeadline();
        if (timeoutNanos != 0 || hasDeadline) {
            this.inQueue = true;
            scheduleTimeout(this, timeoutNanos, hasDeadline);
        }
    }

    private static synchronized void scheduleTimeout(AsyncTimeout node, long timeoutNanos, boolean hasDeadline) {
        synchronized (AsyncTimeout.class) {
            if (head == null) {
                head = new AsyncTimeout();
                new Watchdog().start();
            }
            long now = System.nanoTime();
            if (timeoutNanos != 0 && hasDeadline) {
                node.timeoutAt = Math.min(timeoutNanos, node.deadlineNanoTime() - now) + now;
            } else if (timeoutNanos != 0) {
                node.timeoutAt = now + timeoutNanos;
            } else if (hasDeadline) {
                node.timeoutAt = node.deadlineNanoTime();
            } else {
                throw new AssertionError();
            }
            long remainingNanos = node.remainingNanos(now);
            AsyncTimeout prev = head;
            while (prev.next != null && remainingNanos >= prev.next.remainingNanos(now)) {
                prev = prev.next;
            }
            node.next = prev.next;
            prev.next = node;
            if (prev == head) {
                AsyncTimeout.class.notify();
            }
        }
    }

    public final boolean exit() {
        if (!this.inQueue) {
            return false;
        }
        this.inQueue = false;
        return cancelScheduledTimeout(this);
    }

    private static synchronized boolean cancelScheduledTimeout(AsyncTimeout node) {
        boolean z;
        synchronized (AsyncTimeout.class) {
            for (AsyncTimeout prev = head; prev != null; prev = prev.next) {
                if (prev.next == node) {
                    prev.next = node.next;
                    node.next = null;
                    z = false;
                    break;
                }
            }
            z = true;
        }
        return z;
    }

    private long remainingNanos(long now) {
        return this.timeoutAt - now;
    }

    protected void timedOut() {
    }

    public final Sink sink(Sink sink) {
        return new 1(this, sink);
    }

    public final Source source(Source source) {
        return new 2(this, source);
    }

    final void exit(boolean throwOnTimeout) throws IOException {
        if (exit() && throwOnTimeout) {
            throw newTimeoutException(null);
        }
    }

    final IOException exit(IOException cause) throws IOException {
        return !exit() ? cause : newTimeoutException(cause);
    }

    protected IOException newTimeoutException(@Nullable IOException cause) {
        InterruptedIOException e = new InterruptedIOException("timeout");
        if (cause != null) {
            e.initCause(cause);
        }
        return e;
    }

    @Nullable
    static AsyncTimeout awaitTimeout() throws InterruptedException {
        AsyncTimeout node = head.next;
        if (node == null) {
            long startNanos = System.nanoTime();
            AsyncTimeout.class.wait(IDLE_TIMEOUT_MILLIS);
            if (head.next != null || System.nanoTime() - startNanos < IDLE_TIMEOUT_NANOS) {
                return null;
            }
            return head;
        }
        long waitNanos = node.remainingNanos(System.nanoTime());
        if (waitNanos > 0) {
            long waitMillis = waitNanos / 1000000;
            AsyncTimeout.class.wait(waitMillis, (int) (waitNanos - (waitMillis * 1000000)));
            return null;
        }
        head.next = node.next;
        node.next = null;
        return node;
    }
}
