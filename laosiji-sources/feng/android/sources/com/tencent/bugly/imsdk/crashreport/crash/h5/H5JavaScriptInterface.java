package com.tencent.bugly.imsdk.crashreport.crash.h5;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.tencent.bugly.imsdk.crashreport.inner.InnerApi;
import com.tencent.bugly.imsdk.proguard.w;
import com.tencent.bugly.imsdk.proguard.y;
import com.umeng.analytics.pro.b;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: BUGLY */
public class H5JavaScriptInterface {
    private static HashSet<Integer> a = new HashSet();
    private String b = null;
    private Thread c = null;
    private String d = null;
    private Map<String, String> e = null;

    private H5JavaScriptInterface() {
    }

    public static H5JavaScriptInterface getInstance(WebView webView) {
        String str = null;
        if (webView == null || a.contains(Integer.valueOf(webView.hashCode()))) {
            return null;
        }
        H5JavaScriptInterface h5JavaScriptInterface = new H5JavaScriptInterface();
        a.add(Integer.valueOf(webView.hashCode()));
        h5JavaScriptInterface.c = Thread.currentThread();
        Thread thread = h5JavaScriptInterface.c;
        if (thread != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 2; i < thread.getStackTrace().length; i++) {
                StackTraceElement stackTraceElement = thread.getStackTrace()[i];
                if (!stackTraceElement.toString().contains("crashreport")) {
                    stringBuilder.append(stackTraceElement.toString()).append("\n");
                }
            }
            str = stringBuilder.toString();
        }
        h5JavaScriptInterface.d = str;
        Map hashMap = new HashMap();
        hashMap.put("[WebView] ContentDescription", webView.getContentDescription());
        h5JavaScriptInterface.e = hashMap;
        return h5JavaScriptInterface;
    }

    private static a a(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            a aVar = new a();
            aVar.a = jSONObject.getString("projectRoot");
            if (aVar.a == null) {
                return null;
            }
            aVar.b = jSONObject.getString(b.M);
            if (aVar.b == null) {
                return null;
            }
            aVar.c = jSONObject.getString("url");
            if (aVar.c == null) {
                return null;
            }
            aVar.d = jSONObject.getString("userAgent");
            if (aVar.d == null) {
                return null;
            }
            aVar.e = jSONObject.getString("language");
            if (aVar.e == null) {
                return null;
            }
            aVar.f = jSONObject.getString("name");
            if (aVar.f == null || aVar.f.equals("null")) {
                return null;
            }
            String string = jSONObject.getString("stacktrace");
            if (string == null) {
                return null;
            }
            int indexOf = string.indexOf("\n");
            if (indexOf < 0) {
                w.d("H5 crash stack's format is wrong!", new Object[0]);
                return null;
            }
            aVar.h = string.substring(indexOf + 1);
            aVar.g = string.substring(0, indexOf);
            int indexOf2 = aVar.g.indexOf(":");
            if (indexOf2 > 0) {
                aVar.g = aVar.g.substring(indexOf2 + 1);
            }
            aVar.i = jSONObject.getString("file");
            if (aVar.f == null) {
                return null;
            }
            aVar.j = jSONObject.getLong("lineNumber");
            if (aVar.j < 0) {
                return null;
            }
            aVar.k = jSONObject.getLong("columnNumber");
            if (aVar.k < 0) {
                return null;
            }
            w.a("H5 crash information is following: ", new Object[0]);
            w.a("[projectRoot]: " + aVar.a, new Object[0]);
            w.a("[context]: " + aVar.b, new Object[0]);
            w.a("[url]: " + aVar.c, new Object[0]);
            w.a("[userAgent]: " + aVar.d, new Object[0]);
            w.a("[language]: " + aVar.e, new Object[0]);
            w.a("[name]: " + aVar.f, new Object[0]);
            w.a("[message]: " + aVar.g, new Object[0]);
            w.a("[stacktrace]: \n" + aVar.h, new Object[0]);
            w.a("[file]: " + aVar.i, new Object[0]);
            w.a("[lineNumber]: " + aVar.j, new Object[0]);
            w.a("[columnNumber]: " + aVar.k, new Object[0]);
            return aVar;
        } catch (Throwable th) {
            if (w.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    @JavascriptInterface
    public void printLog(String str) {
        w.d("Log from js: %s", str);
    }

    @JavascriptInterface
    public void reportJSException(String str) {
        if (str == null) {
            w.d("Payload from JS is null.", new Object[0]);
            return;
        }
        String b = y.b(str.getBytes());
        if (this.b == null || !this.b.equals(b)) {
            this.b = b;
            w.d("Handling JS exception ...", new Object[0]);
            a a = a(str);
            if (a == null) {
                w.d("Failed to parse payload.", new Object[0]);
                return;
            }
            Map linkedHashMap = new LinkedHashMap();
            Map linkedHashMap2 = new LinkedHashMap();
            if (a.a != null) {
                linkedHashMap2.put("[JS] projectRoot", a.a);
            }
            if (a.b != null) {
                linkedHashMap2.put("[JS] context", a.b);
            }
            if (a.c != null) {
                linkedHashMap2.put("[JS] url", a.c);
            }
            if (a.d != null) {
                linkedHashMap2.put("[JS] userAgent", a.d);
            }
            if (a.i != null) {
                linkedHashMap2.put("[JS] file", a.i);
            }
            if (a.j != 0) {
                linkedHashMap2.put("[JS] lineNumber", Long.toString(a.j));
            }
            linkedHashMap.putAll(linkedHashMap2);
            linkedHashMap.putAll(this.e);
            linkedHashMap.put("Java Stack", this.d);
            Thread thread = this.c;
            if (a != null) {
                InnerApi.postH5CrashAsync(thread, a.f, a.g, a.h, linkedHashMap);
                return;
            }
            return;
        }
        w.d("Same payload from js. Please check whether you've injected bugly.js more than one times.", new Object[0]);
    }
}
