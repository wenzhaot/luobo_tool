package com.liulishuo.filedownloader.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.liulishuo.filedownloader.FileDownloadEventPool;
import com.liulishuo.filedownloader.IFileDownloadServiceProxy;
import com.liulishuo.filedownloader.event.DownloadServiceConnectChangedEvent;
import com.liulishuo.filedownloader.event.DownloadServiceConnectChangedEvent.ConnectStatus;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseFileServiceUIGuard<CALLBACK extends Binder, INTERFACE extends IInterface> implements IFileDownloadServiceProxy, ServiceConnection {
    private final List<Context> bindContexts = new ArrayList();
    private final CALLBACK callback;
    private final ArrayList<Runnable> connectedRunnableList = new ArrayList();
    private volatile INTERFACE service;
    private final Class<?> serviceClass;
    private final HashMap<String, Object> uiCacheMap = new HashMap();

    protected abstract INTERFACE asInterface(IBinder iBinder);

    protected abstract CALLBACK createCallback();

    protected abstract void registerCallback(INTERFACE interfaceR, CALLBACK callback) throws RemoteException;

    protected abstract void unregisterCallback(INTERFACE interfaceR, CALLBACK callback) throws RemoteException;

    protected CALLBACK getCallback() {
        return this.callback;
    }

    protected INTERFACE getService() {
        return this.service;
    }

    protected BaseFileServiceUIGuard(Class<?> serviceClass) {
        this.serviceClass = serviceClass;
        this.callback = createCallback();
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        this.service = asInterface(service);
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "onServiceConnected %s %s", name, this.service);
        }
        try {
            registerCallback(this.service, this.callback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        List<Runnable> runnableList = (List) this.connectedRunnableList.clone();
        this.connectedRunnableList.clear();
        for (Runnable runnable : runnableList) {
            runnable.run();
        }
        FileDownloadEventPool.getImpl().asyncPublishInNewThread(new DownloadServiceConnectChangedEvent(ConnectStatus.connected, this.serviceClass));
    }

    public void onServiceDisconnected(ComponentName name) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "onServiceDisconnected %s %s", name, this.service);
        }
        releaseConnect(true);
    }

    private void releaseConnect(boolean isLost) {
        if (!(isLost || this.service == null)) {
            try {
                unregisterCallback(this.service, this.callback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "release connect resources %s", this.service);
        }
        this.service = null;
        FileDownloadEventPool.getImpl().asyncPublishInNewThread(new DownloadServiceConnectChangedEvent(isLost ? ConnectStatus.lost : ConnectStatus.disconnected, this.serviceClass));
    }

    public void bindStartByContext(Context context) {
        bindStartByContext(context, null);
    }

    public void bindStartByContext(Context context, Runnable connectedRunnable) {
        if (FileDownloadUtils.isDownloaderProcess(context)) {
            throw new IllegalStateException("Fatal-Exception: You can't bind the FileDownloadService in :filedownloader process.\n It's the invalid operation and is likely to cause unexpected problems.\n Maybe you want to use non-separate process mode for FileDownloader, More detail about non-separate mode, please move to wiki manually: https://github.com/lingochamp/FileDownloader/wiki/filedownloader.properties");
        }
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "bindStartByContext %s", context.getClass().getSimpleName());
        }
        Intent i = new Intent(context, this.serviceClass);
        if (!(connectedRunnable == null || this.connectedRunnableList.contains(connectedRunnable))) {
            this.connectedRunnableList.add(connectedRunnable);
        }
        if (!this.bindContexts.contains(context)) {
            this.bindContexts.add(context);
        }
        context.bindService(i, this, 1);
        context.startService(i);
    }

    public void unbindByContext(Context context) {
        if (this.bindContexts.contains(context)) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "unbindByContext %s", context);
            }
            this.bindContexts.remove(context);
            if (this.bindContexts.isEmpty()) {
                releaseConnect(false);
            }
            Intent i = new Intent(context, this.serviceClass);
            context.unbindService(this);
            context.stopService(i);
        }
    }

    public void startService(Context context) {
        context.startService(new Intent(context, this.serviceClass));
    }

    protected Object popCache(String key) {
        return this.uiCacheMap.remove(key);
    }

    protected String putCache(Object object) {
        if (object == null) {
            return null;
        }
        String key = object.toString();
        this.uiCacheMap.put(key, object);
        return key;
    }

    public boolean isConnected() {
        return getService() != null;
    }
}
