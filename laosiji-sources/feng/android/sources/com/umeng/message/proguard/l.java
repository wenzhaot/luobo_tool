package com.umeng.message.proguard;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import com.stub.StubApp;
import com.umeng.message.MsgConstant;
import com.umeng.message.entity.UMessage;
import com.umeng.message.provider.a;

/* compiled from: MessageStore */
public class l {
    public static final String A = "exclusive";
    public static final String B = "error";
    public static final String C = "message";
    public static final String D = "time";
    public static l a = null;
    public static final String b = "MessageStore.db";
    public static final String c = "MessageStore";
    public static final String d = "MsgTemp";
    public static final String e = "MsgAlias";
    public static final int f = 3;
    public static final String g = "_id";
    public static final String h = "MsdId";
    public static final String i = "Json";
    public static final String j = "SdkVersion";
    public static final String k = "ArrivalTime";
    public static final String l = "ActionType";
    public static final String m = " PRIMARY KEY ";
    public static final String n = " AUTOINCREMENT ";
    public static final String o = "CREATE TABLE IF NOT EXISTS ";
    public static final String p = " Integer ";
    public static final String q = " Long ";
    public static final String r = " Varchar ";
    public static final String s = "(";
    public static final String t = ")";
    public static final String u = " , ";
    public static final String v = " And ";
    public static final String w = " desc ";
    public static final String x = " asc ";
    public static final String y = "type";
    public static final String z = "alias";
    private Context E;

    public static l a(Context context) {
        if (a == null) {
            a = new l(context);
        }
        return a;
    }

    private l(Context context) {
        this.E = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    boolean a(UMessage uMessage) {
        boolean z = false;
        if (uMessage != null) {
            synchronized (a) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(h, uMessage.msg_id);
                contentValues.put(i, uMessage.getRaw().toString());
                contentValues.put(j, MsgConstant.SDK_VERSION);
                contentValues.put(k, Long.valueOf(System.currentTimeMillis()));
                contentValues.put("ActionType", Integer.valueOf(0));
                ContentResolver contentResolver = this.E.getContentResolver();
                a.a(this.E);
                if (contentResolver.insert(a.b, contentValues) != null) {
                    z = true;
                }
            }
        }
        return z;
    }

    boolean a(String str) {
        return a(str, 1);
    }

    boolean b(String str) {
        return a(str, 2);
    }

    boolean a(String str, int i) {
        boolean z = true;
        synchronized (a) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("ActionType", Integer.valueOf(i));
            String[] strArr = new String[]{str};
            ContentResolver contentResolver = this.E.getContentResolver();
            a.a(this.E);
            if (contentResolver.update(a.b, contentValues, "MsdId=?", strArr) != 1) {
                z = false;
            }
        }
        return z;
    }
}
