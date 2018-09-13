package com.talkingdata.sdk;

import android.os.Message;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;

/* compiled from: td */
class t implements Runnable {
    final /* synthetic */ zz this$0;
    final /* synthetic */ String val$roleName;
    final /* synthetic */ a val$service;

    t(zz zzVar, String str, a aVar) {
        this.this$0 = zzVar;
        this.val$roleName = str;
        this.val$service = aVar;
    }

    public void run() {
        a aVar = new a();
        aVar.paraMap.put("apiType", Integer.valueOf(9));
        aVar.paraMap.put("domain", "account");
        aVar.paraMap.put(AuthActivity.ACTION_KEY, "roleCreate");
        aVar.paraMap.put("parameter", this.val$roleName);
        aVar.paraMap.put("service", this.val$service);
        Message.obtain(zz.c(), 102, aVar).sendToTarget();
    }
}
