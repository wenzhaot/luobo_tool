package com.talkingdata.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import com.feng.car.utils.HttpConstant;
import com.taobao.accs.utl.UtilityImpl;

/* compiled from: td */
public final class cq {
    static Handler a = null;
    static HandlerThread b = null;
    private static final String c = "check_wifi_permission";
    private static final String d = "check_bs_permission";
    private static final String e = "check_gps_permission";
    private static final int f = 1;
    private static final int g = 2;
    private static final int h = 3;
    private static final int i = 4;
    private static final long j = 600000;
    private static volatile cq k = null;
    private static WifiManager l;

    static {
        try {
            br.a().register(a());
        } catch (Throwable th) {
        }
    }

    public static cq a() {
        if (k == null) {
            synchronized (cq.class) {
                if (k == null) {
                    k = new cq();
                }
            }
        }
        return k;
    }

    private cq() {
        try {
            b = new HandlerThread("locHandlerThread");
            b.start();
            a = new cr(this, b.getLooper());
            a(4, 0);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    protected void a(int i, long j) {
        try {
            Message obtain = Message.obtain();
            obtain.what = i;
            a.sendMessageDelayed(obtain, j);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public final void onTDEBEventLocationEvent(da daVar) {
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x007f  */
    protected boolean a(java.lang.String r9) {
        /*
        r8 = this;
        r6 = 23;
        r1 = 0;
        r2 = 1;
        r0 = com.talkingdata.sdk.bo.a(r6);
        if (r0 == 0) goto L_0x0050;
    L_0x000a:
        r0 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x0047 }
        r3 = "android.permission.READ_PHONE_STATE";
        r0 = r0.checkSelfPermission(r3);	 Catch:{ Throwable -> 0x0047 }
        if (r0 != 0) goto L_0x00b4;
    L_0x0015:
        r3 = r2;
    L_0x0016:
        r0 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x00a8 }
        r4 = "android.permission.ACCESS_COARSE_LOCATION";
        r0 = r0.checkSelfPermission(r4);	 Catch:{ Throwable -> 0x00a8 }
        if (r0 == 0) goto L_0x002c;
    L_0x0021:
        r0 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x00a8 }
        r4 = "android.permission.ACCESS_FINE_LOCATION";
        r0 = r0.checkSelfPermission(r4);	 Catch:{ Throwable -> 0x00a8 }
        if (r0 != 0) goto L_0x00af;
    L_0x002c:
        r0 = r2;
    L_0x002d:
        r4 = com.talkingdata.sdk.ab.g;
        r5 = "android.permission.ACCESS_WIFI_STATE";
        r4 = com.talkingdata.sdk.bo.b(r4, r5);
        if (r4 == 0) goto L_0x00ad;
    L_0x0038:
        r4 = r2;
    L_0x0039:
        r5 = "check_bs_permission";
        r5 = r9.equals(r5);
        if (r5 == 0) goto L_0x007f;
    L_0x0042:
        if (r0 == 0) goto L_0x007d;
    L_0x0044:
        if (r3 == 0) goto L_0x007d;
    L_0x0046:
        return r2;
    L_0x0047:
        r0 = move-exception;
        r3 = r0;
        r0 = r1;
    L_0x004a:
        com.talkingdata.sdk.cs.postSDKError(r3);
        r3 = r0;
        r0 = r1;
        goto L_0x002d;
    L_0x0050:
        r0 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x0074 }
        r3 = "android.permission.READ_PHONE_STATE";
        r0 = com.talkingdata.sdk.bo.b(r0, r3);	 Catch:{ Throwable -> 0x0074 }
        if (r0 == 0) goto L_0x00b2;
    L_0x005b:
        r3 = r2;
    L_0x005c:
        r0 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x00a3 }
        r4 = "android.permission.ACCESS_COARSE_LOCATION";
        r0 = com.talkingdata.sdk.bo.b(r0, r4);	 Catch:{ Throwable -> 0x00a3 }
        if (r0 != 0) goto L_0x0072;
    L_0x0067:
        r0 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x00a3 }
        r4 = "android.permission.ACCESS_FINE_LOCATION";
        r0 = com.talkingdata.sdk.bo.b(r0, r4);	 Catch:{ Throwable -> 0x00a3 }
        if (r0 == 0) goto L_0x00af;
    L_0x0072:
        r0 = r2;
        goto L_0x002d;
    L_0x0074:
        r0 = move-exception;
        r3 = r0;
        r0 = r1;
    L_0x0077:
        com.talkingdata.sdk.cs.postSDKError(r3);
        r3 = r0;
        r0 = r1;
        goto L_0x002d;
    L_0x007d:
        r2 = r1;
        goto L_0x0046;
    L_0x007f:
        r3 = "check_gps_permission";
        r3 = r9.equals(r3);
        if (r3 == 0) goto L_0x008a;
    L_0x0088:
        r2 = r0;
        goto L_0x0046;
    L_0x008a:
        r3 = "check_wifi_permission";
        r3 = r9.equals(r3);
        if (r3 == 0) goto L_0x00a1;
    L_0x0093:
        r3 = com.talkingdata.sdk.bo.a(r6);
        if (r3 == 0) goto L_0x009f;
    L_0x0099:
        if (r4 == 0) goto L_0x009d;
    L_0x009b:
        if (r0 != 0) goto L_0x0046;
    L_0x009d:
        r2 = r1;
        goto L_0x0046;
    L_0x009f:
        r2 = r4;
        goto L_0x0046;
    L_0x00a1:
        r2 = r1;
        goto L_0x0046;
    L_0x00a3:
        r0 = move-exception;
        r7 = r0;
        r0 = r3;
        r3 = r7;
        goto L_0x0077;
    L_0x00a8:
        r0 = move-exception;
        r7 = r0;
        r0 = r3;
        r3 = r7;
        goto L_0x004a;
    L_0x00ad:
        r4 = r1;
        goto L_0x0039;
    L_0x00af:
        r0 = r1;
        goto L_0x002d;
    L_0x00b2:
        r3 = r1;
        goto L_0x005c;
    L_0x00b4:
        r3 = r1;
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.cq.a(java.lang.String):boolean");
    }

    private void b() {
        try {
            if (a(c)) {
                Context context = ab.g;
                Context context2 = ab.g;
                l = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
                if (l.isWifiEnabled()) {
                    ab.g.registerReceiver(new cy(l), new IntentFilter("android.net.wifi.SCAN_RESULTS"));
                    return;
                }
                return;
            }
            a(1, 180000);
        } catch (Throwable th) {
        }
    }

    private void c() {
        if (a(e)) {
            try {
                dd ddVar = new dd();
                ddVar.b = "env";
                ddVar.c = "locationUpdate";
                ddVar.a = a.ENV;
                br.a().post(ddVar);
                a(2, j);
                return;
            } catch (Throwable th) {
                cs.postSDKError(th);
                return;
            }
        }
        a(2, j);
    }

    @TargetApi(23)
    private void d() {
        try {
            if (!a(d)) {
                a(3, 180000);
            } else if (ab.g != null) {
                Context context = ab.g;
                Context context2 = ab.g;
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
                if (telephonyManager.getSimState() == 5) {
                    telephonyManager.getCellLocation();
                    telephonyManager.listen(new cw(), 16);
                    CellLocation.requestLocationUpdate();
                }
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }
}
