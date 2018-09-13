package com.taobao.agoo.a.a;

import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.e.a;

/* compiled from: Taobao */
public class c extends b {
    public static final String JSON_CMD_REGISTER = "register";
    public String a;
    public String b;
    public String c;
    public String d = String.valueOf(Constants.SDK_VERSION_CODE);
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    public String n;
    public String o;
    public String p;

    public byte[] a() {
        byte[] bArr = null;
        try {
            ALog.i("RegisterDO", "buildData", "data", new a().a(b.JSON_CMD, this.e).a(Constants.KEY_APP_KEY, this.a).a("utdid", this.b).a(Constants.KEY_APP_VERSION, this.c).a(Constants.KEY_SDK_VERSION, this.d).a(Constants.KEY_TTID, this.f).a(Constants.KEY_PACKAGE_NAME, this.g).a("notifyEnable", this.h).a("romInfo", this.i).a("c0", this.j).a("c1", this.k).a("c2", this.l).a("c3", this.m).a("c4", this.n).a("c5", this.o).a("c6", this.p).a().toString());
            return new a().a(b.JSON_CMD, this.e).a(Constants.KEY_APP_KEY, this.a).a("utdid", this.b).a(Constants.KEY_APP_VERSION, this.c).a(Constants.KEY_SDK_VERSION, this.d).a(Constants.KEY_TTID, this.f).a(Constants.KEY_PACKAGE_NAME, this.g).a("notifyEnable", this.h).a("romInfo", this.i).a("c0", this.j).a("c1", this.k).a("c2", this.l).a("c3", this.m).a("c4", this.n).a("c5", this.o).a("c6", this.p).a().toString().getBytes(com.qiniu.android.common.Constants.UTF_8);
        } catch (Throwable th) {
            ALog.e("RegisterDO", "buildData", th, new Object[0]);
            return bArr;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00bd  */
    public static byte[] a(android.content.Context r8, java.lang.String r9, java.lang.String r10) {
        /*
        r1 = 0;
        r0 = com.taobao.accs.utl.UtilityImpl.getDeviceId(r8);	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        r3 = r8.getPackageName();	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        r2 = com.taobao.accs.client.GlobalClientInfo.getInstance(r8);	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        r2 = r2.getPackageInfo();	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        r4 = r2.versionName;	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        r2 = android.text.TextUtils.isEmpty(r9);	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        if (r2 != 0) goto L_0x0025;
    L_0x0019:
        r2 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        if (r2 != 0) goto L_0x0025;
    L_0x001f:
        r2 = android.text.TextUtils.isEmpty(r4);	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        if (r2 == 0) goto L_0x0052;
    L_0x0025:
        r2 = "RegisterDO";
        r3 = "buildRegister param null";
        r5 = 6;
        r5 = new java.lang.Object[r5];	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        r6 = 0;
        r7 = "appKey";
        r5[r6] = r7;	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        r6 = 1;
        r5[r6] = r9;	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        r6 = 2;
        r7 = "utdid";
        r5[r6] = r7;	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        r6 = 3;
        r5[r6] = r0;	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        r0 = 4;
        r6 = "appVersion";
        r5[r0] = r6;	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        r0 = 5;
        r5[r0] = r4;	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        com.taobao.accs.utl.ALog.e(r2, r3, r5);	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        if (r1 == 0) goto L_0x0051;
    L_0x004e:
        r1.a();
    L_0x0051:
        return r1;
    L_0x0052:
        r2 = new com.taobao.agoo.a.a.c;	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        r2.<init>();	 Catch:{ Throwable -> 0x00a4, all -> 0x00b9 }
        r5 = "register";
        r2.e = r5;	 Catch:{ Throwable -> 0x00c3 }
        r2.a = r9;	 Catch:{ Throwable -> 0x00c3 }
        r2.b = r0;	 Catch:{ Throwable -> 0x00c3 }
        r2.c = r4;	 Catch:{ Throwable -> 0x00c3 }
        r2.f = r10;	 Catch:{ Throwable -> 0x00c3 }
        r2.g = r3;	 Catch:{ Throwable -> 0x00c3 }
        r0 = android.os.Build.BRAND;	 Catch:{ Throwable -> 0x00c3 }
        r2.j = r0;	 Catch:{ Throwable -> 0x00c3 }
        r0 = android.os.Build.MODEL;	 Catch:{ Throwable -> 0x00c3 }
        r2.k = r0;	 Catch:{ Throwable -> 0x00c3 }
        r0 = com.taobao.accs.utl.a.d(r8);	 Catch:{ Throwable -> 0x00c3 }
        r2.h = r0;	 Catch:{ Throwable -> 0x00c3 }
        r0 = new com.taobao.accs.utl.d;	 Catch:{ Throwable -> 0x00c3 }
        r0.<init>();	 Catch:{ Throwable -> 0x00c3 }
        r0 = r0.a();	 Catch:{ Throwable -> 0x00c3 }
        r2.i = r0;	 Catch:{ Throwable -> 0x00c3 }
        r0 = "phone";
        r0 = r8.getSystemService(r0);	 Catch:{ Throwable -> 0x00c3 }
        r0 = (android.telephony.TelephonyManager) r0;	 Catch:{ Throwable -> 0x00c3 }
        if (r0 == 0) goto L_0x00a0;
    L_0x008a:
        r3 = r0.getDeviceId();	 Catch:{ Throwable -> 0x00c3 }
    L_0x008e:
        r2.l = r3;	 Catch:{ Throwable -> 0x00c3 }
        if (r0 == 0) goto L_0x00a2;
    L_0x0092:
        r0 = r0.getSubscriberId();	 Catch:{ Throwable -> 0x00c3 }
    L_0x0096:
        r2.m = r0;	 Catch:{ Throwable -> 0x00c3 }
        if (r2 == 0) goto L_0x00c5;
    L_0x009a:
        r0 = r2.a();
    L_0x009e:
        r1 = r0;
        goto L_0x0051;
    L_0x00a0:
        r3 = r1;
        goto L_0x008e;
    L_0x00a2:
        r0 = r1;
        goto L_0x0096;
    L_0x00a4:
        r0 = move-exception;
        r2 = r1;
    L_0x00a6:
        r3 = "RegisterDO";
        r4 = "buildRegister";
        r5 = 0;
        r5 = new java.lang.Object[r5];	 Catch:{ all -> 0x00c1 }
        com.taobao.accs.utl.ALog.e(r3, r4, r0, r5);	 Catch:{ all -> 0x00c1 }
        if (r2 == 0) goto L_0x00c5;
    L_0x00b4:
        r0 = r2.a();
        goto L_0x009e;
    L_0x00b9:
        r0 = move-exception;
        r2 = r1;
    L_0x00bb:
        if (r2 == 0) goto L_0x00c0;
    L_0x00bd:
        r2.a();
    L_0x00c0:
        throw r0;
    L_0x00c1:
        r0 = move-exception;
        goto L_0x00bb;
    L_0x00c3:
        r0 = move-exception;
        goto L_0x00a6;
    L_0x00c5:
        r0 = r1;
        goto L_0x009e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.agoo.a.a.c.a(android.content.Context, java.lang.String, java.lang.String):byte[]");
    }
}
