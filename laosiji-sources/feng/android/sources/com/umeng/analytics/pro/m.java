package com.umeng.analytics.pro;

import android.content.Context;
import com.feng.library.okhttp.utils.OkHttpUtils;
import com.umeng.analytics.b;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: DefconProcesser */
public class m {
    private static final int a = 0;
    private static final int b = 1;
    private static final int c = 2;
    private static final int d = 3;
    private final long e;

    /* compiled from: DefconProcesser */
    private static class a {
        public static final m a = new m();

        private a() {
        }
    }

    private m() {
        this.e = OkHttpUtils.DEFAULT_MILLISECONDS;
    }

    public static m a() {
        return a.a;
    }

    public int a(Context context) {
        return Integer.valueOf(UMEnvelopeBuild.imprintProperty(context, "defcon", String.valueOf(0))).intValue();
    }

    public void a(JSONObject jSONObject, Context context) {
        int a = a(context);
        if (a == 1) {
            jSONObject.remove("events");
            g.a(context).f();
        } else if (a == 2) {
            jSONObject.remove("events");
            jSONObject.remove(b.ac);
            jSONObject.remove(b.ah);
            try {
                jSONObject.put(b.ac, c());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            g.a(context).f();
        } else if (a == 3) {
            jSONObject.remove("events");
            jSONObject.remove(b.ac);
            jSONObject.remove(b.ah);
            g.a(context).f();
        }
    }

    public void b(JSONObject jSONObject, Context context) {
        int a = a(context);
        if (a == 1) {
            jSONObject.remove("error");
            jSONObject.remove(b.N);
            jSONObject.remove(b.O);
            g.a(context).a(false, true);
        } else if (a == 2) {
            jSONObject.remove(b.n);
            try {
                jSONObject.put(b.n, b());
            } catch (Exception e) {
            }
            jSONObject.remove("error");
            jSONObject.remove(b.N);
            jSONObject.remove(b.O);
            g.a(context).a(false, true);
        } else if (a == 3) {
            jSONObject.remove(b.n);
            jSONObject.remove("error");
            jSONObject.remove(b.N);
            jSONObject.remove(b.O);
            g.a(context).a(false, true);
        }
    }

    public JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            long currentTimeMillis = System.currentTimeMillis();
            jSONObject.put("id", p.a().d());
            jSONObject.put(b.p, currentTimeMillis);
            jSONObject.put(b.q, currentTimeMillis + OkHttpUtils.DEFAULT_MILLISECONDS);
            jSONObject.put("duration", OkHttpUtils.DEFAULT_MILLISECONDS);
        } catch (Throwable th) {
        }
        return jSONObject;
    }

    private JSONArray c() {
        JSONArray jSONArray = new JSONArray();
        try {
            long currentTimeMillis = System.currentTimeMillis();
            JSONObject jSONObject = new JSONObject();
            JSONObject j = b.a().j();
            if (j.length() > 0) {
                jSONObject.put(b.ab, j);
            }
            jSONObject.put(b.ad, p.a().d());
            jSONObject.put(b.ae, currentTimeMillis);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(b.af, p.a().d());
            jSONObject2.put(b.ag, currentTimeMillis + OkHttpUtils.DEFAULT_MILLISECONDS);
            if (j.length() > 0) {
                jSONObject2.put(b.ab, j);
            }
            jSONArray.put(jSONObject).put(jSONObject2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONArray;
    }
}
