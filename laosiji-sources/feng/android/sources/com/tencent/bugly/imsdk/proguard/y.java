package com.tencent.bugly.imsdk.proguard;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Process;
import com.feng.library.utils.DateUtil;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.qiniu.android.common.Constants;
import com.stub.StubApp;
import com.tencent.bugly.imsdk.crashreport.common.info.PlugInBean;
import com.umeng.message.proguard.l;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: BUGLY */
public class y {
    private static Map<String, String> a = null;

    public static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        try {
            Writer stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            return stringWriter.getBuffer().toString();
        } catch (Throwable th2) {
            if (!w.a(th2)) {
                th2.printStackTrace();
            }
            return "fail";
        }
    }

    public static String a() {
        return a(System.currentTimeMillis());
    }

    public static String a(long j) {
        try {
            return new SimpleDateFormat(DateUtil.dateFormatYMDHMS, Locale.US).format(new Date(j));
        } catch (Exception e) {
            return new Date().toString();
        }
    }

    public static String a(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(DateUtil.dateFormatYMDHMS, Locale.US).format(date);
        } catch (Exception e) {
            return new Date().toString();
        }
    }

    private static byte[] a(byte[] bArr, int i, String str) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        w.c("rqdp{  enD:} %d %d", Integer.valueOf(bArr.length), Integer.valueOf(i));
        try {
            af a = a.a(i);
            if (a == null) {
                return null;
            }
            a.a(str);
            return a.b(bArr);
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static byte[] b(byte[] bArr, int i, String str) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        try {
            af a = a.a(i);
            if (a == null) {
                return null;
            }
            a.a(str);
            return a.a(bArr);
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            w.d("encrytype %d %s", Integer.valueOf(i), str);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x0095 A:{SYNTHETIC, EDGE_INSN: B:48:0x0095->B:49:? ?: BREAK  , Splitter: B:48:0x0095} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x007c A:{Catch:{ Throwable -> 0x0059 }} */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00a4 A:{SYNTHETIC, Splitter: B:51:0x00a4} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004e A:{LOOP_START, Catch:{ Throwable -> 0x0059 }, LOOP:0: B:17:0x004e->B:20:0x0058} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x007c A:{Catch:{ Throwable -> 0x0059 }} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0095 A:{SYNTHETIC, EDGE_INSN: B:48:0x0095->B:49:? ?: BREAK  , EDGE_INSN: B:48:0x0095->B:49:? ?: BREAK  , Splitter: B:48:0x0095} */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00a4 A:{SYNTHETIC, Splitter: B:51:0x00a4} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0084 A:{SYNTHETIC, Splitter: B:41:0x0084} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0089 A:{SYNTHETIC, Splitter: B:44:0x0089} */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0060 A:{Catch:{ all -> 0x0081 }} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0065 A:{SYNTHETIC, Splitter: B:27:0x0065} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x006a A:{SYNTHETIC, Splitter: B:30:0x006a} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0084 A:{SYNTHETIC, Splitter: B:41:0x0084} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0089 A:{SYNTHETIC, Splitter: B:44:0x0089} */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0060 A:{Catch:{ all -> 0x0081 }} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0065 A:{SYNTHETIC, Splitter: B:27:0x0065} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x006a A:{SYNTHETIC, Splitter: B:30:0x006a} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0084 A:{SYNTHETIC, Splitter: B:41:0x0084} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0089 A:{SYNTHETIC, Splitter: B:44:0x0089} */
    public static byte[] a(java.io.File r9, java.lang.String r10) {
        /*
        r0 = 0;
        r8 = 0;
        r1 = "rqdp{  ZF start}";
        r2 = new java.lang.Object[r8];
        com.tencent.bugly.imsdk.proguard.w.c(r1, r2);
        r1 = "buglyCacheLog.txt";
        if (r9 == 0) goto L_0x00ed;
    L_0x000f:
        r2 = r9.exists();	 Catch:{ Throwable -> 0x00df, all -> 0x00d1 }
        if (r2 == 0) goto L_0x00ed;
    L_0x0015:
        r2 = r9.canRead();	 Catch:{ Throwable -> 0x00df, all -> 0x00d1 }
        if (r2 == 0) goto L_0x00ed;
    L_0x001b:
        r2 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x00df, all -> 0x00d1 }
        r2.<init>(r9);	 Catch:{ Throwable -> 0x00df, all -> 0x00d1 }
        r1 = r9.getName();	 Catch:{ Throwable -> 0x00e4, all -> 0x00d6 }
        r3 = r2;
    L_0x0025:
        r2 = "UTF-8";
        r2 = r10.getBytes(r2);	 Catch:{ Throwable -> 0x00e9, all -> 0x00db }
        r4 = new java.io.ByteArrayInputStream;	 Catch:{ Throwable -> 0x00e9, all -> 0x00db }
        r4.<init>(r2);	 Catch:{ Throwable -> 0x00e9, all -> 0x00db }
        r5 = new java.io.ByteArrayOutputStream;	 Catch:{ Throwable -> 0x00e9, all -> 0x00db }
        r5.<init>();	 Catch:{ Throwable -> 0x00e9, all -> 0x00db }
        r2 = new java.util.zip.ZipOutputStream;	 Catch:{ Throwable -> 0x00e9, all -> 0x00db }
        r2.<init>(r5);	 Catch:{ Throwable -> 0x00e9, all -> 0x00db }
        r6 = 8;
        r2.setMethod(r6);	 Catch:{ Throwable -> 0x0059 }
        r6 = new java.util.zip.ZipEntry;	 Catch:{ Throwable -> 0x0059 }
        r6.<init>(r1);	 Catch:{ Throwable -> 0x0059 }
        r2.putNextEntry(r6);	 Catch:{ Throwable -> 0x0059 }
        r1 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r1 = new byte[r1];	 Catch:{ Throwable -> 0x0059 }
        if (r3 == 0) goto L_0x0076;
    L_0x004e:
        r6 = r3.read(r1);	 Catch:{ Throwable -> 0x0059 }
        if (r6 <= 0) goto L_0x0076;
    L_0x0054:
        r7 = 0;
        r2.write(r1, r7, r6);	 Catch:{ Throwable -> 0x0059 }
        goto L_0x004e;
    L_0x0059:
        r1 = move-exception;
    L_0x005a:
        r4 = com.tencent.bugly.imsdk.proguard.w.a(r1);	 Catch:{ all -> 0x0081 }
        if (r4 != 0) goto L_0x0063;
    L_0x0060:
        r1.printStackTrace();	 Catch:{ all -> 0x0081 }
    L_0x0063:
        if (r3 == 0) goto L_0x0068;
    L_0x0065:
        r3.close();	 Catch:{ IOException -> 0x00bd }
    L_0x0068:
        if (r2 == 0) goto L_0x006d;
    L_0x006a:
        r2.close();	 Catch:{ IOException -> 0x00c2 }
    L_0x006d:
        r1 = "rqdp{  ZF end}";
        r2 = new java.lang.Object[r8];
        com.tencent.bugly.imsdk.proguard.w.c(r1, r2);
    L_0x0075:
        return r0;
    L_0x0076:
        r6 = r4.read(r1);	 Catch:{ Throwable -> 0x0059 }
        if (r6 <= 0) goto L_0x0095;
    L_0x007c:
        r7 = 0;
        r2.write(r1, r7, r6);	 Catch:{ Throwable -> 0x0059 }
        goto L_0x0076;
    L_0x0081:
        r0 = move-exception;
    L_0x0082:
        if (r3 == 0) goto L_0x0087;
    L_0x0084:
        r3.close();	 Catch:{ IOException -> 0x00c7 }
    L_0x0087:
        if (r2 == 0) goto L_0x008c;
    L_0x0089:
        r2.close();	 Catch:{ IOException -> 0x00cc }
    L_0x008c:
        r1 = "rqdp{  ZF end}";
        r2 = new java.lang.Object[r8];
        com.tencent.bugly.imsdk.proguard.w.c(r1, r2);
        throw r0;
    L_0x0095:
        r2.closeEntry();	 Catch:{ Throwable -> 0x0059 }
        r2.flush();	 Catch:{ Throwable -> 0x0059 }
        r2.finish();	 Catch:{ Throwable -> 0x0059 }
        r0 = r5.toByteArray();	 Catch:{ Throwable -> 0x0059 }
        if (r3 == 0) goto L_0x00a7;
    L_0x00a4:
        r3.close();	 Catch:{ IOException -> 0x00b3 }
    L_0x00a7:
        r2.close();	 Catch:{ IOException -> 0x00b8 }
    L_0x00aa:
        r1 = "rqdp{  ZF end}";
        r2 = new java.lang.Object[r8];
        com.tencent.bugly.imsdk.proguard.w.c(r1, r2);
        goto L_0x0075;
    L_0x00b3:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00a7;
    L_0x00b8:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00aa;
    L_0x00bd:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0068;
    L_0x00c2:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x006d;
    L_0x00c7:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0087;
    L_0x00cc:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x008c;
    L_0x00d1:
        r1 = move-exception;
        r2 = r0;
        r3 = r0;
        r0 = r1;
        goto L_0x0082;
    L_0x00d6:
        r1 = move-exception;
        r3 = r2;
        r2 = r0;
        r0 = r1;
        goto L_0x0082;
    L_0x00db:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
        goto L_0x0082;
    L_0x00df:
        r1 = move-exception;
        r2 = r0;
        r3 = r0;
        goto L_0x005a;
    L_0x00e4:
        r1 = move-exception;
        r3 = r2;
        r2 = r0;
        goto L_0x005a;
    L_0x00e9:
        r1 = move-exception;
        r2 = r0;
        goto L_0x005a;
    L_0x00ed:
        r3 = r0;
        goto L_0x0025;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.proguard.y.a(java.io.File, java.lang.String):byte[]");
    }

    public static byte[] a(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        String str = "[Util] Zip %d bytes data with type %s";
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(bArr.length);
        objArr[1] = i == 2 ? "Gzip" : "zip";
        w.c(str, objArr);
        try {
            aa a = z.a(i);
            if (a == null) {
                return null;
            }
            return a.a(bArr);
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static byte[] b(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        String str = "[Util] Unzip %d bytes data with type %s";
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(bArr.length);
        objArr[1] = i == 2 ? "Gzip" : "zip";
        w.c(str, objArr);
        try {
            aa a = z.a(i);
            if (a == null) {
                return null;
            }
            return a.b(bArr);
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static byte[] a(byte[] bArr, int i, int i2, String str) {
        byte[] bArr2 = null;
        if (bArr == null) {
            return bArr2;
        }
        try {
            return a(a(bArr, 2), 1, str);
        } catch (Throwable th) {
            if (w.a(th)) {
                return bArr2;
            }
            th.printStackTrace();
            return bArr2;
        }
    }

    public static byte[] b(byte[] bArr, int i, int i2, String str) {
        try {
            return b(b(bArr, 1, str), 2);
        } catch (Throwable e) {
            if (!w.a(e)) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static long b() {
        try {
            return (((System.currentTimeMillis() + ((long) TimeZone.getDefault().getRawOffset())) / 86400000) * 86400000) - ((long) TimeZone.getDefault().getRawOffset());
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return -1;
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & 255);
            if (toHexString.length() == 1) {
                stringBuffer.append(PushConstants.PUSH_TYPE_NOTIFY);
            }
            stringBuffer.append(toHexString);
        }
        return stringBuffer.toString().toUpperCase();
    }

    public static String b(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "NULL";
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(bArr);
            return a(instance.digest());
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:70:0x00ec A:{SYNTHETIC, Splitter: B:70:0x00ec} */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00f1 A:{SYNTHETIC, Splitter: B:73:0x00f1} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x009a A:{Catch:{ all -> 0x010c }} */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x009f A:{SYNTHETIC, Splitter: B:42:0x009f} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a4 A:{SYNTHETIC, Splitter: B:45:0x00a4} */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00ec A:{SYNTHETIC, Splitter: B:70:0x00ec} */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00f1 A:{SYNTHETIC, Splitter: B:73:0x00f1} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x009a A:{Catch:{ all -> 0x010c }} */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x009f A:{SYNTHETIC, Splitter: B:42:0x009f} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a4 A:{SYNTHETIC, Splitter: B:45:0x00a4} */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00ec A:{SYNTHETIC, Splitter: B:70:0x00ec} */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00f1 A:{SYNTHETIC, Splitter: B:73:0x00f1} */
    public static boolean a(java.io.File r6, java.io.File r7, int r8) {
        /*
        r3 = 0;
        r0 = 0;
        r1 = "rqdp{  ZF start}";
        r2 = new java.lang.Object[r0];
        com.tencent.bugly.imsdk.proguard.w.c(r1, r2);
        if (r6 == 0) goto L_0x0014;
    L_0x000c:
        if (r7 == 0) goto L_0x0014;
    L_0x000e:
        r1 = r6.equals(r7);
        if (r1 == 0) goto L_0x001d;
    L_0x0014:
        r1 = "rqdp{  err ZF 1R!}";
        r2 = new java.lang.Object[r0];
        com.tencent.bugly.imsdk.proguard.w.d(r1, r2);
    L_0x001c:
        return r0;
    L_0x001d:
        r1 = r6.exists();
        if (r1 == 0) goto L_0x0029;
    L_0x0023:
        r1 = r6.canRead();
        if (r1 != 0) goto L_0x0032;
    L_0x0029:
        r1 = "rqdp{  !sFile.exists() || !sFile.canRead(),pls check ,return!}";
        r2 = new java.lang.Object[r0];
        com.tencent.bugly.imsdk.proguard.w.d(r1, r2);
        goto L_0x001c;
    L_0x0032:
        r1 = r7.getParentFile();	 Catch:{ Throwable -> 0x00b1 }
        if (r1 == 0) goto L_0x0049;
    L_0x0038:
        r1 = r7.getParentFile();	 Catch:{ Throwable -> 0x00b1 }
        r1 = r1.exists();	 Catch:{ Throwable -> 0x00b1 }
        if (r1 != 0) goto L_0x0049;
    L_0x0042:
        r1 = r7.getParentFile();	 Catch:{ Throwable -> 0x00b1 }
        r1.mkdirs();	 Catch:{ Throwable -> 0x00b1 }
    L_0x0049:
        r1 = r7.exists();	 Catch:{ Throwable -> 0x00b1 }
        if (r1 != 0) goto L_0x0052;
    L_0x004f:
        r7.createNewFile();	 Catch:{ Throwable -> 0x00b1 }
    L_0x0052:
        r1 = r7.exists();
        if (r1 == 0) goto L_0x001c;
    L_0x0058:
        r1 = r7.canRead();
        if (r1 == 0) goto L_0x001c;
    L_0x005e:
        r4 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x010f, all -> 0x00e7 }
        r4.<init>(r6);	 Catch:{ Throwable -> 0x010f, all -> 0x00e7 }
        r2 = new java.util.zip.ZipOutputStream;	 Catch:{ Throwable -> 0x0112, all -> 0x0107 }
        r1 = new java.io.BufferedOutputStream;	 Catch:{ Throwable -> 0x0112, all -> 0x0107 }
        r5 = new java.io.FileOutputStream;	 Catch:{ Throwable -> 0x0112, all -> 0x0107 }
        r5.<init>(r7);	 Catch:{ Throwable -> 0x0112, all -> 0x0107 }
        r1.<init>(r5);	 Catch:{ Throwable -> 0x0112, all -> 0x0107 }
        r2.<init>(r1);	 Catch:{ Throwable -> 0x0112, all -> 0x0107 }
        r1 = 8;
        r2.setMethod(r1);	 Catch:{ Throwable -> 0x0092, all -> 0x010a }
        r1 = new java.util.zip.ZipEntry;	 Catch:{ Throwable -> 0x0092, all -> 0x010a }
        r3 = r6.getName();	 Catch:{ Throwable -> 0x0092, all -> 0x010a }
        r1.<init>(r3);	 Catch:{ Throwable -> 0x0092, all -> 0x010a }
        r2.putNextEntry(r1);	 Catch:{ Throwable -> 0x0092, all -> 0x010a }
        r1 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r1 = new byte[r1];	 Catch:{ Throwable -> 0x0092, all -> 0x010a }
    L_0x0087:
        r3 = r4.read(r1);	 Catch:{ Throwable -> 0x0092, all -> 0x010a }
        if (r3 <= 0) goto L_0x00bc;
    L_0x008d:
        r5 = 0;
        r2.write(r1, r5, r3);	 Catch:{ Throwable -> 0x0092, all -> 0x010a }
        goto L_0x0087;
    L_0x0092:
        r1 = move-exception;
        r3 = r4;
    L_0x0094:
        r4 = com.tencent.bugly.imsdk.proguard.w.a(r1);	 Catch:{ all -> 0x010c }
        if (r4 != 0) goto L_0x009d;
    L_0x009a:
        r1.printStackTrace();	 Catch:{ all -> 0x010c }
    L_0x009d:
        if (r3 == 0) goto L_0x00a2;
    L_0x009f:
        r3.close();	 Catch:{ IOException -> 0x00dd }
    L_0x00a2:
        if (r2 == 0) goto L_0x00a7;
    L_0x00a4:
        r2.close();	 Catch:{ IOException -> 0x00e2 }
    L_0x00a7:
        r1 = "rqdp{  ZF end}";
        r2 = new java.lang.Object[r0];
        com.tencent.bugly.imsdk.proguard.w.c(r1, r2);
        goto L_0x001c;
    L_0x00b1:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x0052;
    L_0x00b8:
        r1.printStackTrace();
        goto L_0x0052;
    L_0x00bc:
        r2.flush();	 Catch:{ Throwable -> 0x0092, all -> 0x010a }
        r2.closeEntry();	 Catch:{ Throwable -> 0x0092, all -> 0x010a }
        r4.close();	 Catch:{ IOException -> 0x00d3 }
    L_0x00c5:
        r2.close();	 Catch:{ IOException -> 0x00d8 }
    L_0x00c8:
        r1 = "rqdp{  ZF end}";
        r0 = new java.lang.Object[r0];
        com.tencent.bugly.imsdk.proguard.w.c(r1, r0);
        r0 = 1;
        goto L_0x001c;
    L_0x00d3:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00c5;
    L_0x00d8:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00c8;
    L_0x00dd:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00a2;
    L_0x00e2:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00a7;
    L_0x00e7:
        r1 = move-exception;
        r2 = r3;
        r4 = r3;
    L_0x00ea:
        if (r4 == 0) goto L_0x00ef;
    L_0x00ec:
        r4.close();	 Catch:{ IOException -> 0x00fd }
    L_0x00ef:
        if (r2 == 0) goto L_0x00f4;
    L_0x00f1:
        r2.close();	 Catch:{ IOException -> 0x0102 }
    L_0x00f4:
        r2 = "rqdp{  ZF end}";
        r0 = new java.lang.Object[r0];
        com.tencent.bugly.imsdk.proguard.w.c(r2, r0);
        throw r1;
    L_0x00fd:
        r3 = move-exception;
        r3.printStackTrace();
        goto L_0x00ef;
    L_0x0102:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x00f4;
    L_0x0107:
        r1 = move-exception;
        r2 = r3;
        goto L_0x00ea;
    L_0x010a:
        r1 = move-exception;
        goto L_0x00ea;
    L_0x010c:
        r1 = move-exception;
        r4 = r3;
        goto L_0x00ea;
    L_0x010f:
        r1 = move-exception;
        r2 = r3;
        goto L_0x0094;
    L_0x0112:
        r1 = move-exception;
        r2 = r3;
        r3 = r4;
        goto L_0x0094;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.proguard.y.a(java.io.File, java.io.File, int):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0049 A:{Catch:{ all -> 0x00a6 }} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004e A:{SYNTHETIC, Splitter: B:17:0x004e} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0053 A:{SYNTHETIC, Splitter: B:20:0x0053} */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0091 A:{SYNTHETIC, Splitter: B:46:0x0091} */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0096 A:{SYNTHETIC, Splitter: B:49:0x0096} */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0049 A:{Catch:{ all -> 0x00a6 }} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004e A:{SYNTHETIC, Splitter: B:17:0x004e} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0053 A:{SYNTHETIC, Splitter: B:20:0x0053} */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0091 A:{SYNTHETIC, Splitter: B:46:0x0091} */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0096 A:{SYNTHETIC, Splitter: B:49:0x0096} */
    public static java.util.ArrayList<java.lang.String> a(android.content.Context r6, java.lang.String[] r7) {
        /*
        r1 = 0;
        r0 = new java.util.ArrayList;
        r0.<init>();
        r2 = com.tencent.bugly.imsdk.crashreport.common.info.a.a(r6);
        r2 = r2.H();
        if (r2 == 0) goto L_0x0021;
    L_0x0010:
        r0 = new java.util.ArrayList;
        r0.<init>();
        r1 = new java.lang.String;
        r2 = "unknown(low memory)";
        r1.<init>(r2);
        r0.add(r1);
    L_0x0020:
        return r0;
    L_0x0021:
        r2 = java.lang.Runtime.getRuntime();	 Catch:{ Throwable -> 0x00a9, all -> 0x008d }
        r4 = r2.exec(r7);	 Catch:{ Throwable -> 0x00a9, all -> 0x008d }
        r3 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x00a9, all -> 0x008d }
        r2 = new java.io.InputStreamReader;	 Catch:{ Throwable -> 0x00a9, all -> 0x008d }
        r5 = r4.getInputStream();	 Catch:{ Throwable -> 0x00a9, all -> 0x008d }
        r2.<init>(r5);	 Catch:{ Throwable -> 0x00a9, all -> 0x008d }
        r3.<init>(r2);	 Catch:{ Throwable -> 0x00a9, all -> 0x008d }
    L_0x0037:
        r2 = r3.readLine();	 Catch:{ Throwable -> 0x0041, all -> 0x00a4 }
        if (r2 == 0) goto L_0x0058;
    L_0x003d:
        r0.add(r2);	 Catch:{ Throwable -> 0x0041, all -> 0x00a4 }
        goto L_0x0037;
    L_0x0041:
        r0 = move-exception;
        r2 = r1;
    L_0x0043:
        r4 = com.tencent.bugly.imsdk.proguard.w.a(r0);	 Catch:{ all -> 0x00a6 }
        if (r4 != 0) goto L_0x004c;
    L_0x0049:
        r0.printStackTrace();	 Catch:{ all -> 0x00a6 }
    L_0x004c:
        if (r3 == 0) goto L_0x0051;
    L_0x004e:
        r3.close();	 Catch:{ IOException -> 0x0083 }
    L_0x0051:
        if (r2 == 0) goto L_0x0056;
    L_0x0053:
        r2.close();	 Catch:{ IOException -> 0x0088 }
    L_0x0056:
        r0 = r1;
        goto L_0x0020;
    L_0x0058:
        r2 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x0041, all -> 0x00a4 }
        r5 = new java.io.InputStreamReader;	 Catch:{ Throwable -> 0x0041, all -> 0x00a4 }
        r4 = r4.getErrorStream();	 Catch:{ Throwable -> 0x0041, all -> 0x00a4 }
        r5.<init>(r4);	 Catch:{ Throwable -> 0x0041, all -> 0x00a4 }
        r2.<init>(r5);	 Catch:{ Throwable -> 0x0041, all -> 0x00a4 }
    L_0x0066:
        r4 = r2.readLine();	 Catch:{ Throwable -> 0x0070 }
        if (r4 == 0) goto L_0x0072;
    L_0x006c:
        r0.add(r4);	 Catch:{ Throwable -> 0x0070 }
        goto L_0x0066;
    L_0x0070:
        r0 = move-exception;
        goto L_0x0043;
    L_0x0072:
        r3.close();	 Catch:{ IOException -> 0x007e }
    L_0x0075:
        r2.close();	 Catch:{ IOException -> 0x0079 }
        goto L_0x0020;
    L_0x0079:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0020;
    L_0x007e:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0075;
    L_0x0083:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0051;
    L_0x0088:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0056;
    L_0x008d:
        r0 = move-exception;
        r3 = r1;
    L_0x008f:
        if (r3 == 0) goto L_0x0094;
    L_0x0091:
        r3.close();	 Catch:{ IOException -> 0x009a }
    L_0x0094:
        if (r1 == 0) goto L_0x0099;
    L_0x0096:
        r1.close();	 Catch:{ IOException -> 0x009f }
    L_0x0099:
        throw r0;
    L_0x009a:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x0094;
    L_0x009f:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0099;
    L_0x00a4:
        r0 = move-exception;
        goto L_0x008f;
    L_0x00a6:
        r0 = move-exception;
        r1 = r2;
        goto L_0x008f;
    L_0x00a9:
        r0 = move-exception;
        r2 = r1;
        r3 = r1;
        goto L_0x0043;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.proguard.y.a(android.content.Context, java.lang.String[]):java.util.ArrayList<java.lang.String>");
    }

    public static String a(Context context, String str) {
        if (str == null || str.trim().equals("")) {
            return "";
        }
        if (a == null) {
            a = new HashMap();
            List<String> a = a(context, new String[]{"/system/bin/sh", "-c", "getprop"});
            if (a != null && a.size() > 0) {
                w.a(y.class, "Successfully get 'getprop' list.", new Object[0]);
                Pattern compile = Pattern.compile("\\[(.+)\\]: \\[(.*)\\]");
                for (String matcher : a) {
                    Matcher matcher2 = compile.matcher(matcher);
                    if (matcher2.find()) {
                        a.put(matcher2.group(1), matcher2.group(2));
                    }
                }
                w.a(y.class, "System properties number: %dffffdsfsdfff.", Integer.valueOf(a.size()));
            }
        }
        if (a.containsKey(str)) {
            return (String) a.get(str);
        }
        return "fail";
    }

    public static void b(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean a(String str) {
        if (str == null || str.trim().length() <= 0) {
            return true;
        }
        return false;
    }

    public static void b(String str) {
        if (str != null) {
            File file = new File(str);
            if (file.isFile() && file.exists() && file.canWrite()) {
                file.delete();
            }
        }
    }

    public static byte[] c(long j) {
        try {
            return (j).getBytes(Constants.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long c(byte[] bArr) {
        long j = -1;
        if (bArr == null) {
            return j;
        }
        try {
            return Long.parseLong(new String(bArr, Constants.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return j;
        }
    }

    public static Context a(Context context) {
        if (context == null) {
            return context;
        }
        Context origApplicationContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
        return origApplicationContext != null ? origApplicationContext : context;
    }

    public static String b(Throwable th) {
        if (th == null) {
            return "";
        }
        Writer stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    public static void a(Class<?> cls, String str, Object obj, Object obj2) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(null, obj);
        } catch (Exception e) {
        }
    }

    public static Object a(String str, String str2, Object obj, Class<?>[] clsArr, Object[] objArr) {
        Object obj2 = null;
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(null, objArr);
        } catch (Exception e) {
            return obj2;
        }
    }

    public static void a(Parcel parcel, Map<String, PlugInBean> map) {
        int i = 0;
        if (map == null || map.size() <= 0) {
            parcel.writeBundle(null);
            return;
        }
        int size = map.size();
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = new ArrayList(size);
        for (Entry entry : map.entrySet()) {
            arrayList.add(entry.getKey());
            arrayList2.add(entry.getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putInt("pluginNum", arrayList.size());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            bundle.putString("pluginKey" + i2, (String) arrayList.get(i2));
        }
        while (i < arrayList.size()) {
            bundle.putString("pluginVal" + i + "plugInId", ((PlugInBean) arrayList2.get(i)).a);
            bundle.putString("pluginVal" + i + "plugInUUID", ((PlugInBean) arrayList2.get(i)).c);
            bundle.putString("pluginVal" + i + "plugInVersion", ((PlugInBean) arrayList2.get(i)).b);
            i++;
        }
        parcel.writeBundle(bundle);
    }

    public static Map<String, PlugInBean> a(Parcel parcel) {
        Bundle readBundle = parcel.readBundle();
        if (readBundle == null) {
            return null;
        }
        int i;
        HashMap hashMap;
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        int intValue = ((Integer) readBundle.get("pluginNum")).intValue();
        for (i = 0; i < intValue; i++) {
            arrayList.add(readBundle.getString("pluginKey" + i));
        }
        for (i = 0; i < intValue; i++) {
            arrayList2.add(new PlugInBean(readBundle.getString("pluginVal" + i + "plugInId"), readBundle.getString("pluginVal" + i + "plugInVersion"), readBundle.getString("pluginVal" + i + "plugInUUID")));
        }
        if (arrayList.size() == arrayList2.size()) {
            HashMap hashMap2 = new HashMap(arrayList.size());
            for (i = 0; i < arrayList.size(); i++) {
                hashMap2.put(arrayList.get(i), PlugInBean.class.cast(arrayList2.get(i)));
            }
            hashMap = hashMap2;
        } else {
            w.e("map plugin parcel error!", new Object[0]);
            Map hashMap3 = null;
        }
        return hashMap3;
    }

    public static void b(Parcel parcel, Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            parcel.writeBundle(null);
            return;
        }
        int size = map.size();
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = new ArrayList(size);
        for (Entry entry : map.entrySet()) {
            arrayList.add(entry.getKey());
            arrayList2.add(entry.getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("keys", arrayList);
        bundle.putStringArrayList("values", arrayList2);
        parcel.writeBundle(bundle);
    }

    public static Map<String, String> b(Parcel parcel) {
        int i = 0;
        Bundle readBundle = parcel.readBundle();
        if (readBundle == null) {
            return null;
        }
        HashMap hashMap;
        List stringArrayList = readBundle.getStringArrayList("keys");
        List stringArrayList2 = readBundle.getStringArrayList("values");
        if (stringArrayList == null || stringArrayList2 == null || stringArrayList.size() != stringArrayList2.size()) {
            w.e("map parcel error!", new Object[0]);
            Map hashMap2 = null;
        } else {
            HashMap hashMap3 = new HashMap(stringArrayList.size());
            while (i < stringArrayList.size()) {
                hashMap3.put(stringArrayList.get(i), stringArrayList2.get(i));
                i++;
            }
            hashMap2 = hashMap3;
        }
        return hashMap2;
    }

    public static byte[] a(Parcelable parcelable) {
        Parcel obtain = Parcel.obtain();
        parcelable.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }

    public static <T> T a(byte[] bArr, Creator<T> creator) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        try {
            T createFromParcel = creator.createFromParcel(obtain);
            if (obtain == null) {
                return createFromParcel;
            }
            obtain.recycle();
            return createFromParcel;
        } catch (Throwable th) {
            if (obtain != null) {
                obtain.recycle();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x0116 A:{SYNTHETIC, Splitter: B:56:0x0116} */
    public static java.lang.String a(android.content.Context r7, int r8, java.lang.String r9) {
        /*
        r6 = 4;
        r5 = 3;
        r4 = 2;
        r3 = 1;
        r2 = 0;
        r0 = "android.permission.READ_LOGS";
        r0 = com.tencent.bugly.imsdk.crashreport.common.info.AppInfo.a(r7, r0);
        if (r0 != 0) goto L_0x0018;
    L_0x000e:
        r0 = "no read_log permission!";
        r1 = new java.lang.Object[r2];
        com.tencent.bugly.imsdk.proguard.w.d(r0, r1);
        r0 = 0;
    L_0x0017:
        return r0;
    L_0x0018:
        if (r9 != 0) goto L_0x00bb;
    L_0x001a:
        r0 = new java.lang.String[r6];
        r1 = "logcat";
        r0[r2] = r1;
        r1 = "-d";
        r0[r3] = r1;
        r1 = "-v";
        r0[r4] = r1;
        r1 = "threadtime";
        r0[r5] = r1;
    L_0x0030:
        r1 = 0;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r2 = java.lang.Runtime.getRuntime();	 Catch:{ Throwable -> 0x013e }
        r2 = r2.exec(r0);	 Catch:{ Throwable -> 0x013e }
        r0 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x006e, all -> 0x013b }
        r1 = new java.io.InputStreamReader;	 Catch:{ Throwable -> 0x006e, all -> 0x013b }
        r4 = r2.getInputStream();	 Catch:{ Throwable -> 0x006e, all -> 0x013b }
        r1.<init>(r4);	 Catch:{ Throwable -> 0x006e, all -> 0x013b }
        r0.<init>(r1);	 Catch:{ Throwable -> 0x006e, all -> 0x013b }
    L_0x004c:
        r1 = r0.readLine();	 Catch:{ Throwable -> 0x006e, all -> 0x013b }
        if (r1 == 0) goto L_0x00dc;
    L_0x0052:
        r1 = r3.append(r1);	 Catch:{ Throwable -> 0x006e, all -> 0x013b }
        r4 = "\n";
        r1.append(r4);	 Catch:{ Throwable -> 0x006e, all -> 0x013b }
        if (r8 <= 0) goto L_0x004c;
    L_0x005e:
        r1 = r3.length();	 Catch:{ Throwable -> 0x006e, all -> 0x013b }
        if (r1 <= r8) goto L_0x004c;
    L_0x0064:
        r1 = 0;
        r4 = r3.length();	 Catch:{ Throwable -> 0x006e, all -> 0x013b }
        r4 = r4 - r8;
        r3.delete(r1, r4);	 Catch:{ Throwable -> 0x006e, all -> 0x013b }
        goto L_0x004c;
    L_0x006e:
        r0 = move-exception;
        r1 = r2;
    L_0x0070:
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r0);	 Catch:{ all -> 0x0113 }
        if (r2 != 0) goto L_0x0079;
    L_0x0076:
        r0.printStackTrace();	 Catch:{ all -> 0x0113 }
    L_0x0079:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0113 }
        r4 = "\n[error:";
        r2.<init>(r4);	 Catch:{ all -> 0x0113 }
        r0 = r0.toString();	 Catch:{ all -> 0x0113 }
        r0 = r2.append(r0);	 Catch:{ all -> 0x0113 }
        r2 = "]";
        r0 = r0.append(r2);	 Catch:{ all -> 0x0113 }
        r0 = r0.toString();	 Catch:{ all -> 0x0113 }
        r0 = r3.append(r0);	 Catch:{ all -> 0x0113 }
        r0 = r0.toString();	 Catch:{ all -> 0x0113 }
        if (r1 == 0) goto L_0x0017;
    L_0x009e:
        r2 = r1.getOutputStream();	 Catch:{ IOException -> 0x0109 }
        r2.close();	 Catch:{ IOException -> 0x0109 }
    L_0x00a5:
        r2 = r1.getInputStream();	 Catch:{ IOException -> 0x010e }
        r2.close();	 Catch:{ IOException -> 0x010e }
    L_0x00ac:
        r1 = r1.getErrorStream();	 Catch:{ IOException -> 0x00b5 }
        r1.close();	 Catch:{ IOException -> 0x00b5 }
        goto L_0x0017;
    L_0x00b5:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0017;
    L_0x00bb:
        r0 = 6;
        r0 = new java.lang.String[r0];
        r1 = "logcat";
        r0[r2] = r1;
        r1 = "-d";
        r0[r3] = r1;
        r1 = "-v";
        r0[r4] = r1;
        r1 = "threadtime";
        r0[r5] = r1;
        r1 = "-s";
        r0[r6] = r1;
        r1 = 5;
        r0[r1] = r9;
        goto L_0x0030;
    L_0x00dc:
        r0 = r3.toString();	 Catch:{ Throwable -> 0x006e, all -> 0x013b }
        if (r2 == 0) goto L_0x0017;
    L_0x00e2:
        r1 = r2.getOutputStream();	 Catch:{ IOException -> 0x00ff }
        r1.close();	 Catch:{ IOException -> 0x00ff }
    L_0x00e9:
        r1 = r2.getInputStream();	 Catch:{ IOException -> 0x0104 }
        r1.close();	 Catch:{ IOException -> 0x0104 }
    L_0x00f0:
        r1 = r2.getErrorStream();	 Catch:{ IOException -> 0x00f9 }
        r1.close();	 Catch:{ IOException -> 0x00f9 }
        goto L_0x0017;
    L_0x00f9:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0017;
    L_0x00ff:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00e9;
    L_0x0104:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00f0;
    L_0x0109:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x00a5;
    L_0x010e:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x00ac;
    L_0x0113:
        r0 = move-exception;
    L_0x0114:
        if (r1 == 0) goto L_0x012b;
    L_0x0116:
        r2 = r1.getOutputStream();	 Catch:{ IOException -> 0x012c }
        r2.close();	 Catch:{ IOException -> 0x012c }
    L_0x011d:
        r2 = r1.getInputStream();	 Catch:{ IOException -> 0x0131 }
        r2.close();	 Catch:{ IOException -> 0x0131 }
    L_0x0124:
        r1 = r1.getErrorStream();	 Catch:{ IOException -> 0x0136 }
        r1.close();	 Catch:{ IOException -> 0x0136 }
    L_0x012b:
        throw r0;
    L_0x012c:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x011d;
    L_0x0131:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x0124;
    L_0x0136:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x012b;
    L_0x013b:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0114;
    L_0x013e:
        r0 = move-exception;
        goto L_0x0070;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.proguard.y.a(android.content.Context, int, java.lang.String):java.lang.String");
    }

    public static Map<String, String> a(int i, boolean z) {
        Map<String, String> hashMap = new HashMap(12);
        Map allStackTraces = Thread.getAllStackTraces();
        if (allStackTraces == null) {
            return null;
        }
        Thread.currentThread().getId();
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : allStackTraces.entrySet()) {
            stringBuilder.setLength(0);
            if (!(entry.getValue() == null || ((StackTraceElement[]) entry.getValue()).length == 0)) {
                for (StackTraceElement stackTraceElement : (StackTraceElement[]) entry.getValue()) {
                    if (i > 0 && stringBuilder.length() >= i) {
                        stringBuilder.append("\n[Stack over limit size :" + i + " , has been cutted !]");
                        break;
                    }
                    stringBuilder.append(stackTraceElement.toString()).append("\n");
                }
                hashMap.put(((Thread) entry.getKey()).getName() + l.s + ((Thread) entry.getKey()).getId() + l.t, stringBuilder.toString());
            }
        }
        return hashMap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0053 A:{Catch:{ Exception -> 0x0057 }} */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0032 A:{SYNTHETIC, Splitter: B:16:0x0032} */
    public static synchronized byte[] a(int r7) {
        /*
        r1 = 0;
        r3 = com.tencent.bugly.imsdk.proguard.y.class;
        monitor-enter(r3);
        r0 = 16;
        r0 = new byte[r0];	 Catch:{ Exception -> 0x0022, all -> 0x004f }
        r2 = new java.io.DataInputStream;	 Catch:{ Exception -> 0x0022, all -> 0x004f }
        r4 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x0022, all -> 0x004f }
        r5 = new java.io.File;	 Catch:{ Exception -> 0x0022, all -> 0x004f }
        r6 = "/dev/urandom";
        r5.<init>(r6);	 Catch:{ Exception -> 0x0022, all -> 0x004f }
        r4.<init>(r5);	 Catch:{ Exception -> 0x0022, all -> 0x004f }
        r2.<init>(r4);	 Catch:{ Exception -> 0x0022, all -> 0x004f }
        r2.readFully(r0);	 Catch:{ Exception -> 0x0068 }
        r2.close();	 Catch:{ Exception -> 0x0057 }
    L_0x0020:
        monitor-exit(r3);
        return r0;
    L_0x0022:
        r0 = move-exception;
        r2 = r1;
    L_0x0024:
        r4 = "Failed to read from /dev/urandom : %s";
        r5 = 1;
        r5 = new java.lang.Object[r5];	 Catch:{ all -> 0x0066 }
        r6 = 0;
        r5[r6] = r0;	 Catch:{ all -> 0x0066 }
        com.tencent.bugly.imsdk.proguard.w.e(r4, r5);	 Catch:{ all -> 0x0066 }
        if (r2 == 0) goto L_0x0035;
    L_0x0032:
        r2.close();	 Catch:{ Exception -> 0x0057 }
    L_0x0035:
        r0 = "AES";
        r0 = javax.crypto.KeyGenerator.getInstance(r0);	 Catch:{ Exception -> 0x0057 }
        r2 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r4 = new java.security.SecureRandom;	 Catch:{ Exception -> 0x0057 }
        r4.<init>();	 Catch:{ Exception -> 0x0057 }
        r0.init(r2, r4);	 Catch:{ Exception -> 0x0057 }
        r0 = r0.generateKey();	 Catch:{ Exception -> 0x0057 }
        r0 = r0.getEncoded();	 Catch:{ Exception -> 0x0057 }
        goto L_0x0020;
    L_0x004f:
        r0 = move-exception;
        r2 = r1;
    L_0x0051:
        if (r2 == 0) goto L_0x0056;
    L_0x0053:
        r2.close();	 Catch:{ Exception -> 0x0057 }
    L_0x0056:
        throw r0;	 Catch:{ Exception -> 0x0057 }
    L_0x0057:
        r0 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.b(r0);	 Catch:{ all -> 0x0063 }
        if (r2 != 0) goto L_0x0061;
    L_0x005e:
        r0.printStackTrace();	 Catch:{ all -> 0x0063 }
    L_0x0061:
        r0 = r1;
        goto L_0x0020;
    L_0x0063:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
    L_0x0066:
        r0 = move-exception;
        goto L_0x0051;
    L_0x0068:
        r0 = move-exception;
        goto L_0x0024;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.proguard.y.a(int):byte[]");
    }

    public static byte[] a(int i, byte[] bArr, byte[] bArr2) {
        try {
            Key secretKeySpec = new SecretKeySpec(bArr2, "AES");
            Cipher instance = Cipher.getInstance("AES/GCM/NoPadding");
            instance.init(i, secretKeySpec, new IvParameterSpec(bArr2));
            return instance.doFinal(bArr);
        } catch (Throwable e) {
            if (!w.b(e)) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static byte[] b(int i, byte[] bArr, byte[] bArr2) {
        try {
            Key generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bArr2));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(1, generatePublic);
            return instance.doFinal(bArr);
        } catch (Throwable e) {
            if (!w.b(e)) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static boolean a(Context context, String str, long j) {
        w.c("[Util] try to lock file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            File file = new File(context.getFilesDir() + File.separator + str);
            if (file.exists()) {
                if (System.currentTimeMillis() - file.lastModified() < j) {
                    return false;
                }
                w.c("[Util] lock file(%s) is expired, unlock it", str);
                b(context, str);
            }
            if (file.createNewFile()) {
                w.c("[Util] successfully locked file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                return true;
            }
            w.c("[Util] Failed to locked file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return false;
        } catch (Throwable th) {
            w.a(th);
            return false;
        }
    }

    public static boolean b(Context context, String str) {
        w.c("[Util] try to unlock file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            File file = new File(context.getFilesDir() + File.separator + str);
            if (!file.exists()) {
                return true;
            }
            if (!file.delete()) {
                return false;
            }
            w.c("[Util] successfully unlocked file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return true;
        } catch (Throwable th) {
            w.a(th);
            return false;
        }
    }

    private static BufferedReader a(File file) {
        if (file == null || !file.exists() || !file.canRead()) {
            return null;
        }
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(file), Constants.UTF_8));
        } catch (Throwable th) {
            w.a(th);
            return null;
        }
    }

    public static BufferedReader a(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str, str2);
            if (file.exists() && file.canRead()) {
                return a(file);
            }
            return null;
        } catch (Throwable e) {
            w.a(e);
            return null;
        }
    }

    public static Thread a(Runnable runnable, String str) {
        try {
            Thread thread = new Thread(runnable);
            thread.setName(str);
            thread.start();
            return thread;
        } catch (Throwable th) {
            w.e("[Util] Failed to start a thread to execute task with message: %s", th.getMessage());
            return null;
        }
    }

    public static boolean c(String str) {
        boolean z;
        if (str == null || str.trim().length() <= 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return false;
        }
        if (str.length() > 255) {
            w.a("URL's length is larger than 255.", new Object[0]);
            return false;
        } else if (!str.toLowerCase().startsWith("http")) {
            w.a("URL is not start with \"http\".", new Object[0]);
            return false;
        } else if (str.toLowerCase().contains("qq.com")) {
            return true;
        } else {
            w.a("URL does not contain \"qq.com\".", new Object[0]);
            return false;
        }
    }
}
