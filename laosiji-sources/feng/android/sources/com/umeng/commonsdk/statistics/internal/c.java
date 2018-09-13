package com.umeng.commonsdk.statistics.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.SdkVersion;
import com.umeng.commonsdk.statistics.UMServerURL;
import com.umeng.commonsdk.statistics.b;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.message.MsgConstant;

/* compiled from: NetworkHelper */
public class c {
    private static boolean e = false;
    private String a = "10.0.0.172";
    private int b = 80;
    private Context c;
    private b d;

    public c(Context context) {
        this.c = context;
    }

    public void a(b bVar) {
        this.d = bVar;
    }

    private void a() {
        Object imprintProperty = UMEnvelopeBuild.imprintProperty(this.c, "domain_p", "");
        Object imprintProperty2 = UMEnvelopeBuild.imprintProperty(this.c, "domain_s", "");
        if (!TextUtils.isEmpty(imprintProperty)) {
            UMServerURL.DEFAULT_URL = DataHelper.assembleURL(imprintProperty);
        }
        if (!TextUtils.isEmpty(imprintProperty2)) {
            UMServerURL.SECONDARY_URL = DataHelper.assembleURL(imprintProperty2);
        }
        AnalyticsConstants.APPLOG_URL_LIST = new String[]{UMServerURL.DEFAULT_URL, UMServerURL.SECONDARY_URL};
    }

    private void b() {
        Object imprintProperty = UMEnvelopeBuild.imprintProperty(this.c, "domain_p", "");
        Object imprintProperty2 = UMEnvelopeBuild.imprintProperty(this.c, "domain_s", "");
        if (!TextUtils.isEmpty(imprintProperty)) {
            UMServerURL.DEFAULT_URL = DataHelper.assembleURL(imprintProperty);
        }
        if (!TextUtils.isEmpty(imprintProperty2)) {
            UMServerURL.SECONDARY_URL = DataHelper.assembleURL(imprintProperty2);
        }
        imprintProperty = UMEnvelopeBuild.imprintProperty(this.c, "oversea_domain_p", "");
        imprintProperty2 = UMEnvelopeBuild.imprintProperty(this.c, "oversea_domain_s", "");
        if (!TextUtils.isEmpty(imprintProperty)) {
            UMServerURL.OVERSEA_DEFAULT_URL = DataHelper.assembleURL(imprintProperty);
        }
        if (!TextUtils.isEmpty(imprintProperty2)) {
            UMServerURL.OVERSEA_SECONDARY_URL = DataHelper.assembleURL(imprintProperty2);
        }
        AnalyticsConstants.APPLOG_URL_LIST = new String[]{UMServerURL.OVERSEA_DEFAULT_URL, UMServerURL.OVERSEA_SECONDARY_URL};
        if (!TextUtils.isEmpty(b.b)) {
            if (b.b.startsWith("460") || b.b.startsWith("461")) {
                AnalyticsConstants.APPLOG_URL_LIST = new String[]{UMServerURL.DEFAULT_URL, UMServerURL.SECONDARY_URL};
            }
        }
    }

    public byte[] a(byte[] bArr, boolean z) {
        byte[] bArr2 = null;
        if (SdkVersion.SDK_TYPE == 0) {
            a();
        } else {
            UMServerURL.DEFAULT_URL = UMServerURL.OVERSEA_DEFAULT_URL;
            UMServerURL.SECONDARY_URL = UMServerURL.OVERSEA_SECONDARY_URL;
            b();
        }
        for (String a : AnalyticsConstants.APPLOG_URL_LIST) {
            bArr2 = a(bArr, a);
            if (bArr2 != null) {
                if (this.d != null) {
                    this.d.onRequestSucceed(z);
                }
                return bArr2;
            }
            if (this.d != null) {
                this.d.onRequestFailed();
            }
        }
        return bArr2;
    }

    private boolean c() {
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
            return false;
        } catch (Throwable th) {
            com.umeng.commonsdk.proguard.b.a(this.c, th);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x018c A:{SYNTHETIC, Splitter: B:55:0x018c} */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0191 A:{SYNTHETIC, Splitter: B:58:0x0191} */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x01d5 A:{SYNTHETIC, Splitter: B:79:0x01d5} */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x01da A:{SYNTHETIC, Splitter: B:82:0x01da} */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x01f8 A:{SYNTHETIC, Splitter: B:93:0x01f8} */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x01fd A:{SYNTHETIC, Splitter: B:96:0x01fd} */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0215 A:{SYNTHETIC, Splitter: B:105:0x0215} */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x021a A:{SYNTHETIC, Splitter: B:108:0x021a} */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0215 A:{SYNTHETIC, Splitter: B:105:0x0215} */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x021a A:{SYNTHETIC, Splitter: B:108:0x021a} */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0215 A:{SYNTHETIC, Splitter: B:105:0x0215} */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x021a A:{SYNTHETIC, Splitter: B:108:0x021a} */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x018c A:{SYNTHETIC, Splitter: B:55:0x018c} */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0191 A:{SYNTHETIC, Splitter: B:58:0x0191} */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x01d5 A:{SYNTHETIC, Splitter: B:79:0x01d5} */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x01da A:{SYNTHETIC, Splitter: B:82:0x01da} */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x01f8 A:{SYNTHETIC, Splitter: B:93:0x01f8} */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x01fd A:{SYNTHETIC, Splitter: B:96:0x01fd} */
    public byte[] a(byte[] r10, java.lang.String r11) {
        /*
        r9 = this;
        r5 = 0;
        r4 = 1;
        r1 = 0;
        r0 = r9.d;	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        if (r0 == 0) goto L_0x000c;
    L_0x0007:
        r0 = r9.d;	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r0.onRequestStart();	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
    L_0x000c:
        r0 = r9.c();	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        if (r0 == 0) goto L_0x015b;
    L_0x0012:
        r0 = new java.net.Proxy;	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r2 = java.net.Proxy.Type.HTTP;	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r3 = new java.net.InetSocketAddress;	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r6 = r9.a;	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r7 = r9.b;	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r3.<init>(r6, r7);	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r0.<init>(r2, r3);	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r2 = new java.net.URL;	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r2.<init>(r11);	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r0 = r2.openConnection(r0);	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r0 = (javax.net.ssl.HttpsURLConnection) r0;	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r3 = r0;
    L_0x002e:
        r0 = e;	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        if (r0 != 0) goto L_0x0052;
    L_0x0032:
        r0 = org.apache.http.conn.ssl.SSLSocketFactory.STRICT_HOSTNAME_VERIFIER;	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r3.setHostnameVerifier(r0);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r0 = "TLS";
        r0 = javax.net.ssl.SSLContext.getInstance(r0);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r2 = 0;
        r6 = 0;
        r7 = new java.security.SecureRandom;	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r7.<init>();	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r0.init(r2, r6, r7);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r0 = r0.getSocketFactory();	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(r0);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r0 = 1;
        e = r0;	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
    L_0x0052:
        r0 = "X-Umeng-UTC";
        r6 = java.lang.System.currentTimeMillis();	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r2 = java.lang.String.valueOf(r6);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r3.setRequestProperty(r0, r2);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r0 = "X-Umeng-Sdk";
        r2 = r9.c;	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r2 = com.umeng.commonsdk.statistics.internal.a.a(r2);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r2 = r2.b();	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r3.setRequestProperty(r0, r2);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r0 = "Content-Type";
        r2 = r9.c;	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r2 = com.umeng.commonsdk.statistics.internal.a.a(r2);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r2 = r2.a();	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r3.setRequestProperty(r0, r2);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r0 = "Msg-Type";
        r2 = "envelope/json";
        r3.setRequestProperty(r0, r2);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r0 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r3.setConnectTimeout(r0);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r0 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r3.setReadTimeout(r0);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r0 = "POST";
        r3.setRequestMethod(r0);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r0 = 1;
        r3.setDoOutput(r0);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r0 = 1;
        r3.setDoInput(r0);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r0 = 0;
        r3.setUseCaches(r0);	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r2 = r3.getOutputStream();	 Catch:{ SSLHandshakeException -> 0x0251, UnknownHostException -> 0x0242, Throwable -> 0x023b, all -> 0x022e }
        r2.write(r10);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r2.flush();	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r3.connect();	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r0 = r9.d;	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        if (r0 == 0) goto L_0x00bb;
    L_0x00b6:
        r0 = r9.d;	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r0.onRequestEnd();	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
    L_0x00bb:
        r6 = r3.getResponseCode();	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r0 = "Content-Type";
        r0 = r3.getHeaderField(r0);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r7 = android.text.TextUtils.isEmpty(r0);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        if (r7 != 0) goto L_0x025c;
    L_0x00cc:
        r7 = "application/thrift";
        r0 = r0.equalsIgnoreCase(r7);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        if (r0 == 0) goto L_0x025c;
    L_0x00d5:
        r0 = r4;
    L_0x00d6:
        r4 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        if (r4 == 0) goto L_0x00fc;
    L_0x00da:
        r4 = new java.lang.StringBuilder;	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r4.<init>();	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r5 = "status code : ";
        r4 = r4.append(r5);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r4 = r4.append(r6);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r5 = "; isThrifit:";
        r4 = r4.append(r5);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r4 = r4.append(r0);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r4 = r4.toString();	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        com.umeng.commonsdk.statistics.common.MLog.i(r4);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
    L_0x00fc:
        r4 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r6 != r4) goto L_0x019d;
    L_0x0100:
        if (r0 == 0) goto L_0x019d;
    L_0x0102:
        r0 = new java.lang.StringBuilder;	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r0.<init>();	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r4 = "Send message to server. status code is: ";
        r0 = r0.append(r4);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r0 = r0.append(r6);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r0 = r0.toString();	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        com.umeng.commonsdk.statistics.common.MLog.i(r0);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r0 = "MobclickRT";
        r4 = new java.lang.StringBuilder;	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r4.<init>();	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r5 = "Send message to server. status code is: ";
        r4 = r4.append(r5);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r4 = r4.append(r6);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r5 = "; url = ";
        r4 = r4.append(r5);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r4 = r4.append(r11);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r4 = r4.toString();	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        com.umeng.commonsdk.debug.UMRTLog.i(r0, r4);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r4 = r3.getInputStream();	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        r0 = com.umeng.commonsdk.statistics.common.HelperUtils.readStreamToByteArray(r4);	 Catch:{ all -> 0x0170 }
        com.umeng.commonsdk.statistics.common.HelperUtils.safeClose(r4);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        if (r2 == 0) goto L_0x014e;
    L_0x014b:
        r2.close();	 Catch:{ Exception -> 0x0169 }
    L_0x014e:
        if (r3 == 0) goto L_0x015a;
    L_0x0150:
        r1 = r3.getInputStream();	 Catch:{ IOException -> 0x0259 }
        r1.close();	 Catch:{ IOException -> 0x0259 }
    L_0x0157:
        r3.disconnect();
    L_0x015a:
        return r0;
    L_0x015b:
        r0 = new java.net.URL;	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r0.<init>(r11);	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r0 = r0.openConnection();	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r0 = (javax.net.ssl.HttpsURLConnection) r0;	 Catch:{ SSLHandshakeException -> 0x024c, UnknownHostException -> 0x01be, Throwable -> 0x01ee, all -> 0x0211 }
        r3 = r0;
        goto L_0x002e;
    L_0x0169:
        r1 = move-exception;
        r2 = r9.c;
        com.umeng.commonsdk.proguard.b.a(r2, r1);
        goto L_0x014e;
    L_0x0170:
        r0 = move-exception;
        com.umeng.commonsdk.statistics.common.HelperUtils.safeClose(r4);	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
        throw r0;	 Catch:{ SSLHandshakeException -> 0x0175, UnknownHostException -> 0x0246, Throwable -> 0x023e }
    L_0x0175:
        r0 = move-exception;
        r0 = r2;
        r2 = r3;
    L_0x0178:
        r3 = "发送数据时发生javax.net.sslHandshakeException异常，导致友盟后端对设备端证书验证失败。请确保设备端没有运行抓包代理类程序。详见链接 https://lark.alipay.com/yj131525/byt0wl/ufnf3i#A10200";
        com.umeng.commonsdk.statistics.common.MLog.i(r3);	 Catch:{ all -> 0x0233 }
        r3 = com.umeng.commonsdk.UMConfigure.umDebugLog;	 Catch:{ all -> 0x0233 }
        r3 = "A_10201";
        r4 = 2;
        r5 = "\\|";
        com.umeng.commonsdk.debug.UMLog.aq(r3, r4, r5);	 Catch:{ all -> 0x0233 }
        if (r0 == 0) goto L_0x018f;
    L_0x018c:
        r0.close();	 Catch:{ Exception -> 0x01b7 }
    L_0x018f:
        if (r2 == 0) goto L_0x019b;
    L_0x0191:
        r0 = r2.getInputStream();	 Catch:{ IOException -> 0x0249 }
        r0.close();	 Catch:{ IOException -> 0x0249 }
    L_0x0198:
        r2.disconnect();
    L_0x019b:
        r0 = r1;
        goto L_0x015a;
    L_0x019d:
        if (r2 == 0) goto L_0x01a2;
    L_0x019f:
        r2.close();	 Catch:{ Exception -> 0x01b0 }
    L_0x01a2:
        if (r3 == 0) goto L_0x01ae;
    L_0x01a4:
        r0 = r3.getInputStream();	 Catch:{ IOException -> 0x0256 }
        r0.close();	 Catch:{ IOException -> 0x0256 }
    L_0x01ab:
        r3.disconnect();
    L_0x01ae:
        r0 = r1;
        goto L_0x015a;
    L_0x01b0:
        r0 = move-exception;
        r2 = r9.c;
        com.umeng.commonsdk.proguard.b.a(r2, r0);
        goto L_0x01a2;
    L_0x01b7:
        r0 = move-exception;
        r3 = r9.c;
        com.umeng.commonsdk.proguard.b.a(r3, r0);
        goto L_0x018f;
    L_0x01be:
        r0 = move-exception;
        r2 = r1;
        r3 = r1;
    L_0x01c1:
        r0 = "发送数据时发生java.net.UnknownHostException异常，导致友盟后端域名解析失败。请检查系统DNS服务器配置是否正确。详见链接 https://lark.alipay.com/yj131525/byt0wl/ufnf3i#A10200";
        com.umeng.commonsdk.statistics.common.MLog.i(r0);	 Catch:{ all -> 0x0230 }
        r0 = com.umeng.commonsdk.UMConfigure.umDebugLog;	 Catch:{ all -> 0x0230 }
        r0 = "A_10200";
        r4 = 2;
        r5 = "\\|";
        com.umeng.commonsdk.debug.UMLog.aq(r0, r4, r5);	 Catch:{ all -> 0x0230 }
        if (r2 == 0) goto L_0x01d8;
    L_0x01d5:
        r2.close();	 Catch:{ Exception -> 0x01e7 }
    L_0x01d8:
        if (r3 == 0) goto L_0x01e4;
    L_0x01da:
        r0 = r3.getInputStream();	 Catch:{ IOException -> 0x0240 }
        r0.close();	 Catch:{ IOException -> 0x0240 }
    L_0x01e1:
        r3.disconnect();
    L_0x01e4:
        r0 = r1;
        goto L_0x015a;
    L_0x01e7:
        r0 = move-exception;
        r2 = r9.c;
        com.umeng.commonsdk.proguard.b.a(r2, r0);
        goto L_0x01d8;
    L_0x01ee:
        r0 = move-exception;
        r2 = r1;
        r3 = r1;
    L_0x01f1:
        r4 = r9.c;	 Catch:{ all -> 0x0230 }
        com.umeng.commonsdk.proguard.b.a(r4, r0);	 Catch:{ all -> 0x0230 }
        if (r2 == 0) goto L_0x01fb;
    L_0x01f8:
        r2.close();	 Catch:{ Exception -> 0x020a }
    L_0x01fb:
        if (r3 == 0) goto L_0x0207;
    L_0x01fd:
        r0 = r3.getInputStream();	 Catch:{ IOException -> 0x0239 }
        r0.close();	 Catch:{ IOException -> 0x0239 }
    L_0x0204:
        r3.disconnect();
    L_0x0207:
        r0 = r1;
        goto L_0x015a;
    L_0x020a:
        r0 = move-exception;
        r2 = r9.c;
        com.umeng.commonsdk.proguard.b.a(r2, r0);
        goto L_0x01fb;
    L_0x0211:
        r0 = move-exception;
        r3 = r1;
    L_0x0213:
        if (r1 == 0) goto L_0x0218;
    L_0x0215:
        r1.close();	 Catch:{ Exception -> 0x0225 }
    L_0x0218:
        if (r3 == 0) goto L_0x0224;
    L_0x021a:
        r1 = r3.getInputStream();	 Catch:{ IOException -> 0x022c }
        r1.close();	 Catch:{ IOException -> 0x022c }
    L_0x0221:
        r3.disconnect();
    L_0x0224:
        throw r0;
    L_0x0225:
        r1 = move-exception;
        r2 = r9.c;
        com.umeng.commonsdk.proguard.b.a(r2, r1);
        goto L_0x0218;
    L_0x022c:
        r1 = move-exception;
        goto L_0x0221;
    L_0x022e:
        r0 = move-exception;
        goto L_0x0213;
    L_0x0230:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0213;
    L_0x0233:
        r1 = move-exception;
        r3 = r2;
        r8 = r0;
        r0 = r1;
        r1 = r8;
        goto L_0x0213;
    L_0x0239:
        r0 = move-exception;
        goto L_0x0204;
    L_0x023b:
        r0 = move-exception;
        r2 = r1;
        goto L_0x01f1;
    L_0x023e:
        r0 = move-exception;
        goto L_0x01f1;
    L_0x0240:
        r0 = move-exception;
        goto L_0x01e1;
    L_0x0242:
        r0 = move-exception;
        r2 = r1;
        goto L_0x01c1;
    L_0x0246:
        r0 = move-exception;
        goto L_0x01c1;
    L_0x0249:
        r0 = move-exception;
        goto L_0x0198;
    L_0x024c:
        r0 = move-exception;
        r0 = r1;
        r2 = r1;
        goto L_0x0178;
    L_0x0251:
        r0 = move-exception;
        r0 = r1;
        r2 = r3;
        goto L_0x0178;
    L_0x0256:
        r0 = move-exception;
        goto L_0x01ab;
    L_0x0259:
        r1 = move-exception;
        goto L_0x0157;
    L_0x025c:
        r0 = r5;
        goto L_0x00d6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.statistics.internal.c.a(byte[], java.lang.String):byte[]");
    }
}
