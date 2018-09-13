package com.talkingdata.sdk;

import android.os.Message;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;

/* compiled from: td */
class v implements Runnable {
    final /* synthetic */ zz this$0;
    final /* synthetic */ a val$service;

    v(zz zzVar, a aVar) {
        this.this$0 = zzVar;
        this.val$service = aVar;
    }

    public void run() {
        try {
            co.a();
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(1));
            aVar.paraMap.put("appId", zz.g != null ? zz.g : "");
            aVar.paraMap.put("channelId", zz.h != null ? zz.h : "");
            aVar.paraMap.put("service", this.val$service);
            aVar.paraMap.put(AuthActivity.ACTION_KEY, "init");
            Message.obtain(zz.c(), 101, aVar).sendToTarget();
            cq.a();
        } catch (Throwable th) {
        }
    }
}
