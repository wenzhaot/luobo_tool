package com.liulishuo.filedownloader;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import com.liulishuo.filedownloader.BaseDownloadTask.IRunningTask;
import com.liulishuo.filedownloader.download.CustomComponentHolder;
import com.liulishuo.filedownloader.event.DownloadServiceConnectChangedEvent;
import com.liulishuo.filedownloader.model.FileDownloadTaskAtom;
import com.liulishuo.filedownloader.services.DownloadMgrInitialParams.InitCustomMaker;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.stub.StubApp;
import java.io.File;
import java.util.List;

public class FileDownloader {
    private static final Object INIT_LOST_CONNECTED_HANDLER_LOCK = new Object();
    private static final Object INIT_QUEUES_HANDLER_LOCK = new Object();
    private ILostServiceConnectedHandler mLostConnectedHandler;
    private IQueuesHandler mQueuesHandler;
    private Runnable pauseAllRunnable;

    private static final class HolderClass {
        private static final FileDownloader INSTANCE = new FileDownloader();

        private HolderClass() {
        }
    }

    public static void setup(Context context) {
        FileDownloadHelper.holdContext(StubApp.getOrigApplicationContext(context.getApplicationContext()));
    }

    public static InitCustomMaker setupOnApplicationOnCreate(Application application) {
        FileDownloadHelper.holdContext(StubApp.getOrigApplicationContext(application.getApplicationContext()));
        InitCustomMaker customMaker = new InitCustomMaker();
        CustomComponentHolder.getImpl().setInitCustomMaker(customMaker);
        return customMaker;
    }

    public static void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("the provided context must not be null!");
        }
        setup(context);
    }

    public static void init(Context context, InitCustomMaker maker) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(FileDownloader.class, "init Downloader with params: %s %s", context, maker);
        }
        if (context == null) {
            throw new IllegalArgumentException("the provided context must not be null!");
        }
        FileDownloadHelper.holdContext(StubApp.getOrigApplicationContext(context.getApplicationContext()));
        CustomComponentHolder.getImpl().setInitCustomMaker(maker);
    }

    public static FileDownloader getImpl() {
        return HolderClass.INSTANCE;
    }

    public static void setGlobalPost2UIInterval(int intervalMillisecond) {
        FileDownloadMessageStation.INTERVAL = intervalMillisecond;
    }

    public static void setGlobalHandleSubPackageSize(int packageSize) {
        if (packageSize <= 0) {
            throw new IllegalArgumentException("sub package size must more than 0");
        }
        FileDownloadMessageStation.SUB_PACKAGE_SIZE = packageSize;
    }

    public static void enableAvoidDropFrame() {
        setGlobalPost2UIInterval(10);
    }

    public static void disableAvoidDropFrame() {
        setGlobalPost2UIInterval(-1);
    }

    public static boolean isEnabledAvoidDropFrame() {
        return FileDownloadMessageStation.isIntervalValid();
    }

    public BaseDownloadTask create(String url) {
        return new DownloadTask(url);
    }

    public boolean start(FileDownloadListener listener, boolean isSerial) {
        if (listener == null) {
            FileDownloadLog.w(this, "Tasks with the listener can't start, because the listener provided is null: [null, %B]", Boolean.valueOf(isSerial));
            return false;
        } else if (isSerial) {
            return getQueuesHandler().startQueueSerial(listener);
        } else {
            return getQueuesHandler().startQueueParallel(listener);
        }
    }

    public void pause(FileDownloadListener listener) {
        FileDownloadTaskLauncher.getImpl().expire(listener);
        for (IRunningTask task : FileDownloadList.getImpl().copy(listener)) {
            task.getOrigin().pause();
        }
    }

    public void pauseAll() {
        FileDownloadTaskLauncher.getImpl().expireAll();
        for (IRunningTask task : FileDownloadList.getImpl().copy()) {
            task.getOrigin().pause();
        }
        if (FileDownloadServiceProxy.getImpl().isConnected()) {
            FileDownloadServiceProxy.getImpl().pauseAllTasks();
            return;
        }
        if (this.pauseAllRunnable == null) {
            this.pauseAllRunnable = new Runnable() {
                public void run() {
                    FileDownloadServiceProxy.getImpl().pauseAllTasks();
                }
            };
        }
        FileDownloadServiceProxy.getImpl().bindStartByContext(FileDownloadHelper.getAppContext(), this.pauseAllRunnable);
    }

    public int pause(int id) {
        List<IRunningTask> taskList = FileDownloadList.getImpl().getDownloadingList(id);
        if (taskList == null || taskList.isEmpty()) {
            FileDownloadLog.w(this, "request pause but not exist %d", Integer.valueOf(id));
            return 0;
        }
        for (IRunningTask task : taskList) {
            task.getOrigin().pause();
        }
        return taskList.size();
    }

    public boolean clear(int id, String targetFilePath) {
        pause(id);
        if (!FileDownloadServiceProxy.getImpl().clearTaskData(id)) {
            return false;
        }
        File intermediateFile = new File(FileDownloadUtils.getTempPath(targetFilePath));
        if (intermediateFile.exists()) {
            intermediateFile.delete();
        }
        File targetFile = new File(targetFilePath);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        return true;
    }

    public void clearAllTaskData() {
        pauseAll();
        FileDownloadServiceProxy.getImpl().clearAllTaskData();
    }

    public long getSoFar(int downloadId) {
        IRunningTask task = FileDownloadList.getImpl().get(downloadId);
        if (task == null) {
            return FileDownloadServiceProxy.getImpl().getSofar(downloadId);
        }
        return task.getOrigin().getLargeFileSoFarBytes();
    }

    public long getTotal(int id) {
        IRunningTask task = FileDownloadList.getImpl().get(id);
        if (task == null) {
            return FileDownloadServiceProxy.getImpl().getTotal(id);
        }
        return task.getOrigin().getLargeFileTotalBytes();
    }

    public byte getStatusIgnoreCompleted(int id) {
        return getStatus(id, null);
    }

    public byte getStatus(String url, String path) {
        return getStatus(FileDownloadUtils.generateId(url, path), path);
    }

    public byte getStatus(int id, String path) {
        byte status;
        IRunningTask task = FileDownloadList.getImpl().get(id);
        if (task == null) {
            status = FileDownloadServiceProxy.getImpl().getStatus(id);
        } else {
            status = task.getOrigin().getStatus();
        }
        if (path != null && status == (byte) 0 && FileDownloadUtils.isFilenameConverted(FileDownloadHelper.getAppContext()) && new File(path).exists()) {
            return (byte) -3;
        }
        return status;
    }

    public int replaceListener(String url, FileDownloadListener listener) {
        return replaceListener(url, FileDownloadUtils.getDefaultSaveFilePath(url), listener);
    }

    public int replaceListener(String url, String path, FileDownloadListener listener) {
        return replaceListener(FileDownloadUtils.generateId(url, path), listener);
    }

    public int replaceListener(int id, FileDownloadListener listener) {
        IRunningTask task = FileDownloadList.getImpl().get(id);
        if (task == null) {
            return 0;
        }
        task.getOrigin().setListener(listener);
        return task.getOrigin().getId();
    }

    public void bindService() {
        if (!isServiceConnected()) {
            FileDownloadServiceProxy.getImpl().bindStartByContext(FileDownloadHelper.getAppContext());
        }
    }

    public void bindService(Runnable runnable) {
        if (isServiceConnected()) {
            runnable.run();
        } else {
            FileDownloadServiceProxy.getImpl().bindStartByContext(FileDownloadHelper.getAppContext(), runnable);
        }
    }

    public void unBindService() {
        if (isServiceConnected()) {
            FileDownloadServiceProxy.getImpl().unbindByContext(FileDownloadHelper.getAppContext());
        }
    }

    public boolean unBindServiceIfIdle() {
        if (!isServiceConnected() || !FileDownloadList.getImpl().isEmpty() || !FileDownloadServiceProxy.getImpl().isIdle()) {
            return false;
        }
        unBindService();
        return true;
    }

    public boolean isServiceConnected() {
        return FileDownloadServiceProxy.getImpl().isConnected();
    }

    public void addServiceConnectListener(FileDownloadConnectListener listener) {
        FileDownloadEventPool.getImpl().addListener(DownloadServiceConnectChangedEvent.ID, listener);
    }

    public void removeServiceConnectListener(FileDownloadConnectListener listener) {
        FileDownloadEventPool.getImpl().removeListener(DownloadServiceConnectChangedEvent.ID, listener);
    }

    public void startForeground(int id, Notification notification) {
        FileDownloadServiceProxy.getImpl().startForeground(id, notification);
    }

    public void stopForeground(boolean removeNotification) {
        FileDownloadServiceProxy.getImpl().stopForeground(removeNotification);
    }

    public boolean setTaskCompleted(String url, String path, long totalBytes) {
        FileDownloadLog.w(this, "If you invoked this method, please remove it directly feel free, it doesn't need any longer", new Object[0]);
        return true;
    }

    public boolean setTaskCompleted(List<FileDownloadTaskAtom> list) {
        FileDownloadLog.w(this, "If you invoked this method, please remove it directly feel free, it doesn't need any longer", new Object[0]);
        return true;
    }

    public boolean setMaxNetworkThreadCount(int count) {
        if (FileDownloadList.getImpl().isEmpty()) {
            return FileDownloadServiceProxy.getImpl().setMaxNetworkThreadCount(count);
        }
        FileDownloadLog.w(this, "Can't change the max network thread count, because there are actively executing tasks in FileDownloader, please try again after all actively executing tasks are completed or invoking FileDownloader#pauseAll directly.", new Object[0]);
        return false;
    }

    public FileDownloadLine insureServiceBind() {
        return new FileDownloadLine();
    }

    public FileDownloadLineAsync insureServiceBindAsync() {
        return new FileDownloadLineAsync();
    }

    IQueuesHandler getQueuesHandler() {
        if (this.mQueuesHandler == null) {
            synchronized (INIT_QUEUES_HANDLER_LOCK) {
                if (this.mQueuesHandler == null) {
                    this.mQueuesHandler = new QueuesHandler();
                }
            }
        }
        return this.mQueuesHandler;
    }

    ILostServiceConnectedHandler getLostConnectedHandler() {
        if (this.mLostConnectedHandler == null) {
            synchronized (INIT_LOST_CONNECTED_HANDLER_LOCK) {
                if (this.mLostConnectedHandler == null) {
                    this.mLostConnectedHandler = new LostServiceConnectedHandler();
                    addServiceConnectListener((FileDownloadConnectListener) this.mLostConnectedHandler);
                }
            }
        }
        return this.mLostConnectedHandler;
    }
}
