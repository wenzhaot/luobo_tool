package com.baidu.location.e;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.baidu.mapapi.UIMsg.d_ResultType;
import com.baidu.mapapi.UIMsg.m_AppUI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

final class l {
    private static final double[] b = new double[]{45.0d, 135.0d, 225.0d, 315.0d};
    private final h a;
    private final int c;
    private final SQLiteDatabase d;
    private int e = -1;
    private int f = -1;

    private static final class a {
        private double a;
        private double b;

        private a(double d, double d2) {
            this.a = d;
            this.b = d2;
        }
    }

    private enum b {
        AREA("RGCAREA", "area", "addrv", 0, 1000),
        ROAD("RGCROAD", "road", "addrv", 1000, 10000),
        SITE("RGCSITE", "site", "addrv", 100, 50000),
        POI("RGCPOI", "poi", "poiv", 1000, m_AppUI.MSG_APP_GPS);
        
        private final int e;
        private final String f;
        private final String g;
        private final String h;
        private final int i;

        private b(String str, String str2, String str3, int i, int i2) {
            this.f = str;
            this.g = str2;
            this.h = str3;
            this.e = i;
            this.i = i2;
        }

        private String a(int i, double d, double d2) {
            HashSet hashSet = new HashSet();
            hashSet.add(l.b(i, d, d2));
            double d3 = ((double) this.e) * 1.414d;
            if (this.e > 0) {
                int i2 = 0;
                while (true) {
                    int i3 = i2;
                    if (i3 >= l.b.length) {
                        break;
                    }
                    double[] a = l.b(d2, d, d3, l.b[i3]);
                    hashSet.add(l.b(i, a[1], a[0]));
                    i2 = i3 + 1;
                }
            }
            StringBuffer stringBuffer = new StringBuffer();
            Iterator it = hashSet.iterator();
            Object obj = 1;
            while (it.hasNext()) {
                String str = (String) it.next();
                if (obj != null) {
                    obj = null;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append("\"").append(str).append("\"");
            }
            return String.format("SELECT * FROM %s WHERE gridkey IN (%s);", new Object[]{this.f, stringBuffer.toString()});
        }

        private String a(JSONObject jSONObject) {
            Iterator keys = jSONObject.keys();
            StringBuffer stringBuffer = new StringBuffer();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (stringBuffer.length() != 0) {
                    stringBuffer.append(",");
                }
                stringBuffer.append("\"").append(str).append("\"");
            }
            return String.format(Locale.US, "DELETE FROM %s WHERE gridkey IN (%s)", new Object[]{this.f, stringBuffer});
        }

        private static void b(StringBuffer stringBuffer, String str, String str2, int i) {
            if (stringBuffer.length() > 0) {
                stringBuffer.append(",");
            }
            stringBuffer.append("(\"").append(str).append("\",\"").append(str2).append("\",").append(i).append(",").append(System.currentTimeMillis() / 86400000).append(")");
        }

        abstract List<String> a(JSONObject jSONObject, String str, int i);
    }

    l(h hVar, SQLiteDatabase sQLiteDatabase, int i) {
        this.a = hVar;
        this.d = sQLiteDatabase;
        this.c = i;
        if (this.d != null && this.d.isOpen()) {
            try {
                this.d.execSQL("CREATE TABLE IF NOT EXISTS RGCAREA(gridkey VARCHAR(10) PRIMARY KEY, country VARCHAR(100),countrycode VARCHAR(100), province VARCHAR(100), city VARCHAR(100), citycode VARCHAR(100), district VARCHAR(100), timestamp INTEGER, version VARCHAR(50))");
                this.d.execSQL("CREATE TABLE IF NOT EXISTS RGCROAD(_id INTEGER PRIMARY KEY AUTOINCREMENT, gridkey VARCHAR(10), street VARCHAR(100), x1 DOUBLE, y1 DOUBLE, x2 DOUBLE, y2 DOUBLE)");
                this.d.execSQL("CREATE TABLE IF NOT EXISTS RGCSITE(_id INTEGER PRIMARY KEY AUTOINCREMENT, gridkey VARCHAR(10), street VARCHAR(100), streetnumber VARCHAR(100), x DOUBLE, y DOUBLE)");
                this.d.execSQL("CREATE TABLE IF NOT EXISTS RGCPOI(pid VARCHAR(50) PRIMARY KEY , gridkey VARCHAR(10), name VARCHAR(100), type VARCHAR(50), x DOUBLE, y DOUBLE, rank INTEGER)");
                this.d.execSQL("CREATE TABLE IF NOT EXISTS RGCUPDATE(gridkey VARCHAR(10), version VARCHAR(50), type INTEGER, timestamp INTEGER, PRIMARY KEY(gridkey, type))");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private double a(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = ((d5 - d3) * (d - d3)) + ((d6 - d4) * (d2 - d4));
        if (d7 <= 0.0d) {
            return Math.sqrt(((d - d3) * (d - d3)) + ((d2 - d4) * (d2 - d4)));
        }
        double d8 = ((d5 - d3) * (d5 - d3)) + ((d6 - d4) * (d6 - d4));
        if (d7 >= d8) {
            return Math.sqrt(((d - d5) * (d - d5)) + ((d2 - d6) * (d2 - d6)));
        }
        d7 /= d8;
        d8 = ((d5 - d3) * d7) + d3;
        d7 = (d7 * (d6 - d4)) + d4;
        return Math.sqrt(((d7 - d2) * (d7 - d2)) + ((d - d8) * (d - d8)));
    }

    private static int a(int i, int i2) {
        double d;
        int i3;
        if (100 > i2) {
            d = -0.1d;
            i3 = 60000;
        } else if (d_ResultType.SHORT_URL > i2) {
            d = -0.75d;
            i3 = 55500;
        } else {
            d = -0.5d;
            i3 = 0;
        }
        return ((int) (((double) i3) + (d * ((double) i2)))) + i;
    }

    private static String b(int i, double d, double d2) {
        int i2;
        int i3 = i * 5;
        char[] cArr = new char[(i + 1)];
        a aVar = new a(90.0d, -90.0d);
        a aVar2 = new a(180.0d, -180.0d);
        int i4 = 1;
        Object obj = 1;
        int i5 = 0;
        while (i4 <= i3) {
            a aVar3;
            double d3;
            int i6;
            if (obj != null) {
                aVar3 = aVar2;
                d3 = d;
            } else {
                aVar3 = aVar;
                d3 = d2;
            }
            double a = (aVar3.b + aVar3.a) / 2.0d;
            i5 <<= 1;
            if (((int) (d3 * 1000000.0d)) > ((int) (1000000.0d * a))) {
                aVar3.b = a;
                i2 = i5 | 1;
            } else {
                aVar3.a = a;
                i2 = i5;
            }
            if (i4 % 5 == 0) {
                cArr[(i4 / 5) - 1] = "0123456789bcdefghjkmnpqrstuvwxyz".charAt(i2);
                i6 = 0;
            } else {
                i6 = i2;
            }
            i4++;
            obj = obj == null ? 1 : null;
            i5 = i6;
        }
        cArr[i] = 0;
        StringBuffer stringBuffer = new StringBuffer();
        for (i2 = 0; i2 < i; i2++) {
            stringBuffer.append(cArr[i2]);
        }
        return stringBuffer.toString();
    }

    private static double[] b(double d, double d2, double d3, double d4) {
        double[] dArr = new double[2];
        double toRadians = Math.toRadians(d);
        double toRadians2 = Math.toRadians(d2);
        double toRadians3 = Math.toRadians(d4);
        double asin = Math.asin((Math.sin(toRadians) * Math.cos(d3 / 6378137.0d)) + ((Math.cos(toRadians) * Math.sin(d3 / 6378137.0d)) * Math.cos(toRadians3)));
        toRadians = Math.atan2((Math.sin(toRadians3) * Math.sin(d3 / 6378137.0d)) * Math.cos(toRadians), Math.cos(d3 / 6378137.0d) - (Math.sin(toRadians) * Math.sin(asin))) + toRadians2;
        dArr[0] = Math.toDegrees(asin);
        dArr[1] = Math.toDegrees(toRadians);
        return dArr;
    }

    private double c(double d, double d2, double d3, double d4) {
        double d5 = d4 - d2;
        double d6 = d3 - d;
        double toRadians = Math.toRadians(d);
        Math.toRadians(d2);
        double toRadians2 = Math.toRadians(d3);
        Math.toRadians(d4);
        d5 = Math.toRadians(d5);
        d6 = Math.toRadians(d6);
        d5 = (Math.sin(d5 / 2.0d) * ((Math.cos(toRadians) * Math.cos(toRadians2)) * Math.sin(d5 / 2.0d))) + (Math.sin(d6 / 2.0d) * Math.sin(d6 / 2.0d));
        return (Math.atan2(Math.sqrt(d5), Math.sqrt(1.0d - d5)) * 2.0d) * 6378137.0d;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x01ae A:{SYNTHETIC, Splitter: B:85:0x01ae} */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01b3  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x01c3  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01d3  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01e3  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x01f3  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0203  */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0213  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x02f1  */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x02af A:{Splitter: B:67:0x0157, ExcHandler: all (r5_73 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x02df A:{Splitter: B:7:0x0035, ExcHandler: all (r5_21 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x015d A:{Catch:{ Exception -> 0x0276, all -> 0x02af }} */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x01ae A:{SYNTHETIC, Splitter: B:85:0x01ae} */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01b3  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x01c3  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01d3  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01e3  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x01f3  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0203  */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0213  */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x02f1  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x015d A:{Catch:{ Exception -> 0x0276, all -> 0x02af }} */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x01ae A:{SYNTHETIC, Splitter: B:85:0x01ae} */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01b3  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x01c3  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01d3  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01e3  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x01f3  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0203  */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0213  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x02f1  */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x02af A:{Splitter: B:67:0x0157, ExcHandler: all (r5_73 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x02af A:{Splitter: B:67:0x0157, ExcHandler: all (r5_73 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x02df A:{Splitter: B:7:0x0035, ExcHandler: all (r5_21 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x02af A:{Splitter: B:67:0x0157, ExcHandler: all (r5_73 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x02d4 A:{Splitter: B:41:0x0110, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x02af A:{Splitter: B:67:0x0157, ExcHandler: all (r5_73 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x0276 A:{Splitter: B:65:0x014e, ExcHandler: java.lang.Exception (e java.lang.Exception)} */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x02a1 A:{SYNTHETIC, Splitter: B:132:0x02a1} */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01b3  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x01c3  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01d3  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01e3  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x01f3  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0203  */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0213  */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x02f1  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x015d A:{Catch:{ Exception -> 0x0276, all -> 0x02af }} */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x01ae A:{SYNTHETIC, Splitter: B:85:0x01ae} */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01b3  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x01c3  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01d3  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01e3  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x01f3  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0203  */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0213  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x02f1  */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x015d A:{Catch:{ Exception -> 0x0276, all -> 0x02af }} */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x01ae A:{SYNTHETIC, Splitter: B:85:0x01ae} */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01b3  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x01c3  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01d3  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01e3  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x01f3  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0203  */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0213  */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x02f1  */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:122:0x0277, code:
            r5 = null;
            r6 = null;
            r7 = null;
            r8 = null;
            r9 = null;
     */
    /* JADX WARNING: Missing block: B:133:?, code:
            r5.close();
     */
    /* JADX WARNING: Missing block: B:139:0x02af, code:
            r5 = move-exception;
     */
    /* JADX WARNING: Missing block: B:140:0x02b0, code:
            r30 = r5;
            r5 = r4;
            r4 = r30;
     */
    /* JADX WARNING: Missing block: B:142:0x02b7, code:
            r5 = null;
            r6 = null;
            r7 = null;
            r8 = null;
     */
    /* JADX WARNING: Missing block: B:144:0x02c1, code:
            r5 = null;
            r6 = null;
            r7 = null;
     */
    /* JADX WARNING: Missing block: B:146:0x02c9, code:
            r5 = null;
            r6 = null;
     */
    /* JADX WARNING: Missing block: B:150:0x02d4, code:
            r4 = th;
     */
    /* JADX WARNING: Missing block: B:155:0x02df, code:
            r5 = move-exception;
     */
    /* JADX WARNING: Missing block: B:156:0x02e0, code:
            r10 = r4;
            r4 = r5;
     */
    /* JADX WARNING: Missing block: B:161:0x02ed, code:
            r5 = r14;
            r6 = r15;
     */
    com.baidu.location.Address a(double r32, double r34) {
        /*
        r31 = this;
        r24 = 0;
        r23 = 0;
        r22 = 0;
        r21 = 0;
        r20 = 0;
        r25 = 0;
        r12 = 0;
        r11 = 0;
        r10 = 0;
        r4 = com.baidu.location.e.l.b.SITE;	 Catch:{ Exception -> 0x011d, all -> 0x0131 }
        r0 = r31;
        r5 = r0.c;	 Catch:{ Exception -> 0x011d, all -> 0x0131 }
        r6 = r32;
        r8 = r34;
        r4 = r4.a(r5, r6, r8);	 Catch:{ Exception -> 0x011d, all -> 0x0131 }
        r0 = r31;
        r5 = r0.d;	 Catch:{ Exception -> 0x011d, all -> 0x0131 }
        r6 = 0;
        r4 = r5.rawQuery(r4, r6);	 Catch:{ Exception -> 0x011d, all -> 0x0131 }
        r5 = r4.moveToFirst();	 Catch:{ Exception -> 0x02e4, all -> 0x02df }
        if (r5 == 0) goto L_0x031f;
    L_0x002c:
        r6 = 9218868437227405311; // 0x7fefffffffffffff float:NaN double:1.7976931348623157E308;
        r16 = r6;
        r14 = r11;
        r15 = r12;
    L_0x0035:
        r5 = r4.isAfterLast();	 Catch:{ Exception -> 0x02ec, all -> 0x02df }
        if (r5 != 0) goto L_0x0074;
    L_0x003b:
        r5 = 2;
        r19 = r4.getString(r5);	 Catch:{ Exception -> 0x02ec, all -> 0x02df }
        r5 = 3;
        r18 = r4.getString(r5);	 Catch:{ Exception -> 0x02ec, all -> 0x02df }
        r5 = 5;
        r10 = r4.getDouble(r5);	 Catch:{ Exception -> 0x02ec, all -> 0x02df }
        r5 = 4;
        r12 = r4.getDouble(r5);	 Catch:{ Exception -> 0x02ec, all -> 0x02df }
        r5 = r31;
        r6 = r34;
        r8 = r32;
        r8 = r5.c(r6, r8, r10, r12);	 Catch:{ Exception -> 0x02ec, all -> 0x02df }
        r5 = (r8 > r16 ? 1 : (r8 == r16 ? 0 : -1));
        if (r5 >= 0) goto L_0x0319;
    L_0x005d:
        r5 = com.baidu.location.e.l.b.SITE;	 Catch:{ Exception -> 0x02ec, all -> 0x02df }
        r5 = r5.e;	 Catch:{ Exception -> 0x02ec, all -> 0x02df }
        r6 = (double) r5;
        r5 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r5 > 0) goto L_0x0319;
    L_0x0068:
        r5 = r18;
        r6 = r19;
    L_0x006c:
        r4.moveToNext();	 Catch:{ Exception -> 0x02e9, all -> 0x02df }
        r16 = r8;
        r14 = r5;
        r15 = r6;
        goto L_0x0035;
    L_0x0074:
        r5 = r14;
        r6 = r15;
    L_0x0076:
        if (r4 == 0) goto L_0x0314;
    L_0x0078:
        r4.close();	 Catch:{ Exception -> 0x0117 }
        r18 = r5;
        r10 = r6;
    L_0x007e:
        if (r18 != 0) goto L_0x013f;
    L_0x0080:
        r11 = 0;
        r4 = com.baidu.location.e.l.b.ROAD;	 Catch:{ Exception -> 0x025e, all -> 0x026d }
        r0 = r31;
        r5 = r0.c;	 Catch:{ Exception -> 0x025e, all -> 0x026d }
        r6 = r32;
        r8 = r34;
        r4 = r4.a(r5, r6, r8);	 Catch:{ Exception -> 0x025e, all -> 0x026d }
        r0 = r31;
        r5 = r0.d;	 Catch:{ Exception -> 0x025e, all -> 0x026d }
        r6 = 0;
        r19 = r5.rawQuery(r4, r6);	 Catch:{ Exception -> 0x025e, all -> 0x026d }
        r4 = r19.moveToFirst();	 Catch:{ Exception -> 0x02d6, all -> 0x02d4 }
        if (r4 == 0) goto L_0x0138;
    L_0x009e:
        r26 = 9218868437227405311; // 0x7fefffffffffffff float:NaN double:1.7976931348623157E308;
        r4 = "wgs842mc";
        r0 = r32;
        r2 = r34;
        r29 = com.baidu.location.Jni.coorEncrypt(r0, r2, r4);	 Catch:{ Exception -> 0x02d6, all -> 0x02d4 }
        r4 = r10;
    L_0x00af:
        r5 = r19.isAfterLast();	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        if (r5 != 0) goto L_0x0139;
    L_0x00b5:
        r5 = 2;
        r0 = r19;
        r28 = r0.getString(r5);	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r5 = 3;
        r0 = r19;
        r6 = r0.getDouble(r5);	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r5 = 4;
        r0 = r19;
        r8 = r0.getDouble(r5);	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r5 = "wgs842mc";
        r5 = com.baidu.location.Jni.coorEncrypt(r6, r8, r5);	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r6 = 5;
        r0 = r19;
        r6 = r0.getDouble(r6);	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r8 = 6;
        r0 = r19;
        r8 = r0.getDouble(r8);	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r10 = "wgs842mc";
        r16 = com.baidu.location.Jni.coorEncrypt(r6, r8, r10);	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r6 = 0;
        r6 = r29[r6];	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r8 = 1;
        r8 = r29[r8];	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r10 = 0;
        r10 = r5[r10];	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r12 = 1;
        r12 = r5[r12];	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r5 = 0;
        r14 = r16[r5];	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r5 = 1;
        r16 = r16[r5];	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r5 = r31;
        r6 = r5.a(r6, r8, r10, r12, r14, r16);	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r5 = (r6 > r26 ? 1 : (r6 == r26 ? 0 : -1));
        if (r5 >= 0) goto L_0x030f;
    L_0x0102:
        r5 = com.baidu.location.e.l.b.ROAD;	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r5 = r5.e;	 Catch:{ Exception -> 0x02db, all -> 0x02d4 }
        r8 = (double) r5;
        r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r5 > 0) goto L_0x030f;
    L_0x010d:
        r4 = r6;
        r10 = r28;
    L_0x0110:
        r19.moveToNext();	 Catch:{ Exception -> 0x02d6, all -> 0x02d4 }
        r26 = r4;
        r4 = r10;
        goto L_0x00af;
    L_0x0117:
        r4 = move-exception;
        r18 = r5;
        r10 = r6;
        goto L_0x007e;
    L_0x011d:
        r4 = move-exception;
        r4 = r10;
        r5 = r11;
        r6 = r12;
    L_0x0121:
        if (r4 == 0) goto L_0x0314;
    L_0x0123:
        r4.close();	 Catch:{ Exception -> 0x012b }
        r18 = r5;
        r10 = r6;
        goto L_0x007e;
    L_0x012b:
        r4 = move-exception;
        r18 = r5;
        r10 = r6;
        goto L_0x007e;
    L_0x0131:
        r4 = move-exception;
    L_0x0132:
        if (r10 == 0) goto L_0x0137;
    L_0x0134:
        r10.close();	 Catch:{ Exception -> 0x02a5 }
    L_0x0137:
        throw r4;
    L_0x0138:
        r4 = r10;
    L_0x0139:
        if (r19 == 0) goto L_0x030c;
    L_0x013b:
        r19.close();	 Catch:{ Exception -> 0x025a }
        r10 = r4;
    L_0x013f:
        r4 = com.baidu.location.e.l.b.AREA;
        r0 = r31;
        r5 = r0.c;
        r6 = r32;
        r8 = r34;
        r5 = r4.a(r5, r6, r8);
        r4 = 0;
        r0 = r31;
        r6 = r0.d;	 Catch:{ Exception -> 0x0276, all -> 0x0299 }
        r7 = 0;
        r4 = r6.rawQuery(r5, r7);	 Catch:{ Exception -> 0x0276, all -> 0x0299 }
        r5 = r4.moveToFirst();	 Catch:{ Exception -> 0x0276, all -> 0x02af }
        if (r5 == 0) goto L_0x02fe;
    L_0x015d:
        r5 = r4.isAfterLast();	 Catch:{ Exception -> 0x0276, all -> 0x02af }
        if (r5 != 0) goto L_0x02fe;
    L_0x0163:
        r5 = "country";
        r5 = r4.getColumnIndex(r5);	 Catch:{ Exception -> 0x0276, all -> 0x02af }
        r9 = r4.getString(r5);	 Catch:{ Exception -> 0x0276, all -> 0x02af }
        r5 = "countrycode";
        r5 = r4.getColumnIndex(r5);	 Catch:{ Exception -> 0x02b6, all -> 0x02af }
        r8 = r4.getString(r5);	 Catch:{ Exception -> 0x02b6, all -> 0x02af }
        r5 = "province";
        r5 = r4.getColumnIndex(r5);	 Catch:{ Exception -> 0x02c0, all -> 0x02af }
        r7 = r4.getString(r5);	 Catch:{ Exception -> 0x02c0, all -> 0x02af }
        r5 = "city";
        r5 = r4.getColumnIndex(r5);	 Catch:{ Exception -> 0x02c8, all -> 0x02af }
        r6 = r4.getString(r5);	 Catch:{ Exception -> 0x02c8, all -> 0x02af }
        r5 = "citycode";
        r5 = r4.getColumnIndex(r5);	 Catch:{ Exception -> 0x02ce, all -> 0x02af }
        r5 = r4.getString(r5);	 Catch:{ Exception -> 0x02ce, all -> 0x02af }
        r11 = "district";
        r11 = r4.getColumnIndex(r11);	 Catch:{ Exception -> 0x02d2, all -> 0x02af }
        r25 = r4.getString(r11);	 Catch:{ Exception -> 0x02d2, all -> 0x02af }
        r11 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r25;
    L_0x01ac:
        if (r4 == 0) goto L_0x01b1;
    L_0x01ae:
        r4.close();	 Catch:{ Exception -> 0x02aa }
    L_0x01b1:
        if (r11 == 0) goto L_0x01c1;
    L_0x01b3:
        r4 = new java.lang.String;
        r11 = r11.getBytes();
        r11 = com.baidu.android.bbalbs.common.a.b.a(r11);
        r4.<init>(r11);
        r11 = r4;
    L_0x01c1:
        if (r9 == 0) goto L_0x01d1;
    L_0x01c3:
        r4 = new java.lang.String;
        r9 = r9.getBytes();
        r9 = com.baidu.android.bbalbs.common.a.b.a(r9);
        r4.<init>(r9);
        r9 = r4;
    L_0x01d1:
        if (r8 == 0) goto L_0x01e1;
    L_0x01d3:
        r4 = new java.lang.String;
        r8 = r8.getBytes();
        r8 = com.baidu.android.bbalbs.common.a.b.a(r8);
        r4.<init>(r8);
        r8 = r4;
    L_0x01e1:
        if (r7 == 0) goto L_0x01f1;
    L_0x01e3:
        r4 = new java.lang.String;
        r7 = r7.getBytes();
        r7 = com.baidu.android.bbalbs.common.a.b.a(r7);
        r4.<init>(r7);
        r7 = r4;
    L_0x01f1:
        if (r6 == 0) goto L_0x0201;
    L_0x01f3:
        r4 = new java.lang.String;
        r6 = r6.getBytes();
        r6 = com.baidu.android.bbalbs.common.a.b.a(r6);
        r4.<init>(r6);
        r6 = r4;
    L_0x0201:
        if (r5 == 0) goto L_0x0211;
    L_0x0203:
        r4 = new java.lang.String;
        r5 = r5.getBytes();
        r5 = com.baidu.android.bbalbs.common.a.b.a(r5);
        r4.<init>(r5);
        r5 = r4;
    L_0x0211:
        if (r10 == 0) goto L_0x0221;
    L_0x0213:
        r4 = new java.lang.String;
        r10 = r10.getBytes();
        r10 = com.baidu.android.bbalbs.common.a.b.a(r10);
        r4.<init>(r10);
        r10 = r4;
    L_0x0221:
        if (r18 == 0) goto L_0x02f1;
    L_0x0223:
        r4 = new java.lang.String;
        r12 = r18.getBytes();
        r12 = com.baidu.android.bbalbs.common.a.b.a(r12);
        r4.<init>(r12);
    L_0x0230:
        r12 = new com.baidu.location.Address$Builder;
        r12.<init>();
        r11 = r12.country(r11);
        r9 = r11.countryCode(r9);
        r8 = r9.province(r8);
        r7 = r8.city(r7);
        r6 = r7.cityCode(r6);
        r5 = r6.district(r5);
        r5 = r5.street(r10);
        r4 = r5.streetNumber(r4);
        r4 = r4.build();
        return r4;
    L_0x025a:
        r5 = move-exception;
        r10 = r4;
        goto L_0x013f;
    L_0x025e:
        r4 = move-exception;
        r5 = r11;
        r4 = r10;
    L_0x0261:
        if (r5 == 0) goto L_0x030c;
    L_0x0263:
        r5.close();	 Catch:{ Exception -> 0x0269 }
        r10 = r4;
        goto L_0x013f;
    L_0x0269:
        r5 = move-exception;
        r10 = r4;
        goto L_0x013f;
    L_0x026d:
        r4 = move-exception;
        r19 = r11;
    L_0x0270:
        if (r19 == 0) goto L_0x0275;
    L_0x0272:
        r19.close();	 Catch:{ Exception -> 0x02a8 }
    L_0x0275:
        throw r4;
    L_0x0276:
        r5 = move-exception;
        r5 = r20;
        r6 = r21;
        r7 = r22;
        r8 = r23;
        r9 = r24;
    L_0x0281:
        if (r4 == 0) goto L_0x02f5;
    L_0x0283:
        r4.close();	 Catch:{ Exception -> 0x028f }
        r11 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r25;
        goto L_0x01b1;
    L_0x028f:
        r4 = move-exception;
        r11 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r25;
        goto L_0x01b1;
    L_0x0299:
        r5 = move-exception;
        r30 = r5;
        r5 = r4;
        r4 = r30;
    L_0x029f:
        if (r5 == 0) goto L_0x02a4;
    L_0x02a1:
        r5.close();	 Catch:{ Exception -> 0x02ad }
    L_0x02a4:
        throw r4;
    L_0x02a5:
        r5 = move-exception;
        goto L_0x0137;
    L_0x02a8:
        r5 = move-exception;
        goto L_0x0275;
    L_0x02aa:
        r4 = move-exception;
        goto L_0x01b1;
    L_0x02ad:
        r5 = move-exception;
        goto L_0x02a4;
    L_0x02af:
        r5 = move-exception;
        r30 = r5;
        r5 = r4;
        r4 = r30;
        goto L_0x029f;
    L_0x02b6:
        r5 = move-exception;
        r5 = r20;
        r6 = r21;
        r7 = r22;
        r8 = r23;
        goto L_0x0281;
    L_0x02c0:
        r5 = move-exception;
        r5 = r20;
        r6 = r21;
        r7 = r22;
        goto L_0x0281;
    L_0x02c8:
        r5 = move-exception;
        r5 = r20;
        r6 = r21;
        goto L_0x0281;
    L_0x02ce:
        r5 = move-exception;
        r5 = r20;
        goto L_0x0281;
    L_0x02d2:
        r11 = move-exception;
        goto L_0x0281;
    L_0x02d4:
        r4 = move-exception;
        goto L_0x0270;
    L_0x02d6:
        r4 = move-exception;
        r5 = r19;
        r4 = r10;
        goto L_0x0261;
    L_0x02db:
        r5 = move-exception;
        r5 = r19;
        goto L_0x0261;
    L_0x02df:
        r5 = move-exception;
        r10 = r4;
        r4 = r5;
        goto L_0x0132;
    L_0x02e4:
        r5 = move-exception;
        r5 = r11;
        r6 = r12;
        goto L_0x0121;
    L_0x02e9:
        r7 = move-exception;
        goto L_0x0121;
    L_0x02ec:
        r5 = move-exception;
        r5 = r14;
        r6 = r15;
        goto L_0x0121;
    L_0x02f1:
        r4 = r18;
        goto L_0x0230;
    L_0x02f5:
        r11 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r25;
        goto L_0x01b1;
    L_0x02fe:
        r5 = r25;
        r6 = r20;
        r7 = r21;
        r8 = r22;
        r9 = r23;
        r11 = r24;
        goto L_0x01ac;
    L_0x030c:
        r10 = r4;
        goto L_0x013f;
    L_0x030f:
        r10 = r4;
        r4 = r26;
        goto L_0x0110;
    L_0x0314:
        r18 = r5;
        r10 = r6;
        goto L_0x007e;
    L_0x0319:
        r8 = r16;
        r5 = r14;
        r6 = r15;
        goto L_0x006c;
    L_0x031f:
        r5 = r11;
        r6 = r12;
        goto L_0x0076;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.e.l.a(double, double):com.baidu.location.Address");
    }

    void a(JSONObject jSONObject) {
        if (this.d != null && this.d.isOpen()) {
            try {
                this.d.beginTransaction();
                for (b bVar : b.values()) {
                    if (jSONObject.has(bVar.g)) {
                        String str = "";
                        if (jSONObject.has(bVar.h)) {
                            str = jSONObject.getString(bVar.h);
                        }
                        List<String> arrayList = new ArrayList();
                        JSONObject jSONObject2 = jSONObject.getJSONObject(bVar.g);
                        arrayList.add(bVar.a(jSONObject2));
                        arrayList.addAll(bVar.a(jSONObject2, str, bVar.i));
                        for (String str2 : arrayList) {
                            this.d.execSQL(str2);
                        }
                    }
                }
                this.d.setTransactionSuccessful();
                this.e = -1;
                this.f = -1;
                try {
                    this.d.endTransaction();
                } catch (Exception e) {
                }
            } catch (Exception e2) {
                try {
                    this.d.endTransaction();
                } catch (Exception e3) {
                }
            } catch (Throwable th) {
                try {
                    this.d.endTransaction();
                } catch (Exception e4) {
                }
                throw th;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0072 A:{SYNTHETIC, Splitter: B:37:0x0072} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0077 A:{SYNTHETIC, Splitter: B:40:0x0077} */
    boolean a() {
        /*
        r6 = this;
        r3 = -1;
        r0 = 0;
        r1 = 0;
        r2 = r6.a;
        r2 = r2.l();
        r2 = r2.l();
        if (r2 == 0) goto L_0x0055;
    L_0x000f:
        r2 = r6.f;
        if (r2 != r3) goto L_0x0055;
    L_0x0013:
        r2 = r6.e;
        if (r2 != r3) goto L_0x0055;
    L_0x0017:
        r2 = r6.d;
        if (r2 == 0) goto L_0x0055;
    L_0x001b:
        r2 = r6.d;
        r2 = r2.isOpen();
        if (r2 == 0) goto L_0x0055;
    L_0x0023:
        r2 = r6.d;	 Catch:{ Exception -> 0x005f, all -> 0x006e }
        r3 = "SELECT COUNT(*) FROM RGCSITE;";
        r4 = 0;
        r2 = r2.rawQuery(r3, r4);	 Catch:{ Exception -> 0x005f, all -> 0x006e }
        r2.moveToFirst();	 Catch:{ Exception -> 0x0087, all -> 0x0085 }
        r3 = 0;
        r3 = r2.getInt(r3);	 Catch:{ Exception -> 0x0087, all -> 0x0085 }
        r6.f = r3;	 Catch:{ Exception -> 0x0087, all -> 0x0085 }
        r3 = r6.d;	 Catch:{ Exception -> 0x0087, all -> 0x0085 }
        r4 = "SELECT COUNT(*) FROM RGCAREA;";
        r5 = 0;
        r1 = r3.rawQuery(r4, r5);	 Catch:{ Exception -> 0x0087, all -> 0x0085 }
        r1.moveToFirst();	 Catch:{ Exception -> 0x0087, all -> 0x0085 }
        r3 = 0;
        r3 = r1.getInt(r3);	 Catch:{ Exception -> 0x0087, all -> 0x0085 }
        r6.e = r3;	 Catch:{ Exception -> 0x0087, all -> 0x0085 }
        if (r2 == 0) goto L_0x0050;
    L_0x004d:
        r2.close();	 Catch:{ Exception -> 0x007b }
    L_0x0050:
        if (r1 == 0) goto L_0x0055;
    L_0x0052:
        r1.close();	 Catch:{ Exception -> 0x007d }
    L_0x0055:
        r1 = r6.f;
        if (r1 != 0) goto L_0x005e;
    L_0x0059:
        r1 = r6.e;
        if (r1 != 0) goto L_0x005e;
    L_0x005d:
        r0 = 1;
    L_0x005e:
        return r0;
    L_0x005f:
        r2 = move-exception;
        r2 = r1;
    L_0x0061:
        if (r2 == 0) goto L_0x0066;
    L_0x0063:
        r2.close();	 Catch:{ Exception -> 0x007f }
    L_0x0066:
        if (r1 == 0) goto L_0x0055;
    L_0x0068:
        r1.close();	 Catch:{ Exception -> 0x006c }
        goto L_0x0055;
    L_0x006c:
        r1 = move-exception;
        goto L_0x0055;
    L_0x006e:
        r0 = move-exception;
        r2 = r1;
    L_0x0070:
        if (r2 == 0) goto L_0x0075;
    L_0x0072:
        r2.close();	 Catch:{ Exception -> 0x0081 }
    L_0x0075:
        if (r1 == 0) goto L_0x007a;
    L_0x0077:
        r1.close();	 Catch:{ Exception -> 0x0083 }
    L_0x007a:
        throw r0;
    L_0x007b:
        r2 = move-exception;
        goto L_0x0050;
    L_0x007d:
        r1 = move-exception;
        goto L_0x0055;
    L_0x007f:
        r2 = move-exception;
        goto L_0x0066;
    L_0x0081:
        r2 = move-exception;
        goto L_0x0075;
    L_0x0083:
        r1 = move-exception;
        goto L_0x007a;
    L_0x0085:
        r0 = move-exception;
        goto L_0x0070;
    L_0x0087:
        r3 = move-exception;
        goto L_0x0061;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.e.l.a():boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a7 A:{SYNTHETIC, Splitter: B:30:0x00a7} */
    java.util.List<com.baidu.location.Poi> b(double r20, double r22) {
        /*
        r19 = this;
        r8 = 0;
        r14 = new java.util.ArrayList;
        r14.<init>();
        r2 = com.baidu.location.e.l.b.POI;
        r0 = r19;
        r3 = r0.c;
        r4 = r20;
        r6 = r22;
        r4 = r2.a(r3, r4, r6);
        r2 = 0;
        r3 = 0;
        r0 = r19;
        r5 = r0.d;	 Catch:{ Exception -> 0x0099, all -> 0x00a3 }
        r6 = 0;
        r12 = r5.rawQuery(r4, r6);	 Catch:{ Exception -> 0x0099, all -> 0x00a3 }
        r4 = r12.moveToFirst();	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        if (r4 == 0) goto L_0x008e;
    L_0x0025:
        r13 = r3;
    L_0x0026:
        r3 = r12.isAfterLast();	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        if (r3 != 0) goto L_0x008e;
    L_0x002c:
        r3 = 0;
        r15 = r12.getString(r3);	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r3 = 2;
        r16 = r12.getString(r3);	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r3 = 4;
        r10 = r12.getDouble(r3);	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r3 = 5;
        r8 = r12.getDouble(r3);	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r3 = 6;
        r17 = r12.getInt(r3);	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r3 = r19;
        r4 = r22;
        r6 = r20;
        r6 = r3.c(r4, r6, r8, r10);	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r3 = com.baidu.location.e.l.b.POI;	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r3 = r3.e;	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r4 = (double) r3;	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r3 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
        if (r3 >= 0) goto L_0x00b4;
    L_0x005a:
        r4 = new com.baidu.location.Poi;	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r3 = new java.lang.String;	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r5 = r15.getBytes();	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r5 = com.baidu.android.bbalbs.common.a.b.a(r5);	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r3.<init>(r5);	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r5 = new java.lang.String;	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r8 = r16.getBytes();	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r8 = com.baidu.android.bbalbs.common.a.b.a(r8);	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r5.<init>(r8);	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r8 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r4.<init>(r3, r5, r8);	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r3 = (float) r6;	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r3 = java.lang.Math.round(r3);	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r0 = r17;
        r3 = a(r0, r3);	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        if (r3 <= r13) goto L_0x00b4;
    L_0x0088:
        r2 = r4;
    L_0x0089:
        r12.moveToNext();	 Catch:{ Exception -> 0x00b1, all -> 0x00af }
        r13 = r3;
        goto L_0x0026;
    L_0x008e:
        if (r12 == 0) goto L_0x0093;
    L_0x0090:
        r12.close();	 Catch:{ Exception -> 0x00ab }
    L_0x0093:
        if (r2 == 0) goto L_0x0098;
    L_0x0095:
        r14.add(r2);
    L_0x0098:
        return r14;
    L_0x0099:
        r3 = move-exception;
        r3 = r8;
    L_0x009b:
        if (r3 == 0) goto L_0x0093;
    L_0x009d:
        r3.close();	 Catch:{ Exception -> 0x00a1 }
        goto L_0x0093;
    L_0x00a1:
        r3 = move-exception;
        goto L_0x0093;
    L_0x00a3:
        r2 = move-exception;
        r12 = r8;
    L_0x00a5:
        if (r12 == 0) goto L_0x00aa;
    L_0x00a7:
        r12.close();	 Catch:{ Exception -> 0x00ad }
    L_0x00aa:
        throw r2;
    L_0x00ab:
        r3 = move-exception;
        goto L_0x0093;
    L_0x00ad:
        r3 = move-exception;
        goto L_0x00aa;
    L_0x00af:
        r2 = move-exception;
        goto L_0x00a5;
    L_0x00b1:
        r3 = move-exception;
        r3 = r12;
        goto L_0x009b;
    L_0x00b4:
        r3 = r13;
        goto L_0x0089;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.e.l.b(double, double):java.util.List<com.baidu.location.Poi>");
    }

    JSONObject b() {
        Cursor cursor = null;
        Cursor cursor2 = null;
        JSONObject jSONObject = new JSONObject();
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        int currentTimeMillis = (int) (System.currentTimeMillis() / 86400000);
        String str = "SELECT * FROM RGCUPDATE WHERE type=%d AND %d > timestamp+%d ORDER BY gridkey";
        String str2 = "UPDATE RGCUPDATE SET timestamp=timestamp+1 WHERE type = %d AND gridkey IN (%s)";
        try {
            if (this.d != null && this.d.isOpen()) {
                HashSet hashSet;
                String string;
                String[] strArr;
                JSONObject jSONObject2;
                JSONArray jSONArray = new JSONArray();
                JSONArray jSONArray2 = new JSONArray();
                JSONArray jSONArray3 = new JSONArray();
                JSONArray jSONArray4 = new JSONArray();
                cursor2 = this.d.rawQuery(String.format(str, new Object[]{Integer.valueOf(0), Integer.valueOf(currentTimeMillis), Integer.valueOf(this.a.l().p())}), null);
                cursor = this.d.rawQuery(String.format(str, new Object[]{Integer.valueOf(1), Integer.valueOf(currentTimeMillis), Integer.valueOf(this.a.l().q())}), null);
                if (cursor2.moveToFirst()) {
                    hashSet = new HashSet();
                    while (!cursor2.isAfterLast()) {
                        str = cursor2.getString(0);
                        string = cursor2.getString(1);
                        jSONArray3.put(str);
                        hashSet.add(string);
                        if (stringBuffer2.length() > 0) {
                            stringBuffer2.append(",");
                        }
                        stringBuffer2.append("\"").append(str).append("\"");
                        cursor2.moveToNext();
                    }
                    strArr = new String[hashSet.size()];
                    hashSet.toArray(strArr);
                    for (Object put : strArr) {
                        jSONArray4.put(put);
                    }
                }
                if (cursor.moveToFirst()) {
                    hashSet = new HashSet();
                    while (!cursor.isAfterLast()) {
                        str = cursor.getString(0);
                        string = cursor.getString(1);
                        jSONArray.put(str);
                        hashSet.add(string);
                        if (stringBuffer.length() > 0) {
                            stringBuffer.append(",");
                        }
                        stringBuffer.append("\"").append(str).append("\"");
                        cursor.moveToNext();
                    }
                    strArr = new String[hashSet.size()];
                    hashSet.toArray(strArr);
                    for (Object put2 : strArr) {
                        jSONArray2.put(put2);
                    }
                }
                if (jSONArray3.length() != 0) {
                    jSONObject2 = new JSONObject();
                    jSONObject2.put("gk", jSONArray3);
                    jSONObject2.put("ver", jSONArray4);
                    jSONObject.put("addr", jSONObject2);
                }
                if (jSONArray.length() != 0) {
                    jSONObject2 = new JSONObject();
                    jSONObject2.put("gk", jSONArray);
                    jSONObject2.put("ver", jSONArray2);
                    jSONObject.put("poi", jSONObject2);
                }
            }
            if (stringBuffer2.length() > 0) {
                this.d.execSQL(String.format(Locale.US, str2, new Object[]{Integer.valueOf(0), stringBuffer2.toString()}));
            }
            if (stringBuffer.length() > 0) {
                this.d.execSQL(String.format(Locale.US, str2, new Object[]{Integer.valueOf(1), stringBuffer.toString()}));
            }
            if (cursor2 != null) {
                try {
                    cursor2.close();
                } catch (Exception e) {
                }
            }
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e2) {
                }
            }
        } catch (Exception e3) {
            if (cursor2 != null) {
                try {
                    cursor2.close();
                } catch (Exception e4) {
                }
            }
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e5) {
                }
            }
        } catch (Throwable th) {
            if (cursor2 != null) {
                try {
                    cursor2.close();
                } catch (Exception e6) {
                }
            }
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e7) {
                }
            }
        }
        return (jSONObject.has("poi") || jSONObject.has("addr")) ? jSONObject : null;
    }
}
