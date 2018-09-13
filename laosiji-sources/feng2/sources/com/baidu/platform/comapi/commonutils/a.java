package com.baidu.platform.comapi.commonutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class a {
    private static final boolean a = (VERSION.SDK_INT >= 8);

    public static Bitmap a(String str, Context context) {
        try {
            InputStream open = context.getAssets().open(str);
            return open != null ? BitmapFactory.decodeStream(open) : null;
        } catch (Exception e) {
            return BitmapFactory.decodeFile(b("assets/" + str, str, context));
        }
    }

    private static void a(InputStream inputStream, FileOutputStream fileOutputStream) throws IOException {
        byte[] bArr = new byte[4096];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            } finally {
                try {
                    inputStream.close();
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        return;
                    }
                } catch (IOException e2) {
                    return;
                }
            }
        }
        fileOutputStream.flush();
        try {
            fileOutputStream.close();
        } catch (IOException e3) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0090 A:{SYNTHETIC, Splitter: B:32:0x0090} */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0095 A:{Catch:{ IOException -> 0x0099 }} */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0090 A:{SYNTHETIC, Splitter: B:32:0x0090} */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0095 A:{Catch:{ IOException -> 0x0099 }} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x007b A:{SYNTHETIC, Splitter: B:23:0x007b} */
    /* JADX WARNING: Removed duplicated region for block: B:52:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0080 A:{Catch:{ IOException -> 0x0084 }} */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0090 A:{SYNTHETIC, Splitter: B:32:0x0090} */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0095 A:{Catch:{ IOException -> 0x0099 }} */
    public static void a(java.lang.String r7, java.lang.String r8, android.content.Context r9) {
        /*
        r0 = 0;
        r1 = r9.getAssets();	 Catch:{ Exception -> 0x0060, all -> 0x0089 }
        r2 = r1.open(r7);	 Catch:{ Exception -> 0x0060, all -> 0x0089 }
        if (r2 == 0) goto L_0x00b2;
    L_0x000b:
        r1 = r2.available();	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r3 = new byte[r1];	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r2.read(r3);	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r4 = new java.io.File;	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r1.<init>();	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r5 = r9.getFilesDir();	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r5 = r5.getAbsolutePath();	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r1 = r1.append(r5);	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r5 = "/";
        r1 = r1.append(r5);	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r1 = r1.append(r8);	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r1 = r1.toString();	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r4.<init>(r1);	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r1 = r4.exists();	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        if (r1 == 0) goto L_0x0042;
    L_0x003f:
        r4.delete();	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
    L_0x0042:
        r4.createNewFile();	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r1 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r1.<init>(r4);	 Catch:{ Exception -> 0x00ab, all -> 0x009e }
        r1.write(r3);	 Catch:{ Exception -> 0x00ae, all -> 0x00a3 }
        r1.close();	 Catch:{ Exception -> 0x00ae, all -> 0x00a3 }
    L_0x0050:
        if (r2 == 0) goto L_0x0055;
    L_0x0052:
        r2.close();	 Catch:{ IOException -> 0x005b }
    L_0x0055:
        if (r1 == 0) goto L_0x005a;
    L_0x0057:
        r1.close();	 Catch:{ IOException -> 0x005b }
    L_0x005a:
        return;
    L_0x005b:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x005a;
    L_0x0060:
        r1 = move-exception;
        r1 = r0;
    L_0x0062:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00a5 }
        r2.<init>();	 Catch:{ all -> 0x00a5 }
        r3 = "assets/";
        r2 = r2.append(r3);	 Catch:{ all -> 0x00a5 }
        r2 = r2.append(r7);	 Catch:{ all -> 0x00a5 }
        r2 = r2.toString();	 Catch:{ all -> 0x00a5 }
        b(r2, r8, r9);	 Catch:{ all -> 0x00a5 }
        if (r1 == 0) goto L_0x007e;
    L_0x007b:
        r1.close();	 Catch:{ IOException -> 0x0084 }
    L_0x007e:
        if (r0 == 0) goto L_0x005a;
    L_0x0080:
        r0.close();	 Catch:{ IOException -> 0x0084 }
        goto L_0x005a;
    L_0x0084:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x005a;
    L_0x0089:
        r1 = move-exception;
        r2 = r0;
        r6 = r0;
        r0 = r1;
        r1 = r6;
    L_0x008e:
        if (r2 == 0) goto L_0x0093;
    L_0x0090:
        r2.close();	 Catch:{ IOException -> 0x0099 }
    L_0x0093:
        if (r1 == 0) goto L_0x0098;
    L_0x0095:
        r1.close();	 Catch:{ IOException -> 0x0099 }
    L_0x0098:
        throw r0;
    L_0x0099:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0098;
    L_0x009e:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x008e;
    L_0x00a3:
        r0 = move-exception;
        goto L_0x008e;
    L_0x00a5:
        r2 = move-exception;
        r6 = r2;
        r2 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x008e;
    L_0x00ab:
        r1 = move-exception;
        r1 = r2;
        goto L_0x0062;
    L_0x00ae:
        r0 = move-exception;
        r0 = r1;
        r1 = r2;
        goto L_0x0062;
    L_0x00b2:
        r1 = r0;
        goto L_0x0050;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.commonutils.a.a(java.lang.String, java.lang.String, android.content.Context):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c4 A:{SYNTHETIC, Splitter: B:34:0x00c4} */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c4 A:{SYNTHETIC, Splitter: B:34:0x00c4} */
    private static java.lang.String b(java.lang.String r9, java.lang.String r10, android.content.Context r11) {
        /*
        r1 = 0;
        r0 = "";
        r4 = new java.lang.StringBuilder;
        r2 = r11.getFilesDir();
        r2 = r2.getAbsolutePath();
        r4.<init>(r2);
        r2 = a;
        if (r2 == 0) goto L_0x0019;
    L_0x0015:
        r0 = r11.getPackageCodePath();
    L_0x0019:
        r2 = new java.util.zip.ZipFile;	 Catch:{ Exception -> 0x00d3, all -> 0x00c0 }
        r2.<init>(r0);	 Catch:{ Exception -> 0x00d3, all -> 0x00c0 }
        r0 = "/";
        r0 = r10.lastIndexOf(r0);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        if (r0 <= 0) goto L_0x0074;
    L_0x0027:
        r3 = new java.io.File;	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r5 = r11.getFilesDir();	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r5 = r5.getAbsolutePath();	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r3.<init>(r5);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r5 = 0;
        r5 = r10.substring(r5, r0);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r0 = r0 + 1;
        r6 = r10.length();	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r6 = r10.substring(r0, r6);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r0 = new java.io.File;	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r7.<init>();	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r8 = r3.getAbsolutePath();	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r7 = r7.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r8 = "/";
        r7 = r7.append(r8);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r5 = r7.append(r5);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r5 = r5.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r0.<init>(r5, r6);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
    L_0x0064:
        r3.mkdirs();	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r3 = r2.getEntry(r9);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        if (r3 != 0) goto L_0x00a2;
    L_0x006d:
        if (r2 == 0) goto L_0x0072;
    L_0x006f:
        r2.close();	 Catch:{ IOException -> 0x00c8 }
    L_0x0072:
        r0 = r1;
    L_0x0073:
        return r0;
    L_0x0074:
        r3 = new java.io.File;	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r0 = r11.getFilesDir();	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r5 = "assets";
        r3.<init>(r0, r5);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r0 = new java.io.File;	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r5 = r3.getAbsolutePath();	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r0.<init>(r5, r10);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        goto L_0x0064;
    L_0x008a:
        r0 = move-exception;
        r1 = r2;
    L_0x008c:
        r2 = com.baidu.platform.comapi.commonutils.a.class;
        r2 = r2.getSimpleName();	 Catch:{ all -> 0x00d0 }
        r3 = "copyAssetsError";
        android.util.Log.e(r2, r3, r0);	 Catch:{ all -> 0x00d0 }
        if (r1 == 0) goto L_0x009d;
    L_0x009a:
        r1.close();	 Catch:{ IOException -> 0x00ca }
    L_0x009d:
        r0 = r4.toString();
        goto L_0x0073;
    L_0x00a2:
        r1 = r2.getInputStream(r3);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r3 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r3.<init>(r0);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        a(r1, r3);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r0 = "/";
        r0 = r4.append(r0);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        r0.append(r9);	 Catch:{ Exception -> 0x008a, all -> 0x00ce }
        if (r2 == 0) goto L_0x009d;
    L_0x00ba:
        r2.close();	 Catch:{ IOException -> 0x00be }
        goto L_0x009d;
    L_0x00be:
        r0 = move-exception;
        goto L_0x009d;
    L_0x00c0:
        r0 = move-exception;
        r2 = r1;
    L_0x00c2:
        if (r2 == 0) goto L_0x00c7;
    L_0x00c4:
        r2.close();	 Catch:{ IOException -> 0x00cc }
    L_0x00c7:
        throw r0;
    L_0x00c8:
        r0 = move-exception;
        goto L_0x0072;
    L_0x00ca:
        r0 = move-exception;
        goto L_0x009d;
    L_0x00cc:
        r1 = move-exception;
        goto L_0x00c7;
    L_0x00ce:
        r0 = move-exception;
        goto L_0x00c2;
    L_0x00d0:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00c2;
    L_0x00d3:
        r0 = move-exception;
        goto L_0x008c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.commonutils.a.b(java.lang.String, java.lang.String, android.content.Context):java.lang.String");
    }
}
