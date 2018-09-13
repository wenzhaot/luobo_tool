package com.umeng.weixin.handler;

import com.umeng.weixin.umengwx.a;
import com.umeng.weixin.umengwx.b;
import com.umeng.weixin.umengwx.e;
import com.umeng.weixin.umengwx.i;
import com.umeng.weixin.umengwx.k;

class l implements e {
    final /* synthetic */ UmengWXHandler a;

    l(UmengWXHandler umengWXHandler) {
        this.a = umengWXHandler;
    }

    public void a(a aVar) {
    }

    public void a(b bVar) {
        switch (bVar.a()) {
            case 1:
                this.a.a((i) bVar);
                return;
            case 2:
                this.a.a((k) bVar);
                return;
            default:
                return;
        }
    }
}
