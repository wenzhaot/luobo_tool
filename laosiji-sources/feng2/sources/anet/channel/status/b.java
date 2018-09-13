package anet.channel.status;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Pair;
import anet.channel.status.NetworkStatusHelper.NetworkStatus;
import anet.channel.util.ALog;
import java.lang.reflect.Method;
import java.util.Locale;

/* compiled from: Taobao */
class b {
    static Context a = null;
    static volatile NetworkStatus b = NetworkStatus.NONE;
    static volatile String c = EnvironmentCompat.MEDIA_UNKNOWN;
    static volatile String d = "";
    static volatile String e = "";
    static volatile String f = "";
    static volatile String g = EnvironmentCompat.MEDIA_UNKNOWN;
    static volatile String h = "";
    static volatile Pair<String, Integer> i = null;
    private static volatile boolean j = false;
    private static ConnectivityManager k = null;
    private static TelephonyManager l = null;
    private static WifiManager m = null;
    private static SubscriptionManager n = null;
    private static Method o;
    private static BroadcastReceiver p = new NetworkStatusMonitor$1();

    b() {
    }

    static void a() {
        if (!j && a != null) {
            synchronized (a) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                try {
                    a.registerReceiver(p, intentFilter);
                } catch (Exception e) {
                    ALog.e("awcn.NetworkStatusMonitor", "registerReceiver failed", null, new Object[0]);
                }
            }
            b(a);
            return;
        }
        return;
    }

    private static void b(Context context) {
        ALog.d("awcn.NetworkStatusMonitor", "[checkNetworkStatus]", null, new Object[0]);
        NetworkStatus networkStatus = b;
        String str = d;
        String str2 = e;
        if (context != null) {
            try {
                NetworkInfo b = b();
                if (b == null || !b.isConnected()) {
                    a(NetworkStatus.NO, "no network");
                    ALog.i("awcn.NetworkStatusMonitor", "checkNetworkStatus", null, "NO NETWORK");
                } else {
                    ALog.i("awcn.NetworkStatusMonitor", "checkNetworkStatus", null, "info.isConnected", Boolean.valueOf(b.isConnected()), "info.isAvailable", Boolean.valueOf(b.isAvailable()));
                    String replace;
                    if (b.getType() == 0) {
                        Object subtypeName = b.getSubtypeName();
                        replace = !TextUtils.isEmpty(subtypeName) ? subtypeName.replace(" ", "") : "";
                        a(a(b.getSubtype(), replace), replace);
                        d = a(b.getExtraInfo());
                        c();
                    } else if (b.getType() == 1) {
                        a(NetworkStatus.WIFI, "wifi");
                        WifiInfo d = d();
                        if (d != null) {
                            f = d.getBSSID();
                            e = d.getSSID();
                        }
                        replace = "wifi";
                        g = replace;
                        h = replace;
                        i = e();
                    } else {
                        a(NetworkStatus.NONE, EnvironmentCompat.MEDIA_UNKNOWN);
                    }
                }
                if (b != networkStatus || !d.equalsIgnoreCase(str) || !e.equalsIgnoreCase(str2)) {
                    if (ALog.isPrintLog(2)) {
                        NetworkStatusHelper.k();
                    }
                    NetworkStatusHelper.a(b);
                }
            } catch (Throwable e) {
                ALog.e("awcn.NetworkStatusMonitor", "checkNetworkStatus", null, e, new Object[0]);
            }
        }
    }

    private static void a(NetworkStatus networkStatus, String str) {
        b = networkStatus;
        c = str;
        d = "";
        e = "";
        f = "";
        i = null;
        g = "";
        h = "";
    }

    private static NetworkStatus a(int i, String str) {
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return NetworkStatus.G2;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 17:
                return NetworkStatus.G3;
            case 13:
            case 18:
            case 19:
                return NetworkStatus.G4;
            default:
                if (str.equalsIgnoreCase("TD-SCDMA") || str.equalsIgnoreCase("WCDMA") || str.equalsIgnoreCase("CDMA2000")) {
                    return NetworkStatus.G3;
                }
                return NetworkStatus.NONE;
        }
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        String toLowerCase = str.toLowerCase(Locale.US);
        if (toLowerCase.contains("cmwap")) {
            return "cmwap";
        }
        if (toLowerCase.contains("uniwap")) {
            return "uniwap";
        }
        if (toLowerCase.contains("3gwap")) {
            return "3gwap";
        }
        if (toLowerCase.contains("ctwap")) {
            return "ctwap";
        }
        if (toLowerCase.contains("cmnet")) {
            return "cmnet";
        }
        if (toLowerCase.contains("uninet")) {
            return "uninet";
        }
        if (toLowerCase.contains("3gnet")) {
            return "3gnet";
        }
        if (toLowerCase.contains("ctnet")) {
            return "ctnet";
        }
        return EnvironmentCompat.MEDIA_UNKNOWN;
    }

    private static void c() {
        try {
            if (l == null) {
                l = (TelephonyManager) a.getSystemService("phone");
            }
            h = l.getSimOperator();
            if (VERSION.SDK_INT >= 22) {
                if (n == null) {
                    n = SubscriptionManager.from(a);
                    o = n.getClass().getDeclaredMethod("getDefaultDataSubscriptionInfo", new Class[0]);
                }
                if (o != null) {
                    g = ((SubscriptionInfo) o.invoke(n, new Object[0])).getCarrierName().toString();
                }
            }
        } catch (Exception e) {
        }
    }

    static NetworkInfo b() {
        try {
            if (k == null) {
                k = (ConnectivityManager) a.getSystemService("connectivity");
            }
            return k.getActiveNetworkInfo();
        } catch (Throwable th) {
            ALog.e("awcn.NetworkStatusMonitor", "getNetworkInfo", null, th, new Object[0]);
            return null;
        }
    }

    private static WifiInfo d() {
        try {
            if (m == null) {
                m = (WifiManager) a.getSystemService("wifi");
            }
            return m.getConnectionInfo();
        } catch (Throwable th) {
            ALog.e("awcn.NetworkStatusMonitor", "getWifiInfo", null, th, new Object[0]);
            return null;
        }
    }

    private static Pair<String, Integer> e() {
        try {
            CharSequence property = System.getProperty("http.proxyHost");
            if (!TextUtils.isEmpty(property)) {
                return Pair.create(property, Integer.valueOf(Integer.parseInt(System.getProperty("http.proxyPort"))));
            }
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
