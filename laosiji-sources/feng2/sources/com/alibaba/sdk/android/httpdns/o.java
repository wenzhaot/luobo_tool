package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class o {
    private static o a = null;
    private static boolean c = false;
    private static long d = 0;
    private static String h = "https://";
    private static String i = null;
    /* renamed from: a */
    private SharedPreferences f15a;
    private int e;
    /* renamed from: e */
    private long f16e;
    private ExecutorService pool;

    private o() {
        this.e = 0;
        this.a = null;
        this.pool = null;
        this.e = 0;
        this.pool = Executors.newSingleThreadExecutor();
    }

    public static o a() {
        if (a == null) {
            synchronized (o.class) {
                if (a == null) {
                    a = new o();
                }
            }
        }
        return a;
    }

    private void d() {
        if (this.e < d.c.length - 1) {
            this.e++;
        } else {
            this.e = 0;
        }
    }

    synchronized void a(Context context) {
        if (!c) {
            synchronized (o.class) {
                if (!c) {
                    if (context != null) {
                        this.a = context.getSharedPreferences("httpdns_config_cache", 0);
                    }
                    i = this.a.getString("httpdns_server_ips", null);
                    if (i != null) {
                        d.a(i.split(";"));
                    }
                    d = this.a.getLong("schedule_center_last_request_time", 0);
                    if (d == 0 || System.currentTimeMillis() - d >= 86400000) {
                        q.a().a(false);
                        c();
                    }
                    c = true;
                }
            }
        }
    }

    synchronized void a(p pVar) {
        this.e = 0;
        HttpDns.switchDnsService(pVar.isEnabled());
        if (a(pVar.c())) {
            g.e("Scheduler center update success");
            this.e = System.currentTimeMillis();
            s.g();
        }
    }

    synchronized boolean a(String[] strArr) {
        boolean z = false;
        synchronized (this) {
            if (d.a(strArr)) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String append : strArr) {
                    stringBuilder.append(append);
                    stringBuilder.append(";");
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                if (this.a != null) {
                    Editor edit = this.a.edit();
                    edit.putString("httpdns_server_ips", stringBuilder.toString());
                    edit.putLong("schedule_center_last_request_time", System.currentTimeMillis());
                    edit.commit();
                    z = true;
                }
            }
        }
        return z;
    }

    synchronized void b(Throwable th) {
        if (th instanceof SocketTimeoutException) {
            d();
            if (this.e == 0) {
                this.e = System.currentTimeMillis();
                g.f("Scheduler center update failed");
                s.h();
            }
        }
    }

    synchronized void c() {
        if (System.currentTimeMillis() - this.e >= 300000) {
            g.e("update server ips from schedule center.");
            this.e = 0;
            this.pool.submit(new m(d.c.length - 1));
        } else {
            g.e("update server ips from schedule center too often, give up. ");
            s.h();
        }
    }

    synchronized String f() {
        return h + d.c[this.e] + "/sc/httpdns_config?account_id=" + d.c + "&platform=android&sdk_version=" + "1.1.3.1";
    }
}
