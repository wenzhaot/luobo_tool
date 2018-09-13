package com.talkingdata.sdk;

/* compiled from: td */
public class ar {
    private static final String A = "TDtime_set_collect_net";
    private static final String B = "TDdeep_link_url";
    private static final String C = "TDtd_role_id";
    private static final String D = "TDpref.accountid.key";
    private static final String E = "TDpref.accountgame.key";
    private static final String F = "TDpref.missionid.key";
    private static final String G = "TDpref.game.session.startsystem.key";
    public static final String a = "TDpref.profile.key";
    public static final String b = "TDpref.session.key";
    public static final String c = "TDpref.session.backup.key";
    public static final String d = "TDpref.lastactivity.key";
    public static final String e = "TDpref.start.key";
    public static final String f = "TDpref.init.key";
    public static final String g = "TDpref.actstart.key";
    public static final String h = "TDpref.end.key";
    public static final String i = "TDpref.ip";
    public static final String j = "TD_CHANNEL_ID";
    public static final String k = "TDappcontext_push";
    public static final String l = "TDpref.tokensync.key";
    public static final String m = "TDpref.push.msgid.key";
    public static final String n = "TDpref.running.app.key";
    public static final String o = "activities";
    public static final String p = "handHolding";
    public static final String q = "pref_antiCheatingData";
    public static final String r = "TDpref_longtime";
    public static final String s = "TDpref_shorttime";
    public static final String t = "TDaes_key";
    public static final String u = "TDpref_game";
    public static final String v = "TD_push_pref_file";
    static final String w = "TDisAppQuiting";
    public static final String x = "TDpref.last.sdk.check";
    public static final String y = "TDadditionalVersionName";
    public static final String z = "TDadditionalVersionCode";

    public static String a() {
        String str = null;
        if (ab.g == null) {
            return str;
        }
        try {
            return bi.b(ab.g, r, t, null);
        } catch (Throwable th) {
            cs.postSDKError(th);
            return str;
        }
    }

    public static void setAESKey(String str) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, r, t, str);
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    public static String a(a aVar) {
        String str = null;
        if (ab.g == null || aVar == null) {
            return str;
        }
        try {
            return bi.b(ab.g, r + aVar.index(), b, null);
        } catch (Throwable th) {
            cs.postSDKError(th);
            return str;
        }
    }

    public static void a(String str, a aVar) {
        if (ab.g != null && aVar != null) {
            try {
                bi.a(ab.g, r + aVar.index(), b, str);
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    public static void b(String str, a aVar) {
        if (ab.g != null && aVar != null) {
            try {
                bi.a(ab.g, r + aVar.index(), c, str);
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    public static String b(a aVar) {
        String str = null;
        if (ab.g == null || aVar == null) {
            return str;
        }
        try {
            return bi.b(ab.g, r + aVar.index(), c, null);
        } catch (Throwable th) {
            cs.postSDKError(th);
            return str;
        }
    }

    public static void b() {
        if (ab.g != null) {
            try {
                ab.g.getSharedPreferences("TD_CHANNEL_ID", 0).edit().putBoolean("location_called", true).commit();
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    public static boolean c() {
        boolean z = false;
        if (ab.g == null) {
            return z;
        }
        try {
            return ab.g.getSharedPreferences("TD_CHANNEL_ID", 0).getBoolean("location_called", false);
        } catch (Throwable th) {
            cs.postSDKError(th);
            return z;
        }
    }

    public static void setLastActivity(String str) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, s, d, str);
            } catch (Throwable th) {
            }
        }
    }

    public static String d() {
        if (ab.g == null) {
            return "";
        }
        try {
            return bi.b(ab.g, s, d, "");
        } catch (Throwable th) {
            return "";
        }
    }

    public static long c(a aVar) {
        long j = 0;
        if (ab.g == null || aVar == null) {
            return j;
        }
        try {
            return bi.b(ab.g, r + aVar.index(), e, 0);
        } catch (Throwable th) {
            return j;
        }
    }

    public static void a(long j, a aVar) {
        if (ab.g != null && aVar != null) {
            try {
                bi.a(ab.g, r + aVar.index(), e, j);
            } catch (Throwable th) {
            }
        }
    }

    public static void b(long j, a aVar) {
        if (ab.g != null && aVar != null) {
            try {
                bi.a(ab.g, r + aVar.index(), f, j);
            } catch (Throwable th) {
            }
        }
    }

    public static long d(a aVar) {
        long j = 0;
        if (ab.g == null || aVar == null) {
            return j;
        }
        try {
            return bi.b(ab.g, r + aVar.index(), f, 0);
        } catch (Throwable th) {
            return j;
        }
    }

    public static void setInitTime(long j) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, r, f, j);
            } catch (Throwable th) {
            }
        }
    }

    public static long e() {
        long j = 0;
        if (ab.g == null) {
            return j;
        }
        try {
            return bi.b(ab.g, r, f, 0);
        } catch (Throwable th) {
            return j;
        }
    }

    public static void setActivityStartTime(long j) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, s, g, j);
            } catch (Throwable th) {
            }
        }
    }

    public static long f() {
        long j = 0;
        if (ab.g == null) {
            return j;
        }
        try {
            return bi.b(ab.g, s, g, 0);
        } catch (Throwable th) {
            return j;
        }
    }

    public static long e(a aVar) {
        long j = 0;
        if (ab.g == null || aVar == null) {
            return j;
        }
        try {
            return bi.b(ab.g, s + aVar.index(), h, 0);
        } catch (Throwable th) {
            return j;
        }
    }

    public static void c(long j, a aVar) {
        if (ab.g != null && aVar != null) {
            try {
                bi.a(ab.g, s + aVar.index(), h, j);
            } catch (Throwable th) {
            }
        }
    }

    public static void setPostProfile(boolean z) {
        try {
            bi.a(ab.g, r, a, z ? 1 : 0);
        } catch (Throwable th) {
        }
    }

    public static void setCollectRunningTime(long j) {
        try {
            bi.a(ab.g, r, n, j);
        } catch (Throwable th) {
        }
    }

    public static long g() {
        long j = 0;
        if (ab.g == null) {
            return j;
        }
        try {
            return bi.b(ab.g, r, n, 0);
        } catch (Throwable th) {
            return j;
        }
    }

    public static void setAdditionalVersionName(String str) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, r, y, str);
            } catch (Throwable th) {
            }
        }
    }

    public static String h() {
        String str = null;
        if (ab.g == null) {
            return str;
        }
        try {
            return bi.b(ab.g, r, y, null);
        } catch (Throwable th) {
            return str;
        }
    }

    public static void setAdditionalVersionCode(long j) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, r, z, j);
            } catch (Throwable th) {
            }
        }
    }

    public static long i() {
        long j = -1;
        if (ab.g == null) {
            return j;
        }
        try {
            return bi.b(ab.g, r, z, -1);
        } catch (Throwable th) {
            return j;
        }
    }

    public static int j() {
        try {
            if (i() != -1) {
                return Integer.parseInt(String.valueOf(i()));
            }
            return ap.a().b(ab.g);
        } catch (Throwable th) {
            return -1;
        }
    }

    public static String k() {
        try {
            if (h() != null) {
                return h();
            }
            return ap.a().c(ab.g);
        } catch (Throwable th) {
            return "unknown";
        }
    }

    public static void a(String str, String str2) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, s, str, str2);
            } catch (Throwable th) {
            }
        }
    }

    public static String a(String str) {
        String str2 = null;
        if (ab.g == null) {
            return str2;
        }
        try {
            return bi.b(ab.g, s, str, null);
        } catch (Throwable th) {
            return str2;
        }
    }

    public static void b(String str, String str2) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, s, str, str2);
            } catch (Throwable th) {
            }
        }
    }

    public static String b(String str) {
        String str2 = null;
        if (ab.g == null) {
            return str2;
        }
        try {
            return bi.b(ab.g, s, str, null);
        } catch (Throwable th) {
            return str2;
        }
    }

    public static void setLastRoleName(String str) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, s, C, str);
            } catch (Throwable th) {
            }
        }
    }

    public static String l() {
        String str = null;
        if (ab.g == null) {
            return str;
        }
        try {
            return bi.b(ab.g, s, C, null);
        } catch (Throwable th) {
            return str;
        }
    }

    public static long m() {
        long j = 0;
        if (ab.g == null) {
            return j;
        }
        try {
            return bi.b(ab.g, s, A, 0);
        } catch (Throwable th) {
            return j;
        }
    }

    public static void setCollectNetInfoTime(long j) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, s, A, j);
            } catch (Throwable th) {
            }
        }
    }

    public static String n() {
        String str = null;
        if (ab.g == null) {
            return str;
        }
        try {
            return bi.b(ab.g, s, B, null);
        } catch (Throwable th) {
            return str;
        }
    }

    public static void setDeepLink(String str) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, s, B, str);
            } catch (Throwable th) {
            }
        }
    }

    public static String o() {
        if (ab.g == null) {
            return "";
        }
        try {
            return bi.b(ab.g, u, D, "");
        } catch (Throwable th) {
            return "";
        }
    }

    public static void setAccountId(String str) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, u, D, str);
            } catch (Throwable th) {
            }
        }
    }

    public static String c(String str) {
        if (ab.g == null) {
            return "";
        }
        try {
            return bi.b(ab.g, u, str + E, "");
        } catch (Throwable th) {
            return "";
        }
    }

    public static void c(String str, String str2) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, u, str + E, str2);
            } catch (Throwable th) {
            }
        }
    }

    public static void setMissionId(String str) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, u, F, str);
            } catch (Throwable th) {
            }
        }
    }

    public static String p() {
        if (ab.g == null) {
            return "";
        }
        try {
            return bi.b(ab.g, u, F, "");
        } catch (Throwable th) {
            return "";
        }
    }

    public static void q() {
        if (ab.g != null) {
            try {
                bi.a(ab.g, u, G, System.currentTimeMillis());
            } catch (Throwable th) {
            }
        }
    }

    public static void setPushAppContext(String str) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, v, k, str);
            } catch (Throwable th) {
            }
        }
    }

    public static String r() {
        if (ab.g == null) {
            return "";
        }
        try {
            return bi.b(ab.g, v, k, "");
        } catch (Throwable th) {
            return "";
        }
    }

    public static void setPushSyncTokenLastTime(long j) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, v, l, j);
            } catch (Throwable th) {
            }
        }
    }

    public static long s() {
        long j = 0;
        if (ab.g == null) {
            return j;
        }
        try {
            return bi.b(ab.g, v, l, 0);
        } catch (Throwable th) {
            return j;
        }
    }

    public static void setPushLastMsgId(String str) {
        if (ab.g != null) {
            try {
                bi.a(ab.g, v, m, str);
            } catch (Throwable th) {
            }
        }
    }

    public static String t() {
        if (ab.g == null) {
            return "";
        }
        try {
            return bi.b(ab.g, v, m, "");
        } catch (Throwable th) {
            return "";
        }
    }
}
