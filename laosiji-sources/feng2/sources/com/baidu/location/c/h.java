package com.baidu.location.c;

import com.baidu.location.BDLocation;
import com.baidu.location.c.a.d;
import com.baidu.location.c.j.a;
import com.baidu.location.h.e;
import java.util.Date;

class h implements a {
    final /* synthetic */ e a;

    h(e eVar) {
        this.a = eVar;
    }

    public synchronized void a(double d, double d2) {
        this.a.a = true;
        this.a.b = true;
        this.a.M = 0.4d;
        if (this.a.K > 0.1d || this.a.L > 0.1d) {
            if (!(this.a.ad && this.a.ae)) {
            }
            double[] a = this.a.a(this.a.L, this.a.K, d, d2);
            this.a.af.add(Float.valueOf((float) d2));
            this.a.N = d2;
            try {
                double f = this.a.L;
                double e = this.a.K;
                if (this.a.ab != null) {
                    f = this.a.ab.getLatitude();
                    e = this.a.ab.getLongitude();
                }
                if (a[0] >= 1.0d && a[1] >= 1.0d && e.a(a[0], a[1], f, e) <= 10000.0d) {
                    BDLocation bDLocation = new BDLocation();
                    bDLocation.setLocType(BDLocation.TypeNetWorkLocation);
                    bDLocation.setLatitude(a[0]);
                    bDLocation.setLongitude(a[1]);
                    bDLocation.setRadius(15.0f);
                    bDLocation.setDirection((float) d2);
                    bDLocation.setTime(this.a.d.format(new Date()));
                    this.a.L = a[0];
                    this.a.K = a[1];
                    if (this.a.y != null) {
                        bDLocation.setFloor(this.a.y);
                    }
                    if (this.a.z != null) {
                        bDLocation.setBuildingID(this.a.z);
                    }
                    if (this.a.B != null) {
                        bDLocation.setBuildingName(this.a.B);
                    }
                    bDLocation.setParkAvailable(this.a.E);
                    if (this.a.D != null) {
                        bDLocation.setNetworkLocationType(this.a.D);
                    } else {
                        bDLocation.setNetworkLocationType("wf");
                    }
                    if (this.a.r) {
                        bDLocation.setIndoorLocMode(true);
                        this.a.w = this.a.w + 1;
                        if (this.a.Q.size() > 50) {
                            this.a.Q.clear();
                        }
                        this.a.Q.add(new g(this.a.m.d(), d, d2));
                        if (this.a.w < 60 && this.a.m.d() % 3 == 0) {
                            bDLocation.setNetworkLocationType("dr");
                            BDLocation bDLocation2 = new BDLocation(bDLocation);
                            if (d.a().a(bDLocation2)) {
                                this.a.a(bDLocation2, 21);
                            } else {
                                if (null != null) {
                                    bDLocation2.setNetworkLocationType(null);
                                } else {
                                    bDLocation2.setNetworkLocationType("dr2");
                                }
                                this.a.a(bDLocation2, 21);
                            }
                        }
                    }
                }
            } catch (Exception e2) {
            }
        }
    }
}
