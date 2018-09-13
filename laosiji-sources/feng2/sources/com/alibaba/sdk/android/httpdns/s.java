package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.NotificationCompat;
import java.net.SocketTimeoutException;

public class s {
    private static SharedPreferences a = null;
    /* renamed from: a */
    private static a f14a = a.ENABLE;
    private static boolean c = false;
    private static long d = 0;
    private static boolean e = false;
    private static int f = 0;
    private static int g = 0;

    enum a {
        ENABLE,
        PRE_DISABLE,
        DISABLE
    }

    static synchronized String a(n nVar) {
        String str = null;
        synchronized (s.class) {
            if (nVar == n.QUERY_HOST || nVar == n.SNIFF_HOST) {
                if (a == a.ENABLE || a == a.PRE_DISABLE) {
                    str = d.b[f];
                } else if (nVar != n.QUERY_HOST) {
                    str = d.b[f];
                }
            } else if (nVar == n.QUERY_SCHEDULE_CENTER || nVar == n.SNIFF_SCHEDULE_CENTER) {
            }
        }
        return str;
    }

    static synchronized void a(Context context) {
        synchronized (s.class) {
            if (!c) {
                synchronized (s.class) {
                    if (!c) {
                        if (context != null) {
                            a = context.getSharedPreferences("httpdns_config_cache", 0);
                        }
                        e = a.getBoolean(NotificationCompat.CATEGORY_STATUS, false);
                        f = a.getInt("activiate_ip_index", 0);
                        g = f;
                        d = a.getLong("disable_modified_time", 0);
                        if (System.currentTimeMillis() - d >= 86400000) {
                            b(false);
                        }
                        if (e) {
                            a = a.DISABLE;
                        } else {
                            a = a.ENABLE;
                        }
                        c = true;
                    }
                }
            }
        }
    }

    static synchronized void a(String str, String str2) {
        synchronized (s.class) {
            if (!(a == a.ENABLE || str2 == null || !str2.equals(d.b[f]))) {
                g.f((a == a.DISABLE ? "Disable " : "Pre_disable ") + "mode finished. Enter enable mode.");
                a = a.ENABLE;
                b(false);
                q.a().e();
                g = f;
            }
        }
    }

    static synchronized void a(String str, String str2, Throwable th) {
        synchronized (s.class) {
            if (a(th) && str2 != null && str2.equals(d.b[f])) {
                f();
                if (g == f) {
                    q.a().a(false);
                    o.a().c();
                }
                if (a == a.ENABLE) {
                    a = a.PRE_DISABLE;
                    g.f("enter pre_disable mode");
                } else if (a == a.PRE_DISABLE) {
                    a = a.DISABLE;
                    g.f("enter disable mode");
                    b(true);
                    q.a().g(str);
                }
            }
        }
    }

    private static boolean a(Throwable th) {
        if (th instanceof SocketTimeoutException) {
            return true;
        }
        if (!(th instanceof f)) {
            return false;
        }
        f fVar = (f) th;
        return fVar.getErrorCode() == 403 && fVar.getMessage().equals("ServiceLevelDeny");
    }

    static void b(int i) {
        if (a != null && i >= 0 && i < d.b.length) {
            f = i;
            Editor edit = a.edit();
            edit.putInt("activiate_ip_index", i);
            edit.putLong("activiated_ip_index_modified_time", System.currentTimeMillis());
            edit.commit();
        }
    }

    static synchronized void b(boolean z) {
        synchronized (s.class) {
            if (e != z) {
                e = z;
                if (a != null) {
                    Editor edit = a.edit();
                    edit.putBoolean(NotificationCompat.CATEGORY_STATUS, e);
                    edit.putLong("disable_modified_time", System.currentTimeMillis());
                    edit.commit();
                }
            }
        }
    }

    static synchronized boolean d() {
        boolean z;
        synchronized (s.class) {
            z = e;
        }
        return z;
    }

    private static void f() {
        if (f == d.b.length - 1) {
            f = 0;
        } else {
            f++;
        }
        b(f);
    }

    static synchronized void g() {
        synchronized (s.class) {
            b(0);
            g = f;
            q.a().a(true);
        }
    }

    static synchronized void h() {
        synchronized (s.class) {
            q.a().a(true);
        }
    }
}
