package com.talkingdata.sdk;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.talkingdata.sdk.af.AccountType;
import com.tendcloud.appcpa.Order;
import com.tendcloud.appcpa.ShoppingCart;
import java.util.HashMap;

/* compiled from: td */
public final class zz implements ao {
    public static volatile boolean a = false;
    public static volatile boolean b = false;
    static boolean c = false;
    public static d d = null;
    public static final int e = 102;
    private static volatile zz f = null;
    private static String g = null;
    private static String h = null;
    private static boolean i = false;
    private static final int j = 101;
    private static Handler k;
    private static final HandlerThread l = new HandlerThread("ProcessingThread");
    private static Handler m;
    private static final HandlerThread n = new HandlerThread("PauseEventThread");

    /* compiled from: td */
    public static class a {
        public HashMap paraMap = new HashMap();
    }

    static {
        k = null;
        m = null;
        l.start();
        k = new e(l.getLooper());
        n.start();
        m = new p(n.getLooper());
    }

    public zz() {
        f = this;
    }

    static synchronized zz a() {
        zz zzVar;
        synchronized (zz.class) {
            if (f == null) {
                synchronized (zz.class) {
                    if (f == null) {
                        f = new zz();
                    }
                }
            }
            zzVar = f;
        }
        return zzVar;
    }

    static Handler b() {
        return m;
    }

    public static Handler c() {
        return k;
    }

    public void a(Context context, a aVar) {
        a(context, null, null, aVar);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.talkingdata.sdk.zz.a(android.content.Context, java.lang.String, java.lang.String, com.talkingdata.sdk.a):void, dom blocks: [B:1:0x0002, B:11:0x0027]
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1378)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.base/java.lang.Iterable.forEach(Iterable.java:75)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:292)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        */
    public void a(android.content.Context r3, java.lang.String r4, java.lang.String r5, com.talkingdata.sdk.a r6) {
        /*
        r2 = this;
        if (r3 != 0) goto L_0x0009;
    L_0x0002:
        r0 = "Init failed Context is null";	 Catch:{ Throwable -> 0x0019 }
        com.talkingdata.sdk.aq.iForDeveloper(r0);	 Catch:{ Throwable -> 0x0019 }
    L_0x0008:
        return;	 Catch:{ Throwable -> 0x0019 }
    L_0x0009:
        r0 = "android.permission.INTERNET";	 Catch:{ Throwable -> 0x0019 }
        r0 = com.talkingdata.sdk.bo.b(r3, r0);	 Catch:{ Throwable -> 0x0019 }
        if (r0 != 0) goto L_0x001e;	 Catch:{ Throwable -> 0x0019 }
    L_0x0012:
        r0 = "[SDKInit] Permission \"android.permission.INTERNET\" is needed.";	 Catch:{ Throwable -> 0x0019 }
        com.talkingdata.sdk.aq.eForDeveloper(r0);	 Catch:{ Throwable -> 0x0019 }
        goto L_0x0008;
    L_0x0019:
        r0 = move-exception;
        com.talkingdata.sdk.cs.postSDKError(r0);
        goto L_0x0008;
    L_0x001e:
        if (r6 != 0) goto L_0x0027;
    L_0x0020:
        r0 = "Failed to initialize!";	 Catch:{ Throwable -> 0x0019 }
        com.talkingdata.sdk.aq.eForDeveloper(r0);	 Catch:{ Throwable -> 0x0019 }
        goto L_0x0008;
    L_0x0027:
        r0 = a;	 Catch:{ Throwable -> 0x0063 }
        if (r0 != 0) goto L_0x0089;	 Catch:{ Throwable -> 0x0063 }
    L_0x002b:
        r0 = r3.getApplicationContext();	 Catch:{ Throwable -> 0x0063 }
        r0 = com.stub.StubApp.getOrigApplicationContext(r0);	 Catch:{ Throwable -> 0x0063 }
        com.talkingdata.sdk.ab.g = r0;	 Catch:{ Throwable -> 0x0063 }
        g = r4;	 Catch:{ Throwable -> 0x0063 }
        h = r5;	 Catch:{ Throwable -> 0x0063 }
        r0 = "ChannelConfig.json";	 Catch:{ Throwable -> 0x0063 }
        r0 = com.talkingdata.sdk.bo.a(r3, r0);	 Catch:{ Throwable -> 0x0063 }
        r1 = com.talkingdata.sdk.bo.b(r0);	 Catch:{ Throwable -> 0x0063 }
        if (r1 != 0) goto L_0x006e;	 Catch:{ Throwable -> 0x0063 }
    L_0x0046:
        h = r0;	 Catch:{ Throwable -> 0x0063 }
        r0 = h;	 Catch:{ Throwable -> 0x0063 }
        r0 = com.talkingdata.sdk.bo.b(r0);	 Catch:{ Throwable -> 0x0063 }
        if (r0 != 0) goto L_0x0071;	 Catch:{ Throwable -> 0x0063 }
    L_0x0050:
        r0 = h;	 Catch:{ Throwable -> 0x0063 }
    L_0x0052:
        h = r0;	 Catch:{ Throwable -> 0x0063 }
        r0 = g;	 Catch:{ Throwable -> 0x0063 }
        r0 = com.talkingdata.sdk.bo.b(r0);	 Catch:{ Throwable -> 0x0063 }
        if (r0 == 0) goto L_0x0075;	 Catch:{ Throwable -> 0x0063 }
    L_0x005c:
        r0 = "[SDKInit] TD AppId is null";	 Catch:{ Throwable -> 0x0063 }
        com.talkingdata.sdk.aq.eForDeveloper(r0);	 Catch:{ Throwable -> 0x0063 }
        goto L_0x0008;
    L_0x0063:
        r0 = move-exception;
        r1 = "[SDKInit] Failed to initialize!";	 Catch:{ Throwable -> 0x0019 }
        com.talkingdata.sdk.aq.a(r1, r0);	 Catch:{ Throwable -> 0x0019 }
        com.talkingdata.sdk.cs.postSDKError(r0);	 Catch:{ Throwable -> 0x0019 }
        goto L_0x0008;
    L_0x006e:
        r0 = h;	 Catch:{ Throwable -> 0x0063 }
        goto L_0x0046;	 Catch:{ Throwable -> 0x0063 }
    L_0x0071:
        r0 = "Default";	 Catch:{ Throwable -> 0x0063 }
        goto L_0x0052;	 Catch:{ Throwable -> 0x0063 }
    L_0x0075:
        r0 = g;	 Catch:{ Throwable -> 0x0063 }
        r1 = h;	 Catch:{ Throwable -> 0x0063 }
        com.talkingdata.sdk.ab.a(r0, r1, r6);	 Catch:{ Throwable -> 0x0063 }
        r0 = com.talkingdata.sdk.ck.a();	 Catch:{ Throwable -> 0x0063 }
        r0.a(r4, r5, r6);	 Catch:{ Throwable -> 0x0063 }
        r2.a(r3);	 Catch:{ Throwable -> 0x0063 }
        r0 = 1;	 Catch:{ Throwable -> 0x0063 }
        a = r0;	 Catch:{ Throwable -> 0x0063 }
    L_0x0089:
        r0 = new com.talkingdata.sdk.v;	 Catch:{ Throwable -> 0x0063 }
        r0.<init>(r2, r6);	 Catch:{ Throwable -> 0x0063 }
        com.talkingdata.sdk.bo.execute(r0);	 Catch:{ Throwable -> 0x0063 }
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.zz.a(android.content.Context, java.lang.String, java.lang.String, com.talkingdata.sdk.a):void");
    }

    public String b(Context context, a aVar) {
        if (context != null) {
            try {
                if (!a) {
                    a(context, ab.a(context, aVar), ab.b(context, aVar), aVar);
                }
            } catch (Throwable th) {
                return null;
            }
        }
        return au.a(context);
    }

    public String b(Context context) {
        try {
            return au.a(context);
        } catch (Throwable th) {
            return null;
        }
    }

    public Context d() {
        try {
            return ab.g;
        } catch (Throwable th) {
            return null;
        }
    }

    public String c(Context context, a aVar) {
        return ab.a(context, aVar);
    }

    public String d(Context context, a aVar) {
        return ab.b(context, aVar);
    }

    public void a(Activity activity, a aVar) {
        try {
            if (!i || !ab.D) {
                ag.a(activity, aVar);
            }
        } catch (Throwable th) {
        }
    }

    public void a(Activity activity, String str, String str2, a aVar) {
        try {
            if (!a) {
                a((Context) activity, str, str2, aVar);
            }
            a(activity);
            a(activity, aVar);
        } catch (Throwable th) {
        }
    }

    public void b(Activity activity, a aVar) {
        try {
            if (a && !i) {
                a(activity);
                ag.b(activity, aVar);
            }
        } catch (Throwable th) {
        }
    }

    public void e() {
        try {
            aq.a = false;
        } catch (Throwable th) {
        }
    }

    public void a(String str, a aVar) {
        try {
            if (TextUtils.isEmpty(str)) {
                aq.eForDeveloper("onLogin: account could not be null or empty");
                return;
            }
            aq.iForDeveloper("onLogin called --> account is " + str);
            bo.execute(new w(this, aVar, str));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void a(String str, AccountType accountType, String str2, a aVar) {
        try {
            if (TextUtils.isEmpty(str)) {
                aq.eForDeveloper("onRegister: account could not be null or empty");
                return;
            }
            aq.iForDeveloper("onRegister called --> account is " + str + " ，type is " + accountType + " , name is " + str2);
            bo.execute(new x(this, aVar, str, accountType, str2));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void b(String str, AccountType accountType, String str2, a aVar) {
        try {
            if (TextUtils.isEmpty(str)) {
                aq.eForDeveloper("onLogin: account could not be null or empty");
                return;
            }
            aq.iForDeveloper("onLogin called --> account is " + str + " ，type is " + accountType + " , name is " + str2);
            bo.execute(new y(this, aVar, str, accountType, str2));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void a(String str, AccountType accountType, a aVar) {
        try {
            if (TextUtils.isEmpty(str)) {
                aq.eForDeveloper("onLogin: account could not be null or empty");
                return;
            }
            aq.iForDeveloper("onLogin called --> account is " + str + " ，type is " + accountType);
            bo.execute(new z(this, aVar, str, accountType));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void b(String str, a aVar) {
        try {
            if (TextUtils.isEmpty(str)) {
                aq.eForDeveloper("onRegister: account could not be null or empty");
                return;
            }
            aq.iForDeveloper("onRegister called --> account is " + str);
            bo.execute(new ad(this, aVar, str));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void b(String str, AccountType accountType, a aVar) {
        try {
            if (TextUtils.isEmpty(str)) {
                aq.eForDeveloper("onApply: account could not be null or empty");
                return;
            }
            aq.iForDeveloper("onApply called --> account is " + str + " ,type is " + accountType);
            bo.execute(new ae(this, aVar, str, accountType));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void c(String str, AccountType accountType, a aVar) {
        try {
            if (TextUtils.isEmpty(str)) {
                aq.eForDeveloper("onActivate: account could not be null or empty");
                return;
            }
            aq.iForDeveloper("onActivate called --> account is " + str + " ,type is " + accountType);
            bo.execute(new f(this, aVar, str, accountType));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void onLogout(a aVar) {
        try {
            aq.iForDeveloper("ModuleAccount.logout ");
            bo.execute(new g(this, aVar));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void a(String str, String str2, String str3, int i, int i2, a aVar) {
        try {
            aq.iForDeveloper("onAddItemToShoppingCart called --> itemId: " + str + " ,category: " + str2 + " ,name: " + str3 + " ,unitPrice: " + i + " ,amount: " + i2);
            bo.execute(new h(this, aVar, str, str2, str3, i, i2));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void a(String str, String str2, int i, String str3, String str4, a aVar) {
        if (str != null) {
            try {
                if (str.trim().length() > 0) {
                    aq.iForDeveloper("onOrderPaySucc called --> account: " + str + " ,orderid: " + str2 + " ,amount: " + i + " ,currencyType: " + str3 + " ,payType: " + str4);
                    if (str3.trim().length() != 3) {
                        aq.eForDeveloper("currencyType length must be 3 likes CNY so so");
                        return;
                    } else {
                        bo.execute(new i(this, aVar, str, str2, i, str3, str4));
                        return;
                    }
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
                return;
            }
        }
        aq.eForDeveloper("onOrderPaySucc: account could not be null or empty");
    }

    public void b(String str, String str2, int i, String str3, String str4, a aVar) {
        if (str != null) {
            try {
                if (str.trim().length() > 0) {
                    aq.iForDeveloper("onPay called --> account: " + str + " ,orderid: " + str2 + " ,amount: " + i + " ,currencyType: " + str3 + " ,payType: " + str4);
                    if (str3.trim().length() != 3) {
                        aq.eForDeveloper("currencyType length must be 3 likes CNY so so");
                        return;
                    } else {
                        bo.execute(new j(this, aVar, str, str2, i, str3, str4));
                        return;
                    }
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
                return;
            }
        }
        aq.eForDeveloper("onPay: account could not be null or empty");
    }

    public void a(String str, String str2, int i, String str3, String str4, String str5, int i2, a aVar) {
        if (str != null) {
            try {
                if (str.trim().length() > 0) {
                    aq.iForDeveloper("onPay called --> account: " + str + " ,orderid: " + str2 + " ,amount: " + i + " ,currencyType: " + str3 + " ,payType: " + str4 + " ,itemId: " + str5 + " ,itemCount: " + i2);
                    if (str3.trim().length() != 3) {
                        aq.eForDeveloper("currencyType length must be 3 likes CNY so so");
                        return;
                    } else {
                        bo.execute(new k(this, aVar, str, str2, i, str3, str4, str5, i2));
                        return;
                    }
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
                return;
            }
        }
        aq.eForDeveloper("onPay: account could not be null or empty");
    }

    public void c(String str, a aVar) {
        if (str != null) {
            try {
                if (str.trim().length() > 0) {
                    aq.iForDeveloper("onPay called --> accountID: " + str);
                    bo.execute(new l(this, aVar, str));
                    return;
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
                return;
            }
        }
        aq.eForDeveloper("onPay: account could not be null or empty");
    }

    public void a(String str, String str2, int i, String str3, String str4, Order order, a aVar) {
        if (str != null) {
            try {
                if (str.trim().length() > 0) {
                    if (order == null) {
                        aq.eForDeveloper("onPay: order could not be null");
                        return;
                    }
                    aq.iForDeveloper("onPay called --> account: " + str + " ,orderid: " + str2 + " ,amount: " + i + " ,currencyType: " + str3 + " ,payType: " + str4 + " ,order: " + order.toString());
                    if (str3.trim().length() != 3) {
                        aq.eForDeveloper("currencyType length must be 3 likes CNY so so");
                        return;
                    } else {
                        bo.execute(new m(this, aVar, str, str2, i, str3, str4, order));
                        return;
                    }
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
                return;
            }
        }
        aq.eForDeveloper("onPay: account could not be null or empty");
    }

    public void a(String str, String str2, Order order, a aVar) {
        if (str != null) {
            try {
                if (str.trim().length() > 0) {
                    if (order == null || order.optString(Order.keyOrderId).isEmpty()) {
                        aq.eForDeveloper("onPay: order or orderID could not be null or empty");
                        return;
                    }
                    aq.iForDeveloper("onPay called --> account: " + str + " ,payType: " + str2 + " ,order: " + order.toString());
                    bo.execute(new n(this, aVar, str, order, str2));
                    return;
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
                return;
            }
        }
        aq.eForDeveloper("onPay: account could not be null or empty");
    }

    public void a(String str, Order order, a aVar) {
        if (str != null) {
            try {
                if (str.trim().length() > 0) {
                    if (order == null || order.optString(Order.keyOrderId).isEmpty()) {
                        aq.eForDeveloper("onPlaceOrder: order or orderID could not be null or empty");
                        return;
                    }
                    aq.iForDeveloper("onPlaceOrder called --> account: " + str + " ,order: " + order.toString());
                    bo.execute(new o(this, aVar, str, order));
                    return;
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
                return;
            }
        }
        aq.eForDeveloper("onPlaceOrder: account could not be null or empty");
    }

    public void a(String str, String str2, String str3, int i, a aVar) {
        try {
            aq.iForDeveloper("onViewItem called --> itemId: " + str + " ,category: " + str2 + " ,name: " + str3 + " ,unitPrice: " + i);
            bo.execute(new q(this, aVar, str, str2, str3, i));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void a(ShoppingCart shoppingCart, a aVar) {
        try {
            aq.iForDeveloper("onViewShoppingCart called --> shoppingCart: " + shoppingCart);
            if (shoppingCart == null || shoppingCart.length() <= 0) {
                aq.eForDeveloper("viewShoppingCart# shoppingCart can't be null or empty");
            } else {
                bo.execute(new r(this, aVar, shoppingCart));
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void d(String str, a aVar) {
        try {
            if (TextUtils.isEmpty(str)) {
                aq.eForDeveloper("onReceiveDeepLink: url could not be null or empty");
                return;
            }
            aq.iForDeveloper("onReceiveDeepLink --> link: " + str);
            bo.execute(new s(this, str, aVar));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void e(String str, a aVar) {
        try {
            aq.iForDeveloper("createRole called --> roleName: " + str);
            bo.execute(new t(this, str, aVar));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.talkingdata.sdk.zz.a(android.content.Context):void, dom blocks: [B:3:0x0009, B:15:0x005b]
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1378)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.base/java.lang.Iterable.forEach(Iterable.java:75)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:292)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        */
    private void a(android.content.Context r7) {
        /*
        r6 = this;
        r0 = 14;
        r0 = com.talkingdata.sdk.bo.a(r0);
        if (r0 == 0) goto L_0x0056;
    L_0x0008:
        r0 = 0;
        r1 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x008c }
        r1 = r1 instanceof android.app.Activity;	 Catch:{ Throwable -> 0x008c }
        if (r1 == 0) goto L_0x004b;	 Catch:{ Throwable -> 0x008c }
    L_0x000f:
        r0 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x008c }
        r0 = (android.app.Activity) r0;	 Catch:{ Throwable -> 0x008c }
        r0 = r0.getApplication();	 Catch:{ Throwable -> 0x008c }
    L_0x0017:
        if (r0 == 0) goto L_0x004a;	 Catch:{ Throwable -> 0x008c }
    L_0x0019:
        r1 = i;	 Catch:{ Throwable -> 0x008c }
        if (r1 != 0) goto L_0x004a;	 Catch:{ Throwable -> 0x008c }
    L_0x001d:
        r1 = "android.app.Application$ActivityLifecycleCallbacks";	 Catch:{ Throwable -> 0x008c }
        r1 = java.lang.Class.forName(r1);	 Catch:{ Throwable -> 0x008c }
        r2 = r0.getClass();	 Catch:{ Throwable -> 0x008c }
        r3 = "registerActivityLifecycleCallbacks";	 Catch:{ Throwable -> 0x008c }
        r4 = 1;	 Catch:{ Throwable -> 0x008c }
        r4 = new java.lang.Class[r4];	 Catch:{ Throwable -> 0x008c }
        r5 = 0;	 Catch:{ Throwable -> 0x008c }
        r4[r5] = r1;	 Catch:{ Throwable -> 0x008c }
        r1 = r2.getMethod(r3, r4);	 Catch:{ Throwable -> 0x008c }
        r2 = new com.talkingdata.sdk.d;	 Catch:{ Throwable -> 0x008c }
        r2.<init>();	 Catch:{ Throwable -> 0x008c }
        d = r2;	 Catch:{ Throwable -> 0x008c }
        r2 = 1;	 Catch:{ Throwable -> 0x008c }
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x008c }
        r3 = 0;	 Catch:{ Throwable -> 0x008c }
        r4 = d;	 Catch:{ Throwable -> 0x008c }
        r2[r3] = r4;	 Catch:{ Throwable -> 0x008c }
        r1.invoke(r0, r2);	 Catch:{ Throwable -> 0x008c }
        r0 = 1;	 Catch:{ Throwable -> 0x008c }
        i = r0;	 Catch:{ Throwable -> 0x008c }
    L_0x004a:
        return;	 Catch:{ Throwable -> 0x008c }
    L_0x004b:
        r1 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x008c }
        r1 = r1 instanceof android.app.Application;	 Catch:{ Throwable -> 0x008c }
        if (r1 == 0) goto L_0x0017;	 Catch:{ Throwable -> 0x008c }
    L_0x0051:
        r0 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x008c }
        r0 = (android.app.Application) r0;	 Catch:{ Throwable -> 0x008c }
        goto L_0x0017;
    L_0x0056:
        r0 = new com.talkingdata.sdk.u;
        r0.<init>(r6, r7);
        r1 = "android.app.ActivityManagerNative";	 Catch:{ Throwable -> 0x006f }
        r1 = java.lang.Class.forName(r1);	 Catch:{ Throwable -> 0x006f }
        r2 = "gDefault";	 Catch:{ Throwable -> 0x006f }
        r3 = "android.app.IActivityManager";	 Catch:{ Throwable -> 0x006f }
        com.talkingdata.sdk.bo.a(r1, r0, r2, r3);	 Catch:{ Throwable -> 0x006f }
        r0 = 1;	 Catch:{ Throwable -> 0x006f }
        i = r0;	 Catch:{ Throwable -> 0x006f }
        goto L_0x004a;
    L_0x006f:
        r0 = move-exception;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "registerActivityLifecycleListener ";
        r1 = r1.append(r2);
        r0 = r0.getMessage();
        r0 = r1.append(r0);
        r0 = r0.toString();
        com.talkingdata.sdk.aq.eForDeveloper(r0);
        goto L_0x004a;
    L_0x008c:
        r0 = move-exception;
        goto L_0x004a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.zz.a(android.content.Context):void");
    }
}
