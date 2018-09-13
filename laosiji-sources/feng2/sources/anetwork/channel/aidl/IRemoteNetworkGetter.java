package anetwork.channel.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: Taobao */
public interface IRemoteNetworkGetter extends IInterface {

    /* compiled from: Taobao */
    public static abstract class Stub extends Binder implements IRemoteNetworkGetter {
        private static final String DESCRIPTOR = "anetwork.channel.aidl.IRemoteNetworkGetter";
        static final int TRANSACTION_get = 1;

        /* compiled from: Taobao */
        private static class Proxy implements IRemoteNetworkGetter {
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

            public RemoteNetwork get(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    RemoteNetwork asInterface = anetwork.channel.aidl.RemoteNetwork.Stub.asInterface(obtain2.readStrongBinder());
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

        public static IRemoteNetworkGetter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IRemoteNetworkGetter)) {
                return new Proxy(iBinder);
            }
            return (IRemoteNetworkGetter) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    RemoteNetwork remoteNetwork = get(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(remoteNetwork != null ? remoteNetwork.asBinder() : null);
                    return true;
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    RemoteNetwork get(int i) throws RemoteException;
}
