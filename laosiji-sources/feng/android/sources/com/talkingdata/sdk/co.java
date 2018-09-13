package com.talkingdata.sdk;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.tauth.AuthActivity;
import com.umeng.message.MsgConstant;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/* compiled from: td */
public class co {
    private static ScheduledThreadPoolExecutor a = null;
    private static final long b = 30000;
    private static long c = 0;
    private static Map d = null;
    private static final long e = 2;
    private static volatile co f = null;
    private static boolean g = false;
    private static boolean h = false;
    private static boolean i = false;

    /* compiled from: td */
    static class a implements Runnable {
        private a() {
        }

        /* synthetic */ a(cp cpVar) {
            this();
        }

        public void run() {
            try {
                if (bo.b(ab.g, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                    co.d.put("isGetIMEI", Boolean.valueOf(true));
                    co.d.put("duration", Long.valueOf(System.currentTimeMillis() - co.c));
                    co.g();
                } else if (System.currentTimeMillis() - ar.d(a.TRACKING) >= 30000) {
                    co.d.put("isGetIMEI", Boolean.valueOf(false));
                    co.d.put("duration", Long.valueOf(System.currentTimeMillis() - co.c));
                    co.g();
                }
            } catch (Throwable th) {
            }
        }
    }

    /* compiled from: td */
    static class b implements ThreadFactory {
        private b() {
        }

        /* synthetic */ b(cp cpVar) {
            this();
        }

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "Check_Thread #");
        }
    }

    public final void onTDEBEventInitEvent(com.talkingdata.sdk.zz.a aVar) {
        try {
            if (Integer.parseInt(String.valueOf(aVar.paraMap.get("apiType"))) == 1) {
                String valueOf = String.valueOf(aVar.paraMap.get(AuthActivity.ACTION_KEY));
                a aVar2 = (a) aVar.paraMap.get("service");
                if (valueOf.equals("install") && aVar2.name().equals("TRACKING")) {
                    dd ddVar = new dd();
                    Object obj = aVar.paraMap.get("data");
                    ddVar.b = String.valueOf(aVar.paraMap.get("domain"));
                    ddVar.c = valueOf;
                    if (obj != null && (obj instanceof Map)) {
                        ddVar.d = (Map) obj;
                    }
                    ddVar.a = aVar2;
                    br.a().post(ddVar);
                } else if (valueOf.equals("init")) {
                    Context context = ab.g;
                    cv.a();
                    ci.a();
                    cn.a();
                    cj.a();
                    if (!c(context)) {
                    }
                    a(aVar2);
                    e();
                    ab.b = true;
                    if (aVar2.name().equals("TRACKING")) {
                        a(context, aVar2);
                    }
                    sendInitEventWithTDFeatures(aVar2);
                } else if (valueOf.equals("sendInit")) {
                    sendInitEventWithTDFeatures(aVar2);
                }
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private void a(Context context, a aVar) {
        if (!i) {
            try {
                String str = "TalkingData AppCpa SDK init...\n\tSDK_VERSION is: Android+TD+V4.0.21 gp Type:" + ab.c() + "  Build_Num:" + ab.t + "\n\tApp ID is: " + ab.a(context, aVar) + "\n\tApp Channel is: " + ab.b(context, aVar) + "\n\tSDK_OVC is: " + ak.e;
                if (ab.b || aq.a) {
                    Log.i(ab.s, str);
                }
                Object n = ar.n();
                if (!TextUtils.isEmpty(n)) {
                    dl.a().setDeepLink(n);
                }
                i = true;
            } catch (Throwable th) {
            }
        }
    }

    private static void e() {
        try {
            if (ar.e() == 0) {
                ar.setInitTime(System.currentTimeMillis());
            }
        } catch (Throwable th) {
        }
    }

    private static void a(a aVar) {
        if (aVar == null) {
            try {
                aq.eForInternal("TDFeatures is null...");
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        } else if (ar.d(aVar) == 0) {
            ar.b(System.currentTimeMillis(), aVar);
        } else if (System.currentTimeMillis() - ar.d(aVar) > 86400000) {
            bo.e = true;
        }
    }

    public static void sendInitEventWithTDFeatures(a aVar) {
        boolean z = true;
        if (aVar == null) {
            try {
                aq.eForInternal("TDFeatures is null...");
                return;
            } catch (Throwable th) {
                cs.postSDKError(th);
                return;
            }
        }
        Map treeMap = new TreeMap();
        if (System.currentTimeMillis() - ar.d(aVar) > 5000) {
            z = false;
        }
        treeMap.put("first", Boolean.valueOf(z));
        int b = b(ab.g);
        treeMap.put("targetAPI", Integer.valueOf(b));
        if (aVar.name().equals("TRACKING") && z) {
            boolean a = a(ab.g);
            boolean b2 = bo.b(ab.g, MsgConstant.PERMISSION_READ_PHONE_STATE);
            treeMap.put("isDeclareIMEI", Boolean.valueOf(a));
            treeMap.put("isGetIMEI", Boolean.valueOf(b2));
            if (a && !b2 && b >= 23) {
                d = new TreeMap();
                d.put("targetAPI", Integer.valueOf(b));
                d.put("isDeclareIMEI", Boolean.valueOf(a));
                f();
            }
        }
        dd ddVar = new dd();
        ddVar.b = PushConstants.EXTRA_APPLICATION_PENDING_INTENT;
        ddVar.c = "init";
        ddVar.d = treeMap;
        ddVar.a = aVar;
        br.a().post(ddVar);
        dc dcVar = new dc();
        dcVar.a = aVar;
        dcVar.b = com.talkingdata.sdk.dc.a.IMMEDIATELY;
        br.a().post(dcVar);
        if (z) {
            h();
        }
    }

    private static void f() {
        try {
            c = System.currentTimeMillis();
            a = new ScheduledThreadPoolExecutor(1, new b());
            a.scheduleAtFixedRate(new a(), 0, 2, TimeUnit.SECONDS);
        } catch (Throwable th) {
        }
    }

    private static boolean a(Context context) {
        if (context == null) {
            try {
                aq.eForInternal("[ModuleInit] current context is null...");
                return false;
            } catch (Throwable th) {
                return false;
            }
        }
        for (String equalsIgnoreCase : context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions) {
            if (equalsIgnoreCase.equalsIgnoreCase(MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                return true;
            }
        }
        return false;
    }

    private static int b(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            if (applicationInfo != null) {
                return applicationInfo.targetSdkVersion;
            }
            return -1;
        } catch (Throwable th) {
            return -1;
        }
    }

    private static void g() {
        try {
            dd ddVar = new dd();
            ddVar.b = PushConstants.EXTRA_APPLICATION_PENDING_INTENT;
            ddVar.c = "getIMEI";
            ddVar.d = d;
            ddVar.a = a.TRACKING;
            br.a().post(ddVar);
            dc dcVar = new dc();
            dcVar.a = a.TRACKING;
            dcVar.b = com.talkingdata.sdk.dc.a.IMMEDIATELY;
            br.a().post(dcVar);
            a.shutdown();
        } catch (Throwable th) {
        }
    }

    private static boolean c(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            String str = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).processName;
            List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses != null) {
                for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                    if (Process.myPid() == runningAppProcessInfo.pid && runningAppProcessInfo.processName.equals(str)) {
                        return true;
                    }
                }
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        return false;
    }

    private static void h() {
        try {
            dd ddVar = new dd();
            ddVar.b = "env";
            ddVar.c = "getProp";
            Map treeMap = new TreeMap();
            treeMap.put("sysproperty", bo.a());
            ddVar.d = treeMap;
            ddVar.a = a.ENV;
            br.a().post(ddVar);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public static co a() {
        if (f == null) {
            synchronized (co.class) {
                if (f == null) {
                    f = new co();
                }
            }
        }
        return f;
    }

    private co() {
    }

    static {
        try {
            br.a().register(a());
        } catch (Throwable th) {
        }
    }
}
