package com.feng.car.video.shortvideo;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.ijk.media.player.IjkMediaMeta;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;

public class FileUtils {
    public static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final int MD5_FILE_BUFFER_LENGHT = 1048576;
    private static final String TAG = "FileUtils";
    private static final byte[] gSyncCode = new byte[0];

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Unknown predecessor block by arg (r8_25 ?) in PHI: PHI: (r8_28 ?) = (r8_25 ?), (r8_22 ?), (r8_22 ?), (r8_22 ?) binds: {(r8_22 ?)=B:101:0x00c6}
        	at jadx.core.dex.instructions.PhiInsn.replaceArg(PhiInsn.java:78)
        	at jadx.core.dex.visitors.ssa.SSATransform.inlinePhiInsn(SSATransform.java:392)
        	at jadx.core.dex.visitors.ssa.SSATransform.replacePhiWithMove(SSATransform.java:360)
        	at jadx.core.dex.visitors.ssa.SSATransform.fixPhiWithSameArgs(SSATransform.java:300)
        	at jadx.core.dex.visitors.ssa.SSATransform.fixUselessPhi(SSATransform.java:275)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:61)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1378)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.base/java.lang.Iterable.forEach(Iterable.java:75)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:292)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        */
    public static java.lang.String getConfigFromAsserts(android.content.Context r10, java.lang.String r11) {
        /*
        r0 = r10.getAssets();
        r4 = 0;
        r1 = 0;
        r5 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x00d2 }
        r8 = r0.open(r11);	 Catch:{ IOException -> 0x00d2 }
        r5.<init>(r8);	 Catch:{ IOException -> 0x00d2 }
        r2 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x00d4, all -> 0x00cb }
        r2.<init>(r5);	 Catch:{ IOException -> 0x00d4, all -> 0x00cb }
        r6 = "";	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r7 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r7.<init>();	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
    L_0x001c:
        r6 = r2.readLine();	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        if (r6 == 0) goto L_0x0070;	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
    L_0x0022:
        r6 = r6.trim();	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r8 = "#.*";	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r8 = r6.matches(r8);	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        if (r8 != 0) goto L_0x001c;	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
    L_0x002f:
        r8 = ".*=.*";	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r8 = r6.matches(r8);	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        if (r8 == 0) goto L_0x001c;	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
    L_0x0038:
        r8 = ".*#.*";	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r8 = r6.matches(r8);	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        if (r8 == 0) goto L_0x004d;	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
    L_0x0041:
        r8 = 0;	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r9 = "#";	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r9 = r6.indexOf(r9);	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r6 = r6.substring(r8, r9);	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
    L_0x004d:
        r8 = r6.trim();	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r8 = r7.append(r8);	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r9 = "\n";	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r8.append(r9);	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        goto L_0x001c;
    L_0x005c:
        r3 = move-exception;
        r1 = r2;
        r4 = r5;
    L_0x005f:
        r3.printStackTrace();	 Catch:{ all -> 0x00ab }
        if (r1 == 0) goto L_0x0068;
    L_0x0064:
        r1.close();	 Catch:{ IOException -> 0x0099, all -> 0x009f }
        r1 = 0;
    L_0x0068:
        if (r4 == 0) goto L_0x006e;
    L_0x006a:
        r4.close();	 Catch:{ IOException -> 0x00a2, all -> 0x00a8 }
        r4 = 0;
    L_0x006e:
        r8 = 0;
    L_0x006f:
        return r8;
    L_0x0070:
        r2.close();	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r5.close();	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        r8 = r7.toString();	 Catch:{ IOException -> 0x005c, all -> 0x00ce }
        if (r2 == 0) goto L_0x00d9;
    L_0x007c:
        r2.close();	 Catch:{ IOException -> 0x0087, all -> 0x008d }
        r1 = 0;
    L_0x0080:
        if (r5 == 0) goto L_0x00d7;
    L_0x0082:
        r5.close();	 Catch:{ IOException -> 0x0090, all -> 0x0096 }
        r4 = 0;
        goto L_0x006f;
    L_0x0087:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ IOException -> 0x0087, all -> 0x008d }
        r1 = 0;
        goto L_0x0080;
    L_0x008d:
        r8 = move-exception;
        r1 = 0;
        throw r8;
    L_0x0090:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ IOException -> 0x0090, all -> 0x0096 }
        r4 = 0;
        goto L_0x006f;
    L_0x0096:
        r8 = move-exception;
        r4 = 0;
        throw r8;
    L_0x0099:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ IOException -> 0x0099, all -> 0x009f }
        r1 = 0;
        goto L_0x0068;
    L_0x009f:
        r8 = move-exception;
        r1 = 0;
        throw r8;
    L_0x00a2:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ IOException -> 0x00a2, all -> 0x00a8 }
        r4 = 0;
        goto L_0x006e;
    L_0x00a8:
        r8 = move-exception;
        r4 = 0;
        throw r8;
    L_0x00ab:
        r8 = move-exception;
    L_0x00ac:
        if (r1 == 0) goto L_0x00b2;
    L_0x00ae:
        r1.close();	 Catch:{ IOException -> 0x00b9, all -> 0x00bf }
        r1 = 0;
    L_0x00b2:
        if (r4 == 0) goto L_0x00b8;
    L_0x00b4:
        r4.close();	 Catch:{ IOException -> 0x00c2, all -> 0x00c8 }
        r4 = 0;
    L_0x00b8:
        throw r8;
    L_0x00b9:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ IOException -> 0x00b9, all -> 0x00bf }
        r1 = 0;
        goto L_0x00b2;
    L_0x00bf:
        r8 = move-exception;
        r1 = 0;
        throw r8;
    L_0x00c2:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ IOException -> 0x00c2, all -> 0x00c8 }
        r4 = 0;
        goto L_0x00b8;
    L_0x00c8:
        r8 = move-exception;
        r4 = 0;
        throw r8;
    L_0x00cb:
        r8 = move-exception;
        r4 = r5;
        goto L_0x00ac;
    L_0x00ce:
        r8 = move-exception;
        r1 = r2;
        r4 = r5;
        goto L_0x00ac;
    L_0x00d2:
        r3 = move-exception;
        goto L_0x005f;
    L_0x00d4:
        r3 = move-exception;
        r4 = r5;
        goto L_0x005f;
    L_0x00d7:
        r4 = r5;
        goto L_0x006f;
    L_0x00d9:
        r1 = r2;
        goto L_0x0080;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.shortvideo.FileUtils.getConfigFromAsserts(android.content.Context, java.lang.String):java.lang.String");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Unknown predecessor block by arg (r7_25 ?) in PHI: PHI: (r7_28 ?) = (r7_25 ?), (r7_22 ?), (r7_22 ?), (r7_22 ?) binds: {(r7_22 ?)=B:101:0x00c3}
        	at jadx.core.dex.instructions.PhiInsn.replaceArg(PhiInsn.java:78)
        	at jadx.core.dex.visitors.ssa.SSATransform.inlinePhiInsn(SSATransform.java:392)
        	at jadx.core.dex.visitors.ssa.SSATransform.replacePhiWithMove(SSATransform.java:360)
        	at jadx.core.dex.visitors.ssa.SSATransform.fixPhiWithSameArgs(SSATransform.java:300)
        	at jadx.core.dex.visitors.ssa.SSATransform.fixUselessPhi(SSATransform.java:275)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:61)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1378)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.base/java.lang.Iterable.forEach(Iterable.java:75)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:292)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        */
    public static java.lang.String getConfigFromFile(android.content.Context r9, java.lang.String r10) {
        /*
        r3 = 0;
        r0 = 0;
        r4 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x00cf }
        r7 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x00cf }
        r7.<init>(r10);	 Catch:{ IOException -> 0x00cf }
        r4.<init>(r7);	 Catch:{ IOException -> 0x00cf }
        r1 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x00d1, all -> 0x00c8 }
        r1.<init>(r4);	 Catch:{ IOException -> 0x00d1, all -> 0x00c8 }
        r5 = "";	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r6 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r6.<init>();	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
    L_0x0019:
        r5 = r1.readLine();	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        if (r5 == 0) goto L_0x006d;	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
    L_0x001f:
        r5 = r5.trim();	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r7 = "#.*";	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r7 = r5.matches(r7);	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        if (r7 != 0) goto L_0x0019;	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
    L_0x002c:
        r7 = ".*=.*";	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r7 = r5.matches(r7);	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        if (r7 == 0) goto L_0x0019;	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
    L_0x0035:
        r7 = ".*#.*";	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r7 = r5.matches(r7);	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        if (r7 == 0) goto L_0x004a;	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
    L_0x003e:
        r7 = 0;	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r8 = "#";	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r8 = r5.indexOf(r8);	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r5 = r5.substring(r7, r8);	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
    L_0x004a:
        r7 = r5.trim();	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r7 = r6.append(r7);	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r8 = "\n";	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r7.append(r8);	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        goto L_0x0019;
    L_0x0059:
        r2 = move-exception;
        r0 = r1;
        r3 = r4;
    L_0x005c:
        r2.printStackTrace();	 Catch:{ all -> 0x00a8 }
        if (r0 == 0) goto L_0x0065;
    L_0x0061:
        r0.close();	 Catch:{ IOException -> 0x0096, all -> 0x009c }
        r0 = 0;
    L_0x0065:
        if (r3 == 0) goto L_0x006b;
    L_0x0067:
        r3.close();	 Catch:{ IOException -> 0x009f, all -> 0x00a5 }
        r3 = 0;
    L_0x006b:
        r7 = 0;
    L_0x006c:
        return r7;
    L_0x006d:
        r1.close();	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r4.close();	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        r7 = r6.toString();	 Catch:{ IOException -> 0x0059, all -> 0x00cb }
        if (r1 == 0) goto L_0x00d6;
    L_0x0079:
        r1.close();	 Catch:{ IOException -> 0x0084, all -> 0x008a }
        r0 = 0;
    L_0x007d:
        if (r4 == 0) goto L_0x00d4;
    L_0x007f:
        r4.close();	 Catch:{ IOException -> 0x008d, all -> 0x0093 }
        r3 = 0;
        goto L_0x006c;
    L_0x0084:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ IOException -> 0x0084, all -> 0x008a }
        r0 = 0;
        goto L_0x007d;
    L_0x008a:
        r7 = move-exception;
        r0 = 0;
        throw r7;
    L_0x008d:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ IOException -> 0x008d, all -> 0x0093 }
        r3 = 0;
        goto L_0x006c;
    L_0x0093:
        r7 = move-exception;
        r3 = 0;
        throw r7;
    L_0x0096:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ IOException -> 0x0096, all -> 0x009c }
        r0 = 0;
        goto L_0x0065;
    L_0x009c:
        r7 = move-exception;
        r0 = 0;
        throw r7;
    L_0x009f:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ IOException -> 0x009f, all -> 0x00a5 }
        r3 = 0;
        goto L_0x006b;
    L_0x00a5:
        r7 = move-exception;
        r3 = 0;
        throw r7;
    L_0x00a8:
        r7 = move-exception;
    L_0x00a9:
        if (r0 == 0) goto L_0x00af;
    L_0x00ab:
        r0.close();	 Catch:{ IOException -> 0x00b6, all -> 0x00bc }
        r0 = 0;
    L_0x00af:
        if (r3 == 0) goto L_0x00b5;
    L_0x00b1:
        r3.close();	 Catch:{ IOException -> 0x00bf, all -> 0x00c5 }
        r3 = 0;
    L_0x00b5:
        throw r7;
    L_0x00b6:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ IOException -> 0x00b6, all -> 0x00bc }
        r0 = 0;
        goto L_0x00af;
    L_0x00bc:
        r7 = move-exception;
        r0 = 0;
        throw r7;
    L_0x00bf:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ IOException -> 0x00bf, all -> 0x00c5 }
        r3 = 0;
        goto L_0x00b5;
    L_0x00c5:
        r7 = move-exception;
        r3 = 0;
        throw r7;
    L_0x00c8:
        r7 = move-exception;
        r3 = r4;
        goto L_0x00a9;
    L_0x00cb:
        r7 = move-exception;
        r0 = r1;
        r3 = r4;
        goto L_0x00a9;
    L_0x00cf:
        r2 = move-exception;
        goto L_0x005c;
    L_0x00d1:
        r2 = move-exception;
        r3 = r4;
        goto L_0x005c;
    L_0x00d4:
        r3 = r4;
        goto L_0x006c;
    L_0x00d6:
        r0 = r1;
        goto L_0x007d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.shortvideo.FileUtils.getConfigFromFile(android.content.Context, java.lang.String):java.lang.String");
    }

    public static InputStream getInputStream(File oFile) {
        try {
            return new FileInputStream(oFile);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x002c A:{SYNTHETIC, Splitter: B:17:0x002c} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0031 A:{SYNTHETIC, Splitter: B:20:0x0031} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0062 A:{SYNTHETIC, Splitter: B:44:0x0062} */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0067 A:{SYNTHETIC, Splitter: B:47:0x0067} */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0078 A:{SYNTHETIC, Splitter: B:55:0x0078} */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x007d A:{SYNTHETIC, Splitter: B:58:0x007d} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0062 A:{SYNTHETIC, Splitter: B:44:0x0062} */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0067 A:{SYNTHETIC, Splitter: B:47:0x0067} */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0078 A:{SYNTHETIC, Splitter: B:55:0x0078} */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x007d A:{SYNTHETIC, Splitter: B:58:0x007d} */
    public static byte[] getBytes(java.lang.String r10) {
        /*
        r3 = 0;
        r6 = 0;
        r1 = 0;
        r5 = new java.io.File;	 Catch:{ FileNotFoundException -> 0x0099, IOException -> 0x005c }
        r5.<init>(r10);	 Catch:{ FileNotFoundException -> 0x0099, IOException -> 0x005c }
        r7 = new java.io.FileInputStream;	 Catch:{ FileNotFoundException -> 0x0099, IOException -> 0x005c }
        r7.<init>(r5);	 Catch:{ FileNotFoundException -> 0x0099, IOException -> 0x005c }
        r2 = new java.io.ByteArrayOutputStream;	 Catch:{ FileNotFoundException -> 0x009b, IOException -> 0x0092, all -> 0x008b }
        r9 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r2.<init>(r9);	 Catch:{ FileNotFoundException -> 0x009b, IOException -> 0x0092, all -> 0x008b }
        r9 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = new byte[r9];	 Catch:{ FileNotFoundException -> 0x0024, IOException -> 0x0095, all -> 0x008e }
    L_0x0018:
        r8 = r7.read(r0);	 Catch:{ FileNotFoundException -> 0x0024, IOException -> 0x0095, all -> 0x008e }
        r9 = -1;
        if (r8 == r9) goto L_0x0035;
    L_0x001f:
        r9 = 0;
        r2.write(r0, r9, r8);	 Catch:{ FileNotFoundException -> 0x0024, IOException -> 0x0095, all -> 0x008e }
        goto L_0x0018;
    L_0x0024:
        r4 = move-exception;
        r1 = r2;
        r6 = r7;
    L_0x0027:
        r4.printStackTrace();	 Catch:{ all -> 0x0075 }
        if (r6 == 0) goto L_0x002f;
    L_0x002c:
        r6.close();	 Catch:{ IOException -> 0x0052 }
    L_0x002f:
        if (r1 == 0) goto L_0x0034;
    L_0x0031:
        r1.close();	 Catch:{ IOException -> 0x0057 }
    L_0x0034:
        return r3;
    L_0x0035:
        r3 = r2.toByteArray();	 Catch:{ FileNotFoundException -> 0x0024, IOException -> 0x0095, all -> 0x008e }
        if (r7 == 0) goto L_0x003e;
    L_0x003b:
        r7.close();	 Catch:{ IOException -> 0x0046 }
    L_0x003e:
        if (r2 == 0) goto L_0x009e;
    L_0x0040:
        r2.close();	 Catch:{ IOException -> 0x004b }
        r1 = r2;
        r6 = r7;
        goto L_0x0034;
    L_0x0046:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x003e;
    L_0x004b:
        r4 = move-exception;
        r4.printStackTrace();
        r1 = r2;
        r6 = r7;
        goto L_0x0034;
    L_0x0052:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x002f;
    L_0x0057:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x0034;
    L_0x005c:
        r4 = move-exception;
    L_0x005d:
        r4.printStackTrace();	 Catch:{ all -> 0x0075 }
        if (r6 == 0) goto L_0x0065;
    L_0x0062:
        r6.close();	 Catch:{ IOException -> 0x0070 }
    L_0x0065:
        if (r1 == 0) goto L_0x0034;
    L_0x0067:
        r1.close();	 Catch:{ IOException -> 0x006b }
        goto L_0x0034;
    L_0x006b:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x0034;
    L_0x0070:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x0065;
    L_0x0075:
        r9 = move-exception;
    L_0x0076:
        if (r6 == 0) goto L_0x007b;
    L_0x0078:
        r6.close();	 Catch:{ IOException -> 0x0081 }
    L_0x007b:
        if (r1 == 0) goto L_0x0080;
    L_0x007d:
        r1.close();	 Catch:{ IOException -> 0x0086 }
    L_0x0080:
        throw r9;
    L_0x0081:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x007b;
    L_0x0086:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x0080;
    L_0x008b:
        r9 = move-exception;
        r6 = r7;
        goto L_0x0076;
    L_0x008e:
        r9 = move-exception;
        r1 = r2;
        r6 = r7;
        goto L_0x0076;
    L_0x0092:
        r4 = move-exception;
        r6 = r7;
        goto L_0x005d;
    L_0x0095:
        r4 = move-exception;
        r1 = r2;
        r6 = r7;
        goto L_0x005d;
    L_0x0099:
        r4 = move-exception;
        goto L_0x0027;
    L_0x009b:
        r4 = move-exception;
        r6 = r7;
        goto L_0x0027;
    L_0x009e:
        r1 = r2;
        r6 = r7;
        goto L_0x0034;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.shortvideo.FileUtils.getBytes(java.lang.String):byte[]");
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0055 A:{SYNTHETIC, Splitter: B:27:0x0055} */
    /* JADX WARNING: Removed duplicated region for block: B:63:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005a A:{SYNTHETIC, Splitter: B:30:0x005a} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x006b A:{SYNTHETIC, Splitter: B:38:0x006b} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0070 A:{SYNTHETIC, Splitter: B:41:0x0070} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0055 A:{SYNTHETIC, Splitter: B:27:0x0055} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005a A:{SYNTHETIC, Splitter: B:30:0x005a} */
    /* JADX WARNING: Removed duplicated region for block: B:63:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x006b A:{SYNTHETIC, Splitter: B:38:0x006b} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0070 A:{SYNTHETIC, Splitter: B:41:0x0070} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0055 A:{SYNTHETIC, Splitter: B:27:0x0055} */
    /* JADX WARNING: Removed duplicated region for block: B:63:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005a A:{SYNTHETIC, Splitter: B:30:0x005a} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x006b A:{SYNTHETIC, Splitter: B:38:0x006b} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0070 A:{SYNTHETIC, Splitter: B:41:0x0070} */
    public static void getFile(byte[] r10, java.lang.String r11, java.lang.String r12) {
        /*
        r0 = 0;
        r7 = 0;
        r5 = 0;
        r2 = new java.io.File;	 Catch:{ Exception -> 0x004f }
        r2.<init>(r11);	 Catch:{ Exception -> 0x004f }
        r9 = r2.exists();	 Catch:{ Exception -> 0x004f }
        if (r9 != 0) goto L_0x0011;
    L_0x000e:
        r2.mkdirs();	 Catch:{ Exception -> 0x004f }
    L_0x0011:
        r6 = new java.io.File;	 Catch:{ Exception -> 0x004f }
        r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x004f }
        r9.<init>();	 Catch:{ Exception -> 0x004f }
        r9 = r9.append(r11);	 Catch:{ Exception -> 0x004f }
        r9 = r9.append(r12);	 Catch:{ Exception -> 0x004f }
        r9 = r9.toString();	 Catch:{ Exception -> 0x004f }
        r6.<init>(r9);	 Catch:{ Exception -> 0x004f }
        r8 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x008a, all -> 0x007e }
        r8.<init>(r6);	 Catch:{ Exception -> 0x008a, all -> 0x007e }
        r1 = new java.io.BufferedOutputStream;	 Catch:{ Exception -> 0x008d, all -> 0x0081 }
        r1.<init>(r8);	 Catch:{ Exception -> 0x008d, all -> 0x0081 }
        r1.write(r10);	 Catch:{ Exception -> 0x0091, all -> 0x0085 }
        if (r1 == 0) goto L_0x0039;
    L_0x0036:
        r1.close();	 Catch:{ IOException -> 0x0042 }
    L_0x0039:
        if (r8 == 0) goto L_0x0096;
    L_0x003b:
        r8.close();	 Catch:{ IOException -> 0x0047 }
        r5 = r6;
        r7 = r8;
        r0 = r1;
    L_0x0041:
        return;
    L_0x0042:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x0039;
    L_0x0047:
        r4 = move-exception;
        r4.printStackTrace();
        r5 = r6;
        r7 = r8;
        r0 = r1;
        goto L_0x0041;
    L_0x004f:
        r3 = move-exception;
    L_0x0050:
        r3.printStackTrace();	 Catch:{ all -> 0x0068 }
        if (r0 == 0) goto L_0x0058;
    L_0x0055:
        r0.close();	 Catch:{ IOException -> 0x0063 }
    L_0x0058:
        if (r7 == 0) goto L_0x0041;
    L_0x005a:
        r7.close();	 Catch:{ IOException -> 0x005e }
        goto L_0x0041;
    L_0x005e:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x0041;
    L_0x0063:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x0058;
    L_0x0068:
        r9 = move-exception;
    L_0x0069:
        if (r0 == 0) goto L_0x006e;
    L_0x006b:
        r0.close();	 Catch:{ IOException -> 0x0074 }
    L_0x006e:
        if (r7 == 0) goto L_0x0073;
    L_0x0070:
        r7.close();	 Catch:{ IOException -> 0x0079 }
    L_0x0073:
        throw r9;
    L_0x0074:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x006e;
    L_0x0079:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x0073;
    L_0x007e:
        r9 = move-exception;
        r5 = r6;
        goto L_0x0069;
    L_0x0081:
        r9 = move-exception;
        r5 = r6;
        r7 = r8;
        goto L_0x0069;
    L_0x0085:
        r9 = move-exception;
        r5 = r6;
        r7 = r8;
        r0 = r1;
        goto L_0x0069;
    L_0x008a:
        r3 = move-exception;
        r5 = r6;
        goto L_0x0050;
    L_0x008d:
        r3 = move-exception;
        r5 = r6;
        r7 = r8;
        goto L_0x0050;
    L_0x0091:
        r3 = move-exception;
        r5 = r6;
        r7 = r8;
        r0 = r1;
        goto L_0x0050;
    L_0x0096:
        r5 = r6;
        r7 = r8;
        r0 = r1;
        goto L_0x0041;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.shortvideo.FileUtils.getFile(byte[], java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0057 A:{SYNTHETIC, Splitter: B:24:0x0057} */
    public static java.lang.StringBuilder readFile(java.lang.String r9, java.lang.String r10) {
        /*
        r1 = new java.io.File;
        r1.<init>(r9);
        r2 = new java.lang.StringBuilder;
        r7 = "";
        r2.<init>(r7);
        if (r1 == 0) goto L_0x0015;
    L_0x000f:
        r7 = r1.isFile();
        if (r7 != 0) goto L_0x0017;
    L_0x0015:
        r2 = 0;
    L_0x0016:
        return r2;
    L_0x0017:
        r5 = 0;
        r3 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x007b }
        r7 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x007b }
        r7.<init>(r1);	 Catch:{ IOException -> 0x007b }
        r3.<init>(r7, r10);	 Catch:{ IOException -> 0x007b }
        r6 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x007b }
        r6.<init>(r3);	 Catch:{ IOException -> 0x007b }
        r4 = 0;
    L_0x0028:
        r4 = r6.readLine();	 Catch:{ IOException -> 0x0049, all -> 0x0078 }
        if (r4 == 0) goto L_0x005b;
    L_0x002e:
        r7 = r2.toString();	 Catch:{ IOException -> 0x0049, all -> 0x0078 }
        r8 = "";
        r7 = r7.equals(r8);	 Catch:{ IOException -> 0x0049, all -> 0x0078 }
        if (r7 != 0) goto L_0x0045;
    L_0x003b:
        r7 = "line.separator";
        r7 = java.lang.System.getProperty(r7);	 Catch:{ IOException -> 0x0049, all -> 0x0078 }
        r2.append(r7);	 Catch:{ IOException -> 0x0049, all -> 0x0078 }
    L_0x0045:
        r2.append(r4);	 Catch:{ IOException -> 0x0049, all -> 0x0078 }
        goto L_0x0028;
    L_0x0049:
        r0 = move-exception;
        r5 = r6;
    L_0x004b:
        r7 = new java.lang.RuntimeException;	 Catch:{ all -> 0x0054 }
        r8 = "IOException occurred. ";
        r7.<init>(r8, r0);	 Catch:{ all -> 0x0054 }
        throw r7;	 Catch:{ all -> 0x0054 }
    L_0x0054:
        r7 = move-exception;
    L_0x0055:
        if (r5 == 0) goto L_0x005a;
    L_0x0057:
        r5.close();	 Catch:{ IOException -> 0x006e }
    L_0x005a:
        throw r7;
    L_0x005b:
        r6.close();	 Catch:{ IOException -> 0x0049, all -> 0x0078 }
        if (r6 == 0) goto L_0x0016;
    L_0x0060:
        r6.close();	 Catch:{ IOException -> 0x0064 }
        goto L_0x0016;
    L_0x0064:
        r0 = move-exception;
        r7 = new java.lang.RuntimeException;
        r8 = "IOException occurred. ";
        r7.<init>(r8, r0);
        throw r7;
    L_0x006e:
        r0 = move-exception;
        r7 = new java.lang.RuntimeException;
        r8 = "IOException occurred. ";
        r7.<init>(r8, r0);
        throw r7;
    L_0x0078:
        r7 = move-exception;
        r5 = r6;
        goto L_0x0055;
    L_0x007b:
        r0 = move-exception;
        goto L_0x004b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.shortvideo.FileUtils.readFile(java.lang.String, java.lang.String):java.lang.StringBuilder");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0035 A:{SYNTHETIC, Splitter: B:21:0x0035} */
    public static boolean writeFile(java.lang.String r5, java.lang.String r6, boolean r7) {
        /*
        r3 = android.text.TextUtils.isEmpty(r6);
        if (r3 == 0) goto L_0x0008;
    L_0x0006:
        r3 = 0;
    L_0x0007:
        return r3;
    L_0x0008:
        r1 = 0;
        makeDirs(r5);	 Catch:{ IOException -> 0x0028 }
        r2 = new java.io.FileWriter;	 Catch:{ IOException -> 0x0028 }
        r2.<init>(r5, r7);	 Catch:{ IOException -> 0x0028 }
        r2.write(r6);	 Catch:{ IOException -> 0x0046, all -> 0x0043 }
        r2.close();	 Catch:{ IOException -> 0x0046, all -> 0x0043 }
        r3 = 1;
        if (r2 == 0) goto L_0x0007;
    L_0x001a:
        r2.close();	 Catch:{ IOException -> 0x001e }
        goto L_0x0007;
    L_0x001e:
        r0 = move-exception;
        r3 = new java.lang.RuntimeException;
        r4 = "IOException occurred. ";
        r3.<init>(r4, r0);
        throw r3;
    L_0x0028:
        r0 = move-exception;
    L_0x0029:
        r3 = new java.lang.RuntimeException;	 Catch:{ all -> 0x0032 }
        r4 = "IOException occurred. ";
        r3.<init>(r4, r0);	 Catch:{ all -> 0x0032 }
        throw r3;	 Catch:{ all -> 0x0032 }
    L_0x0032:
        r3 = move-exception;
    L_0x0033:
        if (r1 == 0) goto L_0x0038;
    L_0x0035:
        r1.close();	 Catch:{ IOException -> 0x0039 }
    L_0x0038:
        throw r3;
    L_0x0039:
        r0 = move-exception;
        r3 = new java.lang.RuntimeException;
        r4 = "IOException occurred. ";
        r3.<init>(r4, r0);
        throw r3;
    L_0x0043:
        r3 = move-exception;
        r1 = r2;
        goto L_0x0033;
    L_0x0046:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.shortvideo.FileUtils.writeFile(java.lang.String, java.lang.String, boolean):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x003f A:{SYNTHETIC, Splitter: B:20:0x003f} */
    public static boolean writeFile(java.lang.String r6, java.util.List<java.lang.String> r7, boolean r8) {
        /*
        if (r7 == 0) goto L_0x0008;
    L_0x0002:
        r4 = r7.isEmpty();
        if (r4 == 0) goto L_0x000a;
    L_0x0008:
        r4 = 0;
    L_0x0009:
        return r4;
    L_0x000a:
        r1 = 0;
        makeDirs(r6);	 Catch:{ IOException -> 0x0064 }
        r2 = new java.io.FileWriter;	 Catch:{ IOException -> 0x0064 }
        r2.<init>(r6, r8);	 Catch:{ IOException -> 0x0064 }
        r4 = r7.iterator();	 Catch:{ IOException -> 0x0031, all -> 0x0061 }
    L_0x0017:
        r5 = r4.hasNext();	 Catch:{ IOException -> 0x0031, all -> 0x0061 }
        if (r5 == 0) goto L_0x0043;
    L_0x001d:
        r3 = r4.next();	 Catch:{ IOException -> 0x0031, all -> 0x0061 }
        r3 = (java.lang.String) r3;	 Catch:{ IOException -> 0x0031, all -> 0x0061 }
        r2.write(r3);	 Catch:{ IOException -> 0x0031, all -> 0x0061 }
        r5 = "line.separator";
        r5 = java.lang.System.getProperty(r5);	 Catch:{ IOException -> 0x0031, all -> 0x0061 }
        r2.write(r5);	 Catch:{ IOException -> 0x0031, all -> 0x0061 }
        goto L_0x0017;
    L_0x0031:
        r0 = move-exception;
        r1 = r2;
    L_0x0033:
        r4 = new java.lang.RuntimeException;	 Catch:{ all -> 0x003c }
        r5 = "IOException occurred. ";
        r4.<init>(r5, r0);	 Catch:{ all -> 0x003c }
        throw r4;	 Catch:{ all -> 0x003c }
    L_0x003c:
        r4 = move-exception;
    L_0x003d:
        if (r1 == 0) goto L_0x0042;
    L_0x003f:
        r1.close();	 Catch:{ IOException -> 0x0057 }
    L_0x0042:
        throw r4;
    L_0x0043:
        r2.close();	 Catch:{ IOException -> 0x0031, all -> 0x0061 }
        r4 = 1;
        if (r2 == 0) goto L_0x0009;
    L_0x0049:
        r2.close();	 Catch:{ IOException -> 0x004d }
        goto L_0x0009;
    L_0x004d:
        r0 = move-exception;
        r4 = new java.lang.RuntimeException;
        r5 = "IOException occurred. ";
        r4.<init>(r5, r0);
        throw r4;
    L_0x0057:
        r0 = move-exception;
        r4 = new java.lang.RuntimeException;
        r5 = "IOException occurred. ";
        r4.<init>(r5, r0);
        throw r4;
    L_0x0061:
        r4 = move-exception;
        r1 = r2;
        goto L_0x003d;
    L_0x0064:
        r0 = move-exception;
        goto L_0x0033;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.shortvideo.FileUtils.writeFile(java.lang.String, java.util.List, boolean):boolean");
    }

    public static boolean writeFile(String filePath, String content) {
        return writeFile(filePath, content, false);
    }

    public static boolean writeFile(String filePath, List<String> contentList) {
        return writeFile(filePath, (List) contentList, false);
    }

    public static boolean writeFile(String filePath, InputStream stream) {
        return writeFile(filePath, stream, false);
    }

    public static boolean writeFile(String filePath, InputStream stream, boolean append) {
        return writeFile(filePath != null ? new File(filePath) : null, stream, append);
    }

    public static boolean writeFile(File file, InputStream stream) {
        return writeFile(file, stream, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0032 A:{SYNTHETIC, Splitter: B:21:0x0032} */
    public static boolean writeFile(java.io.File r7, java.io.InputStream r8, boolean r9) {
        /*
        r5 = 0;
        if (r7 == 0) goto L_0x0005;
    L_0x0003:
        if (r8 != 0) goto L_0x0006;
    L_0x0005:
        return r5;
    L_0x0006:
        r3 = 0;
        r5 = r7.getAbsolutePath();	 Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0050 }
        makeDirs(r5);	 Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0050 }
        r4 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0050 }
        r4.<init>(r7, r9);	 Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0050 }
        r5 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0 = new byte[r5];	 Catch:{ FileNotFoundException -> 0x0024, IOException -> 0x0067, all -> 0x0064 }
        r2 = -1;
    L_0x0018:
        r2 = r8.read(r0);	 Catch:{ FileNotFoundException -> 0x0024, IOException -> 0x0067, all -> 0x0064 }
        r5 = -1;
        if (r2 == r5) goto L_0x0039;
    L_0x001f:
        r5 = 0;
        r4.write(r0, r5, r2);	 Catch:{ FileNotFoundException -> 0x0024, IOException -> 0x0067, all -> 0x0064 }
        goto L_0x0018;
    L_0x0024:
        r1 = move-exception;
        r3 = r4;
    L_0x0026:
        r5 = new java.lang.RuntimeException;	 Catch:{ all -> 0x002f }
        r6 = "FileNotFoundException occurred. ";
        r5.<init>(r6, r1);	 Catch:{ all -> 0x002f }
        throw r5;	 Catch:{ all -> 0x002f }
    L_0x002f:
        r5 = move-exception;
    L_0x0030:
        if (r3 == 0) goto L_0x0038;
    L_0x0032:
        r3.close();	 Catch:{ IOException -> 0x005a }
        r8.close();	 Catch:{ IOException -> 0x005a }
    L_0x0038:
        throw r5;
    L_0x0039:
        r4.flush();	 Catch:{ FileNotFoundException -> 0x0024, IOException -> 0x0067, all -> 0x0064 }
        r5 = 1;
        if (r4 == 0) goto L_0x0005;
    L_0x003f:
        r4.close();	 Catch:{ IOException -> 0x0046 }
        r8.close();	 Catch:{ IOException -> 0x0046 }
        goto L_0x0005;
    L_0x0046:
        r1 = move-exception;
        r5 = new java.lang.RuntimeException;
        r6 = "IOException occurred. ";
        r5.<init>(r6, r1);
        throw r5;
    L_0x0050:
        r1 = move-exception;
    L_0x0051:
        r5 = new java.lang.RuntimeException;	 Catch:{ all -> 0x002f }
        r6 = "IOException occurred. ";
        r5.<init>(r6, r1);	 Catch:{ all -> 0x002f }
        throw r5;	 Catch:{ all -> 0x002f }
    L_0x005a:
        r1 = move-exception;
        r5 = new java.lang.RuntimeException;
        r6 = "IOException occurred. ";
        r5.<init>(r6, r1);
        throw r5;
    L_0x0064:
        r5 = move-exception;
        r3 = r4;
        goto L_0x0030;
    L_0x0067:
        r1 = move-exception;
        r3 = r4;
        goto L_0x0051;
    L_0x006a:
        r1 = move-exception;
        goto L_0x0026;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.shortvideo.FileUtils.writeFile(java.io.File, java.io.InputStream, boolean):boolean");
    }

    public static boolean copyFile(String sourceFilePath, String destFilePath) {
        try {
            return writeFile(destFilePath, new FileInputStream(sourceFilePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x003d A:{SYNTHETIC, Splitter: B:21:0x003d} */
    public static java.util.List<java.lang.String> readFileToList(java.lang.String r9, java.lang.String r10) {
        /*
        r1 = new java.io.File;
        r1.<init>(r9);
        r2 = new java.util.ArrayList;
        r2.<init>();
        if (r1 == 0) goto L_0x0012;
    L_0x000c:
        r7 = r1.isFile();
        if (r7 != 0) goto L_0x0014;
    L_0x0012:
        r2 = 0;
    L_0x0013:
        return r2;
    L_0x0014:
        r5 = 0;
        r3 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x0061 }
        r7 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0061 }
        r7.<init>(r1);	 Catch:{ IOException -> 0x0061 }
        r3.<init>(r7, r10);	 Catch:{ IOException -> 0x0061 }
        r6 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x0061 }
        r6.<init>(r3);	 Catch:{ IOException -> 0x0061 }
        r4 = 0;
    L_0x0025:
        r4 = r6.readLine();	 Catch:{ IOException -> 0x002f, all -> 0x005e }
        if (r4 == 0) goto L_0x0041;
    L_0x002b:
        r2.add(r4);	 Catch:{ IOException -> 0x002f, all -> 0x005e }
        goto L_0x0025;
    L_0x002f:
        r0 = move-exception;
        r5 = r6;
    L_0x0031:
        r7 = new java.lang.RuntimeException;	 Catch:{ all -> 0x003a }
        r8 = "IOException occurred. ";
        r7.<init>(r8, r0);	 Catch:{ all -> 0x003a }
        throw r7;	 Catch:{ all -> 0x003a }
    L_0x003a:
        r7 = move-exception;
    L_0x003b:
        if (r5 == 0) goto L_0x0040;
    L_0x003d:
        r5.close();	 Catch:{ IOException -> 0x0054 }
    L_0x0040:
        throw r7;
    L_0x0041:
        r6.close();	 Catch:{ IOException -> 0x002f, all -> 0x005e }
        if (r6 == 0) goto L_0x0013;
    L_0x0046:
        r6.close();	 Catch:{ IOException -> 0x004a }
        goto L_0x0013;
    L_0x004a:
        r0 = move-exception;
        r7 = new java.lang.RuntimeException;
        r8 = "IOException occurred. ";
        r7.<init>(r8, r0);
        throw r7;
    L_0x0054:
        r0 = move-exception;
        r7 = new java.lang.RuntimeException;
        r8 = "IOException occurred. ";
        r7.<init>(r8, r0);
        throw r7;
    L_0x005e:
        r7 = move-exception;
        r5 = r6;
        goto L_0x003b;
    L_0x0061:
        r0 = move-exception;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.shortvideo.FileUtils.readFileToList(java.lang.String, java.lang.String):java.util.List<java.lang.String>");
    }

    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            if (extenPosi != -1) {
                return filePath.substring(0, extenPosi);
            }
            return filePath;
        } else if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        } else {
            return filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1);
        }
    }

    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return filePosi != -1 ? filePath.substring(filePosi + 1) : filePath;
    }

    public static String getFolderName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return filePosi == -1 ? "" : filePath.substring(0, filePosi);
    }

    public static String getFileExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return filePosi >= extenPosi ? "" : filePath.substring(extenPosi + 1);
    }

    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }
        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    public static boolean makeFolders(String filePath) {
        return makeDirs(filePath);
    }

    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return true;
        }
        return false;
    }

    public static boolean isFolder(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        return new File(filePath).isDirectory();
    }

    public static boolean isFileExist(String filePath, long size) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (file.exists() && file.isFile() && file.length() == size) {
            return true;
        }
        return false;
    }

    public static boolean isFileExist(String filePath, long size, String checkSum) {
        if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(checkSum)) {
            return false;
        }
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        String fileSum = "";
        try {
            fileSum = getMD5FromFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.length() == size && checkSum.equals(fileSum)) {
            return true;
        }
        return false;
    }

    public static boolean isFolderExist(String directoryPath) {
        if (TextUtils.isEmpty(directoryPath)) {
            return false;
        }
        File dire = new File(directoryPath);
        if (dire.exists() && dire.isDirectory()) {
            return true;
        }
        return false;
    }

    public static boolean deleteFile(String path) {
        int i = 0;
        synchronized (gSyncCode) {
            if (TextUtils.isEmpty(path)) {
                return true;
            }
            File file = new File(path);
            boolean delete;
            if (!file.exists()) {
                return true;
            } else if (file.isFile()) {
                delete = file.delete();
                return delete;
            } else if (file.isDirectory()) {
                File[] filesList = file.listFiles();
                if (filesList != null) {
                    int length = filesList.length;
                    while (i < length) {
                        File f = filesList[i];
                        if (f.isFile()) {
                            f.delete();
                        } else if (f.isDirectory()) {
                            deleteFile(f.getAbsolutePath());
                        }
                        i++;
                    }
                }
                delete = file.delete();
                return delete;
            } else {
                return false;
            }
        }
    }

    public static boolean fileRename(String fromName, String toName) {
        boolean result;
        synchronized (gSyncCode) {
            File fromFile = new File(fromName);
            File toFile = new File(toName);
            if (fromFile.exists()) {
                result = fromFile.renameTo(toFile);
                if (result) {
                }
            } else {
                result = false;
            }
        }
        return result;
    }

    public static long getFileSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return -1;
        }
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        return -1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x018f A:{SYNTHETIC, Splitter: B:80:0x018f} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:100:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0170 A:{SYNTHETIC, Splitter: B:68:0x0170} */
    /* JADX WARNING: Removed duplicated region for block: B:100:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00cb  */
    public static java.lang.String getMD5FromFile(java.lang.String r29) throws java.io.IOException {
        /*
        if (r29 != 0) goto L_0x0004;
    L_0x0002:
        r9 = 0;
    L_0x0003:
        return r9;
    L_0x0004:
        r9 = 0;
        r13 = new java.io.File;
        r0 = r29;
        r13.<init>(r0);
        r24 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0 = r24;
        r4 = new byte[r0];
        r16 = 0;
        r8 = 0;
        r24 = "MD5";
        r8 = java.security.MessageDigest.getInstance(r24);	 Catch:{ NoSuchAlgorithmException -> 0x00d5 }
    L_0x001c:
        r14 = r13.length();
        r24 = 3145728; // 0x300000 float:4.408104E-39 double:1.554196E-317;
        r24 = (r14 > r24 ? 1 : (r14 == r24 ? 0 : -1));
        if (r24 <= 0) goto L_0x0133;
    L_0x0027:
        r19 = 0;
        r24 = "FileUtils";
        r25 = "fileSize is greater than 3MB";
        android.util.Log.d(r24, r25);
        r20 = new java.io.RandomAccessFile;	 Catch:{ Exception -> 0x01a1 }
        r24 = "r";
        r0 = r20;
        r1 = r24;
        r0.<init>(r13, r1);	 Catch:{ Exception -> 0x01a1 }
        r24 = 0;
        r0 = r20;
        r1 = r24;
        r0.seek(r1);	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r5 = 0;
        r23 = 0;
        r6 = 3145728; // 0x300000 float:4.408104E-39 double:1.554196E-317;
        r24 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r26 = 3145728; // 0x300000 float:4.408104E-39 double:1.554196E-317;
        r26 = r14 - r26;
        r0 = r26;
        r0 = (int) r0;	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r25 = r0;
        r25 = r25 / 2;
        r18 = r24 + r25;
        r24 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r24 = r14 - r24;
        r0 = r24;
        r0 = (int) r0;	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r22 = r0;
        r24 = "FileUtils";
        r25 = "midStartPosition = %d, tailStartPosition = %d";
        r26 = 2;
        r0 = r26;
        r0 = new java.lang.Object[r0];	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r26 = r0;
        r27 = 0;
        r28 = java.lang.Integer.valueOf(r18);	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r26[r27] = r28;	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r27 = 1;
        r28 = java.lang.Integer.valueOf(r22);	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r26[r27] = r28;	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r25 = java.lang.String.format(r25, r26);	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        android.util.Log.d(r24, r25);	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r24 = 131072; // 0x20000 float:1.83671E-40 double:6.47582E-319;
        r0 = r24;
        r7 = new byte[r0];	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
    L_0x0091:
        r0 = r23;
        if (r0 >= r6) goto L_0x011a;
    L_0x0095:
        r0 = r20;
        r5 = r0.read(r7);	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r23 = r23 + r5;
        r24 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r0 = r23;
        r1 = r24;
        if (r0 != r1) goto L_0x00f3;
    L_0x00a5:
        r24 = "FileUtils";
        r25 = "totalRead == MD5_FILE_BUFFER_LENGHT";
        android.util.Log.d(r24, r25);	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r0 = r18;
        r0 = (long) r0;	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r24 = r0;
        r0 = r20;
        r1 = r24;
        r0.seek(r1);	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
    L_0x00ba:
        r8.update(r7);	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        goto L_0x0091;
    L_0x00be:
        r10 = move-exception;
        r19 = r20;
    L_0x00c1:
        r10.printStackTrace();	 Catch:{ all -> 0x019e }
        if (r19 == 0) goto L_0x00c9;
    L_0x00c6:
        r19.close();	 Catch:{ Exception -> 0x0129 }
    L_0x00c9:
        if (r8 == 0) goto L_0x0003;
    L_0x00cb:
        r24 = r8.digest();
        r9 = byte2hexWithoutSpace(r24);
        goto L_0x0003;
    L_0x00d5:
        r11 = move-exception;
        r11.printStackTrace();
        r24 = "FileUtils";
        r25 = new java.lang.StringBuilder;
        r26 = "NoSuchAlgorithmException: ";
        r25.<init>(r26);
        r0 = r25;
        r25 = r0.append(r11);
        r25 = r25.toString();
        android.util.Log.e(r24, r25);
        goto L_0x001c;
    L_0x00f3:
        r24 = 2097152; // 0x200000 float:2.938736E-39 double:1.0361308E-317;
        r0 = r23;
        r1 = r24;
        if (r0 != r1) goto L_0x00ba;
    L_0x00fb:
        r24 = "FileUtils";
        r25 = "totalRead == 2 * MD5_FILE_BUFFER_LENGHT";
        android.util.Log.d(r24, r25);	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r0 = r22;
        r0 = (long) r0;	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        r24 = r0;
        r0 = r20;
        r1 = r24;
        r0.seek(r1);	 Catch:{ Exception -> 0x00be, all -> 0x0111 }
        goto L_0x00ba;
    L_0x0111:
        r24 = move-exception;
        r19 = r20;
    L_0x0114:
        if (r19 == 0) goto L_0x0119;
    L_0x0116:
        r19.close();	 Catch:{ Exception -> 0x012e }
    L_0x0119:
        throw r24;
    L_0x011a:
        if (r20 == 0) goto L_0x01a8;
    L_0x011c:
        r20.close();	 Catch:{ Exception -> 0x0122 }
        r19 = r20;
        goto L_0x00c9;
    L_0x0122:
        r12 = move-exception;
        r12.printStackTrace();
        r19 = r20;
        goto L_0x00c9;
    L_0x0129:
        r12 = move-exception;
        r12.printStackTrace();
        goto L_0x00c9;
    L_0x012e:
        r12 = move-exception;
        r12.printStackTrace();
        goto L_0x0119;
    L_0x0133:
        r24 = "FileUtils";
        r25 = "fileSize is smaller than 3MB";
        android.util.Log.d(r24, r25);
        r17 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x019c }
        r0 = r17;
        r0.<init>(r13);	 Catch:{ Exception -> 0x019c }
        r21 = 0;
    L_0x0145:
        r24 = -1;
        r0 = r21;
        r1 = r24;
        if (r0 == r1) goto L_0x017b;
    L_0x014d:
        r24 = 0;
        r0 = r4.length;	 Catch:{ Exception -> 0x0168, all -> 0x0198 }
        r25 = r0;
        r0 = r17;
        r1 = r24;
        r2 = r25;
        r21 = r0.read(r4, r1, r2);	 Catch:{ Exception -> 0x0168, all -> 0x0198 }
        if (r21 <= 0) goto L_0x0145;
    L_0x015e:
        r24 = 0;
        r0 = r24;
        r1 = r21;
        r8.update(r4, r0, r1);	 Catch:{ Exception -> 0x0168, all -> 0x0198 }
        goto L_0x0145;
    L_0x0168:
        r10 = move-exception;
        r16 = r17;
    L_0x016b:
        r10.printStackTrace();	 Catch:{ all -> 0x018c }
        if (r16 == 0) goto L_0x00c9;
    L_0x0170:
        r16.close();	 Catch:{ Exception -> 0x0175 }
        goto L_0x00c9;
    L_0x0175:
        r12 = move-exception;
        r12.printStackTrace();
        goto L_0x00c9;
    L_0x017b:
        if (r17 == 0) goto L_0x01a4;
    L_0x017d:
        r17.close();	 Catch:{ Exception -> 0x0184 }
        r16 = r17;
        goto L_0x00c9;
    L_0x0184:
        r12 = move-exception;
        r12.printStackTrace();
        r16 = r17;
        goto L_0x00c9;
    L_0x018c:
        r24 = move-exception;
    L_0x018d:
        if (r16 == 0) goto L_0x0192;
    L_0x018f:
        r16.close();	 Catch:{ Exception -> 0x0193 }
    L_0x0192:
        throw r24;
    L_0x0193:
        r12 = move-exception;
        r12.printStackTrace();
        goto L_0x0192;
    L_0x0198:
        r24 = move-exception;
        r16 = r17;
        goto L_0x018d;
    L_0x019c:
        r10 = move-exception;
        goto L_0x016b;
    L_0x019e:
        r24 = move-exception;
        goto L_0x0114;
    L_0x01a1:
        r10 = move-exception;
        goto L_0x00c1;
    L_0x01a4:
        r16 = r17;
        goto L_0x00c9;
    L_0x01a8:
        r19 = r20;
        goto L_0x00c9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.shortvideo.FileUtils.getMD5FromFile(java.lang.String):java.lang.String");
    }

    public static String byte2hexWithoutSpace(byte[] buffer) {
        String h = "";
        for (byte b : buffer) {
            String temp = Integer.toHexString(b & 255);
            if (temp.length() == 1) {
                temp = PushConstants.PUSH_TYPE_NOTIFY + temp;
            }
            h = h + temp;
        }
        return h;
    }

    public static String convertSize(long lenght) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (lenght < 1024) {
            return df.format((double) lenght) + " B";
        }
        if (lenght < 1048576) {
            return df.format(((double) lenght) / 1024.0d) + " KB";
        }
        if (lenght < IjkMediaMeta.AV_CH_STEREO_RIGHT) {
            return df.format(((double) lenght) / 1048576.0d) + " MB";
        }
        return df.format(((double) lenght) / 1.073741824E9d) + " GB";
    }

    public static boolean copyFromAssetToSdcard(Context context, String assetFilename, String dstPath) throws IOException {
        Throwable th;
        InputStream source = null;
        OutputStream destination = null;
        try {
            source = context.getAssets().open(new File(assetFilename).getPath());
            File destinationFile = new File(dstPath, assetFilename);
            destinationFile.getParentFile().mkdirs();
            OutputStream destination2 = new FileOutputStream(destinationFile);
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    int nread = source.read(buffer);
                    if (nread == -1) {
                        break;
                    } else if (nread == 0) {
                        nread = source.read();
                        if (nread < 0) {
                            break;
                        }
                        destination2.write(nread);
                    } else {
                        destination2.write(buffer, 0, nread);
                    }
                }
                if (source != null) {
                    try {
                        source.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (destination2 != null) {
                    try {
                        destination2.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                return true;
            } catch (Throwable th2) {
                th = th2;
                destination = destination2;
            }
        } catch (Throwable th3) {
            th = th3;
            if (source != null) {
                try {
                    source.close();
                } catch (Exception e22) {
                    e22.printStackTrace();
                }
            }
            if (destination != null) {
                try {
                    destination.close();
                } catch (Exception e222) {
                    e222.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static void copyFilesFromAssets(Context context, String assetsPath, String savePath) {
        try {
            File file = new File(savePath);
            String[] fileNames = context.getAssets().list(assetsPath);
            if (fileNames.length > 0) {
                file.mkdirs();
                for (String fileName : fileNames) {
                    copyFilesFromAssets(context, assetsPath + "/" + fileName, savePath + "/" + fileName);
                }
                return;
            }
            InputStream is = context.getAssets().open(assetsPath);
            FileOutputStream fos = new FileOutputStream(new File(savePath));
            byte[] buffer = new byte[1024];
            while (true) {
                int byteCount = is.read(buffer);
                if (byteCount != -1) {
                    fos.write(buffer, 0, byteCount);
                } else {
                    fos.flush();
                    is.close();
                    fos.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteLocalFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0041 A:{SYNTHETIC, Splitter: B:19:0x0041} */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004d A:{SYNTHETIC, Splitter: B:25:0x004d} */
    public static java.lang.String getJsonFromFile(java.lang.String r7) {
        /*
        r2 = "";
        r0 = 0;
        r4 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x003b }
        r4.<init>(r7);	 Catch:{ IOException -> 0x003b }
        r1 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x003b }
        r6 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x003b }
        r6.<init>(r4);	 Catch:{ IOException -> 0x003b }
        r1.<init>(r6);	 Catch:{ IOException -> 0x003b }
    L_0x0013:
        r5 = r1.readLine();	 Catch:{ IOException -> 0x0059, all -> 0x0056 }
        if (r5 == 0) goto L_0x002b;
    L_0x0019:
        r6 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0059, all -> 0x0056 }
        r6.<init>();	 Catch:{ IOException -> 0x0059, all -> 0x0056 }
        r6 = r6.append(r2);	 Catch:{ IOException -> 0x0059, all -> 0x0056 }
        r6 = r6.append(r5);	 Catch:{ IOException -> 0x0059, all -> 0x0056 }
        r2 = r6.toString();	 Catch:{ IOException -> 0x0059, all -> 0x0056 }
        goto L_0x0013;
    L_0x002b:
        r1.close();	 Catch:{ IOException -> 0x0059, all -> 0x0056 }
        if (r1 == 0) goto L_0x005c;
    L_0x0030:
        r1.close();	 Catch:{ IOException -> 0x0035 }
        r0 = r1;
    L_0x0034:
        return r2;
    L_0x0035:
        r3 = move-exception;
        r3.printStackTrace();
        r0 = r1;
        goto L_0x0034;
    L_0x003b:
        r3 = move-exception;
    L_0x003c:
        r3.printStackTrace();	 Catch:{ all -> 0x004a }
        if (r0 == 0) goto L_0x0034;
    L_0x0041:
        r0.close();	 Catch:{ IOException -> 0x0045 }
        goto L_0x0034;
    L_0x0045:
        r3 = move-exception;
        r3.printStackTrace();
        goto L_0x0034;
    L_0x004a:
        r6 = move-exception;
    L_0x004b:
        if (r0 == 0) goto L_0x0050;
    L_0x004d:
        r0.close();	 Catch:{ IOException -> 0x0051 }
    L_0x0050:
        throw r6;
    L_0x0051:
        r3 = move-exception;
        r3.printStackTrace();
        goto L_0x0050;
    L_0x0056:
        r6 = move-exception;
        r0 = r1;
        goto L_0x004b;
    L_0x0059:
        r3 = move-exception;
        r0 = r1;
        goto L_0x003c;
    L_0x005c:
        r0 = r1;
        goto L_0x0034;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.shortvideo.FileUtils.getJsonFromFile(java.lang.String):java.lang.String");
    }
}
