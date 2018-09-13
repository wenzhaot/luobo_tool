package com.baidu.location.e;

class f extends Thread {
    final /* synthetic */ c a;

    f(c cVar) {
        this.a = cVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0097 A:{SYNTHETIC, Splitter: B:26:0x0097} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0163 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0351 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0371 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0391 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x03e0 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x04fe A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x051e A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x053e A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0097 A:{SYNTHETIC, Splitter: B:26:0x0097} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0163 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0351 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0371 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0391 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x03e0 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x04fe A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x051e A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x053e A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0097 A:{SYNTHETIC, Splitter: B:26:0x0097} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0163 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0351 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0371 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0391 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x03e0 A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x04fe A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x051e A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x053e A:{Catch:{ Exception -> 0x0267, all -> 0x02db }} */
    public void run() {
        /*
        r21 = this;
        super.run();
        r0 = r21;
        r2 = r0.a;
        r2 = com.baidu.location.e.d.this;
        r2 = r2.h;
        if (r2 == 0) goto L_0x003b;
    L_0x000f:
        r0 = r21;
        r2 = r0.a;
        r2 = com.baidu.location.e.d.this;
        r2 = r2.i;
        if (r2 == 0) goto L_0x003b;
    L_0x001b:
        r0 = r21;
        r2 = r0.a;
        r2 = com.baidu.location.e.d.this;
        r2 = r2.h;
        r2 = r2.isOpen();
        if (r2 == 0) goto L_0x003b;
    L_0x002b:
        r0 = r21;
        r2 = r0.a;
        r2 = com.baidu.location.e.d.this;
        r2 = r2.i;
        r2 = r2.isOpen();
        if (r2 != 0) goto L_0x0044;
    L_0x003b:
        r0 = r21;
        r2 = r0.a;
        r3 = 0;
        r2.f = r3;
    L_0x0043:
        return;
    L_0x0044:
        r5 = 0;
        r4 = 0;
        r2 = 0;
        r0 = r21;
        r3 = r0.a;	 Catch:{ Exception -> 0x0256 }
        r3 = r3.j;	 Catch:{ Exception -> 0x0256 }
        if (r3 == 0) goto L_0x06c5;
    L_0x004f:
        r0 = r21;
        r3 = r0.a;	 Catch:{ Exception -> 0x0256 }
        r6 = r3.j;	 Catch:{ Exception -> 0x0256 }
        r3 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0256 }
        r3.<init>(r6);	 Catch:{ Exception -> 0x0256 }
        r4 = "model";
        r4 = r3.has(r4);	 Catch:{ Exception -> 0x06b1 }
        if (r4 == 0) goto L_0x06c2;
    L_0x0063:
        r4 = "model";
        r4 = r3.getJSONObject(r4);	 Catch:{ Exception -> 0x06b1 }
    L_0x006a:
        r5 = "rgc";
        r5 = r3.has(r5);	 Catch:{ Exception -> 0x06b9 }
        if (r5 == 0) goto L_0x007a;
    L_0x0073:
        r5 = "rgc";
        r2 = r3.getJSONObject(r5);	 Catch:{ Exception -> 0x06b9 }
    L_0x007a:
        r9 = r4;
    L_0x007b:
        r0 = r21;
        r4 = r0.a;	 Catch:{ Exception -> 0x06ae }
        r4 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06ae }
        r4 = r4.h;	 Catch:{ Exception -> 0x06ae }
        r4.beginTransaction();	 Catch:{ Exception -> 0x06ae }
        r0 = r21;
        r4 = r0.a;	 Catch:{ Exception -> 0x06ae }
        r4 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06ae }
        r4 = r4.i;	 Catch:{ Exception -> 0x06ae }
        r4.beginTransaction();	 Catch:{ Exception -> 0x06ae }
    L_0x0095:
        if (r2 == 0) goto L_0x00a8;
    L_0x0097:
        r0 = r21;
        r4 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r4 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r4 = r4.a;	 Catch:{ Exception -> 0x0267 }
        r4 = r4.k();	 Catch:{ Exception -> 0x0267 }
        r4.a(r2);	 Catch:{ Exception -> 0x0267 }
    L_0x00a8:
        if (r3 == 0) goto L_0x00ce;
    L_0x00aa:
        r2 = "type";
        r2 = r3.has(r2);	 Catch:{ Exception -> 0x0267 }
        if (r2 == 0) goto L_0x00ce;
    L_0x00b3:
        r2 = "type";
        r2 = r3.getString(r2);	 Catch:{ Exception -> 0x0267 }
        r4 = "0";
        r2 = r2.equals(r4);	 Catch:{ Exception -> 0x0267 }
        if (r2 == 0) goto L_0x00ce;
    L_0x00c3:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r4 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0267 }
        r2.r = r4;	 Catch:{ Exception -> 0x0267 }
    L_0x00ce:
        if (r3 == 0) goto L_0x00f2;
    L_0x00d0:
        r2 = "bdlist";
        r2 = r3.has(r2);	 Catch:{ Exception -> 0x0267 }
        if (r2 == 0) goto L_0x00f2;
    L_0x00d9:
        r2 = "bdlist";
        r2 = r3.getString(r2);	 Catch:{ Exception -> 0x0267 }
        r4 = ";";
        r2 = r2.split(r4);	 Catch:{ Exception -> 0x0267 }
        r0 = r21;
        r4 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r4 = r4.e;	 Catch:{ Exception -> 0x0267 }
        r4.a(r2);	 Catch:{ Exception -> 0x0267 }
    L_0x00f2:
        if (r3 == 0) goto L_0x0132;
    L_0x00f4:
        r2 = "loadurl";
        r2 = r3.has(r2);	 Catch:{ Exception -> 0x0267 }
        if (r2 == 0) goto L_0x0132;
    L_0x00fd:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.e;	 Catch:{ Exception -> 0x0267 }
        r4 = "loadurl";
        r4 = r3.getJSONObject(r4);	 Catch:{ Exception -> 0x0267 }
        r5 = "host";
        r4 = r4.getString(r5);	 Catch:{ Exception -> 0x0267 }
        r5 = "loadurl";
        r5 = r3.getJSONObject(r5);	 Catch:{ Exception -> 0x0267 }
        r6 = "module";
        r5 = r5.getString(r6);	 Catch:{ Exception -> 0x0267 }
        r6 = "loadurl";
        r3 = r3.getJSONObject(r6);	 Catch:{ Exception -> 0x0267 }
        r6 = "req";
        r3 = r3.getString(r6);	 Catch:{ Exception -> 0x0267 }
        r2.a(r4, r5, r3);	 Catch:{ Exception -> 0x0267 }
    L_0x0132:
        if (r9 == 0) goto L_0x03af;
    L_0x0134:
        r2 = "cell";
        r2 = r9.has(r2);	 Catch:{ Exception -> 0x0267 }
        if (r2 == 0) goto L_0x03af;
    L_0x013d:
        r2 = "cell";
        r10 = r9.getJSONObject(r2);	 Catch:{ Exception -> 0x0267 }
        r11 = r10.keys();	 Catch:{ Exception -> 0x0267 }
        r12 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x0267 }
        r12.<init>();	 Catch:{ Exception -> 0x0267 }
        r13 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x0267 }
        r13.<init>();	 Catch:{ Exception -> 0x0267 }
        r14 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x0267 }
        r14.<init>();	 Catch:{ Exception -> 0x0267 }
        r8 = 1;
        r7 = 1;
        r5 = 1;
        r3 = 0;
        r6 = 0;
        r4 = 0;
    L_0x015d:
        r2 = r11.hasNext();	 Catch:{ Exception -> 0x0267 }
        if (r2 == 0) goto L_0x034f;
    L_0x0163:
        r2 = r11.next();	 Catch:{ Exception -> 0x0267 }
        r2 = (java.lang.String) r2;	 Catch:{ Exception -> 0x0267 }
        r15 = r10.getString(r2);	 Catch:{ Exception -> 0x0267 }
        r16 = ",";
        r16 = r15.split(r16);	 Catch:{ Exception -> 0x0267 }
        r17 = 3;
        r16 = r16[r17];	 Catch:{ Exception -> 0x0267 }
        r16 = java.lang.Double.valueOf(r16);	 Catch:{ Exception -> 0x0267 }
        if (r7 == 0) goto L_0x025e;
    L_0x017e:
        r7 = 0;
    L_0x017f:
        r13.append(r2);	 Catch:{ Exception -> 0x0267 }
        r6 = r6 + 1;
        r16 = r16.doubleValue();	 Catch:{ Exception -> 0x0267 }
        r18 = 0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 <= 0) goto L_0x033e;
    L_0x018e:
        if (r5 == 0) goto L_0x02d2;
    L_0x0190:
        r5 = 0;
    L_0x0191:
        r16 = 40;
        r0 = r16;
        r16 = r14.append(r0);	 Catch:{ Exception -> 0x0267 }
        r0 = r16;
        r2 = r0.append(r2);	 Catch:{ Exception -> 0x0267 }
        r16 = 44;
        r0 = r16;
        r2 = r2.append(r0);	 Catch:{ Exception -> 0x0267 }
        r2 = r2.append(r15);	 Catch:{ Exception -> 0x0267 }
        r15 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0267 }
        r15.<init>();	 Catch:{ Exception -> 0x0267 }
        r16 = ",";
        r15 = r15.append(r16);	 Catch:{ Exception -> 0x0267 }
        r16 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0267 }
        r18 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r16 = r16 / r18;
        r15 = r15.append(r16);	 Catch:{ Exception -> 0x0267 }
        r15 = r15.toString();	 Catch:{ Exception -> 0x0267 }
        r2 = r2.append(r15);	 Catch:{ Exception -> 0x0267 }
        r15 = 41;
        r2.append(r15);	 Catch:{ Exception -> 0x0267 }
        r4 = r4 + 1;
        r2 = r3;
        r3 = r8;
    L_0x01d4:
        r8 = 100;
        if (r6 < r8) goto L_0x01fe;
    L_0x01d8:
        r0 = r21;
        r7 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r7 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r7 = r7.i;	 Catch:{ Exception -> 0x0267 }
        r8 = "DELETE FROM CL WHERE id IN (%s);";
        r15 = 1;
        r15 = new java.lang.Object[r15];	 Catch:{ Exception -> 0x0267 }
        r16 = 0;
        r17 = r13.toString();	 Catch:{ Exception -> 0x0267 }
        r15[r16] = r17;	 Catch:{ Exception -> 0x0267 }
        r8 = java.lang.String.format(r8, r15);	 Catch:{ Exception -> 0x0267 }
        r7.execSQL(r8);	 Catch:{ Exception -> 0x0267 }
        r7 = 1;
        r8 = 0;
        r13.setLength(r8);	 Catch:{ Exception -> 0x0267 }
        r6 = r6 + -100;
    L_0x01fe:
        r8 = 100;
        if (r4 < r8) goto L_0x0228;
    L_0x0202:
        r0 = r21;
        r5 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r5 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r5 = r5.h;	 Catch:{ Exception -> 0x0267 }
        r8 = "INSERT OR REPLACE INTO CL (id,x,y,r,cl,timestamp) VALUES %s;";
        r15 = 1;
        r15 = new java.lang.Object[r15];	 Catch:{ Exception -> 0x0267 }
        r16 = 0;
        r17 = r14.toString();	 Catch:{ Exception -> 0x0267 }
        r15[r16] = r17;	 Catch:{ Exception -> 0x0267 }
        r8 = java.lang.String.format(r8, r15);	 Catch:{ Exception -> 0x0267 }
        r5.execSQL(r8);	 Catch:{ Exception -> 0x0267 }
        r5 = 1;
        r8 = 0;
        r14.setLength(r8);	 Catch:{ Exception -> 0x0267 }
        r4 = r4 + -100;
    L_0x0228:
        r8 = 100;
        if (r2 < r8) goto L_0x0252;
    L_0x022c:
        r0 = r21;
        r3 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r3 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r3 = r3.h;	 Catch:{ Exception -> 0x0267 }
        r8 = "DELETE FROM CL WHERE id IN (%s);";
        r15 = 1;
        r15 = new java.lang.Object[r15];	 Catch:{ Exception -> 0x0267 }
        r16 = 0;
        r17 = r12.toString();	 Catch:{ Exception -> 0x0267 }
        r15[r16] = r17;	 Catch:{ Exception -> 0x0267 }
        r8 = java.lang.String.format(r8, r15);	 Catch:{ Exception -> 0x0267 }
        r3.execSQL(r8);	 Catch:{ Exception -> 0x0267 }
        r3 = 1;
        r8 = 0;
        r12.setLength(r8);	 Catch:{ Exception -> 0x0267 }
        r2 = r2 + -100;
    L_0x0252:
        r8 = r3;
        r3 = r2;
        goto L_0x015d;
    L_0x0256:
        r3 = move-exception;
    L_0x0257:
        r3.printStackTrace();
        r3 = r4;
        r9 = r5;
        goto L_0x007b;
    L_0x025e:
        r17 = 44;
        r0 = r17;
        r13.append(r0);	 Catch:{ Exception -> 0x0267 }
        goto L_0x017f;
    L_0x0267:
        r2 = move-exception;
        r0 = r21;
        r2 = r0.a;	 Catch:{ all -> 0x02db }
        r2.c();	 Catch:{ all -> 0x02db }
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x06a9 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06a9 }
        r2 = r2.h;	 Catch:{ Exception -> 0x06a9 }
        if (r2 == 0) goto L_0x0298;
    L_0x027b:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x06a9 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06a9 }
        r2 = r2.h;	 Catch:{ Exception -> 0x06a9 }
        r2 = r2.isOpen();	 Catch:{ Exception -> 0x06a9 }
        if (r2 == 0) goto L_0x0298;
    L_0x028b:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x06a9 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06a9 }
        r2 = r2.h;	 Catch:{ Exception -> 0x06a9 }
        r2.endTransaction();	 Catch:{ Exception -> 0x06a9 }
    L_0x0298:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x06a9 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06a9 }
        r2 = r2.i;	 Catch:{ Exception -> 0x06a9 }
        if (r2 == 0) goto L_0x02c1;
    L_0x02a4:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x06a9 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06a9 }
        r2 = r2.i;	 Catch:{ Exception -> 0x06a9 }
        r2 = r2.isOpen();	 Catch:{ Exception -> 0x06a9 }
        if (r2 == 0) goto L_0x02c1;
    L_0x02b4:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x06a9 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06a9 }
        r2 = r2.i;	 Catch:{ Exception -> 0x06a9 }
        r2.endTransaction();	 Catch:{ Exception -> 0x06a9 }
    L_0x02c1:
        r0 = r21;
        r2 = r0.a;
        r3 = 0;
        r2.j = r3;
        r0 = r21;
        r2 = r0.a;
        r3 = 0;
        r2.f = r3;
        goto L_0x0043;
    L_0x02d2:
        r16 = 44;
        r0 = r16;
        r14.append(r0);	 Catch:{ Exception -> 0x0267 }
        goto L_0x0191;
    L_0x02db:
        r2 = move-exception;
        r0 = r21;
        r3 = r0.a;	 Catch:{ Exception -> 0x06a6 }
        r3 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06a6 }
        r3 = r3.h;	 Catch:{ Exception -> 0x06a6 }
        if (r3 == 0) goto L_0x0305;
    L_0x02e8:
        r0 = r21;
        r3 = r0.a;	 Catch:{ Exception -> 0x06a6 }
        r3 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06a6 }
        r3 = r3.h;	 Catch:{ Exception -> 0x06a6 }
        r3 = r3.isOpen();	 Catch:{ Exception -> 0x06a6 }
        if (r3 == 0) goto L_0x0305;
    L_0x02f8:
        r0 = r21;
        r3 = r0.a;	 Catch:{ Exception -> 0x06a6 }
        r3 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06a6 }
        r3 = r3.h;	 Catch:{ Exception -> 0x06a6 }
        r3.endTransaction();	 Catch:{ Exception -> 0x06a6 }
    L_0x0305:
        r0 = r21;
        r3 = r0.a;	 Catch:{ Exception -> 0x06a6 }
        r3 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06a6 }
        r3 = r3.i;	 Catch:{ Exception -> 0x06a6 }
        if (r3 == 0) goto L_0x032e;
    L_0x0311:
        r0 = r21;
        r3 = r0.a;	 Catch:{ Exception -> 0x06a6 }
        r3 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06a6 }
        r3 = r3.i;	 Catch:{ Exception -> 0x06a6 }
        r3 = r3.isOpen();	 Catch:{ Exception -> 0x06a6 }
        if (r3 == 0) goto L_0x032e;
    L_0x0321:
        r0 = r21;
        r3 = r0.a;	 Catch:{ Exception -> 0x06a6 }
        r3 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06a6 }
        r3 = r3.i;	 Catch:{ Exception -> 0x06a6 }
        r3.endTransaction();	 Catch:{ Exception -> 0x06a6 }
    L_0x032e:
        r0 = r21;
        r3 = r0.a;
        r4 = 0;
        r3.j = r4;
        r0 = r21;
        r3 = r0.a;
        r4 = 0;
        r3.f = r4;
        throw r2;
    L_0x033e:
        if (r8 == 0) goto L_0x0349;
    L_0x0340:
        r8 = 0;
    L_0x0341:
        r12.append(r2);	 Catch:{ Exception -> 0x0267 }
        r2 = r3 + 1;
        r3 = r8;
        goto L_0x01d4;
    L_0x0349:
        r15 = 44;
        r12.append(r15);	 Catch:{ Exception -> 0x0267 }
        goto L_0x0341;
    L_0x034f:
        if (r6 <= 0) goto L_0x036f;
    L_0x0351:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.i;	 Catch:{ Exception -> 0x0267 }
        r5 = "DELETE FROM CL WHERE id IN (%s);";
        r6 = 1;
        r6 = new java.lang.Object[r6];	 Catch:{ Exception -> 0x0267 }
        r7 = 0;
        r8 = r13.toString();	 Catch:{ Exception -> 0x0267 }
        r6[r7] = r8;	 Catch:{ Exception -> 0x0267 }
        r5 = java.lang.String.format(r5, r6);	 Catch:{ Exception -> 0x0267 }
        r2.execSQL(r5);	 Catch:{ Exception -> 0x0267 }
    L_0x036f:
        if (r4 <= 0) goto L_0x038f;
    L_0x0371:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.h;	 Catch:{ Exception -> 0x0267 }
        r4 = "INSERT OR REPLACE INTO CL (id,x,y,r,cl,timestamp) VALUES %s;";
        r5 = 1;
        r5 = new java.lang.Object[r5];	 Catch:{ Exception -> 0x0267 }
        r6 = 0;
        r7 = r14.toString();	 Catch:{ Exception -> 0x0267 }
        r5[r6] = r7;	 Catch:{ Exception -> 0x0267 }
        r4 = java.lang.String.format(r4, r5);	 Catch:{ Exception -> 0x0267 }
        r2.execSQL(r4);	 Catch:{ Exception -> 0x0267 }
    L_0x038f:
        if (r3 <= 0) goto L_0x03af;
    L_0x0391:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.h;	 Catch:{ Exception -> 0x0267 }
        r3 = "DELETE FROM CL WHERE id IN (%s);";
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x0267 }
        r5 = 0;
        r6 = r12.toString();	 Catch:{ Exception -> 0x0267 }
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r3 = java.lang.String.format(r3, r4);	 Catch:{ Exception -> 0x0267 }
        r2.execSQL(r3);	 Catch:{ Exception -> 0x0267 }
    L_0x03af:
        if (r9 == 0) goto L_0x055c;
    L_0x03b1:
        r2 = "ap";
        r2 = r9.has(r2);	 Catch:{ Exception -> 0x0267 }
        if (r2 == 0) goto L_0x055c;
    L_0x03ba:
        r2 = "ap";
        r10 = r9.getJSONObject(r2);	 Catch:{ Exception -> 0x0267 }
        r11 = r10.keys();	 Catch:{ Exception -> 0x0267 }
        r8 = 0;
        r5 = 0;
        r7 = 0;
        r6 = 1;
        r4 = 1;
        r3 = 1;
        r12 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x0267 }
        r12.<init>();	 Catch:{ Exception -> 0x0267 }
        r13 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x0267 }
        r13.<init>();	 Catch:{ Exception -> 0x0267 }
        r14 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x0267 }
        r14.<init>();	 Catch:{ Exception -> 0x0267 }
    L_0x03da:
        r2 = r11.hasNext();	 Catch:{ Exception -> 0x0267 }
        if (r2 == 0) goto L_0x04fc;
    L_0x03e0:
        r2 = r11.next();	 Catch:{ Exception -> 0x0267 }
        r2 = (java.lang.String) r2;	 Catch:{ Exception -> 0x0267 }
        r15 = r10.getString(r2);	 Catch:{ Exception -> 0x0267 }
        r16 = ",";
        r16 = r15.split(r16);	 Catch:{ Exception -> 0x0267 }
        r17 = 3;
        r16 = r16[r17];	 Catch:{ Exception -> 0x0267 }
        r16 = java.lang.Double.valueOf(r16);	 Catch:{ Exception -> 0x0267 }
        if (r4 == 0) goto L_0x04d4;
    L_0x03fb:
        r4 = 0;
    L_0x03fc:
        r13.append(r2);	 Catch:{ Exception -> 0x0267 }
        r5 = r5 + 1;
        r16 = r16.doubleValue();	 Catch:{ Exception -> 0x0267 }
        r18 = 0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 <= 0) goto L_0x04e6;
    L_0x040b:
        if (r3 == 0) goto L_0x04dd;
    L_0x040d:
        r3 = 0;
    L_0x040e:
        r16 = 40;
        r0 = r16;
        r16 = r14.append(r0);	 Catch:{ Exception -> 0x0267 }
        r0 = r16;
        r2 = r0.append(r2);	 Catch:{ Exception -> 0x0267 }
        r16 = 44;
        r0 = r16;
        r2 = r2.append(r0);	 Catch:{ Exception -> 0x0267 }
        r2 = r2.append(r15);	 Catch:{ Exception -> 0x0267 }
        r15 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0267 }
        r15.<init>();	 Catch:{ Exception -> 0x0267 }
        r16 = ",";
        r15 = r15.append(r16);	 Catch:{ Exception -> 0x0267 }
        r16 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0267 }
        r18 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r16 = r16 / r18;
        r15 = r15.append(r16);	 Catch:{ Exception -> 0x0267 }
        r15 = r15.toString();	 Catch:{ Exception -> 0x0267 }
        r2 = r2.append(r15);	 Catch:{ Exception -> 0x0267 }
        r15 = 41;
        r2.append(r15);	 Catch:{ Exception -> 0x0267 }
        r2 = r7 + 1;
        r7 = r8;
        r20 = r2;
        r2 = r3;
        r3 = r20;
    L_0x0455:
        r8 = 100;
        if (r5 < r8) goto L_0x047f;
    L_0x0459:
        r0 = r21;
        r4 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r4 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r4 = r4.i;	 Catch:{ Exception -> 0x0267 }
        r8 = "DELETE FROM AP WHERE id IN (%s);";
        r15 = 1;
        r15 = new java.lang.Object[r15];	 Catch:{ Exception -> 0x0267 }
        r16 = 0;
        r17 = r13.toString();	 Catch:{ Exception -> 0x0267 }
        r15[r16] = r17;	 Catch:{ Exception -> 0x0267 }
        r8 = java.lang.String.format(r8, r15);	 Catch:{ Exception -> 0x0267 }
        r4.execSQL(r8);	 Catch:{ Exception -> 0x0267 }
        r4 = 1;
        r8 = 0;
        r13.setLength(r8);	 Catch:{ Exception -> 0x0267 }
        r5 = r5 + -100;
    L_0x047f:
        r8 = 100;
        if (r3 < r8) goto L_0x04a9;
    L_0x0483:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.h;	 Catch:{ Exception -> 0x0267 }
        r8 = "INSERT OR REPLACE INTO AP (id,x,y,r,cl,timestamp) VALUES %s;";
        r15 = 1;
        r15 = new java.lang.Object[r15];	 Catch:{ Exception -> 0x0267 }
        r16 = 0;
        r17 = r14.toString();	 Catch:{ Exception -> 0x0267 }
        r15[r16] = r17;	 Catch:{ Exception -> 0x0267 }
        r8 = java.lang.String.format(r8, r15);	 Catch:{ Exception -> 0x0267 }
        r2.execSQL(r8);	 Catch:{ Exception -> 0x0267 }
        r2 = 1;
        r8 = 0;
        r14.setLength(r8);	 Catch:{ Exception -> 0x0267 }
        r3 = r3 + -100;
    L_0x04a9:
        if (r7 <= 0) goto L_0x04cf;
    L_0x04ab:
        r0 = r21;
        r8 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r8 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r8 = r8.h;	 Catch:{ Exception -> 0x0267 }
        r15 = "DELETE FROM AP WHERE id IN (%s);";
        r16 = 1;
        r0 = r16;
        r0 = new java.lang.Object[r0];	 Catch:{ Exception -> 0x0267 }
        r16 = r0;
        r17 = 0;
        r18 = r12.toString();	 Catch:{ Exception -> 0x0267 }
        r16[r17] = r18;	 Catch:{ Exception -> 0x0267 }
        r15 = java.lang.String.format(r15, r16);	 Catch:{ Exception -> 0x0267 }
        r8.execSQL(r15);	 Catch:{ Exception -> 0x0267 }
    L_0x04cf:
        r8 = r7;
        r7 = r3;
        r3 = r2;
        goto L_0x03da;
    L_0x04d4:
        r17 = 44;
        r0 = r17;
        r13.append(r0);	 Catch:{ Exception -> 0x0267 }
        goto L_0x03fc;
    L_0x04dd:
        r16 = 44;
        r0 = r16;
        r14.append(r0);	 Catch:{ Exception -> 0x0267 }
        goto L_0x040e;
    L_0x04e6:
        if (r6 == 0) goto L_0x04f6;
    L_0x04e8:
        r6 = 0;
    L_0x04e9:
        r12.append(r2);	 Catch:{ Exception -> 0x0267 }
        r2 = r8 + 1;
        r20 = r3;
        r3 = r7;
        r7 = r2;
        r2 = r20;
        goto L_0x0455;
    L_0x04f6:
        r15 = 44;
        r12.append(r15);	 Catch:{ Exception -> 0x0267 }
        goto L_0x04e9;
    L_0x04fc:
        if (r5 <= 0) goto L_0x051c;
    L_0x04fe:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.i;	 Catch:{ Exception -> 0x0267 }
        r3 = "DELETE FROM AP WHERE id IN (%s);";
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x0267 }
        r5 = 0;
        r6 = r13.toString();	 Catch:{ Exception -> 0x0267 }
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r3 = java.lang.String.format(r3, r4);	 Catch:{ Exception -> 0x0267 }
        r2.execSQL(r3);	 Catch:{ Exception -> 0x0267 }
    L_0x051c:
        if (r7 <= 0) goto L_0x053c;
    L_0x051e:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.h;	 Catch:{ Exception -> 0x0267 }
        r3 = "INSERT OR REPLACE INTO AP (id,x,y,r,cl,timestamp) VALUES %s;";
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x0267 }
        r5 = 0;
        r6 = r14.toString();	 Catch:{ Exception -> 0x0267 }
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r3 = java.lang.String.format(r3, r4);	 Catch:{ Exception -> 0x0267 }
        r2.execSQL(r3);	 Catch:{ Exception -> 0x0267 }
    L_0x053c:
        if (r8 <= 0) goto L_0x055c;
    L_0x053e:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.h;	 Catch:{ Exception -> 0x0267 }
        r3 = "DELETE FROM AP WHERE id IN (%s);";
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x0267 }
        r5 = 0;
        r6 = r12.toString();	 Catch:{ Exception -> 0x0267 }
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r3 = java.lang.String.format(r3, r4);	 Catch:{ Exception -> 0x0267 }
        r2.execSQL(r3);	 Catch:{ Exception -> 0x0267 }
    L_0x055c:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.h;	 Catch:{ Exception -> 0x0267 }
        r3 = "DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY timestamp DESC, frequency DESC LIMIT %d);";
        r4 = 3;
        r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x0267 }
        r5 = 0;
        r6 = "AP";
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r5 = 1;
        r6 = "AP";
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r5 = 2;
        r6 = 200000; // 0x30d40 float:2.8026E-40 double:9.8813E-319;
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ Exception -> 0x0267 }
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r3 = java.lang.String.format(r3, r4);	 Catch:{ Exception -> 0x0267 }
        r2.execSQL(r3);	 Catch:{ Exception -> 0x0267 }
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.h;	 Catch:{ Exception -> 0x0267 }
        r3 = "DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY timestamp DESC, frequency DESC LIMIT %d);";
        r4 = 3;
        r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x0267 }
        r5 = 0;
        r6 = "CL";
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r5 = 1;
        r6 = "CL";
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r5 = 2;
        r6 = 200000; // 0x30d40 float:2.8026E-40 double:9.8813E-319;
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ Exception -> 0x0267 }
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r3 = java.lang.String.format(r3, r4);	 Catch:{ Exception -> 0x0267 }
        r2.execSQL(r3);	 Catch:{ Exception -> 0x0267 }
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.i;	 Catch:{ Exception -> 0x0267 }
        r3 = "DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY frequency DESC LIMIT %d);";
        r4 = 3;
        r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x0267 }
        r5 = 0;
        r6 = "AP";
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r5 = 1;
        r6 = "AP";
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r5 = 2;
        r6 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ Exception -> 0x0267 }
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r3 = java.lang.String.format(r3, r4);	 Catch:{ Exception -> 0x0267 }
        r2.execSQL(r3);	 Catch:{ Exception -> 0x0267 }
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.i;	 Catch:{ Exception -> 0x0267 }
        r3 = "DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY frequency DESC LIMIT %d);";
        r4 = 3;
        r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x0267 }
        r5 = 0;
        r6 = "CL";
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r5 = 1;
        r6 = "CL";
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r5 = 2;
        r6 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ Exception -> 0x0267 }
        r4[r5] = r6;	 Catch:{ Exception -> 0x0267 }
        r3 = java.lang.String.format(r3, r4);	 Catch:{ Exception -> 0x0267 }
        r2.execSQL(r3);	 Catch:{ Exception -> 0x0267 }
        if (r9 == 0) goto L_0x0629;
    L_0x0610:
        r2 = "ap";
        r2 = r9.has(r2);	 Catch:{ Exception -> 0x0267 }
        if (r2 != 0) goto L_0x0629;
    L_0x0619:
        r2 = "cell";
        r2 = r9.has(r2);	 Catch:{ Exception -> 0x0267 }
        if (r2 != 0) goto L_0x0629;
    L_0x0622:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2.c();	 Catch:{ Exception -> 0x0267 }
    L_0x0629:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.h;	 Catch:{ Exception -> 0x0267 }
        r2.setTransactionSuccessful();	 Catch:{ Exception -> 0x0267 }
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x0267 }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x0267 }
        r2 = r2.i;	 Catch:{ Exception -> 0x0267 }
        r2.setTransactionSuccessful();	 Catch:{ Exception -> 0x0267 }
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x06ac }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06ac }
        r2 = r2.h;	 Catch:{ Exception -> 0x06ac }
        if (r2 == 0) goto L_0x066c;
    L_0x064f:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x06ac }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06ac }
        r2 = r2.h;	 Catch:{ Exception -> 0x06ac }
        r2 = r2.isOpen();	 Catch:{ Exception -> 0x06ac }
        if (r2 == 0) goto L_0x066c;
    L_0x065f:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x06ac }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06ac }
        r2 = r2.h;	 Catch:{ Exception -> 0x06ac }
        r2.endTransaction();	 Catch:{ Exception -> 0x06ac }
    L_0x066c:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x06ac }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06ac }
        r2 = r2.i;	 Catch:{ Exception -> 0x06ac }
        if (r2 == 0) goto L_0x0695;
    L_0x0678:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x06ac }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06ac }
        r2 = r2.i;	 Catch:{ Exception -> 0x06ac }
        r2 = r2.isOpen();	 Catch:{ Exception -> 0x06ac }
        if (r2 == 0) goto L_0x0695;
    L_0x0688:
        r0 = r21;
        r2 = r0.a;	 Catch:{ Exception -> 0x06ac }
        r2 = com.baidu.location.e.d.this;	 Catch:{ Exception -> 0x06ac }
        r2 = r2.i;	 Catch:{ Exception -> 0x06ac }
        r2.endTransaction();	 Catch:{ Exception -> 0x06ac }
    L_0x0695:
        r0 = r21;
        r2 = r0.a;
        r3 = 0;
        r2.j = r3;
        r0 = r21;
        r2 = r0.a;
        r3 = 0;
        r2.f = r3;
        goto L_0x0043;
    L_0x06a6:
        r3 = move-exception;
        goto L_0x032e;
    L_0x06a9:
        r2 = move-exception;
        goto L_0x02c1;
    L_0x06ac:
        r2 = move-exception;
        goto L_0x0695;
    L_0x06ae:
        r4 = move-exception;
        goto L_0x0095;
    L_0x06b1:
        r4 = move-exception;
        r20 = r4;
        r4 = r3;
        r3 = r20;
        goto L_0x0257;
    L_0x06b9:
        r5 = move-exception;
        r20 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r20;
        goto L_0x0257;
    L_0x06c2:
        r4 = r5;
        goto L_0x006a;
    L_0x06c5:
        r3 = r4;
        r4 = r5;
        goto L_0x007a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.e.f.run():void");
    }
}
