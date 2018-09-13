package com.tencent.stat;

class t implements Runnable {
    final /* synthetic */ n a;

    t(n nVar) {
        this.a = nVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0060  */
    public void run() {
        /*
        r9 = this;
        r8 = 0;
        r0 = r9.a;	 Catch:{ Throwable -> 0x0066, all -> 0x005c }
        r0 = r0.d;	 Catch:{ Throwable -> 0x0066, all -> 0x005c }
        r0 = r0.getReadableDatabase();	 Catch:{ Throwable -> 0x0066, all -> 0x005c }
        r1 = "config";
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r1 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x0066, all -> 0x005c }
    L_0x0018:
        r0 = r1.moveToNext();	 Catch:{ Throwable -> 0x0048 }
        if (r0 == 0) goto L_0x0056;
    L_0x001e:
        r0 = 0;
        r0 = r1.getInt(r0);	 Catch:{ Throwable -> 0x0048 }
        r2 = 1;
        r2 = r1.getString(r2);	 Catch:{ Throwable -> 0x0048 }
        r3 = 2;
        r3 = r1.getString(r3);	 Catch:{ Throwable -> 0x0048 }
        r4 = 3;
        r4 = r1.getInt(r4);	 Catch:{ Throwable -> 0x0048 }
        r5 = new com.tencent.stat.b;	 Catch:{ Throwable -> 0x0048 }
        r5.<init>(r0);	 Catch:{ Throwable -> 0x0048 }
        r5.a = r0;	 Catch:{ Throwable -> 0x0048 }
        r0 = new org.json.JSONObject;	 Catch:{ Throwable -> 0x0048 }
        r0.<init>(r2);	 Catch:{ Throwable -> 0x0048 }
        r5.b = r0;	 Catch:{ Throwable -> 0x0048 }
        r5.c = r3;	 Catch:{ Throwable -> 0x0048 }
        r5.d = r4;	 Catch:{ Throwable -> 0x0048 }
        com.tencent.stat.StatConfig.a(r5);	 Catch:{ Throwable -> 0x0048 }
        goto L_0x0018;
    L_0x0048:
        r0 = move-exception;
    L_0x0049:
        r2 = com.tencent.stat.n.e;	 Catch:{ all -> 0x0064 }
        r2.e(r0);	 Catch:{ all -> 0x0064 }
        if (r1 == 0) goto L_0x0055;
    L_0x0052:
        r1.close();
    L_0x0055:
        return;
    L_0x0056:
        if (r1 == 0) goto L_0x0055;
    L_0x0058:
        r1.close();
        goto L_0x0055;
    L_0x005c:
        r0 = move-exception;
        r1 = r8;
    L_0x005e:
        if (r1 == 0) goto L_0x0063;
    L_0x0060:
        r1.close();
    L_0x0063:
        throw r0;
    L_0x0064:
        r0 = move-exception;
        goto L_0x005e;
    L_0x0066:
        r0 = move-exception;
        r1 = r8;
        goto L_0x0049;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.t.run():void");
    }
}
