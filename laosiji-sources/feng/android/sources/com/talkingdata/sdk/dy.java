package com.talkingdata.sdk;

import android.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: td */
public class dy extends dq {
    private cf a;
    private String c = UUID.randomUUID().toString();

    public dy(eb ebVar) {
        switch (ea.$SwitchMap$com$talkingdata$sdk$saf$datamodel$network$TDNetworkType[ebVar.ordinal()]) {
            case 1:
                a("type", (Object) eb.WIFI.a());
                a("available", (Object) Boolean.valueOf(bd.e(ab.g)));
                if (bd.g(ab.g)) {
                    a("connected", (Object) Boolean.valueOf(true));
                    JSONArray a = ck.a().a("WiFi", ed.a().a, ed.a().b);
                    if (a != null) {
                        a("current", (Object) bd.x(ab.g));
                        if (a.length() > 0) {
                            ed.a().a("WiFi", a);
                        }
                    } else {
                        a("current", this.b);
                    }
                    a = bd.y(ab.g);
                    cf a2 = a(a);
                    if (this.a == null) {
                        a("scannable", (Object) a);
                        this.a = a2;
                    } else if (new cg().a(this.a, a2) > 0.8d) {
                        a("scannable", null);
                    } else {
                        a("scannable", (Object) a);
                        this.a = a2;
                        this.c = UUID.randomUUID().toString();
                    }
                    a = ck.a().a("WiFi", ed.a().a, ed.a().b);
                    if (a != null) {
                        a("configured", (Object) bd.w(ab.g));
                        if (a.length() > 0) {
                            ed.a().a("WiFi", a);
                        }
                    } else {
                        a("configured", this.b);
                    }
                    a = ck.a().a("IP", ed.a().a, ed.a().b);
                    if (a != null) {
                        a("ip", (Object) bd.b(ab.g));
                        if (a.length() > 0) {
                            ed.a().a("IP", a);
                        }
                    } else {
                        a("ip", this.b);
                    }
                } else {
                    a("connected", (Object) Boolean.valueOf(false));
                }
                if (bd.a()) {
                    a("proxy", (Object) Proxy.getDefaultHost() + ":" + Proxy.getDefaultPort());
                }
                a("scannableFingerId", (Object) this.c);
                return;
            case 2:
                try {
                    a("type", (Object) eb.CELLULAR.a());
                    a("available", (Object) Boolean.valueOf(bd.f(ab.g)));
                    a("connected", (Object) Boolean.valueOf(bd.h(ab.g)));
                    if (bd.c(ab.g)) {
                        a("current", (Object) bd.a(ab.g, false));
                    }
                    if (bd.a()) {
                        a("proxy", (Object) Proxy.getDefaultHost() + ":" + Proxy.getDefaultPort());
                    }
                    a("scannable", (Object) bd.u(ab.g));
                    return;
                } catch (Throwable th) {
                    return;
                }
            case 3:
                try {
                    a("type", (Object) eb.BLUETOOTH.a());
                    return;
                } catch (Throwable th2) {
                    return;
                }
            default:
                return;
        }
    }

    private static cf a(JSONArray jSONArray) {
        int i = 0;
        if (jSONArray == null) {
            return null;
        }
        List arrayList = new ArrayList();
        while (true) {
            int i2 = i;
            if (i2 < jSONArray.length()) {
                try {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    arrayList.add(new cb(jSONObject.getString("name"), jSONObject.getString("id"), (byte) jSONObject.getInt("level"), (byte) 0, (byte) 0));
                } catch (Throwable th) {
                    aq.eForInternal(th);
                }
                i = i2 + 1;
            } else {
                cf cfVar = new cf();
                cfVar.setBsslist(arrayList);
                return cfVar;
            }
        }
    }
}
