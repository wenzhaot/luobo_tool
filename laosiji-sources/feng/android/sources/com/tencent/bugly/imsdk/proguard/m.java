package com.tencent.bugly.imsdk.proguard;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tencent.bugly.imsdk.crashreport.common.info.a;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public final class m {
    public static final long a = System.currentTimeMillis();
    private static m b = null;
    private Context c;
    private String d = a.b().d;
    private Map<Integer, Map<String, l>> e = new HashMap();
    private SharedPreferences f;

    private m(Context context) {
        this.c = context;
        this.f = context.getSharedPreferences("crashrecord", 0);
    }

    public static synchronized m a(Context context) {
        m mVar;
        synchronized (m.class) {
            if (b == null) {
                b = new m(context);
            }
            mVar = b;
        }
        return mVar;
    }

    public static synchronized m a() {
        m mVar;
        synchronized (m.class) {
            mVar = b;
        }
        return mVar;
    }

    private synchronized boolean b(int i) {
        boolean z;
        try {
            List<l> c = c(i);
            if (c == null) {
                z = false;
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                List arrayList = new ArrayList();
                Collection arrayList2 = new ArrayList();
                for (l lVar : c) {
                    if (lVar.b != null && lVar.b.equalsIgnoreCase(this.d) && lVar.d > 0) {
                        arrayList.add(lVar);
                    }
                    if (lVar.c + 86400000 < currentTimeMillis) {
                        arrayList2.add(lVar);
                    }
                }
                Collections.sort(arrayList);
                if (arrayList.size() < 2) {
                    c.removeAll(arrayList2);
                    a(i, (List) c);
                    z = false;
                } else if (arrayList.size() <= 0 || ((l) arrayList.get(arrayList.size() - 1)).c + 86400000 >= currentTimeMillis) {
                    z = true;
                } else {
                    c.clear();
                    a(i, (List) c);
                    z = false;
                }
            }
        } catch (Exception e) {
            w.e("isFrequentCrash failed", new Object[0]);
            z = false;
        }
        return z;
    }

    public final synchronized void a(int i, final int i2) {
        v.a().a(new Runnable(1004) {
            public final void run() {
                try {
                    if (!TextUtils.isEmpty(m.this.d)) {
                        l lVar;
                        l lVar2;
                        List a = m.this.c(1004);
                        List arrayList;
                        if (a == null) {
                            arrayList = new ArrayList();
                        } else {
                            arrayList = a;
                        }
                        if (m.this.e.get(Integer.valueOf(1004)) == null) {
                            m.this.e.put(Integer.valueOf(1004), new HashMap());
                        }
                        if (((Map) m.this.e.get(Integer.valueOf(1004))).get(m.this.d) == null) {
                            l lVar3 = new l();
                            lVar3.a = (long) 1004;
                            lVar3.g = m.a;
                            lVar3.b = m.this.d;
                            lVar3.f = a.b().j;
                            a.b().getClass();
                            lVar3.e = "2.4.0";
                            lVar3.c = System.currentTimeMillis();
                            lVar3.d = i2;
                            ((Map) m.this.e.get(Integer.valueOf(1004))).put(m.this.d, lVar3);
                            lVar = lVar3;
                        } else {
                            lVar2 = (l) ((Map) m.this.e.get(Integer.valueOf(1004))).get(m.this.d);
                            lVar2.d = i2;
                            lVar = lVar2;
                        }
                        Collection arrayList2 = new ArrayList();
                        int i = 0;
                        for (l lVar22 : arrayList) {
                            if (lVar22.g == lVar.g && lVar22.b != null && lVar22.b.equalsIgnoreCase(lVar.b)) {
                                i = 1;
                                lVar22.d = lVar.d;
                            }
                            if ((lVar22.e != null && !lVar22.e.equalsIgnoreCase(lVar.e)) || ((lVar22.f != null && !lVar22.f.equalsIgnoreCase(lVar.f)) || lVar22.d <= 0)) {
                                arrayList2.add(lVar22);
                            }
                        }
                        arrayList.removeAll(arrayList2);
                        if (i == 0) {
                            arrayList.add(lVar);
                        }
                        m.this.a(1004, (List) arrayList);
                    }
                } catch (Exception e) {
                    w.e("saveCrashRecord failed", new Object[0]);
                }
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0070 A:{Catch:{ Exception -> 0x003b }} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0054 A:{SYNTHETIC, Splitter: B:23:0x0054} */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0068 A:{SYNTHETIC, Splitter: B:33:0x0068} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0070 A:{Catch:{ Exception -> 0x003b }} */
    private synchronized <T extends java.util.List<?>> T c(int r7) {
        /*
        r6 = this;
        r1 = 0;
        monitor-enter(r6);
        r0 = new java.io.File;	 Catch:{ Exception -> 0x003b }
        r2 = r6.c;	 Catch:{ Exception -> 0x003b }
        r3 = "crashrecord";
        r4 = 0;
        r2 = r2.getDir(r3, r4);	 Catch:{ Exception -> 0x003b }
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x003b }
        r3.<init>();	 Catch:{ Exception -> 0x003b }
        r3 = r3.append(r7);	 Catch:{ Exception -> 0x003b }
        r3 = r3.toString();	 Catch:{ Exception -> 0x003b }
        r0.<init>(r2, r3);	 Catch:{ Exception -> 0x003b }
        r2 = r0.exists();	 Catch:{ Exception -> 0x003b }
        if (r2 != 0) goto L_0x0027;
    L_0x0024:
        r0 = r1;
    L_0x0025:
        monitor-exit(r6);
        return r0;
    L_0x0027:
        r2 = new java.io.ObjectInputStream;	 Catch:{ IOException -> 0x0047, ClassNotFoundException -> 0x005b, all -> 0x006c }
        r3 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0047, ClassNotFoundException -> 0x005b, all -> 0x006c }
        r3.<init>(r0);	 Catch:{ IOException -> 0x0047, ClassNotFoundException -> 0x005b, all -> 0x006c }
        r2.<init>(r3);	 Catch:{ IOException -> 0x0047, ClassNotFoundException -> 0x005b, all -> 0x006c }
        r0 = r2.readObject();	 Catch:{ IOException -> 0x007d, ClassNotFoundException -> 0x007b }
        r0 = (java.util.List) r0;	 Catch:{ IOException -> 0x007d, ClassNotFoundException -> 0x007b }
        r2.close();	 Catch:{ Exception -> 0x003b }
        goto L_0x0025;
    L_0x003b:
        r0 = move-exception;
        r0 = "readCrashRecord error";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ all -> 0x0058 }
        com.tencent.bugly.imsdk.proguard.w.e(r0, r2);	 Catch:{ all -> 0x0058 }
    L_0x0045:
        r0 = r1;
        goto L_0x0025;
    L_0x0047:
        r0 = move-exception;
        r0 = r1;
    L_0x0049:
        r2 = "open record file error";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x0076 }
        com.tencent.bugly.imsdk.proguard.w.a(r2, r3);	 Catch:{ all -> 0x0076 }
        if (r0 == 0) goto L_0x0045;
    L_0x0054:
        r0.close();	 Catch:{ Exception -> 0x003b }
        goto L_0x0045;
    L_0x0058:
        r0 = move-exception;
        monitor-exit(r6);
        throw r0;
    L_0x005b:
        r0 = move-exception;
        r2 = r1;
    L_0x005d:
        r0 = "get object error";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x0074 }
        com.tencent.bugly.imsdk.proguard.w.a(r0, r3);	 Catch:{ all -> 0x0074 }
        if (r2 == 0) goto L_0x0045;
    L_0x0068:
        r2.close();	 Catch:{ Exception -> 0x003b }
        goto L_0x0045;
    L_0x006c:
        r0 = move-exception;
        r2 = r1;
    L_0x006e:
        if (r2 == 0) goto L_0x0073;
    L_0x0070:
        r2.close();	 Catch:{ Exception -> 0x003b }
    L_0x0073:
        throw r0;	 Catch:{ Exception -> 0x003b }
    L_0x0074:
        r0 = move-exception;
        goto L_0x006e;
    L_0x0076:
        r2 = move-exception;
        r5 = r2;
        r2 = r0;
        r0 = r5;
        goto L_0x006e;
    L_0x007b:
        r0 = move-exception;
        goto L_0x005d;
    L_0x007d:
        r0 = move-exception;
        r0 = r2;
        goto L_0x0049;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.proguard.m.c(int):T");
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0059 A:{Catch:{ Exception -> 0x0033 }} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0051 A:{SYNTHETIC, Splitter: B:24:0x0051} */
    private synchronized <T extends java.util.List<?>> void a(int r5, T r6) {
        /*
        r4 = this;
        monitor-enter(r4);
        if (r6 != 0) goto L_0x0005;
    L_0x0003:
        monitor-exit(r4);
        return;
    L_0x0005:
        r0 = new java.io.File;	 Catch:{ Exception -> 0x0033 }
        r1 = r4.c;	 Catch:{ Exception -> 0x0033 }
        r2 = "crashrecord";
        r3 = 0;
        r1 = r1.getDir(r2, r3);	 Catch:{ Exception -> 0x0033 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0033 }
        r2.<init>();	 Catch:{ Exception -> 0x0033 }
        r2 = r2.append(r5);	 Catch:{ Exception -> 0x0033 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0033 }
        r0.<init>(r1, r2);	 Catch:{ Exception -> 0x0033 }
        r2 = 0;
        r1 = new java.io.ObjectOutputStream;	 Catch:{ IOException -> 0x0041, all -> 0x0055 }
        r3 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0041, all -> 0x0055 }
        r3.<init>(r0);	 Catch:{ IOException -> 0x0041, all -> 0x0055 }
        r1.<init>(r3);	 Catch:{ IOException -> 0x0041, all -> 0x0055 }
        r1.writeObject(r6);	 Catch:{ IOException -> 0x005f }
        r1.close();	 Catch:{ Exception -> 0x0033 }
        goto L_0x0003;
    L_0x0033:
        r0 = move-exception;
        r0 = "writeCrashRecord error";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ all -> 0x003e }
        com.tencent.bugly.imsdk.proguard.w.e(r0, r1);	 Catch:{ all -> 0x003e }
        goto L_0x0003;
    L_0x003e:
        r0 = move-exception;
        monitor-exit(r4);
        throw r0;
    L_0x0041:
        r0 = move-exception;
        r1 = r2;
    L_0x0043:
        r0.printStackTrace();	 Catch:{ all -> 0x005d }
        r0 = "open record file error";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ all -> 0x005d }
        com.tencent.bugly.imsdk.proguard.w.a(r0, r2);	 Catch:{ all -> 0x005d }
        if (r1 == 0) goto L_0x0003;
    L_0x0051:
        r1.close();	 Catch:{ Exception -> 0x0033 }
        goto L_0x0003;
    L_0x0055:
        r0 = move-exception;
        r1 = r2;
    L_0x0057:
        if (r1 == 0) goto L_0x005c;
    L_0x0059:
        r1.close();	 Catch:{ Exception -> 0x0033 }
    L_0x005c:
        throw r0;	 Catch:{ Exception -> 0x0033 }
    L_0x005d:
        r0 = move-exception;
        goto L_0x0057;
    L_0x005f:
        r0 = move-exception;
        goto L_0x0043;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.proguard.m.a(int, java.util.List):void");
    }

    public final synchronized boolean a(final int i) {
        boolean z = true;
        synchronized (this) {
            try {
                z = this.f.getBoolean(i + "_" + this.d, true);
                v.a().a(new Runnable() {
                    public final void run() {
                        m.this.f.edit().putBoolean(i + "_" + m.this.d, !m.this.b(i)).commit();
                    }
                });
            } catch (Exception e) {
                w.e("canInit error", new Object[0]);
            }
        }
        return z;
    }
}
