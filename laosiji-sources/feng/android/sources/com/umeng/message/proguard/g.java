package com.umeng.message.proguard;

import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* compiled from: DeflaterHelper */
public class g {
    public static int a;

    /* JADX WARNING: Removed duplicated region for block: B:12:0x003a  */
    public static byte[] a(java.lang.String r6, java.lang.String r7) throws java.io.IOException {
        /*
        r0 = 0;
        r4 = 0;
        r1 = com.umeng.message.proguard.h.d(r6);
        if (r1 == 0) goto L_0x0009;
    L_0x0008:
        return r0;
    L_0x0009:
        r2 = new java.util.zip.Deflater;
        r2.<init>();
        r1 = r6.getBytes(r7);
        r2.setInput(r1);
        r2.finish();
        r1 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r3 = new byte[r1];
        a = r4;
        r1 = new java.io.ByteArrayOutputStream;	 Catch:{ all -> 0x004b }
        r1.<init>();	 Catch:{ all -> 0x004b }
    L_0x0023:
        r0 = r2.finished();	 Catch:{ all -> 0x0037 }
        if (r0 != 0) goto L_0x003e;
    L_0x0029:
        r0 = r2.deflate(r3);	 Catch:{ all -> 0x0037 }
        r4 = a;	 Catch:{ all -> 0x0037 }
        r4 = r4 + r0;
        a = r4;	 Catch:{ all -> 0x0037 }
        r4 = 0;
        r1.write(r3, r4, r0);	 Catch:{ all -> 0x0037 }
        goto L_0x0023;
    L_0x0037:
        r0 = move-exception;
    L_0x0038:
        if (r1 == 0) goto L_0x003d;
    L_0x003a:
        r1.close();
    L_0x003d:
        throw r0;
    L_0x003e:
        r2.end();	 Catch:{ all -> 0x0037 }
        if (r1 == 0) goto L_0x0046;
    L_0x0043:
        r1.close();
    L_0x0046:
        r0 = r1.toByteArray();
        goto L_0x0008;
    L_0x004b:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0038;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.proguard.g.a(java.lang.String, java.lang.String):byte[]");
    }

    public static String a(byte[] bArr, String str) throws UnsupportedEncodingException, DataFormatException {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        Inflater inflater = new Inflater();
        byte[] bArr2 = new byte[100];
        inflater.setInput(bArr, 0, bArr.length);
        StringBuilder stringBuilder = new StringBuilder();
        while (!inflater.needsInput()) {
            stringBuilder.append(new String(bArr2, 0, inflater.inflate(bArr2), str));
        }
        inflater.end();
        return stringBuilder.toString();
    }
}
