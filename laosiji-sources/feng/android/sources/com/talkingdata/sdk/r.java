package com.talkingdata.sdk;

import android.os.Message;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;
import com.tendcloud.appcpa.ShoppingCart;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: td */
class r implements Runnable {
    final /* synthetic */ zz this$0;
    final /* synthetic */ a val$service;
    final /* synthetic */ ShoppingCart val$shoppingCart;

    r(zz zzVar, a aVar, ShoppingCart shoppingCart) {
        this.this$0 = zzVar;
        this.val$service = aVar;
        this.val$shoppingCart = shoppingCart;
    }

    public void run() {
        try {
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(8));
            aVar.paraMap.put("service", this.val$service);
            aVar.paraMap.put("domain", "iap");
            aVar.paraMap.put(AuthActivity.ACTION_KEY, "viewItems");
            Map treeMap = new TreeMap();
            treeMap.put("items", this.val$shoppingCart);
            aVar.paraMap.put("data", treeMap);
            Message.obtain(zz.c(), 102, aVar).sendToTarget();
        } catch (Throwable th) {
        }
    }
}
