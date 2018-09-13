package com.baidu.location.c;

import android.location.Location;
import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.Message;
import android.support.graphics.drawable.PathInterpolatorCompat;
import android.util.Log;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Jni;
import com.baidu.location.a.j;
import com.baidu.location.h.k;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class e {
    private static e j;
    private i A;
    private String B;
    private String C;
    private String D;
    private int E;
    private boolean F;
    private int G;
    private c<String> H;
    private int I;
    private c<String> J;
    private double K;
    private double L;
    private double M;
    private double N;
    private boolean O;
    private boolean P;
    private List<g> Q;
    private int R;
    private int S;
    private int T;
    private a U;
    private String V;
    private d W;
    private m X;
    private com.baidu.location.c.m.a Y;
    private boolean Z;
    boolean a;
    private int aa;
    private BDLocation ab;
    private boolean ac;
    private boolean ad;
    private boolean ae;
    private List<Float> af;
    boolean b;
    public e c;
    public SimpleDateFormat d;
    private final int e;
    private boolean f;
    private BDLocationListener g;
    private BDLocationListener h;
    private int i;
    private long k;
    private volatile boolean l;
    private j m;
    private f n;
    private h o;
    private long p;
    private boolean q;
    private boolean r;
    private long s;
    private int t;
    private int u;
    private com.baidu.location.c.j.a v;
    private int w;
    private int x;
    private String y;
    private String z;

    class a {
        private HashMap<String, Integer> b = new HashMap();
        private double c = 0.0d;

        public a(com.baidu.location.f.f fVar) {
            if (fVar.a != null) {
                for (ScanResult scanResult : fVar.a) {
                    int abs = Math.abs(scanResult.level);
                    this.b.put(scanResult.BSSID, Integer.valueOf(abs));
                    this.c = ((double) ((100 - abs) * (100 - abs))) + this.c;
                }
                this.c = Math.sqrt(this.c + 1.0d);
            }
        }

        double a(a aVar) {
            double d = 0.0d;
            for (String str : this.b.keySet()) {
                int intValue = ((Integer) this.b.get(str)).intValue();
                Integer num = (Integer) aVar.a().get(str);
                if (num != null) {
                    d = ((double) ((100 - num.intValue()) * (100 - intValue))) + d;
                }
            }
            return d / (this.c * aVar.b());
        }

        public HashMap<String, Integer> a() {
            return this.b;
        }

        public double b() {
            return this.c;
        }
    }

    class b {
        double a;
        double b;
        long c;
        int d;
        List<Float> e;
        boolean f;
        String g;
        String h;
        String i;
        boolean j = false;

        public b(double d, double d2, long j, int i, List<Float> list, String str, String str2, String str3) {
            this.a = d;
            this.b = d2;
            this.c = j;
            this.d = i;
            this.f = false;
            this.e = new ArrayList(list);
            this.g = str;
            this.h = str2;
            this.i = str3;
        }

        public double a() {
            return this.a;
        }

        public int a(b bVar) {
            return Math.abs(this.d - bVar.c());
        }

        public void a(double d) {
            this.a = d;
        }

        public void a(boolean z) {
            this.f = z;
        }

        public double b() {
            return this.b;
        }

        public float b(b bVar) {
            float[] fArr = new float[1];
            Location.distanceBetween(this.b, this.a, bVar.b, bVar.a, fArr);
            return fArr[0];
        }

        public void b(double d) {
            this.b = d;
        }

        public int c() {
            return this.d;
        }

        public boolean c(b bVar) {
            int a = a(bVar);
            return a != 0 && ((double) (b(bVar) / ((float) a))) <= 1.0d + (0.5d * Math.pow(1.2d, (double) (1 - a)));
        }

        public boolean d() {
            return this.f;
        }

        public Double e() {
            return this.g == null ? null : Double.valueOf(Double.parseDouble(this.g));
        }

        public Double f() {
            return this.h == null ? null : Double.valueOf(Double.parseDouble(this.h));
        }

        public Double g() {
            return this.i == null ? null : Double.valueOf(Double.parseDouble(this.i));
        }
    }

    class c {
        private b[] b;
        private int c;
        private int d;

        public c(e eVar) {
            this(5);
        }

        public c(int i) {
            this.b = new b[(i + 1)];
            this.c = 0;
            this.d = 0;
        }

        public b a() {
            return this.b[((this.d - 1) + this.b.length) % this.b.length];
        }

        public b a(int i) {
            return this.b[(((this.d - 1) - i) + this.b.length) % this.b.length];
        }

        public void a(b bVar) {
            if (this.c != this.d) {
                b a = a();
                if (a.c() == bVar.c()) {
                    a.a((a.a() + bVar.a()) / 2.0d);
                    a.b((a.b() + bVar.b()) / 2.0d);
                    return;
                }
            }
            if (b()) {
                d();
            }
            b(bVar);
        }

        public boolean b() {
            return (this.d + 1) % this.b.length == this.c;
        }

        public boolean b(b bVar) {
            if (b()) {
                return false;
            }
            this.b[this.d] = bVar;
            this.d = (this.d + 1) % this.b.length;
            return true;
        }

        public boolean c() {
            return this.d == this.c;
        }

        public boolean c(b bVar) {
            if (c()) {
                return true;
            }
            if (bVar.c(a())) {
                return true;
            }
            if (a().d()) {
                return false;
            }
            for (int i = 0; i < e(); i++) {
                b a = a(i);
                if (a.d() && a.c(bVar)) {
                    return true;
                }
            }
            return false;
        }

        public boolean d() {
            if (this.c == this.d) {
                return false;
            }
            this.c = (this.c + 1) % this.b.length;
            return true;
        }

        public int e() {
            return ((this.d - this.c) + this.b.length) % this.b.length;
        }

        public String toString() {
            int i;
            int i2 = 0;
            String str = "";
            for (i = 0; i < e(); i++) {
                str = str + this.b[(this.c + i) % this.b.length].a + ",";
            }
            str = str + "  ";
            for (i = 0; i < e(); i++) {
                str = str + this.b[(this.c + i) % this.b.length].b + ",";
            }
            String str2 = str + "  ";
            while (i2 < e()) {
                str2 = str2 + this.b[(this.c + i2) % this.b.length].d + ",";
                i2++;
            }
            return str2 + "  ";
        }
    }

    class d {
        private b[] b;
        private int c;
        private int d;

        public d(e eVar) {
            this(5);
        }

        public d(int i) {
            this.b = new b[(i + 1)];
            this.c = 0;
            this.d = 0;
        }

        public b a() {
            return this.b[((this.d - 1) + this.b.length) % this.b.length];
        }

        public boolean a(b bVar) {
            if (bVar.g() == null || bVar.f() == null) {
                return false;
            }
            double doubleValue = bVar.g().doubleValue();
            if (bVar.f().doubleValue() > 1.0d && doubleValue > 8.0d) {
                return false;
            }
            if (d()) {
                return true;
            }
            b a = a();
            double doubleValue2 = a.e().doubleValue();
            double doubleValue3 = bVar.e().doubleValue();
            doubleValue = com.baidu.location.h.e.a(a.e);
            double a2 = com.baidu.location.h.e.a(bVar.e);
            doubleValue2 = com.baidu.location.h.e.a(doubleValue2, doubleValue3);
            doubleValue = com.baidu.location.h.e.b(doubleValue, a2);
            doubleValue3 = Math.abs(Math.abs(doubleValue2) - Math.abs(doubleValue));
            if (Math.abs(doubleValue) <= 15.0d) {
                return Math.abs(doubleValue2) <= Math.abs(doubleValue) * 2.0d && doubleValue3 <= 20.0d;
            } else {
                e.this.o.t.g();
                return false;
            }
        }

        public float b() {
            if (f() < 4) {
                return 0.0f;
            }
            List arrayList = new ArrayList();
            int i = 2;
            while (true) {
                int i2 = i;
                if (i2 > f()) {
                    return (float) com.baidu.location.h.e.a(arrayList);
                }
                b bVar = this.b[(((this.d - i2) + 1) + this.b.length) % this.b.length];
                b bVar2 = this.b[((this.d - i2) + this.b.length) % this.b.length];
                double b = com.baidu.location.h.e.b(bVar2.b, bVar2.a, bVar.b, bVar.a);
                double toDegrees = 90.0d - Math.toDegrees(Math.atan(bVar.e().doubleValue()));
                if (Math.abs(com.baidu.location.h.e.b(toDegrees, b)) >= Math.abs(com.baidu.location.h.e.b(toDegrees + 180.0d, b))) {
                    toDegrees += 180.0d;
                }
                arrayList.add(Float.valueOf((float) com.baidu.location.h.e.b(com.baidu.location.h.e.a(bVar.e), toDegrees)));
                i = i2 + 1;
            }
        }

        public boolean b(b bVar) {
            if (c()) {
                e();
            }
            return c(bVar);
        }

        public boolean c() {
            return (this.d + 1) % this.b.length == this.c;
        }

        public boolean c(b bVar) {
            if (c()) {
                return false;
            }
            this.b[this.d] = bVar;
            this.d = (this.d + 1) % this.b.length;
            return true;
        }

        public boolean d() {
            return this.d == this.c;
        }

        public boolean e() {
            if (this.c == this.d) {
                return false;
            }
            this.c = (this.c + 1) % this.b.length;
            return true;
        }

        public int f() {
            return ((this.d - this.c) + this.b.length) % this.b.length;
        }

        public void g() {
            this.d = 0;
            this.c = 0;
        }

        public String toString() {
            int i;
            int i2 = 0;
            String str = "";
            for (i = 0; i < f(); i++) {
                str = str + this.b[(this.c + i) % this.b.length].a + ",";
            }
            str = str + "  ";
            for (i = 0; i < f(); i++) {
                str = str + this.b[(this.c + i) % this.b.length].b + ",";
            }
            String str2 = str + "  ";
            while (i2 < f()) {
                str2 = str2 + this.b[(this.c + i2) % this.b.length].d + ",";
                i2++;
            }
            return str2 + "  ";
        }
    }

    public class e extends Handler {
        public void handleMessage(Message message) {
            if (com.baidu.location.f.isServing) {
                switch (message.what) {
                    case 21:
                        e.this.a(message);
                        return;
                    case 28:
                        e.this.b(message);
                        return;
                    case 41:
                        e.this.l();
                        return;
                    case 801:
                        e.this.a((BDLocation) message.obj);
                        return;
                    default:
                        super.dispatchMessage(message);
                        return;
                }
            }
        }
    }

    class f extends Thread {
        private volatile boolean b = true;
        private long c = 0;

        f() {
        }

        public void run() {
            while (this.b) {
                if ((((e.this.l && System.currentTimeMillis() - this.c > e.this.k) || System.currentTimeMillis() - this.c > 10000) && e.this.m.c() == 1) || System.currentTimeMillis() - this.c > 17500) {
                    com.baidu.location.f.g.a().i();
                    e.this.m.e();
                    this.c = System.currentTimeMillis();
                    e.this.l = false;
                } else if (e.this.m.c() != 1) {
                    com.baidu.location.a.a.a().c();
                }
                if (System.currentTimeMillis() - e.this.p > 22000) {
                    e.this.c.sendEmptyMessage(41);
                }
                if (System.currentTimeMillis() - e.this.s > 60000) {
                    e.a().d();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    this.b = false;
                    return;
                }
            }
        }
    }

    private class g {
        public int a;
        public double b;
        public double c;
        public int d = 1;

        public g(int i, double d, double d2) {
            this.a = i;
            this.b = d;
            this.c = d2;
        }

        public String toString() {
            return String.format("%d:%.2f", new Object[]{Integer.valueOf(this.d), Double.valueOf(this.c)});
        }
    }

    class h extends com.baidu.location.h.f {
        public float a;
        private boolean c;
        private boolean d;
        private String e;
        private String f;
        private List<Float> p;
        private a q;
        private c r;
        private d s;
        private d t;
        private int u;
        private long v;
        private long w;

        public h() {
            this.c = false;
            this.d = false;
            this.e = null;
            this.f = null;
            this.p = new ArrayList();
            this.q = null;
            this.r = null;
            this.s = null;
            this.t = null;
            this.a = 0.0f;
            this.u = -1;
            this.v = 0;
            this.w = 0;
            this.k = new HashMap();
            this.r = new c(e.this);
            this.s = new d(e.this);
            this.t = new d(6);
        }

        private boolean a(com.baidu.location.f.f fVar, double d) {
            a aVar = new a(fVar);
            if (this.q != null && aVar.a(this.q) > d) {
                return false;
            }
            this.q = aVar;
            return true;
        }

        public void a() {
            this.h = k.c();
            if (e.this.z == null || e.this.A == null || !e.this.z.equals(e.this.A.a())) {
                this.e = "&nd_idf=1&indoor_polygon=1" + this.e;
            }
            this.i = 1;
            String encodeTp4 = Jni.encodeTp4(this.e);
            this.e = null;
            this.k.put("bloc", encodeTp4);
            this.v = System.currentTimeMillis();
        }

        public void a(boolean z) {
            if (!z || this.j == null) {
                e.this.t = e.this.t + 1;
                e.this.aa = 0;
                e.this.Z = true;
                this.c = false;
                if (e.this.t > 40) {
                    e.this.d();
                } else {
                    return;
                }
            }
            try {
                String str = this.j;
                if (e.this.q) {
                    BDLocation bDLocation = new BDLocation(str);
                    if (!(bDLocation == null || bDLocation.getLocType() != BDLocation.TypeNetWorkLocation || bDLocation.getBuildingID() == null)) {
                        e.this.ab = new BDLocation(bDLocation);
                    }
                    e.this.Z = false;
                    str = bDLocation.getIndoorLocationSurpportBuidlingName();
                    if (str == null) {
                        Log.w(com.baidu.location.h.a.a, "inbldg is null");
                    } else if (!e.this.U.a(str)) {
                        e.this.U.a(str, null);
                    }
                    com.baidu.location.a.k.a().c(true);
                    com.baidu.location.a.k.a().d();
                    if (e.this.m.d() == -1) {
                        e.this.b = false;
                    }
                    if (bDLocation.getBuildingName() != null) {
                        e.this.C = bDLocation.getBuildingName();
                    }
                    if (bDLocation.getFloor() != null) {
                        e.this.s = System.currentTimeMillis();
                        this.w = System.currentTimeMillis();
                        int i = (int) (this.w - this.v);
                        if (i > 10000) {
                            e.this.aa = 0;
                        } else if (i < PathInterpolatorCompat.MAX_NUM_POINTS) {
                            e.this.aa = 2;
                        } else {
                            e.this.aa = 1;
                        }
                        if (bDLocation.getFloor().contains("-a")) {
                            e.this.O = true;
                            bDLocation.setFloor(bDLocation.getFloor().split("-")[0]);
                        } else {
                            e.this.O = false;
                        }
                        e.this.H.add(bDLocation.getFloor());
                    }
                    if (e.this.a && e.this.b) {
                        b bVar = new b(bDLocation.getLongitude(), bDLocation.getLatitude(), System.currentTimeMillis(), e.this.m.d(), this.p, bDLocation.getRetFields("gradient"), bDLocation.getRetFields("mean_error"), bDLocation.getRetFields("confidence"));
                        if (this.r.c(bVar)) {
                            bVar.a(true);
                            Message obtainMessage = e.this.c.obtainMessage(21);
                            obtainMessage.obj = bDLocation;
                            obtainMessage.sendToTarget();
                        } else {
                            e.this.n();
                        }
                        if (bDLocation.getFloor() != null) {
                            this.r.a(bVar);
                        }
                    } else {
                        Message obtainMessage2 = e.this.c.obtainMessage(21);
                        obtainMessage2.obj = bDLocation;
                        obtainMessage2.sendToTarget();
                    }
                } else {
                    this.c = false;
                    return;
                }
            } catch (Exception e) {
            }
            if (this.k != null) {
                this.k.clear();
            }
            this.c = false;
        }

        public void b() {
            if (this.c) {
                this.d = true;
                return;
            }
            StringBuffer stringBuffer = new StringBuffer(1024);
            String h = com.baidu.location.f.b.a().f().h();
            String f = com.baidu.location.f.d.a().f();
            e.this.M = 0.5d;
            com.baidu.location.f.f q = com.baidu.location.f.g.a().q();
            String a = e.this.a(q);
            String a2 = a == null ? q.a(32) : a;
            if (a2 != null && a2.length() >= 10) {
                if (this.f == null || !this.f.equals(a2)) {
                    this.f = a2;
                    int d = e.this.m.d();
                    boolean z = false;
                    if (this.u < 0 || d - this.u > e.this.i) {
                        z = true;
                    }
                    if (e.this.a && e.this.b) {
                        if (!(!e.this.r || a(q, 0.8d) || z)) {
                            return;
                        }
                    } else if (e.this.a && e.this.r && !a(q, 0.7d) && !z) {
                        return;
                    }
                    this.u = d;
                    this.c = true;
                    stringBuffer.append(h);
                    if (f != null) {
                        stringBuffer.append(f);
                    }
                    stringBuffer.append("&coor=gcj02");
                    stringBuffer.append("&lt=1");
                    stringBuffer.append(a2);
                    int size = e.this.Q.size();
                    stringBuffer.append(e.this.a(size));
                    e.this.R = size;
                    e.this.S = e.this.S + 1;
                    stringBuffer.append("&drsi=" + e.this.S);
                    stringBuffer.append("&idpfv=1");
                    e.this.T = e.this.T + 1;
                    if (e.this.V != null) {
                        stringBuffer.append(e.this.V);
                        e.this.V = null;
                    }
                    a = com.baidu.location.a.a.a().e();
                    if (a != null) {
                        stringBuffer.append(a);
                    }
                    stringBuffer.append(com.baidu.location.h.b.a().a(true));
                    this.e = stringBuffer.toString();
                    c(k.f);
                }
            }
        }

        public synchronized void c() {
            if (!this.c) {
                if (this.d) {
                    this.d = false;
                    b();
                }
            }
        }
    }

    private e() {
        this.e = 32;
        this.a = false;
        this.b = false;
        this.i = 5;
        this.k = 3000;
        this.l = true;
        this.c = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = 0;
        this.q = false;
        this.r = false;
        this.s = 0;
        this.t = 0;
        this.u = 0;
        this.w = 0;
        this.x = 0;
        this.y = null;
        this.z = null;
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.E = 0;
        this.F = true;
        this.G = 7;
        this.H = null;
        this.I = 20;
        this.J = null;
        this.K = 0.0d;
        this.L = 0.0d;
        this.M = 0.4d;
        this.N = 0.0d;
        this.O = false;
        this.P = true;
        this.Q = Collections.synchronizedList(new ArrayList());
        this.R = -1;
        this.S = 0;
        this.T = 0;
        this.V = null;
        this.W = null;
        this.Z = false;
        this.d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.aa = 2;
        this.ab = null;
        this.ac = false;
        this.ad = false;
        this.ae = false;
        this.af = Collections.synchronizedList(new ArrayList());
        this.f = false;
        this.g = new f(this);
        this.c = new e();
        this.X = new m();
        this.X.a(800);
        this.Y = new g(this);
        this.v = new h(this);
        this.m = new j(com.baidu.location.f.getServiceContext(), this.v);
        this.o = new h();
        this.H = new c(this.G);
        this.J = new c(this.I);
        this.U = new a(com.baidu.location.f.getServiceContext());
    }

    public static synchronized e a() {
        e eVar;
        synchronized (e.class) {
            if (j == null) {
                j = new e();
            }
            eVar = j;
        }
        return eVar;
    }

    private String a(int i) {
        if (this.Q.size() == 0) {
            return "&dr=0:0";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("&dr=");
        ((g) this.Q.get(0)).d = 1;
        stringBuilder.append(((g) this.Q.get(0)).toString());
        int i2 = 1;
        int i3 = ((g) this.Q.get(0)).a;
        while (i2 < this.Q.size() && i2 <= i) {
            ((g) this.Q.get(i2)).d = ((g) this.Q.get(i2)).a - i3;
            stringBuilder.append(";");
            stringBuilder.append(((g) this.Q.get(i2)).toString());
            int i4 = ((g) this.Q.get(i2)).a;
            i2++;
            i3 = i4;
        }
        return stringBuilder.toString();
    }

    private String a(com.baidu.location.f.f fVar) {
        int a = fVar.a();
        if (a <= 32) {
            return fVar.a(32) + "&aprk=0";
        }
        String toLowerCase;
        String str = "";
        List arrayList = new ArrayList();
        Collection arrayList2 = new ArrayList();
        for (int i = 0; i < a; i++) {
            toLowerCase = ((ScanResult) fVar.a.get(i)).BSSID.replaceAll(":", "").toLowerCase();
            if (this.U == null || !this.U.b(toLowerCase)) {
                arrayList2.add(fVar.a.get(i));
            } else {
                arrayList.add(fVar.a.get(i));
            }
        }
        toLowerCase = arrayList.size() > 0 ? "&aprk=3" : str;
        if (toLowerCase.equals("")) {
            toLowerCase = this.U.b() ? "&aprk=2" : "&aprk=1";
        }
        arrayList.addAll(arrayList2);
        fVar.a = arrayList;
        return fVar.a(32) + toLowerCase;
    }

    private void a(Message message) {
        if (this.q) {
            BDLocation bDLocation = (BDLocation) message.obj;
            if (bDLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                n();
                if (!(bDLocation.getIndoorSurpportPolygon() == null || bDLocation.getIndoorLocationSurpportBuidlingID() == null || (this.A != null && this.A.a().equals(bDLocation.getBuildingID())))) {
                    String[] split = bDLocation.getIndoorSurpportPolygon().split("\\|");
                    Location[] locationArr = new Location[split.length];
                    for (int i = 0; i < split.length; i++) {
                        String[] split2 = split[i].split(",");
                        Location location = new Location("gps");
                        location.setLatitude(Double.valueOf(split2[1]).doubleValue());
                        location.setLongitude(Double.valueOf(split2[0]).doubleValue());
                        locationArr[i] = location;
                    }
                    this.A = new i(bDLocation.getIndoorLocationSurpportBuidlingID(), locationArr);
                }
                this.t = 0;
                if (bDLocation.getBuildingID() == null) {
                    this.r = false;
                    this.u++;
                    if (this.u > 3) {
                        d();
                    }
                } else {
                    this.w = 0;
                    this.u = 0;
                    this.r = true;
                    bDLocation.setIndoorLocMode(true);
                    if (this.K < 0.1d || this.L < 0.1d) {
                        this.L = bDLocation.getLatitude();
                        this.K = bDLocation.getLongitude();
                    }
                    if (this.y == null) {
                        this.y = bDLocation.getFloor();
                    }
                    a(bDLocation.getBuildingName(), bDLocation.getFloor());
                    this.z = bDLocation.getBuildingID();
                    this.B = bDLocation.getBuildingName();
                    this.D = bDLocation.getNetworkLocationType();
                    if (this.D.equals("ble") && this.P) {
                        this.L = bDLocation.getLatitude();
                        this.K = bDLocation.getLongitude();
                        this.P = false;
                    }
                    this.E = bDLocation.isParkAvailable();
                    if (bDLocation.getFloor().equals(m())) {
                        boolean equalsIgnoreCase = bDLocation.getFloor().equalsIgnoreCase(this.y);
                        if (!equalsIgnoreCase) {
                        }
                        this.y = bDLocation.getFloor();
                        if (!equalsIgnoreCase) {
                            j();
                        }
                        if (this.ad) {
                        }
                        if (!this.O && equalsIgnoreCase) {
                            double longitude = ((this.K * ((double) 1000000)) * this.M) + ((1.0d - this.M) * (bDLocation.getLongitude() * ((double) 1000000)));
                            bDLocation.setLatitude((((this.L * ((double) 1000000)) * this.M) + ((1.0d - this.M) * (bDLocation.getLatitude() * ((double) 1000000)))) / ((double) 1000000));
                            bDLocation.setLongitude(longitude / ((double) 1000000));
                        }
                        this.L = bDLocation.getLatitude();
                        this.K = bDLocation.getLongitude();
                    } else {
                        return;
                    }
                }
                if (!(bDLocation.getNetworkLocationType() == null || bDLocation.getNetworkLocationType().equals("ble"))) {
                    j.c().c(bDLocation);
                }
            } else if (bDLocation.getLocType() == 63) {
                this.t++;
                this.r = false;
                this.Z = true;
                if (this.t > 10) {
                    d();
                } else {
                    return;
                }
            } else {
                this.t = 0;
                this.r = false;
            }
            if (this.r) {
                if (bDLocation.getTime() == null) {
                    bDLocation.setTime(this.d.format(new Date()));
                }
                if (bDLocation.getNetworkLocationType().equals("wf")) {
                    b bVar = new b(bDLocation.getLongitude(), bDLocation.getLatitude(), System.currentTimeMillis(), this.m.d(), this.af, bDLocation.getRetFields("gradient"), bDLocation.getRetFields("mean_error"), bDLocation.getRetFields("confidence"));
                    this.af.clear();
                    if (!bVar.e.isEmpty()) {
                        if (this.o.s.a(bVar)) {
                            this.o.t.b(bVar);
                        }
                        this.o.a = this.o.t.b();
                        this.o.s.b(bVar);
                    }
                    bDLocation.setDirection((float) this.N);
                }
                BDLocation bDLocation2 = new BDLocation(bDLocation);
                if (com.baidu.location.c.a.d.a().a(bDLocation2)) {
                    a(bDLocation2, 21);
                } else {
                    bDLocation2.setNetworkLocationType(bDLocation2.getNetworkLocationType() + "2");
                    a(bDLocation2, 21);
                }
            }
            this.o.c();
        }
    }

    private void a(BDLocation bDLocation) {
    }

    private void a(BDLocation bDLocation, int i) {
        if (this.ab != null) {
            if (bDLocation.getAddrStr() == null && this.ab.getAddrStr() != null) {
                bDLocation.setAddr(this.ab.getAddress());
                bDLocation.setAddrStr(this.ab.getAddrStr());
            }
            if (bDLocation.getPoiList() == null && this.ab.getPoiList() != null) {
                bDLocation.setPoiList(this.ab.getPoiList());
            }
            if (bDLocation.getLocationDescribe() == null && this.ab.getLocationDescribe() != null) {
                bDLocation.setLocationDescribe(this.ab.getLocationDescribe());
            }
        }
        if (this.f && this.h != null) {
            bDLocation.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(System.currentTimeMillis())));
            if (bDLocation.getNetworkLocationType().contains("2")) {
                String networkLocationType = bDLocation.getNetworkLocationType();
                bDLocation.setNetworkLocationType(networkLocationType.substring(0, networkLocationType.length() - 1));
                this.h.onReceiveLocation(bDLocation);
                return;
            }
            BDLocation bDLocation2 = new BDLocation(bDLocation);
            Message obtainMessage = this.c.obtainMessage(801);
            obtainMessage.obj = bDLocation2;
            obtainMessage.sendToTarget();
        } else if (!com.baidu.location.f.d.a().j()) {
            bDLocation.setIndoorNetworkState(this.aa);
            bDLocation.setUserIndoorState(1);
            com.baidu.location.a.a.a().a(bDLocation);
        }
    }

    private void a(String str, String str2) {
        this.ad = false;
    }

    private double[] a(double d, double d2, double d3, double d4) {
        double[] dArr = new double[2];
        double toRadians = Math.toRadians(d);
        double toRadians2 = Math.toRadians(d2);
        double toRadians3 = Math.toRadians(d4);
        double asin = Math.asin((Math.sin(toRadians) * Math.cos(d3 / 6378137.0d)) + ((Math.cos(toRadians) * Math.sin(d3 / 6378137.0d)) * Math.cos(toRadians3)));
        toRadians = Math.atan2((Math.sin(toRadians3) * Math.sin(d3 / 6378137.0d)) * Math.cos(toRadians), Math.cos(d3 / 6378137.0d) - (Math.sin(toRadians) * Math.sin(asin))) + toRadians2;
        dArr[0] = Math.toDegrees(asin);
        dArr[1] = Math.toDegrees(toRadians);
        return dArr;
    }

    private void b(Message message) {
        BDLocation bDLocation = (BDLocation) message.obj;
        if (this.K < 0.1d || this.L < 0.1d) {
            this.L = bDLocation.getLatitude();
            this.K = bDLocation.getLongitude();
        }
        this.H.add(bDLocation.getFloor());
        this.y = m();
        bDLocation.setFloor(this.y);
        double longitude = ((this.K * ((double) 1000000)) * this.M) + ((1.0d - this.M) * (bDLocation.getLongitude() * ((double) 1000000)));
        bDLocation.setLatitude((((this.L * ((double) 1000000)) * this.M) + ((1.0d - this.M) * (bDLocation.getLatitude() * ((double) 1000000)))) / ((double) 1000000));
        bDLocation.setLongitude(longitude / ((double) 1000000));
        bDLocation.setTime(this.d.format(new Date()));
        this.L = bDLocation.getLatitude();
        this.K = bDLocation.getLongitude();
        a(bDLocation, 21);
    }

    private void j() {
        this.X.b();
        this.T = 0;
        this.o.s.g();
        this.o.t.g();
        this.o.a = 0.0f;
        this.o.p.clear();
        this.af.clear();
        this.Q.clear();
    }

    private void k() {
        this.H.clear();
        this.J.clear();
        this.s = 0;
        this.t = 0;
        this.E = 0;
        this.x = 0;
        this.y = null;
        this.Z = false;
        this.z = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.F = true;
        this.M = 0.4d;
        this.K = 0.0d;
        this.L = 0.0d;
        this.w = 0;
        this.u = 0;
        this.O = false;
        this.S = 0;
        this.ae = false;
        this.ad = false;
        com.baidu.location.a.k.a().c(false);
        if (this.W != null) {
            this.W.a();
        }
    }

    private void l() {
        if (this.q) {
            this.l = true;
            this.o.b();
            this.p = System.currentTimeMillis();
        }
    }

    private String m() {
        String str;
        Map hashMap = new HashMap();
        int size = this.H.size();
        String str2 = null;
        int i = -1;
        int i2 = 0;
        String str3 = "";
        while (i2 < size) {
            try {
                str = (String) this.H.get(i2);
                str3 = str3 + str + "|";
                if (hashMap.containsKey(str)) {
                    hashMap.put(str, Integer.valueOf(((Integer) hashMap.get(str)).intValue() + 1));
                } else {
                    hashMap.put(str, Integer.valueOf(1));
                }
                i2++;
            } catch (Exception e) {
                return this.y;
            }
        }
        for (String str4 : hashMap.keySet()) {
            int intValue;
            if (((Integer) hashMap.get(str4)).intValue() > i) {
                str = str4;
                intValue = ((Integer) hashMap.get(str4)).intValue();
            } else {
                intValue = i;
                str = str2;
            }
            i = intValue;
            str2 = str;
        }
        return (size != this.G || this.y.equals(str2)) ? str2 == null ? this.y : (size < 3 || size > this.G || !((String) this.H.get(size - 3)).equals(this.H.get(size - 1)) || !((String) this.H.get(size - 2)).equals(this.H.get(size - 1)) || ((String) this.H.get(size - 1)).equals(str2)) ? str2 : (String) this.H.get(size - 1) : (((String) this.H.get(size + -3)).equals(str2) && ((String) this.H.get(size - 2)).equals(str2) && ((String) this.H.get(size - 1)).equals(str2)) ? str2 : this.y;
    }

    private void n() {
        for (int i = this.R; i >= 0 && this.Q.size() > 0; i--) {
            this.Q.remove(0);
        }
        this.R = -1;
    }

    public boolean a(Location location) {
        if (location == null || this.A == null || !this.A.a(location.getLatitude(), location.getLongitude())) {
            this.ac = false;
        } else {
            this.ac = true;
        }
        return this.ac;
    }

    public synchronized void b() {
        if (this.q) {
            this.H.clear();
        }
    }

    public synchronized void c() {
        if (!this.q) {
            this.s = System.currentTimeMillis();
            this.m.a();
            this.n = new f();
            this.n.start();
            this.r = false;
            this.q = true;
            this.S = 0;
            com.baidu.location.a.k.a().c(true);
            com.baidu.location.a.k.a().d();
        }
    }

    public synchronized void d() {
        if (this.q) {
            this.m.b();
            if (this.X != null && this.X.c()) {
                this.X.a();
            }
            if (this.U != null) {
                this.U.c();
            }
            if (this.n != null) {
                this.n.b = false;
                this.n.interrupt();
                this.n = null;
            }
            k();
            this.r = false;
            this.q = false;
            com.baidu.location.a.a.a().d();
        }
    }

    public synchronized void e() {
    }

    public boolean f() {
        return this.q;
    }

    public boolean g() {
        return this.q && this.r;
    }

    public String h() {
        return this.y;
    }

    public String i() {
        return this.z;
    }
}
