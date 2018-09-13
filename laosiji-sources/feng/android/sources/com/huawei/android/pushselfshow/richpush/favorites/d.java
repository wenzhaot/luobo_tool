package com.huawei.android.pushselfshow.richpush.favorites;

import com.huawei.android.pushagent.a.a.c;
import java.util.Iterator;

class d implements Runnable {
    final /* synthetic */ c a;

    d(c cVar) {
        this.a = cVar;
    }

    public void run() {
        Object obj;
        c.a("PushSelfShowLog", "deleteTipDialog mThread run");
        Object obj2 = null;
        Iterator it = this.a.a.h.a().iterator();
        while (true) {
            obj = obj2;
            if (!it.hasNext()) {
                break;
            }
            e eVar = (e) it.next();
            if (eVar.a()) {
                com.huawei.android.pushselfshow.utils.a.c.a(this.a.a.b, eVar.c());
                obj2 = 1;
            } else {
                obj2 = obj;
            }
        }
        if (obj != null) {
            if (!this.a.a.k) {
                this.a.a.h.b();
            }
            this.a.a.a.sendEmptyMessage(1001);
        }
    }
}
