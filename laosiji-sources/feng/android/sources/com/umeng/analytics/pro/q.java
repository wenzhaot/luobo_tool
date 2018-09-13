package com.umeng.analytics.pro;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.CoreProtocol;
import com.umeng.analytics.pro.g.a;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.framework.UMModuleRegister;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: ViewPageTracker */
public class q {
    private static final int b = 5;
    private static JSONArray c = new JSONArray();
    private static Object d = new Object();
    Stack<String> a = new Stack();
    private final Map<String, Long> e = new HashMap();

    public static void a(Context context) {
        if (context != null) {
            try {
                String jSONArray;
                JSONObject jSONObject = new JSONObject();
                synchronized (d) {
                    jSONArray = c.toString();
                    c = new JSONArray();
                }
                if (jSONArray.length() > 0) {
                    jSONObject.put("__a", new JSONArray(jSONArray));
                    if (jSONObject.length() > 0) {
                        g.a(context).a(p.a().d(), jSONObject, a.PAGE);
                    }
                }
            } catch (Throwable th) {
            }
        }
    }

    public void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (UMConfigure.isDebugLog() && this.a.size() != 0) {
                String str2 = (String) this.a.peek();
                String[] strArr = new String[]{"@"};
                String[] strArr2 = new String[]{str2};
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.H, 0, "\\|", strArr, strArr2, null, null);
            }
            synchronized (this.e) {
                this.e.put(str, Long.valueOf(System.currentTimeMillis()));
                if (UMConfigure.isDebugLog()) {
                    this.a.push(str);
                }
            }
        }
    }

    public void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] strArr;
            String[] strArr2;
            UMLog uMLog;
            if (this.e.containsKey(str)) {
                Long l;
                synchronized (this.e) {
                    l = (Long) this.e.get(str);
                }
                if (l != null) {
                    if (UMConfigure.isDebugLog() && this.a.size() > 0 && this.a.peek() == str) {
                        this.a.pop();
                    }
                    long currentTimeMillis = System.currentTimeMillis() - l.longValue();
                    synchronized (d) {
                        try {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put(b.u, str);
                            jSONObject.put("duration", currentTimeMillis);
                            c.put(jSONObject);
                            if (c.length() >= 5) {
                                Context appContext = UMModuleRegister.getAppContext();
                                if (appContext != null) {
                                    UMWorkDispatch.sendEvent(appContext, j.a.c, CoreProtocol.getInstance(appContext), null);
                                }
                            }
                        } catch (Throwable th) {
                        }
                    }
                    if (UMConfigure.isDebugLog() && this.a.size() != 0) {
                        strArr = new String[]{"@"};
                        strArr2 = new String[]{str};
                        uMLog = UMConfigure.umDebugLog;
                        UMLog.aq(h.G, 0, "\\|", strArr, strArr2, null, null);
                    }
                }
            } else if (UMConfigure.isDebugLog() && this.a.size() == 0) {
                strArr = new String[]{"@"};
                strArr2 = new String[]{str};
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.I, 0, "\\|", strArr, strArr2, null, null);
            }
        }
    }

    public void a() {
        String str = null;
        long j = 0;
        synchronized (this.e) {
            for (Entry entry : this.e.entrySet()) {
                String str2;
                long j2;
                if (((Long) entry.getValue()).longValue() > j) {
                    long longValue = ((Long) entry.getValue()).longValue();
                    str2 = (String) entry.getKey();
                    j2 = longValue;
                } else {
                    j2 = j;
                    str2 = str;
                }
                str = str2;
                j = j2;
            }
        }
        if (str != null) {
            b(str);
        }
    }
}
