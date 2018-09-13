package com.qiniu.android.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Etag {
    /* JADX WARNING: Removed duplicated region for block: B:23:0x002a A:{SYNTHETIC, Splitter: B:23:0x002a} */
    public static java.lang.String data(byte[] r6, int r7, int r8) {
        /*
        r1 = 0;
        r2 = new java.io.ByteArrayInputStream;	 Catch:{ IOException -> 0x0017 }
        r2.<init>(r6, r7, r8);	 Catch:{ IOException -> 0x0017 }
        r4 = (long) r8;
        r3 = stream(r2, r4);	 Catch:{ IOException -> 0x0036, all -> 0x0033 }
        if (r2 == 0) goto L_0x0010;
    L_0x000d:
        r2.close();	 Catch:{ Exception -> 0x0012 }
    L_0x0010:
        r1 = r2;
    L_0x0011:
        return r3;
    L_0x0012:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0010;
    L_0x0017:
        r0 = move-exception;
    L_0x0018:
        r0.printStackTrace();	 Catch:{ all -> 0x0027 }
        if (r1 == 0) goto L_0x0020;
    L_0x001d:
        r1.close();	 Catch:{ Exception -> 0x0022 }
    L_0x0020:
        r3 = 0;
        goto L_0x0011;
    L_0x0022:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0020;
    L_0x0027:
        r3 = move-exception;
    L_0x0028:
        if (r1 == 0) goto L_0x002d;
    L_0x002a:
        r1.close();	 Catch:{ Exception -> 0x002e }
    L_0x002d:
        throw r3;
    L_0x002e:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x002d;
    L_0x0033:
        r3 = move-exception;
        r1 = r2;
        goto L_0x0028;
    L_0x0036:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiniu.android.utils.Etag.data(byte[], int, int):java.lang.String");
    }

    public static String data(byte[] data) {
        return data(data, 0, data.length);
    }

    public static String file(File file) throws IOException {
        Throwable th;
        InputStream in = null;
        try {
            InputStream in2 = new FileInputStream(file);
            try {
                String stream = stream(in2, file.length());
                if (in2 != null) {
                    try {
                        in2.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return stream;
            } catch (Throwable th2) {
                th = th2;
                in = in2;
            }
        } catch (Throwable th3) {
            th = th3;
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static String file(String filePath) throws IOException {
        return file(new File(filePath));
    }

    public static String stream(InputStream in, long len) throws IOException {
        if (len == 0) {
            return "Fto5o-5ea0sNMlW_75VgGJCv2AcJ";
        }
        byte[] buffer = new byte[65536];
        byte[][] blocks = new byte[((int) (((4194304 + len) - 1) / 4194304))][];
        for (int i = 0; i < blocks.length; i++) {
            long read;
            long left = len - (4194304 * ((long) i));
            if (left > 4194304) {
                read = 4194304;
            } else {
                read = left;
            }
            blocks[i] = oneBlock(buffer, in, (int) read);
        }
        return resultEncode(blocks);
    }

    private static byte[] oneBlock(byte[] buffer, InputStream in, int len) throws IOException {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("sha-1");
            int buffSize = buffer.length;
            while (len != 0) {
                int next;
                if (buffSize > len) {
                    next = len;
                } else {
                    next = buffSize;
                }
                in.read(buffer, 0, next);
                sha1.update(buffer, 0, next);
                len -= next;
            }
            return sha1.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String resultEncode(byte[][] sha1s) {
        byte head = (byte) 22;
        byte[] finalHash = sha1s[0];
        int len = finalHash.length;
        byte[] ret = new byte[(len + 1)];
        if (sha1s.length != 1) {
            head = (byte) -106;
            try {
                MessageDigest sha1 = MessageDigest.getInstance("sha-1");
                for (byte[] s : sha1s) {
                    sha1.update(s);
                }
                finalHash = sha1.digest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }
        ret[0] = head;
        System.arraycopy(finalHash, 0, ret, 1, len);
        return UrlSafeBase64.encodeToString(ret);
    }
}
