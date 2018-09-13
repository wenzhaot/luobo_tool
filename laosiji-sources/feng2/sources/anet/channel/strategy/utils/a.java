package anet.channel.strategy.utils;

import anet.channel.util.ALog;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: Taobao */
public class a {
    private static AtomicInteger a = new AtomicInteger(0);
    private static ScheduledThreadPoolExecutor b = null;

    static ScheduledThreadPoolExecutor a() {
        if (b == null) {
            synchronized (a.class) {
                if (b == null) {
                    b = new ScheduledThreadPoolExecutor(2, new b());
                    b.setKeepAliveTime(60, TimeUnit.SECONDS);
                    b.allowCoreThreadTimeOut(true);
                }
            }
        }
        return b;
    }

    public static void a(Runnable runnable) {
        try {
            a().submit(runnable);
        } catch (Throwable e) {
            ALog.e(b.TAG, "submit task failed", null, e, new Object[0]);
        }
    }

    public static void a(Runnable runnable, long j) {
        try {
            a().schedule(runnable, j, TimeUnit.MILLISECONDS);
        } catch (Throwable e) {
            ALog.e(b.TAG, "schedule task failed", null, e, new Object[0]);
        }
    }
}
