package com.baidu.location.c;

import com.baidu.location.BDLocation;

class n implements Runnable {
    final /* synthetic */ m a;

    n(m mVar) {
        this.a = mVar;
    }

    public void run() {
        b a = this.a.a(this.a.e);
        if (!(a == null || this.a.a == null)) {
            this.a.e = this.a.e.b(a);
            long currentTimeMillis = System.currentTimeMillis();
            if (!a.b(2.0E-6d) && currentTimeMillis - this.a.j > this.a.b) {
                BDLocation bDLocation = new BDLocation(this.a.c);
                bDLocation.setLatitude(this.a.e.a);
                bDLocation.setLongitude(this.a.e.b);
                this.a.a.a(bDLocation);
                this.a.j = currentTimeMillis;
            }
        }
        this.a.l.postDelayed(this.a.m, 450);
    }
}
