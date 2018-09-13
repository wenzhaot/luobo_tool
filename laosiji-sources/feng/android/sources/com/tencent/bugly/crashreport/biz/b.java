package com.tencent.bugly.crashreport.biz;

import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import com.stub.StubApp;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;

/* compiled from: BUGLY */
public class b {
    public static a a;
    private static boolean b;
    private static int c = 10;
    private static long d = 300000;
    private static long e = 30000;
    private static long f = 0;
    private static int g;
    private static long h;
    private static long i;
    private static long j = 0;
    private static ActivityLifecycleCallbacks k = null;
    private static Class<?> l = null;
    private static boolean m = true;

    static /* synthetic */ int g() {
        int i = g;
        g = i + 1;
        return i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:73:? A:{SYNTHETIC, RETURN, ORIG_RETURN} */
    private static void c(android.content.Context r11, com.tencent.bugly.BuglyStrategy r12) {
        /*
        r1 = 1;
        r0 = 0;
        if (r12 == 0) goto L_0x012c;
    L_0x0004:
        r0 = r12.recordUserInfoOnceADay();
        r1 = r12.isEnableUserInfo();
        r10 = r0;
        r0 = r1;
        r1 = r10;
    L_0x000f:
        if (r1 == 0) goto L_0x006f;
    L_0x0011:
        r2 = com.tencent.bugly.crashreport.common.info.a.a(r11);
        r0 = r2.d;
        r1 = a;
        r3 = r1.a(r0);
        if (r3 == 0) goto L_0x006c;
    L_0x001f:
        r0 = 0;
        r1 = r0;
    L_0x0021:
        r0 = r3.size();
        if (r1 >= r0) goto L_0x006c;
    L_0x0027:
        r0 = r3.get(r1);
        r0 = (com.tencent.bugly.crashreport.biz.UserInfoBean) r0;
        r4 = r0.n;
        r5 = r2.j;
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x0068;
    L_0x0037:
        r4 = r0.b;
        r5 = 1;
        if (r4 != r5) goto L_0x0068;
    L_0x003c:
        r4 = com.tencent.bugly.proguard.z.b();
        r6 = 0;
        r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r6 <= 0) goto L_0x006c;
    L_0x0046:
        r6 = r0.e;
        r4 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
        if (r4 < 0) goto L_0x0068;
    L_0x004c:
        r0 = r0.f;
        r2 = 0;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 > 0) goto L_0x0064;
    L_0x0054:
        r0 = a;
        r1 = com.tencent.bugly.proguard.w.a();
        if (r1 == 0) goto L_0x0064;
    L_0x005c:
        r2 = new com.tencent.bugly.crashreport.biz.a$2;
        r2.<init>();
        r1.a(r2);
    L_0x0064:
        r0 = 0;
    L_0x0065:
        if (r0 != 0) goto L_0x006e;
    L_0x0067:
        return;
    L_0x0068:
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x0021;
    L_0x006c:
        r0 = 1;
        goto L_0x0065;
    L_0x006e:
        r0 = 0;
    L_0x006f:
        r4 = com.tencent.bugly.crashreport.common.info.a.b();
        if (r4 == 0) goto L_0x00b4;
    L_0x0075:
        r3 = 0;
        r2 = 0;
        r1 = java.lang.Thread.currentThread();
        r5 = r1.getStackTrace();
        r6 = r5.length;
        r1 = 0;
        r10 = r1;
        r1 = r3;
        r3 = r10;
    L_0x0084:
        if (r3 >= r6) goto L_0x00aa;
    L_0x0086:
        r7 = r5[r3];
        r8 = r7.getMethodName();
        r9 = "onCreate";
        r8 = r8.equals(r9);
        if (r8 == 0) goto L_0x0099;
    L_0x0095:
        r1 = r7.getClassName();
    L_0x0099:
        r7 = r7.getClassName();
        r8 = "android.app.Activity";
        r7 = r7.equals(r8);
        if (r7 == 0) goto L_0x00a7;
    L_0x00a6:
        r2 = 1;
    L_0x00a7:
        r3 = r3 + 1;
        goto L_0x0084;
    L_0x00aa:
        if (r1 == 0) goto L_0x011d;
    L_0x00ac:
        if (r2 == 0) goto L_0x0119;
    L_0x00ae:
        r2 = 1;
        r4.a(r2);
    L_0x00b2:
        r4.p = r1;
    L_0x00b4:
        if (r0 == 0) goto L_0x00e5;
    L_0x00b6:
        r0 = 0;
        r1 = android.os.Build.VERSION.SDK_INT;
        r2 = 14;
        if (r1 < r2) goto L_0x00e5;
    L_0x00bd:
        r1 = r11.getApplicationContext();
        r1 = com.stub.StubApp.getOrigApplicationContext(r1);
        r1 = r1 instanceof android.app.Application;
        if (r1 == 0) goto L_0x00d3;
    L_0x00c9:
        r0 = r11.getApplicationContext();
        r0 = com.stub.StubApp.getOrigApplicationContext(r0);
        r0 = (android.app.Application) r0;
    L_0x00d3:
        if (r0 == 0) goto L_0x00e5;
    L_0x00d5:
        r1 = k;	 Catch:{ Exception -> 0x0121 }
        if (r1 != 0) goto L_0x00e0;
    L_0x00d9:
        r1 = new com.tencent.bugly.crashreport.biz.b$2;	 Catch:{ Exception -> 0x0121 }
        r1.<init>();	 Catch:{ Exception -> 0x0121 }
        k = r1;	 Catch:{ Exception -> 0x0121 }
    L_0x00e0:
        r1 = k;	 Catch:{ Exception -> 0x0121 }
        r0.registerActivityLifecycleCallbacks(r1);	 Catch:{ Exception -> 0x0121 }
    L_0x00e5:
        r0 = m;
        if (r0 == 0) goto L_0x0067;
    L_0x00e9:
        r0 = java.lang.System.currentTimeMillis();
        i = r0;
        r0 = a;
        r1 = 1;
        r2 = 0;
        r4 = 0;
        r0.a(r1, r2, r4);
        r0 = "[session] launch app, new start";
        r1 = 0;
        r1 = new java.lang.Object[r1];
        com.tencent.bugly.proguard.x.a(r0, r1);
        r0 = a;
        r0.a();
        r0 = a;
        r2 = 21600000; // 0x1499700 float:3.7026207E-38 double:1.0671818E-316;
        r1 = com.tencent.bugly.proguard.w.a();
        r4 = new com.tencent.bugly.crashreport.biz.a$c;
        r4.<init>(r2);
        r1.a(r4, r2);
        goto L_0x0067;
    L_0x0119:
        r1 = "background";
        goto L_0x00b2;
    L_0x011d:
        r1 = "unknown";
        goto L_0x00b2;
    L_0x0121:
        r0 = move-exception;
        r1 = com.tencent.bugly.proguard.x.a(r0);
        if (r1 != 0) goto L_0x00e5;
    L_0x0128:
        r0.printStackTrace();
        goto L_0x00e5;
    L_0x012c:
        r10 = r0;
        r0 = r1;
        r1 = r10;
        goto L_0x000f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.biz.b.c(android.content.Context, com.tencent.bugly.BuglyStrategy):void");
    }

    public static void a(final Context context, final BuglyStrategy buglyStrategy) {
        if (!b) {
            long appReportDelay;
            m = a.a(context).e;
            a = new a(context, m);
            b = true;
            if (buglyStrategy != null) {
                l = buglyStrategy.getUserInfoActivity();
                appReportDelay = buglyStrategy.getAppReportDelay();
            } else {
                appReportDelay = 0;
            }
            if (appReportDelay <= 0) {
                c(context, buglyStrategy);
            } else {
                w.a().a(new Runnable() {
                    public final void run() {
                        b.c(context, buglyStrategy);
                    }
                }, appReportDelay);
            }
        }
    }

    public static void a(long j) {
        if (j < 0) {
            j = com.tencent.bugly.crashreport.common.strategy.a.a().c().q;
        }
        f = j;
    }

    public static void a(StrategyBean strategyBean, boolean z) {
        if (!(a == null || z)) {
            a aVar = a;
            w a = w.a();
            if (a != null) {
                a.a(/* anonymous class already generated */);
            }
        }
        if (strategyBean != null) {
            if (strategyBean.q > 0) {
                e = strategyBean.q;
            }
            if (strategyBean.w > 0) {
                c = strategyBean.w;
            }
            if (strategyBean.x > 0) {
                d = strategyBean.x;
            }
        }
    }

    public static void a() {
        if (a != null) {
            a.a(2, false, 0);
        }
    }

    public static void a(Context context) {
        if (b && context != null) {
            Application application = null;
            if (VERSION.SDK_INT >= 14) {
                if (StubApp.getOrigApplicationContext(context.getApplicationContext()) instanceof Application) {
                    application = (Application) StubApp.getOrigApplicationContext(context.getApplicationContext());
                }
                if (application != null) {
                    try {
                        if (k != null) {
                            application.unregisterActivityLifecycleCallbacks(k);
                        }
                    } catch (Throwable e) {
                        if (!x.a(e)) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            b = false;
        }
    }
}
