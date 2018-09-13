package com.talkingdata.sdk;

/* compiled from: td */
public class cb {
    private String a;
    private String b;
    private byte c;
    private byte d;
    private byte e;

    public cb() {
        this.a = "";
        this.b = "00:00:00:00:00:00";
        this.c = (byte) -127;
        this.d = (byte) 1;
        this.e = (byte) 1;
    }

    public cb(String str, String str2, byte b, byte b2, byte b3) {
        this.a = str;
        this.b = str2;
        this.c = b;
        this.d = b2;
        this.e = b3;
    }

    public String a() {
        return this.a;
    }

    public void setSsid(String str) {
        this.a = str;
    }

    public String b() {
        return this.b;
    }

    public void setBssid(String str) {
        this.b = str;
    }

    public byte c() {
        return this.c;
    }

    public void setRssi(byte b) {
        this.c = b;
    }

    public byte d() {
        return this.d;
    }

    public void setBand(byte b) {
        this.d = b;
    }

    public byte e() {
        return this.e;
    }

    public void setChannel(byte b) {
        this.e = b;
    }

    public cb f() {
        return new cb(this.a, this.b, this.c, this.d, this.e);
    }
}
