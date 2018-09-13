package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.strategy.k.a;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Taobao */
public class ConnProtocol implements Serializable {
    public static final ConnProtocol HTTP = valueOf("http", null, null, false);
    public static final ConnProtocol HTTPS = valueOf("https", null, null, false);
    private static Map<String, ConnProtocol> protocolMap = new HashMap();
    private static final long serialVersionUID = -3523201990674557001L;
    final int isHttp;
    public final boolean l7;
    public final String name;
    public final String protocol;
    public final String publicKey;
    public final String rtt;

    public static ConnProtocol valueOf(a aVar) {
        if (aVar == null) {
            return null;
        }
        return valueOf(aVar.b, aVar.g, aVar.j, aVar.i);
    }

    public static ConnProtocol valueOf(String str, String str2, String str3, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String buildName = buildName(str, str2, str3, z);
        synchronized (protocolMap) {
            ConnProtocol connProtocol;
            if (protocolMap.containsKey(buildName)) {
                connProtocol = (ConnProtocol) protocolMap.get(buildName);
                return connProtocol;
            }
            connProtocol = new ConnProtocol(buildName, str, str2, str3, z);
            protocolMap.put(buildName, connProtocol);
            return connProtocol;
        }
    }

    private ConnProtocol(String str, String str2, String str3, String str4, boolean z) {
        this.name = str;
        this.protocol = str2;
        this.rtt = str3;
        this.publicKey = str4;
        this.l7 = z;
        int i = ("http".equalsIgnoreCase(str2) || "https".equalsIgnoreCase(str2)) ? 1 : 0;
        this.isHttp = i;
    }

    private static String buildName(String str, String str2, String str3, boolean z) {
        if (TextUtils.isEmpty(str3)) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder(18);
        stringBuilder.append(str);
        if (TextUtils.isEmpty(str2)) {
            stringBuilder.append("_0rtt");
        } else {
            stringBuilder.append("_").append(str2);
        }
        stringBuilder.append("_");
        stringBuilder.append(str3);
        if (z) {
            stringBuilder.append("_l7");
        }
        return stringBuilder.toString();
    }

    public String toString() {
        return this.name;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ConnProtocol)) {
            return false;
        }
        return this.name.equals(((ConnProtocol) obj).name);
    }

    public int hashCode() {
        int hashCode = this.protocol.hashCode() + 527;
        if (this.rtt != null) {
            hashCode = (hashCode * 31) + this.rtt.hashCode();
        }
        if (this.publicKey != null) {
            hashCode = (hashCode * 31) + this.publicKey.hashCode();
        }
        return (this.l7 ? 1 : 0) + (hashCode * 31);
    }
}
