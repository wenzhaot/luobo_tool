package com.talkingdata.sdk;

import android.app.Activity;
import android.content.Context;
import com.talkingdata.sdk.af.AccountType;
import com.tendcloud.appcpa.Order;
import com.tendcloud.appcpa.ShoppingCart;

/* compiled from: td */
public interface ao {
    void a(Activity activity, a aVar);

    void a(Activity activity, String str, String str2, a aVar);

    void a(Context context, a aVar);

    void a(Context context, String str, String str2, a aVar);

    void a(ShoppingCart shoppingCart, a aVar);

    void a(String str, a aVar);

    void a(String str, AccountType accountType, a aVar);

    void a(String str, AccountType accountType, String str2, a aVar);

    void a(String str, Order order, a aVar);

    void a(String str, String str2, int i, String str3, String str4, a aVar);

    void a(String str, String str2, int i, String str3, String str4, Order order, a aVar);

    void a(String str, String str2, int i, String str3, String str4, String str5, int i2, a aVar);

    void a(String str, String str2, Order order, a aVar);

    void a(String str, String str2, String str3, int i, int i2, a aVar);

    void a(String str, String str2, String str3, int i, a aVar);

    String b(Context context);

    String b(Context context, a aVar);

    void b(Activity activity, a aVar);

    void b(String str, a aVar);

    void b(String str, AccountType accountType, a aVar);

    void b(String str, AccountType accountType, String str2, a aVar);

    void b(String str, String str2, int i, String str3, String str4, a aVar);

    String c(Context context, a aVar);

    void c(String str, a aVar);

    void c(String str, AccountType accountType, a aVar);

    Context d();

    String d(Context context, a aVar);

    void d(String str, a aVar);

    void e();

    void e(String str, a aVar);

    void onLogout(a aVar);
}
