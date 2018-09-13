package com.umeng.commonsdk.stateless;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.statistics.b;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.message.MsgConstant;

/* compiled from: UMSLNetWorkSenderHelper */
public class e {
    private String a = "10.0.0.172";
    private int b = 80;
    private Context c;

    public e(Context context) {
        this.c = context;
    }

    private void a() {
        Object imprintProperty = UMEnvelopeBuild.imprintProperty(this.c, "sl_domain_p", "");
        if (!TextUtils.isEmpty(imprintProperty)) {
            a.g = DataHelper.assembleStatelessURL(imprintProperty);
        }
    }

    private void b() {
        Object imprintProperty = UMEnvelopeBuild.imprintProperty(this.c, "sl_domain_p", "");
        Object imprintProperty2 = UMEnvelopeBuild.imprintProperty(this.c, "oversea_sl_domain_p", "");
        if (!TextUtils.isEmpty(imprintProperty)) {
            a.f = DataHelper.assembleStatelessURL(imprintProperty);
        }
        if (!TextUtils.isEmpty(imprintProperty2)) {
            a.h = DataHelper.assembleStatelessURL(imprintProperty2);
        }
        a.g = a.h;
        if (!TextUtils.isEmpty(b.b)) {
            if (b.b.startsWith("460") || b.b.startsWith("461")) {
                a.g = a.f;
            }
        }
    }

    private boolean c() {
        if (this.c != null) {
            if (this.c.getPackageManager().checkPermission(MsgConstant.PERMISSION_ACCESS_NETWORK_STATE, this.c.getPackageName()) != 0) {
                return false;
            }
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) this.c.getSystemService("connectivity");
                if (!DeviceConfig.checkPermission(this.c, MsgConstant.PERMISSION_ACCESS_NETWORK_STATE)) {
                    return false;
                }
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (!(activeNetworkInfo == null || activeNetworkInfo.getType() == 1)) {
                    String extraInfo = activeNetworkInfo.getExtraInfo();
                    if (extraInfo != null && (extraInfo.equals("cmwap") || extraInfo.equals("3gwap") || extraInfo.equals("uniwap"))) {
                        return true;
                    }
                }
            } catch (Throwable th) {
                com.umeng.commonsdk.proguard.b.a(this.c, th);
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x016b A:{SYNTHETIC, Splitter: B:53:0x016b} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0170 A:{SYNTHETIC, Splitter: B:56:0x0170} */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0134 A:{SYNTHETIC, Splitter: B:33:0x0134} */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0139 A:{SYNTHETIC, Splitter: B:36:0x0139} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0155 A:{SYNTHETIC, Splitter: B:44:0x0155} */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x015a A:{SYNTHETIC, Splitter: B:47:0x015a} */
    public boolean a(byte[] r9, java.lang.String r10) {
        /*
        r8 = this;
        r2 = 0;
        r4 = 1;
        r1 = 0;
        if (r9 == 0) goto L_0x0007;
    L_0x0005:
        if (r10 != 0) goto L_0x0016;
    L_0x0007:
        r0 = "walle";
        r2 = new java.lang.Object[r4];
        r3 = "[stateless] sendMessage, envelopeByte == null || path == null ";
        r2[r1] = r3;
        com.umeng.commonsdk.statistics.common.e.a(r0, r2);
        r0 = r1;
    L_0x0015:
        return r0;
    L_0x0016:
        r0 = com.umeng.commonsdk.statistics.SdkVersion.SDK_TYPE;
        if (r0 != 0) goto L_0x00f9;
    L_0x001a:
        r8.a();
    L_0x001d:
        r0 = r8.c();	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        if (r0 == 0) goto L_0x0102;
    L_0x0023:
        r0 = new java.net.Proxy;	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r3 = java.net.Proxy.Type.HTTP;	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r5 = new java.net.InetSocketAddress;	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r6 = r8.a;	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r7 = r8.b;	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r5.<init>(r6, r7);	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r0.<init>(r3, r5);	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r3 = new java.net.URL;	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r5 = new java.lang.StringBuilder;	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r5.<init>();	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r6 = com.umeng.commonsdk.stateless.a.g;	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r5 = r5.append(r6);	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r6 = "/";
        r5 = r5.append(r6);	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r5 = r5.append(r10);	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r5 = r5.toString();	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r3.<init>(r5);	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r0 = r3.openConnection(r0);	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r0 = (javax.net.ssl.HttpsURLConnection) r0;	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r3 = r0;
    L_0x0059:
        r0 = org.apache.http.conn.ssl.SSLSocketFactory.STRICT_HOSTNAME_VERIFIER;	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r3.setHostnameVerifier(r0);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r0 = "TLS";
        r0 = javax.net.ssl.SSLContext.getInstance(r0);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r5 = 0;
        r6 = 0;
        r7 = new java.security.SecureRandom;	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r7.<init>();	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r0.init(r5, r6, r7);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r0 = r0.getSocketFactory();	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(r0);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r0 = "X-Umeng-UTC";
        r6 = java.lang.System.currentTimeMillis();	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r5 = java.lang.String.valueOf(r6);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r3.setRequestProperty(r0, r5);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r0 = "Msg-Type";
        r5 = "envelope/json";
        r3.setRequestProperty(r0, r5);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r0 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r3.setConnectTimeout(r0);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r0 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r3.setReadTimeout(r0);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r0 = "POST";
        r3.setRequestMethod(r0);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r0 = 1;
        r3.setDoOutput(r0);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r0 = 1;
        r3.setDoInput(r0);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r0 = 0;
        r3.setUseCaches(r0);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r2 = r3.getOutputStream();	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r2.write(r9);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r2.flush();	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r3.connect();	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r0 = r3.getResponseCode();	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r5 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r0 != r5) goto L_0x0196;
    L_0x00be:
        r0 = "MobclickRT";
        r5 = new java.lang.StringBuilder;	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r5.<init>();	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r6 = "--->>> send stateless message success : ";
        r5 = r5.append(r6);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r6 = com.umeng.commonsdk.stateless.a.g;	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r5 = r5.append(r6);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r6 = "/";
        r5 = r5.append(r6);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r5 = r5.append(r10);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r5 = r5.toString();	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        com.umeng.commonsdk.debug.UMRTLog.i(r0, r5);	 Catch:{ SSLHandshakeException -> 0x018e, Throwable -> 0x018a }
        r0 = r4;
    L_0x00e6:
        if (r2 == 0) goto L_0x00eb;
    L_0x00e8:
        r2.close();	 Catch:{ Exception -> 0x017b }
    L_0x00eb:
        if (r3 == 0) goto L_0x0015;
    L_0x00ed:
        r1 = r3.getInputStream();	 Catch:{ IOException -> 0x0190 }
        r1.close();	 Catch:{ IOException -> 0x0190 }
    L_0x00f4:
        r3.disconnect();
        goto L_0x0015;
    L_0x00f9:
        r0 = com.umeng.commonsdk.stateless.a.h;
        com.umeng.commonsdk.stateless.a.f = r0;
        r8.b();
        goto L_0x001d;
    L_0x0102:
        r0 = new java.net.URL;	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r3 = new java.lang.StringBuilder;	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r3.<init>();	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r5 = com.umeng.commonsdk.stateless.a.g;	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r3 = r3.append(r5);	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r5 = "/";
        r3 = r3.append(r5);	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r3 = r3.append(r10);	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r3 = r3.toString();	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r0.<init>(r3);	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r0 = r0.openConnection();	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r0 = (javax.net.ssl.HttpsURLConnection) r0;	 Catch:{ SSLHandshakeException -> 0x012a, Throwable -> 0x0146, all -> 0x0167 }
        r3 = r0;
        goto L_0x0059;
    L_0x012a:
        r0 = move-exception;
        r3 = r2;
    L_0x012c:
        r4 = "SSLHandshakeException, Failed to send message.";
        com.umeng.commonsdk.statistics.common.MLog.e(r4, r0);	 Catch:{ all -> 0x0186 }
        if (r2 == 0) goto L_0x0137;
    L_0x0134:
        r2.close();	 Catch:{ Exception -> 0x017e }
    L_0x0137:
        if (r3 == 0) goto L_0x0193;
    L_0x0139:
        r0 = r3.getInputStream();	 Catch:{ IOException -> 0x018c }
        r0.close();	 Catch:{ IOException -> 0x018c }
    L_0x0140:
        r3.disconnect();
        r0 = r1;
        goto L_0x0015;
    L_0x0146:
        r0 = move-exception;
        r3 = r2;
    L_0x0148:
        r4 = "Exception,Failed to send message.";
        com.umeng.commonsdk.statistics.common.MLog.e(r4, r0);	 Catch:{ all -> 0x0186 }
        r4 = r8.c;	 Catch:{ all -> 0x0186 }
        com.umeng.commonsdk.proguard.b.a(r4, r0);	 Catch:{ all -> 0x0186 }
        if (r2 == 0) goto L_0x0158;
    L_0x0155:
        r2.close();	 Catch:{ Exception -> 0x0180 }
    L_0x0158:
        if (r3 == 0) goto L_0x0193;
    L_0x015a:
        r0 = r3.getInputStream();	 Catch:{ IOException -> 0x0188 }
        r0.close();	 Catch:{ IOException -> 0x0188 }
    L_0x0161:
        r3.disconnect();
        r0 = r1;
        goto L_0x0015;
    L_0x0167:
        r0 = move-exception;
        r3 = r2;
    L_0x0169:
        if (r2 == 0) goto L_0x016e;
    L_0x016b:
        r2.close();	 Catch:{ Exception -> 0x0182 }
    L_0x016e:
        if (r3 == 0) goto L_0x017a;
    L_0x0170:
        r1 = r3.getInputStream();	 Catch:{ IOException -> 0x0184 }
        r1.close();	 Catch:{ IOException -> 0x0184 }
    L_0x0177:
        r3.disconnect();
    L_0x017a:
        throw r0;
    L_0x017b:
        r1 = move-exception;
        goto L_0x00eb;
    L_0x017e:
        r0 = move-exception;
        goto L_0x0137;
    L_0x0180:
        r0 = move-exception;
        goto L_0x0158;
    L_0x0182:
        r1 = move-exception;
        goto L_0x016e;
    L_0x0184:
        r1 = move-exception;
        goto L_0x0177;
    L_0x0186:
        r0 = move-exception;
        goto L_0x0169;
    L_0x0188:
        r0 = move-exception;
        goto L_0x0161;
    L_0x018a:
        r0 = move-exception;
        goto L_0x0148;
    L_0x018c:
        r0 = move-exception;
        goto L_0x0140;
    L_0x018e:
        r0 = move-exception;
        goto L_0x012c;
    L_0x0190:
        r1 = move-exception;
        goto L_0x00f4;
    L_0x0193:
        r0 = r1;
        goto L_0x0015;
    L_0x0196:
        r0 = r1;
        goto L_0x00e6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.stateless.e.a(byte[], java.lang.String):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00d9 A:{SYNTHETIC, Splitter: B:34:0x00d9} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00ca A:{SYNTHETIC, Splitter: B:27:0x00ca} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00cf  */
    public boolean b(byte[] r9, java.lang.String r10) {
        /*
        r8 = this;
        r2 = 0;
        r4 = 1;
        r1 = 0;
        if (r9 == 0) goto L_0x0007;
    L_0x0005:
        if (r10 != 0) goto L_0x0009;
    L_0x0007:
        r0 = r1;
    L_0x0008:
        return r0;
    L_0x0009:
        r0 = r8.c();	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        if (r0 == 0) goto L_0x009a;
    L_0x000f:
        r0 = new java.net.Proxy;	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r3 = java.net.Proxy.Type.HTTP;	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r5 = new java.net.InetSocketAddress;	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r6 = r8.a;	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r7 = r8.b;	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r5.<init>(r6, r7);	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r0.<init>(r3, r5);	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r3 = new java.net.URL;	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r5.<init>();	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r6 = com.umeng.commonsdk.stateless.a.g;	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r5 = r5.append(r6);	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r6 = "/";
        r5 = r5.append(r6);	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r5 = r5.append(r10);	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r5 = r5.toString();	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r3.<init>(r5);	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r0 = r3.openConnection(r0);	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r3 = r0;
    L_0x0045:
        r0 = "X-Umeng-UTC";
        r6 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x00ea }
        r5 = java.lang.String.valueOf(r6);	 Catch:{ Throwable -> 0x00ea }
        r3.setRequestProperty(r0, r5);	 Catch:{ Throwable -> 0x00ea }
        r0 = "Msg-Type";
        r5 = "envelope/json";
        r3.setRequestProperty(r0, r5);	 Catch:{ Throwable -> 0x00ea }
        r0 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r3.setConnectTimeout(r0);	 Catch:{ Throwable -> 0x00ea }
        r0 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r3.setReadTimeout(r0);	 Catch:{ Throwable -> 0x00ea }
        r0 = "POST";
        r3.setRequestMethod(r0);	 Catch:{ Throwable -> 0x00ea }
        r0 = 1;
        r3.setDoOutput(r0);	 Catch:{ Throwable -> 0x00ea }
        r0 = 1;
        r3.setDoInput(r0);	 Catch:{ Throwable -> 0x00ea }
        r0 = 0;
        r3.setUseCaches(r0);	 Catch:{ Throwable -> 0x00ea }
        r2 = r3.getOutputStream();	 Catch:{ Throwable -> 0x00ea }
        r2.write(r9);	 Catch:{ Throwable -> 0x00ea }
        r2.flush();	 Catch:{ Throwable -> 0x00ea }
        r3.connect();	 Catch:{ Throwable -> 0x00ea }
        r0 = r3.getResponseCode();	 Catch:{ Throwable -> 0x00ea }
        r5 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r0 != r5) goto L_0x00ef;
    L_0x008d:
        r0 = r4;
    L_0x008e:
        if (r2 == 0) goto L_0x0093;
    L_0x0090:
        r2.close();	 Catch:{ Exception -> 0x00e2 }
    L_0x0093:
        if (r3 == 0) goto L_0x0008;
    L_0x0095:
        r3.disconnect();
        goto L_0x0008;
    L_0x009a:
        r0 = new java.net.URL;	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r3.<init>();	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r5 = com.umeng.commonsdk.stateless.a.g;	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r3 = r3.append(r5);	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r5 = "/";
        r3 = r3.append(r5);	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r3 = r3.append(r10);	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r0.<init>(r3);	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r0 = r0.openConnection();	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Throwable -> 0x00c1, all -> 0x00d5 }
        r3 = r0;
        goto L_0x0045;
    L_0x00c1:
        r0 = move-exception;
        r3 = r2;
    L_0x00c3:
        r4 = r8.c;	 Catch:{ all -> 0x00e8 }
        com.umeng.commonsdk.proguard.b.a(r4, r0);	 Catch:{ all -> 0x00e8 }
        if (r2 == 0) goto L_0x00cd;
    L_0x00ca:
        r2.close();	 Catch:{ Exception -> 0x00e4 }
    L_0x00cd:
        if (r3 == 0) goto L_0x00ec;
    L_0x00cf:
        r3.disconnect();
        r0 = r1;
        goto L_0x0008;
    L_0x00d5:
        r0 = move-exception;
        r3 = r2;
    L_0x00d7:
        if (r2 == 0) goto L_0x00dc;
    L_0x00d9:
        r2.close();	 Catch:{ Exception -> 0x00e6 }
    L_0x00dc:
        if (r3 == 0) goto L_0x00e1;
    L_0x00de:
        r3.disconnect();
    L_0x00e1:
        throw r0;
    L_0x00e2:
        r1 = move-exception;
        goto L_0x0093;
    L_0x00e4:
        r0 = move-exception;
        goto L_0x00cd;
    L_0x00e6:
        r1 = move-exception;
        goto L_0x00dc;
    L_0x00e8:
        r0 = move-exception;
        goto L_0x00d7;
    L_0x00ea:
        r0 = move-exception;
        goto L_0x00c3;
    L_0x00ec:
        r0 = r1;
        goto L_0x0008;
    L_0x00ef:
        r0 = r1;
        goto L_0x008e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.stateless.e.b(byte[], java.lang.String):boolean");
    }
}
