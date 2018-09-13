package com.ta.utdid2.b.a;

import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;

/* compiled from: IntUtils */
public class e {
    public static byte[] getBytes(int i) {
        byte[] bArr = new byte[4];
        bArr[3] = (byte) (i % AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS);
        int i2 = i >> 8;
        bArr[2] = (byte) (i2 % AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS);
        i2 >>= 8;
        bArr[1] = (byte) (i2 % AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS);
        bArr[0] = (byte) ((i2 >> 8) % AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS);
        return bArr;
    }
}
