package anet.channel;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import anet.channel.a.c;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.entity.ConnType;
import anet.channel.entity.ConnType.TypeLevel;
import anet.channel.entity.EventType;
import anet.channel.session.TnetSpdySession;
import anet.channel.session.d;
import anet.channel.statist.AlarmObject;
import anet.channel.statist.SessionConnStat;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import anet.channel.util.g;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: Taobao */
class SessionRequest {
    String a;
    SessionCenter b;
    f c;
    volatile boolean d = false;
    volatile Session e;
    volatile boolean f = false;
    SessionConnStat g = null;
    private String h;
    private SessionInfo i;
    private volatile Future j;
    private Object k = new Object();

    /* compiled from: Taobao */
    /* renamed from: anet.channel.SessionRequest$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[EventType.values().length];

        static {
            try {
                a[EventType.AUTH_SUCC.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[EventType.DISCONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[EventType.CONNECT_FAIL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* compiled from: Taobao */
    private interface IConnCb {
        void onDisConnect(Session session, long j, EventType eventType);

        void onFailed(Session session, long j, EventType eventType, int i);

        void onSuccess(Session session, long j);
    }

    /* compiled from: Taobao */
    class a implements IConnCb {
        boolean a = false;
        private Context c;
        private List<anet.channel.entity.a> d;
        private anet.channel.entity.a e;

        a(Context context, List<anet.channel.entity.a> list, anet.channel.entity.a aVar) {
            this.c = context;
            this.d = list;
            this.e = aVar;
        }

        public void onFailed(Session session, long j, EventType eventType, int i) {
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.SessionRequest", "Connect failed", this.e.h(), "session", session, "host", SessionRequest.this.a(), "isHandleFinish", Boolean.valueOf(this.a));
            }
            if (SessionRequest.this.f) {
                SessionRequest.this.f = false;
            } else if (!this.a) {
                this.a = true;
                SessionRequest.this.c.b(SessionRequest.this, session);
                if (session.tryNextWhenFail && NetworkStatusHelper.g() && !this.d.isEmpty()) {
                    if (ALog.isPrintLog(1)) {
                        ALog.d("awcn.SessionRequest", "use next connInfo to create session", this.e.h(), "host", SessionRequest.this.a());
                    }
                    if (this.e.b == this.e.c && (i == -2003 || i == -2410)) {
                        ListIterator listIterator = this.d.listIterator();
                        while (listIterator.hasNext()) {
                            if (session.getIp().equals(((anet.channel.entity.a) listIterator.next()).a.getIp())) {
                                listIterator.remove();
                            }
                        }
                    }
                    anet.channel.entity.a aVar = (anet.channel.entity.a) this.d.remove(0);
                    SessionRequest.this.a(this.c, aVar, new a(this.c, this.d, aVar), aVar.h());
                    return;
                }
                SessionRequest.this.c();
                if (EventType.CONNECT_FAIL.equals(eventType) && i != -2613 && i != -2601) {
                    AlarmObject alarmObject = new AlarmObject();
                    alarmObject.module = "networkPrefer";
                    alarmObject.modulePoint = "policy";
                    alarmObject.arg = SessionRequest.this.a;
                    alarmObject.errorCode = String.valueOf(i);
                    alarmObject.isSuccess = false;
                    AppMonitor.getInstance().commitAlarm(alarmObject);
                    SessionRequest.this.g.errorCode = String.valueOf(i);
                    SessionRequest.this.g.costTime = System.currentTimeMillis() - SessionRequest.this.g.startTime;
                    AppMonitor.getInstance().commitStat(SessionRequest.this.g);
                }
            }
        }

        public void onSuccess(Session session, long j) {
            ALog.d("awcn.SessionRequest", "Connect Success", this.e.h(), "session", session, "host", SessionRequest.this.a());
            try {
                if (SessionRequest.this.f) {
                    SessionRequest.this.f = false;
                    session.close(false);
                    return;
                }
                SessionRequest.this.c.a(SessionRequest.this, session);
                AlarmObject alarmObject = new AlarmObject();
                alarmObject.module = "networkPrefer";
                alarmObject.modulePoint = "policy";
                alarmObject.arg = SessionRequest.this.a;
                alarmObject.isSuccess = true;
                AppMonitor.getInstance().commitAlarm(alarmObject);
                SessionRequest.this.g.ret = 1;
                if (this.e.a != null) {
                    SessionRequest.this.g.succIpType = this.e.a.getIpType();
                }
                SessionRequest.this.g.costTime = System.currentTimeMillis() - SessionRequest.this.g.startTime;
                AppMonitor.getInstance().commitStat(SessionRequest.this.g);
                SessionRequest.this.c();
            } catch (Throwable e) {
                ALog.e("awcn.SessionRequest", "[onSuccess]:", this.e.h(), e, new Object[0]);
            } finally {
                SessionRequest.this.c();
            }
        }

        public void onDisConnect(Session session, long j, EventType eventType) {
            boolean isAppBackground = GlobalAppRuntimeInfo.isAppBackground();
            ALog.d("awcn.SessionRequest", "Connect Disconnect", this.e.h(), "session", session, "host", SessionRequest.this.a(), "appIsBg", Boolean.valueOf(isAppBackground), "isHandleFinish", Boolean.valueOf(this.a));
            SessionRequest.this.c.b(SessionRequest.this, session);
            if (!this.a) {
                this.a = true;
                if (!session.autoReCreate) {
                    return;
                }
                if (isAppBackground) {
                    ALog.e("awcn.SessionRequest", "[onDisConnect]app background, don't Recreate", this.e.h(), "session", session);
                } else if (NetworkStatusHelper.g()) {
                    try {
                        ALog.d("awcn.SessionRequest", "session disconnected, try to recreate session", this.e.h(), new Object[0]);
                        c.a(new i(this, session), (long) ((Math.random() * 10.0d) * 1000.0d), TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                    }
                } else {
                    ALog.e("awcn.SessionRequest", "[onDisConnect]no network, don't Recreate", this.e.h(), "session", session);
                }
            }
        }
    }

    /* compiled from: Taobao */
    private class b implements Runnable {
        String a = null;

        b(String str) {
            this.a = str;
        }

        public void run() {
            if (SessionRequest.this.d) {
                ALog.e("awcn.SessionRequest", "Connecting timeout!!! reset status!", this.a, new Object[0]);
                if (SessionRequest.this.e != null) {
                    SessionRequest.this.e.tryNextWhenFail = false;
                    SessionRequest.this.e.close();
                }
                SessionRequest.this.a(false);
            }
        }
    }

    SessionRequest(String str, SessionCenter sessionCenter) {
        this.a = str;
        this.h = this.a.substring(this.a.indexOf(HttpConstant.SCHEME_SPLIT) + 3);
        this.b = sessionCenter;
        this.i = sessionCenter.attributeManager.b(this.h);
        this.c = sessionCenter.sessionPool;
    }

    protected String a() {
        return this.a;
    }

    void a(boolean z) {
        this.d = z;
        if (!z) {
            if (this.j != null) {
                this.j.cancel(true);
                this.j = null;
            }
            this.e = null;
        }
    }

    protected synchronized void a(Context context, TypeLevel typeLevel, String str) throws NoNetworkException, NoAvailStrategyException {
        if (this.c.a(this, typeLevel) != null) {
            ALog.d("awcn.SessionRequest", "Available Session exist!!!", str, new Object[0]);
        } else {
            if (TextUtils.isEmpty(str)) {
                str = g.a(null);
            }
            ALog.d("awcn.SessionRequest", "SessionRequest start", str, "host", this.a, "type", typeLevel);
            if (this.d) {
                ALog.d("awcn.SessionRequest", "session is connecting, return", str, "host", a());
            } else {
                a(true);
                this.j = c.a(new b(str), 45, TimeUnit.SECONDS);
                this.g = new SessionConnStat();
                this.g.host = this.a;
                this.g.startTime = System.currentTimeMillis();
                if (NetworkStatusHelper.g()) {
                    List a = a(typeLevel, str);
                    if (a.isEmpty()) {
                        ALog.i("awcn.SessionRequest", "no avalible strategy, can't create session", str, "host", this.a, "type", typeLevel);
                        c();
                        throw new NoAvailStrategyException(this);
                    }
                    List a2 = a(a, str);
                    try {
                        anet.channel.entity.a aVar = (anet.channel.entity.a) a2.remove(0);
                        a(context, aVar, new a(context, a2, aVar), aVar.h());
                    } catch (Throwable th) {
                        c();
                    }
                } else {
                    if (ALog.isPrintLog(1)) {
                        ALog.d("awcn.SessionRequest", "network is not available, can't create session", str, "NetworkStatusHelper.isConnected()", Boolean.valueOf(NetworkStatusHelper.g()));
                    }
                    c();
                    throw new NoNetworkException(this);
                }
            }
        }
        return;
    }

    private List<IConnStrategy> a(TypeLevel typeLevel, String str) {
        List<IConnStrategy> list = Collections.EMPTY_LIST;
        try {
            anet.channel.util.c a = anet.channel.util.c.a(a());
            if (a == null) {
                return Collections.EMPTY_LIST;
            }
            list = StrategyCenter.getInstance().getConnStrategyListByHost(a.b());
            if (!list.isEmpty()) {
                boolean equalsIgnoreCase = "https".equalsIgnoreCase(a.a());
                ListIterator listIterator = list.listIterator();
                while (listIterator.hasNext()) {
                    ConnType valueOf = ConnType.valueOf(((IConnStrategy) listIterator.next()).getProtocol());
                    if (!(valueOf.isSSL() == equalsIgnoreCase && (typeLevel == null || valueOf.getTypeLevel() == typeLevel))) {
                        listIterator.remove();
                    }
                }
            }
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.SessionRequest", "[getAvailStrategy]", str, "strategies", list);
            }
            return list;
        } catch (Throwable th) {
            Throwable th2 = th;
            List<IConnStrategy> list2 = list;
            ALog.e("awcn.SessionRequest", "", str, th2, new Object[0]);
            return list2;
        }
    }

    private List<anet.channel.entity.a> a(List<IConnStrategy> list, String str) {
        if (list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<anet.channel.entity.a> arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            IConnStrategy iConnStrategy = (IConnStrategy) list.get(i2);
            int retryTimes = iConnStrategy.getRetryTimes();
            int i3 = 0;
            while (i3 <= retryTimes) {
                int i4 = i + 1;
                anet.channel.entity.a aVar = new anet.channel.entity.a(a(), str + "_" + i4, iConnStrategy);
                aVar.b = i3;
                aVar.c = retryTimes;
                arrayList.add(aVar);
                i3++;
                i = i4;
            }
        }
        return arrayList;
    }

    private void a(Context context, anet.channel.entity.a aVar, IConnCb iConnCb, String str) {
        ConnType c = aVar.c();
        if (context == null || c.isHttpType()) {
            this.e = new d(context, aVar);
        } else {
            this.e = new TnetSpdySession(context, aVar, this.b.config, this.i, this.b.attributeManager.c(this.h));
        }
        ALog.i("awcn.SessionRequest", "create connection...", str, HttpConstant.HOST, a(), "Type", aVar.c(), "IP", aVar.a(), "Port", Integer.valueOf(aVar.b()), "heartbeat", Integer.valueOf(aVar.g()), "session", this.e);
        a(this.e, iConnCb, System.currentTimeMillis());
        this.e.connect();
        SessionConnStat sessionConnStat = this.g;
        sessionConnStat.retryTimes++;
        if (this.g.retryTimes == 1 && aVar.a != null) {
            this.g.firstIpType = aVar.a.getIpType();
        }
    }

    private void a(Session session, IConnCb iConnCb, long j) {
        if (iConnCb != null) {
            session.registerEventcb(EventType.ALL.getType(), new g(this, iConnCb, j));
            session.registerEventcb((EventType.AUTH_SUCC.getType() | EventType.CONNECT_FAIL.getType()) | EventType.AUTH_FAIL.getType(), new h(this, session));
        }
    }

    protected void b(boolean z) {
        ALog.d("awcn.SessionRequest", "closeSessions", null, "host", this.a, "autoCreate", Boolean.valueOf(z));
        if (!(z || this.e == null)) {
            this.e.tryNextWhenFail = false;
            this.e.close(false);
        }
        List<Session> a = this.c.a(this);
        if (a != null) {
            for (Session session : a) {
                if (session != null) {
                    session.close(z);
                }
            }
        }
    }

    protected void a(String str) {
        ALog.d("awcn.SessionRequest", "reCreateSession", str, "host", this.a);
        b(true);
    }

    protected void a(long j) throws InterruptedException, TimeoutException {
        ALog.d("awcn.SessionRequest", "[await]", null, "timeoutMs", Long.valueOf(j));
        if (j > 0) {
            synchronized (this.k) {
                long currentTimeMillis = System.currentTimeMillis() + j;
                while (this.d) {
                    long currentTimeMillis2 = System.currentTimeMillis();
                    if (currentTimeMillis2 >= currentTimeMillis) {
                        break;
                    }
                    this.k.wait(currentTimeMillis - currentTimeMillis2);
                }
                if (this.d) {
                    throw new TimeoutException();
                }
            }
        }
    }

    protected TypeLevel b() {
        Session session = this.e;
        if (session != null) {
            return session.mConnType.getTypeLevel();
        }
        return null;
    }

    void c() {
        a(false);
        synchronized (this.k) {
            this.k.notifyAll();
        }
    }

    void a(Session session, int i, String str) {
        Context context = GlobalAppRuntimeInfo.getContext();
        if (context != null && this.i != null && this.i.isAccs) {
            try {
                Intent intent = new Intent("com.taobao.accs.intent.action.RECEIVE");
                intent.setPackage(context.getPackageName());
                intent.setClassName(context, "com.taobao.accs.data.MsgDistributeService");
                intent.putExtra(Constants.KEY_COMMAND, 103);
                intent.putExtra("host", session.getHost());
                intent.putExtra("is_center_host", true);
                boolean isAvailable = session.isAvailable();
                if (!isAvailable) {
                    intent.putExtra("errorCode", i);
                    intent.putExtra("errorDetail", str);
                }
                intent.putExtra("connect_avail", isAvailable);
                intent.putExtra("type_inapp", true);
                context.startService(intent);
            } catch (Throwable th) {
                ALog.e("awcn.SessionRequest", "sendConnectInfoBroadCastToAccs", null, th, new Object[0]);
            }
        }
    }
}
