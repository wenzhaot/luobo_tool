package com.tencent.stat.common;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.tencent.stat.StatConfig;
import com.umeng.commonsdk.proguard.g;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONObject;

class c {
    String a;
    String b;
    DisplayMetrics c;
    int d;
    String e;
    String f;
    String g;
    String h;
    String i;
    String j;
    String k;
    int l;
    String m;
    Context n;
    private String o;
    private String p;
    private String q;
    private String r;

    private c(Context context) {
        this.b = StatConstants.VERSION;
        this.d = VERSION.SDK_INT;
        this.e = Build.MODEL;
        this.f = Build.MANUFACTURER;
        this.g = Locale.getDefault().getLanguage();
        this.l = 0;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = null;
        this.n = context;
        this.c = k.d(context);
        this.a = k.n(context);
        this.h = StatConfig.getInstallChannel(context);
        this.i = k.m(context);
        this.j = TimeZone.getDefault().getID();
        this.l = k.s(context);
        this.k = k.t(context);
        this.m = context.getPackageName();
        if (this.d >= 14) {
            this.o = k.A(context);
        }
        this.p = k.z(context).toString();
        this.q = k.x(context);
        this.r = k.e();
    }

    /* synthetic */ c(Context context, b bVar) {
        this(context);
    }

    void a(JSONObject jSONObject) {
        jSONObject.put("sr", this.c.widthPixels + "*" + this.c.heightPixels);
        k.a(jSONObject, "av", this.a);
        k.a(jSONObject, "ch", this.h);
        k.a(jSONObject, "mf", this.f);
        k.a(jSONObject, "sv", this.b);
        k.a(jSONObject, Parameters.OS_VERSION, Integer.toString(this.d));
        jSONObject.put("os", 1);
        k.a(jSONObject, "op", this.i);
        k.a(jSONObject, "lg", this.g);
        k.a(jSONObject, "md", this.e);
        k.a(jSONObject, Parameters.TIMEZONE, this.j);
        if (this.l != 0) {
            jSONObject.put("jb", this.l);
        }
        k.a(jSONObject, "sd", this.k);
        k.a(jSONObject, "apn", this.m);
        if (k.h(this.n)) {
            JSONObject jSONObject2 = new JSONObject();
            k.a(jSONObject2, NotificationStyle.BASE_STYLE, k.C(this.n));
            k.a(jSONObject2, "ss", k.D(this.n));
            if (jSONObject2.length() > 0) {
                k.a(jSONObject, "wf", jSONObject2.toString());
            }
        }
        k.a(jSONObject, "sen", this.o);
        k.a(jSONObject, g.v, this.p);
        k.a(jSONObject, "ram", this.q);
        k.a(jSONObject, "rom", this.r);
    }
}