package com.talkingdata.sdk;

import android.os.Message;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;

/* compiled from: td */
class g implements Runnable {
    final /* synthetic */ zz this$0;
    final /* synthetic */ a val$service;

    g(zz zzVar, a aVar) {
        this.this$0 = zzVar;
        this.val$service = aVar;
    }

    public void run() {
        try {
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(9));
            aVar.paraMap.put("domain", "account");
            aVar.paraMap.put(AuthActivity.ACTION_KEY, "logout");
            aVar.paraMap.put("service", this.val$service);
            aVar.paraMap.put("accountId", "");
            Message.obtain(zz.c(), 102, aVar).sendToTarget();
        } catch (Throwable th) {
        }
    }
}
