package anet.channel.status;

import android.content.Context;
import android.net.NetworkInfo;
import android.util.Pair;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.a.c;
import anet.channel.util.ALog;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: Taobao */
public class NetworkStatusHelper {
    private static CopyOnWriteArraySet<INetworkStatusChangeListener> a = new CopyOnWriteArraySet();

    /* compiled from: Taobao */
    public interface INetworkStatusChangeListener {
        void onNetworkStatusChanged(NetworkStatus networkStatus);
    }

    /* compiled from: Taobao */
    public enum NetworkStatus {
        NONE,
        NO,
        G2,
        G3,
        G4,
        WIFI;

        public boolean isMobile() {
            return this == G2 || this == G3 || this == G4;
        }

        public boolean isWifi() {
            return this == WIFI;
        }

        public String getType() {
            if (this == G2) {
                return "2G";
            }
            if (this == G3) {
                return "3G";
            }
            if (this == G4) {
                return "4G";
            }
            return toString();
        }
    }

    public static synchronized void a(Context context) {
        synchronized (NetworkStatusHelper.class) {
            b.a = context;
            b.a();
        }
    }

    public static void a(INetworkStatusChangeListener iNetworkStatusChangeListener) {
        a.add(iNetworkStatusChangeListener);
    }

    public static void b(INetworkStatusChangeListener iNetworkStatusChangeListener) {
        a.remove(iNetworkStatusChangeListener);
    }

    static void a(NetworkStatus networkStatus) {
        c.a(new a(networkStatus));
    }

    public static NetworkStatus a() {
        return b.b;
    }

    public static String b() {
        return b.c;
    }

    public static String c() {
        return b.d;
    }

    public static String d() {
        return b.g;
    }

    public static String e() {
        return b.h;
    }

    public static String f() {
        return b.f;
    }

    public static boolean g() {
        if (b.b != NetworkStatus.NO) {
            return true;
        }
        NetworkInfo b = b.b();
        if (b == null || !b.isConnected()) {
            return false;
        }
        return true;
    }

    public static boolean h() {
        NetworkStatus networkStatus = b.b;
        String str = b.d;
        if ((networkStatus != NetworkStatus.WIFI || j() == null) && (!networkStatus.isMobile() || (!str.contains("wap") && GlobalAppRuntimeInfo.getProxySetting() == null))) {
            return false;
        }
        return true;
    }

    public static String i() {
        NetworkStatus networkStatus = b.b;
        if (networkStatus == NetworkStatus.WIFI && j() != null) {
            return "proxy";
        }
        if (networkStatus.isMobile() && b.d.contains("wap")) {
            return "wap";
        }
        if (!networkStatus.isMobile() || GlobalAppRuntimeInfo.getProxySetting() == null) {
            return "";
        }
        return "auth";
    }

    public static Pair<String, Integer> j() {
        if (b.b != NetworkStatus.WIFI) {
            return null;
        }
        return b.i;
    }

    public static void k() {
        try {
            NetworkStatus networkStatus = b.b;
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("\nNetwork detail*******************************\n");
            stringBuilder.append("Status: ").append(networkStatus.getType()).append(10);
            stringBuilder.append("Subtype: ").append(b.c).append(10);
            if (networkStatus != NetworkStatus.NO) {
                if (networkStatus.isMobile()) {
                    stringBuilder.append("Apn: ").append(b.d).append(10);
                    stringBuilder.append("Carrier: ").append(b.g).append(10);
                } else {
                    stringBuilder.append("BSSID: ").append(b.f).append(10);
                    stringBuilder.append("SSID: ").append(b.e).append(10);
                }
            }
            if (h()) {
                stringBuilder.append("Proxy: ").append(i()).append(10);
                Pair j = j();
                if (j != null) {
                    stringBuilder.append("ProxyHost: ").append((String) j.first).append(10);
                    stringBuilder.append("ProxyPort: ").append(j.second).append(10);
                }
            }
            stringBuilder.append("*********************************************");
            ALog.i("awcn.NetworkStatusHelper", stringBuilder.toString(), null, new Object[0]);
        } catch (Exception e) {
        }
    }
}
