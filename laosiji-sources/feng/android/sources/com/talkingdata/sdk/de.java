package com.talkingdata.sdk;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import com.feng.car.utils.PermissionsConstant;
import com.qiniu.android.common.Constants;
import com.talkingdata.sdk.dc.a;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/* compiled from: td */
public class de {
    private static String a = Constants.UTF_8;
    private static final CRC32 b = new CRC32();
    private static final int c = 5;
    private static final int d = 30000;
    private static final boolean g = true;
    private static volatile de h = null;
    private static HandlerThread i;
    private long e = 0;
    private boolean f = true;
    private Handler j = null;

    static {
        try {
            br.a().register(a());
        } catch (Throwable th) {
        }
    }

    private void d() {
        int i = ab.I;
        if (!this.j.hasMessages(5)) {
            try {
                int i2;
                SecureRandom b = bo.b();
                int[] a = ab.a();
                if (bd.g(ab.g)) {
                    if (this.f) {
                        i2 = a[1];
                    } else {
                        i2 = b.nextInt(30000) + (a[1] * 1);
                    }
                } else if (this.f) {
                    i2 = a[0];
                } else {
                    i2 = (b.nextInt(PermissionsConstant.CODE_FOR_DOWN_WRITE_PERMISSION_BASE) - 30000) + (a[0] * 1);
                }
                if (i2 <= ab.I) {
                    i = i2;
                }
                Iterator it = a.getFeaturesList().iterator();
                while (it.hasNext()) {
                    this.j.sendMessageDelayed(Message.obtain(this.j, 5, (a) it.next()), (long) i);
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    private static boolean e() {
        try {
            Calendar instance = Calendar.getInstance();
            long j = (long) ((instance.get(6) * 1000) + instance.get(3));
            instance.setTimeInMillis(ar.e());
            long j2 = (long) (instance.get(3) + (instance.get(6) * 1000));
            long b = bi.b(ab.g, ab.u, ab.x, 0);
            if (System.currentTimeMillis() - ar.e() < 5000 || Math.abs((j2 / 1000) - (j / 1000)) == 1 || Math.abs((j % 100) - (b % 100)) >= 1) {
                return true;
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        return false;
    }

    private static boolean f() {
        try {
            Calendar instance = Calendar.getInstance();
            long j = (long) ((instance.get(6) * 1000) + instance.get(3));
            instance.setTimeInMillis(ar.e());
            long j2 = (long) (instance.get(3) + (instance.get(6) * 1000));
            long b = bi.b(ab.g, ab.u, ab.y, 0);
            if (System.currentTimeMillis() - ar.e() < 5000 || Math.abs((j2 / 1000) - (j / 1000)) == 1 || Math.abs((j % 100) - (b % 100)) >= 1) {
                return true;
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        return false;
    }

    private void a(String str, a aVar, boolean z) {
        String str2 = "[" + aVar.name() + "] " + str;
        switch (aVar.index()) {
            case 0:
            case 1:
            case 3:
            case 7:
            case 8:
                if (z) {
                    aq.iForDeveloper(str2);
                    return;
                }
                aq.dForInternal(str2);
                return;
            case 4:
                if (z) {
                    aq.iForDeveloper("[Push] " + str);
                    return;
                }
                return;
            default:
                aq.iForInternal(str2);
                return;
        }
    }

    /* JADX WARNING: Failed to extract finally block: empty outs */
    private void a(com.talkingdata.sdk.a r7) {
        /*
        r6 = this;
        r1 = 0;
        r0 = com.talkingdata.sdk.ab.g;
        r0 = com.talkingdata.sdk.bd.c(r0);
        if (r0 != 0) goto L_0x000a;
    L_0x0009:
        return;
    L_0x000a:
        r0 = r7.index();	 Catch:{ Throwable -> 0x0104 }
        r0 = com.talkingdata.sdk.bb.b.getFeatureLockFileName(r0);	 Catch:{ Throwable -> 0x0104 }
        r1 = com.talkingdata.sdk.bb.a(r0);	 Catch:{ Throwable -> 0x0104 }
        if (r1 != 0) goto L_0x0026;
    L_0x0018:
        if (r1 == 0) goto L_0x0009;
    L_0x001a:
        r0 = r7.index();
        r0 = com.talkingdata.sdk.bb.b.getFeatureLockFileName(r0);
        com.talkingdata.sdk.bb.releaseFileLock(r0);
        goto L_0x0009;
    L_0x0026:
        r0 = com.talkingdata.sdk.dh.b();	 Catch:{ Throwable -> 0x0104 }
        r0 = r0.a(r7);	 Catch:{ Throwable -> 0x0104 }
        if (r0 == 0) goto L_0x0036;
    L_0x0030:
        r2 = r0.size();	 Catch:{ Throwable -> 0x0104 }
        if (r2 > 0) goto L_0x004b;
    L_0x0036:
        r0 = "No new data found!";
        r2 = 0;
        r6.a(r0, r7, r2);	 Catch:{ Throwable -> 0x0104 }
        if (r1 == 0) goto L_0x0009;
    L_0x003f:
        r0 = r7.index();
        r0 = com.talkingdata.sdk.bb.b.getFeatureLockFileName(r0);
        com.talkingdata.sdk.bb.releaseFileLock(r0);
        goto L_0x0009;
    L_0x004b:
        r2 = "New data found, Submitting...";
        r3 = 1;
        r6.a(r2, r7, r3);	 Catch:{ Throwable -> 0x0104 }
        r0 = a(r0);	 Catch:{ Throwable -> 0x0104 }
        r0 = com.talkingdata.sdk.bo.e(r0);	 Catch:{ Throwable -> 0x0104 }
        r2 = b;	 Catch:{ Throwable -> 0x0104 }
        r2.reset();	 Catch:{ Throwable -> 0x0104 }
        r2 = b;	 Catch:{ Throwable -> 0x0104 }
        r2.update(r0);	 Catch:{ Throwable -> 0x0104 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0104 }
        r3 = r7.getUrl();	 Catch:{ Throwable -> 0x0104 }
        r2.<init>(r3);	 Catch:{ Throwable -> 0x0104 }
        r3 = r7.name();	 Catch:{ Throwable -> 0x0104 }
        r4 = "TRACKING";
        r3 = r3.equals(r4);	 Catch:{ Throwable -> 0x0104 }
        if (r3 == 0) goto L_0x00e2;
    L_0x007a:
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0104 }
        r3.<init>();	 Catch:{ Throwable -> 0x0104 }
        r4 = "/";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x0104 }
        r4 = b;	 Catch:{ Throwable -> 0x0104 }
        r4 = r4.getValue();	 Catch:{ Throwable -> 0x0104 }
        r4 = java.lang.Long.toHexString(r4);	 Catch:{ Throwable -> 0x0104 }
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x0104 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x0104 }
        r2.append(r3);	 Catch:{ Throwable -> 0x0104 }
        r3 = "/1";
        r2.append(r3);	 Catch:{ Throwable -> 0x0104 }
    L_0x00a1:
        r3 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x0104 }
        r2 = r2.toString();	 Catch:{ Throwable -> 0x0104 }
        r4 = "application/octet-stream";
        r0 = com.talkingdata.sdk.as.a(r3, r7, r2, r0, r4);	 Catch:{ Throwable -> 0x0104 }
        r0 = r0.getStatusCode();	 Catch:{ Throwable -> 0x0104 }
        r2 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r0 != r2) goto L_0x0117;
    L_0x00b6:
        r2 = android.os.SystemClock.elapsedRealtime();	 Catch:{ Throwable -> 0x0104 }
        r6.e = r2;	 Catch:{ Throwable -> 0x0104 }
        r0 = 1;
        r6.f = r0;	 Catch:{ Throwable -> 0x0104 }
        r0 = com.talkingdata.sdk.dh.b();	 Catch:{ Throwable -> 0x0104 }
        r0.sendMessageSuccess(r7);	 Catch:{ Throwable -> 0x0104 }
        r0 = "Data submitted successfully!";
        r2 = 1;
        r6.a(r0, r7, r2);	 Catch:{ Throwable -> 0x0104 }
        r0 = com.talkingdata.sdk.ab.H;	 Catch:{ Throwable -> 0x0104 }
        r2 = 0;
        r0.set(r2);	 Catch:{ Throwable -> 0x0104 }
    L_0x00d3:
        if (r1 == 0) goto L_0x0009;
    L_0x00d5:
        r0 = r7.index();
        r0 = com.talkingdata.sdk.bb.b.getFeatureLockFileName(r0);
        com.talkingdata.sdk.bb.releaseFileLock(r0);
        goto L_0x0009;
    L_0x00e2:
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0104 }
        r3.<init>();	 Catch:{ Throwable -> 0x0104 }
        r4 = "/";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x0104 }
        r4 = b;	 Catch:{ Throwable -> 0x0104 }
        r4 = r4.getValue();	 Catch:{ Throwable -> 0x0104 }
        r4 = java.lang.Long.toHexString(r4);	 Catch:{ Throwable -> 0x0104 }
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x0104 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x0104 }
        r2.append(r3);	 Catch:{ Throwable -> 0x0104 }
        goto L_0x00a1;
    L_0x0104:
        r0 = move-exception;
        com.talkingdata.sdk.cs.postSDKError(r0);	 Catch:{ all -> 0x0127 }
        if (r1 == 0) goto L_0x0009;
    L_0x010a:
        r0 = r7.index();
        r0 = com.talkingdata.sdk.bb.b.getFeatureLockFileName(r0);
        com.talkingdata.sdk.bb.releaseFileLock(r0);
        goto L_0x0009;
    L_0x0117:
        r0 = com.talkingdata.sdk.ab.H;	 Catch:{ Throwable -> 0x0104 }
        r0.incrementAndGet();	 Catch:{ Throwable -> 0x0104 }
        r0 = 0;
        r6.f = r0;	 Catch:{ Throwable -> 0x0104 }
        r0 = "Failed to submit data!";
        r2 = 1;
        r6.a(r0, r7, r2);	 Catch:{ Throwable -> 0x0104 }
        goto L_0x00d3;
    L_0x0127:
        r0 = move-exception;
        if (r1 == 0) goto L_0x0135;
    L_0x012a:
        r1 = r7.index();
        r1 = com.talkingdata.sdk.bb.b.getFeatureLockFileName(r1);
        com.talkingdata.sdk.bb.releaseFileLock(r1);
    L_0x0135:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.de.a(com.talkingdata.sdk.a):void");
    }

    private static String a(List list) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (String append : list) {
            stringBuilder.append(append);
            stringBuilder.append(MiPushClient.ACCEPT_TIME_SEPARATOR);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static byte[] a(byte[] bArr) {
        byte[] bArr2 = null;
        byte[] bArr3 = new byte[2048];
        BufferedInputStream bufferedInputStream;
        try {
            bufferedInputStream = new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(bArr), new Inflater(false)));
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int read = bufferedInputStream.read(bArr3);
                    if (read != -1) {
                        byteArrayOutputStream.write(bArr3, 0, read);
                    } else {
                        byteArrayOutputStream.close();
                        bufferedInputStream.close();
                        return byteArrayOutputStream.toByteArray();
                    }
                }
            } catch (Exception e) {
            }
        } catch (Exception e2) {
            bufferedInputStream = bArr2;
            if (bufferedInputStream == null) {
                return bArr2;
            }
            try {
                bufferedInputStream.close();
                return bArr2;
            } catch (IOException e3) {
                e3.printStackTrace();
                return bArr2;
            }
        }
    }

    private static void g() {
        if (bd.c(ab.g)) {
            try {
                bl.a.execute(new df());
            } catch (Throwable th) {
            }
        }
    }

    public final void onTDEBEventForwardRequest(dc dcVar) {
        long j = 30000;
        if (dcVar != null && ab.g != null) {
            if (dcVar.b.equals(a.IMMEDIATELY)) {
                if (this.j.hasMessages(5, dcVar.a)) {
                    this.j.removeMessages(5);
                }
                Message.obtain(this.j, 5, dcVar.a).sendToTarget();
            } else if (dcVar.b.equals(a.HIGH)) {
                if (this.j.hasMessages(5)) {
                    this.j.removeMessages(5);
                }
                long abs = Math.abs((SystemClock.elapsedRealtime() - this.e) - 30000);
                if (abs <= 30000) {
                    j = abs;
                }
                this.j.sendMessageDelayed(Message.obtain(this.j, 5, dcVar.a), j);
            }
        }
    }

    public static de a() {
        if (h == null) {
            synchronized (de.class) {
                if (h == null) {
                    h = new de();
                }
            }
        }
        return h;
    }

    private de() {
        i = new HandlerThread("ModuleDataForward");
        i.start();
        this.j = new dg(this, i.getLooper());
        g();
        d();
    }
}
