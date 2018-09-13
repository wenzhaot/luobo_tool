package com.tencent.liteav.g;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/* compiled from: HttpFileUtil */
public class c extends a {
    private Context b;
    private String c;
    private String d;
    private String e;
    private b f;
    private long g;
    private long h;
    private boolean i;

    public c(Context context, String str, String str2, String str3, b bVar, boolean z) {
        this.b = context;
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = bVar;
        this.i = z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:69:0x0139 A:{SYNTHETIC, Splitter: B:69:0x0139} */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x013e A:{Catch:{ IOException -> 0x01a1 }} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0143 A:{Catch:{ IOException -> 0x01a1 }} */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x014e A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x01a7 A:{SYNTHETIC, Splitter: B:103:0x01a7} */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x01ac A:{Catch:{ IOException -> 0x01ba }} */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x01b1 A:{Catch:{ IOException -> 0x01ba }} */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0139 A:{SYNTHETIC, Splitter: B:69:0x0139} */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x013e A:{Catch:{ IOException -> 0x01a1 }} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0143 A:{Catch:{ IOException -> 0x01a1 }} */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x014e A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x01a7 A:{SYNTHETIC, Splitter: B:103:0x01a7} */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x01ac A:{Catch:{ IOException -> 0x01ba }} */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x01b1 A:{Catch:{ IOException -> 0x01ba }} */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0139 A:{SYNTHETIC, Splitter: B:69:0x0139} */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x013e A:{Catch:{ IOException -> 0x01a1 }} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0143 A:{Catch:{ IOException -> 0x01a1 }} */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x014e A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x01a7 A:{SYNTHETIC, Splitter: B:103:0x01a7} */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x01ac A:{Catch:{ IOException -> 0x01ba }} */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x01b1 A:{Catch:{ IOException -> 0x01ba }} */
    public void run() {
        /*
        r15 = this;
        r0 = r15.b;
        r0 = a(r0);
        if (r0 == 0) goto L_0x002b;
    L_0x0008:
        r0 = r15.c;
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 != 0) goto L_0x002b;
    L_0x0010:
        r0 = r15.d;
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 != 0) goto L_0x002b;
    L_0x0018:
        r0 = r15.e;
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 != 0) goto L_0x002b;
    L_0x0020:
        r0 = r15.c;
        r1 = "http";
        r0 = r0.startsWith(r1);
        if (r0 != 0) goto L_0x0031;
    L_0x002b:
        r0 = 0;
        r1 = 0;
        r15.a(r0, r1);
    L_0x0030:
        return;
    L_0x0031:
        r0 = new java.io.File;
        r1 = r15.d;
        r0.<init>(r1);
        r1 = r0.exists();
        if (r1 != 0) goto L_0x00d8;
    L_0x003e:
        r0.mkdirs();
    L_0x0041:
        r7 = new java.io.File;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = r15.d;
        r0 = r0.append(r1);
        r1 = java.io.File.separator;
        r0 = r0.append(r1);
        r1 = r15.e;
        r0 = r0.append(r1);
        r0 = r0.toString();
        r7.<init>(r0);
        r1 = 0;
        r4 = 0;
        r3 = 0;
        r2 = 0;
        r6 = 0;
        r0 = r7.exists();	 Catch:{ Exception -> 0x01ce, all -> 0x01a4 }
        if (r0 == 0) goto L_0x006f;
    L_0x006c:
        r7.delete();	 Catch:{ Exception -> 0x01ce, all -> 0x01a4 }
    L_0x006f:
        r7.createNewFile();	 Catch:{ Exception -> 0x01ce, all -> 0x01a4 }
        r0 = new java.net.URL;	 Catch:{ Exception -> 0x01ce, all -> 0x01a4 }
        r5 = r15.c;	 Catch:{ Exception -> 0x01ce, all -> 0x01a4 }
        r0.<init>(r5);	 Catch:{ Exception -> 0x01ce, all -> 0x01a4 }
        r0 = r0.openConnection();	 Catch:{ Exception -> 0x01ce, all -> 0x01a4 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x01ce, all -> 0x01a4 }
        r1 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r0.setConnectTimeout(r1);	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r1 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r0.setReadTimeout(r1);	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r1 = 1;
        r0.setDoInput(r1);	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r1 = "GET";
        r0.setRequestMethod(r1);	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r5 = r0.getResponseCode();	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r1 = r0.getResponseCode();	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r2 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r1 != r2) goto L_0x00ea;
    L_0x009f:
        r2 = 1;
    L_0x00a0:
        if (r2 == 0) goto L_0x0182;
    L_0x00a2:
        r1 = r15.i;	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        if (r1 == 0) goto L_0x00ec;
    L_0x00a6:
        r1 = r0.getContentLength();	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r8 = (long) r1;	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r15.g = r8;	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r8 = r15.g;	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r1 = com.tencent.ttpic.util.VideoDeviceUtil.isExternalStorageSpaceEnough(r8);	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        if (r1 != 0) goto L_0x00ec;
    L_0x00b5:
        r1 = r15.f;	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        if (r1 == 0) goto L_0x00bf;
    L_0x00b9:
        r1 = r15.f;	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r5 = 0;
        r1.a(r7, r5);	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
    L_0x00bf:
        if (r3 == 0) goto L_0x00c4;
    L_0x00c1:
        r3.close();	 Catch:{ IOException -> 0x00d5 }
    L_0x00c4:
        if (r4 == 0) goto L_0x00c9;
    L_0x00c6:
        r4.close();	 Catch:{ IOException -> 0x00d5 }
    L_0x00c9:
        if (r0 == 0) goto L_0x00ce;
    L_0x00cb:
        r0.disconnect();	 Catch:{ IOException -> 0x00d5 }
    L_0x00ce:
        r0 = r15.f;	 Catch:{ IOException -> 0x00d5 }
        r0.a();	 Catch:{ IOException -> 0x00d5 }
        goto L_0x0030;
    L_0x00d5:
        r0 = move-exception;
        goto L_0x0030;
    L_0x00d8:
        r1 = r0.isFile();
        if (r1 == 0) goto L_0x0041;
    L_0x00de:
        r1 = r15.f;
        if (r1 == 0) goto L_0x0041;
    L_0x00e2:
        r1 = r15.f;
        r2 = 0;
        r1.a(r0, r2);
        goto L_0x0030;
    L_0x00ea:
        r2 = 0;
        goto L_0x00a0;
    L_0x00ec:
        r5 = r0.getInputStream();	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r1 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r1 = new byte[r1];	 Catch:{ Exception -> 0x01df, all -> 0x01c1 }
        r4 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x01df, all -> 0x01c1 }
        r4.<init>(r7);	 Catch:{ Exception -> 0x01df, all -> 0x01c1 }
        r8 = 0;
        r15.h = r8;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
    L_0x00fd:
        r3 = r5.read(r1);	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r8 = -1;
        if (r3 == r8) goto L_0x0158;
    L_0x0104:
        r8 = 0;
        r4.write(r1, r8, r3);	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r8 = r15.i;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        if (r8 == 0) goto L_0x00fd;
    L_0x010c:
        r8 = r15.h;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r10 = 100;
        r8 = r8 * r10;
        r10 = r15.g;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r8 = r8 / r10;
        r8 = (int) r8;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r10 = r15.h;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r12 = (long) r3;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r10 = r10 + r12;
        r15.h = r10;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r10 = r15.h;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r12 = 100;
        r10 = r10 * r12;
        r12 = r15.g;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r10 = r10 / r12;
        r3 = (int) r10;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        if (r8 == r3) goto L_0x00fd;
    L_0x0126:
        r8 = r15.f;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        if (r8 == 0) goto L_0x00fd;
    L_0x012a:
        r8 = r15.f;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r8.a(r3);	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        goto L_0x00fd;
    L_0x0130:
        r1 = move-exception;
        r3 = r5;
        r14 = r2;
        r2 = r4;
        r4 = r0;
        r0 = r1;
        r1 = r14;
    L_0x0137:
        if (r2 == 0) goto L_0x013c;
    L_0x0139:
        r2.close();	 Catch:{ IOException -> 0x01a1 }
    L_0x013c:
        if (r3 == 0) goto L_0x0141;
    L_0x013e:
        r3.close();	 Catch:{ IOException -> 0x01a1 }
    L_0x0141:
        if (r4 == 0) goto L_0x0146;
    L_0x0143:
        r4.disconnect();	 Catch:{ IOException -> 0x01a1 }
    L_0x0146:
        r2 = r15.f;	 Catch:{ IOException -> 0x01a1 }
        r2.a();	 Catch:{ IOException -> 0x01a1 }
        r2 = r1;
    L_0x014c:
        if (r2 == 0) goto L_0x0150;
    L_0x014e:
        if (r0 == 0) goto L_0x0030;
    L_0x0150:
        r0 = r15.f;
        r1 = 0;
        r0.a(r7, r1);
        goto L_0x0030;
    L_0x0158:
        r4.flush();	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r1 = r15.f;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        if (r1 == 0) goto L_0x016b;
    L_0x015f:
        r1 = r15.f;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r3 = 100;
        r1.a(r3);	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r1 = r15.f;	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
        r1.a(r7);	 Catch:{ Exception -> 0x0130, all -> 0x01c7 }
    L_0x016b:
        r1 = r6;
    L_0x016c:
        if (r4 == 0) goto L_0x0171;
    L_0x016e:
        r4.close();	 Catch:{ IOException -> 0x019e }
    L_0x0171:
        if (r5 == 0) goto L_0x0176;
    L_0x0173:
        r5.close();	 Catch:{ IOException -> 0x019e }
    L_0x0176:
        if (r0 == 0) goto L_0x017b;
    L_0x0178:
        r0.disconnect();	 Catch:{ IOException -> 0x019e }
    L_0x017b:
        r0 = r15.f;	 Catch:{ IOException -> 0x019e }
        r0.a();	 Catch:{ IOException -> 0x019e }
        r0 = r1;
        goto L_0x014c;
    L_0x0182:
        r1 = new com.tencent.liteav.g.d;	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r6.<init>();	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r8 = "http status got exception. code = ";
        r6 = r6.append(r8);	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r5 = r6.append(r5);	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r5 = r5.toString();	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r1.<init>(r5);	 Catch:{ Exception -> 0x01d6, all -> 0x01bc }
        r5 = r4;
        r4 = r3;
        goto L_0x016c;
    L_0x019e:
        r0 = move-exception;
        r0 = r1;
        goto L_0x014c;
    L_0x01a1:
        r2 = move-exception;
        r2 = r1;
        goto L_0x014c;
    L_0x01a4:
        r0 = move-exception;
    L_0x01a5:
        if (r3 == 0) goto L_0x01aa;
    L_0x01a7:
        r3.close();	 Catch:{ IOException -> 0x01ba }
    L_0x01aa:
        if (r4 == 0) goto L_0x01af;
    L_0x01ac:
        r4.close();	 Catch:{ IOException -> 0x01ba }
    L_0x01af:
        if (r1 == 0) goto L_0x01b4;
    L_0x01b1:
        r1.disconnect();	 Catch:{ IOException -> 0x01ba }
    L_0x01b4:
        r1 = r15.f;	 Catch:{ IOException -> 0x01ba }
        r1.a();	 Catch:{ IOException -> 0x01ba }
    L_0x01b9:
        throw r0;
    L_0x01ba:
        r1 = move-exception;
        goto L_0x01b9;
    L_0x01bc:
        r1 = move-exception;
        r14 = r1;
        r1 = r0;
        r0 = r14;
        goto L_0x01a5;
    L_0x01c1:
        r1 = move-exception;
        r4 = r5;
        r14 = r1;
        r1 = r0;
        r0 = r14;
        goto L_0x01a5;
    L_0x01c7:
        r1 = move-exception;
        r3 = r4;
        r4 = r5;
        r14 = r1;
        r1 = r0;
        r0 = r14;
        goto L_0x01a5;
    L_0x01ce:
        r0 = move-exception;
        r14 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r1;
        r1 = r14;
        goto L_0x0137;
    L_0x01d6:
        r1 = move-exception;
        r14 = r1;
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r0;
        r0 = r14;
        goto L_0x0137;
    L_0x01df:
        r1 = move-exception;
        r4 = r0;
        r0 = r1;
        r1 = r2;
        r2 = r3;
        r3 = r5;
        goto L_0x0137;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.g.c.run():void");
    }

    public static boolean a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    private void a(Exception exception, int i) {
        if (this.f != null) {
            this.f.a(null, exception);
        }
        this.f = null;
    }
}
