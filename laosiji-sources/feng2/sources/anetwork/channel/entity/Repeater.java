package anetwork.channel.entity;

import android.os.RemoteException;
import anet.channel.bytes.ByteArray;
import anet.channel.util.ALog;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.aidl.DefaultProgressEvent;
import anetwork.channel.aidl.ParcelableHeader;
import anetwork.channel.aidl.ParcelableNetworkListener;
import anetwork.channel.aidl.adapter.ParcelableInputStreamImpl;
import anetwork.channel.interceptor.Callback;
import java.util.List;
import java.util.Map;

/* compiled from: Taobao */
public class Repeater implements Callback {
    private static final String TAG = "anet.Repeater";
    private boolean bInputStreamListener = false;
    private RequestConfig config = null;
    private ParcelableInputStreamImpl inputStream = null;
    private ParcelableNetworkListener mListenerWrapper;
    private String seqNo;
    private long startTime;

    public Repeater(ParcelableNetworkListener parcelableNetworkListener, RequestConfig requestConfig) {
        this.mListenerWrapper = parcelableNetworkListener;
        this.config = requestConfig;
        if (parcelableNetworkListener != null) {
            try {
                if ((parcelableNetworkListener.getListenerState() & 8) != 0) {
                    this.bInputStreamListener = true;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onResponseCode(final int i, final Map<String, List<String>> map) {
        if (ALog.isPrintLog(2)) {
            ALog.i(TAG, "[onResponseCode]", this.seqNo, new Object[0]);
        }
        if (this.mListenerWrapper != null) {
            final ParcelableNetworkListener parcelableNetworkListener = this.mListenerWrapper;
            dispatchCallBack(new Runnable() {
                public void run() {
                    try {
                        parcelableNetworkListener.onResponseCode(i, new ParcelableHeader(i, map));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void onDataReceiveSize(int i, int i2, ByteArray byteArray) {
        if (this.mListenerWrapper != null) {
            final ParcelableNetworkListener parcelableNetworkListener = this.mListenerWrapper;
            final ByteArray byteArray2 = byteArray;
            final int i3 = i2;
            final int i4 = i;
            dispatchCallBack(new Runnable() {
                public void run() {
                    if (Repeater.this.bInputStreamListener) {
                        try {
                            if (Repeater.this.inputStream == null) {
                                Repeater.this.inputStream = new ParcelableInputStreamImpl();
                                Repeater.this.inputStream.init(Repeater.this.config, i3);
                                Repeater.this.inputStream.write(byteArray2);
                                parcelableNetworkListener.onInputStreamGet(Repeater.this.inputStream);
                                return;
                            }
                            Repeater.this.inputStream.write(byteArray2);
                            return;
                        } catch (Exception e) {
                            if (Repeater.this.inputStream != null) {
                                try {
                                    Repeater.this.inputStream.close();
                                    return;
                                } catch (RemoteException e2) {
                                    return;
                                }
                            }
                            return;
                        }
                    }
                    DefaultProgressEvent defaultProgressEvent = new DefaultProgressEvent();
                    defaultProgressEvent.setSize(byteArray2.getDataLength());
                    defaultProgressEvent.setTotal(i3);
                    defaultProgressEvent.setDesc("");
                    defaultProgressEvent.setIndex(i4);
                    defaultProgressEvent.setBytedata(byteArray2.getBuffer());
                    try {
                        parcelableNetworkListener.onDataReceived(defaultProgressEvent);
                    } catch (RemoteException e3) {
                        e3.printStackTrace();
                    }
                }
            });
        }
    }

    public void onFinish(final DefaultFinishEvent defaultFinishEvent) {
        if (ALog.isPrintLog(2)) {
            ALog.i(TAG, "[onFinish] ", this.seqNo, new Object[0]);
        }
        if (this.mListenerWrapper != null) {
            final ParcelableNetworkListener parcelableNetworkListener = this.mListenerWrapper;
            Runnable anonymousClass3 = new Runnable() {
                public void run() {
                    if (ALog.isPrintLog(1)) {
                        ALog.d(Repeater.TAG, "[onFinish]on Finish waitTime:" + (System.currentTimeMillis() - Repeater.this.startTime), Repeater.this.seqNo, new Object[0]);
                    }
                    Repeater.this.startTime = System.currentTimeMillis();
                    if (defaultFinishEvent != null) {
                        defaultFinishEvent.setContext(null);
                    }
                    try {
                        parcelableNetworkListener.onFinished(defaultFinishEvent);
                        if (Repeater.this.inputStream != null) {
                            Repeater.this.inputStream.writeEnd();
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                    if (ALog.isPrintLog(1)) {
                        ALog.d(Repeater.TAG, "[onFinish]on Finish process time:" + (System.currentTimeMillis() - Repeater.this.startTime), Repeater.this.seqNo, new Object[0]);
                    }
                }
            };
            this.startTime = System.currentTimeMillis();
            dispatchCallBack(anonymousClass3);
        }
        this.mListenerWrapper = null;
    }

    private void dispatchCallBack(Runnable runnable) {
        RepeatProcessor.submitTask(this.seqNo != null ? this.seqNo.hashCode() : hashCode(), runnable);
    }

    public void setSeqNo(String str) {
        this.seqNo = str;
    }
}
