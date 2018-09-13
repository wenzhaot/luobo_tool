package com.talkingdata.sdk;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.CRC32;

/* compiled from: td */
public class az {
    private static final long l = 3145728;
    private static final String m = "td-cache";
    private static final String n = "td-cache_pos_pref";
    Context a;
    File b;
    RandomAccessFile c;
    String d;
    CRC32 e = new CRC32();
    Lock f = new ReentrantLock();
    FileLock g;
    Lock h = new ReentrantLock();
    long i = 0;
    long j = 0;
    long k = -1;

    public az(Context context, String str) {
        try {
            this.a = context;
            this.d = str;
            this.b = context.getDir(m, 0);
            g();
            a();
            this.j = bi.b(context, n, str, 0);
            this.i = this.j;
            try {
                h();
            } catch (IOException e) {
            }
            if (this.c.length() > l) {
                f();
            }
            b();
        } catch (Throwable th) {
            b();
            throw th;
        }
    }

    public void a() {
        this.h.lock();
        this.g = this.c.getChannel().lock();
    }

    public void b() {
        if (this.g != null) {
            try {
                this.g.release();
                this.h.unlock();
            } catch (Throwable th) {
            }
        }
    }

    public List a(int i) {
        List linkedList = new LinkedList();
        try {
            this.j = bi.b(this.a, n, this.d, 0);
            this.c.seek(this.j);
            while (this.j < this.c.length()) {
                Object a = a(this.j, false);
                if (a != null) {
                    linkedList.add(a);
                }
                if (linkedList.size() >= i) {
                    break;
                }
            }
        } catch (IOException e) {
        }
        if (linkedList.size() == 0) {
            this.i = this.j;
        }
        return linkedList;
    }

    public void c() {
        b(this.j);
        this.i = this.j;
        bi.a(this.a, n, this.d, this.i);
    }

    public void write(byte[] bArr) {
        a(bArr);
    }

    public void d() {
        this.c.getFD().sync();
    }

    public void e() {
        d();
        this.c.close();
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x004d A:{SYNTHETIC, Splitter: B:15:0x004d} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0054  */
    private void f() {
        /*
        r8 = this;
        r6 = 0;
        r0 = r8.i;
        r2 = r8.k;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 >= 0) goto L_0x005a;
    L_0x000a:
        r0 = r8.k;
    L_0x000c:
        r8.j = r0;
        r0 = new java.io.File;
        r1 = r8.b;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = r8.d;
        r2 = r2.append(r3);
        r3 = ".tmp";
        r2 = r2.append(r3);
        r2 = r2.toString();
        r0.<init>(r1, r2);
        r2 = 0;
        r1 = new java.io.FileOutputStream;	 Catch:{ all -> 0x0095 }
        r1.<init>(r0);	 Catch:{ all -> 0x0095 }
    L_0x0031:
        r2 = r8.j;	 Catch:{ all -> 0x004a }
        r4 = r8.c;	 Catch:{ all -> 0x004a }
        r4 = r4.length();	 Catch:{ all -> 0x004a }
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 >= 0) goto L_0x005d;
    L_0x003d:
        r2 = r8.j;	 Catch:{ all -> 0x004a }
        r4 = 0;
        r2 = r8.a(r2, r4);	 Catch:{ all -> 0x004a }
        if (r2 == 0) goto L_0x0031;
    L_0x0046:
        r1.write(r2);	 Catch:{ all -> 0x004a }
        goto L_0x0031;
    L_0x004a:
        r0 = move-exception;
    L_0x004b:
        if (r1 == 0) goto L_0x0050;
    L_0x004d:
        r1.close();	 Catch:{ Throwable -> 0x0093 }
    L_0x0050:
        r1 = r8.c;
        if (r1 == 0) goto L_0x0059;
    L_0x0054:
        r1 = r8.c;
        r1.close();
    L_0x0059:
        throw r0;
    L_0x005a:
        r0 = r8.i;
        goto L_0x000c;
    L_0x005d:
        r1.flush();	 Catch:{ all -> 0x004a }
        if (r1 == 0) goto L_0x0065;
    L_0x0062:
        r1.close();	 Catch:{ Throwable -> 0x0091 }
    L_0x0065:
        r1 = r8.c;
        if (r1 == 0) goto L_0x006e;
    L_0x0069:
        r1 = r8.c;
        r1.close();
    L_0x006e:
        r1 = new java.io.File;
        r2 = r8.b;
        r3 = r8.d;
        r1.<init>(r2, r3);
        r1.delete();
        r0.renameTo(r1);
        r8.g();
        r8.i = r6;
        r8.j = r6;
        r0 = r8.a;
        r1 = "td-cache_pos_pref";
        r2 = r8.d;
        r4 = r8.i;
        com.talkingdata.sdk.bi.a(r0, r1, r2, r4);
        return;
    L_0x0091:
        r1 = move-exception;
        goto L_0x0065;
    L_0x0093:
        r1 = move-exception;
        goto L_0x0050;
    L_0x0095:
        r0 = move-exception;
        r1 = r2;
        goto L_0x004b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.az.f():void");
    }

    private void g() {
        this.c = new RandomAccessFile(new File(this.b, this.d), "rw");
    }

    private void h() {
        Object obj = null;
        while (this.j < this.c.length()) {
            if (this.k == -1 && this.c.length() - this.j < l) {
                this.k = this.j;
            }
            long j = this.j;
            if (a(j) && obj == null) {
                obj = 1;
                if (this.i == 0) {
                    this.i = j;
                }
            }
        }
    }

    private boolean a(long j) {
        try {
            this.f.lock();
            try {
                this.c.seek(j);
                byte readByte = this.c.readByte();
                if (readByte == (byte) 31) {
                    int readInt = this.c.readInt();
                    short readShort = this.c.readShort();
                    if (readShort >= (short) 0 && this.c.getFilePointer() + ((long) readShort) <= this.c.length()) {
                        this.e.reset();
                        for (short s = (short) 0; s < readShort; s++) {
                            this.e.update(this.c.read());
                        }
                        if (this.c.readByte() == (byte) 31 && readInt == ((int) this.e.getValue())) {
                            this.j = this.c.getFilePointer();
                            return true;
                        }
                    }
                } else if (readByte == (byte) 46) {
                    int readInt2 = this.c.readInt();
                    byte readByte2 = this.c.readByte();
                    if (readInt2 >= 0 && ((long) readInt2) < this.c.length() && readByte2 == (byte) 46) {
                        this.j = this.c.getFilePointer();
                        this.i = (long) readInt2;
                        this.f.unlock();
                        return false;
                    }
                }
            } catch (Exception e) {
            }
            this.j = 1 + j;
            this.f.unlock();
            return false;
        } finally {
            this.f.unlock();
        }
    }

    private byte[] a(long j, boolean z) {
        try {
            this.f.lock();
            try {
                this.c.seek(j);
                byte readByte = this.c.readByte();
                if (readByte == (byte) 31) {
                    int readInt = this.c.readInt();
                    short readShort = this.c.readShort();
                    if (readShort >= (short) 0 && this.c.getFilePointer() + ((long) readShort) <= this.c.length()) {
                        byte[] bArr = new byte[readShort];
                        this.c.readFully(bArr);
                        if (this.c.readByte() == (byte) 31) {
                            this.e.reset();
                            this.e.update(bArr);
                            if (readInt == ((int) this.e.getValue())) {
                                this.j = this.c.getFilePointer();
                                return bArr;
                            }
                        }
                    }
                } else if (readByte == (byte) 46) {
                    int readInt2 = this.c.readInt();
                    byte readByte2 = this.c.readByte();
                    if (readInt2 >= 0 && ((long) readInt2) < this.c.length() && readByte2 == (byte) 46) {
                        this.j = this.c.getFilePointer();
                        if (z) {
                            this.i = (long) readInt2;
                        }
                        this.f.unlock();
                        return null;
                    }
                }
            } catch (Exception e) {
            }
            this.j = 1 + j;
            this.f.unlock();
            return null;
        } finally {
            this.f.unlock();
        }
    }

    private void a(byte[] bArr) {
        try {
            this.f.lock();
            this.c.seek(this.c.length());
            this.c.writeByte(31);
            this.e.reset();
            this.e.update(bArr);
            this.c.writeInt((int) this.e.getValue());
            this.c.writeShort(bArr.length);
            this.c.write(bArr);
            this.c.writeByte(31);
        } finally {
            this.f.unlock();
        }
    }

    private void b(long j) {
        try {
            this.f.lock();
            this.c.seek(this.c.length());
            this.c.writeByte(46);
            this.c.writeInt((int) j);
            this.c.writeByte(46);
        } finally {
            this.f.unlock();
        }
    }
}
