package com.taobao.accs.c;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: Taobao */
public class a extends SQLiteOpenHelper {
    private static volatile a c = null;
    private static final Lock e = new ReentrantLock();
    public int a = 0;
    LinkedList<a> b = new LinkedList();
    private Context d;

    /* compiled from: Taobao */
    private class a {
        String a;
        Object[] b;

        private a(String str, Object[] objArr) {
            this.a = str;
            this.b = objArr;
        }
    }

    public SQLiteDatabase getWritableDatabase() {
        if (com.taobao.accs.utl.a.a(super.getWritableDatabase().getPath(), 102400)) {
            return super.getWritableDatabase();
        }
        return null;
    }

    public static a a(Context context) {
        if (c == null) {
            synchronized (a.class) {
                if (c == null) {
                    c = new a(context, Constants.DB_NAME, null, 3);
                }
            }
        }
        return c;
    }

    private a(Context context, String str, CursorFactory cursorFactory, int i) {
        super(context, str, cursorFactory, i);
        this.d = context;
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            if (e.tryLock()) {
                sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS traffic(_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, host TEXT,serviceid TEXT, bid TEXT, isbackground TEXT, size TEXT)");
            }
            e.unlock();
        } catch (Throwable th) {
            e.unlock();
        }
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < i2) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS service");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS network");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ping");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS msg");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ack");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS election");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS bindApp");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS bindUser");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS traffic");
            onCreate(sQLiteDatabase);
        }
    }

    public void a(String str, String str2, String str3, boolean z, long j, String str4) {
        if (a(str, str3, z, str4)) {
            a("UPDATE traffic SET size=? WHERE date=? AND host=? AND bid=? AND isbackground=?", new Object[]{Long.valueOf(j), str4, str, str3, String.valueOf(z)}, true);
            return;
        }
        a("INSERT INTO traffic VALUES(null,?,?,?,?,?,?)", new Object[]{str4, str, str2, str3, String.valueOf(z), Long.valueOf(j)}, true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0094 A:{Catch:{ Exception -> 0x007c, all -> 0x0091 }} */
    private synchronized boolean a(java.lang.String r11, java.lang.String r12, boolean r13, java.lang.String r14) {
        /*
        r10 = this;
        monitor-enter(r10);
        r9 = 0;
        r0 = r10.getWritableDatabase();	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        if (r0 != 0) goto L_0x0010;
    L_0x0008:
        r0 = 0;
        if (r9 == 0) goto L_0x000e;
    L_0x000b:
        r9.close();	 Catch:{ all -> 0x0072 }
    L_0x000e:
        monitor-exit(r10);
        return r0;
    L_0x0010:
        r1 = "traffic";
        r2 = 7;
        r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r3 = 0;
        r4 = "_id";
        r2[r3] = r4;	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r3 = 1;
        r4 = "date";
        r2[r3] = r4;	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r3 = 2;
        r4 = "host";
        r2[r3] = r4;	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r3 = 3;
        r4 = "serviceid";
        r2[r3] = r4;	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r3 = 4;
        r4 = "bid";
        r2[r3] = r4;	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r3 = 5;
        r4 = "isbackground";
        r2[r3] = r4;	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r3 = 6;
        r4 = "size";
        r2[r3] = r4;	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r3 = "date=? AND host=? AND bid=? AND isbackground=?";
        r4 = 4;
        r4 = new java.lang.String[r4];	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r5 = 0;
        r4[r5] = r14;	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r5 = 1;
        r4[r5] = r11;	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r5 = 2;
        r4[r5] = r12;	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r5 = 3;
        r6 = java.lang.String.valueOf(r13);	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r4[r5] = r6;	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 100;
        r8 = java.lang.String.valueOf(r8);	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        r1 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ Exception -> 0x007c, all -> 0x0091 }
        if (r1 == 0) goto L_0x0075;
    L_0x0065:
        r0 = r1.getCount();	 Catch:{ Exception -> 0x009b }
        if (r0 <= 0) goto L_0x0075;
    L_0x006b:
        r0 = 1;
        if (r1 == 0) goto L_0x000e;
    L_0x006e:
        r1.close();	 Catch:{ all -> 0x0072 }
        goto L_0x000e;
    L_0x0072:
        r0 = move-exception;
        monitor-exit(r10);
        throw r0;
    L_0x0075:
        if (r1 == 0) goto L_0x007a;
    L_0x0077:
        r1.close();	 Catch:{ all -> 0x0072 }
    L_0x007a:
        r0 = 0;
        goto L_0x000e;
    L_0x007c:
        r0 = move-exception;
        r1 = r9;
    L_0x007e:
        r2 = "DBHelper";
        r0 = r0.toString();	 Catch:{ all -> 0x0098 }
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x0098 }
        com.taobao.accs.utl.ALog.w(r2, r0, r3);	 Catch:{ all -> 0x0098 }
        if (r1 == 0) goto L_0x007a;
    L_0x008d:
        r1.close();	 Catch:{ all -> 0x0072 }
        goto L_0x007a;
    L_0x0091:
        r0 = move-exception;
    L_0x0092:
        if (r9 == 0) goto L_0x0097;
    L_0x0094:
        r9.close();	 Catch:{ all -> 0x0072 }
    L_0x0097:
        throw r0;	 Catch:{ all -> 0x0072 }
    L_0x0098:
        r0 = move-exception;
        r9 = r1;
        goto L_0x0092;
    L_0x009b:
        r0 = move-exception;
        goto L_0x007e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.c.a.a(java.lang.String, java.lang.String, boolean, java.lang.String):boolean");
    }

    public void a() {
        a("DELETE FROM traffic", null, true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x0153 A:{SYNTHETIC, Splitter: B:50:0x0153} */
    /* JADX WARNING: Missing block: B:58:?, code:
            return null;
     */
    /* JADX WARNING: Missing block: B:59:?, code:
            return null;
     */
    public java.util.List<com.taobao.accs.ut.monitor.TrafficsMonitor.a> a(boolean r15) {
        /*
        r14 = this;
        r9 = 0;
        monitor-enter(r14);
        r1 = 0;
        r10 = new java.util.ArrayList;	 Catch:{ all -> 0x014d }
        r10.<init>();	 Catch:{ all -> 0x014d }
        r0 = r14.getWritableDatabase();	 Catch:{ Exception -> 0x0139 }
        if (r0 != 0) goto L_0x0016;
    L_0x000e:
        if (r9 == 0) goto L_0x0013;
    L_0x0010:
        r1.close();	 Catch:{ all -> 0x014d }
    L_0x0013:
        monitor-exit(r14);	 Catch:{ all -> 0x014d }
        r0 = r9;
    L_0x0015:
        return r0;
    L_0x0016:
        if (r15 == 0) goto L_0x0070;
    L_0x0018:
        r1 = "traffic";
        r2 = 7;
        r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x0139 }
        r3 = 0;
        r4 = "_id";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = 1;
        r4 = "date";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = 2;
        r4 = "host";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = 3;
        r4 = "serviceid";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = 4;
        r4 = "bid";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = 5;
        r4 = "isbackground";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = 6;
        r4 = "size";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = "date=?";
        r4 = 1;
        r4 = new java.lang.String[r4];	 Catch:{ Exception -> 0x0139 }
        r5 = 0;
        r6 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0139 }
        r6 = com.taobao.accs.utl.UtilityImpl.formatDay(r6);	 Catch:{ Exception -> 0x0139 }
        r4[r5] = r6;	 Catch:{ Exception -> 0x0139 }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 100;
        r8 = java.lang.String.valueOf(r8);	 Catch:{ Exception -> 0x0139 }
        r8 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ Exception -> 0x0139 }
    L_0x0066:
        if (r8 != 0) goto L_0x00b0;
    L_0x0068:
        if (r8 == 0) goto L_0x006d;
    L_0x006a:
        r8.close();	 Catch:{ all -> 0x014d }
    L_0x006d:
        monitor-exit(r14);	 Catch:{ all -> 0x014d }
        r0 = r9;
        goto L_0x0015;
    L_0x0070:
        r1 = "traffic";
        r2 = 7;
        r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x0139 }
        r3 = 0;
        r4 = "_id";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = 1;
        r4 = "date";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = 2;
        r4 = "host";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = 3;
        r4 = "serviceid";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = 4;
        r4 = "bid";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = 5;
        r4 = "isbackground";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = 6;
        r4 = "size";
        r2[r3] = r4;	 Catch:{ Exception -> 0x0139 }
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 100;
        r8 = java.lang.String.valueOf(r8);	 Catch:{ Exception -> 0x0139 }
        r8 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ Exception -> 0x0139 }
        goto L_0x0066;
    L_0x00b0:
        r0 = r8.moveToFirst();	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        if (r0 == 0) goto L_0x0130;
    L_0x00b6:
        r0 = 1;
        r1 = r8.getString(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r0 = 2;
        r5 = r8.getString(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r0 = 3;
        r3 = r8.getString(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r0 = 4;
        r2 = r8.getString(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r0 = 5;
        r0 = r8.getString(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r0 = java.lang.Boolean.valueOf(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r4 = r0.booleanValue();	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r0 = 6;
        r6 = r8.getLong(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        if (r2 == 0) goto L_0x012a;
    L_0x00de:
        r12 = 0;
        r0 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r0 <= 0) goto L_0x012a;
    L_0x00e4:
        r0 = new com.taobao.accs.ut.monitor.TrafficsMonitor$a;	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r0.<init>(r1, r2, r3, r4, r5, r6);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r10.add(r0);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r0 = "DBHelper";
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r1.<init>();	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r4 = "resotre traffics from db bid:";
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r2 = " serviceid:";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r1 = r1.append(r3);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r2 = " host:";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r1 = r1.append(r5);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r2 = " size:";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r1 = r1.append(r6);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        com.taobao.accs.utl.ALog.d(r0, r1, r2);	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
    L_0x012a:
        r0 = r8.moveToNext();	 Catch:{ Exception -> 0x015a, all -> 0x0157 }
        if (r0 != 0) goto L_0x00b6;
    L_0x0130:
        if (r8 == 0) goto L_0x0135;
    L_0x0132:
        r8.close();	 Catch:{ all -> 0x014d }
    L_0x0135:
        monitor-exit(r14);	 Catch:{ all -> 0x014d }
        r0 = r10;
        goto L_0x0015;
    L_0x0139:
        r0 = move-exception;
    L_0x013a:
        r1 = "DBHelper";
        r0 = r0.toString();	 Catch:{ all -> 0x0150 }
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ all -> 0x0150 }
        com.taobao.accs.utl.ALog.w(r1, r0, r2);	 Catch:{ all -> 0x0150 }
        if (r9 == 0) goto L_0x0135;
    L_0x0149:
        r9.close();	 Catch:{ all -> 0x014d }
        goto L_0x0135;
    L_0x014d:
        r0 = move-exception;
        monitor-exit(r14);	 Catch:{ all -> 0x014d }
        throw r0;
    L_0x0150:
        r0 = move-exception;
    L_0x0151:
        if (r9 == 0) goto L_0x0156;
    L_0x0153:
        r9.close();	 Catch:{ all -> 0x014d }
    L_0x0156:
        throw r0;	 Catch:{ all -> 0x014d }
    L_0x0157:
        r0 = move-exception;
        r9 = r8;
        goto L_0x0151;
    L_0x015a:
        r0 = move-exception;
        r9 = r8;
        goto L_0x013a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.c.a.a(boolean):java.util.List<com.taobao.accs.ut.monitor.TrafficsMonitor$a>");
    }

    private synchronized void a(String str, Object[] objArr, boolean z) {
        SQLiteDatabase writableDatabase;
        try {
            this.b.add(new a(str, objArr));
            if (this.b.size() > 5 || z) {
                writableDatabase = getWritableDatabase();
                if (writableDatabase != null) {
                    while (this.b.size() > 0) {
                        a aVar = (a) this.b.removeFirst();
                        if (aVar.b != null) {
                            writableDatabase.execSQL(aVar.a, aVar.b);
                        } else {
                            writableDatabase.execSQL(aVar.a);
                        }
                        if (aVar.a.contains("INSERT")) {
                            this.a++;
                            if (this.a > 4000) {
                                ALog.d("DBHelper", "db is full!", new Object[0]);
                                onUpgrade(writableDatabase, 0, 1);
                                this.a = 0;
                                break;
                            }
                        }
                    }
                    writableDatabase.close();
                }
            }
        } catch (Exception e) {
            ALog.d("DBHelper", e.toString(), new Object[0]);
        } catch (Throwable th) {
            writableDatabase.close();
        }
        return;
    }
}
