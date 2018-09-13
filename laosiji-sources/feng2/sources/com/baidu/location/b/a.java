package com.baidu.location.b;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.h.f;
import com.baidu.location.h.k;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONObject;

public class a {
    private static Object b = new Object();
    private static a c = null;
    private static final String d = (k.h() + "/gal.db");
    a a = null;
    private SQLiteDatabase e = null;
    private boolean f = false;
    private String g = null;
    private double h = Double.MAX_VALUE;
    private double i = Double.MAX_VALUE;

    class a extends f {
        int a;
        int b;
        int c;
        int d;
        double e;

        a() {
            this.k = new HashMap();
        }

        public void a() {
            this.h = "http://loc.map.baidu.com/gpsz";
            this.k.put("gpsz", Jni.encode(String.format(Locale.CHINESE, "&x=%d&y=%d&sdk=%f", new Object[]{Integer.valueOf(this.a), Integer.valueOf(this.b), Double.valueOf(this.e)})));
        }

        public void a(double d, double d2, double d3) {
            double[] coorEncrypt = Jni.coorEncrypt(d, d2, "gcj2wgs");
            this.a = (int) Math.floor(coorEncrypt[0] * 100.0d);
            this.b = (int) Math.floor(coorEncrypt[1] * 100.0d);
            this.c = (int) Math.floor(d * 100.0d);
            this.d = (int) Math.floor(d2 * 100.0d);
            this.e = d3;
            a.this.f = true;
            e();
        }

        public void a(boolean z) {
            if (z && this.j != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.j);
                    if (jSONObject != null && jSONObject.has("height")) {
                        String string = jSONObject.getString("height");
                        if (string.contains(",")) {
                            String[] split = string.trim().split(",");
                            int length = split.length;
                            int sqrt = (int) Math.sqrt((double) length);
                            if (sqrt * sqrt == length) {
                                int i = this.c - ((sqrt - 1) / 2);
                                int i2 = this.d - ((sqrt - 1) / 2);
                                for (int i3 = 0; i3 < sqrt; i3++) {
                                    for (length = 0; length < sqrt; length++) {
                                        ContentValues contentValues = new ContentValues();
                                        if (split[(i3 * sqrt) + length].contains("E")) {
                                            contentValues.put("aldata", Double.valueOf(-1000.0d));
                                            contentValues.put("sigma", Double.valueOf(-1000.0d));
                                        } else if (split[(i3 * sqrt) + length].contains(":")) {
                                            String[] split2 = split[(i3 * sqrt) + length].split(":");
                                            if (split2.length == 2) {
                                                contentValues.put("aldata", Double.valueOf(split2[0]));
                                                contentValues.put("sigma", split2[1]);
                                            } else {
                                                contentValues.put("aldata", Double.valueOf(-1000.0d));
                                                contentValues.put("sigma", Double.valueOf(-1000.0d));
                                            }
                                        } else {
                                            contentValues.put("aldata", Double.valueOf(split[(i3 * sqrt) + length]));
                                            contentValues.put("sigma", Double.valueOf(-1000.0d));
                                        }
                                        contentValues.put("tt", Integer.valueOf((int) (System.currentTimeMillis() / 1000)));
                                        int i4 = i + length;
                                        int i5 = i2 + i3;
                                        String format = String.format(Locale.CHINESE, "%d,%d", new Object[]{Integer.valueOf(i4), Integer.valueOf(i5)});
                                        try {
                                            if (a.this.e.update("galdata_new", contentValues, "id = \"" + format + "\"", null) <= 0) {
                                                contentValues.put("id", format);
                                                a.this.e.insert("galdata_new", null, contentValues);
                                            }
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
            a.this.f = false;
        }
    }

    public static a a() {
        a aVar;
        synchronized (b) {
            if (c == null) {
                c = new a();
            }
            aVar = c;
        }
        return aVar;
    }

    private void a(double d, double d2, double d3) {
        if (this.a == null) {
            this.a = new a();
        }
        this.a.a(d, d2, d3);
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00ae A:{SYNTHETIC, Splitter: B:24:0x00ae} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00d8 A:{SYNTHETIC, Splitter: B:41:0x00d8} */
    public double a(double r14, double r16) {
        /*
        r13 = this;
        r8 = 9218868437227405311; // 0x7fefffffffffffff float:NaN double:1.7976931348623157E308;
        r0 = r13.e;
        if (r0 == 0) goto L_0x00e4;
    L_0x0009:
        r0 = 0;
        r1 = java.util.Locale.CHINESE;
        r2 = "%d,%d";
        r3 = 2;
        r3 = new java.lang.Object[r3];
        r4 = 0;
        r6 = 4636737291354636288; // 0x4059000000000000 float:0.0 double:100.0;
        r6 = r6 * r14;
        r6 = java.lang.Math.floor(r6);
        r5 = (int) r6;
        r5 = java.lang.Integer.valueOf(r5);
        r3[r4] = r5;
        r4 = 1;
        r6 = 4636737291354636288; // 0x4059000000000000 float:0.0 double:100.0;
        r6 = r6 * r16;
        r6 = java.lang.Math.floor(r6);
        r5 = (int) r6;
        r5 = java.lang.Integer.valueOf(r5);
        r3[r4] = r5;
        r11 = java.lang.String.format(r1, r2, r3);
        r1 = r13.g;
        if (r1 == 0) goto L_0x0044;
    L_0x0039:
        r1 = r13.g;
        r1 = r1.equals(r11);
        if (r1 == 0) goto L_0x0044;
    L_0x0041:
        r0 = r13.h;
    L_0x0043:
        return r0;
    L_0x0044:
        r1 = r13.e;	 Catch:{ Exception -> 0x00c6, all -> 0x00d3 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00c6, all -> 0x00d3 }
        r2.<init>();	 Catch:{ Exception -> 0x00c6, all -> 0x00d3 }
        r3 = "select * from galdata_new where id = \"";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x00c6, all -> 0x00d3 }
        r2 = r2.append(r11);	 Catch:{ Exception -> 0x00c6, all -> 0x00d3 }
        r3 = "\";";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x00c6, all -> 0x00d3 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x00c6, all -> 0x00d3 }
        r3 = 0;
        r10 = r1.rawQuery(r2, r3);	 Catch:{ Exception -> 0x00c6, all -> 0x00d3 }
        if (r10 == 0) goto L_0x00b4;
    L_0x0068:
        r0 = r10.moveToFirst();	 Catch:{ Exception -> 0x00e0, all -> 0x00de }
        if (r0 == 0) goto L_0x00b4;
    L_0x006e:
        r0 = 1;
        r8 = r10.getDouble(r0);	 Catch:{ Exception -> 0x00e0, all -> 0x00de }
        r0 = 3;
        r0 = r10.getInt(r0);	 Catch:{ Exception -> 0x00e0, all -> 0x00de }
        r2 = -4571364728013586432; // 0xc08f400000000000 float:0.0 double:-1000.0;
        r1 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1));
        if (r1 != 0) goto L_0x0086;
    L_0x0081:
        r8 = 9218868437227405311; // 0x7fefffffffffffff float:NaN double:1.7976931348623157E308;
    L_0x0086:
        r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00e0, all -> 0x00de }
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r2 = r2 / r4;
        r0 = (long) r0;	 Catch:{ Exception -> 0x00e0, all -> 0x00de }
        r0 = r2 - r0;
        r2 = r13.f;	 Catch:{ Exception -> 0x00e0, all -> 0x00de }
        if (r2 != 0) goto L_0x00a7;
    L_0x0094:
        r2 = 604800; // 0x93a80 float:8.47505E-40 double:2.98811E-318;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 <= 0) goto L_0x00a7;
    L_0x009b:
        r6 = 4619803756798672896; // 0x401cd70a40000000 float:2.0 double:7.210000038146973;
        r1 = r13;
        r2 = r14;
        r4 = r16;
        r1.a(r2, r4, r6);	 Catch:{ Exception -> 0x00e0, all -> 0x00de }
    L_0x00a7:
        r13.g = r11;	 Catch:{ Exception -> 0x00e0, all -> 0x00de }
        r13.h = r8;	 Catch:{ Exception -> 0x00e0, all -> 0x00de }
        r0 = r8;
    L_0x00ac:
        if (r10 == 0) goto L_0x0043;
    L_0x00ae:
        r10.close();	 Catch:{ Exception -> 0x00b2 }
        goto L_0x0043;
    L_0x00b2:
        r2 = move-exception;
        goto L_0x0043;
    L_0x00b4:
        r0 = r13.f;	 Catch:{ Exception -> 0x00e0, all -> 0x00de }
        if (r0 != 0) goto L_0x00c4;
    L_0x00b8:
        r6 = 4619803756798672896; // 0x401cd70a40000000 float:2.0 double:7.210000038146973;
        r1 = r13;
        r2 = r14;
        r4 = r16;
        r1.a(r2, r4, r6);	 Catch:{ Exception -> 0x00e0, all -> 0x00de }
    L_0x00c4:
        r0 = r8;
        goto L_0x00ac;
    L_0x00c6:
        r1 = move-exception;
        r2 = r0;
        r0 = r8;
    L_0x00c9:
        if (r2 == 0) goto L_0x0043;
    L_0x00cb:
        r2.close();	 Catch:{ Exception -> 0x00d0 }
        goto L_0x0043;
    L_0x00d0:
        r2 = move-exception;
        goto L_0x0043;
    L_0x00d3:
        r1 = move-exception;
        r10 = r0;
        r0 = r1;
    L_0x00d6:
        if (r10 == 0) goto L_0x00db;
    L_0x00d8:
        r10.close();	 Catch:{ Exception -> 0x00dc }
    L_0x00db:
        throw r0;
    L_0x00dc:
        r1 = move-exception;
        goto L_0x00db;
    L_0x00de:
        r0 = move-exception;
        goto L_0x00d6;
    L_0x00e0:
        r0 = move-exception;
        r2 = r10;
        r0 = r8;
        goto L_0x00c9;
    L_0x00e4:
        r0 = r8;
        goto L_0x0043;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.b.a.a(double, double):double");
    }

    public int a(BDLocation bDLocation) {
        float radius;
        double altitude;
        if (bDLocation != null) {
            radius = bDLocation.getRadius();
            altitude = bDLocation.getAltitude();
        } else {
            altitude = 0.0d;
            radius = 0.0f;
        }
        if (this.e != null && radius > 0.0f && altitude > 0.0d) {
            double a = a(bDLocation.getLongitude(), bDLocation.getLatitude());
            if (a != Double.MAX_VALUE) {
                altitude = Jni.getGpsSwiftRadius(radius, altitude, a);
                return altitude > 50.0d ? 3 : altitude > 20.0d ? 2 : 1;
            }
        }
        return 0;
    }

    public void b() {
        try {
            File file = new File(d);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.exists()) {
                this.e = SQLiteDatabase.openOrCreateDatabase(file, null);
                Cursor rawQuery = this.e.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='galdata'", null);
                if (rawQuery.moveToFirst()) {
                    if (rawQuery.getInt(0) == 0) {
                        this.e.execSQL("CREATE TABLE IF NOT EXISTS galdata_new(id CHAR(40) PRIMARY KEY,aldata DOUBLE, sigma DOUBLE,tt INT);");
                    } else {
                        this.e.execSQL("DROP TABLE galdata");
                        this.e.execSQL("CREATE TABLE galdata_new(id CHAR(40) PRIMARY KEY,aldata DOUBLE, sigma DOUBLE,tt INT);");
                    }
                }
                this.e.setVersion(1);
                rawQuery.close();
            }
        } catch (Exception e) {
            this.e = null;
        }
    }

    public void c() {
        if (this.e != null) {
            try {
                this.e.close();
            } catch (Exception e) {
            } finally {
                this.e = null;
            }
        }
    }
}
