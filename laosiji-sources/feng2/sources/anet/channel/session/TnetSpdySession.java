package anet.channel.session;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.Config;
import anet.channel.DataFrameCb;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.IAuth;
import anet.channel.RequestCb;
import anet.channel.Session;
import anet.channel.Session.Status;
import anet.channel.SessionInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.bytes.ByteArray;
import anet.channel.entity.EventType;
import anet.channel.entity.b;
import anet.channel.entity.c;
import anet.channel.entity.d;
import anet.channel.heartbeat.HeartbeatManager;
import anet.channel.heartbeat.IHeartbeat;
import anet.channel.request.Cancelable;
import anet.channel.request.Request;
import anet.channel.request.TnetCancelable;
import anet.channel.security.ISecurity;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.statist.SessionStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.ConnEvent;
import anet.channel.strategy.StrategyCenter;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpConstant;
import anet.channel.util.Utils;
import anetwork.channel.util.RequestConstant;
import com.baidu.mapapi.UIMsg.d_ResultType;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.android.spdy.RequestPriority;
import org.android.spdy.SessionCb;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdyByteArray;
import org.android.spdy.SpdyDataProvider;
import org.android.spdy.SpdyErrorException;
import org.android.spdy.SpdyRequest;
import org.android.spdy.SpdySession;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;
import org.android.spdy.SuperviseConnectInfo;
import org.android.spdy.SuperviseData;

/* compiled from: Taobao */
public class TnetSpdySession extends Session implements SessionCb {
    private static final int MAX_ERROR_COUNT = 5;
    private static final long MAX_ERROR_INTERVAL = 60000;
    private static final String SSL_TIKET_KEY2 = "accs_ssl_key2_";
    private static final String TAG = "awcn.TnetSpdySession";
    private ConcurrentHashMap<String, List<Long>> accsHostErrors = new ConcurrentHashMap();
    protected IAuth auth = null;
    protected DataFrameCb dataFrameCb = null;
    protected IHeartbeat heartbeat = null;
    protected ISecurity iSecurity = null;
    protected SpdyAgent mAgent;
    protected String mAppkey = null;
    protected long mConnectedTime = 0;
    protected volatile boolean mHasUnrevPing = false;
    protected long mLastPingTime;
    protected SpdySession mSession;
    private int requestTimeoutCount = 0;
    protected int tnetPublicKey = -1;

    /* compiled from: Taobao */
    private class a extends a {
        private Request b;
        private RequestCb c;
        private long d = 0;
        private long e = 0;
        private long f = 0;

        public a(Request request, RequestCb requestCb) {
            this.b = request;
            this.c = requestCb;
            this.d = System.currentTimeMillis();
        }

        public void spdyDataChunkRecvCB(SpdySession spdySession, boolean z, long j, SpdyByteArray spdyByteArray, Object obj) {
            ALog.d(TnetSpdySession.TAG, "spdyDataChunkRecvCB", this.b.getSeq(), "len", Integer.valueOf(spdyByteArray.getDataLength()), "fin", Boolean.valueOf(z));
            if (this.b.rs.firstDataTime == 0) {
                this.b.rs.firstDataTime = System.currentTimeMillis();
            }
            if (this.c != null) {
                ByteArray a = a.a.a(spdyByteArray.getByteArray(), spdyByteArray.getDataLength());
                spdyByteArray.recycle();
                this.c.onDataReceive(a, z);
            }
            TnetSpdySession.this.access$800(EventType.DATA_RECEIVE, null);
        }

        public void spdyStreamCloseCallback(SpdySession spdySession, long j, int i, Object obj, SuperviseData superviseData) {
            ALog.d(TnetSpdySession.TAG, "spdyStreamCloseCallback", this.b.getSeq(), "streamId", Long.valueOf(j));
            this.e = System.currentTimeMillis();
            a(superviseData);
            String str = HttpConstant.SUCCESS;
            if (i != 0) {
                if (i != -2005) {
                    str = ErrorConstant.formatMsg(ErrorConstant.ERROR_TNET_EXCEPTION, "statusCode=" + i);
                    AppMonitor.getInstance().commitStat(new ExceptionStatistic(ErrorConstant.ERROR_TNET_EXCEPTION, str, this.b.rs, null));
                }
                ALog.e(TnetSpdySession.TAG, "spdyStreamCloseCallback error", this.b.getSeq(), "status code", Integer.valueOf(i));
            }
            if (this.c != null) {
                this.c.onFinish(i, str, this.b.rs);
            }
            if (i == -2004 && TnetSpdySession.access$504(TnetSpdySession.this) >= 2) {
                ConnEvent connEvent = new ConnEvent();
                connEvent.isSuccess = false;
                StrategyCenter.getInstance().notifyConnEvent(TnetSpdySession.this.mRealHost, TnetSpdySession.this.mConnStrategy, connEvent);
                TnetSpdySession.this.close(true);
            }
        }

        private void a(SuperviseData superviseData) {
            long j = 0;
            try {
                if (this.e > 0 && this.d > 0) {
                    this.b.rs.serverRT = this.f;
                    RequestStatistic requestStatistic = this.b.rs;
                    if (this.b.rs.firstDataTime != 0) {
                        j = this.e - this.b.rs.firstDataTime;
                    }
                    requestStatistic.recDataTime = j;
                    this.b.rs.oneWayTime = this.e - this.b.rs.start;
                    this.b.rs.waitingTime = this.b.rs.oneWayTime;
                    if (superviseData != null) {
                        this.b.rs.firstDataTime = superviseData.responseStart - superviseData.sendStart;
                        this.b.rs.recDataTime = superviseData.responseEnd - superviseData.responseStart;
                        this.b.rs.sendBeforeTime = superviseData.sendStart - this.d;
                        this.b.rs.sendDataTime = superviseData.sendEnd - superviseData.sendStart;
                        this.b.rs.sendDataSize = (long) (superviseData.bodySize + superviseData.compressSize);
                        this.b.rs.recDataSize = (long) (superviseData.recvBodySize + superviseData.recvCompressSize);
                        SessionStatistic sessionStatistic = TnetSpdySession.this.mSessionStat;
                        sessionStatistic.recvSizeCount += (long) (superviseData.recvBodySize + superviseData.recvCompressSize);
                        sessionStatistic = TnetSpdySession.this.mSessionStat;
                        sessionStatistic.sendSizeCount += (long) (superviseData.bodySize + superviseData.compressSize);
                    }
                }
            } catch (Exception e) {
            }
        }

        public void spdyOnStreamResponse(SpdySession spdySession, long j, Map<String, List<String>> map, Object obj) {
            List list;
            int i;
            try {
                int i2;
                list = (List) map.get(HttpConstant.STATUS);
                if (list == null || list.isEmpty()) {
                    i2 = 0;
                } else {
                    i2 = Integer.parseInt((String) list.get(0));
                }
                i = i2;
            } catch (NumberFormatException e) {
                i = 0;
            }
            if (i > 0) {
                this.b.rs.ret = true;
                TnetSpdySession.this.requestTimeoutCount = 0;
            }
            ALog.i(TnetSpdySession.TAG, "", this.b.getSeq(), "httpStatusCode", Integer.valueOf(i));
            ALog.i(TnetSpdySession.TAG, "", this.b.getSeq(), "response headers", map);
            if (this.c != null) {
                this.c.onResponseCode(i, anet.channel.util.a.a((Map) map));
            }
            TnetSpdySession.this.access$800(EventType.HEADER_RECEIVE, null);
            try {
                list = (List) map.get(HttpConstant.SERVER_RT);
                if (!(list == null || list.isEmpty())) {
                    this.f = Long.parseLong((String) list.get(0));
                }
            } catch (NumberFormatException e2) {
            }
            TnetSpdySession.this.handleResponseCode(this.b, i);
        }
    }

    static /* synthetic */ int access$504(TnetSpdySession tnetSpdySession) {
        int i = tnetSpdySession.requestTimeoutCount + 1;
        tnetSpdySession.requestTimeoutCount = i;
        return i;
    }

    public TnetSpdySession(Context context, anet.channel.entity.a aVar, Config config, SessionInfo sessionInfo, int i) {
        super(context, aVar, aVar.c());
        this.mAppkey = config.getAppkey();
        this.iSecurity = config.getSecurity();
        initSpdyAgent();
        if (i >= 0) {
            this.tnetPublicKey = i;
        } else {
            this.tnetPublicKey = this.mConnType.getTnetPublicKey(this.iSecurity.isSecOff());
        }
        if (sessionInfo != null) {
            this.dataFrameCb = sessionInfo.dataFrameCb;
            this.auth = sessionInfo.auth;
            if (sessionInfo.isKeepAlive) {
                this.mSessionStat.isKL = 1;
                this.autoReCreate = true;
                this.heartbeat = sessionInfo.heartbeat;
                if (this.heartbeat == null) {
                    this.heartbeat = HeartbeatManager.getHeartbeatFactory().createHeartbeat(this);
                }
            }
        }
    }

    public Cancelable request(Request request, RequestCb requestCb) {
        RequestStatistic requestStatistic;
        SpdyErrorException e;
        Exception e2;
        TnetCancelable tnetCancelable = TnetCancelable.NULL;
        if (request != null) {
            requestStatistic = request.rs;
        } else {
            requestStatistic = new RequestStatistic(this.mRealHost, null);
        }
        requestStatistic.setConnType(this.mConnType);
        requestStatistic.setIPAndPort(this.mIp, this.mPort);
        if (requestStatistic.start == 0) {
            requestStatistic.start = System.currentTimeMillis();
        }
        if (request == null || requestCb == null) {
            if (requestCb != null) {
                requestCb.onFinish(-102, ErrorConstant.getErrMsg(-102), requestStatistic);
            }
            return tnetCancelable;
        }
        Cancelable tnetCancelable2;
        try {
            if (this.mSession == null || !(this.mStatus == Status.CONNECTED || this.mStatus == Status.AUTH_SUCC)) {
                requestCb.onFinish(ErrorConstant.ERROR_SESSION_INVALID, "Session不可用", request.rs);
                return tnetCancelable;
            }
            SpdyRequest spdyRequest;
            request.setUrlScheme(this.mConnType.isSSL());
            URL url = request.getUrl();
            if (ALog.isPrintLog(2)) {
                ALog.i(TAG, "", request.getSeq(), "request URL", url.toString());
                ALog.i(TAG, "", request.getSeq(), "request Method", request.getMethod());
                ALog.i(TAG, "", request.getSeq(), "request headers", request.getHeaders());
            }
            if (TextUtils.isEmpty(this.mProxyIp) || this.mProxyPort <= 0) {
                spdyRequest = new SpdyRequest(url, request.getMethod(), RequestPriority.DEFAULT_PRIORITY, request.getReadTimeout(), request.getConnectTimeout());
            } else {
                spdyRequest = new SpdyRequest(url, url.getHost(), url.getPort(), this.mProxyIp, this.mProxyPort, request.getMethod(), RequestPriority.DEFAULT_PRIORITY, request.getReadTimeout(), request.getConnectTimeout(), 0);
            }
            Map headers = request.getHeaders();
            if (headers.containsKey(HttpConstant.HOST)) {
                headers = new HashMap(request.getHeaders());
                headers.put(":host", headers.remove(HttpConstant.HOST));
                spdyRequest.addHeaders(headers);
            } else {
                spdyRequest.addHeaders(headers);
                spdyRequest.addHeader(":host", request.getHost());
            }
            int submitRequest = this.mSession.submitRequest(spdyRequest, new SpdyDataProvider(request.getBodyBytes()), this, new a(request, requestCb));
            if (ALog.isPrintLog(1)) {
                ALog.d(TAG, "", request.getSeq(), "streamId", Integer.valueOf(submitRequest));
            }
            tnetCancelable2 = new TnetCancelable(this.mSession, submitRequest, request.getSeq());
            try {
                SessionStatistic sessionStatistic = this.mSessionStat;
                sessionStatistic.requestCount++;
                sessionStatistic = this.mSessionStat;
                sessionStatistic.stdRCount++;
                this.mLastPingTime = System.currentTimeMillis();
                if (this.heartbeat == null) {
                    return tnetCancelable2;
                }
                this.heartbeat.reSchedule();
                return tnetCancelable2;
            } catch (SpdyErrorException e3) {
                e = e3;
                ALog.e(TAG, "Send request on closed session!!!", this.mSeq, new Object[0]);
                access$200(Status.DISCONNECTED, new c(EventType.DISCONNECTED, false, -1104, "Session is closed!"));
                requestCb.onFinish(ErrorConstant.ERROR_TNET_EXCEPTION, ErrorConstant.formatMsg(ErrorConstant.ERROR_TNET_EXCEPTION, e.toString()), requestStatistic);
                return tnetCancelable2;
            } catch (Exception e4) {
                e2 = e4;
                requestCb.onFinish(-101, ErrorConstant.formatMsg(-101, e2.toString()), requestStatistic);
                return tnetCancelable2;
            }
        } catch (SpdyErrorException e5) {
            e = e5;
            Object obj = tnetCancelable;
            if (e.SpdyErrorGetCode() == -1104 || e.SpdyErrorGetCode() == -1103) {
                ALog.e(TAG, "Send request on closed session!!!", this.mSeq, new Object[0]);
                access$200(Status.DISCONNECTED, new c(EventType.DISCONNECTED, false, -1104, "Session is closed!"));
            }
            requestCb.onFinish(ErrorConstant.ERROR_TNET_EXCEPTION, ErrorConstant.formatMsg(ErrorConstant.ERROR_TNET_EXCEPTION, e.toString()), requestStatistic);
            return tnetCancelable2;
        } catch (Exception e6) {
            e2 = e6;
            tnetCancelable2 = tnetCancelable;
            requestCb.onFinish(-101, ErrorConstant.formatMsg(-101, e2.toString()), requestStatistic);
            return tnetCancelable2;
        }
    }

    public void sendCustomFrame(int i, byte[] bArr, int i2) {
        try {
            if (this.dataFrameCb != null) {
                ALog.e(TAG, "sendCustomFrame", this.mSeq, "dataId", Integer.valueOf(i), "type", Integer.valueOf(i2));
                if (this.mStatus != Status.AUTH_SUCC || this.mSession == null) {
                    ALog.e(TAG, "sendCustomFrame", this.mSeq, "sendCustomFrame con invalid mStatus:" + this.mStatus);
                    onDataFrameException(i, ErrorConstant.ERROR_SESSION_INVALID, true, "session invalid");
                } else if (bArr == null || bArr.length <= 16384) {
                    this.mSession.sendCustomControlFrame(i, i2, 0, bArr == null ? 0 : bArr.length, bArr);
                    SessionStatistic sessionStatistic = this.mSessionStat;
                    sessionStatistic.requestCount++;
                    sessionStatistic = this.mSessionStat;
                    sessionStatistic.cfRCount++;
                    this.mLastPingTime = System.currentTimeMillis();
                    if (this.heartbeat != null) {
                        this.heartbeat.reSchedule();
                    }
                } else {
                    onDataFrameException(i, ErrorConstant.ERROR_DATA_TOO_LARGE, false, null);
                }
            }
        } catch (SpdyErrorException e) {
            ALog.e(TAG, "sendCustomFrame error", this.mSeq, e, new Object[0]);
            onDataFrameException(i, ErrorConstant.ERROR_TNET_EXCEPTION, true, "SpdyErrorException: " + e.toString());
        } catch (Throwable e2) {
            ALog.e(TAG, "sendCustomFrame error", this.mSeq, e2, new Object[0]);
            onDataFrameException(i, -101, true, e2.toString());
        }
    }

    private void onDataFrameException(int i, int i2, boolean z, String str) {
        if (this.dataFrameCb != null) {
            this.dataFrameCb.onException(i, i2, z, str);
        }
    }

    public void connect() {
        if (this.mStatus != Status.CONNECTING && this.mStatus != Status.CONNECTED && this.mStatus != Status.AUTH_SUCC) {
            try {
                if (this.mAgent != null) {
                    String valueOf = String.valueOf(System.currentTimeMillis());
                    ALog.e(TAG, "[connect]", this.mSeq, "host", this.mHost, "connect ", this.mIp + ":" + this.mPort, "sessionId", valueOf, "SpdyProtocol,", this.mConnType, "proxyIp,", this.mProxyIp, "proxyPort,", Integer.valueOf(this.mProxyPort));
                    org.android.spdy.SessionInfo sessionInfo = new org.android.spdy.SessionInfo(this.mIp, this.mPort, this.mHost + "_" + this.mAppkey, this.mProxyIp, this.mProxyPort, valueOf, this, this.mConnType.getTnetConType());
                    sessionInfo.setConnectionTimeoutMs((int) (((float) this.mConnTimeout) * Utils.getNetworkTimeFactor()));
                    sessionInfo.setPubKeySeqNum(this.tnetPublicKey);
                    this.mSession = this.mAgent.createSession(sessionInfo);
                    if (this.mSession.getRefCount() > 1) {
                        ALog.e(TAG, "get session ref count > 1!!!", this.mSeq, new Object[0]);
                        access$200(Status.CONNECTED, new b(EventType.CONNECTED));
                        auth();
                        return;
                    }
                    boolean z;
                    access$200(Status.CONNECTING, null);
                    this.mLastPingTime = System.currentTimeMillis();
                    SessionStatistic sessionStatistic = this.mSessionStat;
                    StringBuilder stringBuilder = new StringBuilder();
                    if (TextUtils.isEmpty(this.mProxyIp)) {
                        z = false;
                    } else {
                        z = true;
                    }
                    sessionStatistic.isProxy = stringBuilder.append(z).append("").toString();
                    this.mSessionStat.isTunnel = RequestConstant.FALSE;
                    this.mSessionStat.isBackground = GlobalAppRuntimeInfo.isAppBackground();
                    this.mConnectedTime = 0;
                }
            } catch (Throwable th) {
                access$200(Status.CONNETFAIL, null);
                ALog.e(TAG, "connect exception ", this.mSeq, th, new Object[0]);
            }
        }
    }

    public void close() {
        ALog.e(TAG, "force close!", this.mSeq, "session", this);
        access$200(Status.DISCONNECTING, null);
        if (this.heartbeat != null) {
            this.heartbeat.stop();
            this.heartbeat = null;
        }
        try {
            if (this.mSession != null) {
                this.mSession.closeSession();
            }
        } catch (Exception e) {
        }
    }

    protected void onDisconnect() {
        this.mHasUnrevPing = false;
    }

    protected Runnable getRecvTimeOutRunnable() {
        return new h(this);
    }

    public void ping(boolean z) {
        if (ALog.isPrintLog(1)) {
            ALog.d(TAG, "ping", this.mSeq, "host", this.mHost, "thread", Thread.currentThread().getName());
        }
        if (z) {
            try {
                if (this.mSession == null) {
                    if (this.mSessionStat != null) {
                        this.mSessionStat.closeReason = "session null";
                    }
                    ALog.e(TAG, this.mHost + " session null", this.mSeq, new Object[0]);
                    close();
                } else if (this.mStatus == Status.CONNECTED || this.mStatus == Status.AUTH_SUCC) {
                    access$800(EventType.PING_SEND, null);
                    this.mHasUnrevPing = true;
                    SessionStatistic sessionStatistic = this.mSessionStat;
                    sessionStatistic.ppkgCount++;
                    this.mSession.submitPing();
                    if (ALog.isPrintLog(1)) {
                        ALog.d(TAG, this.mHost + " submit ping ms:" + (System.currentTimeMillis() - this.mLastPingTime) + " force:" + z, this.mSeq, new Object[0]);
                    }
                    setPingTimeout();
                    this.mLastPingTime = System.currentTimeMillis();
                    if (this.heartbeat != null) {
                        this.heartbeat.reSchedule();
                    }
                }
            } catch (SpdyErrorException e) {
                if (e.SpdyErrorGetCode() == -1104 || e.SpdyErrorGetCode() == -1103) {
                    ALog.e(TAG, "Send request on closed session!!!", this.mSeq, new Object[0]);
                    access$200(Status.DISCONNECTED, new c(EventType.DISCONNECTED, false, -1104, "Session is closed!"));
                }
                ALog.e(TAG, "ping", this.mSeq, e, new Object[0]);
            } catch (Throwable e2) {
                ALog.e(TAG, "ping", this.mSeq, e2, new Object[0]);
            }
        }
    }

    protected void auth() {
        if (this.auth != null) {
            this.auth.auth(this, new i(this));
            return;
        }
        access$200(Status.AUTH_SUCC, null);
        this.mSessionStat.ret = 1;
        if (this.heartbeat != null) {
            this.heartbeat.start();
        }
    }

    public boolean isAvailable() {
        return this.mStatus == Status.AUTH_SUCC;
    }

    private void initSpdyAgent() {
        try {
            SpdyAgent.enableDebug = false;
            this.mAgent = SpdyAgent.getInstance(this.mContext, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
            if (this.iSecurity != null && !this.iSecurity.isSecOff()) {
                this.mAgent.setAccsSslCallback(new j(this));
            }
        } catch (Throwable e) {
            ALog.e(TAG, "Init failed.", null, e, new Object[0]);
        }
    }

    public void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo) {
        d bVar = new b(EventType.CONNECTED);
        bVar.a = (long) superviseConnectInfo.connectTime;
        bVar.b = (long) superviseConnectInfo.handshakeTime;
        this.mSessionStat.connectionTime = (long) superviseConnectInfo.connectTime;
        this.mSessionStat.sslTime = (long) superviseConnectInfo.handshakeTime;
        this.mSessionStat.sslCalTime = (long) superviseConnectInfo.doHandshakeTime;
        this.mSessionStat.netType = NetworkStatusHelper.b();
        this.mConnectedTime = System.currentTimeMillis();
        access$200(Status.CONNECTED, bVar);
        auth();
        ALog.e(TAG, "spdySessionConnectCB connect", this.mSeq, "connectTime", Integer.valueOf(superviseConnectInfo.connectTime), "sslTime:", Integer.valueOf(superviseConnectInfo.handshakeTime));
    }

    public void spdyPingRecvCallback(SpdySession spdySession, long j, Object obj) {
        if (ALog.isPrintLog(2)) {
            ALog.i(TAG, "ping receive", this.mSeq, HttpConstant.HOST, this.mHost, "id", Long.valueOf(j));
        }
        if (j >= 0) {
            this.mHasUnrevPing = false;
            access$800(EventType.PIND_RECEIVE, null);
        }
    }

    public void bioPingRecvCallback(SpdySession spdySession, int i) {
    }

    public void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i, int i2, int i3, int i4, byte[] bArr) {
        ALog.e(TAG, "[spdyCustomControlFrameRecvCallback]", this.mSeq, "len", Integer.valueOf(i4), "frameCb", this.dataFrameCb);
        if (ALog.isPrintLog(1)) {
            String str = "";
            if (i4 < 512) {
                for (byte b : bArr) {
                    str = str + Integer.toHexString(b & 255) + " ";
                }
                ALog.e(TAG, null, this.mSeq, "str", str);
            }
        }
        if (this.dataFrameCb != null) {
            this.dataFrameCb.onDataReceive(this, bArr, i, i2);
        } else {
            ALog.e(TAG, "AccsFrameCb is null", this.mSeq, new Object[0]);
            AppMonitor.getInstance().commitStat(new ExceptionStatistic(-105, null, "rt"));
        }
        SessionStatistic sessionStatistic = this.mSessionStat;
        sessionStatistic.inceptCount++;
    }

    public void spdySessionFailedError(SpdySession spdySession, int i, Object obj) {
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Throwable e) {
                ALog.e(TAG, "[spdySessionFailedError]session clean up failed!", null, e, new Object[0]);
            }
        }
        access$200(Status.CONNETFAIL, new d(EventType.CONNECT_FAIL, i, "tnet connect fail"));
        ALog.e(TAG, null, this.mSeq, " errorId:", Integer.valueOf(i));
        this.mSessionStat.errorCode = (long) i;
        this.mSessionStat.ret = 0;
        this.mSessionStat.netType = NetworkStatusHelper.b();
        AppMonitor.getInstance().commitStat(this.mSessionStat);
        AppMonitor.getInstance().commitAlarm(this.mSessionStat.getAlarmObject());
    }

    public void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i) {
        ALog.e(TAG, "spdySessionCloseCallback", this.mSeq, " errorCode:", Integer.valueOf(i));
        if (this.heartbeat != null) {
            this.heartbeat.stop();
            this.heartbeat = null;
        }
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Throwable e) {
                ALog.e(TAG, "[spdySessionCloseCallback]session clean up failed!", null, e, new Object[0]);
            }
        }
        access$200(Status.DISCONNECTED, new c(EventType.DISCONNECTED, false, i, TextUtils.isEmpty(this.mSessionStat.closeReason) ? "tnet close error:" + i : this.mSessionStat.closeReason + ":" + this.mSessionStat.errorCode));
        if (superviseConnectInfo != null) {
            this.mSessionStat.requestCount = (long) superviseConnectInfo.reused_counter;
            this.mSessionStat.liveTime = (long) superviseConnectInfo.keepalive_period_second;
        }
        if (this.mSessionStat.errorCode == 0) {
            this.mSessionStat.errorCode = (long) i;
        }
        this.mSessionStat.lastPingInterval = (int) (System.currentTimeMillis() - this.mLastPingTime);
        AppMonitor.getInstance().commitStat(this.mSessionStat);
        AppMonitor.getInstance().commitAlarm(this.mSessionStat.getAlarmObject());
    }

    public void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i, int i2) {
        ALog.e(TAG, "spdyCustomControlFrameFailCallback", this.mSeq, "dataId", Integer.valueOf(i));
        onDataFrameException(i, i2, true, "tnet error");
    }

    public byte[] getSSLMeta(SpdySession spdySession) {
        byte[] bArr = null;
        Object domain = spdySession.getDomain();
        if (TextUtils.isEmpty(domain)) {
            ALog.i(TAG, "get sslticket host is null", bArr, new Object[0]);
            return bArr;
        }
        try {
            return this.iSecurity.getBytes(this.mContext, SSL_TIKET_KEY2 + domain);
        } catch (Throwable th) {
            ALog.e(TAG, "getSSLMeta", bArr, th, new Object[0]);
            return bArr;
        }
    }

    public int putSSLMeta(SpdySession spdySession, byte[] bArr) {
        int i = 0;
        Object domain = spdySession.getDomain();
        if (TextUtils.isEmpty(domain)) {
            return -1;
        }
        try {
            if (!this.iSecurity.saveBytes(this.mContext, SSL_TIKET_KEY2 + domain, bArr)) {
                i = -1;
            }
        } catch (Throwable th) {
            ALog.e(TAG, "putSSLMeta", null, th, new Object[0]);
            i = -1;
        }
        return i;
    }

    private void handleResponseCode(Request request, int i) {
        if (request.getHeaders().containsKey(HttpConstant.X_PV)) {
            String host = request.getHost();
            if (ALog.isPrintLog(1)) {
                ALog.d(TAG, "FailOverHandler hook onResponseCode", null, "host", host, "code", Integer.valueOf(i));
            }
            if (i >= d_ResultType.SHORT_URL && i < 600) {
                List linkedList;
                List list = (List) this.accsHostErrors.get(host);
                if (list == null) {
                    linkedList = new LinkedList();
                    list = (List) this.accsHostErrors.putIfAbsent(host, linkedList);
                    if (list != null) {
                        linkedList = list;
                    }
                } else {
                    linkedList = list;
                }
                synchronized (linkedList) {
                    if (linkedList.size() < 5) {
                        linkedList.add(Long.valueOf(System.currentTimeMillis()));
                    } else {
                        long longValue = ((Long) linkedList.remove(0)).longValue();
                        long currentTimeMillis = System.currentTimeMillis();
                        if (currentTimeMillis - longValue <= MAX_ERROR_INTERVAL) {
                            StrategyCenter.getInstance().forceRefreshStrategy(host);
                            linkedList.clear();
                        } else {
                            linkedList.add(Long.valueOf(currentTimeMillis));
                        }
                    }
                }
            }
        }
    }
}
