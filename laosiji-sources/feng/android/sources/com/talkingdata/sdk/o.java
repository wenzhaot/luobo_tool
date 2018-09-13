package com.talkingdata.sdk;

import android.os.Message;
import android.text.TextUtils;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;
import com.tendcloud.appcpa.Order;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONArray;

/* compiled from: td */
class o implements Runnable {
    final /* synthetic */ zz this$0;
    final /* synthetic */ String val$account;
    final /* synthetic */ Order val$order;
    final /* synthetic */ a val$service;

    o(zz zzVar, a aVar, String str, Order order) {
        this.this$0 = zzVar;
        this.val$service = aVar;
        this.val$account = str;
        this.val$order = order;
    }

    public void run() {
        try {
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(8));
            aVar.paraMap.put("service", this.val$service);
            aVar.paraMap.put("domain", "iap");
            aVar.paraMap.put(AuthActivity.ACTION_KEY, "placeOrder");
            Map treeMap = new TreeMap();
            treeMap.put("accountId", this.val$account);
            treeMap.put("orderId", this.val$order.getString(Order.keyOrderId));
            treeMap.put("amount", Integer.valueOf(this.val$order.optInt(Order.keyTotalPrice)));
            if (this.val$service.name().equals("TRACKING")) {
                treeMap.put("currencyType", this.val$order.optString(Order.keyCurrencyType));
            } else if (this.val$service.name().equals("APP") || this.val$service.name().equals("FINTECH")) {
                Object optString = this.val$order.optString(Order.keyCurrencyType);
                if (TextUtils.isEmpty(optString)) {
                    optString = "CNY";
                }
                treeMap.put("currencyType", optString);
            }
            if (this.val$order.has(Order.keyOrderDetail)) {
                JSONArray jSONArray = this.val$order.getJSONArray(Order.keyOrderDetail);
                if (jSONArray != null && jSONArray.length() > 0) {
                    treeMap.put("items", jSONArray);
                }
            }
            aVar.paraMap.put("data", treeMap);
            Message.obtain(zz.c(), 102, aVar).sendToTarget();
        } catch (Throwable th) {
        }
    }
}
