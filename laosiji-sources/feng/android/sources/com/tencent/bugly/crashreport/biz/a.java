package com.tencent.bugly.crashreport.biz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import com.feng.car.utils.FengConstant;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.k;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.t;
import com.tencent.bugly.proguard.u;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: BUGLY */
public final class a {
    private Context a;
    private long b;
    private int c;
    private boolean d = true;

    /* compiled from: BUGLY */
    class a implements Runnable {
        private boolean a;
        private UserInfoBean b;

        public a(UserInfoBean userInfoBean, boolean z) {
            this.b = userInfoBean;
            this.a = z;
        }

        public final void run() {
            try {
                if (this.b != null) {
                    UserInfoBean userInfoBean = this.b;
                    if (userInfoBean != null) {
                        com.tencent.bugly.crashreport.common.info.a b = com.tencent.bugly.crashreport.common.info.a.b();
                        if (b != null) {
                            userInfoBean.j = b.e();
                        }
                    }
                    x.c("[UserInfo] Record user info.", new Object[0]);
                    a.a(a.this, this.b, false);
                }
                if (this.a) {
                    a aVar = a.this;
                    w a = w.a();
                    if (a != null) {
                        a.a(new Runnable() {
                            public final void run() {
                                try {
                                    a.this.c();
                                } catch (Throwable th) {
                                    x.a(th);
                                }
                            }
                        });
                    }
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    /* compiled from: BUGLY */
    class b implements Runnable {
        b() {
        }

        public final void run() {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < a.this.b) {
                w.a().a(new b(), (a.this.b - currentTimeMillis) + 5000);
                return;
            }
            a.this.a(3, false, 0);
            a.this.a();
        }
    }

    /* compiled from: BUGLY */
    class c implements Runnable {
        private long a = 21600000;

        public c(long j) {
            this.a = j;
        }

        public final void run() {
            a aVar = a.this;
            w a = w.a();
            if (a != null) {
                a.a(/* anonymous class already generated */);
            }
            aVar = a.this;
            long j = this.a;
            w.a().a(new c(j), j);
        }
    }

    public a(Context context, boolean z) {
        this.a = context;
        this.d = z;
    }

    public final void a(int i, boolean z, long j) {
        int i2 = 1;
        com.tencent.bugly.crashreport.common.strategy.a a = com.tencent.bugly.crashreport.common.strategy.a.a();
        if (a == null || a.c().h || i == 1 || i == 3) {
            if (i == 1 || i == 3) {
                this.c++;
            }
            com.tencent.bugly.crashreport.common.info.a a2 = com.tencent.bugly.crashreport.common.info.a.a(this.a);
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.b = i;
            userInfoBean.c = a2.d;
            userInfoBean.d = a2.g();
            userInfoBean.e = System.currentTimeMillis();
            userInfoBean.f = -1;
            userInfoBean.n = a2.j;
            if (i != 1) {
                i2 = 0;
            }
            userInfoBean.o = i2;
            userInfoBean.l = a2.a();
            userInfoBean.m = a2.p;
            userInfoBean.g = a2.q;
            userInfoBean.h = a2.r;
            userInfoBean.i = a2.s;
            userInfoBean.k = a2.t;
            userInfoBean.r = a2.B();
            userInfoBean.s = a2.G();
            userInfoBean.p = a2.H();
            userInfoBean.q = a2.I();
            w.a().a(new a(userInfoBean, z), 0);
            return;
        }
        x.e("UserInfo is disable", new Object[0]);
    }

    public final void a() {
        this.b = z.b() + 86400000;
        w.a().a(new b(), (this.b - System.currentTimeMillis()) + 5000);
    }

    private synchronized void c() {
        boolean z = false;
        synchronized (this) {
            if (this.d) {
                u a = u.a();
                if (a != null) {
                    com.tencent.bugly.crashreport.common.strategy.a a2 = com.tencent.bugly.crashreport.common.strategy.a.a();
                    if (a2 != null && (!a2.b() || a.b(1001))) {
                        boolean z2;
                        List list;
                        String str = com.tencent.bugly.crashreport.common.info.a.a(this.a).d;
                        List arrayList = new ArrayList();
                        List a3 = a(str);
                        if (a3 != null) {
                            int i;
                            int i2;
                            UserInfoBean userInfoBean;
                            int size = a3.size() - 20;
                            if (size > 0) {
                                for (int i3 = 0; i3 < a3.size() - 1; i3++) {
                                    i = i3 + 1;
                                    while (true) {
                                        i2 = i;
                                        if (i2 >= a3.size()) {
                                            break;
                                        }
                                        if (((UserInfoBean) a3.get(i3)).e > ((UserInfoBean) a3.get(i2)).e) {
                                            userInfoBean = (UserInfoBean) a3.get(i3);
                                            a3.set(i3, a3.get(i2));
                                            a3.set(i2, userInfoBean);
                                        }
                                        i = i2 + 1;
                                    }
                                }
                                for (i = 0; i < size; i++) {
                                    arrayList.add(a3.get(i));
                                }
                            }
                            Iterator it = a3.iterator();
                            i2 = 0;
                            while (it.hasNext()) {
                                userInfoBean = (UserInfoBean) it.next();
                                if (userInfoBean.f != -1) {
                                    it.remove();
                                    if (userInfoBean.e < z.b()) {
                                        arrayList.add(userInfoBean);
                                    }
                                }
                                if (userInfoBean.e <= System.currentTimeMillis() - 600000 || !(userInfoBean.b == 1 || userInfoBean.b == 4 || userInfoBean.b == 3)) {
                                    i = i2;
                                } else {
                                    i = i2 + 1;
                                }
                                i2 = i;
                            }
                            if (i2 > 15) {
                                x.d("[UserInfo] Upload user info too many times in 10 min: %d", Integer.valueOf(i2));
                                z2 = false;
                            } else {
                                z2 = true;
                            }
                            list = a3;
                        } else {
                            Object list2 = new ArrayList();
                            z2 = true;
                        }
                        if (arrayList.size() > 0) {
                            a(arrayList);
                        }
                        if (!z2 || list2.size() == 0) {
                            x.c("[UserInfo] There is no user info in local database.", new Object[0]);
                        } else {
                            x.c("[UserInfo] Upload user info(size: %d)", Integer.valueOf(list2.size()));
                            k a4 = com.tencent.bugly.proguard.a.a(list2, this.c == 1 ? 1 : 2);
                            if (a4 == null) {
                                x.d("[UserInfo] Failed to create UserInfoPackage.", new Object[0]);
                            } else {
                                byte[] a5 = com.tencent.bugly.proguard.a.a(a4);
                                if (a5 == null) {
                                    x.d("[UserInfo] Failed to encode data.", new Object[0]);
                                } else {
                                    am a6 = com.tencent.bugly.proguard.a.a(this.a, a.a ? 840 : FengConstant.IMAGEMIDDLEWIDTH, a5);
                                    if (a6 == null) {
                                        x.d("[UserInfo] Request package is null.", new Object[0]);
                                    } else {
                                        t anonymousClass1 = new t() {
                                            public final void a(boolean z) {
                                                if (z) {
                                                    x.c("[UserInfo] Successfully uploaded user info.", new Object[0]);
                                                    long currentTimeMillis = System.currentTimeMillis();
                                                    for (UserInfoBean userInfoBean : list2) {
                                                        userInfoBean.f = currentTimeMillis;
                                                        a.a(a.this, userInfoBean, true);
                                                    }
                                                }
                                            }
                                        };
                                        StrategyBean c = com.tencent.bugly.crashreport.common.strategy.a.a().c();
                                        String str2 = a.a ? c.r : c.t;
                                        String str3 = a.a ? StrategyBean.b : StrategyBean.a;
                                        u a7 = u.a();
                                        if (this.c == 1) {
                                            z = true;
                                        }
                                        a7.a(1001, a6, str2, str3, anonymousClass1, z);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public final void b() {
        w a = w.a();
        if (a != null) {
            a.a(/* anonymous class already generated */);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x008c A:{Splitter: B:12:0x0038, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:34:?, code:
            com.tencent.bugly.proguard.x.d("[Database] unknown id.", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:36:0x008c, code:
            r0 = th;
     */
    public final java.util.List<com.tencent.bugly.crashreport.biz.UserInfoBean> a(java.lang.String r10) {
        /*
        r9 = this;
        r7 = 0;
        r0 = com.tencent.bugly.proguard.z.a(r10);	 Catch:{ Throwable -> 0x00d4, all -> 0x00ce }
        if (r0 == 0) goto L_0x0020;
    L_0x0007:
        r3 = r7;
    L_0x0008:
        r0 = com.tencent.bugly.proguard.p.a();	 Catch:{ Throwable -> 0x00d4, all -> 0x00ce }
        r1 = "t_ui";
        r2 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 1;
        r8 = r0.a(r1, r2, r3, r4, r5, r6);	 Catch:{ Throwable -> 0x00d4, all -> 0x00ce }
        if (r8 != 0) goto L_0x0038;
    L_0x0019:
        if (r8 == 0) goto L_0x001e;
    L_0x001b:
        r8.close();
    L_0x001e:
        r0 = r7;
    L_0x001f:
        return r0;
    L_0x0020:
        r0 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00d4, all -> 0x00ce }
        r1 = "_pc = '";
        r0.<init>(r1);	 Catch:{ Throwable -> 0x00d4, all -> 0x00ce }
        r0 = r0.append(r10);	 Catch:{ Throwable -> 0x00d4, all -> 0x00ce }
        r1 = "'";
        r0 = r0.append(r1);	 Catch:{ Throwable -> 0x00d4, all -> 0x00ce }
        r3 = r0.toString();	 Catch:{ Throwable -> 0x00d4, all -> 0x00ce }
        goto L_0x0008;
    L_0x0038:
        r0 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        r0.<init>();	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        r6 = new java.util.ArrayList;	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        r6.<init>();	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
    L_0x0042:
        r1 = r8.moveToNext();	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        if (r1 == 0) goto L_0x0093;
    L_0x0048:
        r1 = a(r8);	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        if (r1 == 0) goto L_0x0064;
    L_0x004e:
        r6.add(r1);	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        goto L_0x0042;
    L_0x0052:
        r0 = move-exception;
        r1 = r8;
    L_0x0054:
        r2 = com.tencent.bugly.proguard.x.a(r0);	 Catch:{ all -> 0x00d1 }
        if (r2 != 0) goto L_0x005d;
    L_0x005a:
        r0.printStackTrace();	 Catch:{ all -> 0x00d1 }
    L_0x005d:
        if (r1 == 0) goto L_0x0062;
    L_0x005f:
        r1.close();
    L_0x0062:
        r0 = r7;
        goto L_0x001f;
    L_0x0064:
        r1 = "_id";
        r1 = r8.getColumnIndex(r1);	 Catch:{ Throwable -> 0x0081, all -> 0x008c }
        r2 = r8.getLong(r1);	 Catch:{ Throwable -> 0x0081, all -> 0x008c }
        r1 = " or _id";
        r1 = r0.append(r1);	 Catch:{ Throwable -> 0x0081, all -> 0x008c }
        r4 = " = ";
        r1 = r1.append(r4);	 Catch:{ Throwable -> 0x0081, all -> 0x008c }
        r1.append(r2);	 Catch:{ Throwable -> 0x0081, all -> 0x008c }
        goto L_0x0042;
    L_0x0081:
        r1 = move-exception;
        r1 = "[Database] unknown id.";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        com.tencent.bugly.proguard.x.d(r1, r2);	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        goto L_0x0042;
    L_0x008c:
        r0 = move-exception;
    L_0x008d:
        if (r8 == 0) goto L_0x0092;
    L_0x008f:
        r8.close();
    L_0x0092:
        throw r0;
    L_0x0093:
        r0 = r0.toString();	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        r1 = r0.length();	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        if (r1 <= 0) goto L_0x00c6;
    L_0x009d:
        r1 = 4;
        r2 = r0.substring(r1);	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        r0 = com.tencent.bugly.proguard.p.a();	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        r1 = "t_ui";
        r3 = 0;
        r4 = 0;
        r5 = 1;
        r0 = r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        r1 = "[Database] deleted %s error data %d";
        r2 = 2;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        r3 = 0;
        r4 = "t_ui";
        r2[r3] = r4;	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        r3 = 1;
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        r2[r3] = r0;	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
        com.tencent.bugly.proguard.x.d(r1, r2);	 Catch:{ Throwable -> 0x0052, all -> 0x008c }
    L_0x00c6:
        if (r8 == 0) goto L_0x00cb;
    L_0x00c8:
        r8.close();
    L_0x00cb:
        r0 = r6;
        goto L_0x001f;
    L_0x00ce:
        r0 = move-exception;
        r8 = r7;
        goto L_0x008d;
    L_0x00d1:
        r0 = move-exception;
        r8 = r1;
        goto L_0x008d;
    L_0x00d4:
        r0 = move-exception;
        r1 = r7;
        goto L_0x0054;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.biz.a.a(java.lang.String):java.util.List<com.tencent.bugly.crashreport.biz.UserInfoBean>");
    }

    private static void a(List<UserInfoBean> list) {
        if (list != null && list.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            while (i < list.size() && i < 50) {
                stringBuilder.append(" or _id").append(" = ").append(((UserInfoBean) list.get(i)).a);
                i++;
            }
            String stringBuilder2 = stringBuilder.toString();
            if (stringBuilder2.length() > 0) {
                stringBuilder2 = stringBuilder2.substring(4);
            }
            stringBuilder.setLength(0);
            try {
                int a = p.a().a("t_ui", stringBuilder2, null, null, true);
                x.c("[Database] deleted %s data %d", "t_ui", Integer.valueOf(a));
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static ContentValues a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (userInfoBean.a > 0) {
                contentValues.put("_id", Long.valueOf(userInfoBean.a));
            }
            contentValues.put("_tm", Long.valueOf(userInfoBean.e));
            contentValues.put("_ut", Long.valueOf(userInfoBean.f));
            contentValues.put("_tp", Integer.valueOf(userInfoBean.b));
            contentValues.put("_pc", userInfoBean.c);
            contentValues.put("_dt", z.a((Parcelable) userInfoBean));
            return contentValues;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private static UserInfoBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            UserInfoBean userInfoBean = (UserInfoBean) z.a(blob, UserInfoBean.CREATOR);
            if (userInfoBean == null) {
                return userInfoBean;
            }
            userInfoBean.a = j;
            return userInfoBean;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }
}
