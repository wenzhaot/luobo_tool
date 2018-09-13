package com.baidu.location.e;

import android.database.sqlite.SQLiteDatabase;
import android.support.graphics.drawable.PathInterpolatorCompat;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.h.f;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONObject;

final class k {
    private static final String d = String.format(Locale.US, "DELETE FROM LOG WHERE timestamp NOT IN (SELECT timestamp FROM LOG ORDER BY timestamp DESC LIMIT %d);", new Object[]{Integer.valueOf(PathInterpolatorCompat.MAX_NUM_POINTS)});
    private static final String e = String.format(Locale.US, "SELECT * FROM LOG ORDER BY timestamp DESC LIMIT %d;", new Object[]{Integer.valueOf(3)});
    private String a = null;
    private final SQLiteDatabase b;
    private final a c;

    private class a extends f {
        private int b;
        private long c;
        private String d;
        private boolean e;
        private boolean f;
        private k p;

        a(k kVar) {
            this.p = kVar;
            this.d = null;
            this.e = false;
            this.f = false;
            this.k = new HashMap();
            this.b = 0;
            this.c = -1;
        }

        private void b() {
            if (!this.e) {
                this.d = this.p.b();
                if (this.c != -1 && this.c + 86400000 <= System.currentTimeMillis()) {
                    this.b = 0;
                    this.c = -1;
                }
                if (this.d != null && this.b < 2) {
                    this.e = true;
                    c("https://ofloc.map.baidu.com/offline_loc");
                }
            }
        }

        public void a() {
            this.k.clear();
            this.k.put("qt", "ofbh");
            this.k.put("req", this.d);
            this.h = h.b;
        }

        public void a(boolean z) {
            this.f = false;
            if (z && this.j != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.j);
                    if (jSONObject != null && jSONObject.has("error") && jSONObject.getInt("error") == BDLocation.TypeNetWorkLocation) {
                        this.f = true;
                    }
                } catch (Exception e) {
                }
            }
            if (!this.f) {
                this.b++;
                this.c = System.currentTimeMillis();
            }
            this.p.a(this.f);
            this.e = false;
        }
    }

    k(SQLiteDatabase sQLiteDatabase) {
        this.b = sQLiteDatabase;
        this.c = new a(this);
        if (this.b != null && this.b.isOpen()) {
            try {
                this.b.execSQL("CREATE TABLE IF NOT EXISTS LOG(timestamp LONG PRIMARY KEY, log VARCHAR(4000))");
            } catch (Exception e) {
            }
        }
    }

    private void a(boolean z) {
        if (z && this.a != null) {
            String format = String.format("DELETE FROM LOG WHERE timestamp in (%s);", new Object[]{this.a});
            try {
                if (this.a.length() > 0) {
                    this.b.execSQL(format);
                }
            } catch (Exception e) {
            }
        }
        this.a = null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x006f A:{SYNTHETIC, Splitter: B:31:0x006f} */
    private java.lang.String b() {
        /*
        r9 = this;
        r0 = 0;
        r2 = new org.json.JSONArray;
        r2.<init>();
        r3 = new org.json.JSONObject;
        r3.<init>();
        r1 = r9.b;	 Catch:{ Exception -> 0x0079, all -> 0x0069 }
        r4 = e;	 Catch:{ Exception -> 0x0079, all -> 0x0069 }
        r5 = 0;
        r1 = r1.rawQuery(r4, r5);	 Catch:{ Exception -> 0x0079, all -> 0x0069 }
        if (r1 == 0) goto L_0x0061;
    L_0x0016:
        r4 = r1.getCount();	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
        if (r4 <= 0) goto L_0x0061;
    L_0x001c:
        r4 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
        r4.<init>();	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
        r1.moveToFirst();	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
    L_0x0024:
        r5 = r1.isAfterLast();	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
        if (r5 != 0) goto L_0x0051;
    L_0x002a:
        r5 = 1;
        r5 = r1.getString(r5);	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
        r2.put(r5);	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
        r5 = r4.length();	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
        if (r5 == 0) goto L_0x003e;
    L_0x0038:
        r5 = ",";
        r4.append(r5);	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
    L_0x003e:
        r5 = 0;
        r6 = r1.getLong(r5);	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
        r4.append(r6);	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
        r1.moveToNext();	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
        goto L_0x0024;
    L_0x004a:
        r2 = move-exception;
    L_0x004b:
        if (r1 == 0) goto L_0x0050;
    L_0x004d:
        r1.close();	 Catch:{ Exception -> 0x0073 }
    L_0x0050:
        return r0;
    L_0x0051:
        r5 = "ofloc";
        r3.put(r5, r2);	 Catch:{ JSONException -> 0x007c }
        r0 = r3.toString();	 Catch:{ JSONException -> 0x007c }
    L_0x005b:
        r2 = r4.toString();	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
        r9.a = r2;	 Catch:{ Exception -> 0x004a, all -> 0x0077 }
    L_0x0061:
        if (r1 == 0) goto L_0x0050;
    L_0x0063:
        r1.close();	 Catch:{ Exception -> 0x0067 }
        goto L_0x0050;
    L_0x0067:
        r1 = move-exception;
        goto L_0x0050;
    L_0x0069:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
    L_0x006d:
        if (r1 == 0) goto L_0x0072;
    L_0x006f:
        r1.close();	 Catch:{ Exception -> 0x0075 }
    L_0x0072:
        throw r0;
    L_0x0073:
        r1 = move-exception;
        goto L_0x0050;
    L_0x0075:
        r1 = move-exception;
        goto L_0x0072;
    L_0x0077:
        r0 = move-exception;
        goto L_0x006d;
    L_0x0079:
        r1 = move-exception;
        r1 = r0;
        goto L_0x004b;
    L_0x007c:
        r2 = move-exception;
        goto L_0x005b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.e.k.b():java.lang.String");
    }

    void a() {
        this.c.b();
    }

    void a(String str) {
        String encodeOfflineLocationUpdateRequest = Jni.encodeOfflineLocationUpdateRequest(str);
        try {
            this.b.execSQL(String.format(Locale.US, "INSERT OR IGNORE INTO LOG VALUES (%d,\"%s\");", new Object[]{Long.valueOf(System.currentTimeMillis()), encodeOfflineLocationUpdateRequest}));
            this.b.execSQL(d);
        } catch (Exception e) {
        }
    }
}
