package com.umeng.analytics.pro;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.feng.car.video.shortvideo.FileUtils;
import com.stub.StubApp;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.CoreProtocol;
import com.umeng.analytics.b;
import com.umeng.analytics.pro.c.e.a;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AutoViewPageTracker */
public class i {
    public static String a = null;
    private static JSONArray e = new JSONArray();
    private static Object f = new Object();
    boolean b = false;
    ActivityLifecycleCallbacks c = new ActivityLifecycleCallbacks() {
        public void onActivityStopped(Activity activity) {
            try {
                if (UMConfigure.isDebugLog()) {
                    String name = activity.getClass().getName();
                    List e = b.a().e();
                    if (!TextUtils.isEmpty(name) && e != null) {
                        if (e.contains(name)) {
                            e.remove(name);
                            return;
                        }
                        String[] strArr = new String[]{"@"};
                        String[] strArr2 = new String[]{name.substring(0, name.length())};
                        UMLog uMLog = UMConfigure.umDebugLog;
                        UMLog.aq(h.s, 0, "\\|", strArr, strArr2, null, null);
                    }
                }
            } catch (Throwable th) {
            }
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityResumed(Activity activity) {
            if (activity == null) {
                return;
            }
            if (i.this.b) {
                i.this.b = false;
                if (TextUtils.isEmpty(i.a)) {
                    i.a = activity.getPackageName() + FileUtils.FILE_EXTENSION_SEPARATOR + activity.getLocalClassName();
                    return;
                } else if (!i.a.equals(activity.getPackageName() + FileUtils.FILE_EXTENSION_SEPARATOR + activity.getLocalClassName())) {
                    i.this.a(activity);
                    return;
                } else {
                    return;
                }
            }
            i.this.a(activity);
        }

        public void onActivityPaused(Activity activity) {
            i.this.b(activity);
            i.this.b = false;
            try {
                if (UMConfigure.isDebugLog()) {
                    String name = activity.getClass().getName();
                    List d = b.a().d();
                    if (!TextUtils.isEmpty(name) && d != null) {
                        if (d.contains(name)) {
                            d.remove(name);
                            return;
                        }
                        String[] strArr = new String[]{"@"};
                        String[] strArr2 = new String[]{name.substring(0, name.length())};
                        UMLog uMLog = UMConfigure.umDebugLog;
                        UMLog.aq(h.r, 0, "\\|", strArr, strArr2, null, null);
                    }
                }
            } catch (Throwable th) {
            }
        }

        public void onActivityDestroyed(Activity activity) {
            try {
                if (UMConfigure.isDebugLog()) {
                    CharSequence name = activity.getClass().getName();
                    List d = b.a().d();
                    List e = b.a().e();
                    if (!TextUtils.isEmpty(name)) {
                        if (d != null && d.contains(name)) {
                            d.remove(name);
                        }
                        if (e != null && e.contains(name)) {
                            e.remove(name);
                        }
                    }
                }
            } catch (Throwable th) {
            }
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }
    };
    private final Map<String, Long> d = new HashMap();
    private Application g = null;
    private boolean h = false;

    public boolean a() {
        return this.h;
    }

    public i(Context context) {
        synchronized (this) {
            if (this.g == null && context != null) {
                if (context instanceof Activity) {
                    this.g = ((Activity) context).getApplication();
                } else if (context instanceof Application) {
                    this.g = (Application) context;
                }
                if (this.g != null) {
                    b(context);
                }
            }
        }
    }

    private void b(Context context) {
        if (!this.h) {
            this.h = true;
            if (this.g != null) {
                this.g.registerActivityLifecycleCallbacks(this.c);
            }
            if ((context instanceof Activity) && a == null) {
                this.b = true;
                a((Activity) context);
            }
        }
    }

    public void b() {
        this.h = false;
        if (this.g != null) {
            this.g.unregisterActivityLifecycleCallbacks(this.c);
            this.g = null;
        }
    }

    public void c() {
        b(null);
        b();
    }

    public static void a(Context context) {
        if (context != null) {
            try {
                String jSONArray;
                JSONObject jSONObject = new JSONObject();
                synchronized (f) {
                    jSONArray = e.toString();
                    e = new JSONArray();
                }
                if (jSONArray.length() > 0) {
                    jSONObject.put(a.c, new JSONArray(jSONArray));
                    g.a(context).a(p.a().d(), jSONObject, g.a.AUTOPAGE);
                }
            } catch (Throwable th) {
            }
        }
    }

    private void a(Activity activity) {
        a = activity.getPackageName() + FileUtils.FILE_EXTENSION_SEPARATOR + activity.getLocalClassName();
        if (AnalyticsConfig.FLAG_DPLUS) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(b.ai, a);
                jSONObject.put("_$!ts", System.currentTimeMillis());
                JSONObject j = b.a().j();
                if (j.length() > 0) {
                    jSONObject.put(b.ab, j);
                }
                Object c = p.a().c();
                String str = "__ii";
                if (TextUtils.isEmpty(c)) {
                    c = "-1";
                }
                jSONObject.put(str, c);
                if (p.a().b()) {
                    jSONObject.put("__ii", "-1");
                }
                j = b.a().h(StubApp.getOrigApplicationContext(activity.getApplicationContext()));
                if (j != null && j.length() > 0) {
                    Iterator keys = j.keys();
                    if (keys != null) {
                        while (keys.hasNext()) {
                            try {
                                String obj = keys.next().toString();
                                if (!Arrays.asList(b.au).contains(obj)) {
                                    jSONObject.put(obj, j.get(obj));
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }
                UMWorkDispatch.sendEvent(StubApp.getOrigApplicationContext(activity.getApplicationContext()), j.a.k, CoreProtocol.getInstance(StubApp.getOrigApplicationContext(activity.getApplicationContext())), jSONObject);
            } catch (JSONException e2) {
            }
        }
        synchronized (this.d) {
            this.d.put(a, Long.valueOf(System.currentTimeMillis()));
        }
    }

    private void b(Activity activity) {
        long j = 0;
        try {
            synchronized (this.d) {
                if (a == null && activity != null) {
                    a = activity.getPackageName() + FileUtils.FILE_EXTENSION_SEPARATOR + activity.getLocalClassName();
                }
                if (!TextUtils.isEmpty(a) && this.d.containsKey(a)) {
                    j = System.currentTimeMillis() - ((Long) this.d.get(a)).longValue();
                    this.d.remove(a);
                }
            }
            synchronized (f) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(b.u, a);
                    jSONObject.put("duration", j);
                    e.put(jSONObject);
                } catch (Throwable th) {
                }
            }
        } catch (Throwable th2) {
        }
    }
}
