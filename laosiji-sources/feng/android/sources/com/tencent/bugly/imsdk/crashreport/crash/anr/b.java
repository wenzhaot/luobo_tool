package com.tencent.bugly.imsdk.crashreport.crash.anr;

import android.content.Context;
import android.os.FileObserver;
import com.tencent.bugly.imsdk.crashreport.common.info.a;
import com.tencent.bugly.imsdk.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.imsdk.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.imsdk.crashreport.crash.c;
import com.tencent.bugly.imsdk.proguard.v;
import com.tencent.bugly.imsdk.proguard.w;
import com.tencent.bugly.imsdk.proguard.x;
import com.tencent.bugly.imsdk.proguard.y;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.android.agoo.common.AgooConstants;

/* compiled from: BUGLY */
public final class b {
    private AtomicInteger a = new AtomicInteger(0);
    private long b = -1;
    private final Context c;
    private final a d;
    private final v e;
    private final com.tencent.bugly.imsdk.crashreport.common.strategy.a f;
    private final String g;
    private final com.tencent.bugly.imsdk.crashreport.crash.b h;
    private FileObserver i;
    private boolean j = true;

    /* compiled from: BUGLY */
    /* renamed from: com.tencent.bugly.imsdk.crashreport.crash.anr.b$1 */
    class AnonymousClass1 extends FileObserver {
        AnonymousClass1(String str, int i) {
            super(str, 8);
        }

        public final void onEvent(int i, String str) {
            if (str != null) {
                String str2 = "/data/anr/" + str;
                if (str2.contains(AgooConstants.MESSAGE_TRACE)) {
                    b.this.a(str2);
                    return;
                }
                w.d("not anr file %s", str2);
            }
        }
    }

    public b(Context context, com.tencent.bugly.imsdk.crashreport.common.strategy.a aVar, a aVar2, v vVar, com.tencent.bugly.imsdk.crashreport.crash.b bVar) {
        this.c = y.a(context);
        this.g = context.getDir("bugly", 0).getAbsolutePath();
        this.d = aVar2;
        this.e = vVar;
        this.f = aVar;
        this.h = bVar;
    }

    private CrashDetailBean a(a aVar) {
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        try {
            crashDetailBean.B = com.tencent.bugly.imsdk.crashreport.common.info.b.g();
            crashDetailBean.C = com.tencent.bugly.imsdk.crashreport.common.info.b.e();
            crashDetailBean.D = com.tencent.bugly.imsdk.crashreport.common.info.b.i();
            crashDetailBean.E = this.d.p();
            crashDetailBean.F = this.d.o();
            crashDetailBean.G = this.d.q();
            crashDetailBean.w = y.a(this.c, c.d, null);
            crashDetailBean.x = x.a(true);
            crashDetailBean.b = 3;
            crashDetailBean.e = this.d.h();
            crashDetailBean.f = this.d.j;
            crashDetailBean.g = this.d.w();
            crashDetailBean.m = this.d.g();
            crashDetailBean.n = "ANR_EXCEPTION";
            crashDetailBean.o = aVar.f;
            crashDetailBean.q = aVar.g;
            crashDetailBean.N = new HashMap();
            crashDetailBean.N.put("BUGLY_CR_01", aVar.e);
            int i = -1;
            if (crashDetailBean.q != null) {
                i = crashDetailBean.q.indexOf("\n");
            }
            crashDetailBean.p = i > 0 ? crashDetailBean.q.substring(0, i) : "GET_FAIL";
            crashDetailBean.r = aVar.c;
            if (crashDetailBean.q != null) {
                crashDetailBean.u = y.b(crashDetailBean.q.getBytes());
            }
            crashDetailBean.y = aVar.b;
            crashDetailBean.z = this.d.d;
            crashDetailBean.A = "main(1)";
            crashDetailBean.H = this.d.y();
            crashDetailBean.h = this.d.v();
            crashDetailBean.i = this.d.I();
            crashDetailBean.v = aVar.d;
            crashDetailBean.K = this.d.n;
            crashDetailBean.L = this.d.a;
            crashDetailBean.M = this.d.a();
            crashDetailBean.O = this.d.F();
            crashDetailBean.P = this.d.G();
            crashDetailBean.Q = this.d.z();
            crashDetailBean.R = this.d.E();
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
        }
        return crashDetailBean;
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x018c A:{Catch:{ all -> 0x01f8 }} */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x01c1 A:{SYNTHETIC, Splitter: B:53:0x01c1} */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x01e7 A:{SYNTHETIC, Splitter: B:70:0x01e7} */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x01e7 A:{SYNTHETIC, Splitter: B:70:0x01e7} */
    private static boolean a(java.lang.String r12, java.lang.String r13, java.lang.String r14) {
        /*
        r11 = 3;
        r5 = 2;
        r3 = 1;
        r4 = 0;
        r6 = com.tencent.bugly.imsdk.crashreport.crash.anr.TraceFileHelper.readTargetDumpInfo(r14, r12, r3);
        if (r6 == 0) goto L_0x0016;
    L_0x000a:
        r1 = r6.d;
        if (r1 == 0) goto L_0x0016;
    L_0x000e:
        r1 = r6.d;
        r1 = r1.size();
        if (r1 > 0) goto L_0x0022;
    L_0x0016:
        r1 = "not found trace dump for %s";
        r2 = new java.lang.Object[r3];
        r2[r4] = r14;
        com.tencent.bugly.imsdk.proguard.w.e(r1, r2);
        r1 = r4;
    L_0x0021:
        return r1;
    L_0x0022:
        r1 = new java.io.File;
        r1.<init>(r13);
        r2 = r1.exists();	 Catch:{ Exception -> 0x0059 }
        if (r2 != 0) goto L_0x0041;
    L_0x002d:
        r2 = r1.getParentFile();	 Catch:{ Exception -> 0x0059 }
        r2 = r2.exists();	 Catch:{ Exception -> 0x0059 }
        if (r2 != 0) goto L_0x003e;
    L_0x0037:
        r2 = r1.getParentFile();	 Catch:{ Exception -> 0x0059 }
        r2.mkdirs();	 Catch:{ Exception -> 0x0059 }
    L_0x003e:
        r1.createNewFile();	 Catch:{ Exception -> 0x0059 }
    L_0x0041:
        r2 = r1.exists();
        if (r2 == 0) goto L_0x004d;
    L_0x0047:
        r2 = r1.canWrite();
        if (r2 != 0) goto L_0x0095;
    L_0x004d:
        r1 = "backup file create fail %s";
        r2 = new java.lang.Object[r3];
        r2[r4] = r13;
        com.tencent.bugly.imsdk.proguard.w.e(r1, r2);
        r1 = r4;
        goto L_0x0021;
    L_0x0059:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x0063;
    L_0x0060:
        r1.printStackTrace();
    L_0x0063:
        r2 = "backup file create error! %s  %s";
        r5 = new java.lang.Object[r5];
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = r1.getClass();
        r7 = r7.getName();
        r6 = r6.append(r7);
        r7 = ":";
        r6 = r6.append(r7);
        r1 = r1.getMessage();
        r1 = r6.append(r1);
        r1 = r1.toString();
        r5[r4] = r1;
        r5[r3] = r13;
        com.tencent.bugly.imsdk.proguard.w.e(r2, r5);
        r1 = r4;
        goto L_0x0021;
    L_0x0095:
        r2 = 0;
        r5 = new java.io.BufferedWriter;	 Catch:{ IOException -> 0x01fb, all -> 0x01e3 }
        r7 = new java.io.FileWriter;	 Catch:{ IOException -> 0x01fb, all -> 0x01e3 }
        r8 = 0;
        r7.<init>(r1, r8);	 Catch:{ IOException -> 0x01fb, all -> 0x01e3 }
        r5.<init>(r7);	 Catch:{ IOException -> 0x01fb, all -> 0x01e3 }
        r1 = r6.d;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r2 = "main";
        r1 = r1.get(r2);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = (java.lang.String[]) r1;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        if (r1 == 0) goto L_0x00ed;
    L_0x00ae:
        r2 = r1.length;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        if (r2 < r11) goto L_0x00ed;
    L_0x00b1:
        r2 = 0;
        r2 = r1[r2];	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r7 = 1;
        r7 = r1[r7];	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r8 = 2;
        r1 = r1[r8];	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r8 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r9 = "\"main\" tid=";
        r8.<init>(r9);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r8.append(r1);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r8 = " :\n";
        r1 = r1.append(r8);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r1.append(r2);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r2 = "\n";
        r1 = r1.append(r2);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r1.append(r7);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r2 = "\n\n";
        r1 = r1.append(r2);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r1.toString();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r5.write(r1);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r5.flush();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
    L_0x00ed:
        r1 = r6.d;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r1.entrySet();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r6 = r1.iterator();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
    L_0x00f7:
        r1 = r6.hasNext();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        if (r1 == 0) goto L_0x01c7;
    L_0x00fd:
        r1 = r6.next();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r0 = r1;
        r0 = (java.util.Map.Entry) r0;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r2 = r0;
        r1 = r2.getKey();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = (java.lang.String) r1;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r7 = "main";
        r1 = r1.equals(r7);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        if (r1 != 0) goto L_0x00f7;
    L_0x0114:
        r1 = r2.getValue();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        if (r1 == 0) goto L_0x00f7;
    L_0x011a:
        r1 = r2.getValue();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = (java.lang.String[]) r1;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r1.length;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        if (r1 < r11) goto L_0x00f7;
    L_0x0123:
        r1 = r2.getValue();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = (java.lang.String[]) r1;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r7 = 0;
        r7 = r1[r7];	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r2.getValue();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = (java.lang.String[]) r1;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r8 = 1;
        r8 = r1[r8];	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r2.getValue();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = (java.lang.String[]) r1;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r9 = 2;
        r9 = r1[r9];	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = "\"";
        r10.<init>(r1);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r2.getKey();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = (java.lang.String) r1;	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r10.append(r1);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r2 = "\" tid=";
        r1 = r1.append(r2);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r1.append(r9);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r2 = " :\n";
        r1 = r1.append(r2);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r1.append(r7);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r2 = "\n";
        r1 = r1.append(r2);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r1.append(r8);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r2 = "\n\n";
        r1 = r1.append(r2);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r1 = r1.toString();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r5.write(r1);	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        r5.flush();	 Catch:{ IOException -> 0x0184, all -> 0x01f6 }
        goto L_0x00f7;
    L_0x0184:
        r1 = move-exception;
        r2 = r5;
    L_0x0186:
        r3 = com.tencent.bugly.imsdk.proguard.w.a(r1);	 Catch:{ all -> 0x01f8 }
        if (r3 != 0) goto L_0x018f;
    L_0x018c:
        r1.printStackTrace();	 Catch:{ all -> 0x01f8 }
    L_0x018f:
        r3 = "dump trace fail %s";
        r5 = 1;
        r5 = new java.lang.Object[r5];	 Catch:{ all -> 0x01f8 }
        r6 = 0;
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01f8 }
        r7.<init>();	 Catch:{ all -> 0x01f8 }
        r8 = r1.getClass();	 Catch:{ all -> 0x01f8 }
        r8 = r8.getName();	 Catch:{ all -> 0x01f8 }
        r7 = r7.append(r8);	 Catch:{ all -> 0x01f8 }
        r8 = ":";
        r7 = r7.append(r8);	 Catch:{ all -> 0x01f8 }
        r1 = r1.getMessage();	 Catch:{ all -> 0x01f8 }
        r1 = r7.append(r1);	 Catch:{ all -> 0x01f8 }
        r1 = r1.toString();	 Catch:{ all -> 0x01f8 }
        r5[r6] = r1;	 Catch:{ all -> 0x01f8 }
        com.tencent.bugly.imsdk.proguard.w.e(r3, r5);	 Catch:{ all -> 0x01f8 }
        if (r2 == 0) goto L_0x01c4;
    L_0x01c1:
        r2.close();	 Catch:{ IOException -> 0x01d8 }
    L_0x01c4:
        r1 = r4;
        goto L_0x0021;
    L_0x01c7:
        r5.close();	 Catch:{ IOException -> 0x01cd }
    L_0x01ca:
        r1 = r3;
        goto L_0x0021;
    L_0x01cd:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x01ca;
    L_0x01d4:
        r1.printStackTrace();
        goto L_0x01ca;
    L_0x01d8:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x01c4;
    L_0x01df:
        r1.printStackTrace();
        goto L_0x01c4;
    L_0x01e3:
        r1 = move-exception;
        r5 = r2;
    L_0x01e5:
        if (r5 == 0) goto L_0x01ea;
    L_0x01e7:
        r5.close();	 Catch:{ IOException -> 0x01eb }
    L_0x01ea:
        throw r1;
    L_0x01eb:
        r2 = move-exception;
        r3 = com.tencent.bugly.imsdk.proguard.w.a(r2);
        if (r3 != 0) goto L_0x01ea;
    L_0x01f2:
        r2.printStackTrace();
        goto L_0x01ea;
    L_0x01f6:
        r1 = move-exception;
        goto L_0x01e5;
    L_0x01f8:
        r1 = move-exception;
        r5 = r2;
        goto L_0x01e5;
    L_0x01fb:
        r1 = move-exception;
        goto L_0x0186;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.crash.anr.b.a(java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    public final boolean a() {
        return this.a.get() != 0;
    }

    /* JADX WARNING: Missing block: B:9:?, code:
            com.tencent.bugly.imsdk.proguard.w.c("read trace first dump for create time!", new java.lang.Object[0]);
            r0 = -1;
            r2 = com.tencent.bugly.imsdk.crashreport.crash.anr.TraceFileHelper.readFirstDumpInfo(r13, false);
     */
    /* JADX WARNING: Missing block: B:10:0x002b, code:
            if (r2 == null) goto L_0x002f;
     */
    /* JADX WARNING: Missing block: B:11:0x002d, code:
            r0 = r2.c;
     */
    /* JADX WARNING: Missing block: B:13:0x0033, code:
            if (r0 != -1) goto L_0x030d;
     */
    /* JADX WARNING: Missing block: B:14:0x0035, code:
            com.tencent.bugly.imsdk.proguard.w.d("trace dump fail could not get time!", new java.lang.Object[0]);
            r4 = java.lang.System.currentTimeMillis();
     */
    /* JADX WARNING: Missing block: B:16:0x004f, code:
            if (java.lang.Math.abs(r4 - r12.b) >= 10000) goto L_0x006d;
     */
    /* JADX WARNING: Missing block: B:17:0x0051, code:
            com.tencent.bugly.imsdk.proguard.w.d("should not process ANR too Fre in %d", java.lang.Integer.valueOf(10000));
     */
    /* JADX WARNING: Missing block: B:18:0x0063, code:
            r12.a.set(0);
     */
    /* JADX WARNING: Missing block: B:23:?, code:
            r12.b = r4;
            r12.a.set(1);
     */
    /* JADX WARNING: Missing block: B:25:?, code:
            r6 = com.tencent.bugly.imsdk.proguard.y.a(com.tencent.bugly.imsdk.crashreport.crash.c.e, false);
     */
    /* JADX WARNING: Missing block: B:26:0x007c, code:
            if (r6 == null) goto L_0x0084;
     */
    /* JADX WARNING: Missing block: B:29:0x0082, code:
            if (r6.size() > 0) goto L_0x00a9;
     */
    /* JADX WARNING: Missing block: B:30:0x0084, code:
            com.tencent.bugly.imsdk.proguard.w.d("can't get all thread skip this anr", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:31:0x008d, code:
            r12.a.set(0);
     */
    /* JADX WARNING: Missing block: B:32:0x0094, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:34:?, code:
            com.tencent.bugly.imsdk.proguard.w.a(r0);
            com.tencent.bugly.imsdk.proguard.w.e("get all thread stack fail!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:35:0x00a1, code:
            r12.a.set(0);
     */
    /* JADX WARNING: Missing block: B:37:?, code:
            r7 = r12.c;
     */
    /* JADX WARNING: Missing block: B:38:0x00b1, code:
            if (10000 >= 0) goto L_0x010e;
     */
    /* JADX WARNING: Missing block: B:39:0x00b3, code:
            r2 = 0;
     */
    /* JADX WARNING: Missing block: B:40:0x00b6, code:
            com.tencent.bugly.imsdk.proguard.w.c("to find!", new java.lang.Object[0]);
            r0 = (android.app.ActivityManager) r7.getSystemService(com.meizu.cloud.pushsdk.constants.PushConstants.INTENT_ACTIVITY_NAME);
            r8 = r2 / 500;
            r1 = 0;
     */
    /* JADX WARNING: Missing block: B:41:0x00ce, code:
            r2 = r1;
            com.tencent.bugly.imsdk.proguard.w.c("waiting!", new java.lang.Object[0]);
            r1 = r0.getProcessesInErrorState();
     */
    /* JADX WARNING: Missing block: B:42:0x00db, code:
            if (r1 == null) goto L_0x0112;
     */
    /* JADX WARNING: Missing block: B:43:0x00dd, code:
            r3 = r1.iterator();
     */
    /* JADX WARNING: Missing block: B:45:0x00e5, code:
            if (r3.hasNext() == false) goto L_0x0112;
     */
    /* JADX WARNING: Missing block: B:46:0x00e7, code:
            r1 = (android.app.ActivityManager.ProcessErrorStateInfo) r3.next();
     */
    /* JADX WARNING: Missing block: B:47:0x00f0, code:
            if (r1.condition != 2) goto L_0x00e1;
     */
    /* JADX WARNING: Missing block: B:48:0x00f2, code:
            com.tencent.bugly.imsdk.proguard.w.c("found!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:49:0x00fb, code:
            if (r1 != null) goto L_0x0129;
     */
    /* JADX WARNING: Missing block: B:50:0x00fd, code:
            com.tencent.bugly.imsdk.proguard.w.c("proc state is unvisiable!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:51:0x0106, code:
            r12.a.set(0);
     */
    /* JADX WARNING: Missing block: B:52:0x010e, code:
            r2 = 10000;
     */
    /* JADX WARNING: Missing block: B:55:?, code:
            com.tencent.bugly.imsdk.proguard.y.b(500);
            r1 = r2 + 1;
     */
    /* JADX WARNING: Missing block: B:56:0x011c, code:
            if (((long) r2) < r8) goto L_0x030a;
     */
    /* JADX WARNING: Missing block: B:57:0x011e, code:
            com.tencent.bugly.imsdk.proguard.w.c("end!", new java.lang.Object[0]);
            r1 = null;
     */
    /* JADX WARNING: Missing block: B:59:0x012f, code:
            if (r1.pid == android.os.Process.myPid()) goto L_0x0147;
     */
    /* JADX WARNING: Missing block: B:60:0x0131, code:
            com.tencent.bugly.imsdk.proguard.w.c("not mind proc!", r1.processName);
     */
    /* JADX WARNING: Missing block: B:61:0x013f, code:
            r12.a.set(0);
     */
    /* JADX WARNING: Missing block: B:63:?, code:
            com.tencent.bugly.imsdk.proguard.w.a("found visiable anr , start to process!", new java.lang.Object[0]);
            r2 = r12.c;
            r12.f.c();
     */
    /* JADX WARNING: Missing block: B:64:0x015d, code:
            if (r12.f.b() != false) goto L_0x017c;
     */
    /* JADX WARNING: Missing block: B:65:0x015f, code:
            com.tencent.bugly.imsdk.proguard.w.e("waiting for remote sync", new java.lang.Object[0]);
            r0 = 0;
     */
    /* JADX WARNING: Missing block: B:67:0x016f, code:
            if (r12.f.b() != false) goto L_0x017c;
     */
    /* JADX WARNING: Missing block: B:68:0x0171, code:
            com.tencent.bugly.imsdk.proguard.y.b(500);
            r0 = r0 + 500;
     */
    /* JADX WARNING: Missing block: B:69:0x017a, code:
            if (r0 < 3000) goto L_0x0169;
     */
    /* JADX WARNING: Missing block: B:70:0x017c, code:
            r0 = new java.io.File(r2.getFilesDir(), "bugly/bugly_trace_" + r4 + ".txt");
            r7 = new com.tencent.bugly.imsdk.crashreport.crash.anr.a();
            r7.c = r4;
            r7.d = r0.getAbsolutePath();
            r7.a = r1.processName;
            r7.f = r1.shortMsg;
            r7.e = r1.longMsg;
            r7.b = r6;
     */
    /* JADX WARNING: Missing block: B:71:0x01b7, code:
            if (r6 == null) goto L_0x0205;
     */
    /* JADX WARNING: Missing block: B:72:0x01b9, code:
            r1 = r6.keySet().iterator();
     */
    /* JADX WARNING: Missing block: B:74:0x01c5, code:
            if (r1.hasNext() == false) goto L_0x0205;
     */
    /* JADX WARNING: Missing block: B:75:0x01c7, code:
            r0 = (java.lang.String) r1.next();
     */
    /* JADX WARNING: Missing block: B:76:0x01d4, code:
            if (r0.startsWith("main(") == false) goto L_0x01c1;
     */
    /* JADX WARNING: Missing block: B:77:0x01d6, code:
            r7.g = (java.lang.String) r6.get(r0);
     */
    /* JADX WARNING: Missing block: B:78:0x01df, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:81:0x01e4, code:
            if (com.tencent.bugly.imsdk.proguard.w.a(r0) == false) goto L_0x01e6;
     */
    /* JADX WARNING: Missing block: B:82:0x01e6, code:
            r0.printStackTrace();
     */
    /* JADX WARNING: Missing block: B:83:0x01e9, code:
            com.tencent.bugly.imsdk.proguard.w.e("handle anr error %s", r0.getClass().toString());
     */
    /* JADX WARNING: Missing block: B:85:?, code:
            r1 = "anr tm:%d\ntr:%s\nproc:%s\nsMsg:%s\n lMsg:%s\n threads:%d";
            r2 = new java.lang.Object[6];
            r2[0] = java.lang.Long.valueOf(r7.c);
            r2[1] = r7.d;
            r2[2] = r7.a;
            r2[3] = r7.f;
            r2[4] = r7.e;
     */
    /* JADX WARNING: Missing block: B:86:0x022b, code:
            if (r7.b != null) goto L_0x0260;
     */
    /* JADX WARNING: Missing block: B:87:0x022d, code:
            r0 = 0;
     */
    /* JADX WARNING: Missing block: B:88:0x022e, code:
            r2[5] = java.lang.Integer.valueOf(r0);
            com.tencent.bugly.imsdk.proguard.w.c(r1, r2);
     */
    /* JADX WARNING: Missing block: B:89:0x023d, code:
            if (r12.f.b() != false) goto L_0x0267;
     */
    /* JADX WARNING: Missing block: B:90:0x023f, code:
            com.tencent.bugly.imsdk.proguard.w.e("crash report sync remote fail, will not upload to Bugly , print local for helpful!", new java.lang.Object[0]);
            com.tencent.bugly.imsdk.crashreport.crash.b.a("ANR", com.tencent.bugly.imsdk.proguard.y.a(), r7.a, null, r7.e, null);
     */
    /* JADX WARNING: Missing block: B:91:0x0258, code:
            r12.a.set(0);
     */
    /* JADX WARNING: Missing block: B:93:?, code:
            r0 = r7.b.size();
     */
    /* JADX WARNING: Missing block: B:95:0x026f, code:
            if (r12.f.c().j != false) goto L_0x0283;
     */
    /* JADX WARNING: Missing block: B:96:0x0271, code:
            com.tencent.bugly.imsdk.proguard.w.d("ANR Report is closed!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:98:0x027c, code:
            r12.a.set(0);
     */
    /* JADX WARNING: Missing block: B:100:?, code:
            com.tencent.bugly.imsdk.proguard.w.a("found visiable anr , start to upload!", new java.lang.Object[0]);
            r5 = a(r7);
     */
    /* JADX WARNING: Missing block: B:101:0x0290, code:
            if (r5 != null) goto L_0x029c;
     */
    /* JADX WARNING: Missing block: B:102:0x0292, code:
            com.tencent.bugly.imsdk.proguard.w.e("pack anr fail!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:103:0x029c, code:
            com.tencent.bugly.imsdk.crashreport.crash.c.a().a(r5);
     */
    /* JADX WARNING: Missing block: B:104:0x02a9, code:
            if (r5.a < 0) goto L_0x0300;
     */
    /* JADX WARNING: Missing block: B:105:0x02ab, code:
            com.tencent.bugly.imsdk.proguard.w.a("backup anr record success!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:106:0x02b4, code:
            if (r13 == null) goto L_0x02da;
     */
    /* JADX WARNING: Missing block: B:108:0x02bf, code:
            if (new java.io.File(r13).exists() == false) goto L_0x02da;
     */
    /* JADX WARNING: Missing block: B:109:0x02c1, code:
            r12.a.set(3);
     */
    /* JADX WARNING: Missing block: B:110:0x02cf, code:
            if (a(r13, r7.d, r7.a) == false) goto L_0x02da;
     */
    /* JADX WARNING: Missing block: B:111:0x02d1, code:
            com.tencent.bugly.imsdk.proguard.w.a("backup trace success", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:112:0x02da, code:
            com.tencent.bugly.imsdk.crashreport.crash.b.a("ANR", com.tencent.bugly.imsdk.proguard.y.a(), r7.a, null, r7.e, r5);
     */
    /* JADX WARNING: Missing block: B:113:0x02ef, code:
            if (r12.h.a(r5) != false) goto L_0x02f9;
     */
    /* JADX WARNING: Missing block: B:114:0x02f1, code:
            r12.h.a(r5, 3000, true);
     */
    /* JADX WARNING: Missing block: B:115:0x02f9, code:
            r12.h.b(r5);
     */
    /* JADX WARNING: Missing block: B:116:0x0300, code:
            com.tencent.bugly.imsdk.proguard.w.d("backup anr record fail!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:118:0x030d, code:
            r4 = r0;
     */
    /* JADX WARNING: Missing block: B:128:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:129:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:130:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:131:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:132:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:133:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:134:?, code:
            return;
     */
    public final void a(java.lang.String r13) {
        /*
        r12 = this;
        monitor-enter(r12);
        r0 = r12.a;	 Catch:{ all -> 0x006a }
        r0 = r0.get();	 Catch:{ all -> 0x006a }
        if (r0 == 0) goto L_0x0014;
    L_0x0009:
        r0 = "trace started return ";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ all -> 0x006a }
        com.tencent.bugly.imsdk.proguard.w.c(r0, r1);	 Catch:{ all -> 0x006a }
        monitor-exit(r12);	 Catch:{ all -> 0x006a }
    L_0x0013:
        return;
    L_0x0014:
        r0 = r12.a;	 Catch:{ all -> 0x006a }
        r1 = 1;
        r0.set(r1);	 Catch:{ all -> 0x006a }
        monitor-exit(r12);	 Catch:{ all -> 0x006a }
        r0 = "read trace first dump for create time!";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.c(r0, r1);	 Catch:{ Throwable -> 0x01df }
        r0 = -1;
        r2 = 0;
        r2 = com.tencent.bugly.imsdk.crashreport.crash.anr.TraceFileHelper.readFirstDumpInfo(r13, r2);	 Catch:{ Throwable -> 0x01df }
        if (r2 == 0) goto L_0x002f;
    L_0x002d:
        r0 = r2.c;	 Catch:{ Throwable -> 0x01df }
    L_0x002f:
        r2 = -1;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 != 0) goto L_0x030d;
    L_0x0035:
        r0 = "trace dump fail could not get time!";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.d(r0, r1);	 Catch:{ Throwable -> 0x01df }
        r0 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x01df }
        r4 = r0;
    L_0x0043:
        r0 = r12.b;	 Catch:{ Throwable -> 0x01df }
        r0 = r4 - r0;
        r0 = java.lang.Math.abs(r0);	 Catch:{ Throwable -> 0x01df }
        r2 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 >= 0) goto L_0x006d;
    L_0x0051:
        r0 = "should not process ANR too Fre in %d";
        r1 = 1;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        r2 = 0;
        r3 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ Throwable -> 0x01df }
        r1[r2] = r3;	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.d(r0, r1);	 Catch:{ Throwable -> 0x01df }
        r0 = r12.a;
        r1 = 0;
        r0.set(r1);
        goto L_0x0013;
    L_0x006a:
        r0 = move-exception;
        monitor-exit(r12);
        throw r0;
    L_0x006d:
        r12.b = r4;	 Catch:{ Throwable -> 0x01df }
        r0 = r12.a;	 Catch:{ Throwable -> 0x01df }
        r1 = 1;
        r0.set(r1);	 Catch:{ Throwable -> 0x01df }
        r0 = com.tencent.bugly.imsdk.crashreport.crash.c.e;	 Catch:{ Throwable -> 0x0094 }
        r1 = 0;
        r6 = com.tencent.bugly.imsdk.proguard.y.a(r0, r1);	 Catch:{ Throwable -> 0x0094 }
        if (r6 == 0) goto L_0x0084;
    L_0x007e:
        r0 = r6.size();	 Catch:{ Throwable -> 0x01df }
        if (r0 > 0) goto L_0x00a9;
    L_0x0084:
        r0 = "can't get all thread skip this anr";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.d(r0, r1);	 Catch:{ Throwable -> 0x01df }
        r0 = r12.a;
        r1 = 0;
        r0.set(r1);
        goto L_0x0013;
    L_0x0094:
        r0 = move-exception;
        com.tencent.bugly.imsdk.proguard.w.a(r0);	 Catch:{ Throwable -> 0x01df }
        r0 = "get all thread stack fail!";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.e(r0, r1);	 Catch:{ Throwable -> 0x01df }
        r0 = r12.a;
        r1 = 0;
        r0.set(r1);
        goto L_0x0013;
    L_0x00a9:
        r7 = r12.c;	 Catch:{ Throwable -> 0x01df }
        r0 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r2 = 0;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 >= 0) goto L_0x010e;
    L_0x00b3:
        r0 = 0;
        r2 = r0;
    L_0x00b6:
        r0 = "to find!";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.c(r0, r1);	 Catch:{ Throwable -> 0x01df }
        r0 = "activity";
        r0 = r7.getSystemService(r0);	 Catch:{ Throwable -> 0x01df }
        r0 = (android.app.ActivityManager) r0;	 Catch:{ Throwable -> 0x01df }
        r8 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r8 = r2 / r8;
        r1 = 0;
        r2 = r1;
    L_0x00ce:
        r1 = "waiting!";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.c(r1, r3);	 Catch:{ Throwable -> 0x01df }
        r1 = r0.getProcessesInErrorState();	 Catch:{ Throwable -> 0x01df }
        if (r1 == 0) goto L_0x0112;
    L_0x00dd:
        r3 = r1.iterator();	 Catch:{ Throwable -> 0x01df }
    L_0x00e1:
        r1 = r3.hasNext();	 Catch:{ Throwable -> 0x01df }
        if (r1 == 0) goto L_0x0112;
    L_0x00e7:
        r1 = r3.next();	 Catch:{ Throwable -> 0x01df }
        r1 = (android.app.ActivityManager.ProcessErrorStateInfo) r1;	 Catch:{ Throwable -> 0x01df }
        r7 = r1.condition;	 Catch:{ Throwable -> 0x01df }
        r10 = 2;
        if (r7 != r10) goto L_0x00e1;
    L_0x00f2:
        r0 = "found!";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.c(r0, r2);	 Catch:{ Throwable -> 0x01df }
    L_0x00fb:
        if (r1 != 0) goto L_0x0129;
    L_0x00fd:
        r0 = "proc state is unvisiable!";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.c(r0, r1);	 Catch:{ Throwable -> 0x01df }
        r0 = r12.a;
        r1 = 0;
        r0.set(r1);
        goto L_0x0013;
    L_0x010e:
        r0 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r2 = r0;
        goto L_0x00b6;
    L_0x0112:
        r10 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        com.tencent.bugly.imsdk.proguard.y.b(r10);	 Catch:{ Throwable -> 0x01df }
        r1 = r2 + 1;
        r2 = (long) r2;	 Catch:{ Throwable -> 0x01df }
        r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r2 < 0) goto L_0x030a;
    L_0x011e:
        r0 = "end!";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.c(r0, r1);	 Catch:{ Throwable -> 0x01df }
        r1 = 0;
        goto L_0x00fb;
    L_0x0129:
        r0 = r1.pid;	 Catch:{ Throwable -> 0x01df }
        r2 = android.os.Process.myPid();	 Catch:{ Throwable -> 0x01df }
        if (r0 == r2) goto L_0x0147;
    L_0x0131:
        r0 = "not mind proc!";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x01df }
        r3 = 0;
        r1 = r1.processName;	 Catch:{ Throwable -> 0x01df }
        r2[r3] = r1;	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.c(r0, r2);	 Catch:{ Throwable -> 0x01df }
        r0 = r12.a;
        r1 = 0;
        r0.set(r1);
        goto L_0x0013;
    L_0x0147:
        r0 = "found visiable anr , start to process!";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.a(r0, r2);	 Catch:{ Throwable -> 0x01df }
        r2 = r12.c;	 Catch:{ Throwable -> 0x01df }
        r0 = r12.f;	 Catch:{ Throwable -> 0x01df }
        r0.c();	 Catch:{ Throwable -> 0x01df }
        r0 = r12.f;	 Catch:{ Throwable -> 0x01df }
        r0 = r0.b();	 Catch:{ Throwable -> 0x01df }
        if (r0 != 0) goto L_0x017c;
    L_0x015f:
        r0 = "waiting for remote sync";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.e(r0, r3);	 Catch:{ Throwable -> 0x01df }
        r0 = 0;
    L_0x0169:
        r3 = r12.f;	 Catch:{ Throwable -> 0x01df }
        r3 = r3.b();	 Catch:{ Throwable -> 0x01df }
        if (r3 != 0) goto L_0x017c;
    L_0x0171:
        r8 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        com.tencent.bugly.imsdk.proguard.y.b(r8);	 Catch:{ Throwable -> 0x01df }
        r0 = r0 + 500;
        r3 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        if (r0 < r3) goto L_0x0169;
    L_0x017c:
        r0 = new java.io.File;	 Catch:{ Throwable -> 0x01df }
        r2 = r2.getFilesDir();	 Catch:{ Throwable -> 0x01df }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01df }
        r7 = "bugly/bugly_trace_";
        r3.<init>(r7);	 Catch:{ Throwable -> 0x01df }
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x01df }
        r7 = ".txt";
        r3 = r3.append(r7);	 Catch:{ Throwable -> 0x01df }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x01df }
        r0.<init>(r2, r3);	 Catch:{ Throwable -> 0x01df }
        r7 = new com.tencent.bugly.imsdk.crashreport.crash.anr.a;	 Catch:{ Throwable -> 0x01df }
        r7.<init>();	 Catch:{ Throwable -> 0x01df }
        r7.c = r4;	 Catch:{ Throwable -> 0x01df }
        r0 = r0.getAbsolutePath();	 Catch:{ Throwable -> 0x01df }
        r7.d = r0;	 Catch:{ Throwable -> 0x01df }
        r0 = r1.processName;	 Catch:{ Throwable -> 0x01df }
        r7.a = r0;	 Catch:{ Throwable -> 0x01df }
        r0 = r1.shortMsg;	 Catch:{ Throwable -> 0x01df }
        r7.f = r0;	 Catch:{ Throwable -> 0x01df }
        r0 = r1.longMsg;	 Catch:{ Throwable -> 0x01df }
        r7.e = r0;	 Catch:{ Throwable -> 0x01df }
        r7.b = r6;	 Catch:{ Throwable -> 0x01df }
        if (r6 == 0) goto L_0x0205;
    L_0x01b9:
        r0 = r6.keySet();	 Catch:{ Throwable -> 0x01df }
        r1 = r0.iterator();	 Catch:{ Throwable -> 0x01df }
    L_0x01c1:
        r0 = r1.hasNext();	 Catch:{ Throwable -> 0x01df }
        if (r0 == 0) goto L_0x0205;
    L_0x01c7:
        r0 = r1.next();	 Catch:{ Throwable -> 0x01df }
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x01df }
        r2 = "main(";
        r2 = r0.startsWith(r2);	 Catch:{ Throwable -> 0x01df }
        if (r2 == 0) goto L_0x01c1;
    L_0x01d6:
        r0 = r6.get(r0);	 Catch:{ Throwable -> 0x01df }
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x01df }
        r7.g = r0;	 Catch:{ Throwable -> 0x01df }
        goto L_0x01c1;
    L_0x01df:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);	 Catch:{ all -> 0x027b }
        if (r1 != 0) goto L_0x01e9;
    L_0x01e6:
        r0.printStackTrace();	 Catch:{ all -> 0x027b }
    L_0x01e9:
        r1 = "handle anr error %s";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ all -> 0x027b }
        r3 = 0;
        r0 = r0.getClass();	 Catch:{ all -> 0x027b }
        r0 = r0.toString();	 Catch:{ all -> 0x027b }
        r2[r3] = r0;	 Catch:{ all -> 0x027b }
        com.tencent.bugly.imsdk.proguard.w.e(r1, r2);	 Catch:{ all -> 0x027b }
        r0 = r12.a;
        r1 = 0;
        r0.set(r1);
        goto L_0x0013;
    L_0x0205:
        r1 = "anr tm:%d\ntr:%s\nproc:%s\nsMsg:%s\n lMsg:%s\n threads:%d";
        r0 = 6;
        r2 = new java.lang.Object[r0];	 Catch:{ Throwable -> 0x01df }
        r0 = 0;
        r4 = r7.c;	 Catch:{ Throwable -> 0x01df }
        r3 = java.lang.Long.valueOf(r4);	 Catch:{ Throwable -> 0x01df }
        r2[r0] = r3;	 Catch:{ Throwable -> 0x01df }
        r0 = 1;
        r3 = r7.d;	 Catch:{ Throwable -> 0x01df }
        r2[r0] = r3;	 Catch:{ Throwable -> 0x01df }
        r0 = 2;
        r3 = r7.a;	 Catch:{ Throwable -> 0x01df }
        r2[r0] = r3;	 Catch:{ Throwable -> 0x01df }
        r0 = 3;
        r3 = r7.f;	 Catch:{ Throwable -> 0x01df }
        r2[r0] = r3;	 Catch:{ Throwable -> 0x01df }
        r0 = 4;
        r3 = r7.e;	 Catch:{ Throwable -> 0x01df }
        r2[r0] = r3;	 Catch:{ Throwable -> 0x01df }
        r3 = 5;
        r0 = r7.b;	 Catch:{ Throwable -> 0x01df }
        if (r0 != 0) goto L_0x0260;
    L_0x022d:
        r0 = 0;
    L_0x022e:
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ Throwable -> 0x01df }
        r2[r3] = r0;	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.c(r1, r2);	 Catch:{ Throwable -> 0x01df }
        r0 = r12.f;	 Catch:{ Throwable -> 0x01df }
        r0 = r0.b();	 Catch:{ Throwable -> 0x01df }
        if (r0 != 0) goto L_0x0267;
    L_0x023f:
        r0 = "crash report sync remote fail, will not upload to Bugly , print local for helpful!";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.e(r0, r1);	 Catch:{ Throwable -> 0x01df }
        r0 = "ANR";
        r1 = com.tencent.bugly.imsdk.proguard.y.a();	 Catch:{ Throwable -> 0x01df }
        r2 = r7.a;	 Catch:{ Throwable -> 0x01df }
        r3 = 0;
        r4 = r7.e;	 Catch:{ Throwable -> 0x01df }
        r5 = 0;
        com.tencent.bugly.imsdk.crashreport.crash.b.a(r0, r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x01df }
    L_0x0258:
        r0 = r12.a;
        r1 = 0;
        r0.set(r1);
        goto L_0x0013;
    L_0x0260:
        r0 = r7.b;	 Catch:{ Throwable -> 0x01df }
        r0 = r0.size();	 Catch:{ Throwable -> 0x01df }
        goto L_0x022e;
    L_0x0267:
        r0 = r12.f;	 Catch:{ Throwable -> 0x01df }
        r0 = r0.c();	 Catch:{ Throwable -> 0x01df }
        r0 = r0.j;	 Catch:{ Throwable -> 0x01df }
        if (r0 != 0) goto L_0x0283;
    L_0x0271:
        r0 = "ANR Report is closed!";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.d(r0, r1);	 Catch:{ Throwable -> 0x01df }
        goto L_0x0258;
    L_0x027b:
        r0 = move-exception;
        r1 = r12.a;
        r2 = 0;
        r1.set(r2);
        throw r0;
    L_0x0283:
        r0 = "found visiable anr , start to upload!";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.a(r0, r1);	 Catch:{ Throwable -> 0x01df }
        r5 = r12.a(r7);	 Catch:{ Throwable -> 0x01df }
        if (r5 != 0) goto L_0x029c;
    L_0x0292:
        r0 = "pack anr fail!";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.e(r0, r1);	 Catch:{ Throwable -> 0x01df }
        goto L_0x0258;
    L_0x029c:
        r0 = com.tencent.bugly.imsdk.crashreport.crash.c.a();	 Catch:{ Throwable -> 0x01df }
        r0.a(r5);	 Catch:{ Throwable -> 0x01df }
        r0 = r5.a;	 Catch:{ Throwable -> 0x01df }
        r2 = 0;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 < 0) goto L_0x0300;
    L_0x02ab:
        r0 = "backup anr record success!";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.a(r0, r1);	 Catch:{ Throwable -> 0x01df }
    L_0x02b4:
        if (r13 == 0) goto L_0x02da;
    L_0x02b6:
        r0 = new java.io.File;	 Catch:{ Throwable -> 0x01df }
        r0.<init>(r13);	 Catch:{ Throwable -> 0x01df }
        r0 = r0.exists();	 Catch:{ Throwable -> 0x01df }
        if (r0 == 0) goto L_0x02da;
    L_0x02c1:
        r0 = r12.a;	 Catch:{ Throwable -> 0x01df }
        r1 = 3;
        r0.set(r1);	 Catch:{ Throwable -> 0x01df }
        r0 = r7.d;	 Catch:{ Throwable -> 0x01df }
        r1 = r7.a;	 Catch:{ Throwable -> 0x01df }
        r0 = a(r13, r0, r1);	 Catch:{ Throwable -> 0x01df }
        if (r0 == 0) goto L_0x02da;
    L_0x02d1:
        r0 = "backup trace success";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.a(r0, r1);	 Catch:{ Throwable -> 0x01df }
    L_0x02da:
        r0 = "ANR";
        r1 = com.tencent.bugly.imsdk.proguard.y.a();	 Catch:{ Throwable -> 0x01df }
        r2 = r7.a;	 Catch:{ Throwable -> 0x01df }
        r3 = 0;
        r4 = r7.e;	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.crashreport.crash.b.a(r0, r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x01df }
        r0 = r12.h;	 Catch:{ Throwable -> 0x01df }
        r0 = r0.a(r5);	 Catch:{ Throwable -> 0x01df }
        if (r0 != 0) goto L_0x02f9;
    L_0x02f1:
        r0 = r12.h;	 Catch:{ Throwable -> 0x01df }
        r2 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        r1 = 1;
        r0.a(r5, r2, r1);	 Catch:{ Throwable -> 0x01df }
    L_0x02f9:
        r0 = r12.h;	 Catch:{ Throwable -> 0x01df }
        r0.b(r5);	 Catch:{ Throwable -> 0x01df }
        goto L_0x0258;
    L_0x0300:
        r0 = "backup anr record fail!";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.d(r0, r1);	 Catch:{ Throwable -> 0x01df }
        goto L_0x02b4;
    L_0x030a:
        r2 = r1;
        goto L_0x00ce;
    L_0x030d:
        r4 = r0;
        goto L_0x0043;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.crash.anr.b.a(java.lang.String):void");
    }

    private synchronized void c() {
        if (e()) {
            w.d("start when started!", new Object[0]);
        } else {
            this.i = new AnonymousClass1("/data/anr/", 8);
            try {
                this.i.startWatching();
                w.a("start anr monitor!", new Object[0]);
                this.e.a(new Runnable() {
                    public final void run() {
                        b.this.b();
                    }
                });
            } catch (Throwable th) {
                this.i = null;
                w.d("start anr monitor failed!", new Object[0]);
                if (!w.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private synchronized void d() {
        if (e()) {
            try {
                this.i.stopWatching();
                this.i = null;
                w.d("close anr monitor!", new Object[0]);
            } catch (Throwable th) {
                w.d("stop anr monitor failed!", new Object[0]);
                if (!w.a(th)) {
                    th.printStackTrace();
                }
            }
        } else {
            w.d("close when closed!", new Object[0]);
        }
    }

    private synchronized boolean e() {
        return this.i != null;
    }

    private synchronized void b(boolean z) {
        if (z) {
            c();
        } else {
            d();
        }
    }

    private synchronized boolean f() {
        return this.j;
    }

    private synchronized void c(boolean z) {
        if (this.j != z) {
            w.a("user change anr %b", Boolean.valueOf(z));
            this.j = z;
        }
    }

    public final void a(boolean z) {
        c(z);
        boolean f = f();
        com.tencent.bugly.imsdk.crashreport.common.strategy.a a = com.tencent.bugly.imsdk.crashreport.common.strategy.a.a();
        if (a != null) {
            f = f && a.c().g;
        }
        if (f != e()) {
            w.a("anr changed to %b", Boolean.valueOf(f));
            b(f);
        }
    }

    protected final void b() {
        long b = y.b() - c.f;
        File file = new File(this.g);
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length != 0) {
                String str = "bugly_trace_";
                String str2 = ".txt";
                int length = str.length();
                int i = 0;
                for (File file2 : listFiles) {
                    String name = file2.getName();
                    if (name.startsWith(str)) {
                        try {
                            int indexOf = name.indexOf(str2);
                            if (indexOf > 0 && Long.parseLong(name.substring(length, indexOf)) >= b) {
                            }
                        } catch (Throwable th) {
                            w.e("tomb format error delete %s", name);
                        }
                        if (file2.delete()) {
                            i++;
                        }
                    }
                }
                w.c("clean tombs %d", Integer.valueOf(i));
            }
        }
    }

    public final synchronized void a(StrategyBean strategyBean) {
        boolean z = true;
        synchronized (this) {
            if (strategyBean != null) {
                if (strategyBean.j != e()) {
                    w.d("server anr changed to %b", Boolean.valueOf(strategyBean.j));
                }
                if (!(strategyBean.j && f())) {
                    z = false;
                }
                if (z != e()) {
                    w.a("anr changed to %b", Boolean.valueOf(z));
                    b(z);
                }
            }
        }
    }
}
