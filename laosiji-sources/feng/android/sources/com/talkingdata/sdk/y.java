package com.talkingdata.sdk;

import android.os.Message;
import com.talkingdata.sdk.af.AccountType;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;

/* compiled from: td */
class y implements Runnable {
    final /* synthetic */ zz this$0;
    final /* synthetic */ String val$account;
    final /* synthetic */ String val$name;
    final /* synthetic */ a val$service;
    final /* synthetic */ AccountType val$type;

    y(zz zzVar, a aVar, String str, AccountType accountType, String str2) {
        this.this$0 = zzVar;
        this.val$service = aVar;
        this.val$account = str;
        this.val$type = accountType;
        this.val$name = str2;
    }

    public void run() {
        try {
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(9));
            aVar.paraMap.put("domain", "account");
            aVar.paraMap.put(AuthActivity.ACTION_KEY, "login");
            aVar.paraMap.put("service", this.val$service);
            aVar.paraMap.put("accountId", this.val$account);
            if (this.val$type != null) {
                aVar.paraMap.put("type", this.val$type.name());
            }
            if (this.val$name != null) {
                aVar.paraMap.put("name", this.val$name);
            }
            Message.obtain(zz.c(), 102, aVar).sendToTarget();
        } catch (Throwable th) {
        }
    }
}
