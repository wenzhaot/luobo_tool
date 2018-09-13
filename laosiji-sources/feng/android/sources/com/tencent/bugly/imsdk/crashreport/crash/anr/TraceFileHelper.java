package com.tencent.bugly.imsdk.crashreport.crash.anr;

import com.tencent.bugly.imsdk.proguard.w;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* compiled from: BUGLY */
public class TraceFileHelper {

    /* compiled from: BUGLY */
    public interface b {
        boolean a(long j);

        boolean a(long j, long j2, String str);

        boolean a(String str, int i, String str2, String str3);
    }

    /* compiled from: BUGLY */
    public static class a {
        public long a;
        public String b;
        public long c;
        public Map<String, String[]> d;
    }

    public static a readTargetDumpInfo(String str, String str2, final boolean z) {
        if (str == null || str2 == null) {
            return null;
        }
        final a aVar = new a();
        readTraceFile(str2, new b() {
            public final boolean a(String str, int i, String str2, String str3) {
                w.c("new thread %s", str);
                if (aVar.a > 0 && aVar.c > 0 && aVar.b != null) {
                    if (aVar.d == null) {
                        aVar.d = new HashMap();
                    }
                    aVar.d.put(str, new String[]{str2, str3, i});
                }
                return true;
            }

            public final boolean a(long j, long j2, String str) {
                w.c("new process %s", str);
                if (!str.equals(str)) {
                    return true;
                }
                aVar.a = j;
                aVar.b = str;
                aVar.c = j2;
                if (z) {
                    return true;
                }
                return false;
            }

            public final boolean a(long j) {
                w.c("process end %d", Long.valueOf(j));
                if (aVar.a <= 0 || aVar.c <= 0 || aVar.b == null) {
                    return true;
                }
                return false;
            }
        });
        if (aVar.a <= 0 || aVar.c <= 0 || aVar.b == null) {
            return null;
        }
        return aVar;
    }

    public static a readFirstDumpInfo(String str, final boolean z) {
        if (str == null) {
            w.e("path:%s", str);
            return null;
        }
        final a aVar = new a();
        readTraceFile(str, new b() {
            public final boolean a(String str, int i, String str2, String str3) {
                w.c("new thread %s", str);
                if (aVar.d == null) {
                    aVar.d = new HashMap();
                }
                aVar.d.put(str, new String[]{str2, str3, i});
                return true;
            }

            public final boolean a(long j, long j2, String str) {
                w.c("new process %s", str);
                aVar.a = j;
                aVar.b = str;
                aVar.c = j2;
                if (z) {
                    return true;
                }
                return false;
            }

            public final boolean a(long j) {
                w.c("process end %d", Long.valueOf(j));
                return false;
            }
        });
        if (aVar.a > 0 && aVar.c > 0 && aVar.b != null) {
            return aVar;
        }
        w.e("first dump error %s", aVar.a + " " + aVar.c + " " + aVar.b);
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:70:0x01ce A:{SYNTHETIC, Splitter: B:70:0x01ce} */
    /* JADX WARNING: Missing block: B:54:0x01a6, code:
            if (r13.a(java.lang.Long.parseLong(r1[1].toString().split("\\s")[2])) != false) goto L_0x0047;
     */
    /* JADX WARNING: Missing block: B:56:?, code:
            r7.close();
     */
    /* JADX WARNING: Missing block: B:57:0x01ad, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:59:0x01b2, code:
            if (com.tencent.bugly.imsdk.proguard.w.a(r0) == false) goto L_0x01b4;
     */
    /* JADX WARNING: Missing block: B:60:0x01b4, code:
            r0.printStackTrace();
     */
    /* JADX WARNING: Missing block: B:99:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:100:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:106:?, code:
            return;
     */
    public static void readTraceFile(java.lang.String r12, com.tencent.bugly.imsdk.crashreport.crash.anr.TraceFileHelper.b r13) {
        /*
        if (r12 == 0) goto L_0x0004;
    L_0x0002:
        if (r13 != 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r0 = new java.io.File;
        r0.<init>(r12);
        r1 = r0.exists();
        if (r1 == 0) goto L_0x0004;
    L_0x0010:
        r0.lastModified();
        r0.length();
        r1 = 0;
        r7 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x01e2, all -> 0x01ca }
        r2 = new java.io.FileReader;	 Catch:{ Exception -> 0x01e2, all -> 0x01ca }
        r2.<init>(r0);	 Catch:{ Exception -> 0x01e2, all -> 0x01ca }
        r7.<init>(r2);	 Catch:{ Exception -> 0x01e2, all -> 0x01ca }
        r0 = "-{5}\\spid\\s\\d+\\sat\\s\\d+-\\d+-\\d+\\s\\d{2}:\\d{2}:\\d{2}\\s-{5}";
        r0 = java.util.regex.Pattern.compile(r0);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = "-{5}\\send\\s\\d+\\s-{5}";
        r8 = java.util.regex.Pattern.compile(r1);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = "Cmd\\sline:\\s(\\S+)";
        r9 = java.util.regex.Pattern.compile(r1);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = "\".+\"\\s(daemon\\s){0,1}prio=\\d+\\stid=\\d+\\s.*";
        r10 = java.util.regex.Pattern.compile(r1);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r11 = new java.text.SimpleDateFormat;	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = "yyyy-MM-dd HH:mm:ss";
        r2 = java.util.Locale.US;	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r11.<init>(r1, r2);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
    L_0x0047:
        r1 = 1;
        r1 = new java.util.regex.Pattern[r1];	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r2 = 0;
        r1[r2] = r0;	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = a(r7, r1);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        if (r1 == 0) goto L_0x01b9;
    L_0x0053:
        r2 = 1;
        r1 = r1[r2];	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r2 = "\\s";
        r1 = r1.split(r2);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r2 = 2;
        r2 = r1[r2];	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r2 = java.lang.Long.parseLong(r2);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r4.<init>();	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r5 = 4;
        r5 = r1[r5];	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r5 = " ";
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r5 = 5;
        r1 = r1[r5];	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = r4.append(r1);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = r11.parse(r1);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r4 = r1.getTime();	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = 1;
        r1 = new java.util.regex.Pattern[r1];	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r6 = 0;
        r1[r6] = r9;	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = a(r7, r1);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        if (r1 != 0) goto L_0x00ab;
    L_0x009a:
        r7.close();	 Catch:{ IOException -> 0x009f }
        goto L_0x0004;
    L_0x009f:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r1 != 0) goto L_0x0004;
    L_0x00a6:
        r0.printStackTrace();
        goto L_0x0004;
    L_0x00ab:
        r6 = 1;
        r1 = r1[r6];	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = r9.matcher(r1);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1.find();	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r6 = 1;
        r1.group(r6);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r6 = 1;
        r6 = r1.group(r6);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = r13;
        r1 = r1.a(r2, r4, r6);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        if (r1 != 0) goto L_0x00da;
    L_0x00c9:
        r7.close();	 Catch:{ IOException -> 0x00ce }
        goto L_0x0004;
    L_0x00ce:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r1 != 0) goto L_0x0004;
    L_0x00d5:
        r0.printStackTrace();
        goto L_0x0004;
    L_0x00da:
        r1 = 2;
        r1 = new java.util.regex.Pattern[r1];	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r2 = 0;
        r1[r2] = r10;	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r2 = 1;
        r1[r2] = r8;	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = a(r7, r1);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        if (r1 == 0) goto L_0x0047;
    L_0x00e9:
        r2 = 0;
        r2 = r1[r2];	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        if (r2 != r10) goto L_0x018d;
    L_0x00ee:
        r2 = 1;
        r1 = r1[r2];	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r2 = "\".+\"";
        r2 = java.util.regex.Pattern.compile(r2);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r2 = r2.matcher(r1);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r2.find();	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r2 = r2.group();	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r3 = 1;
        r4 = r2.length();	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r4 = r4 + -1;
        r2 = r2.substring(r3, r4);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r3 = "NATIVE";
        r1.contains(r3);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r3 = "tid=\\d+";
        r3 = java.util.regex.Pattern.compile(r3);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = r3.matcher(r1);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1.find();	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = r1.group();	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r3 = "=";
        r3 = r1.indexOf(r3);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r3 = r3 + 1;
        r1 = r1.substring(r3);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = java.lang.Integer.parseInt(r1);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r3 = a(r7);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r4 = b(r7);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r13.a(r2, r1, r3, r4);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        goto L_0x00da;
    L_0x0147:
        r0 = move-exception;
        r1 = r7;
    L_0x0149:
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r0);	 Catch:{ all -> 0x01df }
        if (r2 != 0) goto L_0x0152;
    L_0x014f:
        r0.printStackTrace();	 Catch:{ all -> 0x01df }
    L_0x0152:
        r2 = "trace open fail:%s : %s";
        r3 = 2;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x01df }
        r4 = 0;
        r5 = r0.getClass();	 Catch:{ all -> 0x01df }
        r5 = r5.getName();	 Catch:{ all -> 0x01df }
        r3[r4] = r5;	 Catch:{ all -> 0x01df }
        r4 = 1;
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01df }
        r5.<init>();	 Catch:{ all -> 0x01df }
        r0 = r0.getMessage();	 Catch:{ all -> 0x01df }
        r0 = r5.append(r0);	 Catch:{ all -> 0x01df }
        r0 = r0.toString();	 Catch:{ all -> 0x01df }
        r3[r4] = r0;	 Catch:{ all -> 0x01df }
        com.tencent.bugly.imsdk.proguard.w.d(r2, r3);	 Catch:{ all -> 0x01df }
        if (r1 == 0) goto L_0x0004;
    L_0x017c:
        r1.close();	 Catch:{ IOException -> 0x0181 }
        goto L_0x0004;
    L_0x0181:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r1 != 0) goto L_0x0004;
    L_0x0188:
        r0.printStackTrace();
        goto L_0x0004;
    L_0x018d:
        r2 = 1;
        r1 = r1[r2];	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r2 = "\\s";
        r1 = r1.split(r2);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r2 = 2;
        r1 = r1[r2];	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r2 = java.lang.Long.parseLong(r1);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        r1 = r13.a(r2);	 Catch:{ Exception -> 0x0147, all -> 0x01dd }
        if (r1 != 0) goto L_0x0047;
    L_0x01a8:
        r7.close();	 Catch:{ IOException -> 0x01ad }
        goto L_0x0004;
    L_0x01ad:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r1 != 0) goto L_0x0004;
    L_0x01b4:
        r0.printStackTrace();
        goto L_0x0004;
    L_0x01b9:
        r7.close();	 Catch:{ IOException -> 0x01be }
        goto L_0x0004;
    L_0x01be:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r1 != 0) goto L_0x0004;
    L_0x01c5:
        r0.printStackTrace();
        goto L_0x0004;
    L_0x01ca:
        r0 = move-exception;
        r7 = r1;
    L_0x01cc:
        if (r7 == 0) goto L_0x01d1;
    L_0x01ce:
        r7.close();	 Catch:{ IOException -> 0x01d2 }
    L_0x01d1:
        throw r0;
    L_0x01d2:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x01d1;
    L_0x01d9:
        r1.printStackTrace();
        goto L_0x01d1;
    L_0x01dd:
        r0 = move-exception;
        goto L_0x01cc;
    L_0x01df:
        r0 = move-exception;
        r7 = r1;
        goto L_0x01cc;
    L_0x01e2:
        r0 = move-exception;
        goto L_0x0149;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.crash.anr.TraceFileHelper.readTraceFile(java.lang.String, com.tencent.bugly.imsdk.crashreport.crash.anr.TraceFileHelper$b):void");
    }

    private static Object[] a(BufferedReader bufferedReader, Pattern... patternArr) throws IOException {
        if (bufferedReader == null || patternArr == null) {
            return null;
        }
        while (true) {
            CharSequence readLine = bufferedReader.readLine();
            if (readLine == null) {
                return null;
            }
            for (Pattern matcher : patternArr) {
                if (matcher.matcher(readLine).matches()) {
                    return new Object[]{patternArr[r1], readLine};
                }
            }
        }
    }

    private static String a(BufferedReader bufferedReader) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return null;
            }
            stringBuffer.append(readLine + "\n");
        }
        return stringBuffer.toString();
    }

    private static String b(BufferedReader bufferedReader) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null && readLine.trim().length() > 0) {
                stringBuffer.append(readLine + "\n");
            }
        }
        return stringBuffer.toString();
    }
}
