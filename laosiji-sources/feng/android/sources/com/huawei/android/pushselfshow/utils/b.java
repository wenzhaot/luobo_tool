package com.huawei.android.pushselfshow.utils;

import android.content.Context;
import com.huawei.android.pushagent.a.a.c;

final class b implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;

    b(Context context, String str, String str2, String str3) {
        this.a = context;
        this.b = str;
        this.c = str2;
        this.d = str3;
    }

    public void run() {
        try {
            if (a.m(this.a)) {
                String str = "PUSH_PS";
                String stringBuffer = new StringBuffer(String.valueOf(a.a())).append("|").append("PS").append("|").append(a.b(this.a)).append("|").append(this.b).append("|").append(this.c).append("|").append(a.a(this.a)).append("|").append(this.d).toString();
                if (this.a != null) {
                    c.b("PushSelfShowLog", "run normal sendHiAnalytics");
                    Class cls = Class.forName("com.hianalytics.android.v1.HiAnalytics");
                    cls.getMethod("onEvent", new Class[]{Context.class, String.class, String.class}).invoke(cls, new Object[]{this.a, str, stringBuffer});
                    cls.getMethod("onReport", new Class[]{Context.class}).invoke(cls, new Object[]{this.a});
                    c.a("PushSelfShowLog", "send HiAnalytics msg, report cmd =" + this.d + ", msgid = " + this.b + ", eventId = " + this.c);
                    return;
                }
                c.a("PushSelfShowLog", "send HiAnalytics msg, report cmd =" + this.d + ",context = " + this.a);
                return;
            }
            c.a("PushSelfShowLog", "not allowed to sendHiAnalytics!");
        } catch (Throwable e) {
            c.d("PushSelfShowLog", "sendHiAnalytics IllegalAccessException ", e);
        } catch (Throwable e2) {
            c.d("PushSelfShowLog", "sendHiAnalytics IllegalArgumentException ", e2);
        } catch (Throwable e22) {
            c.d("PushSelfShowLog", "sendHiAnalytics InvocationTargetException", e22);
        } catch (Throwable e222) {
            c.d("PushSelfShowLog", "sendHiAnalytics NoSuchMethodException", e222);
        } catch (Throwable e2222) {
            c.d("PushSelfShowLog", "sendHiAnalytics ClassNotFoundException", e2222);
        }
    }
}
