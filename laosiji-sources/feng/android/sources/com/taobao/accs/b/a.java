package com.taobao.accs.b;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.stub.StubApp;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.l;
import com.umeng.analytics.pro.c;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: Taobao */
public class a implements Callback {
    public static final int ACT_START = 0;
    public static final int ACT_STOP = -1;
    public static final String AGOO_PID = "agoo.pid";
    public static final String EX_FILE_NAME = "DaemonServer";
    public static final String PROCESS_NAME = "runServer";
    public static String a = "startservice -n {packname}/com.taobao.accs.ChannelService";
    private static final String c = a.class.getName();
    private static int g = 7200;
    private static int h = 2500;
    private static final ReentrantLock i = new ReentrantLock();
    private static a j = null;
    public boolean b = false;
    private Context d = null;
    private String e;
    private int f = 1800;
    private String k = "100001";
    private String l = "tb_accs_eudemon_1.1.3";
    private String m = "";
    private String n = "21646297";
    private int o = 0;
    private String p = "100.69.165.28";
    private String q = "http://100.69.165.28/agoo/report";
    private int r = 80;
    private boolean s = true;
    private Handler t = null;
    private HandlerThread u = null;

    public a(Context context, int i, boolean z) {
        b();
        a = "startservice -n {packname}/com.taobao.accs.ChannelService";
        this.d = context;
        this.f = i;
        this.b = z;
        this.e = a(new Build(), "CPU_ABI");
        this.m = c.a + context.getPackageName();
        this.o = Constants.SDK_VERSION_CODE;
        String[] appkey = UtilityImpl.getAppkey(this.d);
        String str = (appkey == null || appkey.length == 0) ? "" : appkey[0];
        this.n = str;
        if (l.a(context) == 0) {
            this.p = "agoodm.m.taobao.com";
            this.r = 80;
            this.q = "http://agoodm.m.taobao.com/agoo/report";
            this.k = "1009527";
        } else if (l.a(context) == 1) {
            this.p = "110.75.98.154";
            this.r = 80;
            this.q = "http://agoodm.wapa.taobao.com/agoo/report";
            this.k = "1009527";
        } else {
            this.p = "100.69.168.33";
            this.r = 80;
            this.q = "http://100.69.168.33/agoo/report";
            this.f = 60;
            this.k = "9527";
        }
    }

    private void b() {
        Log.d(c, "start handler init");
        this.u = new HandlerThread("soManager-threads");
        this.u.setPriority(5);
        this.u.start();
        this.t = new Handler(this.u.getLooper(), this);
    }

    private String c() {
        if (this.e.startsWith("arm")) {
            return "armeabi/";
        }
        return this.e + "/";
    }

    private static String a(Build build, String str) {
        try {
            return Build.class.getField(str).get(build).toString();
        } catch (Throwable th) {
            return "Unknown";
        }
    }

    public static a a(Context context, int i, boolean z) {
        try {
            i.lock();
            if (j == null) {
                j = new a(context, i, z);
            }
            i.unlock();
        } catch (Throwable e) {
            ALog.e(c, "getInstance", e, new Object[0]);
            i.unlock();
        } catch (Throwable e2) {
            i.unlock();
            throw e2;
        }
        return j;
    }

    private String d() throws IOException {
        InputStream inputStream = null;
        File file = new File(this.d.getFilesDir(), EX_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        ALog.w(c, "open assets from = " + c() + EX_FILE_NAME, new Object[0]);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            if (this.b) {
                inputStream = this.d.getAssets().open(c() + EX_FILE_NAME);
                byte[] bArr = new byte[100];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
            } else {
                a(fileOutputStream, file);
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e) {
                    ALog.e(c, "error in close input file", e, new Object[0]);
                }
            }
            try {
                fileOutputStream.close();
            } catch (Throwable e2) {
                ALog.e(c, "error in close io", e2, new Object[0]);
            }
        } catch (Throwable e22) {
            ALog.e(c, "error in copy daemon files", e22, new Object[0]);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e222) {
                    ALog.e(c, "error in close input file", e222, new Object[0]);
                }
            }
            try {
                fileOutputStream.close();
            } catch (Throwable e2222) {
                ALog.e(c, "error in close io", e2222, new Object[0]);
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e3) {
                    ALog.e(c, "error in close input file", e3, new Object[0]);
                }
            }
            try {
                fileOutputStream.close();
            } catch (Throwable e32) {
                ALog.e(c, "error in close io", e32, new Object[0]);
            }
        }
        return file.getCanonicalPath();
    }

    /* JADX WARNING: Removed duplicated region for block: B:46:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00a0 A:{SYNTHETIC, Splitter: B:20:0x00a0} */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c6 A:{SYNTHETIC, Splitter: B:34:0x00c6} */
    private void a(java.io.FileOutputStream r9, java.io.File r10) throws java.io.IOException {
        /*
        r8 = this;
        r4 = 0;
        r0 = r8.e;
        r0 = com.taobao.accs.b.b.a(r0);
        r1 = c;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = ">>>soDataSize:datasize:";
        r2 = r2.append(r3);
        r3 = r0.length();
        r2 = r2.append(r3);
        r2 = r2.toString();
        r3 = new java.lang.Object[r4];
        com.taobao.accs.utl.ALog.d(r1, r2, r3);
        r0 = r0.getBytes();
        r0 = android.util.Base64.decode(r0, r4);
        r1 = c;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = ">>>soDataSize:";
        r2 = r2.append(r3);
        r3 = r0.length;
        r2 = r2.append(r3);
        r2 = r2.toString();
        r3 = new java.lang.Object[r4];
        com.taobao.accs.utl.ALog.d(r1, r2, r3);
        r1 = r0.length;
        if (r1 > 0) goto L_0x004e;
    L_0x004d:
        return;
    L_0x004e:
        if (r9 == 0) goto L_0x004d;
    L_0x0050:
        r2 = 0;
        r1 = new android.os.StatFs;
        r3 = r10.getCanonicalPath();
        r1.<init>(r3);
        r3 = r1.getBlockSize();
        r1 = r1.getAvailableBlocks();
        r4 = (long) r1;
        r6 = (long) r3;
        r4 = r4 * r6;
        r1 = r0.length;
        r6 = (long) r1;
        r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r1 >= 0) goto L_0x0074;
    L_0x006b:
        r0 = c;
        r1 = "Disk is not enough for writing this file";
        android.util.Log.e(r0, r1);
        goto L_0x004d;
    L_0x0074:
        r1 = new java.io.ByteArrayInputStream;	 Catch:{ IOException -> 0x00d1, all -> 0x00bb }
        r1.<init>(r0);	 Catch:{ IOException -> 0x00d1, all -> 0x00bb }
        r0 = 100;
        r0 = new byte[r0];	 Catch:{ IOException -> 0x008b }
    L_0x007d:
        r2 = 0;
        r3 = 100;
        r2 = r1.read(r0, r2, r3);	 Catch:{ IOException -> 0x008b }
        if (r2 < 0) goto L_0x00a9;
    L_0x0086:
        r3 = 0;
        r9.write(r0, r3, r2);	 Catch:{ IOException -> 0x008b }
        goto L_0x007d;
    L_0x008b:
        r0 = move-exception;
    L_0x008c:
        r2 = c;	 Catch:{ all -> 0x00cf }
        r3 = "error in write files";
        r4 = 0;
        r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x00cf }
        com.taobao.accs.utl.ALog.e(r2, r3, r0, r4);	 Catch:{ all -> 0x00cf }
        r0 = r9.getFD();
        r0.sync();
        if (r1 == 0) goto L_0x004d;
    L_0x00a0:
        r1.close();	 Catch:{ IOException -> 0x00a4 }
        goto L_0x004d;
    L_0x00a4:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x004d;
    L_0x00a9:
        r0 = r9.getFD();
        r0.sync();
        if (r1 == 0) goto L_0x004d;
    L_0x00b2:
        r1.close();	 Catch:{ IOException -> 0x00b6 }
        goto L_0x004d;
    L_0x00b6:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x004d;
    L_0x00bb:
        r0 = move-exception;
        r1 = r2;
    L_0x00bd:
        r2 = r9.getFD();
        r2.sync();
        if (r1 == 0) goto L_0x00c9;
    L_0x00c6:
        r1.close();	 Catch:{ IOException -> 0x00ca }
    L_0x00c9:
        throw r0;
    L_0x00ca:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00c9;
    L_0x00cf:
        r0 = move-exception;
        goto L_0x00bd;
    L_0x00d1:
        r0 = move-exception;
        r1 = r2;
        goto L_0x008c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.b.a.a(java.io.FileOutputStream, java.io.File):void");
    }

    private void a(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        a("", "chmod 500 " + str, stringBuilder);
        a("", str + " " + e(), stringBuilder);
        ALog.w(c, str + " " + e(), new Object[0]);
    }

    private String e() {
        StringBuilder stringBuilder = new StringBuilder();
        String str = c.a + this.d.getPackageName();
        stringBuilder.append("-s \"" + str + "/lib/" + "\" ");
        stringBuilder.append("-n \"runServer\" ");
        stringBuilder.append("-p \"" + g() + "\" ");
        stringBuilder.append("-f \"" + str + "\" ");
        stringBuilder.append("-t \"" + this.f + "\" ");
        stringBuilder.append("-c \"agoo.pid\" ");
        if (this.m != null) {
            stringBuilder.append("-P " + this.m + " ");
        }
        if (this.k != null) {
            stringBuilder.append("-K " + this.k + " ");
        }
        if (this.l != null) {
            stringBuilder.append("-U " + this.l + " ");
        }
        if (this.q != null) {
            stringBuilder.append("-L " + this.q + " ");
        }
        stringBuilder.append("-D " + f() + " ");
        if (this.p != null) {
            stringBuilder.append("-I " + this.p + " ");
        }
        if (this.r > 0) {
            stringBuilder.append("-O " + this.r + " ");
        }
        str = UtilityImpl.getProxyHost(this.d);
        int proxyPort = UtilityImpl.getProxyPort(this.d);
        if (str != null && proxyPort > 0) {
            stringBuilder.append("-X " + str + " ");
            stringBuilder.append("-Y " + proxyPort + " ");
        }
        if (this.s) {
            stringBuilder.append("-T ");
        }
        stringBuilder.append("-Z ");
        return stringBuilder.toString();
    }

    private String f() {
        String deviceId = UtilityImpl.getDeviceId(this.d);
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = "null";
        }
        deviceId = "{\"package\":\"" + this.d.getPackageName() + "\",\"appKey\":\"" + this.n + "\",\"utdid\":\"" + deviceId + "\",\"sdkVersion\":\"" + this.o + "\"}";
        try {
            return URLEncoder.encode(deviceId, "UTF-8");
        } catch (Throwable th) {
            ALog.e(c, "getReportData failed for url encode, data:" + deviceId, new Object[0]);
            return deviceId;
        }
    }

    private String g() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(a.replaceAll("\\{packname\\}", StubApp.getOrigApplicationContext(this.d.getApplicationContext()).getPackageName()));
        if (VERSION.SDK_INT > 15) {
            stringBuilder.append(" --user 0");
        }
        return stringBuilder.toString();
    }

    private void a(String str, int i, int i2, String str2, String str3, int i3) {
        String str4 = "AndroidVer=" + VERSION.RELEASE + "&Model=" + Build.MODEL + "&AndroidSdk=" + VERSION.SDK_INT + "&AccsVer=" + Constants.SDK_VERSION_CODE + "&Appkey=" + this.n + "&PullCount=" + str2 + "&Pid=" + str + "&StartTime=" + i + "&EndTime=" + i2 + "&ExitCode=" + str3 + "&AliveTime=" + i3;
        Log.d(c, "EUDEMON_ENDSTAT doReportDaemonStat:" + str4);
        UTMini.getInstance().commitEvent(66001, "EUDEMON_ENDSTAT", str4);
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00fd A:{SYNTHETIC, Splitter: B:28:0x00fd} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0102 A:{SYNTHETIC, Splitter: B:31:0x0102} */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0107 A:{SYNTHETIC, Splitter: B:34:0x0107} */
    /* JADX WARNING: Removed duplicated region for block: B:116:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x010c A:{SYNTHETIC, Splitter: B:37:0x010c} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0194 A:{SYNTHETIC, Splitter: B:74:0x0194} */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0199 A:{SYNTHETIC, Splitter: B:77:0x0199} */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x019e A:{SYNTHETIC, Splitter: B:80:0x019e} */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01a3 A:{SYNTHETIC, Splitter: B:83:0x01a3} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00fd A:{SYNTHETIC, Splitter: B:28:0x00fd} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0102 A:{SYNTHETIC, Splitter: B:31:0x0102} */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0107 A:{SYNTHETIC, Splitter: B:34:0x0107} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x010c A:{SYNTHETIC, Splitter: B:37:0x010c} */
    /* JADX WARNING: Removed duplicated region for block: B:116:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0194 A:{SYNTHETIC, Splitter: B:74:0x0194} */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0199 A:{SYNTHETIC, Splitter: B:77:0x0199} */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x019e A:{SYNTHETIC, Splitter: B:80:0x019e} */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01a3 A:{SYNTHETIC, Splitter: B:83:0x01a3} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00fd A:{SYNTHETIC, Splitter: B:28:0x00fd} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0102 A:{SYNTHETIC, Splitter: B:31:0x0102} */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0107 A:{SYNTHETIC, Splitter: B:34:0x0107} */
    /* JADX WARNING: Removed duplicated region for block: B:116:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x010c A:{SYNTHETIC, Splitter: B:37:0x010c} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0194 A:{SYNTHETIC, Splitter: B:74:0x0194} */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0199 A:{SYNTHETIC, Splitter: B:77:0x0199} */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x019e A:{SYNTHETIC, Splitter: B:80:0x019e} */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01a3 A:{SYNTHETIC, Splitter: B:83:0x01a3} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00fd A:{SYNTHETIC, Splitter: B:28:0x00fd} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0102 A:{SYNTHETIC, Splitter: B:31:0x0102} */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0107 A:{SYNTHETIC, Splitter: B:34:0x0107} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x010c A:{SYNTHETIC, Splitter: B:37:0x010c} */
    /* JADX WARNING: Removed duplicated region for block: B:116:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0194 A:{SYNTHETIC, Splitter: B:74:0x0194} */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0199 A:{SYNTHETIC, Splitter: B:77:0x0199} */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x019e A:{SYNTHETIC, Splitter: B:80:0x019e} */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01a3 A:{SYNTHETIC, Splitter: B:83:0x01a3} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0194 A:{SYNTHETIC, Splitter: B:74:0x0194} */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0199 A:{SYNTHETIC, Splitter: B:77:0x0199} */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x019e A:{SYNTHETIC, Splitter: B:80:0x019e} */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01a3 A:{SYNTHETIC, Splitter: B:83:0x01a3} */
    private void h() {
        /*
        r18 = this;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "/data/data/";
        r1 = r1.append(r2);
        r0 = r18;
        r2 = r0.d;
        r2 = r2.getPackageName();
        r1 = r1.append(r2);
        r2 = "/";
        r1 = r1.append(r2);
        r2 = "eudemon";
        r1 = r1.append(r2);
        r13 = r1.toString();
        r1 = new java.io.File;
        r1.<init>(r13);
        r2 = r1.exists();
        if (r2 != 0) goto L_0x0036;
    L_0x0035:
        return;
    L_0x0036:
        r4 = 0;
        r10 = 0;
        r3 = 0;
        r2 = 0;
        r11 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x01e1, all -> 0x018e }
        r11.<init>(r1);	 Catch:{ Exception -> 0x01e1, all -> 0x018e }
        r9 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x01e6, all -> 0x01cf }
        r9.<init>(r11);	 Catch:{ Exception -> 0x01e6, all -> 0x01cf }
        r8 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x01eb, all -> 0x01d3 }
        r8.<init>(r9);	 Catch:{ Exception -> 0x01eb, all -> 0x01d3 }
        r1 = "";
        r12 = r1;
    L_0x004d:
        r1 = r8.readLine();	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        if (r1 == 0) goto L_0x011c;
    L_0x0053:
        r2 = "\\|";
        r6 = r1.split(r2);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r2 = r6.length;	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r3 = 5;
        if (r2 != r3) goto L_0x004d;
    L_0x005e:
        r2 = 0;
        r2 = r6[r2];	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r2 = r2.trim();	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r3 = 1;
        r3 = r6[r3];	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r3 = r3.trim();	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r3 = r3.intValue();	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r4 = 2;
        r4 = r6[r4];	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r4 = r4.trim();	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r4 = r4.intValue();	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r7 = r4 - r3;
        r5 = 3;
        r5 = r6[r5];	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r5 = r5.trim();	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r14 = 4;
        r6 = r6[r14];	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r6 = r6.trim();	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r14 = "0";
        r14 = r6.equals(r14);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        if (r14 == 0) goto L_0x00e7;
    L_0x009c:
        r14 = new java.io.File;	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r15 = "/proc";
        r14.<init>(r15, r2);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r15 = c;	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r16 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r16.<init>();	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r17 = "pidfile:";
        r16 = r16.append(r17);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r0 = r16;
        r16 = r0.append(r14);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r16 = r16.toString();	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        android.util.Log.e(r15, r16);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r14 = r14.exists();	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        if (r14 == 0) goto L_0x00e0;
    L_0x00c5:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r2.<init>();	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r2 = r2.append(r12);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r1 = r2.append(r1);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r2 = "\n";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r12 = r1;
        goto L_0x004d;
    L_0x00e0:
        r0 = r18;
        r1 = r0.f;	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r1 = r1 / 2;
        r7 = r7 + r1;
    L_0x00e7:
        r1 = r18;
        r1.a(r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        goto L_0x004d;
    L_0x00ee:
        r1 = move-exception;
        r2 = r8;
        r3 = r9;
        r4 = r10;
        r5 = r11;
    L_0x00f3:
        r6 = c;	 Catch:{ all -> 0x01db }
        r7 = "report daemon stat exp:";
        android.util.Log.e(r6, r7, r1);	 Catch:{ all -> 0x01db }
        if (r2 == 0) goto L_0x0100;
    L_0x00fd:
        r2.close();	 Catch:{ Throwable -> 0x016f }
    L_0x0100:
        if (r3 == 0) goto L_0x0105;
    L_0x0102:
        r3.close();	 Catch:{ Throwable -> 0x0179 }
    L_0x0105:
        if (r5 == 0) goto L_0x010a;
    L_0x0107:
        r5.close();	 Catch:{ IOException -> 0x0183 }
    L_0x010a:
        if (r4 == 0) goto L_0x0035;
    L_0x010c:
        r4.close();	 Catch:{ IOException -> 0x0111 }
        goto L_0x0035;
    L_0x0111:
        r1 = move-exception;
        r2 = c;
        r3 = "error in close input file";
        android.util.Log.e(r2, r3, r1);
        goto L_0x0035;
    L_0x011c:
        r2 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r1 = new java.io.File;	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r1.<init>(r13);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r2.<init>(r1);	 Catch:{ Exception -> 0x00ee, all -> 0x01d6 }
        r1 = r12.getBytes();	 Catch:{ Exception -> 0x01f1, all -> 0x01d8 }
        r2.write(r1);	 Catch:{ Exception -> 0x01f1, all -> 0x01d8 }
        r8.close();	 Catch:{ Exception -> 0x01f1, all -> 0x01d8 }
        if (r8 == 0) goto L_0x0135;
    L_0x0132:
        r8.close();	 Catch:{ Throwable -> 0x0151 }
    L_0x0135:
        if (r9 == 0) goto L_0x013a;
    L_0x0137:
        r9.close();	 Catch:{ Throwable -> 0x015b }
    L_0x013a:
        if (r11 == 0) goto L_0x013f;
    L_0x013c:
        r11.close();	 Catch:{ IOException -> 0x0165 }
    L_0x013f:
        if (r2 == 0) goto L_0x0035;
    L_0x0141:
        r2.close();	 Catch:{ IOException -> 0x0146 }
        goto L_0x0035;
    L_0x0146:
        r1 = move-exception;
        r2 = c;
        r3 = "error in close input file";
        android.util.Log.e(r2, r3, r1);
        goto L_0x0035;
    L_0x0151:
        r1 = move-exception;
        r3 = c;
        r4 = "error in close buffreader stream";
        android.util.Log.e(r3, r4, r1);
        goto L_0x0135;
    L_0x015b:
        r1 = move-exception;
        r3 = c;
        r4 = "error in close reader stream";
        android.util.Log.e(r3, r4, r1);
        goto L_0x013a;
    L_0x0165:
        r1 = move-exception;
        r3 = c;
        r4 = "error in close input file";
        android.util.Log.e(r3, r4, r1);
        goto L_0x013f;
    L_0x016f:
        r1 = move-exception;
        r2 = c;
        r6 = "error in close buffreader stream";
        android.util.Log.e(r2, r6, r1);
        goto L_0x0100;
    L_0x0179:
        r1 = move-exception;
        r2 = c;
        r3 = "error in close reader stream";
        android.util.Log.e(r2, r3, r1);
        goto L_0x0105;
    L_0x0183:
        r1 = move-exception;
        r2 = c;
        r3 = "error in close input file";
        android.util.Log.e(r2, r3, r1);
        goto L_0x010a;
    L_0x018e:
        r1 = move-exception;
        r8 = r2;
        r9 = r3;
        r11 = r4;
    L_0x0192:
        if (r8 == 0) goto L_0x0197;
    L_0x0194:
        r8.close();	 Catch:{ Throwable -> 0x01a7 }
    L_0x0197:
        if (r9 == 0) goto L_0x019c;
    L_0x0199:
        r9.close();	 Catch:{ Throwable -> 0x01b1 }
    L_0x019c:
        if (r11 == 0) goto L_0x01a1;
    L_0x019e:
        r11.close();	 Catch:{ IOException -> 0x01bb }
    L_0x01a1:
        if (r10 == 0) goto L_0x01a6;
    L_0x01a3:
        r10.close();	 Catch:{ IOException -> 0x01c5 }
    L_0x01a6:
        throw r1;
    L_0x01a7:
        r2 = move-exception;
        r3 = c;
        r4 = "error in close buffreader stream";
        android.util.Log.e(r3, r4, r2);
        goto L_0x0197;
    L_0x01b1:
        r2 = move-exception;
        r3 = c;
        r4 = "error in close reader stream";
        android.util.Log.e(r3, r4, r2);
        goto L_0x019c;
    L_0x01bb:
        r2 = move-exception;
        r3 = c;
        r4 = "error in close input file";
        android.util.Log.e(r3, r4, r2);
        goto L_0x01a1;
    L_0x01c5:
        r2 = move-exception;
        r3 = c;
        r4 = "error in close input file";
        android.util.Log.e(r3, r4, r2);
        goto L_0x01a6;
    L_0x01cf:
        r1 = move-exception;
        r8 = r2;
        r9 = r3;
        goto L_0x0192;
    L_0x01d3:
        r1 = move-exception;
        r8 = r2;
        goto L_0x0192;
    L_0x01d6:
        r1 = move-exception;
        goto L_0x0192;
    L_0x01d8:
        r1 = move-exception;
        r10 = r2;
        goto L_0x0192;
    L_0x01db:
        r1 = move-exception;
        r8 = r2;
        r9 = r3;
        r10 = r4;
        r11 = r5;
        goto L_0x0192;
    L_0x01e1:
        r1 = move-exception;
        r5 = r4;
        r4 = r10;
        goto L_0x00f3;
    L_0x01e6:
        r1 = move-exception;
        r4 = r10;
        r5 = r11;
        goto L_0x00f3;
    L_0x01eb:
        r1 = move-exception;
        r3 = r9;
        r4 = r10;
        r5 = r11;
        goto L_0x00f3;
    L_0x01f1:
        r1 = move-exception;
        r3 = r9;
        r4 = r2;
        r5 = r11;
        r2 = r8;
        goto L_0x00f3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.b.a.h():void");
    }

    public void a() {
        Log.d(c, "start SoManager");
        Message obtain = Message.obtain();
        obtain.what = 0;
        this.t.sendMessage(obtain);
    }

    private void i() {
        ALog.d(c, "api level is:" + VERSION.SDK_INT, new Object[0]);
        b(this.d);
        if (VERSION.SDK_INT < 20) {
            try {
                String d = d();
                h();
                a(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        UTMini.getInstance().commitEvent(66001, "EUDEMON_START", "");
    }

    private void j() {
        File file = new File(c.a + this.d.getPackageName(), "daemonserver.pid");
        if (file.exists()) {
            file.delete();
        }
    }

    public static final PendingIntent a(Context context) {
        Intent intent = new Intent();
        intent.setAction(StubApp.getOrigApplicationContext(context.getApplicationContext()).getPackageName() + ".intent.action.COCKROACH");
        intent.putExtra("cockroach", "cockroach-PPreotect");
        intent.putExtra("pack", StubApp.getOrigApplicationContext(context.getApplicationContext()).getPackageName());
        return PendingIntent.getService(context, 0, intent, 134217728);
    }

    public static void b(Context context) {
        int i = Calendar.getInstance().get(11);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        if (alarmManager != null) {
            PendingIntent a = a(context);
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (i > 23 || i < 6) {
                ALog.w(c, "time is night, do not wakeup cpu", new Object[0]);
                b(alarmManager, a, elapsedRealtime);
                return;
            }
            ALog.w(c, "time is daytime, wakeup cpu for keeping connecntion", new Object[0]);
            a(alarmManager, a, elapsedRealtime);
        }
    }

    private static void a(AlarmManager alarmManager, PendingIntent pendingIntent, long j) {
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            alarmManager.setRepeating(2, ((long) (h * 1000)) + j, (long) (h * 1000), pendingIntent);
        }
    }

    private static void b(AlarmManager alarmManager, PendingIntent pendingIntent, long j) {
        alarmManager.cancel(pendingIntent);
        alarmManager.setRepeating(3, ((long) (g * 1000)) + j, (long) (g * 1000), pendingIntent);
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x007b A:{SYNTHETIC, Splitter: B:23:0x007b} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x008f A:{SYNTHETIC, Splitter: B:30:0x008f} */
    public static void c(android.content.Context r6) {
        /*
        r5 = 0;
        r0 = new java.io.File;	 Catch:{ Throwable -> 0x0054 }
        r1 = r6.getFilesDir();	 Catch:{ Throwable -> 0x0054 }
        r2 = "agoo.pid";
        r0.<init>(r1, r2);	 Catch:{ Throwable -> 0x0054 }
        r1 = c;	 Catch:{ Throwable -> 0x0054 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0054 }
        r2.<init>();	 Catch:{ Throwable -> 0x0054 }
        r3 = "pid path:";
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x0054 }
        r3 = r0.getAbsolutePath();	 Catch:{ Throwable -> 0x0054 }
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x0054 }
        r2 = r2.toString();	 Catch:{ Throwable -> 0x0054 }
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x0054 }
        com.taobao.accs.utl.ALog.d(r1, r2, r3);	 Catch:{ Throwable -> 0x0054 }
        r1 = r0.exists();	 Catch:{ Throwable -> 0x0054 }
        if (r1 == 0) goto L_0x0036;
    L_0x0033:
        r0.delete();	 Catch:{ Throwable -> 0x0054 }
    L_0x0036:
        r0.createNewFile();	 Catch:{ Throwable -> 0x0054 }
        r2 = 0;
        r3 = android.os.Process.myPid();	 Catch:{ Throwable -> 0x006c, all -> 0x008b }
        r1 = new java.io.FileWriter;	 Catch:{ Throwable -> 0x006c, all -> 0x008b }
        r1.<init>(r0);	 Catch:{ Throwable -> 0x006c, all -> 0x008b }
        r0 = java.lang.String.valueOf(r3);	 Catch:{ Throwable -> 0x00a1 }
        r0 = r0.toCharArray();	 Catch:{ Throwable -> 0x00a1 }
        r1.write(r0);	 Catch:{ Throwable -> 0x00a1 }
        if (r1 == 0) goto L_0x0053;
    L_0x0050:
        r1.close();	 Catch:{ Throwable -> 0x0060 }
    L_0x0053:
        return;
    L_0x0054:
        r0 = move-exception;
        r1 = c;
        r2 = "error in create file";
        r3 = new java.lang.Object[r5];
        com.taobao.accs.utl.ALog.e(r1, r2, r0, r3);
        goto L_0x0053;
    L_0x0060:
        r0 = move-exception;
        r1 = c;
        r2 = "error";
        r3 = new java.lang.Object[r5];
        com.taobao.accs.utl.ALog.e(r1, r2, r0, r3);
        goto L_0x0053;
    L_0x006c:
        r0 = move-exception;
        r1 = r2;
    L_0x006e:
        r2 = c;	 Catch:{ all -> 0x009f }
        r3 = "save pid error";
        r4 = 0;
        r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x009f }
        com.taobao.accs.utl.ALog.e(r2, r3, r0, r4);	 Catch:{ all -> 0x009f }
        if (r1 == 0) goto L_0x0053;
    L_0x007b:
        r1.close();	 Catch:{ Throwable -> 0x007f }
        goto L_0x0053;
    L_0x007f:
        r0 = move-exception;
        r1 = c;
        r2 = "error";
        r3 = new java.lang.Object[r5];
        com.taobao.accs.utl.ALog.e(r1, r2, r0, r3);
        goto L_0x0053;
    L_0x008b:
        r0 = move-exception;
        r1 = r2;
    L_0x008d:
        if (r1 == 0) goto L_0x0092;
    L_0x008f:
        r1.close();	 Catch:{ Throwable -> 0x0093 }
    L_0x0092:
        throw r0;
    L_0x0093:
        r1 = move-exception;
        r2 = c;
        r3 = "error";
        r4 = new java.lang.Object[r5];
        com.taobao.accs.utl.ALog.e(r2, r3, r1, r4);
        goto L_0x0092;
    L_0x009f:
        r0 = move-exception;
        goto L_0x008d;
    L_0x00a1:
        r0 = move-exception;
        goto L_0x006e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.b.a.c(android.content.Context):void");
    }

    public boolean handleMessage(Message message) {
        try {
            if (message.what == 0) {
                i();
            } else if (message.what == -1) {
                j();
            }
        } catch (Throwable th) {
            ALog.e(c, "handleMessage error", th, new Object[0]);
        }
        return true;
    }

    public static boolean a(String str, String str2, StringBuilder stringBuilder) {
        Log.w("TAG.", str2);
        try {
            Process exec = Runtime.getRuntime().exec("sh");
            DataInputStream dataInputStream = new DataInputStream(exec.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(exec.getOutputStream());
            if (!(str == null || "".equals(str.trim()))) {
                dataOutputStream.writeBytes("cd " + str + "\n");
            }
            dataOutputStream.writeBytes(str2 + " &\n");
            dataOutputStream.writeBytes("exit \n");
            dataOutputStream.flush();
            exec.waitFor();
            byte[] bArr = new byte[dataInputStream.available()];
            dataInputStream.read(bArr);
            String str3 = new String(bArr);
            if (str3.length() != 0) {
                stringBuilder.append(str3);
            }
            return true;
        } catch (Exception e) {
            stringBuilder.append("Exception:" + e.getMessage());
            return false;
        }
    }
}
