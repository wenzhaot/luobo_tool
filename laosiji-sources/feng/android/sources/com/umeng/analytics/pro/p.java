package com.umeng.analytics.pro;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.stub.StubApp;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.b;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import org.json.JSONObject;

/* compiled from: SessionTracker */
public class p {
    private static final String a = "session_start_time";
    private static final String b = "session_end_time";
    private static final String c = "session_id";
    private static String f = null;
    private static Context g = null;
    private final String d;
    private final String e;

    /* compiled from: SessionTracker */
    private static class a {
        private static final p a = new p();

        private a() {
        }
    }

    private p() {
        this.d = "a_start_time";
        this.e = "a_end_time";
    }

    public static p a() {
        return a.a;
    }

    public boolean a(Context context) {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
        String string = sharedPreferences.getString(c, null);
        if (string == null) {
            return false;
        }
        long j = sharedPreferences.getLong(a, 0);
        long j2 = sharedPreferences.getLong(b, 0);
        if (j2 == 0 || Math.abs(j2 - j) > 86400000) {
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("__ii", string);
            jSONObject.put("__e", j);
            jSONObject.put(com.umeng.analytics.pro.c.e.a.g, j2);
            double[] location = AnalyticsConfig.getLocation();
            if (location != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("lat", location[0]);
                jSONObject2.put("lng", location[1]);
                jSONObject2.put("ts", System.currentTimeMillis());
                jSONObject.put(com.umeng.analytics.pro.c.e.a.e, jSONObject2);
            }
            Class cls = Class.forName("android.net.TrafficStats");
            Method method = cls.getMethod("getUidRxBytes", new Class[]{Integer.TYPE});
            Method method2 = cls.getMethod("getUidTxBytes", new Class[]{Integer.TYPE});
            if (context.getApplicationInfo().uid == -1) {
                return false;
            }
            long longValue = ((Long) method.invoke(null, new Object[]{Integer.valueOf(context.getApplicationInfo().uid)})).longValue();
            j = ((Long) method2.invoke(null, new Object[]{Integer.valueOf(r6)})).longValue();
            if (longValue > 0 && j > 0) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put(b.C, longValue);
                jSONObject3.put(b.B, j);
                jSONObject.put(com.umeng.analytics.pro.c.e.a.d, jSONObject3);
            }
            g.a(context).a(string, jSONObject, com.umeng.analytics.pro.g.a.NEWSESSION);
            q.a(g);
            i.a(g);
            a(sharedPreferences);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private void a(SharedPreferences sharedPreferences) {
        Editor edit = sharedPreferences.edit();
        edit.remove(a);
        edit.remove(b);
        edit.remove("a_start_time");
        edit.remove("a_end_time");
        edit.commit();
    }

    public String b(Context context) {
        String deviceId = DeviceConfig.getDeviceId(context);
        String appkey = UMUtils.getAppkey(context);
        long currentTimeMillis = System.currentTimeMillis();
        if (appkey == null) {
            throw new RuntimeException("Appkey is null or empty, Please check!");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(currentTimeMillis).append(appkey).append(deviceId);
        f = UMUtils.MD5(stringBuilder.toString());
        return f;
    }

    public void a(Context context, Object obj) {
        try {
            if (g == null && context != null) {
                g = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            long longValue = ((Long) obj).longValue();
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(g);
            if (sharedPreferences != null) {
                Editor edit = sharedPreferences.edit();
                String string = sharedPreferences.getString("versionname", "");
                String appVersionName = UMUtils.getAppVersionName(g);
                if (!TextUtils.isEmpty(string) && !string.equals(appVersionName)) {
                    int i = sharedPreferences.getInt("versioncode", 0);
                    String string2 = sharedPreferences.getString("pre_date", "");
                    String string3 = sharedPreferences.getString("pre_version", "");
                    String string4 = sharedPreferences.getString("versionname", "");
                    edit.putString("vers_date", string2);
                    edit.putString("vers_pre_version", string3);
                    edit.putString("cur_version", string4);
                    edit.putString("dp_vers_date", string2);
                    edit.putString("dp_vers_pre_version", string3);
                    edit.putString("dp_cur_version", string4);
                    edit.putInt("vers_code", i);
                    edit.putString("vers_name", string);
                    edit.putInt("dp_vers_code", i);
                    edit.putString("dp_vers_name", string);
                    edit.putLong("a_end_time", 0);
                    edit.commit();
                    if (c(context) == null) {
                        f = a(context, sharedPreferences, longValue);
                    }
                    a(g, longValue);
                    b(g, longValue);
                } else if (a(sharedPreferences, longValue)) {
                    f = a(context, sharedPreferences, longValue);
                    MLog.i("Start new session: " + f);
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "Start new session: " + f);
                } else {
                    f = sharedPreferences.getString(c, null);
                    edit.putLong("a_start_time", longValue);
                    edit.putLong("a_end_time", 0);
                    edit.commit();
                    MLog.i("Extend current session: " + f);
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "Extend current session: " + f);
                    d(context);
                    j.a(g).a(false);
                    j.a(g).d();
                }
            }
        } catch (Throwable th) {
        }
    }

    public void b(Context context, Object obj) {
        try {
            if (g == null && context != null) {
                g = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            long longValue = ((Long) obj).longValue();
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            if (sharedPreferences != null) {
                if (sharedPreferences.getLong("a_start_time", 0) == 0 && AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
                    MLog.e("onPause called before onResume");
                    return;
                }
                Editor edit = sharedPreferences.edit();
                edit.putLong("a_end_time", longValue);
                edit.putLong(b, longValue);
                edit.commit();
            }
        } catch (Throwable th) {
        }
    }

    public boolean b() {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(g);
        if (sharedPreferences == null) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        long j = sharedPreferences.getLong("a_start_time", 0);
        long j2 = sharedPreferences.getLong("a_end_time", 0);
        if ((j == 0 || currentTimeMillis - j >= AnalyticsConfig.kContinueSessionMillis) && currentTimeMillis - j2 > AnalyticsConfig.kContinueSessionMillis) {
            return true;
        }
        return false;
    }

    private boolean a(SharedPreferences sharedPreferences, long j) {
        long j2 = sharedPreferences.getLong("a_start_time", 0);
        long j3 = sharedPreferences.getLong("a_end_time", 0);
        if (j2 != 0 && j - j2 < AnalyticsConfig.kContinueSessionMillis) {
            MLog.i("onResume called before onPause");
            return false;
        } else if (j - j3 <= AnalyticsConfig.kContinueSessionMillis) {
            return false;
        } else {
            try {
                String string = sharedPreferences.getString(c, "-1");
                long j4 = sharedPreferences.getLong(b, 0);
                if (!"-1".equals(string)) {
                    if (j4 == 0) {
                        j4 = System.currentTimeMillis();
                    }
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(com.umeng.analytics.pro.c.e.a.g, j4);
                    g.a(g).a(string, jSONObject, com.umeng.analytics.pro.g.a.END);
                    if (AnalyticsConfig.FLAG_DPLUS) {
                        jSONObject = new JSONObject();
                        JSONObject j5 = b.a().j();
                        if (j5.length() > 0) {
                            jSONObject.put(b.ab, j5);
                        }
                        jSONObject.put(b.af, string);
                        jSONObject.put("__ii", string);
                        jSONObject.put(b.ag, j4);
                        JSONObject h = b.a().h(g);
                        if (h != null && h.length() > 0) {
                            Iterator keys = h.keys();
                            if (keys != null) {
                                while (keys.hasNext()) {
                                    try {
                                        string = keys.next().toString();
                                        if (!Arrays.asList(b.au).contains(string)) {
                                            jSONObject.put(string, h.get(string));
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                        j.a(g).a(jSONObject, 3, false);
                    }
                }
            } catch (Throwable th) {
            }
            return true;
        }
    }

    private String a(Context context, SharedPreferences sharedPreferences, long j) {
        if (g == null && context != null) {
            g = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
        String b = b(g);
        try {
            d(context);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("__e", j);
            g.a(g).a(b, jSONObject, com.umeng.analytics.pro.g.a.BEGIN);
            a(g);
            Editor edit = sharedPreferences.edit();
            edit.putString(c, b);
            edit.putLong(a, j);
            edit.putLong(b, 0);
            edit.putLong("a_start_time", j);
            edit.putLong("a_end_time", 0);
            edit.putInt("versioncode", Integer.parseInt(UMUtils.getAppVersionCode(context)));
            edit.putString("versionname", UMUtils.getAppVersionName(context));
            edit.commit();
            Object jSONObject2 = new JSONObject();
            JSONObject j2 = b.a().j();
            if (j2.length() > 0) {
                jSONObject2.put(b.ab, j2);
            }
            jSONObject2.put(b.ad, b);
            jSONObject2.put("__ii", b);
            jSONObject2.put(b.ae, j);
            j.a(g).c(jSONObject2);
        } catch (Throwable th) {
        }
        return b;
    }

    private void d(Context context) {
        j.a(context).b(context);
        j.a(context).a();
    }

    public boolean a(Context context, long j) {
        boolean z = false;
        try {
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            if (sharedPreferences == null) {
                return false;
            }
            String string = sharedPreferences.getString(c, null);
            if (string == null) {
                return false;
            }
            long j2 = sharedPreferences.getLong("a_start_time", 0);
            long j3 = sharedPreferences.getLong("a_end_time", 0);
            if (j2 > 0 && j3 == 0) {
                b(g, Long.valueOf(j));
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(com.umeng.analytics.pro.c.e.a.g, j);
                    g.a(context).a(string, jSONObject, com.umeng.analytics.pro.g.a.END);
                    j.a(g).b();
                    if (AnalyticsConfig.FLAG_DPLUS) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject = b.a().b();
                        if (jSONObject == null || jSONObject.length() < 1) {
                            jSONObject = b.a().j();
                        }
                        if (jSONObject.length() > 0) {
                            jSONObject2.put(b.ab, jSONObject);
                        }
                        jSONObject2.put(b.af, string);
                        jSONObject2.put("__ii", string);
                        jSONObject2.put(b.ag, j);
                        jSONObject = b.a().h(g);
                        if (jSONObject != null && jSONObject.length() > 0) {
                            Iterator keys = jSONObject.keys();
                            if (keys != null) {
                                while (keys.hasNext()) {
                                    try {
                                        String obj = keys.next().toString();
                                        if (!Arrays.asList(b.au).contains(obj)) {
                                            jSONObject2.put(obj, jSONObject.get(obj));
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                        j.a(g).a(jSONObject2, 3, true);
                    }
                    z = true;
                } catch (Throwable th) {
                    z = true;
                }
            }
            a(context);
            return z;
        } catch (Throwable th2) {
            return z;
        }
    }

    public void b(Context context, long j) {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
        if (sharedPreferences != null) {
            f = b(context);
            try {
                Editor edit = sharedPreferences.edit();
                edit.putString(c, f);
                edit.putLong(a, j);
                edit.putLong(b, 0);
                edit.putLong("a_start_time", j);
                edit.putLong("a_end_time", 0);
                edit.putInt("versioncode", Integer.parseInt(UMUtils.getAppVersionCode(g)));
                edit.putString("versionname", UMUtils.getAppVersionName(g));
                edit.commit();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("__e", j);
                g.a(g).a(f, jSONObject, com.umeng.analytics.pro.g.a.BEGIN);
                Object jSONObject2 = new JSONObject();
                JSONObject j2 = b.a().j();
                if (j2.length() > 0) {
                    jSONObject2.put(b.ab, j2);
                }
                jSONObject2.put(b.ad, f);
                jSONObject2.put("__ii", f);
                jSONObject2.put(b.ae, j);
                j.a(g).b(jSONObject2);
            } catch (Throwable th) {
            }
        }
    }

    public String c() {
        return f;
    }

    public String c(Context context) {
        try {
            if (f == null) {
                return PreferenceWrapper.getDefault(context).getString(c, null);
            }
        } catch (Throwable th) {
        }
        return f;
    }

    public String d() {
        return c(g);
    }
}
