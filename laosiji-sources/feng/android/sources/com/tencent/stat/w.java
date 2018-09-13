package com.tencent.stat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tencent.stat.common.k;

class w extends SQLiteOpenHelper {
    public w(Context context) {
        super(context, k.v(context), null, 3);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x005f  */
    private void a(android.database.sqlite.SQLiteDatabase r10) {
        /*
        r9 = this;
        r8 = 0;
        r1 = "user";
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r0 = r10;
        r1 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x004c, all -> 0x005b }
        r0 = new android.content.ContentValues;	 Catch:{ Throwable -> 0x0065 }
        r0.<init>();	 Catch:{ Throwable -> 0x0065 }
        r2 = r1.moveToNext();	 Catch:{ Throwable -> 0x0065 }
        if (r2 == 0) goto L_0x0035;
    L_0x001a:
        r2 = 0;
        r8 = r1.getString(r2);	 Catch:{ Throwable -> 0x0065 }
        r2 = 1;
        r1.getInt(r2);	 Catch:{ Throwable -> 0x0065 }
        r2 = 2;
        r1.getString(r2);	 Catch:{ Throwable -> 0x0065 }
        r2 = 3;
        r1.getLong(r2);	 Catch:{ Throwable -> 0x0065 }
        r2 = com.tencent.stat.common.k.c(r8);	 Catch:{ Throwable -> 0x0065 }
        r3 = "uid";
        r0.put(r3, r2);	 Catch:{ Throwable -> 0x0065 }
    L_0x0035:
        if (r8 == 0) goto L_0x0046;
    L_0x0037:
        r2 = "user";
        r3 = "uid=?";
        r4 = 1;
        r4 = new java.lang.String[r4];	 Catch:{ Throwable -> 0x0065 }
        r5 = 0;
        r4[r5] = r8;	 Catch:{ Throwable -> 0x0065 }
        r10.update(r2, r0, r3, r4);	 Catch:{ Throwable -> 0x0065 }
    L_0x0046:
        if (r1 == 0) goto L_0x004b;
    L_0x0048:
        r1.close();
    L_0x004b:
        return;
    L_0x004c:
        r0 = move-exception;
        r1 = r8;
    L_0x004e:
        r2 = com.tencent.stat.n.e;	 Catch:{ all -> 0x0063 }
        r2.e(r0);	 Catch:{ all -> 0x0063 }
        if (r1 == 0) goto L_0x004b;
    L_0x0057:
        r1.close();
        goto L_0x004b;
    L_0x005b:
        r0 = move-exception;
        r1 = r8;
    L_0x005d:
        if (r1 == 0) goto L_0x0062;
    L_0x005f:
        r1.close();
    L_0x0062:
        throw r0;
    L_0x0063:
        r0 = move-exception;
        goto L_0x005d;
    L_0x0065:
        r0 = move-exception;
        goto L_0x004e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.w.a(android.database.sqlite.SQLiteDatabase):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0080  */
    private void b(android.database.sqlite.SQLiteDatabase r11) {
        /*
        r10 = this;
        r8 = 0;
        r1 = "events";
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r0 = r11;
        r7 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x0090, all -> 0x008a }
        r0 = new java.util.ArrayList;	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r0.<init>();	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
    L_0x0014:
        r1 = r7.moveToNext();	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        if (r1 == 0) goto L_0x0046;
    L_0x001a:
        r1 = 0;
        r2 = r7.getLong(r1);	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r1 = 1;
        r4 = r7.getString(r1);	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r1 = 2;
        r5 = r7.getInt(r1);	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r1 = 3;
        r6 = r7.getInt(r1);	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r1 = new com.tencent.stat.x;	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r1.<init>(r2, r4, r5, r6);	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r0.add(r1);	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        goto L_0x0014;
    L_0x0037:
        r0 = move-exception;
        r1 = r7;
    L_0x0039:
        r2 = com.tencent.stat.n.e;	 Catch:{ all -> 0x008d }
        r2.e(r0);	 Catch:{ all -> 0x008d }
        if (r1 == 0) goto L_0x0045;
    L_0x0042:
        r1.close();
    L_0x0045:
        return;
    L_0x0046:
        r1 = new android.content.ContentValues;	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r1.<init>();	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r2 = r0.iterator();	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
    L_0x004f:
        r0 = r2.hasNext();	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        if (r0 == 0) goto L_0x0084;
    L_0x0055:
        r0 = r2.next();	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r0 = (com.tencent.stat.x) r0;	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r3 = "content";
        r4 = r0.b;	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r4 = com.tencent.stat.common.k.c(r4);	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r1.put(r3, r4);	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r3 = "events";
        r4 = "event_id=?";
        r5 = 1;
        r5 = new java.lang.String[r5];	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r6 = 0;
        r8 = r0.a;	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r0 = java.lang.Long.toString(r8);	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r5[r6] = r0;	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        r11.update(r3, r1, r4, r5);	 Catch:{ Throwable -> 0x0037, all -> 0x007d }
        goto L_0x004f;
    L_0x007d:
        r0 = move-exception;
    L_0x007e:
        if (r7 == 0) goto L_0x0083;
    L_0x0080:
        r7.close();
    L_0x0083:
        throw r0;
    L_0x0084:
        if (r7 == 0) goto L_0x0045;
    L_0x0086:
        r7.close();
        goto L_0x0045;
    L_0x008a:
        r0 = move-exception;
        r7 = r8;
        goto L_0x007e;
    L_0x008d:
        r0 = move-exception;
        r7 = r1;
        goto L_0x007e;
    L_0x0090:
        r0 = move-exception;
        r1 = r8;
        goto L_0x0039;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.w.b(android.database.sqlite.SQLiteDatabase):void");
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table if not exists events(event_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, content TEXT, status INTEGER, send_count INTEGER, timestamp LONG)");
        sQLiteDatabase.execSQL("create table if not exists user(uid TEXT PRIMARY KEY, user_type INTEGER, app_ver TEXT, ts INTEGER)");
        sQLiteDatabase.execSQL("create table if not exists config(type INTEGER PRIMARY KEY NOT NULL, content TEXT, md5sum TEXT, version INTEGER)");
        sQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        n.e.debug("upgrade DB from oldVersion " + i + " to newVersion " + i2);
        if (i == 1) {
            sQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
            a(sQLiteDatabase);
            b(sQLiteDatabase);
        }
        if (i == 2) {
            a(sQLiteDatabase);
            b(sQLiteDatabase);
        }
    }
}
