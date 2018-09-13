package com.tencent.bugly.imsdk.crashreport.crash;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.tencent.bugly.imsdk.crashreport.common.info.b;
import com.tencent.bugly.imsdk.crashreport.common.strategy.a;
import com.tencent.bugly.imsdk.proguard.t;
import com.tencent.bugly.imsdk.proguard.w;

/* compiled from: BUGLY */
public class BuglyBroadcastRecevier extends BroadcastReceiver {
    private static BuglyBroadcastRecevier d = null;
    private IntentFilter a = new IntentFilter();
    private Context b;
    private String c;
    private boolean e = true;

    public static synchronized BuglyBroadcastRecevier getInstance() {
        BuglyBroadcastRecevier buglyBroadcastRecevier;
        synchronized (BuglyBroadcastRecevier.class) {
            if (d == null) {
                d = new BuglyBroadcastRecevier();
            }
            buglyBroadcastRecevier = d;
        }
        return buglyBroadcastRecevier;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.b != null) {
            this.b.unregisterReceiver(this);
        }
    }

    public synchronized void addFilter(String str) {
        if (!this.a.hasAction(str)) {
            this.a.addAction(str);
        }
        w.c("add action %s", str);
    }

    public synchronized void regist(Context context) {
        try {
            w.a("regis BC", new Object[0]);
            this.b = context;
            context.registerReceiver(this, this.a);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return;
    }

    public synchronized void unregist(Context context) {
        try {
            w.a("unregis BC", new Object[0]);
            context.unregisterReceiver(this);
            this.b = context;
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
        }
    }

    public final void onReceive(Context context, Intent intent) {
        try {
            a(context, intent);
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
        }
    }

    private synchronized boolean a(Context context, Intent intent) {
        boolean z = true;
        synchronized (this) {
            if (!(context == null || intent == null)) {
                if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    if (this.e) {
                        this.e = false;
                    } else {
                        String e = b.e(this.b);
                        w.c("is Connect BC " + e, new Object[0]);
                        w.a("network %s changed to %s", this.c, e);
                        if (e == null) {
                            this.c = null;
                        } else {
                            String str = this.c;
                            this.c = e;
                            long currentTimeMillis = System.currentTimeMillis();
                            a a = a.a();
                            t a2 = t.a();
                            com.tencent.bugly.imsdk.crashreport.common.info.a a3 = com.tencent.bugly.imsdk.crashreport.common.info.a.a(context);
                            if (a == null || a2 == null || a3 == null) {
                                w.d("not inited BC not work", new Object[0]);
                            } else if (!e.equals(str)) {
                                if (currentTimeMillis - a2.a(c.a) > 30000) {
                                    w.a("try to upload crash on network changed.", new Object[0]);
                                    c a4 = c.a();
                                    if (a4 != null) {
                                        a4.a(0);
                                    }
                                }
                                if (currentTimeMillis - a2.a(1001) > 30000) {
                                    w.a("try to upload userinfo on network changed.", new Object[0]);
                                    com.tencent.bugly.imsdk.crashreport.biz.b.a.b();
                                }
                            }
                        }
                    }
                }
            }
            z = false;
        }
        return z;
    }
}
