package anetwork.channel.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: Taobao */
public interface ParcelableNetworkListener extends IInterface {

    /* compiled from: Taobao */
    public static abstract class Stub extends Binder implements ParcelableNetworkListener {
        private static final String DESCRIPTOR = "anetwork.channel.aidl.ParcelableNetworkListener";
        static final int TRANSACTION_getListenerState = 5;
        static final int TRANSACTION_onDataReceived = 1;
        static final int TRANSACTION_onFinished = 2;
        static final int TRANSACTION_onInputStreamGet = 4;
        static final int TRANSACTION_onResponseCode = 3;

        /* compiled from: Taobao */
        private static class Proxy implements ParcelableNetworkListener {
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

            public void onDataReceived(DefaultProgressEvent defaultProgressEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (defaultProgressEvent != null) {
                        obtain.writeInt(1);
                        defaultProgressEvent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onFinished(DefaultFinishEvent defaultFinishEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (defaultFinishEvent != null) {
                        obtain.writeInt(1);
                        defaultFinishEvent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean onResponseCode(int i, ParcelableHeader parcelableHeader) throws RemoteException {
                boolean z = true;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (parcelableHeader != null) {
                        obtain.writeInt(1);
                        parcelableHeader.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onInputStreamGet(ParcelableInputStream parcelableInputStream) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(parcelableInputStream != null ? parcelableInputStream.asBinder() : null);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public byte getListenerState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    byte readByte = obtain2.readByte();
                    return readByte;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ParcelableNetworkListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof ParcelableNetworkListener)) {
                return new Proxy(iBinder);
            }
            return (ParcelableNetworkListener) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            ParcelableHeader parcelableHeader = null;
            switch (i) {
                case 1:
                    DefaultProgressEvent defaultProgressEvent;
                    parcel.enforceInterface(DESCRIPTOR);
                    if (parcel.readInt() != 0) {
                        defaultProgressEvent = (DefaultProgressEvent) DefaultProgressEvent.CREATOR.createFromParcel(parcel);
                    }
                    onDataReceived(defaultProgressEvent);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    DefaultFinishEvent defaultFinishEvent;
                    parcel.enforceInterface(DESCRIPTOR);
                    if (parcel.readInt() != 0) {
                        defaultFinishEvent = (DefaultFinishEvent) DefaultFinishEvent.CREATOR.createFromParcel(parcel);
                    }
                    onFinished(defaultFinishEvent);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    int readInt = parcel.readInt();
                    if (parcel.readInt() != 0) {
                        parcelableHeader = (ParcelableHeader) ParcelableHeader.CREATOR.createFromParcel(parcel);
                    }
                    boolean onResponseCode = onResponseCode(readInt, parcelableHeader);
                    parcel2.writeNoException();
                    parcel2.writeInt(onResponseCode ? 1 : 0);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    onInputStreamGet(anetwork.channel.aidl.ParcelableInputStream.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    byte listenerState = getListenerState();
                    parcel2.writeNoException();
                    parcel2.writeByte(listenerState);
                    return true;
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    byte getListenerState() throws RemoteException;

    void onDataReceived(DefaultProgressEvent defaultProgressEvent) throws RemoteException;

    void onFinished(DefaultFinishEvent defaultFinishEvent) throws RemoteException;

    void onInputStreamGet(ParcelableInputStream parcelableInputStream) throws RemoteException;

    boolean onResponseCode(int i, ParcelableHeader parcelableHeader) throws RemoteException;
}
