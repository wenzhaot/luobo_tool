package com.huawei.android.pushselfshow.utils.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.huawei.android.pushagent.a.a.c;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;
import java.util.ArrayList;

public class a {
    public static ArrayList a(Context context, String str) {
        ArrayList arrayList = new ArrayList();
        try {
            String c = c(context, "hwpushApp.db");
            if (TextUtils.isEmpty(c)) {
                c.a("PushSelfShowLog", "database is null,can't queryAppinfo");
            } else {
                c.a("PushSelfShowLog", "dbName path is " + c);
                if (d.a().a(c, "openmarket")) {
                    String[] strArr = new String[]{str};
                    Cursor a = d.a().a(c, "select * from openmarket where package = ?;", strArr);
                    if (a == null) {
                        c.a("PushSelfShowLog", "cursor is null.");
                    } else {
                        try {
                            if (a.getCount() > 0) {
                                do {
                                    c = a.getString(a.getColumnIndex("msgid"));
                                    arrayList.add(c);
                                    c.a("TAG", "msgid and packageName is  " + c + MiPushClient.ACCEPT_TIME_SEPARATOR + str);
                                } while (a.moveToNext());
                            }
                            try {
                                a.close();
                            } catch (Throwable e) {
                                c.d("PushSelfShowLog", "cursor.close() ", e);
                            }
                        } catch (Throwable e2) {
                            c.c("TAG", "queryAppinfo error " + e2.toString(), e2);
                            try {
                                a.close();
                            } catch (Throwable e22) {
                                c.d("PushSelfShowLog", "cursor.close() ", e22);
                            }
                        } catch (Throwable e222) {
                            try {
                                a.close();
                            } catch (Throwable e3) {
                                c.d("PushSelfShowLog", "cursor.close() ", e3);
                            }
                            throw e222;
                        }
                    }
                }
            }
        } catch (Throwable e2222) {
            c.d("PushSelfShowLog", "queryAppinfo error", e2222);
        }
        return arrayList;
    }

    public static void a(Context context, String str, String str2) {
        try {
            if (!context.getDatabasePath("hwpushApp.db").exists()) {
                context.openOrCreateDatabase("hwpushApp.db", 0, null);
            }
            String c = c(context, "hwpushApp.db");
            if (TextUtils.isEmpty(c)) {
                c.d("PushSelfShowLog", "database is null,can't insert appinfo into db");
                return;
            }
            c.a("PushSelfShowLog", "dbName path is " + c);
            if (!d.a().a(c, "openmarket")) {
                d.a().a(context, c, "create table openmarket(    _id INTEGER PRIMARY KEY AUTOINCREMENT,     msgid  TEXT,    package TEXT);");
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("msgid", str);
            contentValues.put(com.umeng.message.common.a.c, str2);
            d.a().a(context, c, "openmarket", contentValues);
        } catch (Throwable e) {
            c.d("PushSelfShowLog", "insertAppinfo error", e);
        }
    }

    public static void b(Context context, String str) {
        try {
            String c = c(context, "hwpushApp.db");
            if (TextUtils.isEmpty(c)) {
                c.d("PushSelfShowLog", "database is null,can't delete appinfo");
                return;
            }
            c.a("PushSelfShowLog", "dbName path is " + c);
            if (d.a().a(c, "openmarket")) {
                d.a().a(c, "openmarket", "package = ?", new String[]{str});
            }
        } catch (Throwable e) {
            c.d("PushSelfShowLog", "Delete Appinfo error", e);
        }
    }

    private static String c(Context context, String str) {
        String str2 = "";
        if (context == null) {
            return str2;
        }
        File databasePath = context.getDatabasePath("hwpushApp.db");
        return databasePath.exists() ? databasePath.getAbsolutePath() : str2;
    }
}
