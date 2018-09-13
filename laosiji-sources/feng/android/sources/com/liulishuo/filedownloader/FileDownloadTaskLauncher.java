package com.liulishuo.filedownloader;

import com.liulishuo.filedownloader.ITaskHunter.IStarter;
import com.liulishuo.filedownloader.message.MessageSnapshotFlow;
import com.liulishuo.filedownloader.util.FileDownloadExecutors;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

class FileDownloadTaskLauncher {
    private final LaunchTaskPool mLaunchTaskPool = new LaunchTaskPool();

    private static class HolderClass {
        private static final FileDownloadTaskLauncher INSTANCE = new FileDownloadTaskLauncher();

        private HolderClass() {
        }

        static {
            MessageSnapshotFlow.getImpl().setReceiver(new MessageSnapshotGate());
        }
    }

    private static class LaunchTaskPool {
        private ThreadPoolExecutor mPool;
        private LinkedBlockingQueue<Runnable> mWorkQueue;

        LaunchTaskPool() {
            init();
        }

        public void asyncExecute(IStarter taskStarter) {
            this.mPool.execute(new LaunchTaskRunnable(taskStarter));
        }

        public void expire(IStarter starter) {
            this.mWorkQueue.remove(starter);
        }

        public void expire(FileDownloadListener listener) {
            if (listener == null) {
                FileDownloadLog.w(this, "want to expire by listener, but the listener provided is null", new Object[0]);
                return;
            }
            Runnable runnable;
            List<Runnable> needPauseList = new ArrayList();
            Iterator it = this.mWorkQueue.iterator();
            while (it.hasNext()) {
                runnable = (Runnable) it.next();
                LaunchTaskRunnable launchTaskRunnable = (LaunchTaskRunnable) runnable;
                if (launchTaskRunnable.isSameListener(listener)) {
                    launchTaskRunnable.expire();
                    needPauseList.add(runnable);
                }
            }
            if (!needPauseList.isEmpty()) {
                if (FileDownloadLog.NEED_LOG) {
                    FileDownloadLog.d(this, "expire %d tasks with listener[%s]", Integer.valueOf(needPauseList.size()), listener);
                }
                for (Runnable runnable2 : needPauseList) {
                    this.mPool.remove(runnable2);
                }
            }
        }

        public void expireAll() {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "expire %d tasks", Integer.valueOf(this.mWorkQueue.size()));
            }
            this.mPool.shutdownNow();
            init();
        }

        private void init() {
            this.mWorkQueue = new LinkedBlockingQueue();
            this.mPool = FileDownloadExecutors.newDefaultThreadPool(3, this.mWorkQueue, "LauncherTask");
        }
    }

    private static class LaunchTaskRunnable implements Runnable {
        private boolean mExpired = false;
        private final IStarter mTaskStarter;

        LaunchTaskRunnable(IStarter taskStarter) {
            this.mTaskStarter = taskStarter;
        }

        public void run() {
            if (!this.mExpired) {
                this.mTaskStarter.start();
            }
        }

        public boolean isSameListener(FileDownloadListener listener) {
            return this.mTaskStarter != null && this.mTaskStarter.equalListener(listener);
        }

        public boolean equals(Object obj) {
            return super.equals(obj) || obj == this.mTaskStarter;
        }

        public void expire() {
            this.mExpired = true;
        }
    }

    FileDownloadTaskLauncher() {
    }

    public static FileDownloadTaskLauncher getImpl() {
        return HolderClass.INSTANCE;
    }

    synchronized void launch(IStarter taskStarter) {
        this.mLaunchTaskPool.asyncExecute(taskStarter);
    }

    synchronized void expireAll() {
        this.mLaunchTaskPool.expireAll();
    }

    synchronized void expire(IStarter taskStarter) {
        this.mLaunchTaskPool.expire(taskStarter);
    }

    synchronized void expire(FileDownloadListener lis) {
        this.mLaunchTaskPool.expire(lis);
    }
}
