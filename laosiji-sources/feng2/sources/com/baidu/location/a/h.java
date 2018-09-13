package com.baidu.location.a;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.f.f;
import com.baidu.location.f.g;
import com.baidu.location.h.k;
import java.util.HashMap;
import java.util.Locale;

public abstract class h {
    public static String c = null;
    public f a = null;
    public com.baidu.location.f.a b = null;
    final Handler d = new a();
    private boolean e = true;
    private boolean f = true;
    private boolean g = false;
    private String h = null;
    private String i = null;

    public class a extends Handler {
        public void handleMessage(Message message) {
            if (com.baidu.location.f.isServing) {
                switch (message.what) {
                    case 21:
                        h.this.a(message);
                        return;
                    case 62:
                    case 63:
                        h.this.a();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    class b extends com.baidu.location.h.f {
        String a;
        String b;

        public b() {
            this.a = null;
            this.b = null;
            this.k = new HashMap();
        }

        public void a() {
            this.h = k.c();
            if (!((!k.h && !k.i) || h.this.h == null || h.this.i == null)) {
                this.b += String.format(Locale.CHINA, "&ki=%s&sn=%s", new Object[]{h.this.h, h.this.i});
            }
            String encodeTp4 = Jni.encodeTp4(this.b);
            this.b = null;
            if (this.a == null) {
                this.a = s.b();
            }
            this.k.put("bloc", encodeTp4);
            if (this.a != null) {
                this.k.put("up", this.a);
            }
            this.k.put("trtm", String.format(Locale.CHINA, "%d", new Object[]{Long.valueOf(System.currentTimeMillis())}));
        }

        public void a(String str) {
            this.b = str;
            c(k.f);
        }

        public void a(boolean z) {
            Message obtainMessage;
            if (!z || this.j == null) {
                obtainMessage = h.this.d.obtainMessage(63);
                obtainMessage.obj = "HttpStatus error";
                obtainMessage.sendToTarget();
            } else {
                try {
                    BDLocation bDLocation;
                    String str = this.j;
                    h.c = str;
                    try {
                        bDLocation = new BDLocation(str);
                        if (bDLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                            g.a().a(str);
                        }
                        bDLocation.setOperators(com.baidu.location.f.b.a().h());
                        if (k.a().g()) {
                            bDLocation.setDirection(k.a().i());
                        }
                    } catch (Exception e) {
                        bDLocation = new BDLocation();
                        bDLocation.setLocType(0);
                    }
                    this.a = null;
                    if (bDLocation.getLocType() == 0 && bDLocation.getLatitude() == Double.MIN_VALUE && bDLocation.getLongitude() == Double.MIN_VALUE) {
                        obtainMessage = h.this.d.obtainMessage(63);
                        obtainMessage.obj = "HttpStatus error";
                        obtainMessage.sendToTarget();
                    } else {
                        Message obtainMessage2 = h.this.d.obtainMessage(21);
                        obtainMessage2.obj = bDLocation;
                        obtainMessage2.sendToTarget();
                    }
                } catch (Exception e2) {
                    obtainMessage = h.this.d.obtainMessage(63);
                    obtainMessage.obj = "HttpStatus error";
                    obtainMessage.sendToTarget();
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:46:0x00fc  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00f0  */
    public java.lang.String a(java.lang.String r7) {
        /*
        r6 = this;
        r0 = 0;
        r4 = 0;
        r1 = r6.h;
        if (r1 != 0) goto L_0x0010;
    L_0x0006:
        r1 = com.baidu.location.f.getServiceContext();
        r1 = com.baidu.location.a.i.b(r1);
        r6.h = r1;
    L_0x0010:
        r1 = r6.i;
        if (r1 != 0) goto L_0x001e;
    L_0x0014:
        r1 = com.baidu.location.f.getServiceContext();
        r1 = com.baidu.location.a.i.c(r1);
        r6.i = r1;
    L_0x001e:
        r1 = r6.b;
        if (r1 == 0) goto L_0x002a;
    L_0x0022:
        r1 = r6.b;
        r1 = r1.a();
        if (r1 != 0) goto L_0x0034;
    L_0x002a:
        r1 = com.baidu.location.f.b.a();
        r1 = r1.f();
        r6.b = r1;
    L_0x0034:
        r1 = r6.a;
        if (r1 == 0) goto L_0x0040;
    L_0x0038:
        r1 = r6.a;
        r1 = r1.i();
        if (r1 != 0) goto L_0x004a;
    L_0x0040:
        r1 = com.baidu.location.f.g.a();
        r1 = r1.p();
        r6.a = r1;
    L_0x004a:
        r1 = com.baidu.location.f.d.a();
        r1 = r1.j();
        if (r1 == 0) goto L_0x0108;
    L_0x0054:
        r1 = com.baidu.location.f.d.a();
        r2 = r1.h();
    L_0x005c:
        r1 = r6.b;
        if (r1 == 0) goto L_0x0070;
    L_0x0060:
        r1 = r6.b;
        r1 = r1.d();
        if (r1 != 0) goto L_0x0070;
    L_0x0068:
        r1 = r6.b;
        r1 = r1.c();
        if (r1 == 0) goto L_0x007f;
    L_0x0070:
        r1 = r6.a;
        if (r1 == 0) goto L_0x007c;
    L_0x0074:
        r1 = r6.a;
        r1 = r1.a();
        if (r1 != 0) goto L_0x007f;
    L_0x007c:
        if (r2 != 0) goto L_0x007f;
    L_0x007e:
        return r0;
    L_0x007f:
        r0 = r6.b();
        r1 = com.baidu.location.a.g.a();
        r1 = r1.d();
        r3 = -2;
        if (r1 != r3) goto L_0x00a2;
    L_0x008e:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r0 = r1.append(r0);
        r1 = "&imo=1";
        r0 = r0.append(r1);
        r0 = r0.toString();
    L_0x00a2:
        r1 = com.baidu.location.f.getServiceContext();
        r1 = com.baidu.location.h.k.c(r1);
        if (r1 < 0) goto L_0x00c4;
    L_0x00ac:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r0 = r3.append(r0);
        r3 = "&lmd=";
        r0 = r0.append(r3);
        r0 = r0.append(r1);
        r0 = r0.toString();
    L_0x00c4:
        r1 = r6.a;
        if (r1 == 0) goto L_0x00d0;
    L_0x00c8:
        r1 = r6.a;
        r1 = r1.a();
        if (r1 != 0) goto L_0x0106;
    L_0x00d0:
        r1 = com.baidu.location.f.g.a();
        r1 = r1.m();
        if (r1 == 0) goto L_0x0106;
    L_0x00da:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r1 = r3.append(r1);
        r0 = r1.append(r0);
        r0 = r0.toString();
        r3 = r0;
    L_0x00ec:
        r0 = r6.f;
        if (r0 == 0) goto L_0x00fc;
    L_0x00f0:
        r6.f = r4;
        r0 = r6.b;
        r1 = r6.a;
        r5 = 1;
        r0 = com.baidu.location.h.k.a(r0, r1, r2, r3, r4, r5);
        goto L_0x007e;
    L_0x00fc:
        r0 = r6.b;
        r1 = r6.a;
        r0 = com.baidu.location.h.k.a(r0, r1, r2, r3, r4);
        goto L_0x007e;
    L_0x0106:
        r3 = r0;
        goto L_0x00ec;
    L_0x0108:
        r2 = r0;
        goto L_0x005c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.a.h.a(java.lang.String):java.lang.String");
    }

    public abstract void a();

    public abstract void a(Message message);

    public String b() {
        String str;
        String e = a.a().e();
        if (g.j()) {
            str = "&cn=32";
        } else {
            str = String.format(Locale.CHINA, "&cn=%d", new Object[]{Integer.valueOf(com.baidu.location.f.b.a().e())});
        }
        String s;
        if (this.e) {
            this.e = false;
            s = g.a().s();
            if (!(TextUtils.isEmpty(s) || s.equals("02:00:00:00:00:00"))) {
                s = s.replace(":", "");
                str = String.format(Locale.CHINA, "%s&mac=%s", new Object[]{str, s});
            }
            if (VERSION.SDK_INT > 17) {
            }
        } else if (!this.g) {
            s = s.f();
            if (s != null) {
                str = str + s;
            }
            this.g = true;
        }
        return str + e;
    }
}
