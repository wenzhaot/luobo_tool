package com.umeng.weixin.handler;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import java.util.Map;

class n implements Runnable {
    final /* synthetic */ Map a;
    final /* synthetic */ m b;

    n(m mVar, Map map) {
        this.b = mVar;
        this.a = map;
    }

    public void run() {
        if (this.a.get("errcode") != null) {
            this.b.c.getAuthListener(this.b.b).onError(SHARE_MEDIA.WEIXIN, 0, new Throwable(UmengErrorCode.RequestForUserProfileFailed.getMessage() + ((String) this.a.get("errmsg"))));
            return;
        }
        this.b.c.getAuthListener(this.b.b).onComplete(SHARE_MEDIA.WEIXIN, 0, this.a);
    }
}
