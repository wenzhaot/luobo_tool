package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;

/* compiled from: UUIDTracker */
public class s extends a {
    private static final String a = "uuid";
    private static final String e = "yosuid";
    private static final String f = "23346339";
    private Context b = null;
    private String c = null;
    private String d = null;

    public s(Context context) {
        super(a);
        this.b = context;
        this.c = null;
        this.d = null;
    }

    public String f() {
        try {
            if (!(TextUtils.isEmpty(a("ro.yunos.version", "")) || this.b == null)) {
                SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(this.b);
                if (sharedPreferences != null) {
                    String string = sharedPreferences.getString(e, "");
                    if (!TextUtils.isEmpty(string)) {
                        return string;
                    }
                    this.d = b(f);
                    if (!(TextUtils.isEmpty(this.d) || this.b == null || sharedPreferences == null)) {
                        Editor edit = sharedPreferences.edit();
                        if (edit != null) {
                            edit.putString(e, this.d).commit();
                        }
                    }
                    return this.d;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:62:0x0126 A:{SYNTHETIC, Splitter: B:62:0x0126} */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x012b A:{SYNTHETIC, Splitter: B:65:0x012b} */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0130 A:{SYNTHETIC, Splitter: B:68:0x0130} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0135  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0126 A:{SYNTHETIC, Splitter: B:62:0x0126} */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x012b A:{SYNTHETIC, Splitter: B:65:0x012b} */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0130 A:{SYNTHETIC, Splitter: B:68:0x0130} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0135  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010d A:{SYNTHETIC, Splitter: B:49:0x010d} */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0112 A:{SYNTHETIC, Splitter: B:52:0x0112} */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0117 A:{SYNTHETIC, Splitter: B:55:0x0117} */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x011c  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0152 A:{Splitter: B:14:0x00b9, ExcHandler: all (r1_31 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0126 A:{SYNTHETIC, Splitter: B:62:0x0126} */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x012b A:{SYNTHETIC, Splitter: B:65:0x012b} */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0130 A:{SYNTHETIC, Splitter: B:68:0x0130} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0135  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010d A:{SYNTHETIC, Splitter: B:49:0x010d} */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0112 A:{SYNTHETIC, Splitter: B:52:0x0112} */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0117 A:{SYNTHETIC, Splitter: B:55:0x0117} */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x011c  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0126 A:{SYNTHETIC, Splitter: B:62:0x0126} */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x012b A:{SYNTHETIC, Splitter: B:65:0x012b} */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0130 A:{SYNTHETIC, Splitter: B:68:0x0130} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0135  */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:63:?, code:
            r4.close();
     */
    /* JADX WARNING: Missing block: B:66:?, code:
            r2.close();
     */
    /* JADX WARNING: Missing block: B:69:?, code:
            r3.close();
     */
    /* JADX WARNING: Missing block: B:71:0x0135, code:
            r1.disconnect();
     */
    /* JADX WARNING: Missing block: B:84:0x0152, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:85:0x0153, code:
            r3 = null;
            r7 = r1;
            r1 = r0;
            r0 = r7;
     */
    /* JADX WARNING: Missing block: B:95:0x016c, code:
            r1 = null;
     */
    private java.lang.String b(java.lang.String r9) {
        /*
        r8 = this;
        r2 = 0;
        r0 = "ro.yunos.openuuid";
        r1 = "";
        r0 = a(r0, r1);
        r8.d = r0;
        r0 = r8.d;
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 != 0) goto L_0x0018;
    L_0x0015:
        r0 = r8.d;
    L_0x0017:
        return r0;
    L_0x0018:
        r0 = "ro.aliyun.clouduuid";
        r1 = "";
        r0 = a(r0, r1);
        r8.c = r0;
        r0 = r8.c;
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 == 0) goto L_0x0038;
    L_0x002c:
        r0 = "ro.sys.aliyun.clouduuid";
        r1 = "";
        r0 = a(r0, r1);
        r8.c = r0;
    L_0x0038:
        r0 = r8.c;
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 != 0) goto L_0x00fa;
    L_0x0040:
        r3 = 0;
        r5 = 0;
        r0 = new java.net.URL;	 Catch:{ Exception -> 0x0108, all -> 0x0120 }
        r1 = "https://cmnsguider.yunos.com:443/genDeviceToken";
        r0.<init>(r1);	 Catch:{ Exception -> 0x0108, all -> 0x0120 }
        r0 = r0.openConnection();	 Catch:{ Exception -> 0x0108, all -> 0x0120 }
        r0 = (javax.net.ssl.HttpsURLConnection) r0;	 Catch:{ Exception -> 0x0108, all -> 0x0120 }
        r1 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r0.setConnectTimeout(r1);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r1 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r0.setReadTimeout(r1);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r1 = "POST";
        r0.setRequestMethod(r1);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r1 = 1;
        r0.setDoInput(r1);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r1 = 1;
        r0.setDoOutput(r1);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r1 = 0;
        r0.setUseCaches(r1);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r1 = "Content-Type";
        r4 = "application/x-www-form-urlencoded";
        r0.setRequestProperty(r1, r4);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r1 = new com.umeng.commonsdk.statistics.idtracking.s$1;	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r1.<init>();	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r0.setHostnameVerifier(r1);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r1.<init>();	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r4 = "appKey=";
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r4 = "23338940";
        r6 = "UTF-8";
        r4 = java.net.URLEncoder.encode(r4, r6);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r4 = "&uuid=";
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r4 = "FC1FE84794417B1BEF276234F6FB4E63";
        r6 = "UTF-8";
        r4 = java.net.URLEncoder.encode(r4, r6);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r4 = new java.io.DataOutputStream;	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r6 = r0.getOutputStream();	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r4.<init>(r6);	 Catch:{ Exception -> 0x0163, all -> 0x014b }
        r4.writeBytes(r1);	 Catch:{ Exception -> 0x0167, all -> 0x0152 }
        r4.flush();	 Catch:{ Exception -> 0x0167, all -> 0x0152 }
        r1 = r0.getResponseCode();	 Catch:{ Exception -> 0x0167, all -> 0x0152 }
        r3 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r1 != r3) goto L_0x0174;
    L_0x00c7:
        r3 = r0.getInputStream();	 Catch:{ Exception -> 0x016b, all -> 0x0152 }
        r1 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x016f, all -> 0x0158 }
        r5 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x016f, all -> 0x0158 }
        r5.<init>(r3);	 Catch:{ Exception -> 0x016f, all -> 0x0158 }
        r1.<init>(r5);	 Catch:{ Exception -> 0x016f, all -> 0x0158 }
        r2 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x00e4, all -> 0x015d }
        r2.<init>();	 Catch:{ Exception -> 0x00e4, all -> 0x015d }
    L_0x00da:
        r5 = r1.readLine();	 Catch:{ Exception -> 0x00e4, all -> 0x015d }
        if (r5 == 0) goto L_0x00fe;
    L_0x00e0:
        r2.append(r5);	 Catch:{ Exception -> 0x00e4, all -> 0x015d }
        goto L_0x00da;
    L_0x00e4:
        r2 = move-exception;
        r2 = r3;
    L_0x00e6:
        if (r4 == 0) goto L_0x00eb;
    L_0x00e8:
        r4.close();	 Catch:{ Exception -> 0x0139 }
    L_0x00eb:
        if (r1 == 0) goto L_0x00f0;
    L_0x00ed:
        r1.close();	 Catch:{ Exception -> 0x013b }
    L_0x00f0:
        if (r2 == 0) goto L_0x00f5;
    L_0x00f2:
        r2.close();	 Catch:{ Exception -> 0x013d }
    L_0x00f5:
        if (r0 == 0) goto L_0x00fa;
    L_0x00f7:
        r0.disconnect();
    L_0x00fa:
        r0 = r8.d;
        goto L_0x0017;
    L_0x00fe:
        if (r2 == 0) goto L_0x0106;
    L_0x0100:
        r2 = r2.toString();	 Catch:{ Exception -> 0x00e4, all -> 0x015d }
        r8.d = r2;	 Catch:{ Exception -> 0x00e4, all -> 0x015d }
    L_0x0106:
        r2 = r3;
        goto L_0x00e6;
    L_0x0108:
        r0 = move-exception;
        r0 = r2;
        r1 = r2;
    L_0x010b:
        if (r0 == 0) goto L_0x0110;
    L_0x010d:
        r0.close();	 Catch:{ Exception -> 0x013f }
    L_0x0110:
        if (r2 == 0) goto L_0x0115;
    L_0x0112:
        r5.close();	 Catch:{ Exception -> 0x0141 }
    L_0x0115:
        if (r2 == 0) goto L_0x011a;
    L_0x0117:
        r3.close();	 Catch:{ Exception -> 0x0143 }
    L_0x011a:
        if (r1 == 0) goto L_0x00fa;
    L_0x011c:
        r1.disconnect();
        goto L_0x00fa;
    L_0x0120:
        r0 = move-exception;
        r4 = r2;
        r3 = r2;
        r1 = r2;
    L_0x0124:
        if (r4 == 0) goto L_0x0129;
    L_0x0126:
        r4.close();	 Catch:{ Exception -> 0x0145 }
    L_0x0129:
        if (r2 == 0) goto L_0x012e;
    L_0x012b:
        r2.close();	 Catch:{ Exception -> 0x0147 }
    L_0x012e:
        if (r3 == 0) goto L_0x0133;
    L_0x0130:
        r3.close();	 Catch:{ Exception -> 0x0149 }
    L_0x0133:
        if (r1 == 0) goto L_0x0138;
    L_0x0135:
        r1.disconnect();
    L_0x0138:
        throw r0;
    L_0x0139:
        r3 = move-exception;
        goto L_0x00eb;
    L_0x013b:
        r1 = move-exception;
        goto L_0x00f0;
    L_0x013d:
        r1 = move-exception;
        goto L_0x00f5;
    L_0x013f:
        r0 = move-exception;
        goto L_0x0110;
    L_0x0141:
        r0 = move-exception;
        goto L_0x0115;
    L_0x0143:
        r0 = move-exception;
        goto L_0x011a;
    L_0x0145:
        r4 = move-exception;
        goto L_0x0129;
    L_0x0147:
        r2 = move-exception;
        goto L_0x012e;
    L_0x0149:
        r2 = move-exception;
        goto L_0x0133;
    L_0x014b:
        r1 = move-exception;
        r4 = r2;
        r3 = r2;
        r7 = r1;
        r1 = r0;
        r0 = r7;
        goto L_0x0124;
    L_0x0152:
        r1 = move-exception;
        r3 = r2;
        r7 = r1;
        r1 = r0;
        r0 = r7;
        goto L_0x0124;
    L_0x0158:
        r1 = move-exception;
        r7 = r1;
        r1 = r0;
        r0 = r7;
        goto L_0x0124;
    L_0x015d:
        r2 = move-exception;
        r7 = r2;
        r2 = r1;
        r1 = r0;
        r0 = r7;
        goto L_0x0124;
    L_0x0163:
        r1 = move-exception;
        r1 = r0;
        r0 = r2;
        goto L_0x010b;
    L_0x0167:
        r1 = move-exception;
        r1 = r0;
        r0 = r4;
        goto L_0x010b;
    L_0x016b:
        r1 = move-exception;
        r1 = r2;
        goto L_0x00e6;
    L_0x016f:
        r1 = move-exception;
        r1 = r2;
        r2 = r3;
        goto L_0x00e6;
    L_0x0174:
        r1 = r2;
        goto L_0x00e6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.statistics.idtracking.s.b(java.lang.String):java.lang.String");
    }

    public static String a(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class, String.class}).invoke(null, new Object[]{str, str2});
        } catch (Exception e) {
            return str2;
        }
    }
}
