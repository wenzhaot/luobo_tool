package com.umeng.weixin.handler;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;

class b implements Runnable {
    final /* synthetic */ UMAuthListener a;
    final /* synthetic */ String b;
    final /* synthetic */ UmengWXHandler c;

    b(UmengWXHandler umengWXHandler, UMAuthListener uMAuthListener, String str) {
        this.c = umengWXHandler;
        this.a = uMAuthListener;
        this.b = str;
    }

    public void run() {
        this.c.getAuthListener(this.a).onError(SHARE_MEDIA.WEIXIN, 2, new Throwable(UmengErrorCode.RequestForUserProfileFailed.getMessage() + this.b));
    }
}
