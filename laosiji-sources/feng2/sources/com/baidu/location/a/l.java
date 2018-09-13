package com.baidu.location.a;

class l implements Runnable {
    final /* synthetic */ k a;

    l(k kVar) {
        this.a = kVar;
    }

    public void run() {
        if (this.a.c != null && !this.a.h) {
            this.a.c.unregisterListener(k.d, this.a.c.getDefaultSensor(6));
            this.a.p = false;
        }
    }
}
