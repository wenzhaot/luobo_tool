package com.huawei.android.pushselfshow.utils.c;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import com.huawei.android.pushagent.a.a.c;

public class a {
    public Bitmap a(Context context, Bitmap bitmap, float f, float f2) {
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float f3 = f / ((float) width);
            float f4 = f2 / ((float) height);
            Matrix matrix = new Matrix();
            matrix.postScale(f3, f4);
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            if (createBitmap == null) {
                return bitmap;
            }
            c.a("PushSelfShowLog", "reScaleBitmap success");
            return createBitmap;
        } catch (Throwable e) {
            c.c("PushSelfShowLog", "reScaleBitmap fail ,error ：" + e, e);
            return bitmap;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x013c A:{SYNTHETIC, Splitter: B:35:0x013c} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x017e A:{SYNTHETIC, Splitter: B:48:0x017e} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x017e A:{SYNTHETIC, Splitter: B:48:0x017e} */
    public android.graphics.Bitmap a(android.content.Context r8, java.lang.String r9) {
        /*
        r7 = this;
        r0 = 0;
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r1.<init>();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = "image";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = com.huawei.android.pushselfshow.utils.b.b.a(r8);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r3 = new java.io.File;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r3.<init>(r2);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r4 = r3.exists();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        if (r4 != 0) goto L_0x006a;
    L_0x0028:
        r4 = "PushSelfShowLog";
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r5.<init>();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r6 = "mkdir: ";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r6 = r3.getAbsolutePath();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        com.huawei.android.pushagent.a.a.c.a(r4, r5);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r4 = r3.mkdirs();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        if (r4 != 0) goto L_0x006a;
    L_0x004c:
        r4 = "PushSelfShowLog";
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r5.<init>();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r6 = "file mkdir failed ,path is ";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r3 = r3.getPath();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r3 = r5.append(r3);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        com.huawei.android.pushagent.a.a.c.a(r4, r3);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
    L_0x006a:
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r3.<init>();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = r3.append(r2);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r3 = java.io.File.separator;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r1 = r2.append(r1);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r3.<init>();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r4 = "try to download image to ";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r3 = r3.append(r1);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        com.huawei.android.pushagent.a.a.c.a(r2, r3);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = new com.huawei.android.pushselfshow.utils.b.b;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2.<init>();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = r2.b(r8, r9, r1);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        if (r2 == 0) goto L_0x00ed;
    L_0x00a6:
        r2 = "PushSelfShowLog";
        r3 = "download successed";
        com.huawei.android.pushagent.a.a.c.a(r2, r3);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r4 = new android.graphics.BitmapFactory$Options;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r4.<init>();	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = 0;
        r4.inDither = r2;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = 1;
        r4.inPurgeable = r2;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = 1;
        r4.inSampleSize = r2;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = android.graphics.Bitmap.Config.RGB_565;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r4.inPreferredConfig = r2;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = new java.io.File;	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2.<init>(r1);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r3 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x01bf, all -> 0x01b9 }
        r3.<init>(r2);	 Catch:{ Exception -> 0x01bf, all -> 0x01b9 }
        r1 = 0;
        r0 = android.graphics.BitmapFactory.decodeStream(r3, r1, r4);	 Catch:{ Exception -> 0x01c3 }
    L_0x00d0:
        if (r3 == 0) goto L_0x00d5;
    L_0x00d2:
        r3.close();	 Catch:{ Exception -> 0x00f9 }
    L_0x00d5:
        if (r2 == 0) goto L_0x00ec;
    L_0x00d7:
        r1 = r2.isFile();	 Catch:{ Exception -> 0x00f9 }
        if (r1 == 0) goto L_0x00ec;
    L_0x00dd:
        r1 = r2.delete();	 Catch:{ Exception -> 0x00f9 }
        if (r1 == 0) goto L_0x00ec;
    L_0x00e3:
        r1 = "PushSelfShowLog";
        r2 = "image delete success";
        com.huawei.android.pushagent.a.a.c.a(r1, r2);	 Catch:{ Exception -> 0x00f9 }
    L_0x00ec:
        return r0;
    L_0x00ed:
        r1 = "PushSelfShowLog";
        r2 = "download failed";
        com.huawei.android.pushagent.a.a.c.a(r1, r2);	 Catch:{ Exception -> 0x0119, all -> 0x0178 }
        r2 = r0;
        r3 = r0;
        goto L_0x00d0;
    L_0x00f9:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "is.close() error";
        r3 = r3.append(r4);
        r4 = r1.toString();
        r3 = r3.append(r4);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r1);
        goto L_0x00ec;
    L_0x0119:
        r1 = move-exception;
        r2 = r0;
        r3 = r0;
    L_0x011c:
        r4 = "PushSelfShowLog";
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01bd }
        r5.<init>();	 Catch:{ all -> 0x01bd }
        r6 = "getRemoteImage  failed  ,errorinfo is ";
        r5 = r5.append(r6);	 Catch:{ all -> 0x01bd }
        r6 = r1.toString();	 Catch:{ all -> 0x01bd }
        r5 = r5.append(r6);	 Catch:{ all -> 0x01bd }
        r5 = r5.toString();	 Catch:{ all -> 0x01bd }
        com.huawei.android.pushagent.a.a.c.c(r4, r5, r1);	 Catch:{ all -> 0x01bd }
        if (r3 == 0) goto L_0x013f;
    L_0x013c:
        r3.close();	 Catch:{ Exception -> 0x0157 }
    L_0x013f:
        if (r2 == 0) goto L_0x00ec;
    L_0x0141:
        r1 = r2.isFile();	 Catch:{ Exception -> 0x0157 }
        if (r1 == 0) goto L_0x00ec;
    L_0x0147:
        r1 = r2.delete();	 Catch:{ Exception -> 0x0157 }
        if (r1 == 0) goto L_0x00ec;
    L_0x014d:
        r1 = "PushSelfShowLog";
        r2 = "image delete success";
        com.huawei.android.pushagent.a.a.c.a(r1, r2);	 Catch:{ Exception -> 0x0157 }
        goto L_0x00ec;
    L_0x0157:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "is.close() error";
        r3 = r3.append(r4);
        r4 = r1.toString();
        r3 = r3.append(r4);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r1);
        goto L_0x00ec;
    L_0x0178:
        r1 = move-exception;
        r2 = r0;
        r3 = r0;
        r0 = r1;
    L_0x017c:
        if (r3 == 0) goto L_0x0181;
    L_0x017e:
        r3.close();	 Catch:{ Exception -> 0x0199 }
    L_0x0181:
        if (r2 == 0) goto L_0x0198;
    L_0x0183:
        r1 = r2.isFile();	 Catch:{ Exception -> 0x0199 }
        if (r1 == 0) goto L_0x0198;
    L_0x0189:
        r1 = r2.delete();	 Catch:{ Exception -> 0x0199 }
        if (r1 == 0) goto L_0x0198;
    L_0x018f:
        r1 = "PushSelfShowLog";
        r2 = "image delete success";
        com.huawei.android.pushagent.a.a.c.a(r1, r2);	 Catch:{ Exception -> 0x0199 }
    L_0x0198:
        throw r0;
    L_0x0199:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "is.close() error";
        r3 = r3.append(r4);
        r4 = r1.toString();
        r3 = r3.append(r4);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r1);
        goto L_0x0198;
    L_0x01b9:
        r1 = move-exception;
        r3 = r0;
        r0 = r1;
        goto L_0x017c;
    L_0x01bd:
        r0 = move-exception;
        goto L_0x017c;
    L_0x01bf:
        r1 = move-exception;
        r3 = r0;
        goto L_0x011c;
    L_0x01c3:
        r1 = move-exception;
        goto L_0x011c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.c.a.a(android.content.Context, java.lang.String):android.graphics.Bitmap");
    }
}
