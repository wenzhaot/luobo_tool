package com.umeng.commonsdk;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.stub.StubApp;
import com.umeng.commonsdk.framework.UMModuleRegister;
import com.umeng.commonsdk.framework.b;
import com.umeng.commonsdk.internal.d;
import com.umeng.commonsdk.internal.utils.c;
import com.umeng.commonsdk.internal.utils.j;
import com.umeng.commonsdk.internal.utils.l;
import com.umeng.commonsdk.proguard.e;
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild;
import com.umeng.commonsdk.stateless.a;
import com.umeng.commonsdk.stateless.f;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public class UMConfigureImpl {
    private static boolean a = false;
    private static boolean b = false;

    public static void init(Context context) {
        if (context != null) {
            Context origApplicationContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
            b(origApplicationContext);
            a(origApplicationContext);
        }
    }

    private static synchronized void a(final Context context) {
        synchronized (UMConfigureImpl.class) {
            if (context != null) {
                try {
                    if (!b) {
                        Object a = b.a(context);
                        CharSequence packageName = context.getPackageName();
                        if (!(TextUtils.isEmpty(a) || TextUtils.isEmpty(packageName) || !a.equals(packageName))) {
                            new Thread(new Runnable() {
                                public void run() {
                                    try {
                                        JSONArray b = e.b(context);
                                        if (b != null && b.length() > 0) {
                                            f.a(context, context.getFilesDir() + "/" + a.e + "/" + Base64.encodeToString(com.umeng.commonsdk.internal.a.n.getBytes(), 0), 10);
                                            JSONObject jSONObject = new JSONObject();
                                            jSONObject.put("lbs", b);
                                            JSONObject jSONObject2 = new JSONObject();
                                            jSONObject2.put(SocializeProtocolConstants.PROTOCOL_KEY_REQUEST_TYPE, jSONObject);
                                            UMSLEnvelopeBuild uMSLEnvelopeBuild = new UMSLEnvelopeBuild();
                                            uMSLEnvelopeBuild.buildSLEnvelope(context, uMSLEnvelopeBuild.buildSLBaseHeader(context), jSONObject2, com.umeng.commonsdk.internal.a.n);
                                        }
                                    } catch (Throwable e) {
                                        com.umeng.commonsdk.proguard.b.a(context, e);
                                    }
                                }
                            }).start();
                        }
                        b = true;
                    }
                } catch (Throwable th) {
                    com.umeng.commonsdk.proguard.b.a(context, th);
                }
            }
        }
        return;
    }

    private static synchronized void b(final Context context) {
        synchronized (UMConfigureImpl.class) {
            if (context != null) {
                try {
                    if (!a) {
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Object a = b.a(context);
                                    CharSequence packageName = context.getPackageName();
                                    if (!TextUtils.isEmpty(a) && !TextUtils.isEmpty(packageName) && a.equals(packageName)) {
                                        com.umeng.commonsdk.proguard.a.a(context);
                                        try {
                                            e.a(context);
                                        } catch (Throwable th) {
                                            com.umeng.commonsdk.statistics.common.e.c(UMModuleRegister.INNER, "e is " + th);
                                        }
                                        try {
                                            if (!c.a(context).a()) {
                                                c.a(context).b();
                                            }
                                        } catch (Throwable th2) {
                                            com.umeng.commonsdk.statistics.common.e.c(UMModuleRegister.INNER, "e is " + th2);
                                        }
                                        try {
                                            l.b(context);
                                        } catch (Throwable th22) {
                                            com.umeng.commonsdk.statistics.common.e.c(UMModuleRegister.INNER, "e is " + th22);
                                        }
                                        try {
                                            com.umeng.commonsdk.internal.utils.a.n(context);
                                        } catch (Throwable th222) {
                                            com.umeng.commonsdk.statistics.common.e.c(UMModuleRegister.INNER, "e is " + th222);
                                        }
                                        try {
                                            com.umeng.commonsdk.internal.utils.a.d(context);
                                        } catch (Throwable th2222) {
                                            com.umeng.commonsdk.statistics.common.e.c(UMModuleRegister.INNER, "e is " + th2222);
                                        }
                                        try {
                                            j.b(context);
                                        } catch (Throwable th22222) {
                                            com.umeng.commonsdk.statistics.common.e.c(UMModuleRegister.INNER, "e is " + th22222);
                                        }
                                        try {
                                            d.b(context);
                                        } catch (Throwable th222222) {
                                            com.umeng.commonsdk.statistics.common.e.c(UMModuleRegister.INNER, "e is " + th222222);
                                        }
                                        try {
                                            d.c(context);
                                        } catch (Throwable th3) {
                                        }
                                    }
                                } catch (Throwable th2222222) {
                                    com.umeng.commonsdk.proguard.b.a(context, th2222222);
                                }
                            }
                        }).start();
                        if (!com.umeng.commonsdk.internal.utils.b.a(context).a()) {
                            com.umeng.commonsdk.internal.utils.b.a(context).b();
                        }
                        a = true;
                    }
                } catch (Throwable th) {
                    com.umeng.commonsdk.statistics.common.e.c(UMModuleRegister.INNER, "e is " + th.getMessage());
                    com.umeng.commonsdk.proguard.b.a(context, th);
                }
            }
        }
        return;
    }
}
