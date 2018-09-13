package anetwork.channel.cookie;

import android.content.Context;
import android.os.Build.VERSION;
import android.webkit.CookieSyncManager;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import anetwork.channel.http.NetworkSdkSetting;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: Taobao */
public class CookieManager {
    public static final String TAG = "anet.CookieManager";
    private static boolean isCookieValid = true;
    private static volatile boolean isSetup = false;
    private static android.webkit.CookieManager webkitCookMgr;

    public static synchronized void setup(Context context) {
        synchronized (CookieManager.class) {
            if (!isSetup) {
                try {
                    if (VERSION.SDK_INT < 21) {
                        CookieSyncManager.createInstance(context);
                    }
                    webkitCookMgr = android.webkit.CookieManager.getInstance();
                    webkitCookMgr.setAcceptCookie(true);
                    if (VERSION.SDK_INT < 21) {
                        webkitCookMgr.removeExpiredCookie();
                    }
                } catch (Throwable th) {
                    isCookieValid = false;
                    ALog.e(TAG, "Cookie Manager setup failed!!!", null, th, new Object[0]);
                }
                isSetup = true;
            }
        }
        return;
    }

    private static boolean checkSetup() {
        if (!(isSetup || NetworkSdkSetting.getContext() == null)) {
            setup(NetworkSdkSetting.getContext());
        }
        return isSetup;
    }

    public static synchronized void setCookie(String str, String str2) {
        synchronized (CookieManager.class) {
            if (checkSetup() && isCookieValid) {
                try {
                    webkitCookMgr.setCookie(str, str2);
                    if (VERSION.SDK_INT < 21) {
                        CookieSyncManager.getInstance().sync();
                    } else {
                        webkitCookMgr.flush();
                    }
                } catch (Throwable th) {
                    ALog.e(TAG, "set cookie failed. url=" + str + " cookies=" + str2, null, th, new Object[0]);
                }
            }
        }
        return;
    }

    public static void setCookie(String str, Map<String, List<String>> map) {
        if (str != null && map != null) {
            try {
                for (Entry entry : map.entrySet()) {
                    String str2 = (String) entry.getKey();
                    if (str2 != null && (str2.equalsIgnoreCase(HttpConstant.SET_COOKIE) || str2.equalsIgnoreCase(HttpConstant.SET_COOKIE2))) {
                        for (String cookie : (List) entry.getValue()) {
                            setCookie(str, cookie);
                        }
                    }
                }
            } catch (Throwable e) {
                ALog.e(TAG, "set cookie failed", null, e, "url", str, "\nheaders", map);
            }
        }
    }

    public static synchronized String getCookie(String str) {
        String str2 = null;
        synchronized (CookieManager.class) {
            if (checkSetup() && isCookieValid) {
                try {
                    str2 = webkitCookMgr.getCookie(str);
                } catch (Throwable th) {
                    ALog.e(TAG, "get cookie failed. url=" + str, null, th, new Object[0]);
                }
            }
        }
        return str2;
    }
}
