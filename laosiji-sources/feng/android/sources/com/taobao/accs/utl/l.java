package com.taobao.accs.utl;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.text.TextUtils;
import com.taobao.accs.common.Constants;
import com.taobao.accs.d.a;

/* compiled from: Taobao */
public class l {
    public static final String SP_AGOO_BIND_FILE_NAME = "AGOO_BIND";
    private static final byte[] a = new byte[0];

    public static void a(Context context, int i) {
        try {
            synchronized (a) {
                ALog.i("Utils", "setMode", Constants.KEY_MODE, Integer.valueOf(i));
                Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                edit.putInt(Constants.SP_KEY_DEBUG_MODE, i);
                edit.commit();
            }
        } catch (Throwable th) {
            ALog.e("Utils", "setMode", th, new Object[0]);
        }
    }

    /* JADX WARNING: Missing block: B:16:0x0033, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:17:0x0034, code:
            r8 = r0;
            r0 = r1;
            r1 = r8;
     */
    public static int a(android.content.Context r9) {
        /*
        r2 = 0;
        r3 = a;	 Catch:{ Throwable -> 0x0043 }
        monitor-enter(r3);	 Catch:{ Throwable -> 0x0043 }
        r0 = "ACCS_SDK";
        r1 = 0;
        r0 = r9.getSharedPreferences(r0, r1);	 Catch:{ all -> 0x002f }
        r1 = "debug_mode";
        r4 = 0;
        r0 = r0.getInt(r1, r4);	 Catch:{ all -> 0x002f }
        r1 = "Utils";
        r4 = "getMode";
        r5 = 2;
        r5 = new java.lang.Object[r5];	 Catch:{ all -> 0x0047 }
        r6 = 0;
        r7 = "mode";
        r5[r6] = r7;	 Catch:{ all -> 0x0047 }
        r6 = 1;
        r7 = java.lang.Integer.valueOf(r0);	 Catch:{ all -> 0x0047 }
        r5[r6] = r7;	 Catch:{ all -> 0x0047 }
        com.taobao.accs.utl.ALog.i(r1, r4, r5);	 Catch:{ all -> 0x0047 }
        monitor-exit(r3);	 Catch:{ all -> 0x0047 }
    L_0x002e:
        return r0;
    L_0x002f:
        r0 = move-exception;
        r1 = r2;
    L_0x0031:
        monitor-exit(r3);	 Catch:{ all -> 0x004c }
        throw r0;	 Catch:{ Throwable -> 0x0033 }
    L_0x0033:
        r0 = move-exception;
        r8 = r0;
        r0 = r1;
        r1 = r8;
    L_0x0037:
        r3 = "Utils";
        r4 = "getMode";
        r2 = new java.lang.Object[r2];
        com.taobao.accs.utl.ALog.e(r3, r4, r1, r2);
        goto L_0x002e;
    L_0x0043:
        r0 = move-exception;
        r1 = r0;
        r0 = r2;
        goto L_0x0037;
    L_0x0047:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
        goto L_0x0031;
    L_0x004c:
        r0 = move-exception;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.utl.l.a(android.content.Context):int");
    }

    public static void b(Context context) {
        try {
            synchronized (a) {
                Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                edit.clear();
                edit.commit();
            }
        } catch (Throwable th) {
            ALog.e("Utils", "clearAllSharePreferences", th, new Object[0]);
        }
    }

    public static void c(Context context) {
        try {
            Class loadClass = a.a().b().loadClass("com.taobao.accs.utl.UtilityImpl");
            loadClass.getMethod("killService", new Class[]{Context.class}).invoke(loadClass, new Object[]{context});
        } catch (Throwable th) {
            ALog.e("Utils", "killservice", th, new Object[0]);
            th.printStackTrace();
        }
    }

    public static boolean d(Context context) {
        boolean booleanValue;
        try {
            Class loadClass = a.a().b().loadClass("com.taobao.accs.utl.UtilityImpl");
            booleanValue = ((Boolean) loadClass.getMethod("isMainProcess", new Class[]{Context.class}).invoke(loadClass, new Object[]{context})).booleanValue();
        } catch (Throwable th) {
            ALog.e("Utils", "killservice", th, new Object[0]);
            th.printStackTrace();
            booleanValue = true;
        }
        ALog.i("Utils", "isMainProcess", "result", Boolean.valueOf(booleanValue));
        return booleanValue;
    }

    public static void a(Context context, String str) {
        try {
            Class loadClass = a.a().b().loadClass("org.android.agoo.common.Config");
            loadClass.getMethod("setAgooAppKey", new Class[]{Context.class, String.class}).invoke(loadClass, new Object[]{context, str});
        } catch (Throwable th) {
            ALog.e("Utils", "setAgooAppkey", th, new Object[0]);
            th.printStackTrace();
        }
    }

    @Deprecated
    public static void a() {
        try {
            Class loadClass = a.a().b().loadClass("com.taobao.accs.client.AccsConfig");
            loadClass.getMethod("build", new Class[0]).invoke(loadClass, new Object[0]);
        } catch (Throwable th) {
            ALog.e("Utils", "initConfig", th, new Object[0]);
            th.printStackTrace();
        }
    }

    public static void a(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            ALog.e("Utils", "setSpValue null", new Object[0]);
            return;
        }
        try {
            synchronized (a) {
                Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                edit.putString(str, str2);
                edit.apply();
            }
            ALog.i("Utils", "setSpValue", "key", str, "value", str2);
        } catch (Throwable e) {
            ALog.e("Utils", "setSpValue fail", e, new Object[0]);
        }
    }

    /* JADX WARNING: Missing block: B:9:?, code:
            com.taobao.accs.utl.ALog.i("Utils", "getSpValue", "value", r0);
     */
    /* JADX WARNING: Missing block: B:10:0x002c, code:
            if (android.text.TextUtils.isEmpty(r0) == false) goto L_0x0058;
     */
    /* JADX WARNING: Missing block: B:11:0x002e, code:
            com.taobao.accs.utl.ALog.e("Utils", "getSpValue use default!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:22:0x004f, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:23:0x0050, code:
            r10 = r0;
            r0 = r1;
     */
    /* JADX WARNING: Missing block: B:27:?, code:
            return r10;
     */
    /* JADX WARNING: Missing block: B:29:?, code:
            return r0;
     */
    public static java.lang.String b(android.content.Context r8, java.lang.String r9, java.lang.String r10) {
        /*
        r1 = 0;
        r6 = 0;
        r2 = a;	 Catch:{ Throwable -> 0x004c }
        monitor-enter(r2);	 Catch:{ Throwable -> 0x004c }
        r0 = "ACCS_SDK";
        r3 = 0;
        r0 = r8.getSharedPreferences(r0, r3);	 Catch:{ all -> 0x003b }
        r3 = 0;
        r0 = r0.getString(r9, r3);	 Catch:{ all -> 0x003b }
        monitor-exit(r2);	 Catch:{ all -> 0x0053 }
        r1 = "Utils";
        r2 = "getSpValue";
        r3 = 2;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x004f }
        r4 = 0;
        r5 = "value";
        r3[r4] = r5;	 Catch:{ Throwable -> 0x004f }
        r4 = 1;
        r3[r4] = r0;	 Catch:{ Throwable -> 0x004f }
        com.taobao.accs.utl.ALog.i(r1, r2, r3);	 Catch:{ Throwable -> 0x004f }
        r1 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Throwable -> 0x004f }
        if (r1 == 0) goto L_0x0058;
    L_0x002e:
        r1 = "Utils";
        r2 = "getSpValue use default!";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x004f }
        com.taobao.accs.utl.ALog.e(r1, r2, r3);	 Catch:{ Throwable -> 0x004f }
    L_0x003a:
        return r10;
    L_0x003b:
        r0 = move-exception;
    L_0x003c:
        monitor-exit(r2);	 Catch:{ all -> 0x003b }
        throw r0;	 Catch:{ Throwable -> 0x003e }
    L_0x003e:
        r0 = move-exception;
        r10 = r1;
    L_0x0040:
        r1 = "Utils";
        r2 = "getSpValue fail";
        r3 = new java.lang.Object[r6];
        com.taobao.accs.utl.ALog.e(r1, r2, r0, r3);
        goto L_0x003a;
    L_0x004c:
        r0 = move-exception;
        r10 = r1;
        goto L_0x0040;
    L_0x004f:
        r1 = move-exception;
        r10 = r0;
        r0 = r1;
        goto L_0x0040;
    L_0x0053:
        r1 = move-exception;
        r7 = r1;
        r1 = r0;
        r0 = r7;
        goto L_0x003c;
    L_0x0058:
        r10 = r0;
        goto L_0x003a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.utl.l.b(android.content.Context, java.lang.String, java.lang.String):java.lang.String");
    }

    public static void e(Context context) {
        try {
            Editor edit = context.getSharedPreferences("AGOO_BIND", 0).edit();
            edit.clear();
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bundle f(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                return applicationInfo.metaData;
            }
            return null;
        } catch (Throwable th) {
            ALog.e("Utils", "getMetaInfo", th, new Object[0]);
            return null;
        }
    }
}
