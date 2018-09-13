package anetwork.channel.aidl.adapter;

import android.os.Handler;
import android.os.RemoteException;
import anet.channel.util.ALog;
import anetwork.channel.NetworkCallBack.FinishListener;
import anetwork.channel.NetworkCallBack.InputStreamListener;
import anetwork.channel.NetworkCallBack.ProgressListener;
import anetwork.channel.NetworkCallBack.ResponseCodeListener;
import anetwork.channel.NetworkListener;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.aidl.DefaultProgressEvent;
import anetwork.channel.aidl.ParcelableHeader;
import anetwork.channel.aidl.ParcelableInputStream;
import anetwork.channel.aidl.ParcelableNetworkListener.Stub;

/* compiled from: Taobao */
public class ParcelableNetworkListenerWrapper extends Stub {
    private static final String TAG = "anet.ParcelableNetworkListenerWrapper";
    private Handler handler;
    private NetworkListener listener;
    private Object mContext;
    private byte state = (byte) 0;

    public NetworkListener getListener() {
        return this.listener;
    }

    public ParcelableNetworkListenerWrapper(NetworkListener networkListener, Handler handler, Object obj) {
        this.listener = networkListener;
        if (networkListener != null) {
            if (FinishListener.class.isAssignableFrom(networkListener.getClass())) {
                this.state = (byte) (this.state | 1);
            }
            if (ProgressListener.class.isAssignableFrom(networkListener.getClass())) {
                this.state = (byte) (this.state | 2);
            }
            if (ResponseCodeListener.class.isAssignableFrom(networkListener.getClass())) {
                this.state = (byte) (this.state | 4);
            }
            if (InputStreamListener.class.isAssignableFrom(networkListener.getClass())) {
                this.state = (byte) (this.state | 8);
            }
        }
        this.handler = handler;
        this.mContext = obj;
    }

    private void dispatch(final byte b, final Object obj) {
        if (this.handler == null) {
            dispatchCallback(b, obj);
        } else {
            this.handler.post(new Runnable() {
                public void run() {
                    ParcelableNetworkListenerWrapper.this.dispatchCallback(b, obj);
                }
            });
        }
    }

    private void dispatchCallback(byte b, Object obj) {
        if (b == (byte) 4) {
            try {
                ParcelableHeader parcelableHeader = (ParcelableHeader) obj;
                ((ResponseCodeListener) this.listener).onResponseCode(parcelableHeader.getResponseCode(), parcelableHeader.getHeader(), this.mContext);
                if (ALog.isPrintLog(1)) {
                    ALog.d(TAG, "[onResponseCode]" + parcelableHeader, null, new Object[0]);
                }
            } catch (Exception e) {
                ALog.e(TAG, "dispatchCallback error", null, new Object[0]);
            }
        } else if (b == (byte) 2) {
            DefaultProgressEvent defaultProgressEvent = (DefaultProgressEvent) obj;
            if (defaultProgressEvent != null) {
                defaultProgressEvent.setContext(this.mContext);
            }
            ((ProgressListener) this.listener).onDataReceived(defaultProgressEvent, this.mContext);
            if (ALog.isPrintLog(1)) {
                ALog.d(TAG, "[onDataReceived]" + defaultProgressEvent, null, new Object[0]);
            }
        } else if (b == (byte) 1) {
            DefaultFinishEvent defaultFinishEvent = (DefaultFinishEvent) obj;
            if (defaultFinishEvent != null) {
                defaultFinishEvent.setContext(this.mContext);
            }
            ((FinishListener) this.listener).onFinished(defaultFinishEvent, this.mContext);
            if (ALog.isPrintLog(1)) {
                ALog.d(TAG, "[onFinished]" + defaultFinishEvent, null, new Object[0]);
            }
        } else if (b == (byte) 8) {
            ((InputStreamListener) this.listener).onInputStreamGet((ParcelableInputStream) obj, this.mContext);
            if (ALog.isPrintLog(1)) {
                ALog.d(TAG, "[onInputStreamReceived]", null, new Object[0]);
            }
        }
    }

    public void onDataReceived(DefaultProgressEvent defaultProgressEvent) throws RemoteException {
        if ((this.state & 2) != 0) {
            dispatch((byte) 2, defaultProgressEvent);
        }
    }

    public void onFinished(DefaultFinishEvent defaultFinishEvent) throws RemoteException {
        if ((this.state & 1) != 0) {
            dispatch((byte) 1, defaultFinishEvent);
        }
        this.listener = null;
        this.mContext = null;
        this.handler = null;
    }

    public boolean onResponseCode(int i, ParcelableHeader parcelableHeader) throws RemoteException {
        if ((this.state & 4) != 0) {
            dispatch((byte) 4, parcelableHeader);
        }
        return false;
    }

    public void onInputStreamGet(ParcelableInputStream parcelableInputStream) throws RemoteException {
        if ((this.state & 8) != 0) {
            dispatch((byte) 8, parcelableInputStream);
        }
    }

    public byte getListenerState() throws RemoteException {
        return this.state;
    }
}
