package com.umeng.weixin.handler;

import android.os.Bundle;
import com.umeng.socialize.bean.UmengErrorCode;

class h implements Runnable {
    final /* synthetic */ Bundle a;
    final /* synthetic */ UmengWXHandler b;

    h(UmengWXHandler umengWXHandler, Bundle bundle) {
        this.b = umengWXHandler;
        this.a = bundle;
    }

    public void run() {
        this.b.getShareListener(this.b.n).onError(this.b.i, new Throwable(UmengErrorCode.UnKnowCode.getMessage() + this.a.getString("error")));
    }
}
