package com.talkingdata.sdk;

import android.os.Message;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;

/* compiled from: td */
class w implements Runnable {
    final /* synthetic */ zz this$0;
    final /* synthetic */ String val$account;
    final /* synthetic */ a val$service;

    w(zz zzVar, a aVar, String str) {
        this.this$0 = zzVar;
        this.val$service = aVar;
        this.val$account = str;
    }

    public void run() {
        try {
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(9));
            aVar.paraMap.put("domain", "account");
            aVar.paraMap.put(AuthActivity.ACTION_KEY, "login");
            aVar.paraMap.put("service", this.val$service);
            aVar.paraMap.put("accountId", this.val$account);
            Message.obtain(zz.c(), 102, aVar).sendToTarget();
        } catch (Throwable th) {
        }
    }
}
