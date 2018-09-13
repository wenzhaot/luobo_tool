package com.tencent.bugly.crashreport.crash.jni;

import android.content.Context;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.taobao.accs.common.Constants;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.proguard.l;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class b {
    private static String a = null;

    private static Map<String, Integer> c(String str) {
        if (str == null) {
            return null;
        }
        try {
            Map<String, Integer> hashMap = new HashMap();
            for (String split : str.split(MiPushClient.ACCEPT_TIME_SEPARATOR)) {
                String[] split2 = split.split(":");
                if (split2.length != 2) {
                    x.e("error format at %s", split);
                    return null;
                }
                hashMap.put(split2[0], Integer.valueOf(Integer.parseInt(split2[1])));
            }
            return hashMap;
        } catch (Exception e) {
            x.e("error format intStateStr %s", str);
            e.printStackTrace();
            return null;
        }
    }

    protected static String a(String str) {
        if (str == null) {
            return "";
        }
        String[] split = str.split("\n");
        if (split == null || split.length == 0) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str2 : split) {
            if (!str2.contains("java.lang.Thread.getStackTrace(")) {
                stringBuilder.append(str2).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    private static CrashDetailBean a(Context context, Map<String, String> map, NativeExceptionHandler nativeExceptionHandler) {
        if (map == null) {
            return null;
        }
        if (a.a(context) == null) {
            x.e("abnormal com info not created", new Object[0]);
            return null;
        }
        String str = (String) map.get("intStateStr");
        if (str == null || str.trim().length() <= 0) {
            x.e("no intStateStr", new Object[0]);
            return null;
        }
        Map c = c(str);
        if (c == null) {
            x.e("parse intSateMap fail", Integer.valueOf(map.size()));
            return null;
        }
        try {
            ((Integer) c.get("sino")).intValue();
            ((Integer) c.get("sud")).intValue();
            String str2 = (String) map.get("soVersion");
            if (str2 == null) {
                x.e("error format at version", new Object[0]);
                return null;
            }
            String str3;
            String str4;
            String str5;
            String str6;
            String str7;
            String str8;
            String str9;
            str = (String) map.get("errorAddr");
            String str10 = str == null ? "unknown" : str;
            str = (String) map.get("codeMsg");
            if (str == null) {
                str3 = "unknown";
            } else {
                str3 = str;
            }
            str = (String) map.get("tombPath");
            if (str == null) {
                str4 = "unknown";
            } else {
                str4 = str;
            }
            str = (String) map.get("signalName");
            if (str == null) {
                str5 = "unknown";
            } else {
                str5 = str;
            }
            map.get("errnoMsg");
            str = (String) map.get("stack");
            if (str == null) {
                str6 = "unknown";
            } else {
                str6 = str;
            }
            str = (String) map.get("jstack");
            if (str != null) {
                str6 = str6 + "java:\n" + str;
            }
            Integer num = (Integer) c.get("sico");
            if (num != null && num.intValue() > 0) {
                str5 = str5 + l.s + str3 + l.t;
                str3 = "KERNEL";
            }
            str = (String) map.get("nativeLog");
            byte[] bArr = null;
            if (!(str == null || str.isEmpty())) {
                bArr = z.a(null, str, "BuglyNativeLog.txt");
            }
            str = (String) map.get("sendingProcess");
            if (str == null) {
                str7 = "unknown";
            } else {
                str7 = str;
            }
            num = (Integer) c.get("spd");
            if (num != null) {
                str7 = str7 + l.s + num + l.t;
            }
            str = (String) map.get("threadName");
            if (str == null) {
                str8 = "unknown";
            } else {
                str8 = str;
            }
            num = (Integer) c.get("et");
            if (num != null) {
                str8 = str8 + l.s + num + l.t;
            }
            str = (String) map.get("processName");
            if (str == null) {
                str9 = "unknown";
            } else {
                str9 = str;
            }
            num = (Integer) c.get("ep");
            if (num != null) {
                str9 = str9 + l.s + num + l.t;
            }
            Map map2 = null;
            str = (String) map.get("key-value");
            if (str != null) {
                map2 = new HashMap();
                for (String split : str.split("\n")) {
                    String[] split2 = split.split("=");
                    if (split2.length == 2) {
                        map2.put(split2[0], split2[1]);
                    }
                }
            }
            CrashDetailBean packageCrashDatas = nativeExceptionHandler.packageCrashDatas(str9, str8, (((long) ((Integer) c.get("etms")).intValue()) / 1000) + (((long) ((Integer) c.get("ets")).intValue()) * 1000), str5, str10, a(str6), str3, str7, str4, (String) map.get("sysLogPath"), str2, bArr, map2, false);
            if (packageCrashDatas != null) {
                str = (String) map.get(Parameters.SESSION_USER_ID);
                if (str != null) {
                    x.c("[Native record info] userId: %s", str);
                    packageCrashDatas.m = str;
                }
                str = (String) map.get("sysLog");
                if (str != null) {
                    packageCrashDatas.w = str;
                }
                str = (String) map.get(Constants.KEY_APP_VERSION);
                if (str != null) {
                    x.c("[Native record info] appVersion: %s", str);
                    packageCrashDatas.f = str;
                }
                str = (String) map.get("isAppForeground");
                if (str != null) {
                    x.c("[Native record info] isAppForeground: %s", str);
                    packageCrashDatas.M = str.equalsIgnoreCase(ITagManager.STATUS_TRUE);
                }
                str = (String) map.get("launchTime");
                if (str != null) {
                    x.c("[Native record info] launchTime: %s", str);
                    packageCrashDatas.L = Long.parseLong(str);
                }
                packageCrashDatas.y = null;
                packageCrashDatas.k = true;
            }
            return packageCrashDatas;
        } catch (Throwable e) {
            if (!x.a(e)) {
                e.printStackTrace();
            }
        } catch (Throwable e2) {
            x.e("error format", new Object[0]);
            e2.printStackTrace();
            return null;
        }
    }

    private static String a(BufferedInputStream bufferedInputStream) throws IOException {
        if (bufferedInputStream == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            int read = bufferedInputStream.read();
            if (read == -1) {
                return null;
            }
            if (read == 0) {
                return stringBuilder.toString();
            }
            stringBuilder.append((char) read);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a3 A:{SYNTHETIC, Splitter: B:53:0x00a3} */
    public static com.tencent.bugly.crashreport.crash.CrashDetailBean a(android.content.Context r6, java.lang.String r7, com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler r8) {
        /*
        r2 = 0;
        r0 = 0;
        if (r6 == 0) goto L_0x0008;
    L_0x0004:
        if (r7 == 0) goto L_0x0008;
    L_0x0006:
        if (r8 != 0) goto L_0x0011;
    L_0x0008:
        r1 = "get eup record file args error";
        r2 = new java.lang.Object[r2];
        com.tencent.bugly.proguard.x.e(r1, r2);
    L_0x0010:
        return r0;
    L_0x0011:
        r1 = new java.io.File;
        r2 = "rqd_record.eup";
        r1.<init>(r7, r2);
        r2 = r1.exists();
        if (r2 == 0) goto L_0x0010;
    L_0x001f:
        r2 = r1.canRead();
        if (r2 == 0) goto L_0x0010;
    L_0x0025:
        r2 = new java.io.BufferedInputStream;	 Catch:{ IOException -> 0x008c, all -> 0x009e }
        r3 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x008c, all -> 0x009e }
        r3.<init>(r1);	 Catch:{ IOException -> 0x008c, all -> 0x009e }
        r2.<init>(r3);	 Catch:{ IOException -> 0x008c, all -> 0x009e }
        r1 = a(r2);	 Catch:{ IOException -> 0x00ae }
        if (r1 == 0) goto L_0x003e;
    L_0x0035:
        r3 = "NATIVE_RQD_REPORT";
        r3 = r1.equals(r3);	 Catch:{ IOException -> 0x00ae }
        if (r3 != 0) goto L_0x0053;
    L_0x003e:
        r3 = "record read fail! %s";
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ IOException -> 0x00ae }
        r5 = 0;
        r4[r5] = r1;	 Catch:{ IOException -> 0x00ae }
        com.tencent.bugly.proguard.x.e(r3, r4);	 Catch:{ IOException -> 0x00ae }
        r2.close();	 Catch:{ IOException -> 0x004e }
        goto L_0x0010;
    L_0x004e:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0010;
    L_0x0053:
        r4 = new java.util.HashMap;	 Catch:{ IOException -> 0x00ae }
        r4.<init>();	 Catch:{ IOException -> 0x00ae }
        r1 = r0;
    L_0x0059:
        r3 = a(r2);	 Catch:{ IOException -> 0x00ae }
        if (r3 == 0) goto L_0x0068;
    L_0x005f:
        if (r1 != 0) goto L_0x0063;
    L_0x0061:
        r1 = r3;
        goto L_0x0059;
    L_0x0063:
        r4.put(r1, r3);	 Catch:{ IOException -> 0x00ae }
        r1 = r0;
        goto L_0x0059;
    L_0x0068:
        if (r1 == 0) goto L_0x007f;
    L_0x006a:
        r3 = "record not pair! drop! %s";
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ IOException -> 0x00ae }
        r5 = 0;
        r4[r5] = r1;	 Catch:{ IOException -> 0x00ae }
        com.tencent.bugly.proguard.x.e(r3, r4);	 Catch:{ IOException -> 0x00ae }
        r2.close();	 Catch:{ IOException -> 0x007a }
        goto L_0x0010;
    L_0x007a:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0010;
    L_0x007f:
        r0 = a(r6, r4, r8);	 Catch:{ IOException -> 0x00ae }
        r2.close();	 Catch:{ IOException -> 0x0087 }
        goto L_0x0010;
    L_0x0087:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0010;
    L_0x008c:
        r1 = move-exception;
        r2 = r0;
    L_0x008e:
        r1.printStackTrace();	 Catch:{ all -> 0x00ac }
        if (r2 == 0) goto L_0x0010;
    L_0x0093:
        r2.close();	 Catch:{ IOException -> 0x0098 }
        goto L_0x0010;
    L_0x0098:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0010;
    L_0x009e:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
    L_0x00a1:
        if (r2 == 0) goto L_0x00a6;
    L_0x00a3:
        r2.close();	 Catch:{ IOException -> 0x00a7 }
    L_0x00a6:
        throw r0;
    L_0x00a7:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00a6;
    L_0x00ac:
        r0 = move-exception;
        goto L_0x00a1;
    L_0x00ae:
        r1 = move-exception;
        goto L_0x008e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.jni.b.a(android.content.Context, java.lang.String, com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler):com.tencent.bugly.crashreport.crash.CrashDetailBean");
    }

    private static String b(String str, String str2) {
        String str3 = null;
        BufferedReader a = z.a(str, "reg_record.txt");
        if (a != null) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                String readLine = a.readLine();
                if (readLine != null && readLine.startsWith(str2)) {
                    String str4 = "                ";
                    int i = 0;
                    int i2 = 18;
                    int i3 = 0;
                    while (true) {
                        String readLine2 = a.readLine();
                        if (readLine2 == null) {
                            break;
                        }
                        if (i3 % 4 == 0) {
                            if (i3 > 0) {
                                stringBuilder.append("\n");
                            }
                            stringBuilder.append("  ");
                        } else {
                            if (readLine2.length() > 16) {
                                i2 = 28;
                            }
                            stringBuilder.append(str4.substring(0, i2 - i));
                        }
                        i = readLine2.length();
                        stringBuilder.append(readLine2);
                        i3++;
                    }
                    stringBuilder.append("\n");
                    str3 = stringBuilder.toString();
                    if (a != null) {
                        try {
                            a.close();
                        } catch (Throwable e) {
                            x.a(e);
                        }
                    }
                } else if (a != null) {
                    try {
                        a.close();
                    } catch (Throwable e2) {
                        x.a(e2);
                    }
                }
            } catch (Throwable th) {
                if (a != null) {
                    try {
                        a.close();
                    } catch (Throwable e22) {
                        x.a(e22);
                    }
                }
            }
        }
        return str3;
    }

    private static String c(String str, String str2) {
        String str3 = null;
        BufferedReader a = z.a(str, "map_record.txt");
        if (a != null) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                String readLine = a.readLine();
                if (readLine != null && readLine.startsWith(str2)) {
                    while (true) {
                        readLine = a.readLine();
                        if (readLine == null) {
                            break;
                        }
                        stringBuilder.append("  ");
                        stringBuilder.append(readLine);
                        stringBuilder.append("\n");
                    }
                    str3 = stringBuilder.toString();
                    if (a != null) {
                        try {
                            a.close();
                        } catch (Throwable e) {
                            x.a(e);
                        }
                    }
                } else if (a != null) {
                    try {
                        a.close();
                    } catch (Throwable e2) {
                        x.a(e2);
                    }
                }
            } catch (Throwable th) {
                if (a != null) {
                    try {
                        a.close();
                    } catch (Throwable e22) {
                        x.a(e22);
                    }
                }
            }
        }
        return str3;
    }

    public static String a(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String b = b(str, str2);
        if (!(b == null || b.isEmpty())) {
            stringBuilder.append("Register infos:\n");
            stringBuilder.append(b);
        }
        b = c(str, str2);
        if (!(b == null || b.isEmpty())) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("\n");
            }
            stringBuilder.append("System SO infos:\n");
            stringBuilder.append(b);
        }
        return stringBuilder.toString();
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        File file = new File(str, "backup_record.txt");
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public static void a(boolean z, String str) {
        if (str != null) {
            File file = new File(str, "rqd_record.eup");
            if (file.exists() && file.canWrite()) {
                file.delete();
                x.c("delete record file %s", file.getAbsoluteFile());
            }
            file = new File(str, "reg_record.txt");
            if (file.exists() && file.canWrite()) {
                file.delete();
                x.c("delete record file %s", file.getAbsoluteFile());
            }
            file = new File(str, "map_record.txt");
            if (file.exists() && file.canWrite()) {
                file.delete();
                x.c("delete record file %s", file.getAbsoluteFile());
            }
            file = new File(str, "backup_record.txt");
            if (file.exists() && file.canWrite()) {
                file.delete();
                x.c("delete record file %s", file.getAbsoluteFile());
            }
            if (a != null) {
                file = new File(a);
                if (file.exists() && file.canWrite()) {
                    file.delete();
                    x.c("delete record file %s", file.getAbsoluteFile());
                }
            }
            if (z) {
                file = new File(str);
                if (file.canRead() && file.isDirectory()) {
                    File[] listFiles = file.listFiles();
                    if (listFiles != null) {
                        for (File file2 : listFiles) {
                            if (file2.canRead() && file2.canWrite() && file2.length() == 0) {
                                file2.delete();
                                x.c("delete invalid record file %s", file2.getAbsoluteFile());
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00bd A:{SYNTHETIC, Splitter: B:40:0x00bd} */
    public static java.lang.String a(java.lang.String r8, int r9, java.lang.String r10) {
        /*
        r0 = 0;
        if (r8 == 0) goto L_0x0005;
    L_0x0003:
        if (r9 > 0) goto L_0x0006;
    L_0x0005:
        return r0;
    L_0x0006:
        r1 = new java.io.File;
        r1.<init>(r8);
        r2 = r1.exists();
        if (r2 == 0) goto L_0x0005;
    L_0x0011:
        r2 = r1.canRead();
        if (r2 == 0) goto L_0x0005;
    L_0x0017:
        a = r8;
        r2 = "Read system log from native record file(length: %s bytes): %s";
        r3 = 2;
        r3 = new java.lang.Object[r3];
        r4 = 0;
        r6 = r1.length();
        r5 = java.lang.Long.valueOf(r6);
        r3[r4] = r5;
        r4 = 1;
        r5 = r1.getAbsolutePath();
        r3[r4] = r5;
        com.tencent.bugly.proguard.x.a(r2, r3);
        if (r10 != 0) goto L_0x0051;
    L_0x0036:
        r0 = new java.io.File;
        r0.<init>(r8);
        r0 = com.tencent.bugly.proguard.z.a(r0);
    L_0x003f:
        if (r0 == 0) goto L_0x0005;
    L_0x0041:
        r1 = r0.length();
        if (r1 <= r9) goto L_0x0005;
    L_0x0047:
        r1 = r0.length();
        r1 = r1 - r9;
        r0 = r0.substring(r1);
        goto L_0x0005;
    L_0x0051:
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00c8, all -> 0x00b8 }
        r3.<init>();	 Catch:{ Throwable -> 0x00c8, all -> 0x00b8 }
        r2 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x00c8, all -> 0x00b8 }
        r4 = new java.io.InputStreamReader;	 Catch:{ Throwable -> 0x00c8, all -> 0x00b8 }
        r5 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x00c8, all -> 0x00b8 }
        r5.<init>(r1);	 Catch:{ Throwable -> 0x00c8, all -> 0x00b8 }
        r1 = "utf-8";
        r4.<init>(r5, r1);	 Catch:{ Throwable -> 0x00c8, all -> 0x00b8 }
        r2.<init>(r4);	 Catch:{ Throwable -> 0x00c8, all -> 0x00b8 }
    L_0x0068:
        r1 = r2.readLine();	 Catch:{ Throwable -> 0x009a }
        if (r1 == 0) goto L_0x00ab;
    L_0x006e:
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x009a }
        r4.<init>();	 Catch:{ Throwable -> 0x009a }
        r4 = r4.append(r10);	 Catch:{ Throwable -> 0x009a }
        r5 = "[ ]*:";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x009a }
        r4 = r4.toString();	 Catch:{ Throwable -> 0x009a }
        r4 = java.util.regex.Pattern.compile(r4);	 Catch:{ Throwable -> 0x009a }
        r4 = r4.matcher(r1);	 Catch:{ Throwable -> 0x009a }
        r4 = r4.find();	 Catch:{ Throwable -> 0x009a }
        if (r4 == 0) goto L_0x0068;
    L_0x0090:
        r3.append(r1);	 Catch:{ Throwable -> 0x009a }
        r1 = "\n";
        r3.append(r1);	 Catch:{ Throwable -> 0x009a }
        goto L_0x0068;
    L_0x009a:
        r1 = move-exception;
    L_0x009b:
        com.tencent.bugly.proguard.x.a(r1);	 Catch:{ all -> 0x00c6 }
        if (r2 == 0) goto L_0x0005;
    L_0x00a0:
        r2.close();	 Catch:{ Exception -> 0x00a5 }
        goto L_0x0005;
    L_0x00a5:
        r1 = move-exception;
        com.tencent.bugly.proguard.x.a(r1);
        goto L_0x0005;
    L_0x00ab:
        r0 = r3.toString();	 Catch:{ Throwable -> 0x009a }
        r2.close();	 Catch:{ Exception -> 0x00b3 }
        goto L_0x003f;
    L_0x00b3:
        r1 = move-exception;
        com.tencent.bugly.proguard.x.a(r1);
        goto L_0x003f;
    L_0x00b8:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
    L_0x00bb:
        if (r2 == 0) goto L_0x00c0;
    L_0x00bd:
        r2.close();	 Catch:{ Exception -> 0x00c1 }
    L_0x00c0:
        throw r0;
    L_0x00c1:
        r1 = move-exception;
        com.tencent.bugly.proguard.x.a(r1);
        goto L_0x00c0;
    L_0x00c6:
        r0 = move-exception;
        goto L_0x00bb;
    L_0x00c8:
        r1 = move-exception;
        r2 = r0;
        goto L_0x009b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.jni.b.a(java.lang.String, int, java.lang.String):java.lang.String");
    }
}
