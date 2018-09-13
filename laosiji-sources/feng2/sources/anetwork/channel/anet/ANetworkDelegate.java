package anetwork.channel.anet;

import android.os.RemoteException;
import anetwork.channel.aidl.Connection;
import anetwork.channel.aidl.NetworkResponse;
import anetwork.channel.aidl.ParcelableFuture;
import anetwork.channel.aidl.ParcelableNetworkListener;
import anetwork.channel.aidl.ParcelableRequest;
import anetwork.channel.aidl.RemoteNetwork.Stub;

@Deprecated
/* compiled from: Taobao */
public class ANetworkDelegate extends Stub {
    @Deprecated
    public NetworkResponse syncSend(ParcelableRequest parcelableRequest) {
        return null;
    }

    @Deprecated
    public ParcelableFuture asyncSend(ParcelableRequest parcelableRequest, ParcelableNetworkListener parcelableNetworkListener) {
        return null;
    }

    @Deprecated
    public Connection getConnection(ParcelableRequest parcelableRequest) throws RemoteException {
        return null;
    }
}
