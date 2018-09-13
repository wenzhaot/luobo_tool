package com.tencent.bugly.imsdk.proguard;

import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import java.nio.ByteBuffer;

/* compiled from: BUGLY */
public final class k {
    public static boolean a(boolean z, boolean z2) {
        return z == z2;
    }

    public static boolean a(int i, int i2) {
        return i == i2;
    }

    public static boolean a(long j, long j2) {
        return j == j2;
    }

    public static boolean a(Object obj, Object obj2) {
        return obj.equals(obj2);
    }

    public static byte[] a(ByteBuffer byteBuffer) {
        Object obj = new byte[byteBuffer.position()];
        System.arraycopy(byteBuffer.array(), 0, obj, 0, obj.length);
        return obj;
    }

    static {
        byte[] bArr = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70};
        byte[] bArr2 = new byte[AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS];
        byte[] bArr3 = new byte[AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS];
        for (int i = 0; i < AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS; i++) {
            bArr2[i] = bArr[i >>> 4];
            bArr3[i] = bArr[i & 15];
        }
    }
}
