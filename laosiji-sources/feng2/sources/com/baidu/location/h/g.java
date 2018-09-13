package com.baidu.location.h;

class g extends Thread {
    final /* synthetic */ f a;

    g(f fVar) {
        this.a = fVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:71:0x0121 A:{LOOP_END, LOOP:0: B:1:0x001b->B:71:0x0121} */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x009b A:{SYNTHETIC, EDGE_INSN: B:94:0x009b->B:31:0x009b ?: BREAK  } */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x008f A:{SYNTHETIC, Splitter: B:24:0x008f} */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0151  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0094 A:{SYNTHETIC, Splitter: B:27:0x0094} */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x009b A:{SYNTHETIC, EDGE_INSN: B:94:0x009b->B:31:0x009b ?: BREAK  } */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0121 A:{LOOP_END, LOOP:0: B:1:0x001b->B:71:0x0121} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x008f A:{SYNTHETIC, Splitter: B:24:0x008f} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0094 A:{SYNTHETIC, Splitter: B:27:0x0094} */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0151  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0121 A:{LOOP_END, LOOP:0: B:1:0x001b->B:71:0x0121} */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x009b A:{SYNTHETIC, EDGE_INSN: B:94:0x009b->B:31:0x009b ?: BREAK  } */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x008f A:{SYNTHETIC, Splitter: B:24:0x008f} */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0151  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0094 A:{SYNTHETIC, Splitter: B:27:0x0094} */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x009b A:{SYNTHETIC, EDGE_INSN: B:94:0x009b->B:31:0x009b ?: BREAK  } */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0121 A:{LOOP_END, LOOP:0: B:1:0x001b->B:71:0x0121} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0113 A:{SYNTHETIC, Splitter: B:64:0x0113} */
    public void run() {
        /*
        r13 = this;
        r2 = 0;
        r6 = 1;
        r5 = 0;
        r0 = r13.a;
        r1 = com.baidu.location.h.k.c();
        r0.h = r1;
        r0 = r13.a;
        r0.b();
        r0 = r13.a;
        r0.a();
        r0 = r13.a;
        r0 = r0.i;
        r1 = r2;
        r4 = r0;
    L_0x001b:
        if (r4 <= 0) goto L_0x009b;
    L_0x001d:
        r0 = new java.net.URL;	 Catch:{ Exception -> 0x014b, all -> 0x013c }
        r3 = r13.a;	 Catch:{ Exception -> 0x014b, all -> 0x013c }
        r3 = r3.h;	 Catch:{ Exception -> 0x014b, all -> 0x013c }
        r0.<init>(r3);	 Catch:{ Exception -> 0x014b, all -> 0x013c }
        r0 = r0.openConnection();	 Catch:{ Exception -> 0x014b, all -> 0x013c }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x014b, all -> 0x013c }
        r1 = "GET";
        r0.setRequestMethod(r1);	 Catch:{ Exception -> 0x013f, all -> 0x0102 }
        r1 = 1;
        r0.setDoInput(r1);	 Catch:{ Exception -> 0x013f, all -> 0x0102 }
        r1 = 1;
        r0.setDoOutput(r1);	 Catch:{ Exception -> 0x013f, all -> 0x0102 }
        r1 = 0;
        r0.setUseCaches(r1);	 Catch:{ Exception -> 0x013f, all -> 0x0102 }
        r1 = com.baidu.location.h.a.b;	 Catch:{ Exception -> 0x013f, all -> 0x0102 }
        r0.setConnectTimeout(r1);	 Catch:{ Exception -> 0x013f, all -> 0x0102 }
        r1 = com.baidu.location.h.a.b;	 Catch:{ Exception -> 0x013f, all -> 0x0102 }
        r0.setReadTimeout(r1);	 Catch:{ Exception -> 0x013f, all -> 0x0102 }
        r1 = "Content-Type";
        r3 = "application/x-www-form-urlencoded; charset=utf-8";
        r0.setRequestProperty(r1, r3);	 Catch:{ Exception -> 0x013f, all -> 0x0102 }
        r1 = "Accept-Charset";
        r3 = "UTF-8";
        r0.setRequestProperty(r1, r3);	 Catch:{ Exception -> 0x013f, all -> 0x0102 }
        r1 = r0.getResponseCode();	 Catch:{ Exception -> 0x013f, all -> 0x0102 }
        r3 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r1 != r3) goto L_0x00e2;
    L_0x0062:
        r3 = r0.getInputStream();	 Catch:{ Exception -> 0x013f, all -> 0x0102 }
        r1 = new java.io.ByteArrayOutputStream;	 Catch:{ Exception -> 0x0145, all -> 0x0129 }
        r1.<init>();	 Catch:{ Exception -> 0x0145, all -> 0x0129 }
        r7 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r7 = new byte[r7];	 Catch:{ Exception -> 0x007b, all -> 0x012e }
    L_0x006f:
        r8 = r3.read(r7);	 Catch:{ Exception -> 0x007b, all -> 0x012e }
        r9 = -1;
        if (r8 == r9) goto L_0x00ad;
    L_0x0076:
        r9 = 0;
        r1.write(r7, r9, r8);	 Catch:{ Exception -> 0x007b, all -> 0x012e }
        goto L_0x006f;
    L_0x007b:
        r7 = move-exception;
        r11 = r1;
        r1 = r3;
        r3 = r0;
        r0 = r11;
    L_0x0080:
        r7 = com.baidu.location.h.a.a;	 Catch:{ all -> 0x0134 }
        r8 = "NetworkCommunicationException!";
        android.util.Log.d(r7, r8);	 Catch:{ all -> 0x0134 }
        if (r3 == 0) goto L_0x008d;
    L_0x008a:
        r3.disconnect();
    L_0x008d:
        if (r1 == 0) goto L_0x0092;
    L_0x008f:
        r1.close();	 Catch:{ Exception -> 0x00f6 }
    L_0x0092:
        if (r0 == 0) goto L_0x0151;
    L_0x0094:
        r0.close();	 Catch:{ Exception -> 0x00fb }
        r0 = r5;
        r1 = r3;
    L_0x0099:
        if (r0 == 0) goto L_0x0121;
    L_0x009b:
        if (r4 > 0) goto L_0x0126;
    L_0x009d:
        r0 = com.baidu.location.h.f.o;
        r0 = r0 + 1;
        com.baidu.location.h.f.o = r0;
        r0 = r13.a;
        r0.j = r2;
        r0 = r13.a;
        r0.a(r5);
    L_0x00ac:
        return;
    L_0x00ad:
        r3.close();	 Catch:{ Exception -> 0x007b, all -> 0x012e }
        r1.close();	 Catch:{ Exception -> 0x007b, all -> 0x012e }
        r7 = r13.a;	 Catch:{ Exception -> 0x007b, all -> 0x012e }
        r8 = new java.lang.String;	 Catch:{ Exception -> 0x007b, all -> 0x012e }
        r9 = r1.toByteArray();	 Catch:{ Exception -> 0x007b, all -> 0x012e }
        r10 = "utf-8";
        r8.<init>(r9, r10);	 Catch:{ Exception -> 0x007b, all -> 0x012e }
        r7.j = r8;	 Catch:{ Exception -> 0x007b, all -> 0x012e }
        r7 = r13.a;	 Catch:{ Exception -> 0x007b, all -> 0x012e }
        r8 = 1;
        r7.a(r8);	 Catch:{ Exception -> 0x007b, all -> 0x012e }
        r0.disconnect();	 Catch:{ Exception -> 0x007b, all -> 0x012e }
        r7 = r3;
        r3 = r1;
        r1 = r6;
    L_0x00cf:
        if (r0 == 0) goto L_0x00d4;
    L_0x00d1:
        r0.disconnect();
    L_0x00d4:
        if (r7 == 0) goto L_0x00d9;
    L_0x00d6:
        r7.close();	 Catch:{ Exception -> 0x00e9 }
    L_0x00d9:
        if (r3 == 0) goto L_0x0155;
    L_0x00db:
        r3.close();	 Catch:{ Exception -> 0x00ee }
        r11 = r1;
        r1 = r0;
        r0 = r11;
        goto L_0x0099;
    L_0x00e2:
        r0.disconnect();	 Catch:{ Exception -> 0x013f, all -> 0x0102 }
        r1 = r5;
        r3 = r2;
        r7 = r2;
        goto L_0x00cf;
    L_0x00e9:
        r7 = move-exception;
        r7.printStackTrace();
        goto L_0x00d9;
    L_0x00ee:
        r3 = move-exception;
        r3.printStackTrace();
        r11 = r1;
        r1 = r0;
        r0 = r11;
        goto L_0x0099;
    L_0x00f6:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0092;
    L_0x00fb:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = r5;
        r1 = r3;
        goto L_0x0099;
    L_0x0102:
        r1 = move-exception;
        r3 = r2;
        r11 = r1;
        r1 = r0;
        r0 = r11;
    L_0x0107:
        if (r1 == 0) goto L_0x010c;
    L_0x0109:
        r1.disconnect();
    L_0x010c:
        if (r3 == 0) goto L_0x0111;
    L_0x010e:
        r3.close();	 Catch:{ Exception -> 0x0117 }
    L_0x0111:
        if (r2 == 0) goto L_0x0116;
    L_0x0113:
        r2.close();	 Catch:{ Exception -> 0x011c }
    L_0x0116:
        throw r0;
    L_0x0117:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0111;
    L_0x011c:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0116;
    L_0x0121:
        r0 = r4 + -1;
        r4 = r0;
        goto L_0x001b;
    L_0x0126:
        com.baidu.location.h.f.o = r5;
        goto L_0x00ac;
    L_0x0129:
        r1 = move-exception;
        r11 = r1;
        r1 = r0;
        r0 = r11;
        goto L_0x0107;
    L_0x012e:
        r2 = move-exception;
        r11 = r2;
        r2 = r1;
        r1 = r0;
        r0 = r11;
        goto L_0x0107;
    L_0x0134:
        r2 = move-exception;
        r11 = r2;
        r2 = r0;
        r0 = r11;
        r12 = r1;
        r1 = r3;
        r3 = r12;
        goto L_0x0107;
    L_0x013c:
        r0 = move-exception;
        r3 = r2;
        goto L_0x0107;
    L_0x013f:
        r1 = move-exception;
        r1 = r2;
        r3 = r0;
        r0 = r2;
        goto L_0x0080;
    L_0x0145:
        r1 = move-exception;
        r1 = r3;
        r3 = r0;
        r0 = r2;
        goto L_0x0080;
    L_0x014b:
        r0 = move-exception;
        r0 = r2;
        r3 = r1;
        r1 = r2;
        goto L_0x0080;
    L_0x0151:
        r0 = r5;
        r1 = r3;
        goto L_0x0099;
    L_0x0155:
        r11 = r1;
        r1 = r0;
        r0 = r11;
        goto L_0x0099;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.h.g.run():void");
    }
}
