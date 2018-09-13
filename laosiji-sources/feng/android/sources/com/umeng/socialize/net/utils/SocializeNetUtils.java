package com.umeng.socialize.net.utils;

import android.os.Bundle;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.NET;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

public class SocializeNetUtils {
    private static final String TAG = "SocializeNetUtils";

    public static boolean isConSpeCharacters(String str) {
        if (str.replaceAll("[一-龥]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0) {
            return false;
        }
        return true;
    }

    public static boolean isSelfAppkey(String str) {
        if (!str.equals("5126ff896c738f2bfa000438") || ContextUtil.getPackageName().equals("com.umeng.soexample")) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:124:0x0154 A:{SYNTHETIC, Splitter: B:124:0x0154} */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0154 A:{SYNTHETIC, Splitter: B:124:0x0154} */
    public static byte[] getNetData(java.lang.String r6) {
        /*
        r1 = 0;
        r0 = android.text.TextUtils.isEmpty(r6);
        if (r0 == 0) goto L_0x0009;
    L_0x0007:
        r0 = r1;
    L_0x0008:
        return r0;
    L_0x0009:
        r2 = 0;
        r3 = new java.io.ByteArrayOutputStream;	 Catch:{ Exception -> 0x018a, all -> 0x0150 }
        r3.<init>();	 Catch:{ Exception -> 0x018a, all -> 0x0150 }
        r0 = new java.net.URL;	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        r0.<init>(r6);	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        r0 = r0.openConnection();	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        r4 = 1;
        r0.setInstanceFollowRedirects(r4);	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        r4 = com.umeng.socialize.Config.connectionTimeOut;	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        r0.setConnectTimeout(r4);	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        r4 = com.umeng.socialize.Config.readSocketTimeOut;	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        r0.setReadTimeout(r4);	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        r4 = r0.getResponseCode();	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        r5 = 301; // 0x12d float:4.22E-43 double:1.487E-321;
        if (r4 != r5) goto L_0x00b0;
    L_0x0030:
        r4 = "Location";
        r0 = r0.getHeaderField(r4);	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        r4 = r0.equals(r6);	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        if (r4 == 0) goto L_0x0076;
    L_0x003d:
        r0 = com.umeng.socialize.utils.UmengText.NET.NET_AGAIN_ERROR;	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        com.umeng.socialize.utils.SLog.E(r0);	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        if (r1 == 0) goto L_0x004c;
    L_0x0044:
        r2.close();	 Catch:{ IOException -> 0x0055 }
        if (r3 == 0) goto L_0x004c;
    L_0x0049:
        r3.close();	 Catch:{ IOException -> 0x004e }
    L_0x004c:
        r0 = r1;
        goto L_0x0008;
    L_0x004e:
        r0 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r0);
        goto L_0x004c;
    L_0x0055:
        r0 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.TOOL;	 Catch:{ all -> 0x0068 }
        com.umeng.socialize.utils.SLog.error(r2, r0);	 Catch:{ all -> 0x0068 }
        if (r3 == 0) goto L_0x004c;
    L_0x005d:
        r3.close();	 Catch:{ IOException -> 0x0061 }
        goto L_0x004c;
    L_0x0061:
        r0 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r0);
        goto L_0x004c;
    L_0x0068:
        r0 = move-exception;
        if (r3 == 0) goto L_0x006e;
    L_0x006b:
        r3.close();	 Catch:{ IOException -> 0x006f }
    L_0x006e:
        throw r0;
    L_0x006f:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x006e;
    L_0x0076:
        r0 = getNetData(r0);	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        if (r1 == 0) goto L_0x0008;
    L_0x007c:
        r2.close();	 Catch:{ IOException -> 0x008d }
        if (r3 == 0) goto L_0x0008;
    L_0x0081:
        r3.close();	 Catch:{ IOException -> 0x0085 }
        goto L_0x0008;
    L_0x0085:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0008;
    L_0x008d:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.TOOL;	 Catch:{ all -> 0x00a2 }
        com.umeng.socialize.utils.SLog.error(r2, r1);	 Catch:{ all -> 0x00a2 }
        if (r3 == 0) goto L_0x0008;
    L_0x0095:
        r3.close();	 Catch:{ IOException -> 0x009a }
        goto L_0x0008;
    L_0x009a:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0008;
    L_0x00a2:
        r0 = move-exception;
        if (r3 == 0) goto L_0x00a8;
    L_0x00a5:
        r3.close();	 Catch:{ IOException -> 0x00a9 }
    L_0x00a8:
        throw r0;
    L_0x00a9:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x00a8;
    L_0x00b0:
        r2 = r0.getInputStream();	 Catch:{ Exception -> 0x018f, all -> 0x0185 }
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00da }
        r0.<init>();	 Catch:{ Exception -> 0x00da }
        r4 = com.umeng.socialize.utils.UmengText.IMAGE.IMAGEURL;	 Catch:{ Exception -> 0x00da }
        r0 = r0.append(r4);	 Catch:{ Exception -> 0x00da }
        r0 = r0.append(r6);	 Catch:{ Exception -> 0x00da }
        r0 = r0.toString();	 Catch:{ Exception -> 0x00da }
        com.umeng.socialize.utils.SLog.I(r0);	 Catch:{ Exception -> 0x00da }
        r0 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r0 = new byte[r0];	 Catch:{ Exception -> 0x00da }
    L_0x00ce:
        r4 = r2.read(r0);	 Catch:{ Exception -> 0x00da }
        r5 = -1;
        if (r4 == r5) goto L_0x00ed;
    L_0x00d5:
        r5 = 0;
        r3.write(r0, r5, r4);	 Catch:{ Exception -> 0x00da }
        goto L_0x00ce;
    L_0x00da:
        r0 = move-exception;
    L_0x00db:
        r4 = com.umeng.socialize.utils.UmengText.NET.IMAGEDOWN;	 Catch:{ all -> 0x0187 }
        com.umeng.socialize.utils.SLog.error(r4, r0);	 Catch:{ all -> 0x0187 }
        if (r2 == 0) goto L_0x00ea;
    L_0x00e2:
        r2.close();	 Catch:{ IOException -> 0x012f }
        if (r3 == 0) goto L_0x00ea;
    L_0x00e7:
        r3.close();	 Catch:{ IOException -> 0x0128 }
    L_0x00ea:
        r0 = r1;
        goto L_0x0008;
    L_0x00ed:
        r0 = r3.toByteArray();	 Catch:{ Exception -> 0x00da }
        if (r2 == 0) goto L_0x0008;
    L_0x00f3:
        r2.close();	 Catch:{ IOException -> 0x0105 }
        if (r3 == 0) goto L_0x0008;
    L_0x00f8:
        r3.close();	 Catch:{ IOException -> 0x00fd }
        goto L_0x0008;
    L_0x00fd:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0008;
    L_0x0105:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.TOOL;	 Catch:{ all -> 0x011a }
        com.umeng.socialize.utils.SLog.error(r2, r1);	 Catch:{ all -> 0x011a }
        if (r3 == 0) goto L_0x0008;
    L_0x010d:
        r3.close();	 Catch:{ IOException -> 0x0112 }
        goto L_0x0008;
    L_0x0112:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0008;
    L_0x011a:
        r0 = move-exception;
        if (r3 == 0) goto L_0x0120;
    L_0x011d:
        r3.close();	 Catch:{ IOException -> 0x0121 }
    L_0x0120:
        throw r0;
    L_0x0121:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0120;
    L_0x0128:
        r0 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r0);
        goto L_0x00ea;
    L_0x012f:
        r0 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.TOOL;	 Catch:{ all -> 0x0142 }
        com.umeng.socialize.utils.SLog.error(r2, r0);	 Catch:{ all -> 0x0142 }
        if (r3 == 0) goto L_0x00ea;
    L_0x0137:
        r3.close();	 Catch:{ IOException -> 0x013b }
        goto L_0x00ea;
    L_0x013b:
        r0 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r0);
        goto L_0x00ea;
    L_0x0142:
        r0 = move-exception;
        if (r3 == 0) goto L_0x0148;
    L_0x0145:
        r3.close();	 Catch:{ IOException -> 0x0149 }
    L_0x0148:
        throw r0;
    L_0x0149:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0148;
    L_0x0150:
        r0 = move-exception;
        r3 = r1;
    L_0x0152:
        if (r1 == 0) goto L_0x015c;
    L_0x0154:
        r1.close();	 Catch:{ IOException -> 0x0164 }
        if (r3 == 0) goto L_0x015c;
    L_0x0159:
        r3.close();	 Catch:{ IOException -> 0x015d }
    L_0x015c:
        throw r0;
    L_0x015d:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x015c;
    L_0x0164:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.TOOL;	 Catch:{ all -> 0x0177 }
        com.umeng.socialize.utils.SLog.error(r2, r1);	 Catch:{ all -> 0x0177 }
        if (r3 == 0) goto L_0x015c;
    L_0x016c:
        r3.close();	 Catch:{ IOException -> 0x0170 }
        goto L_0x015c;
    L_0x0170:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x015c;
    L_0x0177:
        r0 = move-exception;
        if (r3 == 0) goto L_0x017d;
    L_0x017a:
        r3.close();	 Catch:{ IOException -> 0x017e }
    L_0x017d:
        throw r0;
    L_0x017e:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.NET.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x017d;
    L_0x0185:
        r0 = move-exception;
        goto L_0x0152;
    L_0x0187:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0152;
    L_0x018a:
        r0 = move-exception;
        r2 = r1;
        r3 = r1;
        goto L_0x00db;
    L_0x018f:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00db;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.net.utils.SocializeNetUtils.getNetData(java.lang.String):byte[]");
    }

    public static boolean startWithHttp(String str) {
        return str.startsWith("http://") || str.startsWith("https://");
    }

    public static Bundle parseUrl(String str) {
        try {
            URL url = new URL(str);
            Bundle decodeUrl = decodeUrl(url.getQuery());
            decodeUrl.putAll(decodeUrl(url.getRef()));
            return decodeUrl;
        } catch (Throwable e) {
            SLog.error(NET.TOOL, e);
            return new Bundle();
        }
    }

    public static Bundle parseUri(String str) {
        try {
            return decodeUrl(new URI(str).getQuery());
        } catch (Throwable e) {
            SLog.error(NET.TOOL, e);
            return new Bundle();
        }
    }

    public static Bundle decodeUrl(String str) {
        Bundle bundle = new Bundle();
        if (str != null) {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                try {
                    bundle.putString(URLDecoder.decode(split2[0], "UTF-8"), URLDecoder.decode(split2[1], "UTF-8"));
                } catch (Throwable e) {
                    SLog.error(NET.TOOL, e);
                }
            }
        }
        return bundle;
    }

    public static String request(String str) {
        String str2 = "";
        try {
            URLConnection openConnection = new URL(str).openConnection();
            if (openConnection == null) {
                return str2;
            }
            openConnection.connect();
            InputStream inputStream = openConnection.getInputStream();
            if (inputStream != null) {
                return convertStreamToString(inputStream);
            }
            return str2;
        } catch (Throwable e) {
            SLog.error(NET.TOOL, e);
            return str2;
        }
    }

    public static String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    stringBuilder.append(readLine + "/n");
                } else {
                    try {
                        break;
                    } catch (Throwable e) {
                        SLog.error(NET.TOOL, e);
                    }
                }
            } catch (Throwable e2) {
                SLog.error(NET.TOOL, e2);
                try {
                    inputStream.close();
                } catch (Throwable e22) {
                    SLog.error(NET.TOOL, e22);
                }
            } catch (Throwable e222) {
                try {
                    inputStream.close();
                } catch (Throwable e3) {
                    SLog.error(NET.TOOL, e3);
                }
                throw e222;
            }
        }
        inputStream.close();
        return stringBuilder.toString();
    }
}
