package com.umeng.weixin.handler;

import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.Map;

class k implements Runnable {
    final /* synthetic */ Map a;
    final /* synthetic */ UmengWXHandler b;

    k(UmengWXHandler umengWXHandler, Map map) {
        this.b = umengWXHandler;
        this.a = map;
    }

    public void run() {
        this.b.getAuthListener(this.b.j).onComplete(SHARE_MEDIA.WEIXIN, 0, this.a);
    }
}
