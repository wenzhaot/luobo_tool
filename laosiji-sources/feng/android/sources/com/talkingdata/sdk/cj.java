package com.talkingdata.sdk;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.feng.car.utils.HttpConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: td */
public final class cj {
    private static volatile cj a = null;
    private static Map b = new TreeMap();
    private static final int c = 10800000;

    public static cj a() {
        if (a == null) {
            synchronized (cj.class) {
                if (a == null) {
                    a = new cj();
                }
            }
        }
        return a;
    }

    private cj() {
        try {
            if (c()) {
                b();
                d();
                e();
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private void b() {
        try {
            String str = "";
            List arrayList = new ArrayList();
            if (bo.a(21)) {
                for (am amVar : bj.a()) {
                    if (!(amVar.c.startsWith("android.") || amVar.c.equals(HttpConstant.SYSTEM))) {
                        arrayList.add(amVar.c);
                    }
                }
            } else {
                ActivityManager activityManager = (ActivityManager) ab.g.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
                if (activityManager != null) {
                    PackageManager packageManager = ab.g.getPackageManager();
                    List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
                    if (runningAppProcesses != null) {
                        for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                            str = runningAppProcessInfo.processName;
                            try {
                                if (packageManager.getLaunchIntentForPackage(str) != null) {
                                    arrayList.add(str);
                                }
                            } catch (Throwable th) {
                            }
                        }
                    }
                }
            }
            b.put("ras", arrayList.toString());
            ar.setCollectRunningTime(System.currentTimeMillis());
        } catch (Throwable th2) {
            cs.postSDKError(th2);
        }
    }

    private boolean c() {
        try {
            if (System.currentTimeMillis() - ar.g() > 10800000) {
                return true;
            }
        } catch (Throwable th) {
        }
        return false;
    }

    private void d() {
        try {
            b.put("aas", a(ab.g).toString());
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private List a(Context context) {
        List arrayList = new ArrayList();
        try {
            PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
            if (installedPackages != null) {
                for (PackageInfo packageInfo : installedPackages) {
                    arrayList.add(packageInfo.packageName);
                    arrayList.add(bo.b(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString().getBytes()));
                    if ((packageInfo.applicationInfo.flags & 1) > 0) {
                        arrayList.add(PushConstants.PUSH_TYPE_THROUGH_MESSAGE);
                    } else {
                        arrayList.add(PushConstants.PUSH_TYPE_NOTIFY);
                    }
                }
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:4:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:4:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:14:? A:{SYNTHETIC, RETURN} */
    private void e() {
        /*
        r4 = this;
        r2 = new com.talkingdata.sdk.dd;
        r2.<init>();
        r1 = 0;
        r0 = com.talkingdata.sdk.ck.a();	 Catch:{ Throwable -> 0x0053 }
        r0 = r0.b();	 Catch:{ Throwable -> 0x0053 }
        com.talkingdata.sdk.bb.getFileLock(r0);	 Catch:{ Throwable -> 0x0053 }
        r0 = com.talkingdata.sdk.ck.a();	 Catch:{ Throwable -> 0x0053 }
        r3 = "AppList";
        r0 = r0.a(r3);	 Catch:{ Throwable -> 0x0053 }
        r1 = com.talkingdata.sdk.ck.a();
        r1 = r1.b();
        com.talkingdata.sdk.bb.releaseFileLock(r1);
    L_0x0027:
        if (r0 == 0) goto L_0x0052;
    L_0x0029:
        r1 = r0.length();
        if (r1 <= 0) goto L_0x0039;
    L_0x002f:
        r1 = new android.util.Pair;
        r3 = "AppList";
        r1.<init>(r3, r0);
        r2.e = r1;
    L_0x0039:
        r0 = "env";
        r2.b = r0;
        r0 = "apps";
        r2.c = r0;
        r0 = b;
        r2.d = r0;
        r0 = com.talkingdata.sdk.a.ENV;
        r2.a = r0;
        r0 = com.talkingdata.sdk.br.a();
        r0.post(r2);
    L_0x0052:
        return;
    L_0x0053:
        r0 = move-exception;
        com.talkingdata.sdk.cs.postSDKError(r0);	 Catch:{ all -> 0x0064 }
        r0 = com.talkingdata.sdk.ck.a();
        r0 = r0.b();
        com.talkingdata.sdk.bb.releaseFileLock(r0);
        r0 = r1;
        goto L_0x0027;
    L_0x0064:
        r0 = move-exception;
        r1 = com.talkingdata.sdk.ck.a();
        r1 = r1.b();
        com.talkingdata.sdk.bb.releaseFileLock(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.cj.e():void");
    }
}
