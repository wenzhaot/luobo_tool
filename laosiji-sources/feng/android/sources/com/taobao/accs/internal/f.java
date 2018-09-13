package com.taobao.accs.internal;

import com.taobao.accs.utl.ALog;

/* compiled from: Taobao */
class f implements Runnable {
    final /* synthetic */ b a;

    f(b bVar) {
        this.a = bVar;
    }

    public void run() {
        ALog.w("ElectionServiceImpl", "time out, onReportComplete", "pkgs", this.a.e);
        this.a.e();
    }
}
