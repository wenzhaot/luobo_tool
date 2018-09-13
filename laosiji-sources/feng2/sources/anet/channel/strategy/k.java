package anet.channel.strategy;

import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.util.ALog;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: Taobao */
public class k {

    /* compiled from: Taobao */
    public static class a {
        public final int a;
        public final String b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;
        public final String g;
        public final boolean h;
        public final boolean i;
        public final String j;

        public a(JSONObject jSONObject) {
            boolean z;
            boolean z2 = true;
            this.a = jSONObject.optInt("port");
            this.b = jSONObject.optString("protocol");
            this.c = jSONObject.optInt("cto");
            this.d = jSONObject.optInt("rto");
            this.e = jSONObject.optInt("retry");
            this.f = jSONObject.optInt("heartbeat");
            this.g = jSONObject.optString("rtt", "");
            if (jSONObject.optInt("l7encript", 0) == 1) {
                z = true;
            } else {
                z = false;
            }
            this.i = z;
            this.j = jSONObject.optString("publickey");
            if (jSONObject.optInt("auth", 0) != 1) {
                z2 = false;
            }
            this.h = z2;
        }

        public String toString() {
            return "{port=" + this.a + "protocol=" + this.b + "publickey=" + this.j + "}";
        }
    }

    /* compiled from: Taobao */
    public static class b {
        public final String a;
        public final int b;
        public final String c;
        public final String d;
        public final String[] e;
        public final String[] f;
        public final a[] g;
        public final boolean h;
        public final String i;
        public final boolean j;
        public final int k;
        public final boolean l;

        public b(JSONObject jSONObject) {
            int length;
            int i;
            boolean z = true;
            int i2 = 0;
            this.a = jSONObject.optString("host");
            this.b = jSONObject.optInt("ttl");
            this.c = jSONObject.optString("safeAisles");
            this.d = jSONObject.optString("cname", null);
            this.k = jSONObject.optInt("isHot");
            this.h = jSONObject.optInt("clear") == 1;
            this.i = jSONObject.optString("etag");
            if (jSONObject.optInt("notModified") != 1) {
                z = false;
            }
            this.j = z;
            this.l = jSONObject.optBoolean("effectNow");
            JSONArray optJSONArray = jSONObject.optJSONArray("ips");
            if (optJSONArray != null) {
                length = optJSONArray.length();
                this.e = new String[length];
                for (i = 0; i < length; i++) {
                    this.e[i] = optJSONArray.optString(i);
                }
            } else {
                this.e = null;
            }
            optJSONArray = jSONObject.optJSONArray("sips");
            if (optJSONArray == null || optJSONArray.length() <= 0) {
                this.f = null;
            } else {
                length = optJSONArray.length();
                this.f = new String[length];
                for (i = 0; i < length; i++) {
                    this.f[i] = optJSONArray.optString(i);
                }
            }
            JSONArray optJSONArray2 = jSONObject.optJSONArray("aisles");
            if (optJSONArray2 != null) {
                int length2 = optJSONArray2.length();
                this.g = new a[length2];
                while (i2 < length2) {
                    this.g[i2] = new a(optJSONArray2.optJSONObject(i2));
                    i2++;
                }
                return;
            }
            this.g = null;
        }
    }

    /* compiled from: Taobao */
    public static class c {
        public final String a;
        public final String b;
        public final b[] c;
        public final String d;
        public final String e;
        public final int f;
        public final int g;
        public final int h;

        public c(JSONObject jSONObject) {
            this.a = jSONObject.optString("ip");
            this.b = jSONObject.optString("unit");
            this.d = jSONObject.optString("uid", null);
            this.e = jSONObject.optString("utdid", null);
            this.f = jSONObject.optInt(DispatchConstants.CONFIG_VERSION);
            this.g = jSONObject.optInt("fcl");
            this.h = jSONObject.optInt("fct");
            JSONArray optJSONArray = jSONObject.optJSONArray("dns");
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                this.c = new b[length];
                for (int i = 0; i < length; i++) {
                    this.c[i] = new b(optJSONArray.optJSONObject(i));
                }
                return;
            }
            this.c = null;
        }
    }

    public static c a(JSONObject jSONObject) {
        try {
            return new c(jSONObject);
        } catch (Throwable e) {
            ALog.e("StrategyResultParser", "Parse HttpDns response failed.", null, e, "JSON Content", jSONObject.toString());
            return null;
        }
    }
}
