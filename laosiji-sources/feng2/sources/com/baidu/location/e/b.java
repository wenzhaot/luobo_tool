package com.baidu.location.e;

import com.baidu.location.f;

class b implements Runnable {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public void run() {
        if (f.isServing) {
            if (!this.a.a) {
                this.a.d();
            }
            this.a.e();
        }
    }
}
