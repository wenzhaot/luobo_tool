package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.strategy.k.c;
import anet.channel.strategy.utils.SerialLruCache;
import anet.channel.util.ALog;
import java.io.Serializable;
import java.util.Map;

/* compiled from: Taobao */
class UnitMap implements Serializable {
    private Map<String, String> a;

    UnitMap() {
        a();
    }

    void a() {
        if (this.a == null) {
            this.a = new SerialLruCache(6);
        }
    }

    void a(c cVar) {
        int i = 0;
        Object obj = cVar.b;
        if (!(TextUtils.isEmpty(obj) || obj.equalsIgnoreCase("center"))) {
            i = 1;
        }
        String b = b(cVar.d, cVar.e);
        if (i == 0) {
            synchronized (this.a) {
                this.a.remove(b);
            }
        } else if (b != null) {
            synchronized (this.a) {
                this.a.put(b, obj);
            }
        }
        if (ALog.isPrintLog(1)) {
            synchronized (this.a) {
                ALog.d("awcn.UnitMap", "UnitMap : " + toString(), null, new Object[0]);
            }
        }
    }

    String a(String str, String str2) {
        String b = b(str, str2);
        if (b == null) {
            return null;
        }
        synchronized (this.a) {
            b = (String) this.a.get(b);
        }
        return b;
    }

    void a(String str, String str2, String str3) {
        String b = b(str, str2);
        if (b != null) {
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.UnitMap", "set unit prefix", null, "key", b, "unitPrefix", str3);
            }
            if (TextUtils.isEmpty(str3) || str3.equalsIgnoreCase("center")) {
                synchronized (this.a) {
                    this.a.remove(b);
                }
                return;
            }
            synchronized (this.a) {
                this.a.put(b, str3);
            }
        }
    }

    private String b(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        return str2;
    }

    public String toString() {
        String str;
        synchronized (this.a) {
            str = "UnitMap: " + this.a.toString();
        }
        return str;
    }
}
