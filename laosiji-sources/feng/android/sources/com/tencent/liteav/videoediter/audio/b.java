package com.tencent.liteav.videoediter.audio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: BufferUtils */
public class b {
    public static short[] a(ByteBuffer byteBuffer, int i) {
        if (byteBuffer == null) {
            return null;
        }
        short[] sArr = new short[(i / 2)];
        byteBuffer.order(ByteOrder.nativeOrder()).asShortBuffer().get(sArr);
        return sArr;
    }

    public static ByteBuffer a(short[] sArr) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(sArr.length * 2);
        allocateDirect.order(ByteOrder.nativeOrder()).asShortBuffer().put(sArr);
        allocateDirect.position(0);
        return allocateDirect;
    }

    public static ByteBuffer a(ByteBuffer byteBuffer, short[] sArr) {
        int length = sArr.length * 2;
        if (byteBuffer == null || byteBuffer.capacity() < length) {
            return a(sArr);
        }
        byteBuffer.position(0);
        byteBuffer.order(ByteOrder.nativeOrder()).asShortBuffer().put(sArr);
        byteBuffer.limit(length);
        return byteBuffer;
    }
}
