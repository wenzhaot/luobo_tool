package com.umeng.commonsdk.stateless;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Base64;
import com.stub.StubApp;
import com.taobao.accs.utl.UtilityImpl;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.proguard.a;
import com.umeng.commonsdk.proguard.b;
import com.umeng.commonsdk.proguard.g;
import com.umeng.commonsdk.proguard.u;
import com.umeng.commonsdk.statistics.SdkVersion;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.e;
import com.umeng.commonsdk.utils.UMUtils;
import java.util.Iterator;
import org.json.JSONObject;

public class UMSLEnvelopeBuild {
    private static final String TAG = "UMSLEnvelopeBuild";
    private static String cacheSystemheader = null;
    private static boolean isEncryptEnabled;
    public static Context mContext;
    public static String module;

    public synchronized JSONObject buildSLBaseHeader(Context context) {
        JSONObject jSONObject = null;
        synchronized (this) {
            e.a("walle", "[stateless] begin build hader, thread is " + Thread.currentThread());
            if (context != null) {
                JSONObject jSONObject2;
                JSONObject jSONObject3;
                Context origApplicationContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
                try {
                    if (TextUtils.isEmpty(cacheSystemheader)) {
                        jSONObject2 = new JSONObject();
                        jSONObject2.put(g.o, DeviceConfig.getAppMD5Signature(origApplicationContext));
                        jSONObject2.put(g.p, DeviceConfig.getAppSHA1Key(origApplicationContext));
                        jSONObject2.put(g.q, DeviceConfig.getAppHashKey(origApplicationContext));
                        jSONObject2.put("app_version", DeviceConfig.getAppVersionName(origApplicationContext));
                        jSONObject2.put("version_code", Integer.parseInt(DeviceConfig.getAppVersionCode(origApplicationContext)));
                        jSONObject2.put("idmd5", DeviceConfig.getDeviceIdUmengMD5(origApplicationContext));
                        jSONObject2.put(g.v, DeviceConfig.getCPU());
                        CharSequence mccmnc = DeviceConfig.getMCCMNC(origApplicationContext);
                        if (TextUtils.isEmpty(mccmnc)) {
                            jSONObject2.put(g.A, "");
                        } else {
                            jSONObject2.put(g.A, mccmnc);
                        }
                        mccmnc = DeviceConfig.getSubOSName(origApplicationContext);
                        if (!TextUtils.isEmpty(mccmnc)) {
                            jSONObject2.put(g.J, mccmnc);
                        }
                        mccmnc = DeviceConfig.getSubOSVersion(origApplicationContext);
                        if (!TextUtils.isEmpty(mccmnc)) {
                            jSONObject2.put(g.K, mccmnc);
                        }
                        mccmnc = DeviceConfig.getDeviceType(origApplicationContext);
                        if (!TextUtils.isEmpty(mccmnc)) {
                            jSONObject2.put(g.af, mccmnc);
                        }
                        jSONObject2.put(g.n, DeviceConfig.getPackageName(origApplicationContext));
                        jSONObject2.put(g.t, "Android");
                        jSONObject2.put("device_id", DeviceConfig.getDeviceId(origApplicationContext));
                        jSONObject2.put("device_model", Build.MODEL);
                        jSONObject2.put(g.D, Build.BOARD);
                        jSONObject2.put(g.E, Build.BRAND);
                        jSONObject2.put(g.F, Build.TIME);
                        jSONObject2.put(g.G, Build.MANUFACTURER);
                        jSONObject2.put(g.H, Build.ID);
                        jSONObject2.put(g.I, Build.DEVICE);
                        jSONObject2.put("os", "Android");
                        jSONObject2.put("os_version", VERSION.RELEASE);
                        int[] resolutionArray = DeviceConfig.getResolutionArray(origApplicationContext);
                        if (resolutionArray != null) {
                            jSONObject2.put("resolution", resolutionArray[1] + "*" + resolutionArray[0]);
                        }
                        jSONObject2.put("mc", DeviceConfig.getMac(origApplicationContext));
                        jSONObject2.put(g.L, DeviceConfig.getTimeZone(origApplicationContext));
                        String[] localeInfo = DeviceConfig.getLocaleInfo(origApplicationContext);
                        jSONObject2.put(g.N, localeInfo[0]);
                        jSONObject2.put("language", localeInfo[1]);
                        jSONObject2.put(g.O, DeviceConfig.getNetworkOperatorName(origApplicationContext));
                        jSONObject2.put("display_name", DeviceConfig.getAppName(origApplicationContext));
                        localeInfo = DeviceConfig.getNetworkAccessMode(origApplicationContext);
                        if ("Wi-Fi".equals(localeInfo[0])) {
                            jSONObject2.put(g.P, UtilityImpl.NET_TYPE_WIFI);
                        } else if ("2G/3G".equals(localeInfo[0])) {
                            jSONObject2.put(g.P, "2G/3G");
                        } else {
                            jSONObject2.put(g.P, "unknow");
                        }
                        if (!"".equals(localeInfo[1])) {
                            jSONObject2.put(g.Q, localeInfo[1]);
                        }
                        jSONObject2.put(g.b, SdkVersion.SDK_VERSION);
                        jSONObject2.put(g.c, SdkVersion.SDK_TYPE);
                        if (!TextUtils.isEmpty(module)) {
                            jSONObject2.put(g.d, module);
                        }
                        cacheSystemheader = jSONObject2.toString();
                        jSONObject3 = jSONObject2;
                    } else {
                        jSONObject3 = new JSONObject(cacheSystemheader);
                    }
                } catch (Exception e) {
                    jSONObject3 = null;
                } catch (Throwable th) {
                    b.a(origApplicationContext, th);
                }
                if (jSONObject3 != null) {
                    CharSequence charSequence;
                    jSONObject3.put("channel", UMUtils.getChannel(origApplicationContext));
                    jSONObject3.put("appkey", UMUtils.getAppkey(origApplicationContext));
                    try {
                        if (SdkVersion.SDK_TYPE != 1) {
                            try {
                                Class cls = Class.forName("com.umeng.commonsdk.internal.utils.SDStorageAgent");
                                if (cls != null) {
                                    charSequence = (String) cls.getMethod("getUmtt", new Class[]{Context.class}).invoke(cls, new Object[]{origApplicationContext});
                                } else {
                                    charSequence = null;
                                }
                            } catch (ClassNotFoundException e2) {
                                charSequence = null;
                            } catch (Throwable th2) {
                                charSequence = null;
                            }
                            if (!TextUtils.isEmpty(charSequence)) {
                                jSONObject3.put(g.e, charSequence);
                            }
                        }
                    } catch (Exception e3) {
                    }
                    try {
                        charSequence = UMEnvelopeBuild.imprintProperty(origApplicationContext, "umid", null);
                        if (!TextUtils.isEmpty(charSequence)) {
                            jSONObject3.put("umid", charSequence);
                        }
                    } catch (Exception e4) {
                    }
                    try {
                        if (!(SdkVersion.SDK_TYPE == 1 || a.b(origApplicationContext) == null)) {
                            jSONObject3.put(g.g, a.b(origApplicationContext));
                        }
                    } catch (Exception e5) {
                    }
                    try {
                        jSONObject3.put("wrapper_type", a.a);
                        jSONObject3.put("wrapper_version", a.b);
                    } catch (Exception e6) {
                    }
                    if (jSONObject3 != null) {
                        if (jSONObject3.length() > 0) {
                            jSONObject2 = new JSONObject();
                            e.a("walle", "[stateless] build header end , header is " + jSONObject3.toString() + ", thread is " + Thread.currentThread());
                            jSONObject = jSONObject2.put("header", jSONObject3);
                        }
                    }
                    e.a("walle", "[stateless] build header end , header is null !!! thread is " + Thread.currentThread());
                }
            }
        }
        return jSONObject;
    }

    private synchronized JSONObject makeErrorResult(int i, JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                jSONObject.put(com.umeng.analytics.pro.b.ao, i);
            } catch (Exception e) {
            }
        } else {
            jSONObject = new JSONObject();
            try {
                jSONObject.put(com.umeng.analytics.pro.b.ao, i);
            } catch (Exception e2) {
            }
        }
        return jSONObject;
    }

    public synchronized JSONObject buildSLEnvelope(Context context, JSONObject jSONObject, JSONObject jSONObject2, String str) {
        e.a("walle", "[stateless] build envelope, heade is " + jSONObject.toString());
        e.a("walle", "[stateless] build envelope, body is " + jSONObject2.toString());
        e.a("walle", "[stateless] build envelope, thread is " + Thread.currentThread());
        if (context == null || jSONObject == null || jSONObject2 == null || str == null) {
            e.a("walle", "[stateless] build envelope, context is null or header is null or body is null");
            jSONObject = makeErrorResult(110, null);
        } else {
            try {
                c constructEnvelope;
                context = StubApp.getOrigApplicationContext(context.getApplicationContext());
                if (!(jSONObject == null || jSONObject2 == null)) {
                    Iterator keys = jSONObject2.keys();
                    while (keys.hasNext()) {
                        Object next = keys.next();
                        if (next != null && (next instanceof String)) {
                            String str2 = (String) next;
                            if (!(str2 == null || jSONObject2.opt(str2) == null)) {
                                try {
                                    jSONObject.put(str2, jSONObject2.opt(str2));
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
                if (jSONObject != null) {
                    try {
                        com.umeng.commonsdk.statistics.idtracking.e a = com.umeng.commonsdk.statistics.idtracking.e.a(context);
                        if (a != null) {
                            a.a();
                            CharSequence encodeToString = Base64.encodeToString(new u().a(a.b()), 0);
                            if (!TextUtils.isEmpty(encodeToString)) {
                                JSONObject jSONObject3 = jSONObject.getJSONObject("header");
                                jSONObject3.put(g.V, encodeToString);
                                jSONObject.put("header", jSONObject3);
                            }
                        }
                    } catch (Exception e2) {
                    }
                }
                if (jSONObject != null) {
                    if (f.a((long) jSONObject.toString().getBytes().length, a.c)) {
                        e.a("walle", "[stateless] build envelope, json overstep!!!! size is " + jSONObject.toString().getBytes().length);
                        jSONObject = makeErrorResult(113, jSONObject);
                    }
                }
                e.a("walle", "[stateless] build envelope, json size is " + jSONObject.toString().getBytes().length);
                if (jSONObject != null) {
                    constructEnvelope = constructEnvelope(context, jSONObject.toString().getBytes());
                    if (constructEnvelope == null) {
                        e.a("walle", "[stateless] build envelope, envelope is null !!!!");
                        jSONObject = makeErrorResult(111, jSONObject);
                    }
                } else {
                    constructEnvelope = null;
                }
                if (constructEnvelope != null && f.a((long) constructEnvelope.b().length, a.d)) {
                    e.a("walle", "[stateless] build envelope, envelope overstep!!!! size is " + constructEnvelope.b().length);
                    jSONObject = makeErrorResult(114, jSONObject);
                } else if (f.a(context, Base64.encodeToString(str.getBytes(), 0), Base64.encodeToString((str + "_" + System.currentTimeMillis()).getBytes(), 0), constructEnvelope.b())) {
                    e.a("walle", "[stateless] build envelope, save ok ----->>>>>");
                    e.a("walle", "[stateless] envelope file size is " + jSONObject.toString().getBytes().length);
                    d dVar = new d(context);
                    d.b(d.a);
                    e.a("walle", "[stateless] build envelope end, thread is " + Thread.currentThread());
                } else {
                    e.a("walle", "[stateless] build envelope, save fail ----->>>>>");
                    jSONObject = makeErrorResult(101, jSONObject);
                }
            } catch (Throwable th) {
                b.a(context, th);
                e.a("walle", "build envelope end, thread is " + Thread.currentThread());
                jSONObject = makeErrorResult(110, null);
            }
        }
        return jSONObject;
    }

    private synchronized c constructEnvelope(Context context, byte[] bArr) {
        c a;
        int i = -1;
        Object imprintProperty = UMEnvelopeBuild.imprintProperty(context, "slcodex", null);
        e.a("walle", "[stateless] build envelope, codexStr is " + imprintProperty);
        try {
            if (!TextUtils.isEmpty(imprintProperty)) {
                i = Integer.valueOf(imprintProperty).intValue();
            }
        } catch (Throwable e) {
            b.a(context, e);
        }
        if (i == 0) {
            e.a("walle", "[stateless] build envelope, codexValue is 0");
            a = c.a(context, UMUtils.getAppkey(context), bArr);
        } else if (i == 1) {
            e.a("walle", "[stateless] build envelope, codexValue is 1");
            a = c.b(context, UMUtils.getAppkey(context), bArr);
        } else if (isEncryptEnabled) {
            e.a("walle", "[stateless] build envelope, isEncryptEnabled is true");
            a = c.b(context, UMUtils.getAppkey(context), bArr);
        } else {
            e.a("walle", "[stateless] build envelope, isEncryptEnabled is false");
            a = c.a(context, UMUtils.getAppkey(context), bArr);
        }
        return a;
    }

    public static void setEncryptEnabled(boolean z) {
        isEncryptEnabled = z;
    }
}
