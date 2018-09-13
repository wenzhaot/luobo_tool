package com.umeng.socialize.b.b;

/* compiled from: ImageFormat */
public class d {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 4;
    public static final int f = 5;
    public static final int g = 6;
    public static final int h = 7;
    public static final int i = 8;
    public static final int j = 9;
    public static final int k = 10;
    public static final int l = 11;
    public static final String[] m = new String[]{"jpeg", "gif", "png", "bmp", "pcx", "iff", "ras", "pbm", "pgm", "ppm", "psd", "swf"};

    /* JADX WARNING: Removed duplicated region for block: B:135:0x0160 A:{SYNTHETIC, Splitter: B:135:0x0160} */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x0171 A:{SYNTHETIC, Splitter: B:142:0x0171} */
    public static java.lang.String a(byte[] r8) {
        /*
        r7 = 66;
        r6 = 10;
        r5 = 1;
        r4 = 6;
        r2 = 0;
        r1 = new java.io.ByteArrayInputStream;	 Catch:{ Exception -> 0x0154, all -> 0x016d }
        r1.<init>(r8);	 Catch:{ Exception -> 0x0154, all -> 0x016d }
        r0 = r1.read();	 Catch:{ Exception -> 0x017e }
        r2 = r1.read();	 Catch:{ Exception -> 0x017e }
        r3 = 71;
        if (r0 != r3) goto L_0x002e;
    L_0x0018:
        r3 = 73;
        if (r2 != r3) goto L_0x002e;
    L_0x001c:
        r0 = m;	 Catch:{ Exception -> 0x017e }
        r2 = 1;
        r0 = r0[r2];	 Catch:{ Exception -> 0x017e }
        if (r1 == 0) goto L_0x0026;
    L_0x0023:
        r1.close();	 Catch:{ IOException -> 0x0027 }
    L_0x0026:
        return r0;
    L_0x0027:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x002e:
        r3 = 137; // 0x89 float:1.92E-43 double:6.77E-322;
        if (r0 != r3) goto L_0x0048;
    L_0x0032:
        r3 = 80;
        if (r2 != r3) goto L_0x0048;
    L_0x0036:
        r0 = m;	 Catch:{ Exception -> 0x017e }
        r2 = 2;
        r0 = r0[r2];	 Catch:{ Exception -> 0x017e }
        if (r1 == 0) goto L_0x0026;
    L_0x003d:
        r1.close();	 Catch:{ IOException -> 0x0041 }
        goto L_0x0026;
    L_0x0041:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x0048:
        r3 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        if (r0 != r3) goto L_0x0062;
    L_0x004c:
        r3 = 216; // 0xd8 float:3.03E-43 double:1.067E-321;
        if (r2 != r3) goto L_0x0062;
    L_0x0050:
        r0 = m;	 Catch:{ Exception -> 0x017e }
        r2 = 0;
        r0 = r0[r2];	 Catch:{ Exception -> 0x017e }
        if (r1 == 0) goto L_0x0026;
    L_0x0057:
        r1.close();	 Catch:{ IOException -> 0x005b }
        goto L_0x0026;
    L_0x005b:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x0062:
        if (r0 != r7) goto L_0x007a;
    L_0x0064:
        r3 = 77;
        if (r2 != r3) goto L_0x007a;
    L_0x0068:
        r0 = m;	 Catch:{ Exception -> 0x017e }
        r2 = 3;
        r0 = r0[r2];	 Catch:{ Exception -> 0x017e }
        if (r1 == 0) goto L_0x0026;
    L_0x006f:
        r1.close();	 Catch:{ IOException -> 0x0073 }
        goto L_0x0026;
    L_0x0073:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x007a:
        if (r0 != r6) goto L_0x0090;
    L_0x007c:
        if (r2 >= r4) goto L_0x0090;
    L_0x007e:
        r0 = m;	 Catch:{ Exception -> 0x017e }
        r2 = 4;
        r0 = r0[r2];	 Catch:{ Exception -> 0x017e }
        if (r1 == 0) goto L_0x0026;
    L_0x0085:
        r1.close();	 Catch:{ IOException -> 0x0089 }
        goto L_0x0026;
    L_0x0089:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x0090:
        r3 = 70;
        if (r0 != r3) goto L_0x00ab;
    L_0x0094:
        r3 = 79;
        if (r2 != r3) goto L_0x00ab;
    L_0x0098:
        r0 = m;	 Catch:{ Exception -> 0x017e }
        r2 = 5;
        r0 = r0[r2];	 Catch:{ Exception -> 0x017e }
        if (r1 == 0) goto L_0x0026;
    L_0x009f:
        r1.close();	 Catch:{ IOException -> 0x00a3 }
        goto L_0x0026;
    L_0x00a3:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x00ab:
        r3 = 89;
        if (r0 != r3) goto L_0x00c7;
    L_0x00af:
        r3 = 166; // 0xa6 float:2.33E-43 double:8.2E-322;
        if (r2 != r3) goto L_0x00c7;
    L_0x00b3:
        r0 = m;	 Catch:{ Exception -> 0x017e }
        r2 = 6;
        r0 = r0[r2];	 Catch:{ Exception -> 0x017e }
        if (r1 == 0) goto L_0x0026;
    L_0x00ba:
        r1.close();	 Catch:{ IOException -> 0x00bf }
        goto L_0x0026;
    L_0x00bf:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x00c7:
        r3 = 80;
        if (r0 != r3) goto L_0x010a;
    L_0x00cb:
        r3 = 49;
        if (r2 < r3) goto L_0x010a;
    L_0x00cf:
        r3 = 54;
        if (r2 > r3) goto L_0x010a;
    L_0x00d3:
        r0 = r2 + -48;
        if (r0 < r5) goto L_0x00d9;
    L_0x00d7:
        if (r0 <= r4) goto L_0x00eb;
    L_0x00d9:
        r0 = "";
        if (r1 == 0) goto L_0x0026;
    L_0x00de:
        r1.close();	 Catch:{ IOException -> 0x00e3 }
        goto L_0x0026;
    L_0x00e3:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x00eb:
        r2 = 3;
        r2 = new int[r2];	 Catch:{ Exception -> 0x017e }
        r2 = {7, 8, 9};	 Catch:{ Exception -> 0x017e }
        r0 = r0 + -1;
        r0 = r0 % 3;
        r0 = r2[r0];	 Catch:{ Exception -> 0x017e }
        r2 = m;	 Catch:{ Exception -> 0x017e }
        r0 = r2[r0];	 Catch:{ Exception -> 0x017e }
        if (r1 == 0) goto L_0x0026;
    L_0x00fd:
        r1.close();	 Catch:{ IOException -> 0x0102 }
        goto L_0x0026;
    L_0x0102:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x010a:
        r3 = 56;
        if (r0 != r3) goto L_0x0125;
    L_0x010e:
        if (r2 != r7) goto L_0x0125;
    L_0x0110:
        r0 = m;	 Catch:{ Exception -> 0x017e }
        r2 = 10;
        r0 = r0[r2];	 Catch:{ Exception -> 0x017e }
        if (r1 == 0) goto L_0x0026;
    L_0x0118:
        r1.close();	 Catch:{ IOException -> 0x011d }
        goto L_0x0026;
    L_0x011d:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x0125:
        r3 = 70;
        if (r0 != r3) goto L_0x0142;
    L_0x0129:
        r0 = 87;
        if (r2 != r0) goto L_0x0142;
    L_0x012d:
        r0 = m;	 Catch:{ Exception -> 0x017e }
        r2 = 11;
        r0 = r0[r2];	 Catch:{ Exception -> 0x017e }
        if (r1 == 0) goto L_0x0026;
    L_0x0135:
        r1.close();	 Catch:{ IOException -> 0x013a }
        goto L_0x0026;
    L_0x013a:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x0142:
        r0 = "";
        if (r1 == 0) goto L_0x0026;
    L_0x0147:
        r1.close();	 Catch:{ IOException -> 0x014c }
        goto L_0x0026;
    L_0x014c:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x0154:
        r0 = move-exception;
        r1 = r2;
    L_0x0156:
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CHECK_FORMAT_ERROR;	 Catch:{ all -> 0x017c }
        com.umeng.socialize.utils.SLog.error(r2, r0);	 Catch:{ all -> 0x017c }
        r0 = "";
        if (r1 == 0) goto L_0x0026;
    L_0x0160:
        r1.close();	 Catch:{ IOException -> 0x0165 }
        goto L_0x0026;
    L_0x0165:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x016d:
        r0 = move-exception;
        r1 = r2;
    L_0x016f:
        if (r1 == 0) goto L_0x0174;
    L_0x0171:
        r1.close();	 Catch:{ IOException -> 0x0175 }
    L_0x0174:
        throw r0;
    L_0x0175:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0174;
    L_0x017c:
        r0 = move-exception;
        goto L_0x016f;
    L_0x017e:
        r0 = move-exception;
        goto L_0x0156;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.b.b.d.a(byte[]):java.lang.String");
    }
}
