package com.umeng.weixin.handler;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.Map;

class e implements Runnable {
    final /* synthetic */ UMAuthListener a;
    final /* synthetic */ Map b;
    final /* synthetic */ UmengWXHandler c;

    e(UmengWXHandler umengWXHandler, UMAuthListener uMAuthListener, Map map) {
        this.c = umengWXHandler;
        this.a = uMAuthListener;
        this.b = map;
    }

    public void run() {
        this.c.getAuthListener(this.a).onComplete(SHARE_MEDIA.WEIXIN, 2, this.b);
    }
}
