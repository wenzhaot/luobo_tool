package com.tendcloud.appcpa;

import android.text.TextUtils;
import com.talkingdata.sdk.aq;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: td */
public class Order extends JSONObject {
    private static final String keyAmount = "count";
    private static final String keyCategory = "category";
    public static final String keyCurrencyType = "keyCurrencyType";
    private static final String keyId = "id";
    private static final String keyName = "name";
    public static final String keyOrderDetail = "keyOrderDetail";
    public static final String keyOrderId = "keyOrderId";
    public static final String keyTotalPrice = "keyTotalPrice";
    private static final String keyUnitPrice = "unitPrice";
    private JSONArray orderDetails = null;

    private Order(String str, int i, String str2) {
        try {
            put(keyOrderId, str);
            put(keyTotalPrice, i);
            put(keyCurrencyType, str2);
        } catch (JSONException e) {
        }
    }

    private Order() {
    }

    public static Order createOrder(String str, int i, String str2) {
        try {
            aq.iForDeveloper("createOrder called --> orderId: " + str + " ,totalPrice: " + i + " ,currencyType: " + str2);
            if (TextUtils.isEmpty(str)) {
                aq.eForDeveloper("createOrder: orderId could not be null or empty");
            }
            if (TextUtils.isEmpty(str2) || str2.trim().length() != 3) {
                aq.eForDeveloper("createOrder: currencyType length must be 3 ,likes CNY");
            }
        } catch (Throwable th) {
        }
        return new Order(str, i, str2);
    }

    public synchronized Order addItem(String str, String str2, int i, int i2) {
        try {
            if (this.orderDetails == null) {
                this.orderDetails = new JSONArray();
                put(keyOrderDetail, this.orderDetails);
            }
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put("category", str);
            }
            if (!TextUtils.isEmpty(str2)) {
                jSONObject.put("name", str2);
            }
            jSONObject.put(keyUnitPrice, i);
            jSONObject.put("count", i2);
            this.orderDetails.put(jSONObject);
        } catch (JSONException e) {
        }
        return this;
    }

    public synchronized Order addItem(String str, String str2, String str3, int i, int i2) {
        try {
            if (this.orderDetails == null) {
                this.orderDetails = new JSONArray();
                put(keyOrderDetail, this.orderDetails);
            }
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put("id", str);
            }
            if (!TextUtils.isEmpty(str2)) {
                jSONObject.put("category", str2);
            }
            if (!TextUtils.isEmpty(str3)) {
                jSONObject.put("name", str3);
            }
            jSONObject.put(keyUnitPrice, i);
            jSONObject.put("count", i2);
            this.orderDetails.put(jSONObject);
        } catch (JSONException e) {
        }
        return this;
    }
}
