package com.huawei.android.pushselfshow.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.feng.car.utils.HttpConstant;
import com.huawei.android.pushagent.a.a.c;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.message.MsgConstant;
import com.umeng.socialize.net.dplus.CommonNetImpl;

public class a {
    private static final String[] a = new String[]{HttpConstant.PHONE, "url", "email", PushConstants.EXTRA_APPLICATION_PENDING_INTENT, "cosa", "rp"};
    private Context b;
    private com.huawei.android.pushselfshow.b.a c;

    public a(Context context, com.huawei.android.pushselfshow.b.a aVar) {
        this.b = context;
        this.c = aVar;
    }

    public static boolean a(String str) {
        for (String equals : a) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void b() {
        c.a("PushSelfShowLog", "enter launchUrl");
        try {
            if (!(this.c.H == 0 || this.c.I == null || this.c.I.length() <= 0)) {
                if (this.c.C.indexOf("?") != -1) {
                    this.c.C += "&" + this.c.I + "=" + com.huawei.android.pushselfshow.utils.a.a(com.huawei.android.pushselfshow.utils.a.b(this.b));
                } else {
                    this.c.C += "?" + this.c.I + "=" + com.huawei.android.pushselfshow.utils.a.a(com.huawei.android.pushselfshow.utils.a.b(this.b));
                }
            }
            c.a("PushSelfShowLog", "url =" + this.c.C);
            if (this.c.G == 0) {
                String str = this.c.C;
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW").setFlags(CommonNetImpl.FLAG_AUTH).setData(Uri.parse(str));
                this.b.startActivity(intent);
                return;
            }
            this.c.D = this.c.C;
            this.c.F = "text/html";
            this.c.E = "html";
            g();
        } catch (Throwable e) {
            c.c("PushSelfShowLog", e.toString(), e);
        }
    }

    private void c() {
        c.a("PushSelfShowLog", "enter launchCall");
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.DIAL").setData(Uri.parse("tel:" + this.c.w)).setFlags(CommonNetImpl.FLAG_AUTH);
            this.b.startActivity(intent);
        } catch (Throwable e) {
            c.c("PushSelfShowLog", e.toString(), e);
        }
    }

    private void d() {
        c.a("PushSelfShowLog", "enter launchMail");
        try {
            if (this.c.x != null) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SENDTO").setFlags(CommonNetImpl.FLAG_AUTH).setData(Uri.fromParts("mailto", this.c.x, null)).putExtra("android.intent.extra.SUBJECT", this.c.y).putExtra("android.intent.extra.TEXT", this.c.z).setPackage("com.android.email");
                this.b.startActivity(intent);
            }
        } catch (Throwable e) {
            c.c("PushSelfShowLog", e.toString(), e);
        }
    }

    private void e() {
        try {
            c.a("PushSelfShowLog", "enter launchApp, appPackageName =" + this.c.A);
            if (com.huawei.android.pushselfshow.utils.a.b(this.b, this.c.A)) {
                f();
                return;
            }
            try {
                c.e("PushSelfShowLog", "insert into db message.getMsgId() is " + this.c.a() + ",message.appPackageName is " + this.c.A);
                com.huawei.android.pushselfshow.utils.a.a.a(this.b, this.c.a(), this.c.A);
            } catch (Throwable e) {
                c.d("PushSelfShowLog", "launchApp not exist ,insertAppinfo error", e);
            }
            Intent intent = null;
            if (com.huawei.android.pushselfshow.utils.a.a(this.b, "com.huawei.appmarket", new Intent("com.huawei.appmarket.intent.action.AppDetail")).booleanValue()) {
                c.a("PushSelfShowLog", "app not exist && appmarkt exist ,so open appmarket");
                intent = new Intent("com.huawei.appmarket.intent.action.AppDetail");
                intent.putExtra("APP_PACKAGENAME", this.c.A);
                intent.setPackage("com.huawei.appmarket");
                intent.setFlags(402653184);
                c.a("PushSelfShowLog", "hwAppmarket only support com.huawei.appmarket.intent.action.AppDetail!");
                com.huawei.android.pushselfshow.utils.a.a(this.b, MsgConstant.MESSAGE_NOTIFY_ARRIVAL, this.c);
            }
            if (intent != null) {
                c.a("PushSelfShowLog", "intent is not null " + intent.toURI());
                this.b.startActivity(intent);
                return;
            }
            c.a("PushSelfShowLog", "intent is null ");
        } catch (Exception e2) {
            c.d("PushSelfShowLog", "launchApp error:" + e2.toString());
        }
    }

    /* JADX WARNING: Missing block: B:15:0x00b2, code:
            if (com.huawei.android.pushselfshow.utils.a.a(r5.b, r5.c.A, r0).booleanValue() != false) goto L_0x00b4;
     */
    private void f() {
        /*
        r5 = this;
        r0 = "PushSelfShowLog";
        r1 = "run into launchCosaApp ";
        com.huawei.android.pushagent.a.a.c.e(r0, r1);
        r0 = "PushSelfShowLog";
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc }
        r1.<init>();	 Catch:{ Exception -> 0x00bc }
        r2 = "enter launchExistApp cosa, appPackageName =";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x00bc }
        r2 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r2 = r2.A;	 Catch:{ Exception -> 0x00bc }
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x00bc }
        r2 = ",and msg.intentUri is ";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x00bc }
        r2 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r2 = r2.g;	 Catch:{ Exception -> 0x00bc }
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x00bc }
        r1 = r1.toString();	 Catch:{ Exception -> 0x00bc }
        com.huawei.android.pushagent.a.a.c.a(r0, r1);	 Catch:{ Exception -> 0x00bc }
        r0 = r5.b;	 Catch:{ Exception -> 0x00bc }
        r1 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r1 = r1.A;	 Catch:{ Exception -> 0x00bc }
        r1 = com.huawei.android.pushselfshow.utils.a.a(r0, r1);	 Catch:{ Exception -> 0x00bc }
        r0 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r0 = r0.g;	 Catch:{ Exception -> 0x00bc }
        if (r0 == 0) goto L_0x0095;
    L_0x0046:
        r0 = r5.c;	 Catch:{ Exception -> 0x0089 }
        r0 = r0.g;	 Catch:{ Exception -> 0x0089 }
        r2 = 0;
        r0 = android.content.Intent.parseUri(r0, r2);	 Catch:{ Exception -> 0x0089 }
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0089 }
        r3.<init>();	 Catch:{ Exception -> 0x0089 }
        r4 = "Intent.parseUri(msg.intentUri, 0)，";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0089 }
        r4 = r0.toURI();	 Catch:{ Exception -> 0x0089 }
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0089 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x0089 }
        com.huawei.android.pushagent.a.a.c.a(r2, r3);	 Catch:{ Exception -> 0x0089 }
        r2 = r5.b;	 Catch:{ Exception -> 0x0089 }
        r3 = r5.c;	 Catch:{ Exception -> 0x0089 }
        r3 = r3.A;	 Catch:{ Exception -> 0x0089 }
        r2 = com.huawei.android.pushselfshow.utils.a.a(r2, r3, r0);	 Catch:{ Exception -> 0x0089 }
        r2 = r2.booleanValue();	 Catch:{ Exception -> 0x0089 }
        if (r2 == 0) goto L_0x0105;
    L_0x007d:
        if (r0 != 0) goto L_0x00c8;
    L_0x007f:
        r0 = "PushSelfShowLog";
        r1 = "launchCosaApp,intent == null";
        com.huawei.android.pushagent.a.a.c.a(r0, r1);	 Catch:{ Exception -> 0x00bc }
    L_0x0088:
        return;
    L_0x0089:
        r0 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = "intentUri error ";
        com.huawei.android.pushagent.a.a.c.a(r2, r3, r0);	 Catch:{ Exception -> 0x00bc }
        r0 = r1;
        goto L_0x007d;
    L_0x0095:
        r0 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r0 = r0.B;	 Catch:{ Exception -> 0x00bc }
        if (r0 == 0) goto L_0x0103;
    L_0x009b:
        r0 = new android.content.Intent;	 Catch:{ Exception -> 0x00bc }
        r2 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r2 = r2.B;	 Catch:{ Exception -> 0x00bc }
        r0.<init>(r2);	 Catch:{ Exception -> 0x00bc }
        r2 = r5.b;	 Catch:{ Exception -> 0x00bc }
        r3 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r3 = r3.A;	 Catch:{ Exception -> 0x00bc }
        r2 = com.huawei.android.pushselfshow.utils.a.a(r2, r3, r0);	 Catch:{ Exception -> 0x00bc }
        r2 = r2.booleanValue();	 Catch:{ Exception -> 0x00bc }
        if (r2 == 0) goto L_0x0103;
    L_0x00b4:
        r1 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r1 = r1.A;	 Catch:{ Exception -> 0x00bc }
        r0.setPackage(r1);	 Catch:{ Exception -> 0x00bc }
        goto L_0x007d;
    L_0x00bc:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = r0.toString();
        com.huawei.android.pushagent.a.a.c.c(r1, r2, r0);
        goto L_0x0088;
    L_0x00c8:
        r1 = r5.b;	 Catch:{ Exception -> 0x00bc }
        r1 = com.huawei.android.pushselfshow.utils.a.a(r1, r0);	 Catch:{ Exception -> 0x00bc }
        if (r1 != 0) goto L_0x00da;
    L_0x00d0:
        r0 = "PushSelfShowLog";
        r1 = "no permission to start Activity";
        com.huawei.android.pushagent.a.a.c.c(r0, r1);	 Catch:{ Exception -> 0x00bc }
        goto L_0x0088;
    L_0x00da:
        r1 = 805437440; // 0x30020000 float:4.7293724E-10 double:3.97938969E-315;
        r0.setFlags(r1);	 Catch:{ Exception -> 0x00bc }
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc }
        r2.<init>();	 Catch:{ Exception -> 0x00bc }
        r3 = "start ";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x00bc }
        r3 = r0.toURI();	 Catch:{ Exception -> 0x00bc }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x00bc }
        r2 = r2.toString();	 Catch:{ Exception -> 0x00bc }
        com.huawei.android.pushagent.a.a.c.a(r1, r2);	 Catch:{ Exception -> 0x00bc }
        r1 = r5.b;	 Catch:{ Exception -> 0x00bc }
        r1.startActivity(r0);	 Catch:{ Exception -> 0x00bc }
        goto L_0x0088;
    L_0x0103:
        r0 = r1;
        goto L_0x00b4;
    L_0x0105:
        r0 = r1;
        goto L_0x007d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.a.a.f():void");
    }

    private void g() {
        try {
            c.e("PushSelfShowLog", "run into launchRichPush ");
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(this.b.getPackageName(), "com.huawei.android.pushselfshow.richpush.RichPushActivity"));
            intent.putExtra("type", this.c.E);
            intent.putExtra("selfshow_info", this.c.c());
            intent.putExtra("selfshow_token", this.c.d());
            intent.setFlags(268468240);
            intent.setPackage(this.b.getPackageName());
            this.b.startActivity(intent);
        } catch (Throwable e) {
            c.c("PushSelfShowLog", "launchRichPush failed", e);
        }
    }

    public void a() {
        c.a("PushSelfShowLog", "enter launchNotify()");
        if (this.b == null || this.c == null) {
            c.a("PushSelfShowLog", "launchNotify  context or msg is null");
        } else if (PushConstants.EXTRA_APPLICATION_PENDING_INTENT.equals(this.c.p)) {
            e();
        } else if ("cosa".equals(this.c.p)) {
            f();
        } else if ("email".equals(this.c.p)) {
            d();
        } else if (HttpConstant.PHONE.equals(this.c.p)) {
            c();
        } else if ("rp".equals(this.c.p)) {
            g();
        } else if ("url".equals(this.c.p)) {
            b();
        } else {
            c.a("PushSelfShowLog", this.c.p + " is not exist in hShowType");
        }
    }
}
