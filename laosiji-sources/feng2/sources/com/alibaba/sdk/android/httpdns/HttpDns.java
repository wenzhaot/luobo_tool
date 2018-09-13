package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import com.alibaba.sdk.android.httpdns.a.b;
import com.alibaba.sdk.android.utils.AMSDevReporter;
import com.alibaba.sdk.android.utils.AMSDevReporter.AMSSdkExtInfoKeyEnum;
import com.alibaba.sdk.android.utils.AMSDevReporter.AMSSdkTypeEnum;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpDns implements HttpDnsService {
    private static DegradationFilter degradationFilter = null;
    private static b hostManager = b.a();
    static HttpDns instance = null;
    private static boolean isEnabled = true;
    private static ExecutorService pool = Executors.newFixedThreadPool(3, new h());
    private boolean isExpiredIPEnabled = false;

    private HttpDns() {
    }

    private String getIpByHost(String str) {
        if (isEnabled) {
            String[] ipsByHost = getIpsByHost(str);
            return ipsByHost.length > 0 ? ipsByHost[0] : null;
        } else {
            g.f("HttpDns service turned off");
            return null;
        }
    }

    private String[] getIpsByHost(String str) {
        if (!isEnabled) {
            g.f("HttpDns service turned off");
            return d.d;
        } else if (!j.b(str)) {
            return d.d;
        } else {
            if (j.c(str)) {
                return new String[]{str};
            } else if (degradationFilter != null && degradationFilter.shouldDegradeHttpDNS(str)) {
                return d.d;
            } else {
                if (s.d()) {
                    return getIpsByHostAsync(str);
                }
                c a = hostManager.a(str);
                if (a != null && a.b() && this.isExpiredIPEnabled) {
                    if (!hostManager.a(str)) {
                        g.e("refresh host async: " + str);
                        pool.submit(new l(str, n.QUERY_HOST));
                    }
                    return a.a();
                } else if (a != null && !a.b()) {
                    return a.a();
                } else {
                    g.e("refresh host sync: " + str);
                    try {
                        return (String[]) pool.submit(new l(str, n.QUERY_HOST)).get();
                    } catch (Throwable e) {
                        g.a(e);
                        return d.d;
                    }
                }
            }
        }
    }

    public static HttpDnsService getService(Context context, String str) {
        if (instance == null) {
            synchronized (HttpDns.class) {
                if (instance == null) {
                    Map hashMap = new HashMap();
                    hashMap.put(AMSSdkExtInfoKeyEnum.AMS_EXTINFO_KEY_VERSION.toString(), "1.1.3.1");
                    AMSDevReporter.asyncReport(context, AMSSdkTypeEnum.AMS_HTTPDNS, hashMap);
                    k.setContext(context);
                    l.setContext(context);
                    b.a(context);
                    b.b(context);
                    s.a(context);
                    d.d(str);
                    o.a().a(context);
                    instance = new HttpDns();
                }
            }
        }
        return instance;
    }

    public static HttpDnsService getService(Context context, String str, String str2) {
        if (instance == null) {
            synchronized (HttpDns.class) {
                if (instance == null) {
                    Map hashMap = new HashMap();
                    hashMap.put(AMSSdkExtInfoKeyEnum.AMS_EXTINFO_KEY_VERSION.toString(), "1.1.3.1");
                    AMSDevReporter.asyncReport(context, AMSSdkTypeEnum.AMS_HTTPDNS, hashMap);
                    k.setContext(context);
                    l.setContext(context);
                    b.a(context);
                    b.b(context);
                    s.a(context);
                    d.d(str);
                    o.a().a(context);
                    a.a(str2);
                    instance = new HttpDns();
                }
            }
        }
        return instance;
    }

    static synchronized void switchDnsService(boolean z) {
        synchronized (HttpDns.class) {
            isEnabled = z;
            if (!isEnabled) {
                g.f("httpdns service disabled");
            }
        }
    }

    public String getIpByHostAsync(String str) {
        if (isEnabled) {
            String[] ipsByHostAsync = getIpsByHostAsync(str);
            return ipsByHostAsync.length > 0 ? ipsByHostAsync[0] : null;
        } else {
            g.f("HttpDns service turned off");
            return null;
        }
    }

    /* JADX WARNING: Missing block: B:17:0x003f, code:
            if (r0 != 0) goto L_0x0041;
     */
    public java.lang.String[] getIpsByHostAsync(java.lang.String r5) {
        /*
        r4 = this;
        r0 = 0;
        r1 = isEnabled;
        if (r1 != 0) goto L_0x000e;
    L_0x0005:
        r0 = "HttpDns service turned off";
        com.alibaba.sdk.android.httpdns.g.f(r0);
        r0 = com.alibaba.sdk.android.httpdns.d.d;
    L_0x000d:
        return r0;
    L_0x000e:
        r1 = com.alibaba.sdk.android.httpdns.j.b(r5);
        if (r1 != 0) goto L_0x0017;
    L_0x0014:
        r0 = com.alibaba.sdk.android.httpdns.d.d;
        goto L_0x000d;
    L_0x0017:
        r1 = com.alibaba.sdk.android.httpdns.j.c(r5);
        if (r1 == 0) goto L_0x0024;
    L_0x001d:
        r1 = 1;
        r1 = new java.lang.String[r1];
        r1[r0] = r5;
        r0 = r1;
        goto L_0x000d;
    L_0x0024:
        r1 = degradationFilter;
        if (r1 == 0) goto L_0x0033;
    L_0x0028:
        r1 = degradationFilter;
        r1 = r1.shouldDegradeHttpDNS(r5);
        if (r1 == 0) goto L_0x0033;
    L_0x0030:
        r0 = com.alibaba.sdk.android.httpdns.d.d;
        goto L_0x000d;
    L_0x0033:
        r1 = hostManager;
        r1 = r1.a(r5);
        if (r1 == 0) goto L_0x0041;
    L_0x003b:
        r0 = r1.b();
        if (r0 == 0) goto L_0x0056;
    L_0x0041:
        r2 = hostManager;
        r2 = r2.a(r5);
        if (r2 != 0) goto L_0x0056;
    L_0x0049:
        r2 = com.alibaba.sdk.android.httpdns.s.d();
        if (r2 == 0) goto L_0x005b;
    L_0x004f:
        r2 = com.alibaba.sdk.android.httpdns.q.a();
        r2.g(r5);
    L_0x0056:
        if (r1 != 0) goto L_0x007f;
    L_0x0058:
        r0 = com.alibaba.sdk.android.httpdns.d.d;
        goto L_0x000d;
    L_0x005b:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "refresh host async: ";
        r2 = r2.append(r3);
        r2 = r2.append(r5);
        r2 = r2.toString();
        com.alibaba.sdk.android.httpdns.g.e(r2);
        r2 = new com.alibaba.sdk.android.httpdns.l;
        r3 = com.alibaba.sdk.android.httpdns.n.QUERY_HOST;
        r2.<init>(r5, r3);
        r3 = pool;
        r3.submit(r2);
        goto L_0x0056;
    L_0x007f:
        r2 = com.alibaba.sdk.android.httpdns.s.d();
        if (r2 == 0) goto L_0x0088;
    L_0x0085:
        r0 = com.alibaba.sdk.android.httpdns.d.d;
        goto L_0x000d;
    L_0x0088:
        r2 = r4.isExpiredIPEnabled;
        if (r2 == 0) goto L_0x0092;
    L_0x008c:
        r0 = r1.a();
        goto L_0x000d;
    L_0x0092:
        if (r0 != 0) goto L_0x009a;
    L_0x0094:
        r0 = r1.a();
        goto L_0x000d;
    L_0x009a:
        r0 = com.alibaba.sdk.android.httpdns.d.d;
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.HttpDns.getIpsByHostAsync(java.lang.String):java.lang.String[]");
    }

    public void setAuthCurrentTime(long j) {
        a.setAuthCurrentTime(j);
    }

    public void setCachedIPEnabled(boolean z) {
        b.c(z);
        b.a().a();
    }

    public void setDegradationFilter(DegradationFilter degradationFilter) {
        degradationFilter = degradationFilter;
    }

    public void setExpiredIPEnabled(boolean z) {
        this.isExpiredIPEnabled = z;
    }

    public void setHTTPSRequestEnabled(boolean z) {
        d.setHTTPSRequestEnabled(z);
    }

    public void setLogEnabled(boolean z) {
        g.setLogEnabled(z);
    }

    public void setPreResolveAfterNetworkChanged(boolean z) {
        k.b = z;
    }

    public void setPreResolveHosts(ArrayList<String> arrayList) {
        if (isEnabled) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < arrayList.size()) {
                    String str = (String) arrayList.get(i2);
                    if (!hostManager.a(str)) {
                        pool.submit(new l(str, n.QUERY_HOST));
                    }
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
        g.f("HttpDns service turned off");
    }

    public void setTimeoutInterval(int i) {
        d.setTimeoutInterval(i);
    }
}
