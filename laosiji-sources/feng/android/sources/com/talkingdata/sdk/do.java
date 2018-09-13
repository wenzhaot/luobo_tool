package com.talkingdata.sdk;

import com.taobao.accs.common.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: td */
public class do extends dq {
    private static HashMap a = new HashMap();
    private static HashMap c = new HashMap();
    private static volatile do f = null;
    private final String[] d = new String[]{"PUSH", "EAuth"};
    private final String[] e = new String[]{"APP", "TRACKING", "GAME", "BG", "FINTECH"};

    private do() {
        a("displayName", (Object) ap.a().h(ab.g));
        a("globalId", (Object) ap.a().a(ab.g));
        a("versionName", (Object) ar.k());
        a("versionCode", (Object) Integer.valueOf(ar.j()));
        a("installTime", (Object) Long.valueOf(ap.a().d(ab.g)));
        a("updateTime", (Object) Long.valueOf(ap.a().e(ab.g)));
    }

    public void a(Object obj, a aVar) {
        a.put(aVar.name(), obj);
    }

    public void b(Object obj, a aVar) {
        c.put(aVar.name(), obj);
    }

    private ArrayList b() {
        ArrayList arrayList = new ArrayList();
        try {
            for (Entry key : a.entrySet()) {
                arrayList.add(a.valueOf(key.getKey().toString()));
            }
        } catch (Throwable th) {
        }
        return arrayList;
    }

    public void setSubmitAppId(a aVar) {
        if (aVar != null) {
            try {
                Object obj = a.get(aVar.name());
                if (obj == null && a.size() > 0) {
                    obj = a(aVar);
                }
                a(Constants.KEY_APP_KEY, obj);
            } catch (Throwable th) {
            }
        }
    }

    private Object a(a aVar) {
        if (aVar == null) {
            return null;
        }
        try {
            Object jSONArray;
            if (Arrays.asList(this.d).contains(aVar.name())) {
                jSONArray = new JSONArray();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(Constants.KEY_APP_KEY, null);
                jSONObject.put("service", null);
                jSONArray.put(jSONObject);
                try {
                    if (bo.b(null)) {
                        return jSONArray;
                    }
                    a(jSONArray, aVar);
                    return jSONArray;
                } catch (Throwable th) {
                    return jSONArray;
                }
            } else if (!Arrays.asList(this.e).contains(aVar.name())) {
                return a.get(((a) b().get(0)).name());
            } else {
                jSONArray = ab.a(ab.g, aVar);
                if (bo.b((String) jSONArray)) {
                    return jSONArray;
                }
                a(jSONArray, aVar);
                return jSONArray;
            }
        } catch (Throwable th2) {
            return null;
        }
    }

    public void setSubmitChannelId(a aVar) {
        if (aVar != null) {
            try {
                Object obj = c.get(aVar.name());
                if (obj == null && c.size() > 0) {
                    obj = c.get(((a) b().get(0)).name());
                }
                a("channel", obj);
                return;
            } catch (Throwable th) {
                return;
            }
        }
        setAppChannel("Default");
    }

    public void setAppChannel(String str) {
        a("channel", (Object) str);
    }

    public void setUniqueId(String str) {
        a("uniqueId", (Object) str);
    }

    public static do a() {
        if (f == null) {
            synchronized (dh.class) {
                if (f == null) {
                    f = new do();
                }
            }
        }
        return f;
    }
}
