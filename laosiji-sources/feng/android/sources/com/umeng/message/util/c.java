package com.umeng.message.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.common.UmengMessageDeviceConfig;
import com.umeng.message.proguard.h;
import java.net.URLEncoder;

/* compiled from: NetworkHelper */
public class c {
    public static final int a = 1;
    public static final int b = 2;
    public static final int c = 3;
    private static final String i = c.class.getName();
    private final int d = 1;
    private String e;
    private String f = "10.0.0.172";
    private int g = 80;
    private Context h;

    public c(Context context) {
        this.h = context;
        this.e = a(context);
    }

    private boolean a() {
        if (this.h.getPackageManager().checkPermission(MsgConstant.PERMISSION_ACCESS_NETWORK_STATE, this.h.getPackageName()) != 0) {
            return false;
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.h.getSystemService("connectivity")).getActiveNetworkInfo();
            if (!(activeNetworkInfo == null || activeNetworkInfo.getType() == 1)) {
                String extraInfo = activeNetworkInfo.getExtraInfo();
                if (extraInfo != null && (extraInfo.equals("cmwap") || extraInfo.equals("3gwap") || extraInfo.equals("uniwap"))) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0061 A:{SYNTHETIC, Splitter: B:18:0x0061} */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0061 A:{SYNTHETIC, Splitter: B:18:0x0061} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0097 A:{Splitter: B:3:0x000c, ExcHandler: all (r1_12 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0093  */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:19:?, code:
            r3 = com.umeng.commonsdk.UMConfigure.umDebugLog;
            com.umeng.commonsdk.debug.UMLog.mutlInfo(i, 0, "sendMessage:" + r1.getMessage());
     */
    /* JADX WARNING: Missing block: B:22:0x008c, code:
            r2.disconnect();
     */
    /* JADX WARNING: Missing block: B:23:0x0090, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:24:0x0091, code:
            if (r2 != null) goto L_0x0093;
     */
    /* JADX WARNING: Missing block: B:25:0x0093, code:
            r2.disconnect();
     */
    /* JADX WARNING: Missing block: B:26:0x0096, code:
            throw r0;
     */
    /* JADX WARNING: Missing block: B:27:0x0097, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:28:0x0098, code:
            r2 = r0;
            r0 = r1;
     */
    /* JADX WARNING: Missing block: B:31:0x009f, code:
            r2 = move-exception;
     */
    /* JADX WARNING: Missing block: B:32:0x00a0, code:
            r9 = r2;
            r2 = r0;
            r0 = r1;
            r1 = r9;
     */
    /* JADX WARNING: Missing block: B:35:?, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:36:?, code:
            return r0;
     */
    public byte[] a(byte[] r11, java.lang.String r12) {
        /*
        r10 = this;
        r2 = 0;
        r0 = new java.net.URL;	 Catch:{ Exception -> 0x009b }
        r0.<init>(r12);	 Catch:{ Exception -> 0x009b }
        r0 = r0.openConnection();	 Catch:{ Exception -> 0x009b }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x009b }
        r1 = "POST";
        r0.setRequestMethod(r1);	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r1 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r0.setReadTimeout(r1);	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r1 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r0.setConnectTimeout(r1);	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r1 = "Msg-Type";
        r3 = "envelope";
        r0.setRequestProperty(r1, r3);	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r1 = 1;
        r0.setDoOutput(r1);	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r1 = 0;
        r0.setChunkedStreamingMode(r1);	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r1 = new java.io.BufferedOutputStream;	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r3 = r0.getOutputStream();	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r1.<init>(r3);	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r1.write(r11);	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r1.flush();	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r1.close();	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r3 = new java.io.BufferedInputStream;	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r1 = r0.getInputStream();	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r3.<init>(r1);	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        r1 = com.umeng.message.proguard.h.a(r3);	 Catch:{ all -> 0x0056 }
        com.umeng.message.proguard.h.b(r3);	 Catch:{ Exception -> 0x009f, all -> 0x0097 }
        if (r0 == 0) goto L_0x00a5;
    L_0x0051:
        r0.disconnect();
        r0 = r1;
    L_0x0055:
        return r0;
    L_0x0056:
        r1 = move-exception;
        com.umeng.message.proguard.h.b(r3);	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
        throw r1;	 Catch:{ Exception -> 0x005b, all -> 0x0097 }
    L_0x005b:
        r1 = move-exception;
        r9 = r0;
        r0 = r2;
        r2 = r9;
    L_0x005f:
        if (r1 == 0) goto L_0x0087;
    L_0x0061:
        r3 = com.umeng.commonsdk.UMConfigure.umDebugLog;	 Catch:{ all -> 0x0090 }
        r3 = i;	 Catch:{ all -> 0x0090 }
        r4 = 0;
        r5 = 1;
        r5 = new java.lang.String[r5];	 Catch:{ all -> 0x0090 }
        r6 = 0;
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0090 }
        r7.<init>();	 Catch:{ all -> 0x0090 }
        r8 = "sendMessage:";
        r7 = r7.append(r8);	 Catch:{ all -> 0x0090 }
        r8 = r1.getMessage();	 Catch:{ all -> 0x0090 }
        r7 = r7.append(r8);	 Catch:{ all -> 0x0090 }
        r7 = r7.toString();	 Catch:{ all -> 0x0090 }
        r5[r6] = r7;	 Catch:{ all -> 0x0090 }
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r3, r4, r5);	 Catch:{ all -> 0x0090 }
    L_0x0087:
        r1.printStackTrace();	 Catch:{ all -> 0x0090 }
        if (r2 == 0) goto L_0x0055;
    L_0x008c:
        r2.disconnect();
        goto L_0x0055;
    L_0x0090:
        r0 = move-exception;
    L_0x0091:
        if (r2 == 0) goto L_0x0096;
    L_0x0093:
        r2.disconnect();
    L_0x0096:
        throw r0;
    L_0x0097:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
        goto L_0x0091;
    L_0x009b:
        r0 = move-exception;
        r1 = r0;
        r0 = r2;
        goto L_0x005f;
    L_0x009f:
        r2 = move-exception;
        r9 = r2;
        r2 = r0;
        r0 = r1;
        r1 = r9;
        goto L_0x005f;
    L_0x00a5:
        r0 = r1;
        goto L_0x0055;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.util.c.a(byte[], java.lang.String):byte[]");
    }

    private String a(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(MsgConstant.SDK_VERSION);
        stringBuffer.append("/");
        stringBuffer.append(MsgConstant.SDK_VERSION);
        stringBuffer.append(" ");
        try {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(UmengMessageDeviceConfig.getApplicationLable(context));
            stringBuffer2.append("/");
            stringBuffer2.append(UmengMessageDeviceConfig.getAppVersionName(context));
            stringBuffer2.append(" ");
            stringBuffer2.append(Build.MODEL);
            stringBuffer2.append("/");
            stringBuffer2.append(VERSION.RELEASE);
            stringBuffer2.append(" ");
            stringBuffer2.append(h.a(PushAgent.getInstance(context).getMessageAppkey()));
            stringBuffer.append(URLEncoder.encode(stringBuffer2.toString(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
