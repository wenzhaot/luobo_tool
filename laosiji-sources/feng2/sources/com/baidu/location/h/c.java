package com.baidu.location.h;

import java.io.File;
import java.io.RandomAccessFile;

public class c {
    static c c;
    String a = "firll.dat";
    int b = 3164;
    int d = 0;
    int e = 20;
    int f = 40;
    int g = 60;
    int h = 80;
    int i = 100;

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0059 A:{SYNTHETIC, Splitter: B:25:0x0059} */
    private long a(int r8) {
        /*
        r7 = this;
        r0 = -1;
        r2 = com.baidu.location.h.k.h();
        if (r2 != 0) goto L_0x0009;
    L_0x0008:
        return r0;
    L_0x0009:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r2 = r3.append(r2);
        r3 = java.io.File.separator;
        r2 = r2.append(r3);
        r3 = r7.a;
        r2 = r2.append(r3);
        r3 = r2.toString();
        r2 = 0;
        r4 = new java.io.RandomAccessFile;	 Catch:{ Exception -> 0x004c, all -> 0x0055 }
        r5 = "rw";
        r4.<init>(r3, r5);	 Catch:{ Exception -> 0x004c, all -> 0x0055 }
        r2 = (long) r8;
        r4.seek(r2);	 Catch:{ Exception -> 0x0063, all -> 0x0061 }
        r5 = r4.readInt();	 Catch:{ Exception -> 0x0063, all -> 0x0061 }
        r2 = r4.readLong();	 Catch:{ Exception -> 0x0063, all -> 0x0061 }
        r6 = r4.readInt();	 Catch:{ Exception -> 0x0063, all -> 0x0061 }
        if (r5 != r6) goto L_0x0044;
    L_0x003d:
        if (r4 == 0) goto L_0x0042;
    L_0x003f:
        r4.close();	 Catch:{ IOException -> 0x005d }
    L_0x0042:
        r0 = r2;
        goto L_0x0008;
    L_0x0044:
        if (r4 == 0) goto L_0x0008;
    L_0x0046:
        r4.close();	 Catch:{ IOException -> 0x004a }
        goto L_0x0008;
    L_0x004a:
        r2 = move-exception;
        goto L_0x0008;
    L_0x004c:
        r3 = move-exception;
    L_0x004d:
        if (r2 == 0) goto L_0x0008;
    L_0x004f:
        r2.close();	 Catch:{ IOException -> 0x0053 }
        goto L_0x0008;
    L_0x0053:
        r2 = move-exception;
        goto L_0x0008;
    L_0x0055:
        r0 = move-exception;
        r4 = r2;
    L_0x0057:
        if (r4 == 0) goto L_0x005c;
    L_0x0059:
        r4.close();	 Catch:{ IOException -> 0x005f }
    L_0x005c:
        throw r0;
    L_0x005d:
        r0 = move-exception;
        goto L_0x0042;
    L_0x005f:
        r1 = move-exception;
        goto L_0x005c;
    L_0x0061:
        r0 = move-exception;
        goto L_0x0057;
    L_0x0063:
        r2 = move-exception;
        r2 = r4;
        goto L_0x004d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.h.c.a(int):long");
    }

    public static c a() {
        if (c == null) {
            c = new c();
        }
        return c;
    }

    private void a(int i, long j) {
        String h = k.h();
        if (h != null) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(h + File.separator + this.a, "rw");
                randomAccessFile.seek((long) i);
                randomAccessFile.writeInt(this.b);
                randomAccessFile.writeLong(j);
                randomAccessFile.writeInt(this.b);
                randomAccessFile.close();
            } catch (Exception e) {
            }
        }
    }

    public void a(long j) {
        a(this.d, j);
    }

    public long b() {
        return a(this.d);
    }

    public void b(long j) {
        a(this.e, j);
    }

    public long c() {
        return a(this.e);
    }

    public void c(long j) {
        a(this.g, j);
    }

    public long d() {
        return a(this.g);
    }
}
