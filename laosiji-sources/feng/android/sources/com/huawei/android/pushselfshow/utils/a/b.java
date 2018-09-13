package com.huawei.android.pushselfshow.utils.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.feng.car.utils.HttpConstant;
import com.huawei.android.pushagent.a.a.c;

public class b extends SQLiteOpenHelper {
    private static b a = null;
    private static b b = null;

    private b(Context context) {
        super(context, "push.db", null, 1);
        c.a("PushSelfShowLog", "DBHelper instance, version is 1");
    }

    private b(Context context, String str) {
        super(context, str, null, 1);
        c.a("PushSelfShowLog", "DBHelper instance, version is 1");
    }

    public static synchronized b a(Context context) {
        b bVar;
        synchronized (b.class) {
            if (a != null) {
                bVar = a;
            } else {
                a = new b(context);
                bVar = a;
            }
        }
        return bVar;
    }

    public static synchronized b a(Context context, String str) {
        b bVar;
        synchronized (b.class) {
            if (b != null) {
                bVar = b;
            } else {
                b = new b(context, str);
                bVar = b;
            }
        }
        return bVar;
    }

    private void a(SQLiteDatabase sQLiteDatabase) {
        c.a("PushSelfShowLog", "updateVersionFrom0To1");
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(HttpConstant.TOKEN, " ".getBytes("UTF-8"));
            sQLiteDatabase.update("pushmsg", contentValues, null, null);
        } catch (Throwable e) {
            c.c("PushSelfShowLog", e.toString(), e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x005e  */
    private boolean a(android.database.sqlite.SQLiteDatabase r11, java.lang.String r12) {
        /*
        r10 = this;
        r8 = 0;
        r9 = 0;
        if (r11 != 0) goto L_0x0005;
    L_0x0004:
        return r8;
    L_0x0005:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "(tbl_name='";
        r0 = r0.append(r1);
        r0 = r0.append(r12);
        r1 = "')";
        r0 = r0.append(r1);
        r3 = r0.toString();
        r1 = "sqlite_master";
        r2 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r0 = r11;
        r1 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x0048, all -> 0x005a }
        if (r1 == 0) goto L_0x0042;
    L_0x002f:
        r1.moveToFirst();	 Catch:{ Exception -> 0x0064 }
        r0 = r1.getCount();	 Catch:{ Exception -> 0x0064 }
        if (r0 <= 0) goto L_0x0040;
    L_0x0038:
        r0 = 1;
    L_0x0039:
        if (r1 == 0) goto L_0x003e;
    L_0x003b:
        r1.close();
    L_0x003e:
        r8 = r0;
        goto L_0x0004;
    L_0x0040:
        r0 = r8;
        goto L_0x0039;
    L_0x0042:
        if (r1 == 0) goto L_0x0004;
    L_0x0044:
        r1.close();
        goto L_0x0004;
    L_0x0048:
        r0 = move-exception;
        r1 = r9;
    L_0x004a:
        r2 = "PushSelfShowLog";
        r3 = r0.toString();	 Catch:{ all -> 0x0062 }
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r0);	 Catch:{ all -> 0x0062 }
        if (r1 == 0) goto L_0x0004;
    L_0x0056:
        r1.close();
        goto L_0x0004;
    L_0x005a:
        r0 = move-exception;
        r1 = r9;
    L_0x005c:
        if (r1 == 0) goto L_0x0061;
    L_0x005e:
        r1.close();
    L_0x0061:
        throw r0;
    L_0x0062:
        r0 = move-exception;
        goto L_0x005c;
    L_0x0064:
        r0 = move-exception;
        goto L_0x004a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.a.b.a(android.database.sqlite.SQLiteDatabase, java.lang.String):boolean");
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        c.a("PushSelfShowLog", "onCreate");
        if (a(sQLiteDatabase, "pushmsg")) {
            c.a("PushSelfShowLog", "old table is exist");
            onUpgrade(sQLiteDatabase, 0, 1);
            return;
        }
        try {
            sQLiteDatabase.execSQL("create table notify(url  TEXT  PRIMARY KEY , bmp  BLOB );");
            sQLiteDatabase.execSQL("create table pushmsg( _id INTEGER PRIMARY KEY AUTOINCREMENT, url  TEXT  , token  BLOB ,msg  BLOB );");
        } catch (Throwable e) {
            c.c("PushSelfShowLog", e.toString(), e);
        }
    }

    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        c.a("PushSelfShowLog", "onDowngrade,oldVersion:" + i + ",newVersion:" + i2);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        c.a("PushSelfShowLog", "onUpgrade,oldVersion:" + i + ",newVersion:" + i2);
        if (i == 0) {
            a(sQLiteDatabase);
        }
    }
}
