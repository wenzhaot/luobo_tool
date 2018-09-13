package com.feng.library.okhttp.callback;

import java.io.File;
import okhttp3.Response;

public abstract class FileCallBack extends Callback<File> {
    private String destFileDir;
    private String destFileName;

    public abstract void inProgress(float f, long j);

    public FileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    public File parseNetworkResponse(Response response) throws Exception {
        return saveFile(response);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0060 A:{SYNTHETIC, Splitter: B:14:0x0060} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0065 A:{SYNTHETIC, Splitter: B:17:0x0065} */
    public java.io.File saveFile(okhttp3.Response r19) throws java.io.IOException {
        /*
        r18 = this;
        r13 = 0;
        r2 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r8 = new byte[r2];
        r14 = 0;
        r11 = 0;
        r2 = r19.body();	 Catch:{ all -> 0x007f }
        r13 = r2.byteStream();	 Catch:{ all -> 0x007f }
        r2 = r19.body();	 Catch:{ all -> 0x007f }
        r6 = r2.contentLength();	 Catch:{ all -> 0x007f }
        r16 = 0;
        r9 = new java.io.File;	 Catch:{ all -> 0x007f }
        r0 = r18;
        r2 = r0.destFileDir;	 Catch:{ all -> 0x007f }
        r9.<init>(r2);	 Catch:{ all -> 0x007f }
        r2 = r9.exists();	 Catch:{ all -> 0x007f }
        if (r2 != 0) goto L_0x002b;
    L_0x0028:
        r9.mkdirs();	 Catch:{ all -> 0x007f }
    L_0x002b:
        r10 = new java.io.File;	 Catch:{ all -> 0x007f }
        r0 = r18;
        r2 = r0.destFileName;	 Catch:{ all -> 0x007f }
        r10.<init>(r9, r2);	 Catch:{ all -> 0x007f }
        r12 = new java.io.FileOutputStream;	 Catch:{ all -> 0x007f }
        r12.<init>(r10);	 Catch:{ all -> 0x007f }
    L_0x0039:
        r14 = r13.read(r8);	 Catch:{ all -> 0x005c }
        r2 = -1;
        if (r14 == r2) goto L_0x0069;
    L_0x0040:
        r2 = (long) r14;	 Catch:{ all -> 0x005c }
        r16 = r16 + r2;
        r2 = 0;
        r12.write(r8, r2, r14);	 Catch:{ all -> 0x005c }
        r4 = r16;
        r2 = com.feng.library.okhttp.utils.OkHttpUtils.getInstance();	 Catch:{ all -> 0x005c }
        r15 = r2.getDelivery();	 Catch:{ all -> 0x005c }
        r2 = new com.feng.library.okhttp.callback.FileCallBack$1;	 Catch:{ all -> 0x005c }
        r3 = r18;
        r2.<init>(r4, r6);	 Catch:{ all -> 0x005c }
        r15.post(r2);	 Catch:{ all -> 0x005c }
        goto L_0x0039;
    L_0x005c:
        r2 = move-exception;
        r11 = r12;
    L_0x005e:
        if (r13 == 0) goto L_0x0063;
    L_0x0060:
        r13.close();	 Catch:{ IOException -> 0x007b }
    L_0x0063:
        if (r11 == 0) goto L_0x0068;
    L_0x0065:
        r11.close();	 Catch:{ IOException -> 0x007d }
    L_0x0068:
        throw r2;
    L_0x0069:
        r12.flush();	 Catch:{ all -> 0x005c }
        if (r13 == 0) goto L_0x0071;
    L_0x006e:
        r13.close();	 Catch:{ IOException -> 0x0077 }
    L_0x0071:
        if (r12 == 0) goto L_0x0076;
    L_0x0073:
        r12.close();	 Catch:{ IOException -> 0x0079 }
    L_0x0076:
        return r10;
    L_0x0077:
        r2 = move-exception;
        goto L_0x0071;
    L_0x0079:
        r2 = move-exception;
        goto L_0x0076;
    L_0x007b:
        r3 = move-exception;
        goto L_0x0063;
    L_0x007d:
        r3 = move-exception;
        goto L_0x0068;
    L_0x007f:
        r2 = move-exception;
        goto L_0x005e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.library.okhttp.callback.FileCallBack.saveFile(okhttp3.Response):java.io.File");
    }
}
