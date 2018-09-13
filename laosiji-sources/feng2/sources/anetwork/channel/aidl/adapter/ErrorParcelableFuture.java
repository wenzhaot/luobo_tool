package anetwork.channel.aidl.adapter;

import android.os.RemoteException;
import anetwork.channel.aidl.NetworkResponse;
import anetwork.channel.aidl.ParcelableFuture.Stub;

/* compiled from: Taobao */
public class ErrorParcelableFuture extends Stub {
    int error;

    public ErrorParcelableFuture(int i) {
        this.error = i;
    }

    public boolean cancel(boolean z) throws RemoteException {
        return false;
    }

    public boolean isCancelled() throws RemoteException {
        return false;
    }

    public boolean isDone() throws RemoteException {
        return true;
    }

    public NetworkResponse get(long j) throws RemoteException {
        return new NetworkResponse(this.error);
    }
}
