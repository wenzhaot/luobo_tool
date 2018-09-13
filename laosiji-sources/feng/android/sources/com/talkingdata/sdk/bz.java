package com.talkingdata.sdk;

import com.talkingdata.sdk.bb.b;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;

/* compiled from: td */
final class bz {
    private static IvParameterSpec a = null;
    private static byte[] b = null;
    private static final String c = "mPBE";
    private static final String d = "iv";
    private static final String e = "salt";
    private static final int f = 16;
    private static final int g = 32;
    private static final String h = "AES/CBC/PKCS5Padding";
    private static final String i = "AES";

    bz() {
    }

    private static IvParameterSpec a() {
        if (a != null) {
            return a;
        }
        try {
            bb.getFileLock(b.AES_IV_LOCK.toString());
            byte[] a = a(d, 16);
            if (a == null) {
                a = a(16);
                a(d, a);
            }
            a = new IvParameterSpec(a);
        } catch (Throwable th) {
        } finally {
            bb.releaseFileLock(b.AES_IV_LOCK.toString());
        }
        return a;
    }

    private static byte[] b() {
        if (b != null) {
            return b;
        }
        try {
            bb.getFileLock(b.AES_SALT_LOCK.toString());
            b = a(e, 32);
            if (b == null || b.length == 0) {
                b = a(32);
                a(e, b);
            }
            bb.releaseFileLock(b.AES_SALT_LOCK.toString());
        } catch (Throwable th) {
            bb.releaseFileLock(b.AES_SALT_LOCK.toString());
            throw th;
        }
        return b;
    }

    private static byte[] a(int i) {
        byte[] bArr = new byte[i];
        bo.b().nextBytes(bArr);
        return bArr;
    }

    private static SecretKey a(char[] cArr, byte[] bArr) {
        return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(cArr, bArr, 10000, 128));
    }

    static byte[] a(byte[] bArr, String str) {
        Key a = a(str.toCharArray(), b());
        String str2 = h;
        if (bo.b(19)) {
            str2 = i;
        }
        Cipher instance = Cipher.getInstance(str2);
        instance.init(1, a, a());
        return instance.doFinal(bArr);
    }

    static byte[] b(byte[] bArr, String str) {
        Key a = a(str.toCharArray(), b());
        String str2 = h;
        if (bo.b(19)) {
            str2 = i;
        }
        Cipher instance = Cipher.getInstance(str2);
        instance.init(2, a, a());
        return instance.doFinal(bArr);
    }

    private static void a(String str, byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte append : bArr) {
            stringBuilder.append(append).append(MiPushClient.ACCEPT_TIME_SEPARATOR);
        }
        File file = new File(ab.g.getFilesDir() + File.separator + c);
        if (!(file.exists() || file.isDirectory())) {
            file.mkdirs();
        }
        File file2 = new File(file, str);
        if (!file2.exists()) {
            file2.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        try {
            fileOutputStream.write(stringBuilder.toString().getBytes());
        } catch (Throwable th) {
        } finally {
            fileOutputStream.close();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a7 A:{SYNTHETIC, Splitter: B:32:0x00a7} */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00ae A:{SYNTHETIC, Splitter: B:36:0x00ae} */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ac  */
    private static byte[] a(java.lang.String r6, int r7) {
        /*
        r1 = 0;
        r0 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x00af }
        r2 = "mPBE";
        r3 = 0;
        r0 = r0.getSharedPreferences(r2, r3);	 Catch:{ Throwable -> 0x00af }
        r2 = "";
        r3 = r0.getString(r6, r2);	 Catch:{ Throwable -> 0x00af }
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00af }
        r2.<init>();	 Catch:{ Throwable -> 0x00af }
        r4 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x00af }
        r4 = r4.getFilesDir();	 Catch:{ Throwable -> 0x00af }
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x00af }
        r4 = java.io.File.separator;	 Catch:{ Throwable -> 0x00af }
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x00af }
        r4 = "mPBE";
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x00af }
        r2 = r2.toString();	 Catch:{ Throwable -> 0x00af }
        r4 = new java.io.File;	 Catch:{ Throwable -> 0x00af }
        r4.<init>(r2);	 Catch:{ Throwable -> 0x00af }
        r2 = r4.exists();	 Catch:{ Throwable -> 0x00af }
        if (r2 != 0) goto L_0x0046;
    L_0x003d:
        r2 = r4.isDirectory();	 Catch:{ Throwable -> 0x00af }
        if (r2 != 0) goto L_0x0046;
    L_0x0043:
        r4.mkdirs();	 Catch:{ Throwable -> 0x00af }
    L_0x0046:
        r5 = new java.io.File;	 Catch:{ Throwable -> 0x00af }
        r5.<init>(r4, r6);	 Catch:{ Throwable -> 0x00af }
        r2 = r5.exists();	 Catch:{ Throwable -> 0x00af }
        if (r2 != 0) goto L_0x0074;
    L_0x0051:
        r5.createNewFile();	 Catch:{ Throwable -> 0x00af }
        r2 = android.text.TextUtils.isEmpty(r3);	 Catch:{ Throwable -> 0x00af }
        if (r2 != 0) goto L_0x0093;
    L_0x005a:
        r2 = r3.getBytes();	 Catch:{ Throwable -> 0x00af }
        a(r6, r2);	 Catch:{ Throwable -> 0x00af }
        r0 = r0.edit();	 Catch:{ Throwable -> 0x00af }
        r2 = "";
        r0 = r0.putString(r6, r2);	 Catch:{ Throwable -> 0x00af }
        r0.apply();	 Catch:{ Throwable -> 0x00af }
        r0 = a(r7, r3);	 Catch:{ Throwable -> 0x00af }
    L_0x0073:
        return r0;
    L_0x0074:
        r2 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x0098, all -> 0x00a3 }
        r2.<init>(r5);	 Catch:{ Throwable -> 0x0098, all -> 0x00a3 }
        r4 = r5.length();	 Catch:{ Throwable -> 0x00ba, all -> 0x00b8 }
        r0 = (int) r4;	 Catch:{ Throwable -> 0x00ba, all -> 0x00b8 }
        r0 = new byte[r0];	 Catch:{ Throwable -> 0x00ba, all -> 0x00b8 }
        r2.read(r0);	 Catch:{ Throwable -> 0x00ba, all -> 0x00b8 }
        r4 = new java.lang.String;	 Catch:{ Throwable -> 0x00ba, all -> 0x00b8 }
        r4.<init>(r0);	 Catch:{ Throwable -> 0x00ba, all -> 0x00b8 }
        r0 = a(r7, r4);	 Catch:{ Throwable -> 0x00ba, all -> 0x00b8 }
        if (r2 == 0) goto L_0x0091;
    L_0x008e:
        r2.close();	 Catch:{ Throwable -> 0x00b2 }
    L_0x0091:
        if (r0 != 0) goto L_0x0073;
    L_0x0093:
        r0 = a(r7, r3);	 Catch:{ Throwable -> 0x00af }
        goto L_0x0073;
    L_0x0098:
        r0 = move-exception;
        r0 = r1;
    L_0x009a:
        if (r0 == 0) goto L_0x009f;
    L_0x009c:
        r0.close();	 Catch:{ Throwable -> 0x00b4 }
    L_0x009f:
        if (r1 == 0) goto L_0x0093;
    L_0x00a1:
        r0 = r1;
        goto L_0x0073;
    L_0x00a3:
        r0 = move-exception;
        r2 = r1;
    L_0x00a5:
        if (r2 == 0) goto L_0x00aa;
    L_0x00a7:
        r2.close();	 Catch:{ Throwable -> 0x00b6 }
    L_0x00aa:
        if (r1 == 0) goto L_0x00ae;
    L_0x00ac:
        r0 = r1;
        goto L_0x0073;
    L_0x00ae:
        throw r0;	 Catch:{ Throwable -> 0x00af }
    L_0x00af:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0073;
    L_0x00b2:
        r2 = move-exception;
        goto L_0x0091;
    L_0x00b4:
        r0 = move-exception;
        goto L_0x009f;
    L_0x00b6:
        r2 = move-exception;
        goto L_0x00aa;
    L_0x00b8:
        r0 = move-exception;
        goto L_0x00a5;
    L_0x00ba:
        r0 = move-exception;
        r0 = r2;
        goto L_0x009a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.bz.a(java.lang.String, int):byte[]");
    }

    private static byte[] a(int i, String str) {
        try {
            byte[] bArr = new byte[i];
            String[] split = str.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
            for (int i2 = 0; i2 < split.length; i2++) {
                bArr[i2] = Byte.parseByte(split[i2]);
            }
            return bArr;
        } catch (Throwable th) {
            return null;
        }
    }
}
