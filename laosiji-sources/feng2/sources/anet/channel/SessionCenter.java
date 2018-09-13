package anet.channel;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.LruCache;
import anet.channel.Config.Builder;
import anet.channel.entity.ConnType;
import anet.channel.entity.ConnType.TypeLevel;
import anet.channel.entity.ENV;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.status.NetworkStatusHelper.INetworkStatusChangeListener;
import anet.channel.status.NetworkStatusHelper.NetworkStatus;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.IStrategyListener;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.k.b;
import anet.channel.strategy.k.c;
import anet.channel.util.ALog;
import anet.channel.util.AppLifecycle;
import anet.channel.util.AppLifecycle.AppLifecycleListener;
import anet.channel.util.HttpConstant;
import anet.channel.util.StringUtils;
import anet.channel.util.Utils;
import anet.channel.util.g;
import anetwork.channel.util.RequestConstant;
import com.stub.StubApp;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;

/* compiled from: Taobao */
public class SessionCenter {
    public static final String TAG = "awcn.SessionCenter";
    static Map<Config, SessionCenter> instancesMap = new HashMap();
    private static boolean mInit = false;
    final AccsSessionManager accsSessionManager;
    final c attributeManager = new c();
    Config config;
    Context context = GlobalAppRuntimeInfo.getContext();
    final a innerListener = new a(this, null);
    String seqNum;
    final f sessionPool = new f();
    final LruCache<String, SessionRequest> srCache = new LruCache(32);

    /* compiled from: Taobao */
    private class a implements INetworkStatusChangeListener, IStrategyListener, AppLifecycleListener {
        boolean a;

        private a() {
            this.a = false;
        }

        /* synthetic */ a(SessionCenter sessionCenter, d dVar) {
            this();
        }

        void a() {
            AppLifecycle.registerLifecycleListener(this);
            NetworkStatusHelper.a((INetworkStatusChangeListener) this);
            StrategyCenter.getInstance().registerListener(this);
        }

        void b() {
            StrategyCenter.getInstance().unregisterListener(this);
            AppLifecycle.unregisterLifecycleListener(this);
            NetworkStatusHelper.b(this);
        }

        public void onNetworkStatusChanged(NetworkStatus networkStatus) {
            ALog.e(SessionCenter.TAG, "onNetworkStatusChanged. reCreateSession", SessionCenter.this.seqNum, "networkStatus", networkStatus);
            List<SessionRequest> a = SessionCenter.this.sessionPool.a();
            if (a.isEmpty()) {
                ALog.i(SessionCenter.TAG, "recreate session failed: infos is empty", SessionCenter.this.seqNum, new Object[0]);
            } else {
                for (SessionRequest sessionRequest : a) {
                    ALog.d(SessionCenter.TAG, "network change, try recreate session", SessionCenter.this.seqNum, new Object[0]);
                    sessionRequest.a(null);
                }
            }
            SessionCenter.this.accsSessionManager.checkAndStartSession();
        }

        public void onStrategyUpdated(c cVar) {
            SessionCenter.this.checkEffectNow(cVar);
            SessionCenter.this.accsSessionManager.checkAndStartSession();
        }

        public void forground() {
            ALog.i(SessionCenter.TAG, "[forground]", SessionCenter.this.seqNum, new Object[0]);
            if (SessionCenter.this.context != null && !this.a) {
                this.a = true;
                if (SessionCenter.mInit) {
                    try {
                        anet.channel.a.c.a(new e(this));
                        return;
                    } catch (Exception e) {
                        return;
                    }
                }
                ALog.e(SessionCenter.TAG, "forground not inited!", SessionCenter.this.seqNum, new Object[0]);
            }
        }

        public void background() {
            ALog.i(SessionCenter.TAG, "[background]", SessionCenter.this.seqNum, new Object[0]);
            if (SessionCenter.mInit) {
                try {
                    StrategyCenter.getInstance().saveData();
                    if ("OPPO".equalsIgnoreCase(Build.BRAND)) {
                        ALog.i(SessionCenter.TAG, "close session for OPPO", SessionCenter.this.seqNum, new Object[0]);
                        SessionCenter.this.accsSessionManager.forceCloseSession(false);
                        return;
                    }
                    return;
                } catch (Exception e) {
                    return;
                }
            }
            ALog.e(SessionCenter.TAG, "background not inited!", SessionCenter.this.seqNum, new Object[0]);
        }
    }

    public static synchronized void init(Context context) {
        synchronized (SessionCenter.class) {
            if (context == null) {
                ALog.e(TAG, "paramter context is null!", null, new Object[0]);
                throw new NullPointerException("init failed. paramter context is null");
            }
            GlobalAppRuntimeInfo.setContext(StubApp.getOrigApplicationContext(context.getApplicationContext()));
            if (!mInit) {
                instancesMap.put(Config.DEFAULT_CONFIG, new SessionCenter(Config.DEFAULT_CONFIG));
                AppLifecycle.initialize();
                StrategyCenter.getInstance().initialize(GlobalAppRuntimeInfo.getContext());
                mInit = true;
            }
        }
    }

    @Deprecated
    public static synchronized void init(Context context, String str) {
        synchronized (SessionCenter.class) {
            init(context, str, GlobalAppRuntimeInfo.getEnv());
        }
    }

    public static synchronized void init(Context context, String str, ENV env) {
        synchronized (SessionCenter.class) {
            if (context == null) {
                ALog.e(TAG, "paramter context is null!", null, new Object[0]);
                throw new NullPointerException("init failed. paramter context is null");
            }
            Config config = Config.getConfig(str, env);
            if (config == null) {
                config = new Builder().setAppkey(str).setEnv(env).build();
            }
            init(context, config);
        }
    }

    public static synchronized void init(Context context, Config config) {
        synchronized (SessionCenter.class) {
            if (context == null) {
                ALog.e(TAG, "paramter context is null!", null, new Object[0]);
                throw new NullPointerException("init failed. paramter context is null");
            } else if (config == null) {
                ALog.e(TAG, "paramter config is null!", null, new Object[0]);
                throw new NullPointerException("init failed. paramter config is null");
            } else {
                init(context);
                if (!instancesMap.containsKey(config)) {
                    instancesMap.put(config, new SessionCenter(config));
                }
            }
        }
    }

    private SessionCenter(Config config) {
        this.config = config;
        this.seqNum = config.getAppkey();
        this.innerListener.a();
        this.accsSessionManager = new AccsSessionManager(this);
        if (anet.channel.strategy.dispatch.a.b() == null && !config.getAppkey().equals("[default]")) {
            anet.channel.strategy.dispatch.a.a(new d(this, config.getAppkey(), config.getSecurity()));
        }
    }

    private void dispose() {
        ALog.i(TAG, "instance dispose", this.seqNum, new Object[0]);
        this.accsSessionManager.forceCloseSession(false);
        this.innerListener.b();
    }

    @Deprecated
    public synchronized void switchEnv(ENV env) {
        switchEnvironment(env);
    }

    public static synchronized void switchEnvironment(ENV env) {
        int i = 0;
        synchronized (SessionCenter.class) {
            try {
                if (GlobalAppRuntimeInfo.getEnv() != env) {
                    ALog.i(TAG, "switch env", null, "old", GlobalAppRuntimeInfo.getEnv(), "new", env);
                    GlobalAppRuntimeInfo.setEnv(env);
                    StrategyCenter.getInstance().switchEnv();
                    SpdyAgent instance = SpdyAgent.getInstance(GlobalAppRuntimeInfo.getContext(), SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
                    if (env != ENV.TEST) {
                        i = 1;
                    }
                    instance.switchAccsServer(i);
                }
                Iterator it = instancesMap.entrySet().iterator();
                while (it.hasNext()) {
                    SessionCenter sessionCenter = (SessionCenter) ((Entry) it.next()).getValue();
                    if (sessionCenter.config.getEnv() != env) {
                        ALog.i(TAG, "remove instance", sessionCenter.seqNum, RequestConstant.ENVIRONMENT, sessionCenter.config.getEnv());
                        sessionCenter.dispose();
                        it.remove();
                    }
                }
            } catch (Throwable th) {
                ALog.e(TAG, "switch env error.", null, th, new Object[0]);
            }
        }
        return;
    }

    public static synchronized SessionCenter getInstance(String str) {
        SessionCenter instance;
        synchronized (SessionCenter.class) {
            Config configByTag = Config.getConfigByTag(str);
            if (configByTag == null) {
                throw new RuntimeException("tag not exist!");
            }
            instance = getInstance(configByTag);
        }
        return instance;
    }

    public static synchronized SessionCenter getInstance(Config config) {
        SessionCenter sessionCenter;
        synchronized (SessionCenter.class) {
            if (config == null) {
                throw new NullPointerException("config is null!");
            }
            if (!mInit) {
                Context appContext = Utils.getAppContext();
                if (appContext != null) {
                    init(appContext);
                }
            }
            sessionCenter = (SessionCenter) instancesMap.get(config);
            if (sessionCenter == null) {
                sessionCenter = new SessionCenter(config);
                instancesMap.put(config, sessionCenter);
            }
        }
        return sessionCenter;
    }

    @Deprecated
    public static synchronized SessionCenter getInstance() {
        SessionCenter sessionCenter;
        synchronized (SessionCenter.class) {
            if (!mInit) {
                Context appContext = Utils.getAppContext();
                if (appContext != null) {
                    init(appContext);
                }
            }
            sessionCenter = null;
            for (Entry entry : instancesMap.entrySet()) {
                sessionCenter = (SessionCenter) entry.getValue();
                if (entry.getKey() != Config.DEFAULT_CONFIG) {
                    break;
                }
            }
        }
        return sessionCenter;
    }

    public Session getThrowsException(String str, long j) throws Exception {
        return getThrowsException(str, null, j);
    }

    public Session getThrowsException(String str, TypeLevel typeLevel, long j) throws Exception {
        return getInternal(anet.channel.util.c.a(str), typeLevel, j);
    }

    public Session getThrowsException(anet.channel.util.c cVar, TypeLevel typeLevel, long j) throws Exception {
        return getInternal(cVar, typeLevel, j);
    }

    public Session get(String str, long j) {
        return get(str, null, j);
    }

    public Session get(String str, TypeLevel typeLevel, long j) {
        return get(anet.channel.util.c.a(str), typeLevel, j);
    }

    public Session get(anet.channel.util.c cVar, TypeLevel typeLevel, long j) {
        Session session = null;
        try {
            return getInternal(cVar, typeLevel, j);
        } catch (Throwable e) {
            ALog.e(TAG, "[Get]param url is invaild", this.seqNum, e, "url", cVar.e());
            return session;
        } catch (Throwable e2) {
            ALog.e(TAG, "[Get]timeout exception", this.seqNum, e2, "url", cVar.e());
            return session;
        } catch (NoNetworkException e3) {
            ALog.e(TAG, "[Get]no network", this.seqNum, "url", cVar.e());
            return session;
        } catch (NoAvailStrategyException e4) {
            ALog.w(TAG, "[Get]no strategy", this.seqNum, "url", cVar.e());
            return session;
        } catch (ConnectException e5) {
            ALog.e(TAG, "[Get]connect exception", this.seqNum, "errMsg", e5.getMessage(), "url", cVar.e());
            return session;
        } catch (Throwable e22) {
            ALog.e(TAG, "[Get]exception", this.seqNum, e22, "url", cVar.e());
            return session;
        }
    }

    public void registerSessionInfo(SessionInfo sessionInfo) {
        this.attributeManager.a(sessionInfo);
        if (sessionInfo.isKeepAlive) {
            this.accsSessionManager.checkAndStartSession();
        }
    }

    public void unregisterSessionInfo(String str) {
        if (this.attributeManager.a(str).isKeepAlive) {
            this.accsSessionManager.checkAndStartSession();
        }
    }

    public void registerPublicKey(String str, int i) {
        this.attributeManager.a(str, i);
    }

    public static void checkAndStartAccsSession() {
        for (SessionCenter sessionCenter : instancesMap.values()) {
            sessionCenter.accsSessionManager.checkAndStartSession();
        }
    }

    public void forceRecreateAccsSession() {
        this.accsSessionManager.forceReCreateSession();
    }

    protected Session getInternal(anet.channel.util.c cVar, TypeLevel typeLevel, long j) throws Exception {
        if (!mInit) {
            ALog.e(TAG, "getInternal not inited!", this.seqNum, new Object[0]);
            return null;
        } else if (cVar == null) {
            return null;
        } else {
            ALog.d(TAG, "getInternal", this.seqNum, "u", cVar.e(), "TypeClass", typeLevel, "timeout", Long.valueOf(j));
            String cNameByHost = StrategyCenter.getInstance().getCNameByHost(cVar.b());
            if (cNameByHost == null) {
                cNameByHost = cVar.b();
            }
            String a = cVar.a();
            if (!cVar.h()) {
                a = StrategyCenter.getInstance().getSchemeByHost(cNameByHost, a);
            }
            SessionRequest sessionRequest = getSessionRequest(StringUtils.concatString(a, HttpConstant.SCHEME_SPLIT, cNameByHost));
            Session a2 = this.sessionPool.a(sessionRequest, typeLevel);
            if (a2 != null) {
                ALog.d(TAG, "get internal hit cache session", this.seqNum, "session", a2);
                return a2;
            } else if (this.config == Config.DEFAULT_CONFIG && typeLevel == TypeLevel.SPDY) {
                return null;
            } else {
                if (GlobalAppRuntimeInfo.isAppBackground() && typeLevel == TypeLevel.SPDY && a.a()) {
                    SessionInfo b = this.attributeManager.b(cVar.b());
                    if (b != null && b.isAccs) {
                        ALog.w(TAG, "app background, forbid to create accs session", this.seqNum, new Object[0]);
                        throw new ConnectException("accs session connecting forbidden in background");
                    }
                }
                sessionRequest.a(this.context, typeLevel, g.a(this.seqNum));
                if (j <= 0 || sessionRequest.b() != typeLevel) {
                    return a2;
                }
                sessionRequest.a(j);
                a2 = this.sessionPool.a(sessionRequest, typeLevel);
                if (a2 != null) {
                    return a2;
                }
                throw new ConnectException("session connecting failed or timeout");
            }
        }
    }

    @Deprecated
    public void enterBackground() {
        AppLifecycle.onBackground();
    }

    @Deprecated
    public void enterForeground() {
        AppLifecycle.onForeground();
    }

    private void checkEffectNow(c cVar) {
        b[] bVarArr = cVar.c;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < bVarArr.length) {
                b bVar = bVarArr[i2];
                if (bVar.l) {
                    ALog.i(TAG, "find effectNow", this.seqNum, "host", bVar.a);
                    anet.channel.strategy.k.a[] aVarArr = bVar.g;
                    String[] strArr = bVar.e;
                    for (Session session : this.sessionPool.a(getSessionRequest(StringUtils.buildKey(bVar.c, bVar.a)))) {
                        if (!session.getConnType().isHttpType()) {
                            int i3;
                            Object obj;
                            for (Object equals : strArr) {
                                if (session.getIp().equals(equals)) {
                                    obj = 1;
                                    break;
                                }
                            }
                            obj = null;
                            if (obj == null) {
                                if (ALog.isPrintLog(2)) {
                                    ALog.i(TAG, "ip not match", this.seqNum, "session ip", session.getIp(), "ips", Arrays.toString(strArr));
                                }
                                session.close(true);
                            } else {
                                i3 = 0;
                                while (i3 < aVarArr.length) {
                                    if (session.getPort() == aVarArr[i3].a && session.getConnType().equals(ConnType.valueOf(ConnProtocol.valueOf(aVarArr[i3])))) {
                                        obj = 1;
                                        break;
                                    }
                                    i3++;
                                }
                                obj = null;
                                if (obj == null) {
                                    if (ALog.isPrintLog(2)) {
                                        ALog.i(TAG, "aisle not match", this.seqNum, "port", Integer.valueOf(session.getPort()), "connType", session.getConnType(), "aisle", Arrays.toString(aVarArr));
                                    }
                                    session.close(true);
                                } else {
                                    ALog.i(TAG, "session matches, do nothing", null, new Object[0]);
                                }
                            }
                        }
                    }
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    protected SessionRequest getSessionRequest(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        SessionRequest sessionRequest;
        synchronized (this.srCache) {
            sessionRequest = (SessionRequest) this.srCache.get(str);
            if (sessionRequest == null) {
                sessionRequest = new SessionRequest(str, this);
                this.srCache.put(str, sessionRequest);
            }
        }
        return sessionRequest;
    }
}
