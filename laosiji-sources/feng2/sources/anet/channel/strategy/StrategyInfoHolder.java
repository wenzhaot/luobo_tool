package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.status.NetworkStatusHelper.INetworkStatusChangeListener;
import anet.channel.status.NetworkStatusHelper.NetworkStatus;
import anet.channel.strategy.k.c;
import anet.channel.strategy.utils.SerialLruCache;
import anet.channel.strategy.utils.a;
import anet.channel.util.StringUtils;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* compiled from: Taobao */
class StrategyInfoHolder implements INetworkStatusChangeListener {
    static final String a = b(UnitMap.class.getSimpleName());
    static final String b = b(SafeAislesMap.class.getSimpleName());
    Map<String, StrategyTable> c = new LruStrategyMap();
    UnitMap d = null;
    SafeAislesMap e = null;
    final a f = new a();
    private final StrategyTable g = new StrategyTable("Unknown");
    private final Set<String> h = new HashSet();
    private volatile String i = "";

    /* compiled from: Taobao */
    private static class LruStrategyMap extends SerialLruCache<String, StrategyTable> {
        public LruStrategyMap() {
            super(3);
        }

        protected boolean a(Entry<String, StrategyTable> entry) {
            a.a(new f(this, entry));
            return true;
        }
    }

    public static StrategyInfoHolder a() {
        return new StrategyInfoHolder();
    }

    private StrategyInfoHolder() {
        try {
            e();
            g();
        } catch (Throwable th) {
        } finally {
            f();
        }
    }

    void b() {
        NetworkStatusHelper.b(this);
    }

    private void e() {
        NetworkStatusHelper.a((INetworkStatusChangeListener) this);
        this.i = a(NetworkStatusHelper.a());
    }

    private void f() {
        for (Entry value : this.c.entrySet()) {
            ((StrategyTable) value.getValue()).a();
        }
        if (this.d == null) {
            this.d = new UnitMap();
        } else {
            this.d.a();
        }
        if (this.e == null) {
            this.e = new SafeAislesMap();
        } else {
            this.e.a();
        }
        this.e.a(this);
    }

    private void g() {
        String b = b(this.i);
        if (!TextUtils.isEmpty(this.i)) {
            a(b, this.i);
        }
        this.d = (UnitMap) l.b(a);
        this.e = (SafeAislesMap) l.b(b);
        a.a(new d(this, b));
    }

    /* JADX WARNING: Missing block: B:7:0x0013, code:
            r0 = (anet.channel.strategy.StrategyTable) anet.channel.strategy.l.b(r9);
     */
    /* JADX WARNING: Missing block: B:8:0x0019, code:
            if (r0 == null) goto L_0x0029;
     */
    /* JADX WARNING: Missing block: B:9:0x001b, code:
            r0.a();
            r3 = r8.c;
     */
    /* JADX WARNING: Missing block: B:10:0x0020, code:
            monitor-enter(r3);
     */
    /* JADX WARNING: Missing block: B:12:?, code:
            r8.c.put(r0.a, r0);
     */
    /* JADX WARNING: Missing block: B:13:0x0028, code:
            monitor-exit(r3);
     */
    /* JADX WARNING: Missing block: B:14:0x0029, code:
            r3 = r8.h;
     */
    /* JADX WARNING: Missing block: B:15:0x002b, code:
            monitor-enter(r3);
     */
    /* JADX WARNING: Missing block: B:17:?, code:
            r8.h.remove(r9);
     */
    /* JADX WARNING: Missing block: B:18:0x0031, code:
            monitor-exit(r3);
     */
    /* JADX WARNING: Missing block: B:20:0x0036, code:
            if (android.text.TextUtils.isEmpty(r10) != false) goto L_?;
     */
    /* JADX WARNING: Missing block: B:21:0x0038, code:
            if (r0 == null) goto L_0x008c;
     */
    /* JADX WARNING: Missing block: B:22:0x003a, code:
            r0 = true;
     */
    /* JADX WARNING: Missing block: B:23:0x003b, code:
            anet.channel.util.ALog.i("awcn.StrategyInfoHolder", "restore strategy file", null, "id", r8.i, "result", java.lang.Boolean.valueOf(r0));
            r1 = new anet.channel.statist.AlarmObject();
            r1.module = "networkPrefer";
            r1.modulePoint = "strategy_load_stat";
            r1.isSuccess = r0;
            r1.arg = r8.i;
     */
    /* JADX WARNING: Missing block: B:24:0x0077, code:
            if (anet.channel.GlobalAppRuntimeInfo.isTargetProcess() == false) goto L_?;
     */
    /* JADX WARNING: Missing block: B:25:0x0079, code:
            anet.channel.appmonitor.AppMonitor.getInstance().commitAlarm(r1);
     */
    /* JADX WARNING: Missing block: B:37:0x008c, code:
            r0 = false;
     */
    /* JADX WARNING: Missing block: B:38:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:39:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:40:?, code:
            return;
     */
    private void a(java.lang.String r9, java.lang.String r10) {
        /*
        r8 = this;
        r1 = 1;
        r2 = 0;
        r3 = r8.h;
        monitor-enter(r3);
        r0 = r8.h;	 Catch:{ all -> 0x0083 }
        r0 = r0.contains(r9);	 Catch:{ all -> 0x0083 }
        if (r0 != 0) goto L_0x0081;
    L_0x000d:
        r0 = r8.h;	 Catch:{ all -> 0x0083 }
        r0.add(r9);	 Catch:{ all -> 0x0083 }
        monitor-exit(r3);
        r0 = anet.channel.strategy.l.b(r9);
        r0 = (anet.channel.strategy.StrategyTable) r0;
        if (r0 == 0) goto L_0x0029;
    L_0x001b:
        r0.a();
        r3 = r8.c;
        monitor-enter(r3);
        r4 = r8.c;	 Catch:{ all -> 0x0086 }
        r5 = r0.a;	 Catch:{ all -> 0x0086 }
        r4.put(r5, r0);	 Catch:{ all -> 0x0086 }
        monitor-exit(r3);	 Catch:{ all -> 0x0086 }
    L_0x0029:
        r3 = r8.h;
        monitor-enter(r3);
        r4 = r8.h;	 Catch:{ all -> 0x0089 }
        r4.remove(r9);	 Catch:{ all -> 0x0089 }
        monitor-exit(r3);	 Catch:{ all -> 0x0089 }
        r3 = android.text.TextUtils.isEmpty(r10);
        if (r3 != 0) goto L_0x0080;
    L_0x0038:
        if (r0 == 0) goto L_0x008c;
    L_0x003a:
        r0 = r1;
    L_0x003b:
        r3 = "awcn.StrategyInfoHolder";
        r4 = "restore strategy file";
        r5 = 0;
        r6 = 4;
        r6 = new java.lang.Object[r6];
        r7 = "id";
        r6[r2] = r7;
        r2 = r8.i;
        r6[r1] = r2;
        r1 = 2;
        r2 = "result";
        r6[r1] = r2;
        r1 = 3;
        r2 = java.lang.Boolean.valueOf(r0);
        r6[r1] = r2;
        anet.channel.util.ALog.i(r3, r4, r5, r6);
        r1 = new anet.channel.statist.AlarmObject;
        r1.<init>();
        r2 = "networkPrefer";
        r1.module = r2;
        r2 = "strategy_load_stat";
        r1.modulePoint = r2;
        r1.isSuccess = r0;
        r0 = r8.i;
        r1.arg = r0;
        r0 = anet.channel.GlobalAppRuntimeInfo.isTargetProcess();
        if (r0 == 0) goto L_0x0080;
    L_0x0079:
        r0 = anet.channel.appmonitor.AppMonitor.getInstance();
        r0.commitAlarm(r1);
    L_0x0080:
        return;
    L_0x0081:
        monitor-exit(r3);	 Catch:{ all -> 0x0083 }
        goto L_0x0080;
    L_0x0083:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
    L_0x0086:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
    L_0x0089:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
    L_0x008c:
        r0 = r2;
        goto L_0x003b;
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.strategy.StrategyInfoHolder.a(java.lang.String, java.lang.String):void");
    }

    void c() {
        synchronized (this) {
            for (StrategyTable strategyTable : this.c.values()) {
                l.a(strategyTable, b(strategyTable.a));
            }
            l.a(this.d, a);
            l.a(this.e, b);
        }
    }

    StrategyTable d() {
        StrategyTable strategyTable = this.g;
        if (!TextUtils.isEmpty(this.i)) {
            synchronized (this.c) {
                strategyTable = (StrategyTable) this.c.get(this.i);
                if (strategyTable == null) {
                    strategyTable = new StrategyTable(this.i);
                    this.c.put(this.i, strategyTable);
                }
            }
        }
        return strategyTable;
    }

    private static String b(String str) {
        Object md5ToHex = StringUtils.md5ToHex(str);
        return !TextUtils.isEmpty(md5ToHex) ? md5ToHex : StringUtils.bytesToHexString(str.getBytes());
    }

    private String a(NetworkStatus networkStatus) {
        String str = "";
        if (networkStatus.isWifi()) {
            return new StringBuilder(networkStatus.getType()).append("$").append(NetworkStatusHelper.f()).toString();
        } else if (networkStatus.isMobile()) {
            return new StringBuilder(networkStatus.getType()).append("$").append(NetworkStatusHelper.c()).toString();
        } else {
            return str;
        }
    }

    void a(c cVar) {
        if (cVar.g != 0) {
            anet.channel.strategy.dispatch.a.a(cVar.g, cVar.h);
        }
        synchronized (this) {
            d().update(cVar);
            this.e.a(cVar);
            this.d.a(cVar);
        }
    }

    public void onNetworkStatusChanged(NetworkStatus networkStatus) {
        this.i = a(networkStatus);
        if (!TextUtils.isEmpty(this.i)) {
            synchronized (this.c) {
                if (!this.c.containsKey(this.i)) {
                    a.a(new e(this, this.i));
                }
            }
        }
    }
}
