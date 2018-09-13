package com.umeng.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.stub.StubApp;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.umeng.analytics.pro.g;
import com.umeng.analytics.pro.h;
import com.umeng.analytics.pro.i;
import com.umeng.analytics.pro.j;
import com.umeng.analytics.pro.j.d;
import com.umeng.analytics.pro.k;
import com.umeng.analytics.pro.n;
import com.umeng.analytics.pro.o;
import com.umeng.analytics.pro.p;
import com.umeng.analytics.pro.q;
import com.umeng.analytics.pro.s;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.proguard.l;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.microedition.khronos.opengles.GL10;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: InternalAgent */
public class b implements o {
    private static final String g = "sp";
    private static final String h = "prepp";
    private static final int n = 128;
    private static final int o = 256;
    private static List<String> p = null;
    private static List<String> q = null;
    private Context a;
    private s b;
    private k c;
    private q d;
    private p e;
    private i f;
    private boolean i;
    private volatile JSONObject j;
    private volatile JSONObject k;
    private volatile JSONObject l;
    private boolean m;

    /* compiled from: InternalAgent */
    private static class a {
        private static final b a = new b();

        private a() {
        }
    }

    private b() {
        this.a = null;
        this.c = new k();
        this.d = new q();
        this.e = p.a();
        this.f = null;
        this.i = false;
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = false;
        this.c.a((o) this);
    }

    public static b a() {
        return a.a;
    }

    public void a(Context context) {
        if (context != null) {
            if (this.a == null) {
                this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            if (!this.i) {
                this.i = true;
                i(this.a);
            }
            if (VERSION.SDK_INT > 13) {
                synchronized (this) {
                    if (!this.m) {
                        this.f = new i(context);
                        if (this.f.a()) {
                            this.m = true;
                        }
                    }
                }
            } else {
                this.m = true;
            }
            try {
                if (UMConfigure.isDebugLog()) {
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo("统计SDK常见问题索引贴 详见链接 http://developer.umeng.com/docs/66650/cate/66650", 3, "", null, null);
                }
                try {
                    Class cls = Class.forName("com.umeng.commonsdk.statistics.SdkVersion");
                    if (cls != null) {
                        Field field = cls.getField("SDK_VERSION");
                        if (field != null) {
                            Object obj = field.get(cls).toString();
                            if (TextUtils.isEmpty(obj)) {
                                MLog.e("请注意匹配正确版本的common组件基础库，详见链接 https://developer.umeng.com/docs/66632/detail/70018?um_channel=sdk");
                            } else if (!Arrays.asList(AnalyticsConfig.UM_COMMON_VERSION_LIMIT).contains(obj)) {
                                MLog.e("您当前集成的common组件基础库的版本是" + obj + "，请注意匹配正确版本的common组件基础库，详见链接 https://developer.umeng.com/docs/66632/detail/70018?um_channel=sdk");
                            }
                        } else {
                            MLog.e("请注意匹配正确版本的common组件基础库，详见链接 https://developer.umeng.com/docs/66632/detail/70018?um_channel=sdk");
                        }
                    } else {
                        MLog.e("请注意匹配正确版本的common组件基础库，详见链接 https://developer.umeng.com/docs/66632/detail/70018?um_channel=sdk");
                    }
                } catch (Exception e) {
                    MLog.e("请注意匹配正确版本的common组件基础库，详见链接 https://developer.umeng.com/docs/66632/detail/70018?um_channel=sdk");
                } catch (Throwable th) {
                }
                if (VERSION.SDK_INT > 13) {
                    UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.t, CoreProtocol.getInstance(this.a), Long.valueOf(System.currentTimeMillis()));
                }
            } catch (Throwable th2) {
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.umeng.analytics.b.i(android.content.Context):void, dom blocks: [B:1:0x0002, B:18:0x005b]
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1378)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.base/java.lang.Iterable.forEach(Iterable.java:75)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:292)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        */
    private void i(android.content.Context r4) {
        /*
        r3 = this;
        if (r4 != 0) goto L_0x0009;
    L_0x0002:
        r0 = "unexpected null context in getNativeSuperProperties";	 Catch:{ Throwable -> 0x0070 }
        com.umeng.commonsdk.statistics.common.MLog.e(r0);	 Catch:{ Throwable -> 0x0070 }
    L_0x0008:
        return;	 Catch:{ Throwable -> 0x0070 }
    L_0x0009:
        r0 = r3.a;	 Catch:{ Throwable -> 0x0070 }
        if (r0 != 0) goto L_0x0017;	 Catch:{ Throwable -> 0x0070 }
    L_0x000d:
        r0 = r4.getApplicationContext();	 Catch:{ Throwable -> 0x0070 }
        r0 = com.stub.StubApp.getOrigApplicationContext(r0);	 Catch:{ Throwable -> 0x0070 }
        r3.a = r0;	 Catch:{ Throwable -> 0x0070 }
    L_0x0017:
        r0 = com.umeng.commonsdk.statistics.internal.PreferenceWrapper.getDefault(r4);	 Catch:{ Throwable -> 0x0070 }
        r1 = "sp";	 Catch:{ Throwable -> 0x0070 }
        r2 = 0;	 Catch:{ Throwable -> 0x0070 }
        r1 = r0.getString(r1, r2);	 Catch:{ Throwable -> 0x0070 }
        r2 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Throwable -> 0x0070 }
        if (r2 != 0) goto L_0x004d;
    L_0x0029:
        r2 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0072 }
        r2.<init>(r1);	 Catch:{ JSONException -> 0x0072 }
        r3.j = r2;	 Catch:{ JSONException -> 0x0072 }
        r2 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0072 }
        r2.<init>(r1);	 Catch:{ JSONException -> 0x0072 }
        r3.k = r2;	 Catch:{ JSONException -> 0x0072 }
        r1 = r3.j;	 Catch:{ JSONException -> 0x0072 }
        if (r1 != 0) goto L_0x0042;	 Catch:{ JSONException -> 0x0072 }
    L_0x003b:
        r1 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0072 }
        r1.<init>();	 Catch:{ JSONException -> 0x0072 }
        r3.j = r1;	 Catch:{ JSONException -> 0x0072 }
    L_0x0042:
        r1 = r3.k;	 Catch:{ JSONException -> 0x0072 }
        if (r1 != 0) goto L_0x004d;	 Catch:{ JSONException -> 0x0072 }
    L_0x0046:
        r1 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0072 }
        r1.<init>();	 Catch:{ JSONException -> 0x0072 }
        r3.k = r1;	 Catch:{ JSONException -> 0x0072 }
    L_0x004d:
        r1 = "prepp";	 Catch:{ Throwable -> 0x0070 }
        r2 = 0;	 Catch:{ Throwable -> 0x0070 }
        r0 = r0.getString(r1, r2);	 Catch:{ Throwable -> 0x0070 }
        r1 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Throwable -> 0x0070 }
        if (r1 != 0) goto L_0x0008;
    L_0x005b:
        r1 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x006e }
        r1.<init>(r0);	 Catch:{ JSONException -> 0x006e }
        r3.l = r1;	 Catch:{ JSONException -> 0x006e }
        r0 = r3.l;	 Catch:{ JSONException -> 0x006e }
        if (r0 != 0) goto L_0x0008;	 Catch:{ JSONException -> 0x006e }
    L_0x0066:
        r0 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x006e }
        r0.<init>();	 Catch:{ JSONException -> 0x006e }
        r3.l = r0;	 Catch:{ JSONException -> 0x006e }
        goto L_0x0008;
    L_0x006e:
        r0 = move-exception;
        goto L_0x0008;
    L_0x0070:
        r0 = move-exception;
        goto L_0x0008;
    L_0x0072:
        r1 = move-exception;
        goto L_0x004d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.b.i(android.content.Context):void");
    }

    public JSONObject b() {
        return this.k;
    }

    public void c() {
        this.k = null;
    }

    void a(String str) {
        if (!AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            try {
                this.d.a(str);
            } catch (Throwable th) {
            }
        }
        if (UMConfigure.isDebugLog() && AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            String[] strArr = new String[]{"@"};
            String[] strArr2 = new String[]{str};
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.E, 2, "\\|", strArr, strArr2, null, null);
        }
    }

    void b(String str) {
        if (!AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            try {
                this.d.b(str);
            } catch (Throwable th) {
            }
        }
        if (UMConfigure.isDebugLog() && AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            String[] strArr = new String[]{"@"};
            String[] strArr2 = new String[]{str};
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.F, 2, "\\|", strArr, strArr2, null, null);
        }
    }

    public void a(s sVar) {
        this.b = sVar;
    }

    public void a(Context context, int i) {
        if (context == null) {
            MLog.e("unexpected null context in setVerticalType");
            return;
        }
        if (this.a == null) {
            this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
        if (!(this.i && this.m)) {
            a(this.a);
        }
        AnalyticsConfig.a(this.a, i);
    }

    public List<String> d() {
        return p;
    }

    public List<String> e() {
        return q;
    }

    void b(Context context) {
        if (context == null) {
            MLog.e("unexpected null context in onResume");
            return;
        }
        if (UMConfigure.isDebugLog() && !(context instanceof Activity)) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.o, 2, "\\|");
        }
        if (this.a == null) {
            this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
        try {
            if (!(this.i && this.m)) {
                a(context);
            }
            if (AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
                this.d.a(context.getClass().getName());
            }
            f();
            j(this.a);
            if (UMConfigure.isDebugLog() && (context instanceof Activity)) {
                if (p == null) {
                    p = new ArrayList();
                }
                if (!TextUtils.isEmpty(context.getClass().getName())) {
                    p.add(context.getClass().getName());
                }
            }
        } catch (Throwable th) {
            MLog.e("Exception occurred in Mobclick.onResume(). ", th);
        }
    }

    private void j(Context context) {
        try {
            Class.forName("com.umeng.visual.UMVisualAgent");
        } catch (ClassNotFoundException e) {
            if (VERSION.SDK_INT > 13) {
                UMWorkDispatch.sendEvent(context, com.umeng.analytics.pro.j.a.p, CoreProtocol.getInstance(context), Long.valueOf(System.currentTimeMillis()));
            }
        }
    }

    void c(Context context) {
        UMLog uMLog;
        if (context == null) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.p, 0, "\\|");
            MLog.e("unexpected null context in onPause");
            return;
        }
        if (this.a == null) {
            this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
        if (UMConfigure.isDebugLog() && !(context instanceof Activity)) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.q, 2, "\\|");
        }
        try {
            if (!(this.i && this.m)) {
                a(context);
            }
            if (AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
                this.d.b(context.getClass().getName());
            }
            g();
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e("Exception occurred in Mobclick.onRause(). ", th);
            }
        }
        if (UMConfigure.isDebugLog() && (context instanceof Activity)) {
            if (q == null) {
                q = new ArrayList();
            }
            if (!TextUtils.isEmpty(context.getClass().getName())) {
                q.add(context.getClass().getName());
            }
        }
    }

    public void a(Context context, String str, HashMap<String, Object> hashMap) {
        if (context != null) {
            try {
                if (this.a == null) {
                    this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
                }
            } catch (Throwable th) {
                if (MLog.DEBUG) {
                    MLog.e(th);
                    return;
                }
                return;
            }
        }
        if (!(this.i && this.m)) {
            a(this.a);
        }
        n.a(this.a).a(str, (Map) hashMap);
    }

    void a(Context context, String str) {
        UMLog uMLog;
        if (TextUtils.isEmpty(str)) {
            if (UMConfigure.isDebugLog()) {
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.x, 0, "\\|");
            }
        } else if (context == null) {
            MLog.e("unexpected null context in reportError");
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.w, 0, "\\|");
        } else {
            if (this.a == null) {
                this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            try {
                if (!(this.i && this.m)) {
                    a(this.a);
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("ts", System.currentTimeMillis());
                jSONObject.put(com.umeng.analytics.pro.b.L, 2);
                jSONObject.put(com.umeng.analytics.pro.b.M, str);
                jSONObject.put("__ii", this.e.d());
                UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.j, CoreProtocol.getInstance(this.a), jSONObject);
            } catch (Throwable th) {
                if (MLog.DEBUG) {
                    MLog.e(th);
                }
            }
        }
    }

    void a(Context context, Throwable th) {
        if (context == null || th == null) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.y, 0, "\\|");
            return;
        }
        if (this.a == null) {
            this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
        try {
            if (!(this.i && this.m)) {
                a(this.a);
            }
            a(this.a, DataHelper.convertExceptionToString(th));
        } catch (Throwable e) {
            if (MLog.DEBUG) {
                MLog.e(e);
            }
        }
    }

    public void f() {
        try {
            if (this.a != null) {
                UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.g, CoreProtocol.getInstance(this.a), Long.valueOf(System.currentTimeMillis()));
            }
            if (this.b != null) {
                this.b.a();
            }
        } catch (Throwable th) {
        }
    }

    public void g() {
        try {
            if (this.a != null) {
                UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.h, CoreProtocol.getInstance(this.a), Long.valueOf(System.currentTimeMillis()));
                UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.d, CoreProtocol.getInstance(this.a), null);
                UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.c, CoreProtocol.getInstance(this.a), null);
                UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.i, CoreProtocol.getInstance(this.a), null);
            }
        } catch (Throwable th) {
        }
        if (this.b != null) {
            this.b.b();
        }
    }

    public void a(Context context, String str, String str2, long j, int i) {
        if (context != null) {
            try {
                if (this.a == null) {
                    this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
                }
            } catch (Throwable th) {
                if (MLog.DEBUG) {
                    MLog.e(th);
                    return;
                }
                return;
            }
        }
        if (!(this.i && this.m)) {
            a(this.a);
        }
        n.a(this.a).a(str, str2, j, i);
    }

    public void b(Context context, String str) {
        UMLog uMLog;
        if (context == null) {
            try {
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq("MobclickAgent.onDeepLinkReceived方法Context参数不能为null。|参数Context需要指定ApplicationContext值。", 0, "\\|");
                return;
            } catch (Throwable th) {
                if (MLog.DEBUG) {
                    MLog.e(th);
                    return;
                }
                return;
            }
        }
        if (this.a == null) {
            this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
        if (!(this.i && this.m)) {
            a(this.a);
        }
        if (TextUtils.isEmpty(str)) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq("MobclickAgent.onDeepLinkReceived方法link参数不能为null，也不能为空字符串。|参数link必须为非空字符串。", 0, "\\|");
            if (MLog.DEBUG) {
                MLog.e("please check your link!");
                return;
            }
            return;
        }
        Map hashMap = new HashMap();
        hashMap.put(com.umeng.analytics.pro.b.aq, str);
        b(this.a, com.umeng.analytics.pro.b.ap, hashMap, -1);
    }

    private void b(Context context, String str, Map<String, Object> map, long j) {
        if (context != null) {
            try {
                if (this.a == null) {
                    this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
                }
                if (!(this.i && this.m)) {
                    a(this.a);
                }
                n.a(this.a).a(str, map, j);
            } catch (Throwable th) {
                if (MLog.DEBUG) {
                    MLog.e(th);
                }
            }
        }
    }

    void a(Context context, String str, Map<String, Object> map, long j) {
        try {
            UMLog uMLog;
            if (TextUtils.isEmpty(str)) {
                MLog.e("Event id is empty, please check.");
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.c, 0, "\\|");
            } else if (Arrays.asList(com.umeng.analytics.pro.b.ar).contains(str)) {
                MLog.e("Event id uses reserved keywords, please use other event name. ");
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.b, 0, "\\|");
            } else if (map.isEmpty()) {
                MLog.e("Map is empty, please check.");
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.d, 0, "\\|");
            } else {
                for (Entry key : map.entrySet()) {
                    if (Arrays.asList(com.umeng.analytics.pro.b.ar).contains(key.getKey())) {
                        MLog.e("Map key uses reserved keywords[_$!link], please use other key.");
                        uMLog = UMConfigure.umDebugLog;
                        UMLog.aq(h.e, 0, "\\|");
                        return;
                    }
                }
                b(context, str, map, j);
            }
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(th);
            }
        }
    }

    public void a(Context context, String str, Map<String, Object> map) {
        if (context != null) {
            try {
                if (this.a == null) {
                    this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
                }
            } catch (Throwable th) {
                return;
            }
        }
        if (!(this.i && this.m)) {
            a(this.a);
        }
        if (TextUtils.isEmpty(str)) {
            MLog.e("the eventName is empty! please check~");
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.ad, 0, "\\|");
            return;
        }
        String str2 = "";
        if (this.j == null) {
            this.j = new JSONObject();
        } else {
            str2 = this.j.toString();
        }
        UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.l, CoreProtocol.getInstance(this.a), new d(str, map, str2, System.currentTimeMillis()));
    }

    void d(Context context) {
        try {
            if (this.a == null && context != null) {
                this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            if (this.f != null) {
                this.f.c();
            }
            if (this.d != null) {
                this.d.a();
            }
            if (this.a != null) {
                if (this.e != null) {
                    this.e.b(this.a, Long.valueOf(System.currentTimeMillis()));
                }
                j.a(this.a).a();
                q.a(this.a);
                i.a(this.a);
                PreferenceWrapper.getDefault(this.a).edit().commit();
            }
            UMWorkDispatch.Quit();
        } catch (Throwable th) {
        }
    }

    public void a(Throwable th) {
        try {
            if (this.d != null) {
                this.d.a();
            }
            if (this.f != null) {
                this.f.c();
            }
            if (this.a != null) {
                if (this.e != null) {
                    this.e.b(this.a, Long.valueOf(System.currentTimeMillis()));
                }
                if (th != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("ts", System.currentTimeMillis());
                    jSONObject.put(com.umeng.analytics.pro.b.L, 1);
                    jSONObject.put(com.umeng.analytics.pro.b.M, DataHelper.convertExceptionToString(th));
                    g.a(this.a).a(this.e.d(), jSONObject.toString(), 1);
                }
                j.a(this.a).a();
                q.a(this.a);
                i.a(this.a);
                PreferenceWrapper.getDefault(this.a).edit().commit();
            }
            UMWorkDispatch.Quit();
        } catch (Throwable e) {
            if (MLog.DEBUG) {
                MLog.e("Exception in onAppCrash", e);
            }
        }
    }

    void a(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(com.umeng.analytics.pro.b.H, str);
            jSONObject.put("uid", str2);
            UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.e, CoreProtocol.getInstance(this.a), jSONObject);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(" Excepthon  in  onProfileSignIn", th);
            }
        }
    }

    void h() {
        UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.f, CoreProtocol.getInstance(this.a), null);
    }

    void a(boolean z) {
        AnalyticsConfig.CATCH_EXCEPTION = z;
    }

    void a(GL10 gl10) {
        String[] gpu = UMUtils.getGPU(gl10);
        if (gpu.length == 2) {
            AnalyticsConfig.GPU_VENDER = gpu[0];
            AnalyticsConfig.GPU_RENDERER = gpu[1];
        }
    }

    void b(boolean z) {
        AnalyticsConfig.ACTIVITY_DURATION_OPEN = z;
    }

    void a(double d, double d2) {
        if (AnalyticsConfig.a == null) {
            AnalyticsConfig.a = new double[2];
        }
        AnalyticsConfig.a[0] = d;
        AnalyticsConfig.a[1] = d2;
    }

    void a(Context context, EScenarioType eScenarioType) {
        if (context == null) {
            MLog.e("unexpected null context in setScenarioType");
            return;
        }
        if (this.a == null) {
            this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
        if (eScenarioType != null) {
            int toValue = eScenarioType.toValue();
            if (toValue == EScenarioType.E_DUM_NORMAL.toValue()) {
                AnalyticsConfig.FLAG_DPLUS = true;
            } else if (toValue == EScenarioType.E_DUM_GAME.toValue()) {
                AnalyticsConfig.FLAG_DPLUS = true;
            } else {
                AnalyticsConfig.FLAG_DPLUS = false;
            }
            a(this.a, toValue);
        }
        if (!this.i || !this.m) {
            a(this.a);
        }
    }

    void c(Context context, String str) {
        if (context == null) {
            MLog.e("unexpected null context in setSecret");
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.aq("MobclickAgent.setSecret方法参数context不能为null|参数Context需要指定ApplicationContext值。", 0, "\\|");
            return;
        }
        if (this.a == null) {
            this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
        if (!(this.i && this.m)) {
            a(this.a);
        }
        AnalyticsConfig.a(this.a, str);
    }

    void a(long j) {
        AnalyticsConfig.kContinueSessionMillis = j;
    }

    public synchronized void a(Context context, String str, Object obj) {
        UMLog uMLog;
        if (context == null) {
            try {
                MLog.e("unexpected null context in registerSuperProperty");
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.af, 0, "\\|");
            } catch (Throwable th) {
                th.printStackTrace();
            }
        } else {
            if (this.a == null) {
                this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            if (!(this.i && this.m)) {
                a(this.a);
            }
            if (TextUtils.isEmpty(str) || obj == null) {
                MLog.e("please check key or value, must not NULL!");
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.ag, 0, "\\|");
            } else {
                String subStr = HelperUtils.subStr(str, 128);
                if (Arrays.asList(com.umeng.analytics.pro.b.at).contains(subStr)) {
                    MLog.e("SuperProperty  key is invalid.  ");
                } else {
                    if (obj instanceof String) {
                        obj = HelperUtils.subStr(obj.toString(), 256);
                    }
                    a(subStr, obj);
                    UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.m, CoreProtocol.getInstance(this.a), this.j.toString());
                }
            }
        }
        return;
    }

    private void a(String str, Object obj) {
        int i = 0;
        try {
            if (this.j == null) {
                this.j = new JSONObject();
            }
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray;
            if (obj.getClass().isArray()) {
                if (obj instanceof String[]) {
                    String[] strArr = (String[]) obj;
                    jSONArray = new JSONArray();
                    while (i < strArr.length) {
                        jSONArray.put(strArr[i]);
                        i++;
                    }
                    this.j.put(str, jSONArray);
                } else if (obj instanceof long[]) {
                    long[] jArr = (long[]) obj;
                    jSONArray = new JSONArray();
                    while (i < jArr.length) {
                        jSONArray.put(jArr[i]);
                        i++;
                    }
                    this.j.put(str, jSONArray);
                } else if (obj instanceof int[]) {
                    int[] iArr = (int[]) obj;
                    jSONArray = new JSONArray();
                    while (i < iArr.length) {
                        jSONArray.put(iArr[i]);
                        i++;
                    }
                    this.j.put(str, jSONArray);
                } else if (obj instanceof float[]) {
                    float[] fArr = (float[]) obj;
                    jSONArray = new JSONArray();
                    while (i < fArr.length) {
                        jSONArray.put((double) fArr[i]);
                        i++;
                    }
                    this.j.put(str, jSONArray);
                } else if (obj instanceof double[]) {
                    double[] dArr = (double[]) obj;
                    jSONArray = new JSONArray();
                    while (i < dArr.length) {
                        jSONArray.put(dArr[i]);
                        i++;
                    }
                    this.j.put(str, jSONArray);
                } else if (obj instanceof short[]) {
                    short[] sArr = (short[]) obj;
                    jSONArray = new JSONArray();
                    while (i < sArr.length) {
                        jSONArray.put(sArr[i]);
                        i++;
                    }
                    this.j.put(str, jSONArray);
                }
            } else if (obj instanceof List) {
                List list = (List) obj;
                jSONArray = new JSONArray();
                while (i < list.size()) {
                    Object obj2 = list.get(i);
                    if ((obj2 instanceof String) || (obj2 instanceof Long) || (obj2 instanceof Integer) || (obj2 instanceof Float) || (obj2 instanceof Double) || (obj2 instanceof Short)) {
                        jSONArray.put(list.get(i));
                    }
                    i++;
                }
                this.j.put(str, jSONArray);
            } else if ((obj instanceof String) || (obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Float) || (obj instanceof Double) || (obj instanceof Short)) {
                this.j.put(str, obj);
            }
        } catch (Throwable th) {
        }
    }

    public synchronized void a(Object obj) {
        if (obj != null) {
            try {
                if (this.a != null) {
                    String str = (String) obj;
                    Editor edit = PreferenceWrapper.getDefault(this.a).edit();
                    if (!(edit == null || TextUtils.isEmpty(str))) {
                        edit.putString(g, this.j.toString()).commit();
                    }
                }
            } catch (Throwable th) {
            }
        }
    }

    public synchronized void d(Context context, String str) {
        if (context == null) {
            try {
                MLog.e("unexpected null context in unregisterSuperProperty");
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.ah, 0, "\\|");
            } catch (Throwable th) {
                th.printStackTrace();
            }
        } else {
            if (this.a == null) {
                this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            if (!(this.i && this.m)) {
                a(this.a);
            }
            String subStr = HelperUtils.subStr(str, 128);
            if (this.j == null) {
                this.j = new JSONObject();
            }
            if (this.j.has(subStr)) {
                this.j.remove(subStr);
                UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.o, CoreProtocol.getInstance(this.a), subStr);
            }
        }
        return;
    }

    public synchronized void i() {
        try {
            if (this.j == null || this.a == null) {
                this.j = new JSONObject();
            } else {
                Editor edit = PreferenceWrapper.getDefault(this.a).edit();
                edit.putString(g, this.j.toString());
                edit.commit();
            }
        } catch (Throwable th) {
        }
    }

    public synchronized Object e(Context context, String str) {
        Object obj = null;
        synchronized (this) {
            if (context == null) {
                try {
                    MLog.e("unexpected null context in getSuperProperty");
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.aq(h.ai, 0, "\\|");
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            } else {
                if (this.a == null) {
                    this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
                }
                if (this.j != null) {
                    String subStr = HelperUtils.subStr(str, 128);
                    if (this.j.has(subStr)) {
                        obj = this.j.opt(subStr);
                    }
                } else {
                    this.j = new JSONObject();
                }
            }
        }
        return obj;
    }

    public synchronized String e(Context context) {
        String str = null;
        synchronized (this) {
            if (context == null) {
                try {
                    MLog.e("unexpected null context in getSuperProperties");
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.aq(h.ai, 0, "\\|");
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            } else {
                if (this.a == null) {
                    this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
                }
                if (this.j != null) {
                    str = this.j.toString();
                } else {
                    this.j = new JSONObject();
                }
            }
        }
        return str;
    }

    public synchronized JSONObject j() {
        try {
            if (this.j == null) {
                this.j = new JSONObject();
            }
        } catch (Throwable th) {
        }
        return this.j;
    }

    public synchronized void f(Context context) {
        if (context == null) {
            MLog.e("unexpected null context in clearSuperProperties");
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.ah, 0, "\\|");
        } else {
            if (this.a == null) {
                this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            if (!(this.i && this.m)) {
                a(this.a);
            }
            this.j = new JSONObject();
            UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.n, CoreProtocol.getInstance(this.a), null);
        }
    }

    public synchronized void k() {
        try {
            if (this.a != null) {
                Editor edit = PreferenceWrapper.getDefault(this.a).edit();
                edit.remove(g);
                edit.commit();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return;
    }

    public synchronized void a(Context context, List<String> list) {
        if (context == null) {
            try {
                MLog.e("unexpected null context in setFirstLaunchEvent");
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.aj, 0, "\\|");
            } catch (Throwable th) {
                MLog.e(th);
            }
        } else {
            if (this.a == null) {
                this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            if (!(this.i && this.m)) {
                a(this.a);
            }
            n.a(this.a).a((List) list);
        }
        return;
    }

    public synchronized void a(Context context, JSONObject jSONObject) {
        UMLog uMLog;
        if (context == null) {
            MLog.e("unexpected null context in setPreProperties");
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.al, 0, "\\|");
        } else {
            if (this.a == null) {
                this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            if (!(this.i && this.m)) {
                a(this.a);
            }
            if (this.l == null) {
                this.l = new JSONObject();
            }
            if (UMConfigure.isDebugLog() && jSONObject == null) {
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.am, 0, "\\|");
            }
            Iterator keys = jSONObject.keys();
            if (keys != null) {
                while (keys.hasNext()) {
                    try {
                        String obj = keys.next().toString();
                        Object obj2 = jSONObject.get(obj);
                        if (b(obj, obj2)) {
                            this.l.put(obj, obj2);
                        }
                    } catch (Exception e) {
                    }
                }
            }
            if (this.l.length() > 0) {
                UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.q, CoreProtocol.getInstance(this.a), this.l.toString());
            }
        }
    }

    public synchronized void f(Context context, String str) {
        UMLog uMLog;
        if (context == null) {
            MLog.e("unexpected null context in clearPreProperties(context, " + str + l.t);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.an, 0, "\\|");
        } else {
            if (this.a == null) {
                this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            if (!(this.i && this.m)) {
                a(this.a);
            }
            if (this.l == null) {
                this.l = new JSONObject();
            }
            if (this.l.has(str)) {
                this.l.remove(str);
                UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.r, CoreProtocol.getInstance(this.a), this.l.toString());
            } else if (UMConfigure.isDebugLog()) {
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.ao, 0, "\\|");
            }
        }
    }

    public synchronized void g(Context context) {
        if (context == null) {
            MLog.e("unexpected null context in clearPreProperties");
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.ap, 0, "\\|");
        } else {
            if (this.a == null) {
                this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            if (!(this.i && this.m)) {
                a(this.a);
            }
            if (this.l.length() > 0) {
                UMWorkDispatch.sendEvent(this.a, com.umeng.analytics.pro.j.a.s, CoreProtocol.getInstance(this.a), null);
            }
            this.l = new JSONObject();
        }
    }

    public synchronized JSONObject h(Context context) {
        JSONObject jSONObject;
        if (context == null) {
            MLog.e("unexpected null context in clearPreProperties");
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aq, 0, "\\|");
            jSONObject = null;
        } else {
            if (this.a == null) {
                this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            if (!(this.i && this.m)) {
                a(this.a);
            }
            if (this.l == null) {
                this.l = new JSONObject();
            }
            JSONObject jSONObject2 = new JSONObject();
            if (this.l.length() > 0) {
                try {
                    jSONObject = new JSONObject(this.l.toString());
                } catch (JSONException e) {
                    jSONObject = jSONObject2;
                }
            } else {
                jSONObject = jSONObject2;
            }
        }
        return jSONObject;
    }

    public synchronized void b(Object obj) {
        try {
            Editor edit = PreferenceWrapper.getDefault(this.a).edit();
            if (obj != null) {
                String str = (String) obj;
                if (!(edit == null || TextUtils.isEmpty(str))) {
                    edit.putString(h, str).commit();
                }
            } else if (edit != null) {
                edit.remove(h).commit();
            }
        } catch (Throwable th) {
        }
    }

    private boolean b(String str, Object obj) {
        int length;
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            length = str.getBytes("UTF-8").length;
            if (length > 128) {
                return false;
            }
            if (Arrays.asList(com.umeng.analytics.pro.b.au).contains(str)) {
                return false;
            }
            if (obj instanceof String) {
                if (((String) obj).getBytes("UTF-8").length <= 256) {
                    return true;
                }
                return false;
            } else if (obj instanceof Integer) {
                return true;
            } else {
                if (obj instanceof Long) {
                    return true;
                }
                if (obj instanceof Double) {
                    return true;
                }
                if (obj instanceof Float) {
                    return true;
                }
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            length = 0;
        } catch (Throwable th) {
            return false;
        }
    }
}
