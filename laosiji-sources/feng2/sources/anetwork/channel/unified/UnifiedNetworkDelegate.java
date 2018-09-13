package anetwork.channel.unified;

import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import anet.channel.bytes.ByteArray;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anet.channel.util.StringUtils;
import anetwork.channel.aidl.Connection;
import anetwork.channel.aidl.NetworkResponse;
import anetwork.channel.aidl.ParcelableFuture;
import anetwork.channel.aidl.ParcelableInputStream;
import anetwork.channel.aidl.ParcelableNetworkListener;
import anetwork.channel.aidl.ParcelableRequest;
import anetwork.channel.aidl.RemoteNetwork.Stub;
import anetwork.channel.aidl.adapter.ConnectionDelegate;
import anetwork.channel.aidl.adapter.ParcelableFutureResponse;
import anetwork.channel.aidl.adapter.ParcelableNetworkListenerWrapper;
import anetwork.channel.entity.Repeater;
import anetwork.channel.entity.RequestConfig;
import anetwork.channel.http.NetworkSdkSetting;
import java.io.ByteArrayOutputStream;

/* compiled from: Taobao */
public abstract class UnifiedNetworkDelegate extends Stub {
    public static final int DEGRADABLE = 1;
    public static final int HTTP = 0;
    private static final String TAG = "anet.UnifiedNetworkDelegate";
    protected int type = 1;

    public UnifiedNetworkDelegate(Context context) {
        NetworkSdkSetting.init(context);
    }

    public NetworkResponse syncSend(ParcelableRequest parcelableRequest) throws RemoteException {
        return convertToSync(parcelableRequest);
    }

    public ParcelableFuture asyncSend(ParcelableRequest parcelableRequest, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException {
        try {
            return asyncSend(new RequestConfig(parcelableRequest, this.type), parcelableNetworkListener);
        } catch (Throwable e) {
            ALog.e(TAG, "asyncSend failed", parcelableRequest.getSeqNo(), e, new Object[0]);
            throw new RemoteException(e.getMessage());
        }
    }

    private ParcelableFuture asyncSend(RequestConfig requestConfig, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException {
        return new ParcelableFutureResponse(new UnifiedRequestTask(requestConfig, new Repeater(parcelableNetworkListener, requestConfig)).request());
    }

    public Connection getConnection(ParcelableRequest parcelableRequest) throws RemoteException {
        try {
            RequestConfig requestConfig = new RequestConfig(parcelableRequest, this.type);
            Connection connectionDelegate = new ConnectionDelegate(requestConfig);
            connectionDelegate.setFuture(asyncSend(requestConfig, new ParcelableNetworkListenerWrapper(connectionDelegate, null, null)));
            return connectionDelegate;
        } catch (Throwable e) {
            ALog.e(TAG, "asyncSend failed", parcelableRequest.getSeqNo(), e, new Object[0]);
            throw new RemoteException(e.getMessage());
        }
    }

    private NetworkResponse convertToSync(ParcelableRequest parcelableRequest) {
        NetworkResponse networkResponse = new NetworkResponse();
        try {
            ConnectionDelegate connectionDelegate = (ConnectionDelegate) getConnection(parcelableRequest);
            networkResponse.setStatusCode(connectionDelegate.getStatusCode());
            networkResponse.setConnHeadFields(connectionDelegate.getConnHeadFields());
            ParcelableInputStream inputStream = connectionDelegate.getInputStream();
            if (inputStream != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(connectionDelegate.getInputStream().length());
                ByteArray a = a.a.a(2048);
                while (true) {
                    int read = inputStream.read(a.getBuffer());
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(a.getBuffer(), 0, read);
                }
                networkResponse.setBytedata(byteArrayOutputStream.toByteArray());
                networkResponse.setStatisticData(connectionDelegate.getStatisticData());
            }
            return networkResponse;
        } catch (RemoteException e) {
            networkResponse.setStatusCode(-103);
            Object message = e.getMessage();
            if (!TextUtils.isEmpty(message)) {
                networkResponse.setDesc(StringUtils.concatString(networkResponse.getDesc(), "|", message));
            }
            return networkResponse;
        } catch (Exception e2) {
            networkResponse.setStatusCode(ErrorConstant.ERROR_REQUEST_FAIL);
            return networkResponse;
        }
    }
}
