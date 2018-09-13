package com.meizu.cloud.pushsdk.notification.util;

import com.meizu.cloud.pushinternal.DebugLogger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ZipExtractTask {
    private static final String LOG_TAG = "ZipExtractTask";
    private String mEextractedDir = this.mOutput.getAbsolutePath();
    private final File mInput;
    private final File mOutput;

    public ZipExtractTask(String inFile, String outputDir) {
        this.mInput = new File(inFile);
        this.mOutput = new File(outputDir);
        DebugLogger.i(LOG_TAG, "Extract mInput file = " + this.mInput.toString());
        DebugLogger.i(LOG_TAG, "Extract mOutput file = " + this.mOutput.toString());
    }

    private void deleteZipFile() {
        if (this.mInput != null && this.mInput.exists()) {
            if (this.mInput.delete()) {
                DebugLogger.i(LOG_TAG, "Delete file:" + this.mInput.toString() + " after extracted.");
            } else {
                DebugLogger.i(LOG_TAG, "Can't delete file:" + this.mInput.toString() + " after extracted.");
            }
        }
    }

    public boolean doUnzipSync() {
        return unzip() > 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0133  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0133  */
    private long unzip() {
        /*
        r24 = this;
        r14 = android.os.SystemClock.currentThreadTimeMillis();
        r10 = 0;
        r2 = 0;
        r16 = 0;
        r18 = 0;
        r17 = new java.util.zip.ZipFile;	 Catch:{ ZipException -> 0x0280, IOException -> 0x027d }
        r0 = r24;
        r0 = r0.mInput;	 Catch:{ ZipException -> 0x0280, IOException -> 0x027d }
        r19 = r0;
        r0 = r17;
        r1 = r19;
        r0.<init>(r1);	 Catch:{ ZipException -> 0x0280, IOException -> 0x027d }
        r5 = r17.entries();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
    L_0x001e:
        r19 = r5.hasMoreElements();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        if (r19 == 0) goto L_0x01c9;
    L_0x0024:
        r8 = r5.nextElement();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r8 = (java.util.zip.ZipEntry) r8;	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r19 = r8.isDirectory();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        if (r19 != 0) goto L_0x001e;
    L_0x0030:
        r9 = r8.getName();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        if (r18 != 0) goto L_0x0074;
    L_0x0036:
        if (r9 == 0) goto L_0x0074;
    L_0x0038:
        r19 = "/";
        r0 = r19;
        r19 = r9.split(r0);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r20 = 0;
        r18 = r19[r20];	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r19 = "ZipExtractTask";
        r20 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r20.<init>();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r21 = "Extract temp directory=";
        r20 = r20.append(r21);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r0 = r24;
        r0 = r0.mOutput;	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r21 = r0;
        r20 = r20.append(r21);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r21 = "/";
        r20 = r20.append(r21);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r0 = r20;
        r1 = r18;
        r20 = r0.append(r1);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r20 = r20.toString();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        com.meizu.cloud.pushinternal.DebugLogger.i(r19, r20);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
    L_0x0074:
        r3 = new java.io.File;	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r0 = r24;
        r0 = r0.mOutput;	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r19 = r0;
        r0 = r19;
        r3.<init>(r0, r9);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r19 = r3.getParentFile();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r19 = r19.exists();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        if (r19 != 0) goto L_0x00b7;
    L_0x008b:
        r19 = r3.getParentFile();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r19 = r19.mkdirs();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        if (r19 == 0) goto L_0x015c;
    L_0x0095:
        r19 = "ZipExtractTask";
        r20 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r20.<init>();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r21 = "Make Destination directory=";
        r20 = r20.append(r21);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r21 = r3.getParentFile();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r21 = r21.getAbsolutePath();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r20 = r20.append(r21);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r20 = r20.toString();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        com.meizu.cloud.pushinternal.DebugLogger.i(r19, r20);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
    L_0x00b7:
        r12 = new java.io.FileOutputStream;	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r12.<init>(r3);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r0 = r17;
        r19 = r0.getInputStream(r8);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r0 = r24;
        r1 = r19;
        r19 = r0.copy(r1, r12);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r0 = r19;
        r0 = (long) r0;	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r20 = r0;
        r10 = r10 + r20;
        r12.close();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        goto L_0x001e;
    L_0x00d6:
        r4 = move-exception;
        r16 = r17;
    L_0x00d9:
        r19 = "ZipExtractTask";
        r20 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0252 }
        r20.<init>();	 Catch:{ all -> 0x0252 }
        r21 = "ZipException :";
        r20 = r20.append(r21);	 Catch:{ all -> 0x0252 }
        r21 = r4.toString();	 Catch:{ all -> 0x0252 }
        r20 = r20.append(r21);	 Catch:{ all -> 0x0252 }
        r20 = r20.toString();	 Catch:{ all -> 0x0252 }
        com.meizu.cloud.pushinternal.DebugLogger.e(r19, r20);	 Catch:{ all -> 0x0252 }
        if (r16 == 0) goto L_0x00fc;
    L_0x00f9:
        r16.close();	 Catch:{ IOException -> 0x0231 }
    L_0x00fc:
        r6 = android.os.SystemClock.currentThreadTimeMillis();
        r19 = "ZipExtractTask";
        r20 = new java.lang.StringBuilder;
        r20.<init>();
        r21 = "Extract file ";
        r20 = r20.append(r21);
        r0 = r24;
        r0 = r0.mInput;
        r21 = r0;
        r20 = r20.append(r21);
        r21 = ", UseTime =";
        r20 = r20.append(r21);
        r22 = r6 - r14;
        r21 = java.lang.String.valueOf(r22);
        r20 = r20.append(r21);
        r20 = r20.toString();
        com.meizu.cloud.pushinternal.DebugLogger.i(r19, r20);
        if (r2 == 0) goto L_0x0158;
    L_0x0133:
        r19 = new java.lang.StringBuilder;
        r19.<init>();
        r0 = r24;
        r0 = r0.mOutput;
        r20 = r0;
        r19 = r19.append(r20);
        r20 = "/";
        r19 = r19.append(r20);
        r0 = r19;
        r1 = r18;
        r19 = r0.append(r1);
        r19 = r19.toString();
        com.meizu.cloud.pushsdk.notification.util.FileUtil.deleteDirectory(r19);
    L_0x0158:
        r24.deleteZipFile();
        return r10;
    L_0x015c:
        r19 = "ZipExtractTask";
        r20 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r20.<init>();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r21 = "Can't make destination directory=";
        r20 = r20.append(r21);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r21 = r3.getParentFile();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r21 = r21.getAbsolutePath();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r20 = r20.append(r21);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r20 = r20.toString();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        com.meizu.cloud.pushinternal.DebugLogger.i(r19, r20);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        goto L_0x00b7;
    L_0x0180:
        r4 = move-exception;
        r16 = r17;
    L_0x0183:
        r19 = "ZipExtractTask";
        r20 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0252 }
        r20.<init>();	 Catch:{ all -> 0x0252 }
        r21 = "Extracted IOException:";
        r20 = r20.append(r21);	 Catch:{ all -> 0x0252 }
        r21 = r4.toString();	 Catch:{ all -> 0x0252 }
        r20 = r20.append(r21);	 Catch:{ all -> 0x0252 }
        r20 = r20.toString();	 Catch:{ all -> 0x0252 }
        com.meizu.cloud.pushinternal.DebugLogger.e(r19, r20);	 Catch:{ all -> 0x0252 }
        if (r16 == 0) goto L_0x00fc;
    L_0x01a3:
        r16.close();	 Catch:{ IOException -> 0x01a8 }
        goto L_0x00fc;
    L_0x01a8:
        r4 = move-exception;
        r19 = "ZipExtractTask";
        r20 = new java.lang.StringBuilder;
        r20.<init>();
        r21 = "Extracted IOException:";
        r20 = r20.append(r21);
        r21 = r4.toString();
        r20 = r20.append(r21);
        r20 = r20.toString();
        com.meizu.cloud.pushinternal.DebugLogger.e(r19, r20);
        goto L_0x00fc;
    L_0x01c9:
        r19 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r19.<init>();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r0 = r24;
        r0 = r0.mOutput;	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r20 = r0;
        r19 = r19.append(r20);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r20 = "/";
        r19 = r19.append(r20);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r0 = r19;
        r1 = r18;
        r19 = r0.append(r1);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r13 = r19.toString();	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r0 = r24;
        r0 = r0.mEextractedDir;	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r19 = r0;
        r0 = r19;
        r19 = r0.equals(r13);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        if (r19 != 0) goto L_0x0205;
    L_0x01f9:
        r0 = r24;
        r0 = r0.mEextractedDir;	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r19 = r0;
        r0 = r19;
        com.meizu.cloud.pushsdk.notification.util.FileUtil.copyFolder(r13, r0);	 Catch:{ ZipException -> 0x00d6, IOException -> 0x0180, all -> 0x0279 }
        r2 = 1;
    L_0x0205:
        if (r17 == 0) goto L_0x020a;
    L_0x0207:
        r17.close();	 Catch:{ IOException -> 0x020e }
    L_0x020a:
        r16 = r17;
        goto L_0x00fc;
    L_0x020e:
        r4 = move-exception;
        r19 = "ZipExtractTask";
        r20 = new java.lang.StringBuilder;
        r20.<init>();
        r21 = "Extracted IOException:";
        r20 = r20.append(r21);
        r21 = r4.toString();
        r20 = r20.append(r21);
        r20 = r20.toString();
        com.meizu.cloud.pushinternal.DebugLogger.e(r19, r20);
        r16 = r17;
        goto L_0x00fc;
    L_0x0231:
        r4 = move-exception;
        r19 = "ZipExtractTask";
        r20 = new java.lang.StringBuilder;
        r20.<init>();
        r21 = "Extracted IOException:";
        r20 = r20.append(r21);
        r21 = r4.toString();
        r20 = r20.append(r21);
        r20 = r20.toString();
        com.meizu.cloud.pushinternal.DebugLogger.e(r19, r20);
        goto L_0x00fc;
    L_0x0252:
        r19 = move-exception;
    L_0x0253:
        if (r16 == 0) goto L_0x0258;
    L_0x0255:
        r16.close();	 Catch:{ IOException -> 0x0259 }
    L_0x0258:
        throw r19;
    L_0x0259:
        r4 = move-exception;
        r20 = "ZipExtractTask";
        r21 = new java.lang.StringBuilder;
        r21.<init>();
        r22 = "Extracted IOException:";
        r21 = r21.append(r22);
        r22 = r4.toString();
        r21 = r21.append(r22);
        r21 = r21.toString();
        com.meizu.cloud.pushinternal.DebugLogger.e(r20, r21);
        goto L_0x0258;
    L_0x0279:
        r19 = move-exception;
        r16 = r17;
        goto L_0x0253;
    L_0x027d:
        r4 = move-exception;
        goto L_0x0183;
    L_0x0280:
        r4 = move-exception;
        goto L_0x00d9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.notification.util.ZipExtractTask.unzip():long");
    }

    private int copy(InputStream input, OutputStream output) {
        byte[] buffer = new byte[8192];
        BufferedInputStream in = new BufferedInputStream(input, 8192);
        BufferedOutputStream out = new BufferedOutputStream(output, 8192);
        int count = 0;
        while (true) {
            try {
                int n = in.read(buffer, 0, 8192);
                if (n == -1) {
                    break;
                }
                out.write(buffer, 0, n);
                count += n;
            } catch (IOException e) {
                DebugLogger.e(LOG_TAG, "Extracted IOException:" + e.toString());
                try {
                    out.close();
                } catch (IOException e2) {
                    DebugLogger.e(LOG_TAG, "out.close() IOException e=" + e2.toString());
                }
                try {
                    in.close();
                } catch (IOException e22) {
                    DebugLogger.e(LOG_TAG, "in.close() IOException e=" + e22.toString());
                }
            } catch (Throwable th) {
                try {
                    out.close();
                } catch (IOException e222) {
                    DebugLogger.e(LOG_TAG, "out.close() IOException e=" + e222.toString());
                }
                try {
                    in.close();
                } catch (IOException e2222) {
                    DebugLogger.e(LOG_TAG, "in.close() IOException e=" + e2222.toString());
                }
                throw th;
            }
        }
        out.flush();
        try {
            out.close();
        } catch (IOException e22222) {
            DebugLogger.e(LOG_TAG, "out.close() IOException e=" + e22222.toString());
        }
        try {
            in.close();
        } catch (IOException e222222) {
            DebugLogger.e(LOG_TAG, "in.close() IOException e=" + e222222.toString());
        }
        return count;
    }
}
