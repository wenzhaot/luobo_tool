package com.baidu.location.e;

import com.baidu.location.BDLocation;
import java.util.concurrent.Callable;

class i implements Callable<BDLocation> {
    final /* synthetic */ String[] a;
    final /* synthetic */ h b;

    i(h hVar, String[] strArr) {
        this.b = hVar;
        this.a = strArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x0086 A:{SYNTHETIC, Splitter: B:40:0x0086} */
    /* renamed from: a */
    public com.baidu.location.BDLocation call() {
        /*
        r8 = this;
        r0 = 0;
        r7 = 0;
        r6 = new com.baidu.location.BDLocation;
        r6.<init>();
        r1 = r8.a;
        r1 = r1.length;
        if (r1 <= 0) goto L_0x004f;
    L_0x000c:
        r1 = com.baidu.location.e.h.d;	 Catch:{ Exception -> 0x0050 }
        r1 = r1.getPackageManager();	 Catch:{ Exception -> 0x0050 }
        r2 = com.baidu.location.e.h.c;	 Catch:{ Exception -> 0x0050 }
        r3 = 0;
        r1 = r1.resolveContentProvider(r2, r3);	 Catch:{ Exception -> 0x0050 }
    L_0x001b:
        if (r1 == 0) goto L_0x0053;
    L_0x001d:
        if (r1 == 0) goto L_0x008a;
    L_0x001f:
        r0 = com.baidu.location.e.h.d;	 Catch:{ Exception -> 0x0077, all -> 0x0083 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x0077, all -> 0x0083 }
        r1 = r1.authority;	 Catch:{ Exception -> 0x0077, all -> 0x0083 }
        r1 = com.baidu.location.e.h.c(r1);	 Catch:{ Exception -> 0x0077, all -> 0x0083 }
        r2 = r8.a;	 Catch:{ Exception -> 0x0077, all -> 0x0083 }
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0077, all -> 0x0083 }
        r0 = com.baidu.location.e.j.a(r1);	 Catch:{ Exception -> 0x00c0, all -> 0x00bd }
        if (r1 == 0) goto L_0x003f;
    L_0x003c:
        r1.close();	 Catch:{ Exception -> 0x00b7 }
    L_0x003f:
        r6 = r0;
    L_0x0040:
        if (r6 == 0) goto L_0x004f;
    L_0x0042:
        r0 = r6.getLocType();
        r1 = 67;
        if (r0 == r1) goto L_0x004f;
    L_0x004a:
        r0 = 66;
        r6.setLocType(r0);
    L_0x004f:
        return r6;
    L_0x0050:
        r1 = move-exception;
        r1 = r7;
        goto L_0x001b;
    L_0x0053:
        r2 = r8.b;
        r2 = r2.j;
        r2 = r2.o();
    L_0x005d:
        r3 = r2.length;
        if (r0 >= r3) goto L_0x001d;
    L_0x0060:
        r1 = com.baidu.location.e.h.d;	 Catch:{ Exception -> 0x0074 }
        r1 = r1.getPackageManager();	 Catch:{ Exception -> 0x0074 }
        r3 = r2[r0];	 Catch:{ Exception -> 0x0074 }
        r4 = 0;
        r1 = r1.resolveContentProvider(r3, r4);	 Catch:{ Exception -> 0x0074 }
    L_0x006f:
        if (r1 != 0) goto L_0x001d;
    L_0x0071:
        r0 = r0 + 1;
        goto L_0x005d;
    L_0x0074:
        r1 = move-exception;
        r1 = r7;
        goto L_0x006f;
    L_0x0077:
        r0 = move-exception;
        r0 = r7;
    L_0x0079:
        if (r0 == 0) goto L_0x00c3;
    L_0x007b:
        r0.close();	 Catch:{ Exception -> 0x0080 }
        r0 = r6;
        goto L_0x003f;
    L_0x0080:
        r0 = move-exception;
        r0 = r6;
        goto L_0x003f;
    L_0x0083:
        r0 = move-exception;
    L_0x0084:
        if (r7 == 0) goto L_0x0089;
    L_0x0086:
        r7.close();	 Catch:{ Exception -> 0x00b9 }
    L_0x0089:
        throw r0;
    L_0x008a:
        r0 = new com.baidu.location.e.j$a;
        r1 = r8.a;
        r0.<init>(r1);
        r1 = r8.b;	 Catch:{ Exception -> 0x00a7, all -> 0x00b0 }
        r1 = r1.h;	 Catch:{ Exception -> 0x00a7, all -> 0x00b0 }
        r7 = r1.a(r0);	 Catch:{ Exception -> 0x00a7, all -> 0x00b0 }
        r6 = com.baidu.location.e.j.a(r7);	 Catch:{ Exception -> 0x00a7, all -> 0x00b0 }
        if (r7 == 0) goto L_0x0040;
    L_0x00a1:
        r7.close();	 Catch:{ Exception -> 0x00a5 }
        goto L_0x0040;
    L_0x00a5:
        r0 = move-exception;
        goto L_0x0040;
    L_0x00a7:
        r0 = move-exception;
        if (r7 == 0) goto L_0x0040;
    L_0x00aa:
        r7.close();	 Catch:{ Exception -> 0x00ae }
        goto L_0x0040;
    L_0x00ae:
        r0 = move-exception;
        goto L_0x0040;
    L_0x00b0:
        r0 = move-exception;
        if (r7 == 0) goto L_0x00b6;
    L_0x00b3:
        r7.close();	 Catch:{ Exception -> 0x00bb }
    L_0x00b6:
        throw r0;
    L_0x00b7:
        r1 = move-exception;
        goto L_0x003f;
    L_0x00b9:
        r1 = move-exception;
        goto L_0x0089;
    L_0x00bb:
        r1 = move-exception;
        goto L_0x00b6;
    L_0x00bd:
        r0 = move-exception;
        r7 = r1;
        goto L_0x0084;
    L_0x00c0:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0079;
    L_0x00c3:
        r0 = r6;
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.e.i.a():com.baidu.location.BDLocation");
    }
}
