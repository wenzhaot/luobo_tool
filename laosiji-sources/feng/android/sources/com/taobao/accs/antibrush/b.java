package com.taobao.accs.antibrush;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.client.GlobalConfig;
import com.taobao.accs.utl.ALog;
import java.util.StringTokenizer;

/* compiled from: Taobao */
public class b {
    public static final String KEY_SEC = "sec";
    public static final String TAG = "CookieMgr";
    public static CookieManager a;
    private static volatile boolean b = false;

    public static synchronized void a(Context context) {
        synchronized (b.class) {
            try {
                if (!GlobalConfig.enableCookie) {
                    ALog.i(TAG, "disable cookie", new Object[0]);
                } else if (!b) {
                    if (VERSION.SDK_INT < 21) {
                        CookieSyncManager.createInstance(context);
                    }
                    a = CookieManager.getInstance();
                    a.setAcceptCookie(true);
                    if (VERSION.SDK_INT < 21) {
                        a.removeExpiredCookie();
                    }
                    b = true;
                }
            } catch (Throwable th) {
                ALog.e(TAG, "setup", th, new Object[0]);
            }
        }
        return;
    }

    private static boolean a() {
        if (!(b || GlobalClientInfo.a == null)) {
            a(GlobalClientInfo.a);
        }
        return b;
    }

    public static synchronized String a(String str) {
        String str2 = null;
        synchronized (b.class) {
            if (a()) {
                try {
                    str2 = b(a.getCookie(str));
                } catch (Throwable th) {
                    ALog.e(TAG, "get cookie failed. url=" + str, th, new Object[0]);
                }
            } else {
                ALog.e(TAG, "cookieMgr not setup", new Object[0]);
            }
        }
        return str2;
    }

    public static String b(String str) {
        String str2 = null;
        if (!TextUtils.isEmpty(str)) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, ";");
            do {
                try {
                    String nextToken = stringTokenizer.nextToken();
                    int indexOf = nextToken.indexOf(61);
                    if (indexOf != -1) {
                        String trim = nextToken.substring(0, indexOf).trim();
                        nextToken = nextToken.substring(indexOf + 1).trim();
                        if (KEY_SEC.equals(trim)) {
                            str2 = c(nextToken);
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid cookie name-value pair");
                    }
                } catch (Throwable th) {
                    ALog.e(TAG, "parse", th, new Object[0]);
                }
            } while (stringTokenizer.hasMoreTokens());
        }
        return str2;
    }

    private static String c(String str) {
        if (str != null && str.length() > 2 && str.charAt(0) == '\"' && str.charAt(str.length() - 1) == '\"') {
            return str.substring(1, str.length() - 1);
        }
        if (str == null || str.length() <= 2 || str.charAt(0) != '\'' || str.charAt(str.length() - 1) != '\'') {
            return str;
        }
        return str.substring(1, str.length() - 1);
    }
}
