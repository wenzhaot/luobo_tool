package com.baidu.location.b;

import android.os.Environment;
import android.text.TextUtils;
import anet.channel.request.Request;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.util.HttpConstant;
import com.baidu.location.h.a;
import com.baidu.location.h.k;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.HttpURLConnection;
import java.net.URL;

public class g implements UncaughtExceptionHandler {
    private static g a = null;
    private int b = 0;

    private g() {
    }

    public static g a() {
        if (a == null) {
            a = new g();
        }
        return a;
    }

    private String a(Throwable th) {
        Writer stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    private void a(File file, String str, String str2) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(280);
            randomAccessFile.writeInt(12346);
            randomAccessFile.seek(300);
            randomAccessFile.writeLong(System.currentTimeMillis());
            byte[] bytes = str.getBytes();
            randomAccessFile.writeInt(bytes.length);
            randomAccessFile.write(bytes, 0, bytes.length);
            randomAccessFile.seek(600);
            bytes = str2.getBytes();
            randomAccessFile.writeInt(bytes.length);
            randomAccessFile.write(bytes, 0, bytes.length);
            if (!a(str, str2)) {
                randomAccessFile.seek(280);
                randomAccessFile.writeInt(1326);
            }
            randomAccessFile.close();
        } catch (Exception e) {
        }
    }

    private boolean a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        if (!com.baidu.location.f.g.j()) {
            return false;
        }
        try {
            URL url = new URL(k.e);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("e0");
            stringBuffer.append("=");
            stringBuffer.append(str);
            stringBuffer.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
            stringBuffer.append("e1");
            stringBuffer.append("=");
            stringBuffer.append(str2);
            stringBuffer.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
            if (stringBuffer.length() > 0) {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(a.b);
            httpURLConnection.setReadTimeout(a.b);
            httpURLConnection.setRequestProperty(HttpConstant.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
            httpURLConnection.setRequestProperty("Accept-Charset", Request.DEFAULT_CHARSET);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(stringBuffer.toString().getBytes());
            outputStream.flush();
            outputStream.close();
            return httpURLConnection.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public void b() {
        String str = null;
        try {
            File file = new File((Environment.getExternalStorageDirectory().getPath() + "/traces") + "/error_fs2.dat");
            if (file.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(280);
                if (1326 == randomAccessFile.readInt()) {
                    String str2;
                    byte[] bArr;
                    randomAccessFile.seek(308);
                    int readInt = randomAccessFile.readInt();
                    if (readInt <= 0 || readInt >= 2048) {
                        str2 = null;
                    } else {
                        bArr = new byte[readInt];
                        randomAccessFile.read(bArr, 0, readInt);
                        str2 = new String(bArr, 0, readInt);
                    }
                    randomAccessFile.seek(600);
                    readInt = randomAccessFile.readInt();
                    if (readInt > 0 && readInt < 2048) {
                        bArr = new byte[readInt];
                        randomAccessFile.read(bArr, 0, readInt);
                        str = new String(bArr, 0, readInt);
                    }
                    if (a(str2, str)) {
                        randomAccessFile.seek(280);
                        randomAccessFile.writeInt(12346);
                    }
                }
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x0148  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a5 A:{Catch:{ Exception -> 0x0143 }} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00ae A:{SYNTHETIC, Splitter: B:24:0x00ae} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00ae A:{SYNTHETIC, Splitter: B:24:0x00ae} */
    public void uncaughtException(java.lang.Thread r10, java.lang.Throwable r11) {
        /*
        r9 = this;
        r1 = 0;
        r4 = 0;
        r0 = r9.b;
        r0 = r0 + 1;
        r9.b = r0;
        r0 = r9.b;
        r2 = 2;
        if (r0 <= r2) goto L_0x0015;
    L_0x000d:
        r0 = android.os.Process.myPid();
        android.os.Process.killProcess(r0);
    L_0x0014:
        return;
    L_0x0015:
        r2 = java.lang.System.currentTimeMillis();
        r6 = com.baidu.location.g.a.b();
        r2 = r2 - r6;
        r6 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r0 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r0 >= 0) goto L_0x0071;
    L_0x0024:
        r0 = 1088862290; // 0x40e6b852 float:7.21 double:5.379694505E-315;
        r2 = com.baidu.location.f.getFrameVersion();
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 <= 0) goto L_0x0071;
    L_0x002f:
        r0 = com.baidu.location.h.c.a();
        r2 = r0.c();
        r6 = java.lang.System.currentTimeMillis();
        r2 = r6 - r2;
        r6 = 40000; // 0x9c40 float:5.6052E-41 double:1.97626E-319;
        r0 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r0 >= 0) goto L_0x0109;
    L_0x0044:
        r0 = new java.io.File;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = com.baidu.location.h.k.h();
        r2 = r2.append(r3);
        r3 = java.io.File.separator;
        r2 = r2.append(r3);
        r3 = com.baidu.location.f.getJarFileName();
        r2 = r2.append(r3);
        r2 = r2.toString();
        r0.<init>(r2);
        r2 = r0.exists();
        if (r2 == 0) goto L_0x0071;
    L_0x006e:
        r0.delete();
    L_0x0071:
        r2 = r9.a(r11);	 Catch:{ Exception -> 0x0116 }
        if (r2 == 0) goto L_0x014b;
    L_0x0077:
        r0 = "com.baidu.location";
        r0 = r2.contains(r0);	 Catch:{ Exception -> 0x0143 }
        if (r0 == 0) goto L_0x014b;
    L_0x0080:
        r3 = 1;
    L_0x0081:
        r0 = com.baidu.location.h.b.a();	 Catch:{ Exception -> 0x0143 }
        r5 = 0;
        r0 = r0.a(r5);	 Catch:{ Exception -> 0x0143 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0143 }
        r5.<init>();	 Catch:{ Exception -> 0x0143 }
        r0 = r5.append(r0);	 Catch:{ Exception -> 0x0143 }
        r5 = com.baidu.location.a.a.a();	 Catch:{ Exception -> 0x0143 }
        r5 = r5.e();	 Catch:{ Exception -> 0x0143 }
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x0143 }
        r0 = r0.toString();	 Catch:{ Exception -> 0x0143 }
        if (r0 == 0) goto L_0x0148;
    L_0x00a5:
        r0 = com.baidu.location.Jni.encode(r0);	 Catch:{ Exception -> 0x0143 }
    L_0x00a9:
        r8 = r3;
        r3 = r0;
        r0 = r8;
    L_0x00ac:
        if (r0 == 0) goto L_0x0100;
    L_0x00ae:
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0141 }
        r0.<init>();	 Catch:{ Exception -> 0x0141 }
        r4 = android.os.Environment.getExternalStorageDirectory();	 Catch:{ Exception -> 0x0141 }
        r4 = r4.getPath();	 Catch:{ Exception -> 0x0141 }
        r0 = r0.append(r4);	 Catch:{ Exception -> 0x0141 }
        r4 = "/traces";
        r0 = r0.append(r4);	 Catch:{ Exception -> 0x0141 }
        r4 = r0.toString();	 Catch:{ Exception -> 0x0141 }
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0141 }
        r0.<init>();	 Catch:{ Exception -> 0x0141 }
        r0 = r0.append(r4);	 Catch:{ Exception -> 0x0141 }
        r5 = "/error_fs2.dat";
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x0141 }
        r5 = r0.toString();	 Catch:{ Exception -> 0x0141 }
        r0 = new java.io.File;	 Catch:{ Exception -> 0x0141 }
        r0.<init>(r5);	 Catch:{ Exception -> 0x0141 }
        r5 = r0.exists();	 Catch:{ Exception -> 0x0141 }
        if (r5 != 0) goto L_0x011c;
    L_0x00e9:
        r5 = new java.io.File;	 Catch:{ Exception -> 0x0141 }
        r5.<init>(r4);	 Catch:{ Exception -> 0x0141 }
        r4 = r5.exists();	 Catch:{ Exception -> 0x0141 }
        if (r4 != 0) goto L_0x00f7;
    L_0x00f4:
        r5.mkdirs();	 Catch:{ Exception -> 0x0141 }
    L_0x00f7:
        r4 = r0.createNewFile();	 Catch:{ Exception -> 0x0141 }
        if (r4 != 0) goto L_0x0146;
    L_0x00fd:
        r9.a(r1, r3, r2);	 Catch:{ Exception -> 0x0141 }
    L_0x0100:
        r0 = android.os.Process.myPid();
        android.os.Process.killProcess(r0);
        goto L_0x0014;
    L_0x0109:
        r0 = com.baidu.location.h.c.a();
        r2 = java.lang.System.currentTimeMillis();
        r0.b(r2);
        goto L_0x0071;
    L_0x0116:
        r0 = move-exception;
        r0 = r1;
    L_0x0118:
        r2 = r0;
        r3 = r1;
        r0 = r4;
        goto L_0x00ac;
    L_0x011c:
        r1 = new java.io.RandomAccessFile;	 Catch:{ Exception -> 0x0141 }
        r4 = "rw";
        r1.<init>(r0, r4);	 Catch:{ Exception -> 0x0141 }
        r4 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        r1.seek(r4);	 Catch:{ Exception -> 0x0141 }
        r4 = r1.readLong();	 Catch:{ Exception -> 0x0141 }
        r6 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0141 }
        r4 = r6 - r4;
        r6 = 86400000; // 0x5265c00 float:7.82218E-36 double:4.2687272E-316;
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 <= 0) goto L_0x013d;
    L_0x013a:
        r9.a(r0, r3, r2);	 Catch:{ Exception -> 0x0141 }
    L_0x013d:
        r1.close();	 Catch:{ Exception -> 0x0141 }
        goto L_0x0100;
    L_0x0141:
        r0 = move-exception;
        goto L_0x0100;
    L_0x0143:
        r0 = move-exception;
        r0 = r2;
        goto L_0x0118;
    L_0x0146:
        r1 = r0;
        goto L_0x00fd;
    L_0x0148:
        r0 = r1;
        goto L_0x00a9;
    L_0x014b:
        r3 = r4;
        goto L_0x0081;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.b.g.uncaughtException(java.lang.Thread, java.lang.Throwable):void");
    }
}
