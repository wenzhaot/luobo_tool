package com.umeng.commonsdk.internal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.stub.StubApp;
import com.umeng.commonsdk.proguard.b;
import com.umeng.commonsdk.statistics.common.e;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONObject;

/* compiled from: UMProbe */
public class l {
    public static final String a = "UM_PROBE_DATA";
    public static final String b = "_dsk_s";
    public static final String c = "_thm_z";
    public static final String d = "_gdf_r";
    private static Object e = new Object();

    public static String a(Context context) {
        String str = null;
        try {
            SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences(a, 0);
            if (sharedPreferences == null) {
                return str;
            }
            JSONObject jSONObject = new JSONObject();
            synchronized (e) {
                jSONObject.put(b, sharedPreferences.getString(b, ""));
                jSONObject.put(c, sharedPreferences.getString(c, ""));
                jSONObject.put(d, sharedPreferences.getString(d, ""));
            }
            return jSONObject.toString();
        } catch (Throwable e) {
            b.a(context, e);
            return str;
        }
    }

    public static void b(final Context context) {
        if (!c(context)) {
            final String[] strArr = new String[]{"unknown", "unknown", "unknown"};
            new Thread() {
                public void run() {
                    super.run();
                    strArr[0] = l.c();
                    strArr[1] = l.a();
                    strArr[2] = l.b();
                    e.c("diskType = " + strArr[0] + "; ThremalZone = " + strArr[1] + "; GoldFishRc = " + strArr[2]);
                    l.b(context, strArr);
                }
            }.start();
        }
    }

    private static void b(Context context, String[] strArr) {
        if (context != null) {
            SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences(a, 0);
            if (sharedPreferences != null) {
                synchronized (e) {
                    sharedPreferences.edit().putString(b, strArr[0]).putString(c, strArr[1]).putString(d, strArr[2]).commit();
                }
            }
        }
    }

    public static boolean c(Context context) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences(a, 0);
        if (sharedPreferences == null || TextUtils.isEmpty(sharedPreferences.getString(b, ""))) {
            return false;
        }
        return true;
    }

    public static int a(String str, String str2) throws IOException {
        int i;
        Process exec = Runtime.getRuntime().exec(str);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        String str3 = "";
        do {
            str3 = bufferedReader.readLine();
            if (str3 == null) {
                i = -1;
                break;
            }
        } while (!str3.contains(str2));
        i = 1;
        try {
            if (exec.waitFor() != 0) {
                return -1;
            }
            return i;
        } catch (InterruptedException e) {
            return -1;
        }
    }

    public static String a() {
        String str = "unknown";
        int i = -1;
        try {
            i = a("ls /sys/class/thermal", "thermal_zone");
        } catch (IOException e) {
        }
        if (i > 0) {
            return "thermal_zone";
        }
        if (i < 0) {
            return "noper";
        }
        return str;
    }

    public static String b() {
        String str = "unknown";
        int i = -1;
        try {
            i = a("ls /", "goldfish");
        } catch (IOException e) {
        }
        if (i > 0) {
            return "goldfish";
        }
        if (i < 0) {
            return "noper";
        }
        return str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0030 A:{SYNTHETIC, Splitter: B:12:0x0030} */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0030 A:{SYNTHETIC, Splitter: B:12:0x0030} */
    public static java.lang.String c() {
        /*
        r0 = "unknown";
        r2 = 0;
        r1 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x0048 }
        r3 = new java.io.FileReader;	 Catch:{ Exception -> 0x0048 }
        r4 = "/proc/diskstats";
        r3.<init>(r4);	 Catch:{ Exception -> 0x0048 }
        r1.<init>(r3);	 Catch:{ Exception -> 0x0048 }
        r2 = "";
        r2 = "mmcblk";
        r3 = "sda";
        r4 = "mtd";
        if (r1 == 0) goto L_0x002e;
    L_0x001f:
        r5 = r1.readLine();	 Catch:{ Exception -> 0x0053 }
        if (r5 == 0) goto L_0x002e;
    L_0x0025:
        r6 = r5.contains(r2);	 Catch:{ Exception -> 0x0053 }
        if (r6 == 0) goto L_0x0034;
    L_0x002b:
        r0 = "mmcblk";
    L_0x002e:
        if (r1 == 0) goto L_0x0033;
    L_0x0030:
        r1.close();	 Catch:{ Throwable -> 0x0051 }
    L_0x0033:
        return r0;
    L_0x0034:
        r6 = r5.contains(r3);	 Catch:{ Exception -> 0x0053 }
        if (r6 == 0) goto L_0x003e;
    L_0x003a:
        r0 = "sda";
        goto L_0x002e;
    L_0x003e:
        r5 = r5.contains(r4);	 Catch:{ Exception -> 0x0053 }
        if (r5 == 0) goto L_0x001f;
    L_0x0044:
        r0 = "mtd";
        goto L_0x002e;
    L_0x0048:
        r0 = move-exception;
        r0 = r2;
    L_0x004a:
        r1 = "noper";
        r7 = r0;
        r0 = r1;
        r1 = r7;
        goto L_0x002e;
    L_0x0051:
        r1 = move-exception;
        goto L_0x0033;
    L_0x0053:
        r0 = move-exception;
        r0 = r1;
        goto L_0x004a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.internal.utils.l.c():java.lang.String");
    }
}
