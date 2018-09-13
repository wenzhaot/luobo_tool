package com.baidu.location.a;

import android.location.Location;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Message;
import android.os.Messenger;
import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.LocationClientOption;
import com.baidu.location.b.j;
import com.baidu.location.c.e;
import com.baidu.location.f.d;
import com.baidu.location.f.g;
import com.baidu.location.h.k;
import com.baidu.platform.comapi.location.CoordinateType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class a {
    private static a d = null;
    public boolean a;
    boolean b;
    int c;
    private ArrayList<a> e;
    private boolean f;
    private BDLocation g;
    private BDLocation h;
    private BDLocation i;
    private BDLocation j;
    private boolean k;
    private boolean l;
    private b m;

    private class a {
        public String a = null;
        public Messenger b = null;
        public LocationClientOption c = new LocationClientOption();
        public int d = 0;
        final /* synthetic */ a e;

        public a(a aVar, Message message) {
            boolean z = true;
            this.e = aVar;
            this.b = message.replyTo;
            this.a = message.getData().getString("packName");
            this.c.prodName = message.getData().getString("prodName");
            com.baidu.location.h.b.a().a(this.c.prodName, this.a);
            this.c.coorType = message.getData().getString("coorType");
            this.c.addrType = message.getData().getString("addrType");
            this.c.enableSimulateGps = message.getData().getBoolean("enableSimulateGps", false);
            boolean z2 = k.l || this.c.enableSimulateGps;
            k.l = z2;
            if (!k.g.equals("all")) {
                k.g = this.c.addrType;
            }
            this.c.openGps = message.getData().getBoolean("openGPS");
            this.c.scanSpan = message.getData().getInt("scanSpan");
            this.c.timeOut = message.getData().getInt("timeOut");
            this.c.priority = message.getData().getInt("priority");
            this.c.location_change_notify = message.getData().getBoolean("location_change_notify");
            this.c.mIsNeedDeviceDirect = message.getData().getBoolean("needDirect", false);
            this.c.isNeedAltitude = message.getData().getBoolean("isneedaltitude", false);
            z2 = k.h || message.getData().getBoolean("isneedaptag", false);
            k.h = z2;
            if (!(k.i || message.getData().getBoolean("isneedaptagd", false))) {
                z = false;
            }
            k.i = z;
            k.Q = message.getData().getFloat("autoNotifyLocSensitivity", 0.5f);
            int i = message.getData().getInt("wifitimeout", Integer.MAX_VALUE);
            if (i < k.ae) {
                k.ae = i;
            }
            i = message.getData().getInt("autoNotifyMaxInterval", 0);
            if (i >= k.V) {
                k.V = i;
            }
            i = message.getData().getInt("autoNotifyMinDistance", 0);
            if (i >= k.X) {
                k.X = i;
            }
            i = message.getData().getInt("autoNotifyMinTimeInterval", 0);
            if (i >= k.W) {
                k.W = i;
            }
            if (this.c.scanSpan >= 1000) {
                j.a().b();
            }
            if (this.c.mIsNeedDeviceDirect || this.c.isNeedAltitude) {
                k.a().a(this.c.mIsNeedDeviceDirect);
                k.a().b(this.c.isNeedAltitude);
                k.a().b();
            }
            aVar.b |= this.c.isNeedAltitude;
        }

        private void a(int i) {
            Message obtain = Message.obtain(null, i);
            try {
                if (this.b != null) {
                    this.b.send(obtain);
                }
                this.d = 0;
            } catch (Exception e) {
                if (e instanceof DeadObjectException) {
                    this.d++;
                }
            }
        }

        private void a(int i, Bundle bundle) {
            Message obtain = Message.obtain(null, i);
            obtain.setData(bundle);
            try {
                if (this.b != null) {
                    this.b.send(obtain);
                }
                this.d = 0;
            } catch (Exception e) {
                if (e instanceof DeadObjectException) {
                    this.d++;
                }
                e.printStackTrace();
            }
        }

        private void a(int i, String str, BDLocation bDLocation) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(str, bDLocation);
            bundle.setClassLoader(BDLocation.class.getClassLoader());
            Message obtain = Message.obtain(null, i);
            obtain.setData(bundle);
            try {
                if (this.b != null) {
                    this.b.send(obtain);
                }
                this.d = 0;
            } catch (Exception e) {
                if (e instanceof DeadObjectException) {
                    this.d++;
                }
            }
        }

        public void a() {
            a(111);
        }

        public void a(BDLocation bDLocation) {
            a(bDLocation, 21);
        }

        public void a(BDLocation bDLocation, int i) {
            BDLocation bDLocation2 = new BDLocation(bDLocation);
            if (e.a().f()) {
                bDLocation2.setIndoorLocMode(true);
            }
            if (k.a().h() && (bDLocation2.getLocType() == BDLocation.TypeNetWorkLocation || bDLocation2.getLocType() == 66)) {
                bDLocation2.setAltitude(k.a().j());
            }
            if (i == 21) {
                a(27, "locStr", bDLocation2);
            }
            if (!(this.c.coorType == null || this.c.coorType.equals(CoordinateType.GCJ02))) {
                double longitude = bDLocation2.getLongitude();
                double latitude = bDLocation2.getLatitude();
                if (!(longitude == Double.MIN_VALUE || latitude == Double.MIN_VALUE)) {
                    double[] coorEncrypt;
                    if ((bDLocation2.getCoorType() != null && bDLocation2.getCoorType().equals(CoordinateType.GCJ02)) || bDLocation2.getCoorType() == null) {
                        coorEncrypt = Jni.coorEncrypt(longitude, latitude, this.c.coorType);
                        bDLocation2.setLongitude(coorEncrypt[0]);
                        bDLocation2.setLatitude(coorEncrypt[1]);
                        bDLocation2.setCoorType(this.c.coorType);
                    } else if (!(bDLocation2.getCoorType() == null || !bDLocation2.getCoorType().equals(CoordinateType.WGS84) || this.c.coorType.equals("bd09ll"))) {
                        coorEncrypt = Jni.coorEncrypt(longitude, latitude, "wgs842mc");
                        bDLocation2.setLongitude(coorEncrypt[0]);
                        bDLocation2.setLatitude(coorEncrypt[1]);
                        bDLocation2.setCoorType("wgs84mc");
                    }
                }
            }
            a(i, "locStr", bDLocation2);
        }

        public void b() {
            if (!this.c.location_change_notify) {
                return;
            }
            if (k.b) {
                a(54);
            } else {
                a(55);
            }
        }
    }

    private class b implements Runnable {
        final /* synthetic */ a a;
        private int b;
        private boolean c;

        public void run() {
            if (!this.c) {
                this.b++;
                this.a.l = false;
            }
        }
    }

    private a() {
        this.e = null;
        this.f = false;
        this.a = false;
        this.b = false;
        this.g = null;
        this.h = null;
        this.i = null;
        this.c = 0;
        this.j = null;
        this.k = false;
        this.l = false;
        this.m = null;
        this.e = new ArrayList();
    }

    private a a(Messenger messenger) {
        if (this.e == null) {
            return null;
        }
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            a aVar = (a) it.next();
            if (aVar.b.equals(messenger)) {
                return aVar;
            }
        }
        return null;
    }

    public static a a() {
        if (d == null) {
            d = new a();
        }
        return d;
    }

    private void a(a aVar) {
        if (aVar != null) {
            if (a(aVar.b) != null) {
                aVar.a(14);
                return;
            }
            this.e.add(aVar);
            aVar.a(13);
        }
    }

    private void g() {
        h();
        f();
    }

    private void h() {
        boolean z;
        boolean z2 = false;
        Iterator it = this.e.iterator();
        boolean z3 = false;
        while (true) {
            z = z2;
            if (!it.hasNext()) {
                break;
            }
            a aVar = (a) it.next();
            if (aVar.c.openGps) {
                int i = 1;
            }
            z2 = aVar.c.location_change_notify ? true : z3;
        }
        k.a = z3;
        if (this.f != z) {
            this.f = z;
            d.a().a(this.f);
        }
    }

    public void a(Bundle bundle, int i) {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            try {
                a aVar = (a) it.next();
                aVar.a(i, bundle);
                if (aVar.d > 4) {
                    it.remove();
                }
            } catch (Exception e) {
                return;
            }
        }
    }

    public void a(Message message) {
        if (message != null && message.replyTo != null) {
            this.a = true;
            g.a().b();
            a(new a(this, message));
            g();
        }
    }

    public void a(BDLocation bDLocation) {
        b(bDLocation);
    }

    public void a(String str) {
        c(new BDLocation(str));
    }

    public void b() {
        this.e.clear();
        this.g = null;
        g();
    }

    public void b(Message message) {
        a a = a(message.replyTo);
        if (a != null) {
            this.e.remove(a);
        }
        j.a().c();
        k.a().c();
        g();
    }

    public void b(BDLocation bDLocation) {
        if (e.a().g() && bDLocation.getFloor() != null) {
            this.i = null;
            this.i = new BDLocation(bDLocation);
        }
        boolean z = j.h;
        if (z) {
            j.h = false;
        }
        if (k.V >= 10000 && (bDLocation.getLocType() == 61 || bDLocation.getLocType() == BDLocation.TypeNetWorkLocation || bDLocation.getLocType() == 66)) {
            if (this.g != null) {
                float[] fArr = new float[1];
                Location.distanceBetween(this.g.getLatitude(), this.g.getLongitude(), bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
                if (fArr[0] > ((float) k.X) || z) {
                    this.g = null;
                    this.g = new BDLocation(bDLocation);
                } else {
                    return;
                }
            }
            this.g = new BDLocation(bDLocation);
        }
        Iterator it;
        a aVar;
        if (bDLocation == null || bDLocation.getLocType() != BDLocation.TypeNetWorkLocation || i.a().b()) {
            if (!bDLocation.hasAltitude() && this.b && (bDLocation.getLocType() == BDLocation.TypeNetWorkLocation || bDLocation.getLocType() == 66)) {
                double a = com.baidu.location.b.a.a().a(bDLocation.getLongitude(), bDLocation.getLatitude());
                if (a != Double.MAX_VALUE) {
                    bDLocation.setAltitude(a);
                }
            }
            if (bDLocation.getLocType() == 61) {
                bDLocation.setGpsAccuracyStatus(com.baidu.location.b.a.a().a(bDLocation));
            }
            it = this.e.iterator();
            while (it.hasNext()) {
                try {
                    aVar = (a) it.next();
                    aVar.a(bDLocation);
                    if (aVar.d > 4) {
                        it.remove();
                    }
                } catch (Exception e) {
                    return;
                }
            }
            return;
        }
        if (this.h == null) {
            this.h = new BDLocation();
            this.h.setLocType(505);
        }
        it = this.e.iterator();
        while (it.hasNext()) {
            try {
                aVar = (a) it.next();
                aVar.a(this.h);
                if (aVar.d > 4) {
                    it.remove();
                }
            } catch (Exception e2) {
                return;
            }
        }
    }

    public void c() {
        if (this.i != null) {
            a(this.i);
        }
    }

    public void c(BDLocation bDLocation) {
        Address a = j.c().a(bDLocation);
        String f = j.c().f();
        List g = j.c().g();
        if (a != null) {
            bDLocation.setAddr(a);
        }
        if (f != null) {
            bDLocation.setLocationDescribe(f);
        }
        if (g != null) {
            bDLocation.setPoiList(g);
        }
        if (e.a().g() && e.a().h() != null) {
            bDLocation.setFloor(e.a().h());
            bDLocation.setIndoorLocMode(true);
            if (e.a().i() != null) {
                bDLocation.setBuildingID(e.a().i());
            }
        }
        j.c().d(bDLocation);
        a(bDLocation);
    }

    public boolean c(Message message) {
        boolean z = true;
        a a = a(message.replyTo);
        if (a == null) {
            return false;
        }
        int i = a.c.scanSpan;
        a.c.scanSpan = message.getData().getInt("scanSpan", a.c.scanSpan);
        if (a.c.scanSpan < 1000) {
            j.a().e();
            k.a().c();
            this.a = false;
        } else {
            j.a().d();
            this.a = true;
        }
        if (a.c.scanSpan <= 999 || i >= 1000) {
            z = false;
        } else {
            if (a.c.mIsNeedDeviceDirect || a.c.isNeedAltitude) {
                k.a().a(a.c.mIsNeedDeviceDirect);
                k.a().b(a.c.isNeedAltitude);
                k.a().b();
            }
            this.b |= a.c.isNeedAltitude;
        }
        a.c.openGps = message.getData().getBoolean("openGPS", a.c.openGps);
        String string = message.getData().getString("coorType");
        LocationClientOption locationClientOption = a.c;
        if (string == null || string.equals("")) {
            string = a.c.coorType;
        }
        locationClientOption.coorType = string;
        string = message.getData().getString("addrType");
        locationClientOption = a.c;
        if (string == null || string.equals("")) {
            string = a.c.addrType;
        }
        locationClientOption.addrType = string;
        if (!k.g.equals(a.c.addrType)) {
            j.c().j();
        }
        a.c.timeOut = message.getData().getInt("timeOut", a.c.timeOut);
        a.c.location_change_notify = message.getData().getBoolean("location_change_notify", a.c.location_change_notify);
        a.c.priority = message.getData().getInt("priority", a.c.priority);
        int i2 = message.getData().getInt("wifitimeout", Integer.MAX_VALUE);
        if (i2 < k.ae) {
            k.ae = i2;
        }
        g();
        return z;
    }

    public int d(Message message) {
        if (message == null || message.replyTo == null) {
            return 1;
        }
        a a = a(message.replyTo);
        return (a == null || a.c == null) ? 1 : a.c.priority;
    }

    public void d() {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            ((a) it.next()).a();
        }
    }

    public int e(Message message) {
        if (message == null || message.replyTo == null) {
            return 1000;
        }
        a a = a(message.replyTo);
        return (a == null || a.c == null) ? 1000 : a.c.scanSpan;
    }

    public String e() {
        StringBuffer stringBuffer = new StringBuffer(256);
        if (this.e.isEmpty()) {
            return "&prod=" + com.baidu.location.h.b.e + ":" + com.baidu.location.h.b.d;
        }
        a aVar = (a) this.e.get(0);
        if (aVar.c.prodName != null) {
            stringBuffer.append(aVar.c.prodName);
        }
        if (aVar.a != null) {
            stringBuffer.append(":");
            stringBuffer.append(aVar.a);
            stringBuffer.append("|");
        }
        String stringBuffer2 = stringBuffer.toString();
        return (stringBuffer2 == null || stringBuffer2.equals("")) ? null : "&prod=" + stringBuffer2;
    }

    public void f() {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            ((a) it.next()).b();
        }
    }
}
