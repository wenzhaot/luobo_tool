package com.alibaba.sdk.android.httpdns;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class q {
    private static q a = null;
    private String b;
    private boolean d;
    private long f;
    private ExecutorService pool;

    private q() {
        this.f = 0;
        this.d = true;
        this.b = null;
        this.pool = null;
        this.pool = Executors.newSingleThreadExecutor();
    }

    public static q a() {
        if (a == null) {
            synchronized (q.class) {
                if (a == null) {
                    a = new q();
                }
            }
        }
        return a;
    }

    private boolean c() {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.f != 0 && currentTimeMillis - this.f < 30000) {
            return false;
        }
        this.f = currentTimeMillis;
        return true;
    }

    public synchronized void a(boolean z) {
        this.d = z;
    }

    public synchronized void e() {
        this.f = 0;
    }

    public synchronized void g(String str) {
        if (str != null) {
            this.b = str;
        }
        if (this.d && c() && this.b != null) {
            g.e("launch a sniff task");
            Callable lVar = new l(this.b, n.SNIFF_HOST);
            lVar.a(0);
            this.pool.submit(lVar);
            this.b = null;
        } else {
            g.e("hostname is null or sniff too often or sniffer is turned off");
        }
    }
}
