package com.baidu.lbsapi.auth;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import anet.channel.request.Request;
import anet.channel.strategy.dispatch.DispatchConstants;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;

public class g {
    private Context a;
    private String b = null;
    private HashMap<String, String> c = null;
    private String d = null;

    public g(Context context) {
        this.a = context;
    }

    private String a(Context context) {
        String str = "wifi";
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return null;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                return null;
            }
            String extraInfo = activeNetworkInfo.getExtraInfo();
            return (extraInfo == null || !(extraInfo.trim().toLowerCase().equals("cmwap") || extraInfo.trim().toLowerCase().equals("uniwap") || extraInfo.trim().toLowerCase().equals("3gwap") || extraInfo.trim().toLowerCase().equals("ctwap"))) ? str : extraInfo.trim().toLowerCase().equals("ctwap") ? "ctwap" : "cmwap";
        } catch (Exception e) {
            if (a.a) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0143 A:{Catch:{ all -> 0x0240 }} */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0168 A:{SYNTHETIC, Splitter: B:67:0x0168} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x018a A:{Catch:{ all -> 0x023e }} */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01af A:{SYNTHETIC, Splitter: B:87:0x01af} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x01c7 A:{Catch:{ all -> 0x023e }} */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x01ec A:{SYNTHETIC, Splitter: B:103:0x01ec} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x018a A:{Catch:{ all -> 0x023e }} */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01af A:{SYNTHETIC, Splitter: B:87:0x01af} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x01c7 A:{Catch:{ all -> 0x023e }} */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x01ec A:{SYNTHETIC, Splitter: B:103:0x01ec} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0143 A:{Catch:{ all -> 0x0240 }} */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0168 A:{SYNTHETIC, Splitter: B:67:0x0168} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x018a A:{Catch:{ all -> 0x023e }} */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01af A:{SYNTHETIC, Splitter: B:87:0x01af} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x01c7 A:{Catch:{ all -> 0x023e }} */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x01ec A:{SYNTHETIC, Splitter: B:103:0x01ec} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0139 A:{Catch:{ MalformedURLException -> 0x013d, IOException -> 0x0256, Exception -> 0x024b }} */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0201 A:{SYNTHETIC, Splitter: B:114:0x0201} */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0139 A:{Catch:{ MalformedURLException -> 0x013d, IOException -> 0x0256, Exception -> 0x024b }} */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00d9 A:{SYNTHETIC, Splitter: B:36:0x00d9} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0139 A:{Catch:{ MalformedURLException -> 0x013d, IOException -> 0x0256, Exception -> 0x024b }} */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0089 A:{Catch:{ all -> 0x0274 }} */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x0287  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00d3 A:{Catch:{ MalformedURLException -> 0x013d, IOException -> 0x0256, Exception -> 0x024b }} */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00d9 A:{SYNTHETIC, Splitter: B:36:0x00d9} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00de A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0139 A:{Catch:{ MalformedURLException -> 0x013d, IOException -> 0x0256, Exception -> 0x024b }} */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0201 A:{SYNTHETIC, Splitter: B:114:0x0201} */
    private void a(javax.net.ssl.HttpsURLConnection r13) {
        /*
        r12 = this;
        r11 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r2 = 0;
        r5 = -1;
        r7 = 0;
        r10 = -11;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "https Post start,url:";
        r0 = r0.append(r1);
        r1 = r12.b;
        r0 = r0.append(r1);
        r0 = r0.toString();
        com.baidu.lbsapi.auth.a.a(r0);
        r0 = r12.c;
        if (r0 != 0) goto L_0x002e;
    L_0x0024:
        r0 = "httpsPost request paramters is null.";
        r0 = com.baidu.lbsapi.auth.ErrorMessage.a(r0);
        r12.d = r0;
    L_0x002d:
        return;
    L_0x002e:
        r0 = 1;
        r6 = r13.getOutputStream();	 Catch:{ MalformedURLException -> 0x0259, IOException -> 0x0183, Exception -> 0x01c0, all -> 0x01fd }
        r1 = new java.io.BufferedWriter;	 Catch:{ MalformedURLException -> 0x025d, IOException -> 0x024e, Exception -> 0x0243 }
        r3 = new java.io.OutputStreamWriter;	 Catch:{ MalformedURLException -> 0x025d, IOException -> 0x024e, Exception -> 0x0243 }
        r4 = "UTF-8";
        r3.<init>(r6, r4);	 Catch:{ MalformedURLException -> 0x025d, IOException -> 0x024e, Exception -> 0x0243 }
        r1.<init>(r3);	 Catch:{ MalformedURLException -> 0x025d, IOException -> 0x024e, Exception -> 0x0243 }
        r3 = r12.c;	 Catch:{ MalformedURLException -> 0x025d, IOException -> 0x024e, Exception -> 0x0243 }
        r3 = b(r3);	 Catch:{ MalformedURLException -> 0x025d, IOException -> 0x024e, Exception -> 0x0243 }
        r1.write(r3);	 Catch:{ MalformedURLException -> 0x025d, IOException -> 0x024e, Exception -> 0x0243 }
        r3 = r12.c;	 Catch:{ MalformedURLException -> 0x025d, IOException -> 0x024e, Exception -> 0x0243 }
        r3 = b(r3);	 Catch:{ MalformedURLException -> 0x025d, IOException -> 0x024e, Exception -> 0x0243 }
        com.baidu.lbsapi.auth.a.a(r3);	 Catch:{ MalformedURLException -> 0x025d, IOException -> 0x024e, Exception -> 0x0243 }
        r1.flush();	 Catch:{ MalformedURLException -> 0x025d, IOException -> 0x024e, Exception -> 0x0243 }
        r1.close();	 Catch:{ MalformedURLException -> 0x025d, IOException -> 0x024e, Exception -> 0x0243 }
        r13.connect();	 Catch:{ MalformedURLException -> 0x025d, IOException -> 0x024e, Exception -> 0x0243 }
        r1 = r13.getInputStream();	 Catch:{ IOException -> 0x0277, all -> 0x012a }
        r4 = r13.getResponseCode();	 Catch:{ IOException -> 0x027c, all -> 0x0267 }
        if (r11 != r4) goto L_0x028d;
    L_0x0065:
        r3 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x0280, all -> 0x026b }
        r8 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x0280, all -> 0x026b }
        r9 = "UTF-8";
        r8.<init>(r1, r9);	 Catch:{ IOException -> 0x0280, all -> 0x026b }
        r3.<init>(r8);	 Catch:{ IOException -> 0x0280, all -> 0x026b }
        r2 = new java.lang.StringBuffer;	 Catch:{ IOException -> 0x0082, all -> 0x026f }
        r2.<init>();	 Catch:{ IOException -> 0x0082, all -> 0x026f }
    L_0x0077:
        r8 = r3.read();	 Catch:{ IOException -> 0x0082, all -> 0x026f }
        if (r8 == r5) goto L_0x0113;
    L_0x007d:
        r8 = (char) r8;	 Catch:{ IOException -> 0x0082, all -> 0x026f }
        r2.append(r8);	 Catch:{ IOException -> 0x0082, all -> 0x026f }
        goto L_0x0077;
    L_0x0082:
        r0 = move-exception;
        r2 = r3;
        r3 = r4;
    L_0x0085:
        r4 = com.baidu.lbsapi.auth.a.a;	 Catch:{ all -> 0x0274 }
        if (r4 == 0) goto L_0x00a7;
    L_0x0089:
        r0.printStackTrace();	 Catch:{ all -> 0x0274 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0274 }
        r4.<init>();	 Catch:{ all -> 0x0274 }
        r8 = "httpsPost parse failed;";
        r4 = r4.append(r8);	 Catch:{ all -> 0x0274 }
        r8 = r0.getMessage();	 Catch:{ all -> 0x0274 }
        r4 = r4.append(r8);	 Catch:{ all -> 0x0274 }
        r4 = r4.toString();	 Catch:{ all -> 0x0274 }
        com.baidu.lbsapi.auth.a.a(r4);	 Catch:{ all -> 0x0274 }
    L_0x00a7:
        r4 = -11;
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0274 }
        r8.<init>();	 Catch:{ all -> 0x0274 }
        r9 = "httpsPost failed,IOException:";
        r8 = r8.append(r9);	 Catch:{ all -> 0x0274 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0274 }
        r0 = r8.append(r0);	 Catch:{ all -> 0x0274 }
        r0 = r0.toString();	 Catch:{ all -> 0x0274 }
        r0 = com.baidu.lbsapi.auth.ErrorMessage.a(r4, r0);	 Catch:{ all -> 0x0274 }
        r12.d = r0;	 Catch:{ all -> 0x0274 }
        if (r1 == 0) goto L_0x00d1;
    L_0x00c9:
        if (r2 == 0) goto L_0x00d1;
    L_0x00cb:
        r2.close();	 Catch:{ MalformedURLException -> 0x013d, IOException -> 0x0256, Exception -> 0x024b }
        r1.close();	 Catch:{ MalformedURLException -> 0x013d, IOException -> 0x0256, Exception -> 0x024b }
    L_0x00d1:
        if (r13 == 0) goto L_0x0287;
    L_0x00d3:
        r13.disconnect();	 Catch:{ MalformedURLException -> 0x013d, IOException -> 0x0256, Exception -> 0x024b }
        r0 = r7;
    L_0x00d7:
        if (r6 == 0) goto L_0x00dc;
    L_0x00d9:
        r6.close();	 Catch:{ IOException -> 0x016e }
    L_0x00dc:
        if (r0 == 0) goto L_0x020e;
    L_0x00de:
        if (r11 == r3) goto L_0x020e;
    L_0x00e0:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "httpsPost failed,statusCode:";
        r0 = r0.append(r1);
        r0 = r0.append(r3);
        r0 = r0.toString();
        com.baidu.lbsapi.auth.a.a(r0);
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "httpsPost failed,statusCode:";
        r0 = r0.append(r1);
        r0 = r0.append(r3);
        r0 = r0.toString();
        r0 = com.baidu.lbsapi.auth.ErrorMessage.a(r10, r0);
        r12.d = r0;
        goto L_0x002d;
    L_0x0113:
        r2 = r2.toString();	 Catch:{ IOException -> 0x0082, all -> 0x026f }
        r12.d = r2;	 Catch:{ IOException -> 0x0082, all -> 0x026f }
    L_0x0119:
        if (r1 == 0) goto L_0x0123;
    L_0x011b:
        if (r3 == 0) goto L_0x0123;
    L_0x011d:
        r3.close();	 Catch:{ MalformedURLException -> 0x0262, IOException -> 0x0252, Exception -> 0x0247 }
        r1.close();	 Catch:{ MalformedURLException -> 0x0262, IOException -> 0x0252, Exception -> 0x0247 }
    L_0x0123:
        if (r13 == 0) goto L_0x028a;
    L_0x0125:
        r13.disconnect();	 Catch:{ MalformedURLException -> 0x0262, IOException -> 0x0252, Exception -> 0x0247 }
        r3 = r4;
        goto L_0x00d7;
    L_0x012a:
        r0 = move-exception;
        r1 = r2;
        r3 = r5;
    L_0x012d:
        if (r1 == 0) goto L_0x0137;
    L_0x012f:
        if (r2 == 0) goto L_0x0137;
    L_0x0131:
        r2.close();	 Catch:{ MalformedURLException -> 0x013d, IOException -> 0x0256, Exception -> 0x024b }
        r1.close();	 Catch:{ MalformedURLException -> 0x013d, IOException -> 0x0256, Exception -> 0x024b }
    L_0x0137:
        if (r13 == 0) goto L_0x013c;
    L_0x0139:
        r13.disconnect();	 Catch:{ MalformedURLException -> 0x013d, IOException -> 0x0256, Exception -> 0x024b }
    L_0x013c:
        throw r0;	 Catch:{ MalformedURLException -> 0x013d, IOException -> 0x0256, Exception -> 0x024b }
    L_0x013d:
        r0 = move-exception;
        r2 = r6;
    L_0x013f:
        r1 = com.baidu.lbsapi.auth.a.a;	 Catch:{ all -> 0x0240 }
        if (r1 == 0) goto L_0x0146;
    L_0x0143:
        r0.printStackTrace();	 Catch:{ all -> 0x0240 }
    L_0x0146:
        r1 = -11;
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0240 }
        r4.<init>();	 Catch:{ all -> 0x0240 }
        r6 = "httpsPost failed,MalformedURLException:";
        r4 = r4.append(r6);	 Catch:{ all -> 0x0240 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0240 }
        r0 = r4.append(r0);	 Catch:{ all -> 0x0240 }
        r0 = r0.toString();	 Catch:{ all -> 0x0240 }
        r0 = com.baidu.lbsapi.auth.ErrorMessage.a(r1, r0);	 Catch:{ all -> 0x0240 }
        r12.d = r0;	 Catch:{ all -> 0x0240 }
        if (r2 == 0) goto L_0x0284;
    L_0x0168:
        r2.close();	 Catch:{ IOException -> 0x0178 }
        r0 = r7;
        goto L_0x00dc;
    L_0x016e:
        r1 = move-exception;
        r2 = com.baidu.lbsapi.auth.a.a;
        if (r2 == 0) goto L_0x00dc;
    L_0x0173:
        r1.printStackTrace();
        goto L_0x00dc;
    L_0x0178:
        r0 = move-exception;
        r1 = com.baidu.lbsapi.auth.a.a;
        if (r1 == 0) goto L_0x0180;
    L_0x017d:
        r0.printStackTrace();
    L_0x0180:
        r0 = r7;
        goto L_0x00dc;
    L_0x0183:
        r0 = move-exception;
        r3 = r5;
        r6 = r2;
    L_0x0186:
        r1 = com.baidu.lbsapi.auth.a.a;	 Catch:{ all -> 0x023e }
        if (r1 == 0) goto L_0x018d;
    L_0x018a:
        r0.printStackTrace();	 Catch:{ all -> 0x023e }
    L_0x018d:
        r1 = -11;
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x023e }
        r2.<init>();	 Catch:{ all -> 0x023e }
        r4 = "httpsPost failed,IOException:";
        r2 = r2.append(r4);	 Catch:{ all -> 0x023e }
        r0 = r0.getMessage();	 Catch:{ all -> 0x023e }
        r0 = r2.append(r0);	 Catch:{ all -> 0x023e }
        r0 = r0.toString();	 Catch:{ all -> 0x023e }
        r0 = com.baidu.lbsapi.auth.ErrorMessage.a(r1, r0);	 Catch:{ all -> 0x023e }
        r12.d = r0;	 Catch:{ all -> 0x023e }
        if (r6 == 0) goto L_0x0284;
    L_0x01af:
        r6.close();	 Catch:{ IOException -> 0x01b5 }
        r0 = r7;
        goto L_0x00dc;
    L_0x01b5:
        r0 = move-exception;
        r1 = com.baidu.lbsapi.auth.a.a;
        if (r1 == 0) goto L_0x01bd;
    L_0x01ba:
        r0.printStackTrace();
    L_0x01bd:
        r0 = r7;
        goto L_0x00dc;
    L_0x01c0:
        r0 = move-exception;
        r3 = r5;
        r6 = r2;
    L_0x01c3:
        r1 = com.baidu.lbsapi.auth.a.a;	 Catch:{ all -> 0x023e }
        if (r1 == 0) goto L_0x01ca;
    L_0x01c7:
        r0.printStackTrace();	 Catch:{ all -> 0x023e }
    L_0x01ca:
        r1 = -11;
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x023e }
        r2.<init>();	 Catch:{ all -> 0x023e }
        r4 = "httpsPost failed,Exception:";
        r2 = r2.append(r4);	 Catch:{ all -> 0x023e }
        r0 = r0.getMessage();	 Catch:{ all -> 0x023e }
        r0 = r2.append(r0);	 Catch:{ all -> 0x023e }
        r0 = r0.toString();	 Catch:{ all -> 0x023e }
        r0 = com.baidu.lbsapi.auth.ErrorMessage.a(r1, r0);	 Catch:{ all -> 0x023e }
        r12.d = r0;	 Catch:{ all -> 0x023e }
        if (r6 == 0) goto L_0x0284;
    L_0x01ec:
        r6.close();	 Catch:{ IOException -> 0x01f2 }
        r0 = r7;
        goto L_0x00dc;
    L_0x01f2:
        r0 = move-exception;
        r1 = com.baidu.lbsapi.auth.a.a;
        if (r1 == 0) goto L_0x01fa;
    L_0x01f7:
        r0.printStackTrace();
    L_0x01fa:
        r0 = r7;
        goto L_0x00dc;
    L_0x01fd:
        r0 = move-exception;
        r6 = r2;
    L_0x01ff:
        if (r6 == 0) goto L_0x0204;
    L_0x0201:
        r6.close();	 Catch:{ IOException -> 0x0205 }
    L_0x0204:
        throw r0;
    L_0x0205:
        r1 = move-exception;
        r2 = com.baidu.lbsapi.auth.a.a;
        if (r2 == 0) goto L_0x0204;
    L_0x020a:
        r1.printStackTrace();
        goto L_0x0204;
    L_0x020e:
        r0 = r12.d;
        if (r0 != 0) goto L_0x0223;
    L_0x0212:
        r0 = "httpsPost failed,mResult is null";
        com.baidu.lbsapi.auth.a.a(r0);
        r0 = "httpsPost failed,internal error";
        r0 = com.baidu.lbsapi.auth.ErrorMessage.a(r5, r0);
        r12.d = r0;
        goto L_0x002d;
    L_0x0223:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "httpsPost success end,parse result = ";
        r0 = r0.append(r1);
        r1 = r12.d;
        r0 = r0.append(r1);
        r0 = r0.toString();
        com.baidu.lbsapi.auth.a.a(r0);
        goto L_0x002d;
    L_0x023e:
        r0 = move-exception;
        goto L_0x01ff;
    L_0x0240:
        r0 = move-exception;
        r6 = r2;
        goto L_0x01ff;
    L_0x0243:
        r0 = move-exception;
        r3 = r5;
        goto L_0x01c3;
    L_0x0247:
        r0 = move-exception;
        r3 = r4;
        goto L_0x01c3;
    L_0x024b:
        r0 = move-exception;
        goto L_0x01c3;
    L_0x024e:
        r0 = move-exception;
        r3 = r5;
        goto L_0x0186;
    L_0x0252:
        r0 = move-exception;
        r3 = r4;
        goto L_0x0186;
    L_0x0256:
        r0 = move-exception;
        goto L_0x0186;
    L_0x0259:
        r0 = move-exception;
        r3 = r5;
        goto L_0x013f;
    L_0x025d:
        r0 = move-exception;
        r3 = r5;
        r2 = r6;
        goto L_0x013f;
    L_0x0262:
        r0 = move-exception;
        r3 = r4;
        r2 = r6;
        goto L_0x013f;
    L_0x0267:
        r0 = move-exception;
        r3 = r5;
        goto L_0x012d;
    L_0x026b:
        r0 = move-exception;
        r3 = r4;
        goto L_0x012d;
    L_0x026f:
        r0 = move-exception;
        r2 = r3;
        r3 = r4;
        goto L_0x012d;
    L_0x0274:
        r0 = move-exception;
        goto L_0x012d;
    L_0x0277:
        r0 = move-exception;
        r1 = r2;
        r3 = r5;
        goto L_0x0085;
    L_0x027c:
        r0 = move-exception;
        r3 = r5;
        goto L_0x0085;
    L_0x0280:
        r0 = move-exception;
        r3 = r4;
        goto L_0x0085;
    L_0x0284:
        r0 = r7;
        goto L_0x00dc;
    L_0x0287:
        r0 = r7;
        goto L_0x00d7;
    L_0x028a:
        r3 = r4;
        goto L_0x00d7;
    L_0x028d:
        r3 = r2;
        goto L_0x0119;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.lbsapi.auth.g.a(javax.net.ssl.HttpsURLConnection):void");
    }

    private static String b(HashMap<String, String> hashMap) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (Entry entry : hashMap.entrySet()) {
            Object obj2;
            if (obj != null) {
                obj2 = null;
            } else {
                stringBuilder.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
                obj2 = obj;
            }
            stringBuilder.append(URLEncoder.encode((String) entry.getKey(), Request.DEFAULT_CHARSET));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode((String) entry.getValue(), Request.DEFAULT_CHARSET));
            obj = obj2;
        }
        return stringBuilder.toString();
    }

    private HttpsURLConnection b() {
        try {
            URL url = new URL(this.b);
            a.a("https URL: " + this.b);
            String a = a(this.a);
            if (a == null || a.equals("")) {
                a.c("Current network is not available.");
                this.d = ErrorMessage.a(-10, "Current network is not available.");
                return null;
            }
            a.a("checkNetwork = " + a);
            HttpsURLConnection httpsURLConnection = a.equals("cmwap") ? (HttpsURLConnection) url.openConnection(new Proxy(Type.HTTP, new InetSocketAddress("10.0.0.172", 80))) : a.equals("ctwap") ? (HttpsURLConnection) url.openConnection(new Proxy(Type.HTTP, new InetSocketAddress("10.0.0.200", 80))) : (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setConnectTimeout(50000);
            httpsURLConnection.setReadTimeout(50000);
            return httpsURLConnection;
        } catch (MalformedURLException e) {
            if (a.a) {
                e.printStackTrace();
                a.a(e.getMessage());
            }
            this.d = ErrorMessage.a(-11, "Auth server could not be parsed as a URL.");
            return null;
        } catch (Exception e2) {
            if (a.a) {
                e2.printStackTrace();
                a.a(e2.getMessage());
            }
            this.d = ErrorMessage.a(-11, "Init httpsurlconnection failed.");
            return null;
        }
    }

    private HashMap<String, String> c(HashMap<String, String> hashMap) {
        HashMap<String, String> hashMap2 = new HashMap();
        for (String str : hashMap.keySet()) {
            String str2 = str2.toString();
            hashMap2.put(str2, hashMap.get(str2));
        }
        return hashMap2;
    }

    protected String a(HashMap<String, String> hashMap) {
        this.c = c(hashMap);
        this.b = (String) this.c.get("url");
        HttpsURLConnection b = b();
        if (b == null) {
            a.c("syncConnect failed,httpsURLConnection is null");
            return this.d;
        }
        a(b);
        return this.d;
    }

    protected boolean a() {
        a.a("checkNetwork start");
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.a.getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                return false;
            }
            a.a("checkNetwork end");
            return true;
        } catch (Exception e) {
            if (a.a) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
