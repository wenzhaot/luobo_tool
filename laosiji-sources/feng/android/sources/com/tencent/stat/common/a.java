package com.tencent.stat.common;

import android.content.Context;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.stub.StubApp;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public class a {
    static c a;
    private static StatLogger d = k.b();
    private static JSONObject e = null;
    Integer b = null;
    String c = null;

    public a(Context context) {
        try {
            a(context);
            this.b = k.q(StubApp.getOrigApplicationContext(context.getApplicationContext()));
            this.c = k.p(context);
        } catch (Object th) {
            d.e(th);
        }
    }

    static synchronized c a(Context context) {
        c cVar;
        synchronized (a.class) {
            if (a == null) {
                a = new c(StubApp.getOrigApplicationContext(context.getApplicationContext()), null);
            }
            cVar = a;
        }
        return cVar;
    }

    public static void a(Context context, Map<String, String> map) {
        if (map != null) {
            Map hashMap = new HashMap(map);
            if (e == null) {
                e = new JSONObject();
            }
            for (Entry entry : hashMap.entrySet()) {
                e.put((String) entry.getKey(), entry.getValue());
            }
        }
    }

    public void a(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (a != null) {
                a.a(jSONObject2);
            }
            k.a(jSONObject2, AdvanceSetting.CLEAR_NOTIFICATION, this.c);
            if (this.b != null) {
                jSONObject2.put("tn", this.b);
            }
            jSONObject.put("ev", jSONObject2);
            if (e != null && e.length() > 0) {
                jSONObject.put("eva", e);
            }
        } catch (Object th) {
            d.e(th);
        }
    }
}
