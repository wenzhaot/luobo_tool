package com.huawei.android.pushselfshow.richpush.tools;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.utils.b.a;
import com.huawei.android.pushselfshow.utils.b.b;
import java.io.File;

public class d {
    public static String a(Context context, String str) {
        c.a("PushSelfShowLog", "download richpush file successed ,try to unzip file,file path is " + str);
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.startsWith(b.b(context))) {
            String str2 = "";
            try {
                str2 = str.substring(0, str.lastIndexOf(File.separator));
                new a(str, str2 + File.separator).a();
                File file = new File(str2 + "/" + "index.html");
                if (file.exists()) {
                    c.a("PushSelfShowLog", "unzip success ,so delete src zip file");
                    File file2 = new File(str);
                    if (file2.exists()) {
                        com.huawei.android.pushselfshow.utils.a.a(file2);
                    }
                    return file.getAbsolutePath();
                }
                c.a("PushSelfShowLog", "unzip fail ,don't exist index.html");
                com.huawei.android.pushselfshow.utils.a.a(new File(str2));
                return null;
            } catch (IndexOutOfBoundsException e) {
                c.d("PushSelfShowLog", e.toString());
                return "";
            }
        }
        c.a("PushSelfShowLog", "localfile dose not startsWith PushService directory");
        return "";
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x004c  */
    public java.lang.String a(android.content.Context r8, java.lang.String r9, int r10, java.lang.String r11) {
        /*
        r7 = this;
        r1 = 0;
        r0 = new com.huawei.android.pushselfshow.utils.b.b;	 Catch:{ Exception -> 0x002a }
        r0.<init>();	 Catch:{ Exception -> 0x002a }
        r0 = r0.a(r8, r9, r11);	 Catch:{ Exception -> 0x002a }
        if (r0 == 0) goto L_0x0013;
    L_0x000c:
        r2 = r0.length();	 Catch:{ Exception -> 0x004e }
        if (r2 <= 0) goto L_0x0013;
    L_0x0012:
        return r0;
    L_0x0013:
        r2 = "PushSelfShowLog";
        r3 = "download failed";
        com.huawei.android.pushagent.a.a.c.a(r2, r3);	 Catch:{ Exception -> 0x004e }
    L_0x001c:
        if (r10 <= 0) goto L_0x004c;
    L_0x001e:
        r2 = r10 + -1;
        if (r2 <= 0) goto L_0x0028;
    L_0x0022:
        r2 = r7.a(r8, r9, r2, r11);
        if (r2 != 0) goto L_0x0012;
    L_0x0028:
        r0 = r1;
        goto L_0x0012;
    L_0x002a:
        r0 = move-exception;
        r2 = r1;
    L_0x002c:
        r3 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "download err";
        r4 = r4.append(r5);
        r0 = r0.toString();
        r0 = r4.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.a.a.c.a(r3, r0);
        r0 = r2;
        goto L_0x001c;
    L_0x004c:
        r10 = 1;
        goto L_0x001e;
    L_0x004e:
        r2 = move-exception;
        r6 = r2;
        r2 = r0;
        r0 = r6;
        goto L_0x002c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.tools.d.a(android.content.Context, java.lang.String, int, java.lang.String):java.lang.String");
    }
}
