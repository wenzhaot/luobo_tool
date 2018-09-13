package com.umeng.weixin.handler;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.UmengErrorCode;
import com.umeng.socialize.utils.UmengText.WX;

class g implements Runnable {
    final /* synthetic */ UMShareListener a;
    final /* synthetic */ UmengWXHandler b;

    g(UmengWXHandler umengWXHandler, UMShareListener uMShareListener) {
        this.b = umengWXHandler;
        this.a = uMShareListener;
    }

    public void run() {
        this.b.getShareListener(this.a).onError(this.b.i, new Throwable(UmengErrorCode.ShareDataTypeIllegal.getMessage() + WX.WX_CIRCLE_NOT_SUPPORT_EMOJ));
    }
}
