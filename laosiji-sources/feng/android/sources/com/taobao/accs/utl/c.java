package com.taobao.accs.utl;

/* compiled from: Taobao */
public class c {
    public static final String TAG = "FileUtils";

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0048 A:{SYNTHETIC, Splitter: B:27:0x0048} */
    public static byte[] a(java.io.File r6) {
        /*
        r3 = 0;
        r0 = 0;
        if (r6 == 0) goto L_0x000a;
    L_0x0004:
        r1 = r6.exists();
        if (r1 != 0) goto L_0x0016;
    L_0x000a:
        r1 = "FileUtils";
        r2 = "filetoByte not exist";
        r3 = new java.lang.Object[r3];
        com.taobao.accs.utl.ALog.w(r1, r2, r3);
    L_0x0015:
        return r0;
    L_0x0016:
        r2 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x002a, all -> 0x0043 }
        r2.<init>(r6);	 Catch:{ Throwable -> 0x002a, all -> 0x0043 }
        r0 = a(r2);	 Catch:{ Throwable -> 0x0053 }
        if (r2 == 0) goto L_0x0015;
    L_0x0021:
        r2.close();	 Catch:{ IOException -> 0x0025 }
        goto L_0x0015;
    L_0x0025:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0015;
    L_0x002a:
        r1 = move-exception;
        r2 = r0;
    L_0x002c:
        r3 = "FileUtils";
        r4 = "FileInputStream error";
        r5 = 0;
        r5 = new java.lang.Object[r5];	 Catch:{ all -> 0x0051 }
        com.taobao.accs.utl.ALog.e(r3, r4, r1, r5);	 Catch:{ all -> 0x0051 }
        if (r2 == 0) goto L_0x0015;
    L_0x003a:
        r2.close();	 Catch:{ IOException -> 0x003e }
        goto L_0x0015;
    L_0x003e:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0015;
    L_0x0043:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
    L_0x0046:
        if (r2 == 0) goto L_0x004b;
    L_0x0048:
        r2.close();	 Catch:{ IOException -> 0x004c }
    L_0x004b:
        throw r0;
    L_0x004c:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x004b;
    L_0x0051:
        r0 = move-exception;
        goto L_0x0046;
    L_0x0053:
        r1 = move-exception;
        goto L_0x002c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.utl.c.a(java.io.File):byte[]");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0058 A:{SYNTHETIC, Splitter: B:24:0x0058} */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0067 A:{SYNTHETIC, Splitter: B:32:0x0067} */
    public static boolean a(byte[] r6, java.io.File r7) {
        /*
        r0 = 1;
        r1 = 0;
        if (r6 == 0) goto L_0x0006;
    L_0x0004:
        if (r7 != 0) goto L_0x0024;
    L_0x0006:
        r2 = "FileUtils";
        r3 = "byteToFile null";
        r4 = 4;
        r4 = new java.lang.Object[r4];
        r5 = "data";
        r4[r1] = r5;
        r4[r0] = r6;
        r0 = 2;
        r5 = "file";
        r4[r0] = r5;
        r0 = 3;
        r4[r0] = r7;
        com.taobao.accs.utl.ALog.w(r2, r3, r4);
        r0 = r1;
    L_0x0023:
        return r0;
    L_0x0024:
        if (r7 == 0) goto L_0x0074;
    L_0x0026:
        r2 = r7.exists();
        if (r2 == 0) goto L_0x002f;
    L_0x002c:
        r7.delete();
    L_0x002f:
        r3 = 0;
        r2 = new java.io.FileOutputStream;	 Catch:{ Throwable -> 0x0048, all -> 0x0063 }
        r2.<init>(r7);	 Catch:{ Throwable -> 0x0048, all -> 0x0063 }
        if (r2 == 0) goto L_0x003d;
    L_0x0037:
        r2.write(r6);	 Catch:{ Throwable -> 0x0072 }
        r2.flush();	 Catch:{ Throwable -> 0x0072 }
    L_0x003d:
        if (r2 == 0) goto L_0x0023;
    L_0x003f:
        r2.close();	 Catch:{ IOException -> 0x0043 }
        goto L_0x0023;
    L_0x0043:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0023;
    L_0x0048:
        r0 = move-exception;
        r2 = r3;
    L_0x004a:
        r3 = "FileUtils";
        r4 = "byteToFile write file error";
        r5 = 0;
        r5 = new java.lang.Object[r5];	 Catch:{ all -> 0x0070 }
        com.taobao.accs.utl.ALog.e(r3, r4, r0, r5);	 Catch:{ all -> 0x0070 }
        if (r2 == 0) goto L_0x0074;
    L_0x0058:
        r2.close();	 Catch:{ IOException -> 0x005d }
        r0 = r1;
        goto L_0x0023;
    L_0x005d:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = r1;
        goto L_0x0023;
    L_0x0063:
        r0 = move-exception;
        r2 = r3;
    L_0x0065:
        if (r2 == 0) goto L_0x006a;
    L_0x0067:
        r2.close();	 Catch:{ IOException -> 0x006b }
    L_0x006a:
        throw r0;
    L_0x006b:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x006a;
    L_0x0070:
        r0 = move-exception;
        goto L_0x0065;
    L_0x0072:
        r0 = move-exception;
        goto L_0x004a;
    L_0x0074:
        r0 = r1;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.utl.c.a(byte[], java.io.File):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x003f A:{SYNTHETIC, Splitter: B:28:0x003f} */
    public static byte[] a(java.io.InputStream r6) {
        /*
        r0 = 0;
        if (r6 != 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r2 = new java.io.ByteArrayOutputStream;	 Catch:{ Throwable -> 0x0047, all -> 0x003a }
        r2.<init>();	 Catch:{ Throwable -> 0x0047, all -> 0x003a }
        r1 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r1 = new byte[r1];	 Catch:{ Throwable -> 0x0019 }
    L_0x000d:
        r3 = r6.read(r1);	 Catch:{ Throwable -> 0x0019 }
        r4 = -1;
        if (r3 == r4) goto L_0x002e;
    L_0x0014:
        r4 = 0;
        r2.write(r1, r4, r3);	 Catch:{ Throwable -> 0x0019 }
        goto L_0x000d;
    L_0x0019:
        r1 = move-exception;
    L_0x001a:
        r3 = "FileUtils";
        r4 = "streamToByte error";
        r5 = 0;
        r5 = new java.lang.Object[r5];	 Catch:{ all -> 0x0045 }
        com.taobao.accs.utl.ALog.e(r3, r4, r1, r5);	 Catch:{ all -> 0x0045 }
        if (r2 == 0) goto L_0x0003;
    L_0x0028:
        r2.close();	 Catch:{ Exception -> 0x002c }
        goto L_0x0003;
    L_0x002c:
        r1 = move-exception;
        goto L_0x0003;
    L_0x002e:
        r0 = r2.toByteArray();	 Catch:{ Throwable -> 0x0019 }
        if (r2 == 0) goto L_0x0003;
    L_0x0034:
        r2.close();	 Catch:{ Exception -> 0x0038 }
        goto L_0x0003;
    L_0x0038:
        r1 = move-exception;
        goto L_0x0003;
    L_0x003a:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
    L_0x003d:
        if (r2 == 0) goto L_0x0042;
    L_0x003f:
        r2.close();	 Catch:{ Exception -> 0x0043 }
    L_0x0042:
        throw r0;
    L_0x0043:
        r1 = move-exception;
        goto L_0x0042;
    L_0x0045:
        r0 = move-exception;
        goto L_0x003d;
    L_0x0047:
        r1 = move-exception;
        r2 = r0;
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.utl.c.a(java.io.InputStream):byte[]");
    }
}
