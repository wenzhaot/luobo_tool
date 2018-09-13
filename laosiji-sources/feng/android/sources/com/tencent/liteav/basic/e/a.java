package com.tencent.liteav.basic.e;

/* compiled from: TXSAudioPacket */
public class a implements Cloneable {
    public int a;
    public int b;
    public int c;
    public int d;
    public long e;
    public byte[] f;

    public Object clone() {
        try {
            return (a) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
