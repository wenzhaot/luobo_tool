package com.alibaba.sdk.android.utils;

import android.content.Context;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AMSDevReporter {
    private static Context a;
    /* renamed from: a */
    private static c f8a = new c("AMSDevReporter");
    /* renamed from: a */
    private static ConcurrentHashMap<AMSSdkTypeEnum, AMSReportStatusEnum> f9a = new ConcurrentHashMap();
    /* renamed from: a */
    private static final ExecutorService f10a = Executors.newSingleThreadExecutor(new a());
    /* renamed from: a */
    private static boolean f11a = false;

    public enum AMSReportStatusEnum {
        UNREPORTED,
        REPORTED
    }

    public enum AMSSdkExtInfoKeyEnum {
        AMS_EXTINFO_KEY_VERSION("SdkVersion"),
        AMS_EXTINFO_KEY_PACKAGE("PackageName");
        
        private String description;

        private AMSSdkExtInfoKeyEnum(String str) {
            this.description = str;
        }

        public String toString() {
            return this.description;
        }
    }

    public enum AMSSdkTypeEnum {
        AMS_MAN("MAN"),
        AMS_HTTPDNS("HTTPDNS"),
        AMS_MPUSH("MPUSH"),
        AMS_MAC("MAC"),
        AMS_API("API"),
        AMS_HOTFIX("HOTFIX"),
        AMS_FEEDBACK("FEEDBACK"),
        AMS_IM("IM");
        
        private String description;

        private AMSSdkTypeEnum(String str) {
            this.description = str;
        }

        public String toString() {
            return this.description;
        }
    }

    static {
        int i = 0;
        AMSSdkTypeEnum[] values = AMSSdkTypeEnum.values();
        int length = values.length;
        while (i < length) {
            a.put(values[i], AMSReportStatusEnum.UNREPORTED);
            i++;
        }
    }

    public static void setLogEnabled(boolean z) {
        a.setLogEnabled(z);
    }

    public static AMSReportStatusEnum getReportStatus(AMSSdkTypeEnum aMSSdkTypeEnum) {
        return (AMSReportStatusEnum) a.get(aMSSdkTypeEnum);
    }

    public static void asyncReport(Context context, AMSSdkTypeEnum aMSSdkTypeEnum) {
        asyncReport(context, aMSSdkTypeEnum, null);
    }

    public static void asyncReport(Context context, final AMSSdkTypeEnum aMSSdkTypeEnum, final Map<String, Object> map) {
        if (context == null) {
            a.c("Context is null, return.");
            return;
        }
        a = context;
        a.b("Add [" + aMSSdkTypeEnum.toString() + "] to report queue.");
        a = false;
        a.execute(new Runnable() {
            public void run() {
                if (AMSDevReporter.a) {
                    AMSDevReporter.a.c("Unable to execute remain task in queue, return.");
                    return;
                }
                AMSDevReporter.a.b("Get [" + aMSSdkTypeEnum.toString() + "] from report queue.");
                AMSDevReporter.a(aMSSdkTypeEnum, aMSSdkTypeEnum);
            }
        });
    }

    private static void a(AMSSdkTypeEnum aMSSdkTypeEnum, Map<String, Object> map) {
        int i = 0;
        int i2 = 5;
        String aMSSdkTypeEnum2 = aMSSdkTypeEnum.toString();
        if (a.get(aMSSdkTypeEnum) != AMSReportStatusEnum.UNREPORTED) {
            a.b("[" + aMSSdkTypeEnum2 + "] already reported, return.");
            return;
        }
        while (true) {
            a.b("Report [" + aMSSdkTypeEnum2 + "], times: [" + (i + 1) + "].");
            if (!a(aMSSdkTypeEnum, (Map) map)) {
                i++;
                if (i > 10) {
                    a.c("Report [" + aMSSdkTypeEnum2 + "] stat failed, exceed max retry times, return.");
                    a.put(aMSSdkTypeEnum, AMSReportStatusEnum.UNREPORTED);
                    a = true;
                    break;
                }
                a.b("Report [" + aMSSdkTypeEnum2 + "] failed, wait for [" + i2 + "] seconds.");
                d.a((double) i2);
                i2 *= 2;
                if (i2 >= 60) {
                    i2 = 60;
                }
            } else {
                a.b("Report [" + aMSSdkTypeEnum2 + "] stat success.");
                a.put(aMSSdkTypeEnum, AMSReportStatusEnum.REPORTED);
                break;
            }
        }
        if (a) {
            a.c("Report [" + aMSSdkTypeEnum2 + "] failed, clear remain report in queue.");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:66:0x01f7  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x01fc A:{SYNTHETIC, Splitter: B:68:0x01fc} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0201 A:{Catch:{ IOException -> 0x0205 }} */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01f7  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x01fc A:{SYNTHETIC, Splitter: B:68:0x01fc} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0201 A:{Catch:{ IOException -> 0x0205 }} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0153  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0158 A:{SYNTHETIC, Splitter: B:26:0x0158} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x015d A:{Catch:{ IOException -> 0x01ea }} */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01f7  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x01fc A:{SYNTHETIC, Splitter: B:68:0x01fc} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0201 A:{Catch:{ IOException -> 0x0205 }} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0153  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0158 A:{SYNTHETIC, Splitter: B:26:0x0158} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x015d A:{Catch:{ IOException -> 0x01ea }} */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01f7  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x01fc A:{SYNTHETIC, Splitter: B:68:0x01fc} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0201 A:{Catch:{ IOException -> 0x0205 }} */
    /* renamed from: a */
    private static boolean m7a(com.alibaba.sdk.android.utils.AMSDevReporter.AMSSdkTypeEnum r10, java.util.Map<java.lang.String, java.lang.Object> r11) {
        /*
        r2 = 1;
        r4 = 0;
        r3 = 0;
        r0 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r1 = 14;
        if (r0 < r1) goto L_0x000f;
    L_0x0009:
        r0 = 40965; // 0xa005 float:5.7404E-41 double:2.02394E-319;
        android.net.TrafficStats.setThreadStatsTag(r0);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
    L_0x000f:
        r0 = a;	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r0 = com.ut.device.UTDevice.getUtdid(r0);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r1 = a;	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5.<init>();	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r6 = "stat: ";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5 = r5.append(r0);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r1.b(r5);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r1 = a(r10, r0, r11);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r0.<init>();	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5 = "23356390Raw";
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5 = com.alibaba.sdk.android.utils.d.a(r1);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r0 = r0.toString();	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5.<init>();	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r6 = "16594f72217bece5a457b4803a48f2da";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r0 = com.alibaba.sdk.android.utils.d.a(r0);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r0 = r5.append(r0);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5 = "16594f72217bece5a457b4803a48f2da";
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r0 = r0.toString();	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r0 = com.alibaba.sdk.android.utils.d.b(r0);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5.<init>();	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r6 = "http://adash.man.aliyuncs.com:80/man/api?ak=23356390&s=";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r0 = r5.append(r0);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r0 = r0.toString();	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5 = new java.net.URL;	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5.<init>(r0);	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r0 = r5.openConnection();	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x0220, all -> 0x01f2 }
        r5 = 1;
        r0.setDoOutput(r5);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r5 = 0;
        r0.setUseCaches(r5);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r5 = 15000; // 0x3a98 float:2.102E-41 double:7.411E-320;
        r0.setConnectTimeout(r5);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r5.<init>();	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r6 = "===";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r6 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r6 = "===";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r5 = r5.toString();	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r6 = "Content-Type";
        r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r7.<init>();	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r8 = "multipart/form-data; boundary=";
        r7 = r7.append(r8);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r7 = r7.append(r5);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r7 = r7.toString();	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r0.setRequestProperty(r6, r7);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r6.<init>();	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r7 = "--";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r6 = r6.append(r5);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r7 = "\r\nContent-Disposition: form-data; name=\"";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r7 = "Raw";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r7 = "\"\r\nContent-Type: text/plain; charset=UTF-8\r\n\r\n";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r1 = r6.append(r1);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r6 = "\r\n--";
        r1 = r1.append(r6);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r1 = r1.append(r5);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r5 = "--\r\n";
        r1 = r1.append(r5);	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r5 = r0.getOutputStream();	 Catch:{ Exception -> 0x0225, all -> 0x020c }
        r1 = r1.getBytes();	 Catch:{ Exception -> 0x022b, all -> 0x0211 }
        r5.write(r1);	 Catch:{ Exception -> 0x022b, all -> 0x0211 }
        r1 = r0.getResponseCode();	 Catch:{ Exception -> 0x022b, all -> 0x0211 }
        r6 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r1 != r6) goto L_0x01cf;
    L_0x0124:
        r6 = new java.io.DataInputStream;	 Catch:{ Exception -> 0x022b, all -> 0x0211 }
        r1 = r0.getInputStream();	 Catch:{ Exception -> 0x022b, all -> 0x0211 }
        r6.<init>(r1);	 Catch:{ Exception -> 0x022b, all -> 0x0211 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r1.<init>();	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r4 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r4 = new byte[r4];	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
    L_0x0136:
        r7 = r6.read(r4);	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r8 = -1;
        if (r7 == r8) goto L_0x0162;
    L_0x013d:
        r8 = new java.lang.String;	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r9 = 0;
        r8.<init>(r4, r9, r7);	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r1.append(r8);	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        goto L_0x0136;
    L_0x0147:
        r1 = move-exception;
        r4 = r6;
        r2 = r0;
        r0 = r1;
        r1 = r5;
    L_0x014c:
        r5 = a;	 Catch:{ all -> 0x021c }
        r5.a(r0);	 Catch:{ all -> 0x021c }
        if (r2 == 0) goto L_0x0156;
    L_0x0153:
        r2.disconnect();
    L_0x0156:
        if (r1 == 0) goto L_0x015b;
    L_0x0158:
        r1.close();	 Catch:{ IOException -> 0x01ea }
    L_0x015b:
        if (r4 == 0) goto L_0x0160;
    L_0x015d:
        r4.close();	 Catch:{ IOException -> 0x01ea }
    L_0x0160:
        r0 = r3;
    L_0x0161:
        return r0;
    L_0x0162:
        r4 = a;	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r7.<init>();	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r8 = "Get MAN response: ";
        r7 = r7.append(r8);	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r8 = r1.toString();	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r7 = r7.append(r8);	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r7 = r7.toString();	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r4.a(r7);	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r4 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x01b2 }
        r1 = r1.toString();	 Catch:{ JSONException -> 0x01b2 }
        r4.<init>(r1);	 Catch:{ JSONException -> 0x01b2 }
        r1 = "success";
        r1 = r4.get(r1);	 Catch:{ JSONException -> 0x01b2 }
        r1 = (java.lang.String) r1;	 Catch:{ JSONException -> 0x01b2 }
        r4 = "success";
        r1 = r1.equals(r4);	 Catch:{ JSONException -> 0x01b2 }
        if (r1 == 0) goto L_0x01b8;
    L_0x019a:
        if (r0 == 0) goto L_0x019f;
    L_0x019c:
        r0.disconnect();
    L_0x019f:
        if (r5 == 0) goto L_0x01a4;
    L_0x01a1:
        r5.close();	 Catch:{ IOException -> 0x01ab }
    L_0x01a4:
        if (r6 == 0) goto L_0x01a9;
    L_0x01a6:
        r6.close();	 Catch:{ IOException -> 0x01ab }
    L_0x01a9:
        r0 = r2;
        goto L_0x0161;
    L_0x01ab:
        r0 = move-exception;
        r1 = a;
        r1.a(r0);
        goto L_0x01a9;
    L_0x01b2:
        r1 = move-exception;
        r2 = a;	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
        r2.a(r1);	 Catch:{ Exception -> 0x0147, all -> 0x0217 }
    L_0x01b8:
        if (r0 == 0) goto L_0x01bd;
    L_0x01ba:
        r0.disconnect();
    L_0x01bd:
        if (r5 == 0) goto L_0x01c2;
    L_0x01bf:
        r5.close();	 Catch:{ IOException -> 0x01c8 }
    L_0x01c2:
        if (r6 == 0) goto L_0x0160;
    L_0x01c4:
        r6.close();	 Catch:{ IOException -> 0x01c8 }
        goto L_0x0160;
    L_0x01c8:
        r0 = move-exception;
        r1 = a;
        r1.a(r0);
        goto L_0x0160;
    L_0x01cf:
        r2 = a;	 Catch:{ Exception -> 0x022b, all -> 0x0211 }
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x022b, all -> 0x0211 }
        r6.<init>();	 Catch:{ Exception -> 0x022b, all -> 0x0211 }
        r7 = "MAN API error, response code: ";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x022b, all -> 0x0211 }
        r1 = r6.append(r1);	 Catch:{ Exception -> 0x022b, all -> 0x0211 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x022b, all -> 0x0211 }
        r2.c(r1);	 Catch:{ Exception -> 0x022b, all -> 0x0211 }
        r6 = r4;
        goto L_0x01b8;
    L_0x01ea:
        r0 = move-exception;
        r1 = a;
        r1.a(r0);
        goto L_0x0160;
    L_0x01f2:
        r0 = move-exception;
        r6 = r4;
        r2 = r4;
    L_0x01f5:
        if (r2 == 0) goto L_0x01fa;
    L_0x01f7:
        r2.disconnect();
    L_0x01fa:
        if (r4 == 0) goto L_0x01ff;
    L_0x01fc:
        r4.close();	 Catch:{ IOException -> 0x0205 }
    L_0x01ff:
        if (r6 == 0) goto L_0x0204;
    L_0x0201:
        r6.close();	 Catch:{ IOException -> 0x0205 }
    L_0x0204:
        throw r0;
    L_0x0205:
        r1 = move-exception;
        r2 = a;
        r2.a(r1);
        goto L_0x0204;
    L_0x020c:
        r1 = move-exception;
        r6 = r4;
        r2 = r0;
        r0 = r1;
        goto L_0x01f5;
    L_0x0211:
        r1 = move-exception;
        r6 = r4;
        r2 = r0;
        r0 = r1;
        r4 = r5;
        goto L_0x01f5;
    L_0x0217:
        r1 = move-exception;
        r4 = r5;
        r2 = r0;
        r0 = r1;
        goto L_0x01f5;
    L_0x021c:
        r0 = move-exception;
        r6 = r4;
        r4 = r1;
        goto L_0x01f5;
    L_0x0220:
        r0 = move-exception;
        r1 = r4;
        r2 = r4;
        goto L_0x014c;
    L_0x0225:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
        r1 = r4;
        goto L_0x014c;
    L_0x022b:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
        r1 = r5;
        goto L_0x014c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.utils.AMSDevReporter.a(com.alibaba.sdk.android.utils.AMSDevReporter$AMSSdkTypeEnum, java.util.Map):boolean");
    }

    private static String a(AMSSdkTypeEnum aMSSdkTypeEnum, String str, Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(aMSSdkTypeEnum).append("-").append(str);
        if (map != null) {
            String str2 = (String) map.get(AMSSdkExtInfoKeyEnum.AMS_EXTINFO_KEY_VERSION.toString());
            if (!d.a(str2)) {
                stringBuilder.append("-").append(str2);
            }
            str2 = (String) map.get(AMSSdkExtInfoKeyEnum.AMS_EXTINFO_KEY_PACKAGE.toString());
            if (!d.a(str2)) {
                stringBuilder.append("-").append(str2);
            }
        }
        return stringBuilder.toString();
    }
}
