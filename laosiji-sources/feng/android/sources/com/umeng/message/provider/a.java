package com.umeng.message.provider;

import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;
import com.umeng.message.common.UmengMessageDeviceConfig;

/* compiled from: MessageConstants */
public class a {
    public static String a = null;
    public static Uri b = null;
    public static Uri c = null;
    public static Uri d = null;
    public static Uri e = null;
    public static Uri f = null;
    public static Uri g = null;
    public static Uri h = null;
    public static Uri i = null;
    public static Uri j = null;
    public static Uri k = null;
    private static Context l;
    private static a m;
    private static String n;

    /* compiled from: MessageConstants */
    public static class a implements BaseColumns {
        public static final String a = "/MessageStores/";
        public static final String b = "/MsgTemps/";
        public static final String c = "/MsgAlias/";
        public static final String d = "/MsgAliasDeleteAll/";
        public static final String e = "/MsgLogStores/";
        public static final String f = "/MsgLogIdTypeStores/";
        public static final String g = "/MsgLogStoreForAgoos/";
        public static final String h = "/MsgLogIdTypeStoreForAgoos/";
        public static final String i = "/MsgConfigInfos/";
        public static final String j = "/InAppLogStores/";
        public static final String k = "vnd.android.cursor.dir/vnd.umeng.message";
        public static final String l = "vnd.android.cursor.item/vnd.umeng.message";
        private static final String m = "content://";

        private a() {
        }
    }

    private a() {
    }

    public static a a(Context context) {
        l = context;
        if (m == null) {
            m = new a();
            n = UmengMessageDeviceConfig.getPackageName(context);
            a = n + ".umeng.message";
            b = Uri.parse("content://" + a + a.a);
            c = Uri.parse("content://" + a + a.b);
            d = Uri.parse("content://" + a + a.c);
            e = Uri.parse("content://" + a + a.d);
            f = Uri.parse("content://" + a + a.e);
            g = Uri.parse("content://" + a + a.f);
            h = Uri.parse("content://" + a + a.g);
            i = Uri.parse("content://" + a + a.h);
            j = Uri.parse("content://" + a + a.i);
            k = Uri.parse("content://" + a + a.j);
        }
        return m;
    }
}
