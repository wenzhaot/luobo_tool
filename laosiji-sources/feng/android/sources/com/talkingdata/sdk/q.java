package com.talkingdata.sdk;

import android.os.Message;
import android.text.TextUtils;
import com.feng.car.utils.HttpConstant;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: td */
class q implements Runnable {
    final /* synthetic */ zz this$0;
    final /* synthetic */ String val$category;
    final /* synthetic */ String val$itemId;
    final /* synthetic */ String val$name;
    final /* synthetic */ a val$service;
    final /* synthetic */ int val$unitPrice;

    q(zz zzVar, a aVar, String str, String str2, String str3, int i) {
        this.this$0 = zzVar;
        this.val$service = aVar;
        this.val$itemId = str;
        this.val$category = str2;
        this.val$name = str3;
        this.val$unitPrice = i;
    }

    public void run() {
        try {
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(8));
            aVar.paraMap.put("service", this.val$service);
            aVar.paraMap.put("domain", "iap");
            aVar.paraMap.put(AuthActivity.ACTION_KEY, "viewItem");
            Map treeMap = new TreeMap();
            if (this.val$service.name().equals("APP") || this.val$service.name().equals("FINTECH")) {
                if (!TextUtils.isEmpty(this.val$itemId)) {
                    treeMap.put("id", this.val$itemId);
                }
                if (!TextUtils.isEmpty(this.val$category)) {
                    treeMap.put(HttpConstant.CATEGORY, this.val$category);
                }
                if (!TextUtils.isEmpty(this.val$name)) {
                    treeMap.put("name", this.val$name);
                }
                treeMap.put("unitPrice", Integer.valueOf(this.val$unitPrice));
            } else {
                treeMap.put("id", this.val$itemId);
                treeMap.put(HttpConstant.CATEGORY, this.val$category);
                treeMap.put("name", this.val$name);
                treeMap.put("unitPrice", Integer.valueOf(this.val$unitPrice));
            }
            aVar.paraMap.put("data", treeMap);
            Message.obtain(zz.c(), 102, aVar).sendToTarget();
        } catch (Throwable th) {
        }
    }
}
