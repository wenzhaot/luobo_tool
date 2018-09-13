package anet.channel;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import anet.channel.a.c;
import anet.channel.entity.ConnType;
import anet.channel.entity.EventCb;
import anet.channel.entity.EventType;
import anet.channel.entity.a;
import anet.channel.entity.d;
import anet.channel.request.Cancelable;
import anet.channel.request.Request;
import anet.channel.statist.SessionStatistic;
import anet.channel.strategy.IConnStrategy;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;

/* compiled from: Taobao */
public abstract class Session implements Comparable<Session> {
    private static final String TAG = "awcn.Session";
    protected boolean autoReCreate = false;
    protected IConnStrategy mConnStrategy;
    protected int mConnTimeout;
    protected ConnType mConnType;
    protected Context mContext;
    Map<EventCb, Integer> mEventCallBacks = new LinkedHashMap();
    protected String mHost;
    protected String mIp;
    private boolean mIsConnTimeOut = false;
    protected int mPort;
    protected String mProxyIp;
    protected int mProxyPort;
    protected String mRealHost;
    protected Runnable mRecvTimeOutRunnable;
    protected int mReqTimeout;
    public final String mSeq;
    public final SessionStatistic mSessionStat;
    protected Status mStatus = Status.DISCONNECTED;
    private Future<?> timeoutTaskFuture;
    protected boolean tryNextWhenFail = true;

    /* compiled from: Taobao */
    public enum Status {
        CONNECTED,
        CONNECTING,
        CONNETFAIL,
        AUTHING,
        AUTH_SUCC,
        AUTH_FAIL,
        DISCONNECTED,
        DISCONNECTING
    }

    public abstract void close();

    protected abstract Runnable getRecvTimeOutRunnable();

    public abstract boolean isAvailable();

    public abstract Cancelable request(Request request, RequestCb requestCb);

    public int compareTo(Session session) {
        return ConnType.compare(this.mConnType, session.mConnType);
    }

    public Session(Context context, a aVar, ConnType connType) {
        this.mContext = context;
        this.mIp = aVar.a();
        this.mPort = aVar.b();
        this.mConnType = connType;
        this.mHost = aVar.f();
        this.mRealHost = this.mHost.substring(this.mHost.indexOf(HttpConstant.SCHEME_SPLIT) + 3);
        this.mReqTimeout = aVar.e();
        this.mConnTimeout = aVar.d();
        this.mConnStrategy = aVar.a;
        this.mSessionStat = new SessionStatistic(aVar);
        this.mSeq = aVar.h();
    }

    public void sendCustomFrame(int i, byte[] bArr, int i2) {
    }

    public void checkAvailable() {
        ping(true);
    }

    public static void configTnetALog(Context context, String str, int i, int i2) {
        SpdyAgent instance = SpdyAgent.getInstance(context, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
        if (instance == null || !SpdyAgent.checkLoadSucc()) {
            ALog.e("agent null or configTnetALog load so fail!!!", null, "loadso", Boolean.valueOf(SpdyAgent.checkLoadSucc()));
            return;
        }
        instance.configLogFile(str, i, i2);
    }

    public void connect() {
    }

    public void close(boolean z) {
        this.autoReCreate = z;
        close();
    }

    public void ping(boolean z) {
    }

    public void registerEventcb(int i, EventCb eventCb) {
        if (this.mEventCallBacks != null) {
            this.mEventCallBacks.put(eventCb, Integer.valueOf(i));
        }
    }

    protected void unReceiveEventCb(EventCb eventCb) {
        if (this.mEventCallBacks != null) {
            this.mEventCallBacks.remove(eventCb);
        }
    }

    public String getIp() {
        return this.mIp;
    }

    public int getPort() {
        return this.mPort;
    }

    public ConnType getConnType() {
        return this.mConnType;
    }

    public String getHost() {
        return this.mHost;
    }

    public String getRealHost() {
        return this.mRealHost;
    }

    public IConnStrategy getConnStrategy() {
        return this.mConnStrategy;
    }

    protected void handleCallbacks(EventType eventType, d dVar) {
        c.a(new b(this, eventType, dVar));
    }

    protected void onDisconnect() {
    }

    protected synchronized void notifyStatus(Status status, d dVar) {
        ALog.e(TAG, "notifyStatus", this.mSeq, NotificationCompat.CATEGORY_STATUS, status.name());
        if (!status.equals(this.mStatus)) {
            this.mStatus = status;
            switch (this.mStatus) {
                case CONNECTED:
                    handleCallbacks(EventType.CONNECTED, dVar);
                    break;
                case CONNETFAIL:
                    handleCallbacks(EventType.CONNECT_FAIL, dVar);
                    break;
                case DISCONNECTED:
                    onDisconnect();
                    if (!this.mIsConnTimeOut) {
                        handleCallbacks(EventType.DISCONNECTED, dVar);
                        break;
                    }
                    break;
                case AUTH_SUCC:
                    handleCallbacks(EventType.AUTH_SUCC, dVar);
                    break;
                case AUTH_FAIL:
                    handleCallbacks(EventType.AUTH_FAIL, dVar);
                    break;
            }
        }
        ALog.i(TAG, "ignore notifyStatus", this.mSeq, new Object[0]);
    }

    protected void setPingTimeout() {
        if (this.mRecvTimeOutRunnable == null) {
            this.mRecvTimeOutRunnable = getRecvTimeOutRunnable();
        }
        cancelTimeout();
        if (this.mRecvTimeOutRunnable != null) {
            this.timeoutTaskFuture = c.a(this.mRecvTimeOutRunnable, Constants.RECV_TIMEOUT, TimeUnit.MILLISECONDS);
        }
    }

    protected void cancelTimeout() {
        if (this.mRecvTimeOutRunnable != null && this.timeoutTaskFuture != null) {
            this.timeoutTaskFuture.cancel(true);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Session@[");
        stringBuilder.append(this.mSeq).append('|').append(this.mConnType).append(']');
        return stringBuilder.toString();
    }
}
