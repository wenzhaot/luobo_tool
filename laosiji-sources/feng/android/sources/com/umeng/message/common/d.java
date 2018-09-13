package com.umeng.message.common;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: UmengThreadPoolExecutorFactory */
public class d {
    private static final String a = d.class.getName();
    private static volatile ScheduledThreadPoolExecutor b;
    private static ThreadFactory c = new ThreadFactory() {
        private AtomicInteger a = new AtomicInteger(0);

        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("UMThreadPoolExecutor" + this.a.addAndGet(1));
            return thread;
        }
    };

    private static ScheduledThreadPoolExecutor a() {
        if (b == null) {
            synchronized (d.class) {
                if (b == null) {
                    b = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 4, c);
                }
            }
        }
        return b;
    }

    public static void a(Runnable runnable) {
        try {
            a().execute(runnable);
        } catch (Throwable th) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "UmengThreadPoolExecutorFactory execute exception");
        }
    }

    public static void a(Runnable runnable, long j, TimeUnit timeUnit) {
        try {
            a().schedule(runnable, j, timeUnit);
        } catch (Throwable th) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "UmengThreadPoolExecutorFactory schedule exception");
        }
    }
}
