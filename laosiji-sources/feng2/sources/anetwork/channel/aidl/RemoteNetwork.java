package anetwork.channel.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: Taobao */
public interface RemoteNetwork extends IInterface {

    /* compiled from: Taobao */
    public static abstract class Stub extends Binder implements RemoteNetwork {
        private static final String DESCRIPTOR = "anetwork.channel.aidl.RemoteNetwork";
        static final int TRANSACTION_asyncSend = 2;
        static final int TRANSACTION_getConnection = 3;
        static final int TRANSACTION_syncSend = 1;

        /* compiled from: Taobao */
        private static class Proxy implements RemoteNetwork {
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

            public NetworkResponse syncSend(ParcelableRequest parcelableRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    NetworkResponse networkResponse;
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableRequest != null) {
                        obtain.writeInt(1);
                        parcelableRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        networkResponse = (NetworkResponse) NetworkResponse.CREATOR.createFromParcel(obtain2);
                    } else {
                        networkResponse = null;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return networkResponse;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public ParcelableFuture asyncSend(ParcelableRequest parcelableRequest, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableRequest != null) {
                        obtain.writeInt(1);
                        parcelableRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(parcelableNetworkListener != null ? parcelableNetworkListener.asBinder() : null);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    ParcelableFuture asInterface = anetwork.channel.aidl.ParcelableFuture.Stub.asInterface(obtain2.readStrongBinder());
                    return asInterface;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Connection getConnection(ParcelableRequest parcelableRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableRequest != null) {
                        obtain.writeInt(1);
                        parcelableRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    Connection asInterface = anetwork.channel.aidl.Connection.Stub.asInterface(obtain2.readStrongBinder());
                    return asInterface;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static RemoteNetwork asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof RemoteNetwork)) {
                return new Proxy(iBinder);
            }
            return (RemoteNetwork) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            IBinder iBinder = null;
            ParcelableRequest parcelableRequest;
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    if (parcel.readInt() != 0) {
                        parcelableRequest = (ParcelableRequest) ParcelableRequest.CREATOR.createFromParcel(parcel);
                    } else {
                        parcelableRequest = null;
                    }
                    NetworkResponse syncSend = syncSend(parcelableRequest);
                    parcel2.writeNoException();
                    if (syncSend != null) {
                        parcel2.writeInt(1);
                        syncSend.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    if (parcel.readInt() != 0) {
                        parcelableRequest = (ParcelableRequest) ParcelableRequest.CREATOR.createFromParcel(parcel);
                    } else {
                        parcelableRequest = null;
                    }
                    ParcelableFuture asyncSend = asyncSend(parcelableRequest, anetwork.channel.aidl.ParcelableNetworkListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (asyncSend != null) {
                        iBinder = asyncSend.asBinder();
                    }
                    parcel2.writeStrongBinder(iBinder);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    if (parcel.readInt() != 0) {
                        parcelableRequest = (ParcelableRequest) ParcelableRequest.CREATOR.createFromParcel(parcel);
                    } else {
                        parcelableRequest = null;
                    }
                    Connection connection = getConnection(parcelableRequest);
                    parcel2.writeNoException();
                    if (connection != null) {
                        iBinder = connection.asBinder();
                    }
                    parcel2.writeStrongBinder(iBinder);
                    return true;
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    ParcelableFuture asyncSend(ParcelableRequest parcelableRequest, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException;

    Connection getConnection(ParcelableRequest parcelableRequest) throws RemoteException;

    NetworkResponse syncSend(ParcelableRequest parcelableRequest) throws RemoteException;
}
