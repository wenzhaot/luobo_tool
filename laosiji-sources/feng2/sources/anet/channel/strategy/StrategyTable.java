package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.dispatch.HttpDispatcher;
import anet.channel.strategy.dispatch.a;
import anet.channel.strategy.k.b;
import anet.channel.strategy.k.c;
import anet.channel.strategy.utils.SerialLruCache;
import anet.channel.strategy.utils.d;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/* compiled from: Taobao */
class StrategyTable implements Serializable {
    protected String a;
    protected volatile String b;
    private volatile transient int c;
    private HotHostLruCache d;
    private transient Map<String, StrategyCollection> e;
    private Set<String> f;

    /* compiled from: Taobao */
    private static class HotHostLruCache extends SerialLruCache<String, StrategyCollection> {
        public HotHostLruCache(int i) {
            super(i);
        }

        protected boolean a(Entry<String, StrategyCollection> entry) {
            if (!((StrategyCollection) entry.getValue()).f) {
                return true;
            }
            Iterator it = entrySet().iterator();
            while (it.hasNext()) {
                if (!((StrategyCollection) ((Entry) it.next()).getValue()).f) {
                    it.remove();
                    break;
                }
            }
            return false;
        }
    }

    protected StrategyTable(String str) {
        this.a = str;
        a();
    }

    private void b() {
        if (HttpDispatcher.getInstance().isInitHostsChanged(this.a)) {
            for (String str : HttpDispatcher.getInstance().getInitHosts()) {
                this.d.put(str, new StrategyCollection(str));
            }
        }
    }

    protected void a() {
        if (this.d == null) {
            this.d = new HotHostLruCache(40);
            b();
        }
        for (StrategyCollection checkInit : this.d.values()) {
            checkInit.checkInit();
        }
        if (this.e == null) {
            this.e = new SerialLruCache(40);
        }
        if (this.f == null) {
            this.f = new TreeSet();
        }
        this.c = GlobalAppRuntimeInfo.isTargetProcess() ? 0 : -1;
    }

    public List<IConnStrategy> queryByHost(String str) {
        if (TextUtils.isEmpty(str) || !d.b(str)) {
            return Collections.EMPTY_LIST;
        }
        StrategyCollection strategyCollection;
        c();
        synchronized (this.d) {
            strategyCollection = (StrategyCollection) this.d.get(str);
        }
        if (strategyCollection == null) {
            boolean z;
            synchronized (this.e) {
                strategyCollection = (StrategyCollection) this.e.get(str);
                if (strategyCollection == null) {
                    StrategyCollection strategyCollection2 = new StrategyCollection(str);
                    this.e.put(str, strategyCollection2);
                    strategyCollection = strategyCollection2;
                    z = true;
                } else {
                    z = false;
                }
            }
            if (z) {
                a(str, false);
            }
        } else if (strategyCollection.isExpired()) {
            a(this.d);
        }
        return strategyCollection.queryStrategyList();
    }

    public String getCnameByHost(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        StrategyCollection strategyCollection;
        String str2;
        synchronized (this.d) {
            strategyCollection = (StrategyCollection) this.d.get(str);
        }
        if (strategyCollection == null) {
            synchronized (this.e) {
                strategyCollection = (StrategyCollection) this.e.get(str);
            }
        } else if (strategyCollection.isExpired()) {
            a(this.d);
        }
        if (strategyCollection != null) {
            str2 = strategyCollection.e;
        } else {
            str2 = null;
        }
        return str2;
    }

    public void update(c cVar) {
        ALog.i("awcn.StrategyTable", "update strategyTable with httpDns response", null, new Object[0]);
        try {
            this.b = cVar.a;
            this.c = cVar.f;
            b[] bVarArr = cVar.c;
            if (bVarArr != null) {
                synchronized (this.d) {
                    synchronized (this.e) {
                        for (b bVar : bVarArr) {
                            if (!(bVar == null || bVar.a == null)) {
                                if (!bVar.h) {
                                    StrategyCollection strategyCollection = (StrategyCollection) this.d.get(bVar.a);
                                    if (strategyCollection == null) {
                                        strategyCollection = (StrategyCollection) this.e.get(bVar.a);
                                        if (strategyCollection == null) {
                                            strategyCollection = new StrategyCollection(bVar.a);
                                            (bVar.k == 1 ? this.d : this.e).put(bVar.a, strategyCollection);
                                        } else if (bVar.k == 1) {
                                            this.d.put(bVar.a, this.e.remove(bVar.a));
                                        }
                                    } else if (bVar.k != 1) {
                                        this.e.put(bVar.a, this.d.remove(bVar.a));
                                    }
                                    strategyCollection.update(bVar);
                                } else if (this.d.remove(bVar.a) == null) {
                                    this.e.remove(bVar.a);
                                }
                            }
                        }
                    }
                }
                if (ALog.isPrintLog(1)) {
                    StringBuilder append = new StringBuilder("uniqueId : ").append(this.a);
                    append.append("\n-------------------------hot domains:------------------------------------");
                    ALog.d("awcn.StrategyTable", append.toString(), null, new Object[0]);
                    synchronized (this.d) {
                        for (Entry entry : this.d.entrySet()) {
                            append.setLength(0);
                            append.append((String) entry.getKey()).append(" = ").append(((StrategyCollection) entry.getValue()).toString());
                            ALog.d("awcn.StrategyTable", append.toString(), null, new Object[0]);
                        }
                    }
                    append.setLength(0);
                    append.append("\n-------------------------cold domains:------------------------------------");
                    ALog.d("awcn.StrategyTable", append.toString(), null, new Object[0]);
                    synchronized (this.e) {
                        for (Entry entry2 : this.e.entrySet()) {
                            append.setLength(0);
                            append.append((String) entry2.getKey()).append(" = ").append(((StrategyCollection) entry2.getValue()).toString());
                            ALog.d("awcn.StrategyTable", append.toString(), null, new Object[0]);
                        }
                    }
                }
            }
        } catch (Throwable th) {
            ALog.e("awcn.StrategyTable", "fail to update strategyTable", null, th, new Object[0]);
        }
    }

    private Set<String> b(Map<String, StrategyCollection> map) {
        Set<String> hashSet = new HashSet();
        long currentTimeMillis = System.currentTimeMillis();
        Object obj = map == this.d ? 1 : null;
        for (StrategyCollection strategyCollection : map.values()) {
            if (obj != null || currentTimeMillis >= strategyCollection.c) {
                hashSet.add(strategyCollection.getHostWithEtag());
                strategyCollection.c = 30000 + currentTimeMillis;
            }
        }
        return hashSet;
    }

    protected void a(String str, boolean z) {
        if (!GlobalAppRuntimeInfo.isAppBackground() && !TextUtils.isEmpty(str) && NetworkStatusHelper.g()) {
            int a = a.a();
            if (a != 3) {
                Set hashSet;
                Set set = Collections.EMPTY_SET;
                if (a == 2) {
                    hashSet = new HashSet();
                    hashSet.add(str);
                } else {
                    StrategyCollection strategyCollection;
                    synchronized (this.d) {
                        strategyCollection = (StrategyCollection) this.d.get(str);
                        if (strategyCollection != null && (z || strategyCollection.isExpired())) {
                            set = b(this.d);
                            set.add(strategyCollection.getHostWithEtag());
                        }
                    }
                    if (strategyCollection == null) {
                        synchronized (this.e) {
                            StrategyCollection strategyCollection2;
                            strategyCollection = (StrategyCollection) this.e.get(str);
                            if (strategyCollection == null) {
                                strategyCollection = new StrategyCollection(str);
                                this.e.put(str, strategyCollection);
                                strategyCollection2 = strategyCollection;
                            } else {
                                strategyCollection2 = strategyCollection;
                            }
                            if (z || strategyCollection2.isExpired()) {
                                hashSet = b(this.e);
                                hashSet.add(strategyCollection2.getHostWithEtag());
                            } else {
                                hashSet = set;
                            }
                        }
                    } else {
                        hashSet = set;
                    }
                }
                HttpDispatcher.getInstance().sendAmdcRequest(hashSet, d(), this.c);
            }
        }
    }

    protected void a(Map<String, StrategyCollection> map) {
        if (!GlobalAppRuntimeInfo.isAppBackground() && a.a() <= 0 && NetworkStatusHelper.g()) {
            Set b;
            synchronized (map) {
                b = b(map);
            }
            if (!b.isEmpty()) {
                HttpDispatcher.getInstance().sendAmdcRequest(b, d(), this.c);
            }
        }
    }

    private void c() {
        try {
            if (HttpDispatcher.getInstance().isInitHostsChanged(this.a)) {
                int i;
                synchronized (this.d) {
                    synchronized (this.e) {
                        i = 0;
                        for (String str : HttpDispatcher.getInstance().getInitHosts()) {
                            int i2;
                            if (this.d.containsKey(str) || this.e.containsKey(str)) {
                                i2 = i;
                            } else {
                                this.e.put(str, new StrategyCollection(str));
                                i2 = 1;
                            }
                            i = i2;
                        }
                    }
                }
                if (i != 0) {
                    a(this.e);
                }
            }
        } catch (Throwable e) {
            ALog.e("awcn.StrategyTable", "checkInitHost failed", null, e, new Object[0]);
        }
    }

    void a(String str, IConnStrategy iConnStrategy, ConnEvent connEvent) {
        StrategyCollection strategyCollection;
        if (ALog.isPrintLog(1)) {
            ALog.d("awcn.StrategyTable", "[notifyConnEvent]", null, HttpConstant.HOST, str, "IConnStrategy", iConnStrategy, "ConnEvent", connEvent);
        }
        a(connEvent, iConnStrategy.getIp());
        synchronized (this.d) {
            synchronized (this.e) {
                strategyCollection = (StrategyCollection) this.d.get(str);
                if (strategyCollection == null) {
                    strategyCollection = (StrategyCollection) this.e.get(str);
                }
            }
        }
        if (strategyCollection != null) {
            strategyCollection.notifyConnEvent(iConnStrategy, connEvent);
        }
    }

    private void a(ConnEvent connEvent, String str) {
        if (!TextUtils.isEmpty(str)) {
            synchronized (this.f) {
                if (connEvent.isSuccess) {
                    this.f.add(str);
                } else {
                    this.f.remove(str);
                }
            }
        }
    }

    private String d() {
        StringBuilder stringBuilder = new StringBuilder();
        synchronized (this.f) {
            for (String append : this.f) {
                stringBuilder.append(append).append(";");
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
