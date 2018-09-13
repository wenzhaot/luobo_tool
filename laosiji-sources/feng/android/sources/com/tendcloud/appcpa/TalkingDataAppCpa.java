package com.tendcloud.appcpa;

import android.content.Context;
import com.talkingdata.sdk.a;
import com.talkingdata.sdk.ac;
import com.taobao.accs.AccsClientConfig;

/* compiled from: td */
public class TalkingDataAppCpa {
    public static String TAG = "TDLOG";

    public static void init(Context context, String str, String str2) {
        ac.a(context, str, str2, a.TRACKING);
    }

    public static void setVerboseLogDisable() {
        ac.c();
    }

    public static String getDeviceId(Context context) {
        return ac.d(context, a.TRACKING);
    }

    public static void onRegister(String str) {
        ac.b(str, a.TRACKING);
    }

    public static void onLogin(String str) {
        ac.a(str, a.TRACKING);
    }

    public static void onCreateRole(String str) {
        ac.c(str, a.TRACKING);
    }

    public static void onPay(String str, String str2, int i, String str3) {
        onPay(str, str2, i, str3, AccsClientConfig.DEFAULT_CONFIGTAG);
    }

    public static void onPay(String str, String str2, int i, String str3, String str4) {
        ac.a(str, str2, i, str3, str4, a.TRACKING);
    }

    public static void onPay(String str, String str2, int i, String str3, String str4, Order order) {
        ac.a(str, str2, i, str3, str4, order, a.TRACKING);
    }

    public static void onPay(String str, String str2, int i, String str3, String str4, String str5, int i2) {
        ac.a(str, str2, i, str3, str4, str5, i2, a.TRACKING);
    }

    public static void onPlaceOrder(String str, Order order) {
        ac.a(str, order, a.TRACKING);
    }

    public static void onOrderPaySucc(String str, String str2, int i, String str3, String str4) {
        ac.b(str, str2, i, str3, str4, a.TRACKING);
    }

    public static void onAddItemToShoppingCart(String str, String str2, String str3, int i, int i2) {
        ac.a(str, str2, str3, i, i2, a.TRACKING);
    }

    public static void onViewItem(String str, String str2, String str3, int i) {
        ac.a(str, str2, str3, i, a.TRACKING);
    }

    public static void onViewShoppingCart(ShoppingCart shoppingCart) {
        ac.a(shoppingCart, a.TRACKING);
    }

    public static void onReceiveDeepLink(String str) {
        ac.d(str, a.TRACKING);
    }
}
