package com.taobao.accs.net;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.Config;
import anet.channel.DataFrameCb;
import anet.channel.IAuth;
import anet.channel.IAuth.AuthCallback;
import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.SessionInfo;
import anet.channel.request.Request.Builder;
import anet.channel.session.TnetSpdySession;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message;
import com.taobao.accs.ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.ut.statistics.c;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.i;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: Taobao */
public class k extends b implements DataFrameCb {
    private boolean n = true;
    private ScheduledFuture<?> o = null;
    private Runnable p = new o(this);
    private Set<String> q;

    /* compiled from: Taobao */
    public static class a implements IAuth {
        private String a;
        private int b;
        private String c;
        private b d;

        public a(b bVar, String str) {
            this.c = bVar.d();
            this.a = bVar.c("https://" + str + "/accs/");
            this.b = bVar.c;
            this.d = bVar;
        }

        public void auth(Session session, AuthCallback authCallback) {
            ALog.i(this.c, "auth begin", "seq", session.mSeq);
            ALog.e(this.c, this.b + " auth URL:" + this.a, new Object[0]);
            session.request(new Builder().setUrl(this.a).build(), new p(this, session, authCallback));
        }
    }

    public k(Context context, int i, String str) {
        super(context, i, str);
        if (!i.a(true)) {
            Object tnetLogFilePath = UtilityImpl.getTnetLogFilePath(this.d, "inapp");
            ALog.d(d(), "config tnet log path:" + tnetLogFilePath, new Object[0]);
            if (!TextUtils.isEmpty(tnetLogFilePath)) {
                Session.configTnetALog(context, tnetLogFilePath, 5242880, 5);
            }
        }
        this.o = ThreadPoolExecutorFactory.getScheduledExecutor().schedule(this.p, 120000, TimeUnit.MILLISECONDS);
    }

    public synchronized void a() {
        this.n = true;
        a(this.d);
        ALog.d(d(), this.c + " start", new Object[0]);
    }

    protected void a(Message message, boolean z) {
        if (!this.n || message == null) {
            ALog.e(d(), "not running or msg null! " + this.n, new Object[0]);
            return;
        }
        try {
            if (ThreadPoolExecutorFactory.getSendScheduledExecutor().getQueue().size() > 1000) {
                throw new RejectedExecutionException("accs");
            }
            ScheduledFuture schedule = ThreadPoolExecutorFactory.getSendScheduledExecutor().schedule(new l(this, message), message.O, TimeUnit.MILLISECONDS);
            if (message.a() == 1 && message.M != null) {
                if (message.c() && a(message.M)) {
                    this.e.b(message);
                }
                this.e.a.put(message.M, schedule);
            }
            NetPerformanceMonitor e = message.e();
            if (e != null) {
                e.setDeviceId(UtilityImpl.getDeviceId(this.d));
                e.setConnType(this.c);
                e.onEnterQueueData();
            }
        } catch (RejectedExecutionException e2) {
            this.e.a(message, (int) ErrorCode.MESSAGE_QUEUE_FULL);
            ALog.e(d(), "send queue full count:" + ThreadPoolExecutorFactory.getSendScheduledExecutor().getQueue().size(), new Object[0]);
        } catch (Throwable th) {
            this.e.a(message, -8);
            ALog.e(d(), "send error", th, new Object[0]);
        }
    }

    public void e() {
        ALog.e(d(), this.c + "shut down", new Object[0]);
        this.n = false;
    }

    public void b() {
        this.f = 0;
    }

    public void a(boolean z, boolean z2) {
    }

    public c c() {
        return null;
    }

    protected void a(String str, String str2) {
        try {
            Message a = this.e.a(str);
            if (a != null && a.f != null) {
                Session session = SessionCenter.getInstance(this.i.getAppKey()).get(a.f.toString(), 0);
                if (session != null) {
                    session.checkAvailable();
                }
            }
        } catch (Throwable e) {
            ALog.e(d(), "onTimeOut", e, new Object[0]);
        }
    }

    public void onDataReceive(TnetSpdySession tnetSpdySession, byte[] bArr, int i, int i2) {
        if (ALog.isPrintLog(Level.I)) {
            ALog.i(d(), "onDataReceive, type:" + i2 + " len:" + bArr.length, new Object[0]);
        }
        ThreadPoolExecutorFactory.getScheduledExecutor().execute(new m(this, i2, bArr, tnetSpdySession));
        if (ALog.isPrintLog(Level.E)) {
            ALog.e(d(), "onDataReceive, end:", new Object[0]);
        }
    }

    public void onException(int i, int i2, boolean z, String str) {
        ALog.e(d(), "errorId:" + i2 + "detail:" + str + " dataId:" + i + " needRetry:" + z, new Object[0]);
        ThreadPoolExecutorFactory.getScheduledExecutor().execute(new n(this, i, z, i2));
    }

    public boolean a(String str) {
        if (str == null) {
            return false;
        }
        boolean cancel;
        ScheduledFuture scheduledFuture = (ScheduledFuture) this.e.a.get(str);
        if (scheduledFuture != null) {
            cancel = scheduledFuture.cancel(false);
        } else {
            cancel = false;
        }
        if (!cancel) {
            return cancel;
        }
        ALog.e(d(), CommonNetImpl.CANCEL, "customDataId", str);
        return cancel;
    }

    protected String d() {
        return "InAppConn_" + this.m;
    }

    protected void a(Context context) {
        boolean z = true;
        try {
            if (!this.g) {
                super.a(context);
                String inappHost = this.i.getInappHost();
                if (!(h() && this.i.isKeepalive())) {
                    ALog.i(d(), "close keepalive", new Object[0]);
                    z = false;
                }
                a(SessionCenter.getInstance(Config.getConfigByTag(this.i.getAppKey())), inappHost, z);
                this.g = true;
                ALog.e(d(), "init awcn success!", new Object[0]);
            }
        } catch (Throwable th) {
            ALog.e(d(), "initAwcn", th, new Object[0]);
        }
    }

    public void a(SessionCenter sessionCenter, String str, boolean z) {
        if (this.q == null) {
            this.q = new HashSet(2);
        }
        if (!this.q.contains(str)) {
            sessionCenter.registerSessionInfo(SessionInfo.create(str, z, true, new a(this, str), null, this));
            sessionCenter.registerPublicKey(str, this.i.getInappPubKey());
            this.q.add(str);
            ALog.i(d(), "registerSessionInfo", "host", str);
        }
    }

    public void a(AccsClientConfig accsClientConfig) {
        if (accsClientConfig == null) {
            ALog.i(d(), "updateConfig null", new Object[0]);
            return;
        }
        try {
            boolean z;
            ALog.i("InAppConn_", "updateConfig", "old", this.i.toString(), "new", accsClientConfig.toString());
            this.i = accsClientConfig;
            this.b = this.i.getAppKey();
            this.m = this.i.getTag();
            SessionCenter instance = SessionCenter.getInstance(this.i.getAppKey());
            instance.unregisterSessionInfo(this.i.getInappHost());
            if (h() && this.i.isKeepalive()) {
                z = true;
            } else {
                ALog.i(d(), "close keepalive", new Object[0]);
                z = false;
            }
            a(instance, this.i.getInappHost(), z);
        } catch (Throwable th) {
            ALog.i("InAppConn_", "updateConfig", "excetion", th.toString());
        }
    }
}
