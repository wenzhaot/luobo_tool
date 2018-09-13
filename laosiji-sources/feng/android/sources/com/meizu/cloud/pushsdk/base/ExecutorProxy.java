package com.meizu.cloud.pushsdk.base;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorProxy extends Proxy<Executor> implements Executor {
    private static ExecutorProxy sInstance;

    public static ExecutorProxy get() {
        if (sInstance == null) {
            synchronized (ExecutorProxy.class) {
                if (sInstance == null) {
                    sInstance = new ExecutorProxy(new ThreadPoolExecutor(0, 5, 30, TimeUnit.SECONDS, new LinkedBlockingDeque(100), new RejectedExecutionHandler() {
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                            new Thread(r).start();
                        }
                    }));
                }
            }
        }
        return sInstance;
    }

    protected ExecutorProxy(Executor innerImpl) {
        super(innerImpl);
    }

    public void execute(Runnable command) {
        ((Executor) getImpl()).execute(command);
    }
}
