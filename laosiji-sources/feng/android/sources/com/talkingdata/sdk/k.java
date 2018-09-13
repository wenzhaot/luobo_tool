package com.talkingdata.sdk;

import android.os.Message;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: td */
class k implements Runnable {
    final /* synthetic */ zz this$0;
    final /* synthetic */ String val$account;
    final /* synthetic */ int val$amount;
    final /* synthetic */ String val$currencyType;
    final /* synthetic */ int val$itemCount;
    final /* synthetic */ String val$itemId;
    final /* synthetic */ String val$orderid;
    final /* synthetic */ String val$payType;
    final /* synthetic */ a val$service;

    k(zz zzVar, a aVar, String str, String str2, int i, String str3, String str4, String str5, int i2) {
        this.this$0 = zzVar;
        this.val$service = aVar;
        this.val$account = str;
        this.val$orderid = str2;
        this.val$amount = i;
        this.val$currencyType = str3;
        this.val$payType = str4;
        this.val$itemId = str5;
        this.val$itemCount = i2;
    }

    public void run() {
        try {
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(8));
            aVar.paraMap.put("service", this.val$service);
            aVar.paraMap.put("domain", "iap");
            aVar.paraMap.put(AuthActivity.ACTION_KEY, "currencyPurchase");
            Map treeMap = new TreeMap();
            treeMap.put("accountId", this.val$account);
            treeMap.put("orderId", this.val$orderid);
            treeMap.put("amount", Integer.valueOf(this.val$amount));
            treeMap.put("currencyType", this.val$currencyType);
            treeMap.put("payType", this.val$payType);
            treeMap.put("itemId", this.val$itemId);
            treeMap.put("itemCount", Integer.valueOf(this.val$itemCount));
            aVar.paraMap.put("data", treeMap);
            Message.obtain(zz.c(), 102, aVar).sendToTarget();
        } catch (Throwable th) {
        }
    }
}
