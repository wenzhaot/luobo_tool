package anetwork.channel.aidl.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.StatObject;
import anet.channel.util.ALog;
import anetwork.channel.Network;
import anetwork.channel.NetworkListener;
import anetwork.channel.Request;
import anetwork.channel.Response;
import anetwork.channel.aidl.Connection;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.aidl.IRemoteNetworkGetter;
import anetwork.channel.aidl.NetworkResponse;
import anetwork.channel.aidl.ParcelableFuture;
import anetwork.channel.aidl.ParcelableRequest;
import anetwork.channel.aidl.RemoteNetwork;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.http.HttpNetworkDelegate;
import java.util.concurrent.Future;

/* compiled from: Taobao */
public class NetworkProxy implements Network {
    protected static final int DEGRADE = 1;
    protected static final int HTTP = 0;
    protected static String TAG = "anet.NetworkProxy";
    private Context mContext;
    private RemoteNetwork mDelegate = null;
    private int mType = 0;

    public NetworkProxy(Context context, int i) {
        this.mContext = context;
        this.mType = i;
    }

    public Connection getConnection(Request request, Object obj) {
        initDelegateInstance(true);
        ParcelableRequest parcelableRequest = new ParcelableRequest(request);
        if (parcelableRequest.getURL() == null) {
            return new ConnectionDelegate(-102);
        }
        try {
            return this.mDelegate.getConnection(parcelableRequest);
        } catch (Throwable th) {
            reportRemoteError(th, "[getConnection]call getConnection method failed.");
            return new ConnectionDelegate(-103);
        }
    }

    public Response syncSend(Request request, Object obj) {
        initDelegateInstance(true);
        ParcelableRequest parcelableRequest = new ParcelableRequest(request);
        if (parcelableRequest.getURL() == null) {
            return new NetworkResponse(-102);
        }
        try {
            return this.mDelegate.syncSend(parcelableRequest);
        } catch (Throwable th) {
            reportRemoteError(th, "[syncSend]call syncSend method failed.");
            return new NetworkResponse(-103);
        }
    }

    private void initDelegateInstance(boolean z) {
        if (this.mDelegate == null) {
            if (NetworkConfigCenter.isRemoteNetworkServiceEnable()) {
                RemoteGetterHelper.initRemoteGetterAndWait(this.mContext, z);
                this.mDelegate = tryGetRemoteNetworkInstance(this.mType);
            }
            if (this.mDelegate == null) {
                if (ALog.isPrintLog(2)) {
                    ALog.i(TAG, "[getLocalNetworkInstance]", null, new Object[0]);
                }
                this.mDelegate = new HttpNetworkDelegate(this.mContext);
            }
        }
    }

    public Future<Response> asyncSend(Request request, Object obj, Handler handler, NetworkListener networkListener) {
        initDelegateInstance(Looper.myLooper() != Looper.getMainLooper());
        Future futureResponse = new FutureResponse();
        ParcelableRequest parcelableRequest = new ParcelableRequest(request);
        ParcelableNetworkListenerWrapper parcelableNetworkListenerWrapper = null;
        if (!(networkListener == null && handler == null)) {
            parcelableNetworkListenerWrapper = new ParcelableNetworkListenerWrapper(networkListener, handler, obj);
        }
        futureResponse.setFuture(redirectAsyncCall(this.mDelegate, parcelableRequest, parcelableNetworkListenerWrapper));
        return futureResponse;
    }

    private synchronized RemoteNetwork tryGetRemoteNetworkInstance(int i) {
        RemoteNetwork remoteNetwork = null;
        synchronized (this) {
            if (ALog.isPrintLog(2)) {
                ALog.i(TAG, "[tryGetRemoteNetworkInstance] type=" + i, null, new Object[0]);
            }
            IRemoteNetworkGetter remoteGetter = RemoteGetterHelper.getRemoteGetter();
            if (remoteGetter != null) {
                try {
                    remoteNetwork = remoteGetter.get(i);
                } catch (Throwable th) {
                    reportRemoteError(th, "[tryGetRemoteNetworkInstance]get RemoteNetwork Delegate failed.");
                }
            }
        }
        return remoteNetwork;
    }

    private ParcelableFuture redirectAsyncCall(RemoteNetwork remoteNetwork, ParcelableRequest parcelableRequest, ParcelableNetworkListenerWrapper parcelableNetworkListenerWrapper) {
        if (remoteNetwork == null) {
            return null;
        }
        if (parcelableRequest.getURL() == null) {
            return handleErrorCallBack(parcelableNetworkListenerWrapper, -102);
        }
        try {
            return remoteNetwork.asyncSend(parcelableRequest, parcelableNetworkListenerWrapper);
        } catch (Throwable th) {
            ParcelableFuture handleErrorCallBack = handleErrorCallBack(parcelableNetworkListenerWrapper, -103);
            reportRemoteError(th, "[redirectAsyncCall]call asyncSend exception.");
            return handleErrorCallBack;
        }
    }

    private ParcelableFuture handleErrorCallBack(ParcelableNetworkListenerWrapper parcelableNetworkListenerWrapper, int i) {
        if (parcelableNetworkListenerWrapper != null) {
            try {
                parcelableNetworkListenerWrapper.onFinished(new DefaultFinishEvent(i));
            } catch (Throwable e) {
                ALog.w(TAG, "[handleErrorCallBack]call listenerWrapper.onFinished exception.", null, e, new Object[0]);
            }
        }
        return new ErrorParcelableFuture(i);
    }

    private void reportRemoteError(Throwable th, String str) {
        ALog.e(TAG, null, str, th, new Object[0]);
        StatObject exceptionStatistic = new ExceptionStatistic(-103, null, "rt");
        exceptionStatistic.exceptionStack = th.toString();
        AppMonitor.getInstance().commitStat(exceptionStatistic);
    }
}
