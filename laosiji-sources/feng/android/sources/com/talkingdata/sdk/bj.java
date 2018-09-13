package com.talkingdata.sdk;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Process;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* compiled from: td */
public class bj {
    private bj() {
        throw new AssertionError("no instances");
    }

    public static List a() {
        List arrayList = new ArrayList();
        try {
            for (File file : new File("/proc").listFiles()) {
                if (file != null && file.isDirectory()) {
                    try {
                        int parseInt = Integer.parseInt(file.getName());
                        try {
                            am amVar = new am(-1);
                            if (!((amVar.b >= 1000 && amVar.b <= 9999) || amVar.c.contains(":") || amVar.c.contains("/"))) {
                                arrayList.add(new am(parseInt));
                            }
                        } catch (Throwable th) {
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } catch (Throwable th2) {
        }
        return arrayList;
    }

    public static List a(Context context) {
        List arrayList = new ArrayList();
        try {
            File[] listFiles = new File("/proc").listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file != null && file.isDirectory()) {
                        try {
                            try {
                                am amVar = new am(Integer.parseInt(file.getName()));
                                if (amVar.a && !((amVar.b >= 1000 && amVar.b <= 9999) || amVar.c.contains(":") || amVar.c.contains("/"))) {
                                    arrayList.add(amVar);
                                }
                            } catch (Throwable th) {
                            }
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        } catch (Throwable th2) {
        }
        return arrayList;
    }

    public static boolean b() {
        try {
            List<am> a = a();
            int myPid = Process.myPid();
            for (am amVar : a) {
                if (amVar.d == myPid && amVar.a) {
                    return true;
                }
            }
        } catch (Throwable th) {
        }
        return false;
    }

    public static List b(Context context) {
        try {
            if (VERSION.SDK_INT < 22) {
                return ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
            }
            List<am> a = a();
            List arrayList = new ArrayList();
            for (am amVar : a) {
                RunningAppProcessInfo runningAppProcessInfo = new RunningAppProcessInfo(amVar.c, amVar.d, null);
                runningAppProcessInfo.uid = amVar.b;
                arrayList.add(runningAppProcessInfo);
            }
            return arrayList;
        } catch (Throwable th) {
            return null;
        }
    }

    public static String a(Context context, int i) {
        try {
            for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == i) {
                    return runningAppProcessInfo.processName;
                }
            }
        } catch (Throwable th) {
        }
        return null;
    }
}
