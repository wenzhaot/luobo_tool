package com.umeng.socialize.b.b;

import android.os.Environment;
import android.text.TextUtils;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.DefaultClass;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText.CACHE;
import java.io.File;
import java.io.IOException;

/* compiled from: FileUtil */
public class b {
    private static b b = new b();
    private String a = "";

    private b() {
        try {
            this.a = ContextUtil.getContext().getCacheDir().getCanonicalPath();
        } catch (Throwable e) {
            SLog.error(e);
        }
    }

    public static b a() {
        if (b == null) {
            return new b();
        }
        return b;
    }

    public File b() throws IOException {
        File file = new File(c(), d());
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return file;
    }

    public File c() throws IOException {
        String canonicalPath;
        if (Environment.getExternalStorageDirectory() != null && !TextUtils.isEmpty(Environment.getExternalStorageDirectory().getCanonicalPath())) {
            canonicalPath = Environment.getExternalStorageDirectory().getCanonicalPath();
        } else if (TextUtils.isEmpty(this.a)) {
            canonicalPath = DefaultClass.getString();
            SLog.E(CACHE.SD_NOT_FOUNT);
        } else {
            canonicalPath = this.a;
            SLog.E(CACHE.SD_NOT_FOUNT);
        }
        File file = new File(canonicalPath + c.f);
        if (!(file == null || file.exists())) {
            file.mkdirs();
        }
        return file;
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0053 A:{SYNTHETIC, Splitter: B:36:0x0053} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0058 A:{Catch:{ IOException -> 0x005c }} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0028 A:{SYNTHETIC, Splitter: B:17:0x0028} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x002d A:{Catch:{ IOException -> 0x0047 }} */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0053 A:{SYNTHETIC, Splitter: B:36:0x0053} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0058 A:{Catch:{ IOException -> 0x005c }} */
    public byte[] a(java.io.File r6) {
        /*
        r5 = this;
        r2 = 0;
        r3 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x006b, all -> 0x004e }
        r3.<init>(r6);	 Catch:{ Throwable -> 0x006b, all -> 0x004e }
        r1 = new java.io.ByteArrayOutputStream;	 Catch:{ Throwable -> 0x006e, all -> 0x0063 }
        r1.<init>();	 Catch:{ Throwable -> 0x006e, all -> 0x0063 }
        r0 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r0 = new byte[r0];	 Catch:{ Throwable -> 0x001b, all -> 0x0066 }
    L_0x000f:
        r2 = r3.read(r0);	 Catch:{ Throwable -> 0x001b, all -> 0x0066 }
        r4 = -1;
        if (r2 == r4) goto L_0x0031;
    L_0x0016:
        r4 = 0;
        r1.write(r0, r4, r2);	 Catch:{ Throwable -> 0x001b, all -> 0x0066 }
        goto L_0x000f;
    L_0x001b:
        r0 = move-exception;
        r2 = r3;
    L_0x001d:
        r3 = com.umeng.socialize.utils.UmengText.IMAGE.READ_IMAGE_ERROR;	 Catch:{ all -> 0x0068 }
        com.umeng.socialize.utils.SLog.error(r3, r0);	 Catch:{ all -> 0x0068 }
        r0 = com.umeng.socialize.utils.DefaultClass.getBytes();	 Catch:{ all -> 0x0068 }
        if (r2 == 0) goto L_0x002b;
    L_0x0028:
        r2.close();	 Catch:{ IOException -> 0x0047 }
    L_0x002b:
        if (r1 == 0) goto L_0x0030;
    L_0x002d:
        r1.close();	 Catch:{ IOException -> 0x0047 }
    L_0x0030:
        return r0;
    L_0x0031:
        r0 = r1.toByteArray();	 Catch:{ Throwable -> 0x001b, all -> 0x0066 }
        if (r3 == 0) goto L_0x003a;
    L_0x0037:
        r3.close();	 Catch:{ IOException -> 0x0040 }
    L_0x003a:
        if (r1 == 0) goto L_0x0030;
    L_0x003c:
        r1.close();	 Catch:{ IOException -> 0x0040 }
        goto L_0x0030;
    L_0x0040:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0030;
    L_0x0047:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0030;
    L_0x004e:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
    L_0x0051:
        if (r3 == 0) goto L_0x0056;
    L_0x0053:
        r3.close();	 Catch:{ IOException -> 0x005c }
    L_0x0056:
        if (r1 == 0) goto L_0x005b;
    L_0x0058:
        r1.close();	 Catch:{ IOException -> 0x005c }
    L_0x005b:
        throw r0;
    L_0x005c:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x005b;
    L_0x0063:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0051;
    L_0x0066:
        r0 = move-exception;
        goto L_0x0051;
    L_0x0068:
        r0 = move-exception;
        r3 = r2;
        goto L_0x0051;
    L_0x006b:
        r0 = move-exception;
        r1 = r2;
        goto L_0x001d;
    L_0x006e:
        r0 = move-exception;
        r1 = r2;
        r2 = r3;
        goto L_0x001d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.b.b.b.a(java.io.File):byte[]");
    }

    public String d() {
        return SocializeUtils.hexdigest(String.valueOf(System.currentTimeMillis()));
    }
}
