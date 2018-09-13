package com.umeng.commonsdk.framework;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Process;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.umeng.commonsdk.framework.UMLogDataProtocol.UMBusinessType;
import com.umeng.commonsdk.proguard.g;
import com.umeng.commonsdk.statistics.common.e;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.message.MsgConstant;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/* compiled from: UMFrUtils */
public class b {
    private static final String a = "last_successful_build_time";
    private static Object b = new Object();
    private static String c = "envelope";
    private static String d = null;
    private static Object e = new Object();

    public static String a(Context context) {
        String str = "";
        try {
            int myPid = Process.myPid();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            if (activityManager != null) {
                List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
                if (runningAppProcesses != null && runningAppProcesses.size() > 0) {
                    for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                        if (runningAppProcessInfo.pid == myPid) {
                            return runningAppProcessInfo.processName;
                        }
                    }
                }
            }
        } catch (Throwable th) {
            com.umeng.commonsdk.proguard.b.a(StubApp.getOrigApplicationContext(context.getApplicationContext()), th);
        }
        return str;
    }

    private static boolean a(Context context, String str) {
        boolean z = true;
        if (context == null) {
            return false;
        }
        Context origApplicationContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
        if (VERSION.SDK_INT >= 23) {
            try {
                boolean z2;
                if (((Integer) Class.forName("android.content.Context").getMethod("checkSelfPermission", new Class[]{String.class}).invoke(context, new Object[]{str})).intValue() == 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                return z2;
            } catch (Throwable th) {
                com.umeng.commonsdk.proguard.b.a(origApplicationContext, th);
                return false;
            }
        }
        try {
            if (origApplicationContext.getPackageManager().checkPermission(str, origApplicationContext.getPackageName()) != 0) {
                z = false;
            }
            return z;
        } catch (Throwable th2) {
            com.umeng.commonsdk.proguard.b.a(origApplicationContext, th2);
            return false;
        }
    }

    public static boolean b(Context context) {
        try {
            if (a(context, MsgConstant.PERMISSION_ACCESS_NETWORK_STATE)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null) {
                        return activeNetworkInfo.isConnectedOrConnecting();
                    }
                }
            }
        } catch (Throwable th) {
            com.umeng.commonsdk.proguard.b.a(StubApp.getOrigApplicationContext(context.getApplicationContext()), th);
        }
        return false;
    }

    public static int c(Context context) {
        if (context == null) {
            return 0;
        }
        try {
            File file = new File(h(context));
            synchronized (e) {
                if (file.isDirectory()) {
                    String[] list = file.list();
                    if (list != null) {
                        int length = list.length;
                        return length;
                    }
                }
            }
        } catch (Throwable th) {
            com.umeng.commonsdk.proguard.b.a(context, th);
        }
        return 0;
    }

    private static long a(long j, long j2) {
        long j3;
        if (j < j2) {
            j3 = j2 - j;
        } else {
            j3 = j - j2;
        }
        return j3 / 86400000;
    }

    /* JADX WARNING: Missing block: B:28:?, code:
            return;
     */
    public static void d(final android.content.Context r5) {
        /*
        r3 = 100;
        r0 = new java.io.File;
        r1 = h(r5);
        r0.<init>(r1);
        r1 = e;
        monitor-enter(r1);
        r2 = r0.listFiles();	 Catch:{ all -> 0x005f }
        if (r2 == 0) goto L_0x0017;
    L_0x0014:
        r0 = r2.length;	 Catch:{ all -> 0x005f }
        if (r0 > r3) goto L_0x0019;
    L_0x0017:
        monitor-exit(r1);	 Catch:{ all -> 0x005f }
    L_0x0018:
        return;
    L_0x0019:
        r0 = new com.umeng.commonsdk.framework.b$1;	 Catch:{ all -> 0x005f }
        r0.<init>(r5);	 Catch:{ all -> 0x005f }
        java.util.Arrays.sort(r2, r0);	 Catch:{ all -> 0x005f }
        r0 = r2.length;	 Catch:{ all -> 0x005f }
        if (r0 <= r3) goto L_0x005d;
    L_0x0024:
        r0 = "--->>> biger than 10";
        com.umeng.commonsdk.statistics.common.e.b(r0);	 Catch:{ Throwable -> 0x0059 }
        r0 = 0;
    L_0x002b:
        r3 = r2.length;	 Catch:{ Throwable -> 0x0059 }
        r3 = r3 + -100;
        if (r0 >= r3) goto L_0x005d;
    L_0x0030:
        r3 = r2[r0];	 Catch:{ Throwable -> 0x0059 }
        r3 = r3.delete();	 Catch:{ Throwable -> 0x0059 }
        if (r3 != 0) goto L_0x0056;
    L_0x0038:
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0059 }
        r3.<init>();	 Catch:{ Throwable -> 0x0059 }
        r4 = "--->>> remove [";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x0059 }
        r3 = r3.append(r0);	 Catch:{ Throwable -> 0x0059 }
        r4 = "] file fail.";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x0059 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x0059 }
        com.umeng.commonsdk.statistics.common.e.b(r3);	 Catch:{ Throwable -> 0x0059 }
    L_0x0056:
        r0 = r0 + 1;
        goto L_0x002b;
    L_0x0059:
        r0 = move-exception;
        com.umeng.commonsdk.proguard.b.a(r5, r0);	 Catch:{ all -> 0x005f }
    L_0x005d:
        monitor-exit(r1);	 Catch:{ all -> 0x005f }
        goto L_0x0018;
    L_0x005f:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x005f }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.framework.b.d(android.content.Context):void");
    }

    private static String c(String str) {
        Context appContext = UMModuleRegister.getAppContext();
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int indexOf = str.indexOf(95);
        try {
            return str.substring(indexOf + 1, str.indexOf(95, indexOf + 1));
        } catch (Throwable e) {
            com.umeng.commonsdk.proguard.b.a(appContext, e);
            return "";
        }
    }

    public static File e(final Context context) {
        File file = null;
        if (context != null) {
            File file2 = new File(h(context));
            synchronized (e) {
                File[] listFiles = file2.listFiles();
                if (listFiles == null || listFiles.length == 0) {
                } else {
                    Arrays.sort(listFiles, new Comparator<File>() {
                        /* renamed from: a */
                        public int compare(File file, File file2) {
                            String name = file.getName();
                            String name2 = file2.getName();
                            Object b = b.c(name);
                            Object b2 = b.c(name2);
                            if (TextUtils.isEmpty(b) || TextUtils.isEmpty(b2)) {
                                return 1;
                            }
                            try {
                                long longValue = Long.valueOf(b).longValue() - Long.valueOf(b2).longValue();
                                if (longValue > 0) {
                                    return 1;
                                }
                                if (longValue == 0) {
                                    return 0;
                                }
                                return -1;
                            } catch (Throwable e) {
                                com.umeng.commonsdk.proguard.b.a(context, e);
                                return 1;
                            }
                        }
                    });
                    file = listFiles[0];
                }
            }
        }
        return file;
    }

    public static void f(Context context) {
        if (context != null) {
            try {
                String g = g(context);
                if (!TextUtils.isEmpty(g) && !g.equals(c)) {
                    File file = new File(context.getFilesDir().getAbsolutePath() + "/." + g);
                    if (file.exists()) {
                        File[] listFiles = file.listFiles();
                        if (listFiles != null && listFiles.length != 0) {
                            try {
                                String h = h(context);
                                for (int i = 0; i < listFiles.length; i++) {
                                    listFiles[i].renameTo(new File(h + "/" + listFiles[i].getName()));
                                }
                                if (file.isDirectory()) {
                                    file.delete();
                                }
                            } catch (Throwable th) {
                                com.umeng.commonsdk.proguard.b.a(context, th);
                            }
                        } else if (file.isDirectory()) {
                            file.delete();
                        }
                    }
                }
            } catch (Throwable th2) {
                com.umeng.commonsdk.proguard.b.a(context, th2);
            }
        }
    }

    public static String g(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        if (activityManager != null) {
            List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses == null) {
                e.b("--->>> getEnvelopeDir: can't get process name, use default envelope directory.");
                return c;
            } else if (runningAppProcesses.size() == 0) {
                return c;
            } else {
                try {
                    for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                        if (runningAppProcessInfo.pid == Process.myPid()) {
                            String replace = runningAppProcessInfo.processName.replace(':', '_');
                            e.b("--->>> getEnvelopeDir: use current process name as envelope directory.");
                            return replace;
                        }
                    }
                } catch (Throwable th) {
                    com.umeng.commonsdk.proguard.b.a(context, th);
                }
            }
        }
        return c;
    }

    public static String h(Context context) {
        String str;
        synchronized (e) {
            try {
                if (d == null) {
                    d = context.getFilesDir().getAbsolutePath() + "/." + c;
                }
                File file = new File(d);
                if (!(file.exists() || file.mkdir())) {
                    e.b("--->>> Create Envelope Directory failed!!!");
                }
            } catch (Throwable th) {
                com.umeng.commonsdk.proguard.b.a(context, th);
            }
            str = d;
        }
        return str;
    }

    public static long i(Context context) {
        long j;
        synchronized (b) {
            j = PreferenceWrapper.getDefault(context).getLong(a, 0);
        }
        return j;
    }

    private static void j(Context context) {
        synchronized (b) {
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            sharedPreferences.edit().putLong(a, System.currentTimeMillis()).commit();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0068 A:{SYNTHETIC, Splitter: B:39:0x0068} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005a A:{SYNTHETIC, Splitter: B:30:0x005a} */
    public static int a(android.content.Context r5, java.lang.String r6, byte[] r7) {
        /*
        r3 = 0;
        r0 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r7 != 0) goto L_0x0006;
    L_0x0005:
        return r0;
    L_0x0006:
        r1 = new java.io.File;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r4 = h(r5);
        r2 = r2.append(r4);
        r4 = "/";
        r2 = r2.append(r4);
        r2 = r2.append(r6);
        r2 = r2.toString();
        r1.<init>(r2);
        r4 = e;
        monitor-enter(r4);
        r2 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0053, all -> 0x0064 }
        r2.<init>(r1);	 Catch:{ IOException -> 0x0053, all -> 0x0064 }
        r2.write(r7);	 Catch:{ IOException -> 0x0073 }
        r2.close();	 Catch:{ IOException -> 0x0073 }
        r0 = 0;
        if (r3 == 0) goto L_0x003b;
    L_0x0038:
        r0.close();	 Catch:{ Throwable -> 0x004e }
    L_0x003b:
        r0 = com.umeng.commonsdk.statistics.internal.a.a(r5);	 Catch:{ all -> 0x004b }
        r0 = r0.a(r6);	 Catch:{ all -> 0x004b }
        if (r0 == 0) goto L_0x0048;
    L_0x0045:
        j(r5);	 Catch:{ all -> 0x004b }
    L_0x0048:
        r0 = 0;
        monitor-exit(r4);	 Catch:{ all -> 0x004b }
        goto L_0x0005;
    L_0x004b:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x004b }
        throw r0;
    L_0x004e:
        r0 = move-exception;
        com.umeng.commonsdk.proguard.b.a(r5, r0);	 Catch:{ all -> 0x004b }
        goto L_0x003b;
    L_0x0053:
        r1 = move-exception;
        r2 = r3;
    L_0x0055:
        com.umeng.commonsdk.proguard.b.a(r5, r1);	 Catch:{ all -> 0x0071 }
        if (r2 == 0) goto L_0x005d;
    L_0x005a:
        r2.close();	 Catch:{ Throwable -> 0x005f }
    L_0x005d:
        monitor-exit(r4);	 Catch:{ all -> 0x004b }
        goto L_0x0005;
    L_0x005f:
        r1 = move-exception;
        com.umeng.commonsdk.proguard.b.a(r5, r1);	 Catch:{ all -> 0x004b }
        goto L_0x005d;
    L_0x0064:
        r0 = move-exception;
        r2 = r3;
    L_0x0066:
        if (r2 == 0) goto L_0x006b;
    L_0x0068:
        r2.close();	 Catch:{ Throwable -> 0x006c }
    L_0x006b:
        throw r0;	 Catch:{ all -> 0x004b }
    L_0x006c:
        r1 = move-exception;
        com.umeng.commonsdk.proguard.b.a(r5, r1);	 Catch:{ all -> 0x004b }
        goto L_0x006b;
    L_0x0071:
        r0 = move-exception;
        goto L_0x0066;
    L_0x0073:
        r1 = move-exception;
        goto L_0x0055;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.framework.b.a(android.content.Context, java.lang.String, byte[]):int");
    }

    public static boolean a(File file) {
        boolean delete;
        Context appContext = UMModuleRegister.getAppContext();
        synchronized (e) {
            if (file != null) {
                try {
                    if (file.exists()) {
                        delete = file.delete();
                    }
                } catch (Throwable th) {
                    com.umeng.commonsdk.proguard.b.a(appContext, th);
                }
            }
            delete = true;
        }
        return delete;
    }

    public static byte[] a(String str) throws IOException {
        byte[] bArr;
        Throwable th;
        Throwable th2;
        Context appContext = UMModuleRegister.getAppContext();
        FileChannel fileChannel = null;
        synchronized (e) {
            try {
                FileChannel channel = new RandomAccessFile(str, "r").getChannel();
                try {
                    MappedByteBuffer load = channel.map(MapMode.READ_ONLY, 0, channel.size()).load();
                    System.out.println(load.isLoaded());
                    bArr = new byte[((int) channel.size())];
                    if (load.remaining() > 0) {
                        load.get(bArr, 0, load.remaining());
                    }
                    try {
                        channel.close();
                    } catch (Throwable th3) {
                        com.umeng.commonsdk.proguard.b.a(appContext, th3);
                    }
                } catch (Throwable e) {
                    th2 = e;
                    fileChannel = channel;
                    th3 = th2;
                    try {
                        com.umeng.commonsdk.proguard.b.a(appContext, th3);
                        throw th3;
                    } catch (Throwable th4) {
                        th3 = th4;
                        try {
                            fileChannel.close();
                        } catch (Throwable e2) {
                            com.umeng.commonsdk.proguard.b.a(appContext, e2);
                        }
                        throw th3;
                    }
                } catch (Throwable e22) {
                    th2 = e22;
                    fileChannel = channel;
                    th3 = th2;
                    fileChannel.close();
                    throw th3;
                }
            } catch (IOException e3) {
                th3 = e3;
            }
        }
        return bArr;
    }

    public static boolean a(Context context, UMBusinessType uMBusinessType) {
        String str = g.al;
        if (uMBusinessType == UMBusinessType.U_DPLUS) {
            str = g.am;
        } else if (uMBusinessType == UMBusinessType.U_INTERNAL) {
            str = g.aq;
        }
        synchronized (e) {
            try {
                File[] listFiles = new File(h(context)).listFiles();
                if (listFiles == null || listFiles.length == 0) {
                    return false;
                }
                for (File name : listFiles) {
                    if (name.getName().startsWith(str)) {
                        return true;
                    }
                }
            } catch (Throwable th) {
                com.umeng.commonsdk.proguard.b.a(context, th);
                return false;
            }
        }
    }
}
