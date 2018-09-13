package com.baidu.location.a;

import com.baidu.location.f;
import com.baidu.location.f.g;

class n implements Runnable {
    final /* synthetic */ m a;

    n(m mVar) {
        this.a = mVar;
    }

    public void run() {
        if (g.j() || this.a.a(f.getServiceContext())) {
            this.a.d();
        }
    }
}
