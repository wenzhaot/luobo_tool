package com.ta.utdid2.b.a;

import java.util.Random;

/* compiled from: PhoneInfoUtils */
public class g {
    public static final String c() {
        int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int nanoTime = (int) System.nanoTime();
        int nextInt = new Random().nextInt();
        int nextInt2 = new Random().nextInt();
        Object bytes = e.getBytes(currentTimeMillis);
        Object bytes2 = e.getBytes(nanoTime);
        Object bytes3 = e.getBytes(nextInt);
        Object bytes4 = e.getBytes(nextInt2);
        Object obj = new byte[16];
        System.arraycopy(bytes, 0, obj, 0, 4);
        System.arraycopy(bytes2, 0, obj, 4, 4);
        System.arraycopy(bytes3, 0, obj, 8, 4);
        System.arraycopy(bytes4, 0, obj, 12, 4);
        return b.encodeToString(obj, 2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0018  */
    public static java.lang.String a(android.content.Context r2) {
        /*
        r1 = 0;
        if (r2 == 0) goto L_0x0020;
    L_0x0003:
        r0 = "phone";
        r0 = r2.getSystemService(r0);	 Catch:{ Exception -> 0x001d }
        r0 = (android.telephony.TelephonyManager) r0;	 Catch:{ Exception -> 0x001d }
        if (r0 == 0) goto L_0x0020;
    L_0x000e:
        r0 = r0.getDeviceId();	 Catch:{ Exception -> 0x001d }
    L_0x0012:
        r1 = com.ta.utdid2.b.a.i.a(r0);
        if (r1 == 0) goto L_0x001c;
    L_0x0018:
        r0 = c();
    L_0x001c:
        return r0;
    L_0x001d:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0012;
    L_0x0020:
        r0 = r1;
        goto L_0x0012;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.b.a.g.a(android.content.Context):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0018  */
    public static java.lang.String b(android.content.Context r2) {
        /*
        r1 = 0;
        if (r2 == 0) goto L_0x0020;
    L_0x0003:
        r0 = "phone";
        r0 = r2.getSystemService(r0);	 Catch:{ Exception -> 0x001d }
        r0 = (android.telephony.TelephonyManager) r0;	 Catch:{ Exception -> 0x001d }
        if (r0 == 0) goto L_0x0020;
    L_0x000e:
        r0 = r0.getSubscriberId();	 Catch:{ Exception -> 0x001d }
    L_0x0012:
        r1 = com.ta.utdid2.b.a.i.a(r0);
        if (r1 == 0) goto L_0x001c;
    L_0x0018:
        r0 = c();
    L_0x001c:
        return r0;
    L_0x001d:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0012;
    L_0x0020:
        r0 = r1;
        goto L_0x0012;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.b.a.g.b(android.content.Context):java.lang.String");
    }
}
