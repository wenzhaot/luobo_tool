package com.huawei.android.pushselfshow.richpush.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.utils.a.b;

public class e implements a {
    private String a;

    public e() {
        this.a = null;
        this.a = null;
    }

    protected e(String str) {
        this.a = null;
        this.a = str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x009b  */
    private static void a(android.content.Context r9, android.database.sqlite.SQLiteDatabase r10, java.lang.String r11, android.content.ContentValues r12) {
        /*
        r8 = 0;
        if (r9 != 0) goto L_0x000d;
    L_0x0003:
        r0 = "PushSelfShowLog";
        r1 = "context is null";
        com.huawei.android.pushagent.a.a.c.d(r0, r1);
    L_0x000c:
        return;
    L_0x000d:
        if (r10 != 0) goto L_0x0019;
    L_0x000f:
        r0 = "PushSelfShowLog";
        r1 = "db is null";
        com.huawei.android.pushagent.a.a.c.d(r0, r1);
        goto L_0x000c;
    L_0x0019:
        r0 = android.text.TextUtils.isEmpty(r11);
        if (r0 == 0) goto L_0x0029;
    L_0x001f:
        r0 = "PushSelfShowLog";
        r1 = "table is null";
        com.huawei.android.pushagent.a.a.c.d(r0, r1);
        goto L_0x000c;
    L_0x0029:
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r0 = r10;
        r1 = r11;
        r1 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x00a4, all -> 0x0097 }
        if (r1 != 0) goto L_0x0049;
    L_0x0037:
        r0 = "PushSelfShowLog";
        r2 = "cursor is null";
        com.huawei.android.pushagent.a.a.c.d(r0, r2);	 Catch:{ Exception -> 0x0082 }
        if (r1 == 0) goto L_0x0045;
    L_0x0042:
        r1.close();
    L_0x0045:
        r10.close();
        goto L_0x000c;
    L_0x0049:
        r0 = r1.getCount();	 Catch:{ Exception -> 0x0082 }
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0082 }
        r3.<init>();	 Catch:{ Exception -> 0x0082 }
        r4 = "queryAndInsert, exist rowNumber:";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0082 }
        r3 = r3.append(r0);	 Catch:{ Exception -> 0x0082 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x0082 }
        com.huawei.android.pushagent.a.a.c.a(r2, r3);	 Catch:{ Exception -> 0x0082 }
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        if (r0 >= r2) goto L_0x0078;
    L_0x006b:
        r0 = 0;
        r10.insert(r11, r0, r12);	 Catch:{ Exception -> 0x0082 }
    L_0x006f:
        if (r1 == 0) goto L_0x0074;
    L_0x0071:
        r1.close();
    L_0x0074:
        r10.close();
        goto L_0x000c;
    L_0x0078:
        r0 = "PushSelfShowLog";
        r2 = "queryAndInsert failed";
        com.huawei.android.pushagent.a.a.c.d(r0, r2);	 Catch:{ Exception -> 0x0082 }
        goto L_0x006f;
    L_0x0082:
        r0 = move-exception;
    L_0x0083:
        r2 = "PushSelfShowLog";
        r3 = r0.toString();	 Catch:{ all -> 0x00a2 }
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r0);	 Catch:{ all -> 0x00a2 }
        if (r1 == 0) goto L_0x0092;
    L_0x008f:
        r1.close();
    L_0x0092:
        r10.close();
        goto L_0x000c;
    L_0x0097:
        r0 = move-exception;
        r1 = r8;
    L_0x0099:
        if (r1 == 0) goto L_0x009e;
    L_0x009b:
        r1.close();
    L_0x009e:
        r10.close();
        throw r0;
    L_0x00a2:
        r0 = move-exception;
        goto L_0x0099;
    L_0x00a4:
        r0 = move-exception;
        r1 = r8;
        goto L_0x0083;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.a.e.a(android.content.Context, android.database.sqlite.SQLiteDatabase, java.lang.String, android.content.ContentValues):void");
    }

    public Cursor a(Context context, Uri uri, String str, String[] strArr) {
        SQLiteDatabase readableDatabase = a(context).getReadableDatabase();
        if (readableDatabase != null) {
            try {
                return readableDatabase.rawQuery(str, strArr);
            } catch (Throwable e) {
                c.c("PushSelfShowLog", e.toString(), e);
            }
        }
        return null;
    }

    b a(Context context) {
        return this.a == null ? b.a(context) : b.a(context, this.a);
    }

    public void a(Context context, Uri uri, String str, ContentValues contentValues) {
        a(context, a(context).getWritableDatabase(), str, contentValues);
    }

    public void a(Context context, Uri uri, String str, String str2, String[] strArr) {
        SQLiteDatabase writableDatabase = a(context).getWritableDatabase();
        if (writableDatabase != null) {
            try {
                writableDatabase.delete(str, str2, strArr);
            } catch (Throwable e) {
                c.c("PushSelfShowLog", e.toString(), e);
            } finally {
                writableDatabase.close();
            }
        }
    }
}
