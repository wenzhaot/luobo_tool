package com.liulishuo.filedownloader.i;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.liulishuo.filedownloader.message.MessageSnapshot;

public interface IFileDownloadIPCCallback extends IInterface {

    public static abstract class Stub extends Binder implements IFileDownloadIPCCallback {
        private static final String DESCRIPTOR = "com.liulishuo.filedownloader.i.IFileDownloadIPCCallback";
        static final int TRANSACTION_callback = 1;

        private static class Proxy implements IFileDownloadIPCCallback {
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

            public void callback(MessageSnapshot snapshot) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (snapshot != null) {
                        _data.writeInt(1);
                        snapshot.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFileDownloadIPCCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IFileDownloadIPCCallback)) {
                return new Proxy(obj);
            }
            return (IFileDownloadIPCCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    MessageSnapshot _arg0;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = (MessageSnapshot) MessageSnapshot.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    callback(_arg0);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void callback(MessageSnapshot messageSnapshot) throws RemoteException;
}
