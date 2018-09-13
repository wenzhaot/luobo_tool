package com.talkingdata.sdk;

import android.os.Message;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: td */
class l implements Runnable {
    final /* synthetic */ zz this$0;
    final /* synthetic */ String val$account;
    final /* synthetic */ a val$service;

    l(zz zzVar, a aVar, String str) {
        this.this$0 = zzVar;
        this.val$service = aVar;
        this.val$account = str;
    }

    public void run() {
        try {
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(8));
            aVar.paraMap.put("service", this.val$service);
            aVar.paraMap.put("domain", "iap");
            aVar.paraMap.put(AuthActivity.ACTION_KEY, "pay");
            Map treeMap = new TreeMap();
            treeMap.put("accountId", this.val$account);
            aVar.paraMap.put("data", treeMap);
            Message.obtain(zz.c(), 102, aVar).sendToTarget();
        } catch (Throwable th) {
        }
    }
}
