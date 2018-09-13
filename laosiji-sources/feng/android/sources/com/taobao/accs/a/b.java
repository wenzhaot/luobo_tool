package com.taobao.accs.a;

import android.content.Context;
import com.taobao.accs.a.a.a;

/* compiled from: Taobao */
final class b implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ a b;

    b(Context context, a aVar) {
        this.a = context;
        this.b = aVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0167 A:{Catch:{ Throwable -> 0x016c }} */
    /* JADX WARNING: Removed duplicated region for block: B:54:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0196 A:{SYNTHETIC, Splitter: B:37:0x0196} */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x01a4 A:{SYNTHETIC, Splitter: B:43:0x01a4} */
    public void run() {
        /*
        r9 = this;
        r0 = 0;
        r1 = com.taobao.accs.a.a.c;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        if (r1 != 0) goto L_0x000a;
    L_0x0007:
        com.taobao.accs.a.a.a();	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
    L_0x000a:
        r1 = com.taobao.accs.a.a.c;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        if (r1 == 0) goto L_0x0017;
    L_0x0010:
        r1 = com.taobao.accs.a.a.c;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r1.mkdirs();	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
    L_0x0017:
        r1 = "ElectionServiceUtil";
        r2 = "saveElectionResult electionFile";
        r3 = 6;
        r3 = new java.lang.Object[r3];	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r4 = 0;
        r5 = "path";
        r3[r4] = r5;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r4 = 1;
        r5 = com.taobao.accs.a.a.d;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r5 = r5.getPath();	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r3[r4] = r5;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r4 = 2;
        r5 = "host";
        r3[r4] = r5;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r4 = 3;
        r5 = r9.b;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r5 = r5.a;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r3[r4] = r5;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r4 = 4;
        r5 = "retry";
        r3[r4] = r5;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r4 = 5;
        r5 = r9.b;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r5 = r5.b;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r3[r4] = r5;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        com.taobao.accs.utl.ALog.i(r1, r2, r3);	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r1 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r2 = com.taobao.accs.a.a.d;	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r1.<init>(r2);	 Catch:{ IOException -> 0x01ae, Throwable -> 0x016f, all -> 0x019e }
        r2 = new java.util.HashMap;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r2.<init>();	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r3 = "package";
        r0 = r9.b;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r0 = r0.a;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        if (r0 == 0) goto L_0x00de;
    L_0x006d:
        r0 = "";
    L_0x0070:
        r2.put(r3, r0);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r0 = "retry";
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r3.<init>();	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r4 = r9.b;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r4 = r4.b;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r4 = "";
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r3 = r3.toString();	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r2.put(r0, r3);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r4 = com.taobao.accs.a.a.e;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r6 = 0;
        r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r0 <= 0) goto L_0x00e3;
    L_0x009b:
        r4 = com.taobao.accs.a.a.e;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r6 = java.lang.System.currentTimeMillis();	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r0 >= 0) goto L_0x00e3;
    L_0x00a7:
        r0 = "lastFlushTime";
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r3.<init>();	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r4 = com.taobao.accs.a.a.e;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r4 = "";
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r3 = r3.toString();	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r2.put(r0, r3);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
    L_0x00c5:
        r0 = new org.json.JSONObject;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r0.<init>(r2);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r0 = r0.toString();	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r2 = "UTF-8";
        r0 = r0.getBytes(r2);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r1.write(r0);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        if (r1 == 0) goto L_0x00dd;
    L_0x00da:
        r1.close();	 Catch:{ Throwable -> 0x01b1 }
    L_0x00dd:
        return;
    L_0x00de:
        r0 = r9.b;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r0 = r0.a;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        goto L_0x0070;
    L_0x00e3:
        r0 = "lastFlushTime";
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r3.<init>();	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r4 = java.lang.System.currentTimeMillis();	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r4 = "";
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r3 = r3.toString();	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        r2.put(r0, r3);	 Catch:{ IOException -> 0x0102, Throwable -> 0x01ac }
        goto L_0x00c5;
    L_0x0102:
        r0 = move-exception;
        r0 = r1;
    L_0x0104:
        r1 = new java.io.File;	 Catch:{ Throwable -> 0x016c }
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x016c }
        r2.<init>();	 Catch:{ Throwable -> 0x016c }
        r3 = r9.a;	 Catch:{ Throwable -> 0x016c }
        r3 = r3.getFilesDir();	 Catch:{ Throwable -> 0x016c }
        r3 = r3.getPath();	 Catch:{ Throwable -> 0x016c }
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x016c }
        r3 = com.taobao.accs.a.a.d();	 Catch:{ Throwable -> 0x016c }
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x016c }
        r2 = r2.toString();	 Catch:{ Throwable -> 0x016c }
        r1.<init>(r2);	 Catch:{ Throwable -> 0x016c }
        com.taobao.accs.a.a.c = r1;	 Catch:{ Throwable -> 0x016c }
        r1 = "ElectionServiceUtil";
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x016c }
        r2.<init>();	 Catch:{ Throwable -> 0x016c }
        r3 = "path invailable, new path=";
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x016c }
        r3 = com.taobao.accs.a.a.c;	 Catch:{ Throwable -> 0x016c }
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x016c }
        r2 = r2.toString();	 Catch:{ Throwable -> 0x016c }
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x016c }
        com.taobao.accs.utl.ALog.i(r1, r2, r3);	 Catch:{ Throwable -> 0x016c }
        r1 = new java.io.File;	 Catch:{ Throwable -> 0x016c }
        r2 = com.taobao.accs.a.a.c;	 Catch:{ Throwable -> 0x016c }
        r3 = "accs_election";
        r1.<init>(r2, r3);	 Catch:{ Throwable -> 0x016c }
        com.taobao.accs.a.a.d = r1;	 Catch:{ Throwable -> 0x016c }
        r1 = com.taobao.accs.a.a.d;	 Catch:{ Throwable -> 0x016c }
        r1 = r1.getPath();	 Catch:{ Throwable -> 0x016c }
        com.taobao.accs.a.a.a = r1;	 Catch:{ Throwable -> 0x016c }
        if (r0 == 0) goto L_0x00dd;
    L_0x0167:
        r0.close();	 Catch:{ Throwable -> 0x016c }
        goto L_0x00dd;
    L_0x016c:
        r0 = move-exception;
        goto L_0x00dd;
    L_0x016f:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
    L_0x0173:
        r2 = "ElectionServiceUtil";
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01aa }
        r3.<init>();	 Catch:{ all -> 0x01aa }
        r4 = "saveElectionResult is error,e=";
        r3 = r3.append(r4);	 Catch:{ all -> 0x01aa }
        r0 = r0.toString();	 Catch:{ all -> 0x01aa }
        r0 = r3.append(r0);	 Catch:{ all -> 0x01aa }
        r0 = r0.toString();	 Catch:{ all -> 0x01aa }
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x01aa }
        com.taobao.accs.utl.ALog.e(r2, r0, r3);	 Catch:{ all -> 0x01aa }
        if (r1 == 0) goto L_0x00dd;
    L_0x0196:
        r1.close();	 Catch:{ Throwable -> 0x019b }
        goto L_0x00dd;
    L_0x019b:
        r0 = move-exception;
        goto L_0x00dd;
    L_0x019e:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
    L_0x01a2:
        if (r1 == 0) goto L_0x01a7;
    L_0x01a4:
        r1.close();	 Catch:{ Throwable -> 0x01a8 }
    L_0x01a7:
        throw r0;
    L_0x01a8:
        r1 = move-exception;
        goto L_0x01a7;
    L_0x01aa:
        r0 = move-exception;
        goto L_0x01a2;
    L_0x01ac:
        r0 = move-exception;
        goto L_0x0173;
    L_0x01ae:
        r1 = move-exception;
        goto L_0x0104;
    L_0x01b1:
        r0 = move-exception;
        goto L_0x00dd;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.a.b.run():void");
    }
}
