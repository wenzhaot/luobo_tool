package com.umeng.socialize.net.utils;

import android.text.TextUtils;
import com.umeng.message.util.HttpRequest;
import com.umeng.socialize.Config;
import com.umeng.socialize.net.utils.URequest.FilePair;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText;
import com.umeng.socialize.utils.UmengText.NET;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

public class UClient {
    private static final String END = "\r\n";
    private static final String TAG = "UClient";

    protected static class ResponseObj {
        public int httpResponseCode;
        public JSONObject jsonObject;

        protected ResponseObj() {
        }
    }

    public <T extends UResponse> T execute(URequest uRequest, Class<T> cls) {
        uRequest.onPrepareRequest();
        String trim = uRequest.getHttpMethod().trim();
        verifyMethod(trim);
        ResponseObj responseObj = null;
        if (URequest.GET.equals(trim)) {
            responseObj = httpGetRequest(uRequest);
        } else if (URequest.POST.equals(trim)) {
            responseObj = httpPostRequest(uRequest);
        }
        return createResponse(responseObj, cls);
    }

    protected <T extends UResponse> T createResponse(ResponseObj responseObj, Class<T> cls) {
        if (responseObj == null) {
            return null;
        }
        try {
            return (UResponse) cls.getConstructor(new Class[]{Integer.class, JSONObject.class}).newInstance(new Object[]{Integer.valueOf(responseObj.httpResponseCode), responseObj.jsonObject});
        } catch (Throwable th) {
            SLog.error(NET.CREATE, th);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:84:0x0193  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0193  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0197 A:{Splitter: B:12:0x002e, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0193  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0193  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0193  */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:44:0x00dc, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:45:0x00dd, code:
            r2 = null;
            r3 = null;
     */
    /* JADX WARNING: Missing block: B:62:0x0119, code:
            if (r2.size() <= 0) goto L_0x011b;
     */
    /* JADX WARNING: Missing block: B:86:0x0197, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:87:0x0198, code:
            r3 = null;
     */
    private com.umeng.socialize.net.utils.UClient.ResponseObj httpPostRequest(com.umeng.socialize.net.utils.URequest r9) {
        /*
        r8 = this;
        r1 = 0;
        r0 = r9.toJson();
        if (r0 != 0) goto L_0x0025;
    L_0x0007:
        r0 = "";
    L_0x000a:
        r2 = java.util.UUID.randomUUID();
        r3 = r2.toString();
        r4 = r8.openUrlConnection(r9);	 Catch:{ Throwable -> 0x01a8, all -> 0x0188 }
        if (r4 != 0) goto L_0x002e;
    L_0x0018:
        r8.closeQuietly(r1);
        r8.closeQuietly(r1);
        if (r4 == 0) goto L_0x0023;
    L_0x0020:
        r4.disconnect();
    L_0x0023:
        r0 = r1;
    L_0x0024:
        return r0;
    L_0x0025:
        r0 = r9.toJson();
        r0 = r0.toString();
        goto L_0x000a;
    L_0x002e:
        r2 = r9.getBodyPair();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r5 = r9.mMimeType;	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        if (r5 == 0) goto L_0x00a3;
    L_0x0036:
        r0 = "data";
        r0 = r2.get(r0);	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r2 = "Content-Type";
        r3 = r9.mMimeType;	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r4.setRequestProperty(r2, r3);	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r2 = r4.getOutputStream();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r3 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Throwable -> 0x01ae, all -> 0x019a }
        if (r3 != 0) goto L_0x005c;
    L_0x0055:
        r0 = r0.getBytes();	 Catch:{ Throwable -> 0x01ae, all -> 0x019a }
        r2.write(r0);	 Catch:{ Throwable -> 0x01ae, all -> 0x019a }
    L_0x005c:
        r3 = r2;
    L_0x005d:
        r3.flush();	 Catch:{ Throwable -> 0x01bd, all -> 0x01a3 }
        r2 = r4.getResponseCode();	 Catch:{ Throwable -> 0x01bd, all -> 0x01a3 }
        r0 = new com.umeng.socialize.net.utils.UClient$ResponseObj;	 Catch:{ Throwable -> 0x01bd, all -> 0x01a3 }
        r0.<init>();	 Catch:{ Throwable -> 0x01bd, all -> 0x01a3 }
        r0.httpResponseCode = r2;	 Catch:{ Throwable -> 0x01bd, all -> 0x01a3 }
        r5 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r2 != r5) goto L_0x017a;
    L_0x006f:
        r2 = r4.getInputStream();	 Catch:{ Throwable -> 0x01bd, all -> 0x01a3 }
        r5 = r4.getContentEncoding();	 Catch:{ Throwable -> 0x01c1 }
        r6 = r4.getRequestMethod();	 Catch:{ Throwable -> 0x01c1 }
        r5 = r8.parseResult(r9, r6, r5, r2);	 Catch:{ Throwable -> 0x01c1 }
        r6 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01c1 }
        r6.<init>();	 Catch:{ Throwable -> 0x01c1 }
        r7 = com.umeng.socialize.utils.UmengText.NET.POSTJSON;	 Catch:{ Throwable -> 0x01c1 }
        r6 = r6.append(r7);	 Catch:{ Throwable -> 0x01c1 }
        r6 = r6.append(r5);	 Catch:{ Throwable -> 0x01c1 }
        r6 = r6.toString();	 Catch:{ Throwable -> 0x01c1 }
        com.umeng.socialize.utils.SLog.debug(r6);	 Catch:{ Throwable -> 0x01c1 }
        r0.jsonObject = r5;	 Catch:{ Throwable -> 0x01c1 }
        r8.closeQuietly(r2);
        r8.closeQuietly(r3);
        if (r4 == 0) goto L_0x0024;
    L_0x009f:
        r4.disconnect();
        goto L_0x0024;
    L_0x00a3:
        r5 = r9.postStyle;	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r6 = com.umeng.socialize.net.utils.URequest.PostStyle.APPLICATION;	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        if (r5 != r6) goto L_0x0113;
    L_0x00a9:
        r0 = "Content-Type";
        r3 = "application/x-www-form-urlencoded";
        r4.setRequestProperty(r0, r3);	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r3 = new android.net.Uri$Builder;	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r3.<init>();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r0 = r2.keySet();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r5 = r0.iterator();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
    L_0x00bf:
        r0 = r5.hasNext();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        if (r0 == 0) goto L_0x00f2;
    L_0x00c5:
        r0 = r5.next();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r6 = r2.get(r0);	 Catch:{ Throwable -> 0x00d7, all -> 0x0197 }
        r6 = r6.toString();	 Catch:{ Throwable -> 0x00d7, all -> 0x0197 }
        r3.appendQueryParameter(r0, r6);	 Catch:{ Throwable -> 0x00d7, all -> 0x0197 }
        goto L_0x00bf;
    L_0x00d7:
        r0 = move-exception;
        com.umeng.socialize.utils.SLog.error(r0);	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        goto L_0x00bf;
    L_0x00dc:
        r0 = move-exception;
        r2 = r1;
        r3 = r1;
    L_0x00df:
        r5 = com.umeng.socialize.utils.UmengText.NET.PARSEERROR;	 Catch:{ all -> 0x01a5 }
        com.umeng.socialize.utils.SLog.error(r5, r0);	 Catch:{ all -> 0x01a5 }
        r8.closeQuietly(r2);
        r8.closeQuietly(r3);
        if (r4 == 0) goto L_0x00ef;
    L_0x00ec:
        r4.disconnect();
    L_0x00ef:
        r0 = r1;
        goto L_0x0024;
    L_0x00f2:
        r0 = r3.build();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r0 = r0.getEncodedQuery();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r2 = new java.io.DataOutputStream;	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r3 = r4.getOutputStream();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r2.<init>(r3);	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r3 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Throwable -> 0x01b3, all -> 0x019d }
        if (r3 != 0) goto L_0x0110;
    L_0x0109:
        r0 = r0.getBytes();	 Catch:{ Throwable -> 0x01b3, all -> 0x019d }
        r2.write(r0);	 Catch:{ Throwable -> 0x01b3, all -> 0x019d }
    L_0x0110:
        r3 = r2;
        goto L_0x005d;
    L_0x0113:
        if (r2 == 0) goto L_0x011b;
    L_0x0115:
        r2 = r2.size();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        if (r2 > 0) goto L_0x0121;
    L_0x011b:
        r2 = r9.postStyle;	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r5 = com.umeng.socialize.net.utils.URequest.PostStyle.MULTIPART;	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        if (r2 != r5) goto L_0x0145;
    L_0x0121:
        r0 = "Content-Type";
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r2.<init>();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r5 = "multipart/form-data; boundary=";
        r2 = r2.append(r5);	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r2 = r2.toString();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r4.setRequestProperty(r0, r2);	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r2 = r4.getOutputStream();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r8.addBodyParams(r9, r2, r3);	 Catch:{ Throwable -> 0x01ae, all -> 0x019a }
        r3 = r2;
        goto L_0x005d;
    L_0x0145:
        r2 = "Content-Type";
        r3 = "application/x-www-form-urlencoded";
        r4.setRequestProperty(r2, r3);	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r2 = new android.net.Uri$Builder;	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r2.<init>();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r3 = "content";
        r2.appendQueryParameter(r3, r0);	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r0 = r2.build();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r0 = r0.getEncodedQuery();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r2 = new java.io.DataOutputStream;	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r3 = r4.getOutputStream();	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r2.<init>(r3);	 Catch:{ Throwable -> 0x00dc, all -> 0x0197 }
        r3 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Throwable -> 0x01b8, all -> 0x01a0 }
        if (r3 != 0) goto L_0x0177;
    L_0x0170:
        r0 = r0.getBytes();	 Catch:{ Throwable -> 0x01b8, all -> 0x01a0 }
        r2.write(r0);	 Catch:{ Throwable -> 0x01b8, all -> 0x01a0 }
    L_0x0177:
        r3 = r2;
        goto L_0x005d;
    L_0x017a:
        r8.closeQuietly(r1);
        r8.closeQuietly(r3);
        if (r4 == 0) goto L_0x0185;
    L_0x0182:
        r4.disconnect();
    L_0x0185:
        r0 = r1;
        goto L_0x0024;
    L_0x0188:
        r0 = move-exception;
        r3 = r1;
        r4 = r1;
    L_0x018b:
        r8.closeQuietly(r1);
        r8.closeQuietly(r3);
        if (r4 == 0) goto L_0x0196;
    L_0x0193:
        r4.disconnect();
    L_0x0196:
        throw r0;
    L_0x0197:
        r0 = move-exception;
        r3 = r1;
        goto L_0x018b;
    L_0x019a:
        r0 = move-exception;
        r3 = r2;
        goto L_0x018b;
    L_0x019d:
        r0 = move-exception;
        r3 = r2;
        goto L_0x018b;
    L_0x01a0:
        r0 = move-exception;
        r3 = r2;
        goto L_0x018b;
    L_0x01a3:
        r0 = move-exception;
        goto L_0x018b;
    L_0x01a5:
        r0 = move-exception;
        r1 = r2;
        goto L_0x018b;
    L_0x01a8:
        r0 = move-exception;
        r2 = r1;
        r3 = r1;
        r4 = r1;
        goto L_0x00df;
    L_0x01ae:
        r0 = move-exception;
        r3 = r2;
        r2 = r1;
        goto L_0x00df;
    L_0x01b3:
        r0 = move-exception;
        r3 = r2;
        r2 = r1;
        goto L_0x00df;
    L_0x01b8:
        r0 = move-exception;
        r3 = r2;
        r2 = r1;
        goto L_0x00df;
    L_0x01bd:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00df;
    L_0x01c1:
        r0 = move-exception;
        goto L_0x00df;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.net.utils.UClient.httpPostRequest(com.umeng.socialize.net.utils.URequest):com.umeng.socialize.net.utils.UClient$ResponseObj");
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:44:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0063  */
    private com.umeng.socialize.net.utils.UClient.ResponseObj httpGetRequest(com.umeng.socialize.net.utils.URequest r7) {
        /*
        r6 = this;
        r0 = 0;
        r2 = r6.openUrlConnection(r7);	 Catch:{ Throwable -> 0x0049, all -> 0x005a }
        if (r2 != 0) goto L_0x0010;
    L_0x0007:
        r6.closeQuietly(r0);
        if (r2 == 0) goto L_0x000f;
    L_0x000c:
        r2.disconnect();
    L_0x000f:
        return r0;
    L_0x0010:
        r3 = r2.getResponseCode();	 Catch:{ Throwable -> 0x006d, all -> 0x0067 }
        r1 = new com.umeng.socialize.net.utils.UClient$ResponseObj;	 Catch:{ Throwable -> 0x006d, all -> 0x0067 }
        r1.<init>();	 Catch:{ Throwable -> 0x006d, all -> 0x0067 }
        r1.httpResponseCode = r3;	 Catch:{ Throwable -> 0x006d, all -> 0x0067 }
        r4 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r3 != r4) goto L_0x0040;
    L_0x001f:
        r3 = r2.getInputStream();	 Catch:{ Throwable -> 0x006d, all -> 0x0067 }
        r4 = r2.getContentEncoding();	 Catch:{ Throwable -> 0x0070 }
        r5 = r2.getRequestMethod();	 Catch:{ Throwable -> 0x0070 }
        r4 = r6.parseResult(r7, r5, r4, r3);	 Catch:{ Throwable -> 0x0070 }
        r1.jsonObject = r4;	 Catch:{ Throwable -> 0x0070 }
        r4 = com.umeng.socialize.utils.UmengText.NET.JSONRESULT;	 Catch:{ Throwable -> 0x0070 }
        com.umeng.socialize.utils.SLog.debug(r4);	 Catch:{ Throwable -> 0x0070 }
        r6.closeQuietly(r3);
        if (r2 == 0) goto L_0x003e;
    L_0x003b:
        r2.disconnect();
    L_0x003e:
        r0 = r1;
        goto L_0x000f;
    L_0x0040:
        r6.closeQuietly(r0);
        if (r2 == 0) goto L_0x000f;
    L_0x0045:
        r2.disconnect();
        goto L_0x000f;
    L_0x0049:
        r1 = move-exception;
        r2 = r0;
        r3 = r0;
    L_0x004c:
        r4 = com.umeng.socialize.utils.UmengText.NET.PARSEERROR;	 Catch:{ all -> 0x006b }
        com.umeng.socialize.utils.SLog.error(r4, r1);	 Catch:{ all -> 0x006b }
        r6.closeQuietly(r3);
        if (r2 == 0) goto L_0x000f;
    L_0x0056:
        r2.disconnect();
        goto L_0x000f;
    L_0x005a:
        r1 = move-exception;
        r2 = r0;
        r3 = r0;
        r0 = r1;
    L_0x005e:
        r6.closeQuietly(r3);
        if (r2 == 0) goto L_0x0066;
    L_0x0063:
        r2.disconnect();
    L_0x0066:
        throw r0;
    L_0x0067:
        r1 = move-exception;
        r3 = r0;
        r0 = r1;
        goto L_0x005e;
    L_0x006b:
        r0 = move-exception;
        goto L_0x005e;
    L_0x006d:
        r1 = move-exception;
        r3 = r0;
        goto L_0x004c;
    L_0x0070:
        r1 = move-exception;
        goto L_0x004c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.net.utils.UClient.httpGetRequest(com.umeng.socialize.net.utils.URequest):com.umeng.socialize.net.utils.UClient$ResponseObj");
    }

    private HttpURLConnection openUrlConnection(URequest uRequest) throws IOException {
        Object toGetUrl;
        String trim = uRequest.getHttpMethod().trim();
        if (URequest.GET.equals(trim)) {
            toGetUrl = uRequest.toGetUrl();
        } else if (URequest.POST.equals(trim)) {
            toGetUrl = uRequest.mBaseUrl;
        } else {
            toGetUrl = null;
        }
        if (TextUtils.isEmpty(toGetUrl)) {
            return null;
        }
        HttpURLConnection httpURLConnection;
        URL url = new URL(toGetUrl);
        boolean z = false;
        if ("https".equals(url.getProtocol())) {
            z = true;
        }
        if (z) {
            httpURLConnection = (HttpsURLConnection) url.openConnection();
        } else {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }
        httpURLConnection.setConnectTimeout(Config.connectionTimeOut);
        httpURLConnection.setReadTimeout(Config.readSocketTimeOut);
        httpURLConnection.setRequestMethod(trim);
        if (URequest.GET.equals(trim)) {
            httpURLConnection.setRequestProperty(HttpRequest.HEADER_ACCEPT_ENCODING, HttpRequest.ENCODING_GZIP);
            if (uRequest.mHeaders != null && uRequest.mHeaders.size() > 0) {
                for (String str : uRequest.mHeaders.keySet()) {
                    httpURLConnection.setRequestProperty(str, (String) uRequest.mHeaders.get(str));
                }
            }
        } else if (URequest.POST.equals(trim)) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
        }
        return httpURLConnection;
    }

    private void verifyMethod(String str) {
        if (TextUtils.isEmpty(str) || URequest.GET.equals(str.trim()) == URequest.POST.equals(str.trim())) {
            throw new RuntimeException(UmengText.netMethodError(str));
        }
    }

    private void addBodyParams(URequest uRequest, OutputStream outputStream, String str) throws IOException {
        Object obj;
        StringBuilder stringBuilder = new StringBuilder();
        Map bodyPair = uRequest.getBodyPair();
        for (String str2 : bodyPair.keySet()) {
            if (bodyPair.get(str2) != null) {
                addFormField(stringBuilder, str2, bodyPair.get(str2).toString(), str);
            }
        }
        if (stringBuilder.length() > 0) {
            OutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.write(stringBuilder.toString().getBytes());
            outputStream = dataOutputStream;
            obj = 1;
        } else {
            obj = null;
        }
        Map filePair = uRequest.getFilePair();
        if (filePair != null && filePair.size() > 0) {
            Object obj2 = obj;
            for (String str22 : filePair.keySet()) {
                FilePair filePair2 = (FilePair) filePair.get(str22);
                byte[] bArr = filePair2.mBinaryData;
                if (bArr != null && bArr.length >= 1) {
                    addFilePart(filePair2.mFileName, bArr, str, outputStream);
                    obj2 = 1;
                }
            }
            obj = obj2;
        }
        if (obj != null) {
            finishWrite(outputStream, str);
        }
    }

    private void addFormField(StringBuilder stringBuilder, String str, String str2, String str3) {
        stringBuilder.append("--").append(str3).append(END).append("Content-Disposition: form-data; name=\"").append(str).append("\"").append(END).append("Content-Type: text/plain; charset=").append("UTF-8").append(END).append(END).append(str2).append(END);
    }

    private void addFilePart(String str, byte[] bArr, String str2, OutputStream outputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--").append(str2).append(END).append("Content-Disposition: form-data; name=\"").append("pic").append("\"; filename=\"").append(str).append("\"").append(END).append("Content-Type: ").append("application/octet-stream").append(END).append("Content-Transfer-Encoding: binary").append(END).append(END);
        outputStream.write(stringBuilder.toString().getBytes());
        outputStream.write(bArr);
        outputStream.write(END.getBytes());
    }

    private void finishWrite(OutputStream outputStream, String str) throws IOException {
        outputStream.write(END.getBytes());
        outputStream.write(("--" + str + "--").getBytes());
        outputStream.write(END.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    protected JSONObject parseResult(URequest uRequest, String str, String str2, InputStream inputStream) {
        Closeable wrapStream;
        Throwable th;
        try {
            wrapStream = wrapStream(str2, inputStream);
            Object convertStreamToString;
            JSONObject jSONObject;
            try {
                convertStreamToString = convertStreamToString(wrapStream);
                if (HttpRequest.METHOD_POST.equals(str)) {
                    jSONObject = new JSONObject(convertStreamToString);
                    closeQuietly(wrapStream);
                    return jSONObject;
                } else if (!HttpRequest.METHOD_GET.equals(str)) {
                    closeQuietly(wrapStream);
                    return null;
                } else if (TextUtils.isEmpty(convertStreamToString)) {
                    closeQuietly(wrapStream);
                    return null;
                } else {
                    jSONObject = decryptData(uRequest, convertStreamToString);
                    closeQuietly(wrapStream);
                    return jSONObject;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            wrapStream = null;
            closeQuietly(wrapStream);
            throw th;
        }
        try {
            SLog.error(NET.PARSEERROR, th);
            closeQuietly(wrapStream);
            return null;
        } catch (Throwable th4) {
            th = th4;
            closeQuietly(wrapStream);
            throw th;
        }
    }

    protected InputStream wrapStream(String str, InputStream inputStream) throws IOException {
        if (str == null || "identity".equalsIgnoreCase(str)) {
            return inputStream;
        }
        if (HttpRequest.ENCODING_GZIP.equalsIgnoreCase(str)) {
            return new GZIPInputStream(inputStream);
        }
        if ("deflate".equalsIgnoreCase(str)) {
            return new InflaterInputStream(inputStream, new Inflater(false), 512);
        }
        throw new RuntimeException("unsupported content-encoding: " + str);
    }

    protected String convertStreamToString(InputStream inputStream) {
        Closeable inputStreamReader = new InputStreamReader(inputStream);
        Closeable bufferedReader = new BufferedReader(inputStreamReader, 512);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    stringBuilder.append(readLine + "\n");
                } else {
                    closeQuietly(inputStreamReader);
                    closeQuietly(bufferedReader);
                    return stringBuilder.toString();
                }
            } catch (Throwable th) {
                closeQuietly(inputStreamReader);
                closeQuietly(bufferedReader);
                throw th;
            }
        }
    }

    private JSONObject decryptData(URequest uRequest, String str) {
        try {
            return new JSONObject(uRequest.getDecryptString(str));
        } catch (Throwable th) {
            SLog.error(NET.CREATE, th);
            return null;
        }
    }

    protected void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
                SLog.error(NET.CLOSE, th);
            }
        }
    }
}
