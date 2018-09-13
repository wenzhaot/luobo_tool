package com.umeng.commonsdk.internal.utils;

import com.talkingdata.sdk.aw;
import java.io.InputStream;

/* compiled from: CpuUtil */
public class d {

    /* compiled from: CpuUtil */
    public static class a {
        public String a;
        public String b;
        public int c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        public String i;
        public String j;
        public String k;
        public String l;
    }

    /* JADX WARNING: Removed duplicated region for block: B:116:0x0164 A:{SYNTHETIC, Splitter: B:116:0x0164} */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0169 A:{SYNTHETIC, Splitter: B:119:0x0169} */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0177 A:{SYNTHETIC, Splitter: B:125:0x0177} */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x017c A:{SYNTHETIC, Splitter: B:128:0x017c} */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0164 A:{SYNTHETIC, Splitter: B:116:0x0164} */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0169 A:{SYNTHETIC, Splitter: B:119:0x0169} */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x019c A:{Splitter: B:23:0x0032, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0164 A:{SYNTHETIC, Splitter: B:116:0x0164} */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0169 A:{SYNTHETIC, Splitter: B:119:0x0169} */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0171 A:{Splitter: B:1:0x0004, ExcHandler: all (r1_12 'th' java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:117:?, code:
            r3.close();
     */
    /* JADX WARNING: Missing block: B:120:?, code:
            r2.close();
     */
    /* JADX WARNING: Missing block: B:122:0x0171, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:123:0x0172, code:
            r2 = null;
            r3 = null;
            r0 = r1;
     */
    /* JADX WARNING: Missing block: B:126:?, code:
            r3.close();
     */
    /* JADX WARNING: Missing block: B:129:?, code:
            r2.close();
     */
    /* JADX WARNING: Missing block: B:142:0x019c, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:144:0x019f, code:
            r2 = null;
            r3 = null;
            r0 = 0;
     */
    public static com.umeng.commonsdk.internal.utils.d.a a() {
        /*
        r0 = 0;
        r4 = 0;
        r7 = 1;
        r2 = 0;
        r1 = new com.umeng.commonsdk.internal.utils.d$a;	 Catch:{ Exception -> 0x015d, all -> 0x0171 }
        r1.<init>();	 Catch:{ Exception -> 0x015d, all -> 0x0171 }
        r3 = new java.io.FileReader;	 Catch:{ Exception -> 0x019e, all -> 0x0171 }
        r5 = "/proc/cpuinfo";
        r3.<init>(r5);	 Catch:{ Exception -> 0x019e, all -> 0x0171 }
        if (r3 != 0) goto L_0x001f;
    L_0x0013:
        if (r3 == 0) goto L_0x0018;
    L_0x0015:
        r3.close();	 Catch:{ IOException -> 0x0180 }
    L_0x0018:
        if (r0 == 0) goto L_0x001d;
    L_0x001a:
        r2.close();	 Catch:{ IOException -> 0x0183 }
    L_0x001d:
        r1 = r0;
    L_0x001e:
        return r1;
    L_0x001f:
        r2 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x01a3, all -> 0x0198 }
        r2.<init>(r3);	 Catch:{ Exception -> 0x01a3, all -> 0x0198 }
        if (r2 != 0) goto L_0x0032;
    L_0x0026:
        if (r3 == 0) goto L_0x002b;
    L_0x0028:
        r3.close();	 Catch:{ IOException -> 0x0186 }
    L_0x002b:
        if (r2 == 0) goto L_0x0030;
    L_0x002d:
        r2.close();	 Catch:{ IOException -> 0x0189 }
    L_0x0030:
        r1 = r0;
        goto L_0x001e;
    L_0x0032:
        r0 = r2.readLine();	 Catch:{ Exception -> 0x01a7, all -> 0x019c }
        r6 = r4;
        r5 = r7;
        r8 = r0;
        r0 = r4;
    L_0x003a:
        r9 = android.text.TextUtils.isEmpty(r8);	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 != 0) goto L_0x0046;
    L_0x0040:
        r6 = r6 + 1;
        r9 = 30;
        if (r6 < r9) goto L_0x0053;
    L_0x0046:
        if (r3 == 0) goto L_0x004b;
    L_0x0048:
        r3.close();	 Catch:{ IOException -> 0x018c }
    L_0x004b:
        if (r2 == 0) goto L_0x0050;
    L_0x004d:
        r2.close();	 Catch:{ IOException -> 0x018f }
    L_0x0050:
        r1.c = r0;
        goto L_0x001e;
    L_0x0053:
        r9 = ":\\s+";
        r10 = 2;
        r8 = r8.split(r9, r10);	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r5 == 0) goto L_0x0068;
    L_0x005d:
        if (r8 == 0) goto L_0x0068;
    L_0x005f:
        r9 = r8.length;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 <= r7) goto L_0x0068;
    L_0x0062:
        r5 = 1;
        r5 = r8[r5];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r1.a = r5;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r5 = r4;
    L_0x0068:
        if (r8 == 0) goto L_0x007b;
    L_0x006a:
        r9 = r8.length;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 <= r7) goto L_0x007b;
    L_0x006d:
        r9 = 0;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r10 = "processor";
        r9 = r9.contains(r10);	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 == 0) goto L_0x007b;
    L_0x0079:
        r0 = r0 + 1;
    L_0x007b:
        if (r8 == 0) goto L_0x0091;
    L_0x007d:
        r9 = r8.length;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 <= r7) goto L_0x0091;
    L_0x0080:
        r9 = 0;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r10 = "Features";
        r9 = r9.contains(r10);	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 == 0) goto L_0x0091;
    L_0x008c:
        r9 = 1;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r1.d = r9;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
    L_0x0091:
        if (r8 == 0) goto L_0x00a7;
    L_0x0093:
        r9 = r8.length;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 <= r7) goto L_0x00a7;
    L_0x0096:
        r9 = 0;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r10 = "implementer";
        r9 = r9.contains(r10);	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 == 0) goto L_0x00a7;
    L_0x00a2:
        r9 = 1;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r1.e = r9;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
    L_0x00a7:
        if (r8 == 0) goto L_0x00bd;
    L_0x00a9:
        r9 = r8.length;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 <= r7) goto L_0x00bd;
    L_0x00ac:
        r9 = 0;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r10 = "architecture";
        r9 = r9.contains(r10);	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 == 0) goto L_0x00bd;
    L_0x00b8:
        r9 = 1;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r1.f = r9;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
    L_0x00bd:
        if (r8 == 0) goto L_0x00d3;
    L_0x00bf:
        r9 = r8.length;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 <= r7) goto L_0x00d3;
    L_0x00c2:
        r9 = 0;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r10 = "variant";
        r9 = r9.contains(r10);	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 == 0) goto L_0x00d3;
    L_0x00ce:
        r9 = 1;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r1.g = r9;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
    L_0x00d3:
        if (r8 == 0) goto L_0x00e9;
    L_0x00d5:
        r9 = r8.length;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 <= r7) goto L_0x00e9;
    L_0x00d8:
        r9 = 0;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r10 = "part";
        r9 = r9.contains(r10);	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 == 0) goto L_0x00e9;
    L_0x00e4:
        r9 = 1;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r1.h = r9;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
    L_0x00e9:
        if (r8 == 0) goto L_0x00ff;
    L_0x00eb:
        r9 = r8.length;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 <= r7) goto L_0x00ff;
    L_0x00ee:
        r9 = 0;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r10 = "revision";
        r9 = r9.contains(r10);	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 == 0) goto L_0x00ff;
    L_0x00fa:
        r9 = 1;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r1.i = r9;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
    L_0x00ff:
        if (r8 == 0) goto L_0x0115;
    L_0x0101:
        r9 = r8.length;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 <= r7) goto L_0x0115;
    L_0x0104:
        r9 = 0;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r10 = "Hardware";
        r9 = r9.contains(r10);	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 == 0) goto L_0x0115;
    L_0x0110:
        r9 = 1;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r1.j = r9;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
    L_0x0115:
        if (r8 == 0) goto L_0x012b;
    L_0x0117:
        r9 = r8.length;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 <= r7) goto L_0x012b;
    L_0x011a:
        r9 = 0;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r10 = "Revision";
        r9 = r9.contains(r10);	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 == 0) goto L_0x012b;
    L_0x0126:
        r9 = 1;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r1.k = r9;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
    L_0x012b:
        if (r8 == 0) goto L_0x0141;
    L_0x012d:
        r9 = r8.length;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 <= r7) goto L_0x0141;
    L_0x0130:
        r9 = 0;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r10 = "Serial";
        r9 = r9.contains(r10);	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 == 0) goto L_0x0141;
    L_0x013c:
        r9 = 1;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r1.l = r9;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
    L_0x0141:
        if (r8 == 0) goto L_0x0157;
    L_0x0143:
        r9 = r8.length;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 <= r7) goto L_0x0157;
    L_0x0146:
        r9 = 0;
        r9 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r10 = "implementer";
        r9 = r9.contains(r10);	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        if (r9 == 0) goto L_0x0157;
    L_0x0152:
        r9 = 1;
        r8 = r8[r9];	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        r1.e = r8;	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
    L_0x0157:
        r8 = r2.readLine();	 Catch:{ Exception -> 0x01aa, all -> 0x019c }
        goto L_0x003a;
    L_0x015d:
        r1 = move-exception;
        r2 = r0;
        r3 = r0;
        r1 = r0;
        r0 = r4;
    L_0x0162:
        if (r3 == 0) goto L_0x0167;
    L_0x0164:
        r3.close();	 Catch:{ IOException -> 0x0192 }
    L_0x0167:
        if (r2 == 0) goto L_0x0050;
    L_0x0169:
        r2.close();	 Catch:{ IOException -> 0x016e }
        goto L_0x0050;
    L_0x016e:
        r2 = move-exception;
        goto L_0x0050;
    L_0x0171:
        r1 = move-exception;
        r2 = r0;
        r3 = r0;
        r0 = r1;
    L_0x0175:
        if (r3 == 0) goto L_0x017a;
    L_0x0177:
        r3.close();	 Catch:{ IOException -> 0x0194 }
    L_0x017a:
        if (r2 == 0) goto L_0x017f;
    L_0x017c:
        r2.close();	 Catch:{ IOException -> 0x0196 }
    L_0x017f:
        throw r0;
    L_0x0180:
        r1 = move-exception;
        goto L_0x0018;
    L_0x0183:
        r1 = move-exception;
        goto L_0x001d;
    L_0x0186:
        r1 = move-exception;
        goto L_0x002b;
    L_0x0189:
        r1 = move-exception;
        goto L_0x0030;
    L_0x018c:
        r3 = move-exception;
        goto L_0x004b;
    L_0x018f:
        r2 = move-exception;
        goto L_0x0050;
    L_0x0192:
        r3 = move-exception;
        goto L_0x0167;
    L_0x0194:
        r1 = move-exception;
        goto L_0x017a;
    L_0x0196:
        r1 = move-exception;
        goto L_0x017f;
    L_0x0198:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
        goto L_0x0175;
    L_0x019c:
        r0 = move-exception;
        goto L_0x0175;
    L_0x019e:
        r2 = move-exception;
        r2 = r0;
        r3 = r0;
        r0 = r4;
        goto L_0x0162;
    L_0x01a3:
        r2 = move-exception;
        r2 = r0;
        r0 = r4;
        goto L_0x0162;
    L_0x01a7:
        r0 = move-exception;
        r0 = r4;
        goto L_0x0162;
    L_0x01aa:
        r4 = move-exception;
        goto L_0x0162;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.internal.utils.d.a():com.umeng.commonsdk.internal.utils.d$a");
    }

    public static String b() {
        String str = "";
        try {
            InputStream inputStream = new ProcessBuilder(new String[]{"/system/bin/cat", aw.a}).start().getInputStream();
            byte[] bArr = new byte[24];
            while (inputStream.read(bArr) != -1) {
                str = str + new String(bArr);
            }
            inputStream.close();
        } catch (Exception e) {
        }
        return str.trim();
    }

    public static String c() {
        String str = "";
        try {
            InputStream inputStream = new ProcessBuilder(new String[]{"/system/bin/cat", aw.b}).start().getInputStream();
            byte[] bArr = new byte[24];
            while (inputStream.read(bArr) != -1) {
                str = str + new String(bArr);
            }
            inputStream.close();
        } catch (Exception e) {
        }
        return str.trim();
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x002c A:{SYNTHETIC, Splitter: B:17:0x002c} */
    public static java.lang.String d() {
        /*
        r0 = "";
        r2 = 0;
        r3 = new java.io.FileReader;	 Catch:{ Exception -> 0x001f, all -> 0x0029 }
        r1 = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";
        r3.<init>(r1);	 Catch:{ Exception -> 0x001f, all -> 0x0029 }
        r1 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x001f, all -> 0x0029 }
        r1.<init>(r3);	 Catch:{ Exception -> 0x001f, all -> 0x0029 }
        r2 = r1.readLine();	 Catch:{ Exception -> 0x0037, all -> 0x0034 }
        r0 = r2.trim();	 Catch:{ Exception -> 0x0037, all -> 0x0034 }
        if (r1 == 0) goto L_0x001e;
    L_0x001b:
        r1.close();	 Catch:{ Throwable -> 0x0030 }
    L_0x001e:
        return r0;
    L_0x001f:
        r1 = move-exception;
        r1 = r2;
    L_0x0021:
        if (r1 == 0) goto L_0x001e;
    L_0x0023:
        r1.close();	 Catch:{ Throwable -> 0x0027 }
        goto L_0x001e;
    L_0x0027:
        r1 = move-exception;
        goto L_0x001e;
    L_0x0029:
        r0 = move-exception;
    L_0x002a:
        if (r2 == 0) goto L_0x002f;
    L_0x002c:
        r2.close();	 Catch:{ Throwable -> 0x0032 }
    L_0x002f:
        throw r0;
    L_0x0030:
        r1 = move-exception;
        goto L_0x001e;
    L_0x0032:
        r1 = move-exception;
        goto L_0x002f;
    L_0x0034:
        r0 = move-exception;
        r2 = r1;
        goto L_0x002a;
    L_0x0037:
        r2 = move-exception;
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.internal.utils.d.d():java.lang.String");
    }
}
