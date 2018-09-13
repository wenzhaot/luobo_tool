package com.liulishuo.filedownloader.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FileDownloadExecutors {
    private static final int DEFAULT_IDLE_SECOND = 15;

    static class FileDownloadThreadFactory implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group = Thread.currentThread().getThreadGroup();
        private final String namePrefix;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        FileDownloadThreadFactory(String prefix) {
            this.namePrefix = FileDownloadUtils.getThreadPoolName(prefix);
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != 5) {
                t.setPriority(5);
            }
            return t;
        }
    }

    public static ThreadPoolExecutor newFixedThreadPool(String prefix) {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 15, TimeUnit.SECONDS, new SynchronousQueue(), new FileDownloadThreadFactory(prefix));
    }

    public static ThreadPoolExecutor newDefaultThreadPool(int nThreads, String prefix) {
        return newDefaultThreadPool(nThreads, new LinkedBlockingQueue(), prefix);
    }

    public static ThreadPoolExecutor newDefaultThreadPool(int nThreads, LinkedBlockingQueue<Runnable> queue, String prefix) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(nThreads, nThreads, 15, TimeUnit.SECONDS, queue, new FileDownloadThreadFactory(prefix));
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }
}
