package com.talkingdata.sdk;

import android.app.Activity;
import android.content.Context;
import com.stub.StubApp;
import com.talkingdata.sdk.af.AccountType;
import com.tendcloud.appcpa.Order;
import com.tendcloud.appcpa.ShoppingCart;

/* compiled from: td */
public final class ac {
    private static ao a;

    public static synchronized void a(Context context, a aVar) {
        synchronized (ac.class) {
            try {
                e(context, aVar);
                a.a(context, aVar);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return;
    }

    public static synchronized void a(Context context, String str, String str2, a aVar) {
        synchronized (ac.class) {
            try {
                e(context, aVar);
                a.a(context, str, str2, aVar);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return;
    }

    public static String b(Context context, a aVar) {
        return ab.a(context, aVar);
    }

    public static String c(Context context, a aVar) {
        return ab.b(context, aVar);
    }

    public static boolean a() {
        return zz.a;
    }

    public static synchronized String d(Context context, a aVar) {
        String b;
        synchronized (ac.class) {
            try {
                e(context, aVar);
                b = a.b(context, aVar);
            } catch (Throwable th) {
                th.printStackTrace();
                b = null;
            }
        }
        return b;
    }

    public static synchronized String a(Context context) {
        String str = null;
        synchronized (ac.class) {
            try {
                e(context, null);
                str = a.b(context);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return str;
    }

    public static Context b() {
        return a.d();
    }

    public static void c() {
        try {
            aq.a = false;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(Activity activity, String str, String str2, a aVar) {
        try {
            e(activity, aVar);
            a.a(activity, str, str2, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(Activity activity, a aVar) {
        try {
            e(activity, aVar);
            a.a(activity, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void b(Activity activity, a aVar) {
        try {
            e(activity, aVar);
            a.b(activity, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static int b(Context context) {
        return aw.b(context);
    }

    private static synchronized void e(Context context, a aVar) {
        synchronized (ac.class) {
            if (context != null) {
                ab.g = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            if (ab.g == null) {
                aq.eForDeveloper("Init failed Context is null ");
            } else if (a == null) {
                System.currentTimeMillis();
                a = zz.a();
                if (aVar != null) {
                }
            }
        }
    }

    public static void a(String str, a aVar) {
        try {
            e(ab.g, aVar);
            a.a(str, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(String str, AccountType accountType, String str2, a aVar) {
        try {
            e(ab.g, aVar);
            a.b(str, accountType, str2, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void b(String str, a aVar) {
        try {
            e(ab.g, aVar);
            a.b(str, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void b(String str, AccountType accountType, String str2, a aVar) {
        try {
            e(ab.g, aVar);
            a.a(str, accountType, str2, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void c(String str, a aVar) {
        try {
            e(ab.g, aVar);
            a.e(str, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(String str, String str2, int i, String str3, String str4, a aVar) {
        try {
            e(ab.g, aVar);
            a.b(str, str2, i, str3, str4, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(String str, String str2, Order order, a aVar) {
        try {
            e(ab.g, aVar);
            a.a(str, str2, order, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(String str, String str2, int i, String str3, String str4, Order order, a aVar) {
        try {
            e(ab.g, aVar);
            a.a(str, str2, i, str3, str4, order, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(String str, String str2, int i, String str3, String str4, String str5, int i2, a aVar) {
        try {
            e(ab.g, aVar);
            a.a(str, str2, i, str3, str4, str5, i2, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(String str, Order order, a aVar) {
        try {
            e(ab.g, aVar);
            a.a(str, order, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void b(String str, String str2, int i, String str3, String str4, a aVar) {
        try {
            e(ab.g, aVar);
            a.a(str, str2, i, str3, str4, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(String str, String str2, String str3, int i, int i2, a aVar) {
        try {
            e(ab.g, aVar);
            a.a(str, str2, str3, i, i2, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(String str, String str2, String str3, int i, a aVar) {
        try {
            e(ab.g, aVar);
            a.a(str, str2, str3, i, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(ShoppingCart shoppingCart, a aVar) {
        try {
            e(ab.g, aVar);
            a.a(shoppingCart, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void d(String str, a aVar) {
        try {
            e(ab.g, aVar);
            a.d(str, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
