package com.talkingdata.sdk;

import android.content.Context;
import android.os.Handler;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: td */
public class ab {
    public static String A = "Default";
    public static boolean B = false;
    public static String C = null;
    public static boolean D = false;
    public static final String E = "Android+TD+V4.0.21 gp";
    static long F = 0;
    public static int G = 2;
    public static AtomicInteger H = new AtomicInteger(0);
    public static final int I = 1800000;
    public static final AtomicBoolean J = new AtomicBoolean(false);
    public static final long K = 30000;
    public static final int L = 100;
    public static final String M = "TD_APP_ID";
    public static final String N = "TD_CHANNEL_ID";
    private static HashMap O = new HashMap();
    private static final String P = "+V";
    private static final int Q = 120;
    private static final int R = 30;
    private static final int S = 1000;
    public static final boolean a = false;
    public static boolean b = false;
    public static final AtomicBoolean c = new AtomicBoolean(false);
    public static final Map d = new TreeMap();
    public static boolean e = false;
    public static long f = 0;
    public static Context g = null;
    public static Handler h = null;
    public static final int i = 1;
    public static final String j = "Android+";
    public static FileChannel k = null;
    public static long l = 0;
    public static boolean m = false;
    public static int n = -1;
    public static boolean o = true;
    public static boolean p = true;
    public static boolean q = false;
    public static final String r = "TD";
    public static final String s = "TDLog";
    public static final String t = "667";
    public static final String u = "TD_app_pefercen_profile";
    public static final String v = "TD_appId_";
    public static final String w = "TD_channelId";
    public static final String x = "TD_sdk_last_send_time_wifi";
    public static final String y = "TD_sdk_last_send_time_mobile_data";
    public static final String z = "isDeveloper";

    /* compiled from: td */
    public class a {
        public static final int DST_FILE = 2;
        public static final int DST_SQLITE = 1;
    }

    /* compiled from: td */
    public static class b {
        public static final int API_MYNA_INIT = 90;
        public static final int API_MYNA_START = 91;
        public static final int API_MYNA_STOP = 92;
        public static final int API_ON_ERROR = 3;
        public static final int API_ON_EVENT = 2;
        public static final int API_ON_INIT = 1;
        public static final int API_ON_PAGE_END = 5;
        public static final int API_ON_PAGE_START = 4;
        public static final int API_TYPE_ACCOUNT = 9;
        public static final int API_TYPE_BRAND_GROWTH = 16;
        public static final int API_TYPE_CUST_LOCATION = 14;
        public static final int API_TYPE_GAME = 6;
        public static final int API_TYPE_GAME_SESSION_END = 15;
        public static final int API_TYPE_GAME_SESSION_START = 12;
        public static final int API_TYPE_IAP = 8;
        public static final int API_TYPE_SESSION_END = 11;
        public static final int API_TYPE_SESSION_PAUSE = 13;
        public static final int API_TYPE_SESSION_START = 10;
        public static final int API_TYPE_TRACKING = 7;
    }

    /* compiled from: td */
    public class c {
        public static final int SDT_JSON = 2;
        public static final int SDT_MP = 1;
        public static final int SDT_PB = 3;
        public static final int SDT_UNKNOWN = -1;
    }

    public static String a(Context context, a aVar) {
        if (context == null || aVar == null) {
            aq.dForInternal("Context or Service is null");
            return "";
        }
        String str = (String) O.get(aVar.name());
        return bo.b(str) ? bi.b(context, u, v + aVar.name(), "") : str;
    }

    public static void a(String str, a aVar) {
        if (aVar != null) {
            O.put(aVar.name(), str);
            bi.a(g, u, v + aVar.name(), str);
        }
    }

    public static String b(Context context, a aVar) {
        if (bo.b(A) || A.equals("Default")) {
            A = bi.b(context, u, w + aVar.name(), "Default");
        }
        return A;
    }

    public static void b(String str, a aVar) {
        bi.a(g, u, w + aVar.name(), str);
    }

    public static int[] a() {
        int[] iArr = new int[2];
        try {
            iArr[0] = 120000;
            iArr[1] = 30000;
        } catch (Throwable th) {
        }
        return iArr;
    }

    public static void a(String str, String str2, a aVar) {
        if (g != null) {
            h = new Handler(g.getMainLooper());
        }
        String str3 = "";
        f = System.currentTimeMillis();
        if (str == null || str.trim().isEmpty() || !str.contains("-") || aVar.name().equals("FINTECH")) {
            str3 = str;
        } else {
            str3 = null;
            try {
                str3 = str.split("-")[1];
            } catch (Throwable th) {
            }
        }
        if (!(str2 == null || str2.trim().isEmpty())) {
            A = str2;
        }
        a(str3, aVar);
        b(A, aVar);
        do.a().a(str3, aVar);
        do.a().b(A, aVar);
        a(dz.a);
    }

    public static void setDeveloperMode(boolean z) {
        try {
            bi.a(g, u, z, z ? 1 : 0);
        } catch (Throwable th) {
        }
    }

    public static boolean b() {
        try {
            if (bi.b(g, u, z, 0) != 0) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    private static void a(int i) {
        try {
            dr drVar = new dr();
            switch (i) {
                case 1:
                    drVar.setFrameWork("Cocos2d");
                    return;
                case 2:
                    drVar.setFrameWork("Unity");
                    return;
                case 3:
                    drVar.setFrameWork("AIR");
                    return;
                case 4:
                    drVar.setFrameWork("PhoneGap");
                    return;
                default:
                    drVar.setFrameWork("Native");
                    return;
            }
        } catch (Throwable th) {
        }
    }

    public static String c() {
        String str = "Native";
        try {
            return new dr().b();
        } catch (Throwable th) {
            return str;
        }
    }
}
