package com.umeng.commonsdk.framework;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.stub.StubApp;
import com.umeng.commonsdk.framework.UMLogDataProtocol.UMBusinessType;
import com.umeng.commonsdk.proguard.b;
import com.umeng.commonsdk.statistics.common.e;
import java.util.Iterator;
import org.json.JSONObject;

/* compiled from: UMWorkDispatchImpl */
public class d {
    private static HandlerThread a = null;
    private static Handler b = null;
    private static c c = null;
    private static Object d = new Object();
    private static final int e = 768;
    private static final int f = 769;
    private static final int g = 770;

    public static void a(Context context, int i, UMLogDataProtocol uMLogDataProtocol, Object obj) {
        if (context == null || uMLogDataProtocol == null) {
            e.b("--->>> Context or UMLogDataProtocol parameter cannot be null!");
            return;
        }
        UMModuleRegister.registerAppContext(StubApp.getOrigApplicationContext(context.getApplicationContext()));
        if (UMModuleRegister.registerCallback(i, uMLogDataProtocol)) {
            if (a == null || b == null) {
                e();
            }
            try {
                if (b != null) {
                    if (c == null) {
                        synchronized (d) {
                            b.f(context);
                            c = new c(context, b);
                        }
                    }
                    Message obtainMessage = b.obtainMessage();
                    obtainMessage.what = e;
                    obtainMessage.arg1 = i;
                    obtainMessage.obj = obj;
                    b.sendMessage(obtainMessage);
                }
            } catch (Throwable th) {
                b.a(UMModuleRegister.getAppContext(), th);
            }
        }
    }

    private d() {
    }

    private static void d() {
        e.b("--->>> autoProcess Enter...");
        Context appContext = UMModuleRegister.getAppContext();
        if (appContext != null) {
            long maxDataSpace = UMEnvelopeBuild.maxDataSpace(appContext);
            UMLogDataProtocol callbackFromModuleName = UMModuleRegister.getCallbackFromModuleName("analytics");
            try {
                JSONObject jSONObject;
                JSONObject jSONObject2;
                int i;
                if (!UMEnvelopeBuild.isReadyBuild(appContext, UMBusinessType.U_DPLUS) || callbackFromModuleName == null) {
                    i = 0;
                    jSONObject = null;
                } else {
                    jSONObject2 = callbackFromModuleName.setupReportData(maxDataSpace);
                    if (jSONObject2 != null) {
                        i = jSONObject2.toString().getBytes().length;
                        jSONObject = jSONObject2;
                    } else {
                        i = 0;
                        jSONObject = jSONObject2;
                    }
                }
                if (jSONObject != null) {
                    JSONObject jSONObject3;
                    jSONObject2 = new JSONObject();
                    jSONObject2.put("header", new JSONObject());
                    jSONObject2.put("content", new JSONObject());
                    if (jSONObject == null || i <= 0) {
                        jSONObject3 = jSONObject2;
                    } else {
                        jSONObject3 = a(a(jSONObject2, jSONObject.optJSONObject("header"), "header"), jSONObject.optJSONObject("content"), "content");
                    }
                    if (jSONObject3 != null && UMEnvelopeBuild.buildEnvelopeWithExtHeader(appContext, jSONObject3.optJSONObject("header"), jSONObject3.optJSONObject("content")) != null && jSONObject != null) {
                        callbackFromModuleName.removeCacheData(jSONObject);
                    }
                }
            } catch (Throwable th) {
                b.a(appContext, th);
            }
        }
    }

    private static JSONObject a(JSONObject jSONObject, JSONObject jSONObject2, String str) {
        Context appContext = UMModuleRegister.getAppContext();
        if (!(jSONObject == null || jSONObject2 == null)) {
            try {
                if (jSONObject.opt(str) != null && (jSONObject.opt(str) instanceof JSONObject)) {
                    JSONObject jSONObject3 = (JSONObject) jSONObject.opt(str);
                    Iterator keys = jSONObject2.keys();
                    while (keys.hasNext()) {
                        Object next = keys.next();
                        if (next != null && (next instanceof String)) {
                            String str2 = (String) next;
                            if (!(str2 == null || jSONObject2.opt(str2) == null)) {
                                jSONObject3.put(str2, jSONObject2.opt(str2));
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                b.a(appContext, e);
            } catch (Throwable th) {
                b.a(appContext, th);
            }
        }
        return jSONObject;
    }

    private static synchronized void e() {
        synchronized (d.class) {
            e.b("--->>> Dispatch: init Enter...");
            try {
                if (a == null) {
                    a = new HandlerThread("work_thread");
                    a.start();
                    if (b == null) {
                        b = new Handler(a.getLooper()) {
                            public void handleMessage(Message message) {
                                switch (message.what) {
                                    case d.e /*768*/:
                                        d.b(message);
                                        return;
                                    case d.f /*769*/:
                                        d.d();
                                        return;
                                    case d.g /*770*/:
                                        d.g();
                                        return;
                                    default:
                                        return;
                                }
                            }
                        };
                    }
                }
            } catch (Throwable th) {
                b.a(UMModuleRegister.getAppContext(), th);
            }
            e.b("--->>> Dispatch: init Exit...");
        }
        return;
    }

    public static synchronized boolean a(int i) {
        boolean z;
        synchronized (d.class) {
            if (b == null) {
                z = false;
            } else {
                z = b.hasMessages(i);
            }
        }
        return z;
    }

    private static void b(Message message) {
        int i = message.arg1;
        Object obj = message.obj;
        UMLogDataProtocol callbackFromModuleName = UMModuleRegister.getCallbackFromModuleName(UMModuleRegister.eventType2ModuleName(i));
        if (callbackFromModuleName != null) {
            e.b("--->>> dispatch:handleEvent: call back workEvent with msg type [ 0x" + Integer.toHexString(i) + "]");
            callbackFromModuleName.workEvent(obj, i);
        }
    }

    private static void f() {
        if (a != null) {
            a = null;
        }
        if (b != null) {
            b = null;
        }
        if (c != null) {
            c = null;
        }
    }

    private static void g() {
        if (c != null && a != null) {
            c.a();
            e.b("--->>> handleQuit: Quit dispatch thread.");
            a.quit();
            f();
        }
    }

    public static void a() {
        if (b != null) {
            Message obtainMessage = b.obtainMessage();
            obtainMessage.what = g;
            b.sendMessage(obtainMessage);
        }
    }
}
