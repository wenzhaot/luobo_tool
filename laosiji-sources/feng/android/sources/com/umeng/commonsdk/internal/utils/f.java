package com.umeng.commonsdk.internal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.stub.StubApp;
import com.umeng.commonsdk.internal.utils.a.b;
import com.umeng.commonsdk.internal.utils.a.c;
import com.umeng.commonsdk.statistics.common.e;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: InfoPreference */
public class f {
    private static final String a = "info";
    private static final String b = "a_na";
    private static final String c = "a_st";
    private static final String d = "a_ad";
    private static final String e = "blueinfo";
    private static final String f = "a_dc";
    private static final String g = "bssid";
    private static final String h = "ssid";
    private static final String i = "a_fcy";
    private static final String j = "a_hssid";
    private static final String k = "a_ip";
    private static final String l = "a_ls";
    private static final String m = "a_mac";
    private static final String n = "a_nid";
    private static final String o = "rssi";
    private static final String p = "sta";
    private static final String q = "ts";
    private static final String r = "wifiinfo";
    private static final String s = "ua";

    public static JSONObject a(Context context) {
        JSONObject jSONObject;
        SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("info", 0);
        if (sharedPreferences != null) {
            try {
                String string = sharedPreferences.getString(e, null);
                if (string != null) {
                    jSONObject = new JSONObject(string);
                    return jSONObject;
                }
            } catch (Exception e) {
                if (e != null) {
                }
                return null;
            }
        }
        jSONObject = null;
        return jSONObject;
    }

    public static void a(Context context, Object obj) {
        if (obj != null) {
            try {
                b bVar = (b) obj;
                SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("info", 0);
                String str = null;
                if (sharedPreferences != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(b, bVar.c);
                    jSONObject.put(c, bVar.b);
                    jSONObject.put(d, bVar.a);
                    jSONObject.put("ts", System.currentTimeMillis());
                    if (jSONObject != null) {
                        str = jSONObject.toString();
                    }
                }
                if (str != null) {
                    sharedPreferences.edit().putString(e, str).commit();
                }
            } catch (Exception e) {
                if (e != null) {
                    e.e("saveBluetoothInfo:" + e.getMessage());
                }
            }
        }
    }

    public static JSONArray b(Context context) {
        try {
            JSONArray jSONArray;
            SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("info", 0);
            if (sharedPreferences != null) {
                String string = sharedPreferences.getString(r, null);
                if (string != null) {
                    jSONArray = new JSONArray(string);
                    return jSONArray;
                }
            }
            jSONArray = null;
            return jSONArray;
        } catch (Exception e) {
            if (e == null) {
                return null;
            }
            e.e(e.getMessage());
            return null;
        }
    }

    public static void c(Context context) {
        SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("info", 0);
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove(r).commit();
        }
    }

    public static void a(Context context, c cVar) {
        String str = null;
        try {
            SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("info", 0);
            if (sharedPreferences != null) {
                JSONArray jSONArray;
                String string = sharedPreferences.getString(r, null);
                if (string == null) {
                    jSONArray = new JSONArray();
                } else {
                    jSONArray = new JSONArray(string);
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(f, cVar.a);
                jSONObject.put(g, cVar.b);
                jSONObject.put(h, cVar.c);
                jSONObject.put(i, cVar.d);
                jSONObject.put(j, cVar.e);
                jSONObject.put(k, cVar.f);
                jSONObject.put(l, cVar.g);
                jSONObject.put(m, cVar.h);
                jSONObject.put(n, cVar.i);
                jSONObject.put(o, cVar.j);
                jSONObject.put(p, cVar.k);
                jSONObject.put("ts", cVar.l);
                jSONArray.put(jSONObject);
                if (jSONObject != null) {
                    str = jSONArray.toString();
                }
            }
            if (str != null) {
                sharedPreferences.edit().putString(r, str).commit();
            }
        } catch (Exception e) {
            if (e != null) {
                e.e(e.getMessage());
            }
        }
    }

    public static void a(Context context, String str) {
        SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("info", 0);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString("ua", str).commit();
        }
    }

    public static String d(Context context) {
        SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("info", 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getString("ua", null);
        }
        return null;
    }
}
