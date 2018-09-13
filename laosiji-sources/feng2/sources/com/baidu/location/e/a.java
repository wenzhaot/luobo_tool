package com.baidu.location.e;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.a.j;
import com.baidu.location.f.f;
import com.baidu.location.f.g;
import com.baidu.location.h.k;
import com.facebook.common.util.UriUtil;
import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONObject;

public final class a {
    private static a b = null;
    private static final String l = (Environment.getExternalStorageDirectory().getPath() + "/baidu/tempdata/");
    private static final String m = (Environment.getExternalStorageDirectory().getPath() + "/baidu/tempdata" + "/ls.db");
    public boolean a = false;
    private String c = null;
    private boolean d = false;
    private boolean e = false;
    private double f = 0.0d;
    private double g = 0.0d;
    private double h = 0.0d;
    private double i = 0.0d;
    private double j = 0.0d;
    private volatile boolean k = false;
    private Handler n = null;

    private class a extends AsyncTask<Boolean, Void, Boolean> {
        private a() {
        }

        /* synthetic */ a(a aVar, b bVar) {
            this();
        }

        /* renamed from: a */
        protected Boolean doInBackground(Boolean... boolArr) {
            SQLiteDatabase sQLiteDatabase = null;
            if (boolArr.length != 4) {
                return Boolean.valueOf(false);
            }
            try {
                sQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(a.m, null);
            } catch (Exception e) {
            }
            if (sQLiteDatabase == null) {
                return Boolean.valueOf(false);
            }
            int currentTimeMillis = (int) (System.currentTimeMillis() >> 28);
            try {
                sQLiteDatabase.beginTransaction();
                if (boolArr[0].booleanValue()) {
                    try {
                        sQLiteDatabase.execSQL("delete from wof where ac < " + (currentTimeMillis - 35));
                    } catch (Exception e2) {
                    }
                }
                if (boolArr[1].booleanValue()) {
                    try {
                        sQLiteDatabase.execSQL("delete from bdcltb09 where ac is NULL or ac < " + (currentTimeMillis - 130));
                    } catch (Exception e3) {
                    }
                }
                sQLiteDatabase.setTransactionSuccessful();
                sQLiteDatabase.endTransaction();
                sQLiteDatabase.close();
            } catch (Exception e4) {
            }
            return Boolean.valueOf(true);
        }
    }

    private class b extends AsyncTask<Object, Void, Boolean> {
        private b() {
        }

        /* synthetic */ b(a aVar, b bVar) {
            this();
        }

        /* renamed from: a */
        protected Boolean doInBackground(Object... objArr) {
            SQLiteDatabase sQLiteDatabase = null;
            if (objArr.length != 4) {
                a.this.k = false;
                return Boolean.valueOf(false);
            }
            SQLiteDatabase openOrCreateDatabase;
            try {
                openOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase(a.m, null);
            } catch (Exception e) {
                openOrCreateDatabase = sQLiteDatabase;
            }
            if (openOrCreateDatabase == null) {
                a.this.k = false;
                return Boolean.valueOf(false);
            }
            try {
                openOrCreateDatabase.beginTransaction();
                a.this.a((String) objArr[0], (com.baidu.location.f.a) objArr[1], openOrCreateDatabase);
                a.this.a((f) objArr[2], (BDLocation) objArr[3], openOrCreateDatabase);
                openOrCreateDatabase.setTransactionSuccessful();
                openOrCreateDatabase.endTransaction();
                openOrCreateDatabase.close();
            } catch (Exception e2) {
            }
            a.this.k = false;
            return Boolean.valueOf(true);
        }
    }

    private a() {
        d();
    }

    public static synchronized a a() {
        a aVar;
        synchronized (a.class) {
            if (b == null) {
                b = new a();
            }
            aVar = b;
        }
        return aVar;
    }

    private void a(f fVar, BDLocation bDLocation, SQLiteDatabase sQLiteDatabase) {
        if (bDLocation != null && bDLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
            if (("wf".equals(bDLocation.getNetworkLocationType()) || bDLocation.getRadius() < 300.0f) && fVar.a != null) {
                int currentTimeMillis = (int) (System.currentTimeMillis() >> 28);
                System.currentTimeMillis();
                int i = 0;
                for (ScanResult scanResult : fVar.a) {
                    if (scanResult.level != 0) {
                        int i2 = i + 1;
                        if (i2 <= 6) {
                            ContentValues contentValues = new ContentValues();
                            String encode2 = Jni.encode2(scanResult.BSSID.replace(":", ""));
                            try {
                                int i3;
                                int i4;
                                double d;
                                Object obj;
                                double d2;
                                Cursor rawQuery = sQLiteDatabase.rawQuery("select * from wof where id = \"" + encode2 + "\";", null);
                                if (rawQuery == null || !rawQuery.moveToFirst()) {
                                    i3 = 0;
                                    i4 = 0;
                                    d = 0.0d;
                                    obj = null;
                                    d2 = 0.0d;
                                } else {
                                    double d3 = rawQuery.getDouble(1) - 113.2349d;
                                    double d4 = rawQuery.getDouble(2) - 432.1238d;
                                    int i5 = rawQuery.getInt(4);
                                    i3 = rawQuery.getInt(5);
                                    i4 = i5;
                                    d = d3;
                                    double d5 = d4;
                                    obj = 1;
                                    d2 = d5;
                                }
                                if (rawQuery != null) {
                                    rawQuery.close();
                                }
                                if (obj == null) {
                                    contentValues.put("mktime", Double.valueOf(bDLocation.getLongitude() + 113.2349d));
                                    contentValues.put("time", Double.valueOf(bDLocation.getLatitude() + 432.1238d));
                                    contentValues.put("bc", Integer.valueOf(1));
                                    contentValues.put("cc", Integer.valueOf(1));
                                    contentValues.put("ac", Integer.valueOf(currentTimeMillis));
                                    contentValues.put("id", encode2);
                                    sQLiteDatabase.insert("wof", null, contentValues);
                                } else if (i3 == 0) {
                                    i = i2;
                                } else {
                                    float[] fArr = new float[1];
                                    Location.distanceBetween(d2, d, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
                                    if (fArr[0] > 1500.0f) {
                                        int i6 = i3 + 1;
                                        if (i6 <= 10 || i6 <= i4 * 3) {
                                            contentValues.put("cc", Integer.valueOf(i6));
                                        } else {
                                            contentValues.put("mktime", Double.valueOf(bDLocation.getLongitude() + 113.2349d));
                                            contentValues.put("time", Double.valueOf(bDLocation.getLatitude() + 432.1238d));
                                            contentValues.put("bc", Integer.valueOf(1));
                                            contentValues.put("cc", Integer.valueOf(1));
                                            contentValues.put("ac", Integer.valueOf(currentTimeMillis));
                                        }
                                    } else {
                                        d2 = ((d2 * ((double) i4)) + bDLocation.getLatitude()) / ((double) (i4 + 1));
                                        ContentValues contentValues2 = contentValues;
                                        contentValues2.put("mktime", Double.valueOf((((d * ((double) i4)) + bDLocation.getLongitude()) / ((double) (i4 + 1))) + 113.2349d));
                                        contentValues.put("time", Double.valueOf(d2 + 432.1238d));
                                        contentValues.put("bc", Integer.valueOf(i4 + 1));
                                        contentValues.put("ac", Integer.valueOf(currentTimeMillis));
                                    }
                                    try {
                                        if (sQLiteDatabase.update("wof", contentValues, "id = \"" + encode2 + "\"", null) <= 0) {
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            } catch (Exception e2) {
                            }
                            i = i2;
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0069 A:{Splitter: B:5:0x000f, ExcHandler: java.lang.Exception (e java.lang.Exception)} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0078 A:{SYNTHETIC, Splitter: B:23:0x0078} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:16:0x006a, code:
            if (r0 != null) goto L_0x006c;
     */
    /* JADX WARNING: Missing block: B:18:?, code:
            r0.close();
     */
    /* JADX WARNING: Missing block: B:24:?, code:
            r1.close();
     */
    /* JADX WARNING: Missing block: B:27:0x007e, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:28:0x007f, code:
            r6 = r1;
            r1 = r0;
            r0 = r6;
     */
    /* JADX WARNING: Missing block: B:32:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:35:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:36:?, code:
            return;
     */
    private void a(java.lang.String r8, android.database.sqlite.SQLiteDatabase r9) {
        /*
        r7 = this;
        r0 = 0;
        if (r8 == 0) goto L_0x000b;
    L_0x0003:
        r1 = r7.c;
        r1 = r8.equals(r1);
        if (r1 == 0) goto L_0x000c;
    L_0x000b:
        return;
    L_0x000c:
        r1 = 0;
        r7.d = r1;
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0069, all -> 0x0072 }
        r1.<init>();	 Catch:{ Exception -> 0x0069, all -> 0x0072 }
        r2 = "select * from bdcltb09 where id = \"";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x0069, all -> 0x0072 }
        r1 = r1.append(r8);	 Catch:{ Exception -> 0x0069, all -> 0x0072 }
        r2 = "\";";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x0069, all -> 0x0072 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0069, all -> 0x0072 }
        r2 = 0;
        r0 = r9.rawQuery(r1, r2);	 Catch:{ Exception -> 0x0069, all -> 0x0072 }
        r7.c = r8;	 Catch:{ Exception -> 0x0069, all -> 0x007e }
        r1 = r0.moveToFirst();	 Catch:{ Exception -> 0x0069, all -> 0x007e }
        if (r1 == 0) goto L_0x0061;
    L_0x0037:
        r1 = 1;
        r2 = r0.getDouble(r1);	 Catch:{ Exception -> 0x0069, all -> 0x007e }
        r4 = 4653148304163072062; // 0x40934dbaacd9e83e float:-6.193295E-12 double:1235.4323;
        r2 = r2 - r4;
        r7.g = r2;	 Catch:{ Exception -> 0x0069, all -> 0x007e }
        r1 = 2;
        r2 = r0.getDouble(r1);	 Catch:{ Exception -> 0x0069, all -> 0x007e }
        r4 = 4661478502002851840; // 0x40b0e60000000000 float:0.0 double:4326.0;
        r2 = r2 - r4;
        r7.f = r2;	 Catch:{ Exception -> 0x0069, all -> 0x007e }
        r1 = 3;
        r2 = r0.getDouble(r1);	 Catch:{ Exception -> 0x0069, all -> 0x007e }
        r4 = 4657424210545395263; // 0x40a27ea4b5dcc63f float:-1.6448975E-6 double:2367.3217;
        r2 = r2 - r4;
        r7.h = r2;	 Catch:{ Exception -> 0x0069, all -> 0x007e }
        r1 = 1;
        r7.d = r1;	 Catch:{ Exception -> 0x0069, all -> 0x007e }
    L_0x0061:
        if (r0 == 0) goto L_0x000b;
    L_0x0063:
        r0.close();	 Catch:{ Exception -> 0x0067 }
        goto L_0x000b;
    L_0x0067:
        r0 = move-exception;
        goto L_0x000b;
    L_0x0069:
        r1 = move-exception;
        if (r0 == 0) goto L_0x000b;
    L_0x006c:
        r0.close();	 Catch:{ Exception -> 0x0070 }
        goto L_0x000b;
    L_0x0070:
        r0 = move-exception;
        goto L_0x000b;
    L_0x0072:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x0076:
        if (r1 == 0) goto L_0x007b;
    L_0x0078:
        r1.close();	 Catch:{ Exception -> 0x007c }
    L_0x007b:
        throw r0;
    L_0x007c:
        r1 = move-exception;
        goto L_0x007b;
    L_0x007e:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x0076;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.e.a.a(java.lang.String, android.database.sqlite.SQLiteDatabase):void");
    }

    private void a(String str, com.baidu.location.f.a aVar, SQLiteDatabase sQLiteDatabase) {
        Object obj = null;
        double d = 0.0d;
        if (aVar.b() && j.c().h()) {
            System.currentTimeMillis();
            int currentTimeMillis = (int) (System.currentTimeMillis() >> 28);
            String g = aVar.g();
            try {
                double parseDouble;
                float parseFloat;
                JSONObject jSONObject = new JSONObject(str);
                int parseInt = Integer.parseInt(jSONObject.getJSONObject("result").getString("error"));
                int obj2;
                if (parseInt == BDLocation.TypeNetWorkLocation) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(UriUtil.LOCAL_CONTENT_SCHEME);
                    if (jSONObject2.has("clf")) {
                        String string = jSONObject2.getString("clf");
                        if (string.equals("0")) {
                            JSONObject jSONObject3 = jSONObject2.getJSONObject("point");
                            d = Double.parseDouble(jSONObject3.getString("x"));
                            parseDouble = Double.parseDouble(jSONObject3.getString("y"));
                            parseFloat = Float.parseFloat(jSONObject2.getString("radius"));
                        } else {
                            String[] split = string.split("\\|");
                            d = Double.parseDouble(split[0]);
                            parseDouble = Double.parseDouble(split[1]);
                            parseFloat = Float.parseFloat(split[2]);
                        }
                    }
                    obj2 = 1;
                    parseFloat = 0.0f;
                    parseDouble = 0.0d;
                } else {
                    if (parseInt == BDLocation.TypeServerError) {
                        sQLiteDatabase.delete("bdcltb09", "id = \"" + g + "\"", null);
                        return;
                    }
                    obj2 = 1;
                    parseFloat = 0.0f;
                    parseDouble = 0.0d;
                }
                if (obj2 == null) {
                    d += 1235.4323d;
                    parseDouble += 2367.3217d;
                    float f = 4326.0f + parseFloat;
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("time", Double.valueOf(d));
                    contentValues.put("tag", Float.valueOf(f));
                    contentValues.put("type", Double.valueOf(parseDouble));
                    contentValues.put("ac", Integer.valueOf(currentTimeMillis));
                    try {
                        if (sQLiteDatabase.update("bdcltb09", contentValues, "id = \"" + g + "\"", null) <= 0) {
                            contentValues.put("id", g);
                            sQLiteDatabase.insert("bdcltb09", null, contentValues);
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    private void a(String str, List<ScanResult> list) {
        SQLiteDatabase sQLiteDatabase;
        if (str == null || str.equals(this.c)) {
            sQLiteDatabase = null;
        } else {
            sQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(m, null);
            a(str, sQLiteDatabase);
        }
        if (list != null) {
            if (sQLiteDatabase == null) {
                sQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(m, null);
            }
            a((List) list, sQLiteDatabase);
        }
        if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
            sQLiteDatabase.close();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x0106 A:{SYNTHETIC, Splitter: B:42:0x0106} */
    private void a(java.util.List<android.net.wifi.ScanResult> r25, android.database.sqlite.SQLiteDatabase r26) {
        /*
        r24 = this;
        java.lang.System.currentTimeMillis();
        r2 = 0;
        r0 = r24;
        r0.e = r2;
        if (r25 == 0) goto L_0x0010;
    L_0x000a:
        r2 = r25.size();
        if (r2 != 0) goto L_0x0011;
    L_0x0010:
        return;
    L_0x0011:
        if (r26 == 0) goto L_0x0010;
    L_0x0013:
        if (r25 == 0) goto L_0x0010;
    L_0x0015:
        r2 = 0;
        r16 = 0;
        r14 = 0;
        r12 = 0;
        r11 = 0;
        r3 = 8;
        r0 = new double[r3];
        r21 = r0;
        r19 = 0;
        r18 = 0;
        r4 = new java.lang.StringBuffer;
        r4.<init>();
        r5 = r25.iterator();
        r3 = r2;
    L_0x0030:
        r2 = r5.hasNext();
        if (r2 == 0) goto L_0x0040;
    L_0x0036:
        r2 = r5.next();
        r2 = (android.net.wifi.ScanResult) r2;
        r6 = 10;
        if (r3 <= r6) goto L_0x00aa;
    L_0x0040:
        r2 = 0;
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01ca, all -> 0x01c5 }
        r3.<init>();	 Catch:{ Exception -> 0x01ca, all -> 0x01c5 }
        r5 = "select * from wof where id in (";
        r3 = r3.append(r5);	 Catch:{ Exception -> 0x01ca, all -> 0x01c5 }
        r4 = r4.toString();	 Catch:{ Exception -> 0x01ca, all -> 0x01c5 }
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x01ca, all -> 0x01c5 }
        r4 = ");";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x01ca, all -> 0x01c5 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x01ca, all -> 0x01c5 }
        r4 = 0;
        r0 = r26;
        r13 = r0.rawQuery(r3, r4);	 Catch:{ Exception -> 0x01ca, all -> 0x01c5 }
        r2 = r13.moveToFirst();	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        if (r2 == 0) goto L_0x012c;
    L_0x006d:
        r2 = r13.isAfterLast();	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        if (r2 != 0) goto L_0x0117;
    L_0x0073:
        r2 = 1;
        r2 = r13.getDouble(r2);	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r4 = 4637668614646953253; // 0x405c4f089a027525 float:-2.6978E-23 double:113.2349;
        r4 = r2 - r4;
        r2 = 2;
        r2 = r13.getDouble(r2);	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r6 = 4646309618475430891; // 0x407b01fb15b573eb float:7.3288204E-26 double:432.1238;
        r2 = r2 - r6;
        r6 = 4;
        r6 = r13.getInt(r6);	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r7 = 5;
        r7 = r13.getInt(r7);	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r8 = 8;
        if (r7 <= r8) goto L_0x00d7;
    L_0x0098:
        if (r7 <= r6) goto L_0x00d7;
    L_0x009a:
        r13.moveToNext();	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        goto L_0x006d;
    L_0x009e:
        r2 = move-exception;
        r2 = r13;
    L_0x00a0:
        if (r2 == 0) goto L_0x0010;
    L_0x00a2:
        r2.close();	 Catch:{ Exception -> 0x00a7 }
        goto L_0x0010;
    L_0x00a7:
        r2 = move-exception;
        goto L_0x0010;
    L_0x00aa:
        if (r3 <= 0) goto L_0x00b2;
    L_0x00ac:
        r6 = ",";
        r4.append(r6);
    L_0x00b2:
        r3 = r3 + 1;
        r2 = r2.BSSID;
        r6 = ":";
        r7 = "";
        r2 = r2.replace(r6, r7);
        r2 = com.baidu.location.Jni.encode2(r2);
        r6 = "\"";
        r6 = r4.append(r6);
        r2 = r6.append(r2);
        r6 = "\"";
        r2.append(r6);
        goto L_0x0030;
    L_0x00d7:
        r0 = r24;
        r6 = r0.d;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        if (r6 == 0) goto L_0x0136;
    L_0x00dd:
        r6 = 1;
        r10 = new float[r6];	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r0 = r24;
        r6 = r0.h;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r0 = r24;
        r8 = r0.g;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        android.location.Location.distanceBetween(r2, r4, r6, r8, r10);	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r6 = 0;
        r6 = r10[r6];	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r6 = (double) r6;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r0 = r24;
        r8 = r0.f;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r22 = 4656510908468559872; // 0x409f400000000000 float:0.0 double:2000.0;
        r8 = r8 + r22;
        r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r6 <= 0) goto L_0x010a;
    L_0x00fe:
        r13.moveToNext();	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        goto L_0x006d;
    L_0x0103:
        r2 = move-exception;
    L_0x0104:
        if (r13 == 0) goto L_0x0109;
    L_0x0106:
        r13.close();	 Catch:{ Exception -> 0x01c2 }
    L_0x0109:
        throw r2;
    L_0x010a:
        r11 = 1;
        r16 = r16 + r4;
        r14 = r14 + r2;
        r12 = r12 + 1;
        r2 = r18;
        r3 = r19;
    L_0x0114:
        r4 = 4;
        if (r12 <= r4) goto L_0x01b9;
    L_0x0117:
        if (r12 <= 0) goto L_0x012c;
    L_0x0119:
        r2 = 1;
        r0 = r24;
        r0.e = r2;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r2 = (double) r12;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r2 = r16 / r2;
        r0 = r24;
        r0.i = r2;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r2 = (double) r12;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r2 = r14 / r2;
        r0 = r24;
        r0.j = r2;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
    L_0x012c:
        if (r13 == 0) goto L_0x0010;
    L_0x012e:
        r13.close();	 Catch:{ Exception -> 0x0133 }
        goto L_0x0010;
    L_0x0133:
        r2 = move-exception;
        goto L_0x0010;
    L_0x0136:
        if (r11 == 0) goto L_0x0157;
    L_0x0138:
        r6 = 1;
        r10 = new float[r6];	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r6 = (double) r12;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r6 = r14 / r6;
        r8 = (double) r12;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r8 = r16 / r8;
        android.location.Location.distanceBetween(r2, r4, r6, r8, r10);	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r2 = 0;
        r2 = r10[r2];	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r3 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 <= 0) goto L_0x0152;
    L_0x014d:
        r13.moveToNext();	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        goto L_0x006d;
    L_0x0152:
        r2 = r18;
        r3 = r19;
        goto L_0x0114;
    L_0x0157:
        if (r19 != 0) goto L_0x0165;
    L_0x0159:
        r6 = r18 + 1;
        r21[r18] = r4;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r4 = r6 + 1;
        r21[r6] = r2;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r2 = 1;
        r3 = r2;
        r2 = r4;
        goto L_0x0114;
    L_0x0165:
        r6 = 0;
        r20 = r6;
    L_0x0168:
        r0 = r20;
        r1 = r18;
        if (r0 >= r1) goto L_0x0199;
    L_0x016e:
        r6 = 1;
        r10 = new float[r6];	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r6 = r20 + 1;
        r6 = r21[r6];	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r8 = r21[r20];	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        android.location.Location.distanceBetween(r2, r4, r6, r8, r10);	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r6 = 0;
        r6 = r10[r6];	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r7 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1));
        if (r6 >= 0) goto L_0x01cd;
    L_0x0183:
        r6 = 1;
        r8 = r21[r20];	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r10 = r16 + r8;
        r7 = r20 + 1;
        r8 = r21[r7];	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r8 = r8 + r14;
        r7 = r12 + 1;
    L_0x018f:
        r12 = r20 + 2;
        r20 = r12;
        r14 = r8;
        r16 = r10;
        r11 = r6;
        r12 = r7;
        goto L_0x0168;
    L_0x0199:
        if (r11 == 0) goto L_0x01a6;
    L_0x019b:
        r16 = r16 + r4;
        r14 = r14 + r2;
        r12 = r12 + 1;
        r2 = r18;
        r3 = r19;
        goto L_0x0114;
    L_0x01a6:
        r6 = 8;
        r0 = r18;
        if (r0 >= r6) goto L_0x0117;
    L_0x01ac:
        r6 = r18 + 1;
        r21[r18] = r4;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r4 = r6 + 1;
        r21[r6] = r2;	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r2 = r4;
        r3 = r19;
        goto L_0x0114;
    L_0x01b9:
        r13.moveToNext();	 Catch:{ Exception -> 0x009e, all -> 0x0103 }
        r18 = r2;
        r19 = r3;
        goto L_0x006d;
    L_0x01c2:
        r3 = move-exception;
        goto L_0x0109;
    L_0x01c5:
        r3 = move-exception;
        r13 = r2;
        r2 = r3;
        goto L_0x0104;
    L_0x01ca:
        r3 = move-exception;
        goto L_0x00a0;
    L_0x01cd:
        r6 = r11;
        r7 = r12;
        r8 = r14;
        r10 = r16;
        goto L_0x018f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.e.a.a(java.util.List, android.database.sqlite.SQLiteDatabase):void");
    }

    private String b(boolean z) {
        double d;
        double d2;
        boolean z2;
        boolean z3;
        double d3 = 0.0d;
        if (this.e) {
            d = this.i;
            d2 = this.j;
            d3 = 246.4d;
            z2 = true;
            z3 = true;
        } else if (this.d) {
            d = this.g;
            d2 = this.h;
            d3 = this.f;
            z2 = j.c().h();
            z3 = true;
        } else {
            z2 = false;
            z3 = false;
            d2 = 0.0d;
            d = 0.0d;
        }
        if (!z3) {
            return z ? "{\"result\":{\"time\":\"" + k.a() + "\",\"error\":\"67\"}}" : "{\"result\":{\"time\":\"" + k.a() + "\",\"error\":\"63\"}}";
        } else {
            if (z) {
                return String.format(Locale.CHINA, "{\"result\":{\"time\":\"" + k.a() + "\",\"error\":\"66\"},\"content\":{\"point\":{\"x\":" + "\"%f\",\"y\":\"%f\"},\"radius\":\"%f\",\"isCellChanged\":\"%b\"}}", new Object[]{Double.valueOf(d), Double.valueOf(d2), Double.valueOf(d3), Boolean.valueOf(true)});
            }
            return String.format(Locale.CHINA, "{\"result\":{\"time\":\"" + k.a() + "\",\"error\":\"66\"},\"content\":{\"point\":{\"x\":" + "\"%f\",\"y\":\"%f\"},\"radius\":\"%f\",\"isCellChanged\":\"%b\"}}", new Object[]{Double.valueOf(d), Double.valueOf(d2), Double.valueOf(d3), Boolean.valueOf(z2)});
        }
    }

    private void d() {
        try {
            File file = new File(l);
            File file2 = new File(m);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!file2.exists()) {
                file2.createNewFile();
            }
            if (file2.exists()) {
                SQLiteDatabase openOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase(file2, null);
                openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS bdcltb09(id CHAR(40) PRIMARY KEY,time DOUBLE,tag DOUBLE, type DOUBLE , ac INT);");
                openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS wof(id CHAR(15) PRIMARY KEY,mktime DOUBLE,time DOUBLE, ac INT, bc INT, cc INT);");
                openOrCreateDatabase.setVersion(1);
                openOrCreateDatabase.close();
            }
            this.a = true;
        } catch (Exception e) {
        }
    }

    private void e() {
        SQLiteDatabase openOrCreateDatabase;
        SQLiteDatabase sQLiteDatabase = null;
        boolean z = true;
        try {
            openOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase(m, null);
        } catch (Exception e) {
            openOrCreateDatabase = sQLiteDatabase;
        }
        if (openOrCreateDatabase != null) {
            try {
                long queryNumEntries = DatabaseUtils.queryNumEntries(openOrCreateDatabase, "wof");
                long queryNumEntries2 = DatabaseUtils.queryNumEntries(openOrCreateDatabase, "bdcltb09");
                boolean z2 = queryNumEntries > 10000;
                if (queryNumEntries2 <= 10000) {
                    z = false;
                }
                openOrCreateDatabase.close();
                if (z2 || z) {
                    new a(this, null).execute(new Boolean[]{Boolean.valueOf(z2), Boolean.valueOf(z)});
                }
            } catch (Exception e2) {
            }
        }
    }

    public BDLocation a(String str, List<ScanResult> list, boolean z) {
        if (!this.a) {
            d();
        }
        String str2 = "{\"result\":\"null\"}";
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        String str3 = (FutureTask) newSingleThreadExecutor.submit(new c(this, str, list));
        try {
            str3 = (String) str3.get(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            str3.cancel(true);
            str3 = str2;
            return new BDLocation(str3);
        } catch (ExecutionException e2) {
            str3.cancel(true);
            str3 = str2;
            return new BDLocation(str3);
        } catch (TimeoutException e3) {
            if (z) {
            }
            str3.cancel(true);
            str3 = str2;
            return new BDLocation(str3);
        } finally {
            newSingleThreadExecutor.shutdown();
        }
        return new BDLocation(str3);
    }

    public BDLocation a(boolean z) {
        BDLocation bDLocation = null;
        if (!this.a) {
            d();
        }
        com.baidu.location.f.a f = com.baidu.location.f.b.a().f();
        String g = (f == null || !f.e()) ? null : f.g();
        f o = g.a().o();
        if (o != null) {
            bDLocation = a(g, o.a, true);
        }
        if (bDLocation != null && bDLocation.getLocType() == 66) {
            StringBuffer stringBuffer = new StringBuffer(1024);
            stringBuffer.append(String.format(Locale.CHINA, "&ofl=%f|%f|%f", new Object[]{Double.valueOf(bDLocation.getLatitude()), Double.valueOf(bDLocation.getLongitude()), Float.valueOf(bDLocation.getRadius())}));
            if (o != null && o.a() > 0) {
                stringBuffer.append("&wf=");
                stringBuffer.append(o.c(15));
            }
            if (f != null) {
                stringBuffer.append(f.h());
            }
            stringBuffer.append("&uptype=oldoff");
            stringBuffer.append(k.e(com.baidu.location.f.getServiceContext()));
            stringBuffer.append(com.baidu.location.h.b.a().a(false));
            stringBuffer.append(com.baidu.location.a.a.a().e());
            stringBuffer.toString();
        }
        return bDLocation;
    }

    public void a(String str, com.baidu.location.f.a aVar, f fVar, BDLocation bDLocation) {
        if (!this.a) {
            d();
        }
        int i = (aVar.b() && j.c().h()) ? 0 : true;
        int i2 = (bDLocation == null || bDLocation.getLocType() != BDLocation.TypeNetWorkLocation || (!"wf".equals(bDLocation.getNetworkLocationType()) && bDLocation.getRadius() >= 300.0f)) ? true : 0;
        if (fVar.a == null) {
            i2 = true;
        }
        if ((i == 0 || i2 == 0) && !this.k) {
            this.k = true;
            new b(this, null).execute(new Object[]{str, aVar, fVar, bDLocation});
        }
    }

    public void b() {
        if (this.n == null) {
            this.n = new Handler();
        }
        this.n.postDelayed(new b(this), 3000);
    }
}
