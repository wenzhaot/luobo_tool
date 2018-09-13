package com.umeng.analytics.pro;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.text.TextUtils;
import android.util.Base64;
import com.stub.StubApp;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: UMStoreManager */
public class g {
    public static final int a = 2049;
    public static final int b = 2050;
    private static final int c = 1000;
    private static Context d = null;
    private static String e = null;
    private static final String f = "umeng+";
    private static final String g = "ek__id";
    private static final String h = "ek_key";
    private List<String> i;
    private List<Integer> j;
    private String k;

    /* compiled from: UMStoreManager */
    public enum a {
        AUTOPAGE,
        PAGE,
        BEGIN,
        END,
        NEWSESSION
    }

    /* compiled from: UMStoreManager */
    private static class b {
        private static final g a = new g();

        private b() {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0038 A:{Splitter: B:1:0x0001, ExcHandler: android.database.sqlite.SQLiteDatabaseCorruptException (e android.database.sqlite.SQLiteDatabaseCorruptException)} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0057 A:{Splitter: B:1:0x0001, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x006d A:{SYNTHETIC, Splitter: B:35:0x006d} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:18:?, code:
            com.umeng.analytics.pro.f.a(d);
     */
    /* JADX WARNING: Missing block: B:19:0x003e, code:
            if (r0 != null) goto L_0x0040;
     */
    /* JADX WARNING: Missing block: B:21:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:28:0x0058, code:
            if (r0 != null) goto L_0x005a;
     */
    /* JADX WARNING: Missing block: B:30:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:32:0x0067, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:33:0x0068, code:
            r2 = r1;
            r1 = r0;
            r0 = r2;
     */
    /* JADX WARNING: Missing block: B:36:?, code:
            r1.endTransaction();
     */
    public void a(int r4) {
        /*
        r3 = this;
        r0 = 0;
        r1 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0038, Throwable -> 0x0057, all -> 0x0067 }
        r1 = com.umeng.analytics.pro.e.a(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0038, Throwable -> 0x0057, all -> 0x0067 }
        r0 = r1.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0038, Throwable -> 0x0057, all -> 0x0067 }
        r0.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0038, Throwable -> 0x0057 }
        if (r4 != 0) goto L_0x0028;
    L_0x0010:
        r1 = "delete from __dp where __ty=0";
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0038, Throwable -> 0x0057 }
    L_0x0016:
        r0.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0038, Throwable -> 0x0057 }
        if (r0 == 0) goto L_0x001e;
    L_0x001b:
        r0.endTransaction();	 Catch:{ Throwable -> 0x007a }
    L_0x001e:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x0027:
        return;
    L_0x0028:
        r1 = 4;
        if (r4 != r1) goto L_0x004d;
    L_0x002b:
        r1 = "delete from __dp where __ty=3";
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0038, Throwable -> 0x0057 }
        r1 = "delete from __dp where __ty=2";
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0038, Throwable -> 0x0057 }
        goto L_0x0016;
    L_0x0038:
        r1 = move-exception;
        r1 = d;	 Catch:{ all -> 0x0082 }
        com.umeng.analytics.pro.f.a(r1);	 Catch:{ all -> 0x0082 }
        if (r0 == 0) goto L_0x0043;
    L_0x0040:
        r0.endTransaction();	 Catch:{ Throwable -> 0x007c }
    L_0x0043:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x0027;
    L_0x004d:
        r1 = 1;
        if (r4 != r1) goto L_0x0016;
    L_0x0050:
        r1 = "delete from __dp where __ty=1";
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0038, Throwable -> 0x0057 }
        goto L_0x0016;
    L_0x0057:
        r1 = move-exception;
        if (r0 == 0) goto L_0x005d;
    L_0x005a:
        r0.endTransaction();	 Catch:{ Throwable -> 0x007e }
    L_0x005d:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x0027;
    L_0x0067:
        r1 = move-exception;
        r2 = r1;
        r1 = r0;
        r0 = r2;
    L_0x006b:
        if (r1 == 0) goto L_0x0070;
    L_0x006d:
        r1.endTransaction();	 Catch:{ Throwable -> 0x0080 }
    L_0x0070:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x007a:
        r0 = move-exception;
        goto L_0x001e;
    L_0x007c:
        r0 = move-exception;
        goto L_0x0043;
    L_0x007e:
        r0 = move-exception;
        goto L_0x005d;
    L_0x0080:
        r1 = move-exception;
        goto L_0x0070;
    L_0x0082:
        r1 = move-exception;
        r2 = r1;
        r1 = r0;
        r0 = r2;
        goto L_0x006b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.a(int):void");
    }

    private g() {
        this.i = new ArrayList();
        this.j = new ArrayList();
        this.k = null;
    }

    public static g a(Context context) {
        g a = b.a;
        if (d == null && context != null) {
            d = StubApp.getOrigApplicationContext(context.getApplicationContext());
            a.h();
        }
        return a;
    }

    private void h() {
        synchronized (this) {
            i();
            this.i.clear();
        }
    }

    public void a() {
        this.i.clear();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x007d A:{Splitter: B:1:0x0001, ExcHandler: android.database.sqlite.SQLiteDatabaseCorruptException (e android.database.sqlite.SQLiteDatabaseCorruptException)} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0092 A:{Splitter: B:1:0x0001, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00a8 A:{SYNTHETIC, Splitter: B:27:0x00a8} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:14:?, code:
            com.umeng.analytics.pro.f.a(d);
     */
    /* JADX WARNING: Missing block: B:15:0x0083, code:
            if (r0 != null) goto L_0x0085;
     */
    /* JADX WARNING: Missing block: B:17:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:20:0x0093, code:
            if (r0 != null) goto L_0x0095;
     */
    /* JADX WARNING: Missing block: B:22:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:28:?, code:
            r1.endTransaction();
     */
    /* JADX WARNING: Missing block: B:35:0x00bd, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:36:0x00be, code:
            r8 = r1;
            r1 = r0;
            r0 = r8;
     */
    public void a(org.json.JSONObject r10, int r11) {
        /*
        r9 = this;
        r0 = 0;
        r1 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092, all -> 0x00a2 }
        r1 = com.umeng.analytics.pro.e.a(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092, all -> 0x00a2 }
        r0 = r1.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092, all -> 0x00a2 }
        r0.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r2 = new android.content.ContentValues;	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r2.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r1 = "__ii";
        r1 = r10.optString(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r3 = "__id";
        r4 = java.lang.System.currentTimeMillis();	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r6 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r4 = r4 - r6;
        r4 = java.lang.Long.valueOf(r4);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r2.put(r3, r4);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r3 = "__ii";
        r4 = android.text.TextUtils.isEmpty(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        if (r4 == 0) goto L_0x0037;
    L_0x0034:
        r1 = "-1";
    L_0x0037:
        r2.put(r3, r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r1 = "__ii";
        r10.remove(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r1 = "__io";
        r3 = r10.toString();	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r3 = r9.a(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r2.put(r1, r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r1 = "__ty";
        r3 = java.lang.Integer.valueOf(r11);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r2.put(r1, r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r1 = "__ve";
        r3 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r3 = com.umeng.commonsdk.statistics.common.DeviceConfig.getAppVersionCode(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r2.put(r1, r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r1 = "__dp";
        r3 = 0;
        r0.insert(r1, r3, r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x007d, Throwable -> 0x0092 }
        if (r0 == 0) goto L_0x0073;
    L_0x0070:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00b5 }
    L_0x0073:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x007c:
        return;
    L_0x007d:
        r1 = move-exception;
        r1 = d;	 Catch:{ all -> 0x00bd }
        com.umeng.analytics.pro.f.a(r1);	 Catch:{ all -> 0x00bd }
        if (r0 == 0) goto L_0x0088;
    L_0x0085:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00b7 }
    L_0x0088:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x007c;
    L_0x0092:
        r1 = move-exception;
        if (r0 == 0) goto L_0x0098;
    L_0x0095:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00b9 }
    L_0x0098:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x007c;
    L_0x00a2:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
    L_0x00a6:
        if (r1 == 0) goto L_0x00ab;
    L_0x00a8:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00bb }
    L_0x00ab:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x00b5:
        r0 = move-exception;
        goto L_0x0073;
    L_0x00b7:
        r0 = move-exception;
        goto L_0x0088;
    L_0x00b9:
        r0 = move-exception;
        goto L_0x0098;
    L_0x00bb:
        r1 = move-exception;
        goto L_0x00ab;
    L_0x00bd:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
        goto L_0x00a6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.a(org.json.JSONObject, int):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x009f A:{Splitter: B:1:0x0001, ExcHandler: android.database.sqlite.SQLiteDatabaseCorruptException (e android.database.sqlite.SQLiteDatabaseCorruptException)} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b4 A:{Splitter: B:1:0x0001, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ca A:{SYNTHETIC, Splitter: B:38:0x00ca} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:25:?, code:
            com.umeng.analytics.pro.f.a(d);
     */
    /* JADX WARNING: Missing block: B:26:0x00a5, code:
            if (r0 != null) goto L_0x00a7;
     */
    /* JADX WARNING: Missing block: B:28:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:31:0x00b5, code:
            if (r0 != null) goto L_0x00b7;
     */
    /* JADX WARNING: Missing block: B:33:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:39:?, code:
            r1.endTransaction();
     */
    /* JADX WARNING: Missing block: B:46:0x00df, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:47:0x00e0, code:
            r6 = r1;
            r1 = r0;
            r0 = r6;
     */
    public void a(org.json.JSONArray r8) {
        /*
        r7 = this;
        r0 = 0;
        r1 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00b4, all -> 0x00c4 }
        r1 = com.umeng.analytics.pro.e.a(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00b4, all -> 0x00c4 }
        r0 = r1.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00b4, all -> 0x00c4 }
        r0.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00b4 }
        r1 = 0;
    L_0x000f:
        r2 = r8.length();	 Catch:{ SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00b4 }
        if (r1 >= r2) goto L_0x008d;
    L_0x0015:
        r3 = r8.getJSONObject(r1);	 Catch:{ Exception -> 0x00e4 }
        r4 = new android.content.ContentValues;	 Catch:{ Exception -> 0x00e4 }
        r4.<init>();	 Catch:{ Exception -> 0x00e4 }
        r2 = "__i";
        r2 = r3.optString(r2);	 Catch:{ Exception -> 0x00e4 }
        r5 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Exception -> 0x00e4 }
        if (r5 != 0) goto L_0x0034;
    L_0x002b:
        r5 = "-1";
        r5 = r5.equals(r2);	 Catch:{ Exception -> 0x00e4 }
        if (r5 == 0) goto L_0x0045;
    L_0x0034:
        r2 = com.umeng.analytics.pro.p.a();	 Catch:{ Exception -> 0x00e4 }
        r2 = r2.c();	 Catch:{ Exception -> 0x00e4 }
        r5 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Exception -> 0x00e4 }
        if (r5 == 0) goto L_0x0045;
    L_0x0042:
        r2 = "-1";
    L_0x0045:
        r5 = "__i";
        r4.put(r5, r2);	 Catch:{ Exception -> 0x00e4 }
        r2 = "__e";
        r5 = "id";
        r5 = r3.optString(r5);	 Catch:{ Exception -> 0x00e4 }
        r4.put(r2, r5);	 Catch:{ Exception -> 0x00e4 }
        r2 = "__t";
        r5 = "__t";
        r5 = r3.optInt(r5);	 Catch:{ Exception -> 0x00e4 }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ Exception -> 0x00e4 }
        r4.put(r2, r5);	 Catch:{ Exception -> 0x00e4 }
        r2 = "__i";
        r3.remove(r2);	 Catch:{ Exception -> 0x00e4 }
        r2 = "__t";
        r3.remove(r2);	 Catch:{ Exception -> 0x00e4 }
        r2 = "__s";
        r3 = r3.toString();	 Catch:{ Exception -> 0x00e4 }
        r3 = r7.a(r3);	 Catch:{ Exception -> 0x00e4 }
        r4.put(r2, r3);	 Catch:{ Exception -> 0x00e4 }
        r2 = "__et";
        r3 = 0;
        r0.insert(r2, r3, r4);	 Catch:{ Exception -> 0x00e4 }
    L_0x008a:
        r1 = r1 + 1;
        goto L_0x000f;
    L_0x008d:
        r0.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00b4 }
        if (r0 == 0) goto L_0x0095;
    L_0x0092:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00d7 }
    L_0x0095:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x009e:
        return;
    L_0x009f:
        r1 = move-exception;
        r1 = d;	 Catch:{ all -> 0x00df }
        com.umeng.analytics.pro.f.a(r1);	 Catch:{ all -> 0x00df }
        if (r0 == 0) goto L_0x00aa;
    L_0x00a7:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00d9 }
    L_0x00aa:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x009e;
    L_0x00b4:
        r1 = move-exception;
        if (r0 == 0) goto L_0x00ba;
    L_0x00b7:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00db }
    L_0x00ba:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x009e;
    L_0x00c4:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x00c8:
        if (r1 == 0) goto L_0x00cd;
    L_0x00ca:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00dd }
    L_0x00cd:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x00d7:
        r0 = move-exception;
        goto L_0x0095;
    L_0x00d9:
        r0 = move-exception;
        goto L_0x00aa;
    L_0x00db:
        r0 = move-exception;
        goto L_0x00ba;
    L_0x00dd:
        r1 = move-exception;
        goto L_0x00cd;
    L_0x00df:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x00c8;
    L_0x00e4:
        r2 = move-exception;
        goto L_0x008a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.a(org.json.JSONArray):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x004d A:{Splitter: B:1:0x0001, ExcHandler: android.database.sqlite.SQLiteDatabaseCorruptException (e android.database.sqlite.SQLiteDatabaseCorruptException)} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0062 A:{Splitter: B:1:0x0001, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0078 A:{SYNTHETIC, Splitter: B:29:0x0078} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:16:?, code:
            com.umeng.analytics.pro.f.a(d);
     */
    /* JADX WARNING: Missing block: B:17:0x0053, code:
            if (r0 != null) goto L_0x0055;
     */
    /* JADX WARNING: Missing block: B:19:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:22:0x0063, code:
            if (r0 != null) goto L_0x0065;
     */
    /* JADX WARNING: Missing block: B:24:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:30:?, code:
            r1.endTransaction();
     */
    /* JADX WARNING: Missing block: B:37:0x008d, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:38:0x008e, code:
            r4 = r1;
            r1 = r0;
            r0 = r4;
     */
    public boolean a(java.lang.String r6, java.lang.String r7, int r8) {
        /*
        r5 = this;
        r0 = 0;
        r1 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062, all -> 0x0072 }
        r1 = com.umeng.analytics.pro.e.a(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062, all -> 0x0072 }
        r0 = r1.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062, all -> 0x0072 }
        r0.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062 }
        r1 = new android.content.ContentValues;	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062 }
        r1.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062 }
        r2 = "__i";
        r1.put(r2, r6);	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062 }
        r2 = r5.a(r7);	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062 }
        r3 = android.text.TextUtils.isEmpty(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062 }
        if (r3 != 0) goto L_0x003a;
    L_0x0023:
        r3 = "__a";
        r1.put(r3, r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062 }
        r2 = "__t";
        r3 = java.lang.Integer.valueOf(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062 }
        r1.put(r2, r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062 }
        r2 = "__er";
        r3 = 0;
        r0.insert(r2, r3, r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062 }
    L_0x003a:
        r0.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x004d, Throwable -> 0x0062 }
        if (r0 == 0) goto L_0x0042;
    L_0x003f:
        r0.endTransaction();	 Catch:{ Throwable -> 0x0085 }
    L_0x0042:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x004b:
        r0 = 0;
        return r0;
    L_0x004d:
        r1 = move-exception;
        r1 = d;	 Catch:{ all -> 0x008d }
        com.umeng.analytics.pro.f.a(r1);	 Catch:{ all -> 0x008d }
        if (r0 == 0) goto L_0x0058;
    L_0x0055:
        r0.endTransaction();	 Catch:{ Throwable -> 0x0087 }
    L_0x0058:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x004b;
    L_0x0062:
        r1 = move-exception;
        if (r0 == 0) goto L_0x0068;
    L_0x0065:
        r0.endTransaction();	 Catch:{ Throwable -> 0x0089 }
    L_0x0068:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x004b;
    L_0x0072:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
    L_0x0076:
        if (r1 == 0) goto L_0x007b;
    L_0x0078:
        r1.endTransaction();	 Catch:{ Throwable -> 0x008b }
    L_0x007b:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x0085:
        r0 = move-exception;
        goto L_0x0042;
    L_0x0087:
        r0 = move-exception;
        goto L_0x0058;
    L_0x0089:
        r0 = move-exception;
        goto L_0x0068;
    L_0x008b:
        r1 = move-exception;
        goto L_0x007b;
    L_0x008d:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
        goto L_0x0076;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.a(java.lang.String, java.lang.String, int):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x008c A:{Splitter: B:1:0x0002, ExcHandler: android.database.sqlite.SQLiteDatabaseCorruptException (e android.database.sqlite.SQLiteDatabaseCorruptException)} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a1 A:{Splitter: B:1:0x0002, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00b8 A:{SYNTHETIC, Splitter: B:36:0x00b8} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:23:?, code:
            com.umeng.analytics.pro.f.a(d);
     */
    /* JADX WARNING: Missing block: B:24:0x0092, code:
            if (r0 != null) goto L_0x0094;
     */
    /* JADX WARNING: Missing block: B:26:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:29:0x00a2, code:
            if (r0 != null) goto L_0x00a4;
     */
    /* JADX WARNING: Missing block: B:31:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:37:?, code:
            r1.endTransaction();
     */
    /* JADX WARNING: Missing block: B:45:0x00d0, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:46:0x00d1, code:
            r6 = r1;
            r1 = r0;
            r0 = r6;
     */
    public void b() {
        /*
        r7 = this;
        r1 = 0;
        r0 = 0;
        r2 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1, all -> 0x00b2 }
        r2 = com.umeng.analytics.pro.e.a(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1, all -> 0x00b2 }
        r0 = r2.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1, all -> 0x00b2 }
        r0.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r2 = com.umeng.analytics.pro.p.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r2 = r2.d();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r3 = android.text.TextUtils.isEmpty(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        if (r3 == 0) goto L_0x002c;
    L_0x001d:
        if (r0 == 0) goto L_0x0022;
    L_0x001f:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00c5 }
    L_0x0022:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x002b:
        return;
    L_0x002c:
        r3 = 2;
        r3 = new java.lang.String[r3];	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r4 = 0;
        r5 = "";
        r3[r4] = r5;	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r4 = 1;
        r5 = "-1";
        r3[r4] = r5;	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
    L_0x003b:
        r4 = r3.length;	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        if (r1 >= r4) goto L_0x007a;
    L_0x003e:
        r4 = new java.lang.StringBuilder;	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r4.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r5 = "update __et set __i=\"";
        r4 = r4.append(r5);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r4 = r4.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r5 = "\" where ";
        r4 = r4.append(r5);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r5 = "__i";
        r4 = r4.append(r5);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r5 = "=\"";
        r4 = r4.append(r5);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r5 = r3[r1];	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r4 = r4.append(r5);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r5 = "\"";
        r4 = r4.append(r5);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r4 = r4.toString();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r0.execSQL(r4);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        r1 = r1 + 1;
        goto L_0x003b;
    L_0x007a:
        r0.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008c, Throwable -> 0x00a1 }
        if (r0 == 0) goto L_0x0082;
    L_0x007f:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00c8 }
    L_0x0082:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x002b;
    L_0x008c:
        r1 = move-exception;
        r1 = d;	 Catch:{ all -> 0x00d0 }
        com.umeng.analytics.pro.f.a(r1);	 Catch:{ all -> 0x00d0 }
        if (r0 == 0) goto L_0x0097;
    L_0x0094:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00ca }
    L_0x0097:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x002b;
    L_0x00a1:
        r1 = move-exception;
        if (r0 == 0) goto L_0x00a7;
    L_0x00a4:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00cc }
    L_0x00a7:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x002b;
    L_0x00b2:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x00b6:
        if (r1 == 0) goto L_0x00bb;
    L_0x00b8:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00ce }
    L_0x00bb:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x00c5:
        r0 = move-exception;
        goto L_0x0022;
    L_0x00c8:
        r0 = move-exception;
        goto L_0x0082;
    L_0x00ca:
        r0 = move-exception;
        goto L_0x0097;
    L_0x00cc:
        r0 = move-exception;
        goto L_0x00a7;
    L_0x00ce:
        r1 = move-exception;
        goto L_0x00bb;
    L_0x00d0:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x00b6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.b():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00bf A:{SYNTHETIC, Splitter: B:32:0x00bf} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00dc A:{SYNTHETIC, Splitter: B:41:0x00dc} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00dc A:{SYNTHETIC, Splitter: B:41:0x00dc} */
    public boolean a(java.lang.String r8, org.json.JSONObject r9, com.umeng.analytics.pro.g.a r10) {
        /*
        r7 = this;
        r0 = 0;
        r5 = 0;
        if (r9 != 0) goto L_0x0005;
    L_0x0004:
        return r5;
    L_0x0005:
        r1 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0108, Throwable -> 0x0105, all -> 0x00fb }
        r1 = com.umeng.analytics.pro.e.a(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0108, Throwable -> 0x0105, all -> 0x00fb }
        r1 = r1.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0108, Throwable -> 0x0105, all -> 0x00fb }
        r1.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r0 = com.umeng.analytics.pro.g.a.BEGIN;	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        if (r10 != r0) goto L_0x0051;
    L_0x0016:
        r0 = "__e";
        r0 = r9.opt(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r0 = (java.lang.Long) r0;	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r2 = r0.longValue();	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r0 = new android.content.ContentValues;	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r0.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r4 = "__ii";
        r0.put(r4, r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r4 = "__e";
        r2 = java.lang.String.valueOf(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r0.put(r4, r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r2 = "__sd";
        r3 = 0;
        r1.insert(r2, r3, r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
    L_0x003f:
        r1.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        if (r1 == 0) goto L_0x0047;
    L_0x0044:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00f2 }
    L_0x0047:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x0004;
    L_0x0051:
        r0 = com.umeng.analytics.pro.g.a.END;	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        if (r10 != r0) goto L_0x00b1;
    L_0x0055:
        r0 = "__f";
        r0 = r9.opt(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r0 = (java.lang.Long) r0;	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r2 = r0.longValue();	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r0 = new java.lang.StringBuilder;	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r0.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r4 = "update __sd set __f=\"";
        r0 = r0.append(r4);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r0 = r0.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r2 = "\" where ";
        r0 = r0.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r2 = "__ii";
        r0 = r0.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r2 = "=\"";
        r0 = r0.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r0 = r0.append(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r2 = "\"";
        r0 = r0.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r0 = r0.toString();	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        r1.execSQL(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        goto L_0x003f;
    L_0x009a:
        r0 = move-exception;
        r0 = r1;
    L_0x009c:
        r1 = d;	 Catch:{ all -> 0x0100 }
        com.umeng.analytics.pro.f.a(r1);	 Catch:{ all -> 0x0100 }
        if (r0 == 0) goto L_0x00a6;
    L_0x00a3:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00f5 }
    L_0x00a6:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x0004;
    L_0x00b1:
        r0 = com.umeng.analytics.pro.g.a.PAGE;	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        if (r10 != r0) goto L_0x00cd;
    L_0x00b5:
        r0 = "__a";
        r7.a(r8, r9, r1, r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        goto L_0x003f;
    L_0x00bc:
        r0 = move-exception;
    L_0x00bd:
        if (r1 == 0) goto L_0x00c2;
    L_0x00bf:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00f7 }
    L_0x00c2:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x0004;
    L_0x00cd:
        r0 = com.umeng.analytics.pro.g.a.AUTOPAGE;	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        if (r10 != r0) goto L_0x00e9;
    L_0x00d1:
        r0 = "__b";
        r7.a(r8, r9, r1, r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        goto L_0x003f;
    L_0x00d9:
        r0 = move-exception;
    L_0x00da:
        if (r1 == 0) goto L_0x00df;
    L_0x00dc:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00f9 }
    L_0x00df:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x00e9:
        r0 = com.umeng.analytics.pro.g.a.NEWSESSION;	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        if (r10 != r0) goto L_0x003f;
    L_0x00ed:
        r7.a(r8, r9, r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x009a, Throwable -> 0x00bc, all -> 0x00d9 }
        goto L_0x003f;
    L_0x00f2:
        r0 = move-exception;
        goto L_0x0047;
    L_0x00f5:
        r0 = move-exception;
        goto L_0x00a6;
    L_0x00f7:
        r0 = move-exception;
        goto L_0x00c2;
    L_0x00f9:
        r1 = move-exception;
        goto L_0x00df;
    L_0x00fb:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x00da;
    L_0x0100:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x00da;
    L_0x0105:
        r1 = move-exception;
        r1 = r0;
        goto L_0x00bd;
    L_0x0108:
        r1 = move-exception;
        goto L_0x009c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.a(java.lang.String, org.json.JSONObject, com.umeng.analytics.pro.g$a):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0137  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x013e  */
    private void a(java.lang.String r7, org.json.JSONObject r8, android.database.sqlite.SQLiteDatabase r9) {
        /*
        r6 = this;
        r1 = 0;
        r0 = "__d";
        r3 = r8.optJSONObject(r0);	 Catch:{ Throwable -> 0x0133, all -> 0x013b }
        if (r3 == 0) goto L_0x014c;
    L_0x000a:
        r0 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0133, all -> 0x013b }
        r0.<init>();	 Catch:{ Throwable -> 0x0133, all -> 0x013b }
        r2 = "select __d from __sd where __ii=\"";
        r0 = r0.append(r2);	 Catch:{ Throwable -> 0x0133, all -> 0x013b }
        r0 = r0.append(r7);	 Catch:{ Throwable -> 0x0133, all -> 0x013b }
        r2 = "\"";
        r0 = r0.append(r2);	 Catch:{ Throwable -> 0x0133, all -> 0x013b }
        r0 = r0.toString();	 Catch:{ Throwable -> 0x0133, all -> 0x013b }
        r2 = 0;
        r0 = r9.rawQuery(r0, r2);	 Catch:{ Throwable -> 0x0133, all -> 0x013b }
        if (r0 == 0) goto L_0x0149;
    L_0x002c:
        r2 = r0.moveToNext();	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        if (r2 == 0) goto L_0x0042;
    L_0x0032:
        r1 = "__d";
        r1 = r0.getColumnIndex(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r0.getString(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r6.b(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        goto L_0x002c;
    L_0x0042:
        r2 = r1;
    L_0x0043:
        if (r3 == 0) goto L_0x009d;
    L_0x0045:
        r1 = new org.json.JSONArray;	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1.<init>();	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r4 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        if (r4 != 0) goto L_0x0055;
    L_0x0050:
        r1 = new org.json.JSONArray;	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1.<init>(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
    L_0x0055:
        r1.put(r3);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r6.a(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        if (r2 != 0) goto L_0x009d;
    L_0x0066:
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2.<init>();	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r3 = "update  __sd set __d=\"";
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r2.append(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = "\" where ";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = "__ii";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = "=\"";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r1.append(r7);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = "\"";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r9.execSQL(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
    L_0x009d:
        r1 = "__c";
        r1 = r8.optJSONObject(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        if (r1 == 0) goto L_0x00eb;
    L_0x00a6:
        r1 = r1.toString();	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r6.a(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        if (r2 != 0) goto L_0x00eb;
    L_0x00b4:
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2.<init>();	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r3 = "update  __sd set __c=\"";
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r2.append(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = "\" where ";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = "__ii";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = "=\"";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r1.append(r7);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = "\"";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r9.execSQL(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
    L_0x00eb:
        r1 = "__f";
        r2 = r8.optLong(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1.<init>();	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r4 = "update  __sd set __f=\"";
        r1 = r1.append(r4);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = java.lang.String.valueOf(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = "\" where ";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = "__ii";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = "=\"";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r1.append(r7);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r2 = "\"";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        r9.execSQL(r1);	 Catch:{ Throwable -> 0x0147, all -> 0x0142 }
        if (r0 == 0) goto L_0x0132;
    L_0x012f:
        r0.close();
    L_0x0132:
        return;
    L_0x0133:
        r0 = move-exception;
        r0 = r1;
    L_0x0135:
        if (r0 == 0) goto L_0x0132;
    L_0x0137:
        r0.close();
        goto L_0x0132;
    L_0x013b:
        r0 = move-exception;
    L_0x013c:
        if (r1 == 0) goto L_0x0141;
    L_0x013e:
        r1.close();
    L_0x0141:
        throw r0;
    L_0x0142:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x013c;
    L_0x0147:
        r1 = move-exception;
        goto L_0x0135;
    L_0x0149:
        r2 = r1;
        goto L_0x0043;
    L_0x014c:
        r2 = r1;
        r0 = r1;
        goto L_0x0043;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.a(java.lang.String, org.json.JSONObject, android.database.sqlite.SQLiteDatabase):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:64:0x0141  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0087 A:{LOOP_START, SYNTHETIC, Splitter: B:23:0x0087, LOOP:0: B:23:0x0087->B:26:0x008d, PHI: r0 } */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x013e  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a6 A:{Catch:{ Throwable -> 0x013b, all -> 0x0139 }} */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b4  */
    private void a(java.lang.String r7, org.json.JSONObject r8, android.database.sqlite.SQLiteDatabase r9, java.lang.String r10) throws org.json.JSONException {
        /*
        r6 = this;
        r0 = 0;
        r1 = "__a";
        r1 = r1.equals(r10);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        if (r1 == 0) goto L_0x001f;
    L_0x000a:
        r1 = "__a";
        r1 = r8.optJSONArray(r1);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        if (r1 == 0) goto L_0x0019;
    L_0x0013:
        r2 = r1.length();	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        if (r2 > 0) goto L_0x003d;
    L_0x0019:
        if (r0 == 0) goto L_0x001e;
    L_0x001b:
        r0.close();
    L_0x001e:
        return;
    L_0x001f:
        r1 = "__b";
        r1 = r1.equals(r10);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        if (r1 == 0) goto L_0x0144;
    L_0x0028:
        r1 = "__b";
        r1 = r8.optJSONArray(r1);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        if (r1 == 0) goto L_0x0037;
    L_0x0031:
        r2 = r1.length();	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        if (r2 > 0) goto L_0x003d;
    L_0x0037:
        if (r0 == 0) goto L_0x001e;
    L_0x0039:
        r0.close();
        goto L_0x001e;
    L_0x003d:
        r3 = r1;
    L_0x003e:
        r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        r1.<init>();	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        r2 = "select ";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        r1 = r1.append(r10);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        r2 = " from ";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        r2 = "__sd";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        r2 = " where ";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        r2 = "__ii";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        r2 = "=\"";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        r1 = r1.append(r7);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        r2 = "\"";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        r2 = 0;
        r1 = r9.rawQuery(r1, r2);	 Catch:{ Throwable -> 0x0125, all -> 0x012d }
        if (r1 == 0) goto L_0x0141;
    L_0x0087:
        r2 = r1.moveToNext();	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        if (r2 == 0) goto L_0x009a;
    L_0x008d:
        r0 = r1.getColumnIndex(r10);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r0 = r1.getString(r0);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r0 = r6.b(r0);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        goto L_0x0087;
    L_0x009a:
        r2 = r0;
    L_0x009b:
        r0 = new org.json.JSONArray;	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r0.<init>();	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r4 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        if (r4 != 0) goto L_0x013e;
    L_0x00a6:
        r0 = new org.json.JSONArray;	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r0.<init>(r2);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r2 = r0;
    L_0x00ac:
        r0 = r2.length();	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        if (r0 <= r4) goto L_0x00bb;
    L_0x00b4:
        if (r1 == 0) goto L_0x001e;
    L_0x00b6:
        r1.close();
        goto L_0x001e;
    L_0x00bb:
        r0 = 0;
    L_0x00bc:
        r4 = r3.length();	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        if (r0 >= r4) goto L_0x00ce;
    L_0x00c2:
        r4 = r3.getJSONObject(r0);	 Catch:{ JSONException -> 0x0137 }
        if (r4 == 0) goto L_0x00cb;
    L_0x00c8:
        r2.put(r4);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
    L_0x00cb:
        r0 = r0 + 1;
        goto L_0x00bc;
    L_0x00ce:
        r0 = r2.toString();	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r0 = r6.a(r0);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r2 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        if (r2 != 0) goto L_0x011e;
    L_0x00dc:
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r2.<init>();	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r3 = "update __sd set ";
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r2 = r2.append(r10);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r3 = "=\"";
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r0 = r2.append(r0);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r2 = "\" where ";
        r0 = r0.append(r2);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r2 = "__ii";
        r0 = r0.append(r2);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r2 = "=\"";
        r0 = r0.append(r2);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r0 = r0.append(r7);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r2 = "\"";
        r0 = r0.append(r2);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r0 = r0.toString();	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
        r9.execSQL(r0);	 Catch:{ Throwable -> 0x013b, all -> 0x0139 }
    L_0x011e:
        if (r1 == 0) goto L_0x001e;
    L_0x0120:
        r1.close();
        goto L_0x001e;
    L_0x0125:
        r1 = move-exception;
    L_0x0126:
        if (r0 == 0) goto L_0x001e;
    L_0x0128:
        r0.close();
        goto L_0x001e;
    L_0x012d:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0131:
        if (r1 == 0) goto L_0x0136;
    L_0x0133:
        r1.close();
    L_0x0136:
        throw r0;
    L_0x0137:
        r4 = move-exception;
        goto L_0x00cb;
    L_0x0139:
        r0 = move-exception;
        goto L_0x0131;
    L_0x013b:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0126;
    L_0x013e:
        r2 = r0;
        goto L_0x00ac;
    L_0x0141:
        r2 = r0;
        goto L_0x009b;
    L_0x0144:
        r3 = r0;
        goto L_0x003e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.a(java.lang.String, org.json.JSONObject, android.database.sqlite.SQLiteDatabase, java.lang.String):void");
    }

    public JSONObject a(boolean z) {
        a();
        this.j.clear();
        JSONObject jSONObject = new JSONObject();
        if (z) {
            String a = a(jSONObject, z);
            if (!TextUtils.isEmpty(a)) {
                b(jSONObject, a);
                a(jSONObject, a);
            }
        } else {
            a(jSONObject, z);
            b(jSONObject, null);
            a(jSONObject, null);
        }
        return jSONObject;
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00d2 A:{SYNTHETIC, Splitter: B:58:0x00d2} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00d2 A:{SYNTHETIC, Splitter: B:58:0x00d2} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x008a A:{Splitter: B:3:0x0010, ExcHandler: android.database.sqlite.SQLiteDatabaseCorruptException (e android.database.sqlite.SQLiteDatabaseCorruptException)} */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00ab A:{Splitter: B:3:0x0010, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:56:0x00cd, code:
            r1.close();
     */
    /* JADX WARNING: Missing block: B:59:?, code:
            r2.endTransaction();
     */
    /* JADX WARNING: Missing block: B:79:0x010b, code:
            r2 = move-exception;
     */
    /* JADX WARNING: Missing block: B:80:0x010c, code:
            r9 = r2;
            r2 = r1;
            r1 = r0;
            r0 = r9;
     */
    public org.json.JSONObject c() {
        /*
        r10 = this;
        r0 = 0;
        r2 = new org.json.JSONObject;
        r2.<init>();
        r1 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0114, Throwable -> 0x0111, all -> 0x0105 }
        r1 = com.umeng.analytics.pro.e.a(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0114, Throwable -> 0x0111, all -> 0x0105 }
        r1 = r1.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0114, Throwable -> 0x0111, all -> 0x0105 }
        r1.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab, all -> 0x010b }
        r3 = "select *  from __dp";
        r4 = 0;
        r0 = r1.rawQuery(r3, r4);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab, all -> 0x010b }
        if (r0 == 0) goto L_0x00e6;
    L_0x001d:
        r3 = new org.json.JSONArray;	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        r3.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        r4 = new org.json.JSONArray;	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        r4.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        r5 = new org.json.JSONArray;	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        r5.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
    L_0x002c:
        r6 = r0.moveToNext();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        if (r6 == 0) goto L_0x00e6;
    L_0x0032:
        r6 = "__io";
        r6 = r0.getColumnIndex(r6);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        r6 = r0.getString(r6);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        r7 = "__ty";
        r7 = r0.getColumnIndex(r7);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        r7 = r0.getInt(r7);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        r8 = android.text.TextUtils.isEmpty(r6);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        if (r8 != 0) goto L_0x0065;
    L_0x004e:
        r8 = new org.json.JSONObject;	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        r6 = r10.b(r6);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        r8.<init>(r6);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        if (r8 == 0) goto L_0x0065;
    L_0x0059:
        r6 = r8.length();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        if (r6 <= 0) goto L_0x0065;
    L_0x005f:
        r6 = 2;
        if (r7 != r6) goto L_0x00a4;
    L_0x0062:
        r4.put(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
    L_0x0065:
        r6 = r3.length();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        if (r6 <= 0) goto L_0x0071;
    L_0x006b:
        r6 = "events";
        r2.put(r6, r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
    L_0x0071:
        r6 = r4.length();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        if (r6 <= 0) goto L_0x007d;
    L_0x0077:
        r6 = "session";
        r2.put(r6, r4);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
    L_0x007d:
        r6 = r5.length();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        if (r6 <= 0) goto L_0x002c;
    L_0x0083:
        r6 = "pageview";
        r2.put(r6, r5);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        goto L_0x002c;
    L_0x008a:
        r3 = move-exception;
    L_0x008b:
        r3 = d;	 Catch:{ all -> 0x00c6 }
        com.umeng.analytics.pro.f.a(r3);	 Catch:{ all -> 0x00c6 }
        if (r0 == 0) goto L_0x0095;
    L_0x0092:
        r0.close();
    L_0x0095:
        if (r1 == 0) goto L_0x009a;
    L_0x0097:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00ff }
    L_0x009a:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x00a3:
        return r2;
    L_0x00a4:
        r6 = 3;
        if (r7 != r6) goto L_0x00c0;
    L_0x00a7:
        r4.put(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        goto L_0x0065;
    L_0x00ab:
        r3 = move-exception;
    L_0x00ac:
        if (r0 == 0) goto L_0x00b1;
    L_0x00ae:
        r0.close();
    L_0x00b1:
        if (r1 == 0) goto L_0x00b6;
    L_0x00b3:
        r1.endTransaction();	 Catch:{ Throwable -> 0x0101 }
    L_0x00b6:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x00a3;
    L_0x00c0:
        if (r7 != 0) goto L_0x00df;
    L_0x00c2:
        r3.put(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        goto L_0x0065;
    L_0x00c6:
        r2 = move-exception;
        r9 = r2;
        r2 = r1;
        r1 = r0;
        r0 = r9;
    L_0x00cb:
        if (r1 == 0) goto L_0x00d0;
    L_0x00cd:
        r1.close();
    L_0x00d0:
        if (r2 == 0) goto L_0x00d5;
    L_0x00d2:
        r2.endTransaction();	 Catch:{ Throwable -> 0x0103 }
    L_0x00d5:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x00df:
        r6 = 1;
        if (r7 != r6) goto L_0x0065;
    L_0x00e2:
        r5.put(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        goto L_0x0065;
    L_0x00e6:
        r1.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x008a, Throwable -> 0x00ab }
        if (r0 == 0) goto L_0x00ee;
    L_0x00eb:
        r0.close();
    L_0x00ee:
        if (r1 == 0) goto L_0x00f3;
    L_0x00f0:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00fd }
    L_0x00f3:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x00a3;
    L_0x00fd:
        r0 = move-exception;
        goto L_0x00f3;
    L_0x00ff:
        r0 = move-exception;
        goto L_0x009a;
    L_0x0101:
        r0 = move-exception;
        goto L_0x00b6;
    L_0x0103:
        r1 = move-exception;
        goto L_0x00d5;
    L_0x0105:
        r1 = move-exception;
        r2 = r0;
        r9 = r0;
        r0 = r1;
        r1 = r9;
        goto L_0x00cb;
    L_0x010b:
        r2 = move-exception;
        r9 = r2;
        r2 = r1;
        r1 = r0;
        r0 = r9;
        goto L_0x00cb;
    L_0x0111:
        r1 = move-exception;
        r1 = r0;
        goto L_0x00ac;
    L_0x0114:
        r1 = move-exception;
        r1 = r0;
        goto L_0x008b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.c():org.json.JSONObject");
    }

    /* JADX WARNING: Removed duplicated region for block: B:59:0x0115  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x011a A:{SYNTHETIC, Splitter: B:61:0x011a} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e4 A:{SYNTHETIC, Splitter: B:46:0x00e4} */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0115  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x011a A:{SYNTHETIC, Splitter: B:61:0x011a} */
    private void a(org.json.JSONObject r12, java.lang.String r13) {
        /*
        r11 = this;
        r0 = 0;
        r1 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x01f1, Throwable -> 0x01e8, all -> 0x01d4 }
        r1 = com.umeng.analytics.pro.e.a(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x01f1, Throwable -> 0x01e8, all -> 0x01d4 }
        r2 = r1.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x01f1, Throwable -> 0x01e8, all -> 0x01d4 }
        r2.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x01f5, Throwable -> 0x01ed, all -> 0x01db }
        r1 = "select *  from __et";
        r3 = android.text.TextUtils.isEmpty(r13);	 Catch:{ SQLiteDatabaseCorruptException -> 0x01f5, Throwable -> 0x01ed, all -> 0x01db }
        if (r3 != 0) goto L_0x0032;
    L_0x0017:
        r1 = new java.lang.StringBuilder;	 Catch:{ SQLiteDatabaseCorruptException -> 0x01f5, Throwable -> 0x01ed, all -> 0x01db }
        r1.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x01f5, Throwable -> 0x01ed, all -> 0x01db }
        r3 = "select *  from __et where __i=\"";
        r1 = r1.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x01f5, Throwable -> 0x01ed, all -> 0x01db }
        r1 = r1.append(r13);	 Catch:{ SQLiteDatabaseCorruptException -> 0x01f5, Throwable -> 0x01ed, all -> 0x01db }
        r3 = "\"";
        r1 = r1.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x01f5, Throwable -> 0x01ed, all -> 0x01db }
        r1 = r1.toString();	 Catch:{ SQLiteDatabaseCorruptException -> 0x01f5, Throwable -> 0x01ed, all -> 0x01db }
    L_0x0032:
        r3 = 0;
        r1 = r2.rawQuery(r1, r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x01f5, Throwable -> 0x01ed, all -> 0x01db }
        if (r1 == 0) goto L_0x01b1;
    L_0x0039:
        r5 = new org.json.JSONObject;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r5.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r6 = new org.json.JSONObject;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r6.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r0 = com.umeng.analytics.pro.p.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r3 = r0.c();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
    L_0x004b:
        r0 = r1.moveToNext();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r0 == 0) goto L_0x012d;
    L_0x0051:
        r0 = "__t";
        r0 = r1.getColumnIndex(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r4 = r1.getInt(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r0 = "__i";
        r0 = r1.getColumnIndex(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r0 = r1.getString(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r7 = "__s";
        r7 = r1.getColumnIndex(r7);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r7 = r1.getString(r7);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r8 = android.text.TextUtils.isEmpty(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r8 != 0) goto L_0x0081;
    L_0x0078:
        r8 = "-1";
        r8 = r8.equals(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r8 == 0) goto L_0x0088;
    L_0x0081:
        r0 = android.text.TextUtils.isEmpty(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r0 != 0) goto L_0x004b;
    L_0x0087:
        r0 = r3;
    L_0x0088:
        r8 = 0;
        r8 = r1.getInt(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r9 = r11.j;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r8 = java.lang.Integer.valueOf(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r9.add(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        switch(r4) {
            case 2049: goto L_0x009a;
            case 2050: goto L_0x00f1;
            default: goto L_0x0099;
        };	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
    L_0x0099:
        goto L_0x004b;
    L_0x009a:
        r4 = android.text.TextUtils.isEmpty(r7);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r4 != 0) goto L_0x004b;
    L_0x00a0:
        r8 = new org.json.JSONObject;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r4 = r11.b(r7);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r8.<init>(r4);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r4 = r5.has(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r4 == 0) goto L_0x00d6;
    L_0x00af:
        r4 = r5.optJSONArray(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
    L_0x00b3:
        r4.put(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r5.put(r0, r4);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        goto L_0x004b;
    L_0x00ba:
        r0 = move-exception;
        r0 = r1;
        r1 = r2;
    L_0x00bd:
        r2 = d;	 Catch:{ all -> 0x01e1 }
        com.umeng.analytics.pro.f.a(r2);	 Catch:{ all -> 0x01e1 }
        if (r0 == 0) goto L_0x00c7;
    L_0x00c4:
        r0.close();
    L_0x00c7:
        if (r1 == 0) goto L_0x00cc;
    L_0x00c9:
        r1.endTransaction();	 Catch:{ Throwable -> 0x01cb }
    L_0x00cc:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x00d5:
        return;
    L_0x00d6:
        r4 = new org.json.JSONArray;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r4.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        goto L_0x00b3;
    L_0x00dc:
        r0 = move-exception;
    L_0x00dd:
        if (r1 == 0) goto L_0x00e2;
    L_0x00df:
        r1.close();
    L_0x00e2:
        if (r2 == 0) goto L_0x00e7;
    L_0x00e4:
        r2.endTransaction();	 Catch:{ Throwable -> 0x01ce }
    L_0x00e7:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x00d5;
    L_0x00f1:
        r4 = android.text.TextUtils.isEmpty(r7);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r4 != 0) goto L_0x004b;
    L_0x00f7:
        r8 = new org.json.JSONObject;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r4 = r11.b(r7);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r8.<init>(r4);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r4 = r6.has(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r4 == 0) goto L_0x0127;
    L_0x0106:
        r4 = r6.optJSONArray(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
    L_0x010a:
        r4.put(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r6.put(r0, r4);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        goto L_0x004b;
    L_0x0112:
        r0 = move-exception;
    L_0x0113:
        if (r1 == 0) goto L_0x0118;
    L_0x0115:
        r1.close();
    L_0x0118:
        if (r2 == 0) goto L_0x011d;
    L_0x011a:
        r2.endTransaction();	 Catch:{ Throwable -> 0x01d1 }
    L_0x011d:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x0127:
        r4 = new org.json.JSONArray;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r4.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        goto L_0x010a;
    L_0x012d:
        r0 = r5.length();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r0 <= 0) goto L_0x016f;
    L_0x0133:
        r3 = new org.json.JSONArray;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r3.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r4 = r5.keys();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
    L_0x013c:
        r0 = r4.hasNext();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r0 == 0) goto L_0x0163;
    L_0x0142:
        r7 = new org.json.JSONObject;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r7.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r0 = r4.next();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r0 = (java.lang.String) r0;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r8 = r5.optString(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r9 = new org.json.JSONArray;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r9.<init>(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r7.put(r0, r9);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r0 = r7.length();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r0 <= 0) goto L_0x013c;
    L_0x015f:
        r3.put(r7);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        goto L_0x013c;
    L_0x0163:
        r0 = r3.length();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r0 <= 0) goto L_0x016f;
    L_0x0169:
        r0 = "ekv";
        r12.put(r0, r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
    L_0x016f:
        r0 = r6.length();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r0 <= 0) goto L_0x01b1;
    L_0x0175:
        r3 = new org.json.JSONArray;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r3.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r4 = r6.keys();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
    L_0x017e:
        r0 = r4.hasNext();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r0 == 0) goto L_0x01a5;
    L_0x0184:
        r5 = new org.json.JSONObject;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r5.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r0 = r4.next();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r0 = (java.lang.String) r0;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r7 = r6.optString(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r8 = new org.json.JSONArray;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r8.<init>(r7);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r5.put(r0, r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        r0 = r5.length();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r0 <= 0) goto L_0x017e;
    L_0x01a1:
        r3.put(r5);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        goto L_0x017e;
    L_0x01a5:
        r0 = r3.length();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r0 <= 0) goto L_0x01b1;
    L_0x01ab:
        r0 = "gkv";
        r12.put(r0, r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
    L_0x01b1:
        r2.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00ba, Throwable -> 0x00dc, all -> 0x0112 }
        if (r1 == 0) goto L_0x01b9;
    L_0x01b6:
        r1.close();
    L_0x01b9:
        if (r2 == 0) goto L_0x01be;
    L_0x01bb:
        r2.endTransaction();	 Catch:{ Throwable -> 0x01c9 }
    L_0x01be:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x00d5;
    L_0x01c9:
        r0 = move-exception;
        goto L_0x01be;
    L_0x01cb:
        r0 = move-exception;
        goto L_0x00cc;
    L_0x01ce:
        r0 = move-exception;
        goto L_0x00e7;
    L_0x01d1:
        r1 = move-exception;
        goto L_0x011d;
    L_0x01d4:
        r1 = move-exception;
        r2 = r0;
        r10 = r0;
        r0 = r1;
        r1 = r10;
        goto L_0x0113;
    L_0x01db:
        r1 = move-exception;
        r10 = r1;
        r1 = r0;
        r0 = r10;
        goto L_0x0113;
    L_0x01e1:
        r2 = move-exception;
        r10 = r2;
        r2 = r1;
        r1 = r0;
        r0 = r10;
        goto L_0x0113;
    L_0x01e8:
        r1 = move-exception;
        r1 = r0;
        r2 = r0;
        goto L_0x00dd;
    L_0x01ed:
        r1 = move-exception;
        r1 = r0;
        goto L_0x00dd;
    L_0x01f1:
        r1 = move-exception;
        r1 = r0;
        goto L_0x00bd;
    L_0x01f5:
        r1 = move-exception;
        r1 = r2;
        goto L_0x00bd;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.a(org.json.JSONObject, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00c1 A:{SYNTHETIC, Splitter: B:50:0x00c1} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0062 A:{Splitter: B:3:0x000b, ExcHandler: android.database.sqlite.SQLiteDatabaseCorruptException (e android.database.sqlite.SQLiteDatabaseCorruptException)} */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00e2 A:{Splitter: B:3:0x000b, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a3  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00a8 A:{SYNTHETIC, Splitter: B:42:0x00a8} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00c1 A:{SYNTHETIC, Splitter: B:50:0x00c1} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x006f A:{SYNTHETIC, Splitter: B:23:0x006f} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:21:0x006a, code:
            r0.close();
     */
    /* JADX WARNING: Missing block: B:24:?, code:
            r1.endTransaction();
     */
    /* JADX WARNING: Missing block: B:40:0x00a3, code:
            r0.close();
     */
    /* JADX WARNING: Missing block: B:43:?, code:
            r1.endTransaction();
     */
    /* JADX WARNING: Missing block: B:48:0x00bc, code:
            r1.close();
     */
    /* JADX WARNING: Missing block: B:51:?, code:
            r2.endTransaction();
     */
    /* JADX WARNING: Missing block: B:58:0x00d6, code:
            r2 = move-exception;
     */
    /* JADX WARNING: Missing block: B:59:0x00d7, code:
            r5 = r2;
            r2 = r1;
            r1 = r0;
            r0 = r5;
     */
    private void b(org.json.JSONObject r7, java.lang.String r8) {
        /*
        r6 = this;
        r0 = 0;
        r1 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00e4, Throwable -> 0x009f, all -> 0x00b5 }
        r1 = com.umeng.analytics.pro.e.a(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00e4, Throwable -> 0x009f, all -> 0x00b5 }
        r1 = r1.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00e4, Throwable -> 0x009f, all -> 0x00b5 }
        r1.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2, all -> 0x00d6 }
        r2 = "select *  from __er";
        r3 = android.text.TextUtils.isEmpty(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2, all -> 0x00d6 }
        if (r3 != 0) goto L_0x0032;
    L_0x0017:
        r2 = new java.lang.StringBuilder;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2, all -> 0x00d6 }
        r2.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2, all -> 0x00d6 }
        r3 = "select *  from __er where __i=\"";
        r2 = r2.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2, all -> 0x00d6 }
        r2 = r2.append(r8);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2, all -> 0x00d6 }
        r3 = "\"";
        r2 = r2.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2, all -> 0x00d6 }
        r2 = r2.toString();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2, all -> 0x00d6 }
    L_0x0032:
        r3 = 0;
        r0 = r1.rawQuery(r2, r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2, all -> 0x00d6 }
        if (r0 == 0) goto L_0x0088;
    L_0x0039:
        r2 = new org.json.JSONArray;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2 }
        r2.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2 }
    L_0x003e:
        r3 = r0.moveToNext();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2 }
        if (r3 == 0) goto L_0x007c;
    L_0x0044:
        r3 = "__a";
        r3 = r0.getColumnIndex(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2 }
        r3 = r0.getString(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2 }
        r4 = android.text.TextUtils.isEmpty(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2 }
        if (r4 != 0) goto L_0x003e;
    L_0x0055:
        r4 = new org.json.JSONObject;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2 }
        r3 = r6.b(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2 }
        r4.<init>(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2 }
        r2.put(r4);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2 }
        goto L_0x003e;
    L_0x0062:
        r2 = move-exception;
    L_0x0063:
        r2 = d;	 Catch:{ all -> 0x00dc }
        com.umeng.analytics.pro.f.a(r2);	 Catch:{ all -> 0x00dc }
        if (r0 == 0) goto L_0x006d;
    L_0x006a:
        r0.close();
    L_0x006d:
        if (r1 == 0) goto L_0x0072;
    L_0x006f:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00d0 }
    L_0x0072:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x007b:
        return;
    L_0x007c:
        r3 = r2.length();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2 }
        if (r3 <= 0) goto L_0x0088;
    L_0x0082:
        r3 = "error";
        r7.put(r3, r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2 }
    L_0x0088:
        r1.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0062, Throwable -> 0x00e2 }
        if (r0 == 0) goto L_0x0090;
    L_0x008d:
        r0.close();
    L_0x0090:
        if (r1 == 0) goto L_0x0095;
    L_0x0092:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00ce }
    L_0x0095:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x007b;
    L_0x009f:
        r1 = move-exception;
        r1 = r0;
    L_0x00a1:
        if (r0 == 0) goto L_0x00a6;
    L_0x00a3:
        r0.close();
    L_0x00a6:
        if (r1 == 0) goto L_0x00ab;
    L_0x00a8:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00d2 }
    L_0x00ab:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x007b;
    L_0x00b5:
        r1 = move-exception;
        r2 = r0;
        r5 = r0;
        r0 = r1;
        r1 = r5;
    L_0x00ba:
        if (r1 == 0) goto L_0x00bf;
    L_0x00bc:
        r1.close();
    L_0x00bf:
        if (r2 == 0) goto L_0x00c4;
    L_0x00c1:
        r2.endTransaction();	 Catch:{ Throwable -> 0x00d4 }
    L_0x00c4:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x00ce:
        r0 = move-exception;
        goto L_0x0095;
    L_0x00d0:
        r0 = move-exception;
        goto L_0x0072;
    L_0x00d2:
        r0 = move-exception;
        goto L_0x00ab;
    L_0x00d4:
        r1 = move-exception;
        goto L_0x00c4;
    L_0x00d6:
        r2 = move-exception;
        r5 = r2;
        r2 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x00ba;
    L_0x00dc:
        r2 = move-exception;
        r5 = r2;
        r2 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x00ba;
    L_0x00e2:
        r2 = move-exception;
        goto L_0x00a1;
    L_0x00e4:
        r1 = move-exception;
        r1 = r0;
        goto L_0x0063;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.b(org.json.JSONObject, java.lang.String):void");
    }

    private String a(JSONObject jSONObject, boolean z) {
        SQLiteDatabase sQLiteDatabase = null;
        Cursor cursor = null;
        String str = null;
        try {
            sQLiteDatabase = e.a(d).a();
            sQLiteDatabase.beginTransaction();
            cursor = sQLiteDatabase.rawQuery("select *  from __sd", null);
            if (cursor != null) {
                JSONArray jSONArray = new JSONArray();
                while (cursor.moveToNext()) {
                    JSONObject jSONObject2 = new JSONObject();
                    String string = cursor.getString(cursor.getColumnIndex(com.umeng.analytics.pro.c.e.a.g));
                    String string2 = cursor.getString(cursor.getColumnIndex("__e"));
                    str = cursor.getString(cursor.getColumnIndex("__ii"));
                    if (!(TextUtils.isEmpty(string) || TextUtils.isEmpty(string2))) {
                        if (Long.parseLong(string) - Long.parseLong(string2) > 0) {
                            String string3 = cursor.getString(cursor.getColumnIndex("__a"));
                            String string4 = cursor.getString(cursor.getColumnIndex(com.umeng.analytics.pro.c.e.a.c));
                            String string5 = cursor.getString(cursor.getColumnIndex(com.umeng.analytics.pro.c.e.a.d));
                            String string6 = cursor.getString(cursor.getColumnIndex(com.umeng.analytics.pro.c.e.a.e));
                            this.i.add(str);
                            jSONObject2.put("id", str);
                            jSONObject2.put(b.p, string2);
                            jSONObject2.put(b.q, string);
                            jSONObject2.put("duration", Long.parseLong(string) - Long.parseLong(string2));
                            if (!TextUtils.isEmpty(string3)) {
                                jSONObject2.put(b.s, new JSONArray(b(string3)));
                            }
                            if (!TextUtils.isEmpty(string4)) {
                                jSONObject2.put(b.t, new JSONArray(b(string4)));
                            }
                            if (!TextUtils.isEmpty(string5)) {
                                jSONObject2.put(b.A, new JSONObject(b(string5)));
                            }
                            if (!TextUtils.isEmpty(string6)) {
                                jSONObject2.put(b.w, new JSONArray(b(string6)));
                            }
                            if (jSONObject2.length() > 0) {
                                jSONArray.put(jSONObject2);
                            }
                        }
                        if (z) {
                            break;
                        }
                    }
                }
                if (this.i.size() < 1) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (sQLiteDatabase != null) {
                        try {
                            sQLiteDatabase.endTransaction();
                        } catch (Throwable th) {
                        }
                    }
                    e.a(d).b();
                    return str;
                } else if (jSONArray.length() > 0) {
                    jSONObject.put(b.n, jSONArray);
                }
            }
            sQLiteDatabase.setTransactionSuccessful();
            if (cursor != null) {
                cursor.close();
            }
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.endTransaction();
                } catch (Throwable th2) {
                }
            }
            e.a(d).b();
        } catch (SQLiteDatabaseCorruptException e) {
            f.a(d);
            if (cursor != null) {
                cursor.close();
            }
            if (sQLiteDatabase != null) {
                sQLiteDatabase.endTransaction();
            }
        } catch (Throwable th3) {
        }
        return str;
        e.a(d).b();
        return str;
        e.a(d).b();
        return str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0095 A:{SYNTHETIC, Splitter: B:37:0x0095} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0082 A:{SYNTHETIC, Splitter: B:31:0x0082} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0095 A:{SYNTHETIC, Splitter: B:37:0x0095} */
    public void a(boolean r6, boolean r7) {
        /*
        r5 = this;
        r0 = 0;
        r1 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0069, Throwable -> 0x007e, all -> 0x008f }
        r1 = com.umeng.analytics.pro.e.a(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0069, Throwable -> 0x007e, all -> 0x008f }
        r1 = r1.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0069, Throwable -> 0x007e, all -> 0x008f }
        r1.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        r0 = "";
        if (r7 == 0) goto L_0x002d;
    L_0x0013:
        if (r6 == 0) goto L_0x001b;
    L_0x0015:
        r0 = "delete from __sd";
        r1.execSQL(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
    L_0x001b:
        r1.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        if (r1 == 0) goto L_0x0023;
    L_0x0020:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00a2 }
    L_0x0023:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x002c:
        return;
    L_0x002d:
        r0 = r5.i;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        r0 = r0.size();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        if (r0 <= 0) goto L_0x001b;
    L_0x0035:
        r0 = 0;
        r2 = r0;
    L_0x0037:
        r0 = r5.i;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        r0 = r0.size();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        if (r2 >= r0) goto L_0x001b;
    L_0x003f:
        r0 = new java.lang.StringBuilder;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        r0.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        r3 = "delete from __sd where __ii=\"";
        r3 = r0.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        r0 = r5.i;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        r0 = r0.get(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        r0 = (java.lang.String) r0;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        r0 = r3.append(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        r3 = "\"";
        r0 = r0.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        r0 = r0.toString();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        r1.execSQL(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b3, Throwable -> 0x00b1, all -> 0x00aa }
        r0 = r2 + 1;
        r2 = r0;
        goto L_0x0037;
    L_0x0069:
        r1 = move-exception;
    L_0x006a:
        r1 = d;	 Catch:{ all -> 0x00ac }
        com.umeng.analytics.pro.f.a(r1);	 Catch:{ all -> 0x00ac }
        if (r0 == 0) goto L_0x0074;
    L_0x0071:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00a4 }
    L_0x0074:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x002c;
    L_0x007e:
        r1 = move-exception;
        r1 = r0;
    L_0x0080:
        if (r1 == 0) goto L_0x0085;
    L_0x0082:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00a6 }
    L_0x0085:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x002c;
    L_0x008f:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
    L_0x0093:
        if (r1 == 0) goto L_0x0098;
    L_0x0095:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00a8 }
    L_0x0098:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x00a2:
        r0 = move-exception;
        goto L_0x0023;
    L_0x00a4:
        r0 = move-exception;
        goto L_0x0074;
    L_0x00a6:
        r0 = move-exception;
        goto L_0x0085;
    L_0x00a8:
        r1 = move-exception;
        goto L_0x0098;
    L_0x00aa:
        r0 = move-exception;
        goto L_0x0093;
    L_0x00ac:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
        goto L_0x0093;
    L_0x00b1:
        r0 = move-exception;
        goto L_0x0080;
    L_0x00b3:
        r0 = move-exception;
        r0 = r1;
        goto L_0x006a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.a(boolean, boolean):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0056 A:{Splitter: B:1:0x0001, ExcHandler: android.database.sqlite.SQLiteDatabaseCorruptException (e android.database.sqlite.SQLiteDatabaseCorruptException)} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x006b A:{Splitter: B:1:0x0001, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0081 A:{SYNTHETIC, Splitter: B:30:0x0081} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:17:?, code:
            com.umeng.analytics.pro.f.a(d);
     */
    /* JADX WARNING: Missing block: B:18:0x005c, code:
            if (r0 != null) goto L_0x005e;
     */
    /* JADX WARNING: Missing block: B:20:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:23:0x006c, code:
            if (r0 != null) goto L_0x006e;
     */
    /* JADX WARNING: Missing block: B:25:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:27:0x007b, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:28:0x007c, code:
            r4 = r1;
            r1 = r0;
            r0 = r4;
     */
    public void d() {
        /*
        r5 = this;
        r0 = 0;
        r1 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b, all -> 0x007b }
        r1 = com.umeng.analytics.pro.e.a(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b, all -> 0x007b }
        r0 = r1.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b, all -> 0x007b }
        r0.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        r1 = r5.j;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        r1 = r1.size();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        if (r1 <= 0) goto L_0x003f;
    L_0x0016:
        r1 = 0;
    L_0x0017:
        r2 = r5.j;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        r2 = r2.size();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        if (r1 >= r2) goto L_0x003f;
    L_0x001f:
        r2 = new java.lang.StringBuilder;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        r2.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        r3 = "delete from __et where rowid=";
        r2 = r2.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        r3 = r5.j;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        r3 = r3.get(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        r2 = r2.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        r2 = r2.toString();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        r0.execSQL(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        r1 = r1 + 1;
        goto L_0x0017;
    L_0x003f:
        r1 = r5.j;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        r1.clear();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0056, Throwable -> 0x006b }
        if (r0 == 0) goto L_0x004c;
    L_0x0049:
        r0.endTransaction();	 Catch:{ Throwable -> 0x008e }
    L_0x004c:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x0055:
        return;
    L_0x0056:
        r1 = move-exception;
        r1 = d;	 Catch:{ all -> 0x0096 }
        com.umeng.analytics.pro.f.a(r1);	 Catch:{ all -> 0x0096 }
        if (r0 == 0) goto L_0x0061;
    L_0x005e:
        r0.endTransaction();	 Catch:{ Throwable -> 0x0090 }
    L_0x0061:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x0055;
    L_0x006b:
        r1 = move-exception;
        if (r0 == 0) goto L_0x0071;
    L_0x006e:
        r0.endTransaction();	 Catch:{ Throwable -> 0x0092 }
    L_0x0071:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x0055;
    L_0x007b:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
    L_0x007f:
        if (r1 == 0) goto L_0x0084;
    L_0x0081:
        r1.endTransaction();	 Catch:{ Throwable -> 0x0094 }
    L_0x0084:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x008e:
        r0 = move-exception;
        goto L_0x004c;
    L_0x0090:
        r0 = move-exception;
        goto L_0x0061;
    L_0x0092:
        r0 = move-exception;
        goto L_0x0071;
    L_0x0094:
        r1 = move-exception;
        goto L_0x0084;
    L_0x0096:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
        goto L_0x007f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.d():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0026 A:{Splitter: B:1:0x0001, ExcHandler: android.database.sqlite.SQLiteDatabaseCorruptException (e android.database.sqlite.SQLiteDatabaseCorruptException)} */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x003b A:{Splitter: B:1:0x0001, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0051 A:{SYNTHETIC, Splitter: B:24:0x0051} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:11:?, code:
            com.umeng.analytics.pro.f.a(d);
     */
    /* JADX WARNING: Missing block: B:12:0x002c, code:
            if (r0 != null) goto L_0x002e;
     */
    /* JADX WARNING: Missing block: B:14:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:17:0x003c, code:
            if (r0 != null) goto L_0x003e;
     */
    /* JADX WARNING: Missing block: B:19:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:21:0x004b, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:22:0x004c, code:
            r2 = r1;
            r1 = r0;
            r0 = r2;
     */
    /* JADX WARNING: Missing block: B:25:?, code:
            r1.endTransaction();
     */
    public void e() {
        /*
        r3 = this;
        r0 = 0;
        r1 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0026, Throwable -> 0x003b, all -> 0x004b }
        r1 = com.umeng.analytics.pro.e.a(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0026, Throwable -> 0x003b, all -> 0x004b }
        r0 = r1.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0026, Throwable -> 0x003b, all -> 0x004b }
        r0.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0026, Throwable -> 0x003b }
        r1 = "delete from __er";
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0026, Throwable -> 0x003b }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0026, Throwable -> 0x003b }
        if (r0 == 0) goto L_0x001c;
    L_0x0019:
        r0.endTransaction();	 Catch:{ Throwable -> 0x005e }
    L_0x001c:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x0025:
        return;
    L_0x0026:
        r1 = move-exception;
        r1 = d;	 Catch:{ all -> 0x0066 }
        com.umeng.analytics.pro.f.a(r1);	 Catch:{ all -> 0x0066 }
        if (r0 == 0) goto L_0x0031;
    L_0x002e:
        r0.endTransaction();	 Catch:{ Throwable -> 0x0060 }
    L_0x0031:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x0025;
    L_0x003b:
        r1 = move-exception;
        if (r0 == 0) goto L_0x0041;
    L_0x003e:
        r0.endTransaction();	 Catch:{ Throwable -> 0x0062 }
    L_0x0041:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x0025;
    L_0x004b:
        r1 = move-exception;
        r2 = r1;
        r1 = r0;
        r0 = r2;
    L_0x004f:
        if (r1 == 0) goto L_0x0054;
    L_0x0051:
        r1.endTransaction();	 Catch:{ Throwable -> 0x0064 }
    L_0x0054:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x005e:
        r0 = move-exception;
        goto L_0x001c;
    L_0x0060:
        r0 = move-exception;
        goto L_0x0031;
    L_0x0062:
        r0 = move-exception;
        goto L_0x0041;
    L_0x0064:
        r1 = move-exception;
        goto L_0x0054;
    L_0x0066:
        r1 = move-exception;
        r2 = r1;
        r1 = r0;
        r0 = r2;
        goto L_0x004f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.e():void");
    }

    public void f() {
        b(-1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0033 A:{Splitter: B:1:0x0001, ExcHandler: android.database.sqlite.SQLiteDatabaseCorruptException (e android.database.sqlite.SQLiteDatabaseCorruptException)} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0058 A:{Splitter: B:1:0x0001, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0078 A:{SYNTHETIC, Splitter: B:39:0x0078} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:18:?, code:
            com.umeng.analytics.pro.f.a(d);
     */
    /* JADX WARNING: Missing block: B:19:0x0039, code:
            if (r0 != null) goto L_0x003b;
     */
    /* JADX WARNING: Missing block: B:21:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:28:0x0059, code:
            if (r0 != null) goto L_0x005b;
     */
    /* JADX WARNING: Missing block: B:30:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:40:?, code:
            r1.endTransaction();
     */
    /* JADX WARNING: Missing block: B:53:0x00a0, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:54:0x00a1, code:
            r3 = r1;
            r1 = r0;
            r0 = r3;
     */
    public void b(int r5) {
        /*
        r4 = this;
        r0 = 0;
        r1 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x0033, Throwable -> 0x0058, all -> 0x00a0 }
        r1 = com.umeng.analytics.pro.e.a(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0033, Throwable -> 0x0058, all -> 0x00a0 }
        r0 = r1.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0033, Throwable -> 0x0058, all -> 0x00a0 }
        r0.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0033, Throwable -> 0x0058 }
        r1 = "delete from __dp";
        r2 = -1;
        if (r5 != r2) goto L_0x0029;
    L_0x0014:
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0033, Throwable -> 0x0058 }
    L_0x0017:
        r0.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x0033, Throwable -> 0x0058 }
        if (r0 == 0) goto L_0x001f;
    L_0x001c:
        r0.endTransaction();	 Catch:{ Throwable -> 0x0098 }
    L_0x001f:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x0028:
        return;
    L_0x0029:
        r1 = 1;
        if (r5 != r1) goto L_0x0048;
    L_0x002c:
        r1 = "delete from __dp where __ty=1";
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0033, Throwable -> 0x0058 }
        goto L_0x0017;
    L_0x0033:
        r1 = move-exception;
        r1 = d;	 Catch:{ all -> 0x0072 }
        com.umeng.analytics.pro.f.a(r1);	 Catch:{ all -> 0x0072 }
        if (r0 == 0) goto L_0x003e;
    L_0x003b:
        r0.endTransaction();	 Catch:{ Throwable -> 0x009a }
    L_0x003e:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x0028;
    L_0x0048:
        r1 = 4;
        if (r5 != r1) goto L_0x0068;
    L_0x004b:
        r1 = "delete from __dp where __ty=3";
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0033, Throwable -> 0x0058 }
        r1 = "delete from __dp where __ty=2";
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0033, Throwable -> 0x0058 }
        goto L_0x0017;
    L_0x0058:
        r1 = move-exception;
        if (r0 == 0) goto L_0x005e;
    L_0x005b:
        r0.endTransaction();	 Catch:{ Throwable -> 0x009c }
    L_0x005e:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x0028;
    L_0x0068:
        r1 = 3;
        if (r5 != r1) goto L_0x0085;
    L_0x006b:
        r1 = "delete from __dp where __ty=3";
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0033, Throwable -> 0x0058 }
        goto L_0x0017;
    L_0x0072:
        r1 = move-exception;
        r3 = r1;
        r1 = r0;
        r0 = r3;
    L_0x0076:
        if (r1 == 0) goto L_0x007b;
    L_0x0078:
        r1.endTransaction();	 Catch:{ Throwable -> 0x009e }
    L_0x007b:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x0085:
        if (r5 != 0) goto L_0x008e;
    L_0x0087:
        r1 = "delete from __dp where __ty=0";
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0033, Throwable -> 0x0058 }
        goto L_0x0017;
    L_0x008e:
        r1 = 2;
        if (r5 != r1) goto L_0x0017;
    L_0x0091:
        r1 = "delete from __dp where __ty=2";
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x0033, Throwable -> 0x0058 }
        goto L_0x0017;
    L_0x0098:
        r0 = move-exception;
        goto L_0x001f;
    L_0x009a:
        r0 = move-exception;
        goto L_0x003e;
    L_0x009c:
        r0 = move-exception;
        goto L_0x005e;
    L_0x009e:
        r1 = move-exception;
        goto L_0x007b;
    L_0x00a0:
        r1 = move-exception;
        r3 = r1;
        r1 = r0;
        r0 = r3;
        goto L_0x0076;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.b(int):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0094 A:{SYNTHETIC, Splitter: B:28:0x0094} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0084 A:{SYNTHETIC, Splitter: B:23:0x0084} */
    public void g() {
        /*
        r5 = this;
        r1 = 0;
        r0 = r5.k;
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 != 0) goto L_0x0067;
    L_0x0009:
        r0 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x006a, Throwable -> 0x0080, all -> 0x0091 }
        r0 = com.umeng.analytics.pro.e.a(r0);	 Catch:{ SQLiteDatabaseCorruptException -> 0x006a, Throwable -> 0x0080, all -> 0x0091 }
        r0 = r0.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x006a, Throwable -> 0x0080, all -> 0x0091 }
        r0.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r2 = new java.lang.StringBuilder;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r2.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r3 = "delete from __er where __i=\"";
        r2 = r2.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r3 = r5.k;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r2 = r2.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r3 = "\"";
        r2 = r2.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r2 = r2.toString();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r0.execSQL(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r2 = new java.lang.StringBuilder;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r2.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r3 = "delete from __et where __i=\"";
        r2 = r2.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r3 = r5.k;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r2 = r2.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r3 = "\"";
        r2 = r2.append(r3);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r2 = r2.toString();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r0.execSQL(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00b0, Throwable -> 0x00ae }
        if (r0 == 0) goto L_0x005e;
    L_0x005b:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00a1 }
    L_0x005e:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x0067:
        r5.k = r1;
        return;
    L_0x006a:
        r0 = move-exception;
        r0 = r1;
    L_0x006c:
        r2 = d;	 Catch:{ all -> 0x00a9 }
        com.umeng.analytics.pro.f.a(r2);	 Catch:{ all -> 0x00a9 }
        if (r0 == 0) goto L_0x0076;
    L_0x0073:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00a3 }
    L_0x0076:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x0067;
    L_0x0080:
        r0 = move-exception;
        r0 = r1;
    L_0x0082:
        if (r0 == 0) goto L_0x0087;
    L_0x0084:
        r0.endTransaction();	 Catch:{ Throwable -> 0x00a5 }
    L_0x0087:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x0067;
    L_0x0091:
        r0 = move-exception;
    L_0x0092:
        if (r1 == 0) goto L_0x0097;
    L_0x0094:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00a7 }
    L_0x0097:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x00a1:
        r0 = move-exception;
        goto L_0x005e;
    L_0x00a3:
        r0 = move-exception;
        goto L_0x0076;
    L_0x00a5:
        r0 = move-exception;
        goto L_0x0087;
    L_0x00a7:
        r1 = move-exception;
        goto L_0x0097;
    L_0x00a9:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
        goto L_0x0092;
    L_0x00ae:
        r2 = move-exception;
        goto L_0x0082;
    L_0x00b0:
        r2 = move-exception;
        goto L_0x006c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.g():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x00d6 A:{Splitter: B:1:0x0001, ExcHandler: android.database.sqlite.SQLiteDatabaseCorruptException (e android.database.sqlite.SQLiteDatabaseCorruptException)} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00eb A:{Splitter: B:1:0x0001, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0101 A:{SYNTHETIC, Splitter: B:29:0x0101} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:16:?, code:
            com.umeng.analytics.pro.f.a(d);
     */
    /* JADX WARNING: Missing block: B:17:0x00dc, code:
            if (r0 != null) goto L_0x00de;
     */
    /* JADX WARNING: Missing block: B:19:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:22:0x00ec, code:
            if (r0 != null) goto L_0x00ee;
     */
    /* JADX WARNING: Missing block: B:24:?, code:
            r0.endTransaction();
     */
    /* JADX WARNING: Missing block: B:26:0x00fb, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:27:0x00fc, code:
            r3 = r1;
            r1 = r0;
            r0 = r3;
     */
    /* JADX WARNING: Missing block: B:30:?, code:
            r1.endTransaction();
     */
    public void a(boolean r5, java.lang.String r6) {
        /*
        r4 = this;
        r0 = 0;
        r4.k = r6;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb, all -> 0x00fb }
        r1 = d;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb, all -> 0x00fb }
        r1 = com.umeng.analytics.pro.e.a(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb, all -> 0x00fb }
        r0 = r1.a();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb, all -> 0x00fb }
        r0.beginTransaction();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1 = android.text.TextUtils.isEmpty(r6);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        if (r1 != 0) goto L_0x00c4;
    L_0x0016:
        r1 = new java.lang.StringBuilder;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "delete from __er where __i=\"";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1 = r1.append(r6);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "\"";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1 = r1.toString();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1 = new java.lang.StringBuilder;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "delete from __et where __i=\"";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1 = r1.append(r6);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "\"";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1 = r1.toString();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        if (r5 == 0) goto L_0x00c4;
    L_0x0054:
        r1 = new java.lang.StringBuilder;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "update __sd set __b=\"";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = 0;
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "\" where ";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "__ii";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "=\"";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1 = r1.append(r6);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "\"";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1 = r1.toString();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1 = new java.lang.StringBuilder;	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1.<init>();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "update __sd set __a=\"";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = 0;
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "\" where ";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "__ii";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "=\"";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1 = r1.append(r6);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r2 = "\"";
        r1 = r1.append(r2);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r1 = r1.toString();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        r0.execSQL(r1);	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
    L_0x00c4:
        r0.setTransactionSuccessful();	 Catch:{ SQLiteDatabaseCorruptException -> 0x00d6, Throwable -> 0x00eb }
        if (r0 == 0) goto L_0x00cc;
    L_0x00c9:
        r0.endTransaction();	 Catch:{ Throwable -> 0x010e }
    L_0x00cc:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
    L_0x00d5:
        return;
    L_0x00d6:
        r1 = move-exception;
        r1 = d;	 Catch:{ all -> 0x0116 }
        com.umeng.analytics.pro.f.a(r1);	 Catch:{ all -> 0x0116 }
        if (r0 == 0) goto L_0x00e1;
    L_0x00de:
        r0.endTransaction();	 Catch:{ Throwable -> 0x0110 }
    L_0x00e1:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x00d5;
    L_0x00eb:
        r1 = move-exception;
        if (r0 == 0) goto L_0x00f1;
    L_0x00ee:
        r0.endTransaction();	 Catch:{ Throwable -> 0x0112 }
    L_0x00f1:
        r0 = d;
        r0 = com.umeng.analytics.pro.e.a(r0);
        r0.b();
        goto L_0x00d5;
    L_0x00fb:
        r1 = move-exception;
        r3 = r1;
        r1 = r0;
        r0 = r3;
    L_0x00ff:
        if (r1 == 0) goto L_0x0104;
    L_0x0101:
        r1.endTransaction();	 Catch:{ Throwable -> 0x0114 }
    L_0x0104:
        r1 = d;
        r1 = com.umeng.analytics.pro.e.a(r1);
        r1.b();
        throw r0;
    L_0x010e:
        r0 = move-exception;
        goto L_0x00cc;
    L_0x0110:
        r0 = move-exception;
        goto L_0x00e1;
    L_0x0112:
        r0 = move-exception;
        goto L_0x00f1;
    L_0x0114:
        r1 = move-exception;
        goto L_0x0104;
    L_0x0116:
        r1 = move-exception;
        r3 = r1;
        r1 = r0;
        r0 = r3;
        goto L_0x00ff;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.a(boolean, java.lang.String):void");
    }

    private void i() {
        try {
            if (TextUtils.isEmpty(e)) {
                SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(d);
                Object string = sharedPreferences.getString(g, null);
                if (TextUtils.isEmpty(string)) {
                    string = DeviceConfig.getDBencryptID(d);
                    if (!TextUtils.isEmpty(string)) {
                        sharedPreferences.edit().putString(g, string).commit();
                    }
                }
                if (!TextUtils.isEmpty(string)) {
                    String substring = string.substring(1, 9);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < substring.length(); i++) {
                        char charAt = substring.charAt(i);
                        if (!Character.isDigit(charAt)) {
                            stringBuilder.append(charAt);
                        } else if (Integer.parseInt(Character.toString(charAt)) == 0) {
                            stringBuilder.append(0);
                        } else {
                            stringBuilder.append(10 - Integer.parseInt(Character.toString(charAt)));
                        }
                    }
                    e = stringBuilder.toString();
                }
                if (!TextUtils.isEmpty(e)) {
                    e += new StringBuilder(e).reverse().toString();
                    String string2 = sharedPreferences.getString(h, null);
                    if (TextUtils.isEmpty(string2)) {
                        sharedPreferences.edit().putString(h, a(f)).commit();
                    } else if (!f.equals(b(string2))) {
                        a(true, false);
                    }
                }
            }
        } catch (Throwable th) {
        }
    }

    public String a(String str) {
        try {
            if (TextUtils.isEmpty(e)) {
                return str;
            }
            return Base64.encodeToString(DataHelper.encrypt(str.getBytes(), e.getBytes()), 0);
        } catch (Exception e) {
            return null;
        }
    }

    public String b(String str) {
        try {
            if (TextUtils.isEmpty(e)) {
                return str;
            }
            return new String(DataHelper.decrypt(Base64.decode(str.getBytes(), 0), e.getBytes()));
        } catch (Exception e) {
            return null;
        }
    }
}
