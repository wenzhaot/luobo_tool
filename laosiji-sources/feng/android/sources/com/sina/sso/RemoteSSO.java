package com.sina.sso;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface RemoteSSO extends IInterface {

    public static abstract class Stub extends Binder implements RemoteSSO {
        static final int a = 1;
        static final int b = 2;
        static final int c = 3;
        private static final String d = "com.sina.sso.RemoteSSO";

        private static class Proxy implements RemoteSSO {
            private IBinder a;

            Proxy(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public String getActivityName() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.d);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.d;
            }

            public String getLoginUserName() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.d);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getPackageName() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.d);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, d);
        }

        public static RemoteSSO asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(d);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof RemoteSSO)) ? new Proxy(iBinder) : (RemoteSSO) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String packageName;
            switch (i) {
                case 1:
                    parcel.enforceInterface(d);
                    packageName = getPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(packageName);
                    return true;
                case 2:
                    parcel.enforceInterface(d);
                    packageName = getActivityName();
                    parcel2.writeNoException();
                    parcel2.writeString(packageName);
                    return true;
                case 3:
                    parcel.enforceInterface(d);
                    packageName = getLoginUserName();
                    parcel2.writeNoException();
                    parcel2.writeString(packageName);
                    return true;
                case 1598968902:
                    parcel2.writeString(d);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    String getActivityName();

    String getLoginUserName();

    String getPackageName();
}
