package anet.channel.util;

import android.util.Base64;
import java.net.Proxy;

/* compiled from: Taobao */
public class d {
    public final Proxy a;
    public final String b;
    public final String c;

    private d(Proxy proxy, String str, String str2) {
        this.a = proxy;
        this.b = str;
        this.c = str2;
    }

    public String a() {
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append(this.b).append(":").append(this.c);
        String encodeToString = Base64.encodeToString(stringBuilder.toString().getBytes(), 0);
        StringBuilder stringBuilder2 = new StringBuilder(64);
        stringBuilder2.append("Basic ").append(encodeToString);
        return stringBuilder2.toString();
    }
}
