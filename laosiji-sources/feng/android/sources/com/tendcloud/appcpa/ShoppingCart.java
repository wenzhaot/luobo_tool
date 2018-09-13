package com.tendcloud.appcpa;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: td */
public class ShoppingCart extends JSONArray {
    private static final String keyCategory = "category";
    private static final String keyCount = "count";
    private static final String keyId = "id";
    private static final String keyName = "name";
    private static final String keyUnitPrice = "unitPrice";

    private ShoppingCart() {
    }

    public static ShoppingCart createShoppingCart() {
        return new ShoppingCart();
    }

    public synchronized ShoppingCart addItem(String str, String str2, String str3, int i, int i2) {
        try {
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
            put(jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }
}
