package com.taobao.accs.net;

import com.taobao.accs.utl.ALog;

/* compiled from: Taobao */
class e implements Runnable {
    final /* synthetic */ b a;

    e(b bVar) {
        this.a = bVar;
    }

    public void run() {
        if (this.a.e.c()) {
            ALog.e(this.a.d(), this.a.c + "receive ping time out! ", new Object[0]);
            g.a(this.a.d).c();
            this.a.a("", "receive ping timeout");
            this.a.e.a(-12);
        }
    }
}
