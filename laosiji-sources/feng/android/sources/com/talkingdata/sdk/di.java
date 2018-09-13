package com.talkingdata.sdk;

import android.os.Process;
import java.util.Properties;
import java.util.zip.CRC32;

/* compiled from: td */
public class di extends Properties implements Comparable {
    private String a;
    private byte[] b;
    private int c;
    private int d;
    private CRC32 e;

    /* compiled from: td */
    public static final class a {
        public static final String DATA = "data";
        public static final String LENGTH = "length";
        public static final String RCS32 = "rcs32";
    }

    /* compiled from: td */
    public static final class b {
        public static final String TYPE_ANTICHEATING = "Type";
    }

    public di(String str) {
        this.a = str;
    }

    public di(byte[] bArr) {
        this(a(), bArr);
    }

    private di(String str, byte[] bArr) {
        this(str);
        this.e = new CRC32();
        writeData(bArr);
    }

    public static String a() {
        return System.currentTimeMillis() + "_" + Long.toString((long) Process.myPid());
    }

    public String b() {
        return this.a;
    }

    public byte[] c() {
        return this.b;
    }

    public int d() {
        return this.c;
    }

    public int e() {
        return this.d;
    }

    public int a(String str, int i) {
        String str2 = (String) setProperty(str, String.valueOf(i));
        return str2 == null ? 0 : Integer.parseInt(str2);
    }

    public byte[] a(String str, byte[] bArr) {
        String str2 = (String) setProperty(str, a(bArr));
        return str2 == null ? null : c(str2);
    }

    public int a(String str) {
        return Integer.parseInt(super.getProperty(str));
    }

    public byte[] b(String str) {
        return c(super.getProperty(str));
    }

    public void writeData(byte[] bArr) {
        if (bArr != null) {
            this.b = new byte[bArr.length];
            System.arraycopy(bArr, 0, this.b, 0, bArr.length);
            this.d = this.b.length;
            this.e.reset();
            this.e.update(this.b);
            this.c = (int) this.e.getValue();
        }
    }

    public byte[] c(String str) {
        if (str != null) {
            return str.getBytes();
        }
        return null;
    }

    public String a(byte[] bArr) {
        return new String(bArr);
    }

    /* renamed from: a */
    public int compareTo(di diVar) {
        return b().compareTo(diVar.b());
    }
}
