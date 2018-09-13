package com.umeng.analytics.pro;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.stub.StubApp;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;

/* compiled from: SPHelper */
public final class u {
    private static u a = null;
    private static Context b = null;
    private static String c = null;
    private static final String d = "mobclick_agent_user_";

    /* compiled from: SPHelper */
    private static final class a {
        private static final u a = new u();

        private a() {
        }
    }

    public static synchronized u a(Context context) {
        u a;
        synchronized (u.class) {
            if (b == null && context != null) {
                b = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            if (b != null) {
                c = context.getPackageName();
            }
            a = a.a;
        }
        return a;
    }

    public void a(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            Editor edit = e().edit();
            edit.putString("au_p", str);
            edit.putString("au_u", str2);
            edit.commit();
        }
    }

    public String[] a() {
        SharedPreferences e = e();
        if (e == null) {
            return null;
        }
        CharSequence string = e.getString("au_p", null);
        CharSequence string2 = e.getString("au_u", null);
        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
            return null;
        }
        return new String[]{string, string2};
    }

    public void b() {
        SharedPreferences e = e();
        if (e != null) {
            e.edit().remove("au_p").remove("au_u").commit();
        }
    }

    public String c() {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(b);
        if (sharedPreferences != null) {
            return sharedPreferences.getString("st", null);
        }
        return null;
    }

    public void a(String str) {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(b);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString("st", str).commit();
        }
    }

    public void a(int i) {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(b);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putInt("vt", i).commit();
        }
    }

    public int d() {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(b);
        if (sharedPreferences != null) {
            return sharedPreferences.getInt("vt", 0);
        }
        return 0;
    }

    private SharedPreferences e() {
        if (b == null) {
            return null;
        }
        return b.getSharedPreferences(d + c, 0);
    }
}
