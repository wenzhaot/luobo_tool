package anet.channel.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import anet.channel.monitor.NetworkSpeed;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.status.NetworkStatusHelper.NetworkStatus;
import anetwork.channel.monitor.Monitor;
import com.ta.utdid2.device.UTDevice;
import java.lang.reflect.Method;

/* compiled from: Taobao */
public class Utils {
    private static final String TAG = "awcn.Utils";
    public static int accsVersion = 0;
    public static Context context = null;

    public static String getDeviceId(Context context) {
        return UTDevice.getUtdid(context);
    }

    public static boolean isDebugMode(Context context) {
        return false;
    }

    public static String getImsi(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
        } catch (Throwable th) {
            return null;
        }
    }

    public static String getOperator(Context context) {
        String imsi = getImsi(context);
        String str = "null";
        if (imsi == null || imsi.length() <= 5) {
            return str;
        }
        return imsi.substring(0, 5);
    }

    public static String getMainProcessName(Context context) {
        String str = "";
        if (context == null) {
            return str;
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.processName;
        } catch (NameNotFoundException e) {
            return str;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0038  */
    public static java.lang.String getProcessName(android.content.Context r7, int r8) {
        /*
        r6 = -108; // 0xffffffffffffff94 float:NaN double:NaN;
        r1 = "";
        r0 = "activity";
        r0 = r7.getSystemService(r0);	 Catch:{ Exception -> 0x0070 }
        r0 = (android.app.ActivityManager) r0;	 Catch:{ Exception -> 0x0070 }
        r0 = r0.getRunningAppProcesses();	 Catch:{ Exception -> 0x0070 }
        if (r0 == 0) goto L_0x003d;
    L_0x0014:
        r2 = r0.size();	 Catch:{ Exception -> 0x0070 }
        if (r2 <= 0) goto L_0x003d;
    L_0x001a:
        r2 = r0.iterator();	 Catch:{ Exception -> 0x0070 }
    L_0x001e:
        r0 = r2.hasNext();	 Catch:{ Exception -> 0x0070 }
        if (r0 == 0) goto L_0x006e;
    L_0x0024:
        r0 = r2.next();	 Catch:{ Exception -> 0x0070 }
        r0 = (android.app.ActivityManager.RunningAppProcessInfo) r0;	 Catch:{ Exception -> 0x0070 }
        r0 = (android.app.ActivityManager.RunningAppProcessInfo) r0;	 Catch:{ Exception -> 0x0070 }
        r3 = r0.pid;	 Catch:{ Exception -> 0x0070 }
        if (r3 != r8) goto L_0x001e;
    L_0x0030:
        r0 = r0.processName;	 Catch:{ Exception -> 0x0070 }
    L_0x0032:
        r1 = android.text.TextUtils.isEmpty(r0);
        if (r1 == 0) goto L_0x003c;
    L_0x0038:
        r0 = getProcessNameNew(r8);
    L_0x003c:
        return r0;
    L_0x003d:
        r0 = -108; // 0xffffffffffffff94 float:NaN double:NaN;
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0070 }
        r2.<init>();	 Catch:{ Exception -> 0x0070 }
        r3 = "BuildVersion=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0070 }
        r3 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x0070 }
        r3 = java.lang.String.valueOf(r3);	 Catch:{ Exception -> 0x0070 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0070 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0070 }
        r0 = anet.channel.util.ErrorConstant.formatMsg(r0, r2);	 Catch:{ Exception -> 0x0070 }
        r2 = anet.channel.appmonitor.AppMonitor.getInstance();	 Catch:{ Exception -> 0x0070 }
        r3 = new anet.channel.statist.ExceptionStatistic;	 Catch:{ Exception -> 0x0070 }
        r4 = -108; // 0xffffffffffffff94 float:NaN double:NaN;
        r5 = "rt";
        r3.<init>(r4, r0, r5);	 Catch:{ Exception -> 0x0070 }
        r2.commitStat(r3);	 Catch:{ Exception -> 0x0070 }
    L_0x006e:
        r0 = r1;
        goto L_0x0032;
    L_0x0070:
        r0 = move-exception;
        r2 = anet.channel.appmonitor.AppMonitor.getInstance();
        r3 = new anet.channel.statist.ExceptionStatistic;
        r0 = r0.toString();
        r4 = "rt";
        r3.<init>(r6, r0, r4);
        r2.commitStat(r3);
        goto L_0x006e;
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.util.Utils.getProcessName(android.content.Context, int):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x00da A:{SYNTHETIC, Splitter: B:36:0x00da} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00df A:{Catch:{ IOException -> 0x00e3 }} */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00da A:{SYNTHETIC, Splitter: B:36:0x00da} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00df A:{Catch:{ IOException -> 0x00e3 }} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00f5 A:{SYNTHETIC, Splitter: B:45:0x00f5} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00fa A:{Catch:{ IOException -> 0x00fe }} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00f5 A:{SYNTHETIC, Splitter: B:45:0x00f5} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00fa A:{Catch:{ IOException -> 0x00fe }} */
    private static java.lang.String getProcessNameNew(int r9) {
        /*
        r8 = 0;
        r2 = 0;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "ps  |  grep  ";
        r0 = r0.append(r1);
        r0 = r0.append(r9);
        r0 = r0.toString();
        r1 = java.lang.Runtime.getRuntime();	 Catch:{ Exception -> 0x00c8, all -> 0x00f0 }
        r3 = "sh";
        r4 = r1.exec(r3);	 Catch:{ Exception -> 0x00c8, all -> 0x00f0 }
        r3 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x00c8, all -> 0x00f0 }
        r1 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x00c8, all -> 0x00f0 }
        r5 = r4.getInputStream();	 Catch:{ Exception -> 0x00c8, all -> 0x00f0 }
        r1.<init>(r5);	 Catch:{ Exception -> 0x00c8, all -> 0x00f0 }
        r3.<init>(r1);	 Catch:{ Exception -> 0x00c8, all -> 0x00f0 }
        r1 = new java.io.DataOutputStream;	 Catch:{ Exception -> 0x0110, all -> 0x010b }
        r5 = r4.getOutputStream();	 Catch:{ Exception -> 0x0110, all -> 0x010b }
        r1.<init>(r5);	 Catch:{ Exception -> 0x0110, all -> 0x010b }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0113 }
        r5.<init>();	 Catch:{ Exception -> 0x0113 }
        r0 = r5.append(r0);	 Catch:{ Exception -> 0x0113 }
        r5 = "  &\n";
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x0113 }
        r0 = r0.toString();	 Catch:{ Exception -> 0x0113 }
        r1.writeBytes(r0);	 Catch:{ Exception -> 0x0113 }
        r1.flush();	 Catch:{ Exception -> 0x0113 }
        r0 = "exit\n";
        r1.writeBytes(r0);	 Catch:{ Exception -> 0x0113 }
        r4.waitFor();	 Catch:{ Exception -> 0x0113 }
    L_0x005b:
        r0 = r3.readLine();	 Catch:{ Exception -> 0x0113 }
        if (r0 == 0) goto L_0x00ad;
    L_0x0061:
        r4 = "\\s+";
        r5 = "  ";
        r0 = r0.replaceAll(r4, r5);	 Catch:{ Exception -> 0x0113 }
        r4 = "  ";
        r0 = r0.split(r4);	 Catch:{ Exception -> 0x0113 }
        r4 = r0.length;	 Catch:{ Exception -> 0x0113 }
        r5 = 9;
        if (r4 < r5) goto L_0x005b;
    L_0x0077:
        r4 = 1;
        r4 = r0[r4];	 Catch:{ Exception -> 0x0113 }
        r4 = android.text.TextUtils.isEmpty(r4);	 Catch:{ Exception -> 0x0113 }
        if (r4 != 0) goto L_0x005b;
    L_0x0080:
        r4 = 1;
        r4 = r0[r4];	 Catch:{ Exception -> 0x0113 }
        r4 = r4.trim();	 Catch:{ Exception -> 0x0113 }
        r5 = java.lang.String.valueOf(r9);	 Catch:{ Exception -> 0x0113 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x0113 }
        if (r4 == 0) goto L_0x005b;
    L_0x0091:
        r4 = 8;
        r0 = r0[r4];	 Catch:{ Exception -> 0x0113 }
        if (r3 == 0) goto L_0x009a;
    L_0x0097:
        r3.close();	 Catch:{ IOException -> 0x00a0 }
    L_0x009a:
        if (r1 == 0) goto L_0x009f;
    L_0x009c:
        r1.close();	 Catch:{ IOException -> 0x00a0 }
    L_0x009f:
        return r0;
    L_0x00a0:
        r1 = move-exception;
        r3 = "awcn.Utils";
        r4 = "getProcessNameNew ";
        r5 = new java.lang.Object[r8];
        anet.channel.util.ALog.e(r3, r4, r2, r1, r5);
        goto L_0x009f;
    L_0x00ad:
        if (r3 == 0) goto L_0x00b2;
    L_0x00af:
        r3.close();	 Catch:{ IOException -> 0x00bb }
    L_0x00b2:
        if (r1 == 0) goto L_0x00b7;
    L_0x00b4:
        r1.close();	 Catch:{ IOException -> 0x00bb }
    L_0x00b7:
        r0 = "";
        goto L_0x009f;
    L_0x00bb:
        r0 = move-exception;
        r1 = "awcn.Utils";
        r3 = "getProcessNameNew ";
        r4 = new java.lang.Object[r8];
        anet.channel.util.ALog.e(r1, r3, r2, r0, r4);
        goto L_0x00b7;
    L_0x00c8:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
    L_0x00cb:
        r4 = "awcn.Utils";
        r5 = "getProcessNameNew ";
        r6 = 0;
        r7 = 0;
        r7 = new java.lang.Object[r7];	 Catch:{ all -> 0x010e }
        anet.channel.util.ALog.e(r4, r5, r6, r0, r7);	 Catch:{ all -> 0x010e }
        if (r3 == 0) goto L_0x00dd;
    L_0x00da:
        r3.close();	 Catch:{ IOException -> 0x00e3 }
    L_0x00dd:
        if (r1 == 0) goto L_0x00b7;
    L_0x00df:
        r1.close();	 Catch:{ IOException -> 0x00e3 }
        goto L_0x00b7;
    L_0x00e3:
        r0 = move-exception;
        r1 = "awcn.Utils";
        r3 = "getProcessNameNew ";
        r4 = new java.lang.Object[r8];
        anet.channel.util.ALog.e(r1, r3, r2, r0, r4);
        goto L_0x00b7;
    L_0x00f0:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
    L_0x00f3:
        if (r3 == 0) goto L_0x00f8;
    L_0x00f5:
        r3.close();	 Catch:{ IOException -> 0x00fe }
    L_0x00f8:
        if (r1 == 0) goto L_0x00fd;
    L_0x00fa:
        r1.close();	 Catch:{ IOException -> 0x00fe }
    L_0x00fd:
        throw r0;
    L_0x00fe:
        r1 = move-exception;
        r3 = "awcn.Utils";
        r4 = "getProcessNameNew ";
        r5 = new java.lang.Object[r8];
        anet.channel.util.ALog.e(r3, r4, r2, r1, r5);
        goto L_0x00fd;
    L_0x010b:
        r0 = move-exception;
        r1 = r2;
        goto L_0x00f3;
    L_0x010e:
        r0 = move-exception;
        goto L_0x00f3;
    L_0x0110:
        r0 = move-exception;
        r1 = r2;
        goto L_0x00cb;
    L_0x0113:
        r0 = move-exception;
        goto L_0x00cb;
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.util.Utils.getProcessNameNew(int):java.lang.String");
    }

    public static String getAppVersion(Context context) {
        String str = "";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Throwable e) {
            ALog.w(TAG, "getAppVersion", null, e, new Object[0]);
            return str;
        }
    }

    public static Context getAppContext() {
        if (context != null) {
            return context;
        }
        synchronized (Utils.class) {
            Context context;
            if (context != null) {
                context = context;
                return context;
            }
            try {
                Class cls = Class.forName("android.app.ActivityThread");
                Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(cls, new Object[0]);
                context = (Context) invoke.getClass().getMethod("getApplication", new Class[0]).invoke(invoke, new Object[0]);
            } catch (Throwable e) {
                ALog.w(TAG, "getAppContext", null, e, new Object[0]);
            }
            context = context;
            return context;
        }
    }

    public static int getAccsVersion() {
        if (accsVersion != 0) {
            return accsVersion;
        }
        try {
            accsVersion = ((Integer) Utils.class.getClassLoader().loadClass("com.taobao.accs.ChannelService").getDeclaredField("SDK_VERSION_CODE").get(null)).intValue();
        } catch (Throwable e) {
            ALog.w(TAG, "getAccsVersion", null, e, new Object[0]);
        }
        return accsVersion;
    }

    public static String getStackMsg(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    stringBuffer.append(stackTraceElement.toString() + "\n");
                }
            }
        } catch (Throwable e) {
            ALog.e(TAG, "getStackMsg", null, e, new Object[0]);
        }
        return stringBuffer.toString();
    }

    public static Object invokeStaticMethodThrowException(String str, String str2, Class<?>[] clsArr, Object... objArr) throws Exception {
        if (str == null || str2 == null) {
            return null;
        }
        Method declaredMethod;
        Class cls = Class.forName(str);
        if (clsArr != null) {
            declaredMethod = cls.getDeclaredMethod(str2, clsArr);
        } else {
            declaredMethod = cls.getDeclaredMethod(str2, new Class[0]);
        }
        if (declaredMethod == null) {
            return null;
        }
        declaredMethod.setAccessible(true);
        if (objArr != null) {
            return declaredMethod.invoke(cls, objArr);
        }
        return declaredMethod.invoke(cls, new Object[0]);
    }

    public static float getNetworkTimeFactor() {
        float f = 1.0f;
        NetworkStatus a = NetworkStatusHelper.a();
        if (a == NetworkStatus.G4 || a == NetworkStatus.WIFI) {
            f = 1.0f * 0.8f;
        }
        if (Monitor.getNetSpeed() == NetworkSpeed.Fast) {
            return f * 0.75f;
        }
        return f;
    }
}
