package com.talkingdata.sdk;

import android.os.Build;
import com.umeng.commonsdk.proguard.g;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONObject;

/* compiled from: td */
public class dw extends dq {
    public dw() {
        a("os", (Object) "android");
        a("osVersionName", (Object) aw.a());
        a("osVersionCode", (Object) String.valueOf(aw.i()));
        a(g.L, (Object) TimeZone.getDefault().getID());
        a("locale", (Object) e());
        a("timezoneV", (Object) g());
        a("language", (Object) aw.k());
        a("romVersion", (Object) Build.FINGERPRINT);
        a("basebandVersion", (Object) d());
        a("osBuild", (Object) aw.b());
    }

    public String b() {
        return ((JSONObject) a_()).optString("timezoneV");
    }

    public String c() {
        return ((JSONObject) a_()).optString("locale");
    }

    public static String d() {
        try {
            if (bo.a(14)) {
                return Build.getRadioVersion();
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    public static String e() {
        try {
            return Locale.getDefault().toString();
        } catch (Throwable th) {
            return null;
        }
    }

    private static String g() {
        try {
            return String.valueOf(f());
        } catch (Throwable th) {
            return null;
        }
    }

    public static float f() {
        try {
            return ((float) TimeZone.getDefault().getRawOffset()) / 3600000.0f;
        } catch (Throwable th) {
            return -1.0f;
        }
    }
}
