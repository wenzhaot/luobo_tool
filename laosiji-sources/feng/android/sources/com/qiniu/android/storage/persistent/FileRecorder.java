package com.qiniu.android.storage.persistent;

import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.qiniu.android.storage.Recorder;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Date;

public final class FileRecorder implements Recorder {
    public String directory;

    public FileRecorder(String directory) throws IOException {
        this.directory = directory;
        File f = new File(directory);
        if (f.exists()) {
            if (!f.isDirectory()) {
                throw new IOException("does not mkdir");
            }
        } else if (!f.mkdirs()) {
            throw new IOException("mkdir failed");
        }
    }

    private static String hash(String base) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-1").digest(base.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (byte b : hash) {
                hexString.append(Integer.toString((b & 255) + AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS, 16).substring(1));
            }
            return hexString.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0017 A:{SYNTHETIC, Splitter: B:7:0x0017} */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0017 A:{SYNTHETIC, Splitter: B:7:0x0017} */
    /* JADX WARNING: Removed duplicated region for block: B:16:? A:{SYNTHETIC, RETURN} */
    public void set(java.lang.String r7, byte[] r8) {
        /*
        r6 = this;
        r1 = new java.io.File;
        r4 = r6.directory;
        r5 = hash(r7);
        r1.<init>(r4, r5);
        r2 = 0;
        r3 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x001b }
        r3.<init>(r1);	 Catch:{ IOException -> 0x001b }
        r3.write(r8);	 Catch:{ IOException -> 0x0025 }
        r2 = r3;
    L_0x0015:
        if (r2 == 0) goto L_0x001a;
    L_0x0017:
        r2.close();	 Catch:{ IOException -> 0x0020 }
    L_0x001a:
        return;
    L_0x001b:
        r0 = move-exception;
    L_0x001c:
        r0.printStackTrace();
        goto L_0x0015;
    L_0x0020:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x001a;
    L_0x0025:
        r0 = move-exception;
        r2 = r3;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiniu.android.storage.persistent.FileRecorder.set(java.lang.String, byte[]):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c A:{SYNTHETIC, Splitter: B:10:0x002c} */
    /* JADX WARNING: Removed duplicated region for block: B:21:? A:{SYNTHETIC, RETURN, Catch:{ IOException -> 0x0033 }} */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c A:{SYNTHETIC, Splitter: B:10:0x002c} */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:21:? A:{SYNTHETIC, RETURN, Catch:{ IOException -> 0x0033 }} */
    public byte[] get(java.lang.String r11) {
        /*
        r10 = this;
        r6 = 0;
        r2 = new java.io.File;
        r7 = r10.directory;
        r8 = hash(r11);
        r2.<init>(r7, r8);
        r3 = 0;
        r0 = 0;
        r5 = 0;
        r7 = r10.outOfDate(r2);	 Catch:{ IOException -> 0x0033 }
        if (r7 == 0) goto L_0x0019;
    L_0x0015:
        r2.delete();	 Catch:{ IOException -> 0x0033 }
    L_0x0018:
        return r6;
    L_0x0019:
        r8 = r2.length();	 Catch:{ IOException -> 0x0033 }
        r7 = (int) r8;	 Catch:{ IOException -> 0x0033 }
        r0 = new byte[r7];	 Catch:{ IOException -> 0x0033 }
        r4 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0033 }
        r4.<init>(r2);	 Catch:{ IOException -> 0x0033 }
        r5 = r4.read(r0);	 Catch:{ IOException -> 0x003d }
        r3 = r4;
    L_0x002a:
        if (r3 == 0) goto L_0x002f;
    L_0x002c:
        r3.close();	 Catch:{ IOException -> 0x0038 }
    L_0x002f:
        if (r5 == 0) goto L_0x0018;
    L_0x0031:
        r6 = r0;
        goto L_0x0018;
    L_0x0033:
        r1 = move-exception;
    L_0x0034:
        r1.printStackTrace();
        goto L_0x002a;
    L_0x0038:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x002f;
    L_0x003d:
        r1 = move-exception;
        r3 = r4;
        goto L_0x0034;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiniu.android.storage.persistent.FileRecorder.get(java.lang.String):byte[]");
    }

    private boolean outOfDate(File f) {
        return f.lastModified() + 172800000 < new Date().getTime();
    }

    public void del(String key) {
        new File(this.directory, hash(key)).delete();
    }
}
