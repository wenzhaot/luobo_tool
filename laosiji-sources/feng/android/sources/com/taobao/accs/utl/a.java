package com.taobao.accs.utl;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import com.stub.StubApp;
import com.ta.utdid2.device.UTDevice;
import java.io.File;

/* compiled from: Taobao */
public class a {
    public static String a = "";
    public static String b = null;
    private static String c = "";
    public static final String channelService = "com.taobao.accs.ChannelService";
    private static String d = "";
    private static boolean e = true;
    private static boolean f = false;
    public static final String msgService = "com.taobao.accs.data.MsgDistributeService";

    public static boolean a(Context context) {
        if (f) {
            return e;
        }
        try {
            Object obj;
            if (TextUtils.isEmpty(com.taobao.accs.client.a.d)) {
                if (TextUtils.isEmpty(c)) {
                    c = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.processName;
                }
                obj = c;
            } else {
                obj = com.taobao.accs.client.a.d;
            }
            if (TextUtils.isEmpty(d)) {
                d = a(context, Process.myPid());
            }
            if (!(TextUtils.isEmpty(obj) || TextUtils.isEmpty(d))) {
                e = obj.equalsIgnoreCase(d);
                f = true;
            }
        } catch (Throwable th) {
            ALog.e("AdapterUtilityImpl", "isMainProcess", th, new Object[0]);
        }
        return e;
    }

    public static String a(Context context, int i) {
        if (com.taobao.accs.client.a.f != null) {
            return com.taobao.accs.client.a.f.getCurrProcessName();
        }
        String str = "";
        for (RunningAppProcessInfo runningAppProcessInfo : com.taobao.accs.client.a.a(context).a().getRunningAppProcesses()) {
            String str2;
            try {
                if (runningAppProcessInfo.pid == i) {
                    str2 = runningAppProcessInfo.processName;
                    str = str2;
                }
            } catch (Exception e) {
            }
            str2 = str;
            str = str2;
        }
        return str;
    }

    public static long a() {
        try {
            File dataDirectory = Environment.getDataDirectory();
            if (dataDirectory == null) {
                return -1;
            }
            if (VERSION.SDK_INT >= 9) {
                return dataDirectory.getUsableSpace();
            }
            if (!dataDirectory.exists()) {
                return -1;
            }
            StatFs statFs = new StatFs(dataDirectory.getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            ALog.e("AdapterUtilityImpl", "getUsableSpace", th, new Object[0]);
            return -1;
        }
    }

    public static String a(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    stringBuffer.append(stackTraceElement.toString() + "\n");
                }
            }
        } catch (Exception e) {
        }
        return stringBuffer.toString();
    }

    public static String b(Context context) {
        return UTDevice.getUtdid(context);
    }

    public static boolean c(Context context) {
        if (context != null) {
            try {
                NetworkInfo activeNetworkInfo = com.taobao.accs.client.a.a(context).b().getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    return activeNetworkInfo.isConnected();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static final boolean a(String str, int i) {
        if (str == null) {
            return false;
        }
        StatFs statFs = new StatFs(str);
        int blockSize = statFs.getBlockSize();
        long availableBlocks = (long) statFs.getAvailableBlocks();
        Log.d("FileCheckUtils", "st.getAvailableBlocks()=" + statFs.getAvailableBlocks() + ",st.getAvailableBlocks() * blockSize=" + (((long) statFs.getAvailableBlocks()) * ((long) blockSize)));
        if (statFs.getAvailableBlocks() <= 10 || ((long) blockSize) * availableBlocks <= ((long) i)) {
            return false;
        }
        return true;
    }

    public static String d(Context context) {
        String str = "unknown";
        try {
            boolean z;
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            String packageName = StubApp.getOrigApplicationContext(context.getApplicationContext()).getPackageName();
            int i = applicationInfo.uid;
            if (((Integer) Class.forName(AppOpsManager.class.getName()).getMethod("checkOpNoThrow", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(appOpsManager, new Object[]{Integer.valueOf(((Integer) r1.getDeclaredField("OP_POST_NOTIFICATION").get(appOpsManager)).intValue()), Integer.valueOf(i), packageName})).intValue() == 0) {
                z = true;
            } else {
                z = false;
            }
            return String.valueOf(z);
        } catch (Throwable th) {
            ALog.e("AdapterUtilityImpl", "isNotificationEnabled", th, new Object[0]);
            return str;
        }
    }
}
