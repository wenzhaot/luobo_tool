package com.umeng.weixin.handler;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork;
import java.util.Map;

class o implements UMAuthListener {
    final /* synthetic */ UMAuthListener a;
    final /* synthetic */ UmengWXHandler b;

    o(UmengWXHandler umengWXHandler, UMAuthListener uMAuthListener) {
        this.b = umengWXHandler;
        this.a = uMAuthListener;
    }

    public void onCancel(SHARE_MEDIA share_media, int i) {
        this.b.getAuthListener(this.a).onCancel(share_media, i);
    }

    public void onComplete(SHARE_MEDIA share_media, int i, Map map) {
        QueuedWork.runInBack(new p(this), true);
    }

    public void onError(SHARE_MEDIA share_media, int i, Throwable th) {
        this.b.getAuthListener(this.a).onError(share_media, i, th);
    }

    public void onStart(SHARE_MEDIA share_media) {
    }
}
