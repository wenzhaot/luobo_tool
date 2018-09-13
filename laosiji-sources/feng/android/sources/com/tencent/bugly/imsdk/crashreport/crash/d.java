package com.tencent.bugly.imsdk.crashreport.crash;

import android.content.Context;
import com.tencent.bugly.imsdk.crashreport.common.strategy.a;
import com.tencent.bugly.imsdk.proguard.v;
import com.tencent.bugly.imsdk.proguard.w;
import java.util.Map;

/* compiled from: BUGLY */
public final class d {
    private static d a = null;
    private a b;
    private com.tencent.bugly.imsdk.crashreport.common.info.a c;
    private b d;
    private Context e;

    /* JADX WARNING: Removed duplicated region for block: B:66:0x02af A:{Catch:{ Throwable -> 0x02a8, all -> 0x02bc }} */
    /* JADX WARNING: Missing block: B:39:0x0109, code:
            if (r10 != 8) goto L_0x010c;
     */
    /* JADX WARNING: Missing block: B:40:0x010b, code:
            r10 = 5;
     */
    /* JADX WARNING: Missing block: B:42:?, code:
            r5 = new com.tencent.bugly.imsdk.crashreport.crash.CrashDetailBean();
            r5.B = com.tencent.bugly.imsdk.crashreport.common.info.b.g();
            r5.C = com.tencent.bugly.imsdk.crashreport.common.info.b.e();
            r5.D = com.tencent.bugly.imsdk.crashreport.common.info.b.i();
            r5.E = r8.c.p();
            r5.F = r8.c.o();
            r5.G = r8.c.q();
            r5.w = com.tencent.bugly.imsdk.proguard.y.a(r8.e, com.tencent.bugly.imsdk.crashreport.crash.c.d, null);
            r5.b = r10;
            r5.e = r8.c.h();
            r5.f = r8.c.j;
            r5.g = r8.c.w();
            r5.m = r8.c.g();
            r5.n = r11;
            r5.o = r12;
            r1 = "";
     */
    /* JADX WARNING: Missing block: B:43:0x0187, code:
            if (r13 == null) goto L_0x0257;
     */
    /* JADX WARNING: Missing block: B:44:0x0189, code:
            r2 = r13.split("\n");
     */
    /* JADX WARNING: Missing block: B:45:0x0191, code:
            if (r2.length <= 0) goto L_0x0196;
     */
    /* JADX WARNING: Missing block: B:46:0x0193, code:
            r1 = r2[0];
     */
    /* JADX WARNING: Missing block: B:47:0x0196, code:
            r2 = r1;
            r1 = r13;
     */
    /* JADX WARNING: Missing block: B:48:0x0198, code:
            r5.p = r2;
            r5.q = r1;
            r5.r = java.lang.System.currentTimeMillis();
            r5.u = com.tencent.bugly.imsdk.proguard.y.b(r5.q.getBytes());
            r5.y = com.tencent.bugly.imsdk.proguard.y.a(com.tencent.bugly.imsdk.crashreport.crash.c.e, false);
            r5.z = r8.c.d;
            r5.A = r9.getName() + com.umeng.message.proguard.l.s + r9.getId() + com.umeng.message.proguard.l.t;
            r5.H = r8.c.y();
            r5.h = r8.c.v();
            r5.L = r8.c.a;
            r5.M = r8.c.a();
            r5.O = r8.c.F();
            r5.P = r8.c.G();
            r5.Q = r8.c.z();
            r5.R = r8.c.E();
            r8.d.b(r5);
            r5.x = com.tencent.bugly.imsdk.proguard.x.a(false);
     */
    /* JADX WARNING: Missing block: B:49:0x0232, code:
            if (r5.N != null) goto L_0x023b;
     */
    /* JADX WARNING: Missing block: B:50:0x0234, code:
            r5.N = new java.util.LinkedHashMap();
     */
    /* JADX WARNING: Missing block: B:51:0x023b, code:
            if (r14 == null) goto L_0x0242;
     */
    /* JADX WARNING: Missing block: B:52:0x023d, code:
            r5.N.putAll(r14);
     */
    /* JADX WARNING: Missing block: B:53:0x0242, code:
            if (r5 != null) goto L_0x025f;
     */
    /* JADX WARNING: Missing block: B:54:0x0244, code:
            com.tencent.bugly.imsdk.proguard.w.e("[ExtraCrashManager] Failed to package crash data.", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:55:0x024d, code:
            com.tencent.bugly.imsdk.proguard.w.e("[ExtraCrashManager] Successfully handled.", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:57:?, code:
            r2 = r1;
            r1 = "";
     */
    /* JADX WARNING: Missing block: B:58:0x025f, code:
            com.tencent.bugly.imsdk.crashreport.crash.b.a(r0, com.tencent.bugly.imsdk.proguard.y.a(), r8.c.d, r9, r11 + "\n" + r12 + "\n" + r13, r5);
     */
    /* JADX WARNING: Missing block: B:59:0x0294, code:
            if (r8.d.a(r5) != false) goto L_0x029e;
     */
    /* JADX WARNING: Missing block: B:60:0x0296, code:
            r8.d.a(r5, 3000, false);
     */
    /* JADX WARNING: Missing block: B:61:0x029e, code:
            com.tencent.bugly.imsdk.proguard.w.e("[ExtraCrashManager] Successfully handled.", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:78:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:79:?, code:
            return;
     */
    static /* synthetic */ void a(com.tencent.bugly.imsdk.crashreport.crash.d r8, java.lang.Thread r9, int r10, java.lang.String r11, java.lang.String r12, java.lang.String r13, java.util.Map r14) {
        /*
        r2 = 1;
        r6 = 0;
        switch(r10) {
            case 4: goto L_0x00ac;
            case 5: goto L_0x0014;
            case 6: goto L_0x0014;
            case 7: goto L_0x0005;
            case 8: goto L_0x00b1;
            default: goto L_0x0005;
        };
    L_0x0005:
        r0 = "[ExtraCrashManager] Unknown extra crash type: %d";
        r1 = new java.lang.Object[r2];
        r2 = java.lang.Integer.valueOf(r10);
        r1[r6] = r2;
        com.tencent.bugly.imsdk.proguard.w.d(r0, r1);
    L_0x0013:
        return;
    L_0x0014:
        r0 = "Cocos";
    L_0x0017:
        r1 = "[ExtraCrashManager] %s Crash Happen";
        r2 = new java.lang.Object[r2];
        r2[r6] = r0;
        com.tencent.bugly.imsdk.proguard.w.e(r1, r2);
        r1 = r8.b;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.b();	 Catch:{ Throwable -> 0x02a8 }
        if (r1 != 0) goto L_0x0046;
    L_0x0029:
        r1 = "waiting for remote sync";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x02a8 }
        com.tencent.bugly.imsdk.proguard.w.e(r1, r2);	 Catch:{ Throwable -> 0x02a8 }
        r1 = r6;
    L_0x0033:
        r2 = r8.b;	 Catch:{ Throwable -> 0x02a8 }
        r2 = r2.b();	 Catch:{ Throwable -> 0x02a8 }
        if (r2 != 0) goto L_0x0046;
    L_0x003b:
        r2 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        com.tencent.bugly.imsdk.proguard.y.b(r2);	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1 + 500;
        r2 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        if (r1 < r2) goto L_0x0033;
    L_0x0046:
        r1 = r8.b;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.b();	 Catch:{ Throwable -> 0x02a8 }
        if (r1 != 0) goto L_0x0057;
    L_0x004e:
        r1 = "[ExtraCrashManager] There is no remote strategy, but still store it.";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x02a8 }
        com.tencent.bugly.imsdk.proguard.w.d(r1, r2);	 Catch:{ Throwable -> 0x02a8 }
    L_0x0057:
        r1 = r8.b;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.c();	 Catch:{ Throwable -> 0x02a8 }
        r2 = r1.g;	 Catch:{ Throwable -> 0x02a8 }
        if (r2 != 0) goto L_0x00b6;
    L_0x0061:
        r2 = r8.b;	 Catch:{ Throwable -> 0x02a8 }
        r2 = r2.b();	 Catch:{ Throwable -> 0x02a8 }
        if (r2 == 0) goto L_0x00b6;
    L_0x0069:
        r1 = "[ExtraCrashManager] Crash report was closed by remote , will not upload to Bugly , print local for helpful!";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x02a8 }
        com.tencent.bugly.imsdk.proguard.w.e(r1, r2);	 Catch:{ Throwable -> 0x02a8 }
        r1 = com.tencent.bugly.imsdk.proguard.y.a();	 Catch:{ Throwable -> 0x02a8 }
        r2 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r2 = r2.d;	 Catch:{ Throwable -> 0x02a8 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x02a8 }
        r3.<init>();	 Catch:{ Throwable -> 0x02a8 }
        r3 = r3.append(r11);	 Catch:{ Throwable -> 0x02a8 }
        r4 = "\n";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x02a8 }
        r3 = r3.append(r12);	 Catch:{ Throwable -> 0x02a8 }
        r4 = "\n";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x02a8 }
        r3 = r3.append(r13);	 Catch:{ Throwable -> 0x02a8 }
        r4 = r3.toString();	 Catch:{ Throwable -> 0x02a8 }
        r5 = 0;
        r3 = r9;
        com.tencent.bugly.imsdk.crashreport.crash.b.a(r0, r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x02a8 }
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.imsdk.proguard.w.e(r0, r1);
        goto L_0x0013;
    L_0x00ac:
        r0 = "Unity";
        goto L_0x0017;
    L_0x00b1:
        r0 = "H5";
        goto L_0x0017;
    L_0x00b6:
        switch(r10) {
            case 5: goto L_0x00d3;
            case 6: goto L_0x00d3;
            case 7: goto L_0x00b9;
            case 8: goto L_0x00ed;
            default: goto L_0x00b9;
        };
    L_0x00b9:
        r0 = "[ExtraCrashManager] Unknown extra crash type: %d";
        r1 = 1;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x02a8 }
        r2 = 0;
        r3 = java.lang.Integer.valueOf(r10);	 Catch:{ Throwable -> 0x02a8 }
        r1[r2] = r3;	 Catch:{ Throwable -> 0x02a8 }
        com.tencent.bugly.imsdk.proguard.w.d(r0, r1);	 Catch:{ Throwable -> 0x02a8 }
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.imsdk.proguard.w.e(r0, r1);
        goto L_0x0013;
    L_0x00d3:
        r1 = r1.l;	 Catch:{ Throwable -> 0x02a8 }
        if (r1 != 0) goto L_0x0107;
    L_0x00d7:
        r1 = "[ExtraCrashManager] %s report is disabled.";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x02a8 }
        r3 = 0;
        r2[r3] = r0;	 Catch:{ Throwable -> 0x02a8 }
        com.tencent.bugly.imsdk.proguard.w.e(r1, r2);	 Catch:{ Throwable -> 0x02a8 }
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.imsdk.proguard.w.e(r0, r1);
        goto L_0x0013;
    L_0x00ed:
        r1 = r1.m;	 Catch:{ Throwable -> 0x02a8 }
        if (r1 != 0) goto L_0x0107;
    L_0x00f1:
        r1 = "[ExtraCrashManager] %s report is disabled.";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x02a8 }
        r3 = 0;
        r2[r3] = r0;	 Catch:{ Throwable -> 0x02a8 }
        com.tencent.bugly.imsdk.proguard.w.e(r1, r2);	 Catch:{ Throwable -> 0x02a8 }
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.imsdk.proguard.w.e(r0, r1);
        goto L_0x0013;
    L_0x0107:
        r1 = 8;
        if (r10 != r1) goto L_0x010c;
    L_0x010b:
        r10 = 5;
    L_0x010c:
        r5 = new com.tencent.bugly.imsdk.crashreport.crash.CrashDetailBean;	 Catch:{ Throwable -> 0x02a8 }
        r5.<init>();	 Catch:{ Throwable -> 0x02a8 }
        r2 = com.tencent.bugly.imsdk.crashreport.common.info.b.g();	 Catch:{ Throwable -> 0x02a8 }
        r5.B = r2;	 Catch:{ Throwable -> 0x02a8 }
        r2 = com.tencent.bugly.imsdk.crashreport.common.info.b.e();	 Catch:{ Throwable -> 0x02a8 }
        r5.C = r2;	 Catch:{ Throwable -> 0x02a8 }
        r2 = com.tencent.bugly.imsdk.crashreport.common.info.b.i();	 Catch:{ Throwable -> 0x02a8 }
        r5.D = r2;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r2 = r1.p();	 Catch:{ Throwable -> 0x02a8 }
        r5.E = r2;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r2 = r1.o();	 Catch:{ Throwable -> 0x02a8 }
        r5.F = r2;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r2 = r1.q();	 Catch:{ Throwable -> 0x02a8 }
        r5.G = r2;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.e;	 Catch:{ Throwable -> 0x02a8 }
        r2 = com.tencent.bugly.imsdk.crashreport.crash.c.d;	 Catch:{ Throwable -> 0x02a8 }
        r3 = 0;
        r1 = com.tencent.bugly.imsdk.proguard.y.a(r1, r2, r3);	 Catch:{ Throwable -> 0x02a8 }
        r5.w = r1;	 Catch:{ Throwable -> 0x02a8 }
        r5.b = r10;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.h();	 Catch:{ Throwable -> 0x02a8 }
        r5.e = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.j;	 Catch:{ Throwable -> 0x02a8 }
        r5.f = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.w();	 Catch:{ Throwable -> 0x02a8 }
        r5.g = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.g();	 Catch:{ Throwable -> 0x02a8 }
        r5.m = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x02a8 }
        r1.<init>();	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.append(r11);	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x02a8 }
        r5.n = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x02a8 }
        r1.<init>();	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.append(r12);	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x02a8 }
        r5.o = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = "";
        if (r13 == 0) goto L_0x0257;
    L_0x0189:
        r2 = "\n";
        r2 = r13.split(r2);	 Catch:{ Throwable -> 0x02a8 }
        r3 = r2.length;	 Catch:{ Throwable -> 0x02a8 }
        if (r3 <= 0) goto L_0x0196;
    L_0x0193:
        r1 = 0;
        r1 = r2[r1];	 Catch:{ Throwable -> 0x02a8 }
    L_0x0196:
        r2 = r1;
        r1 = r13;
    L_0x0198:
        r5.p = r2;	 Catch:{ Throwable -> 0x02a8 }
        r5.q = r1;	 Catch:{ Throwable -> 0x02a8 }
        r2 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x02a8 }
        r5.r = r2;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r5.q;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.getBytes();	 Catch:{ Throwable -> 0x02a8 }
        r1 = com.tencent.bugly.imsdk.proguard.y.b(r1);	 Catch:{ Throwable -> 0x02a8 }
        r5.u = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = com.tencent.bugly.imsdk.crashreport.crash.c.e;	 Catch:{ Throwable -> 0x02a8 }
        r2 = 0;
        r1 = com.tencent.bugly.imsdk.proguard.y.a(r1, r2);	 Catch:{ Throwable -> 0x02a8 }
        r5.y = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.d;	 Catch:{ Throwable -> 0x02a8 }
        r5.z = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x02a8 }
        r1.<init>();	 Catch:{ Throwable -> 0x02a8 }
        r2 = r9.getName();	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x02a8 }
        r2 = "(";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x02a8 }
        r2 = r9.getId();	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x02a8 }
        r2 = ")";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x02a8 }
        r5.A = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.y();	 Catch:{ Throwable -> 0x02a8 }
        r5.H = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.v();	 Catch:{ Throwable -> 0x02a8 }
        r5.h = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r2 = r1.a;	 Catch:{ Throwable -> 0x02a8 }
        r5.L = r2;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.a();	 Catch:{ Throwable -> 0x02a8 }
        r5.M = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.F();	 Catch:{ Throwable -> 0x02a8 }
        r5.O = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.G();	 Catch:{ Throwable -> 0x02a8 }
        r5.P = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.z();	 Catch:{ Throwable -> 0x02a8 }
        r5.Q = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r1.E();	 Catch:{ Throwable -> 0x02a8 }
        r5.R = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r8.d;	 Catch:{ Throwable -> 0x02a8 }
        r1.b(r5);	 Catch:{ Throwable -> 0x02a8 }
        r1 = 0;
        r1 = com.tencent.bugly.imsdk.proguard.x.a(r1);	 Catch:{ Throwable -> 0x02a8 }
        r5.x = r1;	 Catch:{ Throwable -> 0x02a8 }
        r1 = r5.N;	 Catch:{ Throwable -> 0x02a8 }
        if (r1 != 0) goto L_0x023b;
    L_0x0234:
        r1 = new java.util.LinkedHashMap;	 Catch:{ Throwable -> 0x02a8 }
        r1.<init>();	 Catch:{ Throwable -> 0x02a8 }
        r5.N = r1;	 Catch:{ Throwable -> 0x02a8 }
    L_0x023b:
        if (r14 == 0) goto L_0x0242;
    L_0x023d:
        r1 = r5.N;	 Catch:{ Throwable -> 0x02a8 }
        r1.putAll(r14);	 Catch:{ Throwable -> 0x02a8 }
    L_0x0242:
        if (r5 != 0) goto L_0x025f;
    L_0x0244:
        r0 = "[ExtraCrashManager] Failed to package crash data.";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x02a8 }
        com.tencent.bugly.imsdk.proguard.w.e(r0, r1);	 Catch:{ Throwable -> 0x02a8 }
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.imsdk.proguard.w.e(r0, r1);
        goto L_0x0013;
    L_0x0257:
        r2 = "";
        r7 = r2;
        r2 = r1;
        r1 = r7;
        goto L_0x0198;
    L_0x025f:
        r1 = com.tencent.bugly.imsdk.proguard.y.a();	 Catch:{ Throwable -> 0x02a8 }
        r2 = r8.c;	 Catch:{ Throwable -> 0x02a8 }
        r2 = r2.d;	 Catch:{ Throwable -> 0x02a8 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x02a8 }
        r3.<init>();	 Catch:{ Throwable -> 0x02a8 }
        r3 = r3.append(r11);	 Catch:{ Throwable -> 0x02a8 }
        r4 = "\n";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x02a8 }
        r3 = r3.append(r12);	 Catch:{ Throwable -> 0x02a8 }
        r4 = "\n";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x02a8 }
        r3 = r3.append(r13);	 Catch:{ Throwable -> 0x02a8 }
        r4 = r3.toString();	 Catch:{ Throwable -> 0x02a8 }
        r3 = r9;
        com.tencent.bugly.imsdk.crashreport.crash.b.a(r0, r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x02a8 }
        r0 = r8.d;	 Catch:{ Throwable -> 0x02a8 }
        r0 = r0.a(r5);	 Catch:{ Throwable -> 0x02a8 }
        if (r0 != 0) goto L_0x029e;
    L_0x0296:
        r0 = r8.d;	 Catch:{ Throwable -> 0x02a8 }
        r2 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        r1 = 0;
        r0.a(r5, r2, r1);	 Catch:{ Throwable -> 0x02a8 }
    L_0x029e:
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.imsdk.proguard.w.e(r0, r1);
        goto L_0x0013;
    L_0x02a8:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);	 Catch:{ all -> 0x02bc }
        if (r1 != 0) goto L_0x02b2;
    L_0x02af:
        r0.printStackTrace();	 Catch:{ all -> 0x02bc }
    L_0x02b2:
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.imsdk.proguard.w.e(r0, r1);
        goto L_0x0013;
    L_0x02bc:
        r0 = move-exception;
        r1 = "[ExtraCrashManager] Successfully handled.";
        r2 = new java.lang.Object[r6];
        com.tencent.bugly.imsdk.proguard.w.e(r1, r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.crash.d.a(com.tencent.bugly.imsdk.crashreport.crash.d, java.lang.Thread, int, java.lang.String, java.lang.String, java.lang.String, java.util.Map):void");
    }

    private d(Context context) {
        c a = c.a();
        if (a != null) {
            this.b = a.a();
            this.c = com.tencent.bugly.imsdk.crashreport.common.info.a.a(context);
            this.d = a.n;
            this.e = context;
            v.a().a(new Runnable() {
                public final void run() {
                    d.a(d.this);
                }
            });
        }
    }

    public static d a(Context context) {
        if (a == null) {
            a = new d(context);
        }
        return a;
    }

    public static void a(Thread thread, int i, String str, String str2, String str3, Map<String, String> map) {
        final Thread thread2 = thread;
        final int i2 = i;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final Map<String, String> map2 = map;
        v.a().a(new Runnable() {
            public final void run() {
                try {
                    if (d.a == null) {
                        w.e("[ExtraCrashManager] Extra crash manager has not been initialized.", new Object[0]);
                    } else {
                        d.a(d.a, thread2, i2, str4, str5, str6, map2);
                    }
                } catch (Throwable th) {
                    if (!w.b(th)) {
                        th.printStackTrace();
                    }
                    w.e("[ExtraCrashManager] Crash error %s %s %s", str4, str5, str6);
                }
            }
        });
    }
}
