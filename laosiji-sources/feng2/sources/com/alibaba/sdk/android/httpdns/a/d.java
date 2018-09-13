package com.alibaba.sdk.android.httpdns.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Iterator;
import java.util.List;

class d extends SQLiteOpenHelper {
    private static final Object a = new Object();

    d(Context context) {
        super(context, "aliclound_httpdns.db", null, 1);
    }

    private long a(SQLiteDatabase sQLiteDatabase, g gVar) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("host_id", Long.valueOf(gVar.g));
        contentValues.put("ip", gVar.m);
        contentValues.put("ttl", gVar.n);
        try {
            return sQLiteDatabase.insert("ip", null, contentValues);
        } catch (Exception e) {
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c2 A:{Splitter: B:3:0x002d, ExcHandler: java.lang.Exception (e java.lang.Exception)} */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b2  */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:26:0x00ad, code:
            r1.close();
     */
    /* JADX WARNING: Missing block: B:28:0x00b2, code:
            r2.close();
     */
    /* JADX WARNING: Missing block: B:30:0x00b6, code:
            r2 = move-exception;
     */
    /* JADX WARNING: Missing block: B:31:0x00b7, code:
            r7 = r2;
            r2 = r1;
            r1 = r0;
            r0 = r7;
     */
    private java.util.List<com.alibaba.sdk.android.httpdns.a.g> a(long r10) {
        /*
        r9 = this;
        r0 = 0;
        r2 = new java.util.ArrayList;
        r2.<init>();
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r1 = "SELECT * FROM ";
        r3.append(r1);
        r1 = "ip";
        r3.append(r1);
        r1 = " WHERE ";
        r3.append(r1);
        r1 = "host_id";
        r3.append(r1);
        r1 = " =? ;";
        r3.append(r1);
        r1 = r9.getWritableDatabase();	 Catch:{ Exception -> 0x0099, all -> 0x00a6 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x00c2, all -> 0x00b6 }
        r4 = 1;
        r4 = new java.lang.String[r4];	 Catch:{ Exception -> 0x00c2, all -> 0x00b6 }
        r5 = 0;
        r6 = java.lang.String.valueOf(r10);	 Catch:{ Exception -> 0x00c2, all -> 0x00b6 }
        r4[r5] = r6;	 Catch:{ Exception -> 0x00c2, all -> 0x00b6 }
        r0 = r1.rawQuery(r3, r4);	 Catch:{ Exception -> 0x00c2, all -> 0x00b6 }
        if (r0 == 0) goto L_0x008e;
    L_0x0041:
        r3 = r0.getCount();	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        if (r3 <= 0) goto L_0x008e;
    L_0x0047:
        r0.moveToFirst();	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
    L_0x004a:
        r3 = new com.alibaba.sdk.android.httpdns.a.g;	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r3.<init>();	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r4 = "id";
        r4 = r0.getColumnIndex(r4);	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r4 = r0.getInt(r4);	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r4 = (long) r4;	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r3.id = r4;	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r4 = "host_id";
        r4 = r0.getColumnIndex(r4);	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r4 = r0.getInt(r4);	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r4 = (long) r4;	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r3.g = r4;	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r4 = "ip";
        r4 = r0.getColumnIndex(r4);	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r4 = r0.getString(r4);	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r3.m = r4;	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r4 = "ttl";
        r4 = r0.getColumnIndex(r4);	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r4 = r0.getString(r4);	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r3.n = r4;	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r2.add(r3);	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        r3 = r0.moveToNext();	 Catch:{ Exception -> 0x00c2, all -> 0x00bc }
        if (r3 != 0) goto L_0x004a;
    L_0x008e:
        if (r0 == 0) goto L_0x0093;
    L_0x0090:
        r0.close();
    L_0x0093:
        if (r1 == 0) goto L_0x0098;
    L_0x0095:
        r1.close();
    L_0x0098:
        return r2;
    L_0x0099:
        r1 = move-exception;
        r1 = r0;
    L_0x009b:
        if (r0 == 0) goto L_0x00a0;
    L_0x009d:
        r0.close();
    L_0x00a0:
        if (r1 == 0) goto L_0x0098;
    L_0x00a2:
        r1.close();
        goto L_0x0098;
    L_0x00a6:
        r1 = move-exception;
        r2 = r0;
        r7 = r0;
        r0 = r1;
        r1 = r7;
    L_0x00ab:
        if (r1 == 0) goto L_0x00b0;
    L_0x00ad:
        r1.close();
    L_0x00b0:
        if (r2 == 0) goto L_0x00b5;
    L_0x00b2:
        r2.close();
    L_0x00b5:
        throw r0;
    L_0x00b6:
        r2 = move-exception;
        r7 = r2;
        r2 = r1;
        r1 = r0;
        r0 = r7;
        goto L_0x00ab;
    L_0x00bc:
        r2 = move-exception;
        r7 = r2;
        r2 = r1;
        r1 = r0;
        r0 = r7;
        goto L_0x00ab;
    L_0x00c2:
        r3 = move-exception;
        goto L_0x009b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.a.d.a(long):java.util.List<com.alibaba.sdk.android.httpdns.a.g>");
    }

    private List<g> a(e eVar) {
        return a(eVar.id);
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x001e A:{Splitter: B:1:0x0001, ExcHandler: java.lang.Exception (e java.lang.Exception)} */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002b  */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:8:0x001f, code:
            if (r0 != null) goto L_0x0021;
     */
    /* JADX WARNING: Missing block: B:9:0x0021, code:
            r0.close();
     */
    /* JADX WARNING: Missing block: B:10:0x0025, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:11:0x0026, code:
            r6 = r1;
            r1 = r0;
            r0 = r6;
     */
    /* JADX WARNING: Missing block: B:13:0x002b, code:
            r1.close();
     */
    /* JADX WARNING: Missing block: B:19:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:20:?, code:
            return;
     */
    /* renamed from: a */
    private void m9a(long r8) {
        /*
        r7 = this;
        r0 = 0;
        r0 = r7.getWritableDatabase();	 Catch:{ Exception -> 0x001e, all -> 0x0025 }
        r1 = "host";
        r2 = "id = ?";
        r3 = 1;
        r3 = new java.lang.String[r3];	 Catch:{ Exception -> 0x001e, all -> 0x002f }
        r4 = 0;
        r5 = java.lang.String.valueOf(r8);	 Catch:{ Exception -> 0x001e, all -> 0x002f }
        r3[r4] = r5;	 Catch:{ Exception -> 0x001e, all -> 0x002f }
        r0.delete(r1, r2, r3);	 Catch:{ Exception -> 0x001e, all -> 0x002f }
        if (r0 == 0) goto L_0x001d;
    L_0x001a:
        r0.close();
    L_0x001d:
        return;
    L_0x001e:
        r1 = move-exception;
        if (r0 == 0) goto L_0x001d;
    L_0x0021:
        r0.close();
        goto L_0x001d;
    L_0x0025:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x0029:
        if (r1 == 0) goto L_0x002e;
    L_0x002b:
        r1.close();
    L_0x002e:
        throw r0;
    L_0x002f:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.a.d.a(long):void");
    }

    private void a(g gVar) {
        b(gVar.id);
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x001e A:{Splitter: B:1:0x0001, ExcHandler: java.lang.Exception (e java.lang.Exception)} */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002b  */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:8:0x001f, code:
            if (r0 != null) goto L_0x0021;
     */
    /* JADX WARNING: Missing block: B:9:0x0021, code:
            r0.close();
     */
    /* JADX WARNING: Missing block: B:10:0x0025, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:11:0x0026, code:
            r6 = r1;
            r1 = r0;
            r0 = r6;
     */
    /* JADX WARNING: Missing block: B:13:0x002b, code:
            r1.close();
     */
    /* JADX WARNING: Missing block: B:19:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:20:?, code:
            return;
     */
    private void b(long r8) {
        /*
        r7 = this;
        r0 = 0;
        r0 = r7.getWritableDatabase();	 Catch:{ Exception -> 0x001e, all -> 0x0025 }
        r1 = "ip";
        r2 = "id = ?";
        r3 = 1;
        r3 = new java.lang.String[r3];	 Catch:{ Exception -> 0x001e, all -> 0x002f }
        r4 = 0;
        r5 = java.lang.String.valueOf(r8);	 Catch:{ Exception -> 0x001e, all -> 0x002f }
        r3[r4] = r5;	 Catch:{ Exception -> 0x001e, all -> 0x002f }
        r0.delete(r1, r2, r3);	 Catch:{ Exception -> 0x001e, all -> 0x002f }
        if (r0 == 0) goto L_0x001d;
    L_0x001a:
        r0.close();
    L_0x001d:
        return;
    L_0x001e:
        r1 = move-exception;
        if (r0 == 0) goto L_0x001d;
    L_0x0021:
        r0.close();
        goto L_0x001d;
    L_0x0025:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x0029:
        if (r1 == 0) goto L_0x002e;
    L_0x002b:
        r1.close();
    L_0x002e:
        throw r0;
    L_0x002f:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.a.d.b(long):void");
    }

    private void c(e eVar) {
        a(eVar.id);
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x007e A:{Catch:{ Exception -> 0x008a, all -> 0x0078 }} */
    /* JADX WARNING: Missing block: B:39:?, code:
            return r2;
     */
    /* renamed from: a */
    long m10a(com.alibaba.sdk.android.httpdns.a.e r10) {
        /*
        r9 = this;
        r0 = 0;
        r4 = a;
        monitor-enter(r4);
        r1 = r10.k;	 Catch:{ all -> 0x0085 }
        r2 = r10.j;	 Catch:{ all -> 0x0085 }
        r9.b(r1, r2);	 Catch:{ all -> 0x0085 }
        r2 = new android.content.ContentValues;	 Catch:{ all -> 0x0085 }
        r2.<init>();	 Catch:{ all -> 0x0085 }
        r1 = r9.getWritableDatabase();	 Catch:{ Exception -> 0x008a, all -> 0x0078 }
        r1.beginTransaction();	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r0 = "host";
        r3 = r10.j;	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r2.put(r0, r3);	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r0 = "sp";
        r3 = r10.k;	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r2.put(r0, r3);	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r0 = "time";
        r3 = r10.l;	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r3 = com.alibaba.sdk.android.httpdns.a.c.c(r3);	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r2.put(r0, r3);	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r0 = "host";
        r3 = 0;
        r2 = r1.insert(r0, r3, r2);	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r10.id = r2;	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r0 = r10.a;	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        if (r0 == 0) goto L_0x006a;
    L_0x0041:
        r0 = r10.a;	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r5 = r0.iterator();	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
    L_0x0047:
        r0 = r5.hasNext();	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        if (r0 == 0) goto L_0x006a;
    L_0x004d:
        r0 = r5.next();	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r0 = (com.alibaba.sdk.android.httpdns.a.g) r0;	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r0.g = r2;	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r6 = r9.a(r1, r0);	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        r0.id = r6;	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        goto L_0x0047;
    L_0x005c:
        r0 = move-exception;
        r0 = r1;
    L_0x005e:
        if (r0 == 0) goto L_0x0066;
    L_0x0060:
        r0.endTransaction();	 Catch:{ all -> 0x0085 }
        r0.close();	 Catch:{ all -> 0x0085 }
    L_0x0066:
        r0 = 0;
        monitor-exit(r4);	 Catch:{ all -> 0x0085 }
    L_0x0069:
        return r0;
    L_0x006a:
        r1.setTransactionSuccessful();	 Catch:{ Exception -> 0x005c, all -> 0x0088 }
        if (r1 == 0) goto L_0x0075;
    L_0x006f:
        r1.endTransaction();	 Catch:{ all -> 0x0085 }
        r1.close();	 Catch:{ all -> 0x0085 }
    L_0x0075:
        monitor-exit(r4);	 Catch:{ all -> 0x0085 }
        r0 = r2;
        goto L_0x0069;
    L_0x0078:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
    L_0x007c:
        if (r1 == 0) goto L_0x0084;
    L_0x007e:
        r1.endTransaction();	 Catch:{ all -> 0x0085 }
        r1.close();	 Catch:{ all -> 0x0085 }
    L_0x0084:
        throw r0;	 Catch:{ all -> 0x0085 }
    L_0x0085:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0085 }
        throw r0;
    L_0x0088:
        r0 = move-exception;
        goto L_0x007c;
    L_0x008a:
        r1 = move-exception;
        goto L_0x005e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.a.d.a(com.alibaba.sdk.android.httpdns.a.e):long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00c5 A:{SYNTHETIC, Splitter: B:35:0x00c5} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ca A:{Catch:{ Exception -> 0x00ad, all -> 0x00be }} */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00b2 A:{Catch:{ Exception -> 0x00ad, all -> 0x00be }} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b7 A:{Catch:{ Exception -> 0x00ad, all -> 0x00be }} */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00d3 A:{Splitter: B:9:0x0050, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00c5 A:{SYNTHETIC, Splitter: B:35:0x00c5} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ca A:{Catch:{ Exception -> 0x00ad, all -> 0x00be }} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:26:0x00b2, code:
            r0.close();
     */
    /* JADX WARNING: Missing block: B:28:0x00b7, code:
            r1.close();
     */
    /* JADX WARNING: Missing block: B:36:?, code:
            r1.close();
     */
    /* JADX WARNING: Missing block: B:38:0x00ca, code:
            r2.close();
     */
    /* JADX WARNING: Missing block: B:42:0x00d3, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:48:0x00e0, code:
            r0 = r1;
            r1 = r2;
            r2 = r3;
     */
    com.alibaba.sdk.android.httpdns.a.e a(java.lang.String r10, java.lang.String r11) {
        /*
        r9 = this;
        r0 = 0;
        r4 = a;
        monitor-enter(r4);
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00bb }
        r1.<init>();	 Catch:{ all -> 0x00bb }
        r2 = "SELECT * FROM ";
        r1.append(r2);	 Catch:{ all -> 0x00bb }
        r2 = "host";
        r1.append(r2);	 Catch:{ all -> 0x00bb }
        r2 = " WHERE ";
        r1.append(r2);	 Catch:{ all -> 0x00bb }
        r2 = "sp";
        r1.append(r2);	 Catch:{ all -> 0x00bb }
        r2 = " =? ";
        r1.append(r2);	 Catch:{ all -> 0x00bb }
        r2 = " AND ";
        r1.append(r2);	 Catch:{ all -> 0x00bb }
        r2 = "host";
        r1.append(r2);	 Catch:{ all -> 0x00bb }
        r2 = " =? ;";
        r1.append(r2);	 Catch:{ all -> 0x00bb }
        r2 = r9.getReadableDatabase();	 Catch:{ Exception -> 0x00ad, all -> 0x00be }
        r1 = r1.toString();	 Catch:{ Exception -> 0x00d5, all -> 0x00ce }
        r3 = 2;
        r3 = new java.lang.String[r3];	 Catch:{ Exception -> 0x00d5, all -> 0x00ce }
        r5 = 0;
        r3[r5] = r10;	 Catch:{ Exception -> 0x00d5, all -> 0x00ce }
        r5 = 1;
        r3[r5] = r11;	 Catch:{ Exception -> 0x00d5, all -> 0x00ce }
        r1 = r2.rawQuery(r1, r3);	 Catch:{ Exception -> 0x00d5, all -> 0x00ce }
        if (r1 == 0) goto L_0x00a0;
    L_0x0050:
        r3 = r1.getCount();	 Catch:{ Exception -> 0x00d9, all -> 0x00d3 }
        if (r3 <= 0) goto L_0x00a0;
    L_0x0056:
        r1.moveToFirst();	 Catch:{ Exception -> 0x00d9, all -> 0x00d3 }
        r3 = new com.alibaba.sdk.android.httpdns.a.e;	 Catch:{ Exception -> 0x00d9, all -> 0x00d3 }
        r3.<init>();	 Catch:{ Exception -> 0x00d9, all -> 0x00d3 }
        r0 = "id";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r0 = r1.getInt(r0);	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r6 = (long) r0;	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r3.id = r6;	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r0 = "host";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r0 = r1.getString(r0);	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r3.j = r0;	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r0 = "sp";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r0 = r1.getString(r0);	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r3.k = r0;	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r0 = "time";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r0 = r1.getString(r0);	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r0 = com.alibaba.sdk.android.httpdns.a.c.d(r0);	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r3.l = r0;	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r0 = r9.a(r3);	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r0 = (java.util.ArrayList) r0;	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r3.a = r0;	 Catch:{ Exception -> 0x00df, all -> 0x00d3 }
        r0 = r3;
    L_0x00a0:
        if (r1 == 0) goto L_0x00a5;
    L_0x00a2:
        r1.close();	 Catch:{ all -> 0x00bb }
    L_0x00a5:
        if (r2 == 0) goto L_0x00e4;
    L_0x00a7:
        r2.close();	 Catch:{ all -> 0x00bb }
        r2 = r0;
    L_0x00ab:
        monitor-exit(r4);	 Catch:{ all -> 0x00bb }
        return r2;
    L_0x00ad:
        r1 = move-exception;
        r1 = r0;
        r2 = r0;
    L_0x00b0:
        if (r0 == 0) goto L_0x00b5;
    L_0x00b2:
        r0.close();	 Catch:{ all -> 0x00bb }
    L_0x00b5:
        if (r1 == 0) goto L_0x00ab;
    L_0x00b7:
        r1.close();	 Catch:{ all -> 0x00bb }
        goto L_0x00ab;
    L_0x00bb:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x00bb }
        throw r0;
    L_0x00be:
        r1 = move-exception;
        r2 = r0;
        r8 = r0;
        r0 = r1;
        r1 = r8;
    L_0x00c3:
        if (r1 == 0) goto L_0x00c8;
    L_0x00c5:
        r1.close();	 Catch:{ all -> 0x00bb }
    L_0x00c8:
        if (r2 == 0) goto L_0x00cd;
    L_0x00ca:
        r2.close();	 Catch:{ all -> 0x00bb }
    L_0x00cd:
        throw r0;	 Catch:{ all -> 0x00bb }
    L_0x00ce:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
        goto L_0x00c3;
    L_0x00d3:
        r0 = move-exception;
        goto L_0x00c3;
    L_0x00d5:
        r1 = move-exception;
        r1 = r2;
        r2 = r0;
        goto L_0x00b0;
    L_0x00d9:
        r3 = move-exception;
        r8 = r1;
        r1 = r2;
        r2 = r0;
        r0 = r8;
        goto L_0x00b0;
    L_0x00df:
        r0 = move-exception;
        r0 = r1;
        r1 = r2;
        r2 = r3;
        goto L_0x00b0;
    L_0x00e4:
        r2 = r0;
        goto L_0x00ab;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.a.d.a(java.lang.String, java.lang.String):com.alibaba.sdk.android.httpdns.a.e");
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00aa A:{SYNTHETIC, Splitter: B:34:0x00aa} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00af A:{Catch:{ Exception -> 0x0093, all -> 0x00a3 }} */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00aa A:{SYNTHETIC, Splitter: B:34:0x00aa} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00af A:{Catch:{ Exception -> 0x0093, all -> 0x00a3 }} */
    java.util.List<com.alibaba.sdk.android.httpdns.a.e> b() {
        /*
        r9 = this;
        r0 = 0;
        r3 = a;
        monitor-enter(r3);
        r4 = new java.util.ArrayList;	 Catch:{ all -> 0x00a0 }
        r4.<init>();	 Catch:{ all -> 0x00a0 }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00a0 }
        r1.<init>();	 Catch:{ all -> 0x00a0 }
        r2 = "SELECT * FROM ";
        r1.append(r2);	 Catch:{ all -> 0x00a0 }
        r2 = "host";
        r1.append(r2);	 Catch:{ all -> 0x00a0 }
        r2 = " ; ";
        r1.append(r2);	 Catch:{ all -> 0x00a0 }
        r2 = r9.getReadableDatabase();	 Catch:{ Exception -> 0x0093, all -> 0x00a3 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x00ba, all -> 0x00b3 }
        r5 = 0;
        r1 = r2.rawQuery(r1, r5);	 Catch:{ Exception -> 0x00ba, all -> 0x00b3 }
        if (r1 == 0) goto L_0x0087;
    L_0x002f:
        r0 = r1.getCount();	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        if (r0 <= 0) goto L_0x0087;
    L_0x0035:
        r1.moveToFirst();	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
    L_0x0038:
        r5 = new com.alibaba.sdk.android.httpdns.a.e;	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r5.<init>();	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r0 = "id";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r0 = r1.getInt(r0);	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r6 = (long) r0;	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r5.id = r6;	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r0 = "host";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r0 = r1.getString(r0);	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r5.j = r0;	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r0 = "sp";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r0 = r1.getString(r0);	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r5.k = r0;	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r0 = "time";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r0 = r1.getString(r0);	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r0 = com.alibaba.sdk.android.httpdns.a.c.d(r0);	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r5.l = r0;	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r0 = r9.a(r5);	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r0 = (java.util.ArrayList) r0;	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r5.a = r0;	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r4.add(r5);	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        r0 = r1.moveToNext();	 Catch:{ Exception -> 0x00bd, all -> 0x00b8 }
        if (r0 != 0) goto L_0x0038;
    L_0x0087:
        if (r1 == 0) goto L_0x008c;
    L_0x0089:
        r1.close();	 Catch:{ all -> 0x00a0 }
    L_0x008c:
        if (r2 == 0) goto L_0x0091;
    L_0x008e:
        r2.close();	 Catch:{ all -> 0x00a0 }
    L_0x0091:
        monitor-exit(r3);	 Catch:{ all -> 0x00a0 }
        return r4;
    L_0x0093:
        r1 = move-exception;
        r1 = r0;
    L_0x0095:
        if (r0 == 0) goto L_0x009a;
    L_0x0097:
        r0.close();	 Catch:{ all -> 0x00a0 }
    L_0x009a:
        if (r1 == 0) goto L_0x0091;
    L_0x009c:
        r1.close();	 Catch:{ all -> 0x00a0 }
        goto L_0x0091;
    L_0x00a0:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x00a0 }
        throw r0;
    L_0x00a3:
        r1 = move-exception;
        r2 = r0;
        r8 = r0;
        r0 = r1;
        r1 = r8;
    L_0x00a8:
        if (r1 == 0) goto L_0x00ad;
    L_0x00aa:
        r1.close();	 Catch:{ all -> 0x00a0 }
    L_0x00ad:
        if (r2 == 0) goto L_0x00b2;
    L_0x00af:
        r2.close();	 Catch:{ all -> 0x00a0 }
    L_0x00b2:
        throw r0;	 Catch:{ all -> 0x00a0 }
    L_0x00b3:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
        goto L_0x00a8;
    L_0x00b8:
        r0 = move-exception;
        goto L_0x00a8;
    L_0x00ba:
        r1 = move-exception;
        r1 = r2;
        goto L_0x0095;
    L_0x00bd:
        r0 = move-exception;
        r0 = r1;
        r1 = r2;
        goto L_0x0095;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.a.d.b():java.util.List<com.alibaba.sdk.android.httpdns.a.e>");
    }

    void b(String str, String str2) {
        synchronized (a) {
            e a = a(str, str2);
            if (a != null) {
                c(a);
                if (a.a != null) {
                    Iterator it = a.a.iterator();
                    while (it.hasNext()) {
                        a((g) it.next());
                    }
                }
            }
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE host (id INTEGER PRIMARY KEY,host TEXT,sp TEXT,time TEXT);");
            sQLiteDatabase.execSQL("CREATE TABLE ip (id INTEGER PRIMARY KEY,host_id INTEGER,ip TEXT,ttl TEXT);");
        } catch (Exception e) {
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i != i2) {
            try {
                sQLiteDatabase.beginTransaction();
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS host;");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ip;");
                sQLiteDatabase.setTransactionSuccessful();
                sQLiteDatabase.endTransaction();
                onCreate(sQLiteDatabase);
            } catch (Exception e) {
            }
        }
    }
}
