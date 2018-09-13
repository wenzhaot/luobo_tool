package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.strategy.k.b;
import anet.channel.strategy.k.c;
import anet.channel.strategy.utils.SerialLruCache;
import anet.channel.strategy.utils.d;
import anet.channel.util.ALog;
import java.io.Serializable;
import java.util.Map.Entry;
import java.util.TreeMap;

/* compiled from: Taobao */
class SafeAislesMap implements Serializable {
    public static final String NO_RESULT = "No_Result";
    private SerialLruCache<String, String> a = null;
    private transient StrategyInfoHolder b = null;

    SafeAislesMap() {
        a();
    }

    void a(StrategyInfoHolder strategyInfoHolder) {
        this.b = strategyInfoHolder;
    }

    void a() {
        if (this.a == null) {
            this.a = new SerialLruCache(128);
        }
    }

    void a(c cVar) {
        if (cVar.c != null) {
            synchronized (this.a) {
                TreeMap treeMap = null;
                for (b bVar : cVar.c) {
                    if (bVar.h) {
                        this.a.remove(bVar.a);
                    } else if (bVar.j) {
                        continue;
                    } else if (bVar.d != null) {
                        if (treeMap == null) {
                            treeMap = new TreeMap();
                        }
                        treeMap.put(bVar.a, bVar.d);
                    } else if ("http".equalsIgnoreCase(bVar.c) || "https".equalsIgnoreCase(bVar.c)) {
                        this.a.put(bVar.a, bVar.c);
                    } else {
                        this.a.put(bVar.a, NO_RESULT);
                    }
                }
                if (treeMap != null) {
                    for (Entry entry : treeMap.entrySet()) {
                        String str = (String) entry.getValue();
                        if (this.a.containsKey(str)) {
                            this.a.put(entry.getKey(), this.a.get(str));
                        } else {
                            this.a.put(entry.getKey(), NO_RESULT);
                        }
                    }
                }
            }
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.SafeAislesMap", toString(), null, new Object[0]);
            }
        }
    }

    String a(String str) {
        if (TextUtils.isEmpty(str) || !d.b(str)) {
            return null;
        }
        String str2;
        synchronized (this.a) {
            str2 = (String) this.a.get(str);
            if (str2 == null) {
                this.a.put(str, NO_RESULT);
            }
        }
        if (str2 == null) {
            this.b.d().a(str, false);
            return str2;
        } else if (NO_RESULT.equals(str2)) {
            return null;
        } else {
            return str2;
        }
    }

    public String toString() {
        String str;
        synchronized (this.a) {
            str = "SafeAislesMap: " + this.a.toString();
        }
        return str;
    }
}
