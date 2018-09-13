package com.taobao.accs.init;

import android.app.Application;
import java.util.HashMap;

/* compiled from: Taobao */
class e implements Runnable {
    final /* synthetic */ HashMap a;
    final /* synthetic */ Application b;
    final /* synthetic */ Launcher_Login c;

    e(Launcher_Login launcher_Login, HashMap hashMap, Application application) {
        this.c = launcher_Login;
        this.a = hashMap;
        this.b = application;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0032 A:{Catch:{ Throwable -> 0x00a6 }} */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0032 A:{Catch:{ Throwable -> 0x00a6 }} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00b8  */
    public void run() {
        /*
        r8 = this;
        r3 = 1;
        r4 = 0;
        r2 = 0;
        r1 = r8.a;	 Catch:{ Throwable -> 0x0097 }
        r5 = "envIndex";
        r1 = r1.get(r5);	 Catch:{ Throwable -> 0x0097 }
        r1 = (java.lang.Integer) r1;	 Catch:{ Throwable -> 0x0097 }
        r6 = r1.intValue();	 Catch:{ Throwable -> 0x0097 }
        r1 = r8.a;	 Catch:{ Throwable -> 0x0097 }
        r5 = "onlineAppKey";
        r1 = r1.get(r5);	 Catch:{ Throwable -> 0x0097 }
        r1 = (java.lang.String) r1;	 Catch:{ Throwable -> 0x0097 }
        if (r6 != r3) goto L_0x007a;
    L_0x001f:
        r2 = r8.a;	 Catch:{ Throwable -> 0x00b3 }
        r3 = "preAppKey";
        r2 = r2.get(r3);	 Catch:{ Throwable -> 0x00b3 }
        r0 = r2;
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x00b3 }
        r1 = r0;
    L_0x002c:
        r2 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Throwable -> 0x00a6 }
        if (r2 == 0) goto L_0x00b8;
    L_0x0032:
        r1 = "Launcher_Login";
        r2 = "login get appkey null";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x00a6 }
        com.taobao.accs.utl.ALog.e(r1, r2, r3);	 Catch:{ Throwable -> 0x00a6 }
        r1 = "21646297";
        r2 = r1;
    L_0x0042:
        r1 = 1;
        com.taobao.accs.init.Launcher_InitAccs.mForceBindUser = r1;	 Catch:{ Throwable -> 0x00a6 }
        r1 = r8.a;	 Catch:{ Throwable -> 0x00a6 }
        r3 = "userId";
        r1 = r1.get(r3);	 Catch:{ Throwable -> 0x00a6 }
        r1 = (java.lang.String) r1;	 Catch:{ Throwable -> 0x00a6 }
        com.taobao.accs.init.Launcher_InitAccs.mUserId = r1;	 Catch:{ Throwable -> 0x00a6 }
        r1 = r8.a;	 Catch:{ Throwable -> 0x00a6 }
        r3 = "sid";
        r1 = r1.get(r3);	 Catch:{ Throwable -> 0x00a6 }
        r1 = (java.lang.String) r1;	 Catch:{ Throwable -> 0x00a6 }
        com.taobao.accs.init.Launcher_InitAccs.mSid = r1;	 Catch:{ Throwable -> 0x00a6 }
        r1 = r8.b;	 Catch:{ Throwable -> 0x00a6 }
        r3 = r1.getApplicationContext();	 Catch:{ Throwable -> 0x00a6 }
        r3 = com.stub.StubApp.getOrigApplicationContext(r3);	 Catch:{ Throwable -> 0x00a6 }
        r1 = r8.a;	 Catch:{ Throwable -> 0x00a6 }
        r5 = "ttid";
        r1 = r1.get(r5);	 Catch:{ Throwable -> 0x00a6 }
        r1 = (java.lang.String) r1;	 Catch:{ Throwable -> 0x00a6 }
        r5 = com.taobao.accs.init.Launcher_InitAccs.mAppReceiver;	 Catch:{ Throwable -> 0x00a6 }
        com.taobao.accs.ACCSManager.bindApp(r3, r2, r1, r5);	 Catch:{ Throwable -> 0x00a6 }
    L_0x0079:
        return;
    L_0x007a:
        r2 = 2;
        if (r6 != r2) goto L_0x0093;
    L_0x007d:
        r5 = r3;
    L_0x007e:
        r2 = 3;
        if (r6 != r2) goto L_0x0095;
    L_0x0081:
        r2 = r3;
    L_0x0082:
        r2 = r2 | r5;
        if (r2 == 0) goto L_0x002c;
    L_0x0085:
        r2 = r8.a;	 Catch:{ Throwable -> 0x00b3 }
        r3 = "dailyAppkey";
        r2 = r2.get(r3);	 Catch:{ Throwable -> 0x00b3 }
        r0 = r2;
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x00b3 }
        r1 = r0;
        goto L_0x002c;
    L_0x0093:
        r5 = r4;
        goto L_0x007e;
    L_0x0095:
        r2 = r4;
        goto L_0x0082;
    L_0x0097:
        r1 = move-exception;
    L_0x0098:
        r3 = "Launcher_Login";
        r5 = "login get param error";
        r6 = 0;
        r6 = new java.lang.Object[r6];	 Catch:{ Throwable -> 0x00a6 }
        com.taobao.accs.utl.ALog.e(r3, r5, r1, r6);	 Catch:{ Throwable -> 0x00a6 }
        r1 = r2;
        goto L_0x002c;
    L_0x00a6:
        r1 = move-exception;
        r2 = "Launcher_Login";
        r3 = "login";
        r4 = new java.lang.Object[r4];
        com.taobao.accs.utl.ALog.e(r2, r3, r1, r4);
        goto L_0x0079;
    L_0x00b3:
        r2 = move-exception;
        r7 = r2;
        r2 = r1;
        r1 = r7;
        goto L_0x0098;
    L_0x00b8:
        r2 = r1;
        goto L_0x0042;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.init.e.run():void");
    }
}
