package com.baidu.mapapi.http;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.baidu.mapapi.JNIInitializer;
import com.baidu.mapapi.common.Logger;
import com.baidu.platform.comapi.util.PermissionCheck;
import com.baidu.platform.comapi.util.e;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpClient {
    public static boolean isHttpsEnable = false;
    HttpURLConnection a;
    private String b = null;
    private String c = null;
    private int d;
    private int e;
    private String f;
    private ProtoResultCallback g;

    public enum HttpStateError {
        NO_ERROR,
        NETWORK_ERROR,
        INNER_ERROR,
        REQUEST_ERROR,
        SERVER_ERROR
    }

    public static abstract class ProtoResultCallback {
        public abstract void onFailed(HttpStateError httpStateError);

        public abstract void onSuccess(String str);
    }

    public HttpClient(String str, ProtoResultCallback protoResultCallback) {
        this.f = str;
        this.g = protoResultCallback;
    }

    private HttpURLConnection a() {
        try {
            HttpURLConnection httpURLConnection;
            URL url = new URL(this.b);
            if (isHttpsEnable) {
                httpURLConnection = (HttpsURLConnection) url.openConnection();
                ((HttpsURLConnection) httpURLConnection).setHostnameVerifier(new b(this));
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpURLConnection.setRequestMethod(this.f);
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(this.d);
            httpURLConnection.setReadTimeout(this.e);
            return httpURLConnection;
        } catch (Exception e) {
            Log.e("HttpClient", "url connect failed");
            if (Logger.debugEnable()) {
                e.printStackTrace();
            } else {
                Logger.logW("HttpClient", e.getMessage());
            }
            return null;
        }
    }

    private void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(NotificationCompat.CATEGORY_STATUS) || jSONObject.has("status_sp")) {
                switch (jSONObject.has(NotificationCompat.CATEGORY_STATUS) ? jSONObject.getInt(NotificationCompat.CATEGORY_STATUS) : jSONObject.getInt("status_sp")) {
                    case 105:
                    case 106:
                        int permissionCheck = PermissionCheck.permissionCheck();
                        if (permissionCheck != 0) {
                            Log.e("HttpClient", "permissionCheck result is: " + permissionCheck);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        } catch (JSONException e) {
            Log.e("HttpClient", "Parse json happened exception");
            e.printStackTrace();
        }
    }

    public static String getAuthToken() {
        return e.z;
    }

    public static String getPhoneInfo() {
        return e.c();
    }

    protected boolean checkNetwork() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) JNIInitializer.getCachedContext().getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
        } catch (Exception e) {
            if (Logger.debugEnable()) {
                e.printStackTrace();
            } else {
                Logger.logW("HttpClient", e.getMessage());
            }
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:77:0x0166 A:{SYNTHETIC, Splitter: B:77:0x0166} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0078 A:{Catch:{ all -> 0x0172 }} */
    /* JADX WARNING: Removed duplicated region for block: B:102:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0099 A:{Catch:{ Exception -> 0x00a0 }} */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0183 A:{Catch:{ Exception -> 0x00a0 }} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0078 A:{Catch:{ all -> 0x0172 }} */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0166 A:{SYNTHETIC, Splitter: B:77:0x0166} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0099 A:{Catch:{ Exception -> 0x00a0 }} */
    /* JADX WARNING: Removed duplicated region for block: B:102:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0183 A:{Catch:{ Exception -> 0x00a0 }} */
    protected void request(java.lang.String r8) {
        /*
        r7 = this;
        r2 = 0;
        r7.b = r8;
        r0 = r7.checkNetwork();
        if (r0 != 0) goto L_0x0011;
    L_0x0009:
        r0 = r7.g;
        r1 = com.baidu.mapapi.http.HttpClient.HttpStateError.NETWORK_ERROR;
        r0.onFailed(r1);
    L_0x0010:
        return;
    L_0x0011:
        r0 = r7.a();
        r7.a = r0;
        r0 = r7.a;
        if (r0 != 0) goto L_0x002c;
    L_0x001b:
        r0 = "HttpClient";
        r1 = "url connection failed";
        android.util.Log.e(r0, r1);
        r0 = r7.g;
        r1 = com.baidu.mapapi.http.HttpClient.HttpStateError.INNER_ERROR;
        r0.onFailed(r1);
        goto L_0x0010;
    L_0x002c:
        r0 = r7.b;
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 == 0) goto L_0x003c;
    L_0x0034:
        r0 = r7.g;
        r1 = com.baidu.mapapi.http.HttpClient.HttpStateError.REQUEST_ERROR;
        r0.onFailed(r1);
        goto L_0x0010;
    L_0x003c:
        r3 = 0;
        r0 = r7.a;	 Catch:{ Exception -> 0x00a0 }
        r0.connect();	 Catch:{ Exception -> 0x00a0 }
        r0 = r7.a;	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r1 = r0.getResponseCode();	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r0 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r0 != r1) goto L_0x00e3;
    L_0x004c:
        r0 = r7.a;	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r1 = r0.getInputStream();	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r3 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x01a3, all -> 0x0198 }
        r0 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x01a3, all -> 0x0198 }
        r4 = "UTF-8";
        r0.<init>(r1, r4);	 Catch:{ Exception -> 0x01a3, all -> 0x0198 }
        r3.<init>(r0);	 Catch:{ Exception -> 0x01a3, all -> 0x0198 }
        r0 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x0070, all -> 0x019c }
        r0.<init>();	 Catch:{ Exception -> 0x0070, all -> 0x019c }
    L_0x0064:
        r2 = r3.read();	 Catch:{ Exception -> 0x0070, all -> 0x019c }
        r4 = -1;
        if (r2 == r4) goto L_0x00bc;
    L_0x006b:
        r2 = (char) r2;	 Catch:{ Exception -> 0x0070, all -> 0x019c }
        r0.append(r2);	 Catch:{ Exception -> 0x0070, all -> 0x019c }
        goto L_0x0064;
    L_0x0070:
        r0 = move-exception;
        r2 = r3;
    L_0x0072:
        r3 = com.baidu.mapapi.common.Logger.debugEnable();	 Catch:{ all -> 0x0172 }
        if (r3 == 0) goto L_0x0166;
    L_0x0078:
        r0.printStackTrace();	 Catch:{ all -> 0x0172 }
    L_0x007b:
        r0 = "HttpClient";
        r3 = "Catch exception. INNER_ERROR";
        android.util.Log.e(r0, r3);	 Catch:{ all -> 0x0172 }
        r0 = r7.g;	 Catch:{ all -> 0x0172 }
        r3 = com.baidu.mapapi.http.HttpClient.HttpStateError.INNER_ERROR;	 Catch:{ all -> 0x0172 }
        r0.onFailed(r3);	 Catch:{ all -> 0x0172 }
        if (r1 == 0) goto L_0x0095;
    L_0x008d:
        if (r2 == 0) goto L_0x0095;
    L_0x008f:
        r2.close();	 Catch:{ Exception -> 0x00a0 }
        r1.close();	 Catch:{ Exception -> 0x00a0 }
    L_0x0095:
        r0 = r7.a;	 Catch:{ Exception -> 0x00a0 }
        if (r0 == 0) goto L_0x0010;
    L_0x0099:
        r0 = r7.a;	 Catch:{ Exception -> 0x00a0 }
        r0.disconnect();	 Catch:{ Exception -> 0x00a0 }
        goto L_0x0010;
    L_0x00a0:
        r0 = move-exception;
        r1 = com.baidu.mapapi.common.Logger.debugEnable();
        if (r1 == 0) goto L_0x0189;
    L_0x00a7:
        r0.printStackTrace();
    L_0x00aa:
        r0 = "HttpClient";
        r1 = "Catch connection exception, INNER_ERROR";
        android.util.Log.e(r0, r1);
        r0 = r7.g;
        r1 = com.baidu.mapapi.http.HttpClient.HttpStateError.INNER_ERROR;
        r0.onFailed(r1);
        goto L_0x0010;
    L_0x00bc:
        r0 = r0.toString();	 Catch:{ Exception -> 0x0070, all -> 0x019c }
        r7.c = r0;	 Catch:{ Exception -> 0x0070, all -> 0x019c }
        r0 = r7.c;	 Catch:{ Exception -> 0x0070, all -> 0x019c }
        r7.a(r0);	 Catch:{ Exception -> 0x0070, all -> 0x019c }
        if (r1 == 0) goto L_0x00d1;
    L_0x00c9:
        if (r3 == 0) goto L_0x00d1;
    L_0x00cb:
        r3.close();	 Catch:{ Exception -> 0x00a0 }
        r1.close();	 Catch:{ Exception -> 0x00a0 }
    L_0x00d1:
        r0 = r7.a;	 Catch:{ Exception -> 0x00a0 }
        if (r0 == 0) goto L_0x00da;
    L_0x00d5:
        r0 = r7.a;	 Catch:{ Exception -> 0x00a0 }
        r0.disconnect();	 Catch:{ Exception -> 0x00a0 }
    L_0x00da:
        r0 = r7.g;
        r1 = r7.c;
        r0.onSuccess(r1);
        goto L_0x0010;
    L_0x00e3:
        r0 = "HttpClient";
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r4.<init>();	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r5 = "responseCode is: ";
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r4 = r4.append(r1);	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r4 = r4.toString();	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        android.util.Log.e(r0, r4);	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r0 = com.baidu.mapapi.http.HttpClient.HttpStateError.NO_ERROR;	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r0 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        if (r1 < r0) goto L_0x0135;
    L_0x0103:
        r0 = com.baidu.mapapi.http.HttpClient.HttpStateError.SERVER_ERROR;	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
    L_0x0105:
        r4 = com.baidu.mapapi.common.Logger.debugEnable();	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        if (r4 == 0) goto L_0x013f;
    L_0x010b:
        r1 = r7.a;	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r1 = r1.getErrorStream();	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r4 = "HttpClient";
        r5 = r1.toString();	 Catch:{ Exception -> 0x01a3, all -> 0x0198 }
        com.baidu.mapapi.common.Logger.logW(r4, r5);	 Catch:{ Exception -> 0x01a3, all -> 0x0198 }
    L_0x011b:
        r4 = r7.g;	 Catch:{ Exception -> 0x01a3, all -> 0x0198 }
        r4.onFailed(r0);	 Catch:{ Exception -> 0x01a3, all -> 0x0198 }
        if (r1 == 0) goto L_0x012a;
    L_0x0122:
        if (r2 == 0) goto L_0x012a;
    L_0x0124:
        r3.close();	 Catch:{ Exception -> 0x00a0 }
        r1.close();	 Catch:{ Exception -> 0x00a0 }
    L_0x012a:
        r0 = r7.a;	 Catch:{ Exception -> 0x00a0 }
        if (r0 == 0) goto L_0x0010;
    L_0x012e:
        r0 = r7.a;	 Catch:{ Exception -> 0x00a0 }
        r0.disconnect();	 Catch:{ Exception -> 0x00a0 }
        goto L_0x0010;
    L_0x0135:
        r0 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r1 < r0) goto L_0x013c;
    L_0x0139:
        r0 = com.baidu.mapapi.http.HttpClient.HttpStateError.REQUEST_ERROR;	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        goto L_0x0105;
    L_0x013c:
        r0 = com.baidu.mapapi.http.HttpClient.HttpStateError.INNER_ERROR;	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        goto L_0x0105;
    L_0x013f:
        r4 = "HttpClient";
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r5.<init>();	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r6 = "Get response from server failed, http response code=";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r1 = r5.append(r1);	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r5 = ", error=";
        r1 = r1.append(r5);	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r1 = r1.append(r0);	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        com.baidu.mapapi.common.Logger.logW(r4, r1);	 Catch:{ Exception -> 0x019f, all -> 0x0195 }
        r1 = r2;
        goto L_0x011b;
    L_0x0166:
        r3 = "HttpClient";
        r0 = r0.getMessage();	 Catch:{ all -> 0x0172 }
        com.baidu.mapapi.common.Logger.logW(r3, r0);	 Catch:{ all -> 0x0172 }
        goto L_0x007b;
    L_0x0172:
        r0 = move-exception;
        r3 = r2;
        r2 = r1;
    L_0x0175:
        if (r2 == 0) goto L_0x017f;
    L_0x0177:
        if (r3 == 0) goto L_0x017f;
    L_0x0179:
        r3.close();	 Catch:{ Exception -> 0x00a0 }
        r2.close();	 Catch:{ Exception -> 0x00a0 }
    L_0x017f:
        r1 = r7.a;	 Catch:{ Exception -> 0x00a0 }
        if (r1 == 0) goto L_0x0188;
    L_0x0183:
        r1 = r7.a;	 Catch:{ Exception -> 0x00a0 }
        r1.disconnect();	 Catch:{ Exception -> 0x00a0 }
    L_0x0188:
        throw r0;	 Catch:{ Exception -> 0x00a0 }
    L_0x0189:
        r1 = "HttpClient";
        r0 = r0.getMessage();
        com.baidu.mapapi.common.Logger.logW(r1, r0);
        goto L_0x00aa;
    L_0x0195:
        r0 = move-exception;
        r3 = r2;
        goto L_0x0175;
    L_0x0198:
        r0 = move-exception;
        r3 = r2;
        r2 = r1;
        goto L_0x0175;
    L_0x019c:
        r0 = move-exception;
        r2 = r1;
        goto L_0x0175;
    L_0x019f:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0072;
    L_0x01a3:
        r0 = move-exception;
        goto L_0x0072;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.mapapi.http.HttpClient.request(java.lang.String):void");
    }

    public void setMaxTimeOut(int i) {
        this.d = i;
    }

    public void setReadTimeOut(int i) {
        this.e = i;
    }
}
