package com.umeng.commonsdk.proguard;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild;
import com.umeng.commonsdk.stateless.a;
import com.umeng.commonsdk.stateless.f;
import com.umeng.commonsdk.statistics.common.e;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UMCrashManager */
public class b {
    private static boolean a = false;
    private static Object b = new Object();

    public static void a(final Context context, final Throwable th) {
        if (!a) {
            e.a("walle-crash", "report is " + a);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        synchronized (b.b) {
                            if (!(context == null || th == null || b.a)) {
                                b.a = true;
                                e.a("walle-crash", "report thread is " + b.a);
                                CharSequence a = c.a(th);
                                if (!TextUtils.isEmpty(a)) {
                                    f.a(context, context.getFilesDir() + "/" + a.e + "/" + Base64.encodeToString(com.umeng.commonsdk.internal.a.a.getBytes(), 0), 10);
                                    UMSLEnvelopeBuild uMSLEnvelopeBuild = new UMSLEnvelopeBuild();
                                    JSONObject buildSLBaseHeader = uMSLEnvelopeBuild.buildSLBaseHeader(context);
                                    try {
                                        JSONObject jSONObject = new JSONObject();
                                        jSONObject.put("content", a);
                                        jSONObject.put("ts", System.currentTimeMillis());
                                        JSONObject jSONObject2 = new JSONObject();
                                        jSONObject2.put("crash", jSONObject);
                                        jSONObject = new JSONObject();
                                        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_REQUEST_TYPE, jSONObject2);
                                        jSONObject2 = uMSLEnvelopeBuild.buildSLEnvelope(context, buildSLBaseHeader, jSONObject, com.umeng.commonsdk.internal.a.a);
                                        if (jSONObject2 == null || !jSONObject2.has(com.umeng.analytics.pro.b.ao)) {
                                        }
                                    } catch (JSONException e) {
                                    }
                                }
                            }
                        }
                    } catch (Throwable th) {
                    }
                }
            }).start();
        }
    }
}
