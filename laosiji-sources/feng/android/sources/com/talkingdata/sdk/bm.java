package com.talkingdata.sdk;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: td */
final class bm implements ThreadFactory {
    private final AtomicInteger mCount = new AtomicInteger(1);

    bm() {
    }

    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "ThreadPoolUtils #" + this.mCount.getAndIncrement());
    }
}
