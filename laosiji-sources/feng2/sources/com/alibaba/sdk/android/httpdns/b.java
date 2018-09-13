package com.alibaba.sdk.android.httpdns;

import android.os.Handler;
import android.os.HandlerThread;
import com.alibaba.sdk.android.httpdns.a.c;
import com.alibaba.sdk.android.httpdns.a.e;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

class b {
    private static Handler a;
    /* renamed from: a */
    private static b f5a = new b();
    /* renamed from: a */
    private static ConcurrentMap<String, c> f6a;
    /* renamed from: a */
    private static ConcurrentSkipListSet<String> f7a;

    private b() {
        a = new ConcurrentHashMap();
        a = new ConcurrentSkipListSet();
        HandlerThread handlerThread = new HandlerThread("DBUpdater");
        handlerThread.start();
        a = new Handler(handlerThread.getLooper());
    }

    static b a() {
        return a;
    }

    private boolean a(e eVar) {
        return (System.currentTimeMillis() / 1000) - c.a(eVar.l) > 604800;
    }

    private void b() {
        List<e> a = com.alibaba.sdk.android.httpdns.a.b.a();
        String g = com.alibaba.sdk.android.httpdns.a.b.g();
        for (e eVar : a) {
            if (a(eVar)) {
                com.alibaba.sdk.android.httpdns.a.b.b(eVar);
            } else if (g.equals(eVar.k)) {
                eVar.l = String.valueOf(System.currentTimeMillis() / 1000);
                a.put(eVar.j, new c(eVar));
            }
        }
    }

    /* renamed from: a */
    int m3a() {
        return a.size();
    }

    c a(String str) {
        return (c) a.get(str);
    }

    /* renamed from: a */
    ArrayList<String> m4a() {
        return new ArrayList(a.keySet());
    }

    /* renamed from: a */
    void m5a() {
        if (com.alibaba.sdk.android.httpdns.a.b.a()) {
            a.post(new Runnable() {
                public void run() {
                    b.this.b();
                }
            });
        }
    }

    void a(String str, c cVar) {
        a.put(str, cVar);
        if (com.alibaba.sdk.android.httpdns.a.b.a()) {
            e a = cVar.a();
            if (a.a == null || a.a.size() <= 0) {
                com.alibaba.sdk.android.httpdns.a.b.b(a);
            } else {
                com.alibaba.sdk.android.httpdns.a.b.a(a);
            }
        }
    }

    /* renamed from: a */
    boolean m6a(String str) {
        return a.contains(str);
    }

    void b(String str) {
        a.add(str);
    }

    void c(String str) {
        a.remove(str);
    }

    void clear() {
        a.clear();
        a.clear();
    }
}
