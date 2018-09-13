package com.liulishuo.filedownloader.i;

import android.app.Notification;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.liulishuo.filedownloader.model.FileDownloadHeader;

public interface IFileDownloadIPCService extends IInterface {

    public static abstract class Stub extends Binder implements IFileDownloadIPCService {
        private static final String DESCRIPTOR = "com.liulishuo.filedownloader.i.IFileDownloadIPCService";
        static final int TRANSACTION_checkDownloading = 3;
        static final int TRANSACTION_clearAllTaskData = 15;
        static final int TRANSACTION_clearTaskData = 14;
        static final int TRANSACTION_getSofar = 8;
        static final int TRANSACTION_getStatus = 10;
        static final int TRANSACTION_getTotal = 9;
        static final int TRANSACTION_isIdle = 11;
        static final int TRANSACTION_pause = 5;
        static final int TRANSACTION_pauseAllTasks = 6;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_setMaxNetworkThreadCount = 7;
        static final int TRANSACTION_start = 4;
        static final int TRANSACTION_startForeground = 12;
        static final int TRANSACTION_stopForeground = 13;
        static final int TRANSACTION_unregisterCallback = 2;

        private static class Proxy implements IFileDownloadIPCService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void registerCallback(IFileDownloadIPCCallback callback) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callback != null) {
                        iBinder = callback.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void unregisterCallback(IFileDownloadIPCCallback callback) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callback != null) {
                        iBinder = callback.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public boolean checkDownloading(String url, String path) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(url);
                    _data.writeString(path);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void start(String url, String path, boolean pathAsDirectory, int callbackProgressTimes, int callbackProgressMinIntervalMillis, int autoRetryTimes, boolean forceReDownload, FileDownloadHeader header, boolean isWifiRequired) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    int i2;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(url);
                    _data.writeString(path);
                    _data.writeInt(pathAsDirectory ? 1 : 0);
                    _data.writeInt(callbackProgressTimes);
                    _data.writeInt(callbackProgressMinIntervalMillis);
                    _data.writeInt(autoRetryTimes);
                    if (forceReDownload) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    _data.writeInt(i2);
                    if (header != null) {
                        _data.writeInt(1);
                        header.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!isWifiRequired) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean pause(int downloadId) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(downloadId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void pauseAllTasks() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setMaxNetworkThreadCount(int count) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(count);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getSofar(int downloadId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(downloadId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getTotal(int downloadId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(downloadId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte getStatus(int downloadId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(downloadId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    byte _result = _reply.readByte();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isIdle() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startForeground(int id, Notification notification) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    if (notification != null) {
                        _data.writeInt(1);
                        notification.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void stopForeground(boolean removeNotification) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!removeNotification) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public boolean clearTaskData(int id) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearAllTaskData() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFileDownloadIPCService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IFileDownloadIPCService)) {
                return new Proxy(obj);
            }
            return (IFileDownloadIPCService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _result;
            long _result2;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    registerCallback(com.liulishuo.filedownloader.i.IFileDownloadIPCCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    unregisterCallback(com.liulishuo.filedownloader.i.IFileDownloadIPCCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    _result = checkDownloading(data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 4:
                    FileDownloadHeader _arg7;
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    boolean _arg2 = data.readInt() != 0;
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    int _arg5 = data.readInt();
                    boolean _arg6 = data.readInt() != 0;
                    if (data.readInt() != 0) {
                        _arg7 = (FileDownloadHeader) FileDownloadHeader.CREATOR.createFromParcel(data);
                    } else {
                        _arg7 = null;
                    }
                    start(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    _result = pause(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    pauseAllTasks();
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    _result = setMaxNetworkThreadCount(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = getSofar(data.readInt());
                    reply.writeNoException();
                    reply.writeLong(_result2);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = getTotal(data.readInt());
                    reply.writeNoException();
                    reply.writeLong(_result2);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    byte _result3 = getStatus(data.readInt());
                    reply.writeNoException();
                    reply.writeByte(_result3);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isIdle();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 12:
                    Notification _arg12;
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg12 = (Notification) Notification.CREATOR.createFromParcel(data);
                    } else {
                        _arg12 = null;
                    }
                    startForeground(_arg02, _arg12);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    stopForeground(data.readInt() != 0);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    _result = clearTaskData(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    clearAllTaskData();
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    boolean checkDownloading(String str, String str2) throws RemoteException;

    void clearAllTaskData() throws RemoteException;

    boolean clearTaskData(int i) throws RemoteException;

    long getSofar(int i) throws RemoteException;

    byte getStatus(int i) throws RemoteException;

    long getTotal(int i) throws RemoteException;

    boolean isIdle() throws RemoteException;

    boolean pause(int i) throws RemoteException;

    void pauseAllTasks() throws RemoteException;

    void registerCallback(IFileDownloadIPCCallback iFileDownloadIPCCallback) throws RemoteException;

    boolean setMaxNetworkThreadCount(int i) throws RemoteException;

    void start(String str, String str2, boolean z, int i, int i2, int i3, boolean z2, FileDownloadHeader fileDownloadHeader, boolean z3) throws RemoteException;

    void startForeground(int i, Notification notification) throws RemoteException;

    void stopForeground(boolean z) throws RemoteException;

    void unregisterCallback(IFileDownloadIPCCallback iFileDownloadIPCCallback) throws RemoteException;
}
