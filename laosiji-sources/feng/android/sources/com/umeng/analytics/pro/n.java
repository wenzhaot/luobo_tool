package com.umeng.analytics.pro;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.stub.StubApp;
import com.umeng.analytics.CoreProtocol;
import com.umeng.analytics.pro.j.d;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: EventTracker */
public class n {
    private static final String a = "fs_lc_tl";
    private static final String f = "-1";
    private static Context g;
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private JSONObject h;

    /* compiled from: EventTracker */
    private static class a {
        private static final n a = new n();

        private a() {
        }
    }

    private n() {
        this.b = 128;
        this.c = AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS;
        this.d = 1024;
        this.e = 10;
        this.h = null;
        try {
            if (this.h == null) {
                b(g);
            }
        } catch (Throwable th) {
        }
    }

    public static n a(Context context) {
        if (g == null && context != null) {
            g = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
        return a.a;
    }

    public void a(String str, Map<String, Object> map, long j) {
        int i = 0;
        try {
            UMLog uMLog;
            if (!a(str)) {
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.f, 0, "\\|");
            } else if (!b((Map) map)) {
            } else {
                if (Arrays.asList(b.as).contains(str)) {
                    MLog.e("the id is valid!");
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.aq(h.b, 0, "\\|");
                    return;
                }
                Object c;
                String str2;
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("id", str);
                jSONObject.put("ts", System.currentTimeMillis());
                if (j > 0) {
                    jSONObject.put(b.R, j);
                }
                jSONObject.put("__t", g.a);
                Iterator it = map.entrySet().iterator();
                while (true) {
                    int i2 = i;
                    if (i2 >= 10 || !it.hasNext()) {
                        c = p.a().c();
                        str2 = "__i";
                    } else {
                        Entry entry = (Entry) it.next();
                        if (Arrays.asList(b.as).contains(entry.getKey())) {
                            MLog.e("the key in map is invalid.  ");
                            uMLog = UMConfigure.umDebugLog;
                            UMLog.aq(h.e, 0, "\\|");
                        } else {
                            Object value = entry.getValue();
                            if ((value instanceof String) || (value instanceof Integer) || (value instanceof Long)) {
                                jSONObject.put((String) entry.getKey(), value);
                            }
                        }
                        i = i2 + 1;
                    }
                }
                c = p.a().c();
                str2 = "__i";
                if (TextUtils.isEmpty(c)) {
                    c = f;
                }
                jSONObject.put(str2, c);
                UMWorkDispatch.sendEvent(g, com.umeng.analytics.pro.j.a.a, CoreProtocol.getInstance(g), jSONObject);
            }
        } catch (Throwable th) {
        }
    }

    public void a(String str, String str2, long j, int i) {
        try {
            UMLog uMLog;
            if (!a(str) || !b(str2)) {
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.l, 0, "\\|");
            } else if (Arrays.asList(b.as).contains(str)) {
                MLog.e("the id is valid!");
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.m, 0, "\\|");
            } else {
                Object str22;
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("id", str);
                jSONObject.put("ts", System.currentTimeMillis());
                if (j > 0) {
                    jSONObject.put(b.R, j);
                }
                jSONObject.put("__t", g.a);
                if (str22 == null) {
                    str22 = "";
                }
                jSONObject.put(str, str22);
                Object c = p.a().c();
                String str3 = "__i";
                if (TextUtils.isEmpty(c)) {
                    c = f;
                }
                jSONObject.put(str3, c);
                UMWorkDispatch.sendEvent(g, com.umeng.analytics.pro.j.a.a, CoreProtocol.getInstance(g), jSONObject);
            }
        } catch (Throwable th) {
        }
    }

    public void a(Object obj) {
        try {
            d dVar = (d) obj;
            Object c = dVar.c();
            Map a = dVar.a();
            Object b = dVar.b();
            long d = dVar.d();
            if (!TextUtils.isEmpty(c)) {
                String subStr = HelperUtils.subStr(c, 128);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(b.aa, subStr);
                jSONObject.put("_$!ts", d);
                c = p.a().c();
                String str = "__ii";
                if (TextUtils.isEmpty(c)) {
                    c = f;
                }
                jSONObject.put(str, c);
                if (!TextUtils.isEmpty(b)) {
                    try {
                        JSONObject jSONObject2 = new JSONObject(b);
                        if (jSONObject2.length() > 0) {
                            jSONObject.put(b.ab, jSONObject2);
                        }
                    } catch (JSONException e) {
                    }
                }
                a();
                if (a != null) {
                    JSONObject a2 = a(a);
                    if (a2.length() > 0) {
                        Iterator keys = a2.keys();
                        while (keys.hasNext()) {
                            String str2 = (String) keys.next();
                            if (Arrays.asList(b.at).contains(str2)) {
                                MLog.e("the key in map about track interface is invalid.  ");
                            } else {
                                jSONObject.put(str2, a2.get(str2));
                            }
                        }
                    }
                }
                if (!(this.h == null || !this.h.has(subStr) || ((Boolean) this.h.get(subStr)).booleanValue())) {
                    jSONObject.put(b.T, 1);
                    this.h.put(subStr, true);
                    c(g);
                }
                j.a(g).a(jSONObject, 0, false);
            }
        } catch (Throwable th) {
        }
    }

    public void a(String str, Map<String, Object> map) {
        int i = 0;
        try {
            if (a(str)) {
                Object c;
                String str2;
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("id", str);
                jSONObject.put("ts", System.currentTimeMillis());
                jSONObject.put(b.R, 0);
                jSONObject.put("__t", 2050);
                Iterator it = map.entrySet().iterator();
                while (true) {
                    int i2 = i;
                    if (i2 >= 10 || !it.hasNext()) {
                        c = p.a().c();
                        str2 = "__i";
                    } else {
                        Entry entry = (Entry) it.next();
                        if (!(b.T.equals(entry.getKey()) || b.R.equals(entry.getKey()) || "id".equals(entry.getKey()) || "ts".equals(entry.getKey()))) {
                            Object value = entry.getValue();
                            if ((value instanceof String) || (value instanceof Integer) || (value instanceof Long)) {
                                jSONObject.put((String) entry.getKey(), value);
                            }
                        }
                        i = i2 + 1;
                    }
                }
                c = p.a().c();
                str2 = "__i";
                if (TextUtils.isEmpty(c)) {
                    c = f;
                }
                jSONObject.put(str2, c);
                UMWorkDispatch.sendEvent(g, com.umeng.analytics.pro.j.a.b, CoreProtocol.getInstance(g), jSONObject);
            }
        } catch (Throwable th) {
        }
    }

    private void b(Context context) {
        try {
            Object string = PreferenceWrapper.getDefault(context).getString(a, null);
            if (!TextUtils.isEmpty(string)) {
                this.h = new JSONObject(string);
            }
            a();
        } catch (Exception e) {
        }
    }

    private void a() {
        int i = 0;
        try {
            Object imprintProperty = UMEnvelopeBuild.imprintProperty(g, "track_list", "");
            if (!TextUtils.isEmpty(imprintProperty)) {
                String[] split = imprintProperty.split("!");
                JSONObject jSONObject = new JSONObject();
                if (this.h != null) {
                    for (String subStr : split) {
                        String subStr2 = HelperUtils.subStr(subStr2, 128);
                        if (this.h.has(subStr2)) {
                            jSONObject.put(subStr2, this.h.get(subStr2));
                        }
                    }
                }
                this.h = new JSONObject();
                if (split.length >= 10) {
                    while (i < 10) {
                        a(split[i], jSONObject);
                        i++;
                    }
                } else {
                    while (i < split.length) {
                        a(split[i], jSONObject);
                        i++;
                    }
                }
                c(g);
            }
        } catch (Exception e) {
        }
    }

    private void a(String str, JSONObject jSONObject) throws JSONException {
        String subStr = HelperUtils.subStr(str, 128);
        if (jSONObject.has(subStr)) {
            a(subStr, ((Boolean) jSONObject.get(subStr)).booleanValue());
        } else {
            a(subStr, false);
        }
    }

    private void a(String str, boolean z) {
        try {
            if (!b.T.equals(str) && !b.R.equals(str) && !"id".equals(str) && !"ts".equals(str) && !this.h.has(str)) {
                this.h.put(str, z);
            }
        } catch (Exception e) {
        }
    }

    private void c(Context context) {
        try {
            if (this.h != null) {
                PreferenceWrapper.getDefault(g).edit().putString(a, this.h.toString()).commit();
            }
        } catch (Throwable th) {
        }
    }

    public void a(List<String> list) {
        int i = 0;
        if (list != null) {
            try {
                if (list.size() > 0) {
                    a();
                    int i2;
                    if (this.h == null) {
                        this.h = new JSONObject();
                        int size = list.size();
                        for (i2 = 0; i2 < size; i2++) {
                            if (this.h != null) {
                                if (this.h.length() >= 5) {
                                    break;
                                }
                            }
                            this.h = new JSONObject();
                            String str = (String) list.get(i2);
                            if (!TextUtils.isEmpty(str)) {
                                a(HelperUtils.subStr(str, 128), false);
                            }
                        }
                        c(g);
                        return;
                    } else if (this.h.length() >= 5) {
                        MLog.d("already setFistLaunchEvent, igone.");
                        return;
                    } else {
                        while (true) {
                            i2 = i;
                            if (i2 >= list.size()) {
                                c(g);
                                return;
                            } else if (this.h.length() >= 5) {
                                MLog.d(" add setFistLaunchEvent over.");
                                return;
                            } else {
                                a(HelperUtils.subStr((String) list.get(i2), 128), false);
                                i = i2 + 1;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                return;
            }
        }
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.ak, 0, "\\|");
    }

    private JSONObject a(Map<String, Object> map) {
        JSONObject jSONObject = new JSONObject();
        try {
            for (Entry entry : map.entrySet()) {
                try {
                    String str = (String) entry.getKey();
                    if (str != null) {
                        String subStr = HelperUtils.subStr(str, 128);
                        Object value = entry.getValue();
                        if (value != null) {
                            JSONArray jSONArray;
                            int i;
                            if (value.getClass().isArray()) {
                                if (value instanceof int[]) {
                                    int[] iArr = (int[]) value;
                                    jSONArray = new JSONArray();
                                    for (int put : iArr) {
                                        jSONArray.put(put);
                                    }
                                    jSONObject.put(subStr, jSONArray);
                                } else if (value instanceof double[]) {
                                    double[] dArr = (double[]) value;
                                    jSONArray = new JSONArray();
                                    for (double put2 : dArr) {
                                        jSONArray.put(put2);
                                    }
                                    jSONObject.put(subStr, jSONArray);
                                } else if (value instanceof long[]) {
                                    long[] jArr = (long[]) value;
                                    jSONArray = new JSONArray();
                                    for (long put3 : jArr) {
                                        jSONArray.put(put3);
                                    }
                                    jSONObject.put(subStr, jSONArray);
                                } else if (value instanceof float[]) {
                                    float[] fArr = (float[]) value;
                                    jSONArray = new JSONArray();
                                    for (float f : fArr) {
                                        jSONArray.put((double) f);
                                    }
                                    jSONObject.put(subStr, jSONArray);
                                } else if (value instanceof short[]) {
                                    short[] sArr = (short[]) value;
                                    jSONArray = new JSONArray();
                                    for (short put4 : sArr) {
                                        jSONArray.put(put4);
                                    }
                                    jSONObject.put(subStr, jSONArray);
                                }
                            } else if (value instanceof List) {
                                List list = (List) value;
                                jSONArray = new JSONArray();
                                for (i = 0; i < list.size(); i++) {
                                    Object obj = list.get(i);
                                    if ((obj instanceof String) || (obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Float) || (obj instanceof Double) || (obj instanceof Short)) {
                                        jSONArray.put(list.get(i));
                                    }
                                }
                                if (jSONArray.length() > 0) {
                                    jSONObject.put(subStr, jSONArray);
                                }
                            } else if (value instanceof String) {
                                jSONObject.put(subStr, HelperUtils.subStr(value.toString(), AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS));
                            } else if ((value instanceof Long) || (value instanceof Integer) || (value instanceof Float) || (value instanceof Double) || (value instanceof Short)) {
                                jSONObject.put(subStr, value);
                            } else {
                                MLog.e("The param has not support type. please check !");
                            }
                        }
                    }
                } catch (Throwable e) {
                    MLog.e(e);
                }
            }
        } catch (Exception e2) {
        }
        return jSONObject;
    }

    private boolean a(String str) {
        if (str != null) {
            try {
                int length = str.trim().getBytes().length;
                if (length > 0 && length <= 128) {
                    return true;
                }
            } catch (Exception e) {
            }
        }
        MLog.e("Event id is empty or too long in tracking Event");
        return false;
    }

    private boolean b(String str) {
        if (str == null) {
            return true;
        }
        try {
            if (str.trim().getBytes().length <= AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS) {
                return true;
            }
            MLog.e("Event label or value is empty or too long in tracking Event");
            return false;
        } catch (Exception e) {
        }
    }

    private boolean c(String str) {
        UMLog uMLog;
        if (str == null) {
            try {
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq("MobclickAgent.onDeepLinkReceived方法link参数不能为null，也不能为空字符串。|参数link必须为非空字符串。", 0, "\\|");
                return true;
            } catch (Exception e) {
            }
        } else if (str.trim().getBytes().length <= 1024) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq("MobclickAgent.onDeepLinkReceived方法link参数长度超过限制。|参数link长度不能超过1024字符。", 0, "\\|");
            return true;
        } else {
            MLog.e("DeepLink value too long in tracking Event.");
            return false;
        }
    }

    private boolean b(Map<String, Object> map) {
        UMLog uMLog;
        if (map != null) {
            try {
                if (!map.isEmpty()) {
                    for (Entry entry : map.entrySet()) {
                        if (!a((String) entry.getKey())) {
                            MLog.e("map has NULL key. please check!");
                            uMLog = UMConfigure.umDebugLog;
                            UMLog.aq(h.h, 0, "\\|");
                            return false;
                        } else if (entry.getValue() == null) {
                            uMLog = UMConfigure.umDebugLog;
                            UMLog.aq(h.i, 0, "\\|");
                            return false;
                        } else if (entry.getValue() instanceof String) {
                            if (b.aq.equals(entry.getKey())) {
                                if (!c(entry.getValue().toString())) {
                                    return false;
                                }
                            } else if (!b(entry.getValue().toString())) {
                                uMLog = UMConfigure.umDebugLog;
                                UMLog.aq(h.j, 0, "\\|");
                                return false;
                            }
                        }
                    }
                    return true;
                }
            } catch (Exception e) {
            }
        }
        MLog.e("map is null or empty in onEvent");
        uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.g, 0, "\\|");
        return false;
    }
}
