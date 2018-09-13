package com.talkingdata.sdk;

import android.os.Message;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;
import com.tendcloud.appcpa.Order;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: td */
class m implements Runnable {
    final /* synthetic */ zz this$0;
    final /* synthetic */ String val$account;
    final /* synthetic */ int val$amount;
    final /* synthetic */ String val$currencyType;
    final /* synthetic */ Order val$order;
    final /* synthetic */ String val$orderid;
    final /* synthetic */ String val$payType;
    final /* synthetic */ a val$service;

    m(zz zzVar, a aVar, String str, String str2, int i, String str3, String str4, Order order) {
        this.this$0 = zzVar;
        this.val$service = aVar;
        this.val$account = str;
        this.val$orderid = str2;
        this.val$amount = i;
        this.val$currencyType = str3;
        this.val$payType = str4;
        this.val$order = order;
    }

    public void run() {
        try {
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(8));
            aVar.paraMap.put("service", this.val$service);
            aVar.paraMap.put("domain", "iap");
            aVar.paraMap.put(AuthActivity.ACTION_KEY, "recharge");
            Map treeMap = new TreeMap();
            treeMap.put("accountId", this.val$account);
            treeMap.put("orderId", this.val$orderid);
            treeMap.put("amount", Integer.valueOf(this.val$amount));
            treeMap.put("currencyType", this.val$currencyType);
            treeMap.put("payType", this.val$payType);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("orderId", this.val$order.getString(Order.keyOrderId));
            jSONObject.put("amount", this.val$order.getInt(Order.keyTotalPrice));
            jSONObject.put("currencyType", this.val$order.getString(Order.keyCurrencyType));
            if (this.val$order.has(Order.keyOrderDetail)) {
                JSONArray jSONArray = this.val$order.getJSONArray(Order.keyOrderDetail);
                if (jSONArray != null && jSONArray.length() > 0) {
                    jSONObject.put("items", jSONArray);
                }
            }
            treeMap.put("order", jSONObject);
            aVar.paraMap.put("data", treeMap);
            Message.obtain(zz.c(), 102, aVar).sendToTarget();
        } catch (Throwable th) {
        }
    }
}
