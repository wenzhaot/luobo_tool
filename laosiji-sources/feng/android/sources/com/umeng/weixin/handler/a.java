package com.umeng.weixin.handler;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.UmengErrorCode;

class a implements Runnable {
    final /* synthetic */ UMShareListener a;
    final /* synthetic */ UmengWXHandler b;

    a(UmengWXHandler umengWXHandler, UMShareListener uMShareListener) {
        this.b = umengWXHandler;
        this.a = uMShareListener;
    }

    public void run() {
        this.b.getShareListener(this.a).onError(this.b.i, new Throwable(UmengErrorCode.NotInstall.getMessage()));
    }
}
