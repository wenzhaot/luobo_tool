package com.talkingdata.sdk;

/* compiled from: td */
public class dv extends dq {
    public static du a = null;
    private static final String c = "type";
    private static final String d = "deviceId";
    private static final String e = "runtimeConfig";
    private static final String f = "hardwareConfig";
    private static final String g = "softwareConfig";
    private dw h = new dw();
    private dt i = new dt();

    public dv() {
        d();
    }

    private void d() {
        a("type", (Object) "mobile");
        a = new du();
        a("deviceId", a.a_());
        a(e, new dx().a_());
        a(f, this.i.a_());
        a(g, this.h.a_());
    }

    public dw b() {
        return this.h;
    }

    public dt c() {
        return this.i;
    }
}
