package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public final class p {
    private static p a = null;
    private static q b = null;
    private static boolean c = false;

    /* compiled from: BUGLY */
    class a extends Thread {
        private int a;
        private o b;
        private String c;
        private ContentValues d;
        private boolean e;
        private String[] f;
        private String g;
        private String[] h;
        private String i;
        private String j;
        private String k;
        private String l;
        private String m;
        private String[] n;
        private int o;
        private String p;
        private byte[] q;

        public a(int i, o oVar) {
            this.a = i;
            this.b = oVar;
        }

        public final void a(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
            this.e = z;
            this.c = str;
            this.f = strArr;
            this.g = str2;
            this.h = strArr2;
            this.i = str3;
            this.j = str4;
            this.k = str5;
            this.l = str6;
        }

        public final void a(int i, String str, byte[] bArr) {
            this.o = i;
            this.p = str;
            this.q = bArr;
        }

        public final void run() {
            switch (this.a) {
                case 1:
                    p.this.a(this.c, this.d, this.b);
                    return;
                case 2:
                    p.this.a(this.c, this.m, this.n, this.b);
                    return;
                case 3:
                    p.this.a(this.e, this.c, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.b);
                    return;
                case 4:
                    p.this.a(this.o, this.p, this.q, this.b);
                    return;
                case 5:
                    p.this.a(this.o, this.b);
                    return;
                case 6:
                    p.this.a(this.o, this.p, this.b);
                    return;
                default:
                    return;
            }
        }
    }

    private p(Context context, List<com.tencent.bugly.a> list) {
        b = new q(context, list);
    }

    public static synchronized p a(Context context, List<com.tencent.bugly.a> list) {
        p pVar;
        synchronized (p.class) {
            if (a == null) {
                a = new p(context, list);
            }
            pVar = a;
        }
        return pVar;
    }

    public static synchronized p a() {
        p pVar;
        synchronized (p.class) {
            pVar = a;
        }
        return pVar;
    }

    public final long a(String str, ContentValues contentValues, o oVar, boolean z) {
        return a(str, contentValues, null);
    }

    public final Cursor a(String str, String[] strArr, String str2, String[] strArr2, o oVar, boolean z) {
        return a(false, str, strArr, str2, null, null, null, null, null, null);
    }

    public final int a(String str, String str2, String[] strArr, o oVar, boolean z) {
        return a(str, str2, null, null);
    }

    private synchronized long a(String str, ContentValues contentValues, o oVar) {
        long j = 0;
        synchronized (this) {
            try {
                SQLiteDatabase writableDatabase = b.getWritableDatabase();
                if (!(writableDatabase == null || contentValues == null)) {
                    long replace = writableDatabase.replace(str, "_id", contentValues);
                    if (replace >= 0) {
                        x.c("[Database] insert %s success.", str);
                    } else {
                        x.d("[Database] replace %s error.", str);
                    }
                    j = replace;
                }
                if (oVar != null) {
                    Long.valueOf(j);
                }
            } catch (Throwable th) {
                if (oVar != null) {
                    Long.valueOf(0);
                }
            }
        }
        return j;
    }

    private synchronized Cursor a(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, o oVar) {
        Cursor query;
        try {
            SQLiteDatabase writableDatabase = b.getWritableDatabase();
            if (writableDatabase != null) {
                query = writableDatabase.query(z, str, strArr, str2, strArr2, str3, str4, str5, str6);
            } else {
                query = null;
            }
            if (oVar != null) {
            }
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            if (oVar != null) {
                query = null;
            } else {
                query = null;
            }
        }
        return query;
    }

    private synchronized int a(String str, String str2, String[] strArr, o oVar) {
        int i = 0;
        synchronized (this) {
            try {
                SQLiteDatabase writableDatabase = b.getWritableDatabase();
                if (writableDatabase != null) {
                    i = writableDatabase.delete(str, str2, strArr);
                }
                if (oVar != null) {
                    Integer.valueOf(i);
                }
            } catch (Throwable th) {
                if (oVar != null) {
                    Integer.valueOf(0);
                }
            }
        }
        return i;
    }

    public final boolean a(int i, String str, byte[] bArr, o oVar, boolean z) {
        if (z) {
            return a(i, str, bArr, null);
        }
        Runnable aVar = new a(4, null);
        aVar.a(i, str, bArr);
        w.a().a(aVar);
        return true;
    }

    public final Map<String, byte[]> a(int i, o oVar, boolean z) {
        return a(i, null);
    }

    public final boolean a(int i, String str, o oVar, boolean z) {
        return a(555, str, null);
    }

    private boolean a(int i, String str, byte[] bArr, o oVar) {
        boolean z = false;
        try {
            r rVar = new r();
            rVar.a = (long) i;
            rVar.f = str;
            rVar.e = System.currentTimeMillis();
            rVar.g = bArr;
            z = b(rVar);
            if (oVar != null) {
                Boolean.valueOf(z);
            }
        } catch (Throwable th) {
            if (oVar != null) {
                Boolean.valueOf(z);
            }
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0030  */
    private java.util.Map<java.lang.String, byte[]> a(int r6, com.tencent.bugly.proguard.o r7) {
        /*
        r5 = this;
        r2 = 0;
        r0 = r5.c(r6);	 Catch:{ Throwable -> 0x003c }
        if (r0 == 0) goto L_0x0040;
    L_0x0007:
        r1 = new java.util.HashMap;	 Catch:{ Throwable -> 0x003c }
        r1.<init>();	 Catch:{ Throwable -> 0x003c }
        r2 = r0.iterator();	 Catch:{ Throwable -> 0x0026 }
    L_0x0010:
        r0 = r2.hasNext();	 Catch:{ Throwable -> 0x0026 }
        if (r0 == 0) goto L_0x0036;
    L_0x0016:
        r0 = r2.next();	 Catch:{ Throwable -> 0x0026 }
        r0 = (com.tencent.bugly.proguard.r) r0;	 Catch:{ Throwable -> 0x0026 }
        r3 = r0.g;	 Catch:{ Throwable -> 0x0026 }
        if (r3 == 0) goto L_0x0010;
    L_0x0020:
        r0 = r0.f;	 Catch:{ Throwable -> 0x0026 }
        r1.put(r0, r3);	 Catch:{ Throwable -> 0x0026 }
        goto L_0x0010;
    L_0x0026:
        r0 = move-exception;
        r4 = r0;
        r0 = r1;
        r1 = r4;
    L_0x002a:
        r2 = com.tencent.bugly.proguard.x.a(r1);	 Catch:{ all -> 0x003a }
        if (r2 != 0) goto L_0x0033;
    L_0x0030:
        r1.printStackTrace();	 Catch:{ all -> 0x003a }
    L_0x0033:
        if (r7 == 0) goto L_0x0035;
    L_0x0035:
        return r0;
    L_0x0036:
        r0 = r1;
    L_0x0037:
        if (r7 == 0) goto L_0x0035;
    L_0x0039:
        goto L_0x0035;
    L_0x003a:
        r0 = move-exception;
        throw r0;
    L_0x003c:
        r0 = move-exception;
        r1 = r0;
        r0 = r2;
        goto L_0x002a;
    L_0x0040:
        r0 = r2;
        goto L_0x0037;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.p.a(int, com.tencent.bugly.proguard.o):java.util.Map<java.lang.String, byte[]>");
    }

    public final synchronized boolean a(r rVar) {
        boolean z = false;
        synchronized (this) {
            if (rVar != null) {
                try {
                    SQLiteDatabase writableDatabase = b.getWritableDatabase();
                    if (writableDatabase != null) {
                        ContentValues c = c(rVar);
                        if (c != null) {
                            long replace = writableDatabase.replace("t_lr", "_id", c);
                            if (replace >= 0) {
                                x.c("[Database] insert %s success.", "t_lr");
                                rVar.a = replace;
                                z = true;
                            }
                        }
                    }
                } catch (Throwable th) {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
        return z;
    }

    private synchronized boolean b(r rVar) {
        boolean z = false;
        synchronized (this) {
            if (rVar != null) {
                try {
                    SQLiteDatabase writableDatabase = b.getWritableDatabase();
                    if (writableDatabase != null) {
                        ContentValues d = d(rVar);
                        if (d != null) {
                            long replace = writableDatabase.replace("t_pf", "_id", d);
                            if (replace >= 0) {
                                x.c("[Database] insert %s success.", "t_pf");
                                rVar.a = replace;
                                z = true;
                            }
                        }
                    }
                } catch (Throwable th) {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x008b A:{SYNTHETIC, Splitter: B:44:0x008b} */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0088 A:{Splitter: B:17:0x0034, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:40:?, code:
            com.tencent.bugly.proguard.x.d("[Database] unknown id.", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:42:0x0088, code:
            r0 = th;
     */
    public final synchronized java.util.List<com.tencent.bugly.proguard.r> a(int r10) {
        /*
        r9 = this;
        r8 = 0;
        monitor-enter(r9);
        r0 = b;	 Catch:{ all -> 0x008f }
        r0 = r0.getWritableDatabase();	 Catch:{ all -> 0x008f }
        if (r0 == 0) goto L_0x005e;
    L_0x000a:
        if (r10 < 0) goto L_0x0032;
    L_0x000c:
        r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00cd, all -> 0x00c7 }
        r2 = "_tp = ";
        r1.<init>(r2);	 Catch:{ Throwable -> 0x00cd, all -> 0x00c7 }
        r1 = r1.append(r10);	 Catch:{ Throwable -> 0x00cd, all -> 0x00c7 }
        r3 = r1.toString();	 Catch:{ Throwable -> 0x00cd, all -> 0x00c7 }
    L_0x001c:
        r1 = "t_lr";
        r2 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r2 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x00cd, all -> 0x00c7 }
        if (r2 != 0) goto L_0x0034;
    L_0x002a:
        if (r2 == 0) goto L_0x002f;
    L_0x002c:
        r2.close();	 Catch:{ all -> 0x008f }
    L_0x002f:
        r0 = r8;
    L_0x0030:
        monitor-exit(r9);
        return r0;
    L_0x0032:
        r3 = r8;
        goto L_0x001c;
    L_0x0034:
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        r3.<init>();	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        r1 = new java.util.ArrayList;	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        r1.<init>();	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
    L_0x003e:
        r4 = r2.moveToNext();	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        if (r4 == 0) goto L_0x0092;
    L_0x0044:
        r4 = a(r2);	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        if (r4 == 0) goto L_0x0060;
    L_0x004a:
        r1.add(r4);	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        goto L_0x003e;
    L_0x004e:
        r0 = move-exception;
        r1 = r2;
    L_0x0050:
        r2 = com.tencent.bugly.proguard.x.a(r0);	 Catch:{ all -> 0x00ca }
        if (r2 != 0) goto L_0x0059;
    L_0x0056:
        r0.printStackTrace();	 Catch:{ all -> 0x00ca }
    L_0x0059:
        if (r1 == 0) goto L_0x005e;
    L_0x005b:
        r1.close();	 Catch:{ all -> 0x008f }
    L_0x005e:
        r0 = r8;
        goto L_0x0030;
    L_0x0060:
        r4 = "_id";
        r4 = r2.getColumnIndex(r4);	 Catch:{ Throwable -> 0x007d, all -> 0x0088 }
        r4 = r2.getLong(r4);	 Catch:{ Throwable -> 0x007d, all -> 0x0088 }
        r6 = " or _id";
        r6 = r3.append(r6);	 Catch:{ Throwable -> 0x007d, all -> 0x0088 }
        r7 = " = ";
        r6 = r6.append(r7);	 Catch:{ Throwable -> 0x007d, all -> 0x0088 }
        r6.append(r4);	 Catch:{ Throwable -> 0x007d, all -> 0x0088 }
        goto L_0x003e;
    L_0x007d:
        r4 = move-exception;
        r4 = "[Database] unknown id.";
        r5 = 0;
        r5 = new java.lang.Object[r5];	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        com.tencent.bugly.proguard.x.d(r4, r5);	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        goto L_0x003e;
    L_0x0088:
        r0 = move-exception;
    L_0x0089:
        if (r2 == 0) goto L_0x008e;
    L_0x008b:
        r2.close();	 Catch:{ all -> 0x008f }
    L_0x008e:
        throw r0;	 Catch:{ all -> 0x008f }
    L_0x008f:
        r0 = move-exception;
        monitor-exit(r9);
        throw r0;
    L_0x0092:
        r3 = r3.toString();	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        r4 = r3.length();	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        if (r4 <= 0) goto L_0x00bf;
    L_0x009c:
        r4 = 4;
        r3 = r3.substring(r4);	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        r4 = "t_lr";
        r5 = 0;
        r0 = r0.delete(r4, r3, r5);	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        r3 = "[Database] deleted %s illegal data %d";
        r4 = 2;
        r4 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        r5 = 0;
        r6 = "t_lr";
        r4[r5] = r6;	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        r5 = 1;
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        r4[r5] = r0;	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
        com.tencent.bugly.proguard.x.d(r3, r4);	 Catch:{ Throwable -> 0x004e, all -> 0x0088 }
    L_0x00bf:
        if (r2 == 0) goto L_0x00c4;
    L_0x00c1:
        r2.close();	 Catch:{ all -> 0x008f }
    L_0x00c4:
        r0 = r1;
        goto L_0x0030;
    L_0x00c7:
        r0 = move-exception;
        r2 = r8;
        goto L_0x0089;
    L_0x00ca:
        r0 = move-exception;
        r2 = r1;
        goto L_0x0089;
    L_0x00cd:
        r0 = move-exception;
        r1 = r8;
        goto L_0x0050;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.p.a(int):java.util.List<com.tencent.bugly.proguard.r>");
    }

    public final synchronized void a(List<r> list) {
        if (list != null) {
            if (list.size() != 0) {
                SQLiteDatabase writableDatabase = b.getWritableDatabase();
                if (writableDatabase != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (r rVar : list) {
                        stringBuilder.append(" or _id").append(" = ").append(rVar.a);
                    }
                    String stringBuilder2 = stringBuilder.toString();
                    if (stringBuilder2.length() > 0) {
                        stringBuilder2 = stringBuilder2.substring(4);
                    }
                    stringBuilder.setLength(0);
                    try {
                        int delete = writableDatabase.delete("t_lr", stringBuilder2, null);
                        x.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(delete));
                    } catch (Throwable th) {
                        if (!x.a(th)) {
                            th.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public final synchronized void b(int i) {
        String str = null;
        synchronized (this) {
            SQLiteDatabase writableDatabase = b.getWritableDatabase();
            if (writableDatabase != null) {
                if (i >= 0) {
                    try {
                        str = "_tp = " + i;
                    } catch (Throwable th) {
                        if (!x.a(th)) {
                            th.printStackTrace();
                        }
                    }
                }
                int delete = writableDatabase.delete("t_lr", str, null);
                x.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(delete));
            }
        }
    }

    private static ContentValues c(r rVar) {
        if (rVar == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (rVar.a > 0) {
                contentValues.put("_id", Long.valueOf(rVar.a));
            }
            contentValues.put("_tp", Integer.valueOf(rVar.b));
            contentValues.put("_pc", rVar.c);
            contentValues.put("_th", rVar.d);
            contentValues.put("_tm", Long.valueOf(rVar.e));
            if (rVar.g != null) {
                contentValues.put("_dt", rVar.g);
            }
            return contentValues;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private static r a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            r rVar = new r();
            rVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            rVar.b = cursor.getInt(cursor.getColumnIndex("_tp"));
            rVar.c = cursor.getString(cursor.getColumnIndex("_pc"));
            rVar.d = cursor.getString(cursor.getColumnIndex("_th"));
            rVar.e = cursor.getLong(cursor.getColumnIndex("_tm"));
            rVar.g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return rVar;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x0087 A:{SYNTHETIC, Splitter: B:40:0x0087} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0084 A:{Splitter: B:13:0x0030, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:36:?, code:
            com.tencent.bugly.proguard.x.d("[Database] unknown id.", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:38:0x0084, code:
            r0 = th;
     */
    private synchronized java.util.List<com.tencent.bugly.proguard.r> c(int r10) {
        /*
        r9 = this;
        r8 = 0;
        monitor-enter(r9);
        r0 = b;	 Catch:{ Throwable -> 0x00d6, all -> 0x00d0 }
        r0 = r0.getWritableDatabase();	 Catch:{ Throwable -> 0x00d6, all -> 0x00d0 }
        if (r0 == 0) goto L_0x005a;
    L_0x000a:
        r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00d6, all -> 0x00d0 }
        r2 = "_id = ";
        r1.<init>(r2);	 Catch:{ Throwable -> 0x00d6, all -> 0x00d0 }
        r1 = r1.append(r10);	 Catch:{ Throwable -> 0x00d6, all -> 0x00d0 }
        r3 = r1.toString();	 Catch:{ Throwable -> 0x00d6, all -> 0x00d0 }
        r1 = "t_pf";
        r2 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r2 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x00d6, all -> 0x00d0 }
        if (r2 != 0) goto L_0x0030;
    L_0x0028:
        if (r2 == 0) goto L_0x002d;
    L_0x002a:
        r2.close();	 Catch:{ all -> 0x008b }
    L_0x002d:
        r0 = r8;
    L_0x002e:
        monitor-exit(r9);
        return r0;
    L_0x0030:
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        r4.<init>();	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        r1 = new java.util.ArrayList;	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        r1.<init>();	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
    L_0x003a:
        r5 = r2.moveToNext();	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        if (r5 == 0) goto L_0x008e;
    L_0x0040:
        r5 = b(r2);	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        if (r5 == 0) goto L_0x005c;
    L_0x0046:
        r1.add(r5);	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        goto L_0x003a;
    L_0x004a:
        r0 = move-exception;
        r1 = r2;
    L_0x004c:
        r2 = com.tencent.bugly.proguard.x.a(r0);	 Catch:{ all -> 0x00d3 }
        if (r2 != 0) goto L_0x0055;
    L_0x0052:
        r0.printStackTrace();	 Catch:{ all -> 0x00d3 }
    L_0x0055:
        if (r1 == 0) goto L_0x005a;
    L_0x0057:
        r1.close();	 Catch:{ all -> 0x008b }
    L_0x005a:
        r0 = r8;
        goto L_0x002e;
    L_0x005c:
        r5 = "_tp";
        r5 = r2.getColumnIndex(r5);	 Catch:{ Throwable -> 0x0079, all -> 0x0084 }
        r5 = r2.getString(r5);	 Catch:{ Throwable -> 0x0079, all -> 0x0084 }
        r6 = " or _tp";
        r6 = r4.append(r6);	 Catch:{ Throwable -> 0x0079, all -> 0x0084 }
        r7 = " = ";
        r6 = r6.append(r7);	 Catch:{ Throwable -> 0x0079, all -> 0x0084 }
        r6.append(r5);	 Catch:{ Throwable -> 0x0079, all -> 0x0084 }
        goto L_0x003a;
    L_0x0079:
        r5 = move-exception;
        r5 = "[Database] unknown id.";
        r6 = 0;
        r6 = new java.lang.Object[r6];	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        com.tencent.bugly.proguard.x.d(r5, r6);	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        goto L_0x003a;
    L_0x0084:
        r0 = move-exception;
    L_0x0085:
        if (r2 == 0) goto L_0x008a;
    L_0x0087:
        r2.close();	 Catch:{ all -> 0x008b }
    L_0x008a:
        throw r0;	 Catch:{ all -> 0x008b }
    L_0x008b:
        r0 = move-exception;
        monitor-exit(r9);
        throw r0;
    L_0x008e:
        r5 = r4.length();	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        if (r5 <= 0) goto L_0x00c8;
    L_0x0094:
        r5 = " and _id";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        r5 = " = ";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        r4.append(r10);	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        r4 = 4;
        r3 = r3.substring(r4);	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        r4 = "t_pf";
        r5 = 0;
        r0 = r0.delete(r4, r3, r5);	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        r3 = "[Database] deleted %s illegal data %d.";
        r4 = 2;
        r4 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        r5 = 0;
        r6 = "t_pf";
        r4[r5] = r6;	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        r5 = 1;
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        r4[r5] = r0;	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
        com.tencent.bugly.proguard.x.d(r3, r4);	 Catch:{ Throwable -> 0x004a, all -> 0x0084 }
    L_0x00c8:
        if (r2 == 0) goto L_0x00cd;
    L_0x00ca:
        r2.close();	 Catch:{ all -> 0x008b }
    L_0x00cd:
        r0 = r1;
        goto L_0x002e;
    L_0x00d0:
        r0 = move-exception;
        r2 = r8;
        goto L_0x0085;
    L_0x00d3:
        r0 = move-exception;
        r2 = r1;
        goto L_0x0085;
    L_0x00d6:
        r0 = move-exception;
        r1 = r8;
        goto L_0x004c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.p.c(int):java.util.List<com.tencent.bugly.proguard.r>");
    }

    private synchronized boolean a(int i, String str, o oVar) {
        boolean z = true;
        boolean z2 = false;
        synchronized (this) {
            try {
                SQLiteDatabase writableDatabase = b.getWritableDatabase();
                if (writableDatabase != null) {
                    String str2;
                    if (z.a(str)) {
                        str2 = "_id = " + i;
                    } else {
                        str2 = "_id = " + i + " and _tp" + " = \"" + str + "\"";
                    }
                    x.c("[Database] deleted %s data %d", "t_pf", Integer.valueOf(writableDatabase.delete("t_pf", str2, null)));
                    if (writableDatabase.delete("t_pf", str2, null) <= 0) {
                        z = false;
                    }
                    z2 = z;
                }
                if (oVar != null) {
                    Boolean.valueOf(z2);
                }
            } catch (Throwable th) {
                if (oVar != null) {
                    Boolean.valueOf(false);
                }
            }
        }
        return z2;
    }

    private static ContentValues d(r rVar) {
        if (rVar == null || z.a(rVar.f)) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (rVar.a > 0) {
                contentValues.put("_id", Long.valueOf(rVar.a));
            }
            contentValues.put("_tp", rVar.f);
            contentValues.put("_tm", Long.valueOf(rVar.e));
            if (rVar.g == null) {
                return contentValues;
            }
            contentValues.put("_dt", rVar.g);
            return contentValues;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static r b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            r rVar = new r();
            rVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            rVar.e = cursor.getLong(cursor.getColumnIndex("_tm"));
            rVar.f = cursor.getString(cursor.getColumnIndex("_tp"));
            rVar.g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return rVar;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}
