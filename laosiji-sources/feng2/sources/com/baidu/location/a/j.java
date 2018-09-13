package com.baidu.location.a;

import android.location.Location;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.location.c.e;
import com.baidu.location.e.h;
import com.baidu.location.f.c;
import com.baidu.location.f.d;
import com.baidu.location.f.f;
import com.baidu.location.f.g;
import com.baidu.location.h.k;
import java.util.List;

public class j extends h {
    public static boolean h = false;
    private static j i = null;
    private double A;
    private boolean B;
    private long C;
    private long D;
    private a E;
    private boolean F;
    private boolean G;
    private boolean H;
    private boolean I;
    private boolean J;
    private b K;
    private boolean L;
    private int M;
    private long N;
    private boolean O;
    final int e;
    public b f;
    public final Handler g;
    private boolean j;
    private String k;
    private BDLocation l;
    private BDLocation m;
    private f n;
    private com.baidu.location.f.a o;
    private f p;
    private com.baidu.location.f.a q;
    private boolean r;
    private volatile boolean s;
    private boolean t;
    private long u;
    private long v;
    private Address w;
    private String x;
    private List<Poi> y;
    private double z;

    private class a implements Runnable {
        private a() {
        }

        public void run() {
            if (j.this.F) {
                j.this.F = false;
                if (!j.this.G && !d.a().j()) {
                    j.this.a(false, false);
                }
            }
        }
    }

    private class b implements Runnable {
        private b() {
        }

        public void run() {
            if (j.this.L) {
                j.this.L = false;
            }
            if (j.this.t) {
                j.this.t = false;
                j.this.h(null);
            }
        }
    }

    private j() {
        this.e = 1000;
        this.j = true;
        this.f = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = true;
        this.s = false;
        this.t = false;
        this.u = 0;
        this.v = 0;
        this.w = null;
        this.x = null;
        this.y = null;
        this.B = false;
        this.C = 0;
        this.D = 0;
        this.E = null;
        this.F = false;
        this.G = false;
        this.H = true;
        this.g = new com.baidu.location.a.h.a();
        this.I = false;
        this.J = false;
        this.K = null;
        this.L = false;
        this.M = 0;
        this.N = 0;
        this.O = true;
        this.f = new b();
    }

    private boolean a(com.baidu.location.f.a aVar) {
        this.b = com.baidu.location.f.b.a().f();
        return this.b == aVar ? false : this.b == null || aVar == null || !aVar.a(this.b);
    }

    private boolean a(f fVar) {
        this.a = g.a().p();
        return fVar == this.a ? false : this.a == null || fVar == null || !fVar.c(this.a);
    }

    private boolean b(com.baidu.location.f.a aVar) {
        return aVar == null ? false : this.q == null || !aVar.a(this.q);
    }

    public static synchronized j c() {
        j jVar;
        synchronized (j.class) {
            if (i == null) {
                i = new j();
            }
            jVar = i;
        }
        return jVar;
    }

    private void c(Message message) {
        boolean z = message.getData().getBoolean("isWaitingLocTag", false);
        if (z) {
            h = true;
        }
        if (z) {
        }
        if (!e.a().g()) {
            int d = a.a().d(message);
            k.a().d();
            switch (d) {
                case 1:
                    d(message);
                    return;
                case 2:
                    g(message);
                    return;
                case 3:
                    if (d.a().j()) {
                        e(message);
                        return;
                    }
                    return;
                default:
                    throw new IllegalArgumentException(String.format("this type %d is illegal", new Object[]{Integer.valueOf(d)}));
            }
        }
    }

    private void d(Message message) {
        if (d.a().j()) {
            e(message);
            k.a().c();
            return;
        }
        g(message);
        k.a().b();
    }

    private void e(Message message) {
        BDLocation bDLocation = new BDLocation(d.a().g());
        if (k.g.equals("all") || k.h || k.i) {
            float[] fArr = new float[2];
            Location.distanceBetween(this.A, this.z, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
            if (fArr[0] < 100.0f) {
                if (this.w != null) {
                    bDLocation.setAddr(this.w);
                }
                if (this.x != null) {
                    bDLocation.setLocationDescribe(this.x);
                }
                if (this.y != null) {
                    bDLocation.setPoiList(this.y);
                }
            } else {
                this.B = true;
                g(null);
            }
        }
        this.l = bDLocation;
        this.m = null;
        a.a().a(bDLocation);
    }

    private void f(Message message) {
        if (g.a().g()) {
            this.t = true;
            if (this.K == null) {
                this.K = new b();
            }
            if (this.L && this.K != null) {
                this.g.removeCallbacks(this.K);
            }
            this.g.postDelayed(this.K, 3500);
            this.L = true;
            return;
        }
        h(message);
    }

    private void g(Message message) {
        this.M = 0;
        if (this.r) {
            this.M = 1;
            this.D = SystemClock.uptimeMillis();
            if (g.a().k()) {
                f(message);
            } else {
                h(message);
            }
        } else if (!this.s) {
            f(message);
            this.D = SystemClock.uptimeMillis();
        }
    }

    private void h(Message message) {
        long j = 0;
        if (!this.s) {
            if (System.currentTimeMillis() - this.u <= 0 || System.currentTimeMillis() - this.u >= 1000) {
                long currentTimeMillis;
                this.s = true;
                this.j = a(this.o);
                if (!(a(this.n) || this.j || this.l == null || this.B)) {
                    if (this.m != null && System.currentTimeMillis() - this.v > 30000) {
                        this.l = this.m;
                        this.m = null;
                    }
                    if (k.a().g()) {
                        this.l.setDirection(k.a().i());
                    }
                    if (this.l.getLocType() == 62) {
                        currentTimeMillis = System.currentTimeMillis() - this.N;
                        if (currentTimeMillis > 0) {
                            j = currentTimeMillis;
                        }
                    }
                    if (this.l.getLocType() == 61 || this.l.getLocType() == BDLocation.TypeNetWorkLocation || (this.l.getLocType() == 62 && j < 15000)) {
                        a.a().a(this.l);
                        m();
                        return;
                    }
                }
                this.u = System.currentTimeMillis();
                String a = a(null);
                this.J = false;
                if (a == null) {
                    this.J = true;
                    this.N = System.currentTimeMillis();
                    String[] l = l();
                    currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - this.C > 60000) {
                        this.C = currentTimeMillis;
                    }
                    String m = g.a().m();
                    a = m != null ? m + b() + l[0] : "" + b() + l[0];
                    if (!(this.b == null || this.b.h() == null)) {
                        a = this.b.h() + a;
                    }
                    m = com.baidu.location.h.b.a().a(true);
                    if (m != null) {
                        a = a + m;
                    }
                }
                if (this.k != null) {
                    a = a + this.k;
                    this.k = null;
                }
                this.f.a(a);
                this.o = this.b;
                this.n = this.a;
                if (!d.a().j()) {
                    k();
                }
                if (h.a().h()) {
                    if (this.E == null) {
                        this.E = new a();
                    }
                    this.g.postDelayed(this.E, h.a().a(c.a(com.baidu.location.f.b.a().e())));
                    this.F = true;
                }
                if (this.r) {
                    this.r = false;
                    if (g.j() && message != null && a.a().e(message) < 1000 && h.a().d()) {
                        h.a().i();
                    }
                    com.baidu.location.b.b.a().b();
                }
                if (this.M > 0) {
                    if (this.M == 2) {
                        g.a().g();
                    }
                    this.M = 0;
                    return;
                }
                return;
            }
            if (this.l != null) {
                a.a().a(this.l);
            }
            m();
        }
    }

    private boolean k() {
        BDLocation bDLocation = null;
        double random = Math.random();
        SystemClock.uptimeMillis();
        com.baidu.location.f.a f = com.baidu.location.f.b.a().f();
        f o = g.a().o();
        long f2 = (o == null || o.a() <= 0) ? 0 : o.f();
        boolean z = f != null && f.e() && (o == null || o.a() == 0);
        if (h.a().d() && h.a().f() && f2 < 60 && (z || (0.0d < random && random < h.a().o()))) {
            BDLocation a = h.a().a(com.baidu.location.f.b.a().f(), g.a().o(), null, com.baidu.location.e.h.b.IS_MIX_MODE, com.baidu.location.e.h.a.NEED_TO_LOG);
            z = (k.g.equals("all") && a.getAddrStr() == null) ? false : true;
            if (k.h && a.getLocationDescribe() == null) {
                z = false;
            }
            if (k.i && a.getPoiList() == null) {
                z = false;
            }
            if (z) {
                bDLocation = a;
            }
        }
        if (bDLocation == null || bDLocation.getLocType() != 66 || !this.s) {
            return false;
        }
        BDLocation bDLocation2 = new BDLocation(bDLocation);
        bDLocation2.setLocType(BDLocation.TypeNetWorkLocation);
        if (!this.s) {
            return false;
        }
        this.G = true;
        a.a().a(bDLocation2);
        this.l = bDLocation2;
        return true;
    }

    private String[] l() {
        int c;
        int i;
        String[] strArr = new String[]{"", "Location failed beacuse we can not get any loc information!"};
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("&apl=");
        int b = k.b(com.baidu.location.f.getServiceContext());
        if (b == 1) {
            strArr[1] = "Location failed beacuse we can not get any loc information in airplane mode, you can turn it off and try again!!";
        }
        stringBuffer.append(b);
        String d = k.d(com.baidu.location.f.getServiceContext());
        if (d.contains("0|0|")) {
            strArr[1] = "Location failed beacuse we can not get any loc information without any location permission!";
        }
        stringBuffer.append(d);
        if (VERSION.SDK_INT >= 23) {
            stringBuffer.append("&loc=");
            c = k.c(com.baidu.location.f.getServiceContext());
            if (c == 0) {
                strArr[1] = "Location failed beacuse we can not get any loc information with the phone loc mode is off, you can turn it on and try again!";
                i = 1;
            } else {
                i = 0;
            }
            stringBuffer.append(c);
        } else {
            i = 0;
        }
        if (VERSION.SDK_INT >= 19) {
            stringBuffer.append("&lmd=");
            c = k.c(com.baidu.location.f.getServiceContext());
            if (c >= 0) {
                stringBuffer.append(c);
            }
        }
        String g = com.baidu.location.f.b.a().g();
        String h = g.a().h();
        stringBuffer.append(h);
        stringBuffer.append(g);
        stringBuffer.append(k.e(com.baidu.location.f.getServiceContext()));
        if (b == 1) {
            com.baidu.location.b.f.a().a(62, 7, "Location failed beacuse we can not get any loc information in airplane mode, you can turn it off and try again!!");
        } else if (d.contains("0|0|")) {
            com.baidu.location.b.f.a().a(62, 4, "Location failed beacuse we can not get any loc information without any location permission!");
        } else if (i != 0) {
            com.baidu.location.b.f.a().a(62, 5, "Location failed beacuse we can not get any loc information with the phone loc mode is off, you can turn it on and try again!");
        } else if (g == null || h == null || !g.equals("&sim=1") || h.equals("&wifio=1")) {
            com.baidu.location.b.f.a().a(62, 9, "Location failed beacuse we can not get any loc information!");
        } else {
            com.baidu.location.b.f.a().a(62, 6, "Location failed beacuse we can not get any loc information , you can insert a sim card or open wifi and try again!");
        }
        strArr[0] = stringBuffer.toString();
        return strArr;
    }

    private void m() {
        this.s = false;
        this.G = false;
        this.H = false;
        this.B = false;
        n();
        if (this.O) {
            this.O = false;
        }
    }

    private void n() {
        if (this.l != null) {
            s.a().c();
        }
    }

    public Address a(BDLocation bDLocation) {
        if (k.g.equals("all") || k.h || k.i) {
            float[] fArr = new float[2];
            Location.distanceBetween(this.A, this.z, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
            if (fArr[0] >= 100.0f) {
                this.x = null;
                this.y = null;
                this.B = true;
                g(null);
            } else if (this.w != null) {
                return this.w;
            }
        }
        return null;
    }

    public void a() {
        if (this.E != null && this.F) {
            this.F = false;
            this.g.removeCallbacks(this.E);
        }
        if (d.a().j()) {
            BDLocation bDLocation = new BDLocation(d.a().g());
            if (k.g.equals("all") || k.h || k.i) {
                float[] fArr = new float[2];
                Location.distanceBetween(this.A, this.z, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
                if (fArr[0] < 100.0f) {
                    if (this.w != null) {
                        bDLocation.setAddr(this.w);
                    }
                    if (this.x != null) {
                        bDLocation.setLocationDescribe(this.x);
                    }
                    if (this.y != null) {
                        bDLocation.setPoiList(this.y);
                    }
                }
            }
            a.a().a(bDLocation);
            m();
        } else if (this.G) {
            m();
        } else {
            BDLocation a;
            if (h.a().d() && h.a().e()) {
                a = h.a().a(com.baidu.location.f.b.a().f(), g.a().o(), null, com.baidu.location.e.h.b.IS_NOT_MIX_MODE, com.baidu.location.e.h.a.NEED_TO_LOG);
                if (a != null && a.getLocType() == 66) {
                    a.a().a(a);
                }
            } else {
                a = null;
            }
            if (a == null || a.getLocType() == 67) {
                if (this.j || this.l == null) {
                    BDLocation a2;
                    if (com.baidu.location.e.a.a().a) {
                        a2 = com.baidu.location.e.a.a().a(false);
                    } else if (a == null) {
                        a = new BDLocation();
                        a.setLocType(67);
                        a2 = a;
                    } else {
                        a2 = a;
                    }
                    if (a2 != null) {
                        a.a().a(a2);
                        if (a2.getLocType() == 67 && !this.J) {
                            com.baidu.location.b.f.a().a(67, 3, "Offline location failed, please check the net (wifi/cell)!");
                        }
                        boolean z = true;
                        if (k.g.equals("all") && a2.getAddrStr() == null) {
                            z = false;
                        }
                        if (k.h && a2.getLocationDescribe() == null) {
                            z = false;
                        }
                        if (k.i && a2.getPoiList() == null) {
                            z = false;
                        }
                        if (!z) {
                            a2.setLocType(67);
                        }
                    }
                } else {
                    a.a().a(this.l);
                }
            }
            this.m = null;
            m();
        }
    }

    public void a(Message message) {
        if (this.E != null && this.F) {
            this.F = false;
            this.g.removeCallbacks(this.E);
        }
        BDLocation bDLocation = (BDLocation) message.obj;
        if (bDLocation != null && bDLocation.getLocType() == BDLocation.TypeServerError && this.J) {
            bDLocation.setLocType(62);
        }
        b(bDLocation);
    }

    public void a(boolean z, boolean z2) {
        BDLocation bDLocation = null;
        if (h.a().d() && h.a().g()) {
            bDLocation = h.a().a(com.baidu.location.f.b.a().f(), g.a().o(), null, com.baidu.location.e.h.b.IS_NOT_MIX_MODE, com.baidu.location.e.h.a.NEED_TO_LOG);
            if ((bDLocation == null || bDLocation.getLocType() == 67) && z && com.baidu.location.e.a.a().a) {
                bDLocation = com.baidu.location.e.a.a().a(false);
            }
        } else if (z && com.baidu.location.e.a.a().a) {
            bDLocation = com.baidu.location.e.a.a().a(false);
        }
        if (bDLocation != null && bDLocation.getLocType() == 66) {
            boolean z3 = true;
            if (k.g.equals("all") && bDLocation.getAddrStr() == null) {
                z3 = false;
            }
            if (k.h && bDLocation.getLocationDescribe() == null) {
                z3 = false;
            }
            if (k.i && bDLocation.getPoiList() == null) {
                z3 = false;
            }
            if (z3 || z2) {
                a.a().a(bDLocation);
            }
        }
    }

    public void b(Message message) {
        if (this.I) {
            c(message);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:105:0x0249  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x023d  */
    public void b(com.baidu.location.BDLocation r12) {
        /*
        r11 = this;
        r6 = 2;
        r0 = 1;
        r2 = 0;
        r9 = 0;
        r8 = 161; // 0xa1 float:2.26E-43 double:7.95E-322;
        r3 = new com.baidu.location.BDLocation;
        r3.<init>(r12);
        r1 = r12.hasAddr();
        if (r1 == 0) goto L_0x0023;
    L_0x0011:
        r1 = r12.getAddress();
        r11.w = r1;
        r4 = r12.getLongitude();
        r11.z = r4;
        r4 = r12.getLatitude();
        r11.A = r4;
    L_0x0023:
        r1 = r12.getLocationDescribe();
        if (r1 == 0) goto L_0x003b;
    L_0x0029:
        r1 = r12.getLocationDescribe();
        r11.x = r1;
        r4 = r12.getLongitude();
        r11.z = r4;
        r4 = r12.getLatitude();
        r11.A = r4;
    L_0x003b:
        r1 = r12.getPoiList();
        if (r1 == 0) goto L_0x0053;
    L_0x0041:
        r1 = r12.getPoiList();
        r11.y = r1;
        r4 = r12.getLongitude();
        r11.z = r4;
        r4 = r12.getLatitude();
        r11.A = r4;
    L_0x0053:
        r1 = com.baidu.location.f.d.a();
        r1 = r1.j();
        if (r1 == 0) goto L_0x02a1;
    L_0x005d:
        r1 = r0;
    L_0x005e:
        if (r1 == 0) goto L_0x00bf;
    L_0x0060:
        r0 = com.baidu.location.f.d.a();
        r0 = r0.g();
        r10 = new com.baidu.location.BDLocation;
        r10.<init>(r0);
        r0 = com.baidu.location.h.k.g;
        r1 = "all";
        r0 = r0.equals(r1);
        if (r0 != 0) goto L_0x0080;
    L_0x0078:
        r0 = com.baidu.location.h.k.h;
        if (r0 != 0) goto L_0x0080;
    L_0x007c:
        r0 = com.baidu.location.h.k.i;
        if (r0 == 0) goto L_0x00b4;
    L_0x0080:
        r8 = new float[r6];
        r0 = r11.A;
        r2 = r11.z;
        r4 = r10.getLatitude();
        r6 = r10.getLongitude();
        android.location.Location.distanceBetween(r0, r2, r4, r6, r8);
        r0 = r8[r9];
        r1 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1));
        if (r0 >= 0) goto L_0x00b4;
    L_0x0099:
        r0 = r11.w;
        if (r0 == 0) goto L_0x00a2;
    L_0x009d:
        r0 = r11.w;
        r10.setAddr(r0);
    L_0x00a2:
        r0 = r11.x;
        if (r0 == 0) goto L_0x00ab;
    L_0x00a6:
        r0 = r11.x;
        r10.setLocationDescribe(r0);
    L_0x00ab:
        r0 = r11.y;
        if (r0 == 0) goto L_0x00b4;
    L_0x00af:
        r0 = r11.y;
        r10.setPoiList(r0);
    L_0x00b4:
        r0 = com.baidu.location.a.a.a();
        r0.a(r10);
        r11.m();
    L_0x00be:
        return;
    L_0x00bf:
        r1 = r11.G;
        if (r1 == 0) goto L_0x010c;
    L_0x00c3:
        r8 = new float[r6];
        r0 = r11.l;
        if (r0 == 0) goto L_0x00e0;
    L_0x00c9:
        r0 = r11.l;
        r0 = r0.getLatitude();
        r2 = r11.l;
        r2 = r2.getLongitude();
        r4 = r12.getLatitude();
        r6 = r12.getLongitude();
        android.location.Location.distanceBetween(r0, r2, r4, r6, r8);
    L_0x00e0:
        r0 = r8[r9];
        r1 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1));
        if (r0 <= 0) goto L_0x00fb;
    L_0x00e8:
        r11.l = r12;
        r0 = r11.H;
        if (r0 != 0) goto L_0x00f7;
    L_0x00ee:
        r11.H = r9;
        r0 = com.baidu.location.a.a.a();
        r0.a(r12);
    L_0x00f7:
        r11.m();
        goto L_0x00be;
    L_0x00fb:
        r0 = r12.getUserIndoorState();
        r1 = -1;
        if (r0 <= r1) goto L_0x00f7;
    L_0x0102:
        r11.l = r12;
        r0 = com.baidu.location.a.a.a();
        r0.a(r12);
        goto L_0x00f7;
    L_0x010c:
        r1 = r12.getLocType();
        r4 = 167; // 0xa7 float:2.34E-43 double:8.25E-322;
        if (r1 != r4) goto L_0x0222;
    L_0x0114:
        r1 = com.baidu.location.b.f.a();
        r4 = 167; // 0xa7 float:2.34E-43 double:8.25E-322;
        r5 = 8;
        r6 = "NetWork location failed because baidu location service can not caculate the location!";
        r1.a(r4, r5, r6);
    L_0x0122:
        r11.m = r2;
        r1 = r12.getLocType();
        if (r1 != r8) goto L_0x029c;
    L_0x012a:
        r1 = "cl";
        r4 = r12.getNetworkLocationType();
        r1 = r1.equals(r4);
        if (r1 == 0) goto L_0x029c;
    L_0x0137:
        r1 = r11.l;
        if (r1 == 0) goto L_0x029c;
    L_0x013b:
        r1 = r11.l;
        r1 = r1.getLocType();
        if (r1 != r8) goto L_0x029c;
    L_0x0143:
        r1 = "wf";
        r4 = r11.l;
        r4 = r4.getNetworkLocationType();
        r1 = r1.equals(r4);
        if (r1 == 0) goto L_0x029c;
    L_0x0152:
        r4 = java.lang.System.currentTimeMillis();
        r6 = r11.v;
        r4 = r4 - r6;
        r6 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r1 >= 0) goto L_0x029c;
    L_0x015f:
        r11.m = r12;
    L_0x0161:
        if (r0 == 0) goto L_0x0285;
    L_0x0163:
        r1 = com.baidu.location.a.a.a();
        r4 = r11.l;
        r1.a(r4);
    L_0x016c:
        r1 = com.baidu.location.h.k.a(r12);
        if (r1 == 0) goto L_0x0294;
    L_0x0172:
        if (r0 != 0) goto L_0x0176;
    L_0x0174:
        r11.l = r12;
    L_0x0176:
        r0 = c;
        r1 = "ssid\":\"";
        r4 = "\"";
        r0 = com.baidu.location.h.k.a(r0, r1, r4);
        r1 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        if (r0 == r1) goto L_0x0298;
    L_0x0186:
        r1 = r11.n;
        if (r1 == 0) goto L_0x0298;
    L_0x018a:
        r1 = r11.n;
        r0 = r1.d(r0);
        r11.k = r0;
    L_0x0192:
        r0 = com.baidu.location.e.h.a();
        r0 = r0.d();
        if (r0 == 0) goto L_0x01c8;
    L_0x019c:
        r0 = r12.getLocType();
        if (r0 != r8) goto L_0x01c8;
    L_0x01a2:
        r0 = "cl";
        r1 = r12.getNetworkLocationType();
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x01c8;
    L_0x01af:
        r0 = r11.o;
        r0 = r11.b(r0);
        if (r0 == 0) goto L_0x01c8;
    L_0x01b7:
        r0 = com.baidu.location.e.h.a();
        r1 = r11.o;
        r4 = com.baidu.location.e.h.b.IS_NOT_MIX_MODE;
        r5 = com.baidu.location.e.h.a.NO_NEED_TO_LOG;
        r0.a(r1, r2, r3, r4, r5);
        r0 = r11.o;
        r11.q = r0;
    L_0x01c8:
        r0 = com.baidu.location.e.h.a();
        r0 = r0.d();
        if (r0 == 0) goto L_0x01f8;
    L_0x01d2:
        r0 = r12.getLocType();
        if (r0 != r8) goto L_0x01f8;
    L_0x01d8:
        r0 = "wf";
        r1 = r12.getNetworkLocationType();
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x01f8;
    L_0x01e5:
        r4 = com.baidu.location.e.h.a();
        r6 = r11.n;
        r8 = com.baidu.location.e.h.b.IS_NOT_MIX_MODE;
        r9 = com.baidu.location.e.h.a.NO_NEED_TO_LOG;
        r5 = r2;
        r7 = r3;
        r4.a(r5, r6, r7, r8, r9);
        r0 = r11.n;
        r11.p = r0;
    L_0x01f8:
        r0 = r11.o;
        if (r0 == 0) goto L_0x0209;
    L_0x01fc:
        r0 = com.baidu.location.e.a.a();
        r1 = c;
        r2 = r11.o;
        r4 = r11.n;
        r0.a(r1, r2, r4, r3);
    L_0x0209:
        r0 = com.baidu.location.f.g.j();
        if (r0 == 0) goto L_0x021d;
    L_0x020f:
        r0 = com.baidu.location.e.h.a();
        r0.i();
        r0 = com.baidu.location.e.h.a();
        r0.m();
    L_0x021d:
        r11.m();
        goto L_0x00be;
    L_0x0222:
        r1 = r12.getLocType();
        if (r1 != r8) goto L_0x0122;
    L_0x0228:
        r1 = android.os.Build.VERSION.SDK_INT;
        r4 = 19;
        if (r1 < r4) goto L_0x029f;
    L_0x022e:
        r1 = com.baidu.location.f.getServiceContext();
        r1 = com.baidu.location.h.k.c(r1);
        if (r1 == 0) goto L_0x023a;
    L_0x0238:
        if (r1 != r6) goto L_0x029f;
    L_0x023a:
        r1 = r0;
    L_0x023b:
        if (r1 == 0) goto L_0x0249;
    L_0x023d:
        r1 = com.baidu.location.b.f.a();
        r4 = "NetWork location successful, open gps will be better!";
        r1.a(r8, r0, r4);
        goto L_0x0122;
    L_0x0249:
        r1 = r12.getRadius();
        r4 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1));
        if (r1 < 0) goto L_0x0122;
    L_0x0253:
        r1 = r12.getNetworkLocationType();
        if (r1 == 0) goto L_0x0122;
    L_0x0259:
        r1 = r12.getNetworkLocationType();
        r4 = "cl";
        r1 = r1.equals(r4);
        if (r1 == 0) goto L_0x0122;
    L_0x0266:
        r1 = com.baidu.location.f.g.a();
        r1 = r1.h();
        if (r1 == 0) goto L_0x0122;
    L_0x0270:
        r4 = "&wifio=1";
        r1 = r1.equals(r4);
        if (r1 != 0) goto L_0x0122;
    L_0x0279:
        r1 = com.baidu.location.b.f.a();
        r4 = "NetWork location successful, open wifi will be better!";
        r1.a(r8, r6, r4);
        goto L_0x0122;
    L_0x0285:
        r1 = com.baidu.location.a.a.a();
        r1.a(r12);
        r4 = java.lang.System.currentTimeMillis();
        r11.v = r4;
        goto L_0x016c;
    L_0x0294:
        r11.l = r2;
        goto L_0x0176;
    L_0x0298:
        r11.k = r2;
        goto L_0x0192;
    L_0x029c:
        r0 = r9;
        goto L_0x0161;
    L_0x029f:
        r1 = r9;
        goto L_0x023b;
    L_0x02a1:
        r1 = r9;
        goto L_0x005e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.a.j.b(com.baidu.location.BDLocation):void");
    }

    public void c(BDLocation bDLocation) {
        j();
        this.l = bDLocation;
        this.l.setIndoorLocMode(false);
    }

    public void d() {
        this.r = true;
        this.s = false;
        this.I = true;
    }

    public void d(BDLocation bDLocation) {
        this.l = new BDLocation(bDLocation);
    }

    public void e() {
        this.s = false;
        this.t = false;
        this.G = false;
        this.H = true;
        j();
        this.I = false;
    }

    public String f() {
        return this.x;
    }

    public List<Poi> g() {
        return this.y;
    }

    public boolean h() {
        return this.j;
    }

    public void i() {
        if (this.t) {
            h(null);
            this.t = false;
            return;
        }
        com.baidu.location.b.b.a().d();
    }

    public void j() {
        this.l = null;
    }
}
