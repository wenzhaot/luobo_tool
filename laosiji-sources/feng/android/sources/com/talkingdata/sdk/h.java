package com.talkingdata.sdk;

import android.os.Message;
import android.text.TextUtils;
import com.feng.car.utils.HttpConstant;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: td */
class h implements Runnable {
    final /* synthetic */ zz this$0;
    final /* synthetic */ int val$amount;
    final /* synthetic */ String val$category;
    final /* synthetic */ String val$itemId;
    final /* synthetic */ String val$name;
    final /* synthetic */ a val$service;
    final /* synthetic */ int val$unitPrice;

    h(zz zzVar, a aVar, String str, String str2, String str3, int i, int i2) {
        this.this$0 = zzVar;
        this.val$service = aVar;
        this.val$itemId = str;
        this.val$category = str2;
        this.val$name = str3;
        this.val$unitPrice = i;
        this.val$amount = i2;
    }

    public void run() {
        try {
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(8));
            aVar.paraMap.put("domain", "iap");
            aVar.paraMap.put(AuthActivity.ACTION_KEY, "addItem");
            aVar.paraMap.put("service", this.val$service);
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
                treeMap.put(HttpConstant.COUNT, Integer.valueOf(this.val$amount));
            } else {
                treeMap.put("id", this.val$itemId);
                treeMap.put(HttpConstant.CATEGORY, this.val$category);
                treeMap.put("name", this.val$name);
                treeMap.put("unitPrice", Integer.valueOf(this.val$unitPrice));
                treeMap.put(HttpConstant.COUNT, Integer.valueOf(this.val$amount));
            }
            aVar.paraMap.put("data", treeMap);
            Message.obtain(zz.c(), 102, aVar).sendToTarget();
        } catch (Throwable th) {
        }
    }
}
