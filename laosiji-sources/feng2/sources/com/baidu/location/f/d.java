package com.baidu.location.f;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.GnssStatus;
import android.location.GnssStatus.Callback;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.a.f;
import com.baidu.location.a.r;
import com.baidu.location.a.s;
import com.baidu.location.b.e;
import com.baidu.location.h.k;
import com.stub.StubApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class d {
    private static d c = null;
    private static int m = 0;
    private static String u = null;
    private int A;
    private int B;
    private HashMap<Integer, List<GpsSatellite>> C;
    private double D = 100.0d;
    private long E = 0;
    private final long a = 1000;
    private final long b = 9000;
    private Context d;
    private LocationManager e = null;
    private Location f;
    private c g = null;
    private d h = null;
    private GpsStatus i;
    private a j;
    private boolean k = false;
    private b l = null;
    private int n = 0;
    private long o = 0;
    private boolean p = false;
    private boolean q = false;
    private String r = null;
    private boolean s = false;
    private long t = 0;
    private Handler v = null;
    private final int w = 1;
    private final int x = 2;
    private final int y = 3;
    private final int z = 4;

    @TargetApi(24)
    private class a extends Callback {
        private a() {
        }

        /* synthetic */ a(d dVar, e eVar) {
            this();
        }

        public void onFirstFix(int i) {
        }

        public void onSatelliteStatusChanged(GnssStatus gnssStatus) {
            int i = 0;
            if (d.this.e != null) {
                int satelliteCount = gnssStatus.getSatelliteCount();
                for (int i2 = 0; i2 < satelliteCount; i2++) {
                    if (gnssStatus.usedInFix(i2)) {
                        i++;
                    }
                }
                d.m = i;
            }
        }

        public void onStarted() {
        }

        public void onStopped() {
            d.this.d(null);
            d.this.b(false);
            d.m = 0;
        }
    }

    private class b implements Listener {
        long a;
        private long c;
        private final int d;
        private boolean e;
        private List<String> f;
        private String g;
        private String h;
        private String i;
        private long j;

        private b() {
            this.a = 0;
            this.c = 0;
            this.d = 400;
            this.e = false;
            this.f = new ArrayList();
            this.g = null;
            this.h = null;
            this.i = null;
            this.j = 0;
        }

        /* synthetic */ b(d dVar, e eVar) {
            this();
        }

        public void onGpsStatusChanged(int i) {
            if (d.this.e != null) {
                switch (i) {
                    case 2:
                        d.this.d(null);
                        d.this.b(false);
                        d.m = 0;
                        return;
                    case 4:
                        if (d.this.q) {
                            try {
                                if (d.this.i == null) {
                                    d.this.i = d.this.e.getGpsStatus(null);
                                } else {
                                    d.this.e.getGpsStatus(d.this.i);
                                }
                                d.this.A = 0;
                                d.this.B = 0;
                                d.this.C = new HashMap();
                                int i2 = 0;
                                double d = 0.0d;
                                int i3 = false;
                                for (GpsSatellite gpsSatellite : d.this.i.getSatellites()) {
                                    if (gpsSatellite.usedInFix()) {
                                        i2++;
                                        d += (double) gpsSatellite.getSnr();
                                        if (gpsSatellite.getPrn() <= 32) {
                                            i3++;
                                        }
                                        if (gpsSatellite.getSnr() >= ((float) k.G)) {
                                            d.this.B = d.this.B + 1;
                                        }
                                    }
                                }
                                if (i3 > 0) {
                                    d.this.n = i3;
                                }
                                if (i2 > 0) {
                                    this.j = System.currentTimeMillis();
                                    d.m = i2;
                                    d.this.D = d / ((double) i2);
                                    return;
                                } else if (System.currentTimeMillis() - this.j > 100) {
                                    this.j = System.currentTimeMillis();
                                    d.m = i2;
                                    return;
                                } else {
                                    return;
                                }
                            } catch (Exception e) {
                                return;
                            }
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private class c implements LocationListener {
        private c() {
        }

        /* synthetic */ c(d dVar, e eVar) {
            this();
        }

        public void onLocationChanged(Location location) {
            StubApp.mark(location);
            d.this.t = System.currentTimeMillis();
            d.this.b(true);
            d.this.d(location);
            d.this.p = false;
        }

        public void onProviderDisabled(String str) {
            d.this.d(null);
            d.this.b(false);
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            switch (i) {
                case 0:
                    d.this.d(null);
                    d.this.b(false);
                    return;
                case 1:
                    d.this.o = System.currentTimeMillis();
                    d.this.p = true;
                    d.this.b(false);
                    return;
                case 2:
                    d.this.p = false;
                    return;
                default:
                    return;
            }
        }
    }

    private class d implements LocationListener {
        private long b;

        private d() {
            this.b = 0;
        }

        /* synthetic */ d(d dVar, e eVar) {
            this();
        }

        public void onLocationChanged(Location location) {
            StubApp.mark(location);
            if (!d.this.q && location != null && location.getProvider() == "gps" && System.currentTimeMillis() - this.b >= 10000 && s.a(location, false)) {
                this.b = System.currentTimeMillis();
                d.this.v.sendMessage(d.this.v.obtainMessage(4, location));
            }
        }

        public void onProviderDisabled(String str) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }
    }

    private d() {
        if (VERSION.SDK_INT >= 24) {
            try {
                Class.forName("android.location.GnssStatus");
                this.k = true;
            } catch (ClassNotFoundException e) {
                this.k = false;
            }
        }
    }

    public static synchronized d a() {
        d dVar;
        synchronized (d.class) {
            if (c == null) {
                c = new d();
            }
            dVar = c;
        }
        return dVar;
    }

    public static String a(Location location) {
        float f = -1.0f;
        if (location == null) {
            return null;
        }
        float speed = (float) (((double) location.getSpeed()) * 3.6d);
        if (!location.hasSpeed()) {
            speed = -1.0f;
        }
        int accuracy = (int) (location.hasAccuracy() ? location.getAccuracy() : -1.0f);
        double altitude = location.hasAltitude() ? location.getAltitude() : 555.0d;
        if (location.hasBearing()) {
            f = location.getBearing();
        }
        return String.format(Locale.CHINA, "&ll=%.5f|%.5f&s=%.1f&d=%.1f&ll_r=%d&ll_n=%d&ll_h=%.2f&ll_t=%d", new Object[]{Double.valueOf(location.getLongitude()), Double.valueOf(location.getLatitude()), Float.valueOf(speed), Float.valueOf(f), Integer.valueOf(accuracy), Integer.valueOf(m), Double.valueOf(altitude), Long.valueOf(location.getTime() / 1000)});
    }

    private void a(double d, double d2, float f) {
        int i = 0;
        if (e.a().f) {
            if (d >= 73.146973d && d <= 135.252686d && d2 <= 54.258807d && d2 >= 14.604847d && f <= 18.0f) {
                int i2 = (int) ((d - k.s) * 1000.0d);
                int i3 = (int) ((k.t - d2) * 1000.0d);
                if (i2 <= 0 || i2 >= 50 || i3 <= 0 || i3 >= 50) {
                    String str = String.format(Locale.CHINA, "&ll=%.5f|%.5f", new Object[]{Double.valueOf(d), Double.valueOf(d2)}) + "&im=" + com.baidu.location.h.b.a().b();
                    k.q = d;
                    k.r = d2;
                    e.a().a(str);
                } else {
                    i2 += i3 * 50;
                    i3 = i2 >> 2;
                    i2 &= 3;
                    if (k.w) {
                        i = (k.v[i3] >> (i2 * 2)) & 3;
                    }
                }
            }
            if (k.u != i) {
                k.u = i;
            }
        }
    }

    private void a(String str, Location location) {
        if (location != null) {
            String str2 = str + com.baidu.location.a.a.a().e();
            boolean f = g.a().f();
            r.a(new a(b.a().f()));
            r.a(System.currentTimeMillis());
            r.a(new Location(location));
            r.a(str2);
            if (!f) {
                s.a(r.c(), null, r.d(), str2);
            }
        }
    }

    public static boolean a(Location location, Location location2, boolean z) {
        if (location == location2) {
            return false;
        }
        if (location == null || location2 == null) {
            return true;
        }
        float speed = location2.getSpeed();
        if (z && ((k.u == 3 || !com.baidu.location.h.d.a().a(location2.getLongitude(), location2.getLatitude())) && speed < 5.0f)) {
            return true;
        }
        float distanceTo = location2.distanceTo(location);
        return speed > k.K ? distanceTo > k.M : speed > k.J ? distanceTo > k.L : distanceTo > 5.0f;
    }

    public static String b(Location location) {
        String a = a(location);
        return a != null ? a + "&g_tp=0" : a;
    }

    private void b(boolean z) {
        this.s = z;
        if (!(z && j())) {
        }
    }

    public static String c(Location location) {
        String a = a(location);
        return a != null ? a + u : a;
    }

    private void d(Location location) {
        this.v.sendMessage(this.v.obtainMessage(1, location));
    }

    private void e(Location location) {
        if (location != null) {
            int i = m;
            if (i == 0) {
                try {
                    i = location.getExtras().getInt("satellites");
                } catch (Exception e) {
                }
            }
            if (i != 0 || k.l) {
                Location location2;
                this.f = location;
                i = m;
                if (this.f == null) {
                    this.r = null;
                    location2 = null;
                } else {
                    Location location3 = new Location(this.f);
                    this.f.setTime(System.currentTimeMillis());
                    float speed = (float) (((double) this.f.getSpeed()) * 3.6d);
                    if (!this.f.hasSpeed()) {
                        speed = -1.0f;
                    }
                    if (i == 0) {
                        try {
                            i = this.f.getExtras().getInt("satellites");
                        } catch (Exception e2) {
                        }
                    }
                    this.r = String.format(Locale.CHINA, "&ll=%.5f|%.5f&s=%.1f&d=%.1f&ll_n=%d&ll_t=%d", new Object[]{Double.valueOf(this.f.getLongitude()), Double.valueOf(this.f.getLatitude()), Float.valueOf(speed), Float.valueOf(this.f.getBearing()), Integer.valueOf(i), Long.valueOf(r2)});
                    a(this.f.getLongitude(), this.f.getLatitude(), speed);
                    location2 = location3;
                }
                try {
                    f.a().a(this.f);
                } catch (Exception e3) {
                }
                if (location2 != null) {
                    com.baidu.location.a.c.a().a(location2);
                }
                if (j() && this.f != null) {
                    if (com.baidu.location.c.e.a().g() && !com.baidu.location.c.e.a().a(this.f)) {
                        com.baidu.location.a.a.a().a(g());
                    } else if (com.baidu.location.c.e.a().g()) {
                        com.baidu.location.a.a.a().a(g());
                    } else {
                        com.baidu.location.a.a.a().a(g());
                    }
                    if (m > 2 && s.a(this.f, true)) {
                        boolean f = g.a().f();
                        r.a(new a(b.a().f()));
                        r.a(System.currentTimeMillis());
                        r.a(new Location(this.f));
                        r.a(com.baidu.location.a.a.a().e());
                        if (!f) {
                            s.a(r.c(), null, r.d(), com.baidu.location.a.a.a().e());
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        this.f = null;
    }

    public void a(boolean z) {
        if (z) {
            c();
        } else {
            d();
        }
    }

    public synchronized void b() {
        if (com.baidu.location.f.isServing) {
            this.d = com.baidu.location.f.getServiceContext();
            try {
                this.e = (LocationManager) this.d.getSystemService("location");
                if (this.k) {
                    this.j = new a(this, null);
                    this.e.registerGnssStatusCallback(this.j);
                } else {
                    this.l = new b(this, null);
                    this.e.addGpsStatusListener(this.l);
                }
                this.h = new d(this, null);
                this.e.requestLocationUpdates("passive", 9000, 0.0f, this.h);
            } catch (Exception e) {
            }
            this.v = new e(this);
        }
    }

    public void c() {
        Log.d(com.baidu.location.h.a.a, "start gps...");
        if (!this.q) {
            try {
                this.g = new c(this, null);
                try {
                    this.e.sendExtraCommand("gps", "force_xtra_injection", new Bundle());
                } catch (Exception e) {
                }
                this.e.requestLocationUpdates("gps", 1000, 0.0f, this.g);
                this.E = System.currentTimeMillis();
                this.q = true;
            } catch (Exception e2) {
            }
        }
    }

    public void d() {
        if (this.q) {
            if (this.e != null) {
                try {
                    if (this.g != null) {
                        this.e.removeUpdates(this.g);
                    }
                } catch (Exception e) {
                }
            }
            k.d = 0;
            k.u = 0;
            this.g = null;
            this.q = false;
            b(false);
        }
    }

    public synchronized void e() {
        d();
        if (this.e != null) {
            try {
                if (this.l != null) {
                    this.e.removeGpsStatusListener(this.l);
                }
                if (this.k && this.j != null) {
                    this.e.unregisterGnssStatusCallback(this.j);
                }
                this.e.removeUpdates(this.h);
            } catch (Exception e) {
            }
            this.l = null;
            this.e = null;
        }
    }

    public String f() {
        if (!j() || this.f == null) {
            return null;
        }
        String replaceAll = a(this.f).replaceAll("ll", "idll").replaceAll("&d=", "&idd=").replaceAll("&s", "&ids=");
        return String.format("%s&idgps_tp=%s", new Object[]{replaceAll, this.f.getProvider()});
    }

    public String g() {
        if (this.f == null) {
            return null;
        }
        double[] dArr;
        int i;
        String str = "{\"result\":{\"time\":\"" + k.a() + "\",\"error\":\"61\"},\"content\":{\"point\":{\"x\":" + "\"%f\",\"y\":\"%f\"},\"radius\":\"%d\",\"d\":\"%f\"," + "\"s\":\"%f\",\"n\":\"%d\"";
        int accuracy = (int) (this.f.hasAccuracy() ? this.f.getAccuracy() : 10.0f);
        float speed = (float) (((double) this.f.getSpeed()) * 3.6d);
        if (!this.f.hasSpeed()) {
            speed = -1.0f;
        }
        double[] dArr2 = new double[2];
        if (com.baidu.location.h.d.a().a(this.f.getLongitude(), this.f.getLatitude())) {
            dArr2 = Jni.coorEncrypt(this.f.getLongitude(), this.f.getLatitude(), BDLocation.BDLOCATION_WGS84_TO_GCJ02);
            if (dArr2[0] > 0.0d || dArr2[1] > 0.0d) {
                dArr = dArr2;
                i = 1;
            } else {
                dArr2[0] = this.f.getLongitude();
                dArr2[1] = this.f.getLatitude();
                dArr = dArr2;
                i = 1;
            }
        } else {
            dArr2[0] = this.f.getLongitude();
            dArr2[1] = this.f.getLatitude();
            dArr = dArr2;
            i = 0;
        }
        String format = String.format(Locale.CHINA, str, new Object[]{Double.valueOf(dArr[0]), Double.valueOf(dArr[1]), Integer.valueOf(accuracy), Float.valueOf(this.f.getBearing()), Float.valueOf(speed), Integer.valueOf(m)});
        if (i == 0) {
            format = format + ",\"in_cn\":\"0\"";
        }
        if (!this.f.hasAltitude()) {
            return format + "}}";
        }
        return format + String.format(Locale.CHINA, ",\"h\":%.2f}}", new Object[]{Double.valueOf(this.f.getAltitude())});
    }

    public Location h() {
        return (this.f != null && Math.abs(System.currentTimeMillis() - this.f.getTime()) <= 60000) ? this.f : null;
    }

    public boolean i() {
        try {
            return (this.f == null || this.f.getLatitude() == 0.0d || this.f.getLongitude() == 0.0d || (m <= 2 && this.f.getExtras().getInt("satellites", 3) <= 2)) ? false : true;
        } catch (Exception e) {
            return (this.f == null || this.f.getLatitude() == 0.0d || this.f.getLongitude() == 0.0d) ? false : true;
        }
    }

    public boolean j() {
        if (!i() || System.currentTimeMillis() - this.t > 10000) {
            return false;
        }
        return (!this.p || System.currentTimeMillis() - this.o >= 3000) ? this.s : true;
    }
}
