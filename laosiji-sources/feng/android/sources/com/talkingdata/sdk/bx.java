package com.talkingdata.sdk;

/* compiled from: td */
public final class bx {
    private static byte[] a = new byte[0];

    private bx() {
    }

    public static byte[] a(byte[] bArr) {
        try {
            return bz.a(bArr, String.valueOf(ca.a()));
        } catch (Throwable th) {
            cs.postSDKError(th);
            return a;
        }
    }

    public static byte[] b(byte[] bArr) {
        try {
            return bz.b(bArr, String.valueOf(ca.a()));
        } catch (Throwable th) {
            cs.postSDKError(th);
            return a;
        }
    }
}
