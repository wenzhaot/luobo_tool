package com.huawei.android.pushselfshow.richpush.tools;

import android.content.Context;

public class c {
    private String a;
    private Context b;

    public c(Context context, String str) {
        this.a = str;
        this.b = context;
    }

    private String b() {
        return "﻿<!DOCTYPE html>\t\t<html>\t\t   <head>\t\t     <meta charset=\"utf-8\">\t\t     <title></title>\t\t     <style type=\"text/css\">\t\t\t\t html { height:100%;}\t\t\t\t body { height:100%; text-align:center;}\t    \t    .centerDiv { display:inline-block; zoom:1; *display:inline; vertical-align:top; text-align:left; width:200px; padding:10px;margin-top:100px;}\t\t\t   .hiddenDiv { height:100%; overflow:hidden; display:inline-block; width:1px; overflow:hidden; margin-left:-1px; zoom:1; *display:inline; *margin-top:-1px; _margin-top:0; vertical-align:middle;}\t\t  \t</style>    \t  </head>\t\t <body>\t\t\t<div id =\"container\" class=\"centerDiv\">";
    }

    private String c() {
        return "﻿\t\t</div>  \t\t<div class=\"hiddenDiv\"></div>\t  </body>   </html>";
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x0153 A:{SYNTHETIC, Splitter: B:49:0x0153} */
    /* JADX WARNING: Removed duplicated region for block: B:62:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x013d A:{SYNTHETIC, Splitter: B:42:0x013d} */
    public java.lang.String a() {
        /*
        r10 = this;
        r0 = 0;
        r1 = 0;
        r2 = r10.b;
        if (r2 != 0) goto L_0x0010;
    L_0x0006:
        r1 = "PushSelfShowLog";
        r2 = "CreateHtmlFile fail ,context is null";
        com.huawei.android.pushagent.a.a.c.d(r1, r2);
    L_0x000f:
        return r0;
    L_0x0010:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = r10.b();
        r2 = r2.append(r3);
        r3 = r10.a;
        r2 = r2.append(r3);
        r3 = r10.c();
        r2 = r2.append(r3);
        r3 = r2.toString();
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r4 = r10.b;
        r4 = r4.getFilesDir();
        r4 = r4.getPath();
        r2 = r2.append(r4);
        r4 = java.io.File.separator;
        r2 = r2.append(r4);
        r4 = "PushService";
        r2 = r2.append(r4);
        r4 = java.io.File.separator;
        r2 = r2.append(r4);
        r4 = "richpush";
        r2 = r2.append(r4);
        r2 = r2.toString();
        r4 = "error.html";
        r5 = new java.io.File;
        r5.<init>(r2);
        r6 = new java.io.File;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r7 = r7.append(r2);
        r8 = java.io.File.separator;
        r7 = r7.append(r8);
        r7 = r7.append(r4);
        r7 = r7.toString();
        r6.<init>(r7);
        r7 = r5.exists();	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        if (r7 != 0) goto L_0x00c6;
    L_0x008a:
        r7 = "PushSelfShowLog";
        r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        r8.<init>();	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        r9 = "Create the path:";
        r8 = r8.append(r9);	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        r2 = r8.append(r2);	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        com.huawei.android.pushagent.a.a.c.a(r7, r2);	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        r2 = r5.mkdirs();	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        if (r2 != 0) goto L_0x00c6;
    L_0x00aa:
        r2 = "PushSelfShowLog";
        r3 = "!path.mkdirs()";
        com.huawei.android.pushagent.a.a.c.a(r2, r3);	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        if (r0 == 0) goto L_0x000f;
    L_0x00b5:
        r1.close();	 Catch:{ Exception -> 0x00ba }
        goto L_0x000f;
    L_0x00ba:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = "stream.close() error ";
        com.huawei.android.pushagent.a.a.c.a(r2, r3, r1);
        goto L_0x000f;
    L_0x00c6:
        r2 = r6.exists();	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        if (r2 == 0) goto L_0x00cf;
    L_0x00cc:
        com.huawei.android.pushselfshow.utils.a.a(r6);	 Catch:{ Exception -> 0x0130, all -> 0x014e }
    L_0x00cf:
        r2 = "PushSelfShowLog";
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        r5.<init>();	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        r7 = "Create the file:";
        r5 = r5.append(r7);	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        r4 = r5.append(r4);	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        r4 = r4.toString();	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        com.huawei.android.pushagent.a.a.c.a(r2, r4);	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        r2 = r6.createNewFile();	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        if (r2 != 0) goto L_0x010b;
    L_0x00ef:
        r2 = "PushSelfShowLog";
        r3 = "!file.createNewFile()";
        com.huawei.android.pushagent.a.a.c.a(r2, r3);	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        if (r0 == 0) goto L_0x000f;
    L_0x00fa:
        r1.close();	 Catch:{ Exception -> 0x00ff }
        goto L_0x000f;
    L_0x00ff:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = "stream.close() error ";
        com.huawei.android.pushagent.a.a.c.a(r2, r3, r1);
        goto L_0x000f;
    L_0x010b:
        r2 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        r2.<init>(r6);	 Catch:{ Exception -> 0x0130, all -> 0x014e }
        r1 = "UTF-8";
        r1 = r3.getBytes(r1);	 Catch:{ Exception -> 0x0164 }
        r2.write(r1);	 Catch:{ Exception -> 0x0164 }
        if (r2 == 0) goto L_0x011f;
    L_0x011c:
        r2.close();	 Catch:{ Exception -> 0x0125 }
    L_0x011f:
        r0 = r6.getAbsolutePath();
        goto L_0x000f;
    L_0x0125:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "stream.close() error ";
        com.huawei.android.pushagent.a.a.c.a(r1, r2, r0);
        goto L_0x011f;
    L_0x0130:
        r1 = move-exception;
        r2 = r0;
    L_0x0132:
        r3 = "PushSelfShowLog";
        r4 = "Create html error ";
        com.huawei.android.pushagent.a.a.c.a(r3, r4, r1);	 Catch:{ all -> 0x0162 }
        if (r2 == 0) goto L_0x000f;
    L_0x013d:
        r2.close();	 Catch:{ Exception -> 0x0142 }
        goto L_0x000f;
    L_0x0142:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = "stream.close() error ";
        com.huawei.android.pushagent.a.a.c.a(r2, r3, r1);
        goto L_0x000f;
    L_0x014e:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
    L_0x0151:
        if (r2 == 0) goto L_0x0156;
    L_0x0153:
        r2.close();	 Catch:{ Exception -> 0x0157 }
    L_0x0156:
        throw r0;
    L_0x0157:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = "stream.close() error ";
        com.huawei.android.pushagent.a.a.c.a(r2, r3, r1);
        goto L_0x0156;
    L_0x0162:
        r0 = move-exception;
        goto L_0x0151;
    L_0x0164:
        r1 = move-exception;
        goto L_0x0132;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.tools.c.a():java.lang.String");
    }
}
