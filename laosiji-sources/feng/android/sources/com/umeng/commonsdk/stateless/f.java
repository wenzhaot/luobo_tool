package com.umeng.commonsdk.stateless;

import android.content.Context;
import android.text.TextUtils;
import com.stub.StubApp;
import com.umeng.commonsdk.proguard.ar;
import com.umeng.commonsdk.proguard.b;
import com.umeng.commonsdk.statistics.common.e;
import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: UMSLUtils */
public class f {
    public static int a;
    private static final byte[] b = new byte[]{(byte) 10, (byte) 1, (byte) 11, (byte) 5, (byte) 4, ar.m, (byte) 7, (byte) 9, (byte) 23, (byte) 3, (byte) 1, (byte) 6, (byte) 8, (byte) 12, ar.k, (byte) 91};
    private static Object c = new Object();

    /* JADX WARNING: Removed duplicated region for block: B:50:0x0194 A:{SYNTHETIC, Splitter: B:50:0x0194} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0168 A:{SYNTHETIC, Splitter: B:44:0x0168} */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0194 A:{SYNTHETIC, Splitter: B:50:0x0194} */
    /* JADX WARNING: Missing block: B:21:0x00ba, code:
            if (null == null) goto L_0x00bf;
     */
    /* JADX WARNING: Missing block: B:23:?, code:
            r1.close();
     */
    /* JADX WARNING: Missing block: B:31:0x00e6, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:32:0x00e7, code:
            r10 = r1;
            r1 = r4;
            r4 = r3;
            r3 = r10;
     */
    /* JADX WARNING: Missing block: B:58:0x01c4, code:
            r1 = th;
     */
    /* JADX WARNING: Missing block: B:59:0x01c5, code:
            r4 = r3;
     */
    /* JADX WARNING: Missing block: B:61:0x01c9, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:62:0x01ca, code:
            r10 = r1;
            r1 = r4;
            r4 = r3;
            r3 = r10;
     */
    public static boolean a(android.content.Context r11, java.lang.String r12, java.lang.String r13, byte[] r14) {
        /*
        r3 = 0;
        r0 = 1;
        r2 = 0;
        if (r11 == 0) goto L_0x01e2;
    L_0x0005:
        r5 = c;	 Catch:{ IOException -> 0x01d0, Throwable -> 0x013b, all -> 0x0190 }
        monitor-enter(r5);	 Catch:{ IOException -> 0x01d0, Throwable -> 0x013b, all -> 0x0190 }
        r1 = "walle";
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x00e2 }
        r6 = 0;
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00e2 }
        r7.<init>();	 Catch:{ all -> 0x00e2 }
        r8 = "[stateless] begin write envelope, thread is ";
        r7 = r7.append(r8);	 Catch:{ all -> 0x00e2 }
        r8 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x00e2 }
        r7 = r7.append(r8);	 Catch:{ all -> 0x00e2 }
        r7 = r7.toString();	 Catch:{ all -> 0x00e2 }
        r4[r6] = r7;	 Catch:{ all -> 0x00e2 }
        com.umeng.commonsdk.statistics.common.e.a(r1, r4);	 Catch:{ all -> 0x00e2 }
        r1 = new java.io.File;	 Catch:{ all -> 0x00e2 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00e2 }
        r4.<init>();	 Catch:{ all -> 0x00e2 }
        r6 = r11.getFilesDir();	 Catch:{ all -> 0x00e2 }
        r4 = r4.append(r6);	 Catch:{ all -> 0x00e2 }
        r6 = "/";
        r4 = r4.append(r6);	 Catch:{ all -> 0x00e2 }
        r6 = "stateless";
        r4 = r4.append(r6);	 Catch:{ all -> 0x00e2 }
        r4 = r4.toString();	 Catch:{ all -> 0x00e2 }
        r1.<init>(r4);	 Catch:{ all -> 0x00e2 }
        r4 = r1.isDirectory();	 Catch:{ all -> 0x00e2 }
        if (r4 != 0) goto L_0x0059;
    L_0x0056:
        r1.mkdir();	 Catch:{ all -> 0x00e2 }
    L_0x0059:
        r4 = new java.io.File;	 Catch:{ all -> 0x00e2 }
        r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00e2 }
        r6.<init>();	 Catch:{ all -> 0x00e2 }
        r1 = r1.getPath();	 Catch:{ all -> 0x00e2 }
        r1 = r6.append(r1);	 Catch:{ all -> 0x00e2 }
        r6 = "/";
        r1 = r1.append(r6);	 Catch:{ all -> 0x00e2 }
        r1 = r1.append(r12);	 Catch:{ all -> 0x00e2 }
        r1 = r1.toString();	 Catch:{ all -> 0x00e2 }
        r4.<init>(r1);	 Catch:{ all -> 0x00e2 }
        r1 = r4.isDirectory();	 Catch:{ all -> 0x00e2 }
        if (r1 != 0) goto L_0x0083;
    L_0x0080:
        r4.mkdir();	 Catch:{ all -> 0x00e2 }
    L_0x0083:
        r1 = new java.io.File;	 Catch:{ all -> 0x00e2 }
        r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00e2 }
        r6.<init>();	 Catch:{ all -> 0x00e2 }
        r4 = r4.getPath();	 Catch:{ all -> 0x00e2 }
        r4 = r6.append(r4);	 Catch:{ all -> 0x00e2 }
        r6 = "/";
        r4 = r4.append(r6);	 Catch:{ all -> 0x00e2 }
        r4 = r4.append(r13);	 Catch:{ all -> 0x00e2 }
        r4 = r4.toString();	 Catch:{ all -> 0x00e2 }
        r1.<init>(r4);	 Catch:{ all -> 0x00e2 }
        r4 = r1.exists();	 Catch:{ all -> 0x00e2 }
        if (r4 != 0) goto L_0x00ad;
    L_0x00aa:
        r1.createNewFile();	 Catch:{ all -> 0x00e2 }
    L_0x00ad:
        r4 = new java.io.FileOutputStream;	 Catch:{ all -> 0x00e2 }
        r4.<init>(r1);	 Catch:{ all -> 0x00e2 }
        r4.write(r14);	 Catch:{ all -> 0x01d6 }
        r4.close();	 Catch:{ all -> 0x01d6 }
        r1 = 0;
        monitor-exit(r5);	 Catch:{ all -> 0x01db }
        if (r3 == 0) goto L_0x00bf;
    L_0x00bc:
        r1.close();	 Catch:{ IOException -> 0x01ba }
    L_0x00bf:
        r1 = "walle";
        r3 = new java.lang.Object[r0];
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "[stateless] end write envelope, thread id ";
        r4 = r4.append(r5);
        r5 = java.lang.Thread.currentThread();
        r4 = r4.append(r5);
        r4 = r4.toString();
        r3[r2] = r4;
        com.umeng.commonsdk.statistics.common.e.a(r1, r3);
    L_0x00e1:
        return r0;
    L_0x00e2:
        r1 = move-exception;
        r4 = r2;
    L_0x00e4:
        monitor-exit(r5);	 Catch:{ all -> 0x01df }
        throw r1;	 Catch:{ IOException -> 0x00e6, Throwable -> 0x01c9, all -> 0x01c4 }
    L_0x00e6:
        r1 = move-exception;
        r10 = r1;
        r1 = r4;
        r4 = r3;
        r3 = r10;
    L_0x00eb:
        r5 = "walle";
        r6 = 1;
        r6 = new java.lang.Object[r6];	 Catch:{ all -> 0x01c7 }
        r7 = 0;
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01c7 }
        r8.<init>();	 Catch:{ all -> 0x01c7 }
        r9 = "[stateless] write envelope, e is ";
        r8 = r8.append(r9);	 Catch:{ all -> 0x01c7 }
        r9 = r3.getMessage();	 Catch:{ all -> 0x01c7 }
        r8 = r8.append(r9);	 Catch:{ all -> 0x01c7 }
        r8 = r8.toString();	 Catch:{ all -> 0x01c7 }
        r6[r7] = r8;	 Catch:{ all -> 0x01c7 }
        com.umeng.commonsdk.statistics.common.e.a(r5, r6);	 Catch:{ all -> 0x01c7 }
        com.umeng.commonsdk.proguard.b.a(r11, r3);	 Catch:{ all -> 0x01c7 }
        if (r4 == 0) goto L_0x0117;
    L_0x0114:
        r4.close();	 Catch:{ IOException -> 0x01bd }
    L_0x0117:
        r3 = "walle";
        r0 = new java.lang.Object[r0];
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "[stateless] end write envelope, thread id ";
        r4 = r4.append(r5);
        r5 = java.lang.Thread.currentThread();
        r4 = r4.append(r5);
        r4 = r4.toString();
        r0[r2] = r4;
        com.umeng.commonsdk.statistics.common.e.a(r3, r0);
        r0 = r1;
        goto L_0x00e1;
    L_0x013b:
        r1 = move-exception;
        r4 = r3;
        r3 = r1;
        r1 = r2;
    L_0x013f:
        r5 = "walle";
        r6 = 1;
        r6 = new java.lang.Object[r6];	 Catch:{ all -> 0x01c7 }
        r7 = 0;
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01c7 }
        r8.<init>();	 Catch:{ all -> 0x01c7 }
        r9 = "[stateless] write envelope, e is ";
        r8 = r8.append(r9);	 Catch:{ all -> 0x01c7 }
        r9 = r3.getMessage();	 Catch:{ all -> 0x01c7 }
        r8 = r8.append(r9);	 Catch:{ all -> 0x01c7 }
        r8 = r8.toString();	 Catch:{ all -> 0x01c7 }
        r6[r7] = r8;	 Catch:{ all -> 0x01c7 }
        com.umeng.commonsdk.statistics.common.e.a(r5, r6);	 Catch:{ all -> 0x01c7 }
        com.umeng.commonsdk.proguard.b.a(r11, r3);	 Catch:{ all -> 0x01c7 }
        if (r4 == 0) goto L_0x016b;
    L_0x0168:
        r4.close();	 Catch:{ IOException -> 0x01c0 }
    L_0x016b:
        r3 = "walle";
        r0 = new java.lang.Object[r0];
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "[stateless] end write envelope, thread id ";
        r4 = r4.append(r5);
        r5 = java.lang.Thread.currentThread();
        r4 = r4.append(r5);
        r4 = r4.toString();
        r0[r2] = r4;
        com.umeng.commonsdk.statistics.common.e.a(r3, r0);
        r0 = r1;
        goto L_0x00e1;
    L_0x0190:
        r1 = move-exception;
        r4 = r3;
    L_0x0192:
        if (r4 == 0) goto L_0x0197;
    L_0x0194:
        r4.close();	 Catch:{ IOException -> 0x01c2 }
    L_0x0197:
        r3 = "walle";
        r0 = new java.lang.Object[r0];
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "[stateless] end write envelope, thread id ";
        r4 = r4.append(r5);
        r5 = java.lang.Thread.currentThread();
        r4 = r4.append(r5);
        r4 = r4.toString();
        r0[r2] = r4;
        com.umeng.commonsdk.statistics.common.e.a(r3, r0);
        throw r1;
    L_0x01ba:
        r1 = move-exception;
        goto L_0x00bf;
    L_0x01bd:
        r3 = move-exception;
        goto L_0x0117;
    L_0x01c0:
        r3 = move-exception;
        goto L_0x016b;
    L_0x01c2:
        r3 = move-exception;
        goto L_0x0197;
    L_0x01c4:
        r1 = move-exception;
        r4 = r3;
        goto L_0x0192;
    L_0x01c7:
        r1 = move-exception;
        goto L_0x0192;
    L_0x01c9:
        r1 = move-exception;
        r10 = r1;
        r1 = r4;
        r4 = r3;
        r3 = r10;
        goto L_0x013f;
    L_0x01d0:
        r1 = move-exception;
        r4 = r3;
        r3 = r1;
        r1 = r2;
        goto L_0x00eb;
    L_0x01d6:
        r1 = move-exception;
        r3 = r4;
        r4 = r2;
        goto L_0x00e4;
    L_0x01db:
        r1 = move-exception;
        r4 = r0;
        goto L_0x00e4;
    L_0x01df:
        r1 = move-exception;
        goto L_0x00e4;
    L_0x01e2:
        r0 = r2;
        goto L_0x00e1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.stateless.f.a(android.content.Context, java.lang.String, java.lang.String, byte[]):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x00b6 A:{SYNTHETIC, Splitter: B:23:0x00b6} */
    public static byte[] a(java.lang.String r9) throws java.io.IOException {
        /*
        r1 = 0;
        r6 = c;
        monitor-enter(r6);
        r0 = "walle";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ all -> 0x00ba }
        r3 = 0;
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00ba }
        r4.<init>();	 Catch:{ all -> 0x00ba }
        r5 = "[stateless] begin read envelope, thread is ";
        r4 = r4.append(r5);	 Catch:{ all -> 0x00ba }
        r5 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x00ba }
        r4 = r4.append(r5);	 Catch:{ all -> 0x00ba }
        r4 = r4.toString();	 Catch:{ all -> 0x00ba }
        r2[r3] = r4;	 Catch:{ all -> 0x00ba }
        com.umeng.commonsdk.statistics.common.e.a(r0, r2);	 Catch:{ all -> 0x00ba }
        r0 = new java.io.RandomAccessFile;	 Catch:{ IOException -> 0x008d }
        r2 = "r";
        r0.<init>(r9, r2);	 Catch:{ IOException -> 0x008d }
        r0 = r0.getChannel();	 Catch:{ IOException -> 0x008d }
        r1 = java.nio.channels.FileChannel.MapMode.READ_ONLY;	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r2 = 0;
        r4 = r0.size();	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r1 = r0.map(r1, r2, r4);	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r1 = r1.load();	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r2 = java.lang.System.out;	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r3 = r1.isLoaded();	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r2.println(r3);	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r2 = r0.size();	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r2 = (int) r2;	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r2 = new byte[r2];	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r3 = r1.remaining();	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        if (r3 <= 0) goto L_0x0062;
    L_0x005a:
        r3 = 0;
        r4 = r1.remaining();	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r1.get(r2, r3, r4);	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
    L_0x0062:
        r1 = "walle";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r4 = 0;
        r5 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r5.<init>();	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r7 = "[stateless] end read envelope, thread id ";
        r5 = r5.append(r7);	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r7 = java.lang.Thread.currentThread();	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r5 = r5.append(r7);	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r5 = r5.toString();	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        r3[r4] = r5;	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        com.umeng.commonsdk.statistics.common.e.a(r1, r3);	 Catch:{ IOException -> 0x00c6, all -> 0x00c1 }
        if (r0 == 0) goto L_0x008b;
    L_0x0088:
        r0.close();	 Catch:{ IOException -> 0x00bd }
    L_0x008b:
        monitor-exit(r6);	 Catch:{ all -> 0x00ba }
        return r2;
    L_0x008d:
        r0 = move-exception;
    L_0x008e:
        r2 = "walle";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x00b3 }
        r4 = 0;
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00b3 }
        r5.<init>();	 Catch:{ all -> 0x00b3 }
        r7 = "[stateless] write envelope, e is ";
        r5 = r5.append(r7);	 Catch:{ all -> 0x00b3 }
        r7 = r0.getMessage();	 Catch:{ all -> 0x00b3 }
        r5 = r5.append(r7);	 Catch:{ all -> 0x00b3 }
        r5 = r5.toString();	 Catch:{ all -> 0x00b3 }
        r3[r4] = r5;	 Catch:{ all -> 0x00b3 }
        com.umeng.commonsdk.statistics.common.e.a(r2, r3);	 Catch:{ all -> 0x00b3 }
        throw r0;	 Catch:{ all -> 0x00b3 }
    L_0x00b3:
        r0 = move-exception;
    L_0x00b4:
        if (r1 == 0) goto L_0x00b9;
    L_0x00b6:
        r1.close();	 Catch:{ IOException -> 0x00bf }
    L_0x00b9:
        throw r0;	 Catch:{ all -> 0x00ba }
    L_0x00ba:
        r0 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x00ba }
        throw r0;
    L_0x00bd:
        r0 = move-exception;
        goto L_0x008b;
    L_0x00bf:
        r1 = move-exception;
        goto L_0x00b9;
    L_0x00c1:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
        goto L_0x00b4;
    L_0x00c6:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
        goto L_0x008e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.stateless.f.a(java.lang.String):byte[]");
    }

    public static File a(Context context) {
        File file;
        Throwable th;
        Throwable th2;
        Throwable th3;
        File file2 = null;
        try {
            synchronized (c) {
                try {
                    e.a("walle", "get last envelope begin, thread is " + Thread.currentThread());
                    if (!(context == null || StubApp.getOrigApplicationContext(context.getApplicationContext()) == null)) {
                        Object obj = StubApp.getOrigApplicationContext(context.getApplicationContext()).getFilesDir() + "/" + a.e;
                        if (!TextUtils.isEmpty(obj)) {
                            File file3 = new File(obj);
                            if (file3 != null && file3.isDirectory()) {
                                File[] listFiles = file3.listFiles();
                                if (listFiles != null && listFiles.length > 0) {
                                    int i = 0;
                                    file = null;
                                    while (i < listFiles.length) {
                                        try {
                                            file2 = listFiles[i];
                                            if (file2 != null && file2.isDirectory()) {
                                                File[] listFiles2 = file2.listFiles();
                                                if (listFiles2 != null && listFiles2.length > 0) {
                                                    Arrays.sort(listFiles2, new Comparator<File>() {
                                                        /* renamed from: a */
                                                        public int compare(File file, File file2) {
                                                            long lastModified = file.lastModified() - file2.lastModified();
                                                            if (lastModified > 0) {
                                                                return 1;
                                                            }
                                                            if (lastModified == 0) {
                                                                return 0;
                                                            }
                                                            return -1;
                                                        }
                                                    });
                                                    file2 = listFiles2[0];
                                                    if (file2 != null && (file == null || file.lastModified() > file2.lastModified())) {
                                                        file = file2;
                                                    }
                                                }
                                            }
                                            i++;
                                        } catch (Throwable th22) {
                                            th = th22;
                                            file2 = file;
                                            th3 = th;
                                            try {
                                                throw th3;
                                            } catch (Throwable th32) {
                                                th = th32;
                                                file = file2;
                                                th22 = th;
                                            }
                                        }
                                    }
                                    e.a("walle", "get last envelope end, thread is " + Thread.currentThread());
                                }
                            }
                        }
                    }
                    file = null;
                    e.a("walle", "get last envelope end, thread is " + Thread.currentThread());
                } catch (Throwable th4) {
                    th32 = th4;
                    throw th32;
                }
            }
        } catch (Throwable th322) {
            th = th322;
            file = null;
            th22 = th;
            b.a(context, th22);
            return file;
        }
    }

    public static void a(Context context, String str, int i) {
        int i2 = 0;
        if (str == null) {
            try {
                e.a("AmapLBS", "[lbs-build] fileDir not exist, thread is " + Thread.currentThread());
                return;
            } catch (Throwable th) {
                b.a(context, th);
                return;
            }
        }
        File file = new File(str);
        if (file.isDirectory()) {
            synchronized (c) {
                File[] listFiles = file.listFiles();
                e.a("AmapLBS", "[lbs-build] delete file begin " + listFiles.length + ", thread is " + Thread.currentThread());
                if (listFiles == null || listFiles.length < i) {
                    e.a("AmapLBS", "[lbs-build] file size < max");
                } else {
                    int i3;
                    e.a("AmapLBS", "[lbs-build] file size >= max");
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : listFiles) {
                        if (obj != null) {
                            arrayList.add(obj);
                        }
                    }
                    if (arrayList != null && arrayList.size() >= i) {
                        Collections.sort(arrayList, new Comparator<File>() {
                            /* renamed from: a */
                            public int compare(File file, File file2) {
                                if (file != null && file2 != null && file.lastModified() < file2.lastModified()) {
                                    return -1;
                                }
                                if (file == null || file2 == null || file.lastModified() != file2.lastModified()) {
                                    return 1;
                                }
                                return 0;
                            }
                        });
                        if (e.a) {
                            for (i3 = 0; i3 < arrayList.size(); i3++) {
                                e.a("AmapLBS", "[lbs-build] overrun native file is " + ((File) arrayList.get(i3)).getPath());
                            }
                        }
                        while (i2 <= arrayList.size() - i) {
                            if (!(arrayList == null || arrayList.get(i2) == null)) {
                                e.a("AmapLBS", "[lbs-build] overrun remove file is " + ((File) arrayList.get(i2)).getPath());
                                try {
                                    ((File) arrayList.get(i2)).delete();
                                    arrayList.remove(i2);
                                } catch (Exception e) {
                                }
                            }
                            i2++;
                        }
                    }
                }
                e.a("AmapLBS", "[lbs-build] delete file end " + listFiles.length + ", thread is " + Thread.currentThread());
            }
            return;
        }
        e.a("AmapLBS", "[lbs-build] fileDir not exist, thread is " + Thread.currentThread());
    }

    public static boolean a(long j, long j2) {
        if (j > j2) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0035  */
    public static byte[] a(byte[] r6) throws java.io.IOException {
        /*
        r0 = 0;
        r4 = 0;
        if (r6 == 0) goto L_0x0007;
    L_0x0004:
        r1 = r6.length;
        if (r1 > 0) goto L_0x0008;
    L_0x0007:
        return r0;
    L_0x0008:
        r2 = new java.util.zip.Deflater;
        r2.<init>();
        r2.setInput(r6);
        r2.finish();
        r1 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r3 = new byte[r1];
        a = r4;
        r1 = new java.io.ByteArrayOutputStream;	 Catch:{ all -> 0x0046 }
        r1.<init>();	 Catch:{ all -> 0x0046 }
    L_0x001e:
        r0 = r2.finished();	 Catch:{ all -> 0x0032 }
        if (r0 != 0) goto L_0x0039;
    L_0x0024:
        r0 = r2.deflate(r3);	 Catch:{ all -> 0x0032 }
        r4 = a;	 Catch:{ all -> 0x0032 }
        r4 = r4 + r0;
        a = r4;	 Catch:{ all -> 0x0032 }
        r4 = 0;
        r1.write(r3, r4, r0);	 Catch:{ all -> 0x0032 }
        goto L_0x001e;
    L_0x0032:
        r0 = move-exception;
    L_0x0033:
        if (r1 == 0) goto L_0x0038;
    L_0x0035:
        r1.close();
    L_0x0038:
        throw r0;
    L_0x0039:
        r2.end();	 Catch:{ all -> 0x0032 }
        if (r1 == 0) goto L_0x0041;
    L_0x003e:
        r1.close();
    L_0x0041:
        r0 = r1.toByteArray();
        goto L_0x0007;
    L_0x0046:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0033;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.stateless.f.a(byte[]):byte[]");
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) throws Exception {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
        instance.init(1, new SecretKeySpec(bArr2, "AES"), new IvParameterSpec(b));
        return instance.doFinal(bArr);
    }

    public static byte[] b(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.reset();
            instance.update(bArr);
            return instance.digest();
        } catch (Exception e) {
            return null;
        }
    }

    public static String c(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i++) {
            stringBuffer.append(String.format("%02X", new Object[]{Byte.valueOf(bArr[i])}));
        }
        return stringBuffer.toString().toLowerCase(Locale.US);
    }
}
