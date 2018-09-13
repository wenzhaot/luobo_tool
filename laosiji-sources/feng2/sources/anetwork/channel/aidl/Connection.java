package anetwork.channel.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.Map;

/* compiled from: Taobao */
public interface Connection extends IInterface {

    /* compiled from: Taobao */
    public static abstract class Stub extends Binder implements Connection {
        private static final String DESCRIPTOR = "anetwork.channel.aidl.Connection";
        static final int TRANSACTION_cancel = 5;
        static final int TRANSACTION_getConnHeadFields = 4;
        static final int TRANSACTION_getDesc = 3;
        static final int TRANSACTION_getInputStream = 1;
        static final int TRANSACTION_getStatusCode = 2;

        /* compiled from: Taobao */
        private static class Proxy implements Connection {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public ParcelableInputStream getInputStream() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    ParcelableInputStream asInterface = anetwork.channel.aidl.ParcelableInputStream.Stub.asInterface(obtain2.readStrongBinder());
                    return asInterface;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getStatusCode() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getDesc() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Map getConnHeadFields() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    Map readHashMap = obtain2.readHashMap(getClass().getClassLoader());
                    return readHashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void cancel() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static Connection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof Connection)) {
                return new Proxy(iBinder);
            }
            return (Connection) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    ParcelableInputStream inputStream = getInputStream();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(inputStream != null ? inputStream.asBinder() : null);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    int statusCode = getStatusCode();
                    parcel2.writeNoException();
                    parcel2.writeInt(statusCode);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    String desc = getDesc();
                    parcel2.writeNoException();
                    parcel2.writeString(desc);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    Map connHeadFields = getConnHeadFields();
                    parcel2.writeNoException();
                    parcel2.writeMap(connHeadFields);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    cancel();
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void cancel() throws RemoteException;

    Map getConnHeadFields() throws RemoteException;

    String getDesc() throws RemoteException;

    ParcelableInputStream getInputStream() throws RemoteException;

    int getStatusCode() throws RemoteException;
}
