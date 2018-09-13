package com.tencent.bugly.imsdk.proguard;

import android.content.Context;
import com.feng.car.utils.FengConstant;
import com.tencent.bugly.imsdk.crashreport.common.info.a;
import java.util.Map;
import java.util.UUID;

/* compiled from: BUGLY */
public final class u implements Runnable {
    private int a;
    private int b;
    private final Context c;
    private final int d;
    private final byte[] e;
    private final a f;
    private final com.tencent.bugly.imsdk.crashreport.common.strategy.a g;
    private final r h;
    private final t i;
    private final int j;
    private final s k;
    private final s l;
    private String m;
    private final String n;
    private final Map<String, String> o;
    private int p;
    private long q;
    private long r;
    private boolean s;
    private boolean t;

    public u(Context context, int i, int i2, byte[] bArr, String str, String str2, s sVar, boolean z, boolean z2) {
        this(context, i, i2, bArr, str, str2, sVar, z, 2, 30000, z2, null);
    }

    public u(Context context, int i, int i2, byte[] bArr, String str, String str2, s sVar, boolean z, int i3, int i4, boolean z2, Map<String, String> map) {
        this.a = 2;
        this.b = 30000;
        this.m = null;
        this.p = 0;
        this.q = 0;
        this.r = 0;
        this.s = true;
        this.t = false;
        this.c = context;
        this.f = a.a(context);
        this.e = bArr;
        this.g = com.tencent.bugly.imsdk.crashreport.common.strategy.a.a();
        this.h = r.a(context);
        this.i = t.a();
        this.j = i;
        this.m = str;
        this.n = str2;
        this.k = sVar;
        t tVar = this.i;
        this.l = null;
        this.s = z;
        this.d = i2;
        if (i3 > 0) {
            this.a = i3;
        }
        if (i4 > 0) {
            this.b = i4;
        }
        this.t = z2;
        this.o = map;
    }

    private void a(am amVar, boolean z, int i, String str, int i2) {
        String str2;
        s sVar;
        int i3;
        long j;
        switch (this.d) {
            case 630:
            case 830:
                str2 = "crash";
                break;
            case FengConstant.IMAGEMIDDLEWIDTH /*640*/:
            case 840:
                str2 = "userinfo";
                break;
            default:
                str2 = String.valueOf(this.d);
                break;
        }
        if (z) {
            w.a("[Upload] Success: %s", str2);
        } else {
            w.e("[Upload] Failed to upload(%d) %s: %s", Integer.valueOf(i), str2, str);
            if (this.s) {
                this.i.a(i2, null);
            }
        }
        if (this.q + this.r > 0) {
            this.i.a((this.i.a(this.t) + this.q) + this.r, this.t);
        }
        if (this.k != null) {
            sVar = this.k;
            i3 = this.d;
            j = this.q;
            j = this.r;
            sVar.a(z);
        }
        if (this.l != null) {
            sVar = this.l;
            i3 = this.d;
            j = this.q;
            j = this.r;
            sVar.a(z);
        }
    }

    private static boolean a(am amVar, a aVar, com.tencent.bugly.imsdk.crashreport.common.strategy.a aVar2) {
        if (amVar == null) {
            w.d("resp == null!", new Object[0]);
            return false;
        } else if (amVar.a != (byte) 0) {
            w.e("resp result error %d", Byte.valueOf(amVar.a));
            return false;
        } else {
            try {
                if (!(y.a(amVar.d) || a.b().i().equals(amVar.d))) {
                    o.a().a(com.tencent.bugly.imsdk.crashreport.common.strategy.a.a, "key_ip", amVar.d.getBytes("UTF-8"), null, true);
                    aVar.d(amVar.d);
                }
                if (!(y.a(amVar.f) || a.b().j().equals(amVar.f))) {
                    o.a().a(com.tencent.bugly.imsdk.crashreport.common.strategy.a.a, "key_imei", amVar.f.getBytes("UTF-8"), null, true);
                    aVar.e(amVar.f);
                }
            } catch (Throwable th) {
                w.a(th);
            }
            aVar.i = amVar.e;
            if (amVar.b == 510) {
                if (amVar.c == null) {
                    w.e("[Upload] Strategy data is null. Response cmd: %d", Integer.valueOf(amVar.b));
                    return false;
                }
                ao aoVar = (ao) a.a(amVar.c, ao.class);
                if (aoVar == null) {
                    w.e("[Upload] Failed to decode strategy from server. Response cmd: %d", Integer.valueOf(amVar.b));
                    return false;
                }
                aVar2.a(aoVar);
            }
            return true;
        }
    }

    /* JADX WARNING: Missing block: B:90:0x035d, code:
            if (r5 == 0) goto L_0x0408;
     */
    /* JADX WARNING: Missing block: B:92:0x0360, code:
            if (r5 != 2) goto L_0x03ef;
     */
    /* JADX WARNING: Missing block: B:95:0x036b, code:
            if ((r11.q + r11.r) <= 0) goto L_0x0382;
     */
    /* JADX WARNING: Missing block: B:96:0x036d, code:
            r11.i.a((r11.i.a(r11.t) + r11.q) + r11.r, r11.t);
     */
    /* JADX WARNING: Missing block: B:97:0x0382, code:
            r11.i.a(r5, null);
            com.tencent.bugly.imsdk.proguard.w.a("[Upload] Session ID is invalid, will try again immediately (pid=%d | tid=%d).", java.lang.Integer.valueOf(android.os.Process.myPid()), java.lang.Integer.valueOf(android.os.Process.myTid()));
            r11.i.a(r11.j, r11.d, r11.e, r11.m, r11.n, r11.k, r11.a, r11.b, true, r11.o);
     */
    /* JADX WARNING: Missing block: B:100:0x03ef, code:
            a(null, false, 1, "status of server is " + r5, r5);
     */
    /* JADX WARNING: Missing block: B:151:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:152:?, code:
            return;
     */
    public final void run() {
        /*
        r11 = this;
        r0 = 0;
        r11.p = r0;	 Catch:{ Throwable -> 0x0032 }
        r0 = 0;
        r11.q = r0;	 Catch:{ Throwable -> 0x0032 }
        r0 = 0;
        r11.r = r0;	 Catch:{ Throwable -> 0x0032 }
        r0 = r11.e;	 Catch:{ Throwable -> 0x0032 }
        r1 = r11.c;	 Catch:{ Throwable -> 0x0032 }
        r1 = com.tencent.bugly.imsdk.crashreport.common.info.b.e(r1);	 Catch:{ Throwable -> 0x0032 }
        if (r1 != 0) goto L_0x0021;
    L_0x0015:
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = "network is not available";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
    L_0x0020:
        return;
    L_0x0021:
        if (r0 == 0) goto L_0x0026;
    L_0x0023:
        r1 = r0.length;	 Catch:{ Throwable -> 0x0032 }
        if (r1 != 0) goto L_0x003d;
    L_0x0026:
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = "request package is empty!";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x0032:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r1 != 0) goto L_0x0020;
    L_0x0039:
        r0.printStackTrace();
        goto L_0x0020;
    L_0x003d:
        r1 = r11.i;	 Catch:{ Throwable -> 0x0032 }
        r2 = r11.t;	 Catch:{ Throwable -> 0x0032 }
        r2 = r1.a(r2);	 Catch:{ Throwable -> 0x0032 }
        r1 = r0.length;	 Catch:{ Throwable -> 0x0032 }
        r4 = (long) r1;	 Catch:{ Throwable -> 0x0032 }
        r4 = r4 + r2;
        r6 = 2097152; // 0x200000 float:2.938736E-39 double:1.0361308E-317;
        r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r1 < 0) goto L_0x008b;
    L_0x004f:
        r0 = "[Upload] Upload too much data, try next time: %d/%d";
        r1 = 2;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x0032 }
        r4 = 0;
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ Throwable -> 0x0032 }
        r1[r4] = r2;	 Catch:{ Throwable -> 0x0032 }
        r2 = 1;
        r4 = 2097152; // 0x200000 float:2.938736E-39 double:1.0361308E-317;
        r3 = java.lang.Long.valueOf(r4);	 Catch:{ Throwable -> 0x0032 }
        r1[r2] = r3;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.e(r0, r1);	 Catch:{ Throwable -> 0x0032 }
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r0 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0032 }
        r4 = "over net consume: ";
        r0.<init>(r4);	 Catch:{ Throwable -> 0x0032 }
        r4 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x0032 }
        r4 = "K";
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x0032 }
        r4 = r0.toString();	 Catch:{ Throwable -> 0x0032 }
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x008b:
        r1 = "[Upload] Run upload task with cmd: %d";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0032 }
        r3 = 0;
        r4 = r11.d;	 Catch:{ Throwable -> 0x0032 }
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x0032 }
        r2[r3] = r4;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.c(r1, r2);	 Catch:{ Throwable -> 0x0032 }
        r1 = r11.c;	 Catch:{ Throwable -> 0x0032 }
        if (r1 == 0) goto L_0x00ad;
    L_0x00a1:
        r1 = r11.f;	 Catch:{ Throwable -> 0x0032 }
        if (r1 == 0) goto L_0x00ad;
    L_0x00a5:
        r1 = r11.g;	 Catch:{ Throwable -> 0x0032 }
        if (r1 == 0) goto L_0x00ad;
    L_0x00a9:
        r1 = r11.h;	 Catch:{ Throwable -> 0x0032 }
        if (r1 != 0) goto L_0x00ba;
    L_0x00ad:
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = "illegal access error";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x00ba:
        r1 = r11.g;	 Catch:{ Throwable -> 0x0032 }
        r1 = r1.c();	 Catch:{ Throwable -> 0x0032 }
        if (r1 != 0) goto L_0x00cf;
    L_0x00c2:
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = "illegal local strategy";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x00cf:
        r3 = 0;
        r7 = new java.util.HashMap;	 Catch:{ Throwable -> 0x0032 }
        r7.<init>();	 Catch:{ Throwable -> 0x0032 }
        r2 = "prodId";
        r4 = r11.f;	 Catch:{ Throwable -> 0x0032 }
        r4 = r4.f();	 Catch:{ Throwable -> 0x0032 }
        r7.put(r2, r4);	 Catch:{ Throwable -> 0x0032 }
        r2 = "bundleId";
        r4 = r11.f;	 Catch:{ Throwable -> 0x0032 }
        r4 = r4.c;	 Catch:{ Throwable -> 0x0032 }
        r7.put(r2, r4);	 Catch:{ Throwable -> 0x0032 }
        r2 = "appVer";
        r4 = r11.f;	 Catch:{ Throwable -> 0x0032 }
        r4 = r4.j;	 Catch:{ Throwable -> 0x0032 }
        r7.put(r2, r4);	 Catch:{ Throwable -> 0x0032 }
        r2 = r11.o;	 Catch:{ Throwable -> 0x0032 }
        if (r2 == 0) goto L_0x00fe;
    L_0x00f9:
        r2 = r11.o;	 Catch:{ Throwable -> 0x0032 }
        r7.putAll(r2);	 Catch:{ Throwable -> 0x0032 }
    L_0x00fe:
        r2 = r11.s;	 Catch:{ Throwable -> 0x0032 }
        if (r2 == 0) goto L_0x0171;
    L_0x0102:
        r2 = "cmd";
        r4 = r11.d;	 Catch:{ Throwable -> 0x0032 }
        r4 = java.lang.Integer.toString(r4);	 Catch:{ Throwable -> 0x0032 }
        r7.put(r2, r4);	 Catch:{ Throwable -> 0x0032 }
        r2 = "platformId";
        r4 = 1;
        r4 = java.lang.Byte.toString(r4);	 Catch:{ Throwable -> 0x0032 }
        r7.put(r2, r4);	 Catch:{ Throwable -> 0x0032 }
        r2 = "sdkVer";
        r4 = r11.f;	 Catch:{ Throwable -> 0x0032 }
        r4.getClass();	 Catch:{ Throwable -> 0x0032 }
        r4 = "2.4.0";
        r7.put(r2, r4);	 Catch:{ Throwable -> 0x0032 }
        r2 = "strategylastUpdateTime";
        r4 = r1.p;	 Catch:{ Throwable -> 0x0032 }
        r1 = java.lang.Long.toString(r4);	 Catch:{ Throwable -> 0x0032 }
        r7.put(r2, r1);	 Catch:{ Throwable -> 0x0032 }
        r1 = r11.i;	 Catch:{ Throwable -> 0x0032 }
        r1 = r1.a(r7);	 Catch:{ Throwable -> 0x0032 }
        if (r1 != 0) goto L_0x0148;
    L_0x013b:
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = "failed to add security info to HTTP headers";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x0148:
        r1 = 2;
        r0 = com.tencent.bugly.imsdk.proguard.y.a(r0, r1);	 Catch:{ Throwable -> 0x0032 }
        if (r0 != 0) goto L_0x015c;
    L_0x014f:
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = "failed to zip request body";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x015c:
        r1 = r11.i;	 Catch:{ Throwable -> 0x0032 }
        r0 = r1.a(r0);	 Catch:{ Throwable -> 0x0032 }
        if (r0 != 0) goto L_0x0171;
    L_0x0164:
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = "failed to encrypt request body";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x0171:
        r6 = r0;
        r0 = r11.i;	 Catch:{ Throwable -> 0x0032 }
        r1 = r11.j;	 Catch:{ Throwable -> 0x0032 }
        r4 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x0032 }
        r0.a(r1, r4);	 Catch:{ Throwable -> 0x0032 }
        r0 = r11.k;	 Catch:{ Throwable -> 0x0032 }
        if (r0 == 0) goto L_0x0185;
    L_0x0181:
        r0 = r11.k;	 Catch:{ Throwable -> 0x0032 }
        r0 = r11.d;	 Catch:{ Throwable -> 0x0032 }
    L_0x0185:
        r0 = r11.l;	 Catch:{ Throwable -> 0x0032 }
        if (r0 == 0) goto L_0x018d;
    L_0x0189:
        r0 = r11.l;	 Catch:{ Throwable -> 0x0032 }
        r0 = r11.d;	 Catch:{ Throwable -> 0x0032 }
    L_0x018d:
        r2 = r11.m;	 Catch:{ Throwable -> 0x0032 }
        r5 = -1;
        r0 = 0;
        r1 = r0;
        r0 = r2;
    L_0x0193:
        r4 = r1 + 1;
        r2 = r11.a;	 Catch:{ Throwable -> 0x0032 }
        if (r1 >= r2) goto L_0x04e5;
    L_0x0199:
        r1 = 1;
        if (r4 <= r1) goto L_0x01c6;
    L_0x019c:
        r1 = "[Upload] Failed to upload last time, wait and try(%d) again.";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0032 }
        r3 = 0;
        r8 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x0032 }
        r2[r3] = r8;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.d(r1, r2);	 Catch:{ Throwable -> 0x0032 }
        r1 = r11.b;	 Catch:{ Throwable -> 0x0032 }
        r2 = (long) r1;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.y.b(r2);	 Catch:{ Throwable -> 0x0032 }
        r1 = r11.a;	 Catch:{ Throwable -> 0x0032 }
        if (r4 != r1) goto L_0x01c6;
    L_0x01b6:
        r0 = "[Upload] Use the back-up url at the last time: %s";
        r1 = 1;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x0032 }
        r2 = 0;
        r3 = r11.n;	 Catch:{ Throwable -> 0x0032 }
        r1[r2] = r3;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.d(r0, r1);	 Catch:{ Throwable -> 0x0032 }
        r0 = r11.n;	 Catch:{ Throwable -> 0x0032 }
    L_0x01c6:
        r1 = "[Upload] Send %d bytes";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0032 }
        r3 = 0;
        r8 = r6.length;	 Catch:{ Throwable -> 0x0032 }
        r8 = java.lang.Integer.valueOf(r8);	 Catch:{ Throwable -> 0x0032 }
        r2[r3] = r8;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.c(r1, r2);	 Catch:{ Throwable -> 0x0032 }
        r1 = r11.s;	 Catch:{ Throwable -> 0x0032 }
        if (r1 == 0) goto L_0x04f1;
    L_0x01db:
        r0 = a(r0);	 Catch:{ Throwable -> 0x0032 }
        r2 = r0;
    L_0x01e0:
        r0 = "[Upload] Upload to %s with cmd %d (pid=%d | tid=%d).";
        r1 = 4;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x0032 }
        r3 = 0;
        r1[r3] = r2;	 Catch:{ Throwable -> 0x0032 }
        r3 = 1;
        r8 = r11.d;	 Catch:{ Throwable -> 0x0032 }
        r8 = java.lang.Integer.valueOf(r8);	 Catch:{ Throwable -> 0x0032 }
        r1[r3] = r8;	 Catch:{ Throwable -> 0x0032 }
        r3 = 2;
        r8 = android.os.Process.myPid();	 Catch:{ Throwable -> 0x0032 }
        r8 = java.lang.Integer.valueOf(r8);	 Catch:{ Throwable -> 0x0032 }
        r1[r3] = r8;	 Catch:{ Throwable -> 0x0032 }
        r3 = 3;
        r8 = android.os.Process.myTid();	 Catch:{ Throwable -> 0x0032 }
        r8 = java.lang.Integer.valueOf(r8);	 Catch:{ Throwable -> 0x0032 }
        r1[r3] = r8;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.c(r0, r1);	 Catch:{ Throwable -> 0x0032 }
        r0 = r11.h;	 Catch:{ Throwable -> 0x0032 }
        r1 = r0.a(r2, r6, r11, r7);	 Catch:{ Throwable -> 0x0032 }
        if (r1 != 0) goto L_0x022f;
    L_0x0213:
        r0 = "Failed to upload for no response!";
        r1 = "[Upload] Failed to upload(%d): %s";
        r3 = 2;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x0032 }
        r8 = 0;
        r9 = 1;
        r9 = java.lang.Integer.valueOf(r9);	 Catch:{ Throwable -> 0x0032 }
        r3[r8] = r9;	 Catch:{ Throwable -> 0x0032 }
        r8 = 1;
        r3[r8] = r0;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.e(r1, r3);	 Catch:{ Throwable -> 0x0032 }
        r3 = 1;
        r1 = r4;
        r0 = r2;
        goto L_0x0193;
    L_0x022f:
        r0 = r11.h;	 Catch:{ Throwable -> 0x0032 }
        r3 = r0.a;	 Catch:{ Throwable -> 0x0032 }
        r0 = r11.s;	 Catch:{ Throwable -> 0x0032 }
        if (r0 == 0) goto L_0x0408;
    L_0x0237:
        if (r3 == 0) goto L_0x023f;
    L_0x0239:
        r0 = r3.size();	 Catch:{ Throwable -> 0x0032 }
        if (r0 != 0) goto L_0x02b6;
    L_0x023f:
        r0 = "[Upload] Headers is empty.";
        r8 = 0;
        r8 = new java.lang.Object[r8];	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.d(r0, r8);	 Catch:{ Throwable -> 0x0032 }
        r0 = 0;
    L_0x0249:
        if (r0 != 0) goto L_0x032a;
    L_0x024b:
        r0 = "[Upload] Headers from server is not valid, just try again (pid=%d | tid=%d).";
        r1 = 2;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x0032 }
        r8 = 0;
        r9 = android.os.Process.myPid();	 Catch:{ Throwable -> 0x0032 }
        r9 = java.lang.Integer.valueOf(r9);	 Catch:{ Throwable -> 0x0032 }
        r1[r8] = r9;	 Catch:{ Throwable -> 0x0032 }
        r8 = 1;
        r9 = android.os.Process.myTid();	 Catch:{ Throwable -> 0x0032 }
        r9 = java.lang.Integer.valueOf(r9);	 Catch:{ Throwable -> 0x0032 }
        r1[r8] = r9;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.c(r0, r1);	 Catch:{ Throwable -> 0x0032 }
        r0 = "[Upload] Failed to upload for no status header.";
        r1 = "[Upload] Failed to upload(%d): %s";
        r8 = 2;
        r8 = new java.lang.Object[r8];	 Catch:{ Throwable -> 0x0032 }
        r9 = 0;
        r10 = 1;
        r10 = java.lang.Integer.valueOf(r10);	 Catch:{ Throwable -> 0x0032 }
        r8[r9] = r10;	 Catch:{ Throwable -> 0x0032 }
        r9 = 1;
        r8[r9] = r0;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.e(r1, r8);	 Catch:{ Throwable -> 0x0032 }
        if (r3 == 0) goto L_0x031c;
    L_0x0283:
        r0 = r3.entrySet();	 Catch:{ Throwable -> 0x0032 }
        r1 = r0.iterator();	 Catch:{ Throwable -> 0x0032 }
    L_0x028b:
        r0 = r1.hasNext();	 Catch:{ Throwable -> 0x0032 }
        if (r0 == 0) goto L_0x031c;
    L_0x0291:
        r0 = r1.next();	 Catch:{ Throwable -> 0x0032 }
        r0 = (java.util.Map.Entry) r0;	 Catch:{ Throwable -> 0x0032 }
        r3 = "[key]: %s, [value]: %s";
        r8 = 2;
        r8 = new java.lang.Object[r8];	 Catch:{ Throwable -> 0x0032 }
        r9 = 0;
        r10 = r0.getKey();	 Catch:{ Throwable -> 0x0032 }
        r8[r9] = r10;	 Catch:{ Throwable -> 0x0032 }
        r9 = 1;
        r0 = r0.getValue();	 Catch:{ Throwable -> 0x0032 }
        r8[r9] = r0;	 Catch:{ Throwable -> 0x0032 }
        r0 = java.lang.String.format(r3, r8);	 Catch:{ Throwable -> 0x0032 }
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.c(r0, r3);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x028b;
    L_0x02b6:
        r0 = "status";
        r0 = r3.containsKey(r0);	 Catch:{ Throwable -> 0x0032 }
        if (r0 != 0) goto L_0x02d1;
    L_0x02bf:
        r0 = "[Upload] Headers does not contain %s";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ Throwable -> 0x0032 }
        r9 = 0;
        r10 = "status";
        r8[r9] = r10;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.d(r0, r8);	 Catch:{ Throwable -> 0x0032 }
        r0 = 0;
        goto L_0x0249;
    L_0x02d1:
        r0 = "Bugly-Version";
        r0 = r3.containsKey(r0);	 Catch:{ Throwable -> 0x0032 }
        if (r0 != 0) goto L_0x02ec;
    L_0x02da:
        r0 = "[Upload] Headers does not contain %s";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ Throwable -> 0x0032 }
        r9 = 0;
        r10 = "Bugly-Version";
        r8[r9] = r10;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.d(r0, r8);	 Catch:{ Throwable -> 0x0032 }
        r0 = 0;
        goto L_0x0249;
    L_0x02ec:
        r0 = "Bugly-Version";
        r0 = r3.get(r0);	 Catch:{ Throwable -> 0x0032 }
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x0032 }
        r8 = "bugly";
        r8 = r0.contains(r8);	 Catch:{ Throwable -> 0x0032 }
        if (r8 != 0) goto L_0x030d;
    L_0x02fe:
        r8 = "[Upload] Bugly version is not valid: %s";
        r9 = 1;
        r9 = new java.lang.Object[r9];	 Catch:{ Throwable -> 0x0032 }
        r10 = 0;
        r9[r10] = r0;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.d(r8, r9);	 Catch:{ Throwable -> 0x0032 }
        r0 = 0;
        goto L_0x0249;
    L_0x030d:
        r8 = "[Upload] Bugly version from headers is: %s";
        r9 = 1;
        r9 = new java.lang.Object[r9];	 Catch:{ Throwable -> 0x0032 }
        r10 = 0;
        r9[r10] = r0;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.c(r8, r9);	 Catch:{ Throwable -> 0x0032 }
        r0 = 1;
        goto L_0x0249;
    L_0x031c:
        r0 = "[Upload] Failed to upload for no status header.";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.c(r0, r1);	 Catch:{ Throwable -> 0x0032 }
        r3 = 1;
        r1 = r4;
        r0 = r2;
        goto L_0x0193;
    L_0x032a:
        r0 = "status";
        r0 = r3.get(r0);	 Catch:{ Throwable -> 0x03c1 }
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x03c1 }
        r5 = java.lang.Integer.parseInt(r0);	 Catch:{ Throwable -> 0x03c1 }
        r0 = "[Upload] Status from server is %d (pid=%d | tid=%d).";
        r8 = 3;
        r8 = new java.lang.Object[r8];	 Catch:{ Throwable -> 0x03c1 }
        r9 = 0;
        r10 = java.lang.Integer.valueOf(r5);	 Catch:{ Throwable -> 0x03c1 }
        r8[r9] = r10;	 Catch:{ Throwable -> 0x03c1 }
        r9 = 1;
        r10 = android.os.Process.myPid();	 Catch:{ Throwable -> 0x03c1 }
        r10 = java.lang.Integer.valueOf(r10);	 Catch:{ Throwable -> 0x03c1 }
        r8[r9] = r10;	 Catch:{ Throwable -> 0x03c1 }
        r9 = 2;
        r10 = android.os.Process.myTid();	 Catch:{ Throwable -> 0x03c1 }
        r10 = java.lang.Integer.valueOf(r10);	 Catch:{ Throwable -> 0x03c1 }
        r8[r9] = r10;	 Catch:{ Throwable -> 0x03c1 }
        com.tencent.bugly.imsdk.proguard.w.c(r0, r8);	 Catch:{ Throwable -> 0x03c1 }
        if (r5 == 0) goto L_0x0408;
    L_0x035f:
        r0 = 2;
        if (r5 != r0) goto L_0x03ef;
    L_0x0362:
        r0 = r11.q;	 Catch:{ Throwable -> 0x0032 }
        r2 = r11.r;	 Catch:{ Throwable -> 0x0032 }
        r0 = r0 + r2;
        r2 = 0;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 <= 0) goto L_0x0382;
    L_0x036d:
        r0 = r11.i;	 Catch:{ Throwable -> 0x0032 }
        r1 = r11.t;	 Catch:{ Throwable -> 0x0032 }
        r0 = r0.a(r1);	 Catch:{ Throwable -> 0x0032 }
        r2 = r11.q;	 Catch:{ Throwable -> 0x0032 }
        r0 = r0 + r2;
        r2 = r11.r;	 Catch:{ Throwable -> 0x0032 }
        r0 = r0 + r2;
        r2 = r11.i;	 Catch:{ Throwable -> 0x0032 }
        r3 = r11.t;	 Catch:{ Throwable -> 0x0032 }
        r2.a(r0, r3);	 Catch:{ Throwable -> 0x0032 }
    L_0x0382:
        r0 = r11.i;	 Catch:{ Throwable -> 0x0032 }
        r1 = 0;
        r0.a(r5, r1);	 Catch:{ Throwable -> 0x0032 }
        r0 = "[Upload] Session ID is invalid, will try again immediately (pid=%d | tid=%d).";
        r1 = 2;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x0032 }
        r2 = 0;
        r3 = android.os.Process.myPid();	 Catch:{ Throwable -> 0x0032 }
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ Throwable -> 0x0032 }
        r1[r2] = r3;	 Catch:{ Throwable -> 0x0032 }
        r2 = 1;
        r3 = android.os.Process.myTid();	 Catch:{ Throwable -> 0x0032 }
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ Throwable -> 0x0032 }
        r1[r2] = r3;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.a(r0, r1);	 Catch:{ Throwable -> 0x0032 }
        r0 = r11.i;	 Catch:{ Throwable -> 0x0032 }
        r1 = r11.j;	 Catch:{ Throwable -> 0x0032 }
        r2 = r11.d;	 Catch:{ Throwable -> 0x0032 }
        r3 = r11.e;	 Catch:{ Throwable -> 0x0032 }
        r4 = r11.m;	 Catch:{ Throwable -> 0x0032 }
        r5 = r11.n;	 Catch:{ Throwable -> 0x0032 }
        r6 = r11.k;	 Catch:{ Throwable -> 0x0032 }
        r7 = r11.a;	 Catch:{ Throwable -> 0x0032 }
        r8 = r11.b;	 Catch:{ Throwable -> 0x0032 }
        r9 = 1;
        r10 = r11.o;	 Catch:{ Throwable -> 0x0032 }
        r0.a(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x03c1:
        r0 = move-exception;
        r0 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0032 }
        r1 = "[Upload] Failed to upload for format of status header is invalid: ";
        r0.<init>(r1);	 Catch:{ Throwable -> 0x0032 }
        r1 = java.lang.Integer.toString(r5);	 Catch:{ Throwable -> 0x0032 }
        r0 = r0.append(r1);	 Catch:{ Throwable -> 0x0032 }
        r0 = r0.toString();	 Catch:{ Throwable -> 0x0032 }
        r1 = "[Upload] Failed to upload(%d): %s";
        r3 = 2;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x0032 }
        r8 = 0;
        r9 = 1;
        r9 = java.lang.Integer.valueOf(r9);	 Catch:{ Throwable -> 0x0032 }
        r3[r8] = r9;	 Catch:{ Throwable -> 0x0032 }
        r8 = 1;
        r3[r8] = r0;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.e(r1, r3);	 Catch:{ Throwable -> 0x0032 }
        r3 = 1;
        r1 = r4;
        r0 = r2;
        goto L_0x0193;
    L_0x03ef:
        r1 = 0;
        r2 = 0;
        r3 = 1;
        r0 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0032 }
        r4 = "status of server is ";
        r0.<init>(r4);	 Catch:{ Throwable -> 0x0032 }
        r0 = r0.append(r5);	 Catch:{ Throwable -> 0x0032 }
        r4 = r0.toString();	 Catch:{ Throwable -> 0x0032 }
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x0408:
        r0 = "[Upload] Received %d bytes";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0032 }
        r4 = 0;
        r6 = r1.length;	 Catch:{ Throwable -> 0x0032 }
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ Throwable -> 0x0032 }
        r2[r4] = r6;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.c(r0, r2);	 Catch:{ Throwable -> 0x0032 }
        r0 = r11.s;	 Catch:{ Throwable -> 0x0032 }
        if (r0 == 0) goto L_0x0482;
    L_0x041d:
        r0 = r1.length;	 Catch:{ Throwable -> 0x0032 }
        if (r0 != 0) goto L_0x0459;
    L_0x0420:
        r0 = r3.entrySet();	 Catch:{ Throwable -> 0x0032 }
        r1 = r0.iterator();	 Catch:{ Throwable -> 0x0032 }
    L_0x0428:
        r0 = r1.hasNext();	 Catch:{ Throwable -> 0x0032 }
        if (r0 == 0) goto L_0x044c;
    L_0x042e:
        r0 = r1.next();	 Catch:{ Throwable -> 0x0032 }
        r0 = (java.util.Map.Entry) r0;	 Catch:{ Throwable -> 0x0032 }
        r2 = "[Upload] HTTP headers from server: key = %s, value = %s";
        r3 = 2;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x0032 }
        r4 = 0;
        r5 = r0.getKey();	 Catch:{ Throwable -> 0x0032 }
        r3[r4] = r5;	 Catch:{ Throwable -> 0x0032 }
        r4 = 1;
        r0 = r0.getValue();	 Catch:{ Throwable -> 0x0032 }
        r3[r4] = r0;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.c(r2, r3);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0428;
    L_0x044c:
        r1 = 0;
        r2 = 0;
        r3 = 1;
        r4 = "response data from server is empty";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x0459:
        r0 = r11.i;	 Catch:{ Throwable -> 0x0032 }
        r0 = r0.b(r1);	 Catch:{ Throwable -> 0x0032 }
        if (r0 != 0) goto L_0x046e;
    L_0x0461:
        r1 = 0;
        r2 = 0;
        r3 = 1;
        r4 = "failed to decrypt response from server";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x046e:
        r1 = 2;
        r0 = com.tencent.bugly.imsdk.proguard.y.b(r0, r1);	 Catch:{ Throwable -> 0x0032 }
        if (r0 != 0) goto L_0x0483;
    L_0x0475:
        r1 = 0;
        r2 = 0;
        r3 = 1;
        r4 = "failed unzip(Gzip) response from server";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x0482:
        r0 = r1;
    L_0x0483:
        r1 = r11.s;	 Catch:{ Throwable -> 0x0032 }
        r1 = com.tencent.bugly.imsdk.proguard.a.a(r0, r1);	 Catch:{ Throwable -> 0x0032 }
        if (r1 != 0) goto L_0x0498;
    L_0x048b:
        r1 = 0;
        r2 = 0;
        r3 = 1;
        r4 = "failed to decode response package";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x0498:
        r0 = r11.s;	 Catch:{ Throwable -> 0x0032 }
        if (r0 == 0) goto L_0x04a1;
    L_0x049c:
        r0 = r11.i;	 Catch:{ Throwable -> 0x0032 }
        r0.a(r5, r1);	 Catch:{ Throwable -> 0x0032 }
    L_0x04a1:
        r2 = "[Upload] Response cmd is: %d, length of sBuffer is: %d";
        r0 = 2;
        r3 = new java.lang.Object[r0];	 Catch:{ Throwable -> 0x0032 }
        r0 = 0;
        r4 = r1.b;	 Catch:{ Throwable -> 0x0032 }
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x0032 }
        r3[r0] = r4;	 Catch:{ Throwable -> 0x0032 }
        r4 = 1;
        r0 = r1.c;	 Catch:{ Throwable -> 0x0032 }
        if (r0 != 0) goto L_0x04d5;
    L_0x04b5:
        r0 = 0;
    L_0x04b6:
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ Throwable -> 0x0032 }
        r3[r4] = r0;	 Catch:{ Throwable -> 0x0032 }
        com.tencent.bugly.imsdk.proguard.w.c(r2, r3);	 Catch:{ Throwable -> 0x0032 }
        r0 = r11.f;	 Catch:{ Throwable -> 0x0032 }
        r2 = r11.g;	 Catch:{ Throwable -> 0x0032 }
        r0 = a(r1, r0, r2);	 Catch:{ Throwable -> 0x0032 }
        if (r0 != 0) goto L_0x04d9;
    L_0x04c9:
        r2 = 0;
        r3 = 2;
        r4 = "failed to process response package";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x04d5:
        r0 = r1.c;	 Catch:{ Throwable -> 0x0032 }
        r0 = r0.length;	 Catch:{ Throwable -> 0x0032 }
        goto L_0x04b6;
    L_0x04d9:
        r2 = 1;
        r3 = 2;
        r4 = "successfully uploaded";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x04e5:
        r1 = 0;
        r2 = 0;
        r4 = "failed after many attempts";
        r5 = 0;
        r0 = r11;
        r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0020;
    L_0x04f1:
        r2 = r0;
        goto L_0x01e0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.proguard.u.run():void");
    }

    public final void a(long j) {
        this.p++;
        this.q += j;
    }

    public final void b(long j) {
        this.r += j;
    }

    private static String a(String str) {
        if (y.a(str)) {
            return str;
        }
        try {
            return String.format("%s?aid=%s", new Object[]{str, UUID.randomUUID().toString()});
        } catch (Throwable th) {
            w.a(th);
            return str;
        }
    }
}
