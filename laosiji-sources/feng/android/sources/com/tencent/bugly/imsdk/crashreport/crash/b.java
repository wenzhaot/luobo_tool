package com.tencent.bugly.imsdk.crashreport.crash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import com.qiniu.android.common.Constants;
import com.tencent.bugly.imsdk.BuglyStrategy;
import com.tencent.bugly.imsdk.crashreport.common.info.PlugInBean;
import com.tencent.bugly.imsdk.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.imsdk.crashreport.common.strategy.a;
import com.tencent.bugly.imsdk.proguard.ag;
import com.tencent.bugly.imsdk.proguard.ai;
import com.tencent.bugly.imsdk.proguard.aj;
import com.tencent.bugly.imsdk.proguard.ak;
import com.tencent.bugly.imsdk.proguard.al;
import com.tencent.bugly.imsdk.proguard.j;
import com.tencent.bugly.imsdk.proguard.n;
import com.tencent.bugly.imsdk.proguard.o;
import com.tencent.bugly.imsdk.proguard.q;
import com.tencent.bugly.imsdk.proguard.s;
import com.tencent.bugly.imsdk.proguard.t;
import com.tencent.bugly.imsdk.proguard.w;
import com.tencent.bugly.imsdk.proguard.y;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/* compiled from: BUGLY */
public final class b {
    private static int a = 0;
    private Context b;
    private t c;
    private o d;
    private a e;
    private n f;
    private BuglyStrategy.a g;

    public b(int i, Context context, t tVar, o oVar, a aVar, BuglyStrategy.a aVar2, n nVar) {
        a = i;
        this.b = context;
        this.c = tVar;
        this.d = oVar;
        this.e = aVar;
        this.g = aVar2;
        this.f = nVar;
    }

    private static List<a> a(List<a> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        List<a> arrayList = new ArrayList();
        for (a aVar : list) {
            if (aVar.d && aVar.b <= currentTimeMillis - 86400000) {
                arrayList.add(aVar);
            }
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x014a  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00bb  */
    private com.tencent.bugly.imsdk.crashreport.crash.CrashDetailBean a(java.util.List<com.tencent.bugly.imsdk.crashreport.crash.a> r11, com.tencent.bugly.imsdk.crashreport.crash.CrashDetailBean r12) {
        /*
        r10 = this;
        r3 = 0;
        if (r11 == 0) goto L_0x0009;
    L_0x0003:
        r0 = r11.size();
        if (r0 != 0) goto L_0x000b;
    L_0x0009:
        r1 = r12;
    L_0x000a:
        return r1;
    L_0x000b:
        r1 = 0;
        r2 = new java.util.ArrayList;
        r0 = 10;
        r2.<init>(r0);
        r4 = r11.iterator();
    L_0x0017:
        r0 = r4.hasNext();
        if (r0 == 0) goto L_0x002b;
    L_0x001d:
        r0 = r4.next();
        r0 = (com.tencent.bugly.imsdk.crashreport.crash.a) r0;
        r5 = r0.e;
        if (r5 == 0) goto L_0x0017;
    L_0x0027:
        r2.add(r0);
        goto L_0x0017;
    L_0x002b:
        r0 = r2.size();
        if (r0 <= 0) goto L_0x0150;
    L_0x0031:
        r4 = r10.b(r2);
        if (r4 == 0) goto L_0x0150;
    L_0x0037:
        r0 = r4.size();
        if (r0 <= 0) goto L_0x0150;
    L_0x003d:
        java.util.Collections.sort(r4);
        r2 = r3;
    L_0x0041:
        r0 = r4.size();
        if (r2 >= r0) goto L_0x00a3;
    L_0x0047:
        r0 = r4.get(r2);
        r0 = (com.tencent.bugly.imsdk.crashreport.crash.CrashDetailBean) r0;
        if (r2 != 0) goto L_0x0054;
    L_0x004f:
        r1 = r2 + 1;
        r2 = r1;
        r1 = r0;
        goto L_0x0041;
    L_0x0054:
        r5 = r0.s;
        if (r5 == 0) goto L_0x014d;
    L_0x0058:
        r0 = r0.s;
        r5 = "\n";
        r5 = r0.split(r5);
        if (r5 == 0) goto L_0x014d;
    L_0x0063:
        r6 = r5.length;
        r0 = r3;
    L_0x0065:
        if (r0 >= r6) goto L_0x014d;
    L_0x0067:
        r7 = r5[r0];
        r8 = r1.s;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r9 = r9.append(r7);
        r9 = r9.toString();
        r8 = r8.contains(r9);
        if (r8 != 0) goto L_0x00a0;
    L_0x007e:
        r8 = r1.t;
        r8 = r8 + 1;
        r1.t = r8;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = r1.s;
        r8 = r8.append(r9);
        r7 = r8.append(r7);
        r8 = "\n";
        r7 = r7.append(r8);
        r7 = r7.toString();
        r1.s = r7;
    L_0x00a0:
        r0 = r0 + 1;
        goto L_0x0065;
    L_0x00a3:
        r0 = r1;
    L_0x00a4:
        if (r0 != 0) goto L_0x014a;
    L_0x00a6:
        r0 = 1;
        r12.j = r0;
        r12.t = r3;
        r0 = "";
        r12.s = r0;
        r1 = r12;
    L_0x00b1:
        r2 = r11.iterator();
    L_0x00b5:
        r0 = r2.hasNext();
        if (r0 == 0) goto L_0x0105;
    L_0x00bb:
        r0 = r2.next();
        r0 = (com.tencent.bugly.imsdk.crashreport.crash.a) r0;
        r3 = r0.e;
        if (r3 != 0) goto L_0x00b5;
    L_0x00c5:
        r3 = r0.d;
        if (r3 != 0) goto L_0x00b5;
    L_0x00c9:
        r3 = r1.s;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r6 = r0.b;
        r4 = r4.append(r6);
        r4 = r4.toString();
        r3 = r3.contains(r4);
        if (r3 != 0) goto L_0x00b5;
    L_0x00e0:
        r3 = r1.t;
        r3 = r3 + 1;
        r1.t = r3;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = r1.s;
        r3 = r3.append(r4);
        r4 = r0.b;
        r0 = r3.append(r4);
        r3 = "\n";
        r0 = r0.append(r3);
        r0 = r0.toString();
        r1.s = r0;
        goto L_0x00b5;
    L_0x0105:
        r2 = r1.r;
        r4 = r12.r;
        r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r0 == 0) goto L_0x000a;
    L_0x010d:
        r0 = r1.s;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r4 = r12.r;
        r2 = r2.append(r4);
        r2 = r2.toString();
        r0 = r0.contains(r2);
        if (r0 != 0) goto L_0x000a;
    L_0x0124:
        r0 = r1.t;
        r0 = r0 + 1;
        r1.t = r0;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r2 = r1.s;
        r0 = r0.append(r2);
        r2 = r12.r;
        r0 = r0.append(r2);
        r2 = "\n";
        r0 = r0.append(r2);
        r0 = r0.toString();
        r1.s = r0;
        goto L_0x000a;
    L_0x014a:
        r1 = r0;
        goto L_0x00b1;
    L_0x014d:
        r0 = r1;
        goto L_0x004f;
    L_0x0150:
        r0 = r1;
        goto L_0x00a4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.crash.b.a(java.util.List, com.tencent.bugly.imsdk.crashreport.crash.CrashDetailBean):com.tencent.bugly.imsdk.crashreport.crash.CrashDetailBean");
    }

    public final boolean a(CrashDetailBean crashDetailBean) {
        return a(crashDetailBean, -123456789);
    }

    public final boolean a(CrashDetailBean crashDetailBean, int i) {
        if (crashDetailBean == null) {
            return true;
        }
        if (!(c.l == null || c.l.isEmpty())) {
            w.c("Crash filter for crash stack is: %s", c.l);
            if (crashDetailBean.q.contains(c.l)) {
                w.d("This crash contains the filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        if (!(c.m == null || c.m.isEmpty())) {
            w.c("Crash regular filter for crash stack is: %s", c.m);
            if (Pattern.compile(c.m).matcher(crashDetailBean.q).find()) {
                w.d("This crash matches the regular filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        int i2 = crashDetailBean.b;
        String str = crashDetailBean.n;
        str = crashDetailBean.p;
        str = crashDetailBean.q;
        long j = crashDetailBean.r;
        str = crashDetailBean.m;
        str = crashDetailBean.e;
        str = crashDetailBean.c;
        if (this.f != null) {
            n nVar = this.f;
            String str2 = crashDetailBean.z;
            if (!nVar.c()) {
                return true;
            }
        }
        if (crashDetailBean.b != 2) {
            q qVar = new q();
            qVar.b = 1;
            qVar.c = crashDetailBean.z;
            qVar.d = crashDetailBean.A;
            qVar.e = crashDetailBean.r;
            this.d.b(1);
            this.d.a(qVar);
            w.b("[crash] a crash occur, handling...", new Object[0]);
        } else {
            w.b("[crash] a caught exception occur, handling...", new Object[0]);
        }
        List<a> b = b();
        List list = null;
        if (b != null && b.size() > 0) {
            List arrayList = new ArrayList(10);
            List<a> arrayList2 = new ArrayList(10);
            arrayList.addAll(a((List) b));
            b.removeAll(arrayList);
            if (!com.tencent.bugly.imsdk.b.c && c.c) {
                int i3 = 0;
                for (a aVar : b) {
                    if (crashDetailBean.u.equals(aVar.c)) {
                        if (aVar.e) {
                            i3 = true;
                        }
                        arrayList2.add(aVar);
                    }
                    i3 = i3;
                }
                if (i3 != 0 || arrayList2.size() >= 2) {
                    w.a("same crash occur too much do merged!", new Object[0]);
                    CrashDetailBean a = a((List) arrayList2, crashDetailBean);
                    for (a aVar2 : arrayList2) {
                        if (aVar2.a != a.a) {
                            arrayList.add(aVar2);
                        }
                    }
                    c(a);
                    c(arrayList);
                    w.b("[crash] save crash success. For this device crash many times, it will not upload crashes immediately", new Object[0]);
                    return true;
                }
            }
            list = arrayList;
        }
        c(crashDetailBean);
        if (!(list == null || list.isEmpty())) {
            c(list);
        }
        w.b("[crash] save crash success", new Object[0]);
        return false;
    }

    public final List<CrashDetailBean> a() {
        StrategyBean c = a.a().c();
        if (c == null) {
            w.d("have not synced remote!", new Object[0]);
            return null;
        } else if (c.g) {
            long currentTimeMillis = System.currentTimeMillis();
            long b = y.b();
            List b2 = b();
            if (b2 == null || b2.size() <= 0) {
                return null;
            }
            List arrayList = new ArrayList();
            Iterator it = b2.iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                if (aVar.b < b - c.f) {
                    it.remove();
                    arrayList.add(aVar);
                } else if (aVar.d) {
                    if (aVar.b >= currentTimeMillis - 86400000) {
                        it.remove();
                    } else if (!aVar.e) {
                        it.remove();
                        arrayList.add(aVar);
                    }
                } else if (((long) aVar.f) >= 3 && aVar.b < currentTimeMillis - 86400000) {
                    it.remove();
                    arrayList.add(aVar);
                }
            }
            if (arrayList.size() > 0) {
                c(arrayList);
            }
            List arrayList2 = new ArrayList();
            List<CrashDetailBean> b3 = b(b2);
            if (b3 != null && b3.size() > 0) {
                String str = com.tencent.bugly.imsdk.crashreport.common.info.a.b().j;
                Iterator it2 = b3.iterator();
                while (it2.hasNext()) {
                    CrashDetailBean crashDetailBean = (CrashDetailBean) it2.next();
                    if (!str.equals(crashDetailBean.f)) {
                        it2.remove();
                        arrayList2.add(crashDetailBean);
                    }
                }
            }
            if (arrayList2.size() > 0) {
                d(arrayList2);
            }
            return b3;
        } else {
            w.d("Crashreport remote closed, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            w.b("[init] WARNING! Crashreport closed by server, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            return null;
        }
    }

    public final void a(CrashDetailBean crashDetailBean, long j, boolean z) {
        boolean z2 = false;
        if (c.k) {
            w.a("try to upload right now", new Object[0]);
            List arrayList = new ArrayList();
            arrayList.add(crashDetailBean);
            if (crashDetailBean.b == 7) {
                z2 = true;
            }
            a(arrayList, 3000, z, z2, z);
            if (this.f != null) {
                n nVar = this.f;
                int i = crashDetailBean.b;
            }
        }
    }

    public final void a(final List<CrashDetailBean> list, long j, boolean z, boolean z2, boolean z3) {
        if (!com.tencent.bugly.imsdk.crashreport.common.info.a.a(this.b).e || this.c == null) {
            return;
        }
        if (z3 || this.c.b(c.a)) {
            StrategyBean c = this.e.c();
            if (!c.g) {
                w.d("remote report is disable!", new Object[0]);
                w.b("[crash] server closed bugly in this app. please check your appid if is correct, and re-install it", new Object[0]);
            } else if (list != null && list.size() != 0) {
                try {
                    j jVar;
                    String str = this.c.a ? c.s : c.t;
                    String str2 = this.c.a ? StrategyBean.c : StrategyBean.a;
                    int i = this.c.a ? 830 : 630;
                    Context context = this.b;
                    com.tencent.bugly.imsdk.crashreport.common.info.a b = com.tencent.bugly.imsdk.crashreport.common.info.a.b();
                    if (context == null || list == null || list.size() == 0 || b == null) {
                        w.d("enEXPPkg args == null!", new Object[0]);
                        jVar = null;
                    } else {
                        j akVar = new ak();
                        akVar.a = new ArrayList();
                        for (CrashDetailBean a : list) {
                            akVar.a.add(a(context, a, b));
                        }
                        jVar = akVar;
                    }
                    if (jVar == null) {
                        w.d("create eupPkg fail!", new Object[0]);
                        return;
                    }
                    byte[] a2 = com.tencent.bugly.imsdk.proguard.a.a(jVar);
                    if (a2 == null) {
                        w.d("send encode fail!", new Object[0]);
                        return;
                    }
                    al a3 = com.tencent.bugly.imsdk.proguard.a.a(this.b, i, a2);
                    if (a3 == null) {
                        w.d("request package is null.", new Object[0]);
                        return;
                    }
                    s anonymousClass1 = new s() {
                        public final void a(boolean z) {
                            b bVar = b.this;
                            b.a(z, list);
                        }
                    };
                    if (z) {
                        this.c.a(a, a3, str, str2, anonymousClass1, j, z2);
                    } else {
                        this.c.a(a, a3, str, str2, anonymousClass1, false);
                    }
                } catch (Throwable th) {
                    w.e("req cr error %s", th.toString());
                    if (!w.b(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    public static void a(boolean z, List<CrashDetailBean> list) {
        if (list != null && list.size() > 0) {
            w.c("up finish update state %b", Boolean.valueOf(z));
            for (CrashDetailBean crashDetailBean : list) {
                w.c("pre uid:%s uc:%d re:%b me:%b", crashDetailBean.c, Integer.valueOf(crashDetailBean.l), Boolean.valueOf(crashDetailBean.d), Boolean.valueOf(crashDetailBean.j));
                crashDetailBean.l++;
                crashDetailBean.d = z;
                w.c("set uid:%s uc:%d re:%b me:%b", crashDetailBean.c, Integer.valueOf(crashDetailBean.l), Boolean.valueOf(crashDetailBean.d), Boolean.valueOf(crashDetailBean.j));
            }
            for (CrashDetailBean crashDetailBean2 : list) {
                c.a().a(crashDetailBean2);
            }
            w.c("update state size %d", Integer.valueOf(list.size()));
        }
        if (!z) {
            w.b("[crash] upload fail.", new Object[0]);
        }
    }

    public final void b(CrashDetailBean crashDetailBean) {
        if (crashDetailBean != null) {
            if (this.g != null || this.f != null) {
                try {
                    int i;
                    String b;
                    w.a("[crash callback] start user's callback:onCrashHandleStart()", new Object[0]);
                    switch (crashDetailBean.b) {
                        case 0:
                            i = 0;
                            break;
                        case 1:
                            i = 2;
                            break;
                        case 2:
                            i = 1;
                            break;
                        case 3:
                            i = 4;
                            break;
                        case 4:
                            i = 3;
                            break;
                        case 5:
                            i = 5;
                            break;
                        case 6:
                            i = 6;
                            break;
                        case 7:
                            i = 7;
                            break;
                        default:
                            return;
                    }
                    int i2 = crashDetailBean.b;
                    String str = crashDetailBean.n;
                    str = crashDetailBean.p;
                    str = crashDetailBean.q;
                    long j = crashDetailBean.r;
                    Map map = null;
                    if (this.f != null) {
                        n nVar = this.f;
                        b = this.f.b();
                        if (b != null) {
                            map = new HashMap(1);
                            map.put("userData", b);
                        }
                    } else if (this.g != null) {
                        map = this.g.onCrashHandleStart(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
                    }
                    if (map != null && map.size() > 0) {
                        crashDetailBean.N = new LinkedHashMap(map.size());
                        for (Entry entry : map.entrySet()) {
                            if (!y.a((String) entry.getKey())) {
                                b = (String) entry.getKey();
                                if (b.length() > 100) {
                                    b = b.substring(0, 100);
                                    w.d("setted key length is over limit %d substring to %s", Integer.valueOf(100), b);
                                }
                                String str2 = b;
                                if (y.a((String) entry.getValue()) || ((String) entry.getValue()).length() <= 30000) {
                                    str = ((String) entry.getValue());
                                } else {
                                    str = ((String) entry.getValue()).substring(((String) entry.getValue()).length() - 30000);
                                    w.d("setted %s value length is over limit %d substring", str2, Integer.valueOf(30000));
                                }
                                crashDetailBean.N.put(str2, str);
                                w.a("add setted key %s value size:%d", str2, Integer.valueOf(str.length()));
                            }
                        }
                    }
                    w.a("[crash callback] start user's callback:onCrashHandleStart2GetExtraDatas()", new Object[0]);
                    byte[] bArr = null;
                    if (this.f != null) {
                        bArr = this.f.a();
                    } else if (this.g != null) {
                        bArr = this.g.onCrashHandleStart2GetExtraDatas(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
                    }
                    crashDetailBean.S = bArr;
                    if (crashDetailBean.S != null) {
                        if (crashDetailBean.S.length > 30000) {
                            w.d("extra bytes size %d is over limit %d will drop over part", Integer.valueOf(crashDetailBean.S.length), Integer.valueOf(30000));
                        }
                        w.a("add extra bytes %d ", Integer.valueOf(crashDetailBean.S.length));
                    }
                } catch (Throwable th) {
                    w.d("crash handle callback somthing wrong! %s", th.getClass().getName());
                    if (!w.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    private static ContentValues d(CrashDetailBean crashDetailBean) {
        int i = 1;
        if (crashDetailBean == null) {
            return null;
        }
        try {
            int i2;
            ContentValues contentValues = new ContentValues();
            if (crashDetailBean.a > 0) {
                contentValues.put("_id", Long.valueOf(crashDetailBean.a));
            }
            contentValues.put("_tm", Long.valueOf(crashDetailBean.r));
            contentValues.put("_s1", crashDetailBean.u);
            String str = "_up";
            if (crashDetailBean.d) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            contentValues.put(str, Integer.valueOf(i2));
            String str2 = "_me";
            if (!crashDetailBean.j) {
                i = 0;
            }
            contentValues.put(str2, Integer.valueOf(i));
            contentValues.put("_uc", Integer.valueOf(crashDetailBean.l));
            contentValues.put("_dt", y.a((Parcelable) crashDetailBean));
            return contentValues;
        } catch (Throwable th) {
            if (w.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private static CrashDetailBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            CrashDetailBean crashDetailBean = (CrashDetailBean) y.a(blob, CrashDetailBean.CREATOR);
            if (crashDetailBean == null) {
                return crashDetailBean;
            }
            crashDetailBean.a = j;
            return crashDetailBean;
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public final void c(CrashDetailBean crashDetailBean) {
        if (crashDetailBean != null) {
            ContentValues d = d(crashDetailBean);
            if (d != null) {
                long a = o.a().a("t_cr", d, null, true);
                if (a >= 0) {
                    w.c("insert %s success!", "t_cr");
                    crashDetailBean.a = a;
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00ae A:{Splitter: B:19:0x005f, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:41:?, code:
            com.tencent.bugly.imsdk.proguard.w.d("unknown id!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:43:0x00ae, code:
            r0 = th;
     */
    private java.util.List<com.tencent.bugly.imsdk.crashreport.crash.CrashDetailBean> b(java.util.List<com.tencent.bugly.imsdk.crashreport.crash.a> r11) {
        /*
        r10 = this;
        r8 = 4;
        r6 = 0;
        r7 = 0;
        if (r11 == 0) goto L_0x000b;
    L_0x0005:
        r0 = r11.size();
        if (r0 != 0) goto L_0x000d;
    L_0x000b:
        r0 = r7;
    L_0x000c:
        return r0;
    L_0x000d:
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r1 = r11.iterator();
    L_0x0016:
        r0 = r1.hasNext();
        if (r0 == 0) goto L_0x0036;
    L_0x001c:
        r0 = r1.next();
        r0 = (com.tencent.bugly.imsdk.crashreport.crash.a) r0;
        r2 = " or _id";
        r2 = r9.append(r2);
        r3 = " = ";
        r2 = r2.append(r3);
        r4 = r0.a;
        r2.append(r4);
        goto L_0x0016;
    L_0x0036:
        r3 = r9.toString();
        r0 = r3.length();
        if (r0 <= 0) goto L_0x0044;
    L_0x0040:
        r3 = r3.substring(r8);
    L_0x0044:
        r9.setLength(r6);
        r0 = com.tencent.bugly.imsdk.proguard.o.a();	 Catch:{ Throwable -> 0x00f6, all -> 0x00f0 }
        r1 = "t_cr";
        r2 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 1;
        r8 = r0.a(r1, r2, r3, r4, r5, r6);	 Catch:{ Throwable -> 0x00f6, all -> 0x00f0 }
        if (r8 != 0) goto L_0x005f;
    L_0x0058:
        if (r8 == 0) goto L_0x005d;
    L_0x005a:
        r8.close();
    L_0x005d:
        r0 = r7;
        goto L_0x000c;
    L_0x005f:
        r6 = new java.util.ArrayList;	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        r6.<init>();	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
    L_0x0064:
        r0 = r8.moveToNext();	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        if (r0 == 0) goto L_0x00b5;
    L_0x006a:
        r0 = a(r8);	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        if (r0 == 0) goto L_0x0086;
    L_0x0070:
        r6.add(r0);	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        goto L_0x0064;
    L_0x0074:
        r0 = move-exception;
        r1 = r8;
    L_0x0076:
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r0);	 Catch:{ all -> 0x00f3 }
        if (r2 != 0) goto L_0x007f;
    L_0x007c:
        r0.printStackTrace();	 Catch:{ all -> 0x00f3 }
    L_0x007f:
        if (r1 == 0) goto L_0x0084;
    L_0x0081:
        r1.close();
    L_0x0084:
        r0 = r7;
        goto L_0x000c;
    L_0x0086:
        r0 = "_id";
        r0 = r8.getColumnIndex(r0);	 Catch:{ Throwable -> 0x00a3, all -> 0x00ae }
        r0 = r8.getLong(r0);	 Catch:{ Throwable -> 0x00a3, all -> 0x00ae }
        r2 = " or _id";
        r2 = r9.append(r2);	 Catch:{ Throwable -> 0x00a3, all -> 0x00ae }
        r3 = " = ";
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x00a3, all -> 0x00ae }
        r2.append(r0);	 Catch:{ Throwable -> 0x00a3, all -> 0x00ae }
        goto L_0x0064;
    L_0x00a3:
        r0 = move-exception;
        r0 = "unknown id!";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        com.tencent.bugly.imsdk.proguard.w.d(r0, r1);	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        goto L_0x0064;
    L_0x00ae:
        r0 = move-exception;
    L_0x00af:
        if (r8 == 0) goto L_0x00b4;
    L_0x00b1:
        r8.close();
    L_0x00b4:
        throw r0;
    L_0x00b5:
        r0 = r9.toString();	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        r1 = r0.length();	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        if (r1 <= 0) goto L_0x00e8;
    L_0x00bf:
        r1 = 4;
        r2 = r0.substring(r1);	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        r0 = com.tencent.bugly.imsdk.proguard.o.a();	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        r1 = "t_cr";
        r3 = 0;
        r4 = 0;
        r5 = 1;
        r0 = r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        r1 = "deleted %s illegle data %d";
        r2 = 2;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        r3 = 0;
        r4 = "t_cr";
        r2[r3] = r4;	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        r3 = 1;
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        r2[r3] = r0;	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
        com.tencent.bugly.imsdk.proguard.w.d(r1, r2);	 Catch:{ Throwable -> 0x0074, all -> 0x00ae }
    L_0x00e8:
        if (r8 == 0) goto L_0x00ed;
    L_0x00ea:
        r8.close();
    L_0x00ed:
        r0 = r6;
        goto L_0x000c;
    L_0x00f0:
        r0 = move-exception;
        r8 = r7;
        goto L_0x00af;
    L_0x00f3:
        r0 = move-exception;
        r8 = r1;
        goto L_0x00af;
    L_0x00f6:
        r0 = move-exception;
        r1 = r7;
        goto L_0x0076;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.crash.b.b(java.util.List):java.util.List<com.tencent.bugly.imsdk.crashreport.crash.CrashDetailBean>");
    }

    private static a b(Cursor cursor) {
        boolean z = true;
        if (cursor == null) {
            return null;
        }
        try {
            a aVar = new a();
            aVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            aVar.b = cursor.getLong(cursor.getColumnIndex("_tm"));
            aVar.c = cursor.getString(cursor.getColumnIndex("_s1"));
            aVar.d = cursor.getInt(cursor.getColumnIndex("_up")) == 1;
            if (cursor.getInt(cursor.getColumnIndex("_me")) != 1) {
                z = false;
            }
            aVar.e = z;
            aVar.f = cursor.getInt(cursor.getColumnIndex("_uc"));
            return aVar;
        } catch (Throwable th) {
            if (w.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0094 A:{Splitter: B:7:0x0045, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:29:?, code:
            com.tencent.bugly.imsdk.proguard.w.d("unknown id!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:31:0x0094, code:
            r0 = th;
     */
    private java.util.List<com.tencent.bugly.imsdk.crashreport.crash.a> b() {
        /*
        r9 = this;
        r7 = 0;
        r8 = new java.util.ArrayList;
        r8.<init>();
        r0 = 6;
        r2 = new java.lang.String[r0];	 Catch:{ Throwable -> 0x00dc, all -> 0x00d6 }
        r0 = 0;
        r1 = "_id";
        r2[r0] = r1;	 Catch:{ Throwable -> 0x00dc, all -> 0x00d6 }
        r0 = 1;
        r1 = "_tm";
        r2[r0] = r1;	 Catch:{ Throwable -> 0x00dc, all -> 0x00d6 }
        r0 = 2;
        r1 = "_s1";
        r2[r0] = r1;	 Catch:{ Throwable -> 0x00dc, all -> 0x00d6 }
        r0 = 3;
        r1 = "_up";
        r2[r0] = r1;	 Catch:{ Throwable -> 0x00dc, all -> 0x00d6 }
        r0 = 4;
        r1 = "_me";
        r2[r0] = r1;	 Catch:{ Throwable -> 0x00dc, all -> 0x00d6 }
        r0 = 5;
        r1 = "_uc";
        r2[r0] = r1;	 Catch:{ Throwable -> 0x00dc, all -> 0x00d6 }
        r0 = com.tencent.bugly.imsdk.proguard.o.a();	 Catch:{ Throwable -> 0x00dc, all -> 0x00d6 }
        r1 = "t_cr";
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 1;
        r6 = r0.a(r1, r2, r3, r4, r5, r6);	 Catch:{ Throwable -> 0x00dc, all -> 0x00d6 }
        if (r6 != 0) goto L_0x0045;
    L_0x003e:
        if (r6 == 0) goto L_0x0043;
    L_0x0040:
        r6.close();
    L_0x0043:
        r0 = r7;
    L_0x0044:
        return r0;
    L_0x0045:
        r0 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        r0.<init>();	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
    L_0x004a:
        r1 = r6.moveToNext();	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        if (r1 == 0) goto L_0x009b;
    L_0x0050:
        r1 = b(r6);	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        if (r1 == 0) goto L_0x006c;
    L_0x0056:
        r8.add(r1);	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        goto L_0x004a;
    L_0x005a:
        r0 = move-exception;
        r7 = r6;
    L_0x005c:
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);	 Catch:{ all -> 0x00d9 }
        if (r1 != 0) goto L_0x0065;
    L_0x0062:
        r0.printStackTrace();	 Catch:{ all -> 0x00d9 }
    L_0x0065:
        if (r7 == 0) goto L_0x006a;
    L_0x0067:
        r7.close();
    L_0x006a:
        r0 = r8;
        goto L_0x0044;
    L_0x006c:
        r1 = "_id";
        r1 = r6.getColumnIndex(r1);	 Catch:{ Throwable -> 0x0089, all -> 0x0094 }
        r2 = r6.getLong(r1);	 Catch:{ Throwable -> 0x0089, all -> 0x0094 }
        r1 = " or _id";
        r1 = r0.append(r1);	 Catch:{ Throwable -> 0x0089, all -> 0x0094 }
        r4 = " = ";
        r1 = r1.append(r4);	 Catch:{ Throwable -> 0x0089, all -> 0x0094 }
        r1.append(r2);	 Catch:{ Throwable -> 0x0089, all -> 0x0094 }
        goto L_0x004a;
    L_0x0089:
        r1 = move-exception;
        r1 = "unknown id!";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        com.tencent.bugly.imsdk.proguard.w.d(r1, r2);	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        goto L_0x004a;
    L_0x0094:
        r0 = move-exception;
    L_0x0095:
        if (r6 == 0) goto L_0x009a;
    L_0x0097:
        r6.close();
    L_0x009a:
        throw r0;
    L_0x009b:
        r0 = r0.toString();	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        r1 = r0.length();	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        if (r1 <= 0) goto L_0x00ce;
    L_0x00a5:
        r1 = 4;
        r2 = r0.substring(r1);	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        r0 = com.tencent.bugly.imsdk.proguard.o.a();	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        r1 = "t_cr";
        r3 = 0;
        r4 = 0;
        r5 = 1;
        r0 = r0.a(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        r1 = "deleted %s illegle data %d";
        r2 = 2;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        r3 = 0;
        r4 = "t_cr";
        r2[r3] = r4;	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        r3 = 1;
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        r2[r3] = r0;	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
        com.tencent.bugly.imsdk.proguard.w.d(r1, r2);	 Catch:{ Throwable -> 0x005a, all -> 0x0094 }
    L_0x00ce:
        if (r6 == 0) goto L_0x00d3;
    L_0x00d0:
        r6.close();
    L_0x00d3:
        r0 = r8;
        goto L_0x0044;
    L_0x00d6:
        r0 = move-exception;
        r6 = r7;
        goto L_0x0095;
    L_0x00d9:
        r0 = move-exception;
        r6 = r7;
        goto L_0x0095;
    L_0x00dc:
        r0 = move-exception;
        goto L_0x005c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.crash.b.b():java.util.List<com.tencent.bugly.imsdk.crashreport.crash.a>");
    }

    private static void c(List<a> list) {
        if (list != null && list.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (a aVar : list) {
                stringBuilder.append(" or _id").append(" = ").append(aVar.a);
            }
            String stringBuilder2 = stringBuilder.toString();
            if (stringBuilder2.length() > 0) {
                stringBuilder2 = stringBuilder2.substring(4);
            }
            stringBuilder.setLength(0);
            try {
                int a = o.a().a("t_cr", stringBuilder2, null, null, true);
                w.c("deleted %s data %d", "t_cr", Integer.valueOf(a));
            } catch (Throwable th) {
                if (!w.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static void d(List<CrashDetailBean> list) {
        if (list != null) {
            try {
                if (list.size() != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (CrashDetailBean crashDetailBean : list) {
                        stringBuilder.append(" or _id").append(" = ").append(crashDetailBean.a);
                    }
                    String stringBuilder2 = stringBuilder.toString();
                    if (stringBuilder2.length() > 0) {
                        stringBuilder2 = stringBuilder2.substring(4);
                    }
                    stringBuilder.setLength(0);
                    int a = o.a().a("t_cr", stringBuilder2, null, null, true);
                    w.c("deleted %s data %d", "t_cr", Integer.valueOf(a));
                }
            } catch (Throwable th) {
                if (!w.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static aj a(Context context, CrashDetailBean crashDetailBean, com.tencent.bugly.imsdk.crashreport.common.info.a aVar) {
        boolean z = true;
        if (context == null || crashDetailBean == null || aVar == null) {
            w.d("enExp args == null", new Object[0]);
            return null;
        }
        ag agVar;
        ai a;
        aj ajVar = new aj();
        switch (crashDetailBean.b) {
            case 0:
                ajVar.a = crashDetailBean.j ? "200" : "100";
                break;
            case 1:
                ajVar.a = crashDetailBean.j ? "201" : "101";
                break;
            case 2:
                ajVar.a = crashDetailBean.j ? "202" : "102";
                break;
            case 3:
                ajVar.a = crashDetailBean.j ? "203" : "103";
                break;
            case 4:
                ajVar.a = crashDetailBean.j ? "204" : "104";
                break;
            case 5:
                ajVar.a = crashDetailBean.j ? "207" : "107";
                break;
            case 6:
                ajVar.a = crashDetailBean.j ? "206" : "106";
                break;
            case 7:
                ajVar.a = crashDetailBean.j ? "208" : "108";
                break;
            default:
                w.e("crash type error! %d", Integer.valueOf(crashDetailBean.b));
                break;
        }
        ajVar.b = crashDetailBean.r;
        ajVar.c = crashDetailBean.n;
        ajVar.d = crashDetailBean.o;
        ajVar.e = crashDetailBean.p;
        ajVar.g = crashDetailBean.q;
        ajVar.h = crashDetailBean.y;
        ajVar.i = crashDetailBean.c;
        ajVar.j = null;
        ajVar.l = crashDetailBean.m;
        ajVar.m = crashDetailBean.e;
        ajVar.f = crashDetailBean.A;
        ajVar.t = com.tencent.bugly.imsdk.crashreport.common.info.a.b().i();
        ajVar.n = null;
        if (crashDetailBean.i != null && crashDetailBean.i.size() > 0) {
            ajVar.o = new ArrayList();
            for (Entry entry : crashDetailBean.i.entrySet()) {
                agVar = new ag();
                agVar.a = ((PlugInBean) entry.getValue()).a;
                agVar.c = ((PlugInBean) entry.getValue()).c;
                agVar.d = ((PlugInBean) entry.getValue()).b;
                agVar.b = aVar.r();
                ajVar.o.add(agVar);
            }
        }
        if (crashDetailBean.h != null && crashDetailBean.h.size() > 0) {
            ajVar.p = new ArrayList();
            for (Entry entry2 : crashDetailBean.h.entrySet()) {
                agVar = new ag();
                agVar.a = ((PlugInBean) entry2.getValue()).a;
                agVar.c = ((PlugInBean) entry2.getValue()).c;
                agVar.d = ((PlugInBean) entry2.getValue()).b;
                ajVar.p.add(agVar);
            }
        }
        if (crashDetailBean.j) {
            int size;
            ajVar.k = crashDetailBean.t;
            if (crashDetailBean.s != null && crashDetailBean.s.length() > 0) {
                if (ajVar.q == null) {
                    ajVar.q = new ArrayList();
                }
                try {
                    ajVar.q.add(new ai((byte) 1, "alltimes.txt", crashDetailBean.s.getBytes(Constants.UTF_8)));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    ajVar.q = null;
                }
            }
            String str = "crashcount:%d sz:%d";
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(ajVar.k);
            if (ajVar.q != null) {
                size = ajVar.q.size();
            } else {
                size = 0;
            }
            objArr[1] = Integer.valueOf(size);
            w.c(str, objArr);
        }
        if (crashDetailBean.w != null) {
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            try {
                ajVar.q.add(new ai((byte) 1, "log.txt", crashDetailBean.w.getBytes(Constants.UTF_8)));
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
                ajVar.q = null;
            }
        }
        if (!y.a(crashDetailBean.T)) {
            Object aiVar;
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            try {
                aiVar = new ai((byte) 1, "crashInfos.txt", crashDetailBean.T.getBytes(Constants.UTF_8));
            } catch (UnsupportedEncodingException e22) {
                e22.printStackTrace();
                aiVar = null;
            }
            if (aiVar != null) {
                w.c("attach crash infos", new Object[0]);
                ajVar.q.add(aiVar);
            }
        }
        if (crashDetailBean.U != null) {
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            a = a("backupRecord.zip", context, crashDetailBean.U);
            if (a != null) {
                w.c("attach backup record", new Object[0]);
                ajVar.q.add(a);
            }
        }
        if (crashDetailBean.x != null && crashDetailBean.x.length > 0) {
            a = new ai((byte) 2, "buglylog.zip", crashDetailBean.x);
            w.c("attach user log", new Object[0]);
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            ajVar.q.add(a);
        }
        if (crashDetailBean.b == 3) {
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            if (crashDetailBean.N != null && crashDetailBean.N.containsKey("BUGLY_CR_01")) {
                try {
                    ajVar.q.add(new ai((byte) 1, "anrMessage.txt", ((String) crashDetailBean.N.get("BUGLY_CR_01")).getBytes(Constants.UTF_8)));
                    w.c("attach anr message", new Object[0]);
                } catch (UnsupportedEncodingException e222) {
                    e222.printStackTrace();
                    ajVar.q = null;
                }
                crashDetailBean.N.remove("BUGLY_CR_01");
            }
            if (crashDetailBean.v != null) {
                a = a("trace.zip", context, crashDetailBean.v);
                if (a != null) {
                    w.c("attach traces", new Object[0]);
                    ajVar.q.add(a);
                }
            }
        }
        if (crashDetailBean.b == 1) {
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            if (crashDetailBean.v != null) {
                a = a("tomb.zip", context, crashDetailBean.v);
                if (a != null) {
                    w.c("attach tombs", new Object[0]);
                    ajVar.q.add(a);
                }
            }
        }
        if (!(aVar.B == null || aVar.B.isEmpty())) {
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String append : aVar.B) {
                stringBuilder.append(append);
            }
            try {
                ajVar.q.add(new ai((byte) 1, "martianlog.txt", stringBuilder.toString().getBytes(Constants.UTF_8)));
                w.c("attach pageTracingList", new Object[0]);
            } catch (UnsupportedEncodingException e2222) {
                e2222.printStackTrace();
            }
        }
        if (crashDetailBean.S != null && crashDetailBean.S.length > 0) {
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            ajVar.q.add(new ai((byte) 1, "userExtraByteData", crashDetailBean.S));
            w.c("attach extraData", new Object[0]);
        }
        ajVar.r = new HashMap();
        ajVar.r.put("A9", crashDetailBean.B);
        ajVar.r.put("A11", crashDetailBean.C);
        ajVar.r.put("A10", crashDetailBean.D);
        ajVar.r.put("A23", crashDetailBean.f);
        ajVar.r.put("A7", aVar.f);
        ajVar.r.put("A6", aVar.s());
        ajVar.r.put("A5", aVar.r());
        ajVar.r.put("A22", aVar.h());
        ajVar.r.put("A2", crashDetailBean.F);
        ajVar.r.put("A1", crashDetailBean.E);
        ajVar.r.put("A24", aVar.h);
        ajVar.r.put("A17", crashDetailBean.G);
        ajVar.r.put("A3", aVar.k());
        ajVar.r.put("A16", aVar.m());
        ajVar.r.put("A25", aVar.n());
        ajVar.r.put("A14", aVar.l());
        ajVar.r.put("A15", aVar.w());
        ajVar.r.put("A13", aVar.x());
        ajVar.r.put("A34", crashDetailBean.z);
        if (aVar.x != null) {
            ajVar.r.put("productIdentify", aVar.x);
        }
        try {
            ajVar.r.put("A26", URLEncoder.encode(crashDetailBean.H, Constants.UTF_8));
        } catch (UnsupportedEncodingException e22222) {
            e22222.printStackTrace();
        }
        if (crashDetailBean.b == 1) {
            ajVar.r.put("A27", crashDetailBean.J);
            ajVar.r.put("A28", crashDetailBean.I);
            ajVar.r.put("A29", crashDetailBean.k);
        }
        ajVar.r.put("A30", crashDetailBean.K);
        ajVar.r.put("A18", crashDetailBean.L);
        ajVar.r.put("A36", (!crashDetailBean.M));
        ajVar.r.put("F02", aVar.q);
        ajVar.r.put("F03", aVar.r);
        ajVar.r.put("F04", aVar.e());
        ajVar.r.put("F05", aVar.s);
        ajVar.r.put("F06", aVar.p);
        ajVar.r.put("F08", aVar.v);
        ajVar.r.put("F09", aVar.w);
        ajVar.r.put("F10", aVar.t);
        if (crashDetailBean.O >= 0) {
            ajVar.r.put("C01", crashDetailBean.O);
        }
        if (crashDetailBean.P >= 0) {
            ajVar.r.put("C02", crashDetailBean.P);
        }
        if (crashDetailBean.Q != null && crashDetailBean.Q.size() > 0) {
            for (Entry entry22 : crashDetailBean.Q.entrySet()) {
                ajVar.r.put("C03_" + ((String) entry22.getKey()), entry22.getValue());
            }
        }
        if (crashDetailBean.R != null && crashDetailBean.R.size() > 0) {
            for (Entry entry222 : crashDetailBean.R.entrySet()) {
                ajVar.r.put("C04_" + ((String) entry222.getKey()), entry222.getValue());
            }
        }
        ajVar.s = null;
        if (crashDetailBean.N != null && crashDetailBean.N.size() > 0) {
            ajVar.s = crashDetailBean.N;
            w.a("setted message size %d", Integer.valueOf(ajVar.s.size()));
        }
        String append2 = "%s rid:%s sess:%s ls:%ds isR:%b isF:%b isM:%b isN:%b mc:%d ,%s ,isUp:%b ,vm:%d";
        Object[] objArr2 = new Object[12];
        objArr2[0] = crashDetailBean.n;
        objArr2[1] = crashDetailBean.c;
        objArr2[2] = aVar.e();
        objArr2[3] = Long.valueOf((crashDetailBean.r - crashDetailBean.L) / 1000);
        objArr2[4] = Boolean.valueOf(crashDetailBean.k);
        objArr2[5] = Boolean.valueOf(crashDetailBean.M);
        objArr2[6] = Boolean.valueOf(crashDetailBean.j);
        if (crashDetailBean.b != 1) {
            z = false;
        }
        objArr2[7] = Boolean.valueOf(z);
        objArr2[8] = Integer.valueOf(crashDetailBean.t);
        objArr2[9] = crashDetailBean.s;
        objArr2[10] = Boolean.valueOf(crashDetailBean.d);
        objArr2[11] = Integer.valueOf(ajVar.r.size());
        w.c(append2, objArr2);
        return ajVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x005c A:{Catch:{ all -> 0x00e7 }} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0061 A:{SYNTHETIC, Splitter: B:23:0x0061} */
    /* JADX WARNING: Removed duplicated region for block: B:62:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00c7 A:{SYNTHETIC, Splitter: B:47:0x00c7} */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00d0  */
    private static com.tencent.bugly.imsdk.proguard.ai a(java.lang.String r9, android.content.Context r10, java.lang.String r11) {
        /*
        r2 = 1;
        r0 = 0;
        r8 = 0;
        if (r11 == 0) goto L_0x0007;
    L_0x0005:
        if (r10 != 0) goto L_0x0010;
    L_0x0007:
        r1 = "rqdp{  createZipAttachment sourcePath == null || context == null ,pls check}";
        r2 = new java.lang.Object[r8];
        com.tencent.bugly.imsdk.proguard.w.d(r1, r2);
    L_0x000f:
        return r0;
    L_0x0010:
        r1 = "zip %s";
        r2 = new java.lang.Object[r2];
        r2[r8] = r11;
        com.tencent.bugly.imsdk.proguard.w.c(r1, r2);
        r1 = new java.io.File;
        r1.<init>(r11);
        r3 = new java.io.File;
        r2 = r10.getCacheDir();
        r3.<init>(r2, r9);
        r2 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r1 = com.tencent.bugly.imsdk.proguard.y.a(r1, r3, r2);
        if (r1 != 0) goto L_0x0039;
    L_0x0030:
        r1 = "zip fail!";
        r2 = new java.lang.Object[r8];
        com.tencent.bugly.imsdk.proguard.w.d(r1, r2);
        goto L_0x000f;
    L_0x0039:
        r1 = new java.io.ByteArrayOutputStream;
        r1.<init>();
        r2 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x00e9, all -> 0x00c2 }
        r2.<init>(r3);	 Catch:{ Throwable -> 0x00e9, all -> 0x00c2 }
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = new byte[r4];	 Catch:{ Throwable -> 0x0055 }
    L_0x0047:
        r5 = r2.read(r4);	 Catch:{ Throwable -> 0x0055 }
        if (r5 <= 0) goto L_0x0076;
    L_0x004d:
        r6 = 0;
        r1.write(r4, r6, r5);	 Catch:{ Throwable -> 0x0055 }
        r1.flush();	 Catch:{ Throwable -> 0x0055 }
        goto L_0x0047;
    L_0x0055:
        r1 = move-exception;
    L_0x0056:
        r4 = com.tencent.bugly.imsdk.proguard.w.a(r1);	 Catch:{ all -> 0x00e7 }
        if (r4 != 0) goto L_0x005f;
    L_0x005c:
        r1.printStackTrace();	 Catch:{ all -> 0x00e7 }
    L_0x005f:
        if (r2 == 0) goto L_0x0064;
    L_0x0061:
        r2.close();	 Catch:{ IOException -> 0x00b7 }
    L_0x0064:
        r1 = r3.exists();
        if (r1 == 0) goto L_0x000f;
    L_0x006a:
        r1 = "del tmp";
        r2 = new java.lang.Object[r8];
        com.tencent.bugly.imsdk.proguard.w.c(r1, r2);
        r3.delete();
        goto L_0x000f;
    L_0x0076:
        r4 = r1.toByteArray();	 Catch:{ Throwable -> 0x0055 }
        r1 = "read bytes :%d";
        r5 = 1;
        r5 = new java.lang.Object[r5];	 Catch:{ Throwable -> 0x0055 }
        r6 = 0;
        r7 = r4.length;	 Catch:{ Throwable -> 0x0055 }
        r7 = java.lang.Integer.valueOf(r7);	 Catch:{ Throwable -> 0x0055 }
        r5[r6] = r7;	 Catch:{ Throwable -> 0x0055 }
        com.tencent.bugly.imsdk.proguard.w.c(r1, r5);	 Catch:{ Throwable -> 0x0055 }
        r1 = new com.tencent.bugly.imsdk.proguard.ai;	 Catch:{ Throwable -> 0x0055 }
        r5 = 2;
        r6 = r3.getName();	 Catch:{ Throwable -> 0x0055 }
        r1.<init>(r5, r6, r4);	 Catch:{ Throwable -> 0x0055 }
        r2.close();	 Catch:{ IOException -> 0x00ac }
    L_0x0098:
        r0 = r3.exists();
        if (r0 == 0) goto L_0x00a9;
    L_0x009e:
        r0 = "del tmp";
        r2 = new java.lang.Object[r8];
        com.tencent.bugly.imsdk.proguard.w.c(r0, r2);
        r3.delete();
    L_0x00a9:
        r0 = r1;
        goto L_0x000f;
    L_0x00ac:
        r0 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r2 != 0) goto L_0x0098;
    L_0x00b3:
        r0.printStackTrace();
        goto L_0x0098;
    L_0x00b7:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x0064;
    L_0x00be:
        r1.printStackTrace();
        goto L_0x0064;
    L_0x00c2:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
    L_0x00c5:
        if (r2 == 0) goto L_0x00ca;
    L_0x00c7:
        r2.close();	 Catch:{ IOException -> 0x00dc }
    L_0x00ca:
        r1 = r3.exists();
        if (r1 == 0) goto L_0x00db;
    L_0x00d0:
        r1 = "del tmp";
        r2 = new java.lang.Object[r8];
        com.tencent.bugly.imsdk.proguard.w.c(r1, r2);
        r3.delete();
    L_0x00db:
        throw r0;
    L_0x00dc:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x00ca;
    L_0x00e3:
        r1.printStackTrace();
        goto L_0x00ca;
    L_0x00e7:
        r0 = move-exception;
        goto L_0x00c5;
    L_0x00e9:
        r1 = move-exception;
        r2 = r0;
        goto L_0x0056;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.crash.b.a(java.lang.String, android.content.Context, java.lang.String):com.tencent.bugly.imsdk.proguard.ai");
    }

    public static void a(String str, String str2, String str3, Thread thread, String str4, CrashDetailBean crashDetailBean) {
        com.tencent.bugly.imsdk.crashreport.common.info.a b = com.tencent.bugly.imsdk.crashreport.common.info.a.b();
        if (b != null) {
            w.e("#++++++++++Record By Bugly++++++++++#", new Object[0]);
            w.e("# You can use Bugly(http:\\\\bugly.qq.com) to get more Crash Detail!", new Object[0]);
            w.e("# PKG NAME: %s", b.c);
            w.e("# APP VER: %s", b.j);
            w.e("# LAUNCH TIME: %s", y.a(new Date(com.tencent.bugly.imsdk.crashreport.common.info.a.b().a)));
            w.e("# CRASH TYPE: %s", str);
            w.e("# CRASH TIME: %s", str2);
            w.e("# CRASH PROCESS: %s", str3);
            if (thread != null) {
                w.e("# CRASH THREAD: %s", thread.getName());
            }
            if (crashDetailBean != null) {
                w.e("# REPORT ID: %s", crashDetailBean.c);
                String str5 = "# CRASH DEVICE: %s %s";
                Object[] objArr = new Object[2];
                objArr[0] = b.g;
                objArr[1] = b.x().booleanValue() ? "ROOTED" : "UNROOT";
                w.e(str5, objArr);
                w.e("# RUNTIME AVAIL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.B), Long.valueOf(crashDetailBean.C), Long.valueOf(crashDetailBean.D));
                w.e("# RUNTIME TOTAL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.E), Long.valueOf(crashDetailBean.F), Long.valueOf(crashDetailBean.G));
                if (!y.a(crashDetailBean.J)) {
                    w.e("# EXCEPTION FIRED BY %s %s", crashDetailBean.J, crashDetailBean.I);
                } else if (crashDetailBean.b == 3) {
                    str5 = "# EXCEPTION ANR MESSAGE:\n %s";
                    objArr = new Object[1];
                    objArr[0] = crashDetailBean.N == null ? "null" : ((String) crashDetailBean.N.get("BUGLY_CR_01"));
                    w.e(str5, objArr);
                }
            }
            if (!y.a(str4)) {
                w.e("# CRASH STACK: ", new Object[0]);
                w.e(str4, new Object[0]);
            }
            w.e("#++++++++++++++++++++++++++++++++++++++++++#", new Object[0]);
        }
    }
}
