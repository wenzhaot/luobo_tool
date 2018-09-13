package com.talkingdata.sdk;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: td */
public final class bl {
    public static final ThreadPoolExecutor a;
    private static final int b = Runtime.getRuntime().availableProcessors();
    private static final int c = Math.max(2, Math.min(b - 1, 4));
    private static final int d = ((b * 2) + 1);
    private static final int e = 30;
    private static final ThreadFactory f = new bm();
    private static final BlockingQueue g = new LinkedBlockingQueue(128);

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(c, d, 30, TimeUnit.SECONDS, g, f);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        a = threadPoolExecutor;
    }
}
